package ${configYAML.apiPackagePath}.client.dto.${escapedVersion};

import ${configYAML.apiPackagePath}.client.function.UnsafeSupplier;

import javax.annotation.Generated;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public class ${schemaName} {
	<#assign properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema) />

	<#list properties?keys as propertyName>
		<#assign
			javaDataType = properties[propertyName]
			propertySchema = freeMarkerTool.getDTOPropertySchema(propertyName, schema)
		/>

		<#if stringUtil.equals(javaDataType, "[Z")>
			<#assign javaDataType = "boolean[]" />
		<#elseif stringUtil.equals(javaDataType, "[D")>
			<#assign javaDataType = "double[]" />
		<#elseif stringUtil.equals(javaDataType, "[F")>
			<#assign javaDataType = "float[]" />
		<#elseif stringUtil.equals(javaDataType, "[I")>
			<#assign javaDataType = "int[]" />
		<#elseif stringUtil.equals(javaDataType, "[J")>
			<#assign javaDataType = "long[]" />
		<#elseif javaDataType?starts_with("[L")>
			<#assign javaDataType = javaDataType[2..(javaDataType?length - 2)] + "[]" />
		<#elseif stringUtil.equals(javaDataType, "java.util.Map")>
			<#assign javaDataType = "Map<String, " + freeMarkerTool.getJavaDataType(configYAML, openAPIYAML, propertySchema.additionalPropertySchema) + ">" />
		</#if>

		public ${javaDataType} get${propertyName?cap_first}() {
			return ${propertyName};
		}

		public void set${propertyName?cap_first}(${javaDataType} ${propertyName}) {
			this.${propertyName} = ${propertyName};
		}

		public void set${propertyName?cap_first}(UnsafeSupplier<${javaDataType}, Exception> ${propertyName}UnsafeSupplier) {
			try {
				${propertyName} = ${propertyName}UnsafeSupplier.get();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		protected ${javaDataType} ${propertyName};
	</#list>

}