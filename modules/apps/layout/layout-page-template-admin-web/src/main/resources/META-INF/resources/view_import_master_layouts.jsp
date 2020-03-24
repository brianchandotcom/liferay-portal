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

<portlet:actionURL name="/layout_page_template/import_master_layout" var="importMasterLayoutURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= importMasterLayoutURL %>"
	enctype="multipart/form-data"
	name="fm"
>
	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<aui:input helpMessage='<%= LanguageUtil.format(request, "import-master-layouts-help", "https://portal.liferay.dev/docs", false) %>' label="select-file" name="file" type="file">
					<aui:validator name="required" />

					<aui:validator name="acceptFiles">
						'zip'
					</aui:validator>
				</aui:input>

				<aui:input checked="<%= true %>" label="overwrite-existing-entries" name="overwrite" type="checkbox" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>

		<%
		List<MasterLayoutsImporterResultEntry> notImportedMasterLayoutsImporterResultEntries = (List<MasterLayoutsImporterResultEntry>)SessionMessages.get(renderRequest, "notImportedMasterLayoutsImporterResultEntries");
		%>

		<c:if test="<%= ListUtil.isNotEmpty(notImportedMasterLayoutsImporterResultEntries) %>">

			<%
			int total = notImportedMasterLayoutsImporterResultEntries.size();
			int viewTotal = (total > 10) ? 10 : total;
			%>

			<div class="alert alert-warning warning-dialog">
				<span class="error-message"><liferay-ui:message key="some-entries-could-not-be-imported" /></span>

				<ul class="error-list-items">

					<%
					for (int i = 0; i < viewTotal; i++) {
						MasterLayoutsImporterResultEntry masterLayoutsImporterResultEntry = notImportedMasterLayoutsImporterResultEntries.get(i);
					%>

						<li>
							<span class="error-info"><%= HtmlUtil.escape(masterLayoutsImporterResultEntry.getErrorMessage()) %></span>
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