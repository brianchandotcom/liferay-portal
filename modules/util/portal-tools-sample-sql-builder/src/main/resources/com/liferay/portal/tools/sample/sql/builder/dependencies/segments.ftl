<#list dataFactory.getSequence(dataFactory.maxSegmentsEntryCount) as index>
	${dataFactory.toInsertSQL(dataFactory.newSegmentsEntry(guestGroupModel.groupId, index))}

	${csvFileWriter.write("segments", segmentEntry.segmentsEntryId + ", "+ segmentEntry.name + "\n")}
</#list>