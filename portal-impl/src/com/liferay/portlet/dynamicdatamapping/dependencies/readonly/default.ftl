<#include "../init.ftl">

<#if fieldValue?? && fieldValue != "">
	<div class="lfr-forms-field-wrapper aui-field-wrapper-content">
		<label class="aui-field-label">
			<@liferay_ui.message key=label />
		</label>

		${fieldValue}

		${fieldStructure.children}
	</div>
</#if>