<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/portal/init.jsp" %>

<c:choose>
	<c:when test='<%= Objects.equals(SessionErrors.get(request, "statusCodeURI"), "urn:oasis:names:tc:SAML:2.0:status:AuthnFailed") %>'>
		<h3 class="portlet-msg-error">
			<liferay-ui:message key="authentication-failed" />
		</h3>
	</c:when>
	<c:otherwise>
		<h3 class="portlet-msg-error">
			<liferay-ui:message key="unable-to-process-saml-request" />
		</h3>
	</c:otherwise>
</c:choose>