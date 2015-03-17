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
SearchFacet searchFacet = (SearchFacet)request.getAttribute("facet_configuration.jsp-searchFacet");

FacetConfiguration facetConfiguration = searchFacet.getFacetConfiguration(searchDisplayContext.getSearchConfiguration());
%>

<aui:fieldset label="general-facet-configuration">
	<aui:row>
		<aui:col width="<%= 50 %>">
			<aui:input label="class-name" name='<%= searchFacet.getClassName() + "className" %>' value="<%= facetConfiguration.getClassName() %>" />

			<aui:input label="display-style" name='<%= searchFacet.getClassName() + "displayStyle" %>' value="<%= facetConfiguration.getDisplayStyle() %>" />

			<aui:input label="fieldName" name='<%= searchFacet.getClassName() + "fieldName" %>' value="<%= facetConfiguration.getFieldName() %>" />
		</aui:col>

		<aui:col width="<%= 50 %>">
			<aui:input label="label" name='<%= searchFacet.getClassName() + "label" %>' value="<%= facetConfiguration.getLabel() %>" />

			<aui:input label="order" name='<%= searchFacet.getClassName() + "order" %>' value="<%= facetConfiguration.getOrder() %>" />

			<aui:input label="weight" name='<%= searchFacet.getClassName() + "weight" %>' value="<%= facetConfiguration.getWeight() %>" />
		</aui:col>
	</aui:row>

	<aui:input label="static" name='<%= searchFacet.getClassName() + "static" %>' type="checkbox" value="<%= facetConfiguration.isStatic() %>" />
</aui:fieldset>

<c:if test="<%= Validator.isNotNull(searchFacet.getConfigurationView()) %>">
	<aui:fieldset label="specific-facet-configuration">
		<liferay-util:include page="<%= searchFacet.getConfigurationView() %>" servletContext="<%= application %>" />
	</aui:fieldset>
</c:if>