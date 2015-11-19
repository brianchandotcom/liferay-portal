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
String redirect = ParamUtil.getString(request, "redirect");

long exportImportConfigurationId = GetterUtil.getLong(request.getAttribute("exportImportConfigurationId"));

ExportImportConfiguration exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);

String cmd = Constants.PUBLISH_TO_LIVE;
String publishActionKey = "publish-to-live";

if (exportImportConfiguration.getType() == ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE) {
	cmd = Constants.PUBLISH_TO_REMOTE;
	publishActionKey = "publish-to-remote-live";
}

long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");

GroupDisplayContextHelper groupDisplayContextHelper = new GroupDisplayContextHelper(request);
%>

<portlet:actionURL name='<%= cmd.equals(Constants.EXPORT) ? "editExportConfiguration" : "editPublishConfiguration" %>' var="confirmedActionURL">
	<portlet:param name="mvcRenderCommandName" value='<%= cmd.equals(Constants.EXPORT) ? "editExportConfiguration" : "editPublishConfiguration" %>' />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
	<portlet:param name="quickPublish" value="<%= Boolean.TRUE.toString() %>" />
</portlet:actionURL>

<aui:form action='<%= confirmedActionURL.toString() + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="fm2">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="exportImportConfigurationId" type="hidden" value="<%= exportImportConfigurationId %>" />

	<div class="export-dialog-tree">
		<ul class="lfr-tree list-unstyled">
			<portlet:renderURL var="advancedPublishURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="publishLayouts" />
				<portlet:param name="tabs1" value='<%= privateLayout ? "private-pages" : "public-pages" %>' />
				<portlet:param name="tabs2" value="new-publication-process" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupDisplayContextHelper.getGroupId()) %>" />
				<portlet:param name="selPlid" value="<%= String.valueOf(selPlid) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				<portlet:param name="quickPublish" value="<%= Boolean.FALSE.toString() %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				cssClass="label publish-mode-switch"
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
						LayoutSet selLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupDisplayContextHelper.getGroupId(), privateLayout);
						%>

						<liferay-util:buffer var="badgeHTML">
							<span class="badge badge-info"><%= (selLayoutSet.getPageCount() > 0) ? selLayoutSet.getPageCount() : LanguageUtil.get(request, "none") %></span>
						</liferay-util:buffer>

						<li class="tree-item">
							<liferay-ui:message arguments="<%= badgeHTML %>" key="pages-x" />
						</li>

						<%
						List<Portlet> dataSiteLevelPortlets = ExportImportHelperUtil.getDataSiteLevelPortlets(company.getCompanyId(), false);

						DateRange dateRange = ExportImportDateUtil.getDateRange(exportImportConfiguration);

						PortletDataContext portletDataContext = PortletDataContextFactoryUtil.createPreparePortletDataContext(company.getCompanyId(), groupDisplayContextHelper.getStagingGroupId(), dateRange.getStartDate(), dateRange.getEndDate());

						ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

						Set<String> portletDataHandlerClasses = new HashSet<String>();

						if (!dataSiteLevelPortlets.isEmpty()) {
							for (Portlet portlet : dataSiteLevelPortlets) {
								String portletDataHandlerClass = portlet.getPortletDataHandlerClass();

								if (portletDataHandlerClasses.contains(portletDataHandlerClass)) {
									continue;
								}

								portletDataHandlerClasses.add(portletDataHandlerClass);

								PortletDataHandler portletDataHandler = portlet.getPortletDataHandlerInstance();

								portletDataHandler.prepareManifestSummary(portletDataContext);

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

			<aui:button-row>
				<aui:button type="submit" value="<%= LanguageUtil.get(request, publishActionKey) %>" />
			</aui:button-row>
		</ul>
	</div>
</aui:form>