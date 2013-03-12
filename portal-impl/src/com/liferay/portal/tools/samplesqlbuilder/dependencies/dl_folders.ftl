<#setting number_format = "0">

<#if (dlFolderDepth <= maxDLFolderDepth)>
	<#list 1..maxDLFolderCount as dlFolderCount>
		<#assign dlFolder = dataFactory.newDLFolder(groupId, parentDLFolderId, dlFolderCount)>

		${sampleSQLBuilder.insertDLFolder(dlFolder, ddmStructureId)}

		${sampleSQLBuilder.insertDLFolders(groupId, dlFolder.folderId, dlFolderDepth + 1, ddmStructureId)}
	</#list>
</#if>