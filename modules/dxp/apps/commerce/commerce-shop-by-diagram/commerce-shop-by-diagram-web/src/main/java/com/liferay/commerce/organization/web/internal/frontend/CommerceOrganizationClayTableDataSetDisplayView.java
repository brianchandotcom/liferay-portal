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

package com.liferay.commerce.shop.by.diagram.web.internal.frontend;

import com.liferay.commerce.shop.by.diagram.web.internal.model.Organization;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"clay.data.provider.key=" + CommerceOrganizationClayTableDataSetDisplayView.NAME,
		"clay.data.set.display.name=" + CommerceOrganizationClayTableDataSetDisplayView.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDataProvider.class,
		ClayDataSetDisplayView.class
	}
)
public class CommerceOrganizationClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView
	implements ClayDataSetActionProvider,
			   ClayDataSetDataProvider<Organization> {

	public static final String NAME = "commerceOrganizations";

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		clayTableSchemaBuilder.addClayTableSchemaField("name", "name");
		clayTableSchemaBuilder.addClayTableSchemaField("path", "path");

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		Organization organization = (Organization)model;

		return DropdownItemListBuilder.add(
			() -> OrganizationPermissionUtil.contains(
				PermissionThreadLocal.getPermissionChecker(),
				organization.getOrganizationId(), ActionKeys.VIEW),
			dropdownItem -> {
				dropdownItem.setHref(
					_getViewOrganizationDetailURL(
						organization.getOrganizationId(), httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "view-details"));
			}
		).add(
			() -> OrganizationPermissionUtil.contains(
				PermissionThreadLocal.getPermissionChecker(),
				organization.getOrganizationId(), ActionKeys.VIEW),
			dropdownItem -> {
				dropdownItem.setHref(
					_getOrganizationViewSuborganizationsURL(
						organization.getOrganizationId(), httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "view-suborganizations"));
			}
		).add(
			() -> OrganizationPermissionUtil.contains(
				PermissionThreadLocal.getPermissionChecker(),
				organization.getOrganizationId(), ActionKeys.DELETE),
			dropdownItem -> {
				StringBundler sb = new StringBundler(7);

				sb.append("javascript:deleteCommerceOrganization");
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(StringPool.APOSTROPHE);
				sb.append(organization.getOrganizationId());
				sb.append(StringPool.APOSTROPHE);
				sb.append(StringPool.CLOSE_PARENTHESIS);
				sb.append(StringPool.SEMICOLON);

				dropdownItem.setHref(sb.toString());

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).build();
	}

	@Override
	public List<Organization> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<Organization> organizations = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long organizationId = ParamUtil.getLong(
			httpServletRequest, "organizationId");

		List<com.liferay.portal.kernel.model.Organization> organizationList =
			_organizationService.getOrganizations(
				themeDisplay.getCompanyId(), organizationId,
				pagination.getStartPosition(), pagination.getEndPosition());

		if (organizationList.isEmpty()) {
			organizationList = _organizationLocalService.getOrganizations(
				themeDisplay.getUserId(), pagination.getStartPosition(),
				pagination.getEndPosition(), null);
		}

		for (com.liferay.portal.kernel.model.Organization organization :
				organizationList) {

			organizations.add(
				new Organization(
					organization.getOrganizationId(), organization.getName(),
					getPath(
						organization.getTreePath(),
						themeDisplay.getPermissionChecker())));
		}

		return organizations;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long organizationId = ParamUtil.getLong(
			httpServletRequest, "organizationId");

		return _organizationService.getOrganizationsCount(
			themeDisplay.getCompanyId(), organizationId);
	}

	protected String getPath(
			String treePath, PermissionChecker permissionChecker)
		throws PortalException {

		String[] organizationIds = StringUtil.split(
			treePath, CharPool.FORWARD_SLASH);

		StringBundler sb = new StringBundler(organizationIds.length * 2);

		for (int i = 1; i < organizationIds.length; i++) {
			if (!OrganizationPermissionUtil.contains(
					permissionChecker, GetterUtil.getLong(organizationIds[i]),
					ActionKeys.VIEW)) {

				continue;
			}

			sb.append(CharPool.FORWARD_SLASH);

			com.liferay.portal.kernel.model.Organization organization =
				_organizationService.getOrganization(
					GetterUtil.getLong(organizationIds[i]));

			sb.append(organization.getName());
		}

		return sb.toString();
	}

	private String _getOrganizationViewSuborganizationsURL(
			long organizationId, HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest,
				com.liferay.portal.kernel.model.Organization.class.getName(),
				PortletProvider.Action.MANAGE)
		).setParameter(
			"organizationId", organizationId
		).build();

		PortletURL backURL = PortletProviderUtil.getPortletURL(
			httpServletRequest,
			com.liferay.portal.kernel.model.Organization.class.getName(),
			PortletProvider.Action.MANAGE);

		portletURL.setParameter(
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL",
			backURL.toString());

		return portletURL.toString();
	}

	private String _getViewOrganizationDetailURL(
			long organizationId, HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL viewURL = PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest,
				com.liferay.portal.kernel.model.Organization.class.getName(),
				PortletProvider.Action.MANAGE)
		).setMVCRenderCommandName(
			"/commerce_organization/view_commerce_organization"
		).setParameter(
			"organizationId", organizationId
		).build();

		PortletURL backURL = PortletProviderUtil.getPortletURL(
			httpServletRequest,
			com.liferay.portal.kernel.model.Organization.class.getName(),
			PortletProvider.Action.MANAGE);

		viewURL.setParameter(
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL",
			backURL.toString());

		return viewURL.toString();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private OrganizationService _organizationService;

}