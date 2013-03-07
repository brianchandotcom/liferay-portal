<#if (maxMBCategoryCount > 0)>
	<#list 1..maxMBCategoryCount as mbCategoryCount>
		<#assign categoryId = counter.get()>

		<#assign mbCategory = dataFactory.newMBCategory(categoryId, groupId, companyId, sampleUserId, "Test Category " + mbCategoryCount, "This is a test category " + mbCategoryCount + ".", maxMBThreadCount, maxMBThreadCount * maxMBMessageCount)>

		${sampleSQLBuilder.insertMBCategory(mbCategory)}

		<#if (maxMBThreadCount > 0) && (maxMBMessageCount > 0)>
			<#list 1..maxMBThreadCount as mbThreadCount>
				<#assign threadId = counter.get()>
				<#assign rootMessageId = counter.get()>

				<#list 1..maxMBMessageCount as mbMessageCount>
					<#if (mbMessageCount = 1)>
						<#assign messageId = rootMessageId>
						<#assign parentMessageId = 0>
					<#else>
						<#assign messageId = counter.get()>
						<#assign parentMessageId = rootMessageId>
					</#if>

					<#assign mbMessage = dataFactory.newMBMessage(messageId, mbCategory.groupId, sampleUserId, 0, 0, categoryId, threadId, rootMessageId, parentMessageId, "Test Message " + mbMessageCount, "This is a test message " + mbMessageCount + ".")>

					${sampleSQLBuilder.insertMBMessage(mbMessage)}
				</#list>

				<#assign mbThread = dataFactory.newMBThread(threadId, mbCategory.groupId, companyId, categoryId, rootMessageId, maxMBCategoryCount, sampleUserId)>

				insert into MBThread values ('${mbThread.uuid}', ${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.userId}, '${mbThread.userName}', '${dataFactory.getDateString(mbThread.createDate)}', '${dataFactory.getDateString(mbThread.modifiedDate)}', ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, 0, ${mbThread.lastPostByUserId}, CURRENT_TIMESTAMP, 0, FALSE, 0, ${mbThread.lastPostByUserId}, '', CURRENT_TIMESTAMP);

				${writerMessageBoardsCSV.write(categoryId + "," + threadId + "," + rootMessageId + "\n")}
			</#list>
		</#if>
	</#list>
</#if>