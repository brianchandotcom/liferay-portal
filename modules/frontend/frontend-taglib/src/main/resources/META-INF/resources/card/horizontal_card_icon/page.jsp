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

<%@ include file="/card/horizontal_card_icon/init.jsp" %>

<%
Object bodyContent = request.getAttribute("liferay-frontend:horizontal-card-icon:bodyContent");

String bodyContentString = StringPool.BLANK;

if (bodyContent != null) {
	bodyContentString = bodyContent.toString();
}
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(bodyContentString) %>">
		<%= bodyContentString %>
	</c:when>
	<c:otherwise>
		<div class="icon-monospaced sticker-default sticker-lg">
			<aui:icon cssClass="text-default" image="<%= icon %>" markupView="lexicon" />
		</div>
	</c:otherwise>
</c:choose>