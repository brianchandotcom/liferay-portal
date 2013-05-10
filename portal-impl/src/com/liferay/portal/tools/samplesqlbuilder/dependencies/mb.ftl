<#if (maxMBCategoryCount > 0)>
	<#list 1..maxMBCategoryCount as mbCategoryCount>
		<#assign mbCategory = dataFactory.newMBCategory(groupId, mbCategoryCount)>

		insert into MBCategory values ('${mbCategory.uuid}', ${mbCategory.categoryId}, ${mbCategory.groupId}, ${mbCategory.companyId}, ${mbCategory.userId}, '${mbCategory.userName}', '${dataFactory.getDateString(mbCategory.createDate)}', '${dataFactory.getDateString(mbCategory.modifiedDate)}', ${mbCategory.parentCategoryId}, '${mbCategory.name}', '${mbCategory.description}', '${mbCategory.displayStyle}', ${mbCategory.threadCount}, ${mbCategory.messageCount}, '${dataFactory.getDateString(mbCategory.lastPostDate)}', ${mbCategory.status}, ${mbCategory.statusByUserId}, '${mbCategory.statusByUserName}', '${dataFactory.getDateString(mbCategory.statusDate)}');

		<@insertResourcePermissions
			_entry = mbCategory
		/>

		<#assign mbMailingList = dataFactory.newMBMailingList(mbCategory)>

		insert into MBMailingList values ('${mbMailingList.uuid}', ${mbMailingList.mailingListId}, ${mbMailingList.groupId}, ${mbMailingList.companyId}, ${mbMailingList.userId}, '${mbMailingList.userName}', '${dataFactory.getDateString(mbMailingList.createDate)}', '${dataFactory.getDateString(mbMailingList.modifiedDate)}', ${mbMailingList.categoryId}, '${mbMailingList.emailAddress}', '${mbMailingList.inProtocol}', '${mbMailingList.inServerName}', ${mbMailingList.inServerPort}, ${mbMailingList.inUseSSL?string}, '${mbMailingList.inUserName}', '${mbMailingList.inPassword}', ${mbMailingList.inReadInterval}, '${mbMailingList.outEmailAddress}', ${mbMailingList.outCustom?string}, '${mbMailingList.outServerName}', ${mbMailingList.outServerPort}, ${mbMailingList.outUseSSL?string}, '${mbMailingList.outUserName}', '${mbMailingList.outPassword}', ${mbMailingList.allowAnonymous?string}, ${mbMailingList.active?string});

		<#if (maxMBThreadCount > 0) && (maxMBMessageCount > 0)>
			<#list 1..maxMBThreadCount as mbThreadCount>
				<#assign mbThread = dataFactory.newMBThread(mbCategory)>

				insert into MBThread values ('${mbThread.uuid}', ${mbThread.threadId}, ${mbThread.groupId}, ${mbThread.companyId}, ${mbThread.userId}, '${mbThread.userName}', '${dataFactory.getDateString(mbThread.createDate)}', '${dataFactory.getDateString(mbThread.modifiedDate)}', ${mbThread.categoryId}, ${mbThread.rootMessageId}, ${mbThread.rootMessageUserId}, ${mbThread.messageCount}, ${mbThread.viewCount}, ${mbThread.lastPostByUserId}, '${dataFactory.getDateString(mbThread.lastPostDate)}', ${mbThread.priority}, ${mbThread.question?string}, ${mbThread.status}, ${mbThread.statusByUserId}, '${mbThread.statusByUserName}', '${dataFactory.getDateString(mbThread.statusDate)}');

				<@insertSubscription
					_entry = mbThread
				/>

				<@insertAssetEntry
					_entry = mbThread
				/>

				<#assign mbThreadFlag = dataFactory.newMBThreadFlag(mbThread)>

				insert into MBThreadFlag values ('${mbThreadFlag.uuid}', ${mbThreadFlag.threadFlagId}, ${mbThreadFlag.groupId}, ${mbThreadFlag.companyId}, ${mbThreadFlag.userId}, '${mbThreadFlag.userName}', '${dataFactory.getDateString(mbThreadFlag.createDate)}', '${dataFactory.getDateString(mbThreadFlag.modifiedDate)}', ${mbThreadFlag.threadId});

				<#list 1..maxMBMessageCount as mbMessageCount>
					<#assign mbMessage = dataFactory.newMBMessage(mbThread, mbMessageCount)>

					<@insertMBMessage
						_mbMessage = mbMessage
					/>

					<@insertResourcePermissions
						_entry = mbMessage
					/>

					<@insertSocialActivity
						_entry = mbMessage
					/>
				</#list>

				${writerMessageBoardsCSV.write(mbCategory.categoryId + "," + mbThread.threadId + "," + mbThread.rootMessageId + "\n")}
			</#list>
		</#if>
	</#list>
</#if>