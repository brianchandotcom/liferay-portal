<#setting number_format = "0">

<#assign mbThread = dataFactory.newMBThread(mbThreadId, groupId, mbRootMessageId, maxCommentCount)>

insert into MBThread values ('${mbThread.uuid}', ${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.userId}, '${mbThread.userName}', '${dataFactory.getDateString(mbThread.createDate)}', '${dataFactory.getDateString(mbThread.modifiedDate)}', ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, ${mbThread.viewCount}, ${mbThread.lastPostByUserId}, '${dataFactory.getDateString(mbThread.lastPostDate)}', ${mbThread.priority}, ${mbThread.question?string}, ${mbThread.status}, ${mbThread.statusByUserId}, '${mbThread.statusByUserName}', '${dataFactory.getDateString(mbThread.statusDate)}');

<#assign mbRootMessage = dataFactory.newMBMessage(mbThread, classNameId, classPK, 0)>

${sampleSQLBuilder.insertMBMessage(mbRootMessage)}

<#if (maxCommentCount > 0)>
	<#list 1..maxCommentCount as commentCount>
		<#assign mbMessage = dataFactory.newMBMessage(mbThread, classNameId, classPK, commentCount)>

		${sampleSQLBuilder.insertMBMessage(mbMessage)}
	</#list>
</#if>

<#assign mbDiscussion = dataFactory.newMBDiscussion(classNameId, classPK, mbThreadId)>

insert into MBDiscussion values (${mbDiscussion.discussionId}, ${mbDiscussion.classNameId}, ${mbDiscussion.classPK}, ${mbDiscussion.threadId});