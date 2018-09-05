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

package com.liferay.dynamic.data.mapping.form.web.internal.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.web.internal.constants.DDMFormWebKeys;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.DDMFormViewFormInstanceRecordDisplayContext;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Leonardo Barros
 */
public class DDMFormAssetRenderer
	extends BaseJSPAssetRenderer<DDMFormInstanceRecord> {

	public DDMFormAssetRenderer(
		DDMFormInstanceRecord formInstanceRecord,
		DDMFormInstanceRecordVersion formInstanceRecordVersion,
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService,
		DDMFormInstanceVersionLocalService formInstanceVersionLocalService,
		DDMFormRenderer ddmFormRenderer,
		DDMFormValuesFactory ddmFormValuesFactory,
		DDMFormValuesMerger ddmFormValuesMerger,
		ModelResourcePermission<DDMFormInstance>
			ddmFormInstanceModelResourcePermission) {

		_formInstanceRecord = formInstanceRecord;
		_formInstanceRecordVersion = formInstanceRecordVersion;
		_ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
		_ddmFormInstanceVersionLocalService = formInstanceVersionLocalService;
		_ddmFormRenderer = ddmFormRenderer;
		_ddmFormValuesFactory = ddmFormValuesFactory;
		_ddmFormValuesMerger = ddmFormValuesMerger;
		_ddmFormInstanceModelResourcePermission =
			ddmFormInstanceModelResourcePermission;

		DDMFormInstance formInstance = null;

		try {
			formInstance = _formInstanceRecordVersion.getFormInstance();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		_formInstance = formInstance;
	}

	@Override
	public DDMFormInstanceRecord getAssetObject() {
		return _formInstanceRecord;
	}

	@Override
	public AssetRendererFactory<DDMFormInstanceRecord>
		getAssetRendererFactory() {

		return new DDMFormAssetRendererFactory();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _formInstance.getAvailableLanguageIds();
	}

	@Override
	public String getClassName() {
		return DDMFormInstanceRecord.class.getName();
	}

	@Override
	public long getClassPK() {
		return _formInstanceRecord.getFormInstanceRecordId();
	}

	@Override
	public long getGroupId() {
		return _formInstanceRecord.getGroupId();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/asset/full_content.jsp";
		}

		return null;
	}

	@Override
	public int getStatus() {
		return _formInstanceRecordVersion.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return _formInstance.getName(locale);
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
		return _formInstanceRecord.getUserId();
	}

	@Override
	public String getUserName() {
		return _formInstanceRecord.getUserName();
	}

	@Override
	public String getUuid() {
		return _formInstanceRecord.getUuid();
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		try {
			return _ddmFormInstanceModelResourcePermission.contains(
				permissionChecker, _formInstance, ActionKeys.VIEW);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return false;
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute(
			DDMFormWebKeys.DYNAMIC_DATA_MAPPING_FORM_INSTANCE_RECORD,
			_formInstanceRecord);

		DDMFormViewFormInstanceRecordDisplayContext
			ddmFormViewFormInstanceRecordDisplayContext =
				new DDMFormViewFormInstanceRecordDisplayContext(
					request, response, _ddmFormInstanceRecordLocalService,
					_ddmFormInstanceVersionLocalService, _ddmFormRenderer,
					_ddmFormValuesFactory, _ddmFormValuesMerger);

		request.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			ddmFormViewFormInstanceRecordDisplayContext);

		return super.include(request, response, template);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormAssetRenderer.class);

	private final ModelResourcePermission<DDMFormInstance>
		_ddmFormInstanceModelResourcePermission;
	private final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;
	private final DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService;
	private final DDMFormRenderer _ddmFormRenderer;
	private final DDMFormValuesFactory _ddmFormValuesFactory;
	private final DDMFormValuesMerger _ddmFormValuesMerger;
	private final DDMFormInstance _formInstance;
	private final DDMFormInstanceRecord _formInstanceRecord;
	private final DDMFormInstanceRecordVersion _formInstanceRecordVersion;

}