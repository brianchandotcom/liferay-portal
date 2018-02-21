(function() {
	AUI().ready(
		function(A) {
			var nodeBody = A.getBody();

			var closeSidenav = A.one('#closeSidenav');

			var cartIcon = A.one('#cartIcon > a');

			if (closeSidenav) {
				closeSidenav.on(
					'click',
					function() {
						nodeBody.toggleClass('sidenav-b2b-close');
					}
				);
			}

			Liferay.after(
				'commerce:productAddedToCart',
				function(event) {
					Liferay.Portlet.refresh('#p_p_id_com_liferay_commerce_cart_content_web_internal_portlet_CommerceCartContentMiniPortlet_INSTANCE_commerceCartContentMiniPortlet_0_');

					if (cartIcon) {
						cartIcon.addClass('animBounce');

						var cartIconCount = A.one('#cartIcon > a .sticker');

						if (cartIconCount) {
							var orderItemCount = event.commerceOrderItemsCount;

							cartIconCount.html(orderItemCount);
						}
					}
				}
			);
		}
	);
})();