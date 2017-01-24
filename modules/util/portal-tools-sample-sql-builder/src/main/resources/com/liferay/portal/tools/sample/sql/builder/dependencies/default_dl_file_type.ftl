${dataFactory.toInsertSQL(dataFactory.defaultDLFileEntryTypeModel)}

<@insertDDMStructure
	_ddmStructureModel = dataFactory.defaultDLDDMStructureModel
	_ddmStructureLayoutModel = dataFactory.defaultDLDDMStructureLayoutModel
	_ddmStructureVersionModel = dataFactory.defaultDLDDMStructureVersionModel
/>