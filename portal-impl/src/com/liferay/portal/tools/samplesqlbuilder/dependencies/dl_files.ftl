<#setting number_format = "0">

<#if (maxFileFanOutCount > 0)>
	<#list 1..maxFileFanOutCount as fileFanOutCount>
		<#assign dlFileEntry = dataFactory.addDlFileEntry(dlFolder.groupId, dlFolder.companyId, dlFolder.userId, dlFolder.folderId, "txt", "text/plain", "TestFile" +  stringUtil.valueOf(fileFanOutCount), "TestFile" + fileFanOutCount + ".txt", "")>

		${sampleSQLBuilder.insertDLFile(dlFileEntry, ddmStructure)}

		${docmentLibraryCsvWriter.write(dlFolder.folderId + "," + dlFileEntry.name + "," + dlFileEntry.fileEntryId + "\n")}
	</#list>
</#if>