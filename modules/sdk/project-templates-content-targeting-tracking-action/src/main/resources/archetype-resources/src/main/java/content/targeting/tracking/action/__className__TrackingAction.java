package ${package}.content.targeting.tracking.action;

import com.liferay.content.targeting.api.model.BaseJSPTrackingAction;
import com.liferay.content.targeting.api.model.TrackingAction;
import com.liferay.content.targeting.exception.InvalidTrackingActionException;
import com.liferay.content.targeting.model.TrackingActionInstance;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author ${author}
 */
@Component(immediate = true, service = TrackingAction.class)
public class ${className}TrackingAction extends BaseJSPTrackingAction {

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
	public List<String> getEventTypes() {
		return ListUtil.fromArray(_EVENT_TYPES);
	}

	@Override
	public String getSummary(
		TrackingActionInstance trackingActionInstance, Locale locale) {

		String summary = LanguageUtil.format(
			locale, "metric-action-x-for-element-x",
			new Object[] {
				trackingActionInstance.getEventType(),
				trackingActionInstance.getElementId()
			});

		return summary;
	}

	@Override
	public String processTrackingAction(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String id, Map<String, String> values)
		throws InvalidTrackingActionException {

		return null;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=${package})",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected void populateContext(
		TrackingActionInstance trackingActionInstance,
		Map<String, Object> context, Map<String, String> values) {

		String alias = StringPool.BLANK;
		String elementId = StringPool.BLANK;
		String eventType = StringPool.BLANK;

		if (!values.isEmpty()) {
			alias = values.get("alias");
			elementId = values.get("elementId");
			eventType = values.get("eventType");
		}
		else if (trackingActionInstance != null) {
			alias = trackingActionInstance.getAlias();
			elementId = trackingActionInstance.getElementId();
			eventType = trackingActionInstance.getEventType();
		}

		context.put("alias", alias);
		context.put("elementId", elementId);
		context.put("eventType", eventType);
		context.put("eventTypes", getEventTypes());
	}

	private static final String[] _EVENT_TYPES = {"click"};

}