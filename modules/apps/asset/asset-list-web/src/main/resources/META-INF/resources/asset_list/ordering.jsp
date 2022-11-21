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

<liferay-frontend:fieldset
	disabled="<%= editAssetListDisplayContext.isLiveGroup() %>"
>
	<clay:row
		id='<%= liferayPortletResponse.getNamespace() + "ordering" %>'
	>
		<clay:col
			md="6"
		>

			<%
			String orderByColumn1 = editAssetListDisplayContext.getOrderByColumn1();
			%>

			<div class="h5"><liferay-ui:message key="order-by" /></div>

			<aui:select label="" name="TypeSettingsProperties--orderByColumn1--" wrapperCssClass="d-inline-flex">
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

			<div class="d-inline-flex order-by-type-container">
				<liferay-ui:icon
					cssClass='<%= StringUtil.equalsIgnoreCase(orderByType1, "DESC") ? "hide icon" : "icon" %>'
					icon="order-list-up"
					linkCssClass="btn btn-outline-borderless btn-outline-secondary"
					markupView="lexicon"
					message="descending"
					url="javascript:void(0);"
				/>

				<liferay-ui:icon
					cssClass='<%= StringUtil.equalsIgnoreCase(orderByType1, "ASC") ? "hide icon" : "icon" %>'
					icon="order-list-down"
					linkCssClass="btn btn-outline-borderless btn-outline-secondary"
					markupView="lexicon"
					message="ascending"
					url="javascript:void(0);"
				/>

				<aui:input cssClass="order-by-type-field" name="TypeSettingsProperties--orderByType1--" type="hidden" value="<%= orderByType1 %>" />
			</div>
		</clay:col>

		<clay:col
			md="6"
		>

			<%
			String orderByColumn2 = editAssetListDisplayContext.getOrderByColumn2();
			%>

			<div class="h5"><liferay-ui:message key="and-then-by" /></div>

			<aui:select label="" name="TypeSettingsProperties--orderByColumn2--" wrapperCssClass="d-inline-flex">
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

			<div class="d-inline-flex order-by-type-container">
				<liferay-ui:icon
					cssClass='<%= StringUtil.equalsIgnoreCase(orderByType2, "DESC") ? "hide icon" : "icon" %>'
					icon="order-list-up"
					linkCssClass="btn btn-outline-borderless btn-outline-secondary"
					markupView="lexicon"
					message="descending"
					url="javascript:void(0);"
				/>

				<liferay-ui:icon
					cssClass='<%= StringUtil.equalsIgnoreCase(orderByType2, "ASC") ? "hide icon" : "icon" %>'
					icon="order-list-down"
					linkCssClass="btn btn-outline-borderless btn-outline-secondary"
					markupView="lexicon"
					message="ascending"
					url="javascript:void(0);"
				/>

				<aui:input cssClass="order-by-type-field" name="TypeSettingsProperties--orderByType2--" type="hidden" value="<%= orderByType2 %>" />
			</div>
		</clay:col>
	</clay:row>
</liferay-frontend:fieldset>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"iconCssClass", ".icon"
		).put(
			"orderingContainerId", liferayPortletResponse.getNamespace() + "ordering"
		).build()
	%>'
	module="js/Ordering"
/>