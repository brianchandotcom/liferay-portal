package ${configYAML.apiPackagePath}.client.dto.${escapedVersion};

import ${configYAML.apiPackagePath}.client.function.UnsafeSupplier;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public class ${schemaName} {
	<#assign enumSchemas = freeMarkerTool.getDTOEnumSchemas(schema) />

	<#list enumSchemas?keys as enumName>
		public static enum ${enumName} {

			<#list enumSchemas[enumName].enumValues as enumValue>
				${freeMarkerTool.getEnumFieldName(enumValue)}("${enumValue}")

				<#if enumValue_has_next>
					,
				</#if>
			</#list>;

			public static ${enumName} create(String value) {
				for (${enumName} ${enumName?uncap_first} : values()) {
					if (Objects.equals(${enumName?uncap_first}.getValue(), value)) {
						return ${enumName?uncap_first};
					}
				}

				return null;
			}

			public String getValue() {
				return _value;
			}

			@Override
			public String toString() {
				return _value;
			}

			private ${enumName}(String value) {
				_value = value;
			}

			private final String _value;

		}
	</#list>

	<#assign properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema) />

	<#list properties?keys as propertyName>
		<#assign
			propertySchema = freeMarkerTool.getDTOPropertySchema(propertyName, schema)
			propertyType = properties[propertyName]
		/>

		public ${propertyType} get${propertyName?cap_first}() {
			return ${propertyName};
		}

		<#if enumSchemas?keys?seq_contains(propertyType)>
			public String get${propertyName?cap_first}AsString() {
				if (${propertyName} == null) {
					return null;
				}

				return ${propertyName}.toString();
			}
		</#if>

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