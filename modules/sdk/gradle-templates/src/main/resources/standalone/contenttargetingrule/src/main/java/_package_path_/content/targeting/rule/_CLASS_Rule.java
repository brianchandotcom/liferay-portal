package _package_.content.targeting.rule;

import com.liferay.content.targeting.anonymous.users.model.AnonymousUser;
import com.liferay.content.targeting.api.model.BaseJSPRule;
import com.liferay.content.targeting.api.model.Rule;
import com.liferay.content.targeting.model.RuleInstance;
import com.liferay.content.targeting.rule.categories.SampleRuleCategory;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, service = Rule.class)
public class _CLASS_Rule extends BaseJSPRule {

	@Activate
	@Override
	public void activate() {
		super.activate();
	}

	@Deactivate
	@Override
	public void deActivate() {
		super.deActivate();
	}

	@Override
	public boolean evaluate(
			HttpServletRequest request, RuleInstance ruleInstance,
			AnonymousUser anonymousUser)
		throws Exception {

		// You can obtain from the typeSettings the Rule Configuration

		String typeSettings = ruleInstance.getTypeSettings();

		// Return true if the AnonymousUser matches this Rule
		
		return _getMatches(typeSettings);
	}

	@Override
	public String getIcon() {
		return "icon-puzzle-piece";
	}

	@Override
	public String getRuleCategoryKey() {
		// Available Categories: BehaviourRuleCategory, SessionAttributesRuleCategory
		// SocialRuleCategory and UserAttributesRoleCategory

		return SampleRuleCategory.KEY;
	}

	@Override
	public String getSummary(RuleInstance ruleInstance, Locale locale) {
		String typeSettings = ruleInstance.getTypeSettings();

		boolean matches = _getMatches(typeSettings);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		if (matches) {
			return LanguageUtil.get(resourceBundle, "the-user-always-matches-this-rule");
		}
		else {
			return LanguageUtil.get(resourceBundle, "the-user-never-matches-this-rule");
		}
	}

	@Override
	public String processRule(
		PortletRequest request, PortletResponse response, String id,
		Map<String, String> values) {

		// Logic to Store the configuration of the Rule

		boolean matches = GetterUtil.getBoolean(values.get("matches"));

		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("matches", matches);

		return jsonObj.toString();
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=_name_)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected void populateContext(
		RuleInstance ruleInstance, Map<String, Object> context,
		Map<String, String> values) {

		boolean matches = false;

		if (!values.isEmpty()) {

			// Value from the request (in case of error)

			matches = GetterUtil.getBoolean(values.get("matches"));
		}
		else if (ruleInstance != null) {
			String typeSettings = ruleInstance.getTypeSettings();

			// Value from the configuration stored

			matches = _getMatches(typeSettings);
		}

		context.put("matches", matches);
	}

	private boolean _getMatches(String typeSettings) {
		try {
			JSONObject jsonObj = JSONFactoryUtil.createJSONObject(typeSettings);

			return jsonObj.getBoolean("matches");
		}
		catch (JSONException jsone) {
		}
		
		return false;
	}

}