<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditDDMTemplateDisplayContext editDDMTemplateDisplayContext = (EditDDMTemplateDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

DDMTemplate ddmTemplate = editDDMTemplateDisplayContext.getDDMTemplate();
%>

<aui:model-context bean="<%= ddmTemplate %>" model="<%= DDMTemplate.class %>" />

<div class="form-group-sm template-properties">
	<p class="control-label mb-1">
		<b><liferay-ui:message key="item-type" /></b>
	</p>

	<p class="small">
		<%= editDDMTemplateDisplayContext.getTemplateTypeLabel() %>
	</p>

	<c:if test="<%= Validator.isNotNull(editDDMTemplateDisplayContext.getTemplateSubtypeLabel()) %>">
		<p class="control-label mb-1">
			<b><liferay-ui:message key="item-subtype" /></b>
		</p>

		<p class="small">
			<%= editDDMTemplateDisplayContext.getTemplateSubtypeLabel() %>
		</p>
	</c:if>

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