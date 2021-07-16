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

CPDefinitionDiagramSetting cpDefinitionDiagramSetting = cpDefinitionDiagramSettingDisplayContext.fetchCPDefinitionDiagramSetting();

String type = BeanParamUtil.getString(cpDefinitionDiagramSetting, renderRequest, "type", DefaultCPDefinitionDiagramType.KEY);

CPDefinitionDiagramType cpDefinitionDiagramType = cpDefinitionDiagramSettingDisplayContext.getCPDefinitionDiagramType(type);
%>

<div class="pt-4">
	<portlet:actionURL name="/cp_definitions/edit_cp_definition_diagram_setting" var="editProductDefinitionDiagramSettingActionURL" />

	<aui:form action="<%= editProductDefinitionDiagramSettingActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateCPDefinitionDiagramSetting" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionDiagramSettingDisplayContext.getCPDefinitionId() %>" />
		<aui:input name="cpDefinitionDiagramSettingId" type="hidden" value="<%= (cpDefinitionDiagramSetting == null) ? StringPool.BLANK : cpDefinitionDiagramSetting.getCPDefinitionDiagramSettingId() %>" />

		<div class="col-md-8">
			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "diagram-settings") %>'
			>
				<aui:select bean="<%= cpDefinitionDiagramSetting %>" label="type" model="<%= CPDefinitionDiagramSetting.class %>" name="type" onChange='<%= liferayPortletResponse.getNamespace() + "selectType();" %>'>

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
				title='<%= LanguageUtil.get(request, "diagram-mapping") %>'
			>

				<%
				cpDefinitionDiagramType.render(cpDefinitionDiagramSetting, request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
				%>

			</commerce-ui:panel>
		</div>

		<div class="col-md-4" />
	</aui:form>
</div>
