#parse ("definitions.vm")
package ${package}.form.field;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = {
#if (${liferayVersion.startsWith("7.0")})
		"ddm.form.field.type.display.order:Integer=9",
#elseif (${liferayVersion.startsWith("7.1")})
		"ddm.form.field.type.description=${artifactId}-description",
		"ddm.form.field.type.display.order:Integer=10",
#end
		"ddm.form.field.type.icon=text",
		"ddm.form.field.type.js.class.name=Liferay.DDM.Field.${className}",
		"ddm.form.field.type.js.module=${artifactId}-form-field",
		"ddm.form.field.type.label=${artifactId}-label",
		"ddm.form.field.type.name=${formFieldTypeName}"
	},
	service = DDMFormFieldType.class
)
public class ${className}DDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public String getName() {
		return "${formFieldTypeName}";
	}

}