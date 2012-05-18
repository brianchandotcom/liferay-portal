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
themeDisplay.setIncludeServiceJs(true);

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

//START  - LPSS-26599 changes
//TODO need to have robust checking here
boolean hasExportImportPermission = true;
boolean hasImportErrors = false;

String rootNodeName = "PortalSettings";
request.setAttribute("edit_settings.jsp-rootNodeName", rootNodeName);

//END  - LPSS-26599 changes

%>

<portlet:actionURL var="editCompanyURL">
	<portlet:param name="struts_action" value="/portal_settings/edit_company" />
</portlet:actionURL>

<%-- Start LPS-26599 changes --%>
<div class="lfr-header-row">
	   <div class="lfr-header-row-content">
		<aui:button-row cssClass="edit-toolbar" id='<%= liferayPortletResponse.getNamespace() + "portalSettingsToolbar" %>'>
		      <c:if test="<%= hasExportImportPermission %>">
			      <c:if test="<%=hasImportErrors%>">
				    <liferay-util:html-top>
					<div class="aui-helper-hidden" id="<portlet:namespace />importSettings">
						<liferay-util:include page="/html/portlet/portal_settings/export_import.jsp">
							<liferay-util:param name="<%= Constants.CMD %>" value="<%= Constants.IMPORT %>" />
							<!-- Other params as required -->
						</liferay-util:include>
					</div>
				   </liferay-util:html-top>

				   <aui:script use="aui-dialog">
					new A.Dialog(
						{
							align: Liferay.Util.Window.ALIGN_CENTER,
							bodyContent: A.one('#<portlet:namespace />importSettings').show(),
							modal: true,
							title: '<%= UnicodeLanguageUtil.get(pageContext, "import") %>',
							width: 600
						}
					).render();
				</aui:script>
		      	   </c:if>
		      </c:if>
		</aui:button-row>
	   </div>
</div>
<div>&nbsp;</div>
<aui:form action="<%= editCompanyURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCompany();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
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


<%-- Updated for LPS26599 --%>
<aui:script use="aui-dialog,aui-toolbar">
	var popup;
        var importPopup;
        var exportPopup;
        var portalSettingsToolbarChildren = [];

	<c:if test="<%=hasExportImportPermission%>">
		portalSettingsToolbarChildren.push(
			{
	  			type: 'ToolbarSpacer'
	 		},
			{
				handler: function(event) {
					<portlet:renderURL var="exportSettingsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="struts_action" value="/portal_settings/export_settings" />
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
						<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
					</portlet:renderURL>

					Liferay.Util.openWindow(
						{
							dialog:
								{
									align: Liferay.Util.Window.ALIGN_CENTER,
									constrain: true,
									modal: true,
									width: 600
								},
							id: '<portlet:namespace />exportDialog',
							title: '<%= UnicodeLanguageUtil.get(pageContext, "export") %>',
							uri: '<%= exportSettingsURL.toString() %>'
						}
					);
				},
				icon: 'export',
				label: '<%= UnicodeLanguageUtil.get(pageContext, "export") %>'
			},
		        {
				handler: function(event) {
					<portlet:renderURL var="importSettingsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="struts_action" value="/portal_settings/import_settings" />
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.IMPORT %>" />
					</portlet:renderURL>

					Liferay.Util.openWindow(
						{
							dialog:
								{
									align: Liferay.Util.Window.ALIGN_CENTER,
									constrain: true,
									modal: true,
									width: 600
								},
							id: '<portlet:namespace />importDialog',
							title: '<%= UnicodeLanguageUtil.get(pageContext, "import") %>',
							uri: '<%= importSettingsURL.toString() %>'
						}
					);


				},
				icon: 'arrowthick-1-t',
				label: '<%= UnicodeLanguageUtil.get(pageContext, "import") %>'
			}
		  );
		  var portalSettingsToolbar = new A.Toolbar(
		  {
			activeState: false,
			boundingBox: '#<portlet:namespace />portalSettingsToolbar',
			children: portalSettingsToolbarChildren
		  }).render();
    </c:if>
</aui:script>

<aui:script>
	function <portlet:namespace />saveCompany() {
		document.<portlet:namespace />fm.method = "post";
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.UPDATE %>";

		var redirect = "<portlet:renderURL><portlet:param name="struts_action" value="/portal_settings/edit_company" /></portlet:renderURL>";

		redirect += Liferay.Util.getHistoryParam('<portlet:namespace />');

		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;

		<portlet:namespace />saveLdap();

		<portlet:namespace />saveEmails();

		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/portal_settings/edit_company" /></portlet:actionURL>");
	}
</aui:script>

<%!
private static String[] _CATEGORY_NAMES = {"configuration", "identification", "miscellaneous"};
%>