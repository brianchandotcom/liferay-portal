<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
OrganizationScreenNavigationDisplayContext organizationScreenNavigationDisplayContext = (OrganizationScreenNavigationDisplayContext)request.getAttribute(UsersAdminWebKeys.ORGANIZATION_SCREEN_NAVIGATION_DISPLAY_CONTEXT);

Organization organization = organizationScreenNavigationDisplayContext.getOrganization();
%>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (organization == null) ? Constants.ADD : Constants.UPDATE %>" />

<div class="form-group">
	<h3 class="sheet-subtitle"><liferay-ui:message key="organization-information" /></h3>

	<liferay-util:include page="/organization/details.jsp" servletContext="<%= application %>" />
</div>

<clay:sheet-section>
	<liferay-util:include page="/organization/parent_organization.jsp" servletContext="<%= application %>" />
</clay:sheet-section>

<clay:sheet-section>
	<h3 class="sheet-subtitle"><liferay-ui:message key="more-information" /></h3>

	<div class="form-group">
		<liferay-util:include page="/organization/categorization.jsp" servletContext="<%= application %>" />
	</div>

	<div class="form-group">
		<liferay-util:include page="/organization/comments.jsp" servletContext="<%= application %>" />
	</div>
</clay:sheet-section>

<c:if test="<%= ExpandoAttributesUtil.hasVisibleAttributes(company.getCompanyId(), Organization.class) %>">
	<clay:sheet-section>
		<h4 class="sheet-tertiary-title"><liferay-ui:message key="custom-fields" /></h4>

		<liferay-util:include page="/organization/custom_fields.jsp" servletContext="<%= application %>" />
	</clay:sheet-section>
</c:if>