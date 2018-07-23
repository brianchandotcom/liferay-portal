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
boolean showBackURL = ParamUtil.getBoolean(request, "showBackURL", true);

if (ddmDisplay.getDescription(locale) != null) {
	portletDisplay.setDescription(ddmDisplay.getDescription(locale));
}

if (ddmDisplay.getTitle(locale) != null) {
	renderResponse.setTitle(ddmDisplay.getTitle(locale));
}
%>

<c:if test="<%= showBackURL && ddmDisplay.isShowBackURLInTitleBar() %>">

	<%
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(ddmDisplay.getViewStructuresBackURL(liferayPortletRequest, liferayPortletResponse));
	%>

</c:if>

<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureReferencedByStructureLinks.class %>" message="the-structure-cannot-be-deleted-because-it-is-required-by-one-or-more-structure-links" />
<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureReferencedByTemplates.class %>" message="the-structure-cannot-be-deleted-because-it-is-required-by-one-or-more-templates" />
<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureThatHasChild.class %>" message="the-structure-cannot-be-deleted-because-it-has-one-or-more-substructures" />

<liferay-ui:success key='<%= DDMPortletKeys.DYNAMIC_DATA_MAPPING + "requestProcessed" %>' message="your-request-completed-successfully" />

<liferay-util:include page="/navigation_bar.jsp" servletContext="<%= application %>" />

<clay:management-toolbar
	actionDropdownItems='<%= ddmDisplayContext.getActionItemsDropdownItems("deleteStructures") %>'
	clearResultsURL="<%= ddmDisplayContext.getClearResultsURL() %>"
	componentId="ddmStructureManagementToolbar"
	creationMenu="<%= ddmDisplayContext.getStructureCreationMenu() %>"
	disabled="<%= ddmDisplayContext.isDisabledManagementBar(DDMWebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE) %>"
	filterDropdownItems="<%= ddmDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddmDisplayContext.getTotalItems(DDMWebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE) %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= ddmDisplayContext.getStructureSearchActionURL() %>"
	searchContainerId="<%= ddmDisplayContext.getStructureSearchContainerId() %>"
	searchFormName="fm1"
	selectable="<%= !user.isDefaultUser() %>"
	sortingOrder="<%= ddmDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddmDisplayContext.getSortingURL() %>"
/>

<aui:form action="<%= ddmDisplayContext.getStructureSearchActionURL() %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= ddmDisplayContext.getStructureSearchActionURL() %>" />
	<aui:input name="deleteStructureIds" type="hidden" />

	<div class="container-fluid-1280" id="<portlet:namespace />entriesContainer">
		<liferay-ui:search-container
			id="ddmStructures"
			rowChecker="<%= new DDMStructureRowChecker(renderResponse) %>"
			searchContainer="<%= ddmDisplayContext.getStructureSearch() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.mapping.model.DDMStructure"
				keyProperty="structureId"
				modelVar="structure"
			>

				<%
				String rowHREF = StringPool.BLANK;

				if (DDMStructurePermission.contains(permissionChecker, structure, ActionKeys.UPDATE)) {
					PortletURL rowURL = renderResponse.createRenderURL();

					rowURL.setParameter("mvcPath", "/edit_structure.jsp");
					rowURL.setParameter("redirect", currentURL);
					rowURL.setParameter("classNameId", String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)));
					rowURL.setParameter("classPK", String.valueOf(structure.getStructureId()));

					rowHREF = rowURL.toString();
				}
				%>

				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="id"
					orderable="<%= true %>"
					orderableProperty="id"
					property="structureId"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					href="<%= rowHREF %>"
					name="name"
					value="<%= HtmlUtil.escape(structure.getName(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					href="<%= rowHREF %>"
					name="description"
					truncate="<%= true %>"
					value="<%= HtmlUtil.escape(structure.getDescription(locale)) %>"
				/>

				<c:if test="<%= Validator.isNull(storageTypeValue) %>">
					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="storage-type"
						value="<%= LanguageUtil.get(request, HtmlUtil.escape(structure.getStorageType())) %>"
					/>
				</c:if>

				<c:if test="<%= scopeClassNameId == 0 %>">
					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="type"
						value="<%= HtmlUtil.escape(ResourceActionsUtil.getModelResource(locale, structure.getClassName())) %>"
					/>
				</c:if>

				<%
				Group group = GroupLocalServiceUtil.getGroup(structure.getGroupId());
				%>

				<liferay-ui:search-container-column-text
					name="scope"
					value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
				/>

				<liferay-ui:search-container-column-date
					href="<%= rowHREF %>"
					name="modified-date"
					orderable="<%= true %>"
					orderableProperty="modified-date"
					value="<%= structure.getModifiedDate() %>"
				/>

				<liferay-ui:search-container-column-jsp
					path="/structure_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</div>
</aui:form>

<aui:script sandbox="<%= true %>">
	var deleteStructures = function() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			var searchContainer = AUI.$('#<portlet:namespace />entriesContainer', form);

			form.attr('method', 'post');
			form.fm('deleteStructureIds').val(Liferay.Util.listCheckedExcept(searchContainer, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteStructure"><portlet:param name="mvcPath" value="/view.jsp" /></portlet:actionURL>');
		}
	};

	var ACTIONS = {
		'deleteStructures': deleteStructures
	};

	Liferay.componentReady('ddmStructureManagementToolbar').then(
		function(managementToolbar) {
			managementToolbar.on(
				'actionItemClicked',
				function(event) {
					var itemData = event.data.item.data;

					if (itemData && itemData.action && ACTIONS[itemData.action]) {
						ACTIONS[itemData.action]();
					}
				}
			);
		}
	);
</aui:script>