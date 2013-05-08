<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute("view.jsp-folder");
%>

<liferay-ui:icon-menu cssClass="actions-button hide" direction="down" icon="" id="actionsButtonContainer" message="actions" showExpanded="<%= false %>" showWhenSingleIcon="<%= true %>">

	<%
	Group scopeGroup = themeDisplay.getScopeGroup();
	%>

	<c:if test="<%= !scopeGroup.isStaged() || scopeGroup.isStagingGroup() || !scopeGroup.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY) %>">

		<%
		String taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.CANCEL_CHECKOUT + "'});";
		%>

		<liferay-ui:icon
			image="undo"
			message="cancel-checkout[document]"
			onClick="<%= taglibOnClick %>"
			url="javascript:;"
		/>

		<%
		taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.CHECKIN + "'});";
		%>

		<liferay-ui:icon
			image="unlock"
			message="checkin"
			onClick="<%= taglibOnClick %>"
			url="javascript:;"
		/>

		<%
		taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.CHECKOUT + "'});";
		%>

		<liferay-ui:icon
			image="lock"
			message="checkout[document]"
			onClick="<%= taglibOnClick %>"
			url="javascript:;"
		/>

		<%
		taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.MOVE + "'});";
		%>

		<liferay-ui:icon
			image="submit"
			message="move"
			onClick="<%= taglibOnClick %>"
			url="javascript:;"
		/>
	</c:if>


	<%
	String taglibURL = "Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.MOVE_TO_TRASH + "'});";
	%>

	<liferay-ui:icon-delete
		confirmation="are-you-sure-you-want-to-delete-the-selected-entries"
		id="moveToTrashAction"
		trash="<%= true %>"
		url="<%= taglibURL %>"
	/>

	<%
	taglibURL = "Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.DELETE + "'});";
	%>

	<liferay-ui:icon-delete
		confirmation="are-you-sure-you-want-to-delete-the-selected-entries"
		id="deleteAction"
		url="<%= taglibURL %>"
	/>
</liferay-ui:icon-menu>

<span class="add-button" id="<portlet:namespace />addButtonContainer">
	<liferay-util:include page="/html/portlet/document_library/add_button.jsp" />
</span>

<span class="sort-button" id="<portlet:namespace />sortButtonContainer">
	<liferay-util:include page="/html/portlet/document_library/sort_button.jsp" />
</span>

<span class="manage-button">
	<c:if test="<%= !user.isDefaultUser() %>">
		<liferay-ui:icon-menu direction="down" icon="" message="manage" showExpanded="<%= false %>" showWhenSingleIcon="<%= true %>">

			<%
			String taglibURL = "javascript:" + renderResponse.getNamespace() + "openFileEntryTypeView()";
			%>

			<liferay-ui:icon
				image="copy"
				message="document-types"
				url="<%= taglibURL %>"
			/>

			<%
			taglibURL = "javascript:" + renderResponse.getNamespace() + "openDDMStructureView()";
			%>

			<liferay-ui:icon
				image="copy"
				message="metadata-sets"
				url="<%= taglibURL %>"
			/>
		</liferay-ui:icon-menu>
	</c:if>
</span>

<aui:script>
	function <portlet:namespace />openFileEntryTypeView() {
		Liferay.Util.openWindow(
			{
				id: '<portlet:namespace />openFileEntryTypeView',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "document-types") %>',
				uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/document_library/view_file_entry_type" /><portlet:param name="redirect" value="<%= currentURL %>" /></liferay-portlet:renderURL>'
			}
		);
	}

	function <portlet:namespace />openDDMStructureView() {
		Liferay.Util.openDDMPortlet(
			{
				ddmResource: '<%= ddmResource %>',
				dialog: {
					destroyOnHide: true
				},
				showGlobalScope: 'true',
				showManageTemplates: 'false',
				storageType: 'xml',
				structureName: 'metadata-set',
				structureType: 'com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "metadata-sets") %>'
			}
		);
	}
</aui:script>