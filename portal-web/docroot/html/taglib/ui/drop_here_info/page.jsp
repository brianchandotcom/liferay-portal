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

<%@ include file="/html/taglib/ui/drop_here_info/init.jsp" %>

<%
String message = GetterUtil.getString((String)request.getAttribute("liferay-ui:drop-here-info:message"));
%>

<div class="drop-here-info">
	<div class="drop-here-indicator">
		<div class="drop-icons">
			<aui:icon cssClass="drop-icon" image="picture" markupView="lexicon" />

			<aui:icon cssClass="drop-icon" image="picture" markupView="lexicon" />

			<aui:icon cssClass="drop-icon" image="picture" markupView="lexicon" />
		</div>

		<div class="drop-text">
			<liferay-ui:message key="<%= message %>" />
		</div>
	</div>
</div>