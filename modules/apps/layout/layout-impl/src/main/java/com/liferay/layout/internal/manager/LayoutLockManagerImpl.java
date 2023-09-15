/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.manager;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.constants.LockedLayoutType;
import com.liferay.layout.manager.LayoutLockManager;
import com.liferay.layout.model.LockedLayout;
import com.liferay.layout.model.LockedLayoutOrder;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntryTable;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.util.comparator.LayoutNameComparator;
import com.liferay.layout.utility.page.kernel.LayoutUtilityPageEntryViewRenderer;
import com.liferay.layout.utility.page.kernel.LayoutUtilityPageEntryViewRendererRegistryUtil;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntryTable;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.LimitStep;
import com.liferay.petra.sql.dsl.query.OrderByStep;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.lock.model.LockTable;
import com.liferay.portal.lock.service.LockLocalService;
import com.liferay.portal.model.impl.LayoutModelImpl;

import java.sql.Types;

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
			try {
				_lockManager.refresh(
					lock.getUuid(), lock.getCompanyId(),
					LayoutModelImpl.LOCK_EXPIRATION_TIME);
			}
			catch (PortalException portalException) {
				throw new LockedLayoutException(portalException);
			}
		}
		else {
			throw new LockedLayoutException();
		}
	}

	@Override
	public List<LockedLayout> getLockedLayouts(
		long companyId, long groupId, LockedLayoutOrder lockedLayoutOrder,
		LockedLayoutType lockedLayoutType) {

		List<Object[]> results = _layoutLocalService.dslQuery(
			DSLQueryFactoryUtil.select(
			).from(
				DSLQueryFactoryUtil.select(
					LayoutTable.INSTANCE.classPK, LockTable.INSTANCE.createDate,
					LayoutTable.INSTANCE.name, LayoutTable.INSTANCE.plid,
					LayoutTable.INSTANCE.type, LockTable.INSTANCE.userName
				).from(
					LayoutTable.INSTANCE
				).innerJoinON(
					LockTable.INSTANCE,
					LockTable.INSTANCE.companyId.eq(
						companyId
					).and(
						LockTable.INSTANCE.className.eq(Layout.class.getName())
					).and(
						LockTable.INSTANCE.key.eq(
							DSLFunctionFactoryUtil.castText(
								LayoutTable.INSTANCE.plid))
					)
				).leftJoinOn(
					LayoutPageTemplateEntryTable.INSTANCE,
					_getLayoutPageTemplateEntryTableLeftJoinOnPredicate(
						groupId, lockedLayoutType)
				).leftJoinOn(
					LayoutUtilityPageEntryTable.INSTANCE,
					_getLayoutUtilityPageEntryTableLeftJoin(
						groupId, lockedLayoutType)
				).where(
					_getWherePredicate(groupId, lockedLayoutType)
				).orderBy(
					orderByStep -> _getLimitStep(lockedLayoutOrder, orderByStep)
				).as(
					"LockedLayoutsTable", LockedLayoutsTable.INSTANCE
				)
			));

		List<LockedLayout> lockedLayouts = new ArrayList<>();

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
		return getLockedLayoutURL(_portal.getHttpServletRequest(actionRequest));
	}

	@Override
	public String getLockedLayoutURL(HttpServletRequest httpServletRequest) {
		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_admin/locked_layout"
		).setBackURL(
			() -> {
				String backURL = ParamUtil.getString(
					httpServletRequest, "backURL");

				if (Validator.isNotNull(backURL)) {
					return backURL;
				}

				backURL = ParamUtil.getString(
					httpServletRequest, "p_l_back_url");

				if (Validator.isNotNull(backURL)) {
					return backURL;
				}

				return ParamUtil.getString(httpServletRequest, "redirect");
			}
		).setParameter(
			"p_l_back_url_title",
			() -> {
				String backURLTitle = ParamUtil.getString(
					httpServletRequest, "p_l_back_url_title");

				if (Validator.isNotNull(backURLTitle)) {
					return backURLTitle;
				}

				return null;
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

	@Override
	public void unlockLayouts(long companyId, long timeWithoutAutosave) {
		Date lastAutosaveDate = new Date(
			System.currentTimeMillis() - (timeWithoutAutosave * Time.MINUTE));

		List<Long> plids = _layoutLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				LayoutTable.INSTANCE.plid
			).from(
				LayoutTable.INSTANCE
			).innerJoinON(
				LockTable.INSTANCE,
				LockTable.INSTANCE.companyId.eq(
					companyId
				).and(
					LockTable.INSTANCE.className.eq(Layout.class.getName())
				).and(
					LockTable.INSTANCE.key.eq(
						DSLFunctionFactoryUtil.castText(
							LayoutTable.INSTANCE.plid))
				).and(
					LockTable.INSTANCE.createDate.lt(lastAutosaveDate)
				)
			).where(
				LayoutTable.INSTANCE.classPK.gt(
					0L
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
							LayoutConstants.TYPE_ASSET_DISPLAY,
							LayoutConstants.TYPE_COLLECTION,
							LayoutConstants.TYPE_CONTENT
						})
				)
			));

		for (Long plid : plids) {
			_lockManager.unlock(Layout.class.getName(), String.valueOf(plid));
		}
	}

	@Override
	public void unlockLayoutsByUserId(long companyId, long userId) {
		List<Long> plids = _layoutLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				LayoutTable.INSTANCE.plid
			).from(
				LayoutTable.INSTANCE
			).innerJoinON(
				LockTable.INSTANCE,
				LockTable.INSTANCE.companyId.eq(
					companyId
				).and(
					LockTable.INSTANCE.className.eq(Layout.class.getName())
				).and(
					LockTable.INSTANCE.key.eq(
						DSLFunctionFactoryUtil.castText(
							LayoutTable.INSTANCE.plid))
				).and(
					LockTable.INSTANCE.userId.eq(userId)
				).and(
					LockTable.INSTANCE.owner.eq(String.valueOf(userId))
				)
			).where(
				LayoutTable.INSTANCE.companyId.eq(
					companyId
				).and(
					LayoutTable.INSTANCE.classPK.gt(0L)
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
							LayoutConstants.TYPE_ASSET_DISPLAY,
							LayoutConstants.TYPE_COLLECTION,
							LayoutConstants.TYPE_CONTENT
						})
				)
			));

		for (Long plid : plids) {
			_lockManager.unlock(Layout.class.getName(), String.valueOf(plid));
		}
	}

	private Predicate _getLayoutPageTemplateEntryTableLeftJoinOnPredicate(
		long groupId, LockedLayoutType lockedLayoutType) {

		if ((lockedLayoutType == null) ||
			Objects.equals(
				lockedLayoutType, LockedLayoutType.COLLECTION_PAGE) ||
			Objects.equals(
				lockedLayoutType, LockedLayoutType.DISPLAY_PAGE_TEMPLATE) ||
			Objects.equals(lockedLayoutType, LockedLayoutType.UTILITY_PAGE)) {

			return null;
		}

		return LayoutPageTemplateEntryTable.INSTANCE.groupId.eq(
			groupId
		).and(
			LayoutPageTemplateEntryTable.INSTANCE.plid.eq(
				LayoutTable.INSTANCE.classPK)
		);
	}

	private Integer _getLayoutPageTemplateEntryType(
		LockedLayoutType lockedLayoutType) {

		if (Objects.equals(
				lockedLayoutType, LockedLayoutType.CONTENT_PAGE_TEMPLATE)) {

			return LayoutPageTemplateEntryTypeConstants.TYPE_BASIC;
		}
		else if (Objects.equals(
					lockedLayoutType, LockedLayoutType.MASTER_PAGE)) {

			return LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT;
		}

		return null;
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

	private String _getLayoutType(LockedLayoutType lockedLayoutType) {
		if (Objects.equals(
				lockedLayoutType, LockedLayoutType.COLLECTION_PAGE)) {

			return LayoutConstants.TYPE_COLLECTION;
		}
		else if (Objects.equals(
					lockedLayoutType, LockedLayoutType.CONTENT_PAGE)) {

			return LayoutConstants.TYPE_CONTENT;
		}
		else if (Objects.equals(
					lockedLayoutType, LockedLayoutType.DISPLAY_PAGE_TEMPLATE)) {

			return LayoutConstants.TYPE_ASSET_DISPLAY;
		}

		return null;
	}

	private Predicate _getLayoutUtilityPageEntryTableLeftJoin(
		long groupId, LockedLayoutType lockedLayoutType) {

		if (!Objects.equals(lockedLayoutType, LockedLayoutType.CONTENT_PAGE) &&
			!Objects.equals(lockedLayoutType, LockedLayoutType.UTILITY_PAGE)) {

			return null;
		}

		return LayoutUtilityPageEntryTable.INSTANCE.groupId.eq(
			groupId
		).and(
			LayoutUtilityPageEntryTable.INSTANCE.plid.eq(
				LayoutTable.INSTANCE.classPK)
		);
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

	private LimitStep _getLimitStep(
		LockedLayoutOrder lockedLayoutOrder, OrderByStep orderByStep) {

		if (lockedLayoutOrder == null) {
			return orderByStep.orderBy(
				LockTable.INSTANCE.createDate.descending());
		}

		if (Objects.equals(
				lockedLayoutOrder.getLockedLayoutOrderType(),
				LockedLayoutOrder.LockedLayoutOrderType.NAME)) {

			return orderByStep.orderBy(
				LayoutTable.INSTANCE,
				new LayoutNameComparator(
					lockedLayoutOrder.isAscending(),
					lockedLayoutOrder.getLocale()));
		}

		return orderByStep.orderBy(_getOrderByExpression(lockedLayoutOrder));
	}

	private OrderByExpression _getOrderByExpression(
		LockedLayoutOrder lockedLayoutOrder) {

		if (Objects.equals(
				lockedLayoutOrder.getLockedLayoutOrderType(),
				LockedLayoutOrder.LockedLayoutOrderType.LAST_AUTOSAVE)) {

			if (lockedLayoutOrder.isAscending()) {
				return LockTable.INSTANCE.createDate.ascending();
			}

			return LockTable.INSTANCE.createDate.descending();
		}

		if (Objects.equals(
				lockedLayoutOrder.getLockedLayoutOrderType(),
				LockedLayoutOrder.LockedLayoutOrderType.USER)) {

			if (lockedLayoutOrder.isAscending()) {
				return LockTable.INSTANCE.userName.ascending();
			}

			return LockTable.INSTANCE.userName.descending();
		}

		if (lockedLayoutOrder.isAscending()) {
			return LockTable.INSTANCE.createDate.ascending();
		}

		return LockTable.INSTANCE.createDate.descending();
	}

	private Predicate _getWherePredicate(
		long groupId, LockedLayoutType lockedLayoutType) {

		Predicate wherePredicate = LayoutTable.INSTANCE.groupId.eq(
			groupId
		).and(
			LayoutTable.INSTANCE.classPK.gt(0L)
		).and(
			LayoutTable.INSTANCE.hidden.eq(true)
		).and(
			LayoutTable.INSTANCE.system.eq(true)
		).and(
			LayoutTable.INSTANCE.status.eq(WorkflowConstants.STATUS_DRAFT)
		);

		if (lockedLayoutType == null) {
			return wherePredicate.and(
				LayoutTable.INSTANCE.type.in(
					new String[] {
						LayoutConstants.TYPE_ASSET_DISPLAY,
						LayoutConstants.TYPE_COLLECTION,
						LayoutConstants.TYPE_CONTENT
					}));
		}

		Integer layoutPageTemplateEntryType = _getLayoutPageTemplateEntryType(
			lockedLayoutType);

		if (layoutPageTemplateEntryType != null) {
			return wherePredicate.and(
				LayoutPageTemplateEntryTable.INSTANCE.type.eq(
					layoutPageTemplateEntryType));
		}

		String layoutType = _getLayoutType(lockedLayoutType);

		if (layoutType != null) {
			return wherePredicate.and(
				LayoutTable.INSTANCE.type.eq(
					layoutType
				).and(
					() -> {
						if (Objects.equals(
								layoutType, LayoutConstants.TYPE_CONTENT)) {

							return LayoutPageTemplateEntryTable.INSTANCE.
								layoutPageTemplateEntryId.isNull(
								).and(
									LayoutUtilityPageEntryTable.INSTANCE.
										LayoutUtilityPageEntryId.isNull()
								);
						}

						return null;
					}
				));
		}

		if (Objects.equals(lockedLayoutType, LockedLayoutType.UTILITY_PAGE)) {
			return wherePredicate.and(
				LayoutUtilityPageEntryTable.INSTANCE.LayoutUtilityPageEntryId.
					isNotNull());
		}

		return wherePredicate;
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
	private LockLocalService _lockLocalService;

	@Reference
	private LockManager _lockManager;

	@Reference
	private Portal _portal;

	private static class LockedLayoutsTable
		extends BaseTable<LockedLayoutsTable> {

		public static final LockedLayoutsTable INSTANCE =
			new LockedLayoutsTable();

		public final Column<LockedLayoutsTable, Long> classPKColumn =
			createColumn(
				"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
		public final Column<LockedLayoutsTable, Date> createDateColumn =
			createColumn(
				"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
		public final Column<LockedLayoutsTable, String> nameColumn =
			createColumn(
				"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
		public final Column<LockedLayoutsTable, Long> plidColumn = createColumn(
			"plid", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
		public final Column<LockedLayoutsTable, String> typeColumn =
			createColumn(
				"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
		public final Column<LockedLayoutsTable, String> userNameColumn =
			createColumn(
				"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

		private LockedLayoutsTable() {
			super("LockedLayoutsTable", LockedLayoutsTable::new);
		}

	}

}