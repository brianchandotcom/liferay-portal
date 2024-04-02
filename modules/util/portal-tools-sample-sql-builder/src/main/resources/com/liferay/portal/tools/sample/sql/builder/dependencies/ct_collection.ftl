<#assign ctSchemaVersionModel = dataFactory.newCTSchemaVersionModel() />

${dataFactory.toInsertSQL(ctSchemaVersionModel)}

<#assign publicationGroupModel = dataFactory.newPublicationGroupModel() />
${dataFactory.toInsertSQL(publicationGroupModel)}

<#list dataFactory.newCTCollectionModels(ctSchemaVersionModel) as ctCollectionModel>
	${dataFactory.toInsertSQL(ctCollectionModel)}

	<#list dataFactory.newCTCollectionContentPageLayoutModels(ctCollectionModel.ctCollectionId, publicationGroupModel.groupId) as ctCollectionContentPageLayoutModel>
		${dataFactory.toInsertSQL(ctCollectionContentPageLayoutModel)}

		<#assign ctEntryModel = dataFactory.newCTEntryModel(ctCollectionModel.ctCollectionId, ctCollectionContentPageLayoutModel, dataFactory.ctChangeTypeAddition) />

		${dataFactory.toInsertSQL(ctEntryModel)}
	</#list>
</#list>