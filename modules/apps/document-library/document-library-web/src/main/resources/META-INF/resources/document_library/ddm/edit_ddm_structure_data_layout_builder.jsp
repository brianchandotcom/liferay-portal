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
Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());

String refererWebDAVToken = WebDAVUtil.getStorageToken(portlet);

String redirect = ParamUtil.getString(request, "redirect");

DLEditDDMStructureDisplayContext dlEditDDMStructureDisplayContext = new DLEditDDMStructureDisplayContext(request);

com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure = dlEditDDMStructureDisplayContext.getDDMStructure();

long ddmStructureId = BeanParamUtil.getLong(ddmStructure, request, "structureId");

long groupId = BeanParamUtil.getLong(ddmStructure, request, "groupId", scopeGroupId);

String title = LanguageUtil.format(request, "new-x", LanguageUtil.get(resourceBundle, "metadata-set"), false);

if (ddmStructure != null) {
	title = LanguageUtil.format(request, "edit-x", ddmStructure.getName(locale), false);
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(title);
%>

<portlet:actionURL name="/document_library/ddm/add_data_definition" var="addDataDefinitionURL" />

<portlet:actionURL name="/document_library/ddm/update_ddm_structure" var="updateDDMStructureURL" />

<clay:container>
	<aui:form action="<%= (ddmStructure == null) ? addDataDefinitionURL : updateDDMStructureURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveDDMStructure();" %>'>
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="dataDefinitionId" type="hidden" value="<%= ddmStructureId %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="dataDefinition" type="hidden" />
		<aui:input name="dataLayout" type="hidden" />
		<aui:input name="status" type="hidden" />

		<aui:model-context bean="<%= ddmStructure %>" model="<%= com.liferay.dynamic.data.mapping.model.DDMStructure.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:field-wrapper>
					<c:if test="<%= (ddmStructure != null) && (DDMStorageLinkLocalServiceUtil.getStructureStorageLinksCount(ddmStructure.getPrimaryKey()) > 0) %>">
						<div class="alert alert-warning">
							<liferay-ui:message key="there-are-content-references-to-this-structure.-you-may-lose-data-if-a-field-name-is-renamed-or-removed" />
						</div>
					</c:if>

					<c:if test="<%= (ddmStructure != null) && (groupId != scopeGroupId) %>">
						<div class="alert alert-warning">
							<liferay-ui:message key="this-structure-does-not-belong-to-this-site.-you-may-affect-other-sites-if-you-edit-this-structure" />
						</div>
					</c:if>
				</aui:field-wrapper>

				<aui:input autoFocus="<%= windowState.equals(LiferayWindowState.POP_UP) %>" name="name" />

				<liferay-ui:panel-container
					cssClass="lfr-structure-entry-details-container"
					extended="<%= false %>"
					id="structureDetailsPanelContainer"
					persistState="<%= true %>"
				>
					<liferay-ui:panel
						collapsible="<%= true %>"
						defaultState="closed"
						extended="<%= false %>"
						id="structureDetailsSectionPanel"
						markupView="lexicon"
						persistState="<%= true %>"
						title='<%= LanguageUtil.get(request, "details") %>'
					>
						<clay:row
							className="lfr-ddm-types-form-column"
						>
							<aui:input name="storageType" type="hidden" value="<%= StorageType.JSON.getValue() %>" />
						</clay:row>

						<aui:input name="description" />

						<aui:field-wrapper label='<%= LanguageUtil.format(request, "parent-x", HtmlUtil.escape(LanguageUtil.get(resourceBundle, "metadata-set")), false) %>'>
							<aui:input name="parentDDMStructureId" type="hidden" value="<%= dlEditDDMStructureDisplayContext.getParentDDMStructureId() %>" />

							<aui:input cssClass="lfr-input-text" disabled="<%= true %>" label="" name="parentDDMStructureName" type="text" value="<%= dlEditDDMStructureDisplayContext.getParentDDMStructureName() %>" />

							<aui:button onClick='<%= renderResponse.getNamespace() + "openParentDDMStructureSelector();" %>' value="select" />

							<aui:button disabled="<%= Validator.isNull(dlEditDDMStructureDisplayContext.getParentDDMStructureName()) %>" name="removeParentDDMStructureButton" onClick='<%= renderResponse.getNamespace() + "removeParentDDMStructure();" %>' value="remove" />
						</aui:field-wrapper>

						<c:if test="<%= ddmStructure != null %>">
							<portlet:resourceURL id="getStructure" var="getStructureURL">
								<portlet:param name="structureId" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
							</portlet:resourceURL>

							<aui:input name="url" type="resource" value="<%= getStructureURL.toString() %>" />

							<c:if test="<%= Validator.isNotNull(refererWebDAVToken) %>">
								<aui:input name="webDavURL" type="resource" value="<%= ddmStructure.getWebDavURL(themeDisplay, refererWebDAVToken) %>" />
							</c:if>
						</c:if>
					</liferay-ui:panel>
				</liferay-ui:panel-container>

				<liferay-data-engine:data-layout-builder
					componentId='<%= renderResponse.getNamespace() + "dataLayoutBuilder" %>'
					contentType="document-library"
					dataDefinitionId="<%= ddmStructureId %>"
					dataLayoutInputId="dataLayout"
					groupId="<%= groupId %>"
					localizable="<%= true %>"
					namespace="<%= renderResponse.getNamespace() %>"
				/>
			</aui:fieldset>
		</aui:fieldset-group>
	</aui:form>

	<aui:button-row>
		<aui:button onClick='<%= renderResponse.getNamespace() + "saveDDMStructure();" %>' primary="<%= true %>" value='<%= LanguageUtil.get(request, "save") %>' />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</clay:container>

<aui:script>
	function <portlet:namespace />openParentDDMStructureSelector() {
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
					'<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/document_library/ddm/select_ddm_structure.jsp" /><portlet:param name="ddmStructureId" value="<%= String.valueOf(dlEditDDMStructureDisplayContext.getDDMStructureId()) %>" /></portlet:renderURL>',
			},
			function (event) {
				var form = document.<portlet:namespace />fm;

				Liferay.Util.setFormValues(form, {
					parentDDMStructureId: event.ddmstructureid,
					parentDDMStructureName: Liferay.Util.unescape(event.name),
				});

				var removeParentDDMStructureButton = Liferay.Util.getFormElement(
					form,
					'removeParentDDMStructureButton'
				);

				if (removeParentDDMStructureButton) {
					Liferay.Util.toggleDisabled(
						removeParentDDMStructureButton,
						false
					);
				}
			}
		);
	}

	function <portlet:namespace />removeParentDDMStructure() {
		var form = document.<portlet:namespace />fm;

		Liferay.Util.setFormValues(form, {
			parentDDMStructureId: '',
			parentDDMStructureName: '',
		});

		var removeParentDDMStructureButton = Liferay.Util.getFormElement(
			form,
			'removeParentDDMStructureButton'
		);

		if (removeParentDDMStructureButton) {
			Liferay.Util.toggleDisabled(removeParentDDMStructureButton, true);
		}
	}

	function <portlet:namespace />getInputLocalizedValues(field) {
		var inputLocalized = Liferay.component('<portlet:namespace />' + field);
		var localizedValues = {};

		if (inputLocalized) {
			var translatedLanguages = inputLocalized
				.get('translatedLanguages')
				.values();

			translatedLanguages.forEach(function (languageId) {
				localizedValues[languageId] = inputLocalized.getValue(languageId);
			});
		}

		return localizedValues;
	}
	function <portlet:namespace />saveDDMStructure() {
		Liferay.componentReady('<portlet:namespace />dataLayoutBuilder').then(
			function (dataLayoutBuilder) {
				var name = <portlet:namespace />getInputLocalizedValues('name');

				var description = <portlet:namespace />getInputLocalizedValues(
					'description'
				);

				var formData = dataLayoutBuilder.getFormData();

				var dataDefinition = formData.definition;

				dataDefinition.description = description;
				dataDefinition.name = name;

				var dataLayout = formData.layout;

				dataLayout.description = description;
				dataLayout.name = name;

				Liferay.Util.postForm(document.<portlet:namespace />fm, {
					data: {
						dataDefinition: JSON.stringify(dataDefinition),
						dataLayout: JSON.stringify(dataLayout),
					},
				});
			}
		);
	}
</aui:script>