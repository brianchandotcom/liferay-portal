<#setting number_format = "0">

insert into DLFolder values ('${dlFolder.uuid}', ${dlFolder.folderId}, ${dlFolder.groupId}, ${dlFolder.companyId}, ${dlFolder.userId}, '${dlFolder.userName}', '${dataFactory.getDateString(dlFolder.createDate)}', '${dataFactory.getDateString(dlFolder.modifiedDate)}', ${dlFolder.repositoryId}, ${dlFolder.mountPoint?string}, ${dlFolder.parentFolderId}, '${dlFolder.name}', '${dlFolder.description}', '${dataFactory.getDateString(dlFolder.lastPostDate)}', ${dlFolder.defaultFileEntryTypeId}, ${dlFolder.hidden?string}, ${dlFolder.overrideFileEntryTypes?string}, ${dlFolder.status}, ${dlFolder.statusByUserId}, '${dlFolder.statusByUserName}', '${dataFactory.getDateString(dlFolder.statusDate)}');

<#assign dlSync = dataFactory.newDLSync(dlFolder)>

insert into DLSync values (${dlSync.syncId}, ${dlSync.companyId}, ${dlSync.createDate}, ${dlSync.modifiedDate}, ${dlSync.fileId}, '${dlSync.fileUuid}', ${dlSync.repositoryId}, ${dlSync.parentFolderId}, '${dlSync.name}', '${dlSync.description}', '${dlSync.event}', '${dlSync.type}', '${dlSync.version}');

<#if (maxDLFileEntryCount > 0)>
	<#list 1..maxDLFileEntryCount as dlFileEntryCount>
		<#assign dlFileEntry = dataFactory.newDlFileEntry(dlFolder, dlFileEntryCount)>

		${sampleSQLBuilder.insertDLFileEntry(dlFileEntry, ddmStructureId)}

		${writerDocumentLibraryCSV.write(dlFolder.folderId + "," + dlFileEntry.name + "," + dlFileEntry.fileEntryId + "," + dataFactory.getDateLong(dlFileEntry.createDate) + "," + dataFactory.getDateLong(dlFolder.createDate) +"\n")}
	</#list>
</#if>