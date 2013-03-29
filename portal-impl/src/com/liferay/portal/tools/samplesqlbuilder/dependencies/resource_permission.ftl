<#include "macro.ftl">

<#assign resourcePermissions = dataFactory.newResourcePermission(resourceName, resourcePrimkey)>

<#list resourcePermissions as resourcePermission>
	insert into ResourcePermission values (${resourcePermission.resourcePermissionId}, ${resourcePermission.companyId}, '${resourcePermission.name}', ${resourcePermission.scope}, '${resourcePermission.primKey}', ${resourcePermission.roleId}, ${resourcePermission.ownerId}, ${resourcePermission.actionIds});
</#list>