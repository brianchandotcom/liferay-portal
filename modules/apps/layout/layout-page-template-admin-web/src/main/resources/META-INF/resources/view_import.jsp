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
ImportDisplayContext importDisplayContext = new ImportDisplayContext(request, renderRequest);
%>

<portlet:actionURL name="/layout_page_template_admin/import" var="importURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= importURL %>"
	enctype="multipart/form-data"
	name="fm"
>
	<liferay-frontend:edit-form-body>
		<liferay-ui:message key="import-help" />

		<a href="https://portal.liferay.dev/docs" target="_blank">
			<liferay-ui:message key="read-more" />
		</a>

		<br /><br />

		<liferay-frontend:fieldset>
			<aui:input label="file" name="file" type="file">
				<aui:validator name="required" />

				<aui:validator name="acceptFiles">
					'zip'
				</aui:validator>
			</aui:input>

			<aui:input checked="<%= true %>" label="overwrite-existing-page-templates" name="overwrite" type="checkbox" />
		</liferay-frontend:fieldset>

		<%
		Map<LayoutsImporterResultEntry.Status, List<LayoutsImporterResultEntry>> layoutsImporterResultEntryMap = importDisplayContext.getLayoutsImporterResultEntryMap();
		%>

		<c:if test="<%= MapUtil.isNotEmpty(layoutsImporterResultEntryMap) %>">

			<%
			String dialogType = importDisplayContext.getDialogType();
			%>

			<div class="alert alert-<%= dialogType %> <%= dialogType %>-dialog">
				<span class="<%= dialogType %>-message"><%= importDisplayContext.getDialogMessage() %></span>

				<ul class="<%= dialogType %>-list-items">

					<%
					Map<Integer, List<LayoutsImporterResultEntry>> importedLayoutsImporterResultEntriesMap = importDisplayContext.getImportedLayoutsImporterResultEntriesMap();
					%>

					<c:if test="<%= MapUtil.isNotEmpty(importedLayoutsImporterResultEntriesMap) %>">

						<%
						for (Map.Entry<Integer, List<LayoutsImporterResultEntry>> entrySet : importedLayoutsImporterResultEntriesMap.entrySet()) {
						%>

							<li>
								<span class="<%= dialogType %>-info"><%= HtmlUtil.escape(importDisplayContext.getSuccessMessage(entrySet)) %></span>
							</li>

						<%
						}
						%>

					</c:if>

					<%
					List<LayoutsImporterResultEntry> layoutsImporterResultEntriesWithWarnings = importDisplayContext.getLayoutsImporterResultEntriesWithWarnings();
					%>

					<c:if test="<%= ListUtil.isNotEmpty(layoutsImporterResultEntriesWithWarnings) %>">

						<%
						for (int i = 0; i < layoutsImporterResultEntriesWithWarnings.size(); i++) {
							LayoutsImporterResultEntry layoutsImporterResultEntry = layoutsImporterResultEntriesWithWarnings.get(i);

							String[] warningMessages = layoutsImporterResultEntry.getWarningMessages();
						%>

							<li>
								<span class="<%= dialogType %>-info"><%= HtmlUtil.escape(importDisplayContext.getWarningMessage(layoutsImporterResultEntry.getName())) %></span>

								<ul>

									<%
									for (String warningMessage : warningMessages) {
									%>

										<li><span class="<%= dialogType %>-info"><%= HtmlUtil.escape(warningMessage) %></span></li>

									<%
									}
									%>

								</ul>
							</li>

						<%
						}
						%>

					</c:if>

					<%
					int total = 0;
					int viewTotal = 0;

					List<LayoutsImporterResultEntry> notImportedLayoutsImporterResultEntries = importDisplayContext.getNotImportedLayoutsImporterResultEntries();
					%>

					<c:if test="<%= ListUtil.isNotEmpty(notImportedLayoutsImporterResultEntries) %>">

						<%
						total = notImportedLayoutsImporterResultEntries.size();

						viewTotal = (total > 10) ? 10 : total;

						for (int i = 0; i < viewTotal; i++) {
							LayoutsImporterResultEntry layoutsImporterResultEntry = notImportedLayoutsImporterResultEntries.get(i);
						%>

							<li>
								<span class="<%= dialogType %>-info"><%= HtmlUtil.escape(layoutsImporterResultEntry.getErrorMessage()) %></span>
							</li>

						<%
						}
						%>

					</c:if>
				</ul>

				<c:if test="<%= total > 10 %>">
					<span class="<%= dialogType %>-info"><%= LanguageUtil.format(request, "x-more-entries-could-also-not-be-imported", "<strong>" + (total - viewTotal) + "</strong>", false) %></span>
				</c:if>
			</div>
		</c:if>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			submitLabel="import"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>