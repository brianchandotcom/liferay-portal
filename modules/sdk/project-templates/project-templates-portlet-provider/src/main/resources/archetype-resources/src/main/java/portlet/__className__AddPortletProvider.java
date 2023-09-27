package ${package}.portlet;

import ${package}.constants.${className}PortletKeys;

#if (!${liferayVersion.startsWith("7.4")} || (${liferayVersion.startsWith("7.4")} && !${newTemplate.equals("true")}))
import com.liferay.portal.kernel.portlet.AddPortletProvider;
#end
import com.liferay.portal.kernel.portlet.BasePortletProvider;
#if (${liferayVersion.startsWith("7.4")}  && ${newTemplate.equals("true")})
import com.liferay.portal.kernel.portlet.PortletProvider;
#end
import com.liferay.portal.kernel.theme.ThemeDisplay;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(
	property = "model.class.name=com.liferay.asset.kernel.model.AssetEntry",
#if (${liferayVersion.startsWith("7.4")}  && ${newTemplate.equals("true")})
	service = PortletProvider.class
#else
	service = AddPortletProvider.class
#end
)
public class ${className}AddPortletProvider
#if (${liferayVersion.startsWith("7.4")} && ${newTemplate.equals("true")})
	extends BasePortletProvider {
#else
	extends BasePortletProvider implements AddPortletProvider {
#end

	@Override
	public String getPortletName() {
		return ${className}PortletKeys.${className.toUpperCase()};
	}

#if (${liferayVersion.startsWith("7.4")}  && ${newTemplate.equals("true")})
	@Override
	public Action[] getSupportedActions() {
		return _supportedActions;
	}

	private final Action[] _supportedActions = {Action.ADD};
#else
	@Override
	public void updatePortletPreferences(
			PortletPreferences portletPreferences, String portletId,
			String className, long classPK, ThemeDisplay themeDisplay)
		throws Exception {

		portletPreferences.setValue("className", className);
		portletPreferences.setValue("classPK", String.valueOf(classPK));
	}
#end

}