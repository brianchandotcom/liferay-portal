<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditAudiencesEntryDisplayContext editAudiencesEntryDisplayContext = (EditAudiencesEntryDisplayContext)request.getAttribute(EditAudiencesEntryDisplayContext.class.getName());

String backURL = editAudiencesEntryDisplayContext.getBackURL();

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
	portletDisplay.setURLBackTitle(editAudiencesEntryDisplayContext.getBackURLTitle());
}

renderResponse.setTitle(editAudiencesEntryDisplayContext.getTitle());
%>

<liferay-ui:error embed="<%= false %>" exception="<%= AudiencesEntryJSONException.class %>" message="you-have-entered-invalid-json" />
<liferay-ui:error embed="<%= false %>" exception="<%= AudiencesEntryNameException.class %>" message="please-enter-a-valid-name" />

<portlet:actionURL name="/audiences/update_audiences_entry" var="updateAudiencesEntryActionURL" />

<aui:form action="<%= updateAudiencesEntryActionURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= editAudiencesEntryDisplayContext.getRedirect() %>" />
	<aui:input name="audiencesEntryId" type="hidden" value="<%= editAudiencesEntryDisplayContext.getAudiencesEntryId() %>" />

	<aui:input name="name" />

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= editAudiencesEntryDisplayContext.getBackURL() %>" type="cancel" />
	</aui:button-row>
</aui:form>