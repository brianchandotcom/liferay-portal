/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.web.internal.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsPortletKeys;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLinkLocalService;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLocalService;
import com.liferay.portal.workflow.kaleo.forms.service.permission.KaleoProcessPermission;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + KaleoFormsPortletKeys.KALEO_FORMS_ADMIN,
	service = AssetRendererFactory.class
)
public class KaleoProcessAssetRendererFactory
	extends BaseAssetRendererFactory<KaleoProcess> {

	public static final String TYPE = "kaleoProcess";

	public KaleoProcessAssetRendererFactory() {
		setCategorizable(false);
		setClassName(KaleoProcess.class.getName());
		setPortletId(KaleoFormsPortletKeys.KALEO_FORMS_ADMIN);
		setSearchable(false);
		setSelectable(true);
	}

	@Override
	public AssetRenderer<KaleoProcess> getAssetRenderer(long classPK, int type)
		throws PortalException {

		DDLRecord record = _ddlRecordLocalService.fetchDDLRecord(classPK);

		KaleoProcess kaleoProcess =
			_kaleoProcessLocalService.getDDLRecordSetKaleoProcess(
				record.getRecordSetId());

		DDLRecordVersion recordVersion = null;

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

		KaleoProcessAssetRenderer kaleoProcessAssetRenderer =
			new KaleoProcessAssetRenderer(kaleoProcess, record, recordVersion);

		kaleoProcessAssetRenderer.setAssetRendererType(type);
		kaleoProcessAssetRenderer.setKaleoProcessLinkLocalService(
			_kaleoProcessLinkLocalService);
		kaleoProcessAssetRenderer.setServletContext(_servletContext);

		return kaleoProcessAssetRenderer;
	}

	@Override
	public String getClassName() {
		return KaleoProcess.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "kaleo-process";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return KaleoProcessPermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.workflow.kaleo.forms.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordLocalService(
		DDLRecordLocalService ddlRecordLocalService) {

		_ddlRecordLocalService = ddlRecordLocalService;
	}

	@Reference(unbind = "-")
	protected void setKaleoProcessLinkLocalService(
		KaleoProcessLinkLocalService kaleoProcessLinkLocalService) {

		_kaleoProcessLinkLocalService = kaleoProcessLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setKaleoProcessLocalService(
		KaleoProcessLocalService kaleoProcessLocalService) {

		_kaleoProcessLocalService = kaleoProcessLocalService;
	}

	private DDLRecordLocalService _ddlRecordLocalService;
	private KaleoProcessLinkLocalService _kaleoProcessLinkLocalService;
	private KaleoProcessLocalService _kaleoProcessLocalService;
	private ServletContext _servletContext;

}