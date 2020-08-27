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

<%@ include file="/info_box/init.jsp" %>

<%
String infoxBoxCssClasses = "info-box" + (Validator.isNotNull(elementClasses) ? StringPool.SPACE + elementClasses : StringPool.BLANK);
String linkId = PortalUtil.generateRandomKey(request, "info-box") + StringPool.UNDERLINE + "action-link";
%>

<div class="<%= infoxBoxCssClasses %>">
	<header class="header pb-2">
		<c:if test="<%= Validator.isNotNull(title) %>">
			<h5 class="mb-0 title"><%= title %></h5>
		</c:if>

		<c:if test="<%= Validator.isNotNull(actionLabel) %>">
			<c:if test="<%= Validator.isNotNull(actionTargetId) %>">
				<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as eventsDefinitions">
					var link = document.getElementById('<%= linkId %>');

					if (link) {
						link.addEventListener('click', function (e) {
							e.preventDefault();
							Liferay.fire(eventsDefinitions.OPEN_MODAL, {
								id: '<%= actionTargetId %>',
							});
						});
					}
				</aui:script>
			</c:if>

			<clay:link
				href='<%= Validator.isNotNull(actionUrl) ? actionUrl : "#" %>'
				id="<%= linkId %>"
				label="<%= actionLabel %>"
			/>
		</c:if>
	</header>

	<div class="description">