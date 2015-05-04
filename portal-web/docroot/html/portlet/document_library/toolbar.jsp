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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String strutsAction = ParamUtil.getString(request, "struts_action");

Folder folder = (Folder)request.getAttribute("view.jsp-folder");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

String keywords = ParamUtil.getString(request, "keywords");

boolean isSearch = strutsAction.equals("/document_library/search");
%>

<aui:nav-bar>
	<aui:nav collapsible="<%= true %>" cssClass="nav-display-style-buttons navbar-nav" icon="th-list" id="displayStyleButtons">
		<c:if test="<%= !isSearch %>">
			<liferay-util:include page="/html/portlet/document_library/display_style_buttons.jsp" />
		</c:if>
	</aui:nav>

	<aui:nav cssClass="navbar-nav" id="toolbarContainer">
		<aui:nav-item cssClass="hide" dropdown="<%= true %>" id="actionsButtonContainer" label="actions">

			<%
			Group scopeGroup = themeDisplay.getScopeGroup();
			%>

			<c:if test="<%= !scopeGroup.isStaged() || scopeGroup.isStagingGroup() || !scopeGroup.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY) %>">

				<%
				String taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.CANCEL_CHECKOUT + "'}); void(0);";
				%>

				<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-remove" label="cancel-checkout[document]" />

				<%
				taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.CHECKIN + "'}); void(0);";
				%>

				<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-lock" label="checkin" />

				<%
				taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.CHECKOUT + "'}); void(0);";
				%>

				<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-unlock" label="checkout[document]" />

				<%
				taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.MOVE + "'}); void(0);";
				%>

				<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-move" label="move" />
			</c:if>

			<%
			String taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.MOVE_TO_TRASH + "'}); void(0);";
			%>

			<aui:nav-item cssClass="item-remove" href="<%= taglibURL %>" iconCssClass="icon-trash" id="moveToTrashAction" label="move-to-the-recycle-bin" />

			<%
			taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteEntries();";
			%>

			<aui:nav-item cssClass="item-remove" href="<%= taglibURL %>" iconCssClass="icon-remove" id="deleteAction" label="delete" />
		</aui:nav-item>

		<liferay-util:include page="/html/portlet/document_library/add_button.jsp" />

		<c:if test="<%= !isSearch %>">
			<liferay-util:include page="/html/portlet/document_library/sort_button.jsp" />
		</c:if>

		<c:if test="<%= !user.isDefaultUser() %>">
			<aui:nav-item dropdown="<%= true %>" label="manage">

				<%
				String taglibURL = "javascript:" + renderResponse.getNamespace() + "openFileEntryTypeView()";
				%>

				<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-file" label="document-types" />

				<%
				taglibURL = "javascript:" + renderResponse.getNamespace() + "openDDMStructureView()";
				%>

				<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-file-text" label="metadata-sets" />
			</aui:nav-item>
		</c:if>
	</aui:nav>

	<c:if test="<%= dlPortletInstanceSettings.isShowFoldersSearch() %>">
		<aui:nav-bar-search>
			<div class="form-search">
				<liferay-portlet:renderURL varImpl="searchURL">
					<portlet:param name="struts_action" value="/document_library/search" />
					<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					<portlet:param name="searchRepositoryId" value="<%= String.valueOf(repositoryId) %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
					<portlet:param name="searchFolderId" value="<%= String.valueOf(folderId) %>" />
					<portlet:param name="showRepositoryTabs" value="<%= (folderId == 0) ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" />
					<portlet:param name="showSearchInfo" value="<%= Boolean.TRUE.toString() %>" />
				</liferay-portlet:renderURL>

				<aui:form action="<%= searchURL %>" method="get" name="fm1">
					<liferay-portlet:renderURLParams varImpl="searchURL" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

					<liferay-ui:input-search />
				</aui:form>
			</div>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			Liferay.fire(
				'<%= renderResponse.getNamespace() %>editEntry',
				{
					action: '<%= Constants.DELETE %>'
				}
			);
		}
	}

	function <portlet:namespace />openFileEntryTypeView() {
		Liferay.Util.openWindow(
			{
				dialog: {
					destroyOnHide: true,
					on: {
						visibleChange: function(event) {
							if (!event.newVal) {
								Liferay.Portlet.refresh('#p_p_id_<%= portletDisplay.getId() %>_');
							}
						}
					}
				},
				id: '<portlet:namespace />openFileEntryTypeView',
				title: '<%= UnicodeLanguageUtil.get(request, "document-types") %>',
				uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/document_library/view_file_entry_type" /><portlet:param name="redirect" value="<%= currentURL %>" /></liferay-portlet:renderURL>'
			}
		);
	}

	function <portlet:namespace />openDDMStructureView() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				dialog: {
					destroyOnHide: true
				},
				groupId: <%= scopeGroupId %>,
				refererPortletName: '<%= PortletKeys.DOCUMENT_LIBRARY %>',
				showAncestorScopes: true,
				showManageTemplates: false,
				title: '<%= UnicodeLanguageUtil.get(request, "metadata-sets") %>'
			}
		);
	}
</aui:script>