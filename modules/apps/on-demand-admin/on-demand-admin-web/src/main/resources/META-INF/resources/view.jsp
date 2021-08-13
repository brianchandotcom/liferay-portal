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

<%@ include file="/init.jsp" %>

<aui:form cssClass="container-fluid container-fluid-max-xl" name="fm">
	<liferay-ui:search-container
		iteratorURL="<%= currentURLObj %>"
		total="<%= CompanyLocalServiceUtil.getCompaniesCount(false) %>"
	>
		<liferay-ui:search-container-results
			results="<%= CompanyLocalServiceUtil.getCompanies(false, searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Company"
			modelVar="curCompany"
		>
			<liferay-ui:search-container-column-text
				name="instance-id"
				property="companyId"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				name="web-id"
				value="<%= HtmlUtil.escape(curCompany.getWebId()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				name="virtual-host"
				value="<%= curCompany.getVirtualHostname() %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				name="mail-domain"
				property="mx"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-ws-nowrap table-column-text-center"
				name="active"
				value='<%= LanguageUtil.get(request, curCompany.isActive() ? "yes" : "no") %>'
			/>

			<liferay-ui:search-container-column-jsp
				path="/actions.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			searchContainer="<%= searchContainer %>"
		/>
	</liferay-ui:search-container>
</aui:form>