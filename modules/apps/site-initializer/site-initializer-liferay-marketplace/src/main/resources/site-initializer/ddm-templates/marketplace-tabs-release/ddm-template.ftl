<div class="marketplace-tabs-info" id="mpRelease">
	<#if (_CUSTOM_FIELD_Release_Notes.getData())?has_content>
		<div class="pt-4">${_CUSTOM_FIELD_Release_Notes.getData()}</div>
	</#if>
</div>

<script>
	var contentEl = document.querySelector('#mpRelease');
	var tabPanel = contentEl.closest('.tab-panel-item');
	var tabTarget = tabPanel.getAttribute('aria-labelledby');
	var tabs = contentEl.closest(".component-tabs");
	var navLink = tabs.querySelector('#' + tabTarget);
	var navItem = navLink.parentElement;

	if (contentEl.textContent.trim() === '') {
		navItem.classList.add('d-none');
	}
</script>