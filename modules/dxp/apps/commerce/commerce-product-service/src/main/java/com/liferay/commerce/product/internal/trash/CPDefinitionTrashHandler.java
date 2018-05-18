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

package com.liferay.commerce.product.internal.trash;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implements trash handling for the commerce product definition entity.
 *
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPDefinition",
	service = TrashHandler.class
)
public class CPDefinitionTrashHandler extends BaseTrashHandler {

	@Override
	public void deleteTrashEntry(long cpDefinitionId) throws PortalException {
		_cpDefinitionLocalService.deleteCPDefinition(cpDefinitionId);
	}

	@Override
	public String getClassName() {
		return CPDefinition.class.getName();
	}

	@Override
	public String getRestoreContainedModelLink(
			PortletRequest portletRequest, long cpDefinitionId)
		throws PortalException {

		PortletURL portletURL = getRestoreURL(
			portletRequest, cpDefinitionId, false);

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpDefinition.getCPDefinitionId()));

		return portletURL.toString();
	}

	@Override
	public String getRestoreContainerModelLink(
			PortletRequest portletRequest, long cpDefinitionId)
		throws PortalException {

		PortletURL portletURL = getRestoreURL(
			portletRequest, cpDefinitionId, true);

		return portletURL.toString();
	}

	@Override
	public String getRestoreMessage(
		PortletRequest portletRequest, long cpDefinitionId) {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			portletRequest);

		return LanguageUtil.get(httpServletRequest, "catalog");
	}

	@Override
	public boolean isInTrash(long cpDefinitionId) throws PortalException {
		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		return cpDefinition.isInTrash();
	}

	@Override
	public void restoreTrashEntry(long userId, long cpDefinitionId)
		throws PortalException {

		_cpDefinitionLocalService.restoreCPDefinitionFromTrash(
			userId, cpDefinitionId);
	}

	protected PortletURL getRestoreURL(
			PortletRequest portletRequest, long cpDefinitionId,
			boolean containerModel)
		throws PortalException {

		PortletURL portletURL = null;

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);
		String portletId = PortletProviderUtil.getPortletId(
			CPDefinition.class.getName(), PortletProvider.Action.VIEW);

		long plid = _portal.getPlidFromPortletId(
			cpDefinition.getGroupId(), portletId);

		if (plid == LayoutConstants.DEFAULT_PLID) {
			portletId = PortletProviderUtil.getPortletId(
				CPDefinition.class.getName(), PortletProvider.Action.MANAGE);

			portletURL = _portal.getControlPanelPortletURL(
				portletRequest, portletId, PortletRequest.RENDER_PHASE);
		}
		else {
			portletURL = PortletURLFactoryUtil.create(
				portletRequest, portletId, plid, PortletRequest.RENDER_PHASE);
		}

		if (!containerModel) {
			portletURL.setParameter("mvcPath", "/view.jsp");
		}

		return portletURL;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long cpDefinitionId,
			String actionId)
		throws PortalException {

		return CPDefinitionPermission.contains(
			permissionChecker, cpDefinitionId, actionId);
	}

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private Portal _portal;

}