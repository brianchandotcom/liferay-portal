<style>
	.app-vocab-facet {
		border-radius: 10px;
	}

	.app-vocab-facet .panel a {
		padding: 1rem;
	}

	.app-vocab-facet .collapse-icon .collapse-icon-closed .lexicon-icon,
	.app-vocab-facet .collapse-icon .collapse-icon-open .lexicon-icon {
		margin-top: 0.3rem;
	}

	.app-vocab-facet .panel-body {
		padding: 0.5rem 1rem 1rem;
	}

	.app-vocab-facet .list-unstyled {
		margin-bottom: 0;
	}
</style>

<#assign
	VOCABULARY_IDS = {
	  "Liferay Version": "449415075",
	  "Edition": "449511439",
	  "Category": "449511395",
	  "Price": "449511429"
	}

	assetCategoryLocalService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetCategoryLocalService")
	assetVocabularyLocalService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetVocabularyLocalService")
/>

<#macro getVocabularyFacet
	vocabIdString
	vocabName
>
<#assign
	vocabId = vocabIdString?number
	vocabulary = assetVocabularyLocalService.getVocabulary(vocabId)
	categoryIds = []
/>

<#if vocabulary?has_content>
	<#assign categories = vocabulary.getCategories() />
	<#if categories?has_content>
	  <#list categories as cur_Category>
		<#assign categoryIds = [cur_Category.getCategoryId()] + categoryIds />
	  </#list>
	</#if>
</#if>

<#assign vocabName = languageUtil.get(locale, vocabulary.getName(), "${vocabName}") />

<#if entries?has_content>
	<#assign orderEntries = entries?sort_by("displayName")?reverse />

	<#if stringUtil.equals(vocabName, "Category") || stringUtil.equals(vocabName, "Price")>
	  <#assign orderEntries = entries?sort_by("displayName") />
	</#if>
	<@liferay_ui["panel-container"]
		cssClass="app-vocab-facet bg-white border-radius-xlarge my-2"
		extended=true
		id="${namespace + 'facetAssetCategoriesPanelContainer' + vocabId}"
		markupView="lexicon"
		persistState=true
	>
	  <@liferay_ui.panel
		collapsible=true
		cssClass="font-size-paragraph-small font-weight-semi-bold search-facet"
		extended=!browserSniffer.isMobile(request)
		id="${namespace + 'facetAssetCategoriesPanel' + vocabId}"
		markupView="lexicon"
		persistState=true
		title="${vocabName?upper_case}"
	>
<ul class="list-unstyled">
		  <#list orderEntries as entry>
			<#if categoryIds?seq_contains(entry.getAssetCategoryId())>
<li class="color-neutral-2 facet-value py-1">
<div class="custom-checkbox custom-control">
<label class="facet-checkbox-label" for="${namespace}_term_${entry.getAssetCategoryId()}">
					<input
					  ${(entry.isSelected())?then("checked","")}
					  class="custom-control-input facet-term"
					  data-term-id="${entry.getAssetCategoryId()}"
					  disabled
					  id="${namespace}_term_${entry.getAssetCategoryId()}"
					  name="${namespace}_term_${entry.getAssetCategoryId()}"
					  onChange="Liferay.Search.FacetUtil.changeSelection(event);"
					  type="checkbox"
					/>
<span class="custom-control-label font-size-paragraph-small term-name ${(entry.isSelected())?then('facet-term-selected', 'facet-term-unselected')}" style="line-height: normal;">
					  <span class="custom-control-label-text">${htmlUtil.escape(entry.getDisplayName())}</span>
					</span>
				  </label>
				</div>
			  </li>
			</#if>
		  </#list>
		</ul>
	  </@>
	</@>
</#if>
</#macro>

<#list VOCABULARY_IDS as VOCABULARY_NAME, VOCABULARY_ID>
<@getVocabularyFacet
	vocabIdString="${VOCABULARY_ID}"
	vocabName="${VOCABULARY_NAME}"
/>
</#list>