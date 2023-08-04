<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ImportDisplayContext importDisplayContext = new ImportDisplayContext(request, renderRequest, renderResponse);
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
			<aui:input label="file" name="file" required="<%= true %>" type="file">
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
			<clay:alert
				displayType="<%= importDisplayContext.getDialogType() %>"
			>
				<span class="font-weight-bold"><%= importDisplayContext.getDialogMessage() %></span>

				<ul>

					<%
					Map<Integer, List<LayoutsImporterResultEntry>> importedLayoutsImporterResultEntriesMap = importDisplayContext.getImportedLayoutsImporterResultEntriesMap();
					%>

					<c:if test="<%= MapUtil.isNotEmpty(importedLayoutsImporterResultEntriesMap) %>">

						<%
						for (Map.Entry<Integer, List<LayoutsImporterResultEntry>> entrySet : importedLayoutsImporterResultEntriesMap.entrySet()) {
						%>

							<li>
								<span class="font-italic"><%= HtmlUtil.escape(importDisplayContext.getSuccessMessage(entrySet)) %></span>
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
						for (LayoutsImporterResultEntry layoutsImporterResultEntry : layoutsImporterResultEntriesWithWarnings) {
							String[] warningMessages = layoutsImporterResultEntry.getWarningMessages();
						%>

							<li>
								<span class="font-italic"><%= HtmlUtil.escape(importDisplayContext.getWarningMessage(layoutsImporterResultEntry.getName())) %></span>

								<ul>

									<%
									for (String warningMessage : warningMessages) {
									%>

										<li><span class="font-italic"><%= HtmlUtil.escape(warningMessage) %></span></li>

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
					int i = 0;

					List<LayoutsImporterResultEntry> notImportedLayoutsImporterResultEntries = importDisplayContext.getNotImportedLayoutsImporterResultEntries();
					%>

					<c:if test="<%= ListUtil.isNotEmpty(notImportedLayoutsImporterResultEntries) %>">

						<%
						for (; (i < notImportedLayoutsImporterResultEntries.size()) && (i < 10); i++) {
							LayoutsImporterResultEntry layoutsImporterResultEntry = notImportedLayoutsImporterResultEntries.get(i);
						%>

							<li>
								<span class="font-italic"><%= HtmlUtil.escape(layoutsImporterResultEntry.getErrorMessage()) %></span>
							</li>

						<%
						}
						%>

					</c:if>
				</ul>

				<c:if test="<%= notImportedLayoutsImporterResultEntries.size() > 10 %>">
					<span><%= LanguageUtil.format(request, "x-more-entries-could-also-not-be-imported", "<strong>" + (notImportedLayoutsImporterResultEntries.size() - i) + "</strong>", false) %></span>
				</c:if>
			</clay:alert>
		</c:if>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			submitLabel="import"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>