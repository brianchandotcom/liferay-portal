<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
User selUser = (User)request.getAttribute(UsersAdminWebKeys.SELECTED_USER);
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
			boolean hasVisibleAttributes = ExpandoAttributesUtil.hasVisibleAttributes(company.getCompanyId(), User.class);

			PortletProvider.Action action = PortletProvider.Action.EDIT;

			if (hasVisibleAttributes) {
				action = PortletProvider.Action.MANAGE;
			}
			%>

			<clay:link
				aria-label='<%= hasVisibleAttributes ? LanguageUtil.format(request, "manage-x", "custom-fields") : LanguageUtil.format(request, "add-x", "custom-fields") %>'
				cssClass="btn btn-secondary btn-sm modify-link"
				displayType="null"
				href='<%=
					PortletURLBuilder.create(
						PortletProviderUtil.getPortletURL(request, ExpandoColumn.class.getName(), action)
					).setRedirect(
						currentURL
					).setParameter(
						"backTitle", LanguageUtil.get(request, "add-user")
					).setParameter(
						"modelResource", User.class.getName()
					).buildString()
				%>'
				label='<%= hasVisibleAttributes ? "manage" : "add" %>'
			/>
		</clay:content-col>
	</c:if>
</clay:content-row>

<liferay-expando:custom-attribute-list
	className="com.liferay.portal.kernel.model.User"
	classPK="<%= (selUser != null) ? selUser.getUserId() : 0 %>"
	editable="<%= true %>"
	label="<%= true %>"
/>