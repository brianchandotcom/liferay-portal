/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoField;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

/**
 * @author Rubén Pulido
 */
public class FragmentMappingFieldUtil {

	public static String getFieldKey(
		DTOConverterContext dtoConverterContext,
		InfoItemServiceRegistry infoItemServiceRegistry, JSONObject jsonObject,
		long scopeGroupId) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(CompanyThreadLocal.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			String collectionFieldId = jsonObject.getString(
				"collectionFieldId");

			if (Validator.isNotNull(collectionFieldId)) {
				return _getCollectionFieldKey(
					collectionFieldId,
					(InfoForm)dtoConverterContext.getAttribute(
						"collectionInfoForm"));
			}

			if (Validator.isNotNull(jsonObject.getString("fieldId"))) {
				return _getInstanceFieldKey(
					infoItemServiceRegistry, jsonObject);
			}

			String mappedField = jsonObject.getString("mappedField");

			if (Validator.isNotNull(mappedField)) {
				return _getContextFieldKey(
					(InfoForm)dtoConverterContext.getAttribute(
						"displayPageTemplateInfoForm"),
					mappedField);
			}
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		return null;
	}

	private static String _getCollectionFieldKey(
		String collectionFieldId, InfoForm infoForm) {

		if (infoForm == null) {
			return collectionFieldId;
		}

		InfoField<?> infoField = infoForm.getInfoField(collectionFieldId);

		if (infoField == null) {
			return collectionFieldId;
		}

		return infoField.getExternalUniqueId();
	}

	private static String _getContextFieldKey(
		InfoForm infoForm, String mappedField) {

		if (infoForm == null) {
			return mappedField;
		}

		InfoField<?> infoField = infoForm.getInfoField(mappedField);

		if (infoField == null) {
			return mappedField;
		}

		return infoField.getExternalUniqueId();
	}

	private static Object _getInfoItem(
		InfoItemReference infoItemReference,
		InfoItemServiceRegistry infoItemServiceRegistry) {

		if (infoItemReference == null) {
			return null;
		}

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		InfoItemObjectProvider<?> infoItemObjectProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemObjectProvider.class, infoItemReference.getClassName(),
				infoItemIdentifier.getInfoItemServiceFilter());

		if (infoItemObjectProvider == null) {
			return null;
		}

		try {
			return infoItemObjectProvider.getInfoItem(infoItemIdentifier);
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchInfoItemException);
			}
		}

		return null;
	}

	private static String _getInstanceFieldKey(
		InfoItemServiceRegistry infoItemServiceRegistry,
		JSONObject jsonObject) {

		InfoItemReference infoItemReference = null;

		int classPK = GetterUtil.getInteger(jsonObject.getString("classPK"));

		if (classPK > 0) {
			infoItemReference = new InfoItemReference(
				jsonObject.getString("className"),
				new ClassPKInfoItemIdentifier(classPK));
		}
		else {
			infoItemReference = new InfoItemReference(
				jsonObject.getString("className"),
				new ERCInfoItemIdentifier(
					jsonObject.getString("externalReferenceCode"),
					jsonObject.getString("scopeExternalReferenceCode")));
		}

		Object infoItem = _getInfoItem(
			infoItemReference, infoItemServiceRegistry);

		if (infoItem == null) {
			return jsonObject.getString("fieldId");
		}

		String fieldName = jsonObject.getString("fieldId");
		InfoItemFormProvider<Object> infoItemFormProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormProvider.class, jsonObject.getString("className"));

		if (infoItemFormProvider == null) {
			return fieldName;
		}

		InfoForm infoForm = infoItemFormProvider.getInfoForm(infoItem);

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