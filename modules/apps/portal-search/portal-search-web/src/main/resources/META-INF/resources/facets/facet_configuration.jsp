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
%>

<aui:fieldset>
	<aui:input label="display-facet" name='<%= searchFacet.getClassName() + "displayFacet" %>' type="checkbox" value="<%= !searchFacet.isStatic() %>" />

	<div class="facet-configuration-options" id="<portlet:namespace /><%= AUIUtil.normalizeId(searchFacet.getClassName()) %>Options">
		<aui:input label="weight" name='<%= searchFacet.getClassName() + "weight" %>' value="<%= searchFacet.getWeight() %>" />

		<%
		searchFacet.includeConfiguration(request, PipingServletResponse.createPipingServletResponse(pageContext));
		%>

	</div>
</aui:fieldset>

<aui:script>
	Liferay.Util.toggleBoxes(
		'<portlet:namespace /><%= AUIUtil.normalizeId(searchFacet.getClassName()) %>displayFacet',
		'<portlet:namespace /><%= AUIUtil.normalizeId(searchFacet.getClassName()) %>Options'
	);
</aui:script>