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
			<%= LanguageUtil.get(resourceBundle, "a-map-should-be-shown-here") %>
		</div>
	</c:otherwise>
</c:choose>