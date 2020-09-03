<#assign ddmStructureModel = dataFactory.newDefaultJournalDDMStructureModel() />

<@insertDDMStructure
	_ddmStructureLayoutModel=dataFactory.newDefaultJournalDDMStructureLayoutModel()
	_ddmStructureModel=ddmStructureModel
	_ddmStructureVersionModel=dataFactory.newDefaultJournalDDMStructureVersionModel(ddmStructureModel)
/>

<#assign ddmTemplateModel = dataFactory.newDefaultJournalDDMTemplateModel() />

${dataFactory.toInsertSQL(ddmTemplateModel)}

${dataFactory.toInsertSQL(dataFactory.newDefaultJournalDDMTemplateVersionModel())}