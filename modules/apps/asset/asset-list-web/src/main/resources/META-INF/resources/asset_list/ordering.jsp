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

<portlet:actionURL name="/asset_list/edit_asset_list_entry_settings" var="editAssetListEntrySettingsURL" />

<liferay-frontend:edit-form
	action="<%= editAssetListEntrySettingsURL %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="assetListEntryId" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryId() %>" />

	<liferay-frontend:edit-form-body>
		<h1 class="sheet-title">
			<liferay-ui:message key="ordering" />
		</h1>

		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<aui:row id="ordering">
					<aui:col width="<%= 50 %>">
						<aui:select label="order-by" name="TypeSettingsProperties--orderByColumn1--" wrapperCssClass="field-inline w80">
							<aui:option label="title" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn1(), "title") %>' value="title" />
							<aui:option label="create-date" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn1(), "createDate") %>' value="createDate" />
							<aui:option label="modified-date" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn1(), "modifiedDate") %>' value="modifiedDate" />
							<aui:option label="publish-date" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn1(), "publishDate") %>' value="publishDate" />
							<aui:option label="expiration-date" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn1(), "expirationDate") %>' value="expirationDate" />
							<aui:option label="priority" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn1(), "priority") %>' value="priority" />
							<aui:option label="view-count" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn1(), "viewCount") %>' value="viewCount" />
							<aui:option label="ratings" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn1(), "ratings") %>' value="ratings" />
						</aui:select>

						<aui:field-wrapper cssClass="field-label-inline order-by-type-container">
							<liferay-ui:icon
								cssClass='<%= StringUtil.equalsIgnoreCase(editAssetListDisplayContext.getOrderByType1(), "DESC") ? "hide icon" : "icon" %>'
								icon="angle-up"
								markupView="lexicon"
								message="ascending"
								url="javascript:;"
							/>

							<liferay-ui:icon
								cssClass='<%= StringUtil.equalsIgnoreCase(editAssetListDisplayContext.getOrderByType1(), "ASC") ? "hide icon" : "icon" %>'
								icon="angle-down"
								markupView="lexicon"
								message="descending"
								url="javascript:;"
							/>

							<aui:input cssClass="order-by-type-field" name="TypeSettingsProperties--orderByType1--" type="hidden" value="<%= editAssetListDisplayContext.getOrderByType1() %>" />
						</aui:field-wrapper>
					</aui:col>

					<aui:col width="<%= 50 %>">
						<aui:select label="and-then-by" name="TypeSettingsProperties--orderByColumn2--" wrapperCssClass="field-inline w80">
							<aui:option label="title" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn2(), "title") %>' value="title" />
							<aui:option label="create-date" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn2(), "createDate") %>' value="createDate" />
							<aui:option label="modified-date" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn2(), "modifiedDate") %>' value="modifiedDate" />
							<aui:option label="publish-date" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn2(), "publishDate") %>' value="publishDate" />
							<aui:option label="expiration-date" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn2(), "expirationDate") %>' value="expirationDate" />
							<aui:option label="priority" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn2(), "priority") %>' value="priority" />
							<aui:option label="view-count" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn2(), "viewCount") %>' value="viewCount" />
							<aui:option label="ratings" selected='<%= Objects.equals(editAssetListDisplayContext.getOrderByColumn2(), "ratings") %>' value="ratings" />
						</aui:select>

						<aui:field-wrapper cssClass="field-label-inline order-by-type-container">
							<liferay-ui:icon
								cssClass='<%= StringUtil.equalsIgnoreCase(editAssetListDisplayContext.getOrderByType2(), "DESC") ? "hide icon" : "icon" %>'
								icon="angle-up"
								markupView="lexicon"
								message="ascending"
								url="javascript:;"
							/>

							<liferay-ui:icon
								cssClass='<%= StringUtil.equalsIgnoreCase(editAssetListDisplayContext.getOrderByType2(), "ASC") ? "hide icon" : "icon" %>'
								icon="angle-down"
								markupView="lexicon"
								message="descending"
								url="javascript:;"
							/>

							<aui:input cssClass="order-by-type-field" name="TypeSettingsProperties--orderByType2--" type="hidden" value="<%= editAssetListDisplayContext.getOrderByType2() %>" />
						</aui:field-wrapper>
					</aui:col>
				</aui:row>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= editAssetListDisplayContext.getRedirectURL() %>" type="cancel" />
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