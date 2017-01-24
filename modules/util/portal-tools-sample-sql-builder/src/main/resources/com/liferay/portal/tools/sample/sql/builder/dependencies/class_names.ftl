<#list dataFactory.classNameModels as classNameModel>
	${dataFactory.toInsertSQL(classNameModel)}
</#list>