<#list dataFactory.newSegmentsEntries(guestGroupModel.groupId) as segmentEntry>
	${dataFactory.toInsertSQL(segmentEntry)}

	${dataFactory.toInsertSQL(dataFactory.newSegmentsEntryResourcePermissionModel(segmentEntry))}

	${csvFileWriter.write("segments", segmentEntry.segmentsEntryId + ", "+ segmentEntry.name + "\n")}
</#list>