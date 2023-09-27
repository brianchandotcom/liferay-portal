<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<c:choose>
	<c:when test='<%= Validator.isNull(fontFamily) && Validator.isNull(fontColor) && (Validator.isNull(fontSize) || fontSize.equals("0")) %>'>
		<liferay-ui:message key="blade_configurationaction_portlet_BladeMessagePortlet.no-config" />
	</c:when>
	<c:otherwise>
		<p style="color: <%= fontColor %>; font-family: <%= fontFamily %>; font-size: <%= fontSize %>;">
			<liferay-ui:message key="blade_configurationaction_portlet_BladeMessagePortlet.caption" />
		</p>
	</c:otherwise>
</c:choose>