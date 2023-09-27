<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CTCollection ctCollection = (CTCollection)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= CTCollectionPermission.contains(permissionChecker, ctCollection, CTActionKeys.PUBLISH) %>">
		<liferay-portlet:renderURL var="rescheduleURL">
			<portlet:param name="mvcRenderCommandName" value="/change_tracking/reschedule_publication" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="reschedule"
			url="<%= rescheduleURL %>"
		/>

		<liferay-portlet:actionURL name="/change_tracking/unschedule_publication" var="unscheduleURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon
			message="unschedule"
			url="<%= unscheduleURL %>"
		/>

		<li aria-hidden="true" class="dropdown-divider" role="presentation"></li>
	</c:if>

	<liferay-portlet:renderURL var="reviewURL">
		<portlet:param name="mvcRenderCommandName" value="/change_tracking/view_changes" />
		<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:icon
		message="review-changes"
		url="<%= reviewURL %>"
	/>
</liferay-ui:icon-menu>