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
portletDisplay.setURLBack(importTranslationResultsDisplayContext.getRedirect(request));

renderResponse.setTitle(LanguageUtil.get(resourceBundle, "import-translation"));
%>

<div class="translation">
	<div class="translation-import">
		<nav class="component-tbar subnav-tbar-light tbar">
			<clay:container-fluid>
				<ul class="tbar-nav">
					<li class="tbar-item tbar-item-expand">
						<div class="pl-2 tbar-section text-left">
							<div class="h4 mb-0 text-truncate-inline" title="<%= HtmlUtil.escapeAttribute(importTranslationResultsDisplayContext.getTitle()) %>">
								<span class="text-truncate"><%= HtmlUtil.escape(importTranslationResultsDisplayContext.getTitle()) %></span>
							</div>
						</div>
					</li>
					<li class="tbar-item">
						<div class="tbar-section text-right">
							<clay:link
								displayType="primary"
								href="<%= importTranslationResultsDisplayContext.getRedirect(request) %>"
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
			size="lg"
		>
			<div class="translation-import-body-form">

				<%
				boolean importTranslationResultsErrors = false;

				if (importTranslationResultsDisplayContext.getFailureMessagesCount() > 0) {
					importTranslationResultsErrors = true;
				}
				%>

				<c:if test="<%= importTranslationResultsDisplayContext.getSuccessMessagesCount() > 0 %>">
					<div>
						<div class="panel panel-secondary" role="tablist">
							<button aria-expanded="<%= !importTranslationResultsErrors %>" class="<%= importTranslationResultsErrors ? "collapsed" : StringPool.BLANK %> btn btn-unstyled collapse-icon collapse-icon-middle panel-header panel-header-link" role="tab" type="button">
								<liferay-util:whitespace-remover>
									<div class="h4 mb-0 text-success">
										<span class="mr-2">
											<clay:icon
												symbol="check-circle-full"
											/>
										</span>
										<%= importTranslationResultsDisplayContext.getSuccessMessageLabel(locale) %>
									</div>
								</liferay-util:whitespace-remover>

								<span class="collapse-icon-closed">
									<clay:icon
										symbol="angle-right"
									/>
								</span>
								<span class="collapse-icon-open">
									<clay:icon
										symbol="angle-down"
									/>
								</span>
							</button>

							<div class="<%= importTranslationResultsErrors ? "collapse" : StringPool.BLANK %> panel-collapse" role="tabpanel">
								<div class="panel-body">
									<ul class="list-group list-group-no-bordered mb-0">

										<%
										for (String successMessage : importTranslationResultsDisplayContext.getSuccessMessages()) {
										%>

											<li class="align-items-center list-group-item">
												<div class="list-group-title"><%= successMessage %></div>
											</li>

										<%
										}
										%>

									</ul>
								</div>
							</div>
						</div>

						<react:component
							module="js/ImportTranslationResultsPanelSuccess"
							props='<%=
								HashMapBuilder.<String, Object>put(
									"defaultExpanded", !importTranslationResultsErrors
								).put(
									"files", importTranslationResultsDisplayContext.getSuccessMessages()
								).put(
									"title", importTranslationResultsDisplayContext.getSuccessMessageLabel(locale)
								).build()
							%>'
						/>
					</div>
				</c:if>

				<c:if test="<%= importTranslationResultsDisplayContext.getFailureMessagesCount() > 0 %>">
					<clay:sheet
						size="full"
					>
						<clay:content-row
							noGutters="true"
						>
							<clay:content-col
								cssClass="align-self-center"
								expand="<%= true %>"
							>
								<liferay-util:whitespace-remover>
									<div class="h4 mb-0 text-danger">
										<span class="mr-2">
											<clay:icon
												symbol="exclamation-full"
											/>
										</span>

										<liferay-ui:message arguments="<%= importTranslationResultsDisplayContext.getFailureMessagesCount() %>" key='<%= (importTranslationResultsDisplayContext.getFailureMessagesCount() == 1) ? "x-file-could-not-be-published" : "x-files-could-not-be-published" %>' />
									</div>
								</liferay-util:whitespace-remover>
							</clay:content-col>

							<clay:content-col>
								<div class="btn-group" role="group">
									<div class="btn-group-item">
										<clay:link
											displayType="secondary"
											href="<%= importTranslationResultsDisplayContext.getImportTranslationURL(request, liferayPortletResponse) %>"
											label="upload-another-file"
											small="<%= true %>"
											type="button"
										/>
									</div>

									<c:if test="<%= importTranslationResultsDisplayContext.isDownloadCSVReportEnabled() %>">
										<div class="btn-group-item">
											<clay:link
												displayType="secondary"
												download="translation-error.csv"
												href="<%= importTranslationResultsDisplayContext.getFailureMessagesCSVDataURL(locale) %>"
												label="download-csv-report"
												small="<%= true %>"
												target="_blank"
												type="button"
											/>
										</div>
									</c:if>
								</div>
							</clay:content-col>
						</clay:content-row>

						<ul class="list-group list-group-no-bordered">

							<%
							Map<String, String> failureMessages = importTranslationResultsDisplayContext.getFailureMessages();

							for (Map.Entry<String, String> entry : failureMessages.entrySet()) {
							%>

								<li class="list-group-item">
									<clay:content-row
										cssClass="mb-2"
									>
										<clay:content-col
											cssClass="lfr-portal-tooltip list-group-title mt-0"
											expand="<%= true %>"
										>
											<div class="text-truncate" data-title="<%= entry.getKey() %>">
												<%= entry.getKey() %>
											</div>
										</clay:content-col>

										<clay:content-col
											cssClass="lfr-portal-tooltip ml-2 text-right"
											expand="<%= true %>"
										>
											<div class="text-truncate" data-title="<%= importTranslationResultsDisplayContext.getFileName() %>">
												<%= importTranslationResultsDisplayContext.getFileName() %>
											</div>
										</clay:content-col>
									</clay:content-row>

									<div class="text-danger"><%= entry.getValue() %></div>
								</li>

							<%
							}
							%>

						</ul>
					</clay:sheet>
				</c:if>
			</div>
		</clay:container-fluid>
	</div>
</div>