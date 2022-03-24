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

<%@ include file="/init.jsp" %>

<%
SegmentsDisplayContext segmentsDisplayContext = (SegmentsDisplayContext)request.getAttribute(SegmentsWebKeys.SEGMENTS_DISPLAY_CONTEXT);

String eventName = liferayPortletResponse.getNamespace() + "assignSiteRoles";

request.setAttribute("view.jsp-eventName", eventName);
%>

<clay:management-toolbar
	actionDropdownItems="<%= segmentsDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= segmentsDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= segmentsDisplayContext.getCreationMenu() %>"
	disabled="<%= segmentsDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= segmentsDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= segmentsDisplayContext.getTotalItems() %>"
	propsTransformer="js/SegmentsManagementToolbarPropsTransformer"
	searchActionURL="<%= segmentsDisplayContext.getSearchActionURL() %>"
	searchContainerId="segmentsEntries"
	searchFormName="searchFm"
	selectable="<%= true %>"
	showCreationMenu="<%= segmentsDisplayContext.isShowCreationMenu() %>"
	sortingOrder="<%= segmentsDisplayContext.getOrderByType() %>"
	sortingURL="<%= segmentsDisplayContext.getSortingURL() %>"
/>

<c:if test="<%= !segmentsDisplayContext.isSegmentationEnabled(themeDisplay.getCompanyId()) %>">
	<clay:stripe
		defaultTitleDisabled="<%= true %>"
		dismissible="<%= true %>"
		displayType="warning"
	>
		<strong><liferay-ui:message key="segmentation-is-disabled" /></strong>

		<%
		String segmentsConfigurationURL = segmentsDisplayContext.getSegmentsCompanyConfigurationURL(request);
		%>

		<c:choose>
			<c:when test="<%= segmentsConfigurationURL != null %>">
				<clay:link
					href="<%= segmentsConfigurationURL %>"
					label='<%= LanguageUtil.get(request, "to-enable,-go-to-instance-settings") %>'
				/>
			</c:when>
			<c:otherwise>
				<span><%=
				LanguageUtil.get(
					request, "contact-your-system-administrator-to-enable-it") %></span>
			</c:otherwise>
		</c:choose>
	</clay:stripe>
</c:if>

<portlet:actionURL name="/segments/delete_segments_entry" var="deleteSegmentsEntryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteSegmentsEntryURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fmSegmentsEntries">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-ui:error exception="<%= RequiredSegmentsEntryException.MustNotDeleteSegmentsEntryReferencedBySegmentsExperiences.class %>" message="the-segment-cannot-be-deleted-because-it-is-required-by-one-or-more-experiences" />

	<liferay-ui:search-container
		id="segmentsEntries"
		searchContainer="<%= segmentsDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.segments.model.SegmentsEntry"
			keyProperty="segmentsEntryId"
			modelVar="segmentsEntry"
		>

			<%
			row.setData(
				HashMapBuilder.<String, Object>put(
					"actions", segmentsDisplayContext.getAvailableActions(segmentsEntry)
				).build());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-title"
				href="<%= segmentsDisplayContext.getSegmentsEntryURL(segmentsEntry) %>"
				name="name"
				target="<%= segmentsDisplayContext.getSegmentsEntryURLTarget(segmentsEntry) %>"
				value="<%= HtmlUtil.escape(segmentsEntry.getName(locale)) %>"
			/>

			<c:if test="<%= segmentsDisplayContext.isAsahEnabled(themeDisplay.getCompanyId()) %>">
				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest table-cell-minw-150"
					name="source"
				>

					<%
					SegmentsSourceDetailsProvider segmentsSourceDetailsProvider = SegmentsSourceDetailsProviderUtil.getSegmentsSourceDetailsProvider(segmentsEntry);
					%>

					<c:if test="<%= segmentsSourceDetailsProvider != null %>">
						<liferay-ui:icon
							message="<%= segmentsSourceDetailsProvider.getLabel(locale) %>"
							src="<%= segmentsSourceDetailsProvider.getIconSrc() %>"
						/>
					</c:if>
				</liferay-ui:search-container-column-text>
			</c:if>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-minw-150"
				name="scope"
			>
				<c:choose>
					<c:when test="<%= segmentsEntry.getGroupId() == themeDisplay.getCompanyGroupId() %>">
						<liferay-ui:message key="global" />
					</c:when>
					<c:when test="<%= segmentsEntry.getGroupId() == themeDisplay.getScopeGroupId() %>">
						<liferay-ui:message key="current-site" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="parent-site" />
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-expand-smallest table-cell-minw-150 table-cell-ws-nowrap"
				name="modified-date"
				value="<%= segmentsEntry.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/segments_entry_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="/segments/update_segments_entry_site_roles" var="updateSegmentsEntrySiteRolesURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= updateSegmentsEntrySiteRolesURL %>" cssClass="hide" method="post" name="updateSegmentsEntrySiteRolesFm">
	<aui:input name="segmentsEntryId" type="hidden" />
	<aui:input name="siteRoleIds" type="hidden" />
</aui:form>

<aui:script require="frontend-js-web/index as frontendJsWeb">
	var {delegate} = frontendJsWeb;

	var form = document.getElementById(
		'<portlet:namespace />updateSegmentsEntrySiteRolesFm'
	);

	var delegateHandler = delegate(
		document,
		'click',
		'.assign-site-roles-link',
		(event) => {
			var link = event.target.closest('.assign-site-roles-link');

			var itemSelectorURL = link.dataset.itemselectorurl;
			var segmentsEntryId = link.dataset.segmentsentryid;

			Liferay.Util.openSelectionModal({
				eventName: '<%= eventName %>',
				multiple: true,
				onSelect: function (selectedItems) {
					if (selectedItems) {
						var data = {
							segmentsEntryId: segmentsEntryId,
							siteRoleIds: selectedItems.map((item) => item.value),
						};

						Liferay.Util.postForm(form, {data: data});
					}
				},
				title: '<liferay-ui:message key="assign-site-roles" />',
				url: itemSelectorURL,
			});
		}
	);

	var onDestroyPortlet = function () {
		delegateHandler.dispose();

		Liferay.detach('destroyPortlet', onDestroyPortlet);
	};

	Liferay.on('destroyPortlet', onDestroyPortlet);
</aui:script>