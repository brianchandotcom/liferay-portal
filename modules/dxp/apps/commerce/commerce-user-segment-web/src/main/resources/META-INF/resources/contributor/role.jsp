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
RoleCommerceUserSegmentCriterionTypeDisplayContext roleCommerceUserSegmentCriterionTypeDisplayContext = (RoleCommerceUserSegmentCriterionTypeDisplayContext)request.getAttribute("role.jsp-portletDisplayContext");
%>

<liferay-util:buffer
	var="removeCommerceUserSegmentCriterionRoleIcon"
>
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<div class="sheet-section">
	<h3 class="sheet-subtitle"><liferay-ui:message key="regular-roles" /></h3>

	<%@ include file="/contributor/regular_roles.jspf" %>
</div>

<div class="sheet-section">
	<h3 class="sheet-subtitle"><liferay-ui:message key="organization-roles" /></h3>

	<%@ include file="/contributor/organization_roles.jspf" %>
</div>

<div class="sheet-section">
	<h3 class="sheet-subtitle"><liferay-ui:message key="site-roles" /></h3>

	<%@ include file="/contributor/site_roles.jspf" %>
</div>