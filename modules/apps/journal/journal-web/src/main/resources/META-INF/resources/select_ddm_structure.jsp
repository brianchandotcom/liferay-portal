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
JournalSelectDDMStructureDisplayContext journalSelectDDMStructureDisplayContext = new JournalSelectDDMStructureDisplayContext(renderRequest, renderResponse);

SearchContainer<DDMStructure> ddmStructureSearch = journalSelectDDMStructureDisplayContext.getDDMStructureSearch();
%>

<clay:management-toolbar
	displayContext="<%= new JournalSelectDDMStructureManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, journalSelectDDMStructureDisplayContext) %>"
/>

<aui:form cssClass="container-fluid-1280" method="post" name="selectDDMStructureFm">
	<liferay-ui:search-container
		searchContainer="<%= ddmStructureSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.mapping.model.DDMStructure"
			keyProperty="structureId"
			modelVar="ddmStructure"
		>
			<liferay-ui:search-container-column-text
				name="id"
				value="<%= String.valueOf(ddmStructure.getStructureId()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
			>
				<c:choose>
					<c:when test="<%= ddmStructure.getStructureId() != journalSelectDDMStructureDisplayContext.getClassPK() %>">

						<%
						Map<String, Object> data = new HashMap<>();

						data.put("ddmstructureid", ddmStructure.getStructureId());
						data.put("ddmstructurekey", ddmStructure.getStructureKey());
						data.put("name", ddmStructure.getName(locale));
						%>

						<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
							<%= HtmlUtil.escape(ddmStructure.getUnambiguousName(ddmStructureSearch.getResults(), themeDisplay.getScopeGroupId(), locale)) %>
						</aui:a>
					</c:when>
					<c:otherwise>
						<%= HtmlUtil.escape(ddmStructure.getUnambiguousName(ddmStructureSearch.getResults(), themeDisplay.getScopeGroupId(), locale)) %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="description"
				truncate="<%= true %>"
				value="<%= HtmlUtil.escape(ddmStructure.getDescription(locale)) %>"
			/>

			<liferay-ui:search-container-column-date
				name="modified-date"
				value="<%= ddmStructure.getModifiedDate() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectDDMStructureFm',
		'<%= HtmlUtil.escapeJS(journalSelectDDMStructureDisplayContext.getEventName()) %>'
	);
</aui:script>