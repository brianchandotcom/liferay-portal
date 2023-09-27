<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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