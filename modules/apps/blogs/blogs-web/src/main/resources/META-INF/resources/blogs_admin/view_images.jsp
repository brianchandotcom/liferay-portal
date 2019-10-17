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
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);
String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/blogs/view");
portletURL.setParameter("navigation", "images");

if (delta > 0) {
	portletURL.setParameter("delta", String.valueOf(delta));
}

portletURL.setParameter("orderBycol", orderByCol);
portletURL.setParameter("orderByType", orderByType);

request.setAttribute("view_images.jsp-portletURL", portletURL);

SearchContainer blogImagesSearchContainer = new SearchContainer(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse), null, "no-images-were-found");

blogImagesSearchContainer.setOrderByComparator(DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType));

blogImagesSearchContainer.setRowChecker(new EmptyOnClickRowChecker(renderResponse));

BlogImagesDisplayContext blogImagesDisplayContext = new BlogImagesDisplayContext(liferayPortletRequest);

blogImagesDisplayContext.populateResults(blogImagesSearchContainer);

BlogImagesManagementToolbarDisplayContext blogImagesManagementToolbarDisplayContext = new BlogImagesManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, currentURLObj);

String displayStyle = blogImagesManagementToolbarDisplayContext.getDisplayStyle();
%>

<clay:management-toolbar
	actionDropdownItems="<%= blogImagesManagementToolbarDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= blogImagesManagementToolbarDisplayContext.getSearchActionURL() %>"
	componentId="blogImagesManagementToolbar"
	disabled="<%= blogImagesSearchContainer.getTotal() <= 0 %>"
	filterDropdownItems="<%= blogImagesManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= blogImagesSearchContainer.getTotal() %>"
	searchActionURL="<%= blogImagesManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="images"
	searchFormName="searchFm"
	showCreationMenu="<%= false %>"
	showInfoButton="<%= false %>"
	sortingOrder="<%= blogImagesManagementToolbarDisplayContext.getOrderByType() %>"
	sortingURL="<%= String.valueOf(blogImagesManagementToolbarDisplayContext.getSortingURL()) %>"
	viewTypeItems="<%= blogImagesManagementToolbarDisplayContext.getViewTypes() %>"
/>

<div class="container-fluid-1280 main-content-body">
	<portlet:actionURL name="/blogs/edit_image" var="editImageURL" />

	<aui:form action="<%= editImageURL %>" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="deleteFileEntryIds" type="hidden" />

		<liferay-ui:search-container
			id="images"
			searchContainer="<%= blogImagesSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.repository.model.FileEntry"
				keyProperty="fileEntryId"
				modelVar="fileEntry"
			>
				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/blogs/edit_image" />
					<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
				</liferay-portlet:renderURL>

				<%
				Map<String, Object> rowData = new HashMap<>();

				rowData.put("actions", StringUtil.merge(blogImagesManagementToolbarDisplayContext.getAvailableActions(fileEntry)));

				row.setData(rowData);
				%>

				<%@ include file="/blogs_admin/image_search_columns.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= displayStyle %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	var deleteImages = function() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-images" />'
			)
		) {
			var form = document.getElementById('<portlet:namespace />fm');

			if (form) {
				var cmd = form.querySelector(
					'#<portlet:namespace /><%= Constants.CMD %>'
				);

				if (cmd) {
					cmd.setAttribute('value', '<%= Constants.DELETE %>');
				}

				var deleteFileEntryIds = form.querySelector(
					'#<portlet:namespace />deleteFileEntryIds'
				);

				if (deleteFileEntryIds) {
					deleteFileEntryIds.setAttribute(
						'value',
						Liferay.Util.listCheckedExcept(
							form,
							'<portlet:namespace />allRowIds'
						)
					);
				}

				submitForm(form);
			}
		}
	};

	var ACTIONS = {
		deleteImages: deleteImages
	};

	Liferay.componentReady('blogImagesManagementToolbar').then(function(
		managementToolbar
	) {
		managementToolbar.on('actionItemClicked', function(event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>