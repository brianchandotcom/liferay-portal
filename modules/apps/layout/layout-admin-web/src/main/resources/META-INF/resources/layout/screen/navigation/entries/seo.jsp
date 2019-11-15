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

if (Validator.isNull(backURL)) {
	backURL = PortalUtil.getLayoutFullURL(layoutsAdminDisplayContext.getSelLayout(), themeDisplay);
}

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

UnicodeProperties layoutTypeSettings = selLayout.getTypeSettingsProperties();
%>

<portlet:actionURL name="/layout/edit_seo" var="editSEOURL" />

<aui:form action="<%= editSEOURL %>" method="post" name="fm">
	<aui:input name="portletResource" type="hidden" value='<%= ParamUtil.getString(request, "portletResource") %>' />
	<aui:input name="groupId" type="hidden" value="<%= layoutsAdminDisplayContext.getGroupId() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= layoutsAdminDisplayContext.isPrivateLayout() %>" />
	<aui:input name="layoutId" type="hidden" value="<%= layoutsAdminDisplayContext.getLayoutId() %>" />

	<div class="sheet sheet-lg">
		<div class="sheet-header">
			<h2 class="sheet-title"><liferay-ui:message key="seo" /></h2>
		</div>

		<div class="sheet-section">
			<liferay-ui:error-marker
				key="<%= WebKeys.ERROR_SECTION %>"
				value="seo"
			/>

			<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

			<c:if test="<%= !StringUtil.equals(selLayout.getType(), LayoutConstants.TYPE_ASSET_DISPLAY) %>">
				<liferay-asset:asset-categories-error />

				<liferay-asset:asset-tags-error />

				<liferay-asset:asset-categories-selector
					className="<%= Layout.class.getName() %>"
					classPK="<%= (selLayout != null) ? selLayout.getPrimaryKey() : 0 %>"
				/>

				<liferay-asset:asset-tags-selector
					className="<%= Layout.class.getName() %>"
					classPK="<%= (selLayout != null) ? selLayout.getPrimaryKey() : 0 %>"
				/>

				<aui:input id="title" label="html-title" name="title" placeholder="title" />

				<h4><liferay-ui:message key="meta-tags" /></h4>

				<aui:input id="descriptionSEO" name="description" placeholder="description" />

				<c:if test="<%= !StringUtil.equals(selLayout.getType(), LayoutConstants.TYPE_ASSET_DISPLAY) %>">

					<%
					LayoutSEOEntry selLayoutSEOEntry = layoutsAdminDisplayContext.getSelLayoutSEOEntry();
					%>

					<c:choose>
						<c:when test="<%= selLayoutSEOEntry != null %>">
							<aui:model-context bean="<%= selLayoutSEOEntry %>" model="<%= LayoutSEOEntry.class %>" />

							<aui:input checked="<%= selLayoutSEOEntry.isCanonicalURLEnabled() %>" helpMessage="use-custom-canonical-url-help" label="use-custom-canonical-url" name="canonicalURLEnabled" type="checkbox" wrapperCssClass="mb-1" />

							<div id="<portlet:namespace />customCanonicalURLSettings">
								<aui:input disabled="<%= !selLayoutSEOEntry.isCanonicalURLEnabled() %>" label="<%= StringPool.BLANK %>" name="canonicalURL" placeholder="<%= layoutsAdminDisplayContext.getCanonicalLayoutURL() %>">
									<aui:validator name="url" />
								</aui:input>
							</div>

							<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />
						</c:when>
						<c:otherwise>
							<aui:input checked="<%= false %>" helpMessage="use-custom-canonical-url-help" label="use-custom-canonical-url" name="canonicalURLEnabled" type="checkbox" wrapperCssClass="mb-1" />

							<div id="<portlet:namespace />customCanonicalURLSettings">
								<aui:input disabled="<%= true %>" label="<%= StringPool.BLANK %>" localized="<%= true %>" name="canonicalURL" placeholder="<%= layoutsAdminDisplayContext.getCanonicalLayoutURL() %>" type="text">
									<aui:validator name="url" />
								</aui:input>
							</div>
						</c:otherwise>
					</c:choose>
				</c:if>

				<aui:input name="keywords" placeholder="keywords" />

				<div class="form-group">
					<label><liferay-ui:message key="preview" /></label>

					<div>

						<%
						Map<String, Object> data = new HashMap<>();

						data.put(
							"targets",
							JSONUtil.putAll(
								JSONUtil.put(
									"defaultValue", layoutsAdminDisplayContext.getCanonicalLayoutURL()
								).put(
									"id", "canonicalURL"
								).put(
									"type", "canonicalURL"
								),
								JSONUtil.put(
									"id", "descriptionSEO"
								).put(
									"type", "description"
								),
								JSONUtil.put(
									"defaultValue", layoutsAdminDisplayContext.getPageTitle()
								).put(
									"id", "title"
								).put(
									"type", "title"
								)));

						data.put("titleSuffix", layoutsAdminDisplayContext.getPageTitleSuffix());
						%>

						<react:component
							data="<%= data %>"
							module="js/seo/PreviewSeo.es"
						/>
					</div>
				</div>
			</c:if>

			<aui:input name="robots" placeholder="robots" />

			<c:if test="<%= PortalUtil.isLayoutSitemapable(selLayout) %>">
				<h4><liferay-ui:message key="sitemap" /></h4>

				<div class="alert alert-warning layout-prototype-info-message <%= selLayout.isLayoutPrototypeLinkActive() ? StringPool.BLANK : "hide" %>">
					<liferay-ui:message arguments='<%= new String[] {"inherit-changes", "general"} %>' key="some-page-settings-are-unavailable-because-x-is-enabled" />
				</div>

				<liferay-ui:error exception="<%= SitemapChangeFrequencyException.class %>" message="please-select-a-valid-change-frequency" />
				<liferay-ui:error exception="<%= SitemapIncludeException.class %>" message="please-select-a-valid-include-value" />
				<liferay-ui:error exception="<%= SitemapPagePriorityException.class %>" message="please-enter-a-valid-page-priority" />

				<%
				boolean sitemapInclude = GetterUtil.getBoolean(layoutTypeSettings.getProperty(LayoutTypePortletConstants.SITEMAP_INCLUDE), true);
				%>

				<aui:select cssClass="propagatable-field" disabled="<%= selLayout.isLayoutPrototypeLinkActive() %>" label="include" name="TypeSettingsProperties--sitemap-include--">
					<aui:option label="yes" selected="<%= sitemapInclude %>" value="1" />
					<aui:option label="no" selected="<%= !sitemapInclude %>" value="0" />
				</aui:select>

				<%
				String sitemapPriority = layoutTypeSettings.getProperty("sitemap-priority", PropsValues.SITES_SITEMAP_DEFAULT_PRIORITY);
				%>

				<aui:input cssClass="propagatable-field" disabled="<%= selLayout.isLayoutPrototypeLinkActive() %>" helpMessage="(0.0 - 1.0)" label="page-priority" name="TypeSettingsProperties--sitemap-priority--" placeholder="0.0" size="3" type="text" value="<%= sitemapPriority %>">
					<aui:validator name="number" />
					<aui:validator errorMessage="please-enter-a-valid-page-priority" name="range">[0,1]</aui:validator>
				</aui:input>

				<%
				String siteMapChangeFrequency = layoutTypeSettings.getProperty("sitemap-changefreq", PropsValues.SITES_SITEMAP_DEFAULT_CHANGE_FREQUENCY);
				%>

				<aui:select cssClass="propagatable-field" disabled="<%= selLayout.isLayoutPrototypeLinkActive() %>" label="change-frequency" name="TypeSettingsProperties--sitemap-changefreq--" value="<%= siteMapChangeFrequency %>">
					<aui:option label="always" />
					<aui:option label="hourly" />
					<aui:option label="daily" />
					<aui:option label="weekly" />
					<aui:option label="monthly" />
					<aui:option label="yearly" />
					<aui:option label="never" />
				</aui:select>
			</c:if>
		</div>

		<div class="sheet-footer">
			<aui:button primary="<%= true %>" type="submit" />

			<aui:button href="<%= backURL %>" type="cancel" />
		</div>
	</div>
</aui:form>

<aui:script>
	var canonicalURLEnabledCheck = document.getElementById(
		'<portlet:namespace />canonicalURLEnabled'
	);
	var canonicalURLField = document.getElementById(
		'<portlet:namespace />canonicalURL'
	);
	var canonicalURLFieldDefaultLocale = document.getElementById(
		'<portlet:namespace />canonicalURL_<%= themeDisplay.getLanguageId() %>'
	);

	if (canonicalURLEnabledCheck && canonicalURLField) {
		canonicalURLEnabledCheck.addEventListener('click', function(event) {
			Liferay.Util.toggleDisabled(canonicalURLField, !event.target.checked);
			Liferay.Util.toggleDisabled(
				canonicalURLFieldDefaultLocale,
				!event.target.checked
			);
		});
	}
</aui:script>