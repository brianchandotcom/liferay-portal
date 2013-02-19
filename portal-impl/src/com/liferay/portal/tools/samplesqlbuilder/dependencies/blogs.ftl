<#if (maxBlogsEntryCount > 0)>
	<#list 1..maxBlogsEntryCount as blogsEntryCount>
		<#assign blogsEntry = dataFactory.addBlogsEntry(groupId, firstUserId, "Test Blog " + blogsEntryCount, "testblog" + blogsEntryCount, "This is a test blog " + blogsEntryCount + ".")>

		${sampleSQLBuilder.insertBlogsEntry(blogsEntry)}

		<#assign mbCategoryId = -1>
		<#assign mbThreadId = counter.get()>
		<#assign mbRootMessageId = counter.get()>

		<#assign mbRootMessage = dataFactory.addMBMessage(mbRootMessageId, groupId, firstUserId, dataFactory.blogsEntryClassNameId, blogsEntry.entryId, mbCategoryId, mbThreadId, mbRootMessageId, 0, stringUtil.valueOf(blogsEntry.entryId), stringUtil.valueOf(blogsEntry.entryId))>

		${sampleSQLBuilder.insertMBMessage(mbRootMessage)}

		<#assign mbThread = dataFactory.addMBThread(mbThreadId, groupId, companyId, mbCategoryId, mbRootMessage.messageId, maxBlogsEntryCommentCount, firstUserId)>

		insert into MBThread values (${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, 0, ${mbThread.lastPostByUserId}, CURRENT_TIMESTAMP, 0, FALSE, 0, ${mbThread.lastPostByUserId}, '', CURRENT_TIMESTAMP);

		<#if (maxBlogsEntryCommentCount > 0)>
			<#list 1..maxBlogsEntryCommentCount as blogsEntryCommentCount>
				<#assign mbMessage = dataFactory.addMBMessage(counter.get(), groupId, firstUserId, dataFactory.blogsEntryClassNameId, blogsEntry.entryId, mbCategoryId, mbThreadId, mbRootMessage.messageId, mbRootMessage.messageId, "N/A", "This is a test comment " + blogsEntryCommentCount + ".")>

				${sampleSQLBuilder.insertMBMessage(mbMessage)}
			</#list>
		</#if>

		<#assign mbDiscussion = dataFactory.addMBDiscussion(dataFactory.blogsEntryClassNameId, blogsEntry.entryId, mbThreadId)>

		insert into MBDiscussion values (${mbDiscussion.discussionId}, ${mbDiscussion.classNameId}, ${mbDiscussion.classPK}, ${mbDiscussion.threadId});

		${writerBlogsCSV.write(blogsEntry.entryId + "," + blogsEntry.urlTitle + "," + mbThreadId + "," + mbRootMessageId + "\n")}
	</#list>
</#if>