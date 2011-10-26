<#setting number_format = "0">

insert into Group_ values (${group.groupId}, ${companyId}, ${defaultUserId}, ${group.classNameId}, ${group.classPK}, 0, 0, '${group.name}', '', 0, '', '${group.friendlyURL}', <#if group.site>TRUE<#else>FALSE</#if>, TRUE);

${sampleSQLBuilder.insertSecurity("com.liferay.portal.model.Group", group.groupId)}

insert into LayoutSet values (${counter.get()}, ${group.groupId}, ${companyId}, TRUE, FALSE, 0, 'classic', '01', '', '', '', ${privateLayouts?size}, '', '', FALSE);
insert into LayoutSet values (${counter.get()}, ${group.groupId}, ${companyId}, FALSE, FALSE, 0, 'classic', '01', '', '', '', ${publicLayouts?size}, '', '', FALSE);

<#list privateLayouts as layout>
	insert into Layout values ('${portalUUIDUtil.generate()}', ${layout.plid}, ${group.groupId}, ${companyId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE, ${layout.getLayoutId()}, 0, '<?xml version="1.0"?>\n\n<root>\n<name>${layout.name}</name>\n</root>', '', '', 'portlet', '${layout.typeSettings}', FALSE, '${layout.friendlyURL}', FALSE, 0, '', '', '', '', '', 0, '', FALSE, '');

	${sampleSQLBuilder.insertResourcePermission("com.liferay.portal.model.Layout", layout.plid)}
	${sampleSQLBuilder.insertSecurity("com.liferay.portal.model.Layout", layout.plid)}
</#list>

<#list publicLayouts as layout>
	insert into Layout values ('${portalUUIDUtil.generate()}', ${layout.plid}, ${group.groupId}, ${companyId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE, ${layout.getLayoutId()}, 0, '<?xml version="1.0"?>\n\n<root>\n<name>${layout.name}</name>\n</root>', '', '', '', '', 'portlet', '${layout.typeSettings}', FALSE, '${layout.friendlyURL}', FALSE, 0, '', '', '', '', '', 0, '', FALSE, '');

	${sampleSQLBuilder.insertResourcePermission("com.liferay.portal.model.Layout", layout.plid)}
	${sampleSQLBuilder.insertSecurity("com.liferay.portal.model.Layout", layout.plid)}
</#list>