package ${configYAML.apiPackagePath}.dto.${versionDirName};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
	</#list>
</#compress>

import com.liferay.petra.function.UnsafeSupplier;

import java.util.Date;

import javax.annotation.Generated;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public interface ${schemaName} {

	<#list freeMarkerTool.getJavaParameters(schema) as javaParameter>
		public ${javaParameter.parameterType} get${javaParameter.parameterName?cap_first}();

		public void set${javaParameter.parameterName?cap_first}(${javaParameter.parameterType} ${javaParameter.parameterName});

		public void set${javaParameter.parameterName?cap_first}(UnsafeSupplier<${javaParameter.parameterType}, Throwable> ${javaParameter.parameterName}UnsafeSupplier);
	</#list>

}