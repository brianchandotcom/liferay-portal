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
CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);

CSDiagramCPTypeHelper csDiagramCPTypeHelper = (CSDiagramCPTypeHelper)request.getAttribute(CSDiagramWebKeys.CS_DIAGRAM_CP_TYPE_HELPER);

CSDiagramSetting csDiagramSetting = csDiagramCPTypeHelper.getCSDiagramSetting(commerceContext.getCommerceAccount(), cpCatalogEntry.getCPDefinitionId(), themeDisplay.getPermissionChecker());
%>

<div class="col my-4 p-0">
	<h4 class="component-title mb-4 text-7">
		<%= cpCatalogEntry.getName() %>
	</h4>

	<p class="text-3">
		<%= cpCatalogEntry.getShortDescription() %>
	</p>
</div>

<%
if (csDiagramSetting != null) {
	CSDiagramType csDiagramType = csDiagramCPTypeHelper.getCSDiagramType(csDiagramSetting.getType());

	csDiagramType.render(csDiagramSetting, request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
}
else {
%>

	<div class="row">
		<div class="col-lg-8 d-flex flex-column">
			<commerce-ui:panel
				bodyClasses="p-0"
				elementClasses="flex-fill"
				title='<%= LanguageUtil.get(resourceBundle, "diagram-mapping") %>'
			>
				<div class="p-3 text-center">
					<liferay-ui:message key="the-diagram-is-not-available" />
				</div>
			</commerce-ui:panel>
		</div>
	</div>

<%
}
%>