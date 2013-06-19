/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.expando.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.ColumnNameException;
import com.liferay.portlet.expando.ColumnTypeException;
import com.liferay.portlet.expando.DuplicateColumnNameException;
import com.liferay.portlet.expando.NoSuchColumnException;
import com.liferay.portlet.expando.ValueDataException;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.service.ExpandoColumnServiceUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Raymond Augé
 */
public class EditExpandoAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				addExpando(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteExpando(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateExpando(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchColumnException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.expando.error");
			}
			else if (e instanceof ColumnNameException ||
					 e instanceof ColumnTypeException ||
					 e instanceof DuplicateColumnNameException ||
					 e instanceof ValueDataException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getColumn(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchColumnException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.expando.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.expando.edit_expando"));
	}

	protected void addExpando(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String modelResource = ParamUtil.getString(
			actionRequest, "modelResource");
		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");

		String name = ParamUtil.getString(actionRequest, "name");
		String preset = ParamUtil.getString(actionRequest, "type");

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			themeDisplay.getCompanyId(), modelResource, resourcePrimKey);

		if (preset.startsWith("Preset")) {
			addPresetExpando(expandoBridge, preset, name);
		}
		else {
			int type = ParamUtil.getInteger(actionRequest, "type");

			expandoBridge.addAttribute(name, type);

			updateProperties(actionRequest, expandoBridge, name);
		}
	}

	protected int addPresetExpando(
			ExpandoBridge expandoBridge, String preset, String name)
		throws Exception {

		int type = 0;
		UnicodeProperties properties = expandoBridge.getAttributeProperties(
			name);

		if (preset.equals("PresetSelectionIntegerArray()")) {
			type = ExpandoColumnConstants.INTEGER_ARRAY;

			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE,
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST);
		}
		else if (preset.equals("PresetSelectionDoubleArray()")) {
			type = ExpandoColumnConstants.DOUBLE_ARRAY;

			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE,
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST);
		}
		else if (preset.equals("PresetSelectionStringArray()")) {
			type = ExpandoColumnConstants.STRING_ARRAY;

			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE,
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST);
		}
		else if (preset.equals("PresetTextBox()")) {
			type = ExpandoColumnConstants.STRING;

			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_HEIGHT, "105");
			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_WIDTH, "450");
		}
		else if (preset.equals("PresetTextBoxIndexed()")) {
			type = ExpandoColumnConstants.STRING;

			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_HEIGHT, "105");
			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_WIDTH, "450");
			properties.setProperty(
				ExpandoColumnConstants.INDEX_TYPE,
				String.valueOf(ExpandoColumnConstants.INDEX_TYPE_TEXT));
		}
		else if (preset.equals("PresetTextFieldSecret()")) {
			type = ExpandoColumnConstants.STRING;

			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_SECRET,
				Boolean.TRUE.toString());
		}
		else {
			type = ExpandoColumnConstants.STRING;

			properties.setProperty(
				ExpandoColumnConstants.INDEX_TYPE,
				String.valueOf(ExpandoColumnConstants.INDEX_TYPE_TEXT));
		}

		expandoBridge.addAttribute(name, type);

		expandoBridge.setAttributeProperties(name, properties);

		return type;
	}

	protected void deleteExpando(ActionRequest actionRequest) throws Exception {
		long columnId = ParamUtil.getLong(actionRequest, "columnId");

		ExpandoColumnServiceUtil.deleteColumn(columnId);
	}

	protected Serializable getValue(
			PortletRequest portletRequest, String name, int type)
		throws PortalException, SystemException {

		String delimiter = StringPool.COMMA;

		Serializable value = null;

		if (type == ExpandoColumnConstants.BOOLEAN) {
			value = ParamUtil.getBoolean(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
		}
		else if (type == ExpandoColumnConstants.DATE) {
			User user = PortalUtil.getUser(portletRequest);

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

			value = PortalUtil.getDate(
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

	protected void updateExpando(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String modelResource = ParamUtil.getString(
			actionRequest, "modelResource");
		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");

		String name = ParamUtil.getString(actionRequest, "name");
		int type = ParamUtil.getInteger(actionRequest, "type");

		Serializable defaultValue = getValue(
			actionRequest, "defaultValue", type);

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			themeDisplay.getCompanyId(), modelResource, resourcePrimKey);

		expandoBridge.setAttributeDefault(name, defaultValue);

		updateProperties(actionRequest, expandoBridge, name);
	}

	protected void updateProperties(
			ActionRequest actionRequest, ExpandoBridge expandoBridge,
			String name)
		throws Exception {

		Enumeration<String> enu = actionRequest.getParameterNames();

		UnicodeProperties properties = expandoBridge.getAttributeProperties(
			name);

		List<String> propertyNames = new ArrayList<String>();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();

			if (param.contains("PropertyName--")) {
				String propertyName = ParamUtil.getString(actionRequest, param);

				propertyNames.add(propertyName);
			}
		}

		for (String propertyName : propertyNames) {
			String value = ParamUtil.getString(
				actionRequest, "Property--" + propertyName + "--");

			properties.setProperty(propertyName, value);
		}

		expandoBridge.setAttributeProperties(name, properties);
	}

}