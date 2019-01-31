package ${configYAML.apiPackagePath}.dto;

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${schemaName};
	</#list>
</#compress>

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
@XmlRootElement(name = "${schemaName}")
public class ${schemaName} {

	<#list schema.properties?keys as propertyName>
		<#assign properties = schema.properties[propertyName] />

		<#list propertyName?split("[^A-Za-z0-9]", "r") as s>
			<#if s?has_content>
				<#if parameterName?has_content>
					<#assign parameterName = "${parameterName}${s?cap_first}" />
				<#else>
					<#assign parameterName = "${s}" />
				</#if>
			</#if>
		</#list>

		<#assign parameterType = "" />

		<#if properties.type??>
			<#if stringUtil.equals(properties.type, "array") && properties.items?? && properties.items.type??>
				<#assign parameterType = "${properties.items.type?cap_first}[]" />
			<#else>
				<#if properties.format?? && stringUtil.equals(properties.format, "int64") && stringUtil.equals(properties.type, "integer")>
					<#assign parameterType = "Long" />
				<#else>
					<#assign parameterType = properties.type?cap_first />
				</#if>
			</#if>
		<#else>
			<#assign reference = "${properties.reference}" />

			<#assign parameterType = "${reference[(reference?last_index_of('/') + 1)..(reference?length - 1)]}" />
		</#if>

		<#assign template>
			public ${parameterType} get${propertyName?cap_first}() {
				return _${propertyName};
			}

			public void set${propertyName?cap_first}(${parameterType} ${propertyName}) {
				_${propertyName} = ${propertyName};
			}

			private ${parameterType} _${propertyName};
		</#assign>

		<#list template?split("\n") as line>
			<#if line?trim?has_content>
${line?replace("^\t\t", "", "r")}
			</#if>
		</#list>
	</#list>

}