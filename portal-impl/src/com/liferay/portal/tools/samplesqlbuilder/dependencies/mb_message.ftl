<#setting number_format = "0">

insert into MBMessage values ('${portalUUIDUtil.generate()}', ${mbMessage.messageId}, ${mbMessage.groupId}, ${companyId}, ${mbMessage.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${mbMessage.classNameId}, ${mbMessage.classPK}, ${mbMessage.categoryId}, ${mbMessage.threadId}, ${mbMessage.rootMessageId}, ${mbMessage.parentMessageId}, '${mbMessage.subject}', '${mbMessage.body}', '${mbMessage.format}', FALSE, FALSE, 0, FALSE, FALSE, ${mbMessage.status}, ${mbMessage.statusByUserId}, '${mbMessage.statusByUserName}', CURRENT_TIMESTAMP);

${sampleSQLBuilder.insertSecurity("com.liferay.portlet.messageboards.model.MBMessage", mbMessage.messageId)}

<#if (mbMessage.classNameId == 0) || (mbMessage.parentMessageId != 0)>
	<#assign assetEntry = dataFactory.addAssetEntry(mbMessage.groupId, mbMessage.userId, dataFactory.MBMessageClassName.classNameId, mbMessage.messageId, true, "text/html", mbMessage.subject)>
<#else>
	<#assign assetEntry = dataFactory.addAssetEntry(mbMessage.groupId, mbMessage.userId, dataFactory.MBMessageClassName.classNameId, mbMessage.messageId, false, "text/html", mbMessage.subject)>
</#if>

insert into AssetEntry (entryId, groupId, companyId, userId, createDate, modifiedDate, classNameId, classPK, visible, mimeType, title) values (${counter.get()}, ${assetEntry.groupId}, ${companyId}, ${assetEntry.userId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${assetEntry.classNameId}, ${assetEntry.classPK}, <#if assetEntry.visible>TRUE<#else>FALSE</#if>, '${assetEntry.mimeType}', '${assetEntry.title}');