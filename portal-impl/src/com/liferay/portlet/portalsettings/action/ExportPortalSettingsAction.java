package com.liferay.portlet.portalsettings.action;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.sites.action.ActionUtil;

import java.io.File;

import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
public class ExportPortalSettingsAction extends PortletAction {

	/**
	 * (non-Javadoc)
	 *
	 * @see
	 * com.liferay.portal.struts.PortletAction#processAction(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.portlet.PortletConfig, javax.portlet.ActionRequest,
	 * javax.portlet.ActionResponse)
	 */
	@Override
	public void processAction(ActionMapping mapping, ActionForm form,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {
		_log.info("Exporting Portal Settings XML");
		File file = null;

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
					.getAttribute(WebKeys.THEME_DISPLAY);

			long companyId = themeDisplay.getCompanyId();

			int ownerType = PortletKeys.PREFS_OWNER_TYPE_COMPANY;

			String fileName = ParamUtil.getString(actionRequest,
					"exportFileName");

			Document document = SAXReaderUtil.createDocument();

			buildExportData(companyId, ownerType, document);

			HttpServletRequest request = PortalUtil
					.getHttpServletRequest(actionRequest);
			HttpServletResponse response = PortalUtil
					.getHttpServletResponse(actionResponse);

			ServletResponseUtil.sendFile(request, response, fileName, document
					.formattedString().getBytes(), ContentTypes.TEXT_XML_UTF8);

			setForward(actionRequest, ActionConstants.COMMON_NULL);

		} catch (Exception e) {
			_log.error(e, e);

			SessionErrors.add(actionRequest, e.getClass());

			String pagesRedirect = ParamUtil.getString(actionRequest,
					"pagesRedirect");

			sendRedirect(actionRequest, actionResponse, pagesRedirect);
		} finally {
			FileUtil.delete(file);
		}

	}

	/**
	 * (non-Javadoc)
	 *
	 * @see
	 * com.liferay.portal.struts.PortletAction#render(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.portlet.PortletConfig, javax.portlet.RenderRequest,
	 * javax.portlet.RenderResponse)
	 */
	@Override
	public ActionForward render(ActionMapping mapping, ActionForm form,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse) throws Exception {
		try {
			ActionUtil.getGroup(renderRequest);
		} catch (Exception e) {
			if (e instanceof NoSuchGroupException
					|| e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.portal_settings.error");
			} else {
				throw e;
			}
		}

		return mapping.findForward(getForward(renderRequest,
				"portlet.portal_settings.export_settings"));
	}

	/**
	 * @param companyId
	 * @param ownerType
	 * @param document
	 * @throws SystemException
	 */
	protected void buildExportData(long companyId, int ownerType,
			Document document) throws SystemException {
		_log.info("Building Preferences XML document ");
		PortletPreferences companyPreferences = PortalPreferencesLocalServiceUtil
				.getPreferences(companyId, companyId, ownerType);
		Element rootElement = document.addElement("portlet-preferences");
		Enumeration<String> prefNames = companyPreferences.getNames();
		while (prefNames.hasMoreElements()) {
			Element preferencesElement = rootElement.addElement("preference");
			String prefName = prefNames.nextElement();
			String prefValue = companyPreferences.getValue(prefName, "");
			Element prefNameElement = preferencesElement.addElement("name");
			prefNameElement.addText(prefName);
			Element prefValueElement = preferencesElement.addElement("value");
			prefValueElement.addText(prefValue);
		}
	}

	private static Log _log = LogFactoryUtil
			.getLog(ExportPortalSettingsAction.class);

}