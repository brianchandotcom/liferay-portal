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

<%@ include file="/asset_categories_selector/init.jsp" %>

<%
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-asset:asset-categories-selector:data");

String id = (String)data.get("id");
String inputName = (String)data.get("inputName");
List<Map<String, Object>> vocabularies = (List<Map<String, Object>>)data.get("vocabularies");
%>

<div>
	<div id="<%= id %>">

		<%
		for (Map<String, Object> vocabulary : vocabularies) {
			String vocabularyId = GetterUtil.getString(vocabulary.get("id"));
		%>

			<div class="field-content">
				<div class="form-group" id="<%= "namespace_assetCategoriesSelector_" + vocabularyId %>">
					<c:if test='<%= Validator.isNotNull(vocabulary.get("title")) %>'>
						<label>
							<%= vocabulary.get("title") %>

							<c:if test='<%= Validator.isNotNull(vocabulary.get("group")) %>'>
								<%= StringPool.BLANK + "(" + vocabulary.get("group") + ")" %>
							</c:if>

							<%
							boolean required = GetterUtil.getBoolean(vocabulary.get("required"));
							%>

							<c:if test="<%= required %>">
								<span class="reference-mark">
									<clay:icon
										symbol="asterisk"
									/>

									<span class="hide-accessible">
										<liferay-ui:message key="required" />
									</span>
								</span>
							</c:if>
						</label>
					</c:if>

					<div class="input-group">
						<div class="input-group-item">
							<div class="form-control form-control-tag-group input-group">
								<div class="input-group-item">

									<%
									List<Map<String, Object>> selectedItems = (List<Map<String, Object>>)vocabulary.get("selectedItems");
									%>

									<c:if test="<%= Validator.isNotNull(selectedItems) %>">

										<%
										for (Map<String, Object> selectedItem : selectedItems) {
											String selectedItemLabel = GetterUtil.getString(selectedItem.get("label"));
											String selectedItemValue = GetterUtil.getString(selectedItem.get("value"));
										%>

											<clay:label
												closeable="<%= true %>"
												label="<%= selectedItemLabel %>"
											/>

											<input name="<%= inputName %>" type="hidden" value="<%= selectedItemValue %>" />

										<%
										}
										%>

									</c:if>

									<input class="form-control-inset" type="text" value="" />
								</div>
							</div>
						</div>

						<div class="input-group-item input-group-item-shrink">
							<button class="btn btn-secondary" type="button">
								<liferay-ui:message key="select" />
							</button>
						</div>
					</div>
				</div>
			</div>

		<%
		}
		%>

	</div>

	<react:component
		data="<%= data %>"
		module="asset_categories_selector/AssetCategoriesSelectorTag.es"
	/>
</div>