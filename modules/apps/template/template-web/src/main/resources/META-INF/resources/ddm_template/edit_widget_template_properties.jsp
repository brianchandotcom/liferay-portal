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
EditDDMTemplateDisplayContext editDDMTemplateDisplayContext = (EditDDMTemplateDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

DDMTemplate ddmTemplate = editDDMTemplateDisplayContext.getDDMTemplate();
%>

<aui:model-context bean="<%= ddmTemplate %>" model="<%= DDMTemplate.class %>" />

<div class="form-group-sm template-properties">
	<c:if test="<%= (ddmTemplate == null) && !editDDMTemplateDisplayContext.autogenerateTemplateKey() %>">
		<aui:input name="templateKey" />
	</c:if>

	<c:if test="<%= ddmTemplate != null %>">
		<aui:input helpMessage="template-key-help" name="templateKey" type="resource" value="<%= ddmTemplate.getTemplateKey() %>" />

		<portlet:resourceURL id="/template/get_ddm_template" var="getTemplateURL">
			<portlet:param name="ddmTemplateId" value="<%= String.valueOf(ddmTemplate.getTemplateId()) %>" />
		</portlet:resourceURL>

		<aui:input name="url" type="resource" value="<%= getTemplateURL %>" />

		<c:if test="<%= Validator.isNotNull(editDDMTemplateDisplayContext.getRefererWebDAVToken()) %>">
			<aui:input name="webDavURL" type="resource" value="<%= ddmTemplate.getWebDavURL(themeDisplay, editDDMTemplateDisplayContext.getRefererWebDAVToken()) %>" />
		</c:if>
	</c:if>

	<aui:input name="description" />

	<%
	String smallImageSource = editDDMTemplateDisplayContext.getSmallImageSource();
	%>

	<aui:select label="" name="smallImageSource" value="<%= smallImageSource %>" wrapperCssClass="mb-3">
		<aui:option label="no-image" value="none" />
		<aui:option label="from-url" value="url" />
		<aui:option label="from-your-computer" value="file" />
	</aui:select>

	<div class="<%= Objects.equals(smallImageSource, "url") ? "" : "hide" %>" id="<portlet:namespace />smallImageURLContainer">
		<aui:input label="" name="smallImageURL" title="small-image-url" wrapperCssClass="mb-3" />

		<c:if test="<%= editDDMTemplateDisplayContext.isSmallImage() && (ddmTemplate != null) && Validator.isNotNull(ddmTemplate.getSmallImageURL()) %>">
			<p class="control-label font-weight-semi-bold">
				<liferay-ui:message key="preview" />
			</p>

			<div class="aspect-ratio aspect-ratio-16-to-9">
				<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="preview" />" class="aspect-ratio-item-fluid" src="<%= HtmlUtil.escapeAttribute(ddmTemplate.getTemplateImageURL(themeDisplay)) %>" />
			</div>
		</c:if>
	</div>

	<div class="<%= Objects.equals(smallImageSource, "file") ? "" : "hide" %>" id="<portlet:namespace />smallImageFileContainer">
		<aui:input label="" name="smallImageFile" type="file" wrapperCssClass="mb-3" />

		<c:if test="<%= editDDMTemplateDisplayContext.isSmallImage() && (ddmTemplate != null) && (ddmTemplate.getSmallImageId() > 0) %>">
			<p>
				<strong><liferay-ui:message key="preview" /></strong>
			</p>

			<div class="aspect-ratio aspect-ratio-16-to-9">
				<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="preview" />" class="aspect-ratio-item-fluid" src="<%= HtmlUtil.escapeAttribute(ddmTemplate.getTemplateImageURL(themeDisplay)) %>" />
			</div>
		</c:if>
	</div>

	<aui:script>
		Liferay.Util.toggleSelectBox(
			'<portlet:namespace />smallImageSource',
			'url',
			'<portlet:namespace />smallImageURLContainer'
		);
		Liferay.Util.toggleSelectBox(
			'<portlet:namespace />smallImageSource',
			'file',
			'<portlet:namespace />smallImageFileContainer'
		);
	</aui:script>
</div>