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

package com.liferay.layout.change.tracking.internal.service;

import com.liferay.change.tracking.CTManager;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.exception.CTEntryException;
import com.liferay.change.tracking.exception.CTException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutVersion;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.comparator.LayoutPriorityComparator;
import com.liferay.portal.service.impl.LayoutLocalServiceHelper;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 * @author Gergely Mathe
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class CTLayoutLocalServiceWrapper extends LayoutLocalServiceWrapper {

	public CTLayoutLocalServiceWrapper() {
		super(null);
	}

	public CTLayoutLocalServiceWrapper(LayoutLocalService layoutLocalService) {
		super(layoutLocalService);
	}

	@Override
	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, long classNameId, long classPK,
			Map<Locale, String> nameMap, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> keywordsMap,
			Map<Locale, String> robotsMap, String type, String typeSettings,
			boolean hidden, boolean system, Map<Locale, String> friendlyURLMap,
			ServiceContext serviceContext)
		throws PortalException {

		Layout layout = _ctManager.executeModelUpdate(
			() -> super.addLayout(
				userId, groupId, privateLayout, parentLayoutId, classNameId,
				classPK, nameMap, titleMap, descriptionMap, keywordsMap,
				robotsMap, type, typeSettings, hidden, system, friendlyURLMap,
				serviceContext));

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_ADDITION);

		return layout;
	}

	@Override
	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, Map<Locale, String> nameMap,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			Map<Locale, String> keywordsMap, Map<Locale, String> robotsMap,
			String type, String typeSettings, boolean hidden, boolean system,
			Map<Locale, String> friendlyURLMap, ServiceContext serviceContext)
		throws PortalException {

		Layout layout = _ctManager.executeModelUpdate(
			() -> super.addLayout(
				userId, groupId, privateLayout, parentLayoutId, nameMap,
				titleMap, descriptionMap, keywordsMap, robotsMap, type,
				typeSettings, hidden, system, friendlyURLMap, serviceContext));

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_ADDITION);

		return layout;
	}

	@Override
	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, Map<Locale, String> nameMap,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			Map<Locale, String> keywordsMap, Map<Locale, String> robotsMap,
			String type, String typeSettings, boolean hidden,
			Map<Locale, String> friendlyURLMap, ServiceContext serviceContext)
		throws PortalException {

		Layout layout = _ctManager.executeModelUpdate(
			() -> super.addLayout(
				userId, groupId, privateLayout, parentLayoutId, nameMap,
				titleMap, descriptionMap, keywordsMap, robotsMap, type,
				typeSettings, hidden, friendlyURLMap, serviceContext));

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_ADDITION);

		return layout;
	}

	@Override
	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, String name, String title, String description,
			String type, boolean hidden, boolean system, String friendlyURL,
			ServiceContext serviceContext)
		throws PortalException {

		Layout layout = _ctManager.executeModelUpdate(
			() -> super.addLayout(
				userId, groupId, privateLayout, parentLayoutId, name, title,
				description, type, hidden, system, friendlyURL,
				serviceContext));

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_ADDITION);

		return layout;
	}

	@Override
	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, String name, String title, String description,
			String type, boolean hidden, String friendlyURL,
			ServiceContext serviceContext)
		throws PortalException {

		Layout layout = _ctManager.executeModelUpdate(
			() -> super.addLayout(
				userId, groupId, privateLayout, parentLayoutId, name, title,
				description, type, hidden, friendlyURL, serviceContext));

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_ADDITION);

		return layout;
	}

	@Override
	public Layout updateIconImage(long plid, byte[] bytes)
		throws PortalException {

		Layout layout = super.updateIconImage(plid, bytes);

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return layout;
	}

	@Override
	public Layout updateLayout(Layout layout) throws PortalException {
		Layout updatedLayout = super.updateLayout(layout);

		LayoutVersion layoutVersion = fetchLatestVersion(updatedLayout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return updatedLayout;
	}

	@Override
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			Date publishDate)
		throws PortalException {

		Layout layout = super.updateLayout(
			groupId, privateLayout, layoutId, publishDate);

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return layout;
	}

	@Override
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long classNameId, long classPK)
		throws PortalException {

		Layout layout = super.updateLayout(
			groupId, privateLayout, layoutId, classNameId, classPK);

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return layout;
	}

	@Override
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, Map<Locale, String> nameMap,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			Map<Locale, String> keywordsMap, Map<Locale, String> robotsMap,
			String type, boolean hidden, Map<Locale, String> friendlyURLMap,
			boolean iconImage, byte[] iconBytes, ServiceContext serviceContext)
		throws PortalException {

		Layout layout = super.updateLayout(
			groupId, privateLayout, layoutId, parentLayoutId, nameMap, titleMap,
			descriptionMap, keywordsMap, robotsMap, type, hidden,
			friendlyURLMap, iconImage, iconBytes, serviceContext);

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return layout;
	}

	@Override
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			String typeSettings)
		throws PortalException {

		Layout layout = super.updateLayout(
			groupId, privateLayout, layoutId, typeSettings);

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return layout;
	}

	@Override
	public Layout updateLookAndFeel(
			long groupId, boolean privateLayout, long layoutId, String themeId,
			String colorSchemeId, String css)
		throws PortalException {

		Layout layout = super.updateLookAndFeel(
			groupId, privateLayout, layoutId, themeId, colorSchemeId, css);

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return layout;
	}

	@Override
	public Layout updateName(Layout layout, String name, String languageId)
		throws PortalException {

		Layout updatedLayout = super.updateName(layout, name, languageId);

		LayoutVersion layoutVersion = fetchLatestVersion(updatedLayout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return updatedLayout;
	}

	@Override
	public Layout updateName(
			long groupId, boolean privateLayout, long layoutId, String name,
			String languageId)
		throws PortalException {

		Layout layout = super.updateName(
			groupId, privateLayout, layoutId, name, languageId);

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return layout;
	}

	@Override
	public Layout updateName(long plid, String name, String languageId)
		throws PortalException {

		Layout layout = super.updateName(plid, name, languageId);

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return layout;
	}

	@Override
	public void updatePriorities(long groupId, boolean privateLayout)
		throws PortalException {

		super.updatePriorities(groupId, privateLayout);

		List<Layout> layouts = getLayouts(groupId, privateLayout);

		for (Layout layout : layouts) {
			LayoutVersion layoutVersion = fetchLatestVersion(layout);

			_registerChange(
				layoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);
		}
	}

	@Override
	public Layout updatePriority(Layout layout, int priority)
		throws PortalException {

		Layout updatedLayout = super.updatePriority(layout, priority);

		if (layout.getPriority() == priority) {
			return updatedLayout;
		}

		int oldPriority = layout.getPriority();

		int nextPriority = _layoutLocalServiceHelper.getNextPriority(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getParentLayoutId(), layout.getSourcePrototypeLayoutUuid(),
			priority);

		if (oldPriority == nextPriority) {
			return updatedLayout;
		}

		LayoutVersion layoutVersion = fetchLatestVersion(updatedLayout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		List<Layout> layouts = super.getLayouts(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getParentLayoutId());

		boolean lessThan = false;

		if (oldPriority < nextPriority) {
			lessThan = true;
		}

		layouts = ListUtil.sort(
			layouts, new LayoutPriorityComparator(layout, lessThan));

		int newPriority = LayoutConstants.FIRST_PRIORITY;

		for (Layout curLayout : layouts) {
			int curNextPriority = _layoutLocalServiceHelper.getNextPriority(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getParentLayoutId(),
				curLayout.getSourcePrototypeLayoutUuid(), newPriority++);

			if (curLayout.getPriority() == curNextPriority) {
				continue;
			}

			LayoutVersion curlayoutVersion = fetchLatestVersion(curLayout);

			_registerChange(
				curlayoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

			if (curLayout.equals(layout)) {
				layout = curLayout;
			}
		}

		return updatedLayout;
	}

	@Override
	public Layout updatePriority(
			long groupId, boolean privateLayout, long layoutId, int priority)
		throws PortalException {

		Layout layout = super.updatePriority(
			groupId, privateLayout, layoutId, priority);

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return layout;
	}

	@Override
	public Layout updatePriority(
			long groupId, boolean privateLayout, long layoutId,
			long nextLayoutId, long previousLayoutId)
		throws PortalException {

		Layout layout = super.updatePriority(
			groupId, privateLayout, layoutId, nextLayoutId, previousLayoutId);

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return layout;
	}

	@Override
	public Layout updatePriority(long plid, int priority)
		throws PortalException {

		Layout layout = super.updatePriority(plid, priority);

		LayoutVersion layoutVersion = fetchLatestVersion(layout);

		_registerChange(layoutVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return layout;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		// Needed for synchronization

	}

	private void _registerChange(LayoutVersion layoutVersion, int changeType)
		throws CTException {

		if (layoutVersion == null) {
			return;
		}

		try {
			_ctManager.registerModelChange(
				layoutVersion.getCompanyId(), PrincipalThreadLocal.getUserId(),
				_portal.getClassNameId(LayoutVersion.class.getName()),
				layoutVersion.getLayoutVersionId(), layoutVersion.getPlid(),
				changeType);
		}
		catch (CTException cte) {
			if (cte instanceof CTEntryException) {
				if (_log.isWarnEnabled()) {
					_log.warn(cte.getMessage());
				}
			}
			else {
				throw cte;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTLayoutLocalServiceWrapper.class);

	@Reference
	private CTManager _ctManager;

	@Reference
	private LayoutLocalServiceHelper _layoutLocalServiceHelper;

	@Reference
	private Portal _portal;

}