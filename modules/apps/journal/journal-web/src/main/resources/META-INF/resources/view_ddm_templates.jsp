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
long classNameId = ParamUtil.getLong(request, "classNameId");
long classPK = ParamUtil.getLong(request, "classPK");
boolean includeCheckBox = ParamUtil.getBoolean(request, "includeCheckBox", true);

DDMStructure structure = null;

long structureClassNameId = PortalUtil.getClassNameId(DDMStructure.class);

if ((classPK > 0) && (structureClassNameId == classNameId)) {
	structure = DDMStructureServiceUtil.getStructure(classPK);
}

boolean showHeader = ParamUtil.getBoolean(request, "showHeader");

boolean controlPanel = false;

if (layout != null) {
	Group group = layout.getGroup();

	controlPanel = group.isControlPanel();
}
%>

<liferay-ui:error exception="<%= RequiredTemplateException.MustNotDeleteTemplateReferencedByTemplateLinks.class %>" message="the-template-cannot-be-deleted-because-it-is-required-by-one-or-more-template-links" />

<portlet:renderURL var="viewTemplateURL">
	<portlet:param name="mvcPath" value="/view_template.jsp" />
</portlet:renderURL>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%= journalDisplayContext.getNavigationBarItems("templates") %>'
/>

<clay:management-toolbar
	actionDropdownItems='<%= ddmDisplayContext.getActionItemsDropdownItems("deleteTemplates") %>'
	clearResultsURL="<%= ddmDisplayContext.getClearResultsURL() %>"
	componentId="ddmTemplateManagementToolbar"
	creationMenu="<%= ddmDisplayContext.getTemplateCreationMenu() %>"
	disabled="<%= ddmDisplayContext.isDisabledManagementBar(DDMWebKeys.DYNAMIC_DATA_MAPPING_TEMPLATE) %>"
	filterDropdownItems="<%= ddmDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddmDisplayContext.getTotalItems(DDMWebKeys.DYNAMIC_DATA_MAPPING_TEMPLATE) %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= ddmDisplayContext.getTemplateSearchActionURL() %>"
	searchContainerId="<%= ddmDisplayContext.getTemplateSearchContainerId() %>"
	searchFormName="fm1"
	selectable="<%= includeCheckBox && !user.isDefaultUser() %>"
	sortingOrder="<%= ddmDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddmDisplayContext.getSortingURL() %>"
/>

<c:if test="<%= showHeader %>">
	<c:choose>
		<c:when test="<%= ddmDisplay.isShowBackURLInTitleBar() %>">

			<%
			portletDisplay.setShowBackIcon(true);
			portletDisplay.setURLBack(PortalUtil.escapeRedirect(ddmDisplay.getViewTemplatesBackURL(liferayPortletRequest, liferayPortletResponse, classPK)));

			renderResponse.setTitle(ddmDisplay.getViewTemplatesTitle(structure, controlPanel, ddmDisplayContext.isSearch(), locale));
			%>

		</c:when>
		<c:otherwise>
			<liferay-ui:header
				backURL="<%= PortalUtil.escapeRedirect(ddmDisplay.getViewTemplatesBackURL(liferayPortletRequest, liferayPortletResponse, classPK)) %>"
				cssClass="container-fluid-1280"
				title="<%= ddmDisplay.getViewTemplatesTitle(structure, controlPanel, ddmDisplayContext.isSearch(), locale) %>"
			/>
		</c:otherwise>
	</c:choose>
</c:if>

<aui:form action="<%= viewTemplateURL.toString() %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= ddmDisplayContext.getTemplateSearchActionURL() %>" />
	<aui:input name="deleteTemplateIds" type="hidden" />

	<div class="container-fluid-1280" id="<portlet:namespace />entriesContainer">
		<liferay-ui:search-container
			id="<%= ddmDisplayContext.getTemplateSearchContainerId() %>"
			rowChecker="<%= new DDMTemplateRowChecker(renderResponse) %>"
			searchContainer="<%= ddmDisplayContext.getTemplateSearch() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.mapping.model.DDMTemplate"
				keyProperty="templateId"
				modelVar="template"
			>

				<%
				String rowHREF = StringPool.BLANK;

				if (DDMTemplatePermission.contains(permissionChecker, template, ActionKeys.UPDATE)) {
					PortletURL rowURL = renderResponse.createRenderURL();

					rowURL.setParameter("mvcPath", "/edit_template.jsp");
					rowURL.setParameter("groupId", String.valueOf(template.getGroupId()));
					rowURL.setParameter("templateId", String.valueOf(template.getTemplateId()));
					rowURL.setParameter("classNameId", String.valueOf(classNameId));
					rowURL.setParameter("classPK", String.valueOf(template.getClassPK()));
					rowURL.setParameter("type", template.getType());
					rowURL.setParameter("structureAvailableFields", renderResponse.getNamespace() + "getAvailableFields");

					rowHREF = rowURL.toString();
				}
				%>

				<liferay-ui:search-container-row-parameter
					name="rowHREF"
					value="<%= rowHREF %>"
				/>

				<%
				Set<String> excludedColumnNames = ddmDisplay.getViewTemplatesExcludedColumnNames();
				%>

				<c:if test='<%= !excludedColumnNames.contains("id") %>'>
					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="id"
						orderable="<%= true %>"
						orderableProperty="id"
						property="templateId"
					/>
				</c:if>

				<c:if test='<%= !excludedColumnNames.contains("name") %>'>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						href="<%= rowHREF %>"
						name="name"
						value="<%= HtmlUtil.escape(template.getName(locale)) %>"
					/>
				</c:if>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="description"
				>
                    <c:if test="<%= Validator.isNotNull(rowHREF) %>">
                        <a href="<%= rowHREF %>">
                    </c:if>

                    <c:choose>
                        <c:when test="<%= template.isSmallImage() %>">
                            <img alt="<%= HtmlUtil.escape(template.getName(locale)) %>" class="lfr-ddm-small-image-view" src="<%= HtmlUtil.escapeAttribute(template.getTemplateImageURL(themeDisplay)) %>" />
                        </c:when>
                        <c:otherwise>
                            <%= HtmlUtil.escape(template.getDescription(locale)) %>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="<%= Validator.isNotNull(rowHREF) %>">
                        </a>
                    </c:if>
                </liferay-ui:search-container-column-text>

				<c:if test='<%= !excludedColumnNames.contains("structure") && (structure == null) %>'>

					<%
					String structureName = StringPool.BLANK;

					if (template.getClassPK() > 0) {
						DDMStructure templateStructure = DDMStructureLocalServiceUtil.getStructure(template.getClassPK());

						structureName = templateStructure.getName(locale);
					}
					%>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="structure"
						value="<%= HtmlUtil.escape(structureName) %>"
					/>
				</c:if>

				<c:if test='<%= !excludedColumnNames.contains("type") && (classNameId == 0) %>'>
					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="type"
						value="<%= HtmlUtil.escape(ddmDisplay.getTemplateType(template, locale)) %>"
					/>
				</c:if>

				<c:if test='<%= !excludedColumnNames.contains("mode") %>'>
					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="mode"
						value="<%= LanguageUtil.get(request, HtmlUtil.escape(template.getMode())) %>"
					/>
				</c:if>

				<c:if test='<%= !excludedColumnNames.contains("language") %>'>
					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="language"
						value='<%= LanguageUtil.get(request, HtmlUtil.escape(template.getLanguage()) + "[stands-for]") %>'
					/>
				</c:if>

				<c:if test='<%= !excludedColumnNames.contains("scope") %>'>

					<%
					Group group = GroupLocalServiceUtil.getGroup(template.getGroupId());
					%>

					<liferay-ui:search-container-column-text
						name="scope"
						value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
					/>
				</c:if>

				<c:if test='<%= !excludedColumnNames.contains("modified-date") %>'>
					<liferay-ui:search-container-column-date
						href="<%= rowHREF %>"
						name="modified-date"
						orderable="<%= true %>"
						orderableProperty="modified-date"
						value="<%= template.getModifiedDate() %>"
					/>
				</c:if>

				<liferay-ui:search-container-column-jsp
					path="/template_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</div>
</aui:form>

<aui:script sandbox="<%= true %>">
	var deleteTemplates = function() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			var searchContainer = AUI.$('#<portlet:namespace />entriesContainer', form);

			form.attr('method', 'post');
			form.fm('deleteTemplateIds').val(Liferay.Util.listCheckedExcept(searchContainer, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteTemplate"><portlet:param name="mvcPath" value="/view_template.jsp" /></portlet:actionURL>');
		}
	};

	var ACTIONS = {
		'deleteTemplates': deleteTemplates
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