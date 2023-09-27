<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	redirect = PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCPath(
		"/view.jsp"
	).buildString();
}

DDLRecordSet recordSet = (DDLRecordSet)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

long displayDDMTemplateId = ParamUtil.getLong(request, "displayDDMTemplateId");

DDMTemplate displayDDMTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(displayDDMTemplateId);
%>

<c:choose>
	<c:when test="<%= displayDDMTemplate != null %>">
		<liferay-util:include page="/view_template_records.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test='<%= ParamUtil.getBoolean(request, "spreadsheet") %>'>
				<liferay-util:include page="/view_spreadsheet_records.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:otherwise>
				<liferay-util:include page="/view_records.jsp" servletContext="<%= application %>" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<%
if (ddlDisplayContext.isAdminPortlet()) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(recordSet.getName(locale));

	PortalUtil.setPageSubtitle(recordSet.getName(locale), request);
	PortalUtil.setPageDescription(recordSet.getDescription(locale), request);
}

PortalUtil.addPortletBreadcrumbEntry(request, recordSet.getName(locale), currentURL);
%>