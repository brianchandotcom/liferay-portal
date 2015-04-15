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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
String redirectURL = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

long exportImportConfigurationId = ParamUtil.getLong(request, "exportImportConfigurationId");

boolean publishOnLayout = false;

if (exportImportConfigurationId <= 0) {
	exportImportConfigurationId = GetterUtil.getLong(request.getAttribute("exportImportConfigurationId"));

	publishOnLayout = true;
}

ExportImportConfiguration exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);

String cmd = Constants.EXPORT;
String submitLanguageKey = Constants.EXPORT;

if (exportImportConfiguration.getType() == ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL) {
	cmd = Constants.PUBLISH_TO_LIVE;
	submitLanguageKey = "publish-to-live";
}
else if (exportImportConfiguration.getType() == ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE) {
	cmd = Constants.PUBLISH_TO_REMOTE;
	submitLanguageKey = "publish-to-remote-live";
}

Map<String, Serializable> settingsMap = exportImportConfiguration.getSettingsMap();

Map<String, String[]> parameterMap = (Map<String, String[]>)settingsMap.get("parameterMap");
long[] layoutIds = GetterUtil.getLongValues(settingsMap.get("layoutIds"));
%>

<c:if test="<%= !publishOnLayout %>">
	<liferay-ui:header
		backURL="<%= backURL %>"
		title="<%= exportImportConfiguration.getName() %>"
	/>
</c:if>

<div id="confirmationSection">
	<span class="selected-labels" id="<portlet:namespace />exportImportConfigurationDescription">
		<liferay-ui:message key="<%= exportImportConfiguration.getDescription() %>" />
	</span>

	<aui:fieldset cssClass="options-group" label="user">
		<liferay-ui:user-display
			displayStyle="1"
			showUserDetails="<%= false %>"
			showUserName="<%= false %>"
			userId="<%= exportImportConfiguration.getUserId() %>"
		/>

		<liferay-ui:message key="<%= Time.getRelativeTimeDescription(exportImportConfiguration.getCreateDate(), locale, timeZone) %>" />
	</aui:fieldset>

	<portlet:actionURL var="confirmedActionURL">
		<portlet:param name="struts_action" value='<%= (cmd.equals(Constants.EXPORT) ? "/layouts_admin/edit_export_configuration" : "/layouts_admin/edit_publish_configuration") %>' />
		<portlet:param name="<%= Constants.CMD %>" value="<%= cmd %>" />
		<portlet:param name="redirect" value="<%= redirectURL %>" />
		<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
	</portlet:actionURL>

	<aui:form action='<%= confirmedActionURL.toString() + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="fm2">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
		<aui:input name="exportImportConfigurationId" type="hidden" value="<%= exportImportConfigurationId %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />

		<div class="export-dialog-tree">
			<ul class="lfr-tree list-unstyled">
				<li class="tree-item">
					<aui:fieldset cssClass="options-group" label="pages">
						<span class="selected-labels" id="<portlet:namespace />pagesSection">

							<%
							StringBundler sb = new StringBundler();

							long sourceGroupId = MapUtil.getLong(settingsMap, "sourceGroupId");

							long[] allLayoutIds = ExportImportHelperUtil.getAllLayoutIds(sourceGroupId);

							if (ArrayUtil.containsAll(layoutIds, allLayoutIds)) {
								sb.append(LanguageUtil.get(locale, "all-pages"));
							}
							else if (ArrayUtil.isNotEmpty(layoutIds)) {
								sb.append(LanguageUtil.get(locale, "selected-pages"));
							}
							else {
								sb.append(LanguageUtil.get(locale, "no-pages"));
							}

							if (MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.LAYOUT_SET_SETTINGS)) {
								sb.append(", ");
								sb.append(LanguageUtil.get(locale, "site-pages-settings"));
							}

							if (MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.THEME_REFERENCE)) {
								sb.append(", ");
								sb.append(LanguageUtil.get(locale, "theme-settings"));
							}

							if (MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.LOGO)) {
								sb.append(", ");
								sb.append(LanguageUtil.get(locale, "logo"));
							}
							%>

							<liferay-ui:message key="<%= sb.toString() %>" />
						</span>
					</aui:fieldset>

					<liferay-staging:content disableInputs="<%= true %>" parameterMap="<%= parameterMap %>" type="<%= cmd %>" />

					<aui:button-row>
						<aui:button type="submit" value="<%= LanguageUtil.get(request, submitLanguageKey) %>" />

						<aui:button href="<%= backURL %>" type="cancel" />
					</aui:button-row>
				</li>
			</ul>
		</div>
	</aui:form>
</div>

<aui:script use="liferay-export-import">
	new Liferay.ExportImport(
		{
			archivedSetupsNode: '#<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL %>',
			commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>',
			deletionsNode: '#<%= PortletDataHandlerKeys.DELETIONS %>',
			exportLAR: true,
			form: document.<portlet:namespace />fm2,
			incompleteProcessMessageNode: '#<portlet:namespace />incompleteProcessMessage',
			layoutSetSettingsNode: '#<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>',
			logoNode: '#<%= PortletDataHandlerKeys.LOGO %>',
			namespace: '<portlet:namespace />',
			rangeAllNode: '#rangeAll',
			rangeDateRangeNode: '#rangeDateRange',
			rangeLastNode: '#rangeLast',
			rangeLastPublishNode: '#rangeLastPublish',
			ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>',
			setupNode: '#<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>',
			themeReferenceNode: '#<%= PortletDataHandlerKeys.THEME_REFERENCE %>',
			userPreferencesNode: '#<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>'
		}
	);
</aui:script>