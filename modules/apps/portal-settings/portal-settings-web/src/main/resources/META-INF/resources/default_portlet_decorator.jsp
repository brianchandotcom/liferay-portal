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

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<div class="row">
	<div class="col-md-12">
		<br />

		<aui:select label="default-portlet-decorator" name='<%= "settings--" + PropsKeys.DEFAULT_PORTLET_DECORATOR_ID + "--" %>' value="<%= (String)request.getAttribute(PropsKeys.DEFAULT_PORTLET_DECORATOR_ID) %>">
			<aui:option label="barebone" value="barebone" />
			<aui:option label="borderless" value="borderless" />
			<aui:option label="decorate" value="decorate" />
		</aui:select>
	</div>
</div>