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
DLFileEntryAdditionalMetadataSetsDisplayContext dlFileEntryAdditionalMetadataSetsDisplayContext = new DLFileEntryAdditionalMetadataSetsDisplayContext(request);
%>

<liferay-util:buffer
	var="removeStructureIcon"
>
	<clay:icon
		symbol="times-circle"
	/>
</liferay-util:buffer>

<aui:model-context bean="<%= dlFileEntryAdditionalMetadataSetsDisplayContext.getDLFileEntryType() %>" model="<%= DLFileEntryType.class %>" />

<liferay-ui:search-container
	headerNames="name,null"
	total="<%= dlFileEntryAdditionalMetadataSetsDisplayContext.getDDMStructuresCount() %>"
>
	<liferay-ui:search-container-results
		results="<%= dlFileEntryAdditionalMetadataSetsDisplayContext.getDDMStructures() %>"
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

<div class="mt-3">
	<liferay-ui:icon
		cssClass="modify-link select-metadata"
		label="<%= true %>"
		linkCssClass="btn btn-secondary"
		message="select"
		url='<%= "javascript:" + liferayPortletResponse.getNamespace() + "openDDMStructureSelector();" %>'
	/>
</div>

<aui:script>
	function <portlet:namespace />openDDMStructureSelector() {
		Liferay.Util.openSelectionModal({
			id: '<portlet:namespace />selectDDMStructure',
			onSelect: function (selectedItem) {
				var searchContainer = Liferay.SearchContainer.get(
					'<portlet:namespace />ddmStructuresSearchContainer'
				);

				var data = searchContainer.getData(false);

				if (!data.includes(selectedItem.ddmstructureid)) {
					var ddmStructureLink =
						'<a class="modify-link" data-rowId="' +
						selectedItem.ddmstructureid +
						'" href="javascript:;" title="<%= LanguageUtil.get(request, "remove") %>"><%= UnicodeFormatter.toString(removeStructureIcon) %></a>';

					searchContainer.addRow(
						[
							Liferay.Util.escapeHTML(selectedItem.name),
							ddmStructureLink,
						],
						selectedItem.ddmstructureid
					);

					searchContainer.updateDataStore();
				}
			},
			selectEventName: '<portlet:namespace />selectDDMStructure',
			title: '<%= UnicodeLanguageUtil.get(request, "select-metadata-set") %>',
			url:
				'<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/document_library/ddm/select_ddm_structure.jsp" /><portlet:param name="ddmStructureId" value="<%= String.valueOf(dlFileEntryAdditionalMetadataSetsDisplayContext.getDDMStructureId()) %>" /></portlet:renderURL>',
		});
	}
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />ddmStructuresSearchContainer'
	);

	searchContainer.get('contentBox').delegate(
		'click',
		(event) => {
			var link = event.currentTarget;

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
		},
		'.modify-link'
	);
</aui:script>