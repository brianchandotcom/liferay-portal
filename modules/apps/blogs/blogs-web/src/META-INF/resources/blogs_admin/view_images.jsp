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

<%@ include file="/blogs_admin/init.jsp" %>

<%
String displayStyle = ParamUtil.getString(request, "displayStyle", "icon");

Folder attachmentsFolder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(themeDisplay.getUserId(), scopeGroupId);

String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/blogs_admin/view");
portletURL.setParameter("navigation", "images");
%>

<liferay-frontend:management-bar
	checkBoxContainerId="imagesSearchContainer"
	includeCheckBox="<%= true %>"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayStyleURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"title", "size"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>

		<%
		String taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteImages();";
		%>

		<aui:a cssClass="btn" href="<%= taglibURL %>" iconCssClass="icon-remove" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<portlet:actionURL name="/blogs/edit_image" var="editImageURL" />

	<aui:form action="<%= editImageURL %>" cssClass="row" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="deleteFileEntryIds" type="hidden" />

		<liferay-ui:search-container
			id="images"
			orderByComparator="<%= DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType) %>"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			searchContainer="<%= new EntrySearch(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse)) %>"
			total="<%= PortletFileRepositoryUtil.getPortletFileEntriesCount(scopeGroupId, attachmentsFolder.getFolderId()) %>"
		>

			<liferay-ui:search-container-results
				results="<%= PortletFileRepositoryUtil.getPortletFileEntries(scopeGroupId, attachmentsFolder.getFolderId(), WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.repository.model.FileEntry"
				keyProperty="fileEntryId"
				modelVar="fileEntry"
			>
				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/blogs/edit_image" />
					<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
				</liferay-portlet:renderURL>

				<%@ include file="/blogs_admin/image_search_columns.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteImages() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-images") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteFileEntryIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>