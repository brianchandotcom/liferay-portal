<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/asset_categories_selector/init.jsp" %>

<%
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-asset:asset-categories-selector:data");

List<Map<String, Object>> vocabularies = (List<Map<String, Object>>)data.get("vocabularies");
%>

<div>
	<div id="<%= (String)data.get("id") %>">

		<%
		for (Map<String, Object> vocabulary : vocabularies) {
			String vocabularyId = GetterUtil.getString(vocabulary.get("id"));
		%>

			<div class="field-content">
				<div class="form-group" id="namespace_assetCategoriesSelector_<%= vocabularyId %>">
					<c:if test='<%= Validator.isNotNull(vocabulary.get("title")) %>'>
						<label for="namespace_assetCategoriesSelector_<%= vocabularyId %>_MultiSelect">
							<%= HtmlUtil.escape(GetterUtil.getString(vocabulary.get("title"))) %>

							<c:if test='<%= GetterUtil.getBoolean(vocabulary.get("required")) %>'>
								<span class="reference-mark">
									<clay:icon
										symbol="asterisk"
									/>

									<span class="hide-accessible sr-only">
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

									<c:if test="<%= selectedItems != null %>">

										<%
										for (Map<String, Object> selectedItem : selectedItems) {
										%>

											<clay:label
												dismissible="<%= true %>"
												label='<%= HtmlUtil.escape(GetterUtil.getString(selectedItem.get("label"))) %>'
											/>

											<input name="<%= (String)data.get("inputName") %>" type="hidden" value="<%= GetterUtil.getString(selectedItem.get("value")) %>" />

										<%
										}
										%>

									</c:if>

									<input class="form-control-inset" id="namespace_assetCategoriesSelector_<%= vocabularyId %>_MultiSelect" type="text" value="" />
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
		module="asset_categories_selector/AssetCategoriesSelectorTag.es"
		props="<%= data %>"
	/>
</div>