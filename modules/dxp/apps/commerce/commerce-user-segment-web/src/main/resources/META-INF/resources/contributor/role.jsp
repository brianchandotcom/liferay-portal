<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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