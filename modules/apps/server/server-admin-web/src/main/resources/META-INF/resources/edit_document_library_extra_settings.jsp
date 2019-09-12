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
DLFileEntry dlFileEntry = null;

List<String> keys = null;
List<String> expandoBridgeAttributeNames = null;

List<DLFileEntry> dlFileEntries = DLFileEntryLocalServiceUtil.getExtraSettingsFileEntries(0, 1);

if (!dlFileEntries.isEmpty()) {
	dlFileEntry = dlFileEntries.get(0);

	List<DLFileVersion> dlFileVersions = dlFileEntry.getFileVersions(WorkflowConstants.STATUS_ANY);

	for (DLFileVersion dlFileVersion : dlFileVersions) {
		UnicodeProperties extraSettingsProperties = dlFileVersion.getExtraSettingsProperties();

		if (extraSettingsProperties.isEmpty()) {
			continue;
		}

		keys = new ArrayList<String>(extraSettingsProperties.size());
		expandoBridgeAttributeNames = new ArrayList<String>(extraSettingsProperties.size());

		ExpandoBridge expandoBridge = dlFileEntry.getExpandoBridge();

		for (String key : extraSettingsProperties.keySet()) {
			if (expandoBridge.hasAttribute(key)) {
				expandoBridgeAttributeNames.add(key);
			}
			else {
				keys.add(key);
			}
		}

		break;
	}
}
%>

<c:choose>
	<c:when test="<%= dlFileEntry == null %>">
		<div class="alert alert-success">
			<liferay-ui:message key="there-are-no-longer-any-documents-and-media-files-with-extra-settings" />
		</div>
	</c:when>
	<c:otherwise>
		<c:if test="<%= (expandoBridgeAttributeNames != null) && !expandoBridgeAttributeNames.isEmpty() %>">
			<div class="alert alert-danger">
				<liferay-ui:message arguments="<%= StringUtil.merge(expandoBridgeAttributeNames) %>" key="custom-fields-already-exist-for-these-extra-settings-x" translateArguments="<%= false %>" />
			</div>
		</c:if>

		<portlet:actionURL name="/server_admin/edit_document_library_extra_settings" var="convertDocumentLibraryExtraSettingsURL" />

		<aui:form action="<%= convertDocumentLibraryExtraSettingsURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "convertDocumentLibraryExtraSettings();" %>'>
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="keys" type="hidden" value="<%= StringUtil.merge(keys) %>" />

			<%
			for (String key : keys) {
				String selectName = "type_" + key;
			%>

				<aui:fieldset>
					<liferay-ui:message arguments="<%= key %>" key="convert-extra-settings-key-from-x-to" translateArguments="<%= false %>" />

					<br />

					<aui:select helpMessage="custom-field-type-help" label="type" name="<%= selectName %>">

						<%
						for (int curType : ExpandoColumnConstants.TYPES) {
							if ((curType == ExpandoColumnConstants.BOOLEAN_ARRAY) || (curType == ExpandoColumnConstants.DATE_ARRAY)) {
								continue;
							}
						%>

							<aui:option label="<%= ExpandoColumnConstants.getTypeLabel(curType) %>" value="<%= curType %>" />

						<%
						}
						%>

					</aui:select>
				</aui:fieldset>

			<%
			}
			%>

			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</aui:form>
	</c:otherwise>
</c:choose>

<aui:script>
	function <portlet:namespace />convertDocumentLibraryExtraSettings() {
		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			var cmd = form.querySelector(
				'#<portlet:namespace /><%= Constants.CMD %>'
			);

			if (cmd) {
				cmd.setAttribute('value', 'convert');
			}

			submitForm(form);
		}
	}
</aui:script>