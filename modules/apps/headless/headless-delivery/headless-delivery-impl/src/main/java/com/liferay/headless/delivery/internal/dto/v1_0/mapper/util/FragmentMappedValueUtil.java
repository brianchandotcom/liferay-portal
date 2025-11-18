/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.mapper.util;

import com.liferay.headless.delivery.dto.v1_0.ClassFieldsReference;
import com.liferay.headless.delivery.dto.v1_0.ClassPKReference;
import com.liferay.headless.delivery.dto.v1_0.ContextReference;
import com.liferay.headless.delivery.dto.v1_0.Field;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public static ClassFieldsReference toDisplayPageClassFieldsReference(
		String displayPageTemplateId) {

		if (StringUtil.startsWith(
				displayPageTemplateId,
				LayoutPageTemplateEntry.class.getSimpleName())) {

			try {
				Matcher matcher = _pattern.matcher(displayPageTemplateId);

				if (matcher.find()) {
					LayoutPageTemplateEntry layoutPageTemplateEntry =
						LayoutPageTemplateEntryLocalServiceUtil.
							getLayoutPageTemplateEntry(
								GetterUtil.getLong(matcher.group(1)));

					return new ClassFieldsReference() {
						{
							setClassName(
								() -> LayoutPageTemplateEntry.class.getName());
							setFields(
								() -> new Field[] {
									new Field() {
										{
											setFieldName(
												() -> "externalReferenceCode");
											setFieldValue(
												layoutPageTemplateEntry::
													getExternalReferenceCode);
										}
									}
								});
						}
					};
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Item reference could not be set since no display " +
							"page template could be obtained",
						exception);
				}

				return null;
			}
		}

		return null;
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
					setContextSource(() -> ContextSource.COLLECTION_ITEM);
				}
			};
		}

		if (layoutJSONObject != null) {
			return toLayoutClassFieldsReference(layoutJSONObject);
		}

		if (Validator.isNotNull(mappedField)) {
			return new ContextReference() {
				{
					setContextSource(() -> ContextSource.DISPLAY_PAGE_ITEM);
				}
			};
		}

		return new ClassPKReference() {
			{
				setClassName(() -> _toItemClassName(jsonObject));
				setClassPK(() -> _toItemClassPK(jsonObject));
			}
		};
	}

	public static ClassFieldsReference toLayoutClassFieldsReference(
		JSONObject layoutJSONObject) {

		final Layout layout;

		try {
			layout = LayoutServiceUtil.getLayout(
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
				setClassName(() -> Layout.class.getName());
				setFields(
					() -> {
						Group group = GroupLocalServiceUtil.getGroup(
							layout.getGroupId());

						return new Field[] {
							new Field() {
								{
									setFieldName(() -> "friendlyURL");
									setFieldValue(layout::getFriendlyURL);
								}
							},
							new Field() {
								{
									setFieldName(() -> "privatePage");
									setFieldValue(
										() -> String.valueOf(
											layout.isPrivateLayout()));
								}
							},
							new Field() {
								{
									setFieldName(() -> "siteKey");
									setFieldValue(group::getGroupKey);
								}
							}
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

	private static final Pattern _pattern = Pattern.compile("_(\\d+)$");

}