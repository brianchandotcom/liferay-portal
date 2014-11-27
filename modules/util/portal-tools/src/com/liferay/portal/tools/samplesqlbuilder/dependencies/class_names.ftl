<#list dataFactory.classNameModels as classNameModel>
	insert into ClassName_ values (${classNameModel.mvccVersion}, ${classNameModel.classNameId}, '${classNameModel.value}');
</#list>