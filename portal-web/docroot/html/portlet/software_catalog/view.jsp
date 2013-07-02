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
String tabs1 = ParamUtil.getString(request, "tabs1", "products");

String tabs1Values = "products";

if (themeDisplay.isSignedIn()) {
	tabs1Values += ",my_products";
}

boolean hasAddLicensePermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_LICENSE);

if (hasAddLicensePermission) {
	tabs1Values += ",licenses";
}

if (SCPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_FRAMEWORK_VERSION)) {
	tabs1Values += ",framework_versions";
}

String tabs1Names = StringUtil.replace(tabs1Values, StringPool.UNDERLINE, StringPool.DASH);

String keywords = ParamUtil.getString(request, "keywords");
String type = ParamUtil.getString(request, "type");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/software_catalog/view");
portletURL.setParameter("tabs1", tabs1);
%>

<form action="<%= HtmlUtil.escape(portletURL.toString()) %>" method="get" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
<liferay-portlet:renderURLParams varImpl="portletURL" />

<liferay-ui:tabs
	names="<%= tabs1Names %>"
	portletURL="<%= portletURL %>"
	tabsValues="<%= tabs1Values %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("products") %>'>

		<%
		portletURL.setParameter("type", type);

		String orderByCol = ParamUtil.getString(request, "orderByCol");
		String orderByType = ParamUtil.getString(request, "orderByType");

		if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
			portalPreferences.setValue(PortletKeys.SOFTWARE_CATALOG, "product-entries-order-by-col", orderByCol);
			portalPreferences.setValue(PortletKeys.SOFTWARE_CATALOG, "product-entries-order-by-type", orderByType);
		}
		else {
			orderByCol = portalPreferences.getValue(PortletKeys.SOFTWARE_CATALOG, "product-entries-order-by-col", "modified-date");
			orderByType = portalPreferences.getValue(PortletKeys.SOFTWARE_CATALOG, "product-entries-order-by-type", "desc");
		}

		List<String> headerNames = new ArrayList<String>();

		headerNames.add("name");
		headerNames.add("version");
		headerNames.add("type");
		headerNames.add("tags");
		headerNames.add("licenses");
		headerNames.add("modified-date");
		headerNames.add(StringPool.BLANK);

		Map orderableHeaders = new HashMap();

		orderableHeaders.put("name", "name");
		orderableHeaders.put("version", "version");
		orderableHeaders.put("type", "type");
		orderableHeaders.put("modified-date", "modified-date");

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, LanguageUtil.get(pageContext, "no-products-were-found"));

		searchContainer.setOrderableHeaders(orderableHeaders);
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);

		Indexer indexer = IndexerRegistryUtil.getIndexer(SCProductEntry.class);

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setKeywords(keywords);

		List results = indexer.search(searchContext).toList();

		DocumentComparator docComparator = new DocumentComparator();

		boolean ascending = true;

		if (orderByType.equals("desc")) {
			ascending = false;
		}

		if (orderByCol.equals("version")) {
			docComparator.addOrderBy("version", ascending);
			docComparator.addOrderBy(Field.MODIFIED_DATE);
			docComparator.addOrderBy(Field.TITLE);
			docComparator.addOrderBy("type");
		}
		else if (orderByCol.equals("modified-date")) {
			docComparator.addOrderBy(Field.MODIFIED_DATE, ascending);
			docComparator.addOrderBy(Field.TITLE);
			docComparator.addOrderBy("version");
			docComparator.addOrderBy("type");
		}
		else if (orderByCol.equals("type")) {
			docComparator.addOrderBy("type", ascending);
			docComparator.addOrderBy(Field.MODIFIED_DATE);
			docComparator.addOrderBy(Field.TITLE);
			docComparator.addOrderBy("version");
		}
		else {
			docComparator.addOrderBy(Field.TITLE, ascending);
			docComparator.addOrderBy("version");
			docComparator.addOrderBy(Field.MODIFIED_DATE);
			docComparator.addOrderBy("type");
		}

		results = ListUtil.sort(results, docComparator);

		int total = results.size();

		searchContainer.setTotal(total);

		results = ListUtil.subList(results, searchContainer.getStart(), searchContainer.getEnd());

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			Document doc = (Document)results.get(i);

			long productEntryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			SCProductEntry productEntry = SCProductEntryLocalServiceUtil.getProductEntry(productEntryId);

			productEntry = productEntry.toEscapedModel();

			SCProductVersion latestProductVersion = productEntry.getLatestVersion();

			if (latestProductVersion != null) {
				latestProductVersion = latestProductVersion.toEscapedModel();
			}

			ResultRow row = new ResultRow(productEntry, productEntryId, i);

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/software_catalog/view_product_entry");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("productEntryId", String.valueOf(productEntryId));

			// Name and short description

			StringBundler sb = new StringBundler(5);

			sb.append("<strong>");
			sb.append(productEntry.getName());
			sb.append("</strong>");

			if (Validator.isNotNull(productEntry.getShortDescription())) {
				sb.append("<br />");
				sb.append(productEntry.getShortDescription());
			}

			row.addText(sb.toString(), rowURL);

			// Version

			if (latestProductVersion != null) {
				row.addText(latestProductVersion.getVersion(), rowURL);
			}
			else {
				row.addText(LanguageUtil.get(pageContext, "not-available"), rowURL);
			}

			// Type

			row.addText(LanguageUtil.get(pageContext, productEntry.getType()), rowURL);

			// Tags

			row.addText(LanguageUtil.get(pageContext, productEntry.getTags()), rowURL);

			// Licenses

			List<SCLicense> licenses = productEntry.getLicenses();

			if (licenses.isEmpty()) {
				row.addText(StringPool.BLANK, rowURL);
			}
			else {
				sb = new StringBundler(licenses.size() * 2);

				for (SCLicense license : licenses) {
					license = license.toEscapedModel();

					sb.append(license.getName());
					sb.append(", ");
				}

				sb.setIndex(sb.index() - 1);

				row.addText(sb.toString(), rowURL);
			}

			// Modified date

			row.addText(dateFormatDateTime.format(productEntry.getModifiedDate()), rowURL);

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/software_catalog/product_entry_action.jsp");

			// Add result row

			resultRows.add(row);
		}

		boolean showAddProductEntryButton = SCPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_PRODUCT_ENTRY);
		boolean showPermissionsButton = SCPermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
		%>

		<div>
			<label for="<portlet:namespace />keyword"><liferay-ui:message key="search" /></label>

			<input id="<portlet:namespace />keyword" name="<portlet:namespace />keywords" size="30" type="text" value="<%= HtmlUtil.escape(keywords) %>" />

			<select name="<portlet:namespace />type">
				<option value=""></option>

				<%
				for (String supportedType : PluginPackageUtil.getSupportedTypes()) {
				%>

					<option <%= type.equals(supportedType) ? "selected" : "" %> value="<%= supportedType %>"><liferay-ui:message key='<%= supportedType + "-plugin" %>' /></option>

				<%
				}
				%>

			</select>

			<input type="submit" value="<liferay-ui:message key="search" />" />
		</div>

		<br />

		<c:if test="<%= showAddProductEntryButton && showPermissionsButton %>">
			<div>
				<c:if test="<%= showAddProductEntryButton %>">
					<input onClick="<portlet:namespace />addProduct();" type="button" value="<liferay-ui:message key="add-product" />" />
				</c:if>

				<c:if test="<%= showPermissionsButton %>">
					<liferay-security:permissionsURL
						modelResource="com.liferay.portlet.softwarecatalog"
						modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>"
						resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
						var="permissionsURL"
					/>

					<input onClick="location.href = '<%= permissionsURL %>';" type="button" value="<liferay-ui:message key="permissions" />" />
				</c:if>
			</div>

			<br />
		</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("my_products") %>'>

		<%
		String orderByCol = ParamUtil.getString(request, "orderByCol");
		String orderByType = ParamUtil.getString(request, "orderByType");

		if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
			portalPreferences.setValue(PortletKeys.SOFTWARE_CATALOG, "product-entries-order-by-col", orderByCol);
			portalPreferences.setValue(PortletKeys.SOFTWARE_CATALOG, "product-entries-order-by-type", orderByType);
		}
		else {
			orderByCol = portalPreferences.getValue(PortletKeys.SOFTWARE_CATALOG, "product-entries-order-by-col", "modified-date");
			orderByType = portalPreferences.getValue(PortletKeys.SOFTWARE_CATALOG, "product-entries-order-by-type", "desc");
		}

		OrderByComparator orderByComparator = SCUtil.getProductEntryOrderByComparator(orderByCol, orderByType);

		List<String> headerNames = new ArrayList<String>();

		headerNames.add("name");
		headerNames.add("version");
		headerNames.add("type");
		headerNames.add("tags");
		headerNames.add("licenses");
		headerNames.add("modified-date");
		headerNames.add(StringPool.BLANK);

		Map orderableHeaders = new HashMap();

		orderableHeaders.put("name", "name");
		orderableHeaders.put("type", "type");
		orderableHeaders.put("modified-date", "modified-date");

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

		searchContainer.setOrderableHeaders(orderableHeaders);
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);

		int total = 0;

		if (tabs1.equals("products")) {
			total = SCProductEntryLocalServiceUtil.getProductEntriesCount(scopeGroupId);
		}
		else {
			total = SCProductEntryLocalServiceUtil.getProductEntriesCount(scopeGroupId, user.getUserId());
		}

		searchContainer.setTotal(total);

		List results = null;

		if (tabs1.equals("products")) {
			results = SCProductEntryLocalServiceUtil.getProductEntries(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd(), orderByComparator);
		}
		else {
			results = SCProductEntryLocalServiceUtil.getProductEntries(scopeGroupId, user.getUserId(), searchContainer.getStart(), searchContainer.getEnd(), orderByComparator);
		}

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			SCProductEntry productEntry = (SCProductEntry)results.get(i);

			productEntry = productEntry.toEscapedModel();

			long productEntryId = productEntry.getProductEntryId();

			SCProductVersion latestProductVersion = productEntry.getLatestVersion();

			if (latestProductVersion != null) {
				latestProductVersion = latestProductVersion.toEscapedModel();
			}

			ResultRow row = new ResultRow(productEntry, productEntryId, i);

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/software_catalog/view_product_entry");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("productEntryId", String.valueOf(productEntryId));

			// Name and short description

			StringBundler sb = new StringBundler(5);

			sb.append("<strong>");
			sb.append(productEntry.getName());
			sb.append("</strong>");

			if (Validator.isNotNull(productEntry.getShortDescription())) {
				sb.append("<br />");
				sb.append(productEntry.getShortDescription());
			}

			row.addText(sb.toString(), rowURL);

			// Version

			if (latestProductVersion != null) {
				row.addText(latestProductVersion.getVersion(), rowURL);
			}
			else {
				row.addText(LanguageUtil.get(pageContext, "not-available"), rowURL);
			}

			// Type

			row.addText(LanguageUtil.get(pageContext, productEntry.getType()), rowURL);

			// Tags

			row.addText(LanguageUtil.get(pageContext, productEntry.getTags()), rowURL);

			// Licenses

			List<SCLicense> licenses = productEntry.getLicenses();

			if (licenses.isEmpty()) {
				row.addText(StringPool.BLANK, rowURL);
			}
			else {
				sb = new StringBundler(licenses.size() * 2);

				for (SCLicense license : licenses) {
					license = license.toEscapedModel();

					sb.append(license.getName());
					sb.append(", ");
				}

				sb.setIndex(sb.index() - 1);

				row.addText(sb.toString(), rowURL);
			}

			// Modified date

			row.addText(dateFormatDateTime.format(productEntry.getModifiedDate()), rowURL);

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/software_catalog/product_entry_action.jsp");

			// Add result row

			resultRows.add(row);
		}

		boolean showAddProductEntryButton = SCPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_PRODUCT_ENTRY);
		%>

		<c:if test="<%= showAddProductEntryButton %>">
			<div>
				<input onClick="location.href = '<portlet:renderURL><portlet:param name="struts_action" value="/software_catalog/edit_product_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>';" type="button" value="<liferay-ui:message key="add-product" />" />
			</div>

			<br />
		</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("framework_versions") %>'>

		<%
		List<String> headerNames = new ArrayList<String>();

		headerNames.add("name");
		headerNames.add("url");
		headerNames.add("active");
		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

		int total = SCFrameworkVersionLocalServiceUtil.getFrameworkVersionsCount(scopeGroupId);

		searchContainer.setTotal(total);

		List results = SCFrameworkVersionLocalServiceUtil.getFrameworkVersions(scopeGroupId, searchContainer.getStart(),searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			SCFrameworkVersion frameworkVersion = (SCFrameworkVersion)results.get(i);

			frameworkVersion = frameworkVersion.toEscapedModel();

			ResultRow row = new ResultRow(frameworkVersion, frameworkVersion.getFrameworkVersionId(), i);

			String rowHREF = frameworkVersion.getUrl();

			TextSearchEntry rowTextEntry = new TextSearchEntry();

			rowTextEntry.setHref(rowHREF);
			rowTextEntry.setName(frameworkVersion.getName());
			rowTextEntry.setTarget("_blank");
			rowTextEntry.setTitle(frameworkVersion.getName());

			// Name

			row.addText(rowTextEntry);

			// URL

			rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

			rowTextEntry.setName(frameworkVersion.getUrl());

			row.addText(rowTextEntry);

			// Active

			rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

			rowTextEntry.setName(LanguageUtil.get(pageContext,frameworkVersion.isActive() ? "yes" : "no"));

			row.addText(rowTextEntry);

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/software_catalog/framework_version_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<%
		boolean showAddFrameworkVersionButton = SCPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_FRAMEWORK_VERSION);
		boolean showPermissionsButton = SCPermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
		%>

		<c:if test="<%= showAddFrameworkVersionButton || showPermissionsButton %>">
			<div>
				<c:if test="<%= showAddFrameworkVersionButton %>">
					<input onClick="location.href = '<portlet:renderURL><portlet:param name="struts_action" value="/software_catalog/edit_framework_version" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>';" type="button" value="<liferay-ui:message key="add-framework-version" />" />
				</c:if>

				<c:if test="<%= showPermissionsButton %>">
					<liferay-security:permissionsURL
						modelResource="com.liferay.portlet.softwarecatalog"
						modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>"
						resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
						var="permissionsURL"
					/>

					<input onClick="location.href = '<%= permissionsURL %>';" type="button" value="<liferay-ui:message key="permissions" />" />
				</c:if>
			</div>

			<br />
		</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("licenses") %>'>

		<%
		List<String> headerNames = new ArrayList<String>();

		headerNames.add("name");
		headerNames.add("url");
		headerNames.add("open-source");
		headerNames.add("active");
		headerNames.add("recommended");
		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

		int total = SCLicenseLocalServiceUtil.getLicensesCount();

		searchContainer.setTotal(total);

		List results = SCLicenseLocalServiceUtil.getLicenses(searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			SCLicense license = (SCLicense)results.get(i);

			license = license.toEscapedModel();

			ResultRow row = new ResultRow(license, license.getLicenseId(), i);

			String rowHREF = license.getUrl();

			TextSearchEntry rowTextEntry = new TextSearchEntry();

			rowTextEntry.setHref(rowHREF);
			rowTextEntry.setName(license.getName());
			rowTextEntry.setTarget("_blank");
			rowTextEntry.setTitle(license.getName());

			// Name

			row.addText(rowTextEntry);

			// URL

			rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

			rowTextEntry.setName(license.getUrl());

			row.addText(rowTextEntry);

			// Open source

			rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

			rowTextEntry.setName(LanguageUtil.get(pageContext, license.isOpenSource() ? "yes" : "no"));

			row.addText(rowTextEntry);

			// Active

			rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

			rowTextEntry.setName(LanguageUtil.get(pageContext, license.isActive() ? "yes" : "no"));

			row.addText(rowTextEntry);

			// Recommended

			rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

			rowTextEntry.setName(LanguageUtil.get(pageContext, license.isRecommended() ? "yes" : "no"));

			row.addText(rowTextEntry);

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/software_catalog/license_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<c:if test="<%= hasAddLicensePermission %>">
			<div>
				<input onClick="location.href = '<portlet:renderURL><portlet:param name="struts_action" value="/software_catalog/edit_license" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>';" type="button" value="<liferay-ui:message key="add-license" />" />
			</div>

			<br />
		</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</c:when>
</c:choose>

</form>

<aui:script>
	function <portlet:namespace />addProduct() {
		var url = '<portlet:renderURL><portlet:param name="struts_action" value="/software_catalog/edit_product_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>';

		if (document.<portlet:namespace />fm.<portlet:namespace />keywords) {
			url += '&<portlet:namespace />name=' + document.<portlet:namespace />fm.<portlet:namespace />keywords.value;
		}

		document.<portlet:namespace />fm.method = 'post';

		submitForm(document.<portlet:namespace />fm, url);
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />keywords);
	</c:if>
</aui:script>

<%
if (!tabs1.equals("products")) {
	PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, StringUtil.replace(tabs1, StringPool.UNDERLINE, StringPool.DASH)), request);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, TextFormatter.format(tabs1, TextFormatter.O)), portletURL.toString());
}
%>