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

<%@ include file="/html/taglib/ui/app_view_entry/init.jsp" %>

<div class="app-view-entry app-view-entry-taglib display-<%= HtmlUtil.escapeAttribute(displayStyle) %> entry-display-style <%= cssClass %>" <%= AUIUtil.buildData(data) %>>
	<liferay-ui:icon
		cssClass='<%= showCheckbox ? "pull-left app-view-entry app-view-entry-taglib entry-display-style selectable" : "pull-left app-view-entry app-view-entry-taglib entry-display-style" %>'
		data="<%= data %>"
		iconCssClass="<%= iconCssClass %>"
		label="<%= true %>"
		linkCssClass="entry-link"
		localizeMessage="<%= false %>"
		message="<%= HtmlUtil.escape(title) %>"
		method="get"
		src="<%= HtmlUtil.escapeAttribute(thumbnailSrc) %>"
		url="<%= url %>"
	/>

	<c:if test="<%= locked %>">
		<i class="icon-lock pull-right"></i>
	</c:if>

	<c:if test="<%= !folder && (status != WorkflowConstants.STATUS_ANY) && (status != WorkflowConstants.STATUS_APPROVED) %>">
		<aui:workflow-status showIcon="<%= false %>" showLabel="<%= false %>" status="<%= status %>" />
	</c:if>
</div>