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

<portlet:actionURL name="/analytics/edit_synced_sites" var="editSyncedSitesURL" />

<div class="sheet sheet-lg">
	<h2 class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<liferay-ui:message key="choose-sites-to-sync" />
		</span>
	</h2>

	<aui:form action="<%= editSyncedSitesURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<%
		LinkedHashMap groupParams = new LinkedHashMap<>();

		groupParams.put("active", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);

		List<Group> groups = GroupServiceUtil.search(themeDisplay.getCompanyId(), new long[] {PortalUtil.getClassNameId(Group.class)}, "", groupParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		AnalyticsConfiguration analyticsConfiguration = (AnalyticsConfiguration)request.getAttribute(AnalyticsSettingsWebKeys.ANALYTICS_CONFIGURATION);

		Set<String> syncedGroupIds = SetUtil.fromArray(analyticsConfiguration.syncedGroupIds());
		%>

		<liferay-ui:search-container
			curParam="inheritedSitesCur"
			headerNames="name"
			rowChecker="<%= new GroupIdChecker(renderResponse, syncedGroupIds) %>"
			total="<%= groups.size() %>"
		>
			<liferay-ui:search-container-results
				results="<%= groups %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.Group"
				escapedModel="<%= true %>"
				keyProperty="groupId"
				modelVar="group"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="site-name"
					value="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
				searchResultCssClass="show-quick-actions-on-hover table table-autofit"
			/>
		</liferay-ui:search-container>

		<h3 class="autofit-row">
			<span class="autofit-col autofit-col-expand">
				<liferay-ui:message key="site-reporting-grouping" />
			</span>
		</h3>

		<aui:input checked="<%= true %>" helpMessage="flat-site-reporting-grouping-help" label="separate-synced-sites-into-individual-site-reports-in-analytics-cloud" name="siteReportingGrouping" type="radio" value="flat" />

		<aui:input helpMessage="aggregate-site-reporting-grouping-help" label="group-all-synced-sites-into-one-site-report-in-analytics-cloud" name="siteReportingGrouping" type="radio" value="aggregate" />

		<aui:button-row>
			<aui:button type="submit" value="save-and-sync" />
		</aui:button-row>
	</aui:form>
</div>