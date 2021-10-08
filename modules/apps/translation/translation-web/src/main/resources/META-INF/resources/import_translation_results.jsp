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
ImportTranslationResultsDisplayContext importTranslationResultsDisplayContext = (ImportTranslationResultsDisplayContext)request.getAttribute(ImportTranslationResultsDisplayContext.class.getName());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(importTranslationResultsDisplayContext.getRedirect());

renderResponse.setTitle(LanguageUtil.get(resourceBundle, "import-translation"));
%>

<div class="translation">
	<div class="translation-import">
		<nav class="component-tbar subnav-tbar-light tbar">
			<clay:container-fluid>
				<ul class="tbar-nav">
					<li class="tbar-item tbar-item-expand">
						<div class="pl-2 tbar-section text-left">
							<h2 class="h4 mb-0 text-truncate-inline" title="<%= HtmlUtil.escapeAttribute(importTranslationResultsDisplayContext.getTitle()) %>">
								<span class="text-truncate"><%= HtmlUtil.escape(importTranslationResultsDisplayContext.getTitle()) %></span>
							</h2>
						</div>
					</li>
					<li class="tbar-item">
						<div class="tbar-section text-right">
							<clay:link
								displayType="primary"
								href="<%= importTranslationResultsDisplayContext.getRedirect() %>"
								label="done"
								small="<%= true %>"
								type="button"
							/>
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
				<h3 class="mb-4"><%= importTranslationResultsDisplayContext.getFileName() %></h3>

				<c:if test="<%= importTranslationResultsDisplayContext.getSuccessMessagesCount() > 0 %>">
					<h4 class="text-success">
						<span class="mr-2">
							<clay:icon
								symbol="check-circle-full"
							/>
						</span>

						<c:choose>
							<c:when test="<%= (importTranslationResultsDisplayContext.getSuccessMessagesCount() > 1) && (importTranslationResultsDisplayContext.getFailureMessagesCount() == 0) %>">
								<liferay-ui:message key="all-files-published" />
							</c:when>
							<c:otherwise>
								<liferay-ui:message arguments="<%= importTranslationResultsDisplayContext.getSuccessMessagesCount() %>" key='<%= (importTranslationResultsDisplayContext.getSuccessMessagesCount() == 1) ? "x-file-published" : "x-files-published" %>' />
							</c:otherwise>
						</c:choose>
					</h4>

					<ul class="list-group list-group-no-bordered list-group-sm">

						<%
						for (String successMessage : importTranslationResultsDisplayContext.getSuccessMessages()) {
						%>

							<li class="align-items-center list-group-item list-group-item-flex">
								<div class="autofit-col autofit-col-expand">
									<section class="autofit-section">
										<div class="list-group-title"><%= successMessage %></div>
									</section>
								</div>

								<div class="autofit-col text-right text-success">
									<div class="list-group-icon">
										<clay:icon
											symbol="check-circle-full"
										/>
									</div>
								</div>
							</li>

						<%
						}
						%>

					</ul>
				</c:if>

				<c:if test="<%= importTranslationResultsDisplayContext.getFailureMessagesCount() > 0 %>">
					<h4 class="text-danger">
						<span class="mr-2">
							<clay:icon
								symbol="exclamation-full"
							/>
						</span>

						<liferay-ui:message arguments="<%= importTranslationResultsDisplayContext.getFailureMessagesCount() %>" key='<%= (importTranslationResultsDisplayContext.getFailureMessagesCount() == 1) ? "x-error" : "x-errors" %>' />

						<small><liferay-ui:message key="some-files-could-not-be-published-check-them-and-upload-another-file" /></small>
					</h4>

					<ul class="list-group list-group-no-bordered">

						<%
						Map<String, String> failureMessages = importTranslationResultsDisplayContext.getFailureMessages();

						for (Map.Entry<String, String> entry : failureMessages.entrySet()) {
						%>

							<li class="align-items-center list-group-item list-group-item-flex">
								<div class="autofit-col autofit-col-expand">
									<div class="list-group-title"><%= entry.getKey() %></div>
									<div class="text-danger"><%= entry.getValue() %></div>
								</div>

								<div class="autofit-col text-danger text-right">
									<div class="list-group-icon">
										<clay:icon
											symbol="exclamation-full"
										/>
									</div>
								</div>
							</li>

						<%
						}
						%>

					</ul>

					<clay:link
						displayType="secondary"
						href="<%= importTranslationResultsDisplayContext.getImportTranslationURL() %>"
						label="upload-another-file"
						small="<%= true %>"
						type="button"
					/>
				</c:if>
			</clay:sheet>
		</clay:container-fluid>
	</div>
</div>