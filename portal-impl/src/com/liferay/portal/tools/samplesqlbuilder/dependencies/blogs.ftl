<#if (maxBlogsEntryCount > 0)>
	<#list 1..maxBlogsEntryCount as blogsEntryCount>
		<#assign blogsEntry = dataFactory.addBlogsEntry(groupId, firstUserId, "Test Blog " + blogsEntryCount, "testblog" + blogsEntryCount, "This is a test blog " + blogsEntryCount + ".")>

		${sampleSQLBuilder.insertBlogsEntry(blogsEntry)}

		<#assign mbCompanyId = 0>
		<#assign mbGroupId = 0>
		<#assign mbUserId = blogsEntry.userId>
		<#assign mbCategoryId = 0>
		<#assign mbThreadId = counter.get()>

		<#assign mbRootMessage = dataFactory.addMBMessage(counter.get(), mbGroupId, mbUserId, dataFactory.blogsEntryClassName.classNameId, blogsEntry.entryId, mbCategoryId, mbThreadId, 0, 0, stringUtil.valueOf(blogsEntry.entryId), stringUtil.valueOf(blogsEntry.entryId))>

		${sampleSQLBuilder.insertMBMessage(mbRootMessage)}

		<#assign mbThread = dataFactory.addMBThread(mbThreadId, mbGroupId, companyId, mbCategoryId, mbRootMessage.messageId, maxBlogsEntryCommentCount, mbUserId)>

		insert into MBThread values (${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, 0, ${mbThread.lastPostByUserId}, CURRENT_TIMESTAMP, 0, FALSE, 0, ${mbThread.lastPostByUserId}, '', CURRENT_TIMESTAMP);

		<#if (maxBlogsEntryCommentCount > 0)>
			<#list 1..maxBlogsEntryCommentCount as blogsEntryCommentCount>
				<#assign mbMessage = dataFactory.addMBMessage(counter.get(), mbGroupId, mbUserId, dataFactory.blogsEntryClassName.classNameId, blogsEntry.entryId, mbCategoryId, mbThreadId, mbRootMessage.messageId, mbRootMessage.messageId, "N/A", "This is a test comment " + blogsEntryCommentCount + ".")>

				${sampleSQLBuilder.insertMBMessage(mbMessage)}
			</#list>
		</#if>

		<#assign mbDiscussion = dataFactory.addMBDiscussion(dataFactory.blogsEntryClassName.classNameId, blogsEntry.entryId, mbThreadId)>

		insert into MBDiscussion values (${mbDiscussion.discussionId}, ${mbDiscussion.classNameId}, ${mbDiscussion.classPK}, ${mbDiscussion.threadId});

		${writerBlogsCSV.write(blogsEntry.entryId + "," + blogsEntry.urlTitle + "," + mbMessage.threadId + "," + mbMessage.messageId + ",")}

		<#if (blogsEntryCount < maxBlogsEntryCount)>
			${writerBlogsCSV.write("\n")}
		</#if>
	</#list>
</#if>