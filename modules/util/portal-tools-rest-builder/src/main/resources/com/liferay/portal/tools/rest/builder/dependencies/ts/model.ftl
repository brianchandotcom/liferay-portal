import { RequestFile } from './models';

<#if imports??>
<#list imports?sort_by("classname") as import>
	<#if import.classname?lower_case != modelName?lower_case>
import { ${import.classname} } from './${import.classname?uncap_first}';
	</#if>
</#list>
</#if>

<#if description??>
/**
* ${description}
*/
</#if>
<#if isEnum??>
export enum ${modelName} {
	<#list enumValues as value>
	${value} = "${value}"<#if value_has_next>,</#if>
	</#list>
}
<#else>
export class ${modelName} <#if parent??>extends ${parent} </#if>{
	<#list properties as property>
	'${property.name}'?: ${property.type};
	</#list>

	static discriminator: string | undefined = <#if discriminator??>"${discriminator}"<#else>undefined</#if>;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		<#list properties as property>
		{
			"name": "${property.name}",
			"baseName": "${property.name}",
			"type": "${property.type}"
		}<#if property_has_next>,</#if>
		</#list>
	];

	static getAttributeTypeMap() {
		<#if parent??>
		return super.getAttributeTypeMap().concat(${modelName}.attributeTypeMap);
		<#else>
		return ${modelName}.attributeTypeMap;
		</#if>
	}
}
</#if>