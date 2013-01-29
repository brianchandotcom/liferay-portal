<#if (maxBlogsEntryCount > 0)>
	<#list 1..maxBlogsEntryCount as blogsEntryCount>
		<#assign blogsEntry = dataFactory.addBlogsEntry(groupId, blogsEntryCount)>

		${sampleSQLBuilder.insertBlogsEntry(blogsEntry)}

		<#assign mbRootMessage = dataFactory.addMBMessage(blogsEntry)>

		${sampleSQLBuilder.insertMBMessage(mbRootMessage)}

		<#assign mbThread = dataFactory.addMBThread(mbRootMessage, maxBlogsEntryCount)>

		insert into MBThread values (${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, ${mbThread.viewCount}, ${mbThread.lastPostByUserId}, '${mbThread.lastPostDate?datetime}', ${mbThread.priority}, ${mbThread.question?string}, ${mbThread.status}, ${mbThread.statusByUserId}, '${mbThread.statusByUserName}', ${mbThread.statusDate!'null'});

		<#if (maxBlogsEntryCommentCount > 0)>
			<#list 1..maxBlogsEntryCommentCount as blogsEntryCommentCount>
				<#assign mbMessage = dataFactory.addMBMessage(blogsEntry, mbRootMessage, blogsEntryCommentCount)>

				${sampleSQLBuilder.insertMBMessage(mbMessage)}
			</#list>
		</#if>

		<#assign mbDiscussion = dataFactory.addMBDiscussion(blogsEntry, mbThread.threadId)>

		insert into MBDiscussion values (${mbDiscussion.discussionId}, ${mbDiscussion.classNameId}, ${mbDiscussion.classPK}, ${mbDiscussion.threadId});

		${writerBlogsCSV.write(blogsEntry.entryId + "," + blogsEntry.urlTitle + "," + mbMessage.threadId + "," + mbMessage.messageId + ",")}

		<#if (blogsEntryCount < maxBlogsEntryCount)>
			${writerBlogsCSV.write("\n")}
		</#if>
	</#list>
</#if>