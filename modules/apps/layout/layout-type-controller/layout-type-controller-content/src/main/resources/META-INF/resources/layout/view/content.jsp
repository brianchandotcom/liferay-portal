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

long previewAssetEntryId = ParamUtil.getLong(request, "previewAssetEntryId");
int previewAssetEntryType = ParamUtil.getInteger(request, "previewAssetEntryType");
%>

<liferay-ui:success key="layoutPublished" message="the-page-was-published-succesfully" />

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
		LayoutPageTemplateStructure layoutPageTemplateStructure = LayoutPageTemplateStructureLocalServiceUtil.fetchLayoutPageTemplateStructure(layout.getGroupId(), PortalUtil.getClassNameId(Layout.class.getName()), layout.getPlid(), true);

		long[] segmentsExperienceIds = GetterUtil.getLongValues(request.getAttribute(SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS), new long[] {SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT});

		String data = layoutPageTemplateStructure.getData(segmentsExperienceIds);
		%>

		<c:if test="<%= Validator.isNotNull(data) %>">

			<%
			JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(data);

			JSONArray structureJSONArray = dataJSONObject.getJSONArray("structure");
			%>

			<c:if test="<%= structureJSONArray != null %>">
				<div class="layout-content" id="main-content" role="main">

					<%
					try {
						request.setAttribute(WebKeys.PORTLET_DECORATE, Boolean.FALSE);

						for (int i = 0; i < structureJSONArray.length(); i++) {
							JSONObject rowJSONObject = structureJSONArray.getJSONObject(i);

							int type = GetterUtil.getInteger(rowJSONObject.getInt("type"), FragmentConstants.TYPE_COMPONENT);

							if (type == FragmentConstants.TYPE_COMPONENT) {
								String backgroundColorCssClass = StringPool.BLANK;
								String backgroundImage = StringPool.BLANK;
								boolean columnSpacing = true;
								String containerType = StringPool.BLANK;
								long paddingHorizontal = 3L;
								long paddingVertical = 3L;

								JSONObject rowConfigJSONObject = rowJSONObject.getJSONObject("config");

								if (rowConfigJSONObject != null) {
									backgroundColorCssClass = rowConfigJSONObject.getString("backgroundColorCssClass");
									backgroundImage = rowConfigJSONObject.getString("backgroundImage");
									columnSpacing = GetterUtil.getBoolean(rowConfigJSONObject.getString("columnSpacing"), true);
									containerType = rowConfigJSONObject.getString("containerType");
									paddingHorizontal = GetterUtil.getLong(rowConfigJSONObject.getString("paddingHorizontal"), paddingHorizontal);
									paddingVertical = GetterUtil.getLong(rowConfigJSONObject.getString("paddingVertical"), paddingVertical);
								}
					%>

								<section class="bg-<%= backgroundColorCssClass %>" style="<%= Validator.isNotNull(backgroundImage) ? "background-image: url(" + backgroundImage + "); background-position: 50% 50%; background-repeat: no-repeat; background-size: cover;" : StringPool.BLANK %>">
									<div class="<%= Objects.equals(containerType, "fluid") ? "container-fluid" : "container" %> <%= (paddingHorizontal != 3L) ? "px-" + paddingHorizontal : "" %> py-<%= paddingVertical %>">
										<div class="row <%= !columnSpacing ? "no-gutters" : StringPool.BLANK %>">

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

														FragmentRendererController fragmentRendererController = (FragmentRendererController)request.getAttribute(FragmentActionKeys.FRAGMENT_RENDERER_CONTROLLER);

														DefaultFragmentRendererContext defaultFragmentRendererContext = new DefaultFragmentRendererContext(fragmentEntryLink);

														defaultFragmentRendererContext.setLocale(locale);
														defaultFragmentRendererContext.setMode(FragmentEntryLinkConstants.VIEW);
														defaultFragmentRendererContext.setPreviewClassPK(previewAssetEntryId);
														defaultFragmentRendererContext.setPreviewType(previewAssetEntryType);
														defaultFragmentRendererContext.setSegmentsExperienceIds(segmentsExperienceIds);
													%>

														<%= fragmentRendererController.render(defaultFragmentRendererContext, request, response) %>

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

							<%
							}
							else {
							%>

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

											defaultFragmentRendererContext.setLocale(locale);
											defaultFragmentRendererContext.setMode(FragmentEntryLinkConstants.VIEW);
											defaultFragmentRendererContext.setPreviewClassPK(previewAssetEntryId);
											defaultFragmentRendererContext.setPreviewType(previewAssetEntryType);
											defaultFragmentRendererContext.setSegmentsExperienceIds(segmentsExperienceIds);
									%>

											<%= fragmentRendererController.render(defaultFragmentRendererContext, request, response) %>

									<%
										}
									}
									%>

								</section>

					<%
							}
						}
					}
					finally {
						request.removeAttribute(WebKeys.PORTLET_DECORATE);
					}
					%>

				</div>
			</c:if>
		</c:if>
	</c:otherwise>
</c:choose>

<liferay-ui:layout-common />