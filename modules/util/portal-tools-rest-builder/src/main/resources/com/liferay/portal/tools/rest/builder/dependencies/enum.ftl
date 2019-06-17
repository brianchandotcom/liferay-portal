package ${configYAML.apiPackagePath}.constant.${escapedVersion};

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public enum ${schemaName} {

	<#list schema.enumValues as enumValue>
		${freeMarkerTool.getEnumFieldName(enumValue)}("${enumValue}")

		<#if enumValue_has_next>
			,
		</#if>
	</#list>;

	@JsonCreator
	public static ${schemaName} fromString(String value) {
		for (${schemaName} ${schemaVarName} : values()) {
			if (Objects.equals(${schemaVarName}.getValue(), value)) {
				return ${schemaVarName};
			}
		}

		return null;
	}

	@JsonValue
	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private ${schemaName}(String value) {
		_value = value;
	}

	private final String _value;

}