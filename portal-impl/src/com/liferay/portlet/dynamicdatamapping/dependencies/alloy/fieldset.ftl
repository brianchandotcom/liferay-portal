<#include "../init.ftl">

<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip)>
	<@aui.fieldset label=escape(label)>
		${fieldStructure.children}
	</@aui.fieldset>
</@>