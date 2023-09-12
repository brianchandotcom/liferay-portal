<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
LayoutPrototype layoutPrototype = (LayoutPrototype)request.getAttribute("edit_layout_prototype.jsp-layoutPrototype");
String redirect = (String)request.getAttribute("edit_layout_prototype.jsp-redirect");

int mergeFailCount = layoutPrototype.getMergeFailCount();
%>

<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">
	<portlet:actionURL name="/layout_page_template_admin/reset_merge_fail_count" var="resetMergeFailCountURL">
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="layoutPrototypeId" value="<%= String.valueOf(layoutPrototype.getLayoutPrototypeId()) %>" />
	</portlet:actionURL>

	<clay:alert
		displayType="warning"
	>
		<liferay-ui:message arguments='<%= new Object[] {mergeFailCount, LanguageUtil.get(request, "page-template")} %>' key="the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-after-x-errors" translateArguments="<%= false %>" />

		<liferay-ui:message arguments="page-template" key="click-reset-to-reset-the-failure-count-and-reenable-propagation" />

		<clay:link
			displayType="secondary"
			href="<%= resetMergeFailCountURL %>"
			label="reset"
			type="button"
		/>
	</clay:alert>
</c:if>