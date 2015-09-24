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
String tabs1 = ParamUtil.getString(request, "tabs1", "templates");

long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getSiteGroupId());
long classNameId = ParamUtil.getLong(request, "classNameId");
long classPK = ParamUtil.getLong(request, "classPK");

DDMStructure structure = null;

long structureClassNameId = PortalUtil.getClassNameId(DDMStructure.class);

if ((classPK > 0) && (structureClassNameId == classNameId)) {
	structure = DDMStructureServiceUtil.getStructure(classPK);
}

long resourceClassNameId = ParamUtil.getLong(request, "resourceClassNameId");

if (resourceClassNameId == 0) {
	resourceClassNameId = PortalUtil.getClassNameId(PortletDisplayTemplate.class);
}

boolean showHeader = ParamUtil.getBoolean(request, "showHeader", false);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter(ActionRequest.ACTION_NAME, "deleteTemplate");
portletURL.setParameter("mvcPath", "/view_template.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("classNameId", String.valueOf(classNameId));
portletURL.setParameter("classPK", String.valueOf(classPK));
portletURL.setParameter("resourceClassNameId", String.valueOf(resourceClassNameId));
portletURL.setParameter("showHeader", String.valueOf(showHeader));

boolean controlPanel = false;

if (layout != null) {
	Group group = layout.getGroup();

	controlPanel = group.isControlPanel();
}

TemplateSearch templateSearch = new TemplateSearch(renderRequest, PortletURLUtil.clone(portletURL, renderResponse));

TemplateSearchTerms templateSearchTerms = (TemplateSearchTerms)templateSearch.getSearchTerms();
%>

<liferay-ui:error exception="<%= RequiredTemplateException.MustNotDeleteTemplateReferencedByTemplateLinks.class %>" message="the-template-cannot-be-deleted-because-it-is-required-by-one-or-more-template-links" />

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="deleteTemplateIds" type="hidden" />

	<%
	String orderByCol = ParamUtil.getString(request, "orderByCol");
	String orderByType = ParamUtil.getString(request, "orderByType");

	if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
		portalPreferences.setValue(DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col", orderByCol);
		portalPreferences.setValue(DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type", orderByType);
	}
	else {
		orderByCol = portalPreferences.getValue(DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col", "id");
		orderByType = portalPreferences.getValue(DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type", "asc");
	}

	OrderByComparator<DDMTemplate> orderByComparator = DDMUtil.getTemplateOrderByComparator(orderByCol, orderByType);
	%>

	<c:if test="<%= showHeader %>">
		<liferay-ui:header
			backURL="<%= ddmDisplay.getViewTemplatesBackURL(liferayPortletRequest, liferayPortletResponse, classPK) %>"
			cssClass="container-fluid-1280"
			title="<%= ddmDisplay.getViewTemplatesTitle(structure, controlPanel, templateSearchTerms.isSearch(), locale) %>"
		/>
	</c:if>

	<liferay-util:include page="/template_search_bar.jsp" servletContext="<%= application %>" />

	<liferay-util:include page="/template_toolbar.jsp" servletContext="<%= application %>" />

	<div class="container-fluid-1280" id="<portlet:namespace />entriesContainer">

		<liferay-ui:search-container
			orderByCol="<%= orderByCol %>"
			orderByComparator="<%= orderByComparator %>"
			orderByType="<%= orderByType %>"
			rowChecker="<%= new RowChecker(renderResponse) %>"
			searchContainer="<%= templateSearch %>"
		>

			<liferay-ui:search-container-results>
				<%@ include file="/template_search_results.jspf" %>
			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.mapping.model.DDMTemplate"
				keyProperty="templateId"
				modelVar="template"
			>

				<%
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcPath", "/edit_template.jsp");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("groupId", String.valueOf(template.getGroupId()));
				rowURL.setParameter("templateId", String.valueOf(template.getTemplateId()));
				rowURL.setParameter("classNameId", String.valueOf(classNameId));
				rowURL.setParameter("classPK", String.valueOf(template.getClassPK()));
				rowURL.setParameter("type", template.getType());
				rowURL.setParameter("structureAvailableFields", renderResponse.getNamespace() + "getAvailableFields");

				String rowHREF = rowURL.toString();
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
						href="<%= rowHREF %>"
						name="name"
						value="<%= HtmlUtil.escape(template.getName(locale)) %>"
					/>
				</c:if>

				<liferay-ui:search-container-column-jsp
					name="description"
					path="/template_description.jsp"
				/>

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
						value="<%= structureName %>"
					/>
				</c:if>

				<c:if test='<%= !excludedColumnNames.contains("type") && (classNameId == 0) %>'>
					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="type"
						value="<%= ddmDisplay.getTemplateType(template, locale) %>"
					/>
				</c:if>

				<c:if test='<%= !excludedColumnNames.contains("mode") %>'>
					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="mode"
						value="<%= LanguageUtil.get(request, template.getMode()) %>"
					/>
				</c:if>

				<c:if test='<%= !excludedColumnNames.contains("language") %>'>
					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="language"
						value='<%= LanguageUtil.get(request, template.getLanguage() + "[stands-for]") %>'
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
					align="right"
					cssClass="entry-action"
					path="/template_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</div>
</aui:form>

<liferay-util:include page="/template_add_buttons.jsp" servletContext="<%= application %>">
	<liferay-util:param name="redirect" value="<%= currentURL %>" />
	<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<liferay-util:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
	<liferay-util:param name="classPK" value="<%= String.valueOf(classPK) %>" />
	<liferay-util:param name="resourceClassNameId" value="<%= String.valueOf(resourceClassNameId) %>" />
</liferay-util:include>

<aui:script>

	function <portlet:namespace />copyTemplate(uri) {
		Liferay.Util.openWindow(
			{
				id: '<portlet:namespace />copyTemplate',
				refreshWindow: window,
				title: '<%= UnicodeLanguageUtil.get(request, "copy-template") %>',
				uri: uri
			}
		);
	}

	AUI.$(document.<portlet:namespace />fm).on(
		'click',
		'input[type=checkbox]',
		function() {
			var form = AUI.$(document.<portlet:namespace />fm);

			var hide = Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds').length == 0;

			AUI.$('#<portlet:namespace />actionsButtonContainer').toggleClass('hide', hide);
		}
	);

</aui:script>