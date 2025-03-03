<#if imports??>
	<#list imports?sort as import>
		<#if import?lower_case != modelName?lower_case>
			import {${import}} from './${import?uncap_first}';
		</#if>
	</#list>
</#if>

/**
 * @author ${configYAML.author}
 * @generated
 */

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
	export class ${modelName} <#if parentClass??>extends ${parentClass} </#if>{
		<#list properties as property>
			'${property.name}'?: ${property.type};
		</#list>

		static 'discriminator': string | undefined = <#if discriminator??>"${discriminator}"<#else>undefined</#if>;

	static 'attributeTypeMap': Array<{
		baseName: string;
		name: string;
		type: string;
	}> = [
	<#list properties as property>
		{
			baseName: "${property.name}",
			name: "${property.name}",
			type: "${property.type}",
		},
	</#list>
		];

		static getAttributeTypeMap() {
			<#if parentClass??>
				return super.getAttributeTypeMap().concat(${modelName}.attributeTypeMap);
			<#else>
				return ${modelName}.attributeTypeMap;
			</#if>
		}
	}
</#if>