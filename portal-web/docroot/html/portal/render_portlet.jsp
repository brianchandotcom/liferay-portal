<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portal/init.jsp" %>

<%
String cmd = ParamUtil.getString(request, Constants.CMD);

Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);

String portletId = portlet.getPortletId();
String rootPortletId = portlet.getRootPortletId();
String instanceId = portlet.getInstanceId();

String portletPrimaryKey = PortletPermissionUtil.getPrimaryKey(plid, portletId);

String queryString = (String)request.getAttribute(WebKeys.RENDER_PORTLET_QUERY_STRING);
String columnId = (String)request.getAttribute(WebKeys.RENDER_PORTLET_COLUMN_ID);
Integer columnPos = (Integer)request.getAttribute(WebKeys.RENDER_PORTLET_COLUMN_POS);
Integer columnCount = (Integer)request.getAttribute(WebKeys.RENDER_PORTLET_COLUMN_COUNT);
Boolean renderPortletResource = (Boolean)request.getAttribute(WebKeys.RENDER_PORTLET_RESOURCE);

boolean allowAddPortletDefaultResource = PortalUtil.isAllowAddPortletDefaultResource(request, portlet);
boolean runtimePortlet = (renderPortletResource != null) && renderPortletResource.booleanValue();

boolean access = false;

if (allowAddPortletDefaultResource && !portlet.isUndeployedPortlet()) {
	PortalUtil.addPortletDefaultResource(request, portlet);

	access = PortletPermissionUtil.contains(permissionChecker, layout, portlet, ActionKeys.VIEW);
}

if (portlet.isUndeployedPortlet()) {
	access = true;
}

boolean stateMax = layoutTypePortlet.hasStateMaxPortletId(portletId);
boolean stateMin = layoutTypePortlet.hasStateMinPortletId(portletId);

boolean modeAbout = layoutTypePortlet.hasModeAboutPortletId(portletId);
boolean modeConfig = layoutTypePortlet.hasModeConfigPortletId(portletId);
boolean modeEdit = layoutTypePortlet.hasModeEditPortletId(portletId);
boolean modeEditDefaults = layoutTypePortlet.hasModeEditDefaultsPortletId(portletId);
boolean modeEditGuest = layoutTypePortlet.hasModeEditGuestPortletId(portletId);
boolean modeHelp = layoutTypePortlet.hasModeHelpPortletId(portletId);
boolean modePreview = layoutTypePortlet.hasModePreviewPortletId(portletId);
boolean modePrint = layoutTypePortlet.hasModePrintPortletId(portletId);

InvokerPortlet invokerPortlet = null;

try {
	if (portlet.isReady()) {
		invokerPortlet = PortletInstanceFactoryUtil.create(portlet, application);
	}
}
/*catch (UnavailableException ue) {
	ue.printStackTrace();
}*/
catch (PortletException pe) {
	pe.printStackTrace();
}
catch (RuntimeException re) {
	re.printStackTrace();
}

PortletPreferences portletSetup = PortletPreferencesFactoryUtil.getStrictLayoutPortletSetup(layout, portletId);

PortletPreferencesIds portletPreferencesIds = PortletPreferencesFactoryUtil.getPortletPreferencesIds(request, portletId);

PortletPreferences portletPreferences = null;

if (allowAddPortletDefaultResource) {
	portletPreferences = PortletPreferencesLocalServiceUtil.getPreferences(portletPreferencesIds);
}
else {
	portletPreferences = PortletPreferencesLocalServiceUtil.getStrictPreferences(portletPreferencesIds);
}

long portletItemId = ParamUtil.getLong(request, "p_p_i_id");

if (portletItemId > 0) {
	PortletPreferencesServiceUtil.restoreArchivedPreferences(themeDisplay.getParentGroupId(), layout, portlet.getRootPortletId(), portletItemId, portletPreferences);
}

PortletConfig portletConfig = PortletConfigFactoryUtil.create(portlet, application);
PortletContext portletCtx = portletConfig.getPortletContext();

WindowState windowState = WindowState.NORMAL;

if (themeDisplay.isStateExclusive()) {
	windowState = LiferayWindowState.EXCLUSIVE;
}
else if (themeDisplay.isStatePopUp()) {
	windowState = LiferayWindowState.POP_UP;
}
else if (stateMax) {
	windowState = WindowState.MAXIMIZED;
}
else if (stateMin) {
	windowState = WindowState.MINIMIZED;
}

PortletMode portletMode = PortletMode.VIEW;

if (modeAbout) {
	portletMode = LiferayPortletMode.ABOUT;
}
else if (modeConfig) {
	portletMode = LiferayPortletMode.CONFIG;
}
else if (modeEdit) {
	portletMode = PortletMode.EDIT;
}
else if (modeEditDefaults) {
	portletMode = LiferayPortletMode.EDIT_DEFAULTS;
}
else if (modeEditGuest) {
	portletMode = LiferayPortletMode.EDIT_GUEST;
}
else if (modeHelp) {
	portletMode = PortletMode.HELP;
}
else if (modePreview) {
	portletMode = LiferayPortletMode.PREVIEW;
}
else if (modePrint) {
	portletMode = LiferayPortletMode.PRINT;
}

HttpServletRequest originalRequest = PortalUtil.getOriginalServletRequest(request);

RenderRequestImpl renderRequestImpl = RenderRequestFactory.create(originalRequest, portlet, invokerPortlet, portletCtx, windowState, portletMode, portletPreferences, plid);

if (Validator.isNotNull(queryString)) {
	DynamicServletRequest dynamicRequest = (DynamicServletRequest)renderRequestImpl.getHttpServletRequest();

	String[] params = StringUtil.split(queryString, CharPool.AMPERSAND);

	for (int i = 0; i < params.length; i++) {
		String[] kvp = StringUtil.split(params[i], CharPool.EQUAL);

		if (kvp.length > 1) {
			dynamicRequest.setParameter(kvp[0], kvp[1]);
		}
		else {
			dynamicRequest.setParameter(kvp[0], StringPool.BLANK);
		}
	}
}

StringServletResponse stringResponse = new StringServletResponse(response);

RenderResponseImpl renderResponseImpl = RenderResponseFactory.create(renderRequestImpl, stringResponse, portletId, company.getCompanyId(), plid);

if (stateMin) {
	renderResponseImpl.setUseDefaultTemplate(true);
}

renderRequestImpl.defineObjects(portletConfig, renderResponseImpl);

String responseContentType = renderRequestImpl.getResponseContentType();

String currentURL = PortalUtil.getCurrentURL(request);

Portlet portletResourcePortlet = null;

if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	String portletResource = ParamUtil.getString(request, "portletResource");

	if (Validator.isNull(portletResource)) {
		portletResource = ParamUtil.getString(renderRequestImpl, "portletResource");
	}

	if (Validator.isNotNull(portletResource)) {
		portletResourcePortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);
	}
}

boolean showCloseIcon = true;
boolean showConfigurationIcon = false;
boolean showEditIcon = false;
boolean showEditDefaultsIcon = false;
boolean showEditGuestIcon = false;
boolean showExportImportIcon = false;
boolean showHelpIcon = false;
boolean showMaxIcon = portlet.hasWindowState(responseContentType, WindowState.MAXIMIZED);
boolean showMinIcon = portlet.hasWindowState(responseContentType, WindowState.MINIMIZED);
boolean showMoveIcon = !stateMax && !themeDisplay.isStateExclusive();
boolean showPortletCssIcon = false;
boolean showPortletIcon = (portletResourcePortlet != null) ? Validator.isNotNull(portletResourcePortlet.getIcon()) : Validator.isNotNull(portlet.getIcon());
boolean showPrintIcon = portlet.hasPortletMode(responseContentType, LiferayPortletMode.PRINT);
boolean showRefreshIcon = portlet.isAjaxable() && (portlet.getRenderWeight() == 0);

Boolean portletParallelRender = (Boolean)request.getAttribute(WebKeys.PORTLET_PARALLEL_RENDER);

if ((portletParallelRender != null) && (portletParallelRender.booleanValue() == false)) {
	showRefreshIcon = false;
}

Group group = layout.getGroup();

if (!portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	if ((!group.hasStagingGroup() || group.isStagingGroup()) &&
		(PortletPermissionUtil.contains(permissionChecker, layout, portlet, ActionKeys.CONFIGURATION))) {

		showConfigurationIcon = true;

		boolean supportsLAR = Validator.isNotNull(portlet.getPortletDataHandlerClass());
		boolean supportsSetup = Validator.isNotNull(portlet.getConfigurationActionClass());

		if (supportsLAR || (supportsSetup && !group.isControlPanel())) {
			showExportImportIcon = true;
		}

		if (PropsValues.PORTLET_CSS_ENABLED) {
			showPortletCssIcon = true;
		}
	}
}

if (group.isLayoutPrototype()) {
	showExportImportIcon = false;
}

if (portlet.hasPortletMode(responseContentType, PortletMode.EDIT)) {
	if (PortletPermissionUtil.contains(permissionChecker, layout, portletId, ActionKeys.PREFERENCES)) {
		showEditIcon = true;
	}
}

if (portlet.hasPortletMode(responseContentType, LiferayPortletMode.EDIT_DEFAULTS)) {
	if (showEditIcon && !layout.isPrivateLayout() && themeDisplay.isShowAddContentIcon()) {
		showEditDefaultsIcon = true;
	}
}

if (portlet.hasPortletMode(responseContentType, LiferayPortletMode.EDIT_GUEST)) {
	if (showEditIcon && !layout.isPrivateLayout() && themeDisplay.isShowAddContentIcon()) {
		showEditGuestIcon = true;
	}
}

if (portlet.hasPortletMode(responseContentType, PortletMode.HELP)) {
	if (PortletPermissionUtil.contains(permissionChecker, layout, portletId, ActionKeys.HELP)) {
		showHelpIcon = true;
	}
}

boolean supportsMimeType = portlet.hasPortletMode(responseContentType, portletMode);

if (responseContentType.equals(ContentTypes.XHTML_MP) && portlet.hasMultipleMimeTypes()) {
	supportsMimeType = GetterUtil.getBoolean(portletSetup.getValue("portletSetupSupportedClientsMobileDevices_" + portletMode, String.valueOf(supportsMimeType)));
}

// Only authenticated with the correct permissions can update a layout. If
// staging is activated, only staging layouts can be updated.

if ((!themeDisplay.isSignedIn()) ||
	(group.hasStagingGroup() && !group.isStagingGroup()) ||
	(!LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE))) {

	showMaxIcon = PropsValues.LAYOUT_GUEST_SHOW_MAX_ICON;
	showMinIcon = PropsValues.LAYOUT_GUEST_SHOW_MIN_ICON;

	if (!(layoutTypePortlet.isCustomizable() && !layoutTypePortlet.isColumnDisabled(columnId) && LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.CUSTOMIZE))) {
		showCloseIcon = false;
		showMoveIcon = false;
	}
}

// Portlets cannot be moved if the column is not customizable

if (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isColumnDisabled(columnId)) {
	showCloseIcon = false;
	showMoveIcon = false;
}

// Portlets cannot be moved unless they belong to the layout

if (!layoutTypePortlet.hasPortletId(portletId)) {
	showCloseIcon = false;
	showMoveIcon = false;
}

// Portlets in the Control Panel cannot be moved

if (layout.isTypeControlPanel()) {
	showCloseIcon = false;
	showMoveIcon = false;
}

// Static portlets cannot be moved

if (portlet.isStatic()) {
	showCloseIcon = false;
	showMoveIcon = false;
}

// Deny access to edit mode if you do not have permission

if (access && !PropsValues.TCK_URL && portletMode.equals(PortletMode.EDIT) && !PortletPermissionUtil.contains(permissionChecker, layout, portletId, ActionKeys.PREFERENCES)) {
	access = false;
}

// Deny access

if (!access) {
	showCloseIcon = false;
	showConfigurationIcon = false;
	showEditIcon = false;
	showEditDefaultsIcon = false;
	showEditGuestIcon = false;
	showExportImportIcon = false;
	showHelpIcon = false;
	showMaxIcon = false;
	showMinIcon = false;
	showMoveIcon = false;
	showPortletCssIcon = false;
	showPrintIcon = false;
}

long previousScopeGroupId = themeDisplay.getScopeGroupId();

if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION) && (portletResourcePortlet != null)) {
	themeDisplay.setScopeGroupId(PortalUtil.getScopeGroupId(request, portletResourcePortlet.getPortletId()));
}
else {
	themeDisplay.setScopeGroupId(PortalUtil.getScopeGroupId(request, portletId));
}

long previousParentGroupId = themeDisplay.getParentGroupId();

Group parentGroup = GroupLocalServiceUtil.getGroup(themeDisplay.getParentGroupId());

if (parentGroup.isStagingGroup()) {
	parentGroup = parentGroup.getLiveGroup();
}

if (parentGroup.isStaged() && !parentGroup.isStagedRemotely() && !parentGroup.isStagedPortlet(portletId)) {
	themeDisplay.setParentGroupId(parentGroup.getGroupId());
}

portletDisplay.recycle();

portletDisplay.setId(portletId);
portletDisplay.setRootPortletId(rootPortletId);
portletDisplay.setInstanceId(instanceId);
portletDisplay.setResourcePK(portletPrimaryKey);
portletDisplay.setPortletName(portletConfig.getPortletName());
portletDisplay.setNamespace(PortalUtil.getPortletNamespace(portletId));

portletDisplay.setAccess(access);
portletDisplay.setActive(portlet.isActive());

portletDisplay.setColumnId(columnId);
portletDisplay.setColumnPos(columnPos.intValue());
portletDisplay.setColumnCount(columnCount.intValue());

portletDisplay.setStateExclusive(themeDisplay.isStateExclusive());
portletDisplay.setStateMax(stateMax);
portletDisplay.setStateMin(stateMin);
portletDisplay.setStateNormal(windowState.equals(WindowState.NORMAL));
portletDisplay.setStatePopUp(themeDisplay.isStatePopUp());

portletDisplay.setModeAbout(modeAbout);
portletDisplay.setModeConfig(modeConfig);
portletDisplay.setModeEdit(modeEdit);
portletDisplay.setModeEditDefaults(modeEditDefaults);
portletDisplay.setModeEditGuest(modeEditGuest);
portletDisplay.setModeHelp(modeHelp);
portletDisplay.setModePreview(modePreview);
portletDisplay.setModePrint(modePrint);

portletDisplay.setShowCloseIcon(showCloseIcon);
portletDisplay.setShowConfigurationIcon(showConfigurationIcon);
portletDisplay.setShowEditIcon(showEditIcon);
portletDisplay.setShowEditDefaultsIcon(showEditDefaultsIcon);
portletDisplay.setShowEditGuestIcon(showEditGuestIcon);
portletDisplay.setShowExportImportIcon(showExportImportIcon);
portletDisplay.setShowHelpIcon(showHelpIcon);
portletDisplay.setShowMaxIcon(showMaxIcon);
portletDisplay.setShowMinIcon(showMinIcon);
portletDisplay.setShowMoveIcon(showMoveIcon);
portletDisplay.setShowPortletCssIcon(showPortletCssIcon);
portletDisplay.setShowPortletIcon(showPortletIcon);
portletDisplay.setShowPrintIcon(showPrintIcon);
portletDisplay.setShowRefreshIcon(showRefreshIcon);

portletDisplay.setWebDAVEnabled(portlet.getWebDAVStorageInstance() != null);
portletDisplay.setRestoreCurrentView(portlet.isRestoreCurrentView());

portletDisplay.setPortletSetup(portletSetup);

// Portlet custom CSS class name

String customCSSClassName = PortletConfigurationUtil.getPortletCustomCSSClassName(portletSetup);

portletDisplay.setCustomCSSClassName(customCSSClassName);

// Portlet icon

String portletIcon = null;

if (portletResourcePortlet != null) {
	portletIcon = portletResourcePortlet.getStaticResourcePath() + portletResourcePortlet.getIcon();
}
else {
	portletIcon = portlet.getStaticResourcePath() + portlet.getIcon();
}

portletDisplay.setURLPortlet(themeDisplay.getCDNHost() + portletIcon);

// URL close

String urlClose = themeDisplay.getPathMain() + "/portal/update_layout?p_l_id=" + plid + "&p_p_id=" + portletDisplay.getId() + "&doAsUserId=" + HttpUtil.encodeURL(themeDisplay.getDoAsUserId()) + "&" + Constants.CMD + "=" + Constants.DELETE + "&referer=" + HttpUtil.encodeURL(themeDisplay.getPathMain() + "/portal/layout?p_l_id=" + plid + "&doAsUserId=" + themeDisplay.getDoAsUserId()) + "&refresh=1";

if (themeDisplay.isAddSessionIdToURL()) {
	urlClose = PortalUtil.getURLWithSessionId(urlClose, themeDisplay.getSessionId());
}

portletDisplay.setURLClose(urlClose);

// URL configuration

PortletURLImpl urlConfiguration = new PortletURLImpl(request, PortletKeys.PORTLET_CONFIGURATION, plid, PortletRequest.RENDER_PHASE);

urlConfiguration.setWindowState(LiferayWindowState.POP_UP);

urlConfiguration.setEscapeXml(false);

if (Validator.isNotNull(portlet.getConfigurationActionClass())) {
	urlConfiguration.setParameter("struts_action", "/portlet_configuration/edit_configuration");
}
else {
	urlConfiguration.setParameter("struts_action", "/portlet_configuration/edit_permissions");
}

urlConfiguration.setParameter("redirect", currentURL);
urlConfiguration.setParameter("returnToFullPageURL", currentURL);
urlConfiguration.setParameter("portletResource", portletDisplay.getId());
urlConfiguration.setParameter("resourcePrimKey", PortletPermissionUtil.getPrimaryKey(plid, portlet.getPortletId()));

portletDisplay.setURLConfiguration(urlConfiguration.toString() + "&" + PortalUtil.getPortletNamespace(PortletKeys.PORTLET_CONFIGURATION));

// URL edit

PortletURLImpl urlEdit = new PortletURLImpl(request, portletDisplay.getId(), plid, PortletRequest.RENDER_PHASE);

if (portletDisplay.isModeEdit()) {
	urlEdit.setWindowState(WindowState.NORMAL);
	urlEdit.setPortletMode(PortletMode.VIEW);
}
else {
	if (portlet.isMaximizeEdit() || portletDisplay.isStateMax()) {
		urlEdit.setWindowState(WindowState.MAXIMIZED);
	}
	else {
		urlEdit.setWindowState(WindowState.NORMAL);
	}

	urlEdit.setPortletMode(PortletMode.EDIT);
}

urlEdit.setEscapeXml(false);

portletDisplay.setURLEdit(urlEdit.toString());

// URL edit defaults

PortletURLImpl urlEditDefaults = new PortletURLImpl(request, portletDisplay.getId(), plid, PortletRequest.RENDER_PHASE);

if (portletDisplay.isModeEditDefaults()) {
	urlEditDefaults.setWindowState(WindowState.NORMAL);
	urlEditDefaults.setPortletMode(PortletMode.VIEW);
}
else {
	if (portlet.isMaximizeEdit()) {
		urlEditDefaults.setWindowState(WindowState.MAXIMIZED);
	}
	else {
		urlEditDefaults.setWindowState(WindowState.NORMAL);
	}

	urlEditDefaults.setPortletMode(LiferayPortletMode.EDIT_DEFAULTS);
}

urlEditDefaults.setEscapeXml(false);

portletDisplay.setURLEditDefaults(urlEditDefaults.toString());

// URL edit guest

PortletURLImpl urlEditGuest = new PortletURLImpl(request, portletDisplay.getId(), plid, PortletRequest.RENDER_PHASE);

if (portletDisplay.isModeEditGuest()) {
	urlEditGuest.setWindowState(WindowState.NORMAL);
	urlEditGuest.setPortletMode(PortletMode.VIEW);
}
else {
	if (portlet.isMaximizeEdit()) {
		urlEditGuest.setWindowState(WindowState.MAXIMIZED);
	}
	else {
		urlEditGuest.setWindowState(WindowState.NORMAL);
	}

	urlEditGuest.setPortletMode(LiferayPortletMode.EDIT_GUEST);
}

urlEditGuest.setEscapeXml(false);

portletDisplay.setURLEditGuest(urlEditGuest.toString());

// URL export / import

PortletURLImpl urlExportImport = new PortletURLImpl(request, PortletKeys.PORTLET_CONFIGURATION, plid, PortletRequest.RENDER_PHASE);

urlExportImport.setWindowState(WindowState.MAXIMIZED);

urlExportImport.setParameter("struts_action", "/portlet_configuration/export_import");
urlExportImport.setParameter("redirect", currentURL);
urlExportImport.setParameter("returnToFullPageURL", currentURL);
urlExportImport.setParameter("portletResource", portletDisplay.getId());

urlExportImport.setEscapeXml(false);

portletDisplay.setURLExportImport(urlExportImport.toString() + "&" + PortalUtil.getPortletNamespace(PortletKeys.PORTLET_CONFIGURATION));

// URL help

PortletURLImpl urlHelp = new PortletURLImpl(request, portletDisplay.getId(), plid, PortletRequest.RENDER_PHASE);

if (portletDisplay.isModeHelp()) {
	urlHelp.setWindowState(WindowState.NORMAL);
	urlHelp.setPortletMode(PortletMode.VIEW);
}
else {
	if (portlet.isMaximizeHelp()) {
		urlHelp.setWindowState(WindowState.MAXIMIZED);
	}
	else {
		urlHelp.setWindowState(WindowState.NORMAL);
	}

	urlHelp.setPortletMode(PortletMode.HELP);
}

urlHelp.setEscapeXml(false);

portletDisplay.setURLHelp(urlHelp.toString());

// URL max

String lifecycle = PortletRequest.RENDER_PHASE;

if (!portletDisplay.isRestoreCurrentView()) {
	lifecycle = PortletRequest.ACTION_PHASE;
}

PortletURLImpl urlMax = new PortletURLImpl(request, portletDisplay.getId(), plid, lifecycle);

if (portletDisplay.isStateMax()) {
	urlMax.setWindowState(WindowState.NORMAL);
}
else {
	urlMax.setWindowState(WindowState.MAXIMIZED);
}

urlMax.setWindowStateRestoreCurrentView(true);

urlMax.setEscapeXml(false);

if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
	String portletNamespace = portletDisplay.getNamespace();

	Set<String> publicRenderParameterNames = SetUtil.fromEnumeration(portletConfig.getPublicRenderParameterNames());

	Map renderParameters = RenderParametersPool.get(request, plid, portletDisplay.getId());

	Iterator itr = renderParameters.entrySet().iterator();

	while (itr.hasNext()) {
		Map.Entry entry = (Map.Entry)itr.next();

		String key = (String)entry.getKey();

		if (key.startsWith(portletNamespace) || publicRenderParameterNames.contains(key)) {
			if (key.startsWith(portletNamespace)) {
				key = key.substring(portletNamespace.length(), key.length());
			}

			String[] values = (String[])entry.getValue();

			urlMax.setParameter(key, values);
		}
	}
}

portletDisplay.setURLMax(urlMax.toString());

// URL min

String urlMin = themeDisplay.getPathMain() + "/portal/update_layout?p_l_id=" + plid + "&p_p_id=" + portletDisplay.getId() + "&p_p_restore=" + portletDisplay.isStateMin() + "&doAsUserId=" + HttpUtil.encodeURL(themeDisplay.getDoAsUserId()) + "&" + Constants.CMD + "=minimize&referer=" + HttpUtil.encodeURL(themeDisplay.getPathMain() + "/portal/layout?p_l_id=" + plid + "&doAsUserId=" + themeDisplay.getDoAsUserId()) + "&refresh=1";

portletDisplay.setURLMin(urlMin);

// URL portlet css

String urlPortletCss = "javascript:;";

portletDisplay.setURLPortletCss(urlPortletCss.toString());

// URL print

PortletURLImpl urlPrint = new PortletURLImpl(request, portletDisplay.getId(), plid, PortletRequest.RENDER_PHASE);

if (portletDisplay.isModePrint()) {
	urlPrint.setWindowState(WindowState.NORMAL);
	urlPrint.setPortletMode(PortletMode.VIEW);
}
else {
	if (portlet.isPopUpPrint()) {
		urlPrint.setWindowState(LiferayWindowState.POP_UP);
	}
	else {
		urlPrint.setWindowState(WindowState.NORMAL);
	}

	urlPrint.setPortletMode(LiferayPortletMode.PRINT);
}

urlPrint.setEscapeXml(false);

portletDisplay.setURLPrint(urlPrint.toString());

// URL refresh

String urlRefresh = "javascript:;";

portletDisplay.setURLRefresh(urlRefresh);

// URL back

String urlBack = null;

if (portletDisplay.isModeEdit()) {
	urlBack = urlEdit.toString();
}
else if (portletDisplay.isModeEditDefaults()) {
	urlBack = urlEditDefaults.toString();
}
else if (portletDisplay.isModeEditGuest()) {
	urlBack = urlEditGuest.toString();
}
else if (portletDisplay.isModeHelp()) {
	urlBack = urlHelp.toString();
}
else if (portletDisplay.isModePrint()) {
	urlBack = urlPrint.toString();
}
else if (portletDisplay.isStateMax()) {
	//if (portletDisplay.getId().equals(PortletKeys.PORTLET_CONFIGURATION)) {
		/*String portletResource = ParamUtil.getString(request, "portletResource");

		urlMax.setAnchor(false);

		urlBack = urlMax.toString() + "#p_" + portletResource;*/

		//urlBack = ParamUtil.getString(renderRequestImpl, "returnToFullPageURL");
	//}
	//else {
	//	urlBack = urlMax.toString();
	//}

	if (portletDisplay.getId().startsWith("WSRP_")) {
		urlBack = portletDisplay.getURLBack();
	}
	else {
		urlBack = ParamUtil.getString(renderRequestImpl, "returnToFullPageURL");
		urlBack = HtmlUtil.stripHtml(urlBack);
		urlBack = PortalUtil.escapeRedirect(urlBack);
	}

	if (Validator.isNull(urlBack)) {
		urlBack = urlMax.toString();
	}
}

if (urlBack != null) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(urlBack);
}

if (themeDisplay.isWidget()) {
	portletDisplay.setShowBackIcon(false);
}

if (group.isControlPanel()) {
	portletDisplay.setShowBackIcon(false);
	portletDisplay.setShowConfigurationIcon(false);
	portletDisplay.setShowMaxIcon(false);
	portletDisplay.setShowMinIcon(false);
	portletDisplay.setShowMoveIcon(false);
	portletDisplay.setShowPortletCssIcon(false);

	if (!portlet.isPreferencesUniquePerLayout() && Validator.isNotNull(portlet.getConfigurationActionClass())) {
		portletDisplay.setShowConfigurationIcon(true);
	}
}

// Portlet decorate

boolean portletDecorateDefault = GetterUtil.getBoolean(themeDisplay.getThemeSetting("portlet-setup-show-borders-default"), PropsValues.THEME_PORTLET_DECORATE_DEFAULT);

boolean portletDecorate = GetterUtil.getBoolean(portletSetup.getValue("portletSetupShowBorders", String.valueOf(portletDecorateDefault)));

Boolean portletDecorateObj = (Boolean)renderRequestImpl.getAttribute(WebKeys.PORTLET_DECORATE);

if (portletDecorateObj != null) {
	portletDecorate = portletDecorateObj.booleanValue();
}

// Make sure the Tiles context is reset for the next portlet

if ((invokerPortlet != null) && (invokerPortlet.isStrutsPortlet() || invokerPortlet.isStrutsBridgePortlet())) {
	request.removeAttribute(ComponentConstants.COMPONENT_CONTEXT);
}
%>

<%@ include file="/html/portal/render_portlet-ext.jsp" %>

<%

// Render portlet

boolean portletException = false;
Boolean portletVisibility = null;

if (portlet.isActive() && portlet.isReady() && access && supportsMimeType) {
	try {
		invokerPortlet.render(renderRequestImpl, renderResponseImpl);

		portletVisibility = (Boolean)renderRequestImpl.getAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY);

		if (portletVisibility != null) {
			request.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, portletVisibility);
		}

		if (themeDisplay.isFacebook() || themeDisplay.isStateExclusive()) {
			renderRequestImpl.setAttribute(WebKeys.STRING_SERVLET_RESPONSE, stringResponse);
		}
	}
	catch (UnavailableException ue) {
		portletException = true;

		if (ue.isPermanent()) {
			PortletInstanceFactoryUtil.destroy(portlet);
		}
	}
	catch (Exception e) {
		portletException = true;

		LogUtil.log(_log, e);
	}
}

// Make sure the Tiles context is reset for the next portlet

if ((invokerPortlet != null) && (invokerPortlet.isStrutsPortlet() || invokerPortlet.isStrutsBridgePortlet())) {
	request.removeAttribute(ComponentConstants.COMPONENT_CONTEXT);
}

if ((layout.isTypePanel() || layout.isTypeControlPanel()) && !portletDisplay.getId().equals(PortletKeys.CONTROL_PANEL_MENU)) {
	PortalUtil.setPageTitle(portletDisplay.getTitle(), request);
}
%>

<c:if test="<%= !themeDisplay.isFacebook() && !themeDisplay.isStateExclusive() && !themeDisplay.isWapTheme() %>">

	<%
	if (themeDisplay.isStatePopUp() || themeDisplay.isWidget()) {
		PortalUtil.setPageTitle(portletDisplay.getTitle(), request);
	}

	String freeformStyles = StringPool.BLANK;
	String cssClasses = StringPool.BLANK;

	if (themeDisplay.isFreeformLayout() && !themeDisplay.isStatePopUp() && !runtimePortlet && !layoutTypePortlet.hasStateMax()) {
		StringBundler sb = new StringBundler(7);

		Properties freeformStyleProps = PropertiesUtil.load(portletSetup.getValue("portlet-freeform-styles", StringPool.BLANK));

		sb.append("style=\"left: ");
		sb.append(GetterUtil.getString(freeformStyleProps.getProperty("left"), "0"));
		sb.append("; position: absolute; top: ");
		sb.append(GetterUtil.getString(freeformStyleProps.getProperty("top"), "0"));
		sb.append("; width: ");
		sb.append(GetterUtil.getString(freeformStyleProps.getProperty("width"), "400px"));
		sb.append(";\"");

		freeformStyles = sb.toString();
	}

	if ((portletVisibility != null) && !layout.isTypeControlPanel()) {
		cssClasses += " lfr-configurator-visibility";
	}

	if (portletDisplay.isStateMin()) {
		cssClasses += " portlet-minimized";
	}

	if (!portletDisplay.isShowMoveIcon()) {
		if (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isColumnDisabled(columnId)) {
			cssClasses += " portlet-static";
		}
		else if (portlet.isStaticStart()) {
			cssClasses += " portlet-static portlet-static-start";
		}
		else if (portlet.isStaticEnd()) {
			cssClasses += " portlet-static portlet-static-end";
		}
	}

	if (!portletDecorate) {
		cssClasses += " portlet-borderless";
	}

	cssClasses = "portlet-boundary portlet-boundary" + HtmlUtil.escapeAttribute(PortalUtil.getPortletNamespace(rootPortletId)) + StringPool.SPACE + cssClasses + StringPool.SPACE + portlet.getCssClassWrapper() + StringPool.SPACE + customCSSClassName;

	if (portletResourcePortlet != null) {
		cssClasses += StringPool.SPACE + portletResourcePortlet.getCssClassWrapper();
	}
	%>

	<div id="p_p_id<%= HtmlUtil.escapeAttribute(renderResponseImpl.getNamespace()) %>" class="<%= cssClasses %>" <%= freeformStyles %>>
		<a id="p_<%= HtmlUtil.escapeAttribute(portletId) %>"></a>

		<div class="portlet-body">
</c:if>

<c:choose>
	<c:when test="<%= !supportsMimeType %>">
	</c:when>
	<c:when test="<%= !access && !portlet.isShowPortletAccessDenied() %>">
	</c:when>
	<c:when test="<%= !portlet.isActive() && !portlet.isShowPortletInactive() %>">
	</c:when>
	<c:otherwise>

		<%
		boolean useDefaultTemplate = portlet.isUseDefaultTemplate();
		Boolean useDefaultTemplateObj = renderResponseImpl.getUseDefaultTemplate();

		if (useDefaultTemplateObj != null) {
			useDefaultTemplate = useDefaultTemplateObj.booleanValue();
		}

		if ((invokerPortlet != null) && (invokerPortlet.isStrutsPortlet() || invokerPortlet.isStrutsBridgePortlet())) {
			if (!access || portletException) {
				PortletRequestProcessor portletReqProcessor = (PortletRequestProcessor)portletCtx.getAttribute(WebKeys.PORTLET_STRUTS_PROCESSOR);

				ActionMapping actionMapping = portletReqProcessor.processMapping(request, response, (String)portlet.getInitParams().get("view-action"));

				ComponentDefinition definition = null;

				if (actionMapping != null) {

					// See action path /weather/view

					String definitionName = actionMapping.getForward();

					if (definitionName == null) {

						// See action path /journal/view_articles

						String[] definitionNames = actionMapping.findForwards();

						for (int definitionNamesPos = 0; definitionNamesPos < definitionNames.length; definitionNamesPos++) {
							if (definitionNames[definitionNamesPos].endsWith("view")) {
								definitionName = definitionNames[definitionNamesPos];

								break;
							}
						}

						if (definitionName == null) {
							definitionName = definitionNames[0];
						}
					}

					definition = TilesUtil.getDefinition(definitionName, request, application);
				}

				String templatePath = StrutsUtil.TEXT_HTML_DIR + "/common/themes/portlet.jsp";

				if (definition != null) {
					templatePath = StrutsUtil.TEXT_HTML_DIR + definition.getPath();
				}
		%>

				<tiles:insert template="<%= templatePath %>" flush="false">
					<tiles:put name="portlet_content" value="/portal/portlet_error.jsp" />
				</tiles:insert>

		<%
			}
			else {
				if (useDefaultTemplate) {
					renderRequestImpl.setAttribute(WebKeys.PORTLET_CONTENT, stringResponse.getString());
		%>

					<tiles:insert template='<%= StrutsUtil.TEXT_HTML_DIR + "/common/themes/portlet.jsp" %>' flush="false">
						<tiles:put name="portlet_content" value="<%= StringPool.BLANK %>" />
					</tiles:insert>

		<%
				}
				else {
					stringResponse.writeTo(pageContext.getOut());
				}
			}
		}
		else {
			renderRequestImpl.setAttribute(WebKeys.PORTLET_CONTENT, stringResponse.getString());

			String portletContent = StringPool.BLANK;

			if (!portlet.isReady()) {
				portletContent = "/portal/portlet_not_ready.jsp";
			}

			if (portletException) {
				portletContent = "/portal/portlet_error.jsp";
			}

			if (!portlet.isAjaxable() && cmd.equals("add")) {
		%>

				<liferay-util:buffer var="nonAjaxableContentMessage">
					<div class="portlet-msg-info">
						<liferay-ui:message key="this-change-will-only-be-shown-after-you-refresh-the-page" />
					</div>
				</liferay-util:buffer>

		<%
				renderRequestImpl.setAttribute(WebKeys.PORTLET_CONTENT, nonAjaxableContentMessage);
			}
		%>

			<c:choose>
				<c:when test="<%= useDefaultTemplate || portletException %>">
					<tiles:insert template='<%= StrutsUtil.TEXT_HTML_DIR + "/common/themes/portlet.jsp" %>' flush="false">
						<tiles:put name="portlet_content" value="<%= portletContent %>" />
					</tiles:insert>
				</c:when>
				<c:otherwise>
					<%= renderRequestImpl.getAttribute(WebKeys.PORTLET_CONTENT) %>
				</c:otherwise>
			</c:choose>

		<%
		}
		%>

	</c:otherwise>
</c:choose>

<%
String staticVar = "yes";

if (portletDisplay.isShowMoveIcon()) {
	staticVar = "no";
}
else {
	if (portlet.isStaticStart()) {
		staticVar = "start";
	}

	if (portlet.isStaticEnd()) {
		staticVar = "end";
	}
}
%>

<c:if test="<%= !themeDisplay.isFacebook() && !themeDisplay.isStateExclusive() && !themeDisplay.isWapTheme() %>">

			<%
			String modules = StringPool.BLANK;

			if (showConfigurationIcon) {
				modules += "aui-editable";
			}
			%>

			<aui:script position='<%= themeDisplay.isIsolated() ? "inline" : "auto" %>' use="<%= modules %>">
				Liferay.Portlet.onLoad(
					{
						canEditTitle: <%= showConfigurationIcon && portletDecorate %>,
						columnPos: <%= columnPos %>,
						isStatic: '<%= staticVar %>',
						namespacedId: 'p_p_id<%= HtmlUtil.escapeJS(renderResponseImpl.getNamespace()) %>',
						portletId: '<%= HtmlUtil.escapeJS(portletDisplay.getId()) %>',
						refreshURL: '<%= HtmlUtil.escapeJS(PortletURLUtil.getRefreshURL(request, themeDisplay)) %>'
					}
				);
			</aui:script>
		</div>
	</div>
</c:if>

<%
if (themeDisplay.isStatePopUp()) {
	String doRefreshPortletId = null;

	if ((doRefreshPortletId = (String)SessionMessages.get(renderRequestImpl, portletConfig.getPortletName() + ".doRefresh")) != null) {
		if (Validator.isNull(doRefreshPortletId) && (portletResourcePortlet != null)) {
			doRefreshPortletId = portletResourcePortlet.getPortletId();
		}
%>

		<aui:script position="inline" use="aui-base">
			if (window.parent) {
				var data = null;

				var curPortletBoundaryId = '#p_p_id_<%= doRefreshPortletId %>_';

				<c:if test='<%= (portletResourcePortlet != null && !portletResourcePortlet.isAjaxable()) || SessionMessages.contains(renderRequestImpl, portletConfig.getPortletName() + ".notAjaxable") %>'>
					data = {
						portletAjaxable: false
					};
				</c:if>

				Liferay.Util.getOpener().Liferay.Portlet.refresh(curPortletBoundaryId, data);
			}
		</aui:script>

<%
	}

	String doCloseRedirect = null;

	if ((doCloseRedirect = (String)SessionMessages.get(renderRequestImpl, portletConfig.getPortletName() + ".doCloseRedirect")) != null) {
%>

		<aui:script use="aui-base,aui-loading-mask">
			var dialog = Liferay.Util.getWindow();

			dialog.on(
				'visibleChange',
				function(event) {
					if (!event.newVal && event.src !== 'hideLink') {
						var topWindow = Liferay.Util.getTop();
						var topA = topWindow.AUI();

						new topA.LoadingMask(
							{
								target: topA.getBody()
							}
						).show();

						topWindow.location.href = '<%= doCloseRedirect %>';
					}
				}
			);
		</aui:script>

<%
	}
}

themeDisplay.setScopeGroupId(previousScopeGroupId);
themeDisplay.setParentGroupId(previousParentGroupId);

if (showPortletCssIcon) {
	themeDisplay.setIncludePortletCssJs(true);
}

SessionMessages.clear(renderRequestImpl);
SessionErrors.clear(renderRequestImpl);

if (themeDisplay.isFacebook() || themeDisplay.isStateExclusive()) {
	request.setAttribute(JavaConstants.JAVAX_PORTLET_REQUEST, renderRequestImpl);
	request.setAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE, renderResponseImpl);
}
else {
	renderRequestImpl.cleanUp();
}
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portal.render_portlet_jsp");
%>