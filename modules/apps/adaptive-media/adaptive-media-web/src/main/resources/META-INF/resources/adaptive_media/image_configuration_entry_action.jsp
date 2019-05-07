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

<%@ include file="/adaptive_media/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

AMImageConfigurationEntry amImageConfigurationEntry = null;

if (row != null) {
	amImageConfigurationEntry = (AMImageConfigurationEntry)row.getObject();
}
else {
	amImageConfigurationEntry = (AMImageConfigurationEntry)request.getAttribute("info_panel.jsp-amImageConfigurationEntry");
}

boolean optimizeImagesEnabled = true;

List<BackgroundTask> optimizeImageSingleBackgroundTasks = (List<BackgroundTask>)request.getAttribute("view.jsp-optimizeImageSingleBackgroundTasks");

if (optimizeImageSingleBackgroundTasks != null) {
	for (BackgroundTask optimizeImageSingleBackgroundTask : optimizeImageSingleBackgroundTasks) {
		Map<String, Serializable> taskContextMap = optimizeImageSingleBackgroundTask.getTaskContextMap();

		String configurationEntryUuid = (String)taskContextMap.get("configurationEntryUuid");

		if (configurationEntryUuid.equals(amImageConfigurationEntry.getUUID())) {
			optimizeImagesEnabled = false;

			break;
		}
	}
}

String entryUuid = String.valueOf(amImageConfigurationEntry.getUUID());
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="actions"
	showWhenSingleIcon="<%= true %>"
>
	<liferay-portlet:renderURL var="editImageConfigurationEntryURL">
		<portlet:param name="mvcRenderCommandName" value="/adaptive_media/edit_image_configuration_entry" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="entryUuid" value="<%= entryUuid %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editImageConfigurationEntryURL %>"
	/>

	<c:choose>
		<c:when test="<%= amImageConfigurationEntry.isEnabled() %>">
			<portlet:actionURL name="/adaptive_media/disable_image_configuration_entry" var="disableImageConfigurationEntryURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="amImageConfigurationEntryUuid" value="<%= entryUuid %>" />
			</portlet:actionURL>

			<%
			String cssClass = StringPool.BLANK;

			if (!optimizeImagesEnabled) {
				cssClass = "disabled";
			}
			%>

			<liferay-ui:icon
				cssClass="<%= cssClass %>"
				id='<%= "icon-disable-" + entryUuid %>'
				message="disable"
				url="<%= disableImageConfigurationEntryURL %>"
			/>
		</c:when>
		<c:otherwise>
			<portlet:actionURL name="/adaptive_media/enable_image_configuration_entry" var="enableImageConfigurationEntryURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="amImageConfigurationEntryUuid" value="<%= entryUuid %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				message="enable"
				url="<%= enableImageConfigurationEntryURL %>"
			/>
		</c:otherwise>
	</c:choose>

	<portlet:actionURL name="/adaptive_media/optimize_images" var="optimizeImagesURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="entryUuid" value="<%= entryUuid %>" />
	</portlet:actionURL>

	<%
	String onClick = liferayPortletResponse.getNamespace() + "adaptRemaining('" + entryUuid + "', '" + optimizeImagesURL.toString() + "');";

	int percentage = AMImageEntryLocalServiceUtil.getPercentage(themeDisplay.getCompanyId(), entryUuid);

	String cssClass = (!amImageConfigurationEntry.isEnabled() || percentage == 100 || !optimizeImagesEnabled) ? "disabled" : StringPool.BLANK;
	%>

	<liferay-ui:icon
		cssClass="<%= cssClass %>"
		id='<%= "icon-adapt-remaining" + entryUuid %>'
		message="adapt-remaining"
		onClick="<%= onClick %>"
		url="javascript:;"
	/>

	<portlet:actionURL name="/adaptive_media/delete_image_configuration_entry" var="deleteImageConfigurationEntryURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="rowIdsAMImageConfigurationEntry" value="<%= entryUuid %>" />
	</portlet:actionURL>

	<c:choose>
		<c:when test="<%= amImageConfigurationEntry.isEnabled() %>">
			<liferay-ui:icon
				cssClass="disabled"
				message="delete"
				url="javascript:;"
			/>
		</c:when>
		<c:otherwise>
			<liferay-ui:icon-delete
				trash="<%= false %>"
				url="<%= deleteImageConfigurationEntryURL %>"
			/>
		</c:otherwise>
	</c:choose>
</liferay-ui:icon-menu>

<%
Map<String, Object> context = new HashMap<>();

context.put("uuid", entryUuid);
%>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "OptionsHandler" + entryUuid %>'
	context="<%= context %>"
	module="adaptive_media/js/AdaptiveMediaOptionsHandler.es"
/>