/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.page.template.internal.service;

import com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.service.impl.LayoutPageTemplateEntryServiceImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeService;
import com.liferay.portal.kernel.service.LayoutPrototypeServiceWrapper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Couso
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class LayoutPageTemplateLayoutPrototypeServiceWrapper
	extends LayoutPrototypeServiceWrapper {

	public LayoutPageTemplateLayoutPrototypeServiceWrapper() {
		super(null);
	}

	public LayoutPageTemplateLayoutPrototypeServiceWrapper(
		LayoutPrototypeService layoutPrototypeService) {

		super(layoutPrototypeService);
	}

	@Override
	public LayoutPrototype addLayoutPrototype(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		try {
			return super.addLayoutPrototype(
				nameMap, descriptionMap, active, serviceContext);
		}
		catch (PrincipalException pe) {
			_portletResourcePermission.check(
				_getPermissionChecker(), serviceContext.getScopeGroupId(),
				LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_ENTRY);

			User user = _getGuestOrUser();

			return _layoutPrototypeLocalService.addLayoutPrototype(
				user.getUserId(), user.getCompanyId(), nameMap, descriptionMap,
				active, serviceContext);
		}
	}

	@Override
	public void deleteLayoutPrototype(long layoutPrototypeId)
		throws PortalException {

		try {
			super.deleteLayoutPrototype(layoutPrototypeId);
		}
		catch (PrincipalException pe) {
			_checkLayoutPrototypePermission(
				layoutPrototypeId, ActionKeys.DELETE, pe);

			_layoutPrototypeLocalService.deleteLayoutPrototype(
				layoutPrototypeId);
		}
	}

	@Override
	public LayoutPrototype fetchLayoutPrototype(long layoutPrototypeId)
		throws PortalException {

		try {
			return super.fetchLayoutPrototype(layoutPrototypeId);
		}
		catch (PrincipalException pe) {
			_checkLayoutPrototypePermission(
				layoutPrototypeId, ActionKeys.VIEW, pe);

			return _layoutPrototypeLocalService.fetchLayoutPrototype(
				layoutPrototypeId);
		}
	}

	@Override
	public LayoutPrototype getLayoutPrototype(long layoutPrototypeId)
		throws PortalException {

		try {
			return super.getLayoutPrototype(layoutPrototypeId);
		}
		catch (PrincipalException pe) {
			_checkLayoutPrototypePermission(
				layoutPrototypeId, ActionKeys.VIEW, pe);

			return _layoutPrototypeLocalService.getLayoutPrototype(
				layoutPrototypeId);
		}
	}

	@Override
	public List<LayoutPrototype> search(
			long companyId, Boolean active,
			OrderByComparator<LayoutPrototype> obc)
		throws PortalException {

		List<LayoutPrototype> filteredLayoutPrototypes = new ArrayList<>();

		List<LayoutPrototype> layoutPrototypes = super.search(
			companyId, active, obc);

		for (LayoutPrototype layoutPrototype : layoutPrototypes) {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchFirstLayoutPageTemplateEntry(
						layoutPrototype.getLayoutPrototypeId());

			if (layoutPageTemplateEntry == null) {
				continue;
			}

			if (_layoutPageTemplateEntryModelResourcePermission.contains(
					_getPermissionChecker(),
					layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
					ActionKeys.VIEW)) {

				filteredLayoutPrototypes.add(layoutPrototype);
			}
		}

		return filteredLayoutPrototypes;
	}

	@Override
	public LayoutPrototype updateLayoutPrototype(
			long layoutPrototypeId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			return super.updateLayoutPrototype(
				layoutPrototypeId, nameMap, descriptionMap, active,
				serviceContext);
		}
		catch (PrincipalException pe) {
			_checkLayoutPrototypePermission(
				layoutPrototypeId, ActionKeys.UPDATE, pe);

			return _layoutPrototypeLocalService.updateLayoutPrototype(
				layoutPrototypeId, nameMap, descriptionMap, active,
				serviceContext);
		}
	}

	private void _checkLayoutPrototypePermission(
			long layoutPrototypeId, String actionId, PrincipalException pe)
		throws PortalException {

		LayoutPrototype layoutPrototype =
			_layoutPrototypeLocalService.fetchLayoutPrototype(
				layoutPrototypeId);

		if (layoutPrototype == null) {
			throw pe;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchFirstLayoutPageTemplateEntry(
					layoutPrototype.getLayoutPrototypeId());

		if (layoutPageTemplateEntry == null) {
			throw pe;
		}

		_layoutPageTemplateEntryModelResourcePermission.check(
			_getPermissionChecker(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(), actionId);
	}

	private User _getGuestOrUser() throws PortalException {
		try {
			return _getUser();
		}
		catch (PrincipalException pe) {
			try {
				return _userLocalService.getDefaultUser(
					CompanyThreadLocal.getCompanyId());
			}
			catch (Exception e) {
				throw pe;
			}
		}
	}

	private PermissionChecker _getPermissionChecker()
		throws PrincipalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			throw new PrincipalException("PermissionChecker not initialized");
		}

		return permissionChecker;
	}

	private User _getUser() throws PortalException {
		return _userLocalService.getUserById(_getUserId());
	}

	private long _getUserId() throws PrincipalException {
		String name = PrincipalThreadLocal.getName();

		if (Validator.isNull(name)) {
			throw new PrincipalException("Principal is null");
		}

		for (String anonymousName : _ANONYMOUS_NAMES) {
			if (StringUtil.equalsIgnoreCase(name, anonymousName)) {
				throw new PrincipalException(
					"Principal cannot be " + anonymousName);
			}
		}

		return GetterUtil.getLong(name);
	}

	private static final String[] _ANONYMOUS_NAMES = {
		BaseServiceImpl.JRUN_ANONYMOUS, BaseServiceImpl.ORACLE_ANONYMOUS,
		BaseServiceImpl.SUN_ANONYMOUS, BaseServiceImpl.WEBLOGIC_ANONYMOUS
	};

	private static volatile ModelResourcePermission<LayoutPageTemplateEntry>
		_layoutPageTemplateEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				LayoutPageTemplateEntryServiceImpl.class,
				"_layoutPageTemplateEntryModelResourcePermission",
				LayoutPageTemplateEntry.class);

	@Reference(
		target = "(component.name=com.liferay.layout.page.template.internal.security.permission.resource.LayoutPageTemplatePortletResourcePermission)"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}