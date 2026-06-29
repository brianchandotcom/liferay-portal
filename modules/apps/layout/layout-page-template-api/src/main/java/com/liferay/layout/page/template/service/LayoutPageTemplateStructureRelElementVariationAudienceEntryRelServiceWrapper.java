/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LayoutPageTemplateStructureRelElementVariationAudienceEntryRelService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureRelElementVariationAudienceEntryRelService
 * @generated
 */
public class
	LayoutPageTemplateStructureRelElementVariationAudienceEntryRelServiceWrapper
		implements LayoutPageTemplateStructureRelElementVariationAudienceEntryRelService,
				   ServiceWrapper
					   <LayoutPageTemplateStructureRelElementVariationAudienceEntryRelService> {

	public LayoutPageTemplateStructureRelElementVariationAudienceEntryRelServiceWrapper() {
		this(null);
	}

	public LayoutPageTemplateStructureRelElementVariationAudienceEntryRelServiceWrapper(
		LayoutPageTemplateStructureRelElementVariationAudienceEntryRelService
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelService) {

		_layoutPageTemplateStructureRelElementVariationAudienceEntryRelService =
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutPageTemplateStructureRelElementVariationAudienceEntryRelService.
			getOSGiServiceIdentifier();
	}

	@Override
	public LayoutPageTemplateStructureRelElementVariationAudienceEntryRelService
		getWrappedService() {

		return _layoutPageTemplateStructureRelElementVariationAudienceEntryRelService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateStructureRelElementVariationAudienceEntryRelService
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelService) {

		_layoutPageTemplateStructureRelElementVariationAudienceEntryRelService =
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelService;
	}

	private
		LayoutPageTemplateStructureRelElementVariationAudienceEntryRelService
			_layoutPageTemplateStructureRelElementVariationAudienceEntryRelService;

}
// LIFERAY-SERVICE-BUILDER-HASH:329433276