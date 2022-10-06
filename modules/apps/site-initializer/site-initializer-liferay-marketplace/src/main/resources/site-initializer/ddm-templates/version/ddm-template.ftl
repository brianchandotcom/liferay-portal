<#if (_CUSTOM_FIELD_version.getData())?has_content>
	<div class="category-container bg-whiteColor d-lg-block mb-1 pt-4 pb-4 pl-4 pr-4" style="background-color: var(--white);border-radius: var(--border-radius-sm);border-style: solid; border-width: 0px;">
		<div class="f-text font-size-paragraph-base font-size-small-caps">
			${languageUtil.get(locale, "version", "Version")}
		</div>

		<span class="f-text font-size-paragraph-base">
			${_CUSTOM_FIELD_version.getData()}
		</span>
	</div>
</#if>