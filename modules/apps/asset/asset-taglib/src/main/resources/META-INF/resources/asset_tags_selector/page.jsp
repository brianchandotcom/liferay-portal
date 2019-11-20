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

<%@ include file="/asset_tags_selector/init.jsp" %>

<%
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-asset:asset-tags-selector:data");

String inputName = (String)data.get("inputName");
List<Map<String, String>> selectedItems = (List<Map<String, String>>)data.get("selectedItems");
%>

<div>
	<div class="lfr-tags-selector-content">

		<%
		for (Map<String, String> selectedItem : selectedItems) {
		%>

			<input name="<%= inputName %>" type="hidden" value="<%= selectedItem.get("value") %>" />

		<%
		}
		%>

		<div class="form-group">
			<label>
				<liferay-ui:message key="tags" />
			</label>

			<div class="input-group input-group-stacked-sm-down">
				<div class="input-group-item">
					<div class="form-control form-control-tag-group">

						<%
						for (Map<String, String> selectedItem : selectedItems) {
						%>

							<clay:label
								closeable="<%= true %>"
								label='<%= selectedItem.get("label") %>'
							/>

						<%
						}
						%>

						<input class="form-control-inset" type="text" value="" />
					</div>
				</div>
			</div>
		</div>

		<button class="btn btn-secondary" type="button">
			<liferay-ui:message key="select" />
		</button>
	</div>

	<react:component
		data="<%= data %>"
		module="asset_tags_selector/AssetTagsSelectorTag.es"
	/>
</div>