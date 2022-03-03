<#include "../init.ftl">

<div class="my-3 position-relative">
	<hr class="position-absolute separator" style="top: 50%;" />

	<label class="position-relative">
		<@liferay_ui.message key=escape(label) />
	</label>
</div>

${fieldStructure.children}