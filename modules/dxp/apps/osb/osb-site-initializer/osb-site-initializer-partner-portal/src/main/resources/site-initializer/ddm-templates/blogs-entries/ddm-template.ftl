<#if entries?has_content>
	<#list entries as curEntry>
		<#assign categories = curEntry.getCategories() />

		<div class="border border-brand-primary-lighten-4 p-4 rounded-lg text-left">
				<#if categories?has_content>
					<h5 class="mb-3 text-break text-primary">
						<#list categories as category>
							${htmlUtil.escape(category.getTitle(locale))}
						</#list>
					</h5>
				</#if>

				<h2 class="mb-3 text-break">${htmlUtil.escape(curEntry.getTitle(locale))}</h2>

				<@liferay_asset["asset-display"] assetEntry=curEntry />
		</div>
	</#list>
</#if>