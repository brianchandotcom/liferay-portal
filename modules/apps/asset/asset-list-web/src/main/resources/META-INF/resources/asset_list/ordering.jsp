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

<liferay-frontend:fieldset-group>
	<liferay-frontend:fieldset>
		<aui:row id="ordering">
			<aui:col width="<%= 50 %>">

				<%
				String orderByColumn1 = editAssetListDisplayContext.getOrderByColumn1();
				%>

				<aui:select label="order-by" name="TypeSettingsProperties--orderByColumn1--" wrapperCssClass="field-inline w80">
					<aui:option label="title" selected='<%= Objects.equals(orderByColumn1, "title") %>' value="title" />
					<aui:option label="create-date" selected='<%= Objects.equals(orderByColumn1, "createDate") %>' value="createDate" />
					<aui:option label="modified-date" selected='<%= Objects.equals(orderByColumn1, "modifiedDate") %>' value="modifiedDate" />
					<aui:option label="publish-date" selected='<%= Objects.equals(orderByColumn1, "publishDate") %>' value="publishDate" />
					<aui:option label="expiration-date" selected='<%= Objects.equals(orderByColumn1, "expirationDate") %>' value="expirationDate" />
					<aui:option label="priority" selected='<%= Objects.equals(orderByColumn1, "priority") %>' value="priority" />
				</aui:select>

				<%
				String orderByType1 = editAssetListDisplayContext.getOrderByType1();
				%>

				<aui:field-wrapper cssClass="field-label-inline order-by-type-container">
					<liferay-ui:icon
						cssClass='<%= StringUtil.equalsIgnoreCase(orderByType1, "DESC") ? "order-arrow-up-active hide icon" : "order-arrow-up-active icon" %>'
						icon="order-arrow"
						linkCssClass="btn btn-outline-borderless btn-outline-secondary"
						markupView="lexicon"
						message="descending"
						url="javascript:;"
					/>

					<liferay-ui:icon
						cssClass='<%= StringUtil.equalsIgnoreCase(orderByType1, "ASC") ? "order-arrow-down-active hide icon" : "order-arrow-down-active icon" %>'
						icon="order-arrow"
						linkCssClass="btn btn-outline-borderless btn-outline-secondary"
						markupView="lexicon"
						message="ascending"
						url="javascript:;"
					/>

					<aui:input cssClass="order-by-type-field" name="TypeSettingsProperties--orderByType1--" type="hidden" value="<%= orderByType1 %>" />
				</aui:field-wrapper>
			</aui:col>

			<aui:col width="<%= 50 %>">

				<%
				String orderByColumn2 = editAssetListDisplayContext.getOrderByColumn2();
				%>

				<aui:select label="and-then-by" name="TypeSettingsProperties--orderByColumn2--" wrapperCssClass="field-inline w80">
					<aui:option label="title" selected='<%= Objects.equals(orderByColumn2, "title") %>' value="title" />
					<aui:option label="create-date" selected='<%= Objects.equals(orderByColumn2, "createDate") %>' value="createDate" />
					<aui:option label="modified-date" selected='<%= Objects.equals(orderByColumn2, "modifiedDate") %>' value="modifiedDate" />
					<aui:option label="publish-date" selected='<%= Objects.equals(orderByColumn2, "publishDate") %>' value="publishDate" />
					<aui:option label="expiration-date" selected='<%= Objects.equals(orderByColumn2, "expirationDate") %>' value="expirationDate" />
					<aui:option label="priority" selected='<%= Objects.equals(orderByColumn2, "priority") %>' value="priority" />
				</aui:select>

				<%
				String orderByType2 = editAssetListDisplayContext.getOrderByType2();
				%>

				<aui:field-wrapper cssClass="field-label-inline order-by-type-container">
					<liferay-ui:icon
						cssClass='<%= StringUtil.equalsIgnoreCase(orderByType2, "DESC") ? "order-arrow-up-active hide icon" : "order-arrow-up-active icon" %>'
						icon="order-arrow"
						linkCssClass="btn btn-outline-borderless btn-outline-secondary"
						markupView="lexicon"
						message="descending"
						url="javascript:;"
					/>

					<liferay-ui:icon
						cssClass='<%= StringUtil.equalsIgnoreCase(orderByType2, "ASC") ? "order-arrow-down-active hide icon" : "order-arrow-down-active icon" %>'
						icon="order-arrow"
						linkCssClass="btn btn-outline-borderless btn-outline-secondary"
						markupView="lexicon"
						message="ascending"
						url="javascript:;"
					/>

					<aui:input cssClass="order-by-type-field" name="TypeSettingsProperties--orderByType2--" type="hidden" value="<%= orderByType2 %>" />
				</aui:field-wrapper>
			</aui:col>
		</aui:row>
	</liferay-frontend:fieldset>
</liferay-frontend:fieldset-group>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />ordering').delegate(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			var orderByTypeContainer = currentTarget.ancestor(
				'.order-by-type-container'
			);

			orderByTypeContainer.all('.icon').toggleClass('hide');

			var orderByTypeField = orderByTypeContainer.one('.order-by-type-field');

			var newVal = orderByTypeField.val() === 'ASC' ? 'DESC' : 'ASC';

			orderByTypeField.val(newVal);
		},
		'.icon'
	);
</aui:script>