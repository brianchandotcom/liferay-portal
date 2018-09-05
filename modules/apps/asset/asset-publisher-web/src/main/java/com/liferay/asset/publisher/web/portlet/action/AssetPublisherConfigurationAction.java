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

package com.liferay.asset.publisher.web.portlet.action;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.asset.kernel.exception.DuplicateQueryRuleException;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.constants.AssetPublisherWebKeys;
import com.liferay.asset.publisher.web.configuration.AssetPublisherPortletInstanceConfiguration;
import com.liferay.asset.publisher.web.configuration.AssetPublisherWebConfiguration;
import com.liferay.asset.publisher.web.internal.util.AssetPublisherWebUtil;
import com.liferay.asset.publisher.web.util.AssetPublisherCustomizer;
import com.liferay.asset.publisher.web.util.AssetPublisherCustomizerRegistry;
import com.liferay.asset.publisher.web.util.AssetPublisherUtil;
import com.liferay.asset.publisher.web.util.AssetQueryRule;
import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.item.selector.ItemSelector;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.PortletPreferencesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
 */
@Component(
	configurationPid = "com.liferay.asset.publisher.web.configuration.AssetPublisherWebConfiguration",
	immediate = true,
	property = "javax.portlet.name=" + AssetPublisherPortletKeys.ASSET_PUBLISHER,
	service = ConfigurationAction.class
)
public class AssetPublisherConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest request) {
		String cmd = ParamUtil.getString(request, Constants.CMD);

		if (Objects.equals(cmd, "edit_query_rule")) {
			return "/edit_query_rule.jsp";
		}

		return "/configuration.jsp";
	}

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		String portletResource = ParamUtil.getString(
			request, "portletResource");

		String rootPortletId = PortletIdCodec.decodePortletName(
			portletResource);

		AssetPublisherCustomizer assetPublisherCustomizer =
			assetPublisherCustomizerRegistry.getAssetPublisherCustomizer(
				rootPortletId);

		request.setAttribute(
			AssetPublisherWebKeys.ASSET_PUBLISHER_CUSTOMIZER,
			assetPublisherCustomizer);

		request.setAttribute(
			AssetPublisherWebKeys.ASSET_PUBLISHER_WEB_CONFIGURATION,
			assetPublisherWebConfiguration);

		request.setAttribute(
			AssetPublisherWebKeys.ASSET_PUBLISHER_WEB_UTIL,
			assetPublisherWebUtil);

		request.setAttribute(AssetPublisherWebKeys.ITEM_SELECTOR, itemSelector);

		request.setAttribute(
			AssetPublisherWebKeys.
				ASSET_PUBLISHER_PORTLET_INSTANCE_CONFIGURATION,
			_getAssetPublisherPortletInstanceConfiguration(request));

		super.include(portletConfig, request, response);
	}

	@Override
	public void postProcess(
			long companyId, PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws ConfigurationException {

		AssetPublisherPortletInstanceConfiguration
			assetPublisherPortletInstanceConfiguration =
				ConfigurationProviderUtil.getSystemConfiguration(
					AssetPublisherPortletInstanceConfiguration.class);

		String languageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());
		LocalizedValuesMap emailAssetEntryAddedBodyMap =
			assetPublisherPortletInstanceConfiguration.
				emailAssetEntryAddedBody();

		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailAssetEntryAddedBody_" + languageId,
			emailAssetEntryAddedBodyMap.get(LocaleUtil.getSiteDefault()));

		LocalizedValuesMap emailAssetEntryAddedSubjectMap =
			assetPublisherPortletInstanceConfiguration.
				emailAssetEntryAddedSubject();

		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailAssetEntryAddedSubject_" + languageId,
			emailAssetEntryAddedSubjectMap.get(LocaleUtil.getSiteDefault()));
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences preferences = actionRequest.getPreferences();

		if (cmd.equals(Constants.TRANSLATE)) {
			super.processAction(portletConfig, actionRequest, actionResponse);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			try {
				HttpServletRequest request = portal.getHttpServletRequest(
					actionRequest);

				AssetPublisherPortletInstanceConfiguration
					assetPublisherPortletInstanceConfiguration =
						_getAssetPublisherPortletInstanceConfiguration(request);

				boolean emailAssetEntryAddedEnabled = GetterUtil.getBoolean(
					getParameter(actionRequest, "emailAssetEntryAddedEnabled"),
					assetPublisherPortletInstanceConfiguration.
						emailAssetEntryAddedEnabled());

				if (emailAssetEntryAddedEnabled) {
					validateEmail(actionRequest, "emailAssetEntryAdded");
					validateEmailFrom(actionRequest);
				}

				updateDisplaySettings(actionRequest);

				String selectionStyle = getParameter(
					actionRequest, "selectionStyle");

				if (selectionStyle.equals("dynamic")) {
					updateQueryLogic(actionRequest, preferences);
				}

				updateDefaultAssetPublisher(actionRequest);

				super.processAction(
					portletConfig, actionRequest, actionResponse);
			}
			catch (Exception e) {
				if (e instanceof AssetTagException ||
					e instanceof DuplicateQueryRuleException) {

					SessionErrors.add(actionRequest, e.getClass(), e);
				}
				else {
					throw e;
				}
			}
		}
		else {
			if (cmd.equals("add-scope")) {
				addScope(actionRequest, preferences);
			}
			else if (cmd.equals("add-selection")) {
				assetPublisherWebUtil.addSelection(actionRequest, preferences);
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

				SessionMessages.add(
					actionRequest,
					portal.getPortletId(actionRequest) +
						SessionMessages.KEY_SUFFIX_REFRESH_PORTLET,
					portletResource);

				SessionMessages.add(
					actionRequest,
					portal.getPortletId(actionRequest) +
						SessionMessages.KEY_SUFFIX_UPDATED_CONFIGURATION);
			}

			String redirect = portal.escapeRedirect(
				ParamUtil.getString(actionRequest, "redirect"));

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.asset.publisher.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		assetPublisherWebConfiguration = ConfigurableUtil.createConfigurable(
			AssetPublisherWebConfiguration.class, properties);
	}

	protected void addScope(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] scopeIds = preferences.getValues(
			"scopeIds",
			new String[] {
				AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX +
					GroupConstants.DEFAULT
			});

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		Group selectedGroup = groupLocalService.fetchGroup(groupId);

		String scopeId = AssetPublisherUtil.getScopeId(
			selectedGroup, themeDisplay.getScopeGroupId());

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

		if (!assetPublisherWebUtil.isScopeIdSelectable(
				themeDisplay.getPermissionChecker(), scopeId,
				themeDisplay.getCompanyGroupId(), layout, true)) {

			throw new PrincipalException();
		}
	}

	protected String getAssetClassName(
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

		if (defaultAssetTypeId <= 0) {
			return null;
		}

		String className = portal.getClassName(defaultAssetTypeId);

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		String assetClassName = assetPublisherWebUtil.getClassName(
			assetRendererFactory);

		return assetClassName;
	}

	protected String[] getClassTypeIds(
			ActionRequest actionRequest, String[] classNameIds)
		throws Exception {

		String assetClassName = getAssetClassName(actionRequest, classNameIds);

		if (assetClassName == null) {
			return null;
		}

		String anyAssetClassTypeString = getParameter(
			actionRequest, "anyClassType" + assetClassName);

		boolean anyAssetClassType = GetterUtil.getBoolean(
			anyAssetClassTypeString);

		if (anyAssetClassType) {
			return null;
		}

		long defaultAssetClassTypeId = GetterUtil.getLong(
			anyAssetClassTypeString, -1);

		if (defaultAssetClassTypeId > -1) {
			return new String[] {String.valueOf(defaultAssetClassTypeId)};
		}

		return StringUtil.split(
			getParameter(actionRequest, "classTypeIds" + assetClassName));
	}

	protected AssetQueryRule getQueryRule(
		ActionRequest actionRequest, int index) {

		boolean contains = ParamUtil.getBoolean(
			actionRequest, "queryContains" + index);
		boolean andOperator = ParamUtil.getBoolean(
			actionRequest, "queryAndOperator" + index);

		String name = ParamUtil.getString(actionRequest, "queryName" + index);

		String[] values = null;

		if (name.equals("assetTags")) {
			values = StringUtil.split(
				ParamUtil.getString(actionRequest, "queryTagNames" + index));
		}
		else {
			values = StringUtil.split(
				ParamUtil.getString(actionRequest, "queryCategoryIds" + index));
		}

		return new AssetQueryRule(contains, andOperator, name, values);
	}

	protected boolean getSubtypesFieldsFilterEnabled(
			ActionRequest actionRequest, String[] classNameIds)
		throws Exception {

		String assetClassName = getAssetClassName(actionRequest, classNameIds);

		if (assetClassName == null) {
			return false;
		}

		return GetterUtil.getBoolean(
			getParameter(
				actionRequest, "subtypeFieldsFilterEnabled" + assetClassName));
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
				AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX +
					GroupConstants.DEFAULT
			});

		String scopeId = ParamUtil.getString(actionRequest, "scopeId");

		scopeIds = ArrayUtil.remove(scopeIds, scopeId);

		if (scopeId.startsWith(
				AssetPublisherUtil.SCOPE_ID_PARENT_GROUP_PREFIX)) {

			scopeId = scopeId.substring("Parent".length());

			scopeIds = ArrayUtil.remove(scopeIds, scopeId);
		}

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

		String[] newEntries = new String[manualEntries.length - 1];

		int i = 0;
		int j = 0;

		for (; i < manualEntries.length; i++) {
			if (i != assetEntryOrder) {
				newEntries[j++] = manualEntries[i];
			}
		}

		preferences.setValues("assetEntryXml", newEntries);
	}

	@Reference(unbind = "-")
	protected void setAssetTagLocalService(
		AssetTagLocalService assetTagLocalService) {

		this.assetTagLocalService = assetTagLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setItemSelector(ItemSelector itemSelector) {
		this.itemSelector = itemSelector;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		this.layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutRevisionLocalService(
		LayoutRevisionLocalService layoutRevisionLocalService) {

		this.layoutRevisionLocalService = layoutRevisionLocalService;
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

			preferences.setValue("enableRss", Boolean.FALSE.toString());
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

		layout = layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		if (layout.isSupportsEmbeddedPortlets() &&
			layout.isPortletEmbedded(portletResource, layout.getGroupId())) {

			return;
		}

		if (LayoutStagingUtil.isBranchingLayout(layout)) {
			HttpServletRequest request = portal.getHttpServletRequest(
				actionRequest);

			LayoutSetBranch layoutSetBranch =
				LayoutStagingUtil.getLayoutSetBranch(layout.getLayoutSet());

			long layoutSetBranchId = layoutSetBranch.getLayoutSetBranchId();

			long layoutRevisionId = staging.getRecentLayoutRevisionId(
				request, layoutSetBranchId, layout.getPlid());

			LayoutRevision layoutRevision =
				layoutRevisionLocalService.getLayoutRevision(layoutRevisionId);

			PortletPreferencesImpl portletPreferences =
				(PortletPreferencesImpl)actionRequest.getPreferences();

			if (layoutRevision != null) {
				portletPreferences.setPlid(
					layoutRevision.getLayoutRevisionId());
			}
		}
	}

	protected void updateDisplaySettings(ActionRequest actionRequest)
		throws Exception {

		String[] classNameIds = StringUtil.split(
			getParameter(actionRequest, "classNameIds"));

		String[] classTypeIds = getClassTypeIds(actionRequest, classNameIds);

		String[] extensions = actionRequest.getParameterValues("extensions");

		if (ArrayUtil.isNotEmpty(extensions) && (extensions.length == 1) &&
			extensions[0].equals(Boolean.FALSE.toString())) {

			extensions = new String[0];
		}

		boolean subtypeFieldsFilterEnabled = getSubtypesFieldsFilterEnabled(
			actionRequest, classNameIds);

		setPreference(actionRequest, "classNameIds", classNameIds);
		setPreference(actionRequest, "classTypeIds", classTypeIds);
		setPreference(actionRequest, "extensions", extensions);
		setPreference(
			actionRequest, "subtypeFieldsFilterEnabled",
			String.valueOf(subtypeFieldsFilterEnabled));
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

		List<AssetQueryRule> queryRules = new ArrayList<>();

		for (int queryRulesIndex : queryRulesIndexes) {
			AssetQueryRule queryRule = getQueryRule(
				actionRequest, queryRulesIndex);

			validateQueryRule(userId, groupId, queryRules, queryRule);

			queryRules.add(queryRule);

			setPreference(
				actionRequest, "queryContains" + i,
				String.valueOf(queryRule.isContains()));
			setPreference(
				actionRequest, "queryAndOperator" + i,
				String.valueOf(queryRule.isAndOperator()));
			setPreference(actionRequest, "queryName" + i, queryRule.getName());
			setPreference(
				actionRequest, "queryValues" + i, queryRule.getValues());

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

	protected void validateQueryRule(
			long userId, long groupId, List<AssetQueryRule> queryRules,
			AssetQueryRule queryRule)
		throws Exception {

		String name = queryRule.getName();

		if (name.equals("assetTags")) {
			assetTagLocalService.checkTags(
				userId, groupId, queryRule.getValues());
		}

		if (queryRules.contains(queryRule)) {
			throw new DuplicateQueryRuleException(
				queryRule.isContains(), queryRule.isAndOperator(),
				queryRule.getName());
		}
	}

	@Reference
	protected AssetPublisherCustomizerRegistry assetPublisherCustomizerRegistry;

	protected AssetPublisherWebConfiguration assetPublisherWebConfiguration;

	@Reference
	protected AssetPublisherWebUtil assetPublisherWebUtil;

	protected AssetTagLocalService assetTagLocalService;
	protected GroupLocalService groupLocalService;
	protected ItemSelector itemSelector;
	protected LayoutLocalService layoutLocalService;
	protected LayoutRevisionLocalService layoutRevisionLocalService;

	@Reference
	protected Portal portal;

	@Reference
	protected Staging staging;

	private AssetPublisherPortletInstanceConfiguration
			_getAssetPublisherPortletInstanceConfiguration(
				HttpServletRequest request)
		throws ConfigurationException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getPortletInstanceConfiguration(
			AssetPublisherPortletInstanceConfiguration.class);
	}

}