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

long exportImportConfigurationId = GetterUtil.getLong(request.getAttribute("exportImportConfigurationId"));

ExportImportConfiguration exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);

String cmd = Constants.PUBLISH_TO_LIVE;
String submitLanguageKey = "publish-to-live";

if (exportImportConfiguration.getType() == ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE) {
	cmd = Constants.PUBLISH_TO_REMOTE;
	submitLanguageKey = "publish-to-remote-live";
}
%>

<portlet:actionURL var="confirmedActionURL">
	<portlet:param name="struts_action" value='<%= (cmd.equals(Constants.EXPORT) ? "/layouts_admin/edit_export_configuration" : "/layouts_admin/edit_publish_configuration") %>' />
	<portlet:param name="redirect" value="<%= redirectURL %>" />
	<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
	<portlet:param name="quickPublish" value="<%= Boolean.TRUE.toString() %>" />
</portlet:actionURL>

<aui:form action='<%= confirmedActionURL.toString() + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="fm2">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />
	<aui:input name="exportImportConfigurationId" type="hidden" value="<%= exportImportConfigurationId %>" />

	<div class="export-dialog-tree">
		<ul class="lfr-tree list-unstyled">
			<portlet:renderURL var="advancedPublishURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="struts_action" value="/staging_bar/publish_layouts" />
				<portlet:param name="tabs2" value="new-publication-process" />
				<portlet:param name="groupId" value="<%= String.valueOf(layoutsAdminDisplayContext.getGroupId()) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(layoutsAdminDisplayContext.isPrivateLayout()) %>" />
				<portlet:param name="quickPublish" value="<%= Boolean.FALSE.toString() %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				cssClass="label label-submit publish-mode-switch"
				iconCssClass="icon-cog"
				label="<%= true %>"
				message="switch-to-advanced-publication"
				method="post"
				url="<%= advancedPublishURL %>"
			/>

			<span class="alert alert-info">
				<liferay-ui:message key="this-process-is-going-to-publish-the-changes-made-since-the-last-publication" />
			</span>

			<aui:fieldset cssClass="options-group" label="changes-since-last-publication">
				<li class="options portlet-list-simple">
					<ul class="portlet-list">

						<%
						LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

						int pagesCount = selLayoutSet.getPageCount();
						%>

						<liferay-util:buffer var="badgeHTML">
							<span class="badge badge-info"><%= pagesCount > 0 ? pagesCount : LanguageUtil.get(request, "none") %></span>
						</liferay-util:buffer>

						<li class="tree-item">
							<liferay-ui:message arguments="<%= badgeHTML %>" key="pages-x" />
						</li>

						<%
						List<Portlet> dataSiteLevelPortlets = LayoutExporter.getDataSiteLevelPortlets(company.getCompanyId(), false);
						Set<String> portletDataHandlerClasses = new HashSet<String>();

						Date endDate = new Date();
						Date startDate = ExportImportDateUtil.getLastPublishDate(selLayoutSet);

						PortletDataContext portletDataContext = PortletDataContextFactoryUtil.createPreparePortletDataContext(company.getCompanyId(), layoutsAdminDisplayContext.getStagingGroupId(), startDate, endDate);

						ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

						if (!dataSiteLevelPortlets.isEmpty()) {
							for (Portlet portlet : dataSiteLevelPortlets) {
								String portletDataHandlerClass = portlet.getPortletDataHandlerClass();

								if (portletDataHandlerClasses.contains(portletDataHandlerClass)) {
									continue;
								}

								portletDataHandlerClasses.add(portletDataHandlerClass);

								String portletTitle = PortalUtil.getPortletTitle(portlet, application, locale);

								PortletDataHandler portletDataHandler = portlet.getPortletDataHandlerInstance();

								portletDataHandler.prepareManifestSummary(portletDataContext);

								long exportModelCount = portletDataHandler.getExportModelCount(manifestSummary);

								long modelDeletionCount = manifestSummary.getModelDeletionCount(portletDataHandler.getDeletionSystemEventStagedModelTypes());

								boolean displayCounts = (exportModelCount != 0) || (modelDeletionCount != 0);

								UnicodeProperties liveGroupTypeSettings = layoutsAdminDisplayContext.getLiveGroup().getTypeSettingsProperties();

								displayCounts = displayCounts && GetterUtil.getBoolean(liveGroupTypeSettings.getProperty(StagingUtil.getStagedPortletId(portlet.getRootPortletId())), portletDataHandler.isPublishToLiveByDefault());

								if (displayCounts) {
								%>

									<liferay-util:buffer var="badgeHTML">
										<span class="badge badge-info"><%= exportModelCount > 0 ? exportModelCount : StringPool.BLANK %></span>

										<span class="badge badge-warning deletions"><%= modelDeletionCount > 0 ? (modelDeletionCount + StringPool.SPACE + LanguageUtil.get(request, "deletions")) : StringPool.BLANK %></span>
									</liferay-util:buffer>

									<li class="tree-item">
										<liferay-ui:message key="<%= portletTitle + StringPool.SPACE + badgeHTML %>" />
									</li>

								<%
								}
							}
						}
						%>

					</ul>
				</li>
			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" value="<%= LanguageUtil.get(request, submitLanguageKey) %>" />

				<aui:button href="<%= backURL %>" type="cancel" />
			</aui:button-row>
		</ul>
	</div>
</aui:form>