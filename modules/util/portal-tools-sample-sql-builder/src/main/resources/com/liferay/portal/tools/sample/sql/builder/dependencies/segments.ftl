<#list dataFactory.newSegmentsEntries(guestGroupModel.groupId) as segmentEntry>
	${dataFactory.toInsertSQL(segmentEntry)}

	${csvFileWriter.write("segments", segmentEntry.segmentsEntryId + ", "+ segmentEntry.name + "\n")}
</#list>