package ${configYAML.apiPackagePath}.dto.${versionDirName};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
	</#list>
</#compress>

import com.liferay.petra.function.UnsafeSupplier;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Date;

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

	<#list javaTool.getJavaParameters(schema) as javaParameter>
		public ${javaParameter.parameterType} get${javaParameter.parameterName?cap_first}() {
			return ${javaParameter.parameterName};
		}

		public void set${javaParameter.parameterName?cap_first}(${javaParameter.parameterType} ${javaParameter.parameterName}) {
			this.${javaParameter.parameterName} = ${javaParameter.parameterName};
		}

		public void set${javaParameter.parameterName?cap_first}(UnsafeSupplier<${javaParameter.parameterType}, Throwable> ${javaParameter.parameterName}UnsafeSupplier) {
			try {
				${javaParameter.parameterName} = ${javaParameter.parameterName}UnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		@GraphQLField
		protected ${javaParameter.parameterType} ${javaParameter.parameterName};
	</#list>

}