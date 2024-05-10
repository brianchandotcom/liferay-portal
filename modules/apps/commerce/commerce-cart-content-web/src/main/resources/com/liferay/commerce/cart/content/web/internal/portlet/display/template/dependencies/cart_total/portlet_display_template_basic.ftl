<#if commerceCartContentTotalDisplayContext.getCommerceOrderPrice()??>
	<#assign
		commerceOrderPrice = commerceCartContentTotalDisplayContext.getCommerceOrderPrice()

		commerceOrderTotal = commerceOrderPrice.getTotal()
	/>

	<div class="h4">
		<strong><@liferay_ui["message"] key="total" /> ${commerceOrderTotal.format(locale)}</strong>
	</div>
</#if>