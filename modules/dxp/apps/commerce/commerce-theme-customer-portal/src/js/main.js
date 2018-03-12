(function() {
	AUI().ready(
		function(A) {
			var sidenavToggle = [
				$('#b2bSiteNavigation [data-toggle="sidenav"]'),
				$('.b2b-site-navigation-toggle [data-toggle="sidenav"]')
			];

			for (i = 0; i < sidenavToggle.length; i++) {
				sidenavToggle[i].sideNavigation();
			}

			Liferay.once(
				'screenLoad',
				function() {
					for (i = 0; i < sidenavToggle.length; i++) {
						var sideNavigation = sidenavToggle[i].data('lexicon.sidenav');

						if (sideNavigation) {
							sideNavigation.destroy();
						}
					}
				}
			);

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