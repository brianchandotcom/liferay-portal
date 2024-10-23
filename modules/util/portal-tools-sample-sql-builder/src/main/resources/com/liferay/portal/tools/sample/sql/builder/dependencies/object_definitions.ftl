<#assign
	objectFolderModel = dataFactory.newObjectFolderModel()

	objectDefinitionModel = dataFactory.newObjectDefinitionModel(objectFolderModel.objectFolderId)
/>

${dataFactory.toInsertSQL(objectDefinitionModel)}

${dataFactory.toInsertSQL(dataFactory.newObjectFieldModel(objectDefinitionModel.titleObjectFieldId, objectDefinitionModel.objectDefinitionId))}

${dataFactory.toInsertSQL(objectFolderModel)}