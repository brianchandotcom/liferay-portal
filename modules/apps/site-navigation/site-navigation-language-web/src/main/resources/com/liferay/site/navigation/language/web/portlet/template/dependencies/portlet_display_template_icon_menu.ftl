<#if entries?has_content>
	<#assign normalizedDefaultLanguageId = stringUtil.toLowerCase(stringUtil.replace(languageId, "_", "-")) />

	<div class="truncate-text">
		<@liferay_ui["icon-menu"]
			direction="left-side"
			icon=normalizedDefaultLanguageId
			markupView="lexicon"
			showWhenSingleIcon=true
			triggerLabel=normalizedDefaultLanguageId
			triggerType="button"
		>
			<#list entries as entry>
				<#if !entry.isSelected() && !entry.isDisabled()>

					<#assign normalizedDefaultLanguageId = stringUtil.toLowerCase(stringUtil.replace(entry.getLanguageId(), "_", "-")) />

					<@liferay_ui["icon"]
						icon=normalizedDefaultLanguageId
						iconCssClass="inline-item inline-item-before"
						markupView="lexicon"
						message=normalizedDefaultLanguageId
						url=entry.getURL()
					/>
				</#if>
			</#list>
		</@>
	</div>
</#if>