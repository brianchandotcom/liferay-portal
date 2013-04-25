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

package com.liferay.portlet.assetpublisher.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.assetpublisher.util.AssetPublisher;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.Date;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
 */
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, portletResource);

		if (cmd.equals(Constants.TRANSLATE)) {
			super.processAction(portletConfig, actionRequest, actionResponse);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			validateEmailAssetEntryAdded(actionRequest);
			validateEmailFrom(actionRequest);

			updateDisplaySettings(actionRequest);

			String selectionStyle = getParameter(
				actionRequest, "selectionStyle");

			if (selectionStyle.equals("dynamic")) {
				updateQueryLogic(actionRequest, preferences);
			}

			updateDefaultAssetPublisher(actionRequest);

			super.processAction(portletConfig, actionRequest, actionResponse);
		}
		else {
			try {
				if (cmd.equals("add-scope")) {
					addScope(actionRequest, preferences);
				}
				else if (cmd.equals("add-selection")) {
					AssetPublisherUtil.addSelection(
						actionRequest, preferences, portletResource);
				}
				else if (cmd.equals("move-selection-down")) {
					moveSelectionDown(actionRequest, preferences);
				}
				else if (cmd.equals("move-selection-up")) {
					moveSelectionUp(actionRequest, preferences);
				}
				else if (cmd.equals("remove-selection")) {
					removeSelection(actionRequest, preferences);
				}
				else if (cmd.equals("remove-scope")) {
					removeScope(actionRequest, preferences);
				}
				else if (cmd.equals("select-scope")) {
					setScopes(actionRequest, preferences);
				}
				else if (cmd.equals("selection-style")) {
					setSelectionStyle(actionRequest, preferences);
				}

				if (SessionErrors.isEmpty(actionRequest)) {
					preferences.store();

					LiferayPortletConfig liferayPortletConfig =
						(LiferayPortletConfig)portletConfig;

					SessionMessages.add(
						actionRequest,
						liferayPortletConfig.getPortletId() +
							SessionMessages.KEY_SUFFIX_REFRESH_PORTLET,
						portletResource);

					SessionMessages.add(
						actionRequest,
						liferayPortletConfig.getPortletId() +
							SessionMessages.KEY_SUFFIX_UPDATED_CONFIGURATION);
				}

				String redirect = PortalUtil.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					actionResponse.sendRedirect(redirect);
				}
			}
			catch (Exception e) {
				if (e instanceof AssetTagException) {
					SessionErrors.add(actionRequest, e.getClass(), e);
				}
				else {
					throw e;
				}
			}
		}
	}

	@Override
	public void serveResource(
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

		if (!cmd.equals("getFieldValue")) {
			return;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			resourceRequest);

		long structureId = ParamUtil.getLong(resourceRequest, "structureId");

		Fields fields = (Fields)serviceContext.getAttribute(
			Fields.class.getName() + structureId);

		if (fields == null) {
			String fieldsNamespace = ParamUtil.getString(
				resourceRequest, "fieldsNamespace");

			fields = DDMUtil.getFields(
				structureId, fieldsNamespace, serviceContext);
		}

		String fieldName = ParamUtil.getString(resourceRequest, "name");

		Field field = fields.get(fieldName);

		Serializable fieldValue = field.getValue(themeDisplay.getLocale());

		DDMStructure ddmStructure = field.getDDMStructure();

		String type = ddmStructure.getFieldType(fieldName);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (fieldValue instanceof Boolean) {
			jsonObject.put("value", (Boolean)fieldValue);
		}
		else if (fieldValue instanceof Date) {
			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"yyyyMMddHHmmss");

			jsonObject.put("value", dateFormat.format(fieldValue));
		}
		else {
			jsonObject.put("value", (String)fieldValue);
		}

		Serializable displayValue = DDMUtil.getDisplayFieldValue(
			fieldValue, type, themeDisplay.getLocale());

		jsonObject.put("displayValue", String.valueOf(displayValue));

		resourceResponse.setContentType(ContentTypes.APPLICATION_JSON);

		PortletResponseUtil.write(resourceResponse, jsonObject.toString());
	}

	protected void addScope(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String[] scopeIds = preferences.getValues(
			"scopeIds",
			new String[] {
				AssetPublisher.SCOPE_ID_GROUP_PREFIX + GroupConstants.DEFAULT
			});

		String scopeId = ParamUtil.getString(actionRequest, "scopeId");

		checkPermission(actionRequest, scopeId);

		if (!ArrayUtil.contains(scopeIds, scopeId)) {
			scopeIds = ArrayUtil.append(scopeIds, scopeId);
		}

		preferences.setValues("scopeIds", scopeIds);
	}

	protected void checkPermission(ActionRequest actionRequest, String scopeId)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (!AssetPublisherUtil.isScopeIdSelectable(
				themeDisplay.getPermissionChecker(), scopeId,
				themeDisplay.getCompanyGroupId(), layout)) {

			throw new PrincipalException();
		}
	}

	protected String[] getClassTypeIds(
			ActionRequest actionRequest, String[] classNameIds)
		throws Exception {

		String anyAssetTypeString = getParameter(actionRequest, "anyAssetType");

		boolean anyAssetType = GetterUtil.getBoolean(anyAssetTypeString);

		if (anyAssetType) {
			return null;
		}

		long defaultAssetTypeId = GetterUtil.getLong(anyAssetTypeString);

		if ((defaultAssetTypeId == 0) && (classNameIds.length == 1)) {
			defaultAssetTypeId = GetterUtil.getLong(classNameIds[0]);
		}

		if (defaultAssetTypeId <= 0 ) {
			return null;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String className = PortalUtil.getClassName(defaultAssetTypeId);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		long[] groupIds = {
			themeDisplay.getCompanyGroupId(), themeDisplay.getSiteGroupId()
		};

		Map<Long, String> classTypes = assetRendererFactory.getClassTypes(
			groupIds, themeDisplay.getLocale());

		if (classTypes.isEmpty()) {
			return null;
		}

		String assetClassName = AssetPublisherUtil.getClassName(
			assetRendererFactory);

		String anyAssetClassTypeString = getParameter(
			actionRequest, "anyClassType" + assetClassName);

		boolean anyAssetClassType = GetterUtil.getBoolean(
			anyAssetClassTypeString);

		if (anyAssetClassType) {
			return null;
		}

		long defaultAssetClassTypeId = GetterUtil.getLong(
			anyAssetClassTypeString);

		if (defaultAssetClassTypeId > 0) {
			return new String[] {String.valueOf(defaultAssetClassTypeId)};
		}
		else {
			return StringUtil.split(
				getParameter(actionRequest, "classTypeIds" + assetClassName));
		}
	}

	protected void moveSelectionDown(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		int assetEntryOrder = ParamUtil.getInteger(
			actionRequest, "assetEntryOrder");

		String[] manualEntries = preferences.getValues(
			"assetEntryXml", new String[0]);

		if ((assetEntryOrder >= (manualEntries.length - 1)) ||
			(assetEntryOrder < 0)) {

			return;
		}

		String temp = manualEntries[assetEntryOrder + 1];

		manualEntries[assetEntryOrder + 1] = manualEntries[assetEntryOrder];
		manualEntries[assetEntryOrder] = temp;

		preferences.setValues("assetEntryXml", manualEntries);
	}

	protected void moveSelectionUp(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		int assetEntryOrder = ParamUtil.getInteger(
			actionRequest, "assetEntryOrder");

		String[] manualEntries = preferences.getValues(
			"assetEntryXml", new String[0]);

		if ((assetEntryOrder >= manualEntries.length) ||
			(assetEntryOrder <= 0)) {

			return;
		}

		String temp = manualEntries[assetEntryOrder - 1];

		manualEntries[assetEntryOrder - 1] = manualEntries[assetEntryOrder];
		manualEntries[assetEntryOrder] = temp;

		preferences.setValues("assetEntryXml", manualEntries);
	}

	protected void removeScope(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String[] scopeIds = preferences.getValues(
			"scopeIds",
			new String[] {
				AssetPublisher.SCOPE_ID_GROUP_PREFIX + GroupConstants.DEFAULT
			});

		String scopeId = ParamUtil.getString(actionRequest, "scopeId");

		scopeIds = ArrayUtil.remove(scopeIds, scopeId);

		preferences.setValues("scopeIds", scopeIds);
	}

	protected void removeSelection(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		int assetEntryOrder = ParamUtil.getInteger(
			actionRequest, "assetEntryOrder");

		String[] manualEntries = preferences.getValues(
			"assetEntryXml", new String[0]);

		if (assetEntryOrder >= manualEntries.length) {
			return;
		}

		String[] newEntries = new String[manualEntries.length -1];

		int i = 0;
		int j = 0;

		for (; i < manualEntries.length; i++) {
			if (i != assetEntryOrder) {
				newEntries[j++] = manualEntries[i];
			}
		}

		preferences.setValues("assetEntryXml", newEntries);
	}

	protected void setScopes(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String[] scopeIds = StringUtil.split(
			getParameter(actionRequest, "scopeIds"));

		preferences.setValues("scopeIds", scopeIds);
	}

	protected void setSelectionStyle(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String selectionStyle = getParameter(actionRequest, "selectionStyle");
		String displayStyle = getParameter(actionRequest, "displayStyle");

		preferences.setValue("selectionStyle", selectionStyle);

		if (selectionStyle.equals("manual") ||
			selectionStyle.equals("view-count")) {

			preferences.setValue("enableRss", String.valueOf(false));
			preferences.setValue("showQueryLogic", Boolean.FALSE.toString());

			preferences.reset("rssDelta");
			preferences.reset("rssDisplayStyle");
			preferences.reset("rssFormat");
			preferences.reset("rssName");
		}

		if (!selectionStyle.equals("view-count") &&
			displayStyle.equals("view-count-details")) {

			preferences.setValue("displayStyle", "full-content");
		}
	}

	protected void updateDefaultAssetPublisher(ActionRequest actionRequest)
		throws Exception {

		boolean defaultAssetPublisher = ParamUtil.getBoolean(
			actionRequest, "defaultAssetPublisher");

		Layout layout = (Layout)actionRequest.getAttribute(WebKeys.LAYOUT);

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		if (defaultAssetPublisher) {
			typeSettingsProperties.setProperty(
				LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID,
				portletResource);
		}
		else {
			String defaultAssetPublisherPortletId =
				typeSettingsProperties.getProperty(
					LayoutTypePortletConstants.
						DEFAULT_ASSET_PUBLISHER_PORTLET_ID);

			if (Validator.isNotNull(defaultAssetPublisherPortletId) &&
				defaultAssetPublisherPortletId.equals(portletResource)) {

				typeSettingsProperties.setProperty(
					LayoutTypePortletConstants.
						DEFAULT_ASSET_PUBLISHER_PORTLET_ID,
					StringPool.BLANK);
			}
		}

		layout = LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	protected void updateDisplaySettings(ActionRequest actionRequest)
		throws Exception {

		String[] classNameIds = StringUtil.split(
			getParameter(actionRequest, "classNameIds"));
		String[] classTypeIds = getClassTypeIds(actionRequest, classNameIds);
		String[] extensions = actionRequest.getParameterValues("extensions");

		setPreference(actionRequest, "classNameIds", classNameIds);
		setPreference(actionRequest, "classTypeIds", classTypeIds);
		setPreference(actionRequest, "extensions", extensions);
	}

	protected void updateQueryLogic(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = themeDisplay.getUserId();
		long groupId = themeDisplay.getSiteGroupId();

		int[] queryRulesIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "queryLogicIndexes"), 0);

		int i = 0;

		for (int queryRulesIndex : queryRulesIndexes) {
			boolean contains = ParamUtil.getBoolean(
				actionRequest, "queryContains" + queryRulesIndex);
			boolean andOperator = ParamUtil.getBoolean(
				actionRequest, "queryAndOperator" + queryRulesIndex);
			String name = ParamUtil.getString(
				actionRequest, "queryName" + queryRulesIndex);

			String[] values = null;

			if (name.equals("assetTags")) {
				values = StringUtil.split(
					ParamUtil.getString(
						actionRequest, "queryTagNames" + queryRulesIndex));

				AssetTagLocalServiceUtil.checkTags(userId, groupId, values);
			}
			else {
				values = StringUtil.split(
					ParamUtil.getString(
						actionRequest, "queryCategoryIds" + queryRulesIndex));
			}

			setPreference(
				actionRequest, "queryContains" + i, String.valueOf(contains));
			setPreference(
				actionRequest, "queryAndOperator" + i,
				String.valueOf(andOperator));
			setPreference(actionRequest, "queryName" + i, name);
			setPreference(actionRequest, "queryValues" + i, values);

			i++;
		}

		// Clear previous preferences that are now blank

		String[] values = preferences.getValues(
			"queryValues" + i, new String[0]);

		while (values.length > 0) {
			setPreference(actionRequest, "queryContains" + i, StringPool.BLANK);
			setPreference(
				actionRequest, "queryAndOperator" + i, StringPool.BLANK);
			setPreference(actionRequest, "queryName" + i, StringPool.BLANK);
			setPreference(actionRequest, "queryValues" + i, new String[0]);

			i++;

			values = preferences.getValues("queryValues" + i, new String[0]);
		}
	}

	protected void validateEmailAssetEntryAdded(ActionRequest actionRequest)
		throws Exception {

		String emailAssetEntryAddedSubject = getLocalizedParameter(
			actionRequest, "emailAssetEntryAddedSubject");
		String emailAssetEntryAddedBody = getLocalizedParameter(
			actionRequest, "emailAssetEntryAddedBody");

		if (Validator.isNull(emailAssetEntryAddedSubject)) {
			SessionErrors.add(actionRequest, "emailAssetEntryAddedSubject");
		}
		else if (Validator.isNull(emailAssetEntryAddedBody)) {
			SessionErrors.add(actionRequest, "emailAssetEntryAddedBody");
		}
	}

	protected void validateEmailFrom(ActionRequest actionRequest)
		throws Exception {

		String emailFromName = getParameter(actionRequest, "emailFromName");
		String emailFromAddress = getParameter(
			actionRequest, "emailFromAddress");

		if (Validator.isNull(emailFromName)) {
			SessionErrors.add(actionRequest, "emailFromName");
		}
		else if (!Validator.isEmailAddress(emailFromAddress) &&
				 !Validator.isVariableTerm(emailFromAddress)) {

			SessionErrors.add(actionRequest, "emailFromAddress");
		}
	}

}