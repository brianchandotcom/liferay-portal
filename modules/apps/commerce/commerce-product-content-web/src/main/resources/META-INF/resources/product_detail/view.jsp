<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
).put(
	"groupedCPTypeHelper", (GroupedCPTypeHelper)request.getAttribute(GroupedCPTypeWebKeys.GROUPED_CP_TYPE_HELPER)
).put(
	"virtualCPTypeHelper", (VirtualCPTypeHelper)request.getAttribute(VirtualCPTypeWebKeys.VIRTUAL_CP_TYPE_HELPER)
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