${dataFactory.toInsertSQL(dataFactory.newDLFileEntryTypeModel())}

<#assign defaultDLDDMStructureModel = dataFactory.newDefaultDLDDMStructureModel() />

<@insertDDMStructure
	_ddmStructureLayoutModel=dataFactory.newDefaultDLDDMStructureLayoutModel()
	_ddmStructureModel=defaultDLDDMStructureModel
	_ddmStructureVersionModel=dataFactory.newDefaultDLDDMStructureVersionModel(defaultDLDDMStructureModel)
/>