<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:form cssClass="container-fluid container-fluid-max-xl" name="fm">
	<liferay-ui:search-container
		iteratorURL="<%= currentURLObj %>"
		total="<%= CompanyLocalServiceUtil.getCompaniesCount() %>"
	>
		<liferay-ui:search-container-results
			results="<%= CompanyLocalServiceUtil.getCompanies(searchContainer.getStart(), searchContainer.getEnd()) %>"
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