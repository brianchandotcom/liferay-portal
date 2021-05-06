#parse ("/definitions.vm")
package ${package}.form.field;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
#if (!(${liferayVersion.startsWith("7.0")} || ${liferayVersion.startsWith("7.1")}))
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
#end

import org.osgi.service.component.annotations.Component;
#if (!(${liferayVersion.startsWith("7.0")} || ${liferayVersion.startsWith("7.1")}))
import org.osgi.service.component.annotations.Reference;
#end

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = {
#if (${liferayVersion.startsWith("7.0")})
		"ddm.form.field.type.display.order:Integer=9",
#else
		"ddm.form.field.type.description=${artifactId}-description",
#if (${liferayVersion.startsWith("7.1")})
		"ddm.form.field.type.display.order:Integer=10",
#elseif (!(${liferayVersion.startsWith("7.0")} || ${liferayVersion.startsWith("7.1")}))
		"ddm.form.field.type.display.order:Integer=13",
#end
		"ddm.form.field.type.group=customized",
#end
		"ddm.form.field.type.icon=text",
#if (${liferayVersion.startsWith("7.0")} || ${liferayVersion.startsWith("7.1")})
		"ddm.form.field.type.js.class.name=Liferay.DDM.Field.${className}",
		"ddm.form.field.type.js.module=${artifactId}-form-field",
#end
		"ddm.form.field.type.label=${artifactId}-label",
		"ddm.form.field.type.name=${formFieldTypeName}"
	},
	service = DDMFormFieldType.class
)
public class ${className}DDMFormFieldType extends BaseDDMFormFieldType {
#if (!(${liferayVersion.startsWith("7.0")} || ${liferayVersion.startsWith("7.1")}))

	@Override
	public String getModuleName() {
		return _npmResolver.resolveModuleName(
			"dynamic-data-${artifactId}-form-field/${artifactId}.es");
	}
#end

	@Override
	public String getName() {
		return "${formFieldTypeName}";
	}
#if (!(${liferayVersion.startsWith("7.0")} || ${liferayVersion.startsWith("7.1")}))

	@Override
	public boolean isCustomDDMFormFieldType() {
		return true;
	}

	@Reference
	private NPMResolver _npmResolver;
#end

}