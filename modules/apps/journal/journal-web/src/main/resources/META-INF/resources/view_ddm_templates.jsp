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

JournalDDMTemplateManagementToolbarDisplayContext journalDDMTemplateManagementToolbarDisplayContext = new JournalDDMTemplateManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, journalDDMTemplateDisplayContext);

DDMStructure ddmStructure = journalDDMTemplateDisplayContext.getDDMStructure();

if (ddmStructure != null) {
	renderResponse.setTitle(LanguageUtil.format(request, "templates-for-structure-x", ddmStructure.getName(locale)));
}
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%= journalDisplayContext.getNavigationBarItems("templates") %>'
/>

<clay:management-toolbar
	displayContext="<%= journalDDMTemplateManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="/journal/delete_ddm_template" var="deleteDDMTemplateURL">
	<portlet:param name="mvcPath" value="/view_ddm_templates.jsp" />
</portlet:actionURL>

<aui:form action="<%= deleteDDMTemplateURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-ui:error exception="<%= RequiredTemplateException.MustNotDeleteTemplateReferencedByTemplateLinks.class %>" message="the-template-cannot-be-deleted-because-it-is-required-by-one-or-more-template-links" />

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

			Map<String, Object> rowData = new HashMap<>();

			rowData.put("actions", journalDDMTemplateManagementToolbarDisplayContext.getAvailableActions(ddmTemplate));

			row.setData(rowData);
			%>

			<c:choose>
				<c:when test='<%= Objects.equals(journalDDMTemplateDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							verticalCard="<%= new JournalDDMTemplateVerticalCard(ddmTemplate, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
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
						value="<%= HtmlUtil.escape(ddmTemplate.getDescription(locale)) %>"
					/>

					<c:if test="<%= journalDDMTemplateDisplayContext.getClassPK() <= 0 %>">

						<%
						String ddmStructureName = StringPool.BLANK;

						if (ddmTemplate.getClassPK() > 0) {
							DDMStructure curDDMStructure = DDMStructureLocalServiceUtil.fetchDDMStructure(ddmTemplate.getClassPK());

							if (curDDMStructure != null) {
								ddmStructureName = curDDMStructure.getName(locale);
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

					<liferay-ui:search-container-column-text>
						<clay:dropdown-actions
							defaultEventHandler="<%= JournalWebConstants.JOURNAL_DDM_TEMPLATE_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
							dropdownItems="<%= journalDDMTemplateDisplayContext.getDDMTemplateActionDropdownItems(ddmTemplate) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= journalDDMTemplateDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<liferay-frontend:component
	componentId="<%= journalDDMTemplateManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/DDMTemplatesManagementToolbarDefaultEventHandler.es"
/>

<liferay-frontend:component
	componentId="<%= JournalWebConstants.JOURNAL_DDM_TEMPLATE_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
	module="js/DDMTemplateElementsDefaultEventHandler.es"
/>