<#setting number_format = "0">

insert into WikiPage values ('${wikiPage.uuid}', ${wikiPage.pageId}, ${wikiPage.resourcePrimKey}, ${wikiPage.groupId}, ${wikiPage.companyId}, ${wikiPage.userId}, '${wikiPage.userName}', '${dataFactory.getDateString(wikiPage.createDate)}', '${dataFactory.getDateString(wikiPage.modifiedDate)}', ${wikiPage.nodeId}, '${wikiPage.title}', ${wikiPage.version}, ${wikiPage.minorEdit?string}, '${wikiPage.content}', '${wikiPage.summary}', '${wikiPage.format}', ${wikiPage.head?string}, '${wikiPage.parentTitle}', '${wikiPage.redirectTitle}', ${wikiPage.status}, ${wikiPage.statusByUserId}, '${wikiPage.statusByUserName}', ${wikiPage.statusDate!'null'});

<#assign wikiPageResource = dataFactory.newWikiPageResource(wikiPage)>

insert into WikiPageResource values ('${wikiPageResource.uuid}', ${wikiPageResource.resourcePrimKey}, ${wikiPageResource.nodeId}, '${wikiPageResource.title}');

<#assign assetEntry = dataFactory.newAssetEntry(wikiPage)>

insert into AssetEntry values (${assetEntry.entryId}, ${assetEntry.groupId}, ${assetEntry.companyId}, ${assetEntry.userId}, '${assetEntry.userName}', '${dataFactory.getDateString(assetEntry.createDate)}', '${dataFactory.getDateString(assetEntry.modifiedDate)}', ${assetEntry.classNameId}, ${assetEntry.classPK}, '${assetEntry.classUuid}', ${assetEntry.classTypeId}, ${assetEntry.visible?string}, '${dataFactory.getDateString(assetEntry.startDate)}', '${dataFactory.getDateString(assetEntry.endDate)}', '${dataFactory.getDateString(assetEntry.publishDate)}', '${dataFactory.getDateString(assetEntry.expirationDate)}', '${assetEntry.mimeType}', '${assetEntry.title}', '${assetEntry.description}', '${assetEntry.summary}', '${assetEntry.url}', '${assetEntry.layoutUuid}', ${assetEntry.height}, ${assetEntry.width}, ${assetEntry.priority}, ${assetEntry.viewCount});