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
String redirect = ParamUtil.getString(request, "redirect");

AssetEntry assetEntry = workflowTaskDisplayContext.getAssetEntry();

AssetRenderer assetRenderer = workflowTaskDisplayContext.getAssetRenderer(workflowTaskDisplayContext.getWorkflowTask());

AssetRendererFactory assetRendererFactory = workflowTaskDisplayContext.getAssetRendererFactory();

String languageId = LanguageUtil.getLanguageId(request);

String[] availableLanguageIds = assetRenderer.getAvailableLanguageIds();

if (ArrayUtil.isNotEmpty(availableLanguageIds) && !ArrayUtil.contains(availableLanguageIds, languageId)) {
	languageId = assetRenderer.getDefaultLanguageId();
}

String title = assetRenderer.getTitle(workflowTaskDisplayContext.getTaskContentLocale());

request.setAttribute(WebKeys.WORKFLOW_ASSET_PREVIEW, Boolean.TRUE);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(title);
%>

<div class="container-fluid-1280 main-content-body">
	<div class="col-md-12 lfr-asset-column lfr-asset-column-details">
		<div class="card-horizontal main-content-card">
			<div class="panel-body">
				<c:if test="<%= assetEntry != null %>">
					<div class="locale-actions">
						<liferay-ui:language
							formAction="<%= currentURL %>"
							languageId="<%= languageId %>"
							languageIds="<%= availableLanguageIds %>"
						/>
					</div>

					<liferay-asset:asset-display
						assetEntry="<%= assetEntry %>"
						assetRenderer="<%= assetRenderer %>"
						assetRendererFactory="<%= assetRendererFactory %>"
					/>
				</c:if>

				<%
				String viewInContextURL = assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, null);
				%>

				<c:if test="<%= viewInContextURL != null %>">
					<div class="asset-more">
						<aui:a href="<%= viewInContextURL %>"><liferay-ui:message key="view-in-context" /> &raquo;</aui:a>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>