<#list dataFactory.roles as role>
	insert into Role_ values (${role.roleId}, ${role.companyId}, ${role.classNameId}, ${role.classPK}, '${role.name}', '${role.title}', '${role.description}', ${role.type}, '${role.subtype}');
</#list>