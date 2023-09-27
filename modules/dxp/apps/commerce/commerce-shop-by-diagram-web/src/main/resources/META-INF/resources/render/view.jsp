<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);

CSDiagramCPTypeHelper csDiagramCPTypeHelper = (CSDiagramCPTypeHelper)request.getAttribute(CSDiagramWebKeys.CS_DIAGRAM_CP_TYPE_HELPER);

CSDiagramSetting csDiagramSetting = csDiagramCPTypeHelper.getCSDiagramSetting(commerceContext.getAccountEntry(), cpCatalogEntry.getCPDefinitionId(), themeDisplay.getPermissionChecker());
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