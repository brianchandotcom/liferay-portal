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
long selPlid = GetterUtil.getLong((String)request.getAttribute("edit_layout_prototype.jsp-selPlid"));

int mergeFailCount = layoutPrototype.getMergeFailCount();
%>

<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">

	<%
	String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_layout_prototypes_merge_alert") + StringPool.UNDERLINE;
	%>

	<clay:alert
		displayType="warning"
	>
		<liferay-ui:message arguments='<%= new Object[] {mergeFailCount, LanguageUtil.get(request, "page-template")} %>' key="the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-after-x-errors" translateArguments="<%= false %>" />

		<liferay-ui:message arguments="page-template" key="click-reset-and-propagate-to-reset-the-failure-count-and-propagate-changes-from-the-x" />

		<clay:button
			cssClass="alert-btn"
			displayType="primary"
			id='<%= randomNamespace + "resetButton" %>'
			label="reset-and-propagate"
			small="<%= true %>"
		/>
	</clay:alert>

	<script>
		(function () {
			var resetButton = document.getElementById(
				'<%= randomNamespace %>resetButton'
			);

			if (resetButton) {
				resetButton.addEventListener('click', (event) => {
					<portlet:actionURL name="/layout_admin/reset_merge_fail_count_and_merge" var="resetMergeFailCountURL">
						<portlet:param name="redirect" value="<%= redirect %>" />
						<portlet:param name="layoutPrototypeId" value="<%= String.valueOf(layoutPrototype.getLayoutPrototypeId()) %>" />
						<portlet:param name="selPlid" value="<%= String.valueOf(selPlid) %>" />
					</portlet:actionURL>

					submitForm(document.hrefFm, '<%= resetMergeFailCountURL %>');
				});
			}
		})();
	</script>
</c:if>