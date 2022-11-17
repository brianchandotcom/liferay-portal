<div class="primary-navigation-account-menu">
	<#if entries?has_content>
		<#list entries as menuItem>
			<#assign
				menuItemCustomFields = menuItem.getExpandoAttributes()
				iconURL = getLocalizedExpandoValue(menuItemCustomFields["Menu Item Image Url"])!""
			/>

			<a class="account-menu-item d-flex justify-content-between p-3 text-decoration-none" href="${menuItem.getURL()}">
				<div class="account-menu-item-group d-flex">
					<div class="account-menu-item-icon mr-1 pr-1">
						<img class="account-menu-item-icon-image" src=${iconURL}>
					</div>

					<div class="account-menu-item-text ml-2">
						${menuItem.getName()}
					</div>
				</div>
			</a>
		</#list>
	</#if>
</div>

<#function getLocalizedExpandoValue expandoField>
	<#list expandoField as language, expandoValue>
		<#if language == locale>
			<#return expandoValue />
		</#if>
	</#list>
</#function>

<style>
	.arrow-hover-icon {
		display: none;
		transform: rotate(-45deg);
	}

	.account-menu-item {
		border-radius: 4px;
		width: 100%;
	}

	.account-menu-item-text {
		color: var(--color-neutral-10);
	}

	.account-menu-item-icon {
		height: 24px;
		width: 24px;
	}

	.account-menu-item:hover,
	.account-menu-item:active,
	.account-menu-item:focus {
		background: var(--color-action-primary-hover-lighten);
		color: var(--color-action-primary-default);
	}

	.account-menu-item:hover .arrow-hover-icon,
	.account-menu-item:active .arrow-hover-icon,
	.account-menu-item:focus .arrow-hover-icon {
		display: block;
	}
</style>