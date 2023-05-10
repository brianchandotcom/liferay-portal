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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownContextItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.layout.admin.web.internal.display.context.LayoutsAdminDisplayContext;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
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
						_getEditLayoutActionUnsafeConsumer(layout)
					).add(
						() -> _isShowTranslateAction(layout),
						_getAutomaticTranslateLayoutActionUnsafeConsumer(
							draftLayout, layout)
					).add(
						_getPreviewLayoutActionUnsafeConsumer(
							draftLayout, layout)
					).add(
						() ->
							_layoutsAdminDisplayContext.
								isShowViewCollectionItemsAction(layout),
						_getViewCollectionItemsLayoutActionUnsafeConsumer(
							layout)
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
						_getAddLayoutActionUnsafeConsumer(layout)
					).add(
						() ->
							_layoutsAdminDisplayContext.
								isShowConvertLayoutAction(layout) &&
							(draftLayout == null),
						_getConvertToContentPageLayoutActionUnsafeConsumer(
							layout)
					).add(
						() ->
							_layoutsAdminDisplayContext.
								isShowConvertLayoutAction(layout) &&
							(draftLayout != null),
						_getDiscardConversionDraftLayoutActionUnsafeConsumer(
							draftLayout)
					).add(
						() ->
							_layoutsAdminDisplayContext.
								isShowPreviewDraftActions(layout),
						_getPreviewDraftLayoutActionUnsafeConsumer(layout)
					).add(
						() ->
							_layoutsAdminDisplayContext.
								isShowDiscardDraftActions(layout),
						_getDiscardDraftLayoutActionUnsafeConsumer(layout)
					).add(
						() ->
							_layoutsAdminDisplayContext.
								isShowOrphanPortletsAction(layout),
						_getOrphanWidgetsLayoutActionUnsafeConsumer(layout)
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.addContext(
						_getCopyLayoutWithPermissionsActionUnsafeConsumer(
							layout)
					).add(
						() ->
							_layoutsAdminDisplayContext.
								isShowExportTranslationAction(layout),
						_getExportForTranslationLayoutActionUnsafeConsumer(
							draftLayout, layout)
					).add(
						() -> _isShowImportTranslationAction(layout),
						_getImportTranslationLayoutActionUnsafeConsumer(
							draftLayout, layout)
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> _layoutsAdminDisplayContext.isShowConfigureAction(
							layout),
						_getConfigureLayoutActionUnsafeConsumer(layout)
					).add(
						() ->
							_layoutsAdminDisplayContext.isShowPermissionsAction(
								layout),
						_getPermissionLayoutActionUnsafeConsumer(layout)
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> _layoutsAdminDisplayContext.isShowDeleteAction(
							layout),
						_getDeleteLayoutActionUnsafeConsumer(layout)
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getAddLayoutActionUnsafeConsumer(Layout layout) {

		return dropdownItem -> {
			dropdownItem.setHref(
				_layoutsAdminDisplayContext.getSelectLayoutPageTemplateEntryURL(
					0, layout.getPlid(), layout.isPrivateLayout()));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "add-page"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getAutomaticTranslateLayoutActionUnsafeConsumer(
			Layout draftLayout, Layout layout) {

		return dropdownItem -> {
			dropdownItem.setHref(
				PortletURLBuilder.create(
					_translationURLProvider.getTranslateURL(
						_themeDisplay.getScopeGroupId(),
						PortalUtil.getClassNameId(Layout.class.getName()),
						BeanPropertiesUtil.getLong(
							draftLayout, "plid", layout.getPlid()),
						RequestBackedPortletURLFactoryUtil.create(
							_httpServletRequest))
				).setRedirect(
					PortalUtil.getCurrentURL(_httpServletRequest)
				).setPortletResource(
					() -> {
						PortletDisplay portletDisplay =
							_themeDisplay.getPortletDisplay();

						return portletDisplay.getId();
					}
				).setParameter(
					"segmentsExperienceId",
					SegmentsExperienceLocalServiceUtil.
						fetchDefaultSegmentsExperienceId(layout.getPlid())
				).buildString());
			dropdownItem.setIcon("automatic-translate");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "translate"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getConfigureLayoutActionUnsafeConsumer(Layout layout) {

		return dropdownItem -> {
			dropdownItem.setHref(
				_layoutsAdminDisplayContext.getConfigureLayoutURL(layout));
			dropdownItem.setIcon("cog");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "configure"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getConvertToContentPageLayoutActionUnsafeConsumer(Layout layout) {

		return dropdownItem -> {
			dropdownItem.setHref(
				_layoutsAdminDisplayContext.getLayoutConversionPreviewURL(
					layout));
			dropdownItem.setIcon("page");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_httpServletRequest, "convert-to-content-page..."));
		};
	}

	private UnsafeConsumer<DropdownContextItem, Exception>
		_getCopyLayoutWithPermissionsActionUnsafeConsumer(Layout layout) {

		return dropdownContextItem -> {
			if (_layoutsAdminDisplayContext.isShowCopyLayoutAction(layout)) {
				dropdownContextItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.putData("action", "copyLayout");
							dropdownItem.putData(
								"copyLayoutURL",
								_layoutsAdminDisplayContext.
									getCopyLayoutRenderURL(false, layout));
							dropdownItem.setLabel(
								LanguageUtil.get(_httpServletRequest, "page"));
						}
					).add(
						dropdownItem -> {
							dropdownItem.putData(
								"action", "copyLayoutWithPermissions");
							dropdownItem.putData(
								"copyLayoutURL",
								_layoutsAdminDisplayContext.
									getCopyLayoutRenderURL(true, layout));
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									"page-with-permissions"));
						}
					).build());
			}
			else {
				dropdownContextItem.setDisabled(true);
			}

			dropdownContextItem.setIcon("copy");
			dropdownContextItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "copy"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteLayoutActionUnsafeConsumer(Layout layout) {

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteLayout");
			dropdownItem.putData(
				"deleteLayoutURL",
				_layoutsAdminDisplayContext.getDeleteLayoutURL(layout));

			String messageKey =
				"are-you-sure-you-want-to-delete-the-page-x.-it-will-be-" +
					"removed-immediately";

			if (layout.hasChildren() && _hasScopeGroup(layout)) {
				messageKey = StringBundler.concat(
					"are-you-sure-you-want-to-delete-the-page-x.-this-page-",
					"serves-as-a-scope-for-content-and-also-contains-child-",
					"pages");
			}
			else if (layout.hasChildren()) {
				messageKey =
					"are-you-sure-you-want-to-delete-the-page-x.-this-page-" +
						"contains-child-pages-that-will-also-be-removed";
			}
			else if (_hasScopeGroup(layout)) {
				messageKey =
					"are-you-sure-you-want-to-delete-the-page-x.-this-page-" +
						"serves-as-a-scope-for-content";
			}

			dropdownItem.putData(
				"message",
				LanguageUtil.format(
					_httpServletRequest, messageKey,
					HtmlUtil.escape(
						layout.getName(_themeDisplay.getLocale()))));

			dropdownItem.setIcon("trash");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDiscardConversionDraftLayoutActionUnsafeConsumer(
			Layout draftLayout) {

		return dropdownItem -> {
			dropdownItem.setHref(
				_layoutsAdminDisplayContext.getDeleteLayoutURL(draftLayout));
			dropdownItem.setLabel(
				LanguageUtil.get(
					_httpServletRequest, "discard-conversion-draft"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDiscardDraftLayoutActionUnsafeConsumer(Layout layout) {

		return dropdownItem -> {
			dropdownItem.putData("action", "discardDraft");
			dropdownItem.putData(
				"discardDraftURL",
				_layoutsAdminDisplayContext.getDiscardDraftURL(layout));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "discard-draft"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getEditLayoutActionUnsafeConsumer(Layout layout) {

		return dropdownItem -> {
			dropdownItem.setHref(
				_layoutsAdminDisplayContext.getEditLayoutURL(layout));
			dropdownItem.setIcon("pencil");

			String label = LanguageUtil.get(_httpServletRequest, "edit");

			if (_layoutsAdminDisplayContext.isConversionDraft(layout)) {
				label = LanguageUtil.get(
					_httpServletRequest, "edit-conversion-draft");
			}

			dropdownItem.setLabel(label);
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getExportForTranslationLayoutActionUnsafeConsumer(
			Layout draftLayout, Layout layout) {

		return dropdownItem -> {
			dropdownItem.setHref(
				PortletURLBuilder.create(
					_translationURLProvider.getExportTranslationURL(
						layout.getGroupId(),
						PortalUtil.getClassNameId(Layout.class.getName()),
						BeanPropertiesUtil.getLong(
							draftLayout, "plid", layout.getPlid()),
						RequestBackedPortletURLFactoryUtil.create(
							_httpServletRequest))
				).setRedirect(
					PortalUtil.getCurrentURL(_httpServletRequest)
				).setPortletResource(
					() -> {
						PortletDisplay portletDisplay =
							_themeDisplay.getPortletDisplay();

						return portletDisplay.getId();
					}
				).buildString());
			dropdownItem.setIcon("upload");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_httpServletRequest, "export-for-translation"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getImportTranslationLayoutActionUnsafeConsumer(
			Layout draftLayout, Layout layout) {

		return dropdownItem -> {
			dropdownItem.setHref(
				PortletURLBuilder.create(
					_translationURLProvider.getImportTranslationURL(
						layout.getGroupId(),
						PortalUtil.getClassNameId(Layout.class.getName()),
						BeanPropertiesUtil.getLong(
							draftLayout, "plid", layout.getPlid()),
						RequestBackedPortletURLFactoryUtil.create(
							_httpServletRequest))
				).setRedirect(
					PortalUtil.getCurrentURL(_httpServletRequest)
				).setPortletResource(
					() -> {
						PortletDisplay portletDisplay =
							_themeDisplay.getPortletDisplay();

						return portletDisplay.getId();
					}
				).buildString());
			dropdownItem.setIcon("download");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "import-translation"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getOrphanWidgetsLayoutActionUnsafeConsumer(Layout layout) {

		return dropdownItem -> {
			dropdownItem.setHref(
				_layoutsAdminDisplayContext.getOrphanPortletsURL(layout));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "orphan-widgets"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getPermissionLayoutActionUnsafeConsumer(Layout layout) {

		return dropdownItem -> {
			dropdownItem.putData("action", "permissionLayout");
			dropdownItem.putData(
				"permissionLayoutURL",
				_layoutsAdminDisplayContext.getPermissionsURL(layout));
			dropdownItem.setIcon("password-policies");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "permissions"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getPreviewDraftLayoutActionUnsafeConsumer(Layout layout) {

		return dropdownItem -> {
			dropdownItem.put("symbolRight", "shortcut");
			dropdownItem.setHref(
				_layoutsAdminDisplayContext.getPreviewDraftURL(layout));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "preview-draft"));
			dropdownItem.setTarget("_blank");
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getPreviewLayoutActionUnsafeConsumer(
			Layout draftLayout, Layout layout) {

		return dropdownItem -> {
			if (layout.isTypeContent() &&
				!GetterUtil.getBoolean(
					draftLayout.getTypeSettingsProperty("published"))) {

				dropdownItem.setDisabled(true);
			}

			dropdownItem.setHref(
				_layoutsAdminDisplayContext.getViewLayoutURL(layout));
			dropdownItem.setIcon("view");

			String label = LanguageUtil.get(_httpServletRequest, "view");

			if (layout.isDenied() || layout.isPending()) {
				label = LanguageUtil.get(_httpServletRequest, "preview");
			}

			dropdownItem.setLabel(label);
			dropdownItem.setTarget(
				HtmlUtil.escape(layout.getTypeSettingsProperty("target")));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getViewCollectionItemsLayoutActionUnsafeConsumer(Layout layout) {

		return dropdownItem -> {
			dropdownItem.putData("action", "viewCollectionItems");
			dropdownItem.putData(
				"viewCollectionItemsURL",
				_layoutsAdminDisplayContext.getViewCollectionItemsURL(layout));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "view-collection-items"));
		};
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
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

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

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutActionDropdownItemsProvider.class);

	private final HttpServletRequest _httpServletRequest;
	private final LayoutsAdminDisplayContext _layoutsAdminDisplayContext;
	private final ThemeDisplay _themeDisplay;
	private final TranslationPermission _translationPermission;
	private final TranslationURLProvider _translationURLProvider;

}