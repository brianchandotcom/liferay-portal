<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "templates");

long classNameId = ParamUtil.getLong(request, "classNameId");
long classPK = ParamUtil.getLong(request, "classPK");

DDMStructure structure = null;

long structureClassNameId = PortalUtil.getClassNameId(DDMStructure.class);

if ((classPK > 0) && (structureClassNameId == classNameId)) {
	structure = DDMStructureServiceUtil.getStructure(classPK);
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/dynamic_data_mapping/view_template");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("classNameId", String.valueOf(classNameId));
portletURL.setParameter("classPK", String.valueOf(classPK));

boolean controlPanel = false;

if (layout != null) {
	Group group = layout.getGroup();

	controlPanel = group.isControlPanel();
}

String title = ddmDisplay.getViewTemplatesTitle(structure, controlPanel, locale);
%>

<liferay-ui:error exception="<%= RequiredTemplateException.class %>">
	<liferay-ui:message key="required-templates-could-not-be-deleted" />

	<liferay-ui:message key="they-are-referenced-by-web-contents" />
</liferay-ui:error>

<liferay-ui:header
	backURL="<%= ddmDisplay.getViewTemplatesBackURL(liferayPortletRequest, liferayPortletResponse, classPK) %>"
	title="<%= title %>"
/>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="deleteTemplateIds" type="hidden" />

	<%
	String orderByCol = ParamUtil.getString(request, "orderByCol");
	String orderByType = ParamUtil.getString(request, "orderByType");

	if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
		portalPreferences.setValue(PortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col", orderByCol);
		portalPreferences.setValue(PortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type", orderByType);
	}
	else {
		orderByCol = portalPreferences.getValue(PortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col", "id");
		orderByType = portalPreferences.getValue(PortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type", "asc");
	}

	OrderByComparator orderByComparator = DDMUtil.getTemplateOrderByComparator(orderByCol, orderByType);
	%>

	<liferay-ui:search-container
		orderByCol="<%= orderByCol %>"
		orderByComparator="<%= orderByComparator %>"
		orderByType="<%= orderByType %>"
		rowChecker="<%= new RowChecker(renderResponse) %>"
		searchContainer="<%= new TemplateSearch(renderRequest, portletURL) %>"
	>

		<liferay-util:include page="/html/portlet/dynamic_data_mapping/template_toolbar.jsp">
			<liferay-util:param name="redirect" value="<%= currentURL %>" />
			<liferay-util:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
			<liferay-util:param name="classPK" value="<%= String.valueOf(classPK) %>" />
		</liferay-util:include>

		<liferay-ui:search-container-results>
			<%@ include file="/html/portlet/dynamic_data_mapping/template_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.dynamicdatamapping.model.DDMTemplate"
			keyProperty="templateId"
			modelVar="template"
		>

			<%
			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/dynamic_data_mapping/edit_template");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("groupId", String.valueOf(template.getGroupId()));
			rowURL.setParameter("templateId", String.valueOf(template.getTemplateId()));
			rowURL.setParameter("classNameId", String.valueOf(classNameId));
			rowURL.setParameter("classPK", String.valueOf(template.getClassPK()));
			rowURL.setParameter("type", template.getType());

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
					value="<%= HtmlUtil.escape(LanguageUtil.get(pageContext, template.getName(locale))) %>"
				/>
			</c:if>

			<liferay-ui:search-container-column-jsp
				name="description"
				path="/html/portlet/dynamic_data_mapping/template_description.jsp"
			/>

			<c:if test='<%= !excludedColumnNames.contains("structure") && (structure == null) %>'>

				<%
				String structureName = StringPool.BLANK;

				if (template.getClassPK() > 0) {
					DDMStructure templateStructure = DDMStructureServiceUtil.getStructure(template.getClassPK());

					structureName = templateStructure.getName();
				}
				%>

				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="structure"
					value="<%= structureName %>"
				/>
			</c:if>

			<c:if test='<%= !excludedColumnNames.contains("type") && Validator.isNull(templateTypeValue) && (classNameId == 0) %>'>
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
					value="<%= LanguageUtil.get(pageContext, template.getMode()) %>"
				/>
			</c:if>

			<c:if test='<%= !excludedColumnNames.contains("language") %>'>
				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="language"
					value='<%= LanguageUtil.get(pageContext, template.getLanguage() + "[stands-for]") %>'
				/>
			</c:if>

			<c:if test='<%= !excludedColumnNames.contains("modified-date") %>'>
				<liferay-ui:search-container-column-text
					buffer="buffer"
					href="<%= rowHREF %>"
					name="modified-date"
					orderable="<%= true %>"
					orderableProperty="modified-date"
				>

					<%
					buffer.append(dateFormatDateTime.format(template.getModifiedDate()));
					%>

				</liferay-ui:search-container-column-text>
			</c:if>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/html/portlet/dynamic_data_mapping/template_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<c:if test="<%= total > 0 %>">
			<aui:button-row>
				<aui:button cssClass="delete-templates-button" onClick='<%= renderResponse.getNamespace() + "deleteTemplates();" %>' value="delete" />
			</aui:button-row>

			<div class="separator"><!-- --></div>
		</c:if>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />copyTemplate(uri) {
		Liferay.Util.openWindow(
			{
				id: '<portlet:namespace />copyTemplate',
				refreshWindow: window,
				title: '<%= UnicodeLanguageUtil.get(pageContext, "copy-template") %>',
				uri: uri
			}
		);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />deleteTemplates',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this") %>')) {
				document.<portlet:namespace />fm.method = "post";
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
				document.<portlet:namespace />fm.<portlet:namespace />deleteTemplateIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");

				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" /></portlet:actionURL>");
			}
		},
		['liferay-util-list-fields']
	);
</aui:script>

<aui:script use="aui-base">
	var buttons = A.all('.delete-templates-button');

	if (buttons.size()) {
		var toggleDisabled = A.bind('toggleDisabled', Liferay.Util, ':button');

		var resultsGrid = A.one('.searchcontainer-content');

		if (resultsGrid) {
			resultsGrid.delegate(
				'click',
				function(event) {
					var disabled = (resultsGrid.one(':checked') == null);

					toggleDisabled(disabled);
				},
				':checkbox'
			);
		}

		toggleDisabled(true);
	}
</aui:script>