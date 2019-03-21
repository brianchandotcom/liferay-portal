package ${configYAML.apiPackagePath}.client.dto.${escapedVersion};

import ${configYAML.apiPackagePath}.client.function.UnsafeSupplier;

import java.util.Date;
import java.util.Map;

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
			propertySchema = freeMarkerTool.getDTOPropertySchema(propertyName, schema)
			propertyType = properties[propertyName]
		/>

		public ${propertyType} get${propertyName?cap_first}() {
			return ${propertyName};
		}

		public void set${propertyName?cap_first}(${propertyType} ${propertyName}) {
			this.${propertyName} = ${propertyName};
		}

		public void set${propertyName?cap_first}(UnsafeSupplier<${propertyType}, Exception> ${propertyName}UnsafeSupplier) {
			try {
				${propertyName} = ${propertyName}UnsafeSupplier.get();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		protected ${propertyType} ${propertyName};
	</#list>

}