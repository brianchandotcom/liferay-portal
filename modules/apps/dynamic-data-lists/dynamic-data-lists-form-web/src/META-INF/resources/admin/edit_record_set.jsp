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

<%@ include file="/admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DDLRecordSet recordSet = ddlFormAdminDisplayContext.getRecordSet();

long recordSetId = BeanParamUtil.getLong(recordSet, request, "recordSetId");
long groupId = BeanParamUtil.getLong(recordSet, request, "groupId", scopeGroupId);
long ddmStructureId = BeanParamUtil.getLong(recordSet, request, "DDMStructureId");
String name = BeanParamUtil.getString(recordSet, request, "name");
String description = BeanParamUtil.getString(recordSet, request, "description");
%>

<portlet:actionURL name="addRecordSet" var="addRecordSetURL">
	<portlet:param name="mvcPath" value="/admin/edit_record_set.jsp" />
</portlet:actionURL>

<portlet:actionURL name="updateRecordSet" var="updateRecordSetURL">
	<portlet:param name="mvcPath" value="/admin/edit_record_set.jsp" />
</portlet:actionURL>

<aui:form action="<%= (recordSet == null) ? addRecordSetURL : updateRecordSetURL %>" cssClass="ddl-form-builder-form" method="post" name="editForm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="recordSetId" type="hidden" value="<%= recordSetId %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />

	<liferay-ui:error exception="<%= RecordSetNameException.class %>" message="please-enter-a-valid-form-name" />
	<liferay-ui:error exception="<%= StructureDefinitionException .class %>" message="please-enter-a-valid-form-definition" />
	<liferay-ui:error exception="<%= StructureLayoutException .class %>" message="please-enter-a-valid-form-layout" />

	<aui:fieldset cssClass="ddl-form-builder-basic-info">
		<div class="ddl-form-builder-name">
			<h2>
				<liferay-ui:input-editor contents="<%= HtmlUtil.escape(LocalizationUtil.getLocalization(name, themeDisplay.getLanguageId())) %>" editorName="alloyeditor" name="nameEditor" placeholder="name" showSource="<%= false %>" />
			</h2>
		</div>

		<aui:input name="name" type="hidden" />

		<div class="ddl-form-builder-description">
			<h4>
				<liferay-ui:input-editor contents="<%= LocalizationUtil.getLocalization(description, themeDisplay.getLanguageId()) %>" editorName="alloyeditor" name="descriptionEditor" placeholder="description" showSource="<%= false %>" />
			</h4>
		</div>

		<aui:input name="description" type="hidden" />
	</aui:fieldset>

	<aui:fieldset cssClass="ddl-form-builder-app">
		<aui:input name="definition" type="hidden" />
		<aui:input name="layout" type="hidden" />

		<div id="<portlet:namespace />formBuilder"></div>
	</aui:fieldset>

	<div class="loading-animation" id="<portlet:namespace />loader"></div>

	<aui:button-row cssClass="ddl-form-builder-buttons">
		<aui:button label="save" primary="<%= true %>" type="submit" />

		<aui:button href="<%= redirect %>" name="cancelButton" type="cancel" />
	</aui:button-row>

	<aui:script>
		var initHandler = Liferay.after(
			'form:registered',
			function(event) {
				if (event.formName === '<portlet:namespace />editForm') {
					var fieldTypes = <%= ddlFormAdminDisplayContext.getDDMFormFieldTypesJSONArray() %>;

					var fieldModules = _.map(
						fieldTypes,
						function(item) {
							return item.javaScriptModule;
						}
					);

					Liferay.provide(
						window,
						'<portlet:namespace />init',
						function() {
							Liferay.DDM.Renderer.FieldTypes.register(fieldTypes);

							new Liferay.DDL.Portlet(
								{
									definition: <%= ddlFormAdminDisplayContext.getSerializedDDMForm() %>,
									editForm: event.form,
									layout: <%= ddlFormAdminDisplayContext.getSerializedDDMFormLayout() %>,
									namespace: '<portlet:namespace />'
								}
							);
						},
						['liferay-ddl-portlet'].concat(fieldModules)
					);

					<portlet:namespace />init();
				}
			}
		);

		var clearPortletHandlers = function(event) {
			if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
				initHandler.detach();

				Liferay.detach('destroyPortlet', clearPortletHandlers);
			}
		};

		Liferay.on('destroyPortlet', clearPortletHandlers);
	</aui:script>
</aui:form>