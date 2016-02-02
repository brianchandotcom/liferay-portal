<%--
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
--%>

<%@ include file="/html/portal/init.jsp" %>

<%
String cmd = ParamUtil.getString(request, Constants.CMD);

Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);

String portletId = portlet.getPortletId();
String rootPortletId = portlet.getRootPortletId();
String instanceId = portlet.getInstanceId();

String portletPrimaryKey = PortletPermissionUtil.getPrimaryKey(plid, portletId);

String columnId = GetterUtil.getString(request.getAttribute(WebKeys.RENDER_PORTLET_COLUMN_ID));
int columnPos = GetterUtil.getInteger(request.getAttribute(WebKeys.RENDER_PORTLET_COLUMN_POS));
int columnCount = GetterUtil.getInteger(request.getAttribute(WebKeys.RENDER_PORTLET_COLUMN_COUNT));
Boolean renderPortletResource = (Boolean)request.getAttribute(WebKeys.RENDER_PORTLET_RESOURCE);

boolean runtimePortlet = (renderPortletResource != null) && renderPortletResource.booleanValue();

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

boolean columnDisabled = layoutTypePortlet.isColumnDisabled(columnId);
boolean customizable = layoutTypePortlet.isCustomizable();

PortletPreferencesIds portletPreferencesIds = PortletPreferencesFactoryUtil.getPortletPreferencesIds(request, portletId);

PortletPreferences portletPreferences = PortletPreferencesLocalServiceUtil.getStrictPreferences(portletPreferencesIds);

PortletPreferences portletSetup = PortletPreferencesFactoryUtil.getStrictLayoutPortletSetup(layout, portletId);

Group group = null;
boolean privateLayout = false;

if (layout instanceof VirtualLayout) {
	VirtualLayout virtualLayout = (VirtualLayout)layout;

	Layout sourceLayout = virtualLayout.getSourceLayout();

	group = sourceLayout.getGroup();
	privateLayout = sourceLayout.isPrivateLayout();
}
else {
	group = layout.getGroup();
	privateLayout = layout.isPrivateLayout();
}

long portletItemId = ParamUtil.getLong(request, "p_p_i_id");

if (portletItemId > 0) {
	PortletPreferencesServiceUtil.restoreArchivedPreferences(themeDisplay.getSiteGroupId(), layout, portlet.getPortletId(), portletItemId, portletPreferences);
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

RenderRequestImpl renderRequestImpl = RenderRequestFactory.create(request, portlet, invokerPortlet, portletCtx, windowState, portletMode, portletPreferences, plid);

BufferCacheServletResponse bufferCacheServletResponse = new BufferCacheServletResponse(response);

RenderResponseImpl renderResponseImpl = RenderResponseFactory.create(renderRequestImpl, bufferCacheServletResponse, portletId, company.getCompanyId(), plid);

if (stateMin) {
	renderResponseImpl.setUseDefaultTemplate(true);
}

renderRequestImpl.defineObjects(portletConfig, renderResponseImpl);

String responseContentType = renderRequestImpl.getResponseContentType();

String currentURL = PortalUtil.getCurrentURL(request);

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNull(portletResource)) {
	portletResource = ParamUtil.getString(renderRequestImpl, "portletResource");
}

Portlet portletResourcePortlet = null;

if (Validator.isNotNull(portletResource)) {
	portletResourcePortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);
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
boolean showStagingIcon = false;

Boolean portletParallelRender = (Boolean)request.getAttribute(WebKeys.PORTLET_PARALLEL_RENDER);

if ((portletParallelRender != null) && (portletParallelRender.booleanValue() == false)) {
	showRefreshIcon = false;
}

Layout curLayout = PortletConfigurationLayoutUtil.getLayout(themeDisplay);

if ((!group.hasStagingGroup() || group.isStagingGroup()) &&
	(PortletPermissionUtil.contains(permissionChecker, themeDisplay.getScopeGroupId(), curLayout, portlet, ActionKeys.CONFIGURATION))) {

	showConfigurationIcon = true;

	boolean supportsConfigurationLAR = portlet.getConfigurationActionInstance() != null;
	boolean supportsDataLAR = !(portlet.getPortletDataHandlerInstance() instanceof DefaultConfigurationPortletDataHandler);

	if (supportsConfigurationLAR || supportsDataLAR || !group.isControlPanel()) {
		showExportImportIcon = true;
	}

	if (PropsValues.PORTLET_CSS_ENABLED) {
		showPortletCssIcon = true;
	}
}

if (!columnDisabled && customizable && !portlet.isPreferencesCompanyWide() && portlet.isPreferencesUniquePerLayout() && LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.CUSTOMIZE)) {
	showConfigurationIcon = true;

	if (PropsValues.PORTLET_CSS_ENABLED) {
		showPortletCssIcon = true;
	}
}

if (group.isLayoutPrototype()) {
	showExportImportIcon = false;
}

if ((group.isStaged() || group.isStagedRemotely()) && !group.hasLocalOrRemoteStagingGroup()) {
	showStagingIcon = true;
}

if (portlet.hasPortletMode(responseContentType, PortletMode.EDIT)) {
	if (PortletPermissionUtil.contains(permissionChecker, layout, portletId, ActionKeys.PREFERENCES)) {
		showEditIcon = true;
	}
}

if (portlet.hasPortletMode(responseContentType, LiferayPortletMode.EDIT_DEFAULTS) && showEditIcon) {
	showEditDefaultsIcon = true;
}

if (portlet.hasPortletMode(responseContentType, LiferayPortletMode.EDIT_GUEST)) {
	if (showEditIcon && !layout.isPrivateLayout()) {
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

	if (!(!columnDisabled && customizable && LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.CUSTOMIZE))) {
		showCloseIcon = false;
		showMoveIcon = false;
	}
}

// Portlets cannot be moved if the column is not customizable

if (columnDisabled && customizable) {
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

// Portlets in a layout linked to a layout prototype cannot be moved

if (layout.isLayoutPrototypeLinkActive()) {
	showCloseIcon = false;
	showConfigurationIcon = false;
	showMoveIcon = false;
	showPortletCssIcon = false;
}

// Portlets in a full page cannot be moved

LayoutTypeController layoutTypeController = layoutTypePortlet.getLayoutTypeController();

if (layoutTypeController.isFullPageDisplayable()) {
	showCloseIcon = false;
	showMaxIcon = false;
	showMinIcon = false;
}

long previousScopeGroupId = themeDisplay.getScopeGroupId();

if (Validator.isNotNull(portletResource)) {
	themeDisplay.setScopeGroupId(PortalUtil.getScopeGroupId(request, portletResourcePortlet.getPortletId()));
}
else {
	themeDisplay.setScopeGroupId(PortalUtil.getScopeGroupId(request, portletId));
}

long previousSiteGroupId = themeDisplay.getSiteGroupId();

Group siteGroup = GroupLocalServiceUtil.getGroup(themeDisplay.getSiteGroupId());

if (siteGroup.isStagingGroup()) {
	siteGroup = siteGroup.getLiveGroup();
}

if (siteGroup.isStaged() && !siteGroup.isStagedRemotely() && !siteGroup.isStagedPortlet(portletId)) {
	themeDisplay.setSiteGroupId(siteGroup.getGroupId());
}

// Portlet decorate

boolean tilesPortletDecorate = GetterUtil.getBoolean(TilesAttributeUtil.getTilesAttribute(pageContext, "portlet_decorate"), true);

boolean portletDecorate = tilesPortletDecorate;

Boolean portletDecorateObj = (Boolean)request.getAttribute(WebKeys.PORTLET_DECORATE);

if (portletDecorateObj != null) {
	portletDecorate = portletDecorateObj.booleanValue();

	request.removeAttribute(WebKeys.PORTLET_DECORATE);
}

portletDisplay.recycle();

portletDisplay.setActive(portlet.isActive());
portletDisplay.setColumnCount(columnCount);
portletDisplay.setColumnId(columnId);
portletDisplay.setColumnPos(columnPos);
portletDisplay.setId(portletId);
portletDisplay.setInstanceId(instanceId);
portletDisplay.setModeAbout(modeAbout);
portletDisplay.setModeConfig(modeConfig);
portletDisplay.setModeEdit(modeEdit);
portletDisplay.setModeEditDefaults(modeEditDefaults);
portletDisplay.setModeEditGuest(modeEditGuest);
portletDisplay.setModeHelp(modeHelp);
portletDisplay.setModePreview(modePreview);
portletDisplay.setModePrint(modePrint);
portletDisplay.setModeView(portletMode.equals(PortletMode.VIEW));
portletDisplay.setNamespace(PortalUtil.getPortletNamespace(portletId));
portletDisplay.setPortletDecorate(portletDecorate);
portletDisplay.setPortletDisplayName(PortalUtil.getPortletTitle(renderRequestImpl));
portletDisplay.setPortletName(portletConfig.getPortletName());
portletDisplay.setPortletResource(portletResource);
portletDisplay.setResourcePK(portletPrimaryKey);
portletDisplay.setRestoreCurrentView(portlet.isRestoreCurrentView());
portletDisplay.setRootPortletId(rootPortletId);
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
portletDisplay.setShowStagingIcon(showStagingIcon);
portletDisplay.setStateExclusive(themeDisplay.isStateExclusive());
portletDisplay.setStateMax(stateMax);
portletDisplay.setStateMin(stateMin);
portletDisplay.setStateNormal(windowState.equals(WindowState.NORMAL));
portletDisplay.setStatePopUp(themeDisplay.isStatePopUp());
portletDisplay.setPortletSetup(portletSetup);
portletDisplay.setWebDAVEnabled(portlet.getWebDAVStorageInstance() != null);

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

StringBundler sb = new StringBundler(18);

sb.append(themeDisplay.getPathMain());
sb.append("/portal/update_layout?p_auth=");
sb.append(AuthTokenUtil.getToken(request));
sb.append("&p_l_id=");
sb.append(plid);
sb.append("&p_p_id=");
sb.append(portletDisplay.getId());
sb.append("&p_v_l_s_g_id=");
sb.append(themeDisplay.getSiteGroupId());
sb.append("&doAsUserId=");
sb.append(HttpUtil.encodeURL(themeDisplay.getDoAsUserId()));
sb.append("&");
sb.append(Constants.CMD);
sb.append("=");
sb.append(Constants.DELETE);
sb.append("&referer=");

StringBundler innerSB = new StringBundler(5);

innerSB.append(themeDisplay.getPathMain());
innerSB.append("/portal/layout?p_l_id=");
innerSB.append(plid);
innerSB.append("&doAsUserId=");
innerSB.append(themeDisplay.getDoAsUserId());

sb.append(HttpUtil.encodeURL(innerSB.toString()));

sb.append("&refresh=1");

String urlClose = sb.toString();

if (themeDisplay.isAddSessionIdToURL()) {
	urlClose = PortalUtil.getURLWithSessionId(urlClose, themeDisplay.getSessionId());
}

portletDisplay.setURLClose(urlClose);

// URL configuration

PortletURL urlConfiguration = PortletProviderUtil.getPortletURL(request, PortletConfigurationApplicationType.PortletConfiguration.CLASS_NAME, PortletProvider.Action.VIEW);

urlConfiguration.setWindowState(LiferayWindowState.POP_UP);

if (portlet.getConfigurationActionInstance() != null) {
	urlConfiguration.setParameter("mvcPath", "/edit_configuration.jsp");

	String settingsScope = (String)request.getAttribute(WebKeys.SETTINGS_SCOPE);

	settingsScope = ParamUtil.get(request, "settingsScope", settingsScope);

	if (Validator.isNotNull(settingsScope)) {
		urlConfiguration.setParameter("settingsScope", settingsScope);
	}
}
else if (PortletPermissionUtil.contains(permissionChecker, layout, portletDisplay.getId(), ActionKeys.PERMISSIONS)) {
	urlConfiguration.setParameter("mvcPath", "/edit_permissions.jsp");
}
else {
	urlConfiguration.setParameter("mvcPath", "/edit_sharing.jsp");
}

urlConfiguration.setParameter("redirect", currentURL);
urlConfiguration.setParameter("returnToFullPageURL", currentURL);
urlConfiguration.setParameter("portletConfiguration", Boolean.TRUE.toString());
urlConfiguration.setParameter("portletResource", portletDisplay.getId());
urlConfiguration.setParameter("resourcePrimKey", portletPrimaryKey);

portletDisplay.setURLConfiguration(urlConfiguration.toString());

StringBuilder urlConfigurationJSSB = new StringBuilder(14);

urlConfigurationJSSB.append("Liferay.Portlet.openWindow({bodyCssClass: 'dialog-with-footer',");
urlConfigurationJSSB.append("namespace: '");
urlConfigurationJSSB.append(portletDisplay.getNamespace());
urlConfigurationJSSB.append("', portlet: '#p_p_id_");
urlConfigurationJSSB.append(portletDisplay.getId());
urlConfigurationJSSB.append("_', portletId: '");
urlConfigurationJSSB.append(portletDisplay.getId());

if (PropsValues.PORTLET_CONFIG_SHOW_PORTLET_ID) {
	urlConfigurationJSSB.append("', subTitle: '");
	urlConfigurationJSSB.append(portletDisplay.getId());
}

urlConfigurationJSSB.append("', title: '");
urlConfigurationJSSB.append(UnicodeLanguageUtil.get(request, "configuration"));
urlConfigurationJSSB.append("', uri: '");
urlConfigurationJSSB.append(HtmlUtil.escapeJS(portletDisplay.getURLConfiguration()));
urlConfigurationJSSB.append("'}); return false;");

portletDisplay.setURLConfigurationJS(urlConfigurationJSSB.toString());

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

PortletURLImpl urlExportImport = new PortletURLImpl(request, PortletKeys.EXPORT_IMPORT, plid, PortletRequest.RENDER_PHASE);

urlExportImport.setWindowState(LiferayWindowState.POP_UP);

urlExportImport.setParameter("mvcRenderCommandName", "exportImport");
urlExportImport.setParameter("redirect", currentURL);
urlExportImport.setParameter("returnToFullPageURL", currentURL);
urlExportImport.setParameter("portletResource", portletDisplay.getId());

urlExportImport.setEscapeXml(false);

portletDisplay.setURLExportImport(urlExportImport.toString() + "&" + PortalUtil.getPortletNamespace(PortletKeys.EXPORT_IMPORT));

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

	Map<String, String[]> renderParameters = RenderParametersPool.get(request, plid, portletDisplay.getId());

	if (renderParameters != null) {
		for (Map.Entry<String, String[]> entry : renderParameters.entrySet()) {
			String key = entry.getKey();

			if (key.startsWith(portletNamespace) || publicRenderParameterNames.contains(key)) {
				if (key.startsWith(portletNamespace)) {
					key = key.substring(portletNamespace.length());
				}

				String[] values = entry.getValue();

				urlMax.setParameter(key, values);
			}
		}
	}
}

portletDisplay.setURLMax(urlMax.toString());

// URL min

sb = new StringBundler(16);

sb.append(themeDisplay.getPathMain());
sb.append("/portal/update_layout?p_l_id=");
sb.append(plid);
sb.append("&p_p_id=");
sb.append(portletDisplay.getId());
sb.append("&p_p_restore=");
sb.append(portletDisplay.isStateMin());
sb.append("&p_v_l_s_g_id=");
sb.append(themeDisplay.getSiteGroupId());
sb.append("&doAsUserId=");
sb.append(HttpUtil.encodeURL(themeDisplay.getDoAsUserId()));
sb.append("&");
sb.append(Constants.CMD);
sb.append("=minimize&referer=");

innerSB = new StringBundler(7);

innerSB.append(themeDisplay.getPathMain());
innerSB.append("/portal/layout?p_auth=");
innerSB.append(AuthTokenUtil.getToken(request));
innerSB.append("&p_l_id=");
innerSB.append(plid);
innerSB.append("&doAsUserId=");
innerSB.append(themeDisplay.getDoAsUserId());

sb.append(HttpUtil.encodeURL(innerSB.toString()));

sb.append("&refresh=1");

portletDisplay.setURLMin(sb.toString());

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

// URL staging

PortletURLImpl urlStaging = new PortletURLImpl(request, PortletKeys.EXPORT_IMPORT, plid, PortletRequest.RENDER_PHASE);

urlStaging.setWindowState(LiferayWindowState.POP_UP);

urlStaging.setParameter("mvcRenderCommandName", "publishPortlet");
urlStaging.setParameter("cmd", Constants.PUBLISH_TO_LIVE);
urlStaging.setParameter("redirect", currentURL);
urlStaging.setParameter("returnToFullPageURL", currentURL);
urlStaging.setParameter("portletResource", portletDisplay.getId());

urlStaging.setEscapeXml(false);

portletDisplay.setURLStaging(urlStaging.toString() + "&" + PortalUtil.getPortletNamespace(PortletKeys.EXPORT_IMPORT));

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
	if (portletDisplay.getId().startsWith("WSRP_")) {
		urlBack = portletDisplay.getURLBack();
	}
	else {
		urlBack = ParamUtil.getString(renderRequestImpl, "returnToFullPageURL");
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

	if (!portlet.isPreferencesUniquePerLayout() && (portlet.getConfigurationActionInstance() != null) && PortletPermissionUtil.contains(permissionChecker, themeDisplay.getScopeGroupId(), curLayout, portlet, ActionKeys.CONFIGURATION)) {
		portletDisplay.setShowConfigurationIcon(true);
	}
}

// Portlet decorator

String portletDecoratorId = portletSetup.getValue("portletSetupPortletDecoratorId", StringPool.BLANK);

PortletDecorator portletDecorator = ThemeLocalServiceUtil.getPortletDecorator(company.getCompanyId(), theme.getThemeId(), portletDecoratorId);

// Make sure the Tiles context is reset for the next portlet

if ((invokerPortlet != null) && (invokerPortlet.isStrutsPortlet() || invokerPortlet.isStrutsBridgePortlet())) {
	request.removeAttribute(ComponentConstants.COMPONENT_CONTEXT);
}
%>

<%@ include file="/html/portal/render_portlet-ext.jsp" %>

<%

// Render portlet

boolean portletException = GetterUtil.getBoolean(request.getAttribute(WebKeys.PARALLEL_RENDERING_TIMEOUT_ERROR));
Boolean portletVisibility = null;

if (portlet.isActive() && portlet.isReady() && supportsMimeType && (invokerPortlet != null)) {
	try {
		if (!PortalUtil.isSkipPortletContentProcesssing(group, request, layoutTypePortlet, portletDisplay, portletDisplay.getPortletName())) {
			invokerPortlet.render(renderRequestImpl, renderResponseImpl);
		}

		portletVisibility = (Boolean)renderRequestImpl.getAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY);

		if (portletVisibility != null) {
			request.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, portletVisibility);
		}

		renderResponseImpl.transferHeaders(bufferCacheServletResponse);
	}
	catch (UnavailableException ue) {
		portletException = true;

		if (ue.isPermanent()) {
			PortletInstanceFactoryUtil.destroy(portlet);
		}
	}
	catch (Exception e) {
		portletException = true;

		// Under parallel rendering context. An interrupted state means the call
		// was cancelled and so we should terminate the render process.

		Thread currentThread = Thread.currentThread();

		if (currentThread.isInterrupted()) {
			return;
		}

		LogUtil.log(_log, e);
	}
}

// Make sure the Tiles context is reset for the next portlet

if ((invokerPortlet != null) && (invokerPortlet.isStrutsPortlet() || invokerPortlet.isStrutsBridgePortlet())) {
	request.removeAttribute(ComponentConstants.COMPONENT_CONTEXT);
}

String portalProductMenuApplicationTypePortletId = PortletProviderUtil.getPortletId(PortalProductMenuApplicationType.ProductMenu.CLASS_NAME, PortletProvider.Action.VIEW);

if ((layout.isTypePanel() || layout.isTypeControlPanel()) && !portletDisplay.getId().equals(portalProductMenuApplicationTypePortletId) && !portlet.isStatic()) {
	PortalUtil.setPageTitle(portletDisplay.getTitle(), request);
}

Boolean renderPortletBoundary = GetterUtil.getBoolean(request.getAttribute(WebKeys.RENDER_PORTLET_BOUNDARY), true) && !themeDisplay.isFacebook() && !themeDisplay.isStateExclusive() && !themeDisplay.isWapTheme();
%>

<c:if test="<%= renderPortletBoundary %>">

	<%
	if ((themeDisplay.isStatePopUp() || themeDisplay.isWidget()) && !portlet.isStatic()) {
		PortalUtil.setPageTitle(portletDisplay.getTitle(), request);
	}

	String freeformStyles = StringPool.BLANK;
	String cssClasses = StringPool.BLANK;

	if (themeDisplay.isFreeformLayout() && !themeDisplay.isStatePopUp() && !runtimePortlet && !layoutTypePortlet.hasStateMax()) {
		sb = new StringBundler(7);

		Properties freeformStyleProps = PropertiesUtil.load(portletSetup.getValue("portlet-freeform-styles", StringPool.BLANK));

		sb.append("style=\"left: ");
		sb.append(GetterUtil.getString(HtmlUtil.escapeAttribute(freeformStyleProps.getProperty("left")), "0"));
		sb.append("; position: absolute; top: ");
		sb.append(GetterUtil.getString(HtmlUtil.escapeAttribute(freeformStyleProps.getProperty("top")), "0"));
		sb.append("; width: ");
		sb.append(GetterUtil.getString(HtmlUtil.escapeAttribute(freeformStyleProps.getProperty("width")), "400px"));
		sb.append(";\"");

		freeformStyles = sb.toString();
	}

	if (portletDisplay.isStateMin()) {
		cssClasses += " portlet-minimized";
	}

	if (!portletDisplay.isShowMoveIcon()) {
		if (columnDisabled && customizable) {
			cssClasses += " portlet-static";
		}
		else if (portlet.isStaticStart()) {
			cssClasses += " portlet-static portlet-static-start";
		}
		else if (portlet.isStaticEnd()) {
			cssClasses += " portlet-static portlet-static-end";
		}
	}

	if ((portletDecorator != null) && portletDisplay.isPortletDecorate()) {
		portletDisplay.setPortletDecoratorId(portletDecorator.getPortletDecoratorId());

		cssClasses += StringPool.SPACE + portletDecorator.getCssClass();
	}

	cssClasses = "portlet-boundary portlet-boundary" + HtmlUtil.escapeAttribute(PortalUtil.getPortletNamespace(rootPortletId)) + StringPool.SPACE + cssClasses + StringPool.SPACE + portlet.getCssClassWrapper() + StringPool.SPACE + HtmlUtil.escapeAttribute(customCSSClassName);

	if (portletResourcePortlet != null) {
		cssClasses += StringPool.SPACE + portletResourcePortlet.getCssClassWrapper();
	}

	String defaultScreenCssClasses = StringPool.BLANK;

	if ((portletVisibility != null) && !layout.isTypeControlPanel()) {
		defaultScreenCssClasses += " lfr-configurator-visibility";
	}
	%>

	<div class="<%= cssClasses %>" id="p_p_id<%= HtmlUtil.escapeAttribute(renderResponseImpl.getNamespace()) %>" <%= freeformStyles %>>
		<div class="<%= defaultScreenCssClasses %>" id="p_p_id<%= HtmlUtil.escapeAttribute(renderResponseImpl.getNamespace()) %>-defaultScreen">
</c:if>

<c:choose>
	<c:when test="<%= !supportsMimeType %>">
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

		PortletRequestProcessor portletReqProcessor = (PortletRequestProcessor)portletCtx.getAttribute(WebKeys.PORTLET_STRUTS_PROCESSOR);

		boolean addNotAjaxablePortlet = !portlet.isAjaxable() && cmd.equals("add");

		if ((portletReqProcessor != null) && !addNotAjaxablePortlet) {
			if (portletException) {
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

				request.setAttribute(WebKeys.PORTLET_CONTENT_JSP, "/portal/portlet_error.jsp");
		%>

				<liferay-util:include page="<%= templatePath %>" />

		<%
			}
			else {
				if (useDefaultTemplate || !portlet.isActive()) {
					renderRequestImpl.setAttribute(WebKeys.PORTLET_CONTENT, bufferCacheServletResponse.getString());

					request.setAttribute(WebKeys.PORTLET_CONTENT_JSP, StringPool.BLANK);
		%>

					<liferay-util:include page='<%= StrutsUtil.TEXT_HTML_DIR + "/common/themes/portlet.jsp" %>' />

		<%
				}
				else {
					pageContext.getOut().write(bufferCacheServletResponse.getString());
				}
			}
		}
		else {
			renderRequestImpl.setAttribute(WebKeys.PORTLET_CONTENT, bufferCacheServletResponse.getString());

			String portletContentJSP = StringPool.BLANK;

			if (!portlet.isReady()) {
				portletContentJSP = "/portal/portlet_not_ready.jsp";
			}

			if (portletException) {
				portletContentJSP = "/portal/portlet_error.jsp";
			}

			if (addNotAjaxablePortlet) {
				portletContentJSP = "/portal/portlet_not_ajaxable.jsp";
			}

			request.setAttribute(WebKeys.PORTLET_CONTENT_JSP, portletContentJSP);
		%>

			<c:choose>
				<c:when test="<%= useDefaultTemplate || portletException || addNotAjaxablePortlet %>">
					<liferay-util:include page='<%= StrutsUtil.TEXT_HTML_DIR + "/common/themes/portlet.jsp" %>' />
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

<aui:script position='<%= themeDisplay.isIsolated() ? "inline" : "auto" %>'>
	Liferay.Portlet.onLoad(
		{
			canEditTitle: <%= showConfigurationIcon %>,
			columnPos: <%= columnPos %>,
			isStatic: '<%= staticVar %>',
			namespacedId: 'p_p_id<%= HtmlUtil.escapeJS(renderResponseImpl.getNamespace()) %>',
			portletId: '<%= HtmlUtil.escapeJS(portletDisplay.getId()) %>',
			refreshURL: '<%= HtmlUtil.escapeJS(PortletURLUtil.getRefreshURL(request, themeDisplay)) %>'
		}
	);
</aui:script>

<c:if test="<%= renderPortletBoundary %>">
		</div>
	</div>
</c:if>

<%
if (themeDisplay.isStatePopUp()) {
	String refreshPortletId = null;

	if ((refreshPortletId = (String)SessionMessages.get(renderRequestImpl, portletId + SessionMessages.KEY_SUFFIX_REFRESH_PORTLET)) != null) {
		if (Validator.isNull(refreshPortletId) && (portletResourcePortlet != null)) {
			refreshPortletId = portletResourcePortlet.getPortletId();
		}

		Map<String, String> refreshPortletData = (Map<String, String>)SessionMessages.get(renderRequestImpl, portletId + SessionMessages.KEY_SUFFIX_REFRESH_PORTLET_DATA);
%>

		<aui:script position="inline" use="aui-base">
			if (window.parent) {
				var data = {
					portletAjaxable: <%= !((portletResourcePortlet != null && !portletResourcePortlet.isAjaxable()) || SessionMessages.contains(renderRequestImpl, portletId + SessionMessages.KEY_SUFFIX_PORTLET_NOT_AJAXABLE)) %>

					<c:if test="<%= (refreshPortletData != null) && !refreshPortletData.isEmpty() %>">

						<%
						for (Map.Entry<String, String> entry : refreshPortletData.entrySet()) {
						%>

							, '<%= entry.getKey() %>': <%= entry.getValue() %>

						<%
						}
						%>

					</c:if>

				};

				Liferay.Util.getOpener().Liferay.Portlet.refresh('#p_p_id_<%= HtmlUtil.escapeJS(refreshPortletId) %>_', data);
			}
		</aui:script>

<%
	}

	String closeRedirect = null;

	if ((closeRedirect = (String)SessionMessages.get(renderRequestImpl, portletId + SessionMessages.KEY_SUFFIX_CLOSE_REDIRECT)) != null) {
%>

		<aui:script use="aui-base">
			var dialog = Liferay.Util.getWindow();

			var hideDialogSignature = '<portlet:namespace />hideRefreshDialog|*';

			dialog.detach(hideDialogSignature);

			dialog.on(
				'<portlet:namespace />hideRefreshDialog|visibleChange',
				function(event) {
					if (!event.newVal && event.src !== 'hideLink') {
						var refreshWindow = dialog._refreshWindow || Liferay.Util.getTop();

						var topA = refreshWindow.AUI();

						topA.use(
							'aui-loading-mask-deprecated',
							function(A) {
								new A.LoadingMask(
									{
										target: A.getBody()
									}
								).show();
							}
						);

						refreshWindow.location.href = '<%= HtmlUtil.escapeJS(closeRedirect) %>';
					}
					else {
						dialog.detach(hideDialogSignature);
					}
				}
			);
		</aui:script>

<%
	}

	String closeRefreshPortletId = null;

	if ((closeRefreshPortletId = (String)SessionMessages.get(renderRequestImpl, portletId + SessionMessages.KEY_SUFFIX_CLOSE_REFRESH_PORTLET)) != null) {
		Map<String, String> refreshPortletData = (Map<String, String>)SessionMessages.get(renderRequestImpl, portletId + SessionMessages.KEY_SUFFIX_REFRESH_PORTLET_DATA);
%>

		<aui:script use="aui-base">
			var dialog = Liferay.Util.getWindow();

			var hideDialogSignature = '<portlet:namespace />hideRefreshDialog|*';

			dialog.detach(hideDialogSignature);

			dialog.on(
				'<portlet:namespace />hideRefreshDialog|visibleChange',
				function(event) {
					if (!event.newVal && event.src !== 'hideLink') {
						var refreshWindow = dialog._refreshWindow || Liferay.Util.getTop();

						if (window.parent) {
							var data = {
								portletAjaxable: <%= !((portletResourcePortlet != null && !portletResourcePortlet.isAjaxable()) || SessionMessages.contains(renderRequestImpl, portletId + SessionMessages.KEY_SUFFIX_PORTLET_NOT_AJAXABLE)) %>

								<c:if test="<%= (refreshPortletData != null) && !refreshPortletData.isEmpty() %>">

									<%
									for (Map.Entry<String, String> entry : refreshPortletData.entrySet()) {
									%>

										, '<%= entry.getKey() %>': <%= entry.getValue() %>

									<%
									}
									%>

								</c:if>

							};

							refreshWindow.Liferay.Portlet.refresh('#p_p_id_<%= closeRefreshPortletId %>_', data);
						}
					}
					else {
						dialog.detach(hideDialogSignature);
					}
				}
			);
		</aui:script>

<%
	}
}

themeDisplay.setScopeGroupId(previousScopeGroupId);
themeDisplay.setSiteGroupId(previousSiteGroupId);

if (showPortletCssIcon) {
	themeDisplay.setIncludePortletCssJs(true);
}

SessionMessages.clear(renderRequestImpl);
SessionErrors.clear(renderRequestImpl);

renderRequestImpl.cleanUp();
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal_web.docroot.html.portal.render_portlet_jsp");
%>