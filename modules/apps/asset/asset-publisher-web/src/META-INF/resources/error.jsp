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

<portlet:renderURL var="portletURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:renderURL>

<liferay-ui:header
	backURL="<%= portletURL.toString() %>"
	title="error"
/>

<liferay-ui:error exception="<%= ConfigurationException.MustBeSelectableScope.class %>" message="the-selected-scope-is-not-available" />
<liferay-ui:error exception="<%= ConfigurationException.MustBeStrictPortlet.class %>" message="the-portlet-is-not-configured-correctly" />
<liferay-ui:error exception="<%= ConfigurationException.MustHaveAncestor.class %>" message="no-matching-scope-group-found" />
<liferay-ui:error exception="<%= ConfigurationException.MustHaveContentSharingWithChildrenEnabled.class %>" message="referenced-group-no-longer-allows-sharing-content-with-child-sites" />
<liferay-ui:error exception="<%= ConfigurationException.MustHaveValidScopeGroupId.class %>" message="no-matching-scope-group-found" />
<liferay-ui:error exception="<%= NoSuchModelException.class %>" message="the-asset-could-not-be-found" />