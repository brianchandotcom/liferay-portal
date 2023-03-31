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

package com.liferay.frontend.data.set.sample.web.internal.view;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.FDSCellRendererCET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 * @author Javier de Arcos
 */
@Component(
	enabled = true,
	property = "frontend.data.set.name=" + FDSSampleFDSNames.CUSTOMIZED,
	service = FDSView.class
)
public class CustomizedTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"id", "id",
			fdsTableSchemaField -> fdsTableSchemaField.setActionId(
				"sampleEditMessage"
			).setContentRenderer(
				"actionLink"
			).setSortable(
				true
			)
		).add(
			"title", "title",
			fdsTableSchemaField -> fdsTableSchemaField.setSortable(true)
		).add(
			"creator.name", "author",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"sampleCustomDataRenderer")
		).add(
			"description", "description"
		).add(
			"date", "date"
		).add(
			"color", "color",
			fdsTableSchemaField -> {
				String moduleName = null;

				List<FDSCellRendererCET> fdsCellRendererCETs =
					(List)_cetManager.getCETs(
						CompanyThreadLocal.getCompanyId(), null,
						ClientExtensionEntryConstants.TYPE_FDS_CELL_RENDERER,
						Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS),
						null);

				// Try to use the first instance registered from the UI that is
				// found

				for (FDSCellRendererCET fdsCellRendererCET :
						fdsCellRendererCETs) {

					if (!fdsCellRendererCET.isReadOnly()) {
						moduleName =
							"default from " + fdsCellRendererCET.getURL();

						break;
					}
				}

				// Try to use the sample-workspace instance if none created from
				// the UI was found

				if (moduleName == null) {
					for (FDSCellRendererCET fdsCellRendererCET :
							fdsCellRendererCETs) {

						if (Objects.equals(
								fdsCellRendererCET.getExternalReferenceCode(),
								"LXC:liferay-sample-fds-cell-renderer")) {

							moduleName =
								"default from " + fdsCellRendererCET.getURL();
						}
					}
				}

				// Use the built-in AMD provided sample as a last resort

				if (moduleName == null) {
					moduleName = _npmResolver.resolveModuleName(
						"@liferay/frontend-data-set-sample-web/js" +
							"/GreenCheckDataRenderer");
				}

				fdsTableSchemaField.setContentRendererModuleURL(moduleName);
			}
		).add(
			"size", "size"
		).add(
			"status", "status",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"status")
		).build();
	}

	@Override
	public String getName() {
		return "customizedTable";
	}

	@Override
	public boolean isDefault() {
		return true;
	}

	@Override
	public boolean isQuickActionsEnabled() {
		return true;
	}

	@Reference
	private CETManager _cetManager;

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

	@Reference
	private NPMResolver _npmResolver;

}