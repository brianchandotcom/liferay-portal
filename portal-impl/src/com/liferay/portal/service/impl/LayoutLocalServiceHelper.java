/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.LayoutNameException;
import com.liferay.portal.LayoutParentLayoutIdException;
import com.liferay.portal.LayoutTypeException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.persistence.LayoutPersistence;
import com.liferay.portal.service.persistence.LayoutSetPersistence;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.comparator.LayoutPriorityComparator;
import com.liferay.portlet.sites.util.SitesUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class LayoutLocalServiceHelper implements IdentifiableBean {

	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	public String getFriendlyURL(
			long groupId, boolean privateLayout, long layoutId, String name,
			String friendlyURL)
		throws PortalException, SystemException {

		friendlyURL = getFriendlyURL(friendlyURL);

		if (Validator.isNull(friendlyURL)) {
			friendlyURL = StringPool.SLASH + getFriendlyURL(name);

			String originalFriendlyURL = friendlyURL;

			for (int i = 1;; i++) {
				try {
					validateFriendlyURL(
						groupId, privateLayout, layoutId, friendlyURL);

					break;
				}
				catch (LayoutFriendlyURLException lfurle) {
					int type = lfurle.getType();

					if (type == LayoutFriendlyURLException.DUPLICATE) {
						friendlyURL = originalFriendlyURL + i;
					}
					else {
						friendlyURL = StringPool.SLASH + layoutId;

						break;
					}
				}
			}
		}

		return friendlyURL;
	}

	public String getFriendlyURL(String friendlyURL) {
		return FriendlyURLNormalizerUtil.normalize(friendlyURL);
	}

	public Map<Locale, String> getFriendlyURLMap(
			long groupId, boolean privateLayout, long layoutId, String name,
			Map<Locale, String> friendlyURLMap)
		throws PortalException, SystemException {

		Map<Locale, String> newFriendlyURLMap = new HashMap<Locale, String>();

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			String friendlyURL = friendlyURLMap.get(locale);

			if (Validator.isNotNull(friendlyURL)) {
				friendlyURL = getFriendlyURL(
					groupId, privateLayout, layoutId, name, friendlyURL);

				newFriendlyURLMap.put(locale, friendlyURL);
			}
		}

		if (newFriendlyURLMap.isEmpty()) {
			String friendlyURL = getFriendlyURL(
				groupId, privateLayout, layoutId, name, StringPool.BLANK);

			newFriendlyURLMap.put(LocaleUtil.getDefault(), friendlyURL);
		}

		return newFriendlyURLMap;
	}

	public int getNextPriority(
			long groupId, boolean privateLayout, long parentLayoutId,
			String sourcePrototypeLayoutUuid, int defaultPriority)
		throws SystemException {

		try {
			int priority = defaultPriority;

			if (priority < 0) {
				Layout layout = layoutPersistence.findByG_P_P_First(
					groupId, privateLayout, parentLayoutId,
					new LayoutPriorityComparator(false));

				priority = layout.getPriority() + 1;
			}

			if ((priority < _PRIORITY_BUFFER) &&
				Validator.isNull(sourcePrototypeLayoutUuid)) {

				LayoutSet layoutSet = layoutSetPersistence.fetchByG_P(
					groupId, privateLayout);

				if (Validator.isNotNull(
						layoutSet.getLayoutSetPrototypeUuid())) {

					priority = priority + _PRIORITY_BUFFER;
				}
			}

			return priority;
		}
		catch (NoSuchLayoutException nsle) {
			return 0;
		}
	}

	public long getParentLayoutId(
			long groupId, boolean privateLayout, long parentLayoutId)
		throws SystemException {

		if (parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			// Ensure parent layout exists

			Layout parentLayout = layoutPersistence.fetchByG_P_L(
				groupId, privateLayout, parentLayoutId);

			if (parentLayout == null) {
				parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;
			}
		}

		return parentLayoutId;
	}

	public boolean hasLayoutSetPrototypeLayout(
			LayoutSetPrototype layoutSetPrototype, String layoutUuid)
		throws PortalException, SystemException {

		Group group = layoutSetPrototype.getGroup();

		Layout layout = layoutPersistence.fetchByUUID_G_P(
			layoutUuid, group.getGroupId(), true);

		if (layout != null) {
			return true;
		}

		return false;
	}

	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	public void validate(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, String name, String type, boolean hidden,
			String friendlyURL)
		throws PortalException, SystemException {

		validateName(name);

		boolean firstLayout = false;

		if (parentLayoutId == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			List<Layout> layouts = layoutPersistence.findByG_P_P(
				groupId, privateLayout, parentLayoutId, 0, 1);

			if (layouts.size() == 0) {
				firstLayout = true;
			}
			else {
				long firstLayoutId = layouts.get(0).getLayoutId();

				if (firstLayoutId == layoutId) {
					firstLayout = true;
				}
			}
		}

		if (firstLayout) {
			validateFirstLayout(type);
		}

		if (!PortalUtil.isLayoutParentable(type)) {
			if (layoutPersistence.countByG_P_P(
					groupId, privateLayout, layoutId) > 0) {

				throw new LayoutTypeException(
					LayoutTypeException.NOT_PARENTABLE);
			}
		}

		validateFriendlyURL(groupId, privateLayout, layoutId, friendlyURL);
	}

	public void validateFirstLayout(String type) throws PortalException {
		if (Validator.isNull(type) || !PortalUtil.isLayoutFirstPageable(type)) {
			LayoutTypeException lte = new LayoutTypeException(
				LayoutTypeException.FIRST_LAYOUT);

			lte.setLayoutType(type);

			throw lte;
		}
	}

	public void validateFriendlyURL(
			long groupId, boolean privateLayout, long layoutId,
			String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(friendlyURL)) {
			return;
		}

		int exceptionType = LayoutImpl.validateFriendlyURL(friendlyURL);

		if (exceptionType != -1) {
			throw new LayoutFriendlyURLException(exceptionType);
		}

		Layout layout = layoutPersistence.fetchByG_P_F(
			groupId, privateLayout, friendlyURL);

		if ((layout != null) && (layout.getLayoutId() != layoutId)) {
			throw new LayoutFriendlyURLException(
				LayoutFriendlyURLException.DUPLICATE);
		}

		LayoutImpl.validateFriendlyURLKeyword(friendlyURL);

		/*List<FriendlyURLMapper> friendlyURLMappers =
			portletLocalService.getFriendlyURLMappers();

		for (FriendlyURLMapper friendlyURLMapper : friendlyURLMappers) {
			if (friendlyURL.indexOf(friendlyURLMapper.getMapping()) != -1) {
				LayoutFriendlyURLException lfurle =
					new LayoutFriendlyURLException(
						LayoutFriendlyURLException.KEYWORD_CONFLICT);

				lfurle.setKeywordConflict(friendlyURLMapper.getMapping());

				throw lfurle;
			}
		}*/

		String layoutIdFriendlyURL = friendlyURL.substring(1);

		if (Validator.isNumber(layoutIdFriendlyURL) &&
			!layoutIdFriendlyURL.equals(String.valueOf(layoutId))) {

			LayoutFriendlyURLException lfurle = new LayoutFriendlyURLException(
				LayoutFriendlyURLException.POSSIBLE_DUPLICATE);

			lfurle.setKeywordConflict(layoutIdFriendlyURL);

			throw lfurle;
		}
	}

	public void validateName(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new LayoutNameException();
		}
	}

	public void validateName(String name, String languageId)
		throws PortalException {

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		if (defaultLanguageId.equals(languageId)) {
			validateName(name);
		}
	}

	public void validateParentLayoutId(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (parentLayoutId != layout.getParentLayoutId()) {

			// Layouts can always be moved to the root level

			if (parentLayoutId == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
				return;
			}

			// Layout cannot become a child of a layout that is not parentable

			Layout parentLayout = layoutPersistence.findByG_P_L(
				groupId, privateLayout, parentLayoutId);

			if (!PortalUtil.isLayoutParentable(parentLayout)) {
				throw new LayoutParentLayoutIdException(
					LayoutParentLayoutIdException.NOT_PARENTABLE);
			}

			// Layout cannot become a child of a layout that is not sortable
			// because it is linked to a layout set prototype

			if (!SitesUtil.isLayoutSortable(parentLayout)) {
				throw new LayoutParentLayoutIdException(
					LayoutParentLayoutIdException.NOT_SORTABLE);
			}

			// Layout cannot become descendant of itself

			if (PortalUtil.isLayoutDescendant(layout, parentLayoutId)) {
				throw new LayoutParentLayoutIdException(
					LayoutParentLayoutIdException.SELF_DESCENDANT);
			}

			// If layout is moved, the new first layout must be valid

			if (layout.getParentLayoutId() ==
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

				List<Layout> layouts = layoutPersistence.findByG_P_P(
					groupId, privateLayout,
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, 0, 2);

				// You can only reach this point if there are more than two
				// layouts at the root level because of the descendant check

				long firstLayoutId = layouts.get(0).getLayoutId();

				if (firstLayoutId == layoutId) {
					Layout secondLayout = layouts.get(1);

					try {
						validateFirstLayout(secondLayout.getType());
					}
					catch (LayoutTypeException lte) {
						throw new LayoutParentLayoutIdException(
							LayoutParentLayoutIdException.FIRST_LAYOUT_TYPE);
					}
				}
			}
		}
	}

	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;

	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;

	private static final int _PRIORITY_BUFFER = 1000000;

	private String _beanIdentifier;

}