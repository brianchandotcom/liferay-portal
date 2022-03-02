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

package com.liferay.layout.admin.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.layout.admin.web.internal.display.context.LayoutsAdminDisplayContext;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.translation.constants.TranslationActionKeys;
import com.liferay.translation.security.permission.TranslationPermission;
import com.liferay.translation.url.provider.TranslationURLProvider;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class LayoutActionDropdownItemsProvider {

	public LayoutActionDropdownItemsProvider(
		HttpServletRequest httpServletRequest,
		LayoutsAdminDisplayContext layoutsAdminDisplayContext,
		TranslationPermission translationPermission,
		TranslationURLProvider translationURLProvider) {

		_httpServletRequest = httpServletRequest;
		_layoutsAdminDisplayContext = layoutsAdminDisplayContext;
		_translationPermission = translationPermission;
		_translationURLProvider = translationURLProvider;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems(
			Layout layout, boolean includeAddChildPageAction)
		throws Exception {

		Layout draftLayout = _layoutsAdminDisplayContext.getDraftLayout(layout);

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() ->
							(_layoutsAdminDisplayContext.isConversionDraft(
								layout) ||
							 layout.isTypeContent()) &&
							_layoutsAdminDisplayContext.isShowConfigureAction(
								layout),
						dropdownItem -> {
							dropdownItem.setHref(
								_layoutsAdminDisplayContext.getEditLayoutURL(
									layout));

							String label = LanguageUtil.get(
								_httpServletRequest, "edit");

							if (_layoutsAdminDisplayContext.isConversionDraft(
									layout)) {

								label = LanguageUtil.get(
									_httpServletRequest,
									"edit-conversion-draft");
							}

							dropdownItem.setLabel(label);
						}
					).add(
						() -> _isShowTranslateAction(layout),
						dropdownItem -> {
							dropdownItem.setHref(
								PortletURLBuilder.create(
									_translationURLProvider.getTranslateURL(
										_themeDisplay.getScopeGroupId(),
										PortalUtil.getClassNameId(
											Layout.class.getName()),
										BeanPropertiesUtil.getLong(
											draftLayout, "plid",
											layout.getPlid()),
										RequestBackedPortletURLFactoryUtil.
											create(_httpServletRequest))
								).setRedirect(
									PortalUtil.getCurrentURL(
										_httpServletRequest)
								).setPortletResource(
									() -> {
										PortletDisplay portletDisplay =
											_themeDisplay.getPortletDisplay();

										return portletDisplay.getId();
									}
								).buildString());
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "translate"));
						}
					).add(
						dropdownItem -> {
							dropdownItem.setHref(
								_layoutsAdminDisplayContext.getViewLayoutURL(
									layout));

							String label = LanguageUtil.get(
								_httpServletRequest, "view");

							if (layout.isDenied() || layout.isPending()) {
								label = LanguageUtil.get(
									_httpServletRequest, "preview");
							}

							dropdownItem.setLabel(label);

							if (layout.isTypeContent() &&
								!GetterUtil.getBoolean(
									draftLayout.getTypeSettingsProperty(
										"published"))) {

								dropdownItem.setDisabled(true);
							}
						}
					).add(
						() ->
							_layoutsAdminDisplayContext.
								isShowViewCollectionItemsAction(layout),
						dropdownItem -> {
							dropdownItem.putData(
								"action", "viewCollectionItems");
							dropdownItem.putData(
								"viewCollectionItemsURL",
								_layoutsAdminDisplayContext.
									getViewCollectionItemsURL(layout));
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									"view-collection-items"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() ->
							includeAddChildPageAction &&
							_layoutsAdminDisplayContext.
								isShowAddChildPageAction(layout),
						dropdownItem -> {
							dropdownItem.setHref(
								_layoutsAdminDisplayContext.
									getSelectLayoutPageTemplateEntryURL(
										_layoutsAdminDisplayContext.
											getFirstLayoutPageTemplateCollectionId(),
										layout.getPlid(),
										layout.isPrivateLayout()));
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "add-page"));
						}
					).add(
						() ->
							_layoutsAdminDisplayContext.
								isShowConvertLayoutAction(layout) &&
							(draftLayout == null),
						dropdownItem -> {
							dropdownItem.setHref(
								_layoutsAdminDisplayContext.
									getLayoutConversionPreviewURL(layout));
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									"convert-to-content-page..."));
						}
					).add(
						() ->
							_layoutsAdminDisplayContext.
								isShowConvertLayoutAction(layout) &&
							(draftLayout != null),
						dropdownItem -> {
							dropdownItem.setHref(
								_layoutsAdminDisplayContext.getDeleteLayoutURL(
									draftLayout));
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									"discard-conversion-draft"));
						}
					).add(
						() ->
							_layoutsAdminDisplayContext.
								isShowPreviewDraftActions(layout),
						dropdownItem -> {
							dropdownItem.put("symbolRight", "shortcut");
							dropdownItem.setHref(
								_layoutsAdminDisplayContext.getPreviewDraftURL(
									layout));
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "preview-draft"));
							dropdownItem.setTarget("_blank");
						}
					).add(
						() ->
							_layoutsAdminDisplayContext.
								isShowDiscardDraftActions(layout),
						dropdownItem -> {
							dropdownItem.putData("action", "discardDraft");
							dropdownItem.putData(
								"discardDraftURL",
								_layoutsAdminDisplayContext.getDiscardDraftURL(
									layout));
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "discard-draft"));
						}
					).add(
						() ->
							_layoutsAdminDisplayContext.
								isShowOrphanPortletsAction(layout),
						dropdownItem -> {
							dropdownItem.setHref(
								_layoutsAdminDisplayContext.
									getOrphanPortletsURL(layout));
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "orphan-widgets"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.putData("action", "copyLayout");
							dropdownItem.putData(
								"copyLayoutURL",
								_layoutsAdminDisplayContext.
									getCopyLayoutRenderURL(layout));

							if (!_layoutsAdminDisplayContext.
									isShowCopyLayoutAction(layout)) {

								dropdownItem.setDisabled(true);
							}

							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "copy-page"));
						}
					).add(
						() ->
							_layoutsAdminDisplayContext.
								isShowExportTranslationAction(layout),
						dropdownItem -> {
							dropdownItem.setHref(
								PortletURLBuilder.create(
									_translationURLProvider.
										getExportTranslationURL(
											layout.getGroupId(),
											PortalUtil.getClassNameId(
												Layout.class.getName()),
											BeanPropertiesUtil.getLong(
												draftLayout, "plid",
												layout.getPlid()),
											RequestBackedPortletURLFactoryUtil.
												create(_httpServletRequest))
								).setRedirect(
									PortalUtil.getCurrentURL(
										_httpServletRequest)
								).setPortletResource(
									() -> {
										PortletDisplay portletDisplay =
											_themeDisplay.getPortletDisplay();

										return portletDisplay.getId();
									}
								).buildString());
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									"export-for-translation"));
						}
					).add(
						() -> _isShowImportTranslationAction(layout),
						dropdownItem -> {
							dropdownItem.setHref(
								PortletURLBuilder.create(
									_translationURLProvider.
										getImportTranslationURL(
											layout.getGroupId(),
											PortalUtil.getClassNameId(
												Layout.class.getName()),
											BeanPropertiesUtil.getLong(
												draftLayout, "plid",
												layout.getPlid()),
											RequestBackedPortletURLFactoryUtil.
												create(_httpServletRequest))
								).setRedirect(
									PortalUtil.getCurrentURL(
										_httpServletRequest)
								).setPortletResource(
									() -> {
										PortletDisplay portletDisplay =
											_themeDisplay.getPortletDisplay();

										return portletDisplay.getId();
									}
								).buildString());
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "import-translation"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> _layoutsAdminDisplayContext.isShowConfigureAction(
							layout),
						dropdownItem -> {
							dropdownItem.setHref(
								_layoutsAdminDisplayContext.
									getConfigureLayoutURL(layout));
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "configure"));
						}
					).add(
						() ->
							_layoutsAdminDisplayContext.isShowPermissionsAction(
								layout),
						dropdownItem -> {
							dropdownItem.putData("action", "permissionLayout");
							dropdownItem.putData(
								"permissionLayoutURL",
								_layoutsAdminDisplayContext.getPermissionsURL(
									layout));
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "permissions"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> _layoutsAdminDisplayContext.isShowDeleteAction(
							layout),
						dropdownItem -> {
							dropdownItem.putData("action", "deleteLayout");
							dropdownItem.putData(
								"deleteLayoutURL",
								_layoutsAdminDisplayContext.getDeleteLayoutURL(
									layout));

							String messageKey =
								"are-you-sure-you-want-to-delete-the-page-x.-" +
									"it-will-be-removed-immediately";

							if (layout.hasChildren() &&
								_hasScopeGroup(layout)) {

								messageKey = StringBundler.concat(
									"are-you-sure-you-want-to-delete-the-page-",
									"x.-this-page-serves-as-a-scope-for-",
									"content-and-also-contains-child-pages");
							}
							else if (layout.hasChildren()) {
								messageKey = StringBundler.concat(
									"are-you-sure-you-want-to-delete-the-page-",
									"x.-this-page-contains-child-pages-that-",
									"will-also-be-removed");
							}
							else if (_hasScopeGroup(layout)) {
								messageKey = StringBundler.concat(
									"are-you-sure-you-want-to-delete-the-page-",
									"x.-this-page-serves-as-a-scope-for-",
									"content");
							}

							dropdownItem.putData(
								"message",
								LanguageUtil.format(
									_httpServletRequest, messageKey,
									HtmlUtil.escape(
										layout.getName(
											_themeDisplay.getLocale()))));

							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "delete"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	private boolean _hasScopeGroup(Layout layout) throws Exception {
		if (layout.hasScopeGroup()) {
			return true;
		}

		Layout draftLayout = layout.fetchDraftLayout();

		if (draftLayout == null) {
			return false;
		}

		return draftLayout.hasScopeGroup();
	}

	private boolean _hasTranslatePermission() {
		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();
		long scopeGroupId = _themeDisplay.getScopeGroupId();

		for (Locale locale : LanguageUtil.getAvailableLocales(scopeGroupId)) {
			if (_translationPermission.contains(
					permissionChecker, scopeGroupId,
					LanguageUtil.getLanguageId(locale),
					TranslationActionKeys.TRANSLATE)) {

				return true;
			}
		}

		return false;
	}

	private boolean _isShowImportTranslationAction(Layout layout) {
		try {
			if (layout.isTypeContent() &&
				!_layoutsAdminDisplayContext.isSingleLanguageSite() &&
				LayoutPermissionUtil.contains(
					_themeDisplay.getPermissionChecker(), layout,
					ActionKeys.UPDATE)) {

				return true;
			}

			return false;
		}
		catch (Exception exception) {
			return false;
		}
	}

	private boolean _isShowTranslateAction(Layout layout) {
		if (layout.isTypeContent() && _hasTranslatePermission() &&
			!_layoutsAdminDisplayContext.isSingleLanguageSite()) {

			return true;
		}

		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private final LayoutsAdminDisplayContext _layoutsAdminDisplayContext;
	private final ThemeDisplay _themeDisplay;
	private final TranslationPermission _translationPermission;
	private final TranslationURLProvider _translationURLProvider;

}