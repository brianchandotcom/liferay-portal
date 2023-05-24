<div class="marketplace-tabs-info" id="mpProfile">
	<#if (_CUSTOM_FIELD_Profile.getData())?has_content>
		<div class="font-size-heading-f5">${languageUtil.get(locale, "profile", "Profile")}</div>
		<div class="pt-4">${_CUSTOM_FIELD_Profile.getData()}</div>
	</#if>
</div>

<script>
	var contentEl = document.querySelector('#mpProfile');
	var tabPanel = contentEl.closest('.tab-panel-item');
	var tabTarget = tabPanel.getAttribute('aria-labelledby');
	var tabs = contentEl.closest(".component-tabs");;
	var navLink = tabs.querySelector('#' + tabTarget);
	var navItem = navLink.parentElement;

	if (contentEl.textContent.trim() === '') {
		navItem.classList.add('d-none');
	}
</script>