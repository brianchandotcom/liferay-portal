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

PortletURL publishTemplatesURL = renderResponse.createRenderURL();

publishTemplatesURL.setParameter("mvcRenderCommandName", "publishLayouts");
publishTemplatesURL.setParameter(Constants.CMD, Constants.PUBLISH);
publishTemplatesURL.setParameter("groupId", String.valueOf(stagingGroupId));
publishTemplatesURL.setParameter("layoutSetBranchId", String.valueOf(layoutSetBranchId));
publishTemplatesURL.setParameter("layoutSetBranchName", layoutSetBranchName);
publishTemplatesURL.setParameter("localPublishing", String.valueOf(localPublishing));
publishTemplatesURL.setParameter("privateLayout", String.valueOf(privateLayout));
publishTemplatesURL.setParameter("publishConfigurationButtons", "saved");
%>

<c:if test='<%= !publishConfigurationButtons.equals("template") %>'>
	<aui:nav-bar cssClass="collapse-basic-search navbar-collapse-absolute" markupView="lexicon">
		<aui:nav cssClass="navbar-nav" id="publishConfigurationButtons">
			<aui:nav-item cssClass='<%= publishConfigurationButtons.equals("custom") ? "hidden-xs" : StringPool.BLANK %>' data-value="custom" href="<%= customPublishURL.toString() %>" label="custom" selected='<%= publishConfigurationButtons.equals("custom") %>' />

			<aui:nav-item cssClass='<%= publishConfigurationButtons.equals("saved") ? "hidden-xs" : StringPool.BLANK %>' data-value="saved" href="<%= publishTemplatesURL.toString() %>" label="publish-templates" selected='<%= publishConfigurationButtons.equals("saved") %>' />

			<portlet:renderURL var="simplePublishRedirectURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="publishLayouts" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				<portlet:param name="quickPublish" value="<%= Boolean.TRUE.toString() %>" />
			</portlet:renderURL>

			<%
			UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();
			%>

			<portlet:renderURL var="simplePublishURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="publishLayoutsSimple" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>" />
				<portlet:param name="redirect" value="<%= simplePublishRedirectURL %>" />
				<portlet:param name="lastImportUserName" value="<%= user.getFullName() %>" />
				<portlet:param name="lastImportUserUuid" value="<%= String.valueOf(user.getUserUuid()) %>" />
				<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
				<portlet:param name="layoutSetBranchName" value="<%= layoutSetBranchName %>" />
				<portlet:param name="localPublishing" value="<%= String.valueOf(localPublishing) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				<portlet:param name="quickPublish" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="remoteAddress" value='<%= liveGroupTypeSettings.getProperty("remoteAddress") %>' />
				<portlet:param name="remotePort" value='<%= liveGroupTypeSettings.getProperty("remotePort") %>' />
				<portlet:param name="remotePathContext" value='<%= liveGroupTypeSettings.getProperty("remotePathContext") %>' />
				<portlet:param name="remoteGroupId" value='<%= liveGroupTypeSettings.getProperty("remoteGroupId") %>' />
				<portlet:param name="secureConnection" value='<%= liveGroupTypeSettings.getProperty("secureConnection") %>' />
				<portlet:param name="sourceGroupId" value="<%= String.valueOf(stagingGroupId) %>" />
				<portlet:param name="targetGroupId" value="<%= String.valueOf(liveGroupId) %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= simplePublishURL %>" label="switch-to-simple-publication" />
		</aui:nav>

		<c:if test='<%= publishConfigurationButtons.equals("saved") %>'>
			<aui:nav-bar-search>
				<liferay-portlet:renderURL varImpl="searchURL">
					<portlet:param name="mvcRenderCommandName" value="publishLayouts" />
					<portlet:param name="publishConfigurationButtons" value="saved" />
				</liferay-portlet:renderURL>

				<aui:form action="<%= searchURL.toString() %>" name="searchFm">
					<liferay-portlet:renderURLParams varImpl="searchURL" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

					<liferay-ui:input-search
						markupView="lexicon"
					/>
				</aui:form>
			</aui:nav-bar-search>
		</c:if>
	</aui:nav-bar>
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