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

<%
String cmd = ParamUtil.getString(request, Constants.CMD, Constants.PUBLISH_TO_LIVE);

long exportImportConfigurationId = GetterUtil.getLong(request.getAttribute("exportImportConfigurationId"));

ExportImportConfiguration exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);

long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");

boolean localPublishing = true;
String publishMessageKey = "publish-to-live";

if (exportImportConfiguration.getType() == ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE) {
	cmd = Constants.PUBLISH_TO_REMOTE;

	localPublishing = false;
	publishMessageKey = "publish-to-remote-live";
}

GroupDisplayContextHelper groupDisplayContextHelper = new GroupDisplayContextHelper(request);

Map<String, Serializable> settingsMap = exportImportConfiguration.getSettingsMap();

Map<String, String[]> parameterMap = (Map<String, String[]>)settingsMap.get("parameterMap");
%>

<div class="container-fluid-1280">
	<aui:nav-bar>
		<aui:nav cssClass="navbar-nav" id="publishConfigurationButtons">
			<portlet:renderURL var="advancedPublishURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="publishLayouts" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= cmd %>" />
				<portlet:param name="tabs1" value='<%= privateLayout ? "private-pages" : "public-pages" %>' />
				<portlet:param name="groupId" value="<%= String.valueOf(groupDisplayContextHelper.getGroupId()) %>" />
				<portlet:param name="layoutSetBranchId" value='<%= MapUtil.getString(parameterMap, "layoutSetBranchId") %>' />
				<portlet:param name="selPlid" value="<%= String.valueOf(selPlid) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				<portlet:param name="quickPublish" value="<%= Boolean.FALSE.toString() %>" />
			</portlet:renderURL>

			<aui:nav-item
				href="<%= advancedPublishURL %>"
				iconCssClass="icon-cog"
				label="switch-to-advanced-publication"
			/>
		</aui:nav>
	</aui:nav-bar>

	<portlet:actionURL name="editPublishConfiguration" var="confirmedActionURL">
		<portlet:param name="mvcRenderCommandName" value="editPublishConfiguration" />
		<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
		<portlet:param name="quickPublish" value="<%= Boolean.TRUE.toString() %>" />
	</portlet:actionURL>

	<aui:form action='<%= confirmedActionURL.toString() + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="fm2">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="exportImportConfigurationId" type="hidden" value="<%= exportImportConfigurationId %>" />

		<div class="export-dialog-tree">

			<%
			String taskExecutorClassName = localPublishing ? BackgroundTaskExecutorNames.LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR : BackgroundTaskExecutorNames.LAYOUT_REMOTE_STAGING_BACKGROUND_TASK_EXECUTOR;

			int incompleteBackgroundTaskCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(groupDisplayContextHelper.getStagingGroupId(), taskExecutorClassName, false);

			incompleteBackgroundTaskCount += BackgroundTaskManagerUtil.getBackgroundTasksCount(groupDisplayContextHelper.getLiveGroupId(), taskExecutorClassName, false);
			%>

			<div class="<%= (incompleteBackgroundTaskCount == 0) ? "hide" : "in-progress" %>" id="<portlet:namespace />incompleteProcessMessage">
				<liferay-util:include page="/incomplete_processes_message.jsp" servletContext="<%= application %>">
					<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
				</liferay-util:include>
			</div>

			<ul class="lfr-tree list-unstyled">
				<aui:fieldset-group markupView="lexicon">
					<aui:fieldset>
						<aui:input name="name" placeholder="process-name-placeholder" />
					</aui:fieldset>

					<aui:fieldset collapsible="<%= true %>" cssClass="options-group" label="changes-since-last-publication" markupView="lexicon">
						<li class="options portlet-list-simple">
							<ul class="portlet-list">

								<%
								LayoutSet selLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupDisplayContextHelper.getGroupId(), privateLayout);
								%>

								<liferay-util:buffer var="badgeHTML">
									<span class="badge badge-info"><%= (selLayoutSet.getPageCount() > 0) ? selLayoutSet.getPageCount() : LanguageUtil.get(request, "none") %></span>
								</liferay-util:buffer>

								<li class="tree-item">
									<liferay-ui:message arguments="<%= badgeHTML %>" key="pages-x" />
								</li>

								<%
								Set<String> portletDataHandlerClassNames = new HashSet<String>();

								List<Portlet> dataSiteLevelPortlets = ExportImportHelperUtil.getDataSiteLevelPortlets(company.getCompanyId(), false);

								if (!dataSiteLevelPortlets.isEmpty()) {
									for (Portlet portlet : dataSiteLevelPortlets) {
										PortletDataHandler portletDataHandler = portlet.getPortletDataHandlerInstance();

										Class<?> portletDataHandlerClass = portletDataHandler.getClass();

										String portletDataHandlerClassName = portletDataHandlerClass.getName();

										if (portletDataHandlerClassNames.contains(portletDataHandlerClassName)) {
											continue;
										}

										portletDataHandlerClassNames.add(portletDataHandlerClassName);

										settingsMap.put("portletId", portlet.getRootPortletId());

										DateRange dateRange = ExportImportDateUtil.getDateRange(exportImportConfiguration);

										PortletDataContext portletDataContext = PortletDataContextFactoryUtil.createPreparePortletDataContext(company.getCompanyId(), groupDisplayContextHelper.getStagingGroupId(), dateRange.getStartDate(), dateRange.getEndDate());

										portletDataHandler.prepareManifestSummary(portletDataContext);

										ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

										long exportModelCount = portletDataHandler.getExportModelCount(manifestSummary);
										long modelDeletionCount = manifestSummary.getModelDeletionCount(portletDataHandler.getDeletionSystemEventStagedModelTypes());

										Group liveGroup = groupDisplayContextHelper.getLiveGroup();

										UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

										if (((exportModelCount > 0) || (modelDeletionCount > 0)) && GetterUtil.getBoolean(liveGroupTypeSettings.getProperty(StagingUtil.getStagedPortletId(portlet.getRootPortletId())), portletDataHandler.isPublishToLiveByDefault())) {
								%>

											<liferay-util:buffer var="badgeHTML">
												<span class="badge badge-info"><%= (exportModelCount > 0) ? exportModelCount : StringPool.BLANK %></span>

												<span class="badge badge-warning deletions"><%= (modelDeletionCount > 0) ? (modelDeletionCount + StringPool.SPACE + LanguageUtil.get(request, "deletions")) : StringPool.BLANK %></span>
											</liferay-util:buffer>

											<li class="tree-item">
												<liferay-ui:message key="<%= PortalUtil.getPortletTitle(portlet, application, locale) + StringPool.SPACE + badgeHTML %>" />
											</li>

								<%
										}
									}
								}
								%>

							</ul>
						</li>
					</aui:fieldset>
				</aui:fieldset-group>

				<span class="publish-simple-help-text">
					<liferay-ui:message key="simple-publication-help" />
				</span>

				<aui:button-row>
					<aui:button cssClass="btn-lg" type="submit" value="<%= LanguageUtil.get(request, publishMessageKey) %>" />
				</aui:button-row>
			</ul>
		</div>
	</aui:form>
</div>