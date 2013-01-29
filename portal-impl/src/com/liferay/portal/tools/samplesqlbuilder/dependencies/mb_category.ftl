<#setting number_format = "0">

insert into MBCategory values ('${mbCategory.uuid}', ${mbCategory.categoryId}, ${mbCategory.groupId}, ${mbCategory.companyId}, ${mbCategory.userId}, '${mbCategory.userName}', '${mbCategory.createDate?datetime}', '${mbCategory.modifiedDate?datetime}', ${mbCategory.parentCategoryId}, '${mbCategory.name}', '${mbCategory.description}', '${mbCategory.displayStyle}', ${mbCategory.threadCount}, ${mbCategory.messageCount}, '${mbCategory.lastPostDate?datetime}', ${mbCategory.status}, ${mbCategory.statusByUserId}, '${mbCategory.statusByUserName}', ${mbCategory.statusDate!'null'});

<#if (mbCategory.categoryId != 0)>
	<#assign mbMailingList = dataFactory.addMBMailingList(mbCategory)>

	insert into MBMailingList values ('${mbMailingList.uuid}', ${mbMailingList.mailingListId}, ${mbMailingList.groupId}, ${mbMailingList.companyId}, ${mbMailingList.userId}, '${mbMailingList.userName}', '${mbMailingList.createDate?datetime}', '${mbMailingList.modifiedDate?datetime}', ${mbMailingList.categoryId}, '${mbMailingList.emailAddress}', '${mbMailingList.inProtocol}', '${mbMailingList.inServerName}', ${mbMailingList.inServerPort}, ${mbMailingList.inUseSSL?string}, '${mbMailingList.inUserName}', '${mbMailingList.inPassword}', ${mbMailingList.inReadInterval}, '${mbMailingList.outEmailAddress}', ${mbMailingList.outCustom?string}, '${mbMailingList.outServerName}', ${mbMailingList.outServerPort}, ${mbMailingList.outUseSSL?string}, '${mbMailingList.outUserName}', '${mbMailingList.outPassword}', ${mbMailingList.allowAnonymous?string}, ${mbMailingList.active?string});
</#if>