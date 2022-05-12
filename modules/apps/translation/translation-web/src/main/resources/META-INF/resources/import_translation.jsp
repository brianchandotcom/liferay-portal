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
ImportTranslationDisplayContext importTranslationDisplayContext = (ImportTranslationDisplayContext)request.getAttribute(ImportTranslationDisplayContext.class.getName());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(importTranslationDisplayContext.getRedirect());

renderResponse.setTitle(LanguageUtil.get(resourceBundle, "import-translation"));

String exceptionErrorMessage = null;
%>

<div class="translation">
	<aui:form action="<%= importTranslationDisplayContext.getImportTranslationURL() %>" cssClass="translation-import" name="fm">
		<liferay-ui:error exception="<%= XLIFFFileException.MustBeValid.class %>">

			<%
			exceptionErrorMessage = LanguageUtil.get(request, "please-enter-a-file-with-a-valid-xliff-file-extension");
			%>

		</liferay-ui:error>

		<span aria-hidden="true" class="loading-animation"></span>

		<react:component
			module="js/import-translation/ImportTranslation"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"errorMessage", exceptionErrorMessage
				).put(
					"portletResource", ParamUtil.getString(request, "portletResource")
				).put(
					"publishButtonLabel", LanguageUtil.get(resourceBundle, importTranslationDisplayContext.getPublishButtonLabel())
				).put(
					"redirect", importTranslationDisplayContext.getRedirect()
				).put(
					"saveButtonLabel", LanguageUtil.get(resourceBundle, importTranslationDisplayContext.getSaveButtonLabel())
				).put(
					"title", HtmlUtil.escape(importTranslationDisplayContext.getTitle())
				).put(
					"workflowActionPublish", WorkflowConstants.ACTION_PUBLISH
				).put(
					"workflowActionSaveDraft", WorkflowConstants.ACTION_SAVE_DRAFT
				).put(
					"workflowPending", importTranslationDisplayContext.isPending()
				).build()
			%>'
		/>
	</aui:form>
</div>