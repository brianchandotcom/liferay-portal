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
FragmentManagementToolbarDisplayContextFactory fragmentManagementToolbarDisplayContextFactory = FragmentManagementToolbarDisplayContextFactory.getInstance();

FragmentManagementToolbarDisplayContext fragmentManagementToolbarDisplayContext = fragmentManagementToolbarDisplayContextFactory.getFragmentManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, fragmentDisplayContext);
%>

<liferay-ui:error exception="<%= RequiredFragmentEntryException.class %>" message="the-fragment-entry-cannot-be-deleted-because-it-is-required-by-one-or-more-page-templates" />

<clay:management-toolbar
	additionalProps="<%= fragmentManagementToolbarDisplayContext.getComponentContext() %>"
	managementToolbarDisplayContext="<%= fragmentManagementToolbarDisplayContext %>"
	propsTransformer="js/ViewFragmentEntriesManagementToolbarPropsTransformer"
/>

<aui:form name="fm">
	<liferay-ui:search-container
		searchContainer="<%= fragmentDisplayContext.getFragmentEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="Object"
			modelVar="object"
		>

			<%
			row.setData(
				HashMapBuilder.<String, Object>put(
					"actions", fragmentDisplayContext.getAvailableActions(object)
				).build());

			FragmentEntryVerticalCardFactory fragmentEntryVerticalCardFactory = FragmentEntryVerticalCardFactory.getInstance();
			%>

			<liferay-ui:search-container-column-text>
				<c:choose>
					<c:when test="<%= object instanceof FragmentComposition %>">
						<clay:vertical-card
							propsTransformer="js/FragmentCompositionDropdownPropsTransformer"
							verticalCard="<%= fragmentEntryVerticalCardFactory.getVerticalCard((FragmentComposition)object, renderRequest, renderResponse, searchContainer.getRowChecker(), fragmentDisplayContext.getFragmentType()) %>"
						/>
					</c:when>
					<c:otherwise>
						<clay:vertical-card
							propsTransformer="js/FragmentEntryDropdownPropsTransformer"
							verticalCard="<%= fragmentEntryVerticalCardFactory.getVerticalCard((FragmentEntry)object, renderRequest, renderResponse, searchContainer.getRowChecker(), fragmentDisplayContext.getFragmentType()) %>"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="/fragment/update_fragment_composition_preview" var="updateFragmentCompositionPreviewURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= updateFragmentCompositionPreviewURL %>" name="fragmentCompositionPreviewFm">
	<aui:input name="fragmentCompositionId" type="hidden" />
	<aui:input id="fragmentCompositionFileEntryId" name="fileEntryId" type="hidden" />
</aui:form>

<aui:form name="fragmentEntryFm">
	<aui:input name="fragmentCollectionId" type="hidden" />
	<aui:input name="fragmentCompositionId" type="hidden" />
	<aui:input name="fragmentCompositionIds" type="hidden" />
	<aui:input name="fragmentEntryIds" type="hidden" />
</aui:form>

<portlet:actionURL name="/fragment/update_fragment_entry_preview" var="updateFragmentEntryPreviewURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= updateFragmentEntryPreviewURL %>" name="fragmentEntryPreviewFm">
	<aui:input name="fragmentEntryId" type="hidden" />
	<aui:input name="fileEntryId" type="hidden" />
</aui:form>