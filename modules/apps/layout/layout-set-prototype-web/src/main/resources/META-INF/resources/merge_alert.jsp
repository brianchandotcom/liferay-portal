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
LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)request.getAttribute("edit_layout_set_prototype.jsp-layoutSetPrototype");
String redirect = (String)request.getAttribute("edit_layout_set_prototype.jsp-redirect");

int mergeFailCount = SitesUtil.getMergeFailCount(layoutSetPrototype);
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
		(function() {
			var resetButton = document.getElementById(
				'<%= randomNamespace %>resetButton'
			);

			if (resetButton) {
				resetButton.addEventListener('click', function(event) {
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