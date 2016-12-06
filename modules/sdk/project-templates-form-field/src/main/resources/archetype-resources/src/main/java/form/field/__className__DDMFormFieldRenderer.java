package ${package}.form.field;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldRenderer;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResource;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true, property = "ddm.form.field.type.name=${className}",
	service = DDMFormFieldRenderer.class
)
public class ${className}DDMFormFieldRenderer extends BaseDDMFormFieldRenderer {

	@Override
	public String getTemplateLanguage() {
		return TemplateConstants.LANG_TYPE_SOY;
	}

	@Override
	public String getTemplateNamespace() {
		return "ddm.${className}";
	}

	@Override
	public TemplateResource getTemplateResource() {
		return _templateResource;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_templateResource = getTemplateResource(
			"/META-INF/resources/${artifactId}.soy");
	}

	private TemplateResource _templateResource;

}