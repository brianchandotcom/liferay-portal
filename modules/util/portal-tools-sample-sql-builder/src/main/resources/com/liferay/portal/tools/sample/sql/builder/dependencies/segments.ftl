<#assign
	segmentsEntryModels = dataFactory.newSegmentsEntryModels(guestGroupModel.groupId)
/>

<#list segmentsEntryModels as segmentsEntryModel>
	${dataFactory.toInsertSQL(segmentsEntryModel)}
</#list>

<#list dataFactory.getSequence(dataFactory.maxSegmentsEntrySegmentsExperienceCount) as i>
	<#assign
		layoutModel = dataFactory.newLayoutModel(guestGroupModel.groupId, "segments_experience_layout_" + i, "", "")
	/>

	${dataFactory.toInsertSQL(layoutModel)}

	<#list segmentsEntryModels as segmentsEntryModel>
		${dataFactory.toInsertSQL(dataFactory.newSegmentsExperienceModel(guestGroupModel.groupId, segmentsEntryModel.segmentsEntryId, layoutModel.plid))}
	</#list>
</#list>