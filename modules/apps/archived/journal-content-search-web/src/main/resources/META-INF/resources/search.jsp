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

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

if (Validator.isNotNull(redirect)) {
	portletDisplay.setURLBack(redirect);
}
else if (Validator.isNotNull(backURL)) {
	portletDisplay.setURLBack(backURL);
}
else {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletDisplay.setURLBack(portletURL.toString());
}

PortletURL portletURL = PortletURLUtil.getCurrent(renderRequest, renderResponse);

request.setAttribute("search.jsp-portletURL", portletURL);
request.setAttribute("search.jsp-returnToFullPageURL", portletDisplay.getURLBack());
%>

<portlet:renderURL var="searchURL">
	<portlet:param name="mvcPath" value="/search.jsp" />
	<portlet:param name="backURL" value="<%= backURL %>" />
	<portlet:param name="targetPortletId" value="<%= journalContentSearchPortletInstanceConfiguration.targetPortletId() %>" />
</portlet:renderURL>

<aui:form action="<%= searchURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "search(); event.preventDefault();" %>'>
	<div class="form-search">
		<liferay-ui:input-search
			name="keywords"
			placeholder='<%= LanguageUtil.get(request, "keywords") %>'
		/>
	</div>

	<div class="search-results">
		<liferay-ui:search-speed
			hits="<%= journalContentSearchDisplayContext.getHits() %>"
			searchContainer="<%= journalContentSearchDisplayContext.getSearchContainer() %>"
		/>
	</div>

	<liferay-ui:search-container
		searchContainer="<%= journalContentSearchDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.search.Document"
			keyProperty="entryClassPK"
			modelVar="document"
		>

			<%
			Summary summary = journalContentSearchDisplayContext.getSummary(document);
			%>

			<liferay-ui:search-container-column-icon
				icon="web-content"
			/>

			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
			>
				<h5>
					<%= summary.getTitle() %>
				</h5>

				<%
				Locale snippetLocale = summary.getLocale();

				String languageId = LocaleUtil.toLanguageId(snippetLocale);
				%>

				<c:if test="<%= languageId.length() > 0 %>">
					<h6 class="text-default">
						<liferay-ui:icon
							image='<%= "../language/" + languageId %>'
							message='<%= LanguageUtil.format(request, "this-result-comes-from-the-x-version-of-this-content", snippetLocale.getDisplayLanguage(locale), false) %>'
						/>
					</h6>
				</c:if>

				<c:if test="<%= Validator.isNotNull(summary.getContent()) %>">
					<h6 class="text-default">
						<%@ include file="/article_content.jspf" %>
					</h6>
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />search() {
		var keywords =
			document.<portlet:namespace />fm.<portlet:namespace />keywords.value;

		keywords = keywords.replace(/^\s+|\s+$/, '');

		if (keywords != '') {
			submitForm(document.<portlet:namespace />fm);
		}
	}
</aui:script>