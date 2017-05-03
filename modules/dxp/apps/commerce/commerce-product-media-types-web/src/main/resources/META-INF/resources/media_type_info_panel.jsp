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
List<CPMediaType> cpMediaTypes = (List<CPMediaType>)request.getAttribute(CPWebKeys.COMMERCE_PRODUCT_MEDIA_TYPES);

if (ListUtil.isEmpty(cpMediaTypes)) {
	cpMediaTypes = Collections.emptyList();
}
%>

<c:choose>
	<c:when test="<%= cpMediaTypes.size() == 1 %>">

		<%
		CPMediaType cpMediaType = cpMediaTypes.get(0);

		request.setAttribute("info_panel.jsp-entry", cpMediaType);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-header-actions">
				<li>
					<liferay-util:include
						page="/media_type_action.jsp"
						servletContext="<%= application %>"
					/>
				</li>
			</ul>

			<h4><%= HtmlUtil.escape(cpMediaType.getTitle(locale)) %></h4>
		</div>

		<aui:nav-bar markupView="lexicon">
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="id" /></h5>

			<p>
				<%= HtmlUtil.escape(String.valueOf(cpMediaType.getCPMediaTypeId())) %>
			</p>
		</div>
	</c:when>
	<c:otherwise>
		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= cpMediaTypes.size() %>" key="x-items-are-selected" /></h4>
		</div>
	</c:otherwise>
</c:choose>