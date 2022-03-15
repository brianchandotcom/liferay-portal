<#if entries?has_content>
	<#assign normalizedDefaultLanguageId = stringUtil.replace(languageId, "_", "-") />

	<div class="text-truncate">
		<@liferay_ui["icon-menu"]
			direction="left-side"
			icon=stringUtil.toLowerCase(normalizedDefaultLanguageId)
			markupView="lexicon"
			showWhenSingleIcon=true
			triggerLabel=normalizedDefaultLanguageId
			triggerType="button"
		>
			<#list entries as entry>
				<#if !entry.isSelected() && !entry.isDisabled()>
					<#assign
						entryLocale = entry.getLocale()
						displayName = stringUtil.toLowerCase(entryLocale.getDisplayLanguage(entryLocale)) + "-" + entryLocale.getDisplayCountry(entryLocale)
						normalizedDefaultLanguageId = stringUtil.toLowerCase(stringUtil.replace(entry.getLanguageId(), "_", "-"))
					/>

					<#if languageUtil.isBetaLocale(entryLocale)>
						<#assign displayName += " [beta]" />
					</#if>

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