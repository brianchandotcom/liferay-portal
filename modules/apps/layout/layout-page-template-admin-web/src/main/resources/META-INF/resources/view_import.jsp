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

<portlet:actionURL name="/layout_page_template/import" var="importURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= importURL %>"
	enctype="multipart/form-data"
	name="fm"
>
	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
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

				<aui:input checked="<%= true %>" label="overwrite-existing-entries" name="overwrite" type="checkbox" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>

		<%
		Map<Integer, List<LayoutPageTemplatesImporterResultEntry>> importedLayoutPageTemplatesImporterResultEntriesMap = importDisplayContext.getImportedLayoutPageTemplatesImporterResultEntriesMap();
		%>

		<c:if test="<%= MapUtil.isNotEmpty(importedLayoutPageTemplatesImporterResultEntriesMap) %>">
			<div class="alert alert-success success-dialog">

				<%
				int importedLayoutPageTemplatesImporterResultEntriesSize = 0;

				if (importedLayoutPageTemplatesImporterResultEntriesMap != null) {
					for (List<LayoutPageTemplatesImporterResultEntry> layoutPageTemplatesImporterResultEntries :
						importedLayoutPageTemplatesImporterResultEntriesMap.values()) {

						if (ListUtil.isNotEmpty(layoutPageTemplatesImporterResultEntries)) {
							importedLayoutPageTemplatesImporterResultEntriesSize =
								importedLayoutPageTemplatesImporterResultEntriesSize +
								layoutPageTemplatesImporterResultEntries.size();
						}
					}
				}
				%>

				<c:if test="<%= importedLayoutPageTemplatesImporterResultEntriesSize == 1 %>">
					<span class="success-message"><liferay-ui:message key="1-entry-was-imported-correctly" /></span>
				</c:if>

				<c:if test="<%= importedLayoutPageTemplatesImporterResultEntriesSize > 1 %>">
					<span class="success-message"><liferay-ui:message arguments="<%= importedLayoutPageTemplatesImporterResultEntriesSize %>" key="x-entries-were-imported-correctly" /></span>
				</c:if>

				<ul class="success-list-items">

					<%
					for (Map.Entry <Integer, List<LayoutPageTemplatesImporterResultEntry>> entrySet :
						importedLayoutPageTemplatesImporterResultEntriesMap.entrySet()) {
					%>

						<li>
							<span class="success-info"><%= HtmlUtil.escape(importDisplayContext.getSuccessMessage(entrySet)) %></span>
						</li>

					<%
					}
					%>

				</ul>
			</div>
		</c:if>

		<%
		List<LayoutPageTemplatesImporterResultEntry> layoutPageTemplatesImporterResultEntriesWithWarnings = importDisplayContext.getLayoutPageTemplatesImporterResultEntriesWithWarnings();
		%>

		<c:if test="<%= ListUtil.isNotEmpty(layoutPageTemplatesImporterResultEntriesWithWarnings) %>">
			<div class="alert alert-warning warning-dialog">
				<span class="error-message"><liferay-ui:message key="some-entries-were-imported-with-warnings" /></span>

				<ul class="error-list-items">

					<%
					for (int i = 0; i < layoutPageTemplatesImporterResultEntriesWithWarnings.size(); i++) {
						LayoutPageTemplatesImporterResultEntry layoutPageTemplatesImporterResultEntry = layoutPageTemplatesImporterResultEntriesWithWarnings.get(i);

						String[] warningMessages = layoutPageTemplatesImporterResultEntry.getWarningMessages();
					%>

						<li>
							<span class="error-info"><%= HtmlUtil.escape(layoutPageTemplatesImporterResultEntry.getName()) %></span>

							<ul>

								<%
								for (String warningMessage : warningMessages) {
								%>

									<li><span class="error-info"><%= HtmlUtil.escape(warningMessage) %></span></li>

								<%
								}
								%>

							</ul>
						</li>

					<%
					}
					%>

				</ul>
			</div>
		</c:if>

		<%
		List<LayoutPageTemplatesImporterResultEntry> notImportedLayoutPageTemplatesImporterResultEntries = importDisplayContext.getNotImportedLayoutPageTemplatesImporterResultEntries();
		%>

		<c:if test="<%= ListUtil.isNotEmpty(notImportedLayoutPageTemplatesImporterResultEntries) %>">

			<%
			int total = notImportedLayoutPageTemplatesImporterResultEntries.size();
			int viewTotal = (total > 10) ? 10 : total;
			%>

			<div class="alert alert-warning warning-dialog">
				<span class="error-message"><liferay-ui:message key="some-entries-could-not-be-imported" /></span>

				<ul class="error-list-items">

					<%
					for (int i = 0; i < viewTotal; i++) {
						LayoutPageTemplatesImporterResultEntry layoutPageTemplatesImporterResultEntry = notImportedLayoutPageTemplatesImporterResultEntries.get(i);
					%>

						<li>
							<span class="error-info"><%= HtmlUtil.escape(layoutPageTemplatesImporterResultEntry.getErrorMessage()) %></span>
						</li>

					<%
					}
					%>

				</ul>

				<c:if test="<%= total > 10 %>">
					<span class="error-info"><%= LanguageUtil.format(request, "x-more-entries-could-also-not-be-imported", "<strong>" + (total - viewTotal) + "</strong>", false) %></span>
				</c:if>
			</div>
		</c:if>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" value="import" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>