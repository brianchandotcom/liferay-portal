<#list dataFactory.roleModels as roleModel>
	${dataFactory.toInsertSQL(roleModel)}
</#list>