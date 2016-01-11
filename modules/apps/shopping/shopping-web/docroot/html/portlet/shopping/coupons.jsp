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
%>

<liferay-util:include page="/html/portlet/shopping/tabs1.jsp" servletContext="<%= application %>" />

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
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

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="deleteCouponIds" type="hidden" />

	<%
	CouponSearch searchContainer = new CouponSearch(renderRequest, portletURL);

	List headerNames = searchContainer.getHeaderNames();

	headerNames.add(StringPool.BLANK);

	searchContainer.setRowChecker(new RowChecker(renderResponse));
	%>

	<liferay-ui:search-form
		page="/html/portlet/shopping/coupon_search.jsp"
		searchContainer="<%= searchContainer %>"
		servletContext="<%= application %>"
	/>

	<%
	CouponDisplayTerms searchTerms = (CouponDisplayTerms)searchContainer.getSearchTerms();

	int total = ShoppingCouponLocalServiceUtil.searchCount(scopeGroupId, company.getCompanyId(), searchTerms.getCode(), searchTerms.isActive(), searchTerms.getDiscountType(), searchTerms.isAndOperator());

	searchContainer.setTotal(total);

	List results = ShoppingCouponServiceUtil.search(scopeGroupId, company.getCompanyId(), searchTerms.getCode(), searchTerms.isActive(), searchTerms.getDiscountType(), searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);
	%>

	<c:if test="<%= !results.isEmpty() %>">
		<div class="separator"><!-- --></div>

		<aui:button-row>
			<aui:button cssClass="btn-lg" disabled="<%= true %>" name="delete" onClick='<%= renderResponse.getNamespace() + "deleteCoupons();" %>' value="delete" />
		</aui:button-row>
	</c:if>

	<%
	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		ShoppingCoupon coupon = (ShoppingCoupon)results.get(i);

		coupon = coupon.toEscapedModel();

		ResultRow row = new ResultRow(coupon, coupon.getCouponId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/shopping/edit_coupon");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("couponId", String.valueOf(coupon.getCouponId()));

		// Code

		row.addText(coupon.getCode(), rowURL);

		// Name and description

		if (Validator.isNotNull(coupon.getDescription())) {
			row.addText(coupon.getName().concat("<br />").concat(coupon.getDescription()), rowURL);
		}
		else {
			row.addText(coupon.getName(), rowURL);
		}

		// Start date

		row.addDate(coupon.getStartDate(), rowURL);

		// End date

		if (coupon.getEndDate() == null) {
			row.addText(LanguageUtil.get(request, "never"), rowURL);
		}
		else {
			row.addDate(coupon.getEndDate(), rowURL);
		}

		// Discount type

		row.addText(LanguageUtil.get(request, coupon.getDiscountType()), rowURL);

		// Action

		row.addJSP("/html/portlet/shopping/coupon_action.jsp", "entry-action", application, request, response);

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
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