<#include "../init.ftl">

<@aui["field-wrapper"] data=data>
	<@aui.fieldset label=escape(label)>
		${fieldStructure.children}
	</@aui.fieldset>
</@>