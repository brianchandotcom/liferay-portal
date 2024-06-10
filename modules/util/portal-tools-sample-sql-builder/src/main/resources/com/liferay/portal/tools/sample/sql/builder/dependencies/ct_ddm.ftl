<#assign defaultCTJournalDDMStructureModel = dataFactory.newDefaultJournalDDMStructureModel() />

<@insertDDMStructure
	_ctCollectionId = ctCollectionIdString
	_ddmStructureLayoutModel = dataFactory.newDefaultJournalDDMStructureLayoutModel()
	_ddmStructureModel = defaultCTJournalDDMStructureModel
	_ddmStructureVersionModel = dataFactory.newDefaultJournalDDMStructureVersionModel(defaultCTJournalDDMStructureModel)
/>

<#assign defaultCTJournalDDMTemplateModel = dataFactory.newDefaultJournalDDMTemplateModel() />

${dataFactory.toCTCollectionInsertSQL(defaultCTJournalDDMTemplateModel, ctCollectionId)}

${dataFactory.toCTCollectionInsertSQL(dataFactory.newDefaultJournalDDMTemplateVersionModel(), ctCollectionId)}

<#assign defaultCTDLDDMStructureModel = dataFactory.newDefaultDLDDMStructureModel() />

<@insertDDMStructure
	_ctCollectionId = ctCollectionIdString
	_ddmStructureLayoutModel = dataFactory.newDefaultDLDDMStructureLayoutModel()
	_ddmStructureModel = defaultCTDLDDMStructureModel
	_ddmStructureVersionModel = dataFactory.newDefaultDLDDMStructureVersionModel(defaultCTDLDDMStructureModel)
/>