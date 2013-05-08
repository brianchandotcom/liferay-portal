<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layout_prototypes/init.jsp" %>

<%
LayoutPrototype layoutPrototype = (LayoutPrototype)request.getAttribute("edit_layout_prototype.jsp-layoutPrototype");
String redirect = (String)request.getAttribute("edit_layout_prototype.jsp-redirect");
long selPlid = GetterUtil.getLong((String)request.getAttribute("edit_layout_prototype.jsp-selPlid"));

int mergeFailCount = SitesUtil.getMergeFailCount(layoutPrototype);
%>

<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">

	<%
	boolean merge = false;

	String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_layout_prototypes_merge_alert") + StringPool.UNDERLINE;

	PortletURL portletURL = liferayPortletResponse.createActionURL();

	portletURL.setParameter("redirect", redirect);
	portletURL.setParameter("layoutPrototypeId",String.valueOf(layoutPrototype.getLayoutPrototypeId()));

	if (selPlid > 0) {
		portletURL.setParameter("struts_action", "/layouts_admin/edit_layouts");
		portletURL.setParameter(Constants.CMD, "reset_merge_fail_count_and_merge");
		portletURL.setParameter("selPlid", String.valueOf(selPlid));

		merge = true;
	}
	else {
		portletURL.setParameter("struts_action", "/layout_prototypes/edit_layout_prototype");
		portletURL.setParameter(Constants.CMD, "reset_merge_fail_count");
	}
	%>

	<span class="alert alert-block">
		<liferay-ui:message arguments='<%= new Object[] {mergeFailCount, "page-template"} %>' key="the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-after-x-errors" />

		<liferay-ui:message arguments="page-template" key='<%= merge ? "click-reset-and-propagate-to-reset-the-failure-count-and-propagate-changes-from-the-x" : "click-reset-to-reset-the-failure-count-and-reenable-propagation" %>' />

		<aui:button id='<%= randomNamespace + "resetButton" %>' value='<%= merge ? "reset-and-propagate" : "reset" %>' />
	</span>

	<aui:script use="aui-base">
		var resetButton= A.one('#<%= randomNamespace %>resetButton');

		resetButton.on(
			'click',
			function(event) {
				submitForm(document.hrefFm, '<%= portletURL.toString() %>');
			}
		);
	</aui:script>
</c:if>