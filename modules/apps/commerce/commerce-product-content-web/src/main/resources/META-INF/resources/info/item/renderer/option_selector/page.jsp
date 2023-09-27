<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/info/item/renderer/option_selector/init.jsp" %>

<form data-senna-off="true" name="fm">

	<%
	cpContentHelper.renderOptions(request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
	%>

</form>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"namespace", namespace
		).build()
	%>'
	destroyOnNavigate="<%= true %>"
	module="info/item/renderer/option_selector/js/OptionSelector"
/>