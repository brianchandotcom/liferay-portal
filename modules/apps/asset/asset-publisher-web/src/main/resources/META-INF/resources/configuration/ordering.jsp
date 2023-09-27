<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<clay:row
	id='<%= liferayPortletResponse.getNamespace() + "ordering" %>'
>
	<clay:col
		md="6"
	>
		<div class="h5"><liferay-ui:message key="order-by" /></div>

		<div class="align-items-center d-flex">
			<aui:select aria-label='<%= LanguageUtil.get(request, "order-by") %>' label="" name="preferences--orderByColumn1--" value="<%= assetPublisherDisplayContext.getOrderByColumn1() %>" wrapperCssClass="d-inline-flex m-0">
				<c:if test="<%= assetPublisherDisplayContext.isOrderingByTitleEnabled() %>">
					<aui:option label="title" />
				</c:if>

				<aui:option label="create-date" value="createDate" />
				<aui:option label="modified-date" value="modifiedDate" />
				<aui:option label="publish-date" value="publishDate" />
				<aui:option label="expiration-date" value="expirationDate" />
				<aui:option label="priority" value="priority" />

				<c:if test="<%= !assetPublisherDisplayContext.isSearchWithIndex() %>">
					<aui:option label="view-count" value="viewCount" />
					<aui:option label="ratings" value="ratings" />
				</c:if>
			</aui:select>

			<%
			String orderByType1 = assetPublisherDisplayContext.getOrderByType1();
			%>

			<div class="d-inline-flex lfr-portal-tooltip ml-1 order-by-type-container">
				<clay:button
					borderless="<%= true %>"
					cssClass='<%= StringUtil.equalsIgnoreCase(orderByType1, "DESC") ? "hide icon" : "icon" %>'
					displayType="secondary"
					icon="order-list-up"
					monospaced="<%= true %>"
					title="ascending"
				/>

				<clay:button
					borderless="<%= true %>"
					cssClass='<%= StringUtil.equalsIgnoreCase(orderByType1, "ASC") ? "hide icon" : "icon" %>'
					displayType="secondary"
					icon="order-list-down"
					monospaced="<%= true %>"
					title="descending"
				/>

				<aui:input cssClass="order-by-type-field" name="preferences--orderByType1--" type="hidden" value="<%= orderByType1 %>" />
			</div>
		</div>
	</clay:col>

	<clay:col
		md="6"
	>

		<%
		String orderByColumn2 = assetPublisherDisplayContext.getOrderByColumn2();
		%>

		<div class="h5"><liferay-ui:message key="and-then-by" /></div>

		<div class="align-items-center d-flex">
			<aui:select aria-label='<%= LanguageUtil.get(request, "and-then-by") %>' label="" name="preferences--orderByColumn2--" wrapperCssClass="d-inline-flex m-0">
				<aui:option label="title" selected='<%= orderByColumn2.equals("title") %>' />
				<aui:option label="create-date" selected='<%= orderByColumn2.equals("createDate") %>' value="createDate" />
				<aui:option label="modified-date" selected='<%= orderByColumn2.equals("modifiedDate") %>' value="modifiedDate" />
				<aui:option label="publish-date" selected='<%= orderByColumn2.equals("publishDate") %>' value="publishDate" />
				<aui:option label="expiration-date" selected='<%= orderByColumn2.equals("expirationDate") %>' value="expirationDate" />
				<aui:option label="priority" selected='<%= orderByColumn2.equals("priority") %>' value="priority" />

				<c:if test="<%= !assetPublisherDisplayContext.isSearchWithIndex() %>">
					<aui:option label="view-count" selected='<%= orderByColumn2.equals("viewCount") %>' value="viewCount" />
					<aui:option label="ratings" selected='<%= orderByColumn2.equals("ratings") %>' value="ratings" />
				</c:if>
			</aui:select>

			<%
			String orderByType2 = assetPublisherDisplayContext.getOrderByType2();
			%>

			<div class="align-items-center d-flex lfr-portal-tooltip ml-1 order-by-type-container">
				<clay:button
					borderless="<%= true %>"
					cssClass='<%= StringUtil.equalsIgnoreCase(orderByType2, "DESC") ? "hide icon" : "icon" %>'
					displayType="secondary"
					icon="order-list-up"
					monospaced="<%= true %>"
					title="ascending"
				/>

				<clay:button
					borderless="<%= true %>"
					cssClass='<%= StringUtil.equalsIgnoreCase(orderByType2, "ASC") ? "hide icon" : "icon" %>'
					displayType="secondary"
					icon="order-list-down"
					monospaced="<%= true %>"
					title="descending"
				/>

				<aui:input cssClass="order-by-type-field" name="preferences--orderByType2--" type="hidden" value="<%= orderByType2 %>" />
			</div>
		</div>
	</clay:col>
</clay:row>

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