<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)request.getAttribute("edit_layout_set_prototype.jsp-layoutSetPrototype");
String redirect = (String)request.getAttribute("edit_layout_set_prototype.jsp-redirect");

int mergeFailCount = layoutSetPrototype.getMergeFailCount();
%>

<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">

	<%
	String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_layout_set_prototypes_merge_alert") + StringPool.UNDERLINE;
	%>

	<div class="alert alert-warning">
		<liferay-ui:message arguments='<%= new Object[] {mergeFailCount, LanguageUtil.get(request, "site-template")} %>' key="the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-after-x-errors" translateArguments="<%= false %>" />

		<liferay-ui:message arguments="site-template" key="click-reset-to-reset-the-failure-count-and-reenable-propagation" />

		<aui:button id='<%= randomNamespace + "resetButton" %>' useNamespace="<%= false %>" value="reset" />
	</div>

	<script>
		(function () {
			var resetButton = document.getElementById(
				'<%= randomNamespace %>resetButton'
			);

			if (resetButton) {
				resetButton.addEventListener('click', (event) => {
					<portlet:actionURL name="resetMergeFailCount" var="resetMergeFailCountURL">
						<portlet:param name="layoutSetPrototypeId" value="<%= String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId()) %>" />
						<portlet:param name="redirect" value="<%= redirect %>" />
					</portlet:actionURL>

					submitForm(document.hrefFm, '<%= resetMergeFailCountURL %>');
				});
			}
		})();
	</script>
</c:if>