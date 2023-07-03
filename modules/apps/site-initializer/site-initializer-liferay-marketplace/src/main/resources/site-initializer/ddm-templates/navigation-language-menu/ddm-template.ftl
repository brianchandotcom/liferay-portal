<ul>
	<#if entries?has_content>
		<#list entries as entry>
			<#assign entryLanguage = entry.longDisplayName?
				replace(" [beta]", "")?
				replace("(australia)", "(Australia)")?
				replace("(united states)", "(United States)")?
				cap_first
			/>

			<#if !entry.isDisabled()>
				<li class="${(entry.isSelected())?then('selected', '')} language-nav-item">
					<#if entry.isSelected()>
						<div class="d-inline-block pl-3 selected-icon">
							<@clay["icon"] symbol="check" />
						</div>
					</#if>

					<@clay["link"]
						cssClass="language-entry-long-text"
						href=entry.getURL()
						label=entryLanguage
						lang=entry.getW3cLanguageId()
						rel="nofollow"
					/>
				</li>
			</#if>
		</#list>
	</#if>
</ul>