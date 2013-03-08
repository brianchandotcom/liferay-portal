<#if (maxMBCategoryCount > 0)>
	<#list 1..maxMBCategoryCount as mbCategoryCount>
		<#assign mbCategory = dataFactory.newMBCategory(groupId, mbCategoryCount)>

		${sampleSQLBuilder.insertMBCategory(mbCategory)}

		<#if (maxMBThreadCount > 0) && (maxMBMessageCount > 0)>
			<#list 1..maxMBThreadCount as mbThreadCount>
				<#assign mbThread = dataFactory.newMBThread(mbCategory)>

				insert into MBThread values ('${mbThread.uuid}', ${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.userId}, '${mbThread.userName}', '${dataFactory.getDateString(mbThread.createDate)}', '${dataFactory.getDateString(mbThread.modifiedDate)}', ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, ${mbThread.viewCount}, ${mbThread.lastPostByUserId}, '${dataFactory.getDateString(mbThread.lastPostDate)}', ${mbThread.priority}, ${mbThread.question?string}, ${mbThread.status}, ${mbThread.statusByUserId}, '${mbThread.statusByUserName}', '${dataFactory.getDateString(mbThread.statusDate)}');

				<#list 1..maxMBMessageCount as mbMessageCount>
					<#assign mbMessage = dataFactory.newMBMessage(mbThread, mbMessageCount)>

					${sampleSQLBuilder.insertMBMessage(mbMessage)}
				</#list>

				${writerMessageBoardsCSV.write(mbCategory.categoryId + "," + mbThread.threadId + "," + mbThread.rootMessageId + "\n")}
			</#list>
		</#if>
	</#list>
</#if>