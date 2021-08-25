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
FragmentCollectionFilter fragmentCollectionFilter = (FragmentCollectionFilter)request.getAttribute(FragmentCollectionFilter.class.getName());
FragmentRendererContext fragmentRendererContext = (FragmentRendererContext)request.getAttribute(FragmentRendererContext.class.getName());
%>

<c:choose>
	<c:when test="<%= fragmentCollectionFilter != null %>">
		<liferay-frontend:component
			context='<%=
				HashMapBuilder.<String, Object>put(
					"filterPrefix", FragmentCollectionFilterConstants.FILTER_PREFIX
				).build()
			%>'
			module="js/CollectionFilterRegister"
			servletContext="<%= application %>"
		/>

		<%
		fragmentCollectionFilter.render(fragmentRendererContext, request, response);
		%>

	</c:when>
	<c:otherwise>
		<clay:button
			cssClass="bg-light dropdown-toggle font-weight-bold form-control-select form-control-sm text-left w-100"
			displayType="secondary"
			label="<%= StringPool.DASH %>"
			small="<%= true %>"
		/>
	</c:otherwise>
</c:choose>