<#include "init.ftl">

${"<#assign assetJournal = " + variableName +"!>"}
${r"
<#if (assetJournal != "")>
	<#assign assetJournal = assetJournal?eval>
	${assetJournal.assetclasspk}
	${assetJournal.assetclassname}
	${assetJournal.assettype}
	${assetJournal.assettitle}
	${assetJournal.groupdescriptivename}
</#if>
"}