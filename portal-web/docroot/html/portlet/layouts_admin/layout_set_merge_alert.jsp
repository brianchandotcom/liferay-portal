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

<%@ include file="/html/portlet/layout_set_prototypes/init.jsp" %>

<%
long groupId = GetterUtil.getLong((String)request.getAttribute("edit_layout_set_prototype.jsp-groupId"));
LayoutSet layoutSet = (LayoutSet)request.getAttribute("edit_layout_set_prototype.jsp-layoutSet");
LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)request.getAttribute("edit_layout_set_prototype.jsp-layoutSetPrototype");
String redirect = (String)request.getAttribute("edit_layout_set_prototype.jsp-redirect");

int mergeFailCount = SitesUtil.getMergeFailCount(layoutSetPrototype);
%>

<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">

	<%
	boolean merge = false;

	String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_layout_set_prototypes_merge_alert") + StringPool.UNDERLINE;

	PortletURL portletURL = liferayPortletResponse.createActionURL();

	portletURL.setParameter("redirect", redirect);
	portletURL.setParameter("layoutSetPrototypeId",String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId()));

	if (groupId > 0) {
		portletURL.setParameter("struts_action", "/sites_admin/edit_site");
		portletURL.setParameter(Constants.CMD, "reset_merge_fail_count_and_merge");
		portletURL.setParameter("groupId", String.valueOf(groupId));
		portletURL.setParameter("privateLayoutSet", String.valueOf(layoutSet.isPrivateLayout()));

		merge = true;
	}
	else {
		portletURL.setParameter("struts_action", "/layout_set_prototypes/edit_layout_set_prototype");
		portletURL.setParameter(Constants.CMD, "reset_merge_fail_count");
	}
	%>

	<div class="alert alert-warning">
		<liferay-ui:message arguments='<%= new Object[] {mergeFailCount, LanguageUtil.get(request, "site-template")} %>' key="the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-after-x-errors" translateArguments="<%= false %>" />

		<liferay-ui:message arguments="site-template" key='<%= merge ? "click-reset-and-propagate-to-reset-the-failure-count-and-propagate-changes-from-the-x" : "click-reset-to-reset-the-failure-count-and-reenable-propagation" %>' />

		<aui:button id='<%= randomNamespace + "resetButton" %>' useNamespace="<%= false %>" value='<%= merge ? "reset-and-propagate" : "reset" %>' />
	</div>

	<aui:script>
		AUI.$('#<%= randomNamespace %>resetButton').on(
			'click',
			function(event) {
				submitForm(document.hrefFm, '<%= portletURL.toString() %>');
			}
		);
	</aui:script>
</c:if>

<%
List<Layout> mergeFailFriendlyURLLayouts = SitesUtil.getMergeFailFriendlyURLLayouts(layoutSet);
%>

<c:if test="<%= !mergeFailFriendlyURLLayouts.isEmpty() %>">
	<div class="alert alert-warning">
		<liferay-ui:message key="some-pages-from-the-site-template-cannot-be-propagated-because-their-friendly-urls-conflict-with-the-following-pages" />

		<liferay-ui:message key="modify-the-friendly-url-of-the-pages-to-allow-their-propagation-from-the-site-template" />

		<ul>
			<liferay-portlet:renderURL portletName="<%= PortletKeys.GROUP_PAGES %>" varImpl="editLayoutsURL">
				<portlet:param name="struts_action" value="/group_pages/edit_layouts" />
				<portlet:param name="tabs1" value='<%= layoutSet.isPrivateLayout() ? "private-pages" : "public-pages" %>' />
				<portlet:param name="redirect" value="<%= redirect %>" />
				<portlet:param name="closeRedirect" value="<%= redirect %>" />
				<portlet:param name="backURL" value="<%= redirect %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			</liferay-portlet:renderURL>

			<%
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