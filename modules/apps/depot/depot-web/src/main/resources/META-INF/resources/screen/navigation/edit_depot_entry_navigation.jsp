<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "redirect");

String actionCommandName = (String)request.getAttribute(DepotAdminWebKeys.ACTION_COMMAND_NAME);
String formDescription = (String)request.getAttribute(DepotAdminWebKeys.FORM_DESCRIPTION);
String formLabel = (String)request.getAttribute(DepotAdminWebKeys.FORM_LABEL);
String jspPath = (String)request.getAttribute(DepotAdminWebKeys.JSP_PATH);

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}

DepotEntry depotEntry = (DepotEntry)request.getAttribute(DepotAdminWebKeys.DEPOT_ENTRY);

Group group = depotEntry.getGroup();

renderResponse.setTitle(group.getDescriptiveName(locale));
%>

<liferay-ui:success key='<%= DepotPortletKeys.DEPOT_ADMIN + "requestProcessed" %>' message="asset-library-was-added" />

<portlet:actionURL name="<%= actionCommandName %>" var="actionCommandURL" />

<aui:form action="<%= actionCommandURL %>" method="post" name="fm">
	<aui:input name="depotEntryId" type="hidden" value="<%= depotEntry.getDepotEntryId() %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<clay:sheet>
		<clay:sheet-header>
			<liferay-ui:error exception="<%= ConfigurationModelListenerException.class %>" message="mime-type-size-limit-error" />

			<h2 class="sheet-title"><%= formLabel %></h2>

			<c:if test="<%= Validator.isNotNull(formDescription) %>">
				<p><%= formDescription %></p>
			</c:if>
		</clay:sheet-header>

		<clay:sheet-section
			cssClass="lfr-depot-sheet-section"
		>
			<liferay-util:include page="<%= jspPath %>" servletContext="<%= application %>" />
		</clay:sheet-section>

		<c:if test="<%= (boolean)request.getAttribute(DepotAdminWebKeys.SHOW_CONTROLS) %>">
			<clay:sheet-footer>
				<aui:button primary="<%= true %>" type="submit" />

				<aui:button href="<%= Validator.isNotNull(backURL) ? backURL : String.valueOf(renderResponse.createRenderURL()) %>" type="cancel" />
			</clay:sheet-footer>
		</c:if>
	</clay:sheet>
</aui:form>