/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDisplayLayoutService}.
 *
 * @author Marco Leo
 * @see CPDisplayLayoutService
 * @generated
 */
public class CPDisplayLayoutServiceWrapper
	implements CPDisplayLayoutService, ServiceWrapper<CPDisplayLayoutService> {

	public CPDisplayLayoutServiceWrapper() {
		this(null);
	}

	public CPDisplayLayoutServiceWrapper(
		CPDisplayLayoutService cpDisplayLayoutService) {

		_cpDisplayLayoutService = cpDisplayLayoutService;
	}

	@Override
	public CPDisplayLayout addCPDisplayLayout(
			long groupId, Class<?> clazz, long classPK,
			String layoutPageTemplateEntryUuid, String layoutUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDisplayLayoutService.addCPDisplayLayout(
			groupId, clazz, classPK, layoutPageTemplateEntryUuid, layoutUuid);
	}

	@Override
	public void deleteCPDisplayLayout(long cpDisplayLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpDisplayLayoutService.deleteCPDisplayLayout(cpDisplayLayoutId);
	}

	@Override
	public CPDisplayLayout fetchCPDisplayLayout(long cpDisplayLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDisplayLayoutService.fetchCPDisplayLayout(cpDisplayLayoutId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDisplayLayoutService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<CPDisplayLayout> searchCPDisplayLayout(
				long companyId, long groupId, String className, Integer type,
				String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDisplayLayoutService.searchCPDisplayLayout(
			companyId, groupId, className, type, keywords, start, end, sort);
	}

	@Override
	public CPDisplayLayout updateCPDisplayLayout(
			long cpDisplayLayoutId, long classPK,
			String layoutPageTemplateEntryUuid, String layoutUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDisplayLayoutService.updateCPDisplayLayout(
			cpDisplayLayoutId, classPK, layoutPageTemplateEntryUuid,
			layoutUuid);
	}

	@Override
	public CPDisplayLayoutService getWrappedService() {
		return _cpDisplayLayoutService;
	}

	@Override
	public void setWrappedService(
		CPDisplayLayoutService cpDisplayLayoutService) {

		_cpDisplayLayoutService = cpDisplayLayoutService;
	}

	private CPDisplayLayoutService _cpDisplayLayoutService;

}