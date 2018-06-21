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

<c:choose>
	<c:when test="<%= ListUtil.isNotEmpty(fragmentEntryLinks) %>">

		<%
		Map<String, Object> fieldValues = assetDisplayLayoutTypeControllerDisplayContext.getAssetDisplayFieldsValues();

		StringBundler sb = new StringBundler(fragmentEntryLinks.size());

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			sb.append(FragmentEntryRenderUtil.renderFragmentEntryLink(fragmentEntryLink, FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE, fieldValues, request, response));
		}

		try {
			request.setAttribute(WebKeys.PORTLET_DECORATE, Boolean.FALSE);
		%>

			<%= sb.toString() %>

		<%
		}
		finally {
			request.removeAttribute(WebKeys.PORTLET_DECORATE);
		}
		%>

	</c:when>
	<c:when test="<%= assetEntry != null %>">
		<div class="sheet">
			<div class="sheet-header">
				<h2 class="sheet-title">
					<%= assetEntry.getTitle(locale) %>
				</h2>

				<div class="sheet-text">
					<%= assetEntry.getDescription(locale) %>
				</div>
			</div>
		</div>
	</c:when>
	<c:otherwise>

		<%
		if (themeDisplay.isStatePopUp() || themeDisplay.isWidget() || layoutTypePortlet.hasStateMax()) {
			String ppid = ParamUtil.getString(request, "p_p_id");

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
		}
		else {
			String themeId = theme.getThemeId();

			String layoutTemplateId = layoutTypePortlet.getLayoutTemplateId();

			if (Validator.isNull(layoutTemplateId)) {
				layoutTemplateId = PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID;
			}

			LayoutTemplate layoutTemplate = LayoutTemplateLocalServiceUtil.getLayoutTemplate(layoutTemplateId, false, theme.getThemeId());

			if (layoutTemplate != null) {
				themeId = layoutTemplate.getThemeId();
			}

			String templateId = themeId + LayoutTemplateConstants.CUSTOM_SEPARATOR + layoutTypePortlet.getLayoutTemplateId();
			String templateContent = LayoutTemplateLocalServiceUtil.getContent(layoutTypePortlet.getLayoutTemplateId(), false, theme.getThemeId());
			String langType = LayoutTemplateLocalServiceUtil.getLangType(layoutTypePortlet.getLayoutTemplateId(), false, theme.getThemeId());

			if (Validator.isNotNull(templateContent)) {
				RuntimePageUtil.processTemplate(request, response, new StringTemplateResource(templateId, templateContent), langType);
			}
		}
		%>

	</c:otherwise>
</c:choose>

<liferay-ui:layout-common />