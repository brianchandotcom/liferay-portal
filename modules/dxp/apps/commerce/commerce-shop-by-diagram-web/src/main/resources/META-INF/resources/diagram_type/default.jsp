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
%>

<div>
	<span aria-hidden="true" class="loading-animation"></span>

	<react:component
		module="js/diagram/Diagram"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"diagramId", csDiagramSetting.getCSDiagramSettingId()
			).put(
				"imageURL", csDiagramSettingDisplayContext.getImageURL()
			).put(
				"isAdmin", true
			).put(
				"productId", cpDefinition.getCProductId()
			).build()
		%>'
	/>
</div>