<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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