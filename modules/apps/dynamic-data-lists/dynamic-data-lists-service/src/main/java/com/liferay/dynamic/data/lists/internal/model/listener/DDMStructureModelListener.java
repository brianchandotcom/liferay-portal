/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(service = ModelListener.class)
public class DDMStructureModelListener extends BaseModelListener<DDMStructure> {

	@Override
	public void onAfterUpdate(
			DDMStructure originalDDMStructure, DDMStructure ddmStructure)
		throws ModelListenerException {

		try {
			ActionableDynamicQuery actionableDynamicQuery =
				_getActionableDynamicQuery(ddmStructure);

			actionableDynamicQuery.performActions();
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private ActionableDynamicQuery _getActionableDynamicQuery(
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
				if (Validator.isNull(
						recordSet.getName(
							LocaleUtil.getSiteDefault(), false))) {

					recordSet.setName(
						ddmStructure.getName(LocaleUtil.getSiteDefault()));
				}

				Locale siteLocale = null;

				if (ExportImportThreadLocal.isImportInProcess()) {
					siteLocale = LocaleThreadLocal.getSiteDefaultLocale();

					Locale stagingLocale = LocaleUtil.fromLanguageId(
						ddmStructure.getDefaultLanguageId());

					LocaleThreadLocal.setSiteDefaultLocale(stagingLocale);
				}

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddGroupPermissions(true);
				serviceContext.setAddGuestPermissions(true);
				serviceContext.setScopeGroupId(recordSet.getGroupId());
				serviceContext.setUserId(
					_userLocalService.getGuestUserId(recordSet.getCompanyId()));

				try {
					_ddlRecordSetLocalService.updateRecordSet(
						recordSet.getRecordSetId(),
						ddmStructure.getStructureId(), recordSet.getNameMap(),
						recordSet.getDescriptionMap(),
						recordSet.getMinDisplayRows(), serviceContext);
				}
				finally {
					if (ExportImportThreadLocal.isImportInProcess()) {
						LocaleThreadLocal.setSiteDefaultLocale(siteLocale);
					}
				}
			});

		return actionableDynamicQuery;
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private UserLocalService _userLocalService;

}