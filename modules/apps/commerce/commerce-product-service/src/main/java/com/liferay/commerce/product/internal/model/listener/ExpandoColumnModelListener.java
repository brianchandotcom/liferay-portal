/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.model.listener;

import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(service = ModelListener.class)
public class ExpandoColumnModelListener
	extends BaseModelListener<ExpandoColumn> {

	public void onAfterRemove(ExpandoColumn expandoColumn)
		throws ModelListenerException {

		if (expandoColumn == null) {
			return;
		}

		try {
			if (_checkCPOptionValueExpandoColumn(expandoColumn.getTableId())) {
				_removeCPDefinitionOptionValueRelExpandoColumn(expandoColumn);
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	public void onAfterUpdate(
			ExpandoColumn originalExpandoColumn, ExpandoColumn expandoColumn)
		throws ModelListenerException {

		try {
			if (_checkCPOptionValueExpandoColumn(expandoColumn.getTableId())) {
				_copyToCPDefinitionOptionValueRelExpandoColumn(expandoColumn);
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private boolean _checkCPOptionValueExpandoColumn(long tableId)
		throws PortalException {

		ExpandoTable expandoTable = _expandoTableLocalService.fetchExpandoTable(
			tableId);

		if (expandoTable == null) {
			return false;
		}

		return Objects.equals(
			expandoTable.getClassName(), CPOptionValue.class.getName());
	}

	private void _copyToCPDefinitionOptionValueRelExpandoColumn(
			ExpandoColumn cpOptionValueExpandoColumn)
		throws PortalException {

		ExpandoTable cpDefinitionOptionValueRelExpandoTable =
			_expandoTableLocalService.addDefaultTable(
				cpOptionValueExpandoColumn.getCompanyId(),
				_classNameLocalService.getClassNameId(
					CPDefinitionOptionValueRel.class.getName()));

		ExpandoColumn cpDefinitionOptionValueRelExpandoColumn =
			_expandoColumnLocalService.addColumn(
				cpDefinitionOptionValueRelExpandoTable.getTableId(),
				cpOptionValueExpandoColumn.getName(),
				cpOptionValueExpandoColumn.getType(),
				cpOptionValueExpandoColumn.getDefaultValue());

		cpDefinitionOptionValueRelExpandoColumn.setTypeSettingsProperties(
			cpOptionValueExpandoColumn.getTypeSettingsProperties());

		_expandoColumnLocalService.updateExpandoColumn(
			cpDefinitionOptionValueRelExpandoColumn);
	}

	private void _removeCPDefinitionOptionValueRelExpandoColumn(
			ExpandoColumn cpOptionValueExpandoColumn)
		throws PortalException {

		ExpandoTable cpDefinitionOptionValueRelExpandoTable =
			_expandoTableLocalService.fetchDefaultTable(
				cpOptionValueExpandoColumn.getCompanyId(),
				_classNameLocalService.getClassNameId(
					CPDefinitionOptionValueRel.class.getName()));

		if (cpDefinitionOptionValueRelExpandoTable == null) {
			return;
		}

		_expandoColumnLocalService.deleteColumn(
			cpDefinitionOptionValueRelExpandoTable.getTableId(),
			cpOptionValueExpandoColumn.getName());

		int count = _expandoColumnLocalService.getColumnsCount(
			cpDefinitionOptionValueRelExpandoTable.getTableId());

		if (count == 0) {
			_expandoTableLocalService.deleteTable(
				cpDefinitionOptionValueRelExpandoTable.getTableId());
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

}