<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<c:choose>
	<c:when test="<%= scopeGroup.isUser() %>">

		<%
		User user2 = UserLocalServiceUtil.getUserById(scopeGroup.getClassPK());

		request.setAttribute(ContactsWebKeys.CONTACTS_USER, user2);
		%>

		<clay:row>
			<clay:col
				cssClass="contacts-container"
			>
				<liferay-util:include page="/view_user.jsp" servletContext="<%= application %>" />
			</clay:col>
		</clay:row>
	</c:when>
	<c:otherwise>
		<div class="lfr-message-info">
			<liferay-ui:message key="this-application-only-functions-when-placed-on-a-user-page" />
		</div>
	</c:otherwise>
</c:choose>