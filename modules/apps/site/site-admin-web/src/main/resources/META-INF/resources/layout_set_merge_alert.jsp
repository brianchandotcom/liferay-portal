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
long groupId = GetterUtil.getLong((String)request.getAttribute("edit_layout_set_prototype.jsp-groupId"));
LayoutSet layoutSet = (LayoutSet)request.getAttribute("edit_layout_set_prototype.jsp-layoutSet");
LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)request.getAttribute("edit_layout_set_prototype.jsp-layoutSetPrototype");
String redirect = (String)request.getAttribute("edit_layout_set_prototype.jsp-redirect");

int mergeFailCount = SitesUtil.getMergeFailCount(layoutSetPrototype);
%>

<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">
	<div class="alert alert-warning">
		<liferay-ui:message arguments='<%= new Object[] {mergeFailCount, LanguageUtil.get(request, "site-template")} %>' key="the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-after-x-errors" translateArguments="<%= false %>" />

		<liferay-ui:message arguments="site-template" key="click-reset-and-propagate-to-reset-the-failure-count-and-propagate-changes-from-the-x" />

		<%
		String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_layout_set_prototypes_merge_alert") + StringPool.UNDERLINE;
		%>

		<aui:button id='<%= randomNamespace + "resetButton" %>' useNamespace="<%= false %>" value="reset-and-propagate" />
	</div>

	<script>
		var resetButton = document.getElementById('<%= randomNamespace %>resetButton');

		if (resetButton) {
			resetButton.addEventListener('click', function(event) {
				<portlet:actionURL name="resetMergeFailCountAndMerge" var="portletURL">
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="layoutSetPrototypeId" value="<%= String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId()) %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
					<portlet:param name="privateLayoutSet" value="<%= String.valueOf(layoutSet.isPrivateLayout()) %>" />
				</portlet:actionURL>

				submitForm(document.hrefFm, '<%= portletURL.toString() %>');
			});
		}
	</script>
</c:if>

<%
List<Layout> mergeFailFriendlyURLLayouts = SitesUtil.getMergeFailFriendlyURLLayouts(layoutSet);
%>

<c:if test="<%= !mergeFailFriendlyURLLayouts.isEmpty() %>">
	<div class="alert alert-warning">
		<liferay-ui:message key="some-pages-from-the-site-template-cannot-be-propagated-because-their-friendly-urls-conflict-with-the-following-pages" />

		<liferay-ui:message key="modify-the-friendly-url-of-the-pages-to-allow-their-propagation-from-the-site-template" />

		<ul>

			<%
			PortletURL editLayoutsURL = PortletProviderUtil.getPortletURL(request, Layout.class.getName(), PortletProvider.Action.VIEW);

			editLayoutsURL.setParameter("tabs1", layoutSet.isPrivateLayout() ? "private-pages" : "public-pages");
			editLayoutsURL.setParameter("redirect", redirect);
			editLayoutsURL.setParameter("groupId", String.valueOf(groupId));

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
	</div>
</c:if>