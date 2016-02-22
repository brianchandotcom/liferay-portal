<#include "../init.ftl">

<@liferay_aui["field-wrapper"] data=data>
	<@liferay_aui.fieldset label=escape(label)>
		${fieldStructure.children}
	</@liferay_aui.fieldset>
</@>