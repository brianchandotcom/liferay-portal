<div class="marketplace-tabs-info" id="mpLicense">
	<#if (_CUSTOM_FIELD_License.getData())?has_content>
		<div class="font-size-heading-f5">${languageUtil.get(locale, "license", "License")}</div>
		<div class="description-content pt-4">${_CUSTOM_FIELD_License.getData()}</div>
	</#if>
</div>

<script>
	var contentEl = document.querySelector('#mpLicense');
	var tabPanel = contentEl.closest('.tab-panel-item');
	var tabTarget = tabPanel.getAttribute('aria-labelledby');
	var tabs = contentEl.closest(".component-tabs");
	var navLink = tabs.querySelector('#' + tabTarget);
	var navItem = navLink.parentElement;

	if (contentEl.textContent.trim() === '') {
		navItem.classList.add('d-none');
	}
</script>