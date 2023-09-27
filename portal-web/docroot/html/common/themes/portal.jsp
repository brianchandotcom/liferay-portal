<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/common/themes/init.jsp" %>

<%
StringBundler sb = (StringBundler)request.getAttribute(WebKeys.LAYOUT_CONTENT);

if ((sb != null) && themeDisplay.isStateExclusive()) {
	sb.writeTo(out);
}
else {
	Definition definition = (Definition)request.getAttribute(TilesUtil.DEFINITION);

	boolean tilesPopUp = false;

	if (definition != null) {
		Map<String, String> attributes = definition.getAttributes();

		tilesPopUp = GetterUtil.getBoolean(attributes.get("pop_up"));
	}
%>

	<c:choose>
		<c:when test="<%= tilesPopUp || themeDisplay.isStatePopUp() || themeDisplay.isWidget() %>">
			<liferay-theme:include
				page="portal_pop_up.jsp"
			/>
		</c:when>
		<c:otherwise>
			<liferay-theme:include
				page="portal_normal.jsp"
			/>
		</c:otherwise>
	</c:choose>

<%
}

request.removeAttribute(WebKeys.LAYOUT_CONTENT);

PortalMessages.clear(request);
SessionMessages.clear(request);
SessionErrors.clear(request);
%>