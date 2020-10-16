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
String redirectionDataAttribute = (String)request.getAttribute("redirectionData");

String redirectionData = URLDecoder.decode(redirectionDataAttribute, "UTF-8");

String redirectUrlAttribute = (String)request.getAttribute("redirectUrl");

String redirectUrl = URLCodec.decodeURL(redirectUrlAttribute);

String sealAttribute = (String)request.getAttribute("seal");
%>

<form action="<%= HtmlUtil.escapeHREF(redirectUrl) %>" class="hide" id="formMercanet" method="post" name="formMercanet">
	<input name="redirectionData" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirectionData) %>" />
	<input name="seal" type="hidden" value="<%= HtmlUtil.escapeAttribute(URLDecoder.decode(sealAttribute, "UTF-8")) %>" />
	<input type="submit" value="Proceed to checkout" />
</form>

<script>
	window.onload = function () {
		document.querySelector('form').submit();
	};
</script>