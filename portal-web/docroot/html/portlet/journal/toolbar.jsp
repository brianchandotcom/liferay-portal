<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<liferay-ui:icon-menu align="left" cssClass="actions-button" direction="down" icon="" id="actionsButtonContainer" message="actions" showExpanded="<%= false %>" showWhenSingleIcon="<%= true %>">

	<%
	String taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.DELETE + "'});";
	%>

	<liferay-ui:icon
		cssClass="delete-articles-button"
		image="delete"
		message="delete"
		onClick="<%= taglibOnClick %>"
		url="javascript:;"
	/>

	<%
	taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.EXPIRE + "'});";
	%>

	<liferay-ui:icon
		cssClass="expire-articles-button"
		image="time"
		message="expire"
		onClick="<%= taglibOnClick %>"
		url="javascript:;"
	/>

	<%
	taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.MOVE + "'});";
	%>

	<liferay-ui:icon
		cssClass="move-articles-button"
		image="submit"
		message="move"
		onClick="<%= taglibOnClick %>"
		url="javascript:;"
	/>
</liferay-ui:icon-menu>

<span class="add-button" id="<portlet:namespace />addButtonContainer">
	<liferay-util:include page="/html/portlet/journal/add_button.jsp" />
</span>

<span class="sort-button" id="<portlet:namespace />sortButtonContainer">
	<liferay-util:include page="/html/portlet/journal/sort_button.jsp" />
</span>

<span class="manage-button">
	<c:if test="<%= !user.isDefaultUser() %>">
		<liferay-ui:icon-menu align="left" direction="down" icon="" message="manage" showExpanded="<%= false %>" showWhenSingleIcon="<%= true %>">

			<%
			String taglibURL = "javascript:" + renderResponse.getNamespace() + "openStructuresView()";
			%>

			<liferay-ui:icon
				message="structures"
				url="<%= taglibURL %>"
			/>

			<%
			taglibURL = "javascript:" + renderResponse.getNamespace() + "openFeedsView()";
			%>

			<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
				<liferay-ui:icon
					message="feeds"
					url="<%= taglibURL %>"
				/>
			</c:if>
		</liferay-ui:icon-menu>
	</c:if>
</span>

<aui:script>
	<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
		function <portlet:namespace />openFeedsView() {
			Liferay.Util.openWindow(
				{
					dialog: {
						width: 820
					},
					id: '<portlet:namespace />openFeedsView',
					title: '<%= UnicodeLanguageUtil.get(pageContext, "feeds") %>',
					uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/view_feeds" /></liferay-portlet:renderURL>'
				}
			);
		}
	</c:if>

	function <portlet:namespace />openStructuresView() {
		Liferay.Util.openDDMPortlet(
			{
				ddmResource: '<%= ddmResource %>',
				ddmResourceActionId: '<%= ActionKeys.ADD_TEMPLATE %>',
				dialog: {
					width: 820
				},
				showGlobalScope: 'false',
				showManageTemplates: 'true',
				storageType: 'xml',
				structureName: 'structure',
				structureType: 'com.liferay.portlet.journal.model.JournalArticle',
				templateType: '<%= DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY %>',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "structures") %>'
			}
		);
	}
</aui:script>