package ${configYAML.apiPackagePath}.dto.${versionDirName};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
	</#list>
</#compress>

import java.util.Date;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
@XmlRootElement(name = "${schemaName}")
public class ${schemaName} {

	<#if schema.properties??>
		<#list schema.properties?keys as propertySchemaName>
			<#assign javaParameter = javaTool.getJavaParameter(propertySchemaName, schema.properties[propertySchemaName]) />

			<#assign content>
				public ${javaParameter.parameterType} get${javaParameter.parameterName?cap_first}() {
					return _${propertySchemaName};
				}

				public void set${javaParameter.parameterName?cap_first}(${javaParameter.parameterType} ${javaParameter.parameterName}) {
					_${propertySchemaName} = ${propertySchemaName};
				}

				private ${javaParameter.parameterType} _${propertySchemaName};
			</#assign>

			<#list content?split("\n") as line>
				${line?replace("^\t\t\t", "", "r")}<#lt>
			</#list>
		</#list>
	</#if>

}