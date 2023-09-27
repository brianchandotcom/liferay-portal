<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
portletDisplay.setShowBackIcon(true);

LiferayPortletURL usersAdminURL = liferayPortletResponse.createLiferayPortletURL(UsersAdminPortletKeys.USERS_ADMIN, PortletRequest.RENDER_PHASE);

portletDisplay.setURLBack(usersAdminURL.toString());

renderResponse.setTitle(StringBundler.concat(selectedUser.getFullName(), " - ", LanguageUtil.get(request, "personal-data-erasure")));
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<liferay-frontend:empty-result-message
		title='<%= LanguageUtil.get(resourceBundle, "you-have-successfully-anonymized-all-remaining-data") %>'
	/>
</clay:container-fluid>

<portlet:actionURL name="/user_associated_data/delete_user" var="deleteUserURL">
	<portlet:param name="p_u_i_d" value="<%= String.valueOf(selectedUser.getUserId()) %>" />
</portlet:actionURL>

<aui:script>
	Liferay.Util.openConfirmModal({
		message:
			'<%= UnicodeLanguageUtil.get(request, "all-personal-data-associated-with-this-users-applications-has-been-deleted-or-anonymized") %>',
		onConfirm: (isConfirmed) => {
			if (isConfirmed) {
				Liferay.Util.navigate('<%= deleteUserURL.toString() %>');
			}
		},
	});
</aui:script>