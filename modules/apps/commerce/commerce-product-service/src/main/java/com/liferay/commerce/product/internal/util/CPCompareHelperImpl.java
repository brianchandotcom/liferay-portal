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

import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.util.CPCompareHelper;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(enabled = false, immediate = true, service = CPCompareHelper.class)
public class CPCompareHelperImpl implements CPCompareHelper {

	@Override
	public void addCompareProduct(
			long groupId, long commerceAccountId, long cpDefinitionId,
			HttpSession httpSession)
		throws PortalException {

		List<Long> cpDefinitionIds = getCPDefinitionIds(
			groupId, commerceAccountId, httpSession);

		if (!cpDefinitionIds.contains(cpDefinitionId)) {
			cpDefinitionIds.add(cpDefinitionId);
		}

		setCPDefinitionIds(groupId, cpDefinitionIds, httpSession);
	}

	@Override
	public List<CPCatalogEntry> getCPCatalogEntries(
			long groupId, long commerceAccountId, HttpSession httpSession)
		throws PortalException {

		List<Long> cpDefinitionIds = (List<Long>)httpSession.getAttribute(
			_getSessionAttributeKey(groupId));

		if (cpDefinitionIds == null) {
			return new ArrayList<>();
		}

		List<CPCatalogEntry> cpCatalogEntries = new ArrayList<>();

		for (long cpDefinitionId : cpDefinitionIds) {
			CPCatalogEntry cpCatalogEntry = null;

			try {
				cpCatalogEntry = _cpDefinitionHelper.getCPCatalogEntry(
					commerceAccountId, groupId, cpDefinitionId,
					LocaleUtil.getDefault());
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(pe, pe);
				}

				continue;
			}

			if (cpCatalogEntry != null) {
				cpCatalogEntries.add(cpCatalogEntry);
			}
		}

		return cpCatalogEntries;
	}

	public List<Long> getCPDefinitionIds(
		long groupId, long commerceAccountId, HttpSession httpSession) {

		List<Long> cpDefinitionIds = (List<Long>)httpSession.getAttribute(
			_getSessionAttributeKey(groupId));

		if (cpDefinitionIds == null) {
			return new ArrayList<>();
		}

		List<Long> activeCPDefinitionIds = new ArrayList<>();

		for (long cpDefinitionId : cpDefinitionIds) {
			CPCatalogEntry cpCatalogEntry = null;

			try {
				cpCatalogEntry = _cpDefinitionHelper.getCPCatalogEntry(
					commerceAccountId, groupId, cpDefinitionId,
					LocaleUtil.getDefault());
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(portalException, portalException);
				}

				continue;
			}

			if (cpCatalogEntry != null) {
				activeCPDefinitionIds.add(cpDefinitionId);
			}
		}

		setCPDefinitionIds(groupId, activeCPDefinitionIds, httpSession);

		return activeCPDefinitionIds;
	}

	@Override
	public void removeCompareProduct(
			long groupId, long commerceAccountId, long cpDefinitionId,
			HttpSession httpSession)
		throws PortalException {

		List<Long> cpDefinitionIds = getCPDefinitionIds(
			groupId, commerceAccountId, httpSession);

		if (cpDefinitionIds.contains(cpDefinitionId)) {
			cpDefinitionIds.remove(cpDefinitionId);
		}

		setCPDefinitionIds(groupId, cpDefinitionIds, httpSession);
	}

	@Override
	public void setCPDefinitionIds(
		long groupId, List<Long> cpDefinitionIds, HttpSession httpSession) {

		httpSession.setAttribute(
			_getSessionAttributeKey(groupId), cpDefinitionIds);
	}

	private String _getSessionAttributeKey(long groupId) {
		return "LIFERAY_SHARED_CP_DEFINITION_IDS_" + groupId;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPCompareHelperImpl.class);

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

}