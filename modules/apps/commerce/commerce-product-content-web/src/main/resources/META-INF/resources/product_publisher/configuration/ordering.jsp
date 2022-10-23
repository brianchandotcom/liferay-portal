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
CPPublisherConfigurationDisplayContext cpPublisherConfigurationDisplayContext = (CPPublisherConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:row
	id="ordering"
>
	<clay:col
		size="6"
	>
		<aui:select label="order-by" name="preferences--orderByColumn1--" value="<%= cpPublisherConfigurationDisplayContext.getOrderByColumn1() %>" wrapperCssClass="field-inline w90">
			<aui:option label="title" />
			<aui:option label="create-date" value="createDate" />
			<aui:option label="modified-date" value="modifiedDate" />
			<aui:option label="publish-date" value="publishDate" />
			<aui:option label="expiration-date" value="expirationDate" />
		</aui:select>

		<%
		String orderByType1 = cpPublisherConfigurationDisplayContext.getOrderByType1();
		%>

		<aui:field-wrapper cssClass="field-label-inline order-by-type-container">
			<liferay-ui:icon
				cssClass='<%= StringUtil.equalsIgnoreCase(orderByType1, "DESC") ? "hide icon" : "icon" %>'
				icon="angle-up"
				markupView="lexicon"
				message="ascending"
				url="javascript:void(0);"
			/>

			<liferay-ui:icon
				cssClass='<%= StringUtil.equalsIgnoreCase(orderByType1, "ASC") ? "hide icon" : "icon" %>'
				icon="angle-down"
				markupView="lexicon"
				message="descending"
				url="javascript:void(0);"
			/>

			<aui:input cssClass="order-by-type-field" name="preferences--orderByType1--" type="hidden" value="<%= orderByType1 %>" />
		</aui:field-wrapper>
	</clay:col>

	<clay:col
		size="6"
	>

		<%
		String orderByColumn2 = cpPublisherConfigurationDisplayContext.getOrderByColumn2();
		%>

		<aui:select label="and-then-by" name="preferences--orderByColumn2--" wrapperCssClass="field-inline w90">
			<aui:option label="title" selected='<%= orderByColumn2.equals("title") %>' />
			<aui:option label="create-date" selected='<%= orderByColumn2.equals("createDate") %>' value="createDate" />
			<aui:option label="modified-date" selected='<%= orderByColumn2.equals("modifiedDate") %>' value="modifiedDate" />
			<aui:option label="publish-date" selected='<%= orderByColumn2.equals("publishDate") %>' value="publishDate" />
			<aui:option label="expiration-date" selected='<%= orderByColumn2.equals("expirationDate") %>' value="expirationDate" />
		</aui:select>

		<%
		String orderByType2 = cpPublisherConfigurationDisplayContext.getOrderByType2();
		%>

		<aui:field-wrapper cssClass="field-label-inline order-by-type-container">
			<liferay-ui:icon
				cssClass='<%= StringUtil.equalsIgnoreCase(orderByType2, "DESC") ? "hide icon" : "icon" %>'
				icon="angle-up"
				markupView="lexicon"
				message="ascending"
				url="javascript:void(0);"
			/>

			<liferay-ui:icon
				cssClass='<%= StringUtil.equalsIgnoreCase(orderByType2, "ASC") ? "hide icon" : "icon" %>'
				icon="angle-down"
				markupView="lexicon"
				message="descending"
				url="javascript:void(0);"
			/>

			<aui:input cssClass="order-by-type-field" name="preferences--orderByType2--" type="hidden" value="<%= orderByType2 %>" />
		</aui:field-wrapper>
	</clay:col>
</clay:row>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />ordering').delegate(
		'click',
		(event) => {
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