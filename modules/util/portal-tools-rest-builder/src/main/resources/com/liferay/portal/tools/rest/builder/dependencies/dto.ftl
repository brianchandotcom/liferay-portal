package ${configYAML.apiPackagePath}.dto.${versionDirName};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
	</#list>
</#compress>

import java.util.Date;
import java.util.List;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
@XmlRootElement(name = "${schemaName}")
public class ${schemaName} {

<#list javaTool.getJavaParameters(schema) as javaParameter>
	public ${javaParameter.parameterType} get${javaParameter.parameterName?cap_first}() {
		return _${javaParameter.parameterName};
	}

	public void set${javaParameter.parameterName?cap_first}(${javaParameter.parameterType} ${javaParameter.parameterName}) {
		_${javaParameter.parameterName} = ${javaParameter.parameterName};
	}

	<#if stringUtil.endsWith(javaParameter.parameterType, "[]")>
		<#assign javaDataType = stringUtil.replaceLast(javaParameter.parameterType, "[]", "") />

	public void set${javaParameter.parameterName?cap_first}(List<${javaDataType}> ${javaParameter.parameterName}) {
		set${javaParameter.parameterName?cap_first}(${javaParameter.parameterName}.toArray(new ${javaDataType}[${javaParameter.parameterName}.size()]));
	}
	</#if>

	private ${javaParameter.parameterType} _${javaParameter.parameterName};

</#list>
}