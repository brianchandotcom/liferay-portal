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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
String className = ParamUtil.getString(request, "className");
long classTypeId = ParamUtil.getLong(request, "classTypeId");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectDDMStructureField");

AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/asset_publisher/select_structure_field");
portletURL.setParameter("portletResource", portletResource);
portletURL.setParameter("className", className);
portletURL.setParameter("classTypeId", String.valueOf(classTypeId));
%>

<div id="<portlet:namespace />selectDDMStructureFieldForm">
	<liferay-ui:search-container
		iteratorURL="<%= portletURL %>"
	>
		<liferay-ui:search-container-results
			results="<%= assetRendererFactory.getClassTypeFieldNames(classTypeId, locale, searchContainer.getStart(), searchContainer.getEnd()) %>"
			total="<%= assetRendererFactory.getClassTypeFieldNamesCount(classTypeId, locale) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.util.Tuple"
			modelVar="field"
		>

			<%
			String label = (String)field.getObject(0);
			String name = (String)field.getObject(1);
			String fieldType = (String)field.getObject(2);
			long ddmStructureId = GetterUtil.getLong(field.getObject(3));
			%>

			<liferay-ui:search-container-column-text>
				<input data-button-id="<%= renderResponse.getNamespace() + "applyButton" + name %>" name="<portlet:namespace />selectStructureFieldSubtype" type="radio" <%= name.equals(ddmStructureFieldName) ? "checked" : StringPool.BLANK %> />
			</liferay-ui:search-container-column-text>

			<%
			String fieldsNamespace = PwdGenerator.getPassword(4);
			%>

			<liferay-ui:search-container-column-text
				name="field"
			>
				<liferay-portlet:resourceURL portletConfiguration="true" var="structureFieldURL">
					<portlet:param name="<%= Constants.CMD %>" value="getFieldValue" />
					<portlet:param name="portletResource" value="<%= portletResource %>" />
					<portlet:param name="structureId" value="<%= String.valueOf(ddmStructureId) %>" />
					<portlet:param name="name" value="<%= name %>" />
					<portlet:param name="fieldsNamespace" value="<%= fieldsNamespace %>" />
				</liferay-portlet:resourceURL>

				<aui:form action="<%= structureFieldURL %>" name='<%= name + "fieldForm" %>'>

					<%
					com.liferay.portlet.dynamicdatamapping.storage.Field ddmField = new com.liferay.portlet.dynamicdatamapping.storage.Field();

					ddmField.setDefaultLocale(themeDisplay.getLocale());
					ddmField.setDDMStructureId(ddmStructureId);
					ddmField.setName(name);

					if (name.equals(ddmStructureFieldName)) {
						if (fieldType.equals(DDMImpl.TYPE_DDM_DATE)) {
							ddmStructureFieldValue = GetterUtil.getDate(ddmStructureFieldValue, DateFormatFactoryUtil.getSimpleDateFormat("yyyyMMddHHmmss"));
						}

						ddmField.setValue(themeDisplay.getLocale(), ddmStructureFieldValue);
					}
					%>

					<liferay-ddm:html-field
						classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
						classPK="<%= ddmStructureId %>"
						field="<%= ddmField %>"
						fieldsNamespace="<%= fieldsNamespace %>"
					/>
				</aui:form>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("form", renderResponse.getNamespace() + name + "fieldForm");
				data.put("label", label);
				data.put("name", name);
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" disabled="<%= name.equals(ddmStructureFieldName) ? false : true %>" id='<%= renderResponse.getNamespace() + "applyButton" + name %>' value="apply" />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</div>

<aui:script use="aui-base,aui-io">
	var Util = Liferay.Util;

	var selectDDMStructureFieldForm = A.one('#<portlet:namespace />selectDDMStructureFieldForm');

	selectDDMStructureFieldForm.delegate(
		'click',
		function(event) {
			var result = Util.getAttributes(event.currentTarget, 'data-');

			var form = A.one('#' + result.form);

			A.io.request(
				form.attr('action'),
				{
					dataType: 'json',
					form: {
						id: form
					},
					on: {
						success: function(event, id, obj) {
							var jsonArray = this.get('responseData');

							result['className'] = '<%= AssetPublisherUtil.getClassName(assetRendererFactory) %>';
							result['displayValue'] = jsonArray.displayValue;
							result['value'] = jsonArray.value;

							Util.getOpener().Liferay.fire('<%= HtmlUtil.escapeJS(eventName) %>', result);

							Util.getWindow().close();
						}
					}
				}
			);
		},
		'.selector-button input'
	);

	A.one('#<portlet:namespace />tuplesSearchContainer').delegate(
		'click',
		function(event) {
			var buttonId = event.target.attr('data-button-id');

			Liferay.Util.toggleDisabled(selectDDMStructureFieldForm.all('.aui-button-input'), true);

			Liferay.Util.toggleDisabled('#' + buttonId, false);
		},
		'input[name=<portlet:namespace />selectStructureFieldSubtype]'
	);
</aui:script>