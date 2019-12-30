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

long logoId = organization.getLogoId();
%>

<h2><%= HtmlUtil.escape(organization.getName()) %></h2>

<div class="details">
	<img alt="<%= HtmlUtil.escapeAttribute(organization.getName()) %>" class="avatar" src="<%= themeDisplay.getPathImage() %>/organization_logo?img_id=<%= logoId %>&t=<%= WebServerServletTokenUtil.getToken(logoId) %>" />

	<dl class="property-list">
		<dt>
			<liferay-ui:message key="type" />
		</dt>
		<dd>
			<%= LanguageUtil.get(request, organization.getType()) %>
		</dd>

		<c:if test="<%= PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_ORGANIZATION_STATUS %>">
			<dt>
				<liferay-ui:message key="status" />
			</dt>
			<dd>
				<%= LanguageUtil.get(request, ListTypeServiceUtil.getListType(organization.getStatusId()).getName()) %>
			</dd>
		</c:if>

		<c:if test="<%= organization.getCountryId() > 0 %>">
			<dt>
				<liferay-ui:message key="country" />
			</dt>
			<dd>
				<%= LanguageUtil.get(request, CountryServiceUtil.getCountry(organization.getCountryId()).getName()) %>
			</dd>
		</c:if>

		<c:if test="<%= organization.getRegionId() > 0 %>">
			<dt>
				<liferay-ui:message key="region" />
			</dt>
			<dd>
				<%= LanguageUtil.get(request, RegionServiceUtil.getRegion(organization.getRegionId()).getName()) %>
			</dd>
		</c:if>

		<c:if test="<%= organization.getParentOrganization() != null %>">
			<dt>
				<liferay-ui:message key="parent-organization" />
			</dt>
			<dd>
				<%= HtmlUtil.escape(organization.getParentOrganization().getName()) %>
			</dd>
		</c:if>
	</dl>
</div>