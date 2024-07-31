<style type="text/css">
	.order-item-discount-price {
		color:#FF0000;
	}

	.order-item-img {
		height: 100px;
		padding-right: 12px;
		vertical-align: top;
		width: 100px;
	}

	.order-item-img img {
		border-radius: 8px;
		max-height: 100px;
		height: 100%;
		object-fit: cover;
		max-width: 100px;
		width: 100%;
	}

	.order-item-info {
		padding-right: 16px;
		vertical-align: top;
	}

	.order-item-info td {
		font-size: 12px;
	}

	.order-item-info-label {
		display: block;
	}

	.order-item-info-label td:first-child {
		font-weight: bold;
		padding: 0;
	}

	.order-item-original-price {
		text-decoration: line-through;
	}

	.order-item-price {
		font-size: 16px;
		font-weight: bold;
		vertical-align: top;
	}

	.order-item-title {
		font-size: 16px;
		padding: 0px;
		text-align: left;
	}

	.order-items {
		border-collapse: inherit;
		border-spacing: 0 16px;
		font-family: 'Roboto', sans-serif;
		text-align: left;
	}
</style>

<div>
	<div class="h4">
		${tableLabel}
	</div>

	<table class="order-items">
		<#foreach orderItem in orderItems>
			<tr>
				<td class="order-item-img">
					<img src="${orderItem.imageURL}">
				</td>
				<td class="order-item-info">
					<table>
						<tr>
							<th class="order-item-title">
								${orderItem.name}
							</th>
						</tr>

						<#if orderItem.options?has_content>
							<tr class="order-item-info-label">
								<td>
									${optionLabel}:
								</td>
								<td>
									${orderItem.options}
								</td>
							</tr>
						</#if>

						<tr class="order-item-info-label">
							<td>
								${skuLabel}:
							</td>
							<td>
								${orderItem.sku}
							</td>
						</tr>

						<#if orderItem.uom?has_content>
							<tr class="order-item-info-label">
								<td>
									${uomLabel}:
								</td>
								<td>
									${orderItem.uom}
								</td>
							</tr>
						</#if>

						<tr class="order-item-info-label">
							<td>
								${qtyLabel}:
							</td>
							<td>
								${orderItem.qty}
							</td>
						</tr>
					</table>
				</td>
				<td class="order-item-price">
					<table>
						<#if orderItem.originalPrice?has_content>
							<tr>
								<td class="order-item-original-price">
									${orderItem.originalPrice}
								</td>
							</tr>
							<tr>
								<td class="order-item-discount-price">
									${orderItem.finalPrice}
								</td>
							</tr>
						<#else>
							<tr>
								<td>
									${orderItem.finalPrice}
								</td>
							</tr>
						</#if>
					</table>
				</td>
			</tr>
		</#foreach>
	</table>
</div>