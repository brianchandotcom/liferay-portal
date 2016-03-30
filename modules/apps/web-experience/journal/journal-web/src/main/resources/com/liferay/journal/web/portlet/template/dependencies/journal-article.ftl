<#include "init.ftl">

<#assign variableName = name + ".getData()">

<#if repeatable>
	<#assign variableName = "cur_" + variableName>
</#if>


${"<#assign assetJournal = " + variableName +" >"}

${r"
<#assign assetJournal = assetJournal?eval>
${assetJournal.assetclasspk}
${assetJournal.assetclassname}
${assetJournal.assettype}
${assetJournal.assettitle}
${assetJournal.groupdescriptivename}
"}