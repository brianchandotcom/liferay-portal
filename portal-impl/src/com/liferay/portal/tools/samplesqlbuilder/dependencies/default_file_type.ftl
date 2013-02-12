<#setting datetime_format = "yyyy-MM-dd HH:mm:ss">

<#assign dlFileEntryType = dataFactory.defaultDLFileEntryType>

insert into DLFileEntryType values ('${dlFileEntryType.uuid}', ${dlFileEntryType.fileEntryTypeId}, ${dlFileEntryType.groupId}, ${dlFileEntryType.companyId}, ${dlFileEntryType.userId}, '${dlFileEntryType.userName}', '${dlFileEntryType.createDate?datetime}', '${dlFileEntryType.modifiedDate?datetime}', '${dlFileEntryType.name}', '${dlFileEntryType.description}');