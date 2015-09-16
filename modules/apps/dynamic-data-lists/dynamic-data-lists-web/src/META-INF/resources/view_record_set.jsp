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

DDLRecordSet recordSet = (DDLRecordSet)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

long displayDDMTemplateId = ParamUtil.getLong(request, "displayDDMTemplateId");

boolean spreadsheet = ParamUtil.getBoolean(request, "spreadsheet");

String title = recordSet.getName(locale);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(title);
%>

<c:choose>
	<c:when test="<%= displayDDMTemplateId > 0 %>">
		<liferay-util:include page="/view_template_records.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="<%= spreadsheet %>">
				<liferay-util:include page="/view_spreadsheet_records.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:otherwise>
				<liferay-util:include page="/view_records.jsp" servletContext="<%= application %>" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<%
if (portletName.equals(DDLPortletKeys.DYNAMIC_DATA_LISTS)) {
	PortalUtil.setPageSubtitle(recordSet.getName(locale), request);
	PortalUtil.setPageDescription(recordSet.getDescription(locale), request);
}

PortalUtil.addPortletBreadcrumbEntry(request, recordSet.getName(locale), currentURL);
%>