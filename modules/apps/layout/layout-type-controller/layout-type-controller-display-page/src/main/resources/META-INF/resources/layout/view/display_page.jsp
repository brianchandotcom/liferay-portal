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
			<c:when test="<%= displayPageLayoutTypeControllerDisplayContext.getStructureJSONArray() != null %>">
				<div class="layout-content" id="main-content" role="main">

					<%
					try {
						request.setAttribute(WebKeys.PORTLET_DECORATE, Boolean.FALSE);

						JSONArray structureJSONArray = displayPageLayoutTypeControllerDisplayContext.getStructureJSONArray();

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
									<div class="<%= Objects.equals(containerType, "fluid") ? "container-fluid" : "container" %> px-<%= paddingHorizontal %> py-<%= paddingVertical %>">
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

														defaultFragmentRendererContext.setFieldValues(displayPageLayoutTypeControllerDisplayContext.getInfoDisplayFieldsValues());
														defaultFragmentRendererContext.setLocale(locale);
														defaultFragmentRendererContext.setMode(FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE);
														defaultFragmentRendererContext.setSegmentsExperienceIds(displayPageLayoutTypeControllerDisplayContext.getSegmentExperienceIds());
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

											defaultFragmentRendererContext.setFieldValues(displayPageLayoutTypeControllerDisplayContext.getInfoDisplayFieldsValues());
											defaultFragmentRendererContext.setLocale(locale);
											defaultFragmentRendererContext.setMode(FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE);
											defaultFragmentRendererContext.setSegmentsExperienceIds(displayPageLayoutTypeControllerDisplayContext.getSegmentExperienceIds());
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
			</c:when>
			<c:when test="<%= infoDisplayObjectProvider != null %>">
				<div class="sheet">
					<div class="sheet-header">
						<h2 class="sheet-title">
							<%= infoDisplayObjectProvider.getTitle(locale) %>
						</h2>

						<div class="sheet-text">
							<%= infoDisplayObjectProvider.getDescription(locale) %>
						</div>
					</div>
				</div>
			</c:when>
		</c:choose>
	</c:otherwise>
</c:choose>

<liferay-ui:layout-common />