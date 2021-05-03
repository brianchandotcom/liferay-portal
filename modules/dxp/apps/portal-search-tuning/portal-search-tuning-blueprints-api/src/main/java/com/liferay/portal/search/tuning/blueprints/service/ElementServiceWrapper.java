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

package com.liferay.portal.search.tuning.blueprints.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ElementService}.
 *
 * @author Brian Wing Shun Chan
 * @see ElementService
 * @generated
 */
public class ElementServiceWrapper
	implements ElementService, ServiceWrapper<ElementService> {

	public ElementServiceWrapper(ElementService elementService) {
		_elementService = elementService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use <code>com.liferay.portal.search.tuning.elements.service.ElementServiceUtil</code> to access the element remote service.
	 */
	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element
			addCompanyElement(
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _elementService.addCompanyElement(
			titleMap, descriptionMap, configuration, type, serviceContext);
	}

	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element
			addGroupElement(
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _elementService.addGroupElement(
			titleMap, descriptionMap, configuration, type, serviceContext);
	}

	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element
			deleteElement(long elementId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _elementService.deleteElement(elementId);
	}

	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element getElement(
			long elementId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _elementService.getElement(elementId);
	}

	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Element>
			getGroupElements(long companyId, int type, int start, int end) {

		return _elementService.getGroupElements(companyId, type, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Element>
			getGroupElements(
				long companyId, int status, int type, int start, int end) {

		return _elementService.getGroupElements(
			companyId, status, type, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Element>
			getGroupElements(
				long companyId, int status, int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.Element>
						orderByComparator) {

		return _elementService.getGroupElements(
			companyId, status, type, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Element>
			getGroupElements(
				long companyId, int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.Element>
						orderByComparator) {

		return _elementService.getGroupElements(
			companyId, type, start, end, orderByComparator);
	}

	@Override
	public int getGroupElementsCount(long companyId, int type) {
		return _elementService.getGroupElementsCount(companyId, type);
	}

	@Override
	public int getGroupElementsCount(long companyId, int status, int type) {
		return _elementService.getGroupElementsCount(companyId, status, type);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _elementService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element
			updateElement(
				long elementId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _elementService.updateElement(
			elementId, titleMap, descriptionMap, configuration, serviceContext);
	}

	@Override
	public ElementService getWrappedService() {
		return _elementService;
	}

	@Override
	public void setWrappedService(ElementService elementService) {
		_elementService = elementService;
	}

	private ElementService _elementService;

}