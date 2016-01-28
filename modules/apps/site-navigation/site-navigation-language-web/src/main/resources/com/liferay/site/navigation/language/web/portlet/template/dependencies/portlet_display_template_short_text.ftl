<style>
	.language-entry-short-text {
		padding: 0 0.5em;
	}
</style>

<#if entries?has_content>
	<#list entries as entry>
		<#if !entry.isDisabled()>
			<@liferay_aui["a"]
				cssClass="language-entry-short-text"
				href=entry.getURL()
				label=entry.getShortDisplayName()
				lang=entry.getW3cLanguageId()
			/>
		</#if>
	</#list>
</#if>