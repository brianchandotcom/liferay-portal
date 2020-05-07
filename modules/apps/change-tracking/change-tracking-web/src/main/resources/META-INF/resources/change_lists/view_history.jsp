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

<%@ include file="/change_lists/init.jsp" %>

<%
ViewHistoryDisplayContext viewHistoryDisplayContext = (ViewHistoryDisplayContext)request.getAttribute(CTWebKeys.VIEW_HISTORY_DISPLAY_CONTEXT);

SearchContainer<CTProcess> searchContainer = viewHistoryDisplayContext.getSearchContainer();

ViewHistoryManagementToolbarDisplayContext viewHistoryManagementToolbarDisplayContext = new ViewHistoryManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, searchContainer, viewHistoryDisplayContext);

Format format = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= viewHistoryDisplayContext.getViewNavigationItems() %>"
/>

<clay:management-toolbar
	displayContext="<%= viewHistoryManagementToolbarDisplayContext %>"
/>

<clay:container>
	<liferay-ui:search-container
		cssClass="change-lists-table"
		searchContainer="<%= searchContainer %>"
		var="reviewChangesSearchContainer"
	>
		<liferay-ui:search-container-row
			className="com.liferay.change.tracking.model.CTProcess"
			escapedModel="<%= true %>"
			keyProperty="ctProcessId"
			modelVar="ctProcess"
		>

			<%
			CTCollection ctCollection = viewHistoryDisplayContext.getCtCollection(ctProcess);
			int status = viewHistoryDisplayContext.getStatus(ctProcess);
			%>

			<liferay-portlet:renderURL var="changesURL">
				<portlet:param name="mvcRenderCommandName" value="/change_lists/view_changes" />
				<portlet:param name="backURL" value="<%= currentURL %>" />
				<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
			</liferay-portlet:renderURL>

			<c:choose>
				<c:when test='<%= Objects.equals(viewHistoryDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-text>
						<span class="lfr-portal-tooltip" title="<%= ctCollection.getUserName() %>">
							<liferay-ui:user-portrait
								userId="<%= ctProcess.getUserId() %>"
							/>
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="autofit-col-expand"
					>
						<c:choose>
							<c:when test="<%= status == BackgroundTaskConstants.STATUS_SUCCESSFUL %>">
								<a href="<%= changesURL %>">
									<%@ include file="/change_lists/publication_info_escaped.jspf" %>
								</a>
							</c:when>
							<c:otherwise>
								<%@ include file="/change_lists/publication_info_escaped.jspf" %>
							</c:otherwise>
						</c:choose>

						<div>
							<clay:label
								label="<%= LanguageUtil.get(resourceBundle, viewHistoryDisplayContext.getStatusLabel(status)) %>"
								style="<%= viewHistoryDisplayContext.getStatusStyle(status) %>"
							/>
						</div>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="publication"
					>
						<c:choose>
							<c:when test="<%= status == BackgroundTaskConstants.STATUS_SUCCESSFUL %>">
								<a href="<%= changesURL %>">
									<%@ include file="/change_lists/publication_info_escaped.jspf" %>
								</a>
							</c:when>
							<c:otherwise>
								<%@ include file="/change_lists/publication_info_escaped.jspf" %>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smaller"
						name="published-date"
						value="<%= format.format(ctProcess.getCreateDate()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smallest text-center"
						name="published-by"
					>
						<span class="lfr-portal-tooltip" title="<%= ctCollection.getUserName() %>">
							<liferay-ui:user-portrait
								userId="<%= ctProcess.getUserId() %>"
							/>
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smaller"
						name="status"
					>
						<clay:label
							label="<%= LanguageUtil.get(resourceBundle, viewHistoryDisplayContext.getStatusLabel(status)) %>"
							style="<%= viewHistoryDisplayContext.getStatusStyle(status) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:otherwise>
			</c:choose>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest"
			>
				<liferay-portlet:renderURL var="revertURL">
					<portlet:param name="mvcRenderCommandName" value="/change_lists/undo_ct_collection" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
					<portlet:param name="revert" value="true" />
				</liferay-portlet:renderURL>

				<a class="btn btn-secondary btn-sm <%= (status != BackgroundTaskConstants.STATUS_SUCCESSFUL) ? "disabled" : StringPool.BLANK %>" href="<%= revertURL %>" type="button">
					<liferay-ui:message key="revert" />
				</a>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= viewHistoryDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
			searchContainer="<%= searchContainer %>"
		/>
	</liferay-ui:search-container>
</clay:container>