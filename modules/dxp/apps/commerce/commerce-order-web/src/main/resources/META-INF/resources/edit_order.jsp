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
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

String title = null;

if (commerceOrder != null) {
	title = LanguageUtil.format(request, "order-x", commerceOrder.getCommerceOrderId());
}
else {
	title = LanguageUtil.get(request, "add-order");
}

renderResponse.setTitle(title);
%>

<div id="<portlet:namespace />editOrderContainer">
	<liferay-frontend:screen-navigation
		key="<%= CommerceOrderScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_ORDER_GENERAL %>"
		modelBean="<%= commerceOrder %>"
		portletURL="<%= currentURLObj %>"
	/>
</div>

<aui:script sandbox="<%= true %>">
	function enableFormEdit(element, enable) {
		var form = $(element).closest('form');

		var editFormButtons = form.find('.edit-form-buttons');
		var editFormLink = form.find('.edit-form-link');
		var fieldset = form.find('fieldset');

		if (enable) {
			editFormButtons.removeClass('hide');
			editFormLink.addClass('hide');

			fieldset.removeAttr('disabled');
		}
		else {
			form[0].reset();

			editFormButtons.addClass('hide');
			editFormLink.removeClass('hide');

			fieldset.attr('disabled', '');
		}
	}

	var editOrderContainer = $('#<portlet:namespace />editOrderContainer');

	editOrderContainer.on(
		'submit', 'form',
		function(event) {
			event.preventDefault();

			submitForm(this);
		}
	);

	editOrderContainer.on(
		'click', '.cancel-form-button',
		function() {
			enableFormEdit(this, false);
		}
	);

	editOrderContainer.on(
		'click', '.edit-form-link',
		function() {
			enableFormEdit(this, true);
		}
	);
</aui:script>