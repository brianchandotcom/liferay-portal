<#assign
	layoutModel = dataFactory.newContentLayoutModel(groupId, objectDefinitionModel.getName(), null)

	fragmentEntryLinkModels = dataFactory.newObjectFieldsFragmentEntryLinkModels(layoutModel, objectFieldModels)
	layoutPageTemplateStructureModel = dataFactory.newLayoutPageTemplateStructureModel(layoutModel)
/>

${dataFactory.toInsertSQL(layoutModel)}

${dataFactory.toInsertSQL(dataFactory.newLayoutFriendlyURLModel(layoutModel))}

<#list fragmentEntryLinkModels as fragmentEntryLinkModel>
	${dataFactory.toInsertSQL(fragmentEntryLinkModel)}
</#list>

${dataFactory.toInsertSQL(layoutPageTemplateStructureModel)}

${dataFactory.toInsertSQL(dataFactory.newObjectDefinitionLayoutPageTemplateStructureRelModel(fragmentEntryLinkModels, layoutPageTemplateStructureModel, objectDefinitionModel))}

${csvFileWriter.write("objectDefinition", virtualHostModel.hostname + "," + groupModel.friendlyURL + "," + groupId + "," + objectDefinitionModel.getName() + "," + objectDefinitionModel.getObjectDefinitionId() + "\n")}