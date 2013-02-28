<#setting number_format = "0">

insert into Group_ values (${group.groupId}, ${group.companyId}, ${group.creatorUserId}, ${group.classNameId}, ${group.classPK}, ${group.parentGroupId}, ${group.liveGroupId}, '${group.treePath}', '${group.name}', '${group.description}', ${group.type}, '${group.typeSettings}', '${group.friendlyURL}', ${group.site?string}, ${group.active?string});

insert into LayoutSet values (${counter.get()}, ${group.groupId}, ${companyId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE, FALSE, 0, 'classic', '01', '', '', '', 0, '', '', FALSE);
insert into LayoutSet values (${counter.get()}, ${group.groupId}, ${companyId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE, FALSE, 0, 'classic', '01', '', '', '', ${publicLayouts?size}, '', '', FALSE);

<#list publicLayouts as layout>
	insert into Layout values ('${layout.uuid}', ${layout.plid}, ${layout.groupId}, ${layout.companyId}, '${dataFactory.getDateString(layout.createDate)}', '${dataFactory.getDateString(layout.modifiedDate)}', ${layout.privateLayout?string}, ${layout.layoutId}, ${layout.parentLayoutId}, '${layout.name}', '${layout.title}', '${layout.description}', '${layout.keywords}', '${layout.robots}', '${layout.type}', '${layout.typeSettings}', ${layout.hidden?string}, '${layout.friendlyURL}', ${layout.iconImage?string}, ${layout.iconImageId}, '${layout.themeId}', '${layout.colorSchemeId}', '${layout.wapThemeId}', '${layout.wapColorSchemeId}', '${layout.css}', ${layout.priority}, '${layout.layoutPrototypeUuid}', ${layout.layoutPrototypeLinkEnabled?string}, '${layout.sourcePrototypeLayoutUuid}');

	${sampleSQLBuilder.insertResourcePermission("com.liferay.portal.model.Layout", stringUtil.valueOf(layout.plid))}
</#list>