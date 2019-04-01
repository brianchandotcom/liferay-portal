package com.liferay.sharing.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.constants.SharingPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SharingPortletKeys.SHARING,
		"mvc.command.name=/sharing/checkUser"
	},
	service = MVCResourceCommand.class
)
public class SharingUserCheckEmailMVCResourceCommand extends
	BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest request = _portal.getHttpServletRequest(
			resourceRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String email = ParamUtil.getString(request, "email");

		User user = null;

		try {
			user = _userLocalService.getUserByEmailAddress(
				themeDisplay.getCompanyId(), email);
		}
		catch (PortalException e) {
		}

		HttpServletResponse response = _portal.getHttpServletResponse(
			resourceResponse);

		response.setContentType(ContentTypes.APPLICATION_JSON);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("userEmail", email);
		jsonObject.put("userExists", user != null);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;
}
