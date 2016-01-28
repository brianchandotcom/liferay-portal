<#include "../init.ftl">

<#assign style = fieldStructure.style!"">

<@liferay_aui["field-wrapper"] data=data label=escape(label)>
	<div class="form-group">
		<div class="separator" style="${escapeAttribute(style)}"></div>
	</div>

	${fieldStructure.children}
</@>