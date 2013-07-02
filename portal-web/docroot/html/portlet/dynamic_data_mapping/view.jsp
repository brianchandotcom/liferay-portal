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
String tabs1 = ParamUtil.getString(request, "tabs1", "structures");

long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getSiteGroupId());

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/dynamic_data_mapping/view");
portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("tabs1", tabs1);
%>

<liferay-ui:error exception="<%= RequiredStructureException.class %>">
	<liferay-ui:message key="required-structures-could-not-be-deleted" />

	<%
	RequiredStructureException rse = (RequiredStructureException)errorException;
	%>

	<c:if test="<%= rse.getType() == RequiredStructureException.REFERENCED_TEMPLATE %>">
		<liferay-ui:message key="they-are-referenced-by-templates" />
	</c:if>
</liferay-ui:error>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="deleteStructureIds" type="hidden" />

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

	OrderByComparator orderByComparator = DDMUtil.getStructureOrderByComparator(orderByCol, orderByType);
	%>

	<liferay-ui:search-container
		orderByCol="<%= orderByCol %>"
		orderByComparator="<%= orderByComparator %>"
		orderByType="<%= orderByType %>"
		rowChecker="<%= new RowChecker(renderResponse) %>"
		searchContainer="<%= new StructureSearch(renderRequest, portletURL) %>"
	>

		<c:if test="<%= showToolbar %>">
			<liferay-util:include page="/html/portlet/dynamic_data_mapping/toolbar.jsp">
				<liferay-util:param name="toolbarItem" value="view-all" />
			</liferay-util:include>
		</c:if>

		<liferay-ui:search-container-results>
			<%@ include file="/html/portlet/dynamic_data_mapping/structure_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.dynamicdatamapping.model.DDMStructure"
			keyProperty="structureId"
			modelVar="structure"
		>

			<%
			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/dynamic_data_mapping/edit_structure");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("classNameId", String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)));
			rowURL.setParameter("classPK", String.valueOf(structure.getStructureId()));

			String rowHREF = rowURL.toString();
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="id"
				orderable="<%= true %>"
				orderableProperty="id"
				property="structureId"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="name"
				value="<%= HtmlUtil.escape(structure.getName(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="description"
				value="<%= HtmlUtil.escape(structure.getDescription(locale)) %>"
			/>

			<c:if test="<%= Validator.isNull(storageTypeValue) %>">
				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="storage-type"
					value="<%= LanguageUtil.get(pageContext, structure.getStorageType()) %>"
				/>
			</c:if>

			<c:if test="<%= scopeClassNameId == 0 %>">
				<liferay-ui:search-container-column-text
					buffer="buffer"
					href="<%= rowHREF %>"
					name="type"
				>

					<%
					buffer.append(ResourceActionsUtil.getModelResource(locale, structure.getClassName()));
					%>

				</liferay-ui:search-container-column-text>
			</c:if>

			<liferay-ui:search-container-column-text
				buffer="buffer"
				href="<%= rowHREF %>"
				name="modified-date"
				orderable="<%= true %>"
				orderableProperty="modified-date"
			>

				<%
				buffer.append(dateFormatDateTime.format(structure.getModifiedDate()));
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/html/portlet/dynamic_data_mapping/structure_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<c:if test="<%= total > 0 %>">
			<aui:button-row>
				<aui:button cssClass="delete-structures-button" disabled="true" name="delete" onClick='<%= renderResponse.getNamespace() + "deleteStructures();" %>' value="delete" />
			</aui:button-row>

			<div class="separator"><!-- --></div>
		</c:if>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />delete', '#<portlet:namespace /><%= searchContainerReference.getId() %>SearchContainer', document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

	function <portlet:namespace />copyStructure(uri) {
		Liferay.Util.openWindow(
			{
				dialog: {
					zIndex: Liferay.zIndex.WINDOW + 2
				},
				id: '<portlet:namespace />copyStructure',
				refreshWindow: window,
				title: '<%= UnicodeLanguageUtil.get(pageContext, "copy-data-definition") %>',
				uri: uri
			}
		);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />deleteStructures',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this") %>')) {
				document.<portlet:namespace />fm.method = "post";
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
				document.<portlet:namespace />fm.<portlet:namespace />deleteStructureIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" /></portlet:actionURL>");
			}
		},
		['liferay-util-list-fields']
	);
</aui:script>