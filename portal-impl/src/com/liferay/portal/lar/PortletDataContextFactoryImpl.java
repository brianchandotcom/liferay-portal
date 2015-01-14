/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactory;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class PortletDataContextFactoryImpl
	implements PortletDataContextFactory {

	@Override
	public PortletDataContext clonePortletDataContext(
		PortletDataContext portletDataContext) {

		PortletDataContext clonePortletDataContext =
			new PortletDataContextImpl();

		clonePortletDataContext.setCompanyId(portletDataContext.getCompanyId());
		clonePortletDataContext.setCompanyGroupId(
			portletDataContext.getCompanyGroupId());
		clonePortletDataContext.setDataStrategy(
			portletDataContext.getDataStrategy());
		clonePortletDataContext.setEndDate(portletDataContext.getEndDate());
		clonePortletDataContext.setGroupId(portletDataContext.getGroupId());

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		clonePortletDataContext.setManifestSummary(
			(ManifestSummary)manifestSummary.clone());

		clonePortletDataContext.setNewLayouts(
			portletDataContext.getNewLayouts());
		clonePortletDataContext.setParameterMap(
			portletDataContext.getParameterMap());
		clonePortletDataContext.setScopeGroupId(
			portletDataContext.getScopeGroupId());
		clonePortletDataContext.setStartDate(portletDataContext.getStartDate());
		clonePortletDataContext.setUserIdStrategy(
			portletDataContext.getUserIdStrategy());
		clonePortletDataContext.setUserPersonalSiteGroupId(
			portletDataContext.getUserPersonalSiteGroupId());

		return clonePortletDataContext;
	}

	@Override
	public PortletDataContext createExportPortletDataContext(
			long companyId, long groupId, Map<String, String[]> parameterMap,
			Date startDate, Date endDate, ZipWriter zipWriter)
		throws PortletDataException {

		validateDateRange(startDate, endDate);

		PortletDataContext portletDataContext = createPortletDataContext(
			companyId, groupId);

		portletDataContext.setEndDate(endDate);
		portletDataContext.setParameterMap(parameterMap);
		portletDataContext.setStartDate(startDate);
		portletDataContext.setZipWriter(zipWriter);

		return portletDataContext;
	}

	@Override
	public PortletDataContext createImportPortletDataContext(
		long companyId, long groupId, Map<String, String[]> parameterMap,
		UserIdStrategy userIdStrategy, ZipReader zipReader) {

		PortletDataContext portletDataContext = createPortletDataContext(
			companyId, groupId);

		String dataStrategy = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.DATA_STRATEGY,
			PortletDataHandlerKeys.DATA_STRATEGY_MIRROR);

		portletDataContext.setDataStrategy(dataStrategy);

		portletDataContext.setNewLayouts(new ArrayList<Layout>());
		portletDataContext.setParameterMap(parameterMap);
		portletDataContext.setUserIdStrategy(userIdStrategy);
		portletDataContext.setZipReader(zipReader);

		return portletDataContext;
	}

	@Override
	public PortletDataContext createPreparePortletDataContext(
			long companyId, long groupId, Date startDate, Date endDate)
		throws PortletDataException {

		validateDateRange(startDate, endDate);

		PortletDataContext portletDataContext = createPortletDataContext(
			companyId, groupId);

		portletDataContext.setEndDate(endDate);
		portletDataContext.setStartDate(startDate);

		return portletDataContext;
	}

	@Override
	public PortletDataContext createPreparePortletDataContext(
			ThemeDisplay themeDisplay, Date startDate, Date endDate)
		throws PortletDataException {

		return createPreparePortletDataContext(
			themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
			startDate, endDate);
	}

	protected PortletDataContext createPortletDataContext(
		long companyId, long groupId) {

		PortletDataContext portletDataContext = new PortletDataContextImpl();

		try {
			Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
				companyId);

			portletDataContext.setCompanyGroupId(companyGroup.getGroupId());
		}
		catch (Exception e) {
			if (!CompanyThreadLocal.isDeleteInProcess()) {
				throw new IllegalStateException(e);
			}
		}

		portletDataContext.setCompanyId(companyId);
		portletDataContext.setGroupId(groupId);
		portletDataContext.setScopeGroupId(groupId);

		try {
			Group userPersonalSiteGroup =
				GroupLocalServiceUtil.getUserPersonalSiteGroup(companyId);

			portletDataContext.setUserPersonalSiteGroupId(
				userPersonalSiteGroup.getGroupId());
		}
		catch (Exception e) {
			if (!CompanyThreadLocal.isDeleteInProcess()) {
				throw new IllegalStateException(e);
			}
		}

		return portletDataContext;
	}

	protected void validateDateRange(Date startDate, Date endDate)
		throws PortletDataException {

		if ((startDate == null) && (endDate != null)) {
			throw new PortletDataException(
				PortletDataException.END_DATE_IS_MISSING_START_DATE);
		}
		else if ((startDate != null) && (endDate == null)) {
			throw new PortletDataException(
				PortletDataException.START_DATE_IS_MISSING_END_DATE);
		}

		if (startDate != null) {
			if (startDate.after(endDate) || startDate.equals(endDate)) {
				throw new PortletDataException(
					PortletDataException.START_DATE_AFTER_END_DATE);
			}

			Date now = new Date();

			if (startDate.after(now)) {
				throw new PortletDataException(
					PortletDataException.FUTURE_START_DATE);
			}

			if (endDate.after(now)) {
				throw new PortletDataException(
					PortletDataException.FUTURE_END_DATE);
			}
		}
	}

}