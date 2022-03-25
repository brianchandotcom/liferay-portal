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

String url = cpContentHelper.getFriendlyURL(cpCatalogEntry, themeDisplay);
%>

<div class="cp-renderer">
	<div class="card d-flex flex-column product-card">
		<div class="card-item-first position-relative">
			<a href="<%= url %>">
				<liferay-adaptive-media:img
					class="img-fluid product-card-picture"
					fileVersion="<%= csDiagramCPTypeHelper.getCPDiagramImageFileVersion(cpCatalogEntry.getCPDefinitionId(), csDiagramSetting, request) %>"
				/>
			</a>
		</div>

		<div class="card-body d-flex flex-column justify-content-between py-2">
			<div class="cp-information">
				<p class="card-subtitle">
					<span class="text-truncate-inline">
						<span class="text-truncate"></span>
					</span>
				</p>

				<p class="card-title" title="<%= cpCatalogEntry.getName() %>">
					<a href="<%= url %>">
						<span class="text-truncate-inline">
							<span class="text-truncate"><%= cpCatalogEntry.getName() %></span>
						</span>
					</a>
				</p>

				<p class="card-body">
					<span class="two-lined-description">
						<%= cpCatalogEntry.getShortDescription() %>
					</span>
				</p>

				<div class="add-to-cart d-flex my-2">
					<a class="btn btn-block btn-secondary" href="<%= url %>" role="button" style="margin-top: 0.35rem;">
						<liferay-ui:message key="view" />
					</a>
				</div>
			</div>
		</div>
	</div>
</div>