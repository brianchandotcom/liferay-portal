/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.model.LayoutPrototype;

/**
 * Provides a wrapper for {@link LayoutPrototypeService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPrototypeService
 * @generated
 */
public class LayoutPrototypeServiceWrapper
	implements LayoutPrototypeService, ServiceWrapper<LayoutPrototypeService> {

	public LayoutPrototypeServiceWrapper() {
		this(null);
	}

	public LayoutPrototypeServiceWrapper(
		LayoutPrototypeService layoutPrototypeService) {

		_layoutPrototypeService = layoutPrototypeService;
	}

	@Override
	public LayoutPrototype addLayoutPrototype(
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			boolean active, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPrototypeService.addLayoutPrototype(
			nameMap, descriptionMap, active, serviceContext);
	}

	@Override
	public void deleteLayoutPrototype(long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutPrototypeService.deleteLayoutPrototype(layoutPrototypeId);
	}

	@Override
	public LayoutPrototype fetchLayoutPrototype(long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPrototypeService.fetchLayoutPrototype(layoutPrototypeId);
	}

	@Override
	public LayoutPrototype getLayoutPrototype(long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPrototypeService.getLayoutPrototype(layoutPrototypeId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutPrototypeService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<LayoutPrototype> search(
			long companyId, Boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutPrototype>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPrototypeService.search(
			companyId, active, orderByComparator);
	}

	@Override
	public LayoutPrototype updateLayoutPrototype(
			long layoutPrototypeId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			boolean active, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPrototypeService.updateLayoutPrototype(
			layoutPrototypeId, nameMap, descriptionMap, active, serviceContext);
	}

	@Override
	public LayoutPrototypeService getWrappedService() {
		return _layoutPrototypeService;
	}

	@Override
	public void setWrappedService(
		LayoutPrototypeService layoutPrototypeService) {

		_layoutPrototypeService = layoutPrototypeService;
	}

	private LayoutPrototypeService _layoutPrototypeService;

}