<#assign
	layoutModel = dataFactory.newContentLayoutModel(groupId, objectDefinitionModel.getName(), null)
/>

${dataFactory.toInsertSQL(layoutModel)}

${dataFactory.toInsertSQL(dataFactory.newLayoutFriendlyURLModel(layoutModel))}

<#assign layoutPageTemplateStructureModel = dataFactory.newLayoutPageTemplateStructureModel(layoutModel) />

${dataFactory.toInsertSQL(layoutPageTemplateStructureModel)}

<#assign fragmentEntryLinkModels = dataFactory.newObjectFieldsFragmentEntryLinkModels(layoutModel, objectFieldModels) />

<#list fragmentEntryLinkModels as fragmentEntryLinkModel>
	${dataFactory.toInsertSQL(fragmentEntryLinkModel)}
</#list>

<#assign layoutPageTemplateStructureRelModel = dataFactory.newObjectDefinitionLayoutPageTemplateStructureRelModel(fragmentEntryLinkModels, layoutPageTemplateStructureModel, objectDefinitionModel) />

${dataFactory.toInsertSQL(layoutPageTemplateStructureRelModel)}

${csvFileWriter.write("objectDefinition", virtualHostModel.hostname + "," + groupModel.friendlyURL + "," + groupId + "," + objectDefinitionModel.getName() + "," + objectDefinitionModel.getObjectDefinitionId() + "\n")}