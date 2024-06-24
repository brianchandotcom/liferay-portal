<#list dataFactory.newCTCollectionContentPageLayoutModels(ctCollectionId, publicationGroupId) as ctCollectionContentPageLayoutModel>
	${dataFactory.toInsertSQL(ctCollectionContentPageLayoutModel)}

	${dataFactory.toInsertSQL(dataFactory.newCTEntryModel(ctCollectionId, ctCollectionContentPageLayoutModel, dataFactory.ctChangeTypeAddition))}
</#list>