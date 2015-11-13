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

<%@ include file="/html/portlet/software_catalog/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

SCFrameworkVersion frameworkVersion = (SCFrameworkVersion)request.getAttribute(WebKeys.SOFTWARE_CATALOG_FRAMEWORK_VERSION);

long frameworkVersionId = BeanParamUtil.getLong(frameworkVersion, request, "frameworkVersionId");
%>

<form action="<portlet:actionURL name="/software_catalog/edit_framework_version" />" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveFrameworkVersion(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input name="<portlet:namespace />frameworkVersionId" type="hidden" value="<%= frameworkVersionId %>" />

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= (frameworkVersion == null) %>"
	title='<%= (frameworkVersion == null) ? "new-framework-version" : frameworkVersion.getName() %>'
/>

<liferay-ui:error exception="<%= FrameworkVersionNameException.class %>" message="please-enter-a-valid-name" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="name" />
	</td>
	<td>
		<liferay-ui:input-field autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" bean="<%= frameworkVersion %>" field="name" model="<%= SCFrameworkVersion.class %>" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="url" />
	</td>
	<td>
		<liferay-ui:input-field bean="<%= frameworkVersion %>" field="url" model="<%= SCFrameworkVersion.class %>" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="active" />
	</td>
	<td>
		<liferay-ui:input-field bean="<%= frameworkVersion %>" defaultValue="<%= Boolean.TRUE %>" field="active" model="<%= SCFrameworkVersion.class %>" />
	</td>
</tr>

<c:if test="<%= frameworkVersion == null %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="permissions" />
		</td>
		<td>
			<liferay-ui:input-permissions
				modelName="<%= SCFrameworkVersion.class.getName() %>"
			/>
		</td>
	</tr>
</c:if>

</table>

<div class="btn-toolbar">
	<aui:button cssClass="btn-primary" type="submit" value="save" />

	<%
	String taglibCancel = "location.href = '" + HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) + "';";
	%>

	<aui:button onClick="<%= taglibCancel %>" value="cancel" />
</div>
</form>

<aui:script>
	function <portlet:namespace />saveFrameworkVersion() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= (frameworkVersion == null) ? Constants.ADD : Constants.UPDATE %>';

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/software_catalog/view");
portletURL.setParameter("tabs1", "framework_versions");

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "framework-versions"), portletURL.toString());

if (frameworkVersion != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, frameworkVersion.getName(), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-framework-version"), currentURL);
}
%>