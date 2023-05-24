<div class="marketplace-tabs-info" id="mpDescription">
	<#if (description.getData())?has_content>
		<div class="font-size-heading-f5">${languageUtil.get(locale, "description", "Description")}</div>
		<div class="description-content pt-4">${description.getData()}</div>
	</#if>
</div>