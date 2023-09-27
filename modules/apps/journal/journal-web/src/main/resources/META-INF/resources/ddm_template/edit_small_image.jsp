<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalEditDDMTemplateDisplayContext journalEditDDMTemplateDisplayContext = new JournalEditDDMTemplateDisplayContext(request, renderResponse);

DDMTemplate ddmTemplate = journalEditDDMTemplateDisplayContext.getDDMTemplate();

String smallImageSource = journalEditDDMTemplateDisplayContext.getSmallImageSource();
%>

<aui:model-context bean="<%= ddmTemplate %>" model="<%= DDMTemplate.class %>" />

<liferay-ui:error exception="<%= TemplateSmallImageContentException.class %>" message="the-small-image-file-could-not-be-saved" />

<liferay-ui:error exception="<%= TemplateSmallImageNameException.class %>">
	<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= HtmlUtil.escape(StringUtil.merge(journalEditDDMTemplateDisplayContext.imageExtensions(), StringPool.COMMA)) %>.
</liferay-ui:error>

<liferay-ui:error exception="<%= TemplateSmallImageSizeException.class %>">
	<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(journalEditDDMTemplateDisplayContext.smallImageMaxSize(), locale) %>" key="please-enter-a-small-image-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
</liferay-ui:error>

<aui:select label="" name="smallImageSource" value="<%= smallImageSource %>" wrapperCssClass="mb-3">
	<aui:option label="no-image" value="none" />
	<aui:option label="from-url" value="url" />
	<aui:option label="from-your-computer" value="file" />
</aui:select>

<div class="<%= Objects.equals(smallImageSource, "url") ? "" : "hide" %>" id="<portlet:namespace />smallImageURLContainer">
	<aui:input label="" name="smallImageURL" title="small-image-url" wrapperCssClass="mb-3" />

	<c:if test="<%= journalEditDDMTemplateDisplayContext.isSmallImage() && (ddmTemplate != null) && Validator.isNotNull(ddmTemplate.getSmallImageURL()) %>">
		<p class="control-label font-weight-semi-bold">
			<liferay-ui:message key="preview" />
		</p>

		<div class="aspect-ratio aspect-ratio-16-to-9">
			<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="preview" />" class="aspect-ratio-item-fluid" src="<%= HtmlUtil.escapeAttribute(ddmTemplate.getTemplateImageURL(themeDisplay)) %>" />
		</div>
	</c:if>
</div>

<div class="<%= Objects.equals(smallImageSource, "file") ? "" : "hide" %>" id="<portlet:namespace />smallImageFileContainer">
	<div>

		<%
		ThemeDisplay finalThemeDisplay = themeDisplay;
		%>

		<react:component
			module="js/ImageInput.es"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"name", "smallImageFile"
				).put(
					"previewURL",
					() -> {
						if (journalEditDDMTemplateDisplayContext.isSmallImage() && (ddmTemplate != null) && (ddmTemplate.getSmallImageId() > 0)) {
							return ddmTemplate.getTemplateImageURL(finalThemeDisplay);
						}

						return StringPool.BLANK;
					}
				).build()
			%>'
		/>
	</div>
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