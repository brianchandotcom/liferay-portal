<#assign
	selectedLanguage = locale.getDisplayName(locale)
	selectedDisplayLanguage = selectedLanguage?keep_before(" (")
/>

<#if selectedLanguage?contains("Australia") || selectedLanguage?contains("United States")>
	<#assign selectedDisplayLanguage = selectedLanguage />
</#if>

<div class="dropdown dropdown-action">
	<button
		aria-expanded="false" aria-haspopup="true" class="btn btn-unstyled dropdown-toggle"
		data-toggle="dropdown" id="languageSelector" type="button">
		<svg class="lexicon-icon" focusable="false" role="presentation" viewBox="0 0 512 512">
			<use xlink:href="#language" />
		</svg>

		<span>${selectedDisplayLanguage?cap_first}</span>
	</button>

	<ul aria-labelledby="languageSelector" class="dropdown-menu">
		<#if entries?has_content>
			<#list entries as entry>
				<#assign entryLanguage = entry.longDisplayName?
														replace(" [beta]", "")?
														replace("(australia)", "(Australia)")?
														replace("(united states)", "(United States)")?
														cap_first />
				<#if !entry.isDisabled()>
					<li class="${(entry.isSelected())?then('active selected', '')}">
						<@liferay_aui["a"]
							cssClass="dropdown-item"
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
</div>

<@liferay_aui["script"]
	position="inline"
	use="liferay-menu"
>
	Liferay.Menu.register('languageSelector');
</@>