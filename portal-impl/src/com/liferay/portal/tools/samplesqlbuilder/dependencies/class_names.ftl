<#list dataFactory.classNameModels as classNameModel>
	insert into ClassName_ values (${classNameModel.classNameId}, '${classNameModel.value}');
</#list>