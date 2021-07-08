package com.liferay.layout.reports.web.internal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.util.SessionClicks;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Yurena Cabrera
 */

@Component(
	immediate = true, property = "key=logout.events.pre",
	service = LifecycleAction.class
)
public class LogoutPreAction extends Action {

	@Override
	public void run(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) throws ActionException {

		SessionClicks.put(httpServletRequest, "com.liferay.layout.reports.web_layoutReportsPanelState", "closed");
	}
}
