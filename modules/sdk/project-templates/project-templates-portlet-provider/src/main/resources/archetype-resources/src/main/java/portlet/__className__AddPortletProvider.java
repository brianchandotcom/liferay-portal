package ${package}.portlet;

import ${package}.constants.${className}PortletKeys;

import com.liferay.portal.kernel.portlet.AddPortletProvider;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(
	property = "model.class.name=com.liferay.asset.kernel.model.AssetEntry",
	service = AddPortletProvider.class
)
public class ${className}AddPortletProvider
	extends BasePortletProvider implements AddPortletProvider {

	@Override
	public String getPortletName() {
		return ${className}PortletKeys.${className.toUpperCase()};
	}

	@Override
	public void updatePortletPreferences(
			PortletPreferences portletPreferences, String portletId,
			String className, long classPK, ThemeDisplay themeDisplay)
		throws Exception {

		portletPreferences.setValue("className", className);
		portletPreferences.setValue("classPK", String.valueOf(classPK));
	}

}