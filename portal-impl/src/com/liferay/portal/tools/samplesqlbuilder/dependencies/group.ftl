<#setting number_format = "0">

insert into Group_ values (${group.groupId}, ${group.companyId}, ${group.creatorUserId}, ${group.classNameId}, ${group.classPK}, ${group.parentGroupId}, ${group.liveGroupId}, '${group.treePath}', '${group.name}', '${group.description}', ${group.type}, '${group.typeSettings}', '${group.friendlyURL}', ${group.site?string}, ${group.active?string});

<#assign layoutSets = dataFactory.newLayoutSets(group.groupId, publicLayouts?size)>

<#list layoutSets as layoutSet>
	insert into LayoutSet values (${layoutSet.layoutSetId}, ${layoutSet.groupId}, ${layoutSet.companyId}, '${dataFactory.getDateString(layoutSet.createDate)}', '${dataFactory.getDateString(layoutSet.modifiedDate)}', ${layoutSet.privateLayout?string}, ${layoutSet.logo?string}, ${layoutSet.logoId}, '${layoutSet.themeId}', '${layoutSet.colorSchemeId}', '${layoutSet.wapThemeId}', '${layoutSet.wapColorSchemeId}', '${layoutSet.css}', ${layoutSet.pageCount}, '${layoutSet.settings}', '${layoutSet.layoutSetPrototypeUuid}', ${layoutSet.layoutSetPrototypeLinkEnabled?string});
</#list>

<#list publicLayouts as layout>
	insert into Layout values ('${layout.uuid}', ${layout.plid}, ${layout.groupId}, ${layout.companyId}, '${dataFactory.getDateString(layout.createDate)}', '${dataFactory.getDateString(layout.modifiedDate)}', ${layout.privateLayout?string}, ${layout.layoutId}, ${layout.parentLayoutId}, '${layout.name}', '${layout.title}', '${layout.description}', '${layout.keywords}', '${layout.robots}', '${layout.type}', '${layout.typeSettings}', ${layout.hidden?string}, '${layout.friendlyURL}', ${layout.iconImage?string}, ${layout.iconImageId}, '${layout.themeId}', '${layout.colorSchemeId}', '${layout.wapThemeId}', '${layout.wapColorSchemeId}', '${layout.css}', ${layout.priority}, '${layout.layoutPrototypeUuid}', ${layout.layoutPrototypeLinkEnabled?string}, '${layout.sourcePrototypeLayoutUuid}');

	${sampleSQLBuilder.insertResourcePermission("com.liferay.portal.model.Layout", stringUtil.valueOf(layout.plid))}
</#list>