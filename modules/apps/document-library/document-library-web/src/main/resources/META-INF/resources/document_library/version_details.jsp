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

<%@ include file="/document_library/init.jsp" %>

<%
boolean checkedOut = GetterUtil.getBoolean(request.getAttribute("edit_file_entry.jsp-checkedOut"));
%>

<div id="<portlet:namespace />versionDetails" style="display: none">
	<aui:fieldset>
		<h5 class="control-label"><liferay-ui:message key="select-whether-this-is-a-major-or-minor-version" /></h5>

		<aui:input checked="<%= checkedOut %>" label="major-version" name="versionDetailsVersionIncrease" onChange='<%= renderResponse.getNamespace() + "showVersionNotes(event);" %>' type="radio" value="<%= DLVersionNumberIncrease.MAJOR %>" />

		<aui:input checked="<%= !checkedOut %>" label="minor-version" name="versionDetailsVersionIncrease" onChange='<%= renderResponse.getNamespace() + "showVersionNotes(event);" %>' type="radio" value="<%= DLVersionNumberIncrease.MINOR %>" />

		<aui:input checked="<%= false %>" label="keep-current-version-number" name="versionDetailsVersionIncrease" onChange='<%= renderResponse.getNamespace() + "hideVersionNotes(event);" %>' type="radio" value="<%= DLVersionNumberIncrease.NONE %>" />

		<aui:input label="version-notes" maxLength="75" name="versionDetailsChangeLog" />
	</aui:fieldset>

	<aui:script>
		function <portlet:namespace />hideVersionNotes(event) {
			var fieldset = event.currentTarget.closest('fieldset');

			var versionNotes = fieldset.querySelector(
				'#<portlet:namespace />versionDetailsChangeLog'
			);

			if (versionNotes) {
				versionNotes.parentElement.classList.add('hide');
			}
		}

		function <portlet:namespace />showVersionNotes(event) {
			var fieldset = event.currentTarget.closest('fieldset');

			var versionNotes = fieldset.querySelector(
				'#<portlet:namespace />versionDetailsChangeLog'
			);

			if (versionNotes) {
				versionNotes.parentElement.classList.remove('hide');
			}
		}
	</aui:script>
</div>