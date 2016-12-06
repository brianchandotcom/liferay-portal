package ${package}.form.field;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {
		"ddm.form.field.type.display.order:Integer=9",
		"ddm.form.field.type.icon=text",
		"ddm.form.field.type.js.class.name=Liferay.DDM.Field.${className}",
		"ddm.form.field.type.js.module=${artifactId}-form-field",
		"ddm.form.field.type.label=${artifactId}-label",
		"ddm.form.field.type.name=${className}"
	},
	service = DDMFormFieldType.class
)
public class ${className}DDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public String getName() {
		return "${className}";
	}

}