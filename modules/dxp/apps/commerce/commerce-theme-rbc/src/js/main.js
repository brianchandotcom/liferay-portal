(function() {
	AUI().ready(
		'liferay-sign-in-modal',
		function(A) {
			var animateNodes = A.all('.animate');

			var cartIcon = A.one('#cartIcon a');
			var wishListIcon = A.one('#wishListIcon a');

			var signIn = A.one('a.sign-in');

			if (signIn && signIn.getData('redirect') !== 'true') {
				signIn.plug(Liferay.SignInModal);
			}

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