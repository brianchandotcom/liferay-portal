/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LayoutContentVersionService}.
 *
 * @author Lourdes Fernández Besada
 * @see LayoutContentVersionService
 * @generated
 */
public class LayoutContentVersionServiceWrapper
	implements LayoutContentVersionService,
			   ServiceWrapper<LayoutContentVersionService> {

	public LayoutContentVersionServiceWrapper() {
		this(null);
	}

	public LayoutContentVersionServiceWrapper(
		LayoutContentVersionService layoutContentVersionService) {

		_layoutContentVersionService = layoutContentVersionService;
	}

	@Override
	public com.liferay.layout.content.model.LayoutContentVersion
			addLayoutContentVersion(
				String externalReferenceCode, String data,
				java.util.Map<java.util.Locale, String> nameMap, long plid,
				int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutContentVersionService.addLayoutContentVersion(
			externalReferenceCode, data, nameMap, plid, status);
	}

	@Override
	public com.liferay.layout.content.model.LayoutContentVersion
			addOrUpdateLayoutContentVersion(
				String externalReferenceCode, String data,
				java.util.Map<java.util.Locale, String> nameMap, long plid,
				int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutContentVersionService.addOrUpdateLayoutContentVersion(
			externalReferenceCode, data, nameMap, plid, status);
	}

	@Override
	public com.liferay.layout.content.model.LayoutContentVersion
			deleteLayoutContentVersion(long layoutContentVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutContentVersionService.deleteLayoutContentVersion(
			layoutContentVersionId);
	}

	@Override
	public com.liferay.layout.content.model.LayoutContentVersion
			getLayoutContentVersion(long layoutContentVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutContentVersionService.getLayoutContentVersion(
			layoutContentVersionId);
	}

	@Override
	public com.liferay.layout.content.model.LayoutContentVersion
			getLayoutContentVersionByExternalReferenceCode(
				String externalReferenceCode, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutContentVersionService.
			getLayoutContentVersionByExternalReferenceCode(
				externalReferenceCode, groupId);
	}

	@Override
	public java.util.List<com.liferay.layout.content.model.LayoutContentVersion>
			getLayoutContentVersions(long plid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutContentVersionService.getLayoutContentVersions(plid);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutContentVersionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.layout.content.model.LayoutContentVersion
			updateLayoutContentVersion(
				long layoutContentVersionId,
				java.util.Map<java.util.Locale, String> nameMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutContentVersionService.updateLayoutContentVersion(
			layoutContentVersionId, nameMap);
	}

	@Override
	public LayoutContentVersionService getWrappedService() {
		return _layoutContentVersionService;
	}

	@Override
	public void setWrappedService(
		LayoutContentVersionService layoutContentVersionService) {

		_layoutContentVersionService = layoutContentVersionService;
	}

	private LayoutContentVersionService _layoutContentVersionService;

}
// LIFERAY-SERVICE-BUILDER-HASH:2033561900