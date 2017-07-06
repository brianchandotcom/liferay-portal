<#if entries?has_content>
	<div class="truncate-text">
		<@liferay_ui["icon-menu"]
			icon="../aui/globe"
			message=locale.getDisplayName(locale)
		>
			<#list entries as entry>
				<#if !entry.isSelected() && !entry.isDisabled()>
					<@liferay_ui["icon"]
						image="../language/" + entry.getLanguageId()
						label=true
						lang=entry.getW3cLanguageId()
						message=entry.getLongDisplayName()
						url=entry.getURL()
					/>
				</#if>
			</#list>
		</@>
	</div>
</#if>