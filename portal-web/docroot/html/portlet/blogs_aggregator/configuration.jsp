<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/blogs_aggregator/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String organizationName = StringPool.BLANK;

Organization organization = null;

if (organizationId > 0) {
	organization = OrganizationLocalServiceUtil.getOrganization(organizationId);

	organizationName = organization.getName();
}
%>

<portlet:renderURL var="organizationSelectorURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value="/portlet_configuration/select_organization" />
	<portlet:param name="tabs1" value="organizations" />
</portlet:renderURL>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="preferences--organizationId--" type="hidden" value="<%= organizationId %>" />

	<aui:fieldset>
		<aui:select name="preferences--selectionMethod--">
			<aui:option label="users" selected='<%= selectionMethod.equals("users") %>' />
			<aui:option label="scope" selected='<%= selectionMethod.equals("scope") %>' />
		</aui:select>

		<div id="<portlet:namespace />usersSelectionOptions">
			<aui:field-wrapper label="organization">
				<span id="<portlet:namespace />organizationName"><%= HtmlUtil.escape(organizationName) %></span>

				<aui:button name="selectOrganizationButton" onClick='<%= renderResponse.getNamespace() + "openOrganizationSelector();" %>' value="select" />

				<aui:button disabled="<%= organizationId <= 0 %>" name="removeOrganizationButton" onClick='<%= renderResponse.getNamespace() + "removeOrganization();" %>' value="remove" />
			</aui:field-wrapper>
		</div>

		<aui:select name="preferences--displayStyle--">
			<aui:option label="body-and-image" selected='<%= displayStyle.equals("body-and-image") %>' />
			<aui:option label="body" selected='<%= displayStyle.equals("body") %>' />
			<aui:option label="abstract" selected='<%= displayStyle.equals("abstract") %>' />
			<aui:option label="abstract-without-title" selected='<%= displayStyle.equals("abstract-without-title") %>' />
			<aui:option label="quote" selected='<%= displayStyle.equals("quote") %>' />
			<aui:option label="quote-without-title" selected='<%= displayStyle.equals("quote-without-title") %>' />
			<aui:option label="title" selected='<%= displayStyle.equals("title") %>' />
		</aui:select>

		<aui:select label="maximum-items-to-display" name="preferences--max--">
			<aui:option label="1" selected="<%= max == 1 %>" />
			<aui:option label="2" selected="<%= max == 2 %>" />
			<aui:option label="3" selected="<%= max == 3 %>" />
			<aui:option label="4" selected="<%= max == 4 %>" />
			<aui:option label="5" selected="<%= max == 5 %>" />
			<aui:option label="10" selected="<%= max == 10 %>" />
			<aui:option label="15" selected="<%= max == 15 %>" />
			<aui:option label="20" selected="<%= max == 20 %>" />
			<aui:option label="25" selected="<%= max == 25 %>" />
			<aui:option label="30" selected="<%= max == 30 %>" />
			<aui:option label="40" selected="<%= max == 40 %>" />
			<aui:option label="50" selected="<%= max == 50 %>" />
			<aui:option label="60" selected="<%= max == 60 %>" />
			<aui:option label="70" selected="<%= max == 70 %>" />
			<aui:option label="80" selected="<%= max == 80 %>" />
			<aui:option label="90" selected="<%= max == 90 %>" />
			<aui:option label="100" selected="<%= max == 100 %>" />
		</aui:select>

		<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
			<aui:input name="preferences--enableRssSubscription--" type="checkbox" value="<%= enableRssSubscription %>" />
		</c:if>

		<aui:input name="preferences--showTags--" type="checkbox" value="<%= showTags %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />openOrganizationSelector() {
		var organizationWindow = window.open('<%= organizationSelectorURL %>', 'organization', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');

		organizationWindow.focus();
	}

	function <portlet:namespace />removeOrganization() {
		document.<portlet:namespace />fm.<portlet:namespace />organizationId.value = "";

		var nameEl = document.getElementById("<portlet:namespace />organizationName");

		nameEl.innerHTML = "";

		document.getElementById("<portlet:namespace />removeOrganizationButton").disabled = true;
	}

	function <portlet:namespace />selectOrganization(organizationId, groupId, name) {
		document.<portlet:namespace />fm.<portlet:namespace />organizationId.value = organizationId;

		var nameEl = document.getElementById("<portlet:namespace />organizationName");

		nameEl.innerHTML = name + "&nbsp;";

		document.getElementById("<portlet:namespace />removeOrganizationButton").disabled = false;
	}
</aui:script>

<aui:script use="aui-base">
	var selectionMethod = A.one('#<portlet:namespace />selectionMethod');

	function showHiddenFields() {
		var usersSelectionOptions = A.one('#<portlet:namespace />usersSelectionOptions');

		if (selectionMethod.val() == 'users') {
			usersSelectionOptions.show();
		}
		else {
			usersSelectionOptions.hide();
		}
	}

	showHiddenFields();

	selectionMethod.on('change', showHiddenFields);
</aui:script>