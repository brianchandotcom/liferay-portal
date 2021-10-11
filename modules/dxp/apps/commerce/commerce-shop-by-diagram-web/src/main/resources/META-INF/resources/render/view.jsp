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
CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);

CSDiagramCPTypeDisplayContext csDiagramCPTypeDisplayContext = (CSDiagramCPTypeDisplayContext)request.getAttribute(CSDiagramWebKeys.CS_DIAGRAM_CP_TYPE_DISPLAY_CONTEXT);

CSDiagramSetting csDiagramSetting = csDiagramCPTypeDisplayContext.getCSDiagramSetting(cpCatalogEntry.getCPDefinitionId());

if (csDiagramSetting != null) {
	CSDiagramType csDiagramType = csDiagramCPTypeDisplayContext.getCSDiagramType(csDiagramSetting.getType());

	csDiagramType.render(csDiagramSetting, request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
}
%>