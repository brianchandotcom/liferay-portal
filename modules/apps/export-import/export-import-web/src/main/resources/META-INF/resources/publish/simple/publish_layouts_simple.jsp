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
String cmd = ParamUtil.getString(request, Constants.CMD, Constants.PUBLISH_TO_LIVE);

long exportImportConfigurationId = GetterUtil.getLong(request.getAttribute("exportImportConfigurationId"), ParamUtil.getLong(request, "exportImportConfigurationId"));

ExportImportConfiguration exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);

long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

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

<aui:nav-bar cssClass="navbar-collapse-absolute">
	<aui:nav cssClass="navbar-nav" id="publishConfigurationButtons">
		<portlet:renderURL var="advancedPublishURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="publishLayouts" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= cmd %>" />
			<portlet:param name="tabs1" value='<%= privateLayout ? "private-pages" : "public-pages" %>' />
			<portlet:param name="groupId" value="<%= String.valueOf(groupDisplayContextHelper.getGroupId()) %>" />
			<portlet:param name="layoutSetBranchId" value='<%= MapUtil.getString(parameterMap, "layoutSetBranchId") %>' />
			<portlet:param name="selPlid" value="<%= String.valueOf(selPlid) %>" />
			<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
		</portlet:renderURL>

		<aui:nav-item href="<%= advancedPublishURL %>" iconCssClass="icon-cog" label="switch-to-advanced-publication" selected="<%= false %>" />
	</aui:nav>
</aui:nav-bar>

<portlet:actionURL name="editPublishConfiguration" var="confirmedActionURL">
	<portlet:param name="mvcRenderCommandName" value="editPublishConfigurationSimple" />
	<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
	<portlet:param name="quickPublish" value="<%= Boolean.TRUE.toString() %>" />
</portlet:actionURL>

<aui:form action='<%= confirmedActionURL.toString() + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="fm2">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="exportImportConfigurationId" type="hidden" value="<%= exportImportConfigurationId %>" />

	<%@ include file="/publish/error/error_auth_exception.jspf" %>

	<%@ include file="/publish/error/error_remote_export_exception.jspf" %>

	<%@ include file="/publish/error/error_remote_options_exception.jspf" %>

	<div class="export-dialog-tree">

		<%
		String taskExecutorClassName = localPublishing ? BackgroundTaskExecutorNames.LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR : BackgroundTaskExecutorNames.LAYOUT_REMOTE_STAGING_BACKGROUND_TASK_EXECUTOR;

		int incompleteBackgroundTaskCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(groupDisplayContextHelper.getStagingGroupId(), taskExecutorClassName, false);

		incompleteBackgroundTaskCount += BackgroundTaskManagerUtil.getBackgroundTasksCount(groupDisplayContextHelper.getLiveGroupId(), taskExecutorClassName, false);
		%>

		<div class="container-fluid-1280">
			<div class="<%= (incompleteBackgroundTaskCount == 0) ? "hide" : "in-progress" %>" id="<portlet:namespace />incompleteProcessMessage">
				<liferay-util:include page="/incomplete_processes_message.jsp" servletContext="<%= application %>">
					<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
				</liferay-util:include>
			</div>

			<ul class="lfr-tree list-unstyled">
				<aui:fieldset-group markupView="lexicon">
					<aui:fieldset>
						<aui:input maxlength='<%= ModelHintsUtil.getMaxLength(ExportImportConfiguration.class.getName(), "name") %>' name="name" placeholder="process-name-placeholder" />
					</aui:fieldset>

					<aui:fieldset collapsible="<%= true %>" cssClass="options-group" label="changes-since-last-publication" markupView="lexicon">
						<li class="options portlet-list-simple">
							<ul class="portlet-list">

								<%
								int layoutsCount = 0;

								long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");

								if (layoutSetBranchId > 0) {
									List<LayoutRevision> approvedLayoutRevisions = LayoutRevisionLocalServiceUtil.getLayoutRevisions(layoutSetBranchId, true, WorkflowConstants.STATUS_APPROVED);
									List<LayoutRevision> pendingLayoutRevisions = LayoutRevisionLocalServiceUtil.getLayoutRevisions(layoutSetBranchId, true, WorkflowConstants.STATUS_PENDING);

									layoutsCount = approvedLayoutRevisions.size() + pendingLayoutRevisions.size();
								}
								else {
									LayoutSet selLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupDisplayContextHelper.getGroupId(), privateLayout);

									layoutsCount = selLayoutSet.getPageCount();
								}
								%>

								<liferay-util:buffer
									var="badgeHTML"
								>
									<span class="badge badge-info">
										<c:choose>
											<c:when test="<%= layoutsCount == 0 %>">
												<liferay-ui:message key="none" />
											</c:when>
											<c:otherwise>
												<liferay-ui:message arguments='<%= new String[] {"<strong>" + String.valueOf(layoutsCount) + "</strong>", String.valueOf(layoutsCount)} %>' key="x-of-x" />
											</c:otherwise>
										</c:choose>
									</span>
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

										PortletDataContext portletDataContext = PortletDataContextFactoryUtil.createPreparePortletDataContext(company.getCompanyId(), groupDisplayContextHelper.getStagingGroupId(), ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE, dateRange.getStartDate(), dateRange.getEndDate());

										portletDataHandler.prepareManifestSummary(portletDataContext);

										ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

										long exportModelCount = portletDataHandler.getExportModelCount(manifestSummary);
										long modelDeletionCount = manifestSummary.getModelDeletionCount(portletDataHandler.getDeletionSystemEventStagedModelTypes());

										UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

										if (((exportModelCount > 0) || (modelDeletionCount > 0)) && GetterUtil.getBoolean(liveGroupTypeSettings.getProperty(StagingUtil.getStagedPortletId(portlet.getRootPortletId())), portletDataHandler.isPublishToLiveByDefault())) {
								%>

											<liferay-util:buffer
												var="badgeHTML"
											>
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
			</ul>
		</div>
	</div>

	<aui:button-row>
		<aui:button type="submit" value="<%= LanguageUtil.get(request, publishMessageKey) %>" />
	</aui:button-row>
</aui:form>