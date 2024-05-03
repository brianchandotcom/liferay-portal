<#assign listTypeDefinitionModel = dataFactory.newListTypeDefinitionModel() />

${dataFactory.toInsertSQL(listTypeDefinitionModel)}

<#list dataFactory.newListTypeEntryModels(listTypeDefinitionModel.getListTypeDefinitionId()) as listTypeEntryModel>
	${dataFactory.toInsertSQL(listTypeEntryModel)}
</#list>