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
					<#assign
						entryLocale = entry.getLocale()

						displayName = entry.getLongDisplayName() + "-" + entryLocale.getDisplayCountry(entryLocale)
						normalizedDefaultLanguageId = stringUtil.toLowerCase(stringUtil.replace(entry.getLanguageId(), "_", "-"))
					/>

					<@liferay_ui["icon"]
						icon=normalizedDefaultLanguageId
						iconCssClass="inline-item inline-item-before"
						markupView="lexicon"
						message=displayName
						url=entry.getURL()
					/>
				</#if>
			</#list>
		</@>
	</div>
</#if>