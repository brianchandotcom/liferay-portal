<#setting number_format = "0">

insert into Group_ values (${group.groupId}, ${group.companyId}, ${group.creatorUserId}, ${group.classNameId}, ${group.classPK}, ${group.parentGroupId}, ${group.liveGroupId}, '${group.treePath}', '${group.name}', '${group.description}', ${group.type}, '${group.typeSettings}', '${group.friendlyURL}', ${group.site?string}, ${group.active?string});

<#assign layoutSets = dataFactory.newLayoutSets(group.groupId, publicPageCount)>

<#list layoutSets as layoutSet>
	insert into LayoutSet values (${layoutSet.layoutSetId}, ${layoutSet.groupId}, ${layoutSet.companyId}, '${dataFactory.getDateString(layoutSet.createDate)}', '${dataFactory.getDateString(layoutSet.modifiedDate)}', ${layoutSet.privateLayout?string}, ${layoutSet.logo?string}, ${layoutSet.logoId}, '${layoutSet.themeId}', '${layoutSet.colorSchemeId}', '${layoutSet.wapThemeId}', '${layoutSet.wapColorSchemeId}', '${layoutSet.css}', ${layoutSet.pageCount}, '${layoutSet.settings}', '${layoutSet.layoutSetPrototypeUuid}', ${layoutSet.layoutSetPrototypeLinkEnabled?string});
</#list>