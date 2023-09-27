<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/asset_tags_selector/init.jsp" %>

<%
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-asset:asset-tags-selector:data");

List<Map<String, String>> selectedItems = (List<Map<String, String>>)data.get("selectedItems");
%>

<div>
	<div class="lfr-tags-selector-content">

		<%
		for (Map<String, String> selectedItem : selectedItems) {
		%>

			<input name="<%= (String)data.get("inputName") %>" type="hidden" value="<%= selectedItem.get("value") %>" />

		<%
		}
		%>

		<div class="form-group">
			<label for="namespace_assetTagsSelector_MultiSelect">
				<liferay-ui:message key="tags" />
			</label>

			<div class="input-group input-group-stacked-sm-down">
				<div class="input-group-item">
					<div class="form-control form-control-tag-group">

						<%
						for (Map<String, String> selectedItem : selectedItems) {
						%>

							<clay:label
								dismissible="<%= true %>"
								label='<%= HtmlUtil.escape(selectedItem.get("label")) %>'
							/>

						<%
						}
						%>

						<input class="form-control-inset" id="namespace_assetTagsSelector_MultiSelect" type="text" value="" />
					</div>
				</div>
			</div>
		</div>

		<button class="btn btn-secondary" type="button">
			<liferay-ui:message key="select" />
		</button>
	</div>

	<react:component
		module="asset_tags_selector/AssetTagsSelectorTag.es"
		props="<%= data %>"
	/>
</div>