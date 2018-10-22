/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.journal.web.internal.asset.display.contributor;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.AssetDisplayContributorField;
import com.liferay.asset.display.contributor.AssetDisplayContributorFieldTracker;
import com.liferay.asset.display.contributor.AssetDisplayField;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.Value;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.web.asset.JournalArticleDDMFormValuesReader;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = AssetDisplayContributor.class)
public class JournalArticleAssetDisplayContributor
	implements AssetDisplayContributor {

	@Override
	public Set<AssetDisplayField> getAssetDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException {

		// Fields for asset entry

		Set<AssetDisplayField> assetDisplayFields =
			_assetDisplayContributorFieldTracker.
				getAssetEntryAssetDisplayFields(locale);

		// Fields for the specific asset type

		Set<AssetDisplayField> journalArticleAssetDisplayFields =
			_assetDisplayContributorFieldTracker.getAssetDisplayFields(
				getClassName(), locale);

		assetDisplayFields.addAll(journalArticleAssetDisplayFields);

		// Fields for the class type

		List<AssetDisplayField> classTypeFields = getClassTypeFields(
			classTypeId, locale);

		assetDisplayFields.addAll(classTypeFields);

		return assetDisplayFields;
	}

	@Override
	public Map<String, Object> getAssetDisplayFieldsValues(
			AssetEntry assetEntry, Locale locale)
		throws PortalException {

		// Field values for asset entry

		Map<String, Object> parameterMap =
			_assetDisplayContributorFieldTracker.
				getAssetEntryAssetDisplayFieldsValues(assetEntry, locale);

		// Field values for the specific asset type

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					assetEntry.getClassNameId());

		List<AssetDisplayContributorField> assetDisplayContributorFields =
			_assetDisplayContributorFieldTracker.
				getAssetDisplayContributorFields(getClassName());

		AssetRenderer<JournalArticle> assetRenderer =
			assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());

		for (AssetDisplayContributorField assetDisplayContributorField :
				assetDisplayContributorFields) {

			parameterMap.put(
				assetDisplayContributorField.getKey(),
				assetDisplayContributorField.getValue(
					assetRenderer.getAssetObject(), locale));
		}

		// Field values for the class type

		Map<String, Object> classTypeValues = _getClassTypeValues(
			assetRenderer.getAssetObject(), locale);

		parameterMap.putAll(classTypeValues);

		return parameterMap;
	}

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public String getLabel(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	private Map<String, Object> _getClassTypeValues(
		JournalArticle article, Locale locale) {

		Map<String, Object> classTypeValues = new HashMap<>();

		JournalArticleDDMFormValuesReader journalArticleDDMFormValuesReader =
			new JournalArticleDDMFormValuesReader(article);

		journalArticleDDMFormValuesReader.setFieldsToDDMFormValuesConverter(
			_fieldsToDDMFormValuesConverter);
		journalArticleDDMFormValuesReader.setJournalConverter(
			_journalConverter);

		try {
			DDMFormValues ddmFormValues =
				journalArticleDDMFormValuesReader.getDDMFormValues();

			Map<String, List<DDMFormFieldValue>> ddmFormFieldsValuesMap =
				ddmFormValues.getDDMFormFieldValuesMap();

			for (Map.Entry<String, List<DDMFormFieldValue>>
					entry: ddmFormFieldsValuesMap.entrySet()) {

				List<DDMFormFieldValue> ddmFormFieldValues = entry.getValue();

				DDMFormFieldValue ddmFormFieldValue0 = ddmFormFieldValues.get(
					0);

				Value value = ddmFormFieldValue0.getValue();

				String fieldValue = value.getString(locale);

				if (Objects.equals(ddmFormFieldValue0.getType(), "ddm-image")) {
					fieldValue = _transformFileEntryURL(fieldValue);
				}

				classTypeValues.put(entry.getKey(), fieldValue);
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return classTypeValues;
	}

	private String _transformFileEntryURL(String data) {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(data);

			String uuid = jsonObject.getString("uuid");
			long groupId = jsonObject.getLong("groupId");

			if (Validator.isNull(uuid) && (groupId == 0)) {
				return StringPool.BLANK;
			}

			FileEntry fileEntry = _dlAppService.getFileEntryByUuidAndGroupId(
				uuid, groupId);

			return DLUtil.getImagePreviewURL(fileEntry, null);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleAssetDisplayContributor.class);

	@Reference
	private AssetDisplayContributorFieldTracker
		_assetDisplayContributorFieldTracker;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private JournalConverter _journalConverter;

}