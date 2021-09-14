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
%>

<div class="cp-renderer">
	<div class="card d-flex flex-column product-card">
		<div class="aspect-ratio aspect-ratio-4-to-3 card-item-first">

			<%
			String url = cpContentHelper.getFriendlyURL(cpCatalogEntry, themeDisplay);
			%>

			<a href="<%= url %>">
				<div class="aspect-ratio-bg-cover aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid card-type-asset-icon h-100 w-100" style="background-image: url('<%= csDiagramCPTypeDisplayContext.getImageURL(cpCatalogEntry.getCPDefinitionId()) %>');"></div>
			</a>
		</div>

		<div class="card-body d-flex flex-column justify-content-between py-2">
			<div class="cp-information">
				<p class="card-title" title="<%= cpCatalogEntry.getName() %>">
					<a href="<%= url %>">
						<span class="text-truncate-inline">
							<span class="text-truncate"><%= cpCatalogEntry.getName() %></span>
						</span>
					</a>
				</p>
			</div>

			<div>
				<div class="add-to-cart d-flex my-2 pt-5">
					<a class="btn btn-block btn-secondary" href="<%= url %>" role="button" style="margin-top: 0.35rem;">
						<liferay-ui:message key="view" />
					</a>
				</div>
			</div>
		</div>
	</div>
</div>