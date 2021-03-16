/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal.hit.contributor;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.hit.HitContributor;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.result.ResultBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=dlfileentry-metadata",
	service = HitContributor.class
)
public class DLFileEntryMetadataHitContributor implements HitContributor {

	@Override
	public void contribute(
		JSONObject hitJSONObject, Document document,
		ResultBuilder resultBuilder, BlueprintsAttributes blueprintsAttributes,
		ResourceBundle resourceBundle) {

		String entryClassName = document.getString(Field.ENTRY_CLASS_NAME);

		if (!entryClassName.equals(DLFileEntry.class.getName()) ||
			Validator.isBlank(document.getString("mimeType")) ||
			Validator.isNull(document.getLong("size"))) {

			return;
		}

		try {
			_contribute(hitJSONObject, document, blueprintsAttributes);
		}
		catch (Exception exception) {
			_log.error(exception.getMessage(), exception);
		}
	}

	private void _contribute(
			JSONObject hitJSONObject, Document document,
			BlueprintsAttributes blueprintsAttributes)
		throws Exception {

		_setFormat(hitJSONObject, document);

		_setImageDimensions(hitJSONObject, document, blueprintsAttributes);

		_setSize(hitJSONObject, document);
	}

	private String _getImageDimensions(
			Document document, BlueprintsAttributes blueprintsAttributes)
		throws Exception {

		String length = document.getString(
			_getTikaRawMetadataField(blueprintsAttributes, "LENGTH"));

		String width = document.getString(
			_getTikaRawMetadataField(blueprintsAttributes, "WIDTH"));

		if (Validator.isBlank(length) || Validator.isBlank(width)) {
			return null;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(width);
		sb.append(" x ");
		sb.append(length);
		sb.append(" px");

		return sb.toString();
	}

	private String _getSizeFormatted(Document document) {
		long size = document.getLong("size");

		StringBundler sb = new StringBundler(2);

		if (size >= _MBYTES) {
			sb.append(Math.round(size / (float)_MBYTES));
			sb.append(" MB");
		}
		else if (size >= _KBYTES) {
			sb.append(Math.round(size / (float)_KBYTES));
			sb.append(" KB");
		}
		else {
			sb.append(1);
			sb.append(" KB");
		}

		return sb.toString();
	}

	private String _getTikaRawMetadataField(
			BlueprintsAttributes blueprintsAttributes, String key)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append("ddm__text__");
		sb.append(
			GetterUtil.getString(_getTikaRawStructureId(blueprintsAttributes)));
		sb.append("__TIFF_IMAGE_");
		sb.append(key);

		return sb.toString();
	}

	private long _getTikaRawStructureId(
			BlueprintsAttributes blueprintsAttributes)
		throws Exception {

		long companyId = blueprintsAttributes.getCompanyId();

		if (_tikaRawStructureIdMap.get(companyId) == null) {
			DynamicQuery structureQuery =
				_ddmStructureLocalService.dynamicQuery();

			structureQuery.add(
				RestrictionsFactoryUtil.eq("structureKey", "TIKARAWMETADATA"));
			structureQuery.add(
				RestrictionsFactoryUtil.eq("companyId", companyId));

			List<DDMStructure> structures =
				_ddmStructureLocalService.dynamicQuery(structureQuery);

			DDMStructure structure = structures.get(0);

			_tikaRawStructureIdMap.put(companyId, structure.getStructureId());
		}

		return _tikaRawStructureIdMap.get(companyId);
	}

	private void _setFormat(JSONObject hitJSONObject, Document document) {
		String mimeType = document.getString("mimeType");

		if (_mimeTypes.containsKey(mimeType)) {
			mimeType = _mimeTypes.get(mimeType);
		}
		else if (mimeType.startsWith("application_")) {
			mimeType = mimeType.split("application_")[1];
		}
		else if (mimeType.startsWith("image_")) {
			mimeType = mimeType.split("image_")[1];
		}
		else if (mimeType.startsWith("text_")) {
			mimeType = mimeType.split("text_")[1];
		}
		else if (mimeType.startsWith("video_")) {
			mimeType = mimeType.split("video_")[1];
		}

		hitJSONObject.put("fileFormat", mimeType);
	}

	private void _setImageDimensions(
			JSONObject hitJSONObject, Document document,
			BlueprintsAttributes blueprintsAttributes)
		throws Exception {

		String dimensions = _getImageDimensions(document, blueprintsAttributes);

		if (!Validator.isBlank(dimensions)) {
			hitJSONObject.put(
				"imageDimensions",
				_getImageDimensions(document, blueprintsAttributes));
		}
	}

	private void _setSize(JSONObject hitJSONObject, Document document) {
		hitJSONObject.put("fileSize", _getSizeFormatted(document));
	}

	private static final long _KBYTES = 1024;

	private static final long _MBYTES = 1024 * 1024;

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryMetadataHitContributor.class);

	private static final Map<String, String> _mimeTypes = HashMapBuilder.put(
		"application_vnd.ms-excel", "xls"
	).put(
		"application_vnd.ms-powerpoint", "ppt"
	).put(
		"application_vnd.ms-word", "doc"
	).put(
		"application_vnd.oasis.opendocument.presentation", "odp"
	).put(
		"application_vnd.oasis.opendocument.spreadsheet", "ods"
	).put(
		"application_vnd.oasis.opendocument.text", "odt"
	).put(
		"application_vnd.openxmlformats-officedocument.presentationml." +
			"presentation",
		"pptx"
	).put(
		"application_vnd.openxmlformats-officedocument.spreadsheetml.sheet",
		"xlsx"
	).put(
		"application_vnd.openxmlformats-officedocument.wordprocessingml." +
			"document",
		"docx"
	).build();

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLAppService _dLAppService;

	private final Map<Long, Long> _tikaRawStructureIdMap = new HashMap<>();

}