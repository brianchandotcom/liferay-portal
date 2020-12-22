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

<%@ include file="/panel_category/init.jsp" %>

<c:if test="<%= !panelApps.isEmpty() && showHeader %>">
	<a aria-expanded="<%= active %>" class="<%= headerActive ? "active" : "" %> collapse-icon collapse-icon-middle nav-link <%= active ? StringPool.BLANK : "collapsed" %> list-group-heading panel-header" data-qa-id="appGroup" data-toggle="liferay-collapse" href="#<%= id %>">
		<c:if test="<%= !panelCategory.includeHeader(request, PipingServletResponse.createPipingServletResponse(pageContext)) %>">
			<%= panelCategory.getLabel(themeDisplay.getLocale()) %>

			<c:if test="<%= notificationsCount > 0 %>">
				<clay:badge
					cssClass="float-right panel-notifications-count"
					data-qa-id="notificationsCount"
					displayType="danger"
					label="<%= String.valueOf(notificationsCount) %>"
				/>
			</c:if>
		</c:if>

		<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

		<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
	</a>

	<div class="collapse <%= active ? "show" : StringPool.BLANK %>" id="<%= id %>">
		<div class="list-group-item">
</c:if>