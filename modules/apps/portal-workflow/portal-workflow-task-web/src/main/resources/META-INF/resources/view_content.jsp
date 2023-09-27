<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

AssetEntry assetEntry = workflowTaskDisplayContext.getAssetEntry();

AssetRenderer<?> assetRenderer = workflowTaskDisplayContext.getAssetRenderer(workflowTaskDisplayContext.getWorkflowTask());

AssetRendererFactory<?> assetRendererFactory = workflowTaskDisplayContext.getAssetRendererFactory();

String languageId = LanguageUtil.getLanguageId(request);

String[] availableLanguageIds = assetRenderer.getAvailableLanguageIds();

if (ArrayUtil.isNotEmpty(availableLanguageIds) && !ArrayUtil.contains(availableLanguageIds, languageId)) {
	languageId = assetRenderer.getDefaultLanguageId();
}

request.setAttribute(WebKeys.WORKFLOW_ASSET_PREVIEW, Boolean.TRUE);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(assetRenderer.getTitle(workflowTaskDisplayContext.getTaskContentLocale()));
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
				<c:if test="<%= assetEntry != null %>">
					<c:if test="<%= assetRenderer.isLocalizable() %>">
						<div class="locale-actions">
							<liferay-ui:language
								formAction="<%= currentURL %>"
								languageId="<%= languageId %>"
								languageIds="<%= availableLanguageIds %>"
							/>
						</div>
					</c:if>

					<liferay-asset:asset-display
						assetEntry="<%= assetEntry %>"
						assetRenderer="<%= assetRenderer %>"
						assetRendererFactory="<%= assetRendererFactory %>"
						showExtraInfo="<%= workflowTaskDisplayContext.isShowExtraInfo() %>"
					/>
				</c:if>

				<%
				WorkflowHandler<?> workflowHandler = workflowTaskDisplayContext.getWorkflowHandler(workflowTaskDisplayContext.getWorkflowTask());

				String viewInContextURL = workflowHandler.getURLViewInContext(assetRenderer.getClassPK(), liferayPortletRequest, liferayPortletResponse, null);
				%>

				<c:if test="<%= viewInContextURL != null %>">
					<div class="asset-more">
						<aui:a href="<%= viewInContextURL %>"><liferay-ui:message key="view-in-context" /> &raquo;</aui:a>
					</div>
				</c:if>
			</div>
		</div>
	</clay:col>
</clay:container-fluid>