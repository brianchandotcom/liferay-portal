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

	<%
	GroupDisplayContext groupDisplayContext = new GroupDisplayContext(renderRequest, renderResponse);
	%>

	<clay:management-toolbar
		displayContext="<%= new GroupManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, groupDisplayContext) %>"
	/>

	<aui:form action="<%= editSyncedSitesURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:search-container
			id="selectGroups"
			searchContainer="<%= groupDisplayContext.getGroupSearch() %>"
			var="groupSearchContainer"
		>
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

		<aui:button-row>
			<aui:button type="submit" value="save-and-sync" />
		</aui:button-row>
	</aui:form>
</div>