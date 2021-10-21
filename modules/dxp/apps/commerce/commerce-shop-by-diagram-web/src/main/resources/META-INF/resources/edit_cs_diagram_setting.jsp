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
CSDiagramSettingDisplayContext csDiagramSettingDisplayContext = (CSDiagramSettingDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = csDiagramSettingDisplayContext.getCPDefinition();

CSDiagramSetting csDiagramSetting = csDiagramSettingDisplayContext.fetchCSDiagramSetting();

String type = BeanParamUtil.getString(csDiagramSetting, renderRequest, "type", DefaultCSDiagramType.KEY);

CSDiagramType csDiagramType = csDiagramSettingDisplayContext.getCSDiagramType(type);
%>

<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/commerce-shop-by-diagram-web/css/shop-by-diagram-edit-page.css") %>" rel="stylesheet" />

<portlet:actionURL name="/cp_definitions/edit_cs_diagram_setting" var="editProductDefinitionDiagramSettingActionURL" />

<aui:form action="<%= editProductDefinitionDiagramSettingActionURL %>" cssClass="mt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateCSDiagramSetting" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />
	<aui:input name="radius" type="hidden" value="<%= (csDiagramSetting != null) ? csDiagramSetting.getRadius() : csDiagramSettingDisplayContext.getRadius() %>" />

	<liferay-ui:error exception="<%= NoSuchCPAttachmentFileEntryException.class %>" message="please-select-an-existing-file" />
	<liferay-ui:error exception="<%= NoSuchFileEntryException.class %>" message="please-select-an-existing-file" />

	<div class="row">
		<div class="col-lg-8 d-flex flex-column">
			<commerce-ui:panel
				elementClasses="flex-fill"
				title='<%= LanguageUtil.get(resourceBundle, "diagram-settings") %>'
			>
				<aui:select bean="<%= csDiagramSetting %>" label="type" model="<%= CSDiagramSetting.class %>" name="type">

					<%
					for (CSDiagramType curCSDiagramType : csDiagramSettingDisplayContext.getCSDiagramTypes()) {
						String csDiagramTypeKey = curCSDiagramType.getKey();
					%>

						<aui:option label="<%= curCSDiagramType.getLabel(locale) %>" selected="<%= (csDiagramSetting != null) && csDiagramTypeKey.equals(type) %>" value="<%= curCSDiagramType.getKey() %>" />

					<%
					}
					%>

				</aui:select>
			</commerce-ui:panel>
		</div>

		<div class="col-lg-4">
			<commerce-ui:panel
				bodyClasses="p-0 preview-container"
				elementClasses="flex-fill"
				title='<%= LanguageUtil.get(resourceBundle, "diagram-file") %>'
			>

				<%
				FileEntry fileEntry = csDiagramSettingDisplayContext.fetchFileEntry();
				%>

				<aui:model-context bean="<%= fileEntry %>" model="<%= FileEntry.class %>" />

				<div class="lfr-attachment-cover-image-selector">
					<portlet:actionURL name="/cp_definitions/upload_cs_diagram_setting_image" var="uploadCSDiagramSettingImageActionURL" />

					<liferay-item-selector:image-selector
						draggableImage="vertical"
						fileEntryId='<%= BeanParamUtil.getLong(fileEntry, request, "fileEntryId") %>'
						itemSelectorEventName="addFileEntry"
						itemSelectorURL="<%= csDiagramSettingDisplayContext.getImageItemSelectorUrl() %>"
						maxFileSize="<%= csDiagramSettingDisplayContext.getImageMaxSize() %>"
						paramName="fileEntry"
						uploadURL="<%= uploadCSDiagramSettingImageActionURL %>"
						validExtensions="<%= StringUtil.merge(csDiagramSettingDisplayContext.getImageExtensions(), StringPool.COMMA_AND_SPACE) %>"
					/>
				</div>
			</commerce-ui:panel>
		</div>

		<div class="col-lg-8 d-flex flex-column">
			<commerce-ui:panel
				bodyClasses="p-0"
				elementClasses="flex-fill"
				title='<%= LanguageUtil.get(resourceBundle, "diagram-mapping") %>'
			>

				<%
				if (csDiagramSetting != null) {
					csDiagramType.render(csDiagramSetting, request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
				}
				else {
				%>

					<div class="p-3 text-center">
						<liferay-ui:message key="please-upload-a-file" />
					</div>

				<%
				}
				%>

			</commerce-ui:panel>
		</div>

		<div class="col-lg-4">
			<commerce-ui:panel
				bodyClasses="p-0"
				elementClasses="flex-fill"
				title='<%= LanguageUtil.get(resourceBundle, "mapped-products") %>'
			>
				<react:component
					module="js/DiagramTable/DiagramTable"
					props='<%=
						HashMapBuilder.<String, Object>put(
							"isAdmin", true
						).put(
							"productId", cpDefinition.getCProductId()
						).build()
					%>'
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
			"diagramId", (csDiagramSetting != null) ? csDiagramSetting.getCSDiagramSettingId() : 0
		).put(
			"selectType", liferayPortletResponse.getNamespace() + "selectType"
		).build()
	%>'
	module="js/edit_cp_definition_diagram_setting"
/>