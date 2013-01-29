<#setting number_format = "0">

insert into WikiPage values ('${wikiPage.uuid}', ${wikiPage.pageId}, ${wikiPage.resourcePrimKey}, ${wikiPage.groupId}, ${wikiPage.companyId}, ${wikiPage.userId}, '${wikiPage.userName}', '${wikiPage.createDate?datetime}', '${wikiPage.modifiedDate?datetime}', ${wikiPage.nodeId}, '${wikiPage.title}', ${wikiPage.version}, ${wikiPage.minorEdit?string}, '${wikiPage.content}', '${wikiPage.summary}', '${wikiPage.format}', ${wikiPage.head?string}, '${wikiPage.parentTitle}', '${wikiPage.redirectTitle}', ${wikiPage.status}, ${wikiPage.statusByUserId}, '${wikiPage.statusByUserName}', ${wikiPage.statusDate!'null'});

<#assign wikiPageResource = dataFactory.addWikiPageResource(wikiPage)>

insert into WikiPageResource values ('${wikiPageResource.uuid}', ${wikiPageResource.resourcePrimKey}, ${wikiPageResource.nodeId}, '${wikiPageResource.title}');

<#assign assetEntry = dataFactory.addAssetEntry(wikiPage)>

insert into AssetEntry values (${assetEntry.entryId}, ${assetEntry.groupId}, ${assetEntry.companyId}, ${assetEntry.userId}, '${assetEntry.userName}', '${assetEntry.createDate?datetime}', '${assetEntry.modifiedDate?datetime}', ${assetEntry.classNameId}, ${assetEntry.classPK}, '${assetEntry.classUuid}', ${assetEntry.classTypeId}, ${assetEntry.visible?string}, ${assetEntry.startDate!'null'}, ${assetEntry.endDate!'null'}, ${assetEntry.publishDate!'null'}, ${assetEntry.expirationDate!'null'}, '${assetEntry.mimeType}', '${assetEntry.title}', '${assetEntry.description}', '${assetEntry.summary}', '${assetEntry.url}', '${assetEntry.layoutUuid}', ${assetEntry.height}, ${assetEntry.width}, ${assetEntry.priority}, ${assetEntry.viewCount});