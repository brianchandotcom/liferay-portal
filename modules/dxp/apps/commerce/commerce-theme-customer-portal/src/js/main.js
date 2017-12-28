(function() {
	AUI().ready(
		function(A) {
			var nodeBody = A.getBody();

			var closeSidenav = A.one('#closeSidenav');

			var iconSidenav = A.one('.sidenav-icon');

			if (closeSidenav) {
				closeSidenav.on(
					'click',
					function() {
						nodeBody.addClass('sidenav-b2b-close');
					}
				);
			}

			if (iconSidenav) {
				iconSidenav.on(
					'click',
					function() {
						nodeBody.removeClass('sidenav-b2b-close');
					}
				);
			}

			Liferay.after(
				'commerce:productAddedToCart',
				function(event) {
					Liferay.Portlet.refresh('#p_p_id_com_liferay_commerce_cart_content_web_internal_portlet_CommerceCartContentMiniPortlet_INSTANCE_commerceCartContentMiniPortlet_0_');
				}
			);
		}
	);
})();