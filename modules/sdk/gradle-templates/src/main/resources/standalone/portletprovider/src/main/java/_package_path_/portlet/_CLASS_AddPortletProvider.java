package _package_.portlet;

import _package_.constants._CLASS_PortletKeys;

import com.liferay.portal.kernel.portlet.AddPortletProvider;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.asset.kernel.model.AssetEntry",
		"service.ranking:Integer=" + Integer.MAX_VALUE
	},
	service = AddPortletProvider.class
)
public class _CLASS_AddPortletProvider
	extends BasePortletProvider implements AddPortletProvider {

	@Override
	public String getPortletName() {
		return _CLASS_PortletKeys._CLASS_;
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