<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/map_display/init.jsp" %>

<c:choose>
	<c:when test="<%= mapProvider != null %>">
		<div class="lfr-map" data-latitude="<%= latitude %>" data-longitude="<%= longitude %>" id="<%= name %>Map">

			<%
			mapProvider.include(request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
			%>

		</div>
	</c:when>
	<c:otherwise>
		<div class="alert alert-danger">
			<liferay-ui:message key="a-map-should-be-shown-here" />
		</div>
	</c:otherwise>
</c:choose>