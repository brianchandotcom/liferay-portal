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
String redirect = editAssetListDisplayContext.getRedirectURL();
%>

<portlet:actionURL name="/asset_list/edit_asset_list_entry_settings" var="editAssetListEntrySettingsURL" />

<liferay-frontend:edit-form
	action="<%= editAssetListEntrySettingsURL %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="assetListEntryId" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryId() %>" />
	<aui:input name="type" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryType() %>" />

	<liferay-frontend:edit-form-body>
		<h3 class="sheet-subtitle">
			<span class="autofit-padded-no-gutters autofit-row">
				<span class="autofit-col autofit-col-expand">
					<span class="heading-text">
						<liferay-ui:message key="ordering" />
					</span>
				</span>
			</span>
		</h3>

		<liferay-frontend:fieldset-group>
			<aui:row id="ordering">
				<aui:col width="<%= 30 %>">

					<%
					String orderByColumn1 = editAssetListDisplayContext.getOrderByColumn1();
					%>

					<aui:select label="order-by" name="TypeSettingsProperties--orderByColumn1--" value="<%= orderByColumn1 %>" wrapperCssClass="field-inline w80">
						<aui:option label="title" />
						<aui:option label="create-date" value="createDate" />
						<aui:option label="modified-date" value="modifiedDate" />
						<aui:option label="publish-date" value="publishDate" />
						<aui:option label="expiration-date" value="expirationDate" />
						<aui:option label="priority" value="priority" />
						<aui:option label="view-count" value="viewCount" />
						<aui:option label="ratings" value="ratings" />
					</aui:select>

					<%
					String orderByType1 = editAssetListDisplayContext.getOrderByType1();
					%>

					<aui:field-wrapper cssClass="field-label-inline order-by-type-container">
						<liferay-ui:icon
							cssClass='<%= StringUtil.equalsIgnoreCase(orderByType1, "DESC") ? "hide icon" : "icon" %>'
							icon="angle-up"
							markupView="lexicon"
							message="ascending"
							url="javascript:;"
						/>

						<liferay-ui:icon
							cssClass='<%= StringUtil.equalsIgnoreCase(orderByType1, "ASC") ? "hide icon" : "icon" %>'
							icon="angle-down"
							markupView="lexicon"
							message="descending"
							url="javascript:;"
						/>

						<aui:input cssClass="order-by-type-field" name="TypeSettingsProperties--orderByType1--" type="hidden" value="<%= orderByType1 %>" />
					</aui:field-wrapper>
				</aui:col>

				<aui:col width="<%= 30 %>">

					<%
					String orderByColumn2 = editAssetListDisplayContext.getOrderByColumn2();
					%>

					<aui:select label="and-then-by" name="TypeSettingsProperties--orderByColumn2--" wrapperCssClass="field-inline w80">
						<aui:option label="title" selected='<%= orderByColumn2.equals("title") %>' />
						<aui:option label="create-date" selected='<%= orderByColumn2.equals("createDate") %>' value="createDate" />
						<aui:option label="modified-date" selected='<%= orderByColumn2.equals("modifiedDate") %>' value="modifiedDate" />
						<aui:option label="publish-date" selected='<%= orderByColumn2.equals("publishDate") %>' value="publishDate" />
						<aui:option label="expiration-date" selected='<%= orderByColumn2.equals("expirationDate") %>' value="expirationDate" />
						<aui:option label="priority" selected='<%= orderByColumn2.equals("priority") %>' value="priority" />
						<aui:option label="view-count" selected='<%= orderByColumn2.equals("viewCount") %>' value="viewCount" />
						<aui:option label="ratings" selected='<%= orderByColumn2.equals("ratings") %>' value="ratings" />
					</aui:select>

					<%
					String orderByType2 = editAssetListDisplayContext.getOrderByType2();
					%>

					<aui:field-wrapper cssClass="field-label-inline order-by-type-container">
						<liferay-ui:icon
							cssClass='<%= StringUtil.equalsIgnoreCase(orderByType2, "DESC") ? "hide icon" : "icon" %>'
							icon="angle-up"
							markupView="lexicon"
							message="ascending"
							url="javascript:;"
						/>

						<liferay-ui:icon
							cssClass='<%= StringUtil.equalsIgnoreCase(orderByType2, "ASC") ? "hide icon" : "icon" %>'
							icon="angle-down"
							markupView="lexicon"
							message="descending"
							url="javascript:;"
						/>

						<aui:input cssClass="order-by-type-field" name="TypeSettingsProperties--orderByType2--" type="hidden" value="<%= orderByType2 %>" />
					</aui:field-wrapper>
				</aui:col>
			</aui:row>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />ordering').delegate(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			var orderByTypeContainer = currentTarget.ancestor('.order-by-type-container');

			orderByTypeContainer.all('.icon').toggleClass('hide');

			var orderByTypeField = orderByTypeContainer.one('.order-by-type-field');

			var newVal = orderByTypeField.val() === 'ASC' ? 'DESC' : 'ASC';

			orderByTypeField.val(newVal);
		},
		'.icon'
	);
</aui:script>