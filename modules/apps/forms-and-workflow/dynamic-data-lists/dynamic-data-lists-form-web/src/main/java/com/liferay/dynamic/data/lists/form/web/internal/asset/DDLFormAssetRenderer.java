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

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.dynamic.data.lists.constants.DDLWebKeys;
import com.liferay.dynamic.data.lists.form.web.internal.display.context.DDLFormViewRecordDisplayContext;
import com.liferay.dynamic.data.lists.model.DDLFormRecord;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordSetPermission;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Leonardo Barros
 */
public class DDLFormAssetRenderer extends BaseJSPAssetRenderer<DDLFormRecord> {

	public DDLFormAssetRenderer(
		DDLFormRecord formRecord, DDLRecordVersion recordVersion,
		DDLRecordLocalService ddlRecordLocalService,
		DDMFormRenderer ddmFormRenderer,
		DDMFormValuesFactory ddmFormValuesFactory,
		DDMFormValuesMerger ddmFormValuesMerger,
		DDMStructureLocalService ddmStructureLocalService) {

		_formRecord = formRecord;
		_recordVersion = recordVersion;
		_ddlRecordLocalService = ddlRecordLocalService;
		_ddmFormRenderer = ddmFormRenderer;
		_ddmFormValuesFactory = ddmFormValuesFactory;
		_ddmFormValuesMerger = ddmFormValuesMerger;
		_ddmStructureLocalService = ddmStructureLocalService;

		_record = formRecord.getDDLRecord();

		DDLRecordSet recordSet = null;

		try {
			recordSet = _recordVersion.getRecordSet();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		_recordSet = recordSet;
	}

	@Override
	public DDLFormRecord getAssetObject() {
		return _formRecord;
	}

	@Override
	public AssetRendererFactory<DDLFormRecord> getAssetRendererFactory() {
		return new DDLFormAssetRendererFactory();
	}

	@Override
	public String getClassName() {
		return DDLFormRecord.class.getName();
	}

	@Override
	public long getClassPK() {
		return _record.getRecordId();
	}

	@Override
	public long getGroupId() {
		return _record.getGroupId();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/asset/full_content.jsp";
		}
		else {
			return null;
		}
	}

	@Override
	public int getStatus() {
		return _recordVersion.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return _recordSet.getName(locale);
	}

	@Override
	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		return noSuchEntryRedirect;
	}

	@Override
	public long getUserId() {
		return _record.getUserId();
	}

	@Override
	public String getUserName() {
		return _record.getUserName();
	}

	@Override
	public String getUuid() {
		return _record.getUuid();
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return DDLRecordSetPermission.contains(
			permissionChecker, _recordSet, ActionKeys.VIEW);
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD, _formRecord);

		DDLFormViewRecordDisplayContext ddlFormViewRecordDisplayContext =
			new DDLFormViewRecordDisplayContext(
				request, response, _ddlRecordLocalService, _ddmFormRenderer,
				_ddmFormValuesFactory, _ddmFormValuesMerger,
				_ddmStructureLocalService);

		request.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, ddlFormViewRecordDisplayContext);

		return super.include(request, response, template);
	}

	protected DDLRecord getDDLRecord() {
		return _formRecord.getDDLRecord();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLFormAssetRenderer.class);

	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DDMFormRenderer _ddmFormRenderer;
	private final DDMFormValuesFactory _ddmFormValuesFactory;
	private final DDMFormValuesMerger _ddmFormValuesMerger;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDLFormRecord _formRecord;
	private final DDLRecord _record;
	private final DDLRecordSet _recordSet;
	private final DDLRecordVersion _recordVersion;

}