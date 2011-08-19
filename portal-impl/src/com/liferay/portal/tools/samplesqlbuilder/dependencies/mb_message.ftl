<#setting number_format = "0">

insert into MBMessage values ('${portalUUIDUtil.generate()}', ${mbMessage.messageId}, ${mbMessage.groupId}, ${companyId}, ${mbMessage.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${mbMessage.classNameId}, ${mbMessage.classPK}, ${mbMessage.categoryId}, ${mbMessage.threadId}, ${mbMessage.rootMessageId}, ${mbMessage.parentMessageId}, '${mbMessage.subject}', '${mbMessage.body}', '${mbMessage.format}', FALSE, FALSE, 0, FALSE, ${mbMessage.status}, ${mbMessage.statusByUserId}, '${mbMessage.statusByUserName}', CURRENT_TIMESTAMP, false);

${sampleSQLBuilder.insertSecurity("com.liferay.portlet.messageboards.model.MBMessage", mbMessage.messageId)}

<#if (mbMessage.classNameId == 0) || (mbMessage.parentMessageId != 0)>
	<#assign assetEntry = dataFactory.addAssetEntry(mbMessage.groupId, mbMessage.userId, dataFactory.MBMessageClassName.classNameId, mbMessage.messageId, true, "text/html", mbMessage.subject)>
<#else>
	<#assign assetEntry = dataFactory.addAssetEntry(mbMessage.groupId, mbMessage.userId, dataFactory.MBMessageClassName.classNameId, mbMessage.messageId, false, "text/html", mbMessage.subject)>
</#if>

${sampleSQLBuilder.insertAssetEntry(assetEntry)}