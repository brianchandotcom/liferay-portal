<#assign segmentEntries = dataFactory.newSegmentsEntries(groupId) />

<#list segmentEntries as segmentEntry>
	${dataFactory.toInsertSQL(segmentEntry)}

	${csvFileWriter.write("segments", segmentEntry.segmentsEntryId + ", "+ segmentEntry.name + "\n")}
</#list>