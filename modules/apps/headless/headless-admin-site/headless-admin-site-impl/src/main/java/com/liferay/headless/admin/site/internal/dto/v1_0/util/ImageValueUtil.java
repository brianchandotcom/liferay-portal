/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.DirectFragmentImageValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentImageValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.admin.site.dto.v1_0.ImageValue;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.ItemImageValue;
import com.liferay.headless.admin.site.dto.v1_0.MappedFragmentImageValue;
import com.liferay.headless.admin.site.dto.v1_0.URLImageValue;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class ImageValueUtil {

	public static FragmentImageValue toFragmentImageValue(
			long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
			JSONObject jsonObject, long scopeGroupId)
		throws Exception {

		if (FragmentMappingUtil.isMappedValue(jsonObject)) {
			FragmentMappedValue fragmentMappedValue =
				FragmentMappingUtil.toFragmentMappedValue(
					companyId, infoItemServiceRegistry, jsonObject,
					scopeGroupId);

			if (fragmentMappedValue == null) {
				return null;
			}

			MappedFragmentImageValue mappedFragmentImageValue =
				new MappedFragmentImageValue();

			mappedFragmentImageValue.setFragmentMappedValue(
				() -> fragmentMappedValue);
			mappedFragmentImageValue.setType(FragmentImageValue.Type.MAPPED);

			return mappedFragmentImageValue;
		}

		Map<String, ImageValue> imageValueMap =
			LocalizedValueUtil.toLocalizedValues(
				jsonObject,
				key -> _getImageValue(
					companyId, scopeGroupId, jsonObject.get(key)));

		if (MapUtil.isEmpty(imageValueMap)) {
			return null;
		}

		DirectFragmentImageValue directFragmentImageValue =
			new DirectFragmentImageValue();

		directFragmentImageValue.setValue_i18n(() -> imageValueMap);
		directFragmentImageValue.setType(FragmentImageValue.Type.DIRECT);

		return directFragmentImageValue;
	}

	public static JSONObject toFragmentImageValueJSONObject(
			FragmentImageValue fragmentImageValue,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		if (fragmentImageValue == null) {
			return null;
		}

		if (fragmentImageValue instanceof DirectFragmentImageValue) {
			DirectFragmentImageValue directFragmentImageValue =
				(DirectFragmentImageValue)fragmentImageValue;

			return LocalizedValueUtil.toJSONObject(
				directFragmentImageValue.getValue_i18n(),
				imageValue -> _getImageValueJSONObject(
					imageValue, layoutStructureItemImporterContext));
		}

		if (!(fragmentImageValue instanceof MappedFragmentImageValue)) {
			return null;
		}

		MappedFragmentImageValue mappedFragmentImageValue =
			(MappedFragmentImageValue)fragmentImageValue;

		return FragmentMappingUtil.getFragmentMappedValueJSONObject(
			layoutStructureItemImporterContext.getCompanyId(),
			mappedFragmentImageValue.getFragmentMappedValue(),
			layoutStructureItemImporterContext.getInfoItemServiceRegistry(),
			layoutStructureItemImporterContext.getGroupId());
	}

	private static ImageValue _getImageValue(
			long companyId, long scopeGroupId, Object value)
		throws Exception {

		JSONObject jsonObject = null;

		if (value instanceof JSONObject) {
			jsonObject = (JSONObject)value;
		}
		else {
			String valueString = GetterUtil.getString(value);

			if (Validator.isNull(valueString)) {
				return null;
			}

			jsonObject = JSONFactoryUtil.safeCreateJSONObject(
				valueString, true);

			if (jsonObject == null) {
				return _getURLImageValue(valueString);
			}
		}

		if (JSONUtil.isEmpty(jsonObject)) {
			return null;
		}

		if (FileEntryUtil.isItemImageValue(jsonObject)) {
			ItemExternalReference itemExternalReference =
				FileEntryUtil.getFileEntryItemExternalReference(
					companyId, jsonObject, scopeGroupId);

			if (itemExternalReference == null) {
				return null;
			}

			ItemImageValue itemImageValue = new ItemImageValue();

			itemImageValue.setItemExternalReference(
				() -> itemExternalReference);
			itemImageValue.setType(ImageValue.Type.ITEM);

			return itemImageValue;
		}

		return _getURLImageValue(jsonObject.getString("url"));
	}

	private static JSONObject _getImageValueJSONObject(
			ImageValue imageValue,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws PortalException {

		if (imageValue == null) {
			return null;
		}

		if (Objects.equals(imageValue.getType(), ImageValue.Type.ITEM)) {
			ItemImageValue itemImageValue = (ItemImageValue)imageValue;

			ItemExternalReference itemExternalReference =
				itemImageValue.getItemExternalReference();

			if ((itemExternalReference == null) ||
				Validator.isNull(
					itemExternalReference.getExternalReferenceCode())) {

				return null;
			}

			return FileEntryUtil.getFileEntryJSONObject(
				layoutStructureItemImporterContext.getCompanyId(),
				itemExternalReference.getExternalReferenceCode(),
				itemExternalReference.getScope(),
				layoutStructureItemImporterContext.getGroupId());
		}

		URLImageValue urlImageValue = (URLImageValue)imageValue;

		if (Validator.isNull(urlImageValue.getUrl())) {
			return null;
		}

		return JSONUtil.put("url", urlImageValue.getUrl());
	}

	private static URLImageValue _getURLImageValue(String url) {
		if (Validator.isNull(url)) {
			return null;
		}

		URLImageValue urlImageValue = new URLImageValue();

		urlImageValue.setType(URLImageValue.Type.URL);
		urlImageValue.setUrl(() -> url);

		return urlImageValue;
	}

}