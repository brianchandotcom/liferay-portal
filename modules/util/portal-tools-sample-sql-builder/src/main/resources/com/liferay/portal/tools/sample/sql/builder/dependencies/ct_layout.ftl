<#list dataFactory.newCTCollectionContentPageLayoutModels(ctCollectionId, publicationGroupId) as ctCollectionContentPageLayoutModel>
	${dataFactory.toInsertSQL(ctCollectionContentPageLayoutModel)}

	<#assign ctEntryModel = dataFactory.newCTEntryModel(ctCollectionId, ctCollectionContentPageLayoutModel, dataFactory.ctChangeTypeAddition) />
	${dataFactory.toInsertSQL(ctEntryModel)}
</#list>