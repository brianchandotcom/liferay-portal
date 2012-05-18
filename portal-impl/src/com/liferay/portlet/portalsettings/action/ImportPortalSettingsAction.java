package com.liferay.portlet.portalsettings.action;

import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.PortalSettingsImportException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.sites.action.ActionUtil;

import java.io.File;
import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
public class ImportPortalSettingsAction extends PortletAction {

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
		_log.info("Importing Portal Settings XML");
		try {

			UploadPortletRequest uploadPortletRequest = PortalUtil
					.getUploadPortletRequest(actionRequest);

			ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
					.getAttribute(WebKeys.THEME_DISPLAY);

			long companyId = themeDisplay.getCompanyId();

			System.out.println("Company Id:"+companyId);

			int ownerType = PortletKeys.PREFS_OWNER_TYPE_COMPANY;

			File file = uploadPortletRequest.getFile("importFileName");

			String xml = readPreferenceXml(file);

			System.out.println("Xml:"+xml);

			// TODO need to change this to right expection
			if (!file.exists()) {
				throw new LARFileException("Import file does not exist");
			}

			PortalPreferencesLocalServiceUtil.updatePreferences(companyId,
					ownerType, xml);

			addSuccessMessage(actionRequest, actionResponse);

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			sendRedirect(actionRequest, actionResponse, redirect);
		} catch (Exception e) {
			if ((e instanceof LARFileException)
					|| (e instanceof LARTypeException)) {

				SessionErrors.add(actionRequest, e.getClass());
			} else {
				_log.error(e, e);

				SessionErrors.add(actionRequest,
						PortalSettingsImportException.class.getName());
			}
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
	 *
	 * @param xmlFile
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	protected String readPreferenceXml(File xmlFile) throws DocumentException,
			IOException {
		String xml = null;
		Document document = SAXReaderUtil.read(xmlFile);
		xml = document.compactString();
		document.removeProcessingInstruction(xml);
		return xml;
	}

	private static Log _log = LogFactoryUtil
			.getLog(ImportPortalSettingsAction.class);

}