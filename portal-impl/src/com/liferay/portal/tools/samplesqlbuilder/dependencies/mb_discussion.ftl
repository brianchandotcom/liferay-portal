<#setting number_format = "0">

<#assign mbCategoryId = -1>

<#assign mbRootMessage = dataFactory.addMBMessage(mbRootMessageId, groupId, userId, classNameId, classPK, mbCategoryId, mbThreadId, mbRootMessageId, 0, stringUtil.valueOf(classPK), stringUtil.valueOf(classPK))>

${sampleSQLBuilder.insertMBMessage(mbRootMessage)}

<#assign messageCount = maxCommentCount>

<#if (maxCommentCount = 0)>
	<#assign messageCount = 1>
</#if>

<#assign mbThread = dataFactory.addMBThread(mbThreadId, groupId, companyId, mbCategoryId, mbRootMessageId, messageCount, userId)>

insert into MBThread values (${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, 0, ${mbThread.lastPostByUserId}, CURRENT_TIMESTAMP, 0, FALSE, 0, ${mbThread.lastPostByUserId}, '', CURRENT_TIMESTAMP);

<#if (maxCommentCount > 0)>
	<#list 1..maxCommentCount as commentCount>
		<#assign mbMessage = dataFactory.addMBMessage(counter.get(), groupId, userId, classNameId, classPK, mbCategoryId, mbThreadId, mbRootMessageId, mbRootMessageId, "N/A", "This is a test comment " + commentCount + ".")>

		${sampleSQLBuilder.insertMBMessage(mbMessage)}
	</#list>
</#if>

<#assign mbDiscussion = dataFactory.addMBDiscussion(classNameId, classPK, mbThreadId)>

insert into MBDiscussion values (${mbDiscussion.discussionId}, ${mbDiscussion.classNameId}, ${mbDiscussion.classPK}, ${mbDiscussion.threadId});