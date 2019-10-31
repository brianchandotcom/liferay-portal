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
String[] installedPatches = PatcherUtil.getInstalledPatches();

long uptimeDiff = System.currentTimeMillis() - PortalUtil.getUptime().getTime();
long days = uptimeDiff / Time.DAY;
long hours = (uptimeDiff / Time.HOUR) % 24;
long minutes = (uptimeDiff / Time.MINUTE) % 60;
long seconds = (uptimeDiff / Time.SECOND) % 60;

Runtime runtime = Runtime.getRuntime();

long totalMemory = runtime.totalMemory();
long usedMemory = totalMemory - runtime.freeMemory();
%>

<liferay-ui:panel-container
	extended="<%= true %>"
	id="adminServerAdministrationActionsPanelContainer"
	persistState="<%= true %>"
>
	<div class="panel panel-default server-admin-tabs" id="adminServerInformationPanel">
		<div class="panel-body">
			<div class="alert alert-info">
				<strong><liferay-ui:message key="info" /></strong>: <%= ReleaseInfo.getReleaseInfo() %>
				<c:if test="<%= (installedPatches != null) && (installedPatches.length > 0) %>">
					<strong><liferay-ui:message key="patch" /></strong>: <%= StringUtil.merge(installedPatches, StringPool.COMMA_AND_SPACE) %>
				</c:if>

				<strong><liferay-ui:message key="uptime" /></strong>:

				<c:if test="<%= days > 0 %>">
					<%= days %> <%= LanguageUtil.get(request, ((days > 1) ? "days" : "day")) %>,
				</c:if>

				<%
				NumberFormat timeNumberFormat = NumberFormat.getInstance();

				timeNumberFormat.setMaximumIntegerDigits(2);
				timeNumberFormat.setMinimumIntegerDigits(2);
				%>

				<%= timeNumberFormat.format(hours) %>:<%= timeNumberFormat.format(minutes) %>:<%= timeNumberFormat.format(seconds) %>
			</div>

			<div class="meter-wrapper text-center">
				<portlet:resourceURL id="/server_admin/view_chart" var="totalMemoryChartURL">
					<portlet:param name="type" value="total" />
					<portlet:param name="totalMemory" value="<%= String.valueOf(totalMemory) %>" />
					<portlet:param name="usedMemory" value="<%= String.valueOf(usedMemory) %>" />
				</portlet:resourceURL>

				<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="memory-used-vs-total-memory" />" src="<%= totalMemoryChartURL %>" />

				<portlet:resourceURL id="/server_admin/view_chart" var="maxMemoryChartURL">
					<portlet:param name="type" value="max" />
					<portlet:param name="maxMemory" value="<%= String.valueOf(runtime.maxMemory()) %>" />
					<portlet:param name="usedMemory" value="<%= String.valueOf(usedMemory) %>" />
				</portlet:resourceURL>

				<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="memory-used-vs-max-memory" />" src="<%= maxMemoryChartURL %>" />
			</div>

			<br />

			<%
			NumberFormat basicNumberFormat = NumberFormat.getInstance(locale);
			%>

			<table class="lfr-table memory-status-table">
				<tr>
					<td>
						<h4 class="float-right"><liferay-ui:message key="used-memory" /></h4>
					</td>
					<td>
						<span class="text-muted"><%= basicNumberFormat.format(usedMemory) %> <liferay-ui:message key="bytes" /></span>
					</td>
				</tr>
				<tr>
					<td>
						<h4 class="float-right"><liferay-ui:message key="total-memory" /></h4>
					</td>
					<td>
						<span class="text-muted"><%= basicNumberFormat.format(runtime.totalMemory()) %> <liferay-ui:message key="bytes" /></span>
					</td>
				</tr>
				<tr>
					<td>
						<h4 class="float-right"><liferay-ui:message key="maximum-memory" /></h4>
					</td>
					<td>
						<span class="text-muted"><%= basicNumberFormat.format(runtime.maxMemory()) %> <liferay-ui:message key="bytes" /></span>
					</td>
				</tr>
			</table>

			<br />
		</div>
	</div>

	<liferay-ui:panel
		collapsible="<%= true %>"
		cssClass="server-admin-actions-panel"
		extended="<%= true %>"
		id="adminServerAdministrationSystemActionsPanel"
		markupView="lexicon"
		persistState="<%= true %>"
		title="system-actions"
	>
		<ul class="list-group system-action-group">
			<li class="clearfix list-group-item">
				<div class="float-left">
					<h5><liferay-ui:message key="run-the-garbage-collector-to-free-up-memory" /></h5>
				</div>

				<div class="float-right">
					<aui:button cssClass="save-server-button" data-cmd="gc" value="execute" />
				</div>
			</li>
			<li class="clearfix list-group-item">
				<div class="float-left">
					<h5><liferay-ui:message key="generate-thread-dump" /></h5>
				</div>

				<div class="float-right">
					<aui:button cssClass="save-server-button" data-cmd="threadDump" value="execute" />
				</div>
			</li>
		</ul>
	</liferay-ui:panel>

	<liferay-ui:panel
		collapsible="<%= true %>"
		cssClass="server-admin-actions-panel"
		extended="<%= true %>"
		id="adminServerAdministrationCacheActionsPanel"
		markupView="lexicon"
		persistState="<%= true %>"
		title="cache-actions"
	>
		<ul class="list-group system-action-group">
			<li class="clearfix list-group-item">
				<div class="float-left">
					<h5><liferay-ui:message key="clear-content-cached-by-this-vm" /></h5>
				</div>

				<div class="float-right">
					<aui:button cssClass="save-server-button" data-cmd="cacheSingle" value="execute" />
				</div>
			</li>
			<li class="clearfix list-group-item">
				<div class="float-left">
					<h5><liferay-ui:message key="clear-content-cached-across-the-cluster" /></h5>
				</div>

				<div class="float-right">
					<aui:button cssClass="save-server-button" data-cmd="cacheMulti" value="execute" />
				</div>
			</li>
			<li class="clearfix list-group-item">
				<div class="float-left">
					<h5><liferay-ui:message key="clear-the-database-cache" /></h5>
				</div>

				<div class="float-right">
					<aui:button cssClass="save-server-button" data-cmd="cacheDb" value="execute" />
				</div>
			</li>
			<li class="clearfix list-group-item">
				<div class="float-left">
					<h5><liferay-ui:message key="clear-the-direct-servlet-cache" /></h5>
				</div>

				<div class="float-right">
					<aui:button cssClass="save-server-button" data-cmd="cacheServlet" value="execute" />
				</div>
			</li>
		</ul>
	</liferay-ui:panel>

	<liferay-ui:panel
		collapsible="<%= true %>"
		cssClass="server-admin-actions-panel"
		extended="<%= true %>"
		id="adminServerAdministrationVerificationActionsPanel"
		markupView="lexicon"
		persistState="<%= true %>"
		title="verification-actions"
	>
		<ul class="list-group system-action-group">
			<li class="clearfix list-group-item">
				<div class="float-left">
					<h5><liferay-ui:message key="verify-database-tables-of-all-plugins" /></h5>
				</div>

				<div class="float-right">
					<aui:button cssClass="save-server-button" data-cmd="verifyPluginTables" value="execute" />
				</div>
			</li>
			<li class="clearfix list-group-item">
				<div class="float-left">
					<h5><liferay-ui:message key="verify-membership-policies" /></h5>
				</div>

				<div class="float-right">
					<aui:button cssClass="save-server-button" data-cmd="verifyMembershipPolicies" value="execute" />
				</div>
			</li>
		</ul>
	</liferay-ui:panel>

	<liferay-ui:panel
		collapsible="<%= true %>"
		cssClass="server-admin-actions-panel"
		extended="<%= true %>"
		id="adminServerAdministrationCleanUpActionsPanel"
		markupView="lexicon"
		persistState="<%= true %>"
		title="clean-up-actions"
	>
		<ul class="list-group system-action-group">
			<li class="clearfix list-group-item">
				<div class="float-left">
					<h5><liferay-ui:message key="reset-preview-and-thumbnail-files-for-documents-and-media" /></h5>
				</div>

				<div class="float-right">
					<aui:button cssClass="save-server-button" data-cmd="dlPreviews" value="execute" />
				</div>
			</li>
			<li class="clearfix list-group-item">
				<div class="float-left">
					<h5><liferay-ui:message key="clean-up-permissions" /> <liferay-ui:icon-help message="clean-up-permissions-help" /></h5>
				</div>

				<div class="float-right">
					<aui:button cssClass="save-server-button" data-cmd="cleanUpAddToPagePermissions" value="execute" />
				</div>
			</li>
			<li class="clearfix list-group-item">
				<div class="float-left">
					<h5><liferay-ui:message key="clean-up-orphaned-page-revision-portlet-preferences" /> <liferay-ui:icon-help message="clean-up-orphaned-page-revision-portlet-preferences-help" /></h5>
				</div>

				<div class="float-right">
					<aui:button cssClass="save-server-button" data-cmd="cleanUpLayoutRevisionPortletPreferences" value="execute" />
				</div>
			</li>
			<li class="clearfix list-group-item">
				<div class="float-left">
					<h5><liferay-ui:message key="clean-up-orphaned-theme-portlet-preferences" /> <liferay-ui:icon-help message="clean-up-orphaned-theme-portlet-preferences-help" /></h5>
				</div>

				<div class="float-right">
					<aui:button cssClass="save-server-button" data-cmd="cleanUpOrphanedPortletPreferences" value="execute" />
				</div>
			</li>
		</ul>
	</liferay-ui:panel>
</liferay-ui:panel-container>