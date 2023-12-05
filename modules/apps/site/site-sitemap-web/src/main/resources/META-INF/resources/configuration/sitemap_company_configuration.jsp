<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SitemapCompanyConfigurationDisplayContext sitemapCompanyConfigurationDisplayContext = (SitemapCompanyConfigurationDisplayContext)request.getAttribute(SitemapCompanyConfigurationDisplayContext.class.getName());
%>

<clay:content-row
	cssClass="c-mb-3"
>
	<clay:content-col>
		<span>
			<liferay-ui:message key="the-sitemap-protocol-notifies-search-engines-of-the-structure-of-the-website" />
		</span>
		<span>
			<clay:link
				href="http://www.sitemaps.org"
				label="for-more-information,-visit-www.sitemaps.org"
				target="_blank"
			/>
		</span>
	</clay:content-col>
</clay:content-row>

<clay:sheet-section role="group" aria-labelledby='<%= liferayPortletResponse.getNamespace() + "pagesTitle" %>'>
	<clay:content-row
		containerElement="h3"
		cssClass="c-mb-3 sheet-subtitle"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<span class="heading-text text-secondary" id="<portlet:namespace />pagesTitle"><liferay-ui:message key="pages" /></span>
		</clay:content-col>
	</clay:content-row>

	<clay:content-row
		cssClass="c-mt-2"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<clay:checkbox
				checked="<%= sitemapCompanyConfigurationDisplayContext.includePages() %>"
				id='<%= liferayPortletResponse.getNamespace() + "includePages" %>'
				label='<%= LanguageUtil.get(request, "include-pages-in-the-sitemap-xml") %>'
				name='<%= liferayPortletResponse.getNamespace() + "includePages" %>'
			/>

			<p class="c-mb-0 c-mt-2 small text-secondary"><liferay-ui:message key="this-configuration-affects-to-widget-pages,-collection-pages,-content-pages,-panel-pages,-and-embedded-pages" /></p>
		</clay:content-col>
	</clay:content-row>
</clay:sheet-section>

<clay:sheet-section role="group" aria-labelledby='<%= liferayPortletResponse.getNamespace() + "webContentTitle" %>'>
	<clay:content-row
		containerElement="h3"
		cssClass="c-mb-3 sheet-subtitle"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<span class="heading-text text-secondary" id="<portlet:namespace />webContentTitle"><liferay-ui:message key="web-content" /></span>
		</clay:content-col>
	</clay:content-row>

	<clay:content-row
		cssClass="c-mt-2"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<clay:checkbox
				checked="<%= sitemapCompanyConfigurationDisplayContext.includeWebContent() %>"
				id='<%= liferayPortletResponse.getNamespace() + "includeWebContent" %>'
				label='<%= LanguageUtil.get(request, "include-web-content-in-the-sitemap-xml") %>'
				name='<%= liferayPortletResponse.getNamespace() + "includeWebContent" %>'
			/>
		</clay:content-col>
	</clay:content-row>
</clay:sheet-section>

<clay:sheet-section role="group" aria-labelledby='<%= liferayPortletResponse.getNamespace() + "categoriesTitle" %>'>
	<clay:content-row
		containerElement="h3"
		cssClass="c-mb-3 sheet-subtitle"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<span class="heading-text text-secondary" id="<portlet:namespace />categoriesTitle"><liferay-ui:message key="categories" /></span>
		</clay:content-col>
	</clay:content-row>

	<clay:content-row
		cssClass="c-mt-2"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<clay:checkbox
				checked="<%= sitemapCompanyConfigurationDisplayContext.includeCategories() %>"
				id='<%= liferayPortletResponse.getNamespace() + "includeCategories" %>'
				label='<%= LanguageUtil.get(request, "include-categories-in-the-sitemap-xml") %>'
				name='<%= liferayPortletResponse.getNamespace() + "includeCategories" %>'
			/>
		</clay:content-col>
	</clay:content-row>
</clay:sheet-section>