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

<%@ include file="/document_library/init.jsp" %>

<%
DLFileEntryType fileEntryType = (DLFileEntryType)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY_TYPE);

com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure = (com.liferay.dynamic.data.mapping.model.DDMStructure)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE);

long ddmStructureId = BeanParamUtil.getLong(ddmStructure, request, "structureId");

List<DDMStructure> ddmStructures = null;

if (fileEntryType != null) {
	ddmStructures = fileEntryType.getDDMStructures();

	if (ddmStructure != null) {
		ddmStructures = ListUtil.filter(fileEntryType.getDDMStructures(), currentDDMStructure -> currentDDMStructure.getStructureId() != ddmStructure.getStructureId());
	}
}
%>

<liferay-util:buffer
	var="removeStructureIcon"
>
	<clay:icon
		symbol="times-circle"
	/>
</liferay-util:buffer>

<aui:model-context bean="<%= fileEntryType %>" model="<%= DLFileEntryType.class %>" />

<liferay-ui:search-container
	headerNames="name,null"
	total="<%= (ddmStructures != null) ? ddmStructures.size() : 0 %>"
>
	<liferay-ui:search-container-results
		results="<%= ddmStructures %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.dynamic.data.mapping.kernel.DDMStructure"
		escapedModel="<%= true %>"
		keyProperty="structureId"
		modelVar="curDDMStructure"
	>
		<liferay-ui:search-container-column-text
			name="name"
			value="<%= HtmlUtil.escape(curDDMStructure.getName(locale)) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="modify-link" data-rowId="<%= curDDMStructure.getStructureId() %>" href="javascript:;" title="<%= LanguageUtil.get(request, "remove") %>"><%= removeStructureIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
		paginate="<%= false %>"
	/>
</liferay-ui:search-container>

<liferay-ui:icon
	cssClass="modify-link select-metadata"
	label="<%= true %>"
	linkCssClass="btn btn-secondary"
	message="select"
	url='<%= "javascript:" + renderResponse.getNamespace() + "openDDMStructureSelector();" %>'
/>

<aui:script>
	function <portlet:namespace />openDDMStructureSelector() {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					modal: true,
				},
				eventName: '<portlet:namespace />selectDDMStructure',
				id: '<portlet:namespace />selectDDMStructure',
				title:
					'<%= UnicodeLanguageUtil.get(request, "select-structure") %>',
				uri:
					'<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/document_library/ddm/select_ddm_structure.jsp" /><portlet:param name="ddmStructureId" value="<%= String.valueOf(ddmStructureId) %>" /></portlet:renderURL>',
			},
			function(event) {
				var searchContainer = Liferay.SearchContainer.get(
					'<portlet:namespace />ddmStructuresSearchContainer'
				);

				var data = searchContainer.getData(false);

				if (!data.includes(event.ddmstructureid)) {
					var ddmStructureLink =
						'<a class="modify-link" data-rowId="' +
						event.ddmstructureid +
						'" href="javascript:;" title="<%= LanguageUtil.get(request, "remove") %>"><%= UnicodeFormatter.toString(removeStructureIcon) %></a>';

					searchContainer.addRow(
						[event.name, ddmStructureLink],
						event.ddmstructureid
					);

					searchContainer.updateDataStore();
				}
			}
		);
	}
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />ddmStructuresSearchContainer'
	);

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
		},
		'.modify-link'
	);
</aui:script>