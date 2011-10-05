<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

Group scopedGroup = themeDisplay.getScopeGroup();

boolean actionsAlowed = true;

if(scopedGroup.isStaged()){
	actionsAlowed = scopedGroup.isStagingGroup() || !scopedGroup.isStagedPortlet(portletDisplay.getRootPortletId());
}
%>

<aui:input cssClass="select-documents aui-state-default" inline="<%= true %>" label="" name='<%= RowChecker.ALL_ROW_IDS %>' type="checkbox" />

<liferay-ui:icon-menu align="left" cssClass="actions-button" direction="down" disabled="<%= true %>" icon="" id="actionsButtonContainer" message="actions" showExpanded="<%= false %>" showWhenSingleIcon="<%= actionsAlowed ? false : true %>">

	<%
	String taglibUrl = "";
	%>
	<c:if test="<%= actionsAlowed %>">
		<%
			taglibUrl = "javascript:" + renderResponse.getNamespace() + "editFileEntry('" + Constants.CANCEL_CHECKOUT + "')";
		%>

		<liferay-ui:icon
			image="undo"
			message="cancel-checkout"
			url="<%= taglibUrl %>"
		/>

		<%
		taglibUrl = "javascript:" + renderResponse.getNamespace() + "editFileEntry('" + Constants.CHECKIN + "')";
		%>

		<liferay-ui:icon
			image="unlock"
			message="checkin"
			url="<%= taglibUrl %>"
		/>

		<%
		taglibUrl = "javascript:" + renderResponse.getNamespace() + "editFileEntry('" + Constants.CHECKOUT + "')";
		%>

		<liferay-ui:icon
			image="lock"
			message="checkout"
			url="<%= taglibUrl %>"
		/>

		<%
		taglibUrl = "javascript:" + renderResponse.getNamespace() + "editFileEntry('" + Constants.MOVE + "')";
		%>

		<liferay-ui:icon
			image="submit"
			message="move"
			url="<%= taglibUrl %>"
		/>
	</c:if>
	<%
	taglibUrl = "javascript:" + renderResponse.getNamespace() + "editFileEntry('" + Constants.DELETE + "')";
	%>

	<liferay-ui:icon-delete
		confirmation="are-you-sure-you-want-to-delete-the-selected-entries"
		url="<%= taglibUrl %>"
	/>
</liferay-ui:icon-menu>

<span class="add-button" id="<portlet:namespace />addButtonContainer">
	<liferay-util:include page="/html/portlet/document_library/add_button.jsp" />
</span>

<span class="sort-button" id="<portlet:namespace />sortButtonContainer">
	<liferay-util:include page="/html/portlet/document_library/sort_button.jsp" />
</span>

<span class="manage-button">
	<liferay-ui:icon-menu align="left" direction="down" icon="" message="manage" showExpanded="<%= false %>" showWhenSingleIcon="<%= true %>">

		<%
		String taglibUrl = "javascript:" + renderResponse.getNamespace() + "openFileEntryTypeView()";
		%>

		<liferay-ui:icon
			image="copy"
			message="document-types"
			url="<%= taglibUrl %>"
		/>

		<%
		taglibUrl = "javascript:" + renderResponse.getNamespace() + "openDDMStructureView()";
		%>

		<liferay-ui:icon
			image="copy"
			message="metadata-sets"
			url="<%= taglibUrl %>"
		/>
	</liferay-ui:icon-menu>
</span>

<aui:script>
	function <portlet:namespace />openFileEntryTypeView() {
		Liferay.Util.openWindow(
			{
				dialog: {
					stack: false,
					width:820
				},
				title: '<liferay-ui:message key="document-types" />',
				uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/document_library/view_file_entry_type" /><portlet:param name="redirect" value="<%= currentURL %>" /></liferay-portlet:renderURL>'
			}
		);
	}

	function <portlet:namespace />openDDMStructureView() {
		Liferay.Util.openDDMPortlet(
			{
				dialog: {
					stack: false,
					width:820
				},
				showManageTemplates: 'false',
				storageType: 'xml',
				structureName: 'metadata-set',
				structureType: 'com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata',
				title: '<liferay-ui:message key="metadata-sets" />'
			}
		);
	}
</aui:script>