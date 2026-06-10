<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditAudienceEntryDisplayContext editAudienceEntryDisplayContext = (EditAudienceEntryDisplayContext)request.getAttribute(EditAudienceEntryDisplayContext.class.getName());

String backURL = editAudienceEntryDisplayContext.getBackURL();

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
	portletDisplay.setURLBackTitle(editAudienceEntryDisplayContext.getBackURLTitle());
}

renderResponse.setTitle(editAudienceEntryDisplayContext.getTitle());
%>

<liferay-ui:error embed="<%= false %>" exception="<%= AudienceEntryNameException.class %>" message="please-enter-a-valid-name" />

<portlet:actionURL name="/audience/update_audience_entry" var="updateAudienceEntryActionURL" />

<aui:form action="<%= updateAudienceEntryActionURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= editAudienceEntryDisplayContext.getRedirect() %>" />
	<aui:input name="audienceEntryId" type="hidden" value="<%= editAudienceEntryDisplayContext.getAudienceEntryId() %>" />

	<aui:input name="name" />

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= editAudienceEntryDisplayContext.getBackURL() %>" type="cancel" />
	</aui:button-row>
</aui:form>