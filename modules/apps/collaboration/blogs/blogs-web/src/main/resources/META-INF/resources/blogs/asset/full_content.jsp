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

<%@ include file="/blogs/init.jsp" %>

<%
BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletDisplay.getId());
%>

<liferay-util:html-top outputKey="blogs_common_main_css">
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/blogs/css/common_main.css", portlet.getTimestamp()) %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="portlet-blogs">
	<div class="entry-body">

		<%
		String coverImageURL = entry.getCoverImageURL(themeDisplay);
		%>

		<c:if test="<%= Validator.isNotNull(coverImageURL) %>">
			<div class="cover-image-container" style="background-image: url(<%= coverImageURL %>)"></div>
		</c:if>

		<%
		String subtitle = entry.getSubtitle();
		%>

		<c:if test="<%= Validator.isNotNull(subtitle) %>">
			<div class="entry-subtitle">
				<p><%= HtmlUtil.escape(subtitle) %></p>
			</div>
		</c:if>

		<div class="entry-date icon-calendar">
			<span class="hide-accessible"><liferay-ui:message key="published-date" /></span>

			<%= dateFormatDateTime.format(entry.getDisplayDate()) %>
		</div>

		<%= entry.getContent() %>

		<liferay-expando:custom-attributes-available className="<%= BlogsEntry.class.getName() %>">
			<liferay-expando:custom-attribute-list
				className="<%= BlogsEntry.class.getName() %>"
				classPK="<%= (entry != null) ? entry.getEntryId() : 0 %>"
				editable="<%= false %>"
				label="<%= true %>"
			/>
		</liferay-expando:custom-attributes-available>
	</div>
</div>