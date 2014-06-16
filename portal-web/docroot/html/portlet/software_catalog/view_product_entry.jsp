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
String tabs2 = ParamUtil.getString(request, "tabs2", "version-history");

String redirect = ParamUtil.getString(request, "redirect");

SCProductEntry productEntry = (SCProductEntry)request.getAttribute(WebKeys.SOFTWARE_CATALOG_PRODUCT_ENTRY);

productEntry = productEntry.toEscapedModel();

long productEntryId = BeanParamUtil.getLong(productEntry, request, "productEntryId");

SCProductVersion latestProductVersion = productEntry.getLatestVersion();

PortletURL addProductVersionURL = renderResponse.createRenderURL();

addProductVersionURL.setParameter("struts_action", "/software_catalog/edit_product_version");
addProductVersionURL.setParameter(Constants.CMD, Constants.ADD);
addProductVersionURL.setParameter("tabs2", tabs2);
addProductVersionURL.setParameter("redirect", currentURL);
addProductVersionURL.setParameter("productEntryId", String.valueOf(productEntryId));

PortletURL editProductEntryURL = renderResponse.createRenderURL();

editProductEntryURL.setParameter("struts_action", "/software_catalog/edit_product_entry");
editProductEntryURL.setParameter("tabs2", tabs2);
editProductEntryURL.setParameter("redirect", currentURL);
editProductEntryURL.setParameter("productEntryId", String.valueOf(productEntryId));

PortletURL viewProductEntryURL = renderResponse.createRenderURL();

viewProductEntryURL.setParameter("struts_action", "/software_catalog/view_product_entry");
viewProductEntryURL.setParameter("tabs2", tabs2);
viewProductEntryURL.setParameter("redirect", redirect);
viewProductEntryURL.setParameter("productEntryId", String.valueOf(productEntryId));
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	escapeXml="<%= false %>"
	localizeTitle="<%= false %>"
	title='<%= productEntry.getName() + " " + ((latestProductVersion == null) ? "" : HtmlUtil.escape(latestProductVersion.getVersion())) %>'
/>

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="type" />:
	</td>
	<td>
		<liferay-ui:message key="<%= productEntry.getType() %>" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="licenses" />:
	</td>
	<td>

		<%
		List<SCLicense> productEntryLicenses = productEntry.getLicenses();

		for (int i = 0; i < productEntryLicenses.size(); i++) {
			SCLicense license = productEntryLicenses.get(i);
		%>

			<aui:a href="<%= license.getUrl() %>" target="_blank"><%= HtmlUtil.escape(license.getName()) %></aui:a><c:if test="<%= i < productEntryLicenses.size() - 1 %>">, </c:if>

		<%
		}
		%>

	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="author" />:
	</td>
	<td>
		<%= productEntry.getAuthor() %>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="page-url" />:
	</td>
	<td>
		<a href="<%= productEntry.getPageURL() %>"><%= productEntry.getPageURL() %></a>
	</td>
</tr>

<c:if test="<%= Validator.isNotNull(productEntry.getTags()) %>">
	<tr>
		<td>
			<liferay-ui:message key="tags" />:
		</td>
		<td>
			<%= productEntry.getTags() %>
		</td>
	</tr>
</c:if>

<tr>
	<td>
		<liferay-ui:message key="short-description" />:
	</td>
	<td>
		<%= productEntry.getShortDescription() %>
	</td>
</tr>

<c:if test="<%= Validator.isNotNull(productEntry.getLongDescription()) %>">
	<tr>
		<td>
			<liferay-ui:message key="long-description" />:
		</td>
		<td>
			<%= productEntry.getLongDescription() %>
		</td>
	</tr>
</c:if>

</table>

<br />

<c:choose>
	<c:when test="<%= latestProductVersion != null %>">
		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="modified-date" />:
			</td>
			<td>
				<%= dateFormatDateTime.format(latestProductVersion.getModifiedDate()) %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="change-log" />:
			</td>
			<td>
				<%= HtmlUtil.escape(latestProductVersion.getChangeLog()) %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="framework-versions" />:
			</td>
			<td>
				<%= _getFrameworkVersions(latestProductVersion.getFrameworkVersions()) %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="download-links" />:
			</td>
			<td>
				<c:if test="<%= Validator.isNotNull(latestProductVersion.getDownloadPageURL()) %>">
					<liferay-ui:icon
						iconCssClass="icon-download"
						message="download-page"
						url="<%= latestProductVersion.getDownloadPageURL() %>"
					/>
				</c:if>

				<c:if test="<%= Validator.isNotNull(latestProductVersion.getDirectDownloadURL()) %>">
					<liferay-ui:icon
						iconCssClass="icon-download"
						message="direct-download"
						url="<%= latestProductVersion.getDirectDownloadURL() %>"
					/>
				</c:if>
			</td>
		</tr>
		</table>

		<br />
	</c:when>
	<c:otherwise>
		<div class="alert alert-danger">
			<liferay-ui:message key="this-product-does-not-have-any-released-versions" />
		</div>
	</c:otherwise>
</c:choose>

<%
List productScreenshots = SCProductScreenshotLocalServiceUtil.getProductScreenshots(productEntryId);
%>

<c:if test="<%= !productScreenshots.isEmpty() %>">
	<div>

		<%
		for (int i = 0; i < productScreenshots.size(); i++) {
			SCProductScreenshot productScreenshot = (SCProductScreenshot)productScreenshots.get(i);
		%>

			<aui:a href='<%= themeDisplay.getPathImage() + "/software_catalog?img_id=" + productScreenshot.getFullImageId() + "&t=" + WebServerServletTokenUtil.getToken(productScreenshot.getFullImageId()) %>' target="_blank"><img alt="<liferay-ui:message key="screenshot" />" src="<%= themeDisplay.getPathImage() %>/software_catalog?img_id=<%= productScreenshot.getThumbnailId() %>&t=<%= WebServerServletTokenUtil.getToken(productScreenshot.getThumbnailId()) %>" /></aui:a>

		<%
		}
		%>

	</div>
</c:if>

<liferay-ui:ratings
	className="<%= SCProductEntry.class.getName() %>"
	classPK="<%= productEntry.getProductEntryId() %>"
/>

<c:if test="<%= SCProductEntryPermission.contains(permissionChecker, productEntryId, ActionKeys.UPDATE) %>">

	<div class="btn-toolbar">

		<%
		String taglibEditProductEntry = "location.href = '" + editProductEntryURL.toString() + "';";
		%>

		<aui:button onClick="<%= taglibEditProductEntry %>" value="edit-product" />

		<%
		String taglibAddProductVersion = "location.href = '" + addProductVersionURL.toString() + "';";
		%>

		<aui:button onClick="<%= taglibAddProductVersion %>" value="add-product-version" />
	</div>
</c:if>

<liferay-ui:tabs
	names="version-history,comments"
	param="tabs2"
	portletURL="<%= viewProductEntryURL %>"
/>

<c:choose>
	<c:when test='<%= PropsValues.SC_PRODUCT_COMMENTS_ENABLED && tabs2.equals("comments") %>'>
		<portlet:actionURL var="discussionURL">
			<portlet:param name="struts_action" value="/software_catalog/edit_product_entry_discussion" />
		</portlet:actionURL>

		<liferay-ui:discussion
			className="<%= SCProductEntry.class.getName() %>"
			classPK="<%= productEntry.getProductEntryId() %>"
			formAction="<%= discussionURL %>"
			redirect="<%= currentURL %>"
			userId="<%= productEntry.getUserId() %>"
		/>
	</c:when>
	<c:when test='<%= tabs2.equals("version-history") %>'>

		<%
		PortletURL viewProductVersionURL = renderResponse.createRenderURL();

		viewProductVersionURL.setParameter("struts_action", "/software_catalog/view_product_entry");
		viewProductVersionURL.setParameter("productEntryId", String.valueOf(productEntryId));

		List<String> headerNames = new ArrayList<String>();

		headerNames.add("version");
		headerNames.add("supported-framework-versions");
		headerNames.add("modified-date");
		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", SearchContainer.DEFAULT_DELTA, viewProductVersionURL, headerNames, null);

		int total = SCProductVersionServiceUtil.getProductVersionsCount(productEntryId);

		searchContainer.setTotal(total);

		List results = SCProductVersionServiceUtil.getProductVersions(productEntryId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			SCProductVersion curProductVersion = (SCProductVersion)results.get(i);

			curProductVersion = curProductVersion.toEscapedModel();

			ResultRow row = new ResultRow(curProductVersion, String.valueOf(curProductVersion.getProductVersionId()), i);

			// Name and description

			StringBundler sb = new StringBundler(6);

			sb.append("<strong>");
			sb.append(curProductVersion.getVersion());
			sb.append("</strong>");

			if (Validator.isNotNull(curProductVersion.getChangeLog())) {
				sb.append("<br />");
				sb.append(curProductVersion.getChangeLog());
			}

			sb.append("</a>");

			row.addText(sb.toString());

			row.addText(_getFrameworkVersions(curProductVersion.getFrameworkVersions()));
			row.addDate(curProductVersion.getModifiedDate());

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/software_catalog/product_version_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</c:when>
</c:choose>

<%
PortalUtil.setPageSubtitle(productEntry.getName(), request);
PortalUtil.setPageDescription(productEntry.getShortDescription(), request);
PortalUtil.setPageKeywords(productEntry.getTags(), request);

SCProductEntry unescapedProductEntry = productEntry.toUnescapedModel();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/software_catalog/view_product");
portletURL.setParameter("redirect", currentURL);
portletURL.setParameter("productEntryId", String.valueOf(productEntry.getProductEntryId()));

PortalUtil.addPortletBreadcrumbEntry(request, unescapedProductEntry.getName(), portletURL.toString());
%>

<%!
public String _getFrameworkVersions(List<SCFrameworkVersion> frameworkVersions) {
	if (frameworkVersions.isEmpty()) {
		return StringPool.BLANK;
	}

	StringBundler sb = new StringBundler(frameworkVersions.size() * 6);

	for (SCFrameworkVersion frameworkVersion : frameworkVersions) {
		frameworkVersion = frameworkVersion.toEscapedModel();

		if (Validator.isNotNull(frameworkVersion.getUrl())) {
			sb.append("<a href='");
			sb.append(frameworkVersion.getUrl());
			sb.append("'>");
			sb.append(frameworkVersion.getName());
			sb.append("</a>");
		}
		else {
			sb.append(frameworkVersion.getName());
		}

		sb.append(", ");
	}

	sb.setIndex(sb.index() - 1);

	return sb.toString();
}
%>