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

package com.liferay.dynamic.data.lists.internal.model.listener;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = ModelListener.class)
public class DDMStructureModelListener extends BaseModelListener<DDMStructure> {

	@Override
	public void onAfterUpdate(DDMStructure ddmStructure)
		throws ModelListenerException {

		try {
			ActionableDynamicQuery actionableDynamicQuery =
				getActionableDynamicQuery(ddmStructure);

			actionableDynamicQuery.performActions();
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	protected ActionableDynamicQuery getActionableDynamicQuery(
		DDMStructure ddmStructure) {

		ActionableDynamicQuery actionableDynamicQuery =
			_ddlRecordSetLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property ddmStructureIdProperty = PropertyFactoryUtil.forName(
					"DDMStructureId");

				dynamicQuery.add(
					ddmStructureIdProperty.eq(ddmStructure.getStructureId()));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(DDLRecordSet recordSet) -> {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddGuestPermissions(true);
				serviceContext.setAddGroupPermissions(true);

				serviceContext.setScopeGroupId(recordSet.getGroupId());

				long defaultUserId = _userLocalService.getDefaultUserId(
					recordSet.getCompanyId());

				serviceContext.setUserId(defaultUserId);

				Locale siteLocale = null;

				if (ExportImportThreadLocal.isImportInProcess()) {
					siteLocale = LocaleThreadLocal.getSiteDefaultLocale();

					Locale stagingLocale = LocaleUtil.fromLanguageId(
						ddmStructure.getDefaultLanguageId());

					LocaleThreadLocal.setSiteDefaultLocale(stagingLocale);
				}

				_ddlRecordSetLocalService.updateRecordSet(
					recordSet.getRecordSetId(), ddmStructure.getStructureId(),
					recordSet.getNameMap(), recordSet.getDescriptionMap(),
					recordSet.getMinDisplayRows(), serviceContext);

				if (ExportImportThreadLocal.isImportInProcess()) {
					LocaleThreadLocal.setSiteDefaultLocale(siteLocale);
				}
			});

		return actionableDynamicQuery;
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private UserLocalService _userLocalService;

}