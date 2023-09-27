/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ObjectLayoutService}.
 *
 * @author Marco Leo
 * @see ObjectLayoutService
 * @generated
 */
public class ObjectLayoutServiceWrapper
	implements ObjectLayoutService, ServiceWrapper<ObjectLayoutService> {

	public ObjectLayoutServiceWrapper() {
		this(null);
	}

	public ObjectLayoutServiceWrapper(ObjectLayoutService objectLayoutService) {
		_objectLayoutService = objectLayoutService;
	}

	@Override
	public com.liferay.object.model.ObjectLayout addObjectLayout(
			long objectDefinitionId, boolean defaultObjectLayout,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.List<com.liferay.object.model.ObjectLayoutTab>
				objectLayoutTabs)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutService.addObjectLayout(
			objectDefinitionId, defaultObjectLayout, nameMap, objectLayoutTabs);
	}

	@Override
	public com.liferay.object.model.ObjectLayout deleteObjectLayout(
			long objectLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutService.deleteObjectLayout(objectLayoutId);
	}

	@Override
	public com.liferay.object.model.ObjectLayout getObjectLayout(
			long objectLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutService.getObjectLayout(objectLayoutId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectLayoutService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.object.model.ObjectLayout updateObjectLayout(
			long objectLayoutId, boolean defaultObjectLayout,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.List<com.liferay.object.model.ObjectLayoutTab>
				objectLayoutTabs)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutService.updateObjectLayout(
			objectLayoutId, defaultObjectLayout, nameMap, objectLayoutTabs);
	}

	@Override
	public ObjectLayoutService getWrappedService() {
		return _objectLayoutService;
	}

	@Override
	public void setWrappedService(ObjectLayoutService objectLayoutService) {
		_objectLayoutService = objectLayoutService;
	}

	private ObjectLayoutService _objectLayoutService;

}