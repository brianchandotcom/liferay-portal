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

String backURL = ParamUtil.getString(request, "backURL", redirect);

LayoutsSEODisplayContext layoutsSEODisplayContext = (LayoutsSEODisplayContext)request.getAttribute(LayoutSEOWebKeys.LAYOUT_PAGE_LAYOUT_SEO_DISPLAY_CONTEXT);

if (Validator.isNull(backURL)) {
	backURL = PortalUtil.getLayoutFullURL(layoutsSEODisplayContext.getSelLayout(), themeDisplay);
}

Layout selLayout = layoutsSEODisplayContext.getSelLayout();
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathProxy() + application.getContextPath() + "/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<portlet:actionURL copyCurrentRenderParameters="<%= true %>" name="/layout/edit_open_graph" var="editOpenGraphURL" />

<h2 class="mb-4 text-7"><liferay-ui:message key="open-graph" /></h2>

<liferay-frontend:edit-form
	action="<%= editOpenGraphURL %>"
	cssClass="pt-0"
	method="post"
	name="fm"
	wrappedFormContent="<%= false %>"
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="portletResource" type="hidden" value='<%= ParamUtil.getString(request, "portletResource") %>' />
	<aui:input name="groupId" type="hidden" value="<%= layoutsSEODisplayContext.getGroupId() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= layoutsSEODisplayContext.isPrivateLayout() %>" />
	<aui:input name="layoutId" type="hidden" value="<%= layoutsSEODisplayContext.getLayoutId() %>" />

	<liferay-frontend:edit-form-body>
		<clay:sheet
			cssClass="ml-0"
		>
			<clay:sheet-section>
				<liferay-ui:error-marker
					key="<%= WebKeys.ERROR_SECTION %>"
					value="open-graph"
				/>

				<h3 class="mb-4"><liferay-ui:message key="settings" /></h3>

				<p class="text-secondary">
					<liferay-ui:message key="open-graph-description" />
				</p>

				<clay:alert
					cssClass="mb-4"
					displayType="info"
					message='<%= LanguageUtil.get(request, "add-multiple-fields-to-define-how-the-meta-tags-will-be-filled") %>'
				/>

				<c:choose>
					<c:when test="<%= selLayout.isTypeAssetDisplay() %>">
						<div class="dpt-mapping">
							<div class="dpt-mapping-placeholder">
								<aui:input disabled="<%= true %>" label="title" localized="<%= false %>" name="openGraphTitle" />

								<div class="form-text">
									<liferay-ui:message arguments='<%= new String[] {"text", "title"} %>' key="map-a-x-field-it-will-be-used-as-x" />
								</div>

								<aui:input disabled="<%= true %>" label="description" localized="<%= false %>" name="openGraphDescription" type="textarea" />

								<div class="form-text">
									<liferay-ui:message arguments='<%= new String[] {"text", "description"} %>' key="map-a-x-field-it-will-be-used-as-x" />
								</div>

								<aui:input disabled="<%= true %>" label="image" localized="<%= false %>" name="openGraphImageTitle" />

								<div class="form-text">
									<liferay-ui:message arguments='<%= new String[] {"image", "image"} %>' key="map-a-x-field-it-will-be-used-as-x" />
								</div>

								<aui:input disabled="<%= true %>" label="open-graph-image-alt-description" name="openGraphImageAlt" type="textarea" />

								<div class="form-text">
									<liferay-ui:message arguments='<%= new String[] {"text", "open-graph-image-alt-description"} %>' key="map-a-x-field-it-will-be-used-as-x" />
								</div>
							</div>

							<react:component
								module="js/seo/display_page_templates/OpenGraphMapping"
								props="<%= layoutsSEODisplayContext.getOpenGraphMappingData() %>"
								servletContext="<%= application %>"
							/>
						</div>
					</c:when>
					<c:otherwise>
						<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

						<%
						LayoutSEOEntry selLayoutSEOEntry = layoutsSEODisplayContext.getSelLayoutSEOEntry();
						%>

						<div class="form-group mb-2">
							<label class="control-label"><liferay-ui:message key="image" /> <liferay-ui:icon-help message="open-graph-image-help" /></label>

							<div class="align-items-center d-flex">
								<aui:input id="openGraphImageInput" label="<%= StringPool.BLANK %>" name="openGraphImageTitle" placeholder="no-image-is-loaded" readonly="<%= true %>" title="image" type="text" value="<%= layoutsSEODisplayContext.getOpenGraphImageTitle() %>" wrapperCssClass="flex-grow-1 mb-0 mr-2" />

								<clay:button
									aria-label='<%= LanguageUtil.format(locale, "select-x", "image") %>'
									cssClass="mr-2"
									displayType="secondary"
									icon="plus"
									id='<%= liferayPortletResponse.getNamespace() + "openGraphImageButton" %>'
								/>

								<clay:button
									aria-label='<%= LanguageUtil.format(locale, "clear-x", "image") %>'
									disabled="<%= (selLayoutSEOEntry != null) ? Validator.isNull(layoutsSEODisplayContext.getOpenGraphImageTitle()) : true %>"
									displayType="secondary"
									icon="times-circle"
									id='<%= liferayPortletResponse.getNamespace() + "openGraphClearImageButton" %>'
								/>
							</div>
						</div>

						<div id="<portlet:namespace />openGraphSettings">
							<aui:model-context bean="<%= selLayoutSEOEntry %>" model="<%= LayoutSEOEntry.class %>" />

							<aui:input disabled="<%= (selLayoutSEOEntry != null) ? Validator.isNull(layoutsSEODisplayContext.getOpenGraphImageTitle()) : true %>" helpMessage="open-graph-image-alt-description-help" label="open-graph-image-alt-description" name="openGraphImageAlt" placeholder="open-graph-alt-description" />

							<aui:input checked="<%= (selLayoutSEOEntry != null) ? selLayoutSEOEntry.isOpenGraphTitleEnabled() : false %>" helpMessage="use-custom-open-graph-title-help" label="use-custom-open-graph-title" name="openGraphTitleEnabled" type="checkbox" wrapperCssClass="mb-2" />

							<div id="<portlet:namespace />openGraphTitleWrapper">
								<aui:input disabled="<%= (selLayoutSEOEntry != null) ? !selLayoutSEOEntry.isOpenGraphTitleEnabled() : true %>" label="custom-title" name="openGraphTitle" placeholder="title" />
							</div>

							<aui:input checked="<%= (selLayoutSEOEntry != null) ? selLayoutSEOEntry.isOpenGraphDescriptionEnabled() : false %>" helpMessage="use-custom-open-graph-description-help" label="use-custom-open-graph-description" name="openGraphDescriptionEnabled" type="checkbox" wrapperCssClass="mb-2" />

							<div id="<portlet:namespace />openGraphDescriptionWrapper">
								<aui:input disabled="<%= (selLayoutSEOEntry != null) ? !selLayoutSEOEntry.isOpenGraphDescriptionEnabled() : true %>" label="custom-description" name="openGraphDescription" placeholder="description" />
							</div>

							<aui:input id="openGraphImageFileEntryId" name="openGraphImageFileEntryId" type="hidden" />
						</div>

						<div class="form-group">
							<label><liferay-ui:message key="preview" /> <liferay-ui:icon-help message="preview-help" /></label>

							<div>
								<react:component
									module="js/seo/PreviewSeo.es"
									props='<%=
										HashMapBuilder.<String, Object>put(
											"displayType", "og"
										).put(
											"targets",
											HashMapBuilder.<String, Object>put(
												"description",
												HashMapBuilder.<String, Object>put(
													"defaultValue", selLayout.getDescriptionMap()
												).put(
													"id", "openGraphDescription"
												).build()
											).put(
												"imgUrl",
												HashMapBuilder.<String, Object>put(
													"defaultValue", layoutsSEODisplayContext.getDefaultOpenGraphImageURL()
												).put(
													"value", layoutsSEODisplayContext.getOpenGraphImageURL()
												).build()
											).put(
												"title",
												HashMapBuilder.<String, Object>put(
													"defaultValue", layoutsSEODisplayContext.getDefaultPageTitleWithSuffixMap()
												).put(
													"id", "openGraphTitle"
												).build()
											).put(
												"url", Collections.singletonMap("defaultValue", layoutsSEODisplayContext.getDefaultCanonicalURLMap())
											).build()
										).build()
									%>'
									servletContext="<%= application %>"
								/>
							</div>
						</div>

						<portlet:actionURL name="/layout/upload_open_graph_image" var="uploadOpenGraphImageURL" />

						<liferay-frontend:component
							context='<%=
								HashMapBuilder.<String, Object>put(
									"uploadOpenGraphImageURL", layoutsSEODisplayContext.getItemSelectorURL()
								).build()
							%>'
							module="js/seo/openGraph.es"
							servletContext="<%= application %>"
						/>
					</c:otherwise>
				</c:choose>
			</clay:sheet-section>
		</clay:sheet>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= backURL %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>