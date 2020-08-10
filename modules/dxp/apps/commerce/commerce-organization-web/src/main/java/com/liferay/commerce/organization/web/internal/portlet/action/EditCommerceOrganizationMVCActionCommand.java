/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.organization.web.internal.portlet.action;

import com.liferay.commerce.organization.constants.CommerceOrganizationPortletKeys;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.DuplicateOrganizationException;
import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.exception.OrganizationNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceOrganizationPortletKeys.COMMERCE_ORGANIZATION,
		"mvc.command.name=editCommerceOrganization"
	},
	service = MVCActionCommand.class
)
public class EditCommerceOrganizationMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteOrganization(ActionRequest actionRequest)
		throws Exception {

		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationId");

		if (organizationId > 0) {
			_organizationService.deleteOrganization(organizationId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				Callable<Organization> organizationCallable =
					new OrganizationCallable(actionRequest);

				Organization organization = TransactionInvokerUtil.invoke(
					_transactionConfig, organizationCallable);

				if (cmd.equals(Constants.ADD)) {
					String redirect = getSaveAndContinueRedirect(
						actionRequest, organization);

					JSONObject jsonObject = JSONUtil.put(
						"redirectURL", redirect);

					JSONPortletResponseUtil.writeJSON(
						actionRequest, actionResponse, jsonObject);
				}
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteOrganization(actionRequest);
			}
		}
		catch (Throwable t) {
			if (t instanceof NoSuchOrganizationException ||
				t instanceof PrincipalException) {

				SessionErrors.add(actionRequest, t.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (t instanceof DuplicateOrganizationException ||
					 t instanceof OrganizationNameException) {

				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(actionRequest, t.getClass());
			}
			else {
				_log.error(t, t);
			}
		}

		hideDefaultSuccessMessage(actionRequest);
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, Organization organization)
		throws PortalException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			actionRequest, Organization.class.getName(),
			PortletProvider.Action.MANAGE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommerceOrganization");

		portletURL.setParameter(
			"organizationId", String.valueOf(organization.getOrganizationId()));

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		portletURL.setParameter("redirect", redirect);

		return portletURL.toString();
	}

	protected Organization updateOrganization(ActionRequest actionRequest)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationId");

		String name = ParamUtil.getString(actionRequest, "name");
		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		byte[] logoBytes = null;

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			logoBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Organization.class.getName(), actionRequest);

		Organization organization = null;

		if (cmd.equals(Constants.UPDATE) && (organizationId > 0)) {
			Organization oldOrganization = _organizationService.getOrganization(
				organizationId);

			organization = _organizationService.updateOrganization(
				organizationId, oldOrganization.getParentOrganizationId(), name,
				oldOrganization.getType(), oldOrganization.getRegionId(),
				oldOrganization.getCountryId(), oldOrganization.getStatusId(),
				oldOrganization.getComments(), !deleteLogo, logoBytes, false,
				oldOrganization.getAddresses(), null, null, null, null,
				serviceContext);
		}
		else if (cmd.equals(Constants.ADD)) {
			organization = _organizationService.addOrganization(
				organizationId, name, OrganizationConstants.TYPE_ORGANIZATION,
				0L, 0L, ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, null,
				false, serviceContext);
		}

		return organization;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceOrganizationMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private OrganizationService _organizationService;

	private class OrganizationCallable implements Callable<Organization> {

		@Override
		public Organization call() throws Exception {
			return updateOrganization(_actionRequest);
		}

		private OrganizationCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}