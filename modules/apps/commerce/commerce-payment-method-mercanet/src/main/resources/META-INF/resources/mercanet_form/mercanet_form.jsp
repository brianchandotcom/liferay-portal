<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirectionData = URLDecoder.decode((String)request.getAttribute(CommercePaymentWebKeys.REDIRECTION_DATA), StringPool.UTF8);
String redirectURL = URLCodec.decodeURL((String)request.getAttribute(CommercePaymentWebKeys.REDIRECT_URL));
String seal = URLDecoder.decode((String)request.getAttribute(CommercePaymentWebKeys.SEAL), StringPool.UTF8);
%>

<form action="<%= HtmlUtil.escapeHREF(redirectURL) %>" class="hide" id="formMercanet" method="post" name="formMercanet">
	<input name="redirectionData" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirectionData) %>" />
	<input name="seal" type="hidden" value="<%= HtmlUtil.escapeAttribute(seal) %>" />

	<input type="submit" value="Proceed to checkout" />
</form>

<script>
	window.onload = function () {
		document.querySelector('form').submit();
	};
</script>