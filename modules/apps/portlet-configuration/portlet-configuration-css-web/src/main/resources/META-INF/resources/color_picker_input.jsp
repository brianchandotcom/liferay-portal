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
String color = ParamUtil.getString(request, "color");
String label = ParamUtil.getString(request, "label");
String name = ParamUtil.getString(request, "name");
%>

<div>
	<div class="form-group">
		<input name="<%= name %>" type="hidden" value="#<%= color %>" />

		<div class="clay-color-picker">
			<label><%= label %></label>

			<div class="clay-color input-group">
				<div class="input-group-item input-group-item-shrink input-group-prepend">
					<div class="input-group-text">
						<button class="btn clay-color-btn dropdown-toggle" style="border-width: 0px; height: 28px; width: 28px;" title="<%= color %>" type="button" />
					</div>
				</div>

				<div class="input-group-append input-group-item">
					<input class="form-control input-group-inset input-group-inset-before" type="text" />

					<label class="input-group-inset-item input-group-inset-item-before"><%= color %></label>
				</div>
			</div>
		</div>
	</div>

	<%
	Map<String, Object> data = new HashMap<>();

	data.put("color", color);
	data.put("label", label);
	data.put("name", name);
	%>

	<react:component
		data="<%= data %>"
		module="js/ColorPickerInput.es"
		servletContext="<%= application %>"
	/>
</div>