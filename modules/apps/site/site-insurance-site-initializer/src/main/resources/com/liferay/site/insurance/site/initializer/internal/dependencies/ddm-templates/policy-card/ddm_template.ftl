<style>

	.policy-card .policy-type {
		font-size: var(--h2-font-size);
		font-weight: var(--font-weight-bolder);
		line-height: var(--line-height-sm);
		margin-bottom: var(--spacer-1);
	}
	.policy-card .policy-name {
		font-size: var(--h3-font-size);
		line-height: var(--line-height-sm);
		margin-bottom: var(--spacer-4);
		color: var(--gray-600);
	}
</style>

<a class="card-link" href="${friendlyURLs[themeDisplay.getLanguageId()]!""}">
	<div class="policy-card product-card">
		<div class="policy-type">
			${PolicyName.PolicyType.getData()}
		</div>

		<div class="policy-name">
			${PolicyName.getData()}
		</div>

		<div>
			<span class="product-card-label">Insured:</span> ${PolicyOwner.getData()}
		</div>

		<div>
			<span class="product-card-label">Premium:</span> ${YearlyFee.getData()}
		</div>

		<div class="product-card-status">
			<#if Status.StatusImage.getData()?? && Status.StatusImage.getData() != "">
				<img alt="${Status.StatusImage.getAttribute("alt")}" data-fileentryid="${Status.StatusImage.getAttribute("fileEntryId")}" src="${Status.StatusImage.getData()}" />
			</#if>${Status.getData()}
		</div>
	</div>
</a>