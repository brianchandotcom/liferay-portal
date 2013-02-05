<#list dataFactory.classNames as className>
	insert into ClassName_ values (${className.classNameId}, '${className.value}');
</#list>