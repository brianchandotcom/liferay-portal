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

long productEntryId = BeanParamUtil.getLong(productEntry, request, "productEntryId");

String type = BeanParamUtil.getString(productEntry, request, "type");

Set<Long> licenseIds = new HashSet<Long>();

if ((productEntry != null) && (request.getParameterValues("licenses") == null)) {
	for (SCLicense license : productEntry.getLicenses()) {
		licenseIds.add(license.getLicenseId());
	}
}
else {
	long[] licenses = ParamUtil.getLongValues(request, "licenses");

	for (int i = 0; i < licenses.length; i++) {
		licenseIds.add(licenses[i]);
	}
}

List productScreenshots = SCProductScreenshotLocalServiceUtil.getProductScreenshots(productEntryId);

int screenshotsCount = ParamUtil.getInteger(request, "screenshotsCount", productScreenshots.size());
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/software_catalog/edit_product_entry" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveProductEntry(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input name="<portlet:namespace />productEntryId" type="hidden" value="<%= productEntryId %>" />
<input name="<portlet:namespace />screenshotsCount" type="hidden" value="<%= screenshotsCount %>" />

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= (productEntry == null) %>"
	title='<%= (productEntry == null) ? "new-product" : productEntry.getName() %>'
/>

<liferay-ui:error exception="<%= DuplicateProductEntryModuleIdException.class %>" message="please-enter-a-unique-site-id-and-artifact-id-combination" />
<liferay-ui:error exception="<%= ProductEntryAuthorException.class %>" message="please-enter-a-valid-author" />
<liferay-ui:error exception="<%= ProductEntryLicenseException.class %>" message="please-select-at-least-one-license" />
<liferay-ui:error exception="<%= ProductEntryNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= ProductEntryPageURLException.class %>" message="please-enter-a-valid-page-url" />
<liferay-ui:error exception="<%= ProductEntryScreenshotsException.class %>" message="screenshots-must-contain-a-valid-thumbnail-and-a-valid-full-image" />
<liferay-ui:error exception="<%= ProductEntryShortDescriptionException.class %>" message="please-enter-a-valid-short-description" />
<liferay-ui:error exception="<%= ProductEntryTypeException.class %>" message="please-select-a-valid-type" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="name" />
	</td>
	<td>
		<liferay-ui:input-field autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" bean="<%= productEntry %>" field="name" model="<%= SCProductEntry.class %>" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="type" />
	</td>
	<td>
		<select name="<portlet:namespace />type">

			<%
			for (String supportedType : PluginPackageUtil.getSupportedTypes()) {
			%>

				<option <%= type.equals(supportedType) ? "selected" : "" %> value="<%= supportedType %>"><liferay-ui:message key='<%= supportedType + "-plugin" %>' /></option>

			<%
			}
			%>

		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="licenses" />
	</td>
	<td>
		<select multiple="true" name="<portlet:namespace />licenses">
			<optgroup label="<liferay-ui:message key="recommended-licenses" />">

				<%
				for (SCLicense license : SCLicenseLocalServiceUtil.getLicenses(true, true)) {
				%>

					<option <%= licenseIds.contains(license.getLicenseId()) ? "selected" : "" %> value="<%= license.getLicenseId() %>"><%= HtmlUtil.escape(license.getName()) %></option>

				<%
				}
				%>

			</optgroup>

			<optgroup label="<liferay-ui:message key="other-licenses" />">

				<%
				for (SCLicense license : SCLicenseLocalServiceUtil.getLicenses(true, false)) {
				%>

					<option <%= licenseIds.contains(license.getLicenseId()) ? "selected" : "" %> value="<%= license.getLicenseId() %>"><%= HtmlUtil.escape(license.getName()) %></option>

				<%
				}
				%>

			</optgroup>
		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="author" />
	</td>
	<td>
		<liferay-ui:input-field bean="<%= productEntry %>" field="author" model="<%= SCProductEntry.class %>" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="page-url" />
	</td>
	<td>
		<liferay-ui:input-field bean="<%= productEntry %>" field="pageURL" model="<%= SCProductEntry.class %>" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="tags" />
	</td>
	<td>
		<liferay-ui:input-field bean="<%= productEntry %>" field="tags" model="<%= SCProductEntry.class %>" /> (<liferay-ui:message key="comma-delimited-list" />)
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="short-description" />
	</td>
	<td>
		<liferay-ui:input-field bean="<%= productEntry %>" field="shortDescription" model="<%= SCProductEntry.class %>" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="long-description" />
	</td>
	<td>
		<liferay-ui:input-field bean="<%= productEntry %>" field="longDescription" model="<%= SCProductEntry.class %>" />
	</td>
</tr>

<c:if test="<%= productEntry == null %>">
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
				modelName="<%= SCProductEntry.class.getName() %>"
			/>
		</td>
	</tr>
</c:if>

</table>

<div class="lfr-asset-panels">
	<liferay-ui:panel-container extended="<%= false %>" id="productEntryPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel extended="<%= false %>" id="pluginRepositoryPanel" persistState="<%= true %>" title="plugin-repository">
			<table class="lfr-table">
			<tr>
				<td>
					<liferay-ui:message key="site-id" />
				</td>
				<td>
					<liferay-ui:input-field bean="<%= productEntry %>" field="repoGroupId" model="<%= SCProductEntry.class %>" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="artifact-id" />
				</td>
				<td>
					<liferay-ui:input-field bean="<%= productEntry %>" field="repoArtifactId" model="<%= SCProductEntry.class %>" />
				</td>
			</tr>
			</table>
		</liferay-ui:panel>

		<liferay-ui:panel extended="<%= false %>" id="screenshotsPanel" persistState="<%= true %>" title="screenshots">
			<table class="lfr-table">

			<%
			for (int i = 0; i < screenshotsCount; i++) {
				SCProductScreenshot productScreenshot = null;

				if (i < productScreenshots.size()) {
					productScreenshot = (SCProductScreenshot)productScreenshots.get(i);
				}
			%>

				<tr>
					<td>
						<liferay-ui:message key="thumbnail" />
					</td>
					<td>
						<input class="lfr-input-text" name="<portlet:namespace />thumbnail<%= i %>" type="file" />
					</td>

					<c:if test="<%= productScreenshot != null %>">
						<td class="lfr-top" rowspan="3">
							<table class="lfr-table">
							<tr>
								<td>
									<aui:a href='<%= themeDisplay.getPathImage() + "/software_catalog?img_id=" + productScreenshot.getThumbnailId() + "&t=" + WebServerServletTokenUtil.getToken(productScreenshot.getThumbnailId()) %>' target="_blank"><liferay-ui:message key="see-thumbnail" /></aui:a>
								</td>
								<td>
									<aui:a href='<%= themeDisplay.getPathImage() + "/software_catalog?img_id=" + productScreenshot.getFullImageId() + "&t=" + WebServerServletTokenUtil.getToken(productScreenshot.getFullImageId()) %>' target="_blank"><liferay-ui:message key="see-full-image" /></aui:a>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<liferay-ui:message key="use-existing-images" /> <liferay-ui:input-checkbox param='<%= "preserveScreenshot" + i %>' defaultValue="<%= true %>" />
								</td>
							</tr>
						</table>
						</td>
					</c:if>
				</tr>
				<tr>
					<td>
						<liferay-ui:message key="full-image" />
					</td>
					<td>
						<input class="lfr-input-text" name="<portlet:namespace />fullImage<%= i %>" type="file" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<br />
					</td>
				</tr>

			<%
			}
			%>

			</table>

			<input onClick="<portlet:namespace />addScreenShot();" type="button" value="<liferay-ui:message key="add-screenshot" />" />

			<c:if test="<%= screenshotsCount > 0 %>">
				<input onClick="<portlet:namespace />removeScreenShot();" type="button" value="<liferay-ui:message key="remove-screenshot" />" />
			</c:if>
		</liferay-ui:panel>
	</liferay-ui:panel-container>
</div>

<input type="submit" value="<liferay-ui:message key="save" />" />

<input onClick="location.href = '<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>';" type="button" value="<liferay-ui:message key="cancel" />" />

</form>

<aui:script>
	function <portlet:namespace />addScreenShot() {
		document.<portlet:namespace />fm.<portlet:namespace />screenshotsCount.value = "<%= screenshotsCount + 1 %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />removeScreenShot() {
		document.<portlet:namespace />fm.<portlet:namespace />screenshotsCount.value = "<%= screenshotsCount - 1 %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />saveProductEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (productEntry == null) ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%
if (productEntry != null) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/software_catalog/view_product");
	portletURL.setParameter("redirect", currentURL);
	portletURL.setParameter("productEntryId", String.valueOf(productEntry.getProductEntryId()));

	PortalUtil.addPortletBreadcrumbEntry(request, productEntry.getName(), portletURL.toString());
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-page"), currentURL);
}
%>