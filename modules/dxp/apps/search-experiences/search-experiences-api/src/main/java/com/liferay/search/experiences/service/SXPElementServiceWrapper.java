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

package com.liferay.search.experiences.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SXPElementService}.
 *
 * @author Brian Wing Shun Chan
 * @see SXPElementService
 * @generated
 */
public class SXPElementServiceWrapper
	implements ServiceWrapper<SXPElementService>, SXPElementService {

	public SXPElementServiceWrapper() {
		this(null);
	}

	public SXPElementServiceWrapper(SXPElementService sxpElementService) {
		_sxpElementService = sxpElementService;
	}

	@Override
	public com.liferay.search.experiences.model.SXPElement addSXPElement(
			java.util.Map<java.util.Locale, String> descriptionMap,
			String elementDefinitionJSON, boolean readOnly,
			String schemaVersion,
			java.util.Map<java.util.Locale, String> titleMap, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpElementService.addSXPElement(
			descriptionMap, elementDefinitionJSON, readOnly, schemaVersion,
			titleMap, type, serviceContext);
	}

	@Override
	public com.liferay.search.experiences.model.SXPElement deleteSXPElement(
			long sxpElementId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpElementService.deleteSXPElement(sxpElementId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _sxpElementService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.search.experiences.model.SXPElement getSXPElement(
			long sxpElementId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpElementService.getSXPElement(sxpElementId);
	}

	@Override
	public com.liferay.search.experiences.model.SXPElement updateSXPElement(
			long sxpElementId,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String elementDefinitionJSON, String schemaVersion, boolean hidden,
			java.util.Map<java.util.Locale, String> titleMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpElementService.updateSXPElement(
			sxpElementId, descriptionMap, elementDefinitionJSON, schemaVersion,
			hidden, titleMap, serviceContext);
	}

	@Override
	public SXPElementService getWrappedService() {
		return _sxpElementService;
	}

	@Override
	public void setWrappedService(SXPElementService sxpElementService) {
		_sxpElementService = sxpElementService;
	}

	private SXPElementService _sxpElementService;

}