/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.manager;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.manager.LayoutLockManager;
import com.liferay.layout.model.LockedLayout;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.utility.page.kernel.LayoutUtilityPageEntryViewRenderer;
import com.liferay.layout.utility.page.kernel.LayoutUtilityPageEntryViewRendererRegistryUtil;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.LockedLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.lock.model.LockTable;
import com.liferay.portal.model.impl.LayoutModelImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = LayoutLockManager.class)
public class LayoutLockManagerImpl implements LayoutLockManager {

	@Override
	public String getLayoutType(long classPK, Locale locale, String type) {
		if (Objects.equals(type, LayoutConstants.TYPE_ASSET_DISPLAY)) {
			return _language.get(locale, "display-page-template");
		}

		if (Objects.equals(type, LayoutConstants.TYPE_COLLECTION)) {
			return _language.get(locale, "collection-page");
		}

		if (!Objects.equals(type, LayoutConstants.TYPE_CONTENT)) {
			return StringPool.BLANK;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(classPK);

		if (layoutPageTemplateEntry != null) {
			return _getLayoutPageTemplateEntryTypeLabel(
				layoutPageTemplateEntry, locale);
		}

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.
				fetchLayoutUtilityPageEntryByPlid(classPK);

		if (layoutUtilityPageEntry != null) {
			return _getLayoutUtilityPageEntryTypeLabel(
				layoutUtilityPageEntry, locale);
		}

		return _language.get(locale, "content-page");
	}

	@Override
	public void getLock(ActionRequest actionRequest) throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (!FeatureFlagManagerUtil.isEnabled("LPS-180328") ||
			!layout.isDraftLayout()) {

			return;
		}

		Lock lock = _lockManager.fetchLock(
			Layout.class.getName(), layout.getPlid());

		if (lock == null) {
			try {
				_lockManager.lock(
					themeDisplay.getUserId(), Layout.class.getName(),
					layout.getPlid(), String.valueOf(themeDisplay.getUserId()),
					false, LayoutModelImpl.LOCK_EXPIRATION_TIME);
			}
			catch (PortalException portalException) {
				throw new LockedLayoutException(portalException);
			}
		}
		else if (lock.getUserId() == themeDisplay.getUserId()) {
			_lockManager.refresh(
				lock.getUuid(), lock.getCompanyId(),
				LayoutModelImpl.LOCK_EXPIRATION_TIME);
		}
		else {
			throw new LockedLayoutException();
		}
	}

	@Override
	public List<LockedLayout> getLockedLayouts(long groupId) {
		List<Object[]> results = _layoutLocalService.dslQuery(
			DSLQueryFactoryUtil.select(
				LayoutTable.INSTANCE.classPK, LockTable.INSTANCE.createDate,
				LayoutTable.INSTANCE.name, LayoutTable.INSTANCE.plid,
				LayoutTable.INSTANCE.type, LockTable.INSTANCE.userName
			).from(
				LayoutTable.INSTANCE
			).innerJoinON(
				LockTable.INSTANCE,
				LockTable.INSTANCE.key.eq(
					DSLFunctionFactoryUtil.castText(LayoutTable.INSTANCE.plid))
			).where(
				LayoutTable.INSTANCE.groupId.eq(
					groupId
				).and(
					LayoutTable.INSTANCE.hidden.eq(true)
				).and(
					LayoutTable.INSTANCE.system.eq(true)
				).and(
					LayoutTable.INSTANCE.status.eq(
						WorkflowConstants.STATUS_DRAFT)
				).and(
					LayoutTable.INSTANCE.type.in(
						new String[] {
							LayoutConstants.TYPE_COLLECTION,
							LayoutConstants.TYPE_CONTENT
						})
				)
			).orderBy(
				orderByStep -> orderByStep.orderBy(
					LockTable.INSTANCE.createDate.descending())
			));

		List<LockedLayout> lockedLayouts = new ArrayList<>(results.size());

		for (Object[] columns : results) {
			lockedLayouts.add(
				new LockedLayout(
					GetterUtil.getLong(columns[0]), (Date)columns[1],
					GetterUtil.getString(columns[2]),
					GetterUtil.getLong(columns[3]),
					GetterUtil.getString(columns[4]),
					GetterUtil.getString(columns[5])));
		}

		return lockedLayouts;
	}

	@Override
	public String getLockedLayoutURL(ActionRequest actionRequest) {
		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				actionRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_admin/locked_layout"
		).setBackURL(
			() -> {
				String backURL = ParamUtil.getString(actionRequest, "backURL");

				if (Validator.isNotNull(backURL)) {
					return backURL;
				}

				HttpServletRequest httpServletRequest =
					_portal.getHttpServletRequest(actionRequest);

				backURL = ParamUtil.getString(
					httpServletRequest, "p_l_back_url");

				if (Validator.isNotNull(backURL)) {
					return backURL;
				}

				return ParamUtil.getString(httpServletRequest, "redirect");
			}
		).buildString();
	}

	@Override
	public String getUnlockDraftLayoutURL(
			LiferayPortletResponse liferayPortletResponse,
			PortletURLBuilder.UnsafeSupplier<Object, Exception>
				redirectUnsafeSupplier)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-180328")) {
			return String.valueOf(redirectUnsafeSupplier.get());
		}

		return PortletURLBuilder.createActionURL(
			liferayPortletResponse
		).setActionName(
			"/layout_content_page_editor/unlock_draft_layout"
		).setRedirect(
			redirectUnsafeSupplier
		).buildString();
	}

	@Override
	public void unlock(Layout layout, long userId) {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-180328") ||
			!layout.isDraftLayout()) {

			return;
		}

		_lockManager.unlock(
			Layout.class.getName(), String.valueOf(layout.getPlid()),
			String.valueOf(userId));
	}

	private String _getLayoutPageTemplateEntryTypeLabel(
		LayoutPageTemplateEntry layoutPageTemplateEntry, Locale locale) {

		if (Objects.equals(
				layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_BASIC)) {

			return _language.get(locale, "content-page-template");
		}

		if (Objects.equals(
				layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE)) {

			return _language.get(locale, "display-page-template");
		}

		if (Objects.equals(
				layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT)) {

			return _language.get(locale, "master");
		}

		return StringPool.BLANK;
	}

	private String _getLayoutUtilityPageEntryTypeLabel(
		LayoutUtilityPageEntry layoutUtilityPageEntry, Locale locale) {

		LayoutUtilityPageEntryViewRenderer layoutUtilityPageEntryViewRenderer =
			LayoutUtilityPageEntryViewRendererRegistryUtil.
				getLayoutUtilityPageEntryViewRenderer(
					layoutUtilityPageEntry.getType());

		if (layoutUtilityPageEntryViewRenderer == null) {
			return StringPool.BLANK;
		}

		return layoutUtilityPageEntryViewRenderer.getLabel(locale);
	}

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Reference
	private LockManager _lockManager;

	@Reference
	private Portal _portal;

}