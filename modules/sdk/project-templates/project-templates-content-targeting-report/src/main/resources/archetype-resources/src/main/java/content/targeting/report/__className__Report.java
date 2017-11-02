package ${package}.content.targeting.report;

import com.liferay.content.targeting.api.model.BaseJSPReport;
import com.liferay.content.targeting.api.model.Report;
import com.liferay.content.targeting.model.ReportInstance;
import com.liferay.content.targeting.model.UserSegment;
import com.liferay.content.targeting.service.ReportInstanceLocalService;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Date;
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
@Component(immediate = true, service = Report.class)
public class ${className}Report extends BaseJSPReport {

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
	public String getReportType() {
		return UserSegment.class.getName();
	}

	@Override
	public boolean isInstantiable() {
		return true;
	}

	public String processEditReport(
			PortletRequest portletRequest, PortletResponse portletResponse,
			ReportInstance reportInstance)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String setting1 = ParamUtil.getString(portletRequest, "setting1");

		jsonObject.put("setting1", setting1);

		String setting2 = ParamUtil.getString(portletRequest, "setting2");

		jsonObject.put("setting2", setting2);

		return jsonObject.toString();
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=${artifactId})",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	public void updateReport(ReportInstance reportInstance) {
		try {
			if (reportInstance != null) {
				reportInstance.setModifiedDate(new Date());

				_reportInstanceLocalService.updateReportInstance(
					reportInstance);
			}
		}
		catch (Exception e) {
			_log.error("Unable to update report", e);
		}
	}

	@Override
	protected void populateContext(
		ReportInstance reportInstance, Map<String, Object> context) {

		String setting1 = null;
		String setting2 = null;

		if (reportInstance != null) {
			try {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					reportInstance.getTypeSettings());

				setting1 = jsonObject.getString("setting1");
				setting2 = jsonObject.getString("setting2");
			}
			catch (JSONException jsone) {
			}
		}

		context.put("setting1", setting1);
		context.put("setting2", setting2);
	}

	@Override
	protected void populateEditContext(
		ReportInstance reportInstance, Map<String, Object> context) {

		populateContext(reportInstance, context);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		${className}Report.class);

	@Reference(unbind = "-")
	private volatile ReportInstanceLocalService _reportInstanceLocalService;

}