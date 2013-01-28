<#setting number_format = "0">

insert into BlogsEntry values ('${portalUUIDUtil.generate()}', ${blogsEntry.entryId}, ${blogsEntry.groupId}, ${companyId}, ${blogsEntry.userId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '${blogsEntry.title}', '${blogsEntry.urlTitle}', '${blogsEntry.description}', '${blogsEntry.content}', CURRENT_TIMESTAMP, FALSE, FALSE, '', FALSE, ${blogsEntry.smallImageId}, '${blogsEntry.smallImageURL}', ${blogsEntry.status}, ${blogsEntry.statusByUserId}, '${blogsEntry.statusByUserName}', CURRENT_TIMESTAMP);

<#assign assetEntry = dataFactory.addAssetEntry(blogsEntry.groupId, blogsEntry.userId, dataFactory.blogsEntryClassNameId, blogsEntry.entryId, true, "text/html", blogsEntry.title)>

insert into AssetEntry (entryId, groupId, companyId, userId, createDate, modifiedDate, classNameId, classPK, visible, mimeType, title) values (${counter.get()}, ${assetEntry.groupId}, ${companyId}, ${assetEntry.userId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${assetEntry.classNameId}, ${assetEntry.classPK}, <#if assetEntry.visible>TRUE<#else>FALSE</#if>, '${assetEntry.mimeType}', '${assetEntry.title}');