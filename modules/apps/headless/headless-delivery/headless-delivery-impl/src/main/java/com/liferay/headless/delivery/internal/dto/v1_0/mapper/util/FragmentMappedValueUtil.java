/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.mapper.util;

import com.liferay.headless.delivery.dto.v1_0.ClassFieldsReference;
import com.liferay.headless.delivery.dto.v1_0.ClassPKReference;
import com.liferay.headless.delivery.dto.v1_0.ContextReference;
import com.liferay.headless.delivery.dto.v1_0.Field;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Jürgen Kappler
 */
public class FragmentMappedValueUtil {

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

	public static boolean isSaveFragmentMappedValue(
		JSONObject jsonObject, boolean saveMapping) {

		if ((jsonObject == null) || !saveMapping) {
			return false;
		}

		if (jsonObject.has("classNameId") && jsonObject.has("classPK") &&
			jsonObject.has("fieldId")) {

			return true;
		}

		if (jsonObject.has("collectionFieldId") || jsonObject.has("layout") ||
			jsonObject.has("mappedField")) {

			return true;
		}

		return false;
	}

	public static Object toItemReference(JSONObject jsonObject) {
		String collectionFieldId = jsonObject.getString("collectionFieldId");
		String fieldId = jsonObject.getString("fieldId");
		JSONObject layoutJSONObject = jsonObject.getJSONObject("layout");
		String mappedField = jsonObject.getString("mappedField");

		if (Validator.isNull(collectionFieldId) && Validator.isNull(fieldId) &&
			(layoutJSONObject == null) && Validator.isNull(mappedField)) {

			return null;
		}

		if (Validator.isNotNull(collectionFieldId)) {
			return new ContextReference() {
				{
					contextSource = ContextSource.COLLECTION_ITEM;
				}
			};
		}

		if (layoutJSONObject != null) {
			return toLayoutClassFieldsReference(layoutJSONObject);
		}

		if (Validator.isNotNull(mappedField)) {
			return new ContextReference() {
				{
					contextSource = ContextSource.DISPLAY_PAGE_ITEM;
				}
			};
		}

		return new ClassPKReference() {
			{
				className = _toItemClassName(jsonObject);
				classPK = _toItemClassPK(jsonObject);
			}
		};
	}

	public static ClassFieldsReference toLayoutClassFieldsReference(
		JSONObject layoutJSONObject) {

		final Layout layout;

		try {
			layout = LayoutLocalServiceUtil.getLayout(
				layoutJSONObject.getLong("groupId"),
				layoutJSONObject.getBoolean("privateLayout"),
				layoutJSONObject.getLong("layoutId"));
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Item reference could not be set since no layout could " +
						"be obtained",
					portalException);
			}

			return null;
		}

		return new ClassFieldsReference() {
			{
				className = Layout.class.getName();

				setFields(
					() -> {
						Field friendlyURLField = new Field();

						friendlyURLField.setFieldName("friendlyURL");
						friendlyURLField.setFieldValue(layout.getFriendlyURL());

						Field privatePageField = new Field();

						privatePageField.setFieldName("privatePage");
						privatePageField.setFieldValue(
							String.valueOf(layout.isPrivateLayout()));

						Field siteKeyField = new Field();

						Group group = GroupLocalServiceUtil.getGroup(
							layout.getGroupId());

						siteKeyField.setFieldName("siteKey");
						siteKeyField.setFieldValue(group.getGroupKey());

						return new Field[] {
							friendlyURLField, privatePageField, siteKeyField
						};
					});
			}
		};
	}

	private static String _toItemClassName(JSONObject jsonObject) {
		String classNameIdString = jsonObject.getString("classNameId");

		if (Validator.isNull(classNameIdString)) {
			return null;
		}

		long classNameId = 0;

		try {
			classNameId = Long.parseLong(classNameIdString);
		}
		catch (NumberFormatException numberFormatException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					String.format(
						"Item class name could not be set since class name " +
							"ID %s could not be parsed to a long",
						classNameIdString),
					numberFormatException);
			}

			return null;
		}

		String className = null;

		try {
			className = PortalUtil.getClassName(classNameId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Item class name could not be set since no class name " +
						"could be obtained for class name ID " + classNameId,
					exception);
			}

			return null;
		}

		return className;
	}

	private static Long _toItemClassPK(JSONObject jsonObject) {
		String classPKString = jsonObject.getString("classPK");

		if (Validator.isNull(classPKString)) {
			return null;
		}

		Long classPK = null;

		try {
			classPK = Long.parseLong(classPKString);
		}
		catch (NumberFormatException numberFormatException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					String.format(
						"Item class PK could not be set since class PK %s " +
							"could not be parsed to a long",
						classPKString),
					numberFormatException);
			}

			return null;
		}

		return classPK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentMappedValueUtil.class);

}