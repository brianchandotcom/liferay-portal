<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

SCProductEntry productEntry = (SCProductEntry)request.getAttribute(WebKeys.SOFTWARE_CATALOG_PRODUCT_ENTRY);

productEntry = productEntry.toEscapedModel();

SCProductVersion productVersion = (SCProductVersion)request.getAttribute(WebKeys.SOFTWARE_CATALOG_PRODUCT_VERSION);

long productEntryId = productEntry.getProductEntryId();
long productVersionId = BeanParamUtil.getLong(productVersion, request, "productVersionId");

Set<Long> frameworkVersionIds = new HashSet<Long>();

String[] frameworkVersions = request.getParameterValues("frameworkVersions");

if ((productVersion != null) && (frameworkVersions == null)) {
	for (SCFrameworkVersion frameworkVersion : productVersion.getFrameworkVersions()) {
		frameworkVersionIds.add(frameworkVersion.getFrameworkVersionId());
	}
}
else {
	long[] frameworkVersionIdsArray = GetterUtil.getLongValues(frameworkVersions);

	for (int i = 0; i < frameworkVersionIdsArray.length; i++) {
		frameworkVersionIds.add(frameworkVersionIdsArray[i]);
	}
}

boolean testDirectDownloadURL = ParamUtil.getBoolean(request, "testDirectDownloadURL", true);

PortletURL editProductEntryURL = renderResponse.createRenderURL();

editProductEntryURL.setParameter("struts_action", "/software_catalog/edit_product_entry");
editProductEntryURL.setParameter("redirect", currentURL);
editProductEntryURL.setParameter("productEntryId", String.valueOf(productEntryId));
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/software_catalog/edit_product_version" /><portlet:param name="productEntryId" value="<%= String.valueOf(productEntryId) %>" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveEntry(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input name="<portlet:namespace />productVersionId" type="hidden" value="<%= productVersionId %>" />

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= false %>"
	title="<%= productEntry.getName() %>"
/>

<liferay-ui:error exception="<%= DuplicateProductVersionDirectDownloadURLException.class %>" message="please-enter-a-unique-direct-download-url" />
<liferay-ui:error exception="<%= ProductVersionChangeLogException.class %>" message="please-enter-a-valid-change-log" />
<liferay-ui:error exception="<%= ProductVersionDownloadURLException.class %>" message="please-enter-a-download-page-url-or-a-direct-download-url" />
<liferay-ui:error exception="<%= ProductVersionFrameworkVersionException.class %>" message="please-select-at-least-one-framework-version" />
<liferay-ui:error exception="<%= ProductVersionNameException.class %>" message="please-enter-a-valid-version-name" />
<liferay-ui:error exception="<%= UnavailableProductVersionDirectDownloadURLException.class %>" message="please-enter-a-valid-direct-download-url" />

<fieldset>
	<legend><liferay-ui:message key="main-fields" /></legend>

	<table class="lfr-table">
	<tr>
		<td>
			<liferay-ui:message key="version-name" />
		</td>
		<td>
			<liferay-ui:input-field autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" bean="<%= productVersion %>" field="version" model="<%= SCProductVersion.class %>" />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="change-log" />
		</td>
		<td>
			<liferay-ui:input-field bean="<%= productVersion %>" field="changeLog" model="<%= SCProductVersion.class %>" />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="supported-framework-versions" />
		</td>
		<td>
			<select multiple="true" name="<portlet:namespace />frameworkVersions">

				<%
				for (SCFrameworkVersion frameworkVersion : SCFrameworkVersionServiceUtil.getFrameworkVersions(scopeGroupId, true)) {
				%>

					<option <%= (frameworkVersionIds.contains(frameworkVersion.getFrameworkVersionId())) ? "selected" : "" %> value="<%= frameworkVersion.getFrameworkVersionId() %>"><%= HtmlUtil.escape(frameworkVersion.getName()) %></option>

				<%
				}
				%>

			</select>
		</td>
	</tr>
	</table>
</fieldset>

<fieldset class="repository-fields">
	<legend><%= LanguageUtil.get(pageContext, "repository-fields") %></legend>

	<table class="lfr-table">
	<tr>
		<td>
			<liferay-ui:message key="download-page-url" />
		</td>
		<td>
			<liferay-ui:input-field bean="<%= productVersion %>" field="downloadPageURL" model="<%= SCProductVersion.class %>" />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="direct-download-url" /> (<liferay-ui:message key="recommended" />)
		</td>
		<td>
			<liferay-ui:input-field bean="<%= productVersion %>" field="directDownloadURL" model="<%= SCProductVersion.class %>" />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="test-direct-download-url" />
		</td>
		<td>
			<select name="<portlet:namespace />testDirectDownloadURL">
				<option <%= testDirectDownloadURL ? "selected" : "" %> value="1"><liferay-ui:message key="yes" /></option>
				<option <%= !testDirectDownloadURL ? "selected" : "" %> value="0"><liferay-ui:message key="no" /></option>
			</select>
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="include-artifact-in-repository" />
		</td>

		<c:choose>
			<c:when test="<%= Validator.isNotNull(productEntry.getRepoArtifactId()) && Validator.isNotNull(productEntry.getRepoArtifactId()) %>">
				<td>
					<select name="<portlet:namespace />repoStoreArtifact">
						<option <%= ((productVersion != null) && productVersion.getRepoStoreArtifact()) ? "selected" : "" %> value="1"><liferay-ui:message key="yes" /></option>
						<option <%= ((productVersion != null) && !productVersion.getRepoStoreArtifact()) ? "selected" : "" %> value="0"><liferay-ui:message key="no" /></option>
					</select>
				</td>
			</c:when>
			<c:otherwise>
				<td>
					<a href="<%= editProductEntryURL %>"><liferay-ui:message key="you-must-specify-a-site-id-and-artifact-id-before-you-can-add-a-product-version" /></a>
				</td>
			</c:otherwise>
		</c:choose>
	</tr>
	</table>
</fieldset>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input onClick="location.href = '<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>';" type="button" value="<liferay-ui:message key="cancel" />" />

</form>

<aui:script>
	function <portlet:namespace />saveEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (productVersion == null) ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />toggleSelectBoxes() {
		if (document.<portlet:namespace />fm.<portlet:namespace />repoStoreArtifact) {
			if (document.<portlet:namespace />fm.<portlet:namespace />directDownloadURL.value == '') {
				document.<portlet:namespace />fm.<portlet:namespace />repoStoreArtifact.disabled = true;
				document.<portlet:namespace />fm.<portlet:namespace />repoStoreArtifact.options[0].selected = true;
			}
			else {
				document.<portlet:namespace />fm.<portlet:namespace />repoStoreArtifact.disabled = false;
			}
		}

		if (document.<portlet:namespace />fm.<portlet:namespace />testDirectDownloadURL) {
			if (document.<portlet:namespace />fm.<portlet:namespace />directDownloadURL.value == '') {
				document.<portlet:namespace />fm.<portlet:namespace />testDirectDownloadURL.disabled = true;
				document.<portlet:namespace />fm.<portlet:namespace />testDirectDownloadURL.options[0].selected = true;
			}
			else {
				document.<portlet:namespace />fm.<portlet:namespace />testDirectDownloadURL.disabled = false;
			}
		}
	}

	document.<portlet:namespace />fm.<portlet:namespace />directDownloadURL.onkeyup = <portlet:namespace />toggleSelectBoxes;

	<portlet:namespace />toggleSelectBoxes();
</aui:script>