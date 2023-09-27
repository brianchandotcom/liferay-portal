<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

PushNotificationsDevice pushNotificationsDevice = (PushNotificationsDevice)row.getObject();

long pushNotificationsDeviceId = pushNotificationsDevice.getPushNotificationsDeviceId();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= PushNotificationsPermission.contains(permissionChecker, PushNotificationsActionKeys.MANAGE_DEVICES) %>">
		<liferay-portlet:actionURL name="deletePushNotificationsDevice" var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="pushNotificationsDeviceId" value="<%= String.valueOf(pushNotificationsDeviceId) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon-delete
			confirmation="are-you-sure-you-want-to-delete-this-device"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>