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

<%@ include file="/document_library/init.jsp" %>

<div>

	<%
	Map<String, Object> data = HashMapBuilder.<String, Object>put(
		"dlVersionNumberIncreaseValues", HashMapBuilder.<String, Object>put("MAJOR", DLVersionNumberIncrease.MAJOR)
		.put("MINOR", DLVersionNumberIncrease.MINOR)
		.put("NONE", DLVersionNumberIncrease.NONE).build()
	).put("bridgeComponentId", liferayPortletResponse.getNamespace() + "BridgeCheckinComponent"
	).put("checkedOut", GetterUtil.getBoolean(request.getAttribute("edit_file_entry.jsp-checkedOut"))).build();
	%>

	<react:component
		data="<%= data %>"
		module="document_library/js/checkin/Checkin.es"
	/>
</div>