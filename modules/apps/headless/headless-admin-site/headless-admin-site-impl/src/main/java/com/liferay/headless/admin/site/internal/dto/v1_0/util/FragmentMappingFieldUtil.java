/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Rubén Pulido
 */
public class FragmentMappingFieldUtil {

	public static String getFieldKey(JSONObject jsonObject) {
		String collectionFieldId = jsonObject.getString("collectionFieldId");

		if (Validator.isNotNull(collectionFieldId)) {
			return collectionFieldId;
		}

		String fieldId = jsonObject.getString("fieldId");

		if (Validator.isNotNull(fieldId)) {
			return fieldId;
		}

		String mappedField = jsonObject.getString("mappedField");

		if (Validator.isNotNull(mappedField)) {
			return mappedField;
		}

		return null;
	}

	private String _toExternalUniqueId(
		String className, InfoItemServiceRegistry infoItemServiceRegistry,
		String fieldName, Object infoItem,
		LayoutPageTemplateEntry layoutPageTemplateEntry, long scopeGroupId) {

		InfoItemFormProvider<Object> infoItemFormProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormProvider.class, className);

		if (infoItemFormProvider == null) {
			return fieldName;
		}

		InfoForm infoForm = null;

		if (infoItem != null) {
			infoForm = infoItemFormProvider.getInfoForm(infoItem);

			if (infoForm == null) {
				return fieldName;
			}

			InfoField<?> infoField = infoForm.getInfoField(fieldName);

			if (infoField == null) {
				return fieldName;
			}

			return infoField.getExternalUniqueId();
		}

		if (layoutPageTemplateEntry == null) {
			return fieldName;
		}

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				layoutPageTemplateEntry.getClassName());

		if (infoItemFormVariationsProvider == null) {
			return fieldName;
		}

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				layoutPageTemplateEntry.getGroupId(),
				String.valueOf(layoutPageTemplateEntry.getClassTypeId()));

		if (infoItemFormVariation == null) {
			return fieldName;
		}

		try {
			infoForm = infoItemFormProvider.getInfoForm(
				infoItemFormVariation.getKey(), scopeGroupId);
		}
		catch (NoSuchFormVariationException noSuchFormVariationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFormVariationException);
			}
		}

		if (infoForm == null) {
			return fieldName;
		}

		InfoField<?> infoField = infoForm.getInfoField(fieldName);

		if (infoField == null) {
			return fieldName;
		}

		return infoField.getExternalUniqueId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentMappingFieldUtil.class);

}