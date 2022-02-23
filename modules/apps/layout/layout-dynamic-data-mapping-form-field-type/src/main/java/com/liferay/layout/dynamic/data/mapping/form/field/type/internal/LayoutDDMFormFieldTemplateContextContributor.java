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

package com.liferay.layout.dynamic.data.mapping.form.field.type.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.dynamic.data.mapping.form.field.type.constants.LayoutDDMFormFieldTypeConstants;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = "ddm.form.field.type.name=" + LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT,
	service = {
		DDMFormFieldTemplateContextContributor.class,
		LayoutDDMFormFieldTemplateContextContributor.class
	}
)
public class LayoutDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		return HashMapBuilder.<String, Object>put(
			"itemSelectorURL",
			_getItemSelectorURL(
				ddmFormFieldRenderingContext,
				ddmFormFieldRenderingContext.getHttpServletRequest())
		).put(
			"portletNamespace",
			ddmFormFieldRenderingContext.getPortletNamespace()
		).put(
			"predefinedValue",
			() -> {
				LocalizedValue localizedValue =
					(LocalizedValue)ddmFormField.getProperty("predefinedValue");

				String predefinedValue = StringPool.BLANK;

				if (localizedValue != null) {
					predefinedValue = GetterUtil.getString(
						localizedValue.getString(
							ddmFormFieldRenderingContext.getLocale()));
				}

				return _getValue(
					GetterUtil.getLong(
						ddmFormFieldRenderingContext.getProperty("groupId")),
					ddmFormFieldRenderingContext.getLocale(), predefinedValue);
			}
		).put(
			"value",
			_getValue(
				GetterUtil.getLong(
					ddmFormFieldRenderingContext.getProperty("groupId")),
				ddmFormFieldRenderingContext.getLocale(),
				GetterUtil.getString(
					ddmFormFieldRenderingContext.getProperty("value")))
		).build();
	}

	private String _getItemSelectorURL(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		HttpServletRequest httpServletRequest) {

		if (_itemSelector == null) {
			return null;
		}

		LayoutItemSelectorCriterion layoutItemSelectorCriterion =
			new LayoutItemSelectorCriterion();

		layoutItemSelectorCriterion.setShowPrivatePages(true);
		layoutItemSelectorCriterion.setShowPublicPages(true);

		layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new UUIDItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			ddmFormFieldRenderingContext.getPortletNamespace() + "selectLayout",
			layoutItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	private String _getValue(
		long defaultGroupId, Locale defaultLocale, String value) {

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			long groupId = GetterUtil.getLong(
				defaultGroupId, serviceContext.getScopeGroupId());

			if (jsonObject.has("groupId")) {
				groupId = jsonObject.getLong("groupId");
			}

			boolean privateLayout = jsonObject.getBoolean("privateLayout");
			long layoutId = jsonObject.getLong("layoutId");

			Layout layout = _layoutLocalService.fetchLayout(
				groupId, privateLayout, layoutId);

			if (layout == null) {
				return StringPool.BLANK;
			}

			if (!jsonObject.has("groupId")) {
				jsonObject.put("groupId", layout.getGroupId());
			}

			if (!jsonObject.has("id")) {
				jsonObject.put("id", layout.getUuid());
			}

			if (!jsonObject.has("name")) {
				jsonObject.put("name", layout.getName(defaultLocale));
			}

			if (!jsonObject.has("value")) {
				jsonObject.put("value", layout.getFriendlyURL(defaultLocale));
			}

			return jsonObject.toJSONString();
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return StringPool.BLANK;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutDDMFormFieldTemplateContextContributor.class);

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private LayoutLocalService _layoutLocalService;

}