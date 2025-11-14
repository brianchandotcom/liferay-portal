<#list dataFactory.getSequence(dataFactory.maxObjectEntryPageCount) as objectEntryPageCount>
	<#include "custom_object_definitions.ftl">

	<#assign
		friendlyURLs = []
		name = objectDefinitionModel.getName()
		plid = ""
		segmentsExperienceId = ""
	/>

	<#list dataFactory.getObjectLayoutDataItemTypes() as layoutDataItemType>
		<#assign
			contentLayoutModels = dataFactory.newContentPageLayoutModels(groupId, name + "_" +layoutDataItemType)
			segmentsExperienceModels = dataFactory.newSegmentsExperienceModels(contentLayoutModels)
			fragmentEntryLinkModels = dataFactory.newObjectFieldsFragmentEntryLinkModels(layoutDataItemType, contentLayoutModels, objectFieldModels, segmentsExperienceModels)
		/>

		<#list fragmentEntryLinkModels as fragmentEntryLinkModel>
			${dataFactory.toInsertSQL(fragmentEntryLinkModel)}
		</#list>

		<#list segmentsExperienceModels as segmentsExperienceModel>
			${dataFactory.toInsertSQL(segmentsExperienceModel)}
		</#list>

		<#list contentLayoutModels as contentLayoutModel>
			<#assign layoutPageTemplateStructureModel = dataFactory.newLayoutPageTemplateStructureModel(contentLayoutModel) />

			${dataFactory.toInsertSQL(contentLayoutModel)}

			${dataFactory.toInsertSQL(dataFactory.newLayoutFriendlyURLModel(contentLayoutModel))}

			${dataFactory.toInsertSQL(layoutPageTemplateStructureModel)}

			<#assign layoutPageTemplateStructureRelModel = dataFactory.newObjectDefinitionLayoutPageTemplateStructureRelModel(fragmentEntryLinkModels, layoutDataItemType, contentLayoutModel, layoutPageTemplateStructureModel, objectDefinitionModel) />

			${dataFactory.toInsertSQL(layoutPageTemplateStructureRelModel)}

			<#if contentLayoutModel.friendlyURL?contains(name?c_lower_case)>
				<#assign friendlyURLs = friendlyURLs + [contentLayoutModel.getFriendlyURL()] />

				<#if layoutDataItemType == 'form'>
					 <#assign
						plid = contentLayoutModel.getPlid()?string
						segmentsExperienceId = layoutPageTemplateStructureRelModel.getSegmentsExperienceId()?string
					 />
				</#if>
			</#if>
		</#list>
	</#list>

	${csvFileWriter.write("objectDefinition",virtualHostModel.hostname + "," + groupModel.friendlyURL + "," + friendlyURLs?join(",") + "," + dataFactory.getClassNameId(objectDefinitionModel.getClassName()) + "," + dataFactory.getDefaultListTypeEntryKey() + "," + groupId + "," + plid + "," + segmentsExperienceId + "\n")}
</#list>