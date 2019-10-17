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
JournalEditDDMTemplateDisplayContext journalEditDDMTemplateDisplayContext = new JournalEditDDMTemplateDisplayContext(request);

DDMTemplate ddmTemplate = journalEditDDMTemplateDisplayContext.getDDMTemplate();

String smallImageSource = journalEditDDMTemplateDisplayContext.getSmallImageSource();
%>

<aui:model-context bean="<%= ddmTemplate %>" model="<%= DDMTemplate.class %>" />

<liferay-ui:error exception="<%= TemplateSmallImageContentException.class %>" message="the-small-image-file-could-not-be-saved" />

<liferay-ui:error exception="<%= TemplateSmallImageNameException.class %>">
	<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= HtmlUtil.escape(StringUtil.merge(journalEditDDMTemplateDisplayContext.imageExtensions(), StringPool.COMMA)) %>.
</liferay-ui:error>

<liferay-ui:error exception="<%= TemplateSmallImageSizeException.class %>">
	<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(journalEditDDMTemplateDisplayContext.smallImageMaxSize(), locale) %>" key="please-enter-a-small-image-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
</liferay-ui:error>

<aui:select label="" name="smallImageSource" value="<%= smallImageSource %>" wrapperCssClass="mb-3">
	<aui:option label="no-image" value="none" />
	<aui:option label="from-url" value="url" />
	<aui:option label="from-your-computer" value="file" />
</aui:select>

<div class="<%= Objects.equals(smallImageSource, "url") ? "" : "hide" %>" id="<portlet:namespace/>smallImageURLContainer">
	<aui:input label="" name="smallImageURL" title="small-image-url" wrapperCssClass="mb-3" />

	<c:if test="<%= journalEditDDMTemplateDisplayContext.isSmallImage() && (ddmTemplate != null) && Validator.isNotNull(ddmTemplate.getSmallImageURL()) %>">
		<p>
			<strong><liferay-ui:message key="preview" /></strong>
		</p>

		<div class="aspect-ratio aspect-ratio-16-to-9">
			<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="preview" />" class="aspect-ratio-item-fluid" src="<%= HtmlUtil.escapeAttribute(ddmTemplate.getTemplateImageURL(themeDisplay)) %>" />
		</div>
	</c:if>
</div>

<div class="<%= Objects.equals(smallImageSource, "file") ? "" : "hide" %>" id="<portlet:namespace/>smallImageFileContainer">
	<aui:input label="" name="smallImageFile" type="file" wrapperCssClass="mb-3" />

	<c:if test="<%= journalEditDDMTemplateDisplayContext.isSmallImage() && (ddmTemplate != null) && (ddmTemplate.getSmallImageId() > 0) %>">
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