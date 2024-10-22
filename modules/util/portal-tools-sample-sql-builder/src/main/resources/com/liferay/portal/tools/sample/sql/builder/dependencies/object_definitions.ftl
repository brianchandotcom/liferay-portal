<#assign
	objectDefinitionModel = dataFactory.newObjectDefinitionModel()
/>

${dataFactory.toInsertSQL(objectDefinitionModel)}

${dataFactory.toInsertSQL(dataFactory.newObjectActionModel(objectDefinitionModel.getObjectDefinitionId()))}