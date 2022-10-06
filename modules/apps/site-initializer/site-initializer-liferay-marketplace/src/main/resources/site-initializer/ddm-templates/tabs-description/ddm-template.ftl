<div class="marketplace-tabs-info" id="mpDescription">
	<#if (description.getData())?has_content>
			<div class="font-size-heading-f5">${languageUtil.get(locale, "description", "Description")}</div>
			<div class="description-content pt-4">${description.getData()}</div>
	</#if>
</div>

<script>
	var contentEl = document.querySelector('#mpDescription');
	var tabPanel = contentEl.closest('.tab-panel-item');
	var tabTarget = tabPanel.getAttribute('aria-labelledby');
	var tabs = contentEl.closest(".f-tabs");
	var navLink = tabs.querySelector('#' + tabTarget);
	var navItem = navLink.parentElement;

	if (contentEl.textContent.trim() === '') {
		navItem.classList.add('d-none');
	}
</script>