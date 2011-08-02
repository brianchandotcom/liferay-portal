<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
Group liveGroup = (Group)request.getAttribute("site.liveGroup");
%>

<aui:model-context bean="<%= liveGroup %>" model="<%= Group.class %>" />

<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />
<liferay-ui:error exception="<%= GroupNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= RequiredGroupException.class %>" message="old-group-name-is-a-required-system-group" />

<liferay-ui:asset-categories-error />

<liferay-ui:asset-tags-error />

<aui:fieldset>
	<c:choose>
		<c:when test="<%= (liveGroup != null) && PortalUtil.isSystemGroup(liveGroup.getName()) %>">
			<aui:input name="name" type="hidden" />
		</c:when>
		<c:when test="<%= (liveGroup != null) && liveGroup.isOrganization() %>">
			<aui:field-wrapper helpMessage="the-name-of-this-site-cannot-be-edited-because-it-belongs-to-an-organization" label="name">
				<%= liveGroup.getDescriptiveName() %>
			</aui:field-wrapper>
		</c:when>
		<c:otherwise>
			<aui:input name="name" />
		</c:otherwise>
	</c:choose>

	<aui:input name="description" />

	<aui:select label="membership-type" name="type">
		<aui:option label="open" value="<%= GroupConstants.TYPE_SITE_OPEN %>" />
		<aui:option label="restricted" value="<%= GroupConstants.TYPE_SITE_RESTRICTED %>" />
		<aui:option label="private" value="<%= GroupConstants.TYPE_SITE_PRIVATE %>" />
	</aui:select>

	<aui:input inlineLabel="left" name="active" value="<%= true %>" />

	<aui:input name="categories" type="assetCategories" />

	<aui:input name="tags" type="assetTags" />

	<c:if test="<%= liveGroup != null %>">
		<aui:field-wrapper label="site-id">
			<%= liveGroup.getGroupId() %>
		</aui:field-wrapper>
	</c:if>
</aui:fieldset>