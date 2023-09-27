/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.expando.kernel.model.ExpandoRow;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.language.LanguageResources;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ConstraintResolver.class)
public class ExpandoRowConstraintResolver
	implements ConstraintResolver<ExpandoRow> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-expando-row";
	}

	@Override
	public Class<ExpandoRow> getModelClass() {
		return ExpandoRow.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "the-conflicting-expando-row-was-deleted";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return LanguageResources.getResourceBundle(locale);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"tableId", "classPK"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<ExpandoRow> constraintResolverContext)
		throws PortalException {

		ExpandoRow expandoRow = constraintResolverContext.getSourceCTModel();

		List<ExpandoValue> expandoValues =
			_expandoValueLocalService.getRowValues(expandoRow.getRowId());

		_expandoRowLocalService.deleteRow(expandoRow);

		for (ExpandoValue expandoValue : expandoValues) {
			_expandoValueLocalService.addValue(
				expandoValue.getClassNameId(), expandoValue.getTableId(),
				expandoValue.getColumnId(), expandoValue.getClassPK(),
				expandoValue.getData());
		}
	}

	@Reference
	private ExpandoRowLocalService _expandoRowLocalService;

	@Reference
	private ExpandoValueLocalService _expandoValueLocalService;

}