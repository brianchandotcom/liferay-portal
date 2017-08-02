(function() {
	AUI().ready(
		function(A) {
			var animateNodes = A.all('.animate');

			var cartIcon = A.one('#cartIcon a');
			var wishListIcon = A.one('#wishListIcon a');

			animateNodes.each(
				function(node) {
					node.getDOMNode().addEventListener('animationend', animationEnd);
				}
			);

			Liferay.after(
				'commerce:productAddedToCart',
				function(event) {
					Liferay.Portlet.refresh('#p_p_id_com_liferay_commerce_cart_content_web_internal_portlet_CommerceCartContentMiniPortlet_INSTANCE_commerceCartContentMiniPortlet_0_');

					if (cartIcon) {
						cartIcon.addClass('animBounce');
					}

				}
			);

			Liferay.after(
				'commerce:productAddedToWishList',
				function(event) {
					Liferay.Portlet.refresh('#p_p_id_com_liferay_commerce_cart_content_web_internal_portlet_CommerceCartContentMiniPortlet_INSTANCE_commerceCartContentMiniPortlet_1_');

					if (wishListIcon) {
						wishListIcon.addClass('animBounce');
					}
				}
			);

			function animationEnd(event) {
				A.one(event.target).removeClass('animBounce');
			}
		}
	);
})();