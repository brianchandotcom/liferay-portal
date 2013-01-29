<#setting number_format = "0">

${writerRepositoryCSV.write(group.groupId + ", " + group.name + "\n")}

<#assign ddmStructure = dataFactory.addDDMStructureForDL(groupId)>

insert into DDMStructure values ('${ddmStructure.uuid}', ${ddmStructure.structureId}, ${ddmStructure.groupId}, ${ddmStructure.companyId}, ${ddmStructure.userId}, '${ddmStructure.userName}', '${ddmStructure.createDate?datetime}', '${ddmStructure.modifiedDate?datetime}', ${ddmStructure.parentStructureId}, ${ddmStructure.classNameId}, '${ddmStructure.structureKey}', '${ddmStructure.name}', '${ddmStructure.description}', '${ddmStructure.xsd}', '${ddmStructure.storageType}', ${ddmStructure.type});

<#assign ddmStructureId = ddmStructure.structureId>

<#if (maxDLFolderCount > 0)>
	${sampleSQLBuilder.insertDLFolders(groupId, 0, 1, ddmStructureId)}
</#if>