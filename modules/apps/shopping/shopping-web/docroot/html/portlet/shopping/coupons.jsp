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

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/shopping/view");
portletURL.setParameter("tabs1", "coupons");

CouponSearch couponSearch = new CouponSearch(renderRequest, PortletURLUtil.clone(portletURL, renderResponse));

CouponDisplayTerms searchTerms = (CouponDisplayTerms)couponSearch.getSearchTerms();

int totalCoupons = ShoppingCouponLocalServiceUtil.searchCount(scopeGroupId, company.getCompanyId(), searchTerms.getKeywords(), searchTerms.isActive(), searchTerms.getDiscountType(), searchTerms.isAndOperator());

couponSearch.setTotal(totalCoupons);

List coupons = ShoppingCouponServiceUtil.search(scopeGroupId, company.getCompanyId(), searchTerms.getKeywords(), searchTerms.isActive(), searchTerms.getDiscountType(), searchTerms.isAndOperator(), couponSearch.getStart(), couponSearch.getEnd());

couponSearch.setResults(coupons);
%>

<liferay-util:include page="/html/portlet/shopping/tabs1.jsp" servletContext="<%= application %>">
	<liferay-util:param name="showSearch" value="<%= Boolean.TRUE.toString() %>" />
</liferay-util:include>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="coupons"
>
	<liferay-frontend:management-bar-filters>

		<%
		PortletURL discountTypeURL = PortletURLUtil.clone(portletURL, renderResponse);

		discountTypeURL.setParameter("active", searchTerms.getActive());
		%>

		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= ArrayUtil.append(new String[] {"all"}, ShoppingCouponConstants.DISCOUNT_TYPES) %>'
			navigationParam="discountType"
			portletURL="<%= discountTypeURL %>"
		/>

		<%
		PortletURL activeURL = PortletURLUtil.clone(portletURL, renderResponse);

		activeURL.setParameter("discountType", searchTerms.getDiscountType());
		%>

		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"yes", "no"} %>'
			navigationParam="active"
			portletURL="<%= activeURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="deleteCouponIds" type="hidden" />

	<c:if test="<%= !coupons.isEmpty() %>">
		<div class="separator"><!-- --></div>

		<aui:button-row>
			<aui:button cssClass="btn-lg" disabled="<%= true %>" name="delete" onClick='<%= renderResponse.getNamespace() + "deleteCoupons();" %>' value="delete" />
		</aui:button-row>
	</c:if>

	<liferay-ui:search-container
		id="coupons"
		searchContainer="<%= couponSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.shopping.model.ShoppingCoupon"
			modelVar="coupon"
		>
			<liferay-ui:search-container-column-text
				name="name"
			>
				<strong><%= coupon.getName() %></strong>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="description"
				property="description"
			/>

			<liferay-ui:search-container-column-text
				name="code"
				property="code"
			/>

			<liferay-ui:search-container-column-text
				name="discount-type"
				property="discountType"
			/>

			<liferay-ui:search-container-column-date
				name="start-date"
				value="<%= coupon.getStartDate() %>"
			/>

			<c:choose>
				<c:when test="<%= coupon.getEndDate() != null %>">
					<liferay-ui:search-container-column-date
						name="endDate"
						value="<%= coupon.getEndDate() %>"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						name="end-date"
					>
						<liferay-ui:message key="never" />
					</liferay-ui:search-container-column-text>
				</c:otherwise>
			</c:choose>

			<liferay-ui:search-container-column-jsp
				cssClass="list-group-item-field"
				path="/html/portlet/shopping/coupon_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<portlet:renderURL var="editCouponURL">
	<portlet:param name="struts_action" value="/shopping/edit_coupon" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-coupon") %>' url="<%= editCouponURL.toString() %>" />
</liferay-frontend:add-menu>

<aui:script>
	Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />delete', '#<portlet:namespace /><%= searchContainerReference.getId() %>SearchContainer', document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

	Liferay.provide(
		window,
		'<portlet:namespace />deleteCoupons',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-coupons") %>')) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.DELETE %>';
				document.<portlet:namespace />fm.<portlet:namespace />deleteCouponIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

				submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_coupon" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
			}
		},
		['liferay-util-list-fields']
	);
</aui:script>