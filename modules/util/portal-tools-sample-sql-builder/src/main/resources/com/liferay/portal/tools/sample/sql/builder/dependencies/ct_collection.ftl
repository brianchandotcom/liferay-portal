<#assign ctSchemaVersionModel = dataFactory.newCTSchemaVersionModel() />
${dataFactory.toInsertSQL(ctSchemaVersionModel)}

<#list dataFactory.newCTCollectionModels(ctSchemaVersionModel) as ctCollectionModel>
	<#list dataFactory.newCTCollectionContentPageLayoutModels(ctCollectionModel.getCtCollectionId(), dataFactory.globalGroupId) as ctCollectionContentPageLayoutModel>
		${dataFactory.toInsertSQL(ctCollectionContentPageLayoutModel)}
	</#list>
</#list>