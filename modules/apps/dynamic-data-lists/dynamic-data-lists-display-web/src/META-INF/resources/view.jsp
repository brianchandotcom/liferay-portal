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
String redirect = ParamUtil.getString(request, "redirect", currentURL);

DDLRecordSet recordSet = ddlDisplayContext.getRecordSet();
%>

<c:choose>
	<c:when test="<%= (recordSet == null) %>">
		<div class="alert alert-info">
			<liferay-ui:message key="select-an-existing-form-or-add-a-form-to-be-displayed-in-this-application" />
		</div>
	</c:when>
	<c:otherwise>
		<liferay-ui:search-container
			emptyResultsMessage="no-forms-entries-were-found"
			iteratorURL="<%= renderResponse.createRenderURL() %>"
			total="<%= DDLRecordLocalServiceUtil.getRecordsCount(ddlDisplayContext.getRecordSetId(), WorkflowConstants.STATUS_APPROVED) %>"
		>

			<liferay-ui:search-container-results
				results="<%= DDLRecordLocalServiceUtil.getRecords(ddlDisplayContext.getRecordSetId(), WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
			/>

			<liferay-ui:ddm-template-renderer
				className="<%= DDLRecord.class.getName() %>"
				contextObjects="<%= ddlDisplayContext.getContextObjects(searchContainer) %>"
				displayStyle="<%= ddlDisplayContext.getDisplayStyle() %>"
				displayStyleGroupId="<%= ddlDisplayContext.getDisplayStyleGroupId() %>"
				entries="<%= results %>"
			/>
		</liferay-ui:search-container>
	</c:otherwise>
</c:choose>

<c:if test="<%= ddlDisplayContext.isShowConfigurationIcon() %>">
	<div class="icons-container lfr-meta-actions">
		<div class="lfr-icon-actions">
			<liferay-ui:icon
				cssClass="lfr-icon-action lfr-icon-action-configuration"
				iconCssClass="icon-cog"
				label="<%= true %>"
				message="select-form"
				method="get"
				onClick="<%= portletDisplay.getURLConfigurationJS() %>"
				url="<%= portletDisplay.getURLConfiguration() %>"
			/>
		</div>
	</div>
</c:if>