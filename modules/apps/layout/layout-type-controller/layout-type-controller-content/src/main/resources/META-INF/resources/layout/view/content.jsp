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

<%@ include file="/layout/view/init.jsp" %>

<%
String ppid = ParamUtil.getString(request, "p_p_id");

if ((themeDisplay.isStatePopUp() || themeDisplay.isWidget() || layoutTypePortlet.hasStateMax()) && Validator.isNotNull(ppid)) {
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
	try {
		request.setAttribute(WebKeys.PORTLET_DECORATE, Boolean.FALSE);

		LayoutPageTemplateStructure layoutPageTemplateStructure = LayoutPageTemplateStructureLocalServiceUtil.fetchLayoutPageTemplateStructure(layout.getGroupId(), PortalUtil.getClassNameId(Layout.class.getName()), layout.getPlid(), true);

		String data = layoutPageTemplateStructure.getData();
%>

		<c:if test="<%= Validator.isNotNull(data) %>">

			<%
			JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(layoutPageTemplateStructure.getData());

			JSONArray structureJSONArray = dataJSONObject.getJSONArray("structure");
			%>

			<c:if test="<%= structureJSONArray != null %>">
				<div class="layout-content" id="main-content" role="main">

					<%
					for (int i = 0; i < structureJSONArray.length(); i++) {
						JSONObject rowJSONObject = structureJSONArray.getJSONObject(i);

						JSONObject rowConfigJSONObject = rowJSONObject.getJSONObject("config");

						String backgroundColor = StringPool.BLANK;
						String backgroundImage = StringPool.BLANK;
						boolean columnSpacing = true;
						String containerType = StringPool.BLANK;
						long paddingHorizontal = 3L;
						long paddingVertical = 3L;

						if (rowConfigJSONObject != null) {
							backgroundColor = rowConfigJSONObject.getString("backgroundColor");
							backgroundImage = rowConfigJSONObject.getString("backgroundImage");
							columnSpacing = GetterUtil.getBoolean(rowConfigJSONObject.getString("columnSpacing"), columnSpacing);
							containerType = rowConfigJSONObject.getString("containerType");
							paddingHorizontal = GetterUtil.getLong(rowConfigJSONObject.getString("paddingHorizontal"), paddingHorizontal);
							paddingVertical = GetterUtil.getLong(rowConfigJSONObject.getString("paddingVertical"), paddingVertical);
						}
					%>

						<div class="container-fluid px-<%= paddingHorizontal %> py-<%= paddingVertical %>" style="<%= Validator.isNotNull(backgroundColor) ? "background-color:" + backgroundColor + ";" : StringPool.BLANK %> <%= Validator.isNotNull(backgroundImage) ? "background-image: url(" + backgroundImage + "); background-position: 50% 50%; background-repeat: no-repeat; background-size: cover;" : StringPool.BLANK %>">
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

												<%= FragmentEntryRenderUtil.renderFragmentEntryLink(fragmentEntryLink, FragmentEntryLinkConstants.VIEW, Collections.emptyMap(), request, response, locale) %>

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
			</c:if>
		</c:if>

<%
	}
	finally {
		request.removeAttribute(WebKeys.PORTLET_DECORATE);
	}
}
%>

<liferay-ui:layout-common />