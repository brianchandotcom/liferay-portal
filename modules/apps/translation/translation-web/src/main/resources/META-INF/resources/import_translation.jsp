<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ImportTranslationDisplayContext importTranslationDisplayContext = (ImportTranslationDisplayContext)request.getAttribute(ImportTranslationDisplayContext.class.getName());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(importTranslationDisplayContext.getRedirect());

renderResponse.setTitle(LanguageUtil.get(resourceBundle, "import-translation"));
%>

<div class="translation">
	<aui:form action="<%= importTranslationDisplayContext.getImportTranslationURL() %>" cssClass="translation-import" enctype="multipart/form-data" name="fm">
		<span aria-hidden="true" class="loading-animation"></span>

		<react:component
			module="js/import-translation/ImportTranslation"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"errorMessage", importTranslationDisplayContext.getErrorMessage()
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