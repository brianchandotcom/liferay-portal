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
LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)request.getAttribute("edit_layout_set_prototype.jsp-layoutSetPrototype");
String redirect = (String)request.getAttribute("edit_layout_set_prototype.jsp-redirect");

int mergeFailCount = layoutSetPrototype.getMergeFailCount();
%>

<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">
	<clay:alert>
		<liferay-ui:message arguments='<%= new Object[] {mergeFailCount, LanguageUtil.get(request, "site-template")} %>' key="the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-after-x-errors" translateArguments="<%= false %>" />

		<liferay-ui:message arguments="site-template" key="click-reset-and-propagate-to-reset-the-failure-count-and-propagate-changes-from-the-x" />

		<liferay-portlet:actionURL name="/site_admin/reset_merge_fail_count_and_merge" portletName="<%= SiteAdminPortletKeys.SITE_ADMIN %>" var="portletURL">
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="layoutSetPrototypeId" value="<%= String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId()) %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			<portlet:param name="privateLayoutSet" value="<%= String.valueOf(layoutSet.isPrivateLayout()) %>" />
		</liferay-portlet:actionURL>

		<clay:link
			cssClass="c-mt-2"
			displayType="secondary"
			href="<%= portletURL.toString() %>"
			label="reset-and-propagate"
			type="button"
		/>
	</clay:alert>
</c:if>

<%
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