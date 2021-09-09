<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionDiagramSettingDisplayContext cpDefinitionDiagramSettingDisplayContext = (CPDefinitionDiagramSettingDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionDiagramSettingDisplayContext.getCPDefinition();

CPDefinitionDiagramSetting cpDefinitionDiagramSetting = cpDefinitionDiagramSettingDisplayContext.fetchCPDefinitionDiagramSetting();

String type = BeanParamUtil.getString(cpDefinitionDiagramSetting, renderRequest, "type", DefaultCPDefinitionDiagramType.KEY);

CPDefinitionDiagramType cpDefinitionDiagramType = cpDefinitionDiagramSettingDisplayContext.getCPDefinitionDiagramType(type);
%>

<portlet:actionURL name="/cp_definitions/edit_cp_definition_diagram_setting" var="editProductDefinitionDiagramSettingActionURL" />

<aui:form action="<%= editProductDefinitionDiagramSettingActionURL %>" cssClass="mt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateCPDefinitionDiagramSetting" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />

	<liferay-ui:error exception="<%= NoSuchCPAttachmentFileEntryException.class %>" message="please-select-an-existing-file" />
	<liferay-ui:error exception="<%= NoSuchFileEntryException.class %>" message="please-select-an-existing-file" />

	<div class="row">
		<div class="col-8">
			<commerce-ui:panel
				title='<%= LanguageUtil.get(resourceBundle, "diagram-settings") %>'
			>
				<aui:select bean="<%= cpDefinitionDiagramSetting %>" label="type" model="<%= CPDefinitionDiagramSetting.class %>" name="type">

					<%
					for (CPDefinitionDiagramType curCPDefinitionDiagramType : cpDefinitionDiagramSettingDisplayContext.getCPDefinitionDiagramTypes()) {
						String cpDefinitionDiagramTypeKey = curCPDefinitionDiagramType.getKey();
					%>

						<aui:option label="<%= curCPDefinitionDiagramType.getLabel(locale) %>" selected="<%= (cpDefinitionDiagramSetting != null) && cpDefinitionDiagramTypeKey.equals(type) %>" value="<%= curCPDefinitionDiagramType.getKey() %>" />

					<%
					}
					%>

				</aui:select>
			</commerce-ui:panel>

			<commerce-ui:panel
				title='<%= LanguageUtil.get(resourceBundle, "diagram-mapping") %>'
			>

				<%
				cpDefinitionDiagramType.render(cpDefinitionDiagramSetting, request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
				%>

			</commerce-ui:panel>
		</div>

		<div class="col-4">
			<commerce-ui:panel
				elementClasses="flex-fill"
				title='<%= LanguageUtil.get(resourceBundle, "diagram-file") %>'
			>
				<div class="row">
					<div class="col-12 h-100">

						<%
						FileEntry fileEntry = cpDefinitionDiagramSettingDisplayContext.fetchFileEntry();
						%>

						<aui:model-context bean="<%= fileEntry %>" model="<%= FileEntry.class %>" />

						<div class="lfr-attachment-cover-image-selector">
							<portlet:actionURL name="/cp_definitions/upload_cp_definition_diagram_setting_image" var="uploadCPDefinitionDiagramSettingImageActionURL" />

							<liferay-item-selector:image-selector
								draggableImage="vertical"
								fileEntryId='<%= BeanParamUtil.getLong(fileEntry, request, "fileEntryId") %>'
								itemSelectorEventName="addFileEntry"
								itemSelectorURL="<%= cpDefinitionDiagramSettingDisplayContext.getImageItemSelectorUrl() %>"
								maxFileSize="<%= cpDefinitionDiagramSettingDisplayContext.getImageMaxSize() %>"
								paramName="fileEntry"
								uploadURL="<%= uploadCPDefinitionDiagramSettingImageActionURL %>"
								validExtensions="<%= StringUtil.merge(cpDefinitionDiagramSettingDisplayContext.getImageExtensions(), StringPool.COMMA_AND_SPACE) %>"
							/>
						</div>
					</div>
				</div>
			</commerce-ui:panel>

			<commerce-ui:panel
				elementClasses="flex-fill"
				title='<%= LanguageUtil.get(resourceBundle, "mapped-products") %>'
			>
				<clay:headless-data-set-display
					apiURL="<%= cpDefinitionDiagramSettingDisplayContext.getCPDefinitionDiagramEntriesAPIURL() %>"
					formId="fm"
					id="<%= CSDiagramDataSetConstants.CS_DIAGRAM_MAPPED_PRODUCTS_DATA_SET_KEY %>"
					itemsPerPage="<%= 10 %>"
					namespace="<%= liferayPortletResponse.getNamespace() %>"
					pageNumber="<%= 1 %>"
					portletURL="<%= cpDefinitionDiagramSettingDisplayContext.getPortletURL() %>"
					style="stacked"
				/>
			</commerce-ui:panel>
		</div>
	</div>
</aui:form>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"currentURL", currentURL
		).put(
			"diagramId", (cpDefinitionDiagramSetting != null) ? cpDefinitionDiagramSetting.getCPDefinitionDiagramSettingId() : 0
		).put(
			"selectType", liferayPortletResponse.getNamespace() + "selectType"
		).build()
	%>'
	module="js/edit_cp_definition_diagram_setting"
/>