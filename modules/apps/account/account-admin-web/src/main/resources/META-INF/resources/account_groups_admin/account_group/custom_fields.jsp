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
AccountGroupDisplay accountGroupDisplay = (AccountGroupDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_GROUP_DISPLAY);
%>

<clay:content-row>
	<clay:content-col
		expand="<%= true %>"
	>
		<h4 class="sheet-tertiary-title">
			<liferay-ui:message key="custom-fields" />
		</h4>
	</clay:content-col>

	<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, PortletKeys.EXPANDO, ActionKeys.ACCESS_IN_CONTROL_PANEL) %>">
		<clay:content-col>

			<%
			boolean hasVisibleAttributes = ExpandoAttributesUtil.hasVisibleAttributes(company.getCompanyId(), AccountGroup.class);
			%>

			<liferay-ui:icon
				cssClass="modify-link"
				label="<%= true %>"
				linkCssClass="btn btn-secondary btn-sm"
				message='<%= hasVisibleAttributes ? "manage" : "add" %>'
				method="get"
				url='<%=
					PortletURLBuilder.create(
						PortletProviderUtil.getPortletURL(request, ExpandoColumn.class.getName(), hasVisibleAttributes ? PortletProvider.Action.MANAGE : PortletProvider.Action.EDIT)
					).setRedirect(
						currentURL
					).setParameter(
						"modelResource", AccountGroup.class.getName()
					).buildString()
				%>'
			/>
		</clay:content-col>
	</c:if>
</clay:content-row>

<liferay-expando:custom-attribute-list
	className="<%= AccountGroup.class.getName() %>"
	classPK="<%= accountGroupDisplay.getAccountGroupId() %>"
	editable="<%= true %>"
	label="<%= true %>"
/>