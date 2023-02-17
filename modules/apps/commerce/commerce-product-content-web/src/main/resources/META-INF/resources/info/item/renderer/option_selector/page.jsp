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

<%@ include file="/info/item/renderer/option_selector/init.jsp" %>

<form data-senna-off="true" name="fm">
	<%= cpContentHelper.renderOptions(request, PipingServletResponseFactory.createPipingServletResponse(pageContext)) %>
</form>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"accountId", accountId
		).put(
			"channelId", channelId
		).put(
			"cpDefinitionId", cpDefinitionId
		).put(
			"namespace", namespace
		).put(
			"productId", productId
		).build()
	%>'
	module="info/item/renderer/option_selector/js/OptionSelector"
/>