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

package com.liferay.commerce.product.definitions.web.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.AssetUtil;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionAssetRenderer
	extends BaseJSPAssetRenderer<CPDefinition> implements TrashRenderer {

	public CPDefinitionAssetRenderer(CPDefinition cpDefinition) {
		_cpDefinition = cpDefinition;
	}

	@Override
	public CPDefinition getAssetObject() {
		return _cpDefinition;
	}

	@Override
	public String getClassName() {
		return CPDefinition.class.getName();
	}

	@Override
	public long getClassPK() {
		return _cpDefinition.getCPDefinitionId();
	}

	@Override
	public Date getDisplayDate() {
		return _cpDefinition.getDisplayDate();
	}

	@Override
	public long getGroupId() {
		return _cpDefinition.getGroupId();
	}

	@Override
	public String getJspPath(
		HttpServletRequest httpServletRequest, String template) {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	@Override
	public String getPortletId() {
		AssetRendererFactory<CPDefinition> assetRendererFactory =
			getAssetRendererFactory();

		return assetRendererFactory.getPortletId();
	}

	@Override
	public int getStatus() {
		return _cpDefinition.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		int abstractLength = AssetUtil.ASSET_ENTRY_ABSTRACT_LENGTH;

		if (portletRequest != null) {
			abstractLength = GetterUtil.getInteger(
				portletRequest.getAttribute(
					WebKeys.ASSET_ENTRY_ABSTRACT_LENGTH),
				AssetUtil.ASSET_ENTRY_ABSTRACT_LENGTH);
		}

		String summary = _cpDefinition.getDescription();

		if (Validator.isNull(summary)) {
			summary = HtmlUtil.stripHtml(
				StringUtil.shorten(
					_cpDefinition.getDescriptionMapAsXML(), abstractLength));
		}

		return summary;
	}

	@Override
	public String getTitle(Locale locale) {
		String languageId = LanguageUtil.getLanguageId(locale);

		return _cpDefinition.getName(languageId);
	}

	@Override
	public String getType() {
		return CPDefinitionAssetRendererFactory.TYPE;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(
			_cpDefinition.getGroupId());

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, group, CPPortletKeys.CP_DEFINITIONS, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/editProductDefinition");
		portletURL.setParameter(
			"cpDefinitionId",
			String.valueOf(_cpDefinition.getCPDefinitionId()));

		return portletURL;
	}

	@Override
	public String getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		AssetRendererFactory<CPDefinition> assetRendererFactory =
			getAssetRendererFactory();

		PortletURL portletURL = assetRendererFactory.getURLView(
			liferayPortletResponse, windowState);

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter(
			"cpDefinitionId",
			String.valueOf(_cpDefinition.getCPDefinitionId()));
		portletURL.setWindowState(windowState);

		return portletURL.toString();
	}

	@Override
	public long getUserId() {
		return _cpDefinition.getUserId();
	}

	@Override
	public String getUserName() {
		return _cpDefinition.getUserName();
	}

	@Override
	public String getUuid() {
		return _cpDefinition.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		return CPDefinitionPermission.contains(
			permissionChecker, _cpDefinition, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return CPDefinitionPermission.contains(
			permissionChecker, _cpDefinition, ActionKeys.VIEW);
	}

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String template)
		throws Exception {

		httpServletRequest.setAttribute(CPWebKeys.CP_DEFINITION, _cpDefinition);

		return super.include(httpServletRequest, httpServletResponse, template);
	}

	@Override
	public boolean isPrintable() {
		return true;
	}

	private final CPDefinition _cpDefinition;

}