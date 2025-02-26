<#list dataFactory.getSequence(dataFactory.maxObjectEntryPageCount) as objectEntryPageCount>
	<#include "custom_object_definitions.ftl">

	<#assign
		name = objectDefinitionModel.getName()

		contentLayoutModels = dataFactory.newContentPageLayoutModels(groupId, name)

		segmentsExperienceModel = dataFactory.newSegmentsExperienceModel(contentLayoutModels)
	/>

	${dataFactory.toInsertSQL(segmentsExperienceModel)}

	<#list contentLayoutModels as contentLayoutModel>
		<#assign
			fragmentEntryLinkModels = dataFactory.newObjectFieldsFragmentEntryLinkModels(contentLayoutModel, objectFieldModels, segmentsExperienceModel.getSegmentsExperienceId())

			layoutPageTemplateStructureModel = dataFactory.newLayoutPageTemplateStructureModel(contentLayoutModel)
		/>

		${dataFactory.toInsertSQL(contentLayoutModel)}

		${dataFactory.toInsertSQL(dataFactory.newLayoutFriendlyURLModel(contentLayoutModel))}

		<#list fragmentEntryLinkModels as fragmentEntryLinkModel>
			${dataFactory.toInsertSQL(fragmentEntryLinkModel)}
		</#list>

		${dataFactory.toInsertSQL(layoutPageTemplateStructureModel)}

		${dataFactory.toInsertSQL(dataFactory.newObjectDefinitionLayoutPageTemplateStructureRelModel(fragmentEntryLinkModels, layoutPageTemplateStructureModel, objectDefinitionModel))}

		 <#if contentLayoutModel.friendlyURL?contains(name)>
			${csvFileWriter.write("objectDefinition", virtualHostModel.hostname + "," + groupModel.friendlyURL + "," + contentLayoutModel.getFriendlyURL() + "\n")}
		</#if>
	</#list>
</#list>