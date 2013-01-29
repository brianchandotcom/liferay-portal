<#if (maxMBCategoryCount > 0)>
	<#list 1..maxMBCategoryCount as mbCategoryCount>
		<#assign mbCategory = dataFactory.addMBCategory(groupId, mbCategoryCount)>

		${sampleSQLBuilder.insertMBCategory(mbCategory)}

		<#if (maxMBThreadCount > 0) && (maxMBMessageCount > 0)>
			<#list 1..maxMBThreadCount as mbThreadCount>
				<#assign mbRootMessage = dataFactory.addMBMessage(mbCategory, 1)>

				${sampleSQLBuilder.insertMBMessage(mbRootMessage)}

				<#if (maxMBMessageCount > 1)>
					<#list 2..maxMBMessageCount as mbMessageCount>
						<#assign mbMessage = dataFactory.addMBMessage(mbCategory, mbRootMessage, mbMessageCount)>

						${sampleSQLBuilder.insertMBMessage(mbMessage)}
					</#list>
				</#if>

				<#assign mbThread = dataFactory.addMBThread(mbRootMessage, maxMBMessageCount)>

				insert into MBThread values (${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, ${mbThread.viewCount}, ${mbThread.lastPostByUserId}, '${mbThread.lastPostDate?datetime}', ${mbThread.priority}, ${mbThread.question?string}, ${mbThread.status}, ${mbThread.statusByUserId}, '${mbThread.statusByUserName}', ${mbThread.statusDate!'null'});

				${writerMessageBoardsCSV.write(mbThread.categoryId + "," + mbThread.threadId + "," + mbThread.rootMessageId + "\n")}
			</#list>
		</#if>
	</#list>
</#if>