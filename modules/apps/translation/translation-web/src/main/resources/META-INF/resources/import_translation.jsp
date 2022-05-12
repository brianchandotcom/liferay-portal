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
		<nav class="component-tbar subnav-tbar-light tbar">
			<clay:container-fluid>
				<ul class="tbar-nav">
					<li class="tbar-item tbar-item-expand">
						<div class="pl-2 tbar-section text-left">
							<h2 class="h4 text-truncate-inline" title="<%= HtmlUtil.escapeAttribute(importTranslationDisplayContext.getTitle()) %>">
								<span class="text-truncate"><%= HtmlUtil.escape(importTranslationDisplayContext.getTitle()) %></span>
							</h2>
						</div>
					</li>
					<li class="tbar-item">
						<div class="tbar-section text-right">
							<div class="btn-group">
								<div class="btn-group-item">
									<aui:button cssClass="btn-sm" href="<%= importTranslationDisplayContext.getRedirect() %>" type="cancel" />
								</div>

								<div class="btn-group-item">
									<aui:button cssClass="btn-sm" disabled="<%= true %>" primary="<%= false %>" type="submit" value="<%= importTranslationDisplayContext.getSaveButtonLabel() %>" />
								</div>

								<div class="btn-group-item">
									<aui:button cssClass="btn-sm" disabled="<%= true %>" primary="<%= true %>" type="submit" value="<%= importTranslationDisplayContext.getPublishButtonLabel() %>" />
								</div>
						</div>
					</li>
				</ul>
			</clay:container-fluid>
		</nav>

		<clay:container-fluid
			cssClass="container-view"
		>
			<clay:sheet
				cssClass="translation-import-body-form"
			>
				<liferay-ui:error exception="<%= XLIFFFileException.MustBeValid.class %>" message="please-enter-a-file-with-a-valid-xliff-file-extension">

					<%
					exceptionErrorMessage = LanguageUtil.get(request, "please-enter-a-file-with-a-valid-xliff-file-extension");
					%>

				</liferay-ui:error>

				<div>
					<p class="h3"><liferay-ui:message key="import-files" /></p>

					<p class="text-secondary">
						<liferay-ui:message key="please-upload-your-translation-files" />
					</p>

					<div class="mt-4">
						<p class="h5"><liferay-ui:message key="file-upload" /></p>

						<clay:button
							disabled="<%= true %>"
							displayType="secondary"
							label="select-files"
							small="<%= true %>"
						/>
					</div>
				</div>
			</clay:sheet>
		</clay:container-fluid>

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