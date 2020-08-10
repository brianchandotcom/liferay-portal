<#if commerceCartContentTotalDisplayContext.getCommerceOrderPrice()??>
	<#assign
		commerceOrderPrice = commerceCartContentTotalDisplayContext.getCommerceOrderPrice()

		commerceOrderTotal = commerceOrderPrice.getTotal()
	/>

	<h4>
		<strong><@liferay_ui["message"] key="total" /> ${commerceOrderTotal.format(locale)}</strong>
	</h4>
</#if>