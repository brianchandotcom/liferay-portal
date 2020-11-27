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
DLSelectDDMStructureDisplayContext dlSelectDDMStructureDisplayContext = new DLSelectDDMStructureDisplayContext(renderRequest, renderResponse);

SearchContainer<com.liferay.dynamic.data.mapping.model.DDMStructure> ddmStructureSearch = dlSelectDDMStructureDisplayContext.getDDMStructureSearch();
%>

<clay:management-toolbar-v2
	displayContext="<%= new DLSelectDDMStructureManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, dlSelectDDMStructureDisplayContext) %>"
/>

<aui:form cssClass="container-fluid container-fluid-max-xl" method="post" name="selectDDMStructureFm">
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
				cssClass="table-cell-expand"
				name="name"
			>
				<c:choose>
					<c:when test="<%= ddmStructure.getStructureId() != dlSelectDDMStructureDisplayContext.getDDMStructureId() %>">
						<aui:a
							cssClass="selector-button"
							data='<%=
								HashMapBuilder.<String, Object>put(
									"ddmstructureid", ddmStructure.getStructureId()
								).put(
									"ddmstructurekey", ddmStructure.getStructureKey()
								).put(
									"name", ddmStructure.getName(locale)
								).build()
							%>'
							href="javascript:;"
						>
							<%= HtmlUtil.escape(ddmStructure.getUnambiguousName(ddmStructureSearch.getResults(), themeDisplay.getScopeGroupId(), locale)) %>
						</aui:a>
					</c:when>
					<c:otherwise>
						<%= HtmlUtil.escape(ddmStructure.getUnambiguousName(ddmStructureSearch.getResults(), themeDisplay.getScopeGroupId(), locale)) %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
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