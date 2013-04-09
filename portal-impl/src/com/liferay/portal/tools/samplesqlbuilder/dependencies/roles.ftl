<#list dataFactory.roles as role>
	insert into Role_ values ('${role.uuid}', ${role.roleId}, ${role.companyId}, ${role.userId}, '${role.userName}', '${dataFactory.getDateString(role.createDate)}', '${dataFactory.getDateString(role.modifiedDate)}', ${role.classNameId}, ${role.classPK}, '${role.name}', '${role.title}', '${role.description}', ${role.type}, '${role.subtype}');
</#list>