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
%>

<div class="translation">
	<aui:form action="<%= importTranslationDisplayContext.getImportTranslationURL() %>" cssClass="translation-import" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= importTranslationDisplayContext.getRedirect() %>" />
		<aui:input name="portletResource" type="hidden" value='<%= ParamUtil.getString(request, "portletResource") %>' />
		<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

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
						<div class="metadata-type-button-row tbar-section text-right">
							<aui:button cssClass="btn-sm mr-3" href="<%= importTranslationDisplayContext.getRedirect() %>" type="cancel" />

							<aui:button cssClass="btn-sm mr-3" id="saveDraftBtn" primary="<%= false %>" type="submit" value="<%= importTranslationDisplayContext.getSaveButtonLabel() %>" />

							<aui:button cssClass="btn-sm mr-3" disabled="<%= importTranslationDisplayContext.isPending() %>" id="submitBtnId" primary="<%= true %>" type="submit" value="<%= importTranslationDisplayContext.getPublishButtonLabel() %>" />
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
				<liferay-ui:error exception="<%= XLIFFFileException.MustBeValid.class %>" message="please-enter-a-file-with-a-valid-xliff-file-extension" />

				<div>
					<c:choose>
						<c:when test="<%= importTranslationDisplayContext.isBulkTranslationEnabled() %>">
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

							<react:component
								module="js/ImportTranslationMultipleFiles"
								props='<%=
									HashMapBuilder.<String, Object>put(
										"saveDraftBtnId", liferayPortletResponse.getNamespace() + "saveDraftBtn"
									).put(
										"submitBtnId", liferayPortletResponse.getNamespace() + "submitBtnId"
									).put(
										"workflowPending", importTranslationDisplayContext.isPending()
									).build()
								%>'
							/>
						</c:when>
						<c:otherwise>
							<react:component
								module="js/ImportTranslation.es"
								props='<%=
									HashMapBuilder.<String, Object>put(
										"saveDraftBtnId", liferayPortletResponse.getNamespace() + "saveDraftBtn"
									).put(
										"submitBtnId", liferayPortletResponse.getNamespace() + "submitBtnId"
									).put(
										"workflowPending", importTranslationDisplayContext.isPending()
									).build()
								%>'
							/>
						</c:otherwise>
					</c:choose>
				</div>
			</clay:sheet>
		</clay:container-fluid>
	</aui:form>
</div>

<script>
	var saveDraftBtn = document.getElementById('<portlet:namespace />saveDraftBtn');

	saveDraftBtn.addEventListener('click', () => {
		var workflowActionInput = document.getElementById(
			'<portlet:namespace />workflowAction'
		);

		workflowActionInput.value = '<%= WorkflowConstants.ACTION_SAVE_DRAFT %>';
	});
</script>