<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long groupId = GetterUtil.getLong((String)request.getAttribute("edit_layout_set_prototype.jsp-groupId"));
LayoutSet layoutSet = (LayoutSet)request.getAttribute("edit_layout_set_prototype.jsp-layoutSet");
String redirect = (String)request.getAttribute("edit_layout_set_prototype.jsp-redirect");

List<Layout> mergeFailFriendlyURLLayouts = layoutSet.getMergeFailFriendlyURLLayouts();
%>

<c:if test="<%= !mergeFailFriendlyURLLayouts.isEmpty() %>">
	<clay:alert
		displayType="warning"
	>
		<liferay-ui:message key="some-pages-from-the-site-template-cannot-be-propagated-because-their-friendly-urls-conflict-with-the-following-pages" />

		<liferay-ui:message key="modify-the-friendly-url-of-the-pages-to-allow-their-propagation-from-the-site-template" />

		<ul>

			<%
			PortletURL editLayoutsURL = PortletURLBuilder.create(
				PortletProviderUtil.getPortletURL(request, Layout.class.getName(), PortletProvider.Action.VIEW)
			).setRedirect(
				redirect
			).setTabs1(
				layoutSet.isPrivateLayout() ? "private-pages" : "public-pages"
			).setParameter(
				"groupId", groupId
			).buildPortletURL();

			for (Layout mergeFailFriendlyURLLayout : mergeFailFriendlyURLLayouts) {
				editLayoutsURL.setParameter("selPlid", String.valueOf(mergeFailFriendlyURLLayout.getPlid()));
			%>

				<li>
					<aui:a href="<%= editLayoutsURL.toString() %>">
						<%= HtmlUtil.escape(mergeFailFriendlyURLLayout.getName(locale)) %>
					</aui:a>
				</li>

			<%
			}
			%>

		</ul>
	</clay:alert>
</c:if>