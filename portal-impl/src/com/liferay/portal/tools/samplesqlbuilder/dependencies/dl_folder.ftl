<#setting number_format = "0">

<#assign dlFolderCreateDate = dataFactory.getDateString(dlFolder.createDate)>
<#assign dlFolderCreateDateLong = dataFactory.getDateLong(dlFolder.createDate)>

insert into DLFolder values ('${portalUUIDUtil.generate()}', ${dlFolder.folderId}, ${dlFolder.groupId}, ${dlFolder.companyId}, ${dlFolder.userId}, '', '${dlFolderCreateDate}', '${dlFolderCreateDate}', ${dlFolder.repositoryId}, 0, ${dlFolder.parentFolderId}, '${dlFolder.name}', '${dlFolder.description}', null, 0, FALSE, 0, 0, ${dlFolder.userId}, '', '${dlFolderCreateDate}');

<#assign dlSync = dataFactory.addDLSync(dlFolder.companyId, dlFolder.folderId, dlFolder.repositoryId, dlFolder.parentFolderId, true)>

insert into DLSync values (${dlSync.syncId}, ${dlSync.companyId}, '${dlFolderCreateDateLong}', '${dlFolderCreateDateLong}', ${dlSync.fileId}, '${dlSync.fileUuid}', ${dlSync.repositoryId}, ${dlSync.parentFolderId}, '${dlSync.name}', '${dlSync.description}', '${dlSync.event}', '${dlSync.type}', '${dlSync.version}');

<#if (maxDLFileEntryCount > 0)>
	<#list 1..maxDLFileEntryCount as dlFileEntryCount>
		<#assign dlFileEntry = dataFactory.addDlFileEntry(dlFolder.groupId, dlFolder.companyId, dlFolder.userId, dlFolder.folderId, "txt", "text/plain", "TestFile" + stringUtil.valueOf(dlFileEntryCount), "TestFile" + dlFileEntryCount + ".txt", "")>

		${sampleSQLBuilder.insertDLFileEntry(dlFileEntry, ddmStructure)}

		${writerDocumentLibraryCSV.write(dlFolder.folderId + "," + dlFileEntry.name + "," + dlFileEntry.fileEntryId + "," + dataFactory.getDateLong(dlFileEntry.createDate) + "," + dataFactory.getDateLong(dlFolder.createDate) +"\n")}
	</#list>
</#if>