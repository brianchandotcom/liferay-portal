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
portletDisplay.setShowBackIcon(false);

BlueprintsContentDisplayBuilder blueprintsContentDisplayBuilder = new BlueprintsContentDisplayBuilder();

blueprintsContentDisplayBuilder.setAssetEntryId(ParamUtil.getLong(request, "assetEntryId"));
blueprintsContentDisplayBuilder.setEntryClassName(ParamUtil.getString(request, "entryClassName"));
blueprintsContentDisplayBuilder.setEntryClassPK(ParamUtil.getLong(request, "entryClassPK"));
blueprintsContentDisplayBuilder.setLocale(locale);
blueprintsContentDisplayBuilder.setPermissionChecker(permissionChecker);
blueprintsContentDisplayBuilder.setPortal(PortalUtil.getPortal());
blueprintsContentDisplayBuilder.setRenderRequest(renderRequest);
blueprintsContentDisplayBuilder.setRenderResponse(renderResponse);

BlueprintsContentDisplayContext blueprintsContentDisplayContext = blueprintsContentDisplayBuilder.build();
%>

<c:if test="<%= blueprintsContentDisplayContext.isVisible() %>">
	<div class="mb-2">
		<h4 class="component-title">
			<span class="asset-title d-inline">
				<%= HtmlUtil.escape(blueprintsContentDisplayContext.getHeaderTitle()) %>
			</span>

			<c:if test="<%= blueprintsContentDisplayContext.hasEditPermission() %>">
				<span class="d-inline-flex">
					<liferay-ui:icon
						cssClass="visible-interaction"
						icon="pencil"
						label="<%= false %>"
						markupView="lexicon"
						message='<%= LanguageUtil.format(request, "edit-x-x", new Object[] {"hide-accessible", HtmlUtil.escape(blueprintsContentDisplayContext.getIconEditTarget())}, false) %>'
						method="get"
						url="<%= blueprintsContentDisplayContext.getIconURLString() %>"
					/>
				</span>
			</c:if>
		</h4>
	</div>

	<liferay-asset:asset-display
		assetEntry="<%= blueprintsContentDisplayContext.getAssetEntry() %>"
		assetRenderer="<%= blueprintsContentDisplayContext.getAssetRenderer() %>"
		assetRendererFactory="<%= blueprintsContentDisplayContext.getAssetRendererFactory() %>"
	/>
</c:if>