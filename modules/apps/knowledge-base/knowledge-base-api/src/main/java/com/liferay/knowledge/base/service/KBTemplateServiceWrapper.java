/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.service;

import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link KBTemplateService}.
 *
 * @author Brian Wing Shun Chan
 * @see KBTemplateService
 * @generated
 */
public class KBTemplateServiceWrapper
	implements KBTemplateService, ServiceWrapper<KBTemplateService> {

	public KBTemplateServiceWrapper() {
		this(null);
	}

	public KBTemplateServiceWrapper(KBTemplateService kbTemplateService) {
		_kbTemplateService = kbTemplateService;
	}

	@Override
	public KBTemplate addKBTemplate(
			String portletId, String title, String content,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbTemplateService.addKBTemplate(
			portletId, title, content, serviceContext);
	}

	@Override
	public KBTemplate deleteKBTemplate(long kbTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbTemplateService.deleteKBTemplate(kbTemplateId);
	}

	@Override
	public void deleteKBTemplates(long groupId, long[] kbTemplateIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbTemplateService.deleteKBTemplates(groupId, kbTemplateIds);
	}

	@Override
	public java.util.List<KBTemplate> getGroupKBTemplates(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator) {

		return _kbTemplateService.getGroupKBTemplates(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getGroupKBTemplatesCount(long groupId) {
		return _kbTemplateService.getGroupKBTemplatesCount(groupId);
	}

	@Override
	public KBTemplate getKBTemplate(long kbTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbTemplateService.getKBTemplate(kbTemplateId);
	}

	@Override
	public com.liferay.knowledge.base.model.KBTemplateSearchDisplay
			getKBTemplateSearchDisplay(
				long groupId, String title, String content,
				java.util.Date startDate, java.util.Date endDate,
				boolean andOperator, int[] curStartValues, int cur, int delta,
				com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
					orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbTemplateService.getKBTemplateSearchDisplay(
			groupId, title, content, startDate, endDate, andOperator,
			curStartValues, cur, delta, orderByComparator);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kbTemplateService.getOSGiServiceIdentifier();
	}

	@Override
	public KBTemplate updateKBTemplate(
			long kbTemplateId, String title, String content,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbTemplateService.updateKBTemplate(
			kbTemplateId, title, content, serviceContext);
	}

	@Override
	public KBTemplateService getWrappedService() {
		return _kbTemplateService;
	}

	@Override
	public void setWrappedService(KBTemplateService kbTemplateService) {
		_kbTemplateService = kbTemplateService;
	}

	private KBTemplateService _kbTemplateService;

}