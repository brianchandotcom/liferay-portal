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
AssetEntry assetEntry = assetDisplayLayoutTypeControllerDisplayContext.getAssetEntry();

LayoutPageTemplateStructure layoutPageTemplateStructure = null;

JSONArray structureJSONArray = null;

if (assetEntry != null) {
	layoutPageTemplateStructure = LayoutPageTemplateStructureLocalServiceUtil.fetchLayoutPageTemplateStructure(assetEntry.getGroupId(), PortalUtil.getClassNameId(LayoutPageTemplateEntry.class.getName()), assetDisplayLayoutTypeControllerDisplayContext.getLayoutPageTemplateEntryId(), true);

	String data = layoutPageTemplateStructure.getData();

	if (Validator.isNotNull(data)) {
		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(layoutPageTemplateStructure.getData());

		structureJSONArray = dataJSONObject.getJSONArray("structure");
	}
}
%>

<c:choose>
	<c:when test="<%= structureJSONArray != null %>">

		<%
		String currentI18nLanguageId = GetterUtil.getString(request.getAttribute(AssetDisplayWebKeys.CURRENT_I18N_LANGUAGE_ID), themeDisplay.getLanguageId());

		try {
			request.setAttribute(WebKeys.PORTLET_DECORATE, Boolean.FALSE);
		%>

			<div class="layout-content" id="main-content" role="main">

				<%
				for (int i = 0; i < structureJSONArray.length(); i++) {
					JSONObject rowJSONObject = structureJSONArray.getJSONObject(i);

					JSONObject rowConfigJSONObject = rowJSONObject.getJSONObject("config");

					String backgroundColorCssClass = StringPool.BLANK;
					String backgroundImage = StringPool.BLANK;
					boolean columnSpacing = true;
					String containerType = StringPool.BLANK;
					long paddingHorizontal = 3L;
					long paddingVertical = 3L;

					if (rowConfigJSONObject != null) {
						backgroundColorCssClass = rowConfigJSONObject.getString("backgroundColorCssClass");
						backgroundImage = rowConfigJSONObject.getString("backgroundImage");
						columnSpacing = GetterUtil.getBoolean(rowConfigJSONObject.getString("columnSpacing"), true);
						containerType = rowConfigJSONObject.getString("containerType");
						paddingHorizontal = GetterUtil.getLong(rowConfigJSONObject.getString("paddingHorizontal"), paddingHorizontal);
						paddingVertical = GetterUtil.getLong(rowConfigJSONObject.getString("paddingVertical"), paddingVertical);
					}
				%>

					<div class="container-fluid bg-fragment-<%= backgroundColorCssClass %> px-<%= paddingHorizontal %> py-<%= paddingVertical %>" style="<%= Validator.isNotNull(backgroundImage) ? "background-image: url(" + backgroundImage + "); background-position: 50% 50%; background-repeat: no-repeat; background-size: cover;" : StringPool.BLANK %>">
						<div class="<%= Objects.equals(containerType, "fixed") ? "container" : "container-fluid" %> p-0">
							<div class="row <%= columnSpacing ? StringPool.BLANK : "no-gutters" %>">

								<%
								JSONArray columnsJSONArray = rowJSONObject.getJSONArray("columns");

								for (int j = 0; j < columnsJSONArray.length(); j++) {
									JSONObject columnJSONObject = columnsJSONArray.getJSONObject(j);

									String size = columnJSONObject.getString("size");
								%>

									<div class="col <%= Validator.isNotNull(size) ? "col-" + size : StringPool.BLANK %>">

										<%
										JSONArray fragmentEntryLinkIdsJSONArray = columnJSONObject.getJSONArray("fragmentEntryLinkIds");

										for (int k = 0; k < fragmentEntryLinkIdsJSONArray.length(); k++) {
											long fragmentEntryLinkId = fragmentEntryLinkIdsJSONArray.getLong(k);

											if (fragmentEntryLinkId <= 0) {
												continue;
											}

											FragmentEntryLink fragmentEntryLink = FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(fragmentEntryLinkId);

											if (fragmentEntryLink == null) {
												continue;
											}
										%>

											<%= FragmentEntryRenderUtil.renderFragmentEntryLink(fragmentEntryLink, FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE, assetDisplayLayoutTypeControllerDisplayContext.getAssetDisplayFieldsValues(), request, response, locale) %>

										<%
										}
										%>

									</div>

								<%
								}
								%>

							</div>
						</div>
					</div>

				<%
				}
				%>

			</div>

		<%
		}
		finally {
			request.removeAttribute(WebKeys.PORTLET_DECORATE);

			request.setAttribute(WebKeys.I18N_LANGUAGE_ID, currentI18nLanguageId);
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