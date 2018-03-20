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

<%@ include file="/deletions/init.jsp" %>

<%
String deleteApplicationDataBeforeImportingLabelTitle = LanguageUtil.get(request, "delete-application-data-before-importing");
String deleteApplicationDataBeforeImportingLabelWarning = LanguageUtil.get(request, "delete-content-before-importing-warning");
String deleteApplicationDataBeforeImportingLabelSuggestion = LanguageUtil.get(request, "delete-content-before-importing-suggestion");

String individualDeletionsTitle = StringPool.BLANK;
String individualDeletionsDescription = StringPool.BLANK;

if (cmd.equals(Constants.EXPORT)) {
	individualDeletionsTitle = LanguageUtil.get(request, "export-individual-deletions");
	individualDeletionsDescription = LanguageUtil.get(request, "deletions-help-export");
}
else {
	individualDeletionsTitle = LanguageUtil.get(request, "replicate-individual-deletions");
	individualDeletionsDescription = LanguageUtil.get(request, "deletions-help");
}

String deleteApplicationDataBeforeImportingLabel =
	deleteApplicationDataBeforeImportingLabelTitle + ": <span style='font-weight: normal;'>" +
		deleteApplicationDataBeforeImportingLabelWarning + " " + deleteApplicationDataBeforeImportingLabelSuggestion + "</span> ";
String individualDeletionsLabel = individualDeletionsTitle + ": <span style='font-weight: normal;'>" + individualDeletionsDescription + "</span> ";
%>

<c:if test="<%= cmd.equals(Constants.EXPORT) || cmd.equals(Constants.IMPORT) || cmd.equals(Constants.PUBLISH) %>">
	<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" cssClass="options-group" label="deletions" markupView="lexicon">
		<c:if test="<%= !cmd.equals(Constants.EXPORT) %>">
			<aui:input disabled="<%= disableInputs %>" id="deletePortletDataBeforeImportingCheckbox" label="<%= deleteApplicationDataBeforeImportingLabel %>" name="<%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA, false) %>" />
		</c:if>

		<aui:input disabled="<%= disableInputs %>" label="<%= individualDeletionsLabel %>" name="<%= PortletDataHandlerKeys.DELETIONS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.DELETIONS, false) %>" />
	</aui:fieldset>
</c:if>