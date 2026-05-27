/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.SiteTemplate;
import com.liferay.headless.admin.site.resource.v1_0.SiteTemplateResource;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Balázs Sáfrány-Kovalik
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/site-template.properties",
	scope = ServiceScope.PROTOTYPE, service = SiteTemplateResource.class
)
public class SiteTemplateResourceImpl extends BaseSiteTemplateResourceImpl {

	@Override
	public Page<SiteTemplate> getSiteTemplatesPage(
			Boolean active, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_layoutSetPrototypeService.search(
					contextCompany.getCompanyId(), active,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toSiteTemplate),
			pagination,
			_layoutSetPrototypeService.searchCount(
				contextCompany.getCompanyId(), active));
	}

	private SiteTemplate _toSiteTemplate(
		LayoutSetPrototype layoutSetPrototype) {

		return new SiteTemplate() {
			{
				setActive(layoutSetPrototype::isActive);
				setDefaultLanguageId(
					() -> {
						String xml = layoutSetPrototype.getName();

						if (xml == null) {
							return "";
						}

						return _localization.getDefaultLanguageId(
							xml, contextCompany.getLocale());
					});
				setDescription(
					() -> layoutSetPrototype.getDescription(
						contextAcceptLanguage.getPreferredLocale()));
				setDescription_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						layoutSetPrototype.getDescriptionMap()));
				setId(layoutSetPrototype::getLayoutSetPrototypeId);
				setLogo(
					() -> {
						Group group = layoutSetPrototype.getGroup();

						ThemeDisplay themeDisplay = new ThemeDisplay() {
							{
								setCompany(
									_companyLocalService.getCompany(
										group.getCompanyId()));
								setPathImage(_portal.getPathImage());
							}
						};

						return group.getLogoURL(themeDisplay, true);
					});
				setName(
					() -> layoutSetPrototype.getName(
						contextAcceptLanguage.getPreferredLocale()));
				setName_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						layoutSetPrototype.getNameMap()));
				setPagesUpdateable(
					() -> {
						UnicodeProperties settingsUnicodeProperties =
							layoutSetPrototype.getSettingsProperties();

						return GetterUtil.getBoolean(
							settingsUnicodeProperties.getProperty(
								"layoutsUpdateable"));
					});
				setSiteExternalReferenceCode(
					() -> {
						Group group = layoutSetPrototype.getGroup();

						return group.getExternalReferenceCode();
					});
			}
		};
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private LayoutSetPrototypeService _layoutSetPrototypeService;

	@Reference
	private Localization _localization;

	@Reference
	private Portal _portal;

}