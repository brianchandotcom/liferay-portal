<#list dataFactory.getSequence(dataFactory.maxObjectEntryPageCount) as objectEntryPageCount>
	<#include "custom_object_definitions.ftl">

	<#assign
		name = objectDefinitionModel.getName()

		contentLayoutModels = dataFactory.newContentPageLayoutModels(groupId, name)

		segmentsExperienceModel = dataFactory.newSegmentsExperienceModel(contentLayoutModels)

		fragmentEntryLinkModels = dataFactory.newObjectFieldsFragmentEntryLinkModels(contentLayoutModels, objectFieldModels, segmentsExperienceModel.getSegmentsExperienceId())
	/>

	<#list fragmentEntryLinkModels as fragmentEntryLinkModel>
		${dataFactory.toInsertSQL(fragmentEntryLinkModel)}
	</#list>

	${dataFactory.toInsertSQL(segmentsExperienceModel)}

	<#list contentLayoutModels as contentLayoutModel>
		<#assign layoutPageTemplateStructureModel = dataFactory.newLayoutPageTemplateStructureModel(contentLayoutModel) />

		${dataFactory.toInsertSQL(contentLayoutModel)}

		${dataFactory.toInsertSQL(dataFactory.newLayoutFriendlyURLModel(contentLayoutModel))}

		${dataFactory.toInsertSQL(layoutPageTemplateStructureModel)}

		${dataFactory.toInsertSQL(dataFactory.newObjectDefinitionLayoutPageTemplateStructureRelModel(fragmentEntryLinkModels, layoutPageTemplateStructureModel, objectDefinitionModel))}

		 <#if contentLayoutModel.friendlyURL?contains(name)>
			${csvFileWriter.write("objectDefinition", virtualHostModel.hostname + "," + groupModel.friendlyURL + "," + contentLayoutModel.getFriendlyURL() + "\n")}
		</#if>
	</#list>
</#list>