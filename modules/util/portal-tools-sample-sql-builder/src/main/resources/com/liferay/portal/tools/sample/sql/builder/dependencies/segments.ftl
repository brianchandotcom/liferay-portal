<#assign
	segmentEntries = dataFactory.newSegmentsEntries(guestGroupModel.groupId)
/>

<#list segmentEntries as segmentEntry>
	${dataFactory.toInsertSQL(segmentEntry)}
</#list>

<#list dataFactory.getSequence(dataFactory.maxSegmentsEntrySegmentsExperienceCount) as i>
	<#assign
		layoutModel = dataFactory.newLayoutModel(guestGroupModel.groupId, "segments_experience_layout_" + i, "", "")
	/>

	${dataFactory.toInsertSQL(layoutModel)}

	<#list segmentEntries as segmentEntry>
		${dataFactory.toInsertSQL(dataFactory.newSegmentsExperience(guestGroupModel.groupId, layoutModel.plid, segmentEntry.segmentsEntryId))}
	</#list>
</#list>