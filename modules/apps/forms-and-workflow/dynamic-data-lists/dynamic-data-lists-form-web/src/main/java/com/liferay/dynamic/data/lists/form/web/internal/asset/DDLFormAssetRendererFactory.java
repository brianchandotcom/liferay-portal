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

package com.liferay.dynamic.data.lists.form.web.internal.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLFormRecord;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.model.impl.DDLFormRecordImpl;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordPermission;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM
	},
	service = AssetRendererFactory.class
)
public class DDLFormAssetRendererFactory
	extends BaseAssetRendererFactory<DDLFormRecord> {

	public static final String TYPE = "form";

	public DDLFormAssetRendererFactory() {
		setCategorizable(false);
		setClassName(DDLFormRecord.class.getName());
		setPortletId(DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM);
		setSearchable(true);
		setSelectable(false);
	}

	@Override
	public AssetRenderer<DDLFormRecord> getAssetRenderer(long classPK, int type)
		throws PortalException {

		DDLRecord record = _ddlRecordLocalService.fetchDDLRecord(classPK);

		DDLRecordVersion recordVersion = null;

		if (record == null) {
			recordVersion = _ddlRecordVersionLocalService.getRecordVersion(
				classPK);

			record = recordVersion.getRecord();
		}
		else {
			if (type == TYPE_LATEST) {
				recordVersion = record.getLatestRecordVersion();
			}
			else if (type == TYPE_LATEST_APPROVED) {
				recordVersion = record.getRecordVersion();
			}
			else {
				throw new IllegalArgumentException(
					"Unknown asset renderer type " + type);
			}
		}

		return createAssetRenderer(record, recordVersion, type);
	}

	@Override
	public String getClassName() {
		return DDLFormRecord.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "dynamic-data-list";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return DDLRecordPermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.dynamic.data.lists.form.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected AssetRenderer<DDLFormRecord> createAssetRenderer(
		DDLRecord record, DDLRecordVersion recordVersion, int type) {

		DDLFormRecord formRecord = new DDLFormRecordImpl(record);

		DDLFormAssetRenderer ddlFormAssetRenderer = new DDLFormAssetRenderer(
			formRecord, recordVersion, _ddlRecordLocalService, _ddmFormRenderer,
			_ddmFormValuesFactory, _ddmFormValuesMerger,
			_ddmStructureLocalService);

		ddlFormAssetRenderer.setAssetRendererType(type);
		ddlFormAssetRenderer.setServletContext(_servletContext);

		return ddlFormAssetRenderer;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordLocalService(
		DDLRecordLocalService ddlRecordLocalService) {

		_ddlRecordLocalService = ddlRecordLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordVersionLocalService(
		DDLRecordVersionLocalService ddlRecordVersionLocalService) {

		_ddlRecordVersionLocalService = ddlRecordVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormRenderer(DDMFormRenderer ddmFormRenderer) {
		_ddmFormRenderer = ddmFormRenderer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesFactory(
		DDMFormValuesFactory ddmFormValuesFactory) {

		_ddmFormValuesFactory = ddmFormValuesFactory;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesMerger(
		DDMFormValuesMerger ddmFormValuesMerger) {

		_ddmFormValuesMerger = ddmFormValuesMerger;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	private DDLRecordLocalService _ddlRecordLocalService;
	private DDLRecordVersionLocalService _ddlRecordVersionLocalService;
	private DDMFormRenderer _ddmFormRenderer;
	private DDMFormValuesFactory _ddmFormValuesFactory;
	private DDMFormValuesMerger _ddmFormValuesMerger;
	private DDMStructureLocalService _ddmStructureLocalService;
	private ServletContext _servletContext;

}