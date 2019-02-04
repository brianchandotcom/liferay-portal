package ${configYAML.apiPackagePath}.dto;

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${schemaName};
	</#list>
</#compress>

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
@GraphQLName("${schemaName}")
@XmlRootElement(name = "${schemaName}")
public class ${schemaName} {

	<#list schema.properties?keys as propertyName>
		<#assign javaParameter = javaTool.getJavaParameter(schema.properties[propertyName], propertyName) />

		<#assign content>
			public ${javaParameter.parameterType} get${javaParameter.parameterName?cap_first}() {
				return ${propertyName};
			}

			public void set${javaParameter.parameterName?cap_first}(${javaParameter.parameterType} ${javaParameter.parameterName}) {
				this.${propertyName} = ${propertyName};
			}

			@GraphQLField
			private ${javaParameter.parameterType} ${propertyName};
		</#assign>

		<#list content?split("\n") as line>
			${line?replace("^\t\t", "", "r")}<#lt>
		</#list>
	</#list>

}