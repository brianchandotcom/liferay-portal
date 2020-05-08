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

<%@ include file="/init.jsp" %>

<liferay-staging:defineObjects />

<%
String cmd = ParamUtil.getString(request, Constants.CMD);

if (Validator.isNull(cmd)) {
	cmd = ParamUtil.getString(request, "originalCmd", Constants.PUBLISH_TO_LIVE);
}

String publishConfigurationButtons = ParamUtil.getString(request, "publishConfigurationButtons", "custom");

long exportImportConfigurationId = 0;

ExportImportConfiguration exportImportConfiguration = null;

Map<String, Serializable> exportImportConfigurationSettingsMap = Collections.emptyMap();

Map<String, String[]> parameterMap = Collections.emptyMap();

if (SessionMessages.contains(liferayPortletRequest, portletDisplay.getId() + "exportImportConfigurationId")) {
	exportImportConfigurationId = (Long)SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "exportImportConfigurationId");

	if (exportImportConfigurationId > 0) {
		exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);
	}

	exportImportConfigurationSettingsMap = (Map<String, Serializable>)SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "settingsMap");

	parameterMap = (Map<String, String[]>)exportImportConfigurationSettingsMap.get("parameterMap");
}
else {
	exportImportConfigurationId = ParamUtil.getLong(request, "exportImportConfigurationId");

	if (exportImportConfigurationId > 0) {
		exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);

		exportImportConfigurationSettingsMap = exportImportConfiguration.getSettingsMap();

		parameterMap = (Map<String, String[]>)exportImportConfigurationSettingsMap.get("parameterMap");
	}
}

long layoutSetBranchId = MapUtil.getLong(parameterMap, "layoutSetBranchId", ParamUtil.getLong(request, "layoutSetBranchId"));
String layoutSetBranchName = MapUtil.getString(parameterMap, "layoutSetBranchName", ParamUtil.getString(request, "layoutSetBranchName"));

long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

boolean configuredPublish = (exportImportConfiguration == null) ? false : true;

PortletURL customPublishURL = renderResponse.createRenderURL();

customPublishURL.setParameter("mvcRenderCommandName", "publishLayouts");
customPublishURL.setParameter(Constants.CMD, cmd);
customPublishURL.setParameter("tabs1", privateLayout ? "private-pages" : "public-pages");
customPublishURL.setParameter("groupId", String.valueOf(stagingGroupId));
customPublishURL.setParameter("layoutSetBranchId", String.valueOf(layoutSetBranchId));
customPublishURL.setParameter("privateLayout", String.valueOf(privateLayout));
customPublishURL.setParameter("publishConfigurationButtons", "custom");
customPublishURL.setParameter("selPlid", String.valueOf(selPlid));

boolean localPublishing = true;

if ((liveGroup.isStaged() && liveGroup.isStagedRemotely()) || cmd.equals(Constants.PUBLISH_TO_REMOTE)) {
	localPublishing = false;
}

UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

PortletURL publishTemplatesURL = renderResponse.createRenderURL();

publishTemplatesURL.setParameter("mvcRenderCommandName", "publishLayouts");
publishTemplatesURL.setParameter(Constants.CMD, Constants.PUBLISH);
publishTemplatesURL.setParameter("groupId", String.valueOf(stagingGroupId));
publishTemplatesURL.setParameter("layoutSetBranchId", String.valueOf(layoutSetBranchId));
publishTemplatesURL.setParameter("layoutSetBranchName", layoutSetBranchName);
publishTemplatesURL.setParameter("localPublishing", String.valueOf(localPublishing));
publishTemplatesURL.setParameter("privateLayout", String.valueOf(privateLayout));
publishTemplatesURL.setParameter("publishConfigurationButtons", "saved");

PortletURL simplePublishRedirectURL = renderResponse.createRenderURL();

simplePublishRedirectURL.setParameter("mvcRenderCommandName", "publishLayouts");
simplePublishRedirectURL.setParameter("groupId", String.valueOf(groupId));
simplePublishRedirectURL.setParameter("privateLayout", String.valueOf(privateLayout));
simplePublishRedirectURL.setParameter("quickPublish", Boolean.TRUE.toString());

PortletURL simplePublishURL = renderResponse.createRenderURL();

simplePublishURL.setParameter("mvcRenderCommandName", "publishLayoutsSimple");
simplePublishURL.setParameter(Constants.CMD, "localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE");
simplePublishURL.setParameter("redirect", simplePublishRedirectURL.toString());
simplePublishURL.setParameter("lastImportUserName", user.getFullName());
simplePublishURL.setParameter("lastImportUserUuid", String.valueOf(user.getUserUuid()));
simplePublishURL.setParameter("layoutSetBranchId", String.valueOf(layoutSetBranchId));
simplePublishURL.setParameter("layoutSetBranchName", layoutSetBranchName);
simplePublishURL.setParameter("localPublishing", String.valueOf(localPublishing));
simplePublishURL.setParameter("privateLayout", String.valueOf(privateLayout));
simplePublishURL.setParameter("quickPublish", Boolean.TRUE.toString());
simplePublishURL.setParameter("remoteAddress", liveGroupTypeSettings.getProperty("remoteAddress"));
simplePublishURL.setParameter("remotePort", liveGroupTypeSettings.getProperty("remotePort"));
simplePublishURL.setParameter("remotePathContext", liveGroupTypeSettings.getProperty("remotePathContext"));
simplePublishURL.setParameter("remoteGroupId", liveGroupTypeSettings.getProperty("remoteGroupId"));
simplePublishURL.setParameter("secureConnection", liveGroupTypeSettings.getProperty("secureConnection"));
simplePublishURL.setParameter("sourceGroupId", String.valueOf(stagingGroupId));
simplePublishURL.setParameter("targetGroupId", String.valueOf(liveGroupId));
%>

<c:if test='<%= !publishConfigurationButtons.equals("template") %>'>
	<clay:container
		className="publish-navbar"
	>
		<div class="autofit-row autofit-row-center">
			<div class="autofit-col autofit-col-expand">
				<clay:navigation-bar
					navigationItems='<%=
						new JSPNavigationItemList(pageContext) {
							{
								add(
									navigationItem -> {
										navigationItem.setActive(publishConfigurationButtons.equals("custom"));
										navigationItem.setHref(customPublishURL.toString());
										navigationItem.setLabel(LanguageUtil.get(request, "custom"));
									}
								);
								add(
									navigationItem -> {
										navigationItem.setActive(publishConfigurationButtons.equals("saved"));
										navigationItem.setHref(publishTemplatesURL.toString());
										navigationItem.setLabel(LanguageUtil.get(request, "publish-templates"));
									}
								);
							}
						}
					%>'
				/>
			</div>

			<div class="autofit-col">
				<clay:link
					buttonStyle="link"
					elementClasses="btn-sm"
					href="<%= simplePublishURL.toString() %>"
					label='<%= LanguageUtil.get(request, "switch-to-simple-publication") %>'
				/>
			</div>
		</div>
	</clay:container>
</c:if>

<c:choose>
	<c:when test='<%= publishConfigurationButtons.equals("saved") %>'>
		<liferay-util:include page="/publish/publish_templates/view.jsp" servletContext="<%= application %>">
			<liferay-util:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
			<liferay-util:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
			<liferay-util:param name="layoutSetBranchName" value="<%= layoutSetBranchName %>" />
			<liferay-util:param name="localPublishing" value="<%= String.valueOf(localPublishing) %>" />
			<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
		</liferay-util:include>
	</c:when>
	<c:when test="<%= configuredPublish %>">
		<liferay-util:include page="/publish/publish_layouts.jsp" servletContext="<%= application %>">
			<liferay-util:param name="<%= Constants.CMD %>" value="<%= cmd %>" />
			<liferay-util:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfigurationId) %>" />
		</liferay-util:include>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/publish/publish_layouts.jsp" servletContext="<%= application %>">
			<liferay-util:param name="<%= Constants.CMD %>" value="<%= cmd %>" />
			<liferay-util:param name="tabs1" value='<%= privateLayout ? "private-pages" : "public-pages" %>' />
			<liferay-util:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
			<liferay-util:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
			<liferay-util:param name="localPublishing" value="<%= String.valueOf(localPublishing) %>" />
			<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
			<liferay-util:param name="selPlid" value="<%= String.valueOf(selPlid) %>" />
		</liferay-util:include>
	</c:otherwise>
</c:choose>