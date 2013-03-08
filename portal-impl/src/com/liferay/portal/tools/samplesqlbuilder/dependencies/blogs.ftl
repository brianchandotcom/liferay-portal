<#if (maxBlogsEntryCount > 0)>
	<#list 1..maxBlogsEntryCount as blogsEntryCount>
		<#assign blogsEntry = dataFactory.newBlogsEntry(groupId, blogsEntryCount)>

		${sampleSQLBuilder.insertBlogsEntry(blogsEntry)}

		<#assign mbThreadId = counter.get()>
		<#assign mbRootMessageId = counter.get()>

		${sampleSQLBuilder.insertMBDiscussion(groupId, dataFactory.blogsEntryClassNameId, blogsEntry.entryId, mbThreadId, mbRootMessageId, maxBlogsEntryCommentCount)}

		${writerBlogsCSV.write(blogsEntry.entryId + "," + blogsEntry.urlTitle + "," + mbThreadId + "," + mbRootMessageId + "\n")}
	</#list>
</#if>