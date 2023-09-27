<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/info/item/renderer/diagram_card/init.jsp" %>

<%
CSDiagramSetting csDiagramSetting = csDiagramCPTypeHelper.getCSDiagramSetting(commerceContext.getAccountEntry(), cpCatalogEntry.getCPDefinitionId(), themeDisplay.getPermissionChecker());

String url = cpContentHelper.getFriendlyURL(cpCatalogEntry, themeDisplay);
%>

<div class="cp-renderer">
	<div class="card d-flex flex-column product-card">
		<div class="card-item-first position-relative">
			<a href="<%= url %>">
				<c:if test="<%= showImage %>">
					<liferay-adaptive-media:img
						class="img-fluid product-card-picture"
						fileVersion="<%= csDiagramCPTypeHelper.getCPDiagramImageFileVersion(cpCatalogEntry.getCPDefinitionId(), csDiagramSetting, request) %>"
					/>
				</c:if>
			</a>
		</div>

		<div class="card-body d-flex flex-column justify-content-between py-2">
			<div class="cp-information">
				<p class="card-subtitle">
					<span class="text-truncate-inline">
						<span class="text-truncate"></span>
					</span>
				</p>

				<c:if test="<%= showName %>">
					<p class="card-title" title="<%= cpCatalogEntry.getName() %>">
						<a href="<%= url %>">
							<span class="text-truncate-inline">
								<span class="text-truncate"><%= cpCatalogEntry.getName() %></span>
							</span>
						</a>
					</p>
				</c:if>

				<c:if test="<%= showPrice %>">
					<p class="card-body">
						<span class="two-lined-description">
							<%= cpCatalogEntry.getShortDescription() %>
						</span>
					</p>
				</c:if>

				<div class="add-to-cart d-flex my-2">
					<c:if test="<%= showAddToCartButton %>">
						<a class="btn btn-block btn-secondary" href="<%= url %>" role="button" style="margin-top: 0.35rem;">
							<liferay-ui:message key="view" />
						</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>