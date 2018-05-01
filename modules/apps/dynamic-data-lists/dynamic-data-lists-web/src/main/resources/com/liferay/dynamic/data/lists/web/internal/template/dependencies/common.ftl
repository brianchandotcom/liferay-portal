<#include "init.ftl">

<#if stringUtil.equals(language, "ftl")>
	${r"<#if cur_record?has_content>"}
		${r"${"}ddlDisplayTemplateHelper.renderRecordFieldValue(${fieldValueVariable}, ${localeVariable})${r"}"}
	${r"</#if>"}
<#else>
	${r"#if($cur_record)"}
		$ddlDisplayTemplateHelper.renderRecordFieldValue(${fieldValueVariable}, ${localeVariable})
	${r"#end"}
</#if>