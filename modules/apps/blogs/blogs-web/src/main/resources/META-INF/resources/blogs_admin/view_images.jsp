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
Folder attachmentsFolder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(themeDisplay.getUserId(), scopeGroupId);

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(BlogsPortletKeys.BLOGS_ADMIN, "images-display-style", "icon");
}
else {
	portalPreferences.setValue(BlogsPortletKeys.BLOGS_ADMIN, "images-display-style", displayStyle);
}

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);
String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/blogs_admin/view");
portletURL.setParameter("navigation", "images");
portletURL.setParameter("orderBycol", orderByCol);
portletURL.setParameter("orderByType", orderByType);

if (delta > 0) {
	portletURL.setParameter("delta", String.valueOf(delta));
}

PortletURL displayStyleURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

if (cur > 0) {
	displayStyleURL.setParameter("cur", String.valueOf(cur));
}

request.setAttribute("view_images.jsp-portletURL", portletURL);

String keywords = ParamUtil.getString(request, "keywords");
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="images"
>
	<c:if test="<%= Validator.isNull(keywords) %>">
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
				portletURL="<%= displayStyleURL %>"
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
	</c:if>

	<liferay-frontend:management-bar-action-buttons>

		<%
		String taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteImages();";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" iconCssClass="icon-remove" label="delete" />
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
			searchContainer='<%= new SearchContainer(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse), null, "no-images-were-found") %>'
		>

			<%
			List<FileEntry> results = null;

			if (Validator.isNull(keywords)) {
				total = PortletFileRepositoryUtil.getPortletFileEntriesCount(scopeGroupId, attachmentsFolder.getFolderId());
				results = PortletFileRepositoryUtil.getPortletFileEntries(scopeGroupId, attachmentsFolder.getFolderId(), WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
			}
			else {
				SearchContext searchContext = SearchContextFactory.getInstance(request);

				searchContext.setEnd(searchContainer.getEnd());
				searchContext.setFolderIds(new long[] {attachmentsFolder.getFolderId()});
				searchContext.setStart(searchContainer.getStart());

				Folder folder = DLAppLocalServiceUtil.getFolder(attachmentsFolder.getFolderId());

				Hits hits = PortletFileRepositoryUtil.searchPortletFileEntries(folder.getRepositoryId(), searchContext);

				total = hits.getLength();

				Document[] docs = hits.getDocs();

				results = new ArrayList<FileEntry>();

				for (Document doc : docs) {
					long fileEntryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

					FileEntry fileEntry = null;

					try {
						fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);

						results.add(fileEntry);
					}
					catch (Exception e) {
						if (_log.isWarnEnabled()) {
							_log.warn("Documents and Media search index is stale and contains file entry {" + fileEntryId + "}");
						}

						continue;
					}
				}
			}

			searchContainer.setTotal(total);
			searchContainer.setResults(results);
			%>

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

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_blogs_web.blogs_admin.view_images_jsp");
%>