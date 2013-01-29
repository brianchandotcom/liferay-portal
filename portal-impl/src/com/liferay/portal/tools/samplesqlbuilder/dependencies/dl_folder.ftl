<#setting number_format = "0">

insert into DLFolder values ('${dlFolder.uuid}', ${dlFolder.folderId}, ${dlFolder.groupId}, ${dlFolder.companyId}, ${dlFolder.userId}, '${dlFolder.userName}', '${dlFolder.createDate?datetime}', '${dlFolder.modifiedDate?datetime}', ${dlFolder.repositoryId}, ${dlFolder.mountPoint?string}, ${dlFolder.parentFolderId}, '${dlFolder.name}', '${dlFolder.description}', '${dlFolder.lastPostDate?datetime}', ${dlFolder.defaultFileEntryTypeId}, ${dlFolder.hidden?string}, ${dlFolder.overrideFileEntryTypes?string}, ${dlFolder.status}, ${dlFolder.statusByUserId}, '${dlFolder.statusByUserName}', ${dlFolder.statusDate!'null'});

<#assign dlSync = dataFactory.addDLSync(dlFolder)>

insert into DLSync values (${dlSync.syncId}, ${dlSync.companyId}, '${dlSync.createDate?datetime}', '${dlSync.modifiedDate?datetime}', ${dlSync.fileId}, '${dlSync.fileUuid}', ${dlSync.repositoryId}, ${dlSync.parentFolderId}, '${dlSync.name}', '${dlSync.description}', '${dlSync.event}', '${dlSync.type}', '${dlSync.version}');

<#if (maxDLFileEntryCount > 0)>
	<#list 1..maxDLFileEntryCount as dlFileEntryCount>
		<#assign dlFileEntry = dataFactory.addDlFileEntry(dlFolder, dlFileEntryCount)>

		${sampleSQLBuilder.insertDLFileEntry(dlFileEntry, ddmStructureId)}

		${writerDocumentLibraryCSV.write(dlFolder.folderId + "," + dlFileEntry.name + "," + dlFileEntry.fileEntryId + "," + dataFactory.getDateLong(dlFileEntry.createDate) + "," + dataFactory.getDateLong(dlFolder.createDate) +"\n")}
	</#list>
</#if>