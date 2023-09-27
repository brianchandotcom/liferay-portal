<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/info_box/init.jsp" %>

<%
String linkId = PortalUtil.generateRandomKey(request, "info-box") + "_action-link";
%>

<div class="info-box<%= Validator.isNotNull(elementClasses) ? StringPool.SPACE + elementClasses : StringPool.BLANK %>">
	<header class="header pb-2">
		<c:if test="<%= Validator.isNotNull(title) %>">
			<h5 class="mb-0 title"><%= HtmlUtil.escape(title) %></h5>
		</c:if>

		<c:if test="<%= Validator.isNotNull(actionLabel) %>">
			<c:if test="<%= Validator.isNotNull(actionTargetId) %>">
				<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as eventsDefinitions">
					var link = document.getElementById('<%= HtmlUtil.escapeJS(linkId) %>');

					if (link) {
						link.addEventListener('click', (e) => {
							e.preventDefault();
							Liferay.fire(eventsDefinitions.OPEN_MODAL, {
								id: '<%= HtmlUtil.escapeJS(actionTargetId) %>',
							});
						});
					}
				</aui:script>
			</c:if>

			<clay:link
				href='<%= Validator.isNotNull(actionUrl) ? actionUrl : "#" %>'
				id="<%= HtmlUtil.escape(linkId) %>"
				label="<%= HtmlUtil.escape(actionLabel) %>"
			/>
		</c:if>
	</header>

	<div class="description">