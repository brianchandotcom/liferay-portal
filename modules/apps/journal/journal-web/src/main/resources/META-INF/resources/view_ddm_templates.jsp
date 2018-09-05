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
JournalDDMTemplateDisplayContext journalDDMTemplateDisplayContext = new JournalDDMTemplateDisplayContext(renderRequest, renderResponse);
%>

<liferay-ui:error exception="<%= RequiredTemplateException.MustNotDeleteTemplateReferencedByTemplateLinks.class %>" message="the-template-cannot-be-deleted-because-it-is-required-by-one-or-more-template-links" />

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%= journalDisplayContext.getNavigationBarItems("templates") %>'
/>

<clay:management-toolbar
	actionDropdownItems="<%= journalDDMTemplateDisplayContext.getActionItemsDropdownItems() %>"
	clearResultsURL="<%= journalDDMTemplateDisplayContext.getClearResultsURL() %>"
	componentId="ddmTemplateManagementToolbar"
	creationMenu="<%= journalDDMTemplateDisplayContext.isShowAddButton() ? journalDDMTemplateDisplayContext.getCreationMenu() : null %>"
	disabled="<%= journalDDMTemplateDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= journalDDMTemplateDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= journalDDMTemplateDisplayContext.getTotalItems() %>"
	searchActionURL="<%= journalDDMTemplateDisplayContext.getSearchActionURL() %>"
	searchContainerId="ddmTemplates"
	selectable="<%= !user.isDefaultUser() %>"
	sortingOrder="<%= journalDDMTemplateDisplayContext.getOrderByType() %>"
	sortingURL="<%= journalDDMTemplateDisplayContext.getSortingURL() %>"
/>

<portlet:actionURL name="/journal/delete_ddm_template" var="deleteDDMTemplateURL">
	<portlet:param name="mvcPath" value="/view_ddm_template.jsp" />
</portlet:actionURL>

<aui:form action="<%= deleteDDMTemplateURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-ui:search-container
		id="ddmTemplates"
		searchContainer="<%= journalDDMTemplateDisplayContext.getDDMTemplateSearch() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.mapping.model.DDMTemplate"
			keyProperty="templateId"
			modelVar="ddmTemplate"
		>

			<%
			String rowHREF = StringPool.BLANK;

			if (DDMTemplatePermission.contains(permissionChecker, ddmTemplate, ActionKeys.UPDATE)) {
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcPath", "/edit_ddm_template.jsp");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("ddmTemplateId", String.valueOf(ddmTemplate.getTemplateId()));

				rowHREF = rowURL.toString();
			}
			%>

			<liferay-ui:search-container-column-text
				name="id"
				property="templateId"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				href="<%= rowHREF %>"
				name="name"
				value="<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="description"
			>
				<c:choose>
					<c:when test="<%= ddmTemplate.isSmallImage() %>">
						<img alt="<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>" class="lfr-ddm-small-image-view" src="<%= HtmlUtil.escapeAttribute(ddmTemplate.getTemplateImageURL(themeDisplay)) %>" />
					</c:when>
					<c:otherwise>
						<%= HtmlUtil.escape(ddmTemplate.getDescription(locale)) %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<c:if test="<%= journalDDMTemplateDisplayContext.getDDMStructure() == null %>">

				<%
				String ddmStructureName = StringPool.BLANK;

				if (ddmTemplate.getClassPK() > 0) {
					DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchDDMStructure(ddmTemplate.getClassPK());

					if (ddmStructure != null) {
						ddmStructureName = ddmStructure.getName(locale);
					}
				}
				%>

				<liferay-ui:search-container-column-text
					name="structure"
					value="<%= HtmlUtil.escape(ddmStructureName) %>"
				/>
			</c:if>

			<liferay-ui:search-container-column-text
				name="language"
				value='<%= LanguageUtil.get(request, HtmlUtil.escape(ddmTemplate.getLanguage()) + "[stands-for]") %>'
			/>

			<%
			Group group = GroupLocalServiceUtil.getGroup(ddmTemplate.getGroupId());
			%>

			<liferay-ui:search-container-column-text
				name="scope"
				value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
			/>

			<liferay-ui:search-container-column-date
				name="modified-date"
				value="<%= ddmTemplate.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-jsp
				path="/ddm_template_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script sandbox="<%= true %>">
	var deleteDDMTemplates = function() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			submitForm(document.querySelector('#<portlet:namespace />fm'));
		}
	};

	var ACTIONS = {
		'deleteDDMTemplates': deleteDDMTemplates
	};

	Liferay.componentReady('ddmTemplateManagementToolbar').then(
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