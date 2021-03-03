<#assign defaultJournalDDMStructureModel = dataFactory.newDefaultJournalDDMStructureModel() />

<@insertDDMStructure
	_ddmStructureLayoutModel=dataFactory.newDefaultJournalDDMStructureLayoutModel()
	_ddmStructureModel=defaultJournalDDMStructureModel
	_ddmStructureVersionModel=dataFactory.newDefaultJournalDDMStructureVersionModel(defaultJournalDDMStructureModel)
/>

<#assign defaultJournalDDMTemplateModel = dataFactory.newDefaultJournalDDMTemplateModel() />

${dataFactory.toInsertSQL(defaultJournalDDMTemplateModel)}

${dataFactory.toInsertSQL(dataFactory.newDefaultJournalDDMTemplateVersionModel())}

<#assign defaultDLDDMStructureModel = dataFactory.newDefaultDLDDMStructureModel() />

<@insertDDMStructure
	_ddmStructureLayoutModel=dataFactory.newDefaultDLDDMStructureLayoutModel()
	_ddmStructureModel=defaultDLDDMStructureModel
	_ddmStructureVersionModel=dataFactory.newDefaultDLDDMStructureVersionModel(defaultDLDDMStructureModel)
/>