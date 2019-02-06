package ${configYAML.apiPackagePath}.dto;

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${schemaName};
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
		<#list schema.properties?keys as propertyName>
			<#assign javaParameter = javaTool.getJavaParameter(schema.properties[propertyName], propertyName) />

			<#assign content>
				public ${javaParameter.parameterType} get${javaParameter.parameterName?cap_first}() {
					return _${propertyName};
				}

				public void set${javaParameter.parameterName?cap_first}(${javaParameter.parameterType} ${javaParameter.parameterName}) {
					_${propertyName} = ${propertyName};
				}

				private ${javaParameter.parameterType} _${propertyName};
			</#assign>

			<#list content?split("\n") as line>
				${line?replace("^\t\t\t", "", "r")}<#lt>
			</#list>
		</#list>
	</#if>

}