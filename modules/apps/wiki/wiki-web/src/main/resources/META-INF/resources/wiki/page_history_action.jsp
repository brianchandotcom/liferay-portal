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

<%@ include file="/wiki/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WikiPage wikiPage = null;

if (row != null) {
	wikiPage = (WikiPage)row.getObject();
}
else {
	wikiPage = (WikiPage)request.getAttribute("info_panel.jsp-wikiPage");
}
%>

<c:if test="<%= (wikiPage.getStatus() == WorkflowConstants.STATUS_APPROVED) && WikiPagePermissionChecker.contains(permissionChecker, wikiPage, ActionKeys.UPDATE) %>">
	<portlet:actionURL name="/wiki/edit_page" var="revertURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.REVERT %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
		<portlet:param name="title" value="<%= HtmlUtil.unescape(wikiPage.getTitle()) %>" />
		<portlet:param name="version" value="<%= String.valueOf(wikiPage.getVersion()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		iconCssClass="icon-undo"
		message="revert"
		url="<%= revertURL %>"
	/>
</c:if>

<c:if test="<%= row == null %>">
	<portlet:renderURL var="compareVersionsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcRenderCommandName" value="/wiki/select_version" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
		<portlet:param name="title" value="<%= HtmlUtil.unescape(wikiPage.getTitle()) %>" />
		<portlet:param name="sourceVersion" value="<%= String.valueOf(wikiPage.getVersion()) %>" />
	</portlet:renderURL>

	<%
	Map<String, Object> data = new HashMap<String, Object>();

	data.put("uri", compareVersionsURL);
	%>

	<liferay-ui:icon
		cssClass="compare-to-link"
		data="<%= data %>"
		label="<%= true %>"
		message="compare-to"
		url="javascript:;"
	/>

	<aui:script sandbox="<%= true %>">
		$('body').on(
			'click',
			'.compare-to-link a',
			function(event) {
				var currentTarget = $(event.currentTarget);

				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							destroyOnHide: true,
							modal: true
						},
						eventName: '<portlet:namespace />selectVersionFm',
						id: '<portlet:namespace />compareVersions' + currentTarget.attr('id'),
						title: '<liferay-ui:message key="compare-versions" />',
						uri: currentTarget.data('uri')
					},
					function(event) {
						<portlet:renderURL var="compareVersionURL">
							<portlet:param name="mvcRenderCommandName" value="/wiki/compare_versions" />
							<portlet:param name="backURL" value="<%= currentURL %>" />
							<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
							<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
							<portlet:param name="type" value="html" />
						</portlet:renderURL>

						var uri = '<%= compareVersionURL %>';

						uri = Liferay.Util.addParams('<portlet:namespace />sourceVersion=' + event.sourceversion, uri);
						uri = Liferay.Util.addParams('<portlet:namespace />targetVersion=' + event.targetversion, uri);

						location.href = uri;
					}
				);
			}
		);
	</aui:script>
</c:if>