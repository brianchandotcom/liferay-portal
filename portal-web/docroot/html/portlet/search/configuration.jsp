<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/search/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />
<liferay-portlet:renderURL portletConfiguration="true" varImpl="portletURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value='<%= portletURL.toString() %>' />

	<aui:fieldset>
		<aui:input name="preferences--displayAssetTypeFacet--" type="checkbox" value="<%= displayAssetTypeFacet %>" />

		<aui:input name="preferences--displayAssetTagsFacet--" type="checkbox" value="<%= displayAssetTagsFacet %>" />

		<aui:input name="preferences--displayAssetCategoriesFacet--" type="checkbox" value="<%= displayAssetCategoriesFacet %>" />

		<aui:input name="preferences--displayModifiedRangeFacet--" type="checkbox" value="<%= displayModifiedRangeFacet %>" />

		<c:if test="<%= permissionChecker.isCompanyAdmin() %>"
			<aui:input helpMessage="display-results-in-document-form-help" name="preferences--displayResultsInDocumentForm--" type="checkbox" value="<%= displayResultsInDocumentForm %>" />
		</c:if>

		<aui:input name="preferences--viewInContext--" type="checkbox" value="<%= viewInContext %>" />

		<aui:input helpMessage="display-main-query-help" name="preferences--displayMainQuery--" type="checkbox" value="<%= displayMainQuery %>" />

		<aui:input helpMessage="display-open-search-results-help" name="preferences--displayOpenSearchResults--" type="checkbox" value="<%= displayOpenSearchResults %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>