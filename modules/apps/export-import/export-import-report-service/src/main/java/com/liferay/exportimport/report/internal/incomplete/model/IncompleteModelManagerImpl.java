/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.internal.incomplete.model;

import com.liferay.exportimport.kernel.incomplete.model.IncompleteModelManager;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.report.service.ImportReportEntryLocalService;
import com.liferay.petra.function.UnsafeBiFunction;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.lazy.referencing.LazyReferencingThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.function.BiFunction;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = IncompleteModelManager.class)
public class IncompleteModelManagerImpl implements IncompleteModelManager {

	@Override
	public <T, E extends Exception> T getOrAddIncompleteModel(
			Class<T> clazz, long companyId, String entityExternalReferenceCode,
			BiFunction<String, Long, T> fetchByExternalReferenceCodeBiFunction,
			UnsafeBiFunction<String, Long, T, E>
				getByExternalReferenceCodeUnsafeBiFunction,
			UnsafeSupplier<T, E> incompleteModelUnsafeSupplier)
		throws E {

		if (!LazyReferencingThreadLocal.isEnabled()) {
			return getByExternalReferenceCodeUnsafeBiFunction.apply(
				entityExternalReferenceCode, companyId);
		}

		T model = fetchByExternalReferenceCodeBiFunction.apply(
			entityExternalReferenceCode, companyId);

		if (model != null) {
			return model;
		}

		try (SafeCloseable safeCloseable =
				IncompleteModelThreadLocal.setIncompleteModelWithSafeCloseable(
					true)) {

			_importReportEntryLocalService.addIncompleteImportReportEntry(
				companyId, 0L,
				GetterUtil.getLong(ExportImportThreadLocal.getClassNameId()),
				GetterUtil.getLong(ExportImportThreadLocal.getClassPK()),
				_classNameLocalService.getClassNameId(clazz.getName()),
				entityExternalReferenceCode);

			return incompleteModelUnsafeSupplier.get();
		}
	}

	@Override
	public <T, E extends Exception> T getOrAddIncompleteModel(
			Class<T> clazz, String entityExternalReferenceCode,
			BiFunction<String, Long, T> fetchByExternalReferenceCodeBiFunction,
			UnsafeBiFunction<String, Long, T, E>
				getByExternalReferenceCodeUnsafeBiFunction,
			long groupId, UnsafeSupplier<T, E> incompleteModelUnsafeSupplier)
		throws E {

		if (!LazyReferencingThreadLocal.isEnabled()) {
			return getByExternalReferenceCodeUnsafeBiFunction.apply(
				entityExternalReferenceCode, groupId);
		}

		T model = fetchByExternalReferenceCodeBiFunction.apply(
			entityExternalReferenceCode, groupId);

		if (model != null) {
			return model;
		}

		Group group = _groupLocalService.fetchGroup(groupId);

		try (SafeCloseable safeCloseable =
				IncompleteModelThreadLocal.setIncompleteModelWithSafeCloseable(
					true)) {

			_importReportEntryLocalService.addIncompleteImportReportEntry(
				group.getCompanyId(), groupId,
				GetterUtil.getLong(ExportImportThreadLocal.getClassNameId()),
				GetterUtil.getLong(ExportImportThreadLocal.getClassPK()),
				_classNameLocalService.getClassNameId(clazz.getName()),
				entityExternalReferenceCode);

			return incompleteModelUnsafeSupplier.get();
		}
	}

	@Override
	public boolean isIncompleteModel() {
		return IncompleteModelThreadLocal.isIncompleteModel();
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ImportReportEntryLocalService _importReportEntryLocalService;

}