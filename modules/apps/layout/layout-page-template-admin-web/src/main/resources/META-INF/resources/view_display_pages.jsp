<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DisplayPageDisplayContext displayPageDisplayContext = new DisplayPageDisplayContext(request, renderRequest, renderResponse);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= layoutPageTemplatesAdminDisplayContext.getNavigationItems() %>"
/>

<liferay-ui:success key="displayPagePublished" message="the-display-page-template-was-published-successfully" />

<%
DisplayPageManagementToolbarDisplayContext displayPageManagementToolbarDisplayContext = new DisplayPageManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, displayPageDisplayContext);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= displayPageManagementToolbarDisplayContext %>"
	propsTransformer="js/propsTransformers/DisplayPageManagementToolbarPropsTransformer"
/>

<portlet:actionURL name="/layout_page_template_admin/delete_layout_page_template_entry" var="deleteDisplayPageURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteDisplayPageURL %>" cssClass="container-fluid container-fluid-max-xl" name="fm">
	<liferay-ui:error key="<%= RequiredLayoutPageTemplateEntryException.class.getName() %>" message="you-cannot-delete-display-page-templates-that-are-used-by-one-or-more-items.-please-view-the-usages-and-try-to-unassign-them" />

	<liferay-ui:success key="displayPageTemplateDeleted" message='<%= GetterUtil.getString(MultiSessionMessages.get(renderRequest, "displayPageTemplateDeleted")) %>' />

	<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPS-189856") %>'>
		<liferay-site-navigation:breadcrumb
			breadcrumbEntries="<%= displayPageDisplayContext.getLayoutPageTemplateBreadcrumbEntries() %>"
		/>
	</c:if>

	<liferay-ui:search-container
		id="displayPages"
		searchContainer="<%= displayPageDisplayContext.getDisplayPagesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="Object"
			modelVar="object"
		>

			<%
			LayoutPageTemplateCollection curLayoutPageTemplateCollection = null;
			LayoutPageTemplateEntry curLayoutPageTemplateEntry = null;

			Object result = row.getObject();

			if (result instanceof LayoutPageTemplateEntry) {
				curLayoutPageTemplateEntry = (LayoutPageTemplateEntry)result;
			}
			else {
				curLayoutPageTemplateCollection = (LayoutPageTemplateCollection)result;
			}
			%>

			<c:choose>
				<c:when test="<%= curLayoutPageTemplateCollection != null %>">

					<%
					row.setCssClass("card-page-item card-page-item-directory " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<clay:horizontal-card
							horizontalCard="<%= new DisplayPageTemplateCollectionHorizontalCard (curLayoutPageTemplateCollection, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
							propsTransformer="js/propsTransformers/LayoutPageTemplateCollectionPropsTransformer"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test="<%= curLayoutPageTemplateEntry != null %>">

					<%
					row.setData(
						HashMapBuilder.<String, Object>put(
							"actions", displayPageManagementToolbarDisplayContext.getAvailableActions(curLayoutPageTemplateEntry)
						).build());
					%>

					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							additionalProps='<%=
								HashMapBuilder.<String, Object>put(
									"changeContentTypeURL", displayPageDisplayContext.getChangeContentTypeURL(curLayoutPageTemplateEntry)
								).put(
									"mappingTypes", displayPageDisplayContext.getMappingTypesJSONArray()
								).build()
							%>'
							propsTransformer="js/propsTransformers/DisplayPageDropdownPropsTransformer"
							verticalCard="<%= new DisplayPageVerticalCard(curLayoutPageTemplateEntry, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
			resultRowSplitter="<%= displayPageDisplayContext.isSearch() ? null : new LayoutPageTemplateResultRowSplitter() %>"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="/layout_page_template_admin/update_layout_page_template_entry_preview" var="updateLayoutPageTemplateEntryPreviewURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= updateLayoutPageTemplateEntryPreviewURL %>" name="layoutPageTemplateEntryPreviewFm">
	<aui:input name="layoutPageTemplateEntryId" type="hidden" />
	<aui:input name="fileEntryId" type="hidden" />
</aui:form>