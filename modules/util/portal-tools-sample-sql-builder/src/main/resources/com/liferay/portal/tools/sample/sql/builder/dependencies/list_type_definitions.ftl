<#list dataFactory.newListTypeDefinitionModels() as listTypeDefinitionModel>

	${dataFactory.toInsertSQL(listTypeDefinitionModel)}

	<#list dataFactory.newListTypeEntryModels(listTypeDefinitionModel.getListTypeDefinitionId()) as listTypeEntryModel>
		${dataFactory.toInsertSQL(listTypeEntryModel)}
	</#list>
</#list>