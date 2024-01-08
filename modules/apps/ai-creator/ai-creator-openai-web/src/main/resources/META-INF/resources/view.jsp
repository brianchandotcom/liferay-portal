<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AICreatorOpenAIDisplayContext aiCreatorOpenAIDisplayContext = (AICreatorOpenAIDisplayContext)request.getAttribute(AICreatorOpenAIDisplayContext.class.getName());
%>

<div>
	<div class="inline-item my-5 p-5 w-100">
		<span aria-hidden="true" class="loading-animation"></span>
	</div>

	<c:choose>
		<c:when test="<%= aiCreatorOpenAIDisplayContext.isGenerations() %>">
			<react:component
				module="{AICreatorImageModal} from ai-creator-openai-web"
				props="<%= aiCreatorOpenAIDisplayContext.getGenerationsProps() %>"
			/>
		</c:when>
		<c:otherwise>
			<react:component
				module="{AICreatorModal} from ai-creator-openai-web"
				props="<%= aiCreatorOpenAIDisplayContext.getCompletionProps() %>"
			/>
		</c:otherwise>
	</c:choose>
</div>