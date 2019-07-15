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

package com.liferay.expando.web.internal.portlet;

import com.liferay.expando.constants.ExpandoPortletKeys;
import com.liferay.expando.kernel.exception.ColumnNameException;
import com.liferay.expando.kernel.exception.ColumnTypeException;
import com.liferay.expando.kernel.exception.DuplicateColumnNameException;
import com.liferay.expando.kernel.exception.NoSuchColumnException;
import com.liferay.expando.kernel.exception.ValueDataException;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.service.ExpandoColumnService;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.Serializable;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-expando",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/expando.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Custom Fields",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ExpandoPortletKeys.EXPANDO,
		"javax.portlet.resource-bundle=content.Language"
	},
	service = Portlet.class
)
public class ExpandoPortlet extends MVCPortlet {

	public void addExpando(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String modelResource = ParamUtil.getString(
			actionRequest, "modelResource");
		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");

		int type = ParamUtil.getInteger(actionRequest, "type");

		String dataType = ParamUtil.getString(actionRequest, "dataType");
		String precisionType = ParamUtil.getString(
			actionRequest, "precisionType");

		if (Validator.isNotNull(dataType) &&
			Validator.isNotNull(precisionType)) {

			type = _getNumberType(dataType, precisionType, type);
		}

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			themeDisplay.getCompanyId(), modelResource, resourcePrimKey);

		String name = ParamUtil.getString(actionRequest, "name");

		if (!Field.validateFieldName(name)) {
			throw new ColumnNameException.MustValidate();
		}

		expandoBridge.addAttribute(name, type);

		Serializable defaultValue = getDefaultValue(actionRequest, type);

		expandoBridge.setAttributeDefault(name, defaultValue);

		updateProperties(actionRequest, expandoBridge, name);
	}

	public void deleteExpando(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long columnId = ParamUtil.getLong(actionRequest, "columnId");

		_expandoColumnService.deleteColumn(columnId);
	}

	public void deleteExpandos(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] columnIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "columnIds"), 0L);

		for (long columnId : columnIds) {
			_expandoColumnService.deleteColumn(columnId);
		}
	}

	public void updateExpando(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String modelResource = ParamUtil.getString(
			actionRequest, "modelResource");
		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");

		String name = ParamUtil.getString(actionRequest, "name");

		int type = ParamUtil.getInteger(actionRequest, "type");

		Serializable defaultValue = getDefaultValue(actionRequest, type);

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			themeDisplay.getCompanyId(), modelResource, resourcePrimKey);

		expandoBridge.setAttributeDefault(name, defaultValue);

		updateProperties(actionRequest, expandoBridge, name);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, ColumnNameException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, ColumnTypeException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, DuplicateColumnNameException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, ValueDataException.class.getName())) {

			include("/edit/expando.jsp", renderRequest, renderResponse);
		}
		else if (SessionErrors.contains(
					renderRequest, NoSuchColumnException.class.getName()) ||
				 SessionErrors.contains(
					 renderRequest, PrincipalException.getNestedClasses())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected Serializable getDefaultValue(
			ActionRequest actionRequest, int type)
		throws Exception {

		if (type == ExpandoColumnConstants.GEOLOCATION) {
			return JSONFactoryUtil.createJSONObject(
				ParamUtil.getString(actionRequest, "defaultValue"));
		}

		if (type == ExpandoColumnConstants.STRING_LOCALIZED) {
			return (Serializable)LocalizationUtil.getLocalizationMap(
				actionRequest, "defaultValueLocalized");
		}

		return getValue(actionRequest, "defaultValue", type);
	}

	protected Serializable getValue(
			PortletRequest portletRequest, String name, int type)
		throws PortalException {

		String delimiter = StringPool.COMMA;

		Serializable value = null;

		if (type == ExpandoColumnConstants.BOOLEAN) {
			value = ParamUtil.getBoolean(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
		}
		else if (type == ExpandoColumnConstants.DATE) {
			User user = _portal.getUser(portletRequest);

			int valueDateMonth = ParamUtil.getInteger(
				portletRequest, name + "Month");
			int valueDateDay = ParamUtil.getInteger(
				portletRequest, name + "Day");
			int valueDateYear = ParamUtil.getInteger(
				portletRequest, name + "Year");
			int valueDateHour = ParamUtil.getInteger(
				portletRequest, name + "Hour");
			int valueDateMinute = ParamUtil.getInteger(
				portletRequest, name + "Minute");
			int valueDateAmPm = ParamUtil.getInteger(
				portletRequest, name + "AmPm");

			if (valueDateAmPm == Calendar.PM) {
				valueDateHour += 12;
			}

			value = _portal.getDate(
				valueDateMonth, valueDateDay, valueDateYear, valueDateHour,
				valueDateMinute, user.getTimeZone(), ValueDataException.class);
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
		}
		else if (type == ExpandoColumnConstants.DOUBLE) {
			value = ParamUtil.getDouble(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			String paramValue = ParamUtil.getString(portletRequest, name);

			if (paramValue.contains(StringPool.NEW_LINE)) {
				delimiter = StringPool.NEW_LINE;
			}

			String[] values = StringUtil.split(paramValue, delimiter);

			value = GetterUtil.getDoubleValues(values);
		}
		else if (type == ExpandoColumnConstants.FLOAT) {
			value = ParamUtil.getFloat(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			String paramValue = ParamUtil.getString(portletRequest, name);

			if (paramValue.contains(StringPool.NEW_LINE)) {
				delimiter = StringPool.NEW_LINE;
			}

			String[] values = StringUtil.split(paramValue, delimiter);

			value = GetterUtil.getFloatValues(values);
		}
		else if (type == ExpandoColumnConstants.INTEGER) {
			value = ParamUtil.getInteger(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			String paramValue = ParamUtil.getString(portletRequest, name);

			if (paramValue.contains(StringPool.NEW_LINE)) {
				delimiter = StringPool.NEW_LINE;
			}

			String[] values = StringUtil.split(paramValue, delimiter);

			value = GetterUtil.getIntegerValues(values);
		}
		else if (type == ExpandoColumnConstants.LONG) {
			value = ParamUtil.getLong(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			String paramValue = ParamUtil.getString(portletRequest, name);

			if (paramValue.contains(StringPool.NEW_LINE)) {
				delimiter = StringPool.NEW_LINE;
			}

			String[] values = StringUtil.split(paramValue, delimiter);

			value = GetterUtil.getLongValues(values);
		}
		else if (type == ExpandoColumnConstants.NUMBER) {
			value = ParamUtil.getNumber(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.NUMBER_ARRAY) {
			String paramValue = ParamUtil.getString(portletRequest, name);

			if (paramValue.contains(StringPool.NEW_LINE)) {
				delimiter = StringPool.NEW_LINE;
			}

			String[] values = StringUtil.split(paramValue, delimiter);

			value = GetterUtil.getNumberValues(values);
		}
		else if (type == ExpandoColumnConstants.SHORT) {
			value = ParamUtil.getShort(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			String paramValue = ParamUtil.getString(portletRequest, name);

			if (paramValue.contains(StringPool.NEW_LINE)) {
				delimiter = StringPool.NEW_LINE;
			}

			String[] values = StringUtil.split(paramValue, delimiter);

			value = GetterUtil.getShortValues(values);
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			String paramValue = ParamUtil.getString(portletRequest, name);

			if (paramValue.contains(StringPool.NEW_LINE)) {
				delimiter = StringPool.NEW_LINE;
			}

			value = StringUtil.split(paramValue, delimiter);
		}
		else {
			value = ParamUtil.getString(portletRequest, name);
		}

		return value;
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof ColumnNameException ||
			cause instanceof ColumnTypeException ||
			cause instanceof DuplicateColumnNameException ||
			cause instanceof NoSuchColumnException ||
			cause instanceof PrincipalException ||
			cause instanceof ValueDataException) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setExpandoColumnService(
		ExpandoColumnService expandoColumnService) {

		_expandoColumnService = expandoColumnService;
	}

	protected void updateProperties(
			ActionRequest actionRequest, ExpandoBridge expandoBridge,
			String name)
		throws Exception {

		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			actionRequest, "Property--");

		boolean searchable = ParamUtil.getBoolean(actionRequest, "searchable");

		if (!searchable) {
			properties.setProperty(
				ExpandoColumnConstants.INDEX_TYPE,
				String.valueOf(ExpandoColumnConstants.INDEX_TYPE_NONE));
		}

		expandoBridge.setAttributeProperties(name, properties);
	}

	private int _getNumberType(
		String dataType, String precisionType, int type) {

		if (dataType.equals(ExpandoColumnConstants.DATA_TYPE_DECIMAL) &&
			precisionType.equals(ExpandoColumnConstants.PRECISION_64_BIT)) {

			if (type == ExpandoColumnConstants.STRING_ARRAY) {
				return ExpandoColumnConstants.DOUBLE_ARRAY;
			}

			return ExpandoColumnConstants.DOUBLE;
		}

		if (dataType.equals(ExpandoColumnConstants.DATA_TYPE_DECIMAL) &&
			precisionType.equals(ExpandoColumnConstants.PRECISION_32_BIT)) {

			if (type == ExpandoColumnConstants.STRING_ARRAY) {
				return ExpandoColumnConstants.FLOAT_ARRAY;
			}

			return ExpandoColumnConstants.FLOAT;
		}

		if (dataType.equals(ExpandoColumnConstants.DATA_TYPE_INTEGER) &&
			precisionType.equals(ExpandoColumnConstants.PRECISION_64_BIT)) {

			if (type == ExpandoColumnConstants.STRING_ARRAY) {
				return ExpandoColumnConstants.LONG_ARRAY;
			}

			return ExpandoColumnConstants.LONG;
		}

		if (dataType.equals(ExpandoColumnConstants.DATA_TYPE_INTEGER) &&
			precisionType.equals(ExpandoColumnConstants.PRECISION_32_BIT)) {

			if (type == ExpandoColumnConstants.STRING_ARRAY) {
				return ExpandoColumnConstants.INTEGER_ARRAY;
			}

			return ExpandoColumnConstants.INTEGER;
		}

		if (dataType.equals(ExpandoColumnConstants.DATA_TYPE_INTEGER) &&
			precisionType.equals(ExpandoColumnConstants.PRECISION_16_BIT)) {

			if (type == ExpandoColumnConstants.STRING_ARRAY) {
				return ExpandoColumnConstants.SHORT_ARRAY;
			}

			return ExpandoColumnConstants.SHORT;
		}

		return 0;
	}

	private ExpandoColumnService _expandoColumnService;

	@Reference
	private Portal _portal;

}