<#setting number_format = "0">

${writerRepositoryCSV.write(group.groupId + ", " + group.name + "\n")}

${sampleSQLBuilder.insertDLFolders(groupId, 0, 1, dataFactory.defaultDLDDMStructure)}