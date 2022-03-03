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
ViewTranslationDisplayContext viewTranslationDisplayContext = (ViewTranslationDisplayContext)request.getAttribute(ViewTranslationDisplayContext.class.getName());
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css") %>" rel="stylesheet" />
</liferay-util:html-top>

<clay:container-fluid
	cssClass="container-view translation"
>
	<div class="sheet translation-edit-body-form">
		<clay:row>
			<clay:col
				md="6"
			>

				<%
				String sourceLanguageIdTitle = viewTranslationDisplayContext.getLanguageIdTitle(viewTranslationDisplayContext.getSourceLanguageId());
				%>

				<clay:icon
					symbol="<%= StringUtil.toLowerCase(sourceLanguageIdTitle) %>"
				/>

				<span class="ml-1"> <%= sourceLanguageIdTitle %> </span>

				<hr class="separator" />
			</clay:col>

			<clay:col
				md="6"
			>

				<%
				String targetLanguageIdTitle = viewTranslationDisplayContext.getLanguageIdTitle(viewTranslationDisplayContext.getTargetLanguageId());
				%>

				<clay:icon
					symbol="<%= StringUtil.toLowerCase(targetLanguageIdTitle) %>"
				/>

				<span class="ml-1"> <%= targetLanguageIdTitle %> </span>

				<hr class="separator" />
			</clay:col>
		</clay:row>

		<%
		for (InfoFieldSetEntry infoFieldSetEntry : viewTranslationDisplayContext.getInfoFieldSetEntries()) {
			List<InfoField> infoFields = viewTranslationDisplayContext.getInfoFields(infoFieldSetEntry);

			if (ListUtil.isEmpty(infoFields)) {
				continue;
			}

			String infoFieldSetLabel = viewTranslationDisplayContext.getInfoFieldSetLabel(infoFieldSetEntry, locale);
		%>

			<c:if test="<%= Validator.isNotNull(infoFieldSetLabel) %>">
				<clay:row>
					<clay:col
						md="6"
					>
						<div class="fieldset-title">
							<%= infoFieldSetLabel %>
						</div>
					</clay:col>

					<clay:col
						md="6"
					>
						<div class="fieldset-title">
							<%= infoFieldSetLabel %>
						</div>
					</clay:col>
				</clay:row>
			</c:if>

			<%
			for (InfoField<TextInfoFieldType> infoField : infoFields) {
				boolean html = viewTranslationDisplayContext.getBooleanValue(infoField, TextInfoFieldType.HTML);
				String label = viewTranslationDisplayContext.getInfoFieldLabel(infoField);
				boolean multiline = viewTranslationDisplayContext.getBooleanValue(infoField, TextInfoFieldType.MULTILINE);
			%>

				<clay:row>
					<clay:col
						md="6"
					>

						<%
						String sourceContent = viewTranslationDisplayContext.getStringValue(infoField, viewTranslationDisplayContext.getSourceLocale());
						String sourceContentDir = LanguageUtil.get(viewTranslationDisplayContext.getSourceLocale(), "lang.dir");
						%>

						<c:choose>
							<c:when test="<%= html %>">
								<label class="control-label">
									<%= label %>
								</label>

								<div class="translation-editor-preview" dir="<%= sourceContentDir %>">
									<%= sourceContent %>
								</div>
							</c:when>
							<c:otherwise>
								<aui:input dir="<%= sourceContentDir %>" label="<%= label %>" name="<%= label %>" readonly="true" tabIndex="-1" type='<%= multiline ? "textarea" : "text" %>' value="<%= sourceContent %>" />
							</c:otherwise>
						</c:choose>
					</clay:col>

					<clay:col
						md="6"
					>

						<%
						String targetContent = viewTranslationDisplayContext.getStringValue(infoField, viewTranslationDisplayContext.getTargetLocale());
						%>

						<c:choose>
							<c:when test="<%= html %>">
								<label class="control-label">
									<%= label %>
								</label>

								<div class="translation-editor-preview" dir="<%= LanguageUtil.get(viewTranslationDisplayContext.getTargetLocale(), "lang.dir") %>">
									<%= targetContent %>
								</div>
							</c:when>
							<c:otherwise>
								<aui:input dir='<%= LanguageUtil.get(viewTranslationDisplayContext.getTargetLocale(), "lang.dir") %>' label="<%= label %>" name="<%= label %>" readonly="true" tabIndex="-1" type='<%= multiline ? "textarea" : "text" %>' value="<%= targetContent %>" />
							</c:otherwise>
						</c:choose>
					</clay:col>
				</clay:row>

		<%
			}
		}
		%>

	</div>
</clay:container-fluid>