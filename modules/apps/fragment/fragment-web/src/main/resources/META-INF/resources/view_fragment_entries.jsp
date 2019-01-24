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
FragmentManagementToolbarDisplayContext fragmentManagementToolbarDisplayContext = new FragmentManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, fragmentDisplayContext);
%>

<liferay-ui:error exception="<%= RequiredFragmentEntryException.class %>" message="the-fragment-entry-cannot-be-deleted-because-it-is-required-by-one-or-more-page-templates" />

<clay:management-toolbar
	displayContext="<%= fragmentManagementToolbarDisplayContext %>"
/>

<aui:form name="fm">
	<liferay-ui:search-container
		searchContainer="<%= fragmentDisplayContext.getFragmentEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.fragment.model.FragmentEntry"
			keyProperty="fragmentEntryId"
			modelVar="fragmentEntry"
		>

			<%
			row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
			%>

			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					verticalCard="<%= new FragmentEntryVerticalCard(fragmentEntry, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
			resultRowSplitter="<%= fragmentDisplayContext.isSearch() ? null : new FragmentEntryResultRowSplitter() %>"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="/fragment/update_fragment_entry_preview" var="updateFragmentEntryPreviewURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= updateFragmentEntryPreviewURL %>" name="fragmentEntryPreviewFm">
	<aui:input name="fragmentEntryId" type="hidden" />
	<aui:input name="fileEntryId" type="hidden" />
</aui:form>

<portlet:actionURL name="/fragment/move_fragment_entry" var="moveFragmentEntryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= moveFragmentEntryURL %>" name="moveFragmentEntryFm">
	<aui:input name="fragmentEntryIds" type="hidden" />
	<aui:input name="fragmentCollectionId" type="hidden" />
</aui:form>

<c:if test="<%= FragmentPermission.contains(permissionChecker, scopeGroupId, FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES) %>">
	<aui:script require='<%= npmResolvedPackageName + "/js/ElementsDefaultEventHandler.es as ElementsDefaultEventHandler" %>'>
		Liferay.component(
			'<%= FragmentWebKeys.FRAGMENT_ENTRY_ELEMENTS_DEFAULT_EVENT_HANDLER %>',
			new ElementsDefaultEventHandler.default(
				{
					namespace: '<portlet:namespace />',
					spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
				}
			),
			{
				destroyOnNavigate: true,
				portletId: '<%= HtmlUtil.escapeJS(portletDisplay.getId()) %>'
			}
		);
	</aui:script>
</c:if>

<aui:script require='<%= npmResolvedPackageName + "/js/ManagementToolbarDefaultEventHandler.es as ManagementToolbarDefaultEventHandler" %>'>
	Liferay.component(
		'<%= fragmentManagementToolbarDisplayContext.getDefaultEventHandler() %>',
		new ManagementToolbarDefaultEventHandler.default(
			{
				deleteFragmentEntriesURL: '<portlet:actionURL name="/fragment/delete_fragment_entries"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>',
				exportFragmentEntriesURL: '<portlet:resourceURL id="/fragment/export_fragment_entries" />',
				namespace: '<portlet:namespace />',
				selectFragmentCollectionURL: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/fragment/select_fragment_collection" /></portlet:renderURL>',
				spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
			}
		),
		{
			destroyOnNavigate: true,
			portletId: '<%= HtmlUtil.escapeJS(portletDisplay.getId()) %>'
		}
	);
</aui:script>