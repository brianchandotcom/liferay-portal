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

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPFriendlyURLEntry;
import com.liferay.commerce.product.service.CPFriendlyURLEntryLocalService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CPDefinitionHelperImpl implements CPDefinitionHelper {

	@Override
	public String getFriendlyURL(long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException {

		long classNameId = _portal.getClassNameId(CPDefinition.class);

		CPFriendlyURLEntry cpFriendlyURLEntry =
			_cpFriendlyURLEntryLocalService.fetchCPFriendlyURLEntry(
				themeDisplay.getScopeGroupId(), themeDisplay.getCompanyId(),
				classNameId, cpDefinitionId, themeDisplay.getLanguageId(),
				true);

		if (cpFriendlyURLEntry == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"No friendly url found for cpDefinitionId : " +
						cpDefinitionId);
			}

			return StringPool.BLANK;
		}

		return themeDisplay.getPortalURL() + CPConstants.SEPARATOR_PRODUCT_URL +
			cpFriendlyURLEntry.getUrlTitle();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionHelperImpl.class);

	@Reference
	private CPFriendlyURLEntryLocalService _cpFriendlyURLEntryLocalService;

	@Reference
	private Portal _portal;

}