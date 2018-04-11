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
List<Object> rowObjects = (List<Object>)request.getAttribute("ROW_OBJECTS");
UADEntityDisplay uadEntityDisplay = (UADEntityDisplay)request.getAttribute("UAD_ENTITY_DISPLAY");
%>

<div class="sidebar sidebar-light">
	<div class="sidebar-body">
		<dl class="sidebar-dl sidebar-section">
			<c:choose>
				<c:when test="<%= ListUtil.isEmpty(rowObjects) %>">
					<div class="sidebar-header">
						<h2 class="sidebar-title"><%= uadEntityDisplay.getTypeName() %></h2>
						<h4 class="sidebar-subtitle"><%= uadEntityDisplay.getApplicationName() %></h4>
					</div>
				</c:when>
				<c:when test="<%= ListUtil.isNotEmpty(rowObjects) && (rowObjects.size() == 1) %>">

					<%
					Map<String, Object> displayValues = uadEntityDisplay.getNonanonymizableFieldValues(rowObjects.get(0));
					String identifierFieldName = uadEntityDisplay.getDisplayFieldNames()[0];
					%>

					<div class="sidebar-header">
						<h2 class="sidebar-title"><%= StringUtil.shorten(String.valueOf(displayValues.get(identifierFieldName)), 200) %></h2>

						<h4 class="sidebar-subtitle"><%= uadEntityDisplay.getTypeName() %></h4>
					</div>

					<div class="sidebar-body">
						<dl class="sidebar-dl sidebar-section">

							<%
							for (Map.Entry<String, Object> entry : displayValues.entrySet()) {
								if (identifierFieldName.equals(entry.getKey())) {
									continue;
								}
							%>

								<dt class="sidebar-dt"><%= entry.getKey() %></dt>
								<dd class="sidebar-dd"><%= StringUtil.shorten(String.valueOf(entry.getValue()), 200) %></dd>

							<%
							}
							%>

						</dl>
					</div>
				</c:when>
				<c:when test="<%= ListUtil.isNotEmpty(rowObjects) && (rowObjects.size() > 1) %>">
					<div class="sidebar-header">
						<h2 class="sidebar-title"><%= uadEntityDisplay.getTypeName() %></h2>
						<h4 class="sidebar-subtitle"><%= rowObjects.size() %> items are selected.</h4>
					</div>
				</c:when>
			</c:choose>
		</dl>
	</div>
</div>