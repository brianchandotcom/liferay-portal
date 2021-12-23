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

package com.liferay.search.experiences.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.search.experiences.constants.SXPActionKeys;
import com.liferay.search.experiences.constants.SXPConstants;
import com.liferay.search.experiences.exception.SXPElementReadOnlyException;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalService;
import com.liferay.search.experiences.service.base.SXPElementServiceBaseImpl;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=sxp",
		"json.web.service.context.path=SXPElement"
	},
	service = AopService.class
)
public class SXPElementServiceImpl extends SXPElementServiceBaseImpl {

	@Override
	public SXPElement addSXPElement(
			Map<Locale, String> descriptionMap, String elementDefinitionJSON,
			boolean readOnly, String schemaVersion,
			Map<Locale, String> titleMap, int type,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null, SXPActionKeys.ADD_SXP_ELEMENT);

		return sxpElementLocalService.addSXPElement(
			getUserId(), descriptionMap, elementDefinitionJSON, readOnly,
			titleMap, type, serviceContext);
	}

	@Override
	public SXPElement deleteSXPElement(long sxpElementId)
		throws PortalException {

		_sxpElementModelResourcePermission.check(
			getPermissionChecker(), sxpElementId, ActionKeys.DELETE);

		SXPElement sxpElement = sxpElementPersistence.findByPrimaryKey(
			sxpElementId);

		if (sxpElement.isReadOnly()) {
			throw new SXPElementReadOnlyException();
		}

		return sxpElementLocalService.deleteSXPElement(sxpElement);
	}

	@Override
	public SXPElement getSXPElement(long sxpElementId) throws PortalException {
		SXPElement sxpElement = _sxpElementLocalService.getSXPElement(
			sxpElementId);

		_sxpElementModelResourcePermission.check(
			getPermissionChecker(), sxpElement, ActionKeys.VIEW);

		return sxpElement;
	}

	@Override
	public SXPElement updateSXPElement(
			long sxpElementId, Map<Locale, String> descriptionMap,
			String elementDefinitionJSON, String schemaVersion, boolean hidden,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		_sxpElementModelResourcePermission.check(
			getPermissionChecker(), sxpElementId, ActionKeys.UPDATE);

		return _sxpElementLocalService.updateSXPElement(
			getUserId(), sxpElementId, descriptionMap, elementDefinitionJSON,
			hidden, titleMap, serviceContext);
	}

	@Reference(target = "(resource.name=" + SXPConstants.RESOURCE_NAME + ")")
	private volatile PortletResourcePermission _portletResourcePermission;

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.search.experiences.model.SXPElement)"
	)
	private volatile ModelResourcePermission<SXPElement>
		_sxpElementModelResourcePermission;

	@Reference
	private UserLocalService _userLocalService;

}