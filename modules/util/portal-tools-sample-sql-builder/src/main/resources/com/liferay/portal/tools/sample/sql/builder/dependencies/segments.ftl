<#assign segmentEntries = dataFactory.newSegmentsEntries(groupId) />

<#list segmentEntries as segmentEntry>
	${dataFactory.toInsertSQL(segmentEntry)}

	${dataFactory.toInsertSQL(dataFactory.newSegmentsEntryResourcePermissionModel(segmentEntry))}

	${csvFileWriter.write("segments", segmentEntry.segmentsEntryId + ", "+ segmentEntry.name + "\n")}
</#list>