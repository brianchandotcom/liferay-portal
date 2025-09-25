/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.FragmentLink;
import com.liferay.headless.admin.site.dto.v1_0.FragmentLinkInlineValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentLinkMappedValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentLinkValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentMappedValueItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.Mapping;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Mikel Lorza
 */
public class FragmentLinkValueUtil {

	public static boolean isSaveFragmentMappedValue(JSONObject jsonObject) {
		if (jsonObject == null) {
			return false;
		}

		if ((jsonObject.has("classNameId") &&
			 jsonObject.has("externalReferenceCode") &&
			 jsonObject.has("fieldId")) ||
			jsonObject.has("layout")) {

			return true;
		}

		return false;
	}

	public static FragmentLink toFragmentLink(
		InfoItemServiceRegistry infoItemServiceRegistry, JSONObject jsonObject,
		long scopeGroupId) {

		if (jsonObject == null) {
			return null;
		}

		boolean saveFragmentMappedValue = isSaveFragmentMappedValue(jsonObject);

		if (jsonObject.isNull("href") && !saveFragmentMappedValue) {
			return null;
		}

		return new FragmentLink() {
			{
				setTarget(
					() -> {
						String target = jsonObject.getString("target");

						if (Validator.isNull(target)) {
							return null;
						}

						if (StringUtil.equalsIgnoreCase(target, "_parent") ||
							StringUtil.equalsIgnoreCase(target, "_top")) {

							target = "_self";
						}

						return Target.create(
							TargetUtil.toExternalValue(target));
					});
				setValue(
					() -> _toFragmentLinkValue(
						infoItemServiceRegistry, jsonObject,
						saveFragmentMappedValue, scopeGroupId));
			}
		};
	}

	public static FragmentLinkMappedValue toFragmentLinkMappedValue(
		InfoItemServiceRegistry infoItemServiceRegistry, JSONObject jsonObject,
		long scopeGroupId) {

		return new FragmentLinkMappedValue() {
			{
				setMapping(
					() -> new Mapping() {
						{
							setFieldKey(() -> _getFieldKey(jsonObject));
							setItemReference(
								() ->
									_toFragmentMappedValueItemExternalReference(
										infoItemServiceRegistry, jsonObject,
										scopeGroupId));
						}
					});
				setType(Type.FRAGMENT_MAPPED_VALUE);
			}
		};
	}

	private static String _getFieldKey(JSONObject jsonObject) {
		String fieldId = jsonObject.getString("fieldId");

		if (Validator.isNotNull(fieldId)) {
			return fieldId;
		}

		return null;
	}

	private static FragmentLinkValue _toFragmentLinkValue(
		InfoItemServiceRegistry infoItemServiceRegistry, JSONObject jsonObject,
		boolean saveFragmentMappedValue, long scopeGroupId) {

		if (saveFragmentMappedValue) {
			return toFragmentLinkMappedValue(
				infoItemServiceRegistry, jsonObject, scopeGroupId);
		}

		return new FragmentLinkInlineValue() {
			{
				setType(Type.FRAGMENT_INLINE_VALUE);
				setValue_i18n(
					() -> LocalizedValueUtil.toLocalizedValues(
						jsonObject.getJSONObject("href")));
			}
		};
	}

	private static FragmentMappedValueItemExternalReference
		_toFragmentMappedValueItemExternalReference(
			InfoItemServiceRegistry infoItemServiceRegistry,
			JSONObject jsonObject, long scopeGroupId) {

		String fieldId = jsonObject.getString("fieldId");
		JSONObject layoutJSONObject = jsonObject.getJSONObject("layout");

		if (Validator.isNull(fieldId) && (layoutJSONObject == null)) {
			return null;
		}

		if (layoutJSONObject != null) {
			return _toLayoutFragmentMappedValueItemExternalReference(
				layoutJSONObject, scopeGroupId);
		}

		String itemClassPK = jsonObject.getString("classPK");
		String itemExternalReferenceCode = jsonObject.getString(
			"externalReferenceCode");
		String itemClassName = _toItemClassName(jsonObject);

		if ((itemClassPK == null) || (itemClassName == null) ||
			(itemExternalReferenceCode == null)) {

			return null;
		}

		return new FragmentMappedValueItemExternalReference() {
			{
				setClassName(() -> itemClassName);

				setExternalReferenceCode(() -> itemExternalReferenceCode);

				InfoItemObjectProvider<Object> infoItemObjectProvider =
					infoItemServiceRegistry.getFirstInfoItemService(
						InfoItemObjectProvider.class, itemClassName,
						ClassPKInfoItemIdentifier.INFO_ITEM_SERVICE_FILTER);

				if (infoItemObjectProvider != null) {
					try {
						GroupedModel groupedModel =
							(GroupedModel)infoItemObjectProvider.getInfoItem(
								new ClassPKInfoItemIdentifier(
									GetterUtil.getLong(itemClassPK)));

						setScope(
							() -> ScopeUtil.getScope(
								groupedModel.getGroupId(), scopeGroupId));
					}
					catch (PortalException portalException) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Item external reference could not be set " +
									"since no item could be obtained",
								portalException);
						}
					}
				}

				setType(Type.ITEM_EXTERNAL_REFERENCE);
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

	private static FragmentMappedValueItemExternalReference
		_toLayoutFragmentMappedValueItemExternalReference(
			JSONObject layoutJSONObject, long scopeGroupId) {

		Layout layout;

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

		return new FragmentMappedValueItemExternalReference() {
			{
				setClassName(Layout.class::getName);
				setExternalReferenceCode(layout::getExternalReferenceCode);
				setScope(
					() -> ScopeUtil.getScope(
						layout.getGroupId(), scopeGroupId));
				setType(Type.ITEM_EXTERNAL_REFERENCE);
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentLinkValueUtil.class);

}