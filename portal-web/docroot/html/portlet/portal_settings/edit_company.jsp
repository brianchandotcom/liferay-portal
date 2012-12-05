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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
String[] configurationSections = PropsValues.COMPANY_SETTINGS_FORM_CONFIGURATION;
String[] identificationSections = PropsValues.COMPANY_SETTINGS_FORM_IDENTIFICATION;
String[] miscellaneousSections = PropsValues.COMPANY_SETTINGS_FORM_MISCELLANEOUS;

String[][] categorySections = {configurationSections, identificationSections, miscellaneousSections};

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/portal_settings/view");

request.setAttribute("addresses.className", Account.class.getName());
request.setAttribute("emailAddresses.className", Account.class.getName());
request.setAttribute("phones.className", Account.class.getName());
request.setAttribute("websites.className", Account.class.getName());

request.setAttribute("addresses.classPK", company.getAccountId());
request.setAttribute("emailAddresses.classPK", company.getAccountId());
request.setAttribute("phones.classPK", company.getAccountId());
request.setAttribute("websites.classPK", company.getAccountId());
%>

<portlet:actionURL var="editCompanyURL">
	<portlet:param name="struts_action" value="/portal_settings/edit_company" />
</portlet:actionURL>

<aui:form action="<%= editCompanyURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCompany();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" />

	<liferay-util:buffer var="htmlTop">
		<div class="company-info">
			<p class="float-container">
				<img alt="<liferay-ui:message key="logo" />" class="company-logo" src="<%= themeDisplay.getPathImage() %>/company_logo?img_id=<%= company.getLogoId() %>&t=<%= WebServerServletTokenUtil.getToken(company.getLogoId()) %>" /><br />

				<span class="company-name"><%= HtmlUtil.escape(company.getName()) %></span>
			</p>
		</div>
	</liferay-util:buffer>

	<liferay-ui:form-navigator
		categoryNames="<%= _CATEGORY_NAMES %>"
		categorySections="<%= categorySections %>"
		htmlTop="<%= htmlTop %>"
		jspPath="/html/portlet/portal_settings/"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCompany() {
		document.<portlet:namespace />fm.method = "post";
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.UPDATE %>";

		var redirect = "<portlet:renderURL><portlet:param name="struts_action" value="/portal_settings/edit_company" /></portlet:renderURL>";

		redirect += Liferay.Util.getHistoryParam('<portlet:namespace />');

		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;

		<portlet:namespace />saveEmails();
		<portlet:namespace />saveLdap();
		<portlet:namespace />saveLocales();

		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/portal_settings/edit_company" /></portlet:actionURL>");
	}
</aui:script>

<%!
private static final String[] _CATEGORY_NAMES = {"configuration", "identification", "miscellaneous"};
%>