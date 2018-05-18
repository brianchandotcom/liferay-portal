<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
		containerCssClass="col-md-10"
		key="<%= CommerceOrderScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_ORDER_GENERAL %>"
		modelBean="<%= commerceOrder %>"
		navCssClass="col-md-2"
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