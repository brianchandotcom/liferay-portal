package _package_.content.targeting.report;

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

@Component(immediate = true, service = Report.class)
public class _CLASS_Report extends BaseJSPReport {

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

		// Change to Campaign.class.getNamme() to have a Campaign Report

		return UserSegment.class.getName();
	}

	@Override
	public boolean isInstantiable() {

		// return false if you don't want users to create different
		// instances of this report

		return true;
	}

	public String processEditReport(
			PortletRequest portletRequest, PortletResponse portletResponse,
			ReportInstance reportInstance)
		throws Exception {

		// Logic to Store user configuration. This is only needed if
		// the report is instantiable

		String setting1 = ParamUtil.getString(portletRequest, "setting1");
		String setting2 = ParamUtil.getString(portletRequest, "setting2");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("setting1", setting1);
		jsonObject.put("setting2", setting2);

		return jsonObject.toString();
	}

	@Reference(unbind = "-")
	public void setReportInstanceLocalService(
		ReportInstanceLocalService reportInstanceLocalService) {

		_reportInstanceLocalService = reportInstanceLocalService;
	}

	@Override
	@Reference(target = "(osgi.web.symbolicname=ctreport.test)", unbind = "-")
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
			_log.error("Cannot update report", e);
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
		CtreportTestReport.class);

	private ReportInstanceLocalService _reportInstanceLocalService;

}