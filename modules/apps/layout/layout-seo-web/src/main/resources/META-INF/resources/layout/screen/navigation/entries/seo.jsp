<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

UnicodeProperties layoutTypeSettingsUnicodeProperties = selLayout.getTypeSettingsProperties();
boolean nondefaultAssetDisplayPage = selLayout.isTypeAssetDisplay() && !layoutsSEODisplayContext.isDefaultAssetDisplayPage() && FeatureFlagManagerUtil.isEnabled("LPS-195205");
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathProxy() + application.getContextPath() + "/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<portlet:actionURL copyCurrentRenderParameters="<%= true %>" name="/layout/edit_seo" var="editSEOURL">
	<portlet:param name="mvcRenderCommandName" value="/layout/edit_seo" />
</portlet:actionURL>

<h2 class="mb-4 text-7"><liferay-ui:message key="seo" /></h2>

<liferay-frontend:edit-form
	action="<%= editSEOURL %>"
	cssClass="pt-0"
	method="post"
	name="fm"
	wrappedFormContent="<%= false %>"
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
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
					value="seo"
				/>

				<h3 class="mb-4"><liferay-ui:message key="settings" /></h3>

				<clay:alert
					cssClass="mb-4"
					displayType="info"
					message="add-multiple-fields-to-define-how-the-meta-tags-will-be-filled"
				/>

				<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

				<c:choose>
					<c:when test="<%= selLayout.isTypeAssetDisplay() %>">
						<div class="dpt-mapping">
							<div class="dpt-mapping-placeholder">
								<aui:model-context bean="<%= null %>" model="<%= null %>" />

								<aui:input disabled="<%= true %>" id="title" label="html-title" localized="<%= false %>" name="title" placeholder="title" />

								<div class="form-text">
									<liferay-ui:message arguments='<%= new String[] {"text", "html-title"} %>' key="map-a-x-field-it-will-be-used-as-x" />
								</div>

								<aui:input disabled="<%= true %>" id="descriptionSEO" localized="<%= false %>" name="description" placeholder="description" type="textarea" />

								<div class="form-text">
									<liferay-ui:message arguments='<%= new String[] {"text", "description"} %>' key="map-a-x-field-it-will-be-used-as-x" />
								</div>

								<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />
							</div>

							<react:component
								module="js/seo/display_page_templates/SeoMapping"
								props="<%= layoutsSEODisplayContext.getSEOMappingData() %>"
								servletContext="<%= application %>"
							/>
						</div>
					</c:when>
					<c:otherwise>
						<aui:input helpMessage="html-title-help" id="title" label="html-title" name="title" placeholder="title" />
						<aui:input helpMessage="description-help" id="descriptionSEO" name="description" placeholder="description" />

						<%
						LayoutSEOEntry selLayoutSEOEntry = layoutsSEODisplayContext.getSelLayoutSEOEntry();
						%>

						<liferay-util:buffer
							var="infoCanonicalURL"
						>
							<clay:alert
								message="use-custom-canonical-url-alert-info"
							/>
						</liferay-util:buffer>

						<c:choose>
							<c:when test="<%= selLayoutSEOEntry != null %>">
								<aui:model-context bean="<%= selLayoutSEOEntry %>" model="<%= LayoutSEOEntry.class %>" />

								<aui:input checked="<%= selLayoutSEOEntry.isCanonicalURLEnabled() %>" helpMessage="use-custom-canonical-url-help" label="use-custom-canonical-url" labelCssClass="font-weight-normal" name="canonicalURLEnabled" type="checkbox" wrapperCssClass="mb-2" />

								<div id="<portlet:namespace />customCanonicalURLSettings">
									<aui:input disabled="<%= !selLayoutSEOEntry.isCanonicalURLEnabled() %>" label="canonical-url" name="canonicalURL" placeholder="<%= layoutsSEODisplayContext.getDefaultCanonicalURL() %>">
										<aui:validator name="url" />
									</aui:input>
								</div>

								<div class="<%= selLayoutSEOEntry.isCanonicalURLEnabled() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />canonicalURLAlert">
									<%= infoCanonicalURL %>
								</div>

								<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />
							</c:when>
							<c:otherwise>
								<aui:input checked="<%= false %>" helpMessage="use-custom-canonical-url-help" label="use-custom-canonical-url" name="canonicalURLEnabled" type="checkbox" wrapperCssClass="mb-2" />

								<div id="<portlet:namespace />customCanonicalURLSettings">
									<aui:input disabled="<%= true %>" label="canonical-url" localized="<%= true %>" name="canonicalURL" placeholder="<%= layoutsSEODisplayContext.getDefaultCanonicalURL() %>" type="text">
										<aui:validator name="url" />
									</aui:input>
								</div>

								<div class="hide" id="<portlet:namespace />canonicalURLAlert">
									<%= infoCanonicalURL %>
								</div>
							</c:otherwise>
						</c:choose>

						<aui:input name="keywords" placeholder="keywords" />

						<div class="form-group">
							<label><liferay-ui:message key="preview" /></label>

							<div>
								<react:component
									module="js/seo/PreviewSeo.es"
									props='<%=
										HashMapBuilder.<String, Object>put(
											"targets",
											HashMapBuilder.<String, Object>put(
												"description",
												HashMapBuilder.put(
													"defaultValue", selLayout.getDescription(locale)
												).put(
													"id", "descriptionSEO"
												).build()
											).put(
												"title",
												HashMapBuilder.<String, Object>put(
													"defaultValue", layoutsSEODisplayContext.getDefaultPageTitleMap()
												).put(
													"id", "title"
												).build()
											).put(
												"url",
												HashMapBuilder.<String, Object>put(
													"defaultValue", layoutsSEODisplayContext.getDefaultCanonicalURLMap()
												).put(
													"id", "canonicalURL"
												).build()
											).build()
										).put(
											"titleSuffix", layoutsSEODisplayContext.getPageTitleSuffix()
										).build()
									%>'
									servletContext="<%= application %>"
								/>
							</div>
						</div>

						<liferay-frontend:component
							module="js/seo/seo.es"
							servletContext="<%= application %>"
						/>
					</c:otherwise>
				</c:choose>

				<c:if test="<%= nondefaultAssetDisplayPage %>">
					<clay:alert
						displayType="info"
						message="robots-can-only-be-defined-for-display-page-templates-marked-as-default"
					/>

					<aui:input disabled="<%= true %>" label="robots" name="" placeholder="robots" type="textarea" value="noindex, nofollow" />
				</c:if>

				<div class="<%= nondefaultAssetDisplayPage ? "d-none" : StringPool.BLANK %>">
					<aui:input name="robots" placeholder="robots" />
				</div>
			</clay:sheet-section>
		</clay:sheet>

		<c:if test="<%= PortalUtil.isLayoutSitemapable(selLayout) %>">
			<clay:sheet
				cssClass="ml-0"
			>
				<clay:sheet-section>
					<h3 class="mb-4"><liferay-ui:message key="sitemap" /></h3>

					<div class="alert alert-warning layout-prototype-info-message <%= selLayout.isLayoutPrototypeLinkActive() ? StringPool.BLANK : "hide" %>">
						<liferay-ui:message arguments='<%= new String[] {"inherit-changes", "general"} %>' key="some-page-settings-are-unavailable-because-x-is-enabled" />
					</div>

					<liferay-ui:error exception="<%= SitemapChangeFrequencyException.class %>" message="please-select-a-valid-change-frequency" />
					<liferay-ui:error exception="<%= SitemapIncludeException.class %>" message="please-select-a-valid-include-value" />
					<liferay-ui:error exception="<%= SitemapPagePriorityException.class %>" message="please-enter-a-valid-page-priority" />

					<%
					boolean sitemapInclude = GetterUtil.getBoolean(layoutTypeSettingsUnicodeProperties.getProperty(LayoutTypePortletConstants.SITEMAP_INCLUDE), true);
					%>

					<c:if test="<%= nondefaultAssetDisplayPage %>">
						<div class="section-disabled">
							<clay:alert
								displayType="info"
								message="only-display-page-templates-that-are-marked-as-default-for-an-asset-type-will-be-indexed-in-order-to-avoid-duplicity"
							/>

							<aui:select cssClass="propagatable-field" disabled="<%= true %>" label="include" name="">
								<aui:option label="yes" value="1" />
								<aui:option label="no" selected="<%= true %>" value="0" />
							</aui:select>

							<aui:input cssClass="propagatable-field" disabled="<%= true %>" helpMessage="page-priority-help" label="page-priority" name="" placeholder="0.0" size="3" type="text" value='<%= layoutTypeSettingsUnicodeProperties.getProperty("sitemap-priority", PropsValues.SITES_SITEMAP_DEFAULT_PRIORITY) %>'>
								<aui:validator name="number" />
								<aui:validator errorMessage="please-enter-a-valid-page-priority" name="range">[0,1]</aui:validator>
							</aui:input>

							<aui:select cssClass="propagatable-field" disabled="<%= true %>" label="change-frequency" name="" value='<%= layoutTypeSettingsUnicodeProperties.getProperty("sitemap-changefreq", PropsValues.SITES_SITEMAP_DEFAULT_CHANGE_FREQUENCY) %>'>
								<aui:option label="always" />
								<aui:option label="hourly" />
								<aui:option label="daily" />
								<aui:option label="weekly" />
								<aui:option label="monthly" />
								<aui:option label="yearly" />
								<aui:option label="never" />
							</aui:select>
						</div>
					</c:if>

					<div class="<%= nondefaultAssetDisplayPage ? "d-none" : StringPool.BLANK %>">
						<aui:select cssClass="propagatable-field" disabled="<%= selLayout.isLayoutPrototypeLinkActive() %>" label="include" name="TypeSettingsProperties--sitemap-include--">
							<aui:option label="yes" selected="<%= sitemapInclude %>" value="1" />
							<aui:option label="no" selected="<%= !sitemapInclude %>" value="0" />
						</aui:select>

						<aui:input cssClass="propagatable-field" disabled="<%= selLayout.isLayoutPrototypeLinkActive() %>" helpMessage="page-priority-help" label="page-priority" name="TypeSettingsProperties--sitemap-priority--" placeholder="0.0" size="3" type="text" value='<%= layoutTypeSettingsUnicodeProperties.getProperty("sitemap-priority", PropsValues.SITES_SITEMAP_DEFAULT_PRIORITY) %>'>
							<aui:validator name="number" />
							<aui:validator errorMessage="please-enter-a-valid-page-priority" name="range">[0,1]</aui:validator>
						</aui:input>

						<aui:select cssClass="propagatable-field" disabled="<%= selLayout.isLayoutPrototypeLinkActive() %>" label="change-frequency" name="TypeSettingsProperties--sitemap-changefreq--" value='<%= layoutTypeSettingsUnicodeProperties.getProperty("sitemap-changefreq", PropsValues.SITES_SITEMAP_DEFAULT_CHANGE_FREQUENCY) %>'>
							<aui:option label="always" />
							<aui:option label="hourly" />
							<aui:option label="daily" />
							<aui:option label="weekly" />
							<aui:option label="monthly" />
							<aui:option label="yearly" />
							<aui:option label="never" />
						</aui:select>
					</div>
				</clay:sheet-section>
			</clay:sheet>
		</c:if>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= backURL %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>