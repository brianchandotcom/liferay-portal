<#assign
	orderId = '${commerceCartContentTotalDisplayContext.getCommerceOrderId()}'
	orderTotalItems = 0
	shippingPrice = 'Free'
	subTotal = '${commerceCartContentTotalDisplayContext.getCommerceOrderSubtotal()}'
	tax = '$0.00'
	total = '${commerceCartContentTotalDisplayContext.getCommerceOrderSubtotal()}'
	zipCode = '(No Zip Code)'
/>

<#if commerceCartContentTotalDisplayContext.getCommerceOrderId() == 0>
	<#assign
		orderId = '0 Items in your cart'
	/>
</#if>

<#list entries as curCommerceOrderItem>
	<#assign orderTotalItems = orderTotalItems + curCommerceOrderItem.getQuantity() />
</#list>

<div class="commerce-component-header">
	<h2 class="component-title">
		<span class="component-title-label">Total</span>
		<span class="component-title-value">${total}</span>
	</h2>
</div>

<div class="commerce-panel-secondary panel">
	<div class="panel-header">
		<span class="panel-title">Summary</span>
	</div>

	<div class="panel-body">
		<ul class="commerce-cart-content-total-list list-unstyled">
			<li class="autofit-float autofit-row autofit-row-center commerce-cart-content-total-item">
				<div class="autofit-col autofit-col-expand">
					<div class="autofit-section">
						<span class="commerce-cart-content-total-label subtotal-label">Subtotal (${orderTotalItems})</span>
					</div>
				</div>

				<div class="autofit-col">
					<div class="autofit-section">
						<span class="commerce-cart-content-total-value subtotal-value">${subTotal}</span>
					</div>
				</div>
			</li>
			<li class="autofit-float autofit-row autofit-row-center commerce-cart-content-total-item">
				<div class="autofit-col autofit-col-expand">
					<div class="autofit-section">
						<span class="commerce-cart-content-total-label shipping-label">Delivery</span>
					</div>
				</div>

				<div class="autofit-col">
					<div class="autofit-section">
						<span class="commerce-cart-content-total-value shipping-value">${shippingPrice}</span>
					</div>
				</div>
			</li>
			<li class="autofit-float autofit-row autofit-row-center commerce-cart-content-total-item">
				<div class="autofit-col autofit-col-expand">
					<div class="autofit-section">
						<span class="commerce-cart-content-total-label tax-label">Tax</span>
					</div>
				</div>

				<div class="autofit-col">
					<div class="autofit-section">
						<span class="commerce-cart-content-total-value tax-value">${tax}</span>
					</div>
				</div>
			</li>
			<li class="commerce-cart-content-total-item">
				<span class="commerce-cart-content-total-label zipcode-label">Delivery &amp; Tax for</span>
				<span class="commerce-cart-content-total-value zipcode-value">${zipCode}</span>
			</li>
			<li class="commerce-cart-content-total-divider"></li>
			<li class="autofit-float autofit-row autofit-row-center commerce-cart-content-total-item">
				<div class="autofit-col autofit-col-expand">
					<div class="autofit-section">
						<span class="commerce-cart-content-total-label order-label">Order #</span>
					</div>
				</div>

				<div class="autofit-col">
					<div class="autofit-section">
						<span class="commerce-cart-content-total-value order-value">${orderId}</span>
					</div>
				</div>
			</li>
			<li class="commerce-cart-content-total-item">
				<div class="input-group">
					<div class="input-group-item">
						<input aria-label="Enter Promo Code" class="form-control input-group-inset input-group-inset-after" placeholder="Enter Promo Code" type="text">
						<div class="input-group-inset-item input-group-inset-item-after">
							<button class="btn btn-outline-primary btn-outline-borderless btn-monospaced" type="button"><svg aria-hidden="true" class="lexicon-icon lexicon-icon-caret-right" focusable="false"><use xlink:href="${themeDisplay.getPathThemeImages()}/lexicon/icons.svg#caret-right" /></svg></button>
						</div>
					</div>
				</div>
			</li>
			<li class="autofit-float autofit-row autofit-row-center commerce-cart-content-total-item">
				<div class="autofit-col autofit-col-expand">
					<div class="autofit-section">
						<span class="commerce-cart-content-total-label total-label">Total</span>
					</div>
				</div>

				<div class="autofit-col">
					<div class="autofit-section">
						<span class="commerce-cart-content-total-value total-value">${total}</span>
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>

<div class="autofit-float autofit-row autofit-row-center commerce-cart-content-total-actions">
	<div class="autofit-col autofit-col-expand">
		<div class="autofit-section">
			<a class="btn btn-primary commerce-btn-cta" href="${commerceCartContentTotalDisplayContext.getCheckoutPortletURL()}" role="button">Checkout</a>
		</div>
	</div>
</div>