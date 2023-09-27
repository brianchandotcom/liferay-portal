<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/instance/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long assetEntryId = ParamUtil.getLong(request, "assetEntryId");

String type = ParamUtil.getString(request, "type");

AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByType(type);

AssetEntry assetEntry = assetRendererFactory.getAssetEntry(assetEntryId);

long assetEntryVersionId = ParamUtil.getLong(request, "assetEntryVersionId");

AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(assetEntryVersionId, AssetRendererFactory.TYPE_LATEST);

request.setAttribute(WebKeys.WORKFLOW_ASSET_PREVIEW, Boolean.TRUE);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(assetRenderer.getTitle(locale));
%>

<clay:container-fluid
	cssClass="container-view"
>
	<clay:col
		cssClass="lfr-asset-column lfr-asset-column-details"
		md="12"
	>
		<div class="card">
			<div class="panel-body">
				<div class="instance-content-container">
					<c:if test="<%= assetEntry != null %>">
						<liferay-asset:asset-display
							assetEntry="<%= assetEntry %>"
							assetRenderer="<%= assetRenderer %>"
							assetRendererFactory="<%= assetRendererFactory %>"
							showExtraInfo="<%= workflowInstanceViewDisplayContext.isShowExtraInfo() %>"
						/>

						<%
						WorkflowHandler<?> workflowHandler = WorkflowHandlerRegistryUtil.getWorkflowHandler(assetRenderer.getClassName());

						String viewInContextURL = workflowHandler.getURLViewInContext(assetRenderer.getClassPK(), liferayPortletRequest, liferayPortletResponse, null);
						%>

						<c:if test="<%= viewInContextURL != null %>">
							<div class="asset-more">
								<aui:a href="<%= viewInContextURL %>"><liferay-ui:message key="view-in-context" /> &raquo;</aui:a>
							</div>
						</c:if>
					</c:if>
				</div>
			</div>
		</div>
	</clay:col>
</clay:container-fluid>