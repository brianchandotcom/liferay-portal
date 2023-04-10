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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;
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
				boolean clientExtension = false;
				String moduleName = null;

				List<FDSCellRendererCET> fdsCellRendererCETs =
					(List)_cetManager.getCETs(
						CompanyThreadLocal.getCompanyId(), null,
						ClientExtensionEntryConstants.TYPE_FDS_CELL_RENDERER,
						Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS),
						null);

				// Use the UI client extension if available

				for (FDSCellRendererCET fdsCellRendererCET :
						fdsCellRendererCETs) {

					if (!fdsCellRendererCET.isReadOnly()) {
						moduleName =
							"default from " + fdsCellRendererCET.getURL();

						clientExtension = true;

						break;
					}
				}

				// Use the workspace client extension if available

				if (moduleName == null) {
					for (FDSCellRendererCET fdsCellRendererCET :
							fdsCellRendererCETs) {

						if (Objects.equals(
								fdsCellRendererCET.getExternalReferenceCode(),
								"LXC:liferay-sample-fds-cell-renderer")) {

							moduleName =
								"default from " + fdsCellRendererCET.getURL();

							clientExtension = true;
						}
					}
				}

				// Use the built-in AMD provided sample as a last resort

				if (moduleName == null) {
					ServiceContext serviceContext =
						ServiceContextThreadLocal.getServiceContext();

					AbsolutePortalURLBuilder absolutePortalURLBuilder =
						_absolutePortalURLBuilderFactory.
							getAbsolutePortalURLBuilder(
								serviceContext.getRequest());

					String moduleURL = absolutePortalURLBuilder.forESModule(
						"frontend-data-set-sample-web", "index.js"
					).build();

					moduleName = "{GreenCheckDataRenderer} from " + moduleURL;
				}

				fdsTableSchemaField.setContentRendererClientExtension(
					clientExtension);
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
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	@Reference
	private CETManager _cetManager;

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}