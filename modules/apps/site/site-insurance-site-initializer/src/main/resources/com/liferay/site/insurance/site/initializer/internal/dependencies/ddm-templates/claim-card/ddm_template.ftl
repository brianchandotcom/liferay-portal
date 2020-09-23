<style>
	.claim-card .policy-name {
		font-size: var(--h3-font-size);
		font-weight: var(--font-weight-bolder);
		line-height: var(--line-height-sm);
		margin-bottom: var(--spacer-1);
	}

	.claim-card .product-reference {
		font-size: var(--font-size-lg);
		margin-bottom: var(--spacer-3);
	}

	.claim-card.product-card:before {
		background-color: var(--primary);
	}

</style>

<a class="card-link" href="${friendlyURLs[themeDisplay.getLanguageId()]!""}">
	<div class="claim-card product-card">
		<div class="policy-name">
			${PolicyName.getData()}
		</div>

		<div class="product-reference">
			${ReferenceNumber.getData()}
		</div>

		<div>
			<span class="product-card-label">Type:</span> ${PolicyType.getData()}
		</div>

		<div>
			<span class="product-card-label">Filed Date:</span> ${FiledDate1.getData()}
		</div>

		<div class="product-card-status">
			<#if CurrentStatusImage.getData()?? && CurrentStatusImage.getData() != "">
				<img alt="${CurrentStatusImage.getAttribute("alt")}" data-fileentryid="${CurrentStatusImage.getAttribute("fileEntryId")}" src="${CurrentStatusImage.getData()}" />
			</#if>${CurrentStatus.getData()}
		</div>
	</div>
</a>