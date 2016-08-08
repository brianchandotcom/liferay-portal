package _package_.theme.contributor;

import com.liferay.portal.kernel.template.TemplateContextContributor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {"type=" + TemplateContextContributor.TYPE_THEME},
	service = TemplateContextContributor.class
)
public class _CLASS_TemplateContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects, HttpServletRequest request) {

		// This variable can be used in any theme file. For example, you could
		// add this code to the file portal_normal.ftl in your theme:
		// <h1>${sample_text}</h1>

		contextObjects.put("sample_text", "This is some sample text");
	}

}