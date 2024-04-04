<#assign ctSchemaVersionModel = dataFactory.newCTSchemaVersionModel() />

${dataFactory.toInsertSQL(ctSchemaVersionModel)}

<#list dataFactory.newCTCollectionModels(ctSchemaVersionModel) as ctCollectionModel>
	${dataFactory.toInsertSQL(ctCollectionModel)}

	<#list dataFactory.newCTCollectionContentPageLayoutModels(ctCollectionModel.ctCollectionId, dataFactory.globalGroupId) as ctCollectionContentPageLayoutModel>
		${dataFactory.toInsertSQL(ctCollectionContentPageLayoutModel)}

		<#assign ctEntryModel = dataFactory.newCTEntryModel(ctCollectionModel.ctCollectionId, ctCollectionContentPageLayoutModel, dataFactory.ctChangeTypeAddition) />

		${dataFactory.toInsertSQL(ctEntryModel)}
	</#list>
</#list>