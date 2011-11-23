<#setting number_format = "0">

<#assign folderCreateDate = dataFactory.getDateAsString(dlFolder.createDate)>

insert into DLFolder values ('${portalUUIDUtil.generate()}', ${dlFolder.folderId}, ${dlFolder.groupId}, ${dlFolder.companyId}, ${dlFolder.userId}, '', '${folderCreateDate}', '${folderCreateDate}', ${dlFolder.repositoryId}, 0, ${dlFolder.parentFolderId}, '${dlFolder.name}', '${dlFolder.description}', null, 0, 0);

<#assign dlSync = dataFactory.addDLSync(dlFolder.companyId, dlFolder.folderId, dlFolder.repositoryId, dlFolder.parentFolderId, true)>

insert into DLSync values (${dlSync.syncId}, ${dlSync.companyId}, '${folderCreateDate}', '${folderCreateDate}', ${dlSync.fileId}, ${dlSync.repositoryId}, ${dlSync.parentFolderId}, '${dlSync.event}', '${dlSync.type}');

${sampleSQLBuilder.insertSecurity("com.liferay.portlet.documentlibrary.model.DLFolder", dlFolder.folderId)}

<#if (maxDLFileEntryCount > 0)>
	<#list 1..maxDLFileEntryCount as dlFileEntryCount>
		<#assign dlFileEntry = dataFactory.addDlFileEntry(dlFolder.groupId, dlFolder.companyId, dlFolder.userId, dlFolder.folderId, "txt", "text/plain", "TestFile" + stringUtil.valueOf(dlFileEntryCount), "TestFile" + dlFileEntryCount + ".txt", "")>

		${sampleSQLBuilder.insertDLFileEntry(dlFileEntry, ddmStructure)}

		<#assign createDate = dataFactory.getDateAsLong(dlFolder.createDate)>
		<#assign fileCreateDate = dataFactory.getDateAsLong(dlFileEntry.createDate)>

		${writerDocumentLibraryCSV.write(dlFolder.folderId + "," + dlFileEntry.name + "," + dlFileEntry.fileEntryId + "," + fileCreateDate + "," + createDate +"\n")}
	</#list>
</#if>