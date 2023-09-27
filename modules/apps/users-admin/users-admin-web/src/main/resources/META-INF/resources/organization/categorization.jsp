<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<label class="control-label">
	<liferay-ui:message key="categorization" />
</label>

<div class="form-group">
	<liferay-asset:asset-categories-selector
		className="<%= Organization.class.getName() %>"
		classPK="<%= (organization != null) ? organization.getPrimaryKey() : 0 %>"
		visibilityTypes="<%= AssetVocabularyConstants.VISIBILITY_TYPES %>"
	/>
</div>

<div class="form-group">
	<liferay-asset:asset-tags-selector
		className="<%= Organization.class.getName() %>"
		classPK="<%= (organization != null) ? organization.getPrimaryKey() : 0 %>"
	/>
</div>