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

<%@ include file="/definition_link/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

String randomNamespace = (String)row.getParameter("randomNamespace");

WorkflowDefinitionLinkSearchEntry workflowDefinitionLinkSearchEntry = (WorkflowDefinitionLinkSearchEntry)row.getObject();

Map<String, String> resourceTooltips = (Map<String, String>)row.getParameter("resourceTooltips");

String classname = workflowDefinitionLinkSearchEntry.getClassName();

String resource = workflowDefinitionLinkSearchEntry.getResource();
%>

<c:choose>
	<c:when test="<%= resourceTooltips.containsKey(classname) %>">
		<div class="workflow-definition-link-resource">
			<span id="<%= randomNamespace %>resourceTooltip">
				<%= resource %>
				<div class="clay-tooltip-right tooltip" id="<%= randomNamespace %>tooltip" role="tooltip">
					<div class="arrow"></div>
					<div class="tooltip-inner">
						<div><%= resourceTooltips.get(classname) %></div>
					</div>
				</div>
			</span>
		</div>
	</c:when>
	<c:otherwise><span><%= resource %></span></c:otherwise>
</c:choose>

<aui:script require="metal-dom/src/dom">
	var dom = metalDomSrcDom.default;

	var tooltip = document.getElementById('<%= randomNamespace %>tooltip');

	var resourceTooltip = document.getElementById(
		'<%= randomNamespace %>resourceTooltip'
	);

	if (resourceTooltip) {
		resourceTooltip.addEventListener('mouseover', function() {
			dom.toggleClasses(tooltip, 'show');
		});

		resourceTooltip.addEventListener('mouseout', function() {
			dom.toggleClasses(tooltip, 'show');
		});
	}
</aui:script>