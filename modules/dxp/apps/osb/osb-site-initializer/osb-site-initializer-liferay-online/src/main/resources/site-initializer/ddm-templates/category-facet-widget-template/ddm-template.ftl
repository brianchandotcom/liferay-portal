<style>
	.label-liferay-online .btn-light {
		background-color: #fff;
		border-color: #fff;
	}

	.label-liferay-online .btn-light:hover {
		background-color: #eee;
		border-color: #eee;
	}
</style>

<#if entries?has_content>
	<div class="container">
		<div class="d-flex label-container label-liferay-online">
			<#list entries as entry>
				<button
					class="btn btn-sm facet-term term-name mr-2 ${(entry.isSelected())?then('btn-dark', 'btn-light')}"
					data-term-id="${entry.getAssetCategoryId()}"
					disabled
					onClick="Liferay.Search.FacetUtil.changeSelection(event);"
					type="button"
			>
					<span class="label-item label-item-expand">
						${htmlUtil.escape(entry.getDisplayName())}
					</span>
				</button>
			</#list>

			<#if !assetCategoriesSearchFacetDisplayContext.isNothingSelected()>
				<button
					class="btn btn-light btn-sm ml-1 facet-term term-name"
					data-term-id="${entry.getAssetCategoryId()}"
					disabled
					onClick="Liferay.Search.FacetUtil.clearSelections(event);"
					type="button"
				>
					<@liferay_aui.icon
						cssClass="mr-1"
						image="times"
						markupView="lexicon"
						value="clear"
					/>

					Clear filter
				</button>
			</#if>
		</div>
	</div>
</#if>