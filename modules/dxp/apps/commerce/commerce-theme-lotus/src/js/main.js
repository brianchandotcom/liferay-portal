(function() {
	AUI().ready(
		function(A) {
			var WIN = A.getWin();

			var navbarEcommerce = A.one('.navbar-ecommerce');

			var animateNodes = A.all('.animate');

			var cartIcon = A.one('#cartIcon > a');

			var wishListIcon = A.one('#wishListIcon > a');

			var ScrollPos = function(e) {
				var ScrollPosY = (WIN.get('docScrollY'));

				if (ScrollPosY > 100) {
					if (!navbarEcommerce.hasClass('navbar-reduced')) {
						navbarEcommerce.addClass('navbar-reduced');
					}
				}
				else {
					navbarEcommerce.removeClass('navbar-reduced');
				}
			};

			A.on('mousewheel', ScrollPos);

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

						var cartIconCount = A.one('#cartIcon > a .sticker');

						if (cartIconCount) {
							var orderItemCount = event.commerceOrderItemsCount;

							cartIconCount.html(orderItemCount);
						}
						else {
							var bagFullIcon = A.one('#cartIcon > a .icon-bag-full');
							var bagIcon = A.one('#cartIcon > a .icon-bag');

							bagFullIcon.show();
							bagIcon.remove();

							cartIcon.append('<span class="sticker sticker-outside">' + event.commerceOrderItemsCount + '</span>');
						}
					}
				}
			);

			Liferay.after(
				'commerce:productAddedToWishList',
				function(event) {
					Liferay.Portlet.refresh('#p_p_id_com_liferay_commerce_cart_content_web_internal_portlet_CommerceCartContentMiniPortlet_INSTANCE_commerceCartContentMiniPortlet_1_');

					if (wishListIcon) {
						wishListIcon.addClass('animBounce');

						var wishListIconCount = A.one('#wishListIcon > a .sticker');

						if (wishListIconCount) {
							var orderItemCount = event.commerceOrderItemsQuantity;

							wishListIconCount.html(orderItemCount);
						}
						else {
							var bagFullIcon = A.one('#wishListIcon > a .icon-heart-full');
							var bagIcon = A.one('#wishListIcon > a .icon-heart');

							bagFullIcon.show();
							bagIcon.remove();

							wishListIcon.append('<span class="sticker sticker-outside">' + event.commerceOrderItemsCount + '</span>');
						}
					}
				}
			);

			function animationEnd(event) {
				A.one(event.target).removeClass('animBounce');
			}
		}
	);
})();