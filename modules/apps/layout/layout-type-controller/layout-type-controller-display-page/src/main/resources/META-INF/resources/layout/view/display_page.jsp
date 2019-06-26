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
String ppid = ParamUtil.getString(request, "p_p_id");
%>

<liferay-ui:success key="displayPagePublished" message="the-display-page-template-was-published-succesfully" />

<c:choose>
	<c:when test="<%= (themeDisplay.isStatePopUp() || themeDisplay.isWidget() || layoutTypePortlet.hasStateMax()) && Validator.isNotNull(ppid) %>">

		<%
		String templateId = null;
		String templateContent = null;
		String langType = null;

		if (themeDisplay.isStatePopUp() || themeDisplay.isWidget()) {
			templateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "pop_up";
			templateContent = LayoutTemplateLocalServiceUtil.getContent("pop_up", true, theme.getThemeId());
			langType = LayoutTemplateLocalServiceUtil.getLangType("pop_up", true, theme.getThemeId());
		}
		else {
			ppid = StringUtil.split(layoutTypePortlet.getStateMax())[0];

			templateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "max";
			templateContent = LayoutTemplateLocalServiceUtil.getContent("max", true, theme.getThemeId());
			langType = LayoutTemplateLocalServiceUtil.getLangType("max", true, theme.getThemeId());
		}

		if (Validator.isNotNull(templateContent)) {
			RuntimePageUtil.processTemplate(request, response, ppid, new StringTemplateResource(templateId, templateContent), langType);
		}
		%>

	</c:when>
	<c:otherwise>

		<%
		AssetRendererFactory assetRendererFactory = displayPageLayoutTypeControllerDisplayContext.getAssetRendererFactory();

		InfoDisplayObjectProvider infoDisplayObjectProvider = displayPageLayoutTypeControllerDisplayContext.getInfoDisplayObjectProvider();
		%>

		<c:if test="<%= assetRendererFactory != null %>">
			<liferay-ui:success key='<%= assetRendererFactory.getPortletId() + "requestProcessed" %>' message="your-request-processed-successfully" />
		</c:if>

		<c:choose>
			<c:when test="<%= (assetRendererFactory != null) && !assetRendererFactory.hasPermission(permissionChecker, infoDisplayObjectProvider.getClassPK(), ActionKeys.VIEW) %>">
				<div class="layout-content" id="main-content" role="main">
					<div class="container-fluid-1280 pt-3">
						<div class="alert alert-danger">
							<liferay-ui:message key="you-do-not-have-permission-to-view-this-page" />
						</div>
					</div>
				</div>
			</c:when>
			<c:when test="<%= infoDisplayObjectProvider != null %>">

				<%
				LayoutPageTemplateEntry layoutPageTemplateEntry = LayoutPageTemplateEntryLocalServiceUtil.getLayoutPageTemplateEntry(displayPageLayoutTypeControllerDisplayContext.getLayoutPageTemplateEntryId());
				%>

				<liferay-layout:render-fragment-layout
					fieldValues="<%= displayPageLayoutTypeControllerDisplayContext.getInfoDisplayFieldsValues() %>"
					groupId="<%= infoDisplayObjectProvider.getGroupId() %>"
					mode="<%= FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE %>"
					plid="<%= layoutPageTemplateEntry.getPlid() %>"
				/>
			</c:when>
		</c:choose>
	</c:otherwise>
</c:choose>

<liferay-ui:layout-common />