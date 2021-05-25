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
OpenGraphSettingsDisplayContext openGraphSettingsDisplayContext = (OpenGraphSettingsDisplayContext)request.getAttribute(OpenGraphSettingsDisplayContext.class.getName());
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<aui:field-wrapper cssClass="form-group">
	<aui:input id="openGraphEnabled" label="enable-open-graph" name="openGraphEnabled" type="checkbox" value="<%= openGraphSettingsDisplayContext.isOpenGraphEnabled() %>" />

	<p class="small text-secondary"><liferay-ui:message key="enable-open-graph-description" /></p>
</aui:field-wrapper>

<div class="open-graph-settings <%= openGraphSettingsDisplayContext.isOpenGraphEnabled() ? "" : "disabled" %>" id="<portlet:namespace />openGraphSettings">
	<h4 class="sheet-subtitle">
		<liferay-ui:message key="open-graph-image" />
	</h4>

	<p class="small text-secondary">
		<liferay-ui:message key="open-graph-image-description" />
	</p>

	<div class="form-group">
		<label class="control-label"><liferay-ui:message key="image" /></label>

		<div class="input-group mb-3">
			<div class="input-group-item">
				<input class="field form-control lfr-input-text" disabled="<%= !openGraphSettingsDisplayContext.isOpenGraphEnabled() %>" id="<portlet:namespace />openGraphImageTitle" name="<portlet:namespace />parentSiteTitle" placeholder="image" readonly="<%= true %>" type="text" value="<%= openGraphSettingsDisplayContext.getOpenGraphImageTitle() %>" />
			</div>

			<div class="input-group-item input-group-item-shrink">
				<button class="btn btn-secondary mr-1" id="<portlet:namespace />openGraphImageButton" type="button">
					<liferay-ui:message key="select" />
				</button>

				<button class="btn btn-secondary" id="<portlet:namespace />openGraphClearImageButton" type="button">
					<liferay-ui:message key="clear" />
				</button>
			</div>
		</div>

		<aui:model-context bean="<%= openGraphSettingsDisplayContext.getLayoutSEOSite() %>" model="<%= LayoutSEOSite.class %>" />

		<aui:input disabled="<%= !openGraphSettingsDisplayContext.isOpenGraphEnabled() || Validator.isNull(openGraphSettingsDisplayContext.getOpenGraphImageURL()) %>" helpMessage="open-graph-image-alt-description-help" label="open-graph-image-alt-description" name="openGraphImageAlt" placeholder="open-graph-alt-description" />
	</div>

	<aui:input id="openGraphImageFileEntryId" name="openGraphImageFileEntryId" type="hidden" value="<%= openGraphSettingsDisplayContext.getOpenGraphImageFileEntryId() %>" />

	<div class="form-group">
		<label><liferay-ui:message key="preview" /> <liferay-ui:icon-help message="preview-help" /></label>

		<div class="preview-seo preview-seo-og" dir="ltr">
			<div class="aspect-ratio aspect-ratio-191-to-100 bg-light mb-0 preview-seo-image">
				<div class="preview-seo-placeholder" id="<portlet:namespace />openGraphPreviewPlaceholder">
					<liferay-ui:icon
						icon="picture"
						iconCssClass="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid preview-seo-placeholder-icon"
						markupView="lexicon"
					/>

					<div class="preview-seo-placeholder-text"><liferay-ui:message key="open-graph-image-placeholder" /></div>
				</div>

				<img alt="" class="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-flush <%= Validator.isNull(openGraphSettingsDisplayContext.getOpenGraphImageURL()) ? "hide" : "" %>" id="<portlet:namespace />openGraphPreviewImage" src="<%= openGraphSettingsDisplayContext.getOpenGraphImageURL() %>" />
			</div>
		</div>
	</div>
</div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"uploadOpenGraphImageURL", openGraphSettingsDisplayContext.getItemSelectorURL()
		).build()
	%>'
	module="js/seo/openGraphSettings.es"
	servletContext="<%= application %>"
/>