<#include "../init.ftl">

<style type="text/css">
	pre.lfr-ddm-readonly-textarea {
		font-family: inherit;
		font-size: inherit;
		font-style: inherit;
		font-weight: inherit;
	}
</style>

<div class="field-wrapper-content lfr-forms-field-wrapper">
	<#if hasFieldValue || showEmptyFieldLabel>
		<label>
			<@liferay_ui.message key=escape(label) />
		</label>
	</#if>

	<#if hasFieldValue>
		<#if !disabled>
			<@liferay_aui.input
				name=namespacedFieldName
				type="hidden"
				value=fieldValue
			/>
		</#if>

		<pre class="lfr-ddm-readonly-textarea">${escape(fieldValue)}</pre>
	</#if>
</div>