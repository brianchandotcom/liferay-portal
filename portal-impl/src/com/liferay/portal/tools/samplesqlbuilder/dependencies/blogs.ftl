<#if (maxBlogsEntryCount > 0)>
	<#list 1..maxBlogsEntryCount as blogsEntryCount>
		<#assign blogsEntry = dataFactory.addBlogsEntry(groupId, firstUserId, "Test Blog " + blogsEntryCount, "testblog" + blogsEntryCount, "This is a test blog " + blogsEntryCount + ".")>

		${sampleSQLBuilder.insertBlogsEntry(blogsEntry)}

		<#assign mbThreadId = counter.get()>
		<#assign mbRootMessageId = counter.get()>

		${sampleSQLBuilder.insertMBDiscussion(groupId, firstUserId, mbRootMessageId, mbThreadId, dataFactory.blogsEntryClassNameId, blogsEntry.entryId, maxBlogsEntryCommentCount)}

		${writerBlogsCSV.write(blogsEntry.entryId + "," + blogsEntry.urlTitle + "," + mbThreadId + "," + mbRootMessageId + "\n")}
	</#list>
</#if>