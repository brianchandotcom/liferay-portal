<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DDLRecordSet recordSet = (DDLRecordSet)request.getAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

long displayDDMTemplateId = ParamUtil.getLong(request, "displayDDMTemplateId");

boolean spreadsheet = ParamUtil.getBoolean(request, "spreadsheet");
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= false %>"
	title="<%= recordSet.getName(locale) %>"
/>

<c:choose>
	<c:when test="<%= displayDDMTemplateId > 0 %>">
		<liferay-util:include page="/html/portlet/dynamic_data_lists/view_template_records.jsp" />
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="<%= spreadsheet %>">
				<liferay-util:include page="/html/portlet/dynamic_data_lists/view_spreadsheet_records.jsp" />
			</c:when>
			<c:otherwise>
				<liferay-util:include page="/html/portlet/dynamic_data_lists/view_records.jsp" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<%
if (portletName.equals(PortletKeys.DYNAMIC_DATA_LISTS)) {
	PortalUtil.setPageSubtitle(recordSet.getName(locale), request);
	PortalUtil.setPageDescription(recordSet.getDescription(locale), request);
}

PortalUtil.addPortletBreadcrumbEntry(request, recordSet.getName(locale), currentURL);
%>