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

<%@ include file="/render_fragment_layout/init.jsp" %>

<%
Map<String, Object> fieldValues = (Map<String, Object>)request.getAttribute("liferay-layout:render-fragment-layout:fieldValues");
String mode = (String)request.getAttribute("liferay-layout:render-fragment-layout:mode");
long previewClassNameId = (long)request.getAttribute("liferay-layout:render-fragment-layout:previewClassNameId");
long previewClassPK = (long)request.getAttribute("liferay-layout:render-fragment-layout:previewClassPK");
int previewType = (int)request.getAttribute("liferay-layout:render-fragment-layout:previewType");
long[] segmentsExperienceIds = (long[])request.getAttribute("liferay-layout:render-fragment-layout:segmentsExperienceIds");

RenderFragmentLayoutDisplayContext renderFragmentLayoutDisplayContext = (RenderFragmentLayoutDisplayContext)request.getAttribute("render_layout_data_structure.jsp-renderFragmentLayoutDisplayContext");

JSONObject dataJSONObject = (JSONObject)request.getAttribute("render_layout_data_structure.jsp-dataJSONObject");

JSONArray structureJSONArray = dataJSONObject.getJSONArray("structure");

if (structureJSONArray != null) {
	for (int i = 0; i < structureJSONArray.length(); i++) {
		JSONObject rowJSONObject = structureJSONArray.getJSONObject(i);

		JSONObject rowConfigJSONObject = rowJSONObject.getJSONObject("config");

		int type = rowJSONObject.getInt("type", FragmentConstants.TYPE_COMPONENT);
%>

		<c:choose>
			<c:when test="<%= type == FragmentConstants.TYPE_COMPONENT %>">

				<%
				String backgroundColorCssClass = StringPool.BLANK;
				String backgroundImage = StringPool.BLANK;
				boolean columnSpacing = true;
				String containerType = "fluid";
				long paddingHorizontal = -1L;
				long paddingVertical = -1L;

				if (rowConfigJSONObject != null) {
					backgroundColorCssClass = rowConfigJSONObject.getString("backgroundColorCssClass");
					backgroundImage = renderFragmentLayoutDisplayContext.getBackgroundImage(rowConfigJSONObject);
					columnSpacing = rowConfigJSONObject.getBoolean("columnSpacing", true);
					containerType = rowConfigJSONObject.getString("containerType");
					paddingHorizontal = rowConfigJSONObject.getLong("paddingHorizontal", paddingHorizontal);
					paddingVertical = rowConfigJSONObject.getLong("paddingVertical", paddingVertical);
				}
				%>

				<section class="<%= Validator.isNotNull(backgroundColorCssClass) ? "bg-" + backgroundColorCssClass : "" %>" style="<%= Validator.isNotNull(backgroundImage) ? "background-image: url(" + backgroundImage + "); background-position: 50% 50%; background-repeat: no-repeat; background-size: cover;" : StringPool.BLANK %>">
					<div class="<%= Objects.equals(containerType, "fluid") ? "container-fluid" : "container" %> <%= (paddingHorizontal != -1L) ? "px-" + paddingHorizontal : "" %> <%= (paddingVertical != -1L) ? "py-" + paddingVertical : "" %>">
						<div class="row <%= !columnSpacing ? "no-gutters" : StringPool.BLANK %>">

							<%
							JSONArray columnsJSONArray = rowJSONObject.getJSONArray("columns");

							for (int j = 0; j < columnsJSONArray.length(); j++) {
								JSONObject columnJSONObject = columnsJSONArray.getJSONObject(j);

								String size = columnJSONObject.getString("size");
							%>

								<div class="<%= Validator.isNotNull(size) ? "col-md-" + size : "col-md" %>">

									<%
									JSONArray fragmentEntryLinkIdsJSONArray = columnJSONObject.getJSONArray("fragmentEntryLinkIds");

									for (int k = 0; k < fragmentEntryLinkIdsJSONArray.length(); k++) {
									%>

										<c:choose>
											<c:when test='<%= Objects.equals(fragmentEntryLinkIdsJSONArray.getString(k), "drop-zone") %>'>

												<%
												LayoutPageTemplateStructure layoutPageTemplateStructure = LayoutPageTemplateStructureLocalServiceUtil.fetchLayoutPageTemplateStructure(themeDisplay.getScopeGroupId(), PortalUtil.getClassNameId(Layout.class.getName()), themeDisplay.getPlid(), true);

												String data = layoutPageTemplateStructure.getData(segmentsExperienceIds);
												%>

												<c:if test="<%= Validator.isNotNull(data) %>">

													<%
													request.setAttribute("render_layout_data_structure.jsp-dataJSONObject", JSONFactoryUtil.createJSONObject(data));
													%>

													<liferay-util:include page="/render_fragment_layout/render_layout_data_structure.jsp" servletContext="<%= application %>" />
												</c:if>
											</c:when>
											<c:otherwise>

												<%
												long fragmentEntryLinkId = fragmentEntryLinkIdsJSONArray.getLong(k);

												if (fragmentEntryLinkId <= 0) {
													continue;
												}

												FragmentEntryLink fragmentEntryLink = FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(fragmentEntryLinkId);

												if (fragmentEntryLink == null) {
													continue;
												}

												FragmentRendererController fragmentRendererController = (FragmentRendererController)request.getAttribute(FragmentActionKeys.FRAGMENT_RENDERER_CONTROLLER);

												DefaultFragmentRendererContext defaultFragmentRendererContext = new DefaultFragmentRendererContext(fragmentEntryLink);

												defaultFragmentRendererContext.setFieldValues(fieldValues);
												defaultFragmentRendererContext.setLocale(locale);
												defaultFragmentRendererContext.setMode(mode);
												defaultFragmentRendererContext.setPreviewClassNameId(previewClassNameId);
												defaultFragmentRendererContext.setPreviewClassPK(previewClassPK);
												defaultFragmentRendererContext.setPreviewType(previewType);
												defaultFragmentRendererContext.setSegmentsExperienceIds(segmentsExperienceIds);
												%>

												<%= fragmentRendererController.render(defaultFragmentRendererContext, request, response) %>
											</c:otherwise>
										</c:choose>

									<%
									}
									%>

								</div>

							<%
							}
							%>

						</div>
					</div>
				</section>
			</c:when>
			<c:otherwise>
				<section>

					<%
					JSONArray columnsJSONArray = rowJSONObject.getJSONArray("columns");

					for (int j = 0; j < columnsJSONArray.length(); j++) {
						JSONObject columnJSONObject = columnsJSONArray.getJSONObject(j);

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

							FragmentRendererController fragmentRendererController = (FragmentRendererController)request.getAttribute(FragmentActionKeys.FRAGMENT_RENDERER_CONTROLLER);

							DefaultFragmentRendererContext defaultFragmentRendererContext = new DefaultFragmentRendererContext(fragmentEntryLink);

							defaultFragmentRendererContext.setFieldValues(fieldValues);
							defaultFragmentRendererContext.setLocale(locale);
							defaultFragmentRendererContext.setMode(mode);
							defaultFragmentRendererContext.setPreviewClassNameId(previewClassNameId);
							defaultFragmentRendererContext.setPreviewClassPK(previewClassPK);
							defaultFragmentRendererContext.setPreviewType(previewType);
							defaultFragmentRendererContext.setSegmentsExperienceIds(segmentsExperienceIds);
					%>

							<%= fragmentRendererController.render(defaultFragmentRendererContext, request, response) %>

					<%
						}
					}
					%>

				</section>
			</c:otherwise>
		</c:choose>

<%
	}
}
%>