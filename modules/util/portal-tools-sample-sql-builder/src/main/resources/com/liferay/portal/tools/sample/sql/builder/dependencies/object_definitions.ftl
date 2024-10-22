<#assign
	objectFolderModel = dataFactory.newObjectFolderModel()
	objectDefinitionModel = dataFactory.newObjectDefinitionModel(objectFolderModel.objectFolderId)
/>

${dataFactory.toInsertSQL(objectFolderModel)}
${dataFactory.toInsertSQL(objectDefinitionModel)}