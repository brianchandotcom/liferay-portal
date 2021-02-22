<#list dataFactory.getSequence(dataFactory.maxSegmentsEntryCount) as index>
	${dataFactory.toInsertSQL(dataFactory.newSegmentsEntry(guestGroupModel.groupId, index))}
</#list>