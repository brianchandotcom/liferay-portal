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
long organizationId = ParamUtil.getLong(request, "organizationId");

Organization organization = OrganizationServiceUtil.fetchOrganization(organizationId);
%>

<aui:model-context bean="<%= organization %>" model="<%= Organization.class %>" />

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="categorization"
/>

<liferay-asset:asset-categories-error />

<liferay-asset:asset-tags-error />

<div class="form-group">
	<liferay-asset:asset-categories-selector
		className="<%= Organization.class.getName() %>"
		classPK="<%= (organization != null) ? organization.getPrimaryKey() : 0 %>"
	/>
</div>

<div class="form-group">
	<liferay-asset:asset-tags-selector
		className="<%= Organization.class.getName() %>"
		classPK="<%= (organization != null) ? organization.getPrimaryKey() : 0 %>"
	/>
</div>