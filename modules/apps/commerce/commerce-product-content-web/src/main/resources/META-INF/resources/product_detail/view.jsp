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
CPContentConfigurationDisplayContext cpContentConfigurationDisplayContext = (CPContentConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);

Map<String, Object> contextObjects = HashMapBuilder.<String, Object>put(
	"cpCatalogEntry", cpCatalogEntry
).put(
	"cpContentHelper", cpContentHelper
).build();

CPInstance cpInstance = cpContentHelper.getDefaultCPInstance(request);

request.setAttribute("cpCatalogEntry", cpCatalogEntry);
request.setAttribute("cpInstance", cpInstance);
%>

<c:choose>
	<c:when test="<%= cpContentConfigurationDisplayContext.isSelectionStyleADT() %>">
		<liferay-ddm:template-renderer
			className="<%= CPContentPortlet.class.getName() %>"
			contextObjects="<%= contextObjects %>"
			displayStyle="<%= cpContentConfigurationDisplayContext.getDisplayStyle() %>"
			displayStyleGroupId="<%= cpContentConfigurationDisplayContext.getDisplayStyleGroupId() %>"
			entries="<%= Collections.singletonList(cpCatalogEntry) %>"
		/>
	</c:when>
	<c:when test="<%= cpContentConfigurationDisplayContext.isSelectionStyleCustomRenderer() %>">
		<c:choose>
			<c:when test="<%= cpCatalogEntry == null %>">
				<c:if test="<%= permissionChecker.isCompanyAdmin() || permissionChecker.isGroupAdmin(scopeGroupId) %>">
					<div class="alert alert-info">
						<liferay-ui:message key="there-is-no-product-to-display" />
					</div>
				</c:if>
			</c:when>
			<c:otherwise>

				<%
				cpContentHelper.renderCPType(request, response);
				%>

			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>