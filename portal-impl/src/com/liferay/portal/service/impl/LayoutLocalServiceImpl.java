/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.LayoutHiddenException;
import com.liferay.portal.LayoutNameException;
import com.liferay.portal.LayoutParentLayoutIdException;
import com.liferay.portal.LayoutTypeException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.lar.LayoutExporter;
import com.liferay.portal.lar.LayoutImporter;
import com.liferay.portal.lar.PortletExporter;
import com.liferay.portal.lar.PortletImporter;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutReference;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutLocalServiceBaseImpl;
import com.liferay.portal.util.FriendlyURLNormalizer;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.LayoutComparator;
import com.liferay.portal.util.comparator.LayoutPriorityComparator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletException;

/**
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 */
public class LayoutLocalServiceImpl extends LayoutLocalServiceBaseImpl {

	public static String getCounterName(long groupId, boolean privateLayout) {
		StringBundler sb = new StringBundler();

		sb.append(Layout.class.getName());
		sb.append(StringPool.POUND);
		sb.append(groupId);
		sb.append(StringPool.POUND);
		sb.append(privateLayout);

		return sb.toString();
	}

	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, Map<Locale, String> nameMap,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			Map<Locale, String> keywordsMap, Map<Locale, String> robotsMap,
			String type, boolean hidden, String friendlyURL, boolean locked,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Layout

		User user = userPersistence.findByPrimaryKey(userId);
		long layoutId = getNextLayoutId(groupId, privateLayout);
		parentLayoutId = getParentLayoutId(
			groupId, privateLayout, parentLayoutId);
		String name = nameMap.get(LocaleUtil.getDefault());
		friendlyURL = getFriendlyURL(
			groupId, privateLayout, layoutId, name, friendlyURL);
		int priority = getNextPriority(groupId, privateLayout, parentLayoutId);

		validate(
			groupId, privateLayout, layoutId, parentLayoutId, name, type,
			hidden, friendlyURL);

		Date now = new Date();

		long plid = counterLocalService.increment();

		Layout layout = layoutPersistence.create(plid);

		layout.setUuid(serviceContext.getUuid());
		layout.setGroupId(groupId);
		layout.setCompanyId(user.getCompanyId());
		layout.setCreateDate(serviceContext.getCreateDate(now));
		layout.setModifiedDate(serviceContext.getModifiedDate(now));
		layout.setPrivateLayout(privateLayout);
		layout.setLayoutId(layoutId);
		layout.setParentLayoutId(parentLayoutId);
		layout.setNameMap(nameMap);
		layout.setTitleMap(titleMap);
		layout.setDescriptionMap(descriptionMap);
		layout.setKeywordsMap(keywordsMap);
		layout.setRobotsMap(robotsMap);
		layout.setType(type);
		layout.setHidden(hidden);
		layout.setFriendlyURL(friendlyURL);
		layout.setPriority(priority);

		if (locked) {
			UnicodeProperties typeSettingsProperties =
				layout.getTypeSettingsProperties();

			typeSettingsProperties.put("locked", String.valueOf(locked));

			layout.setTypeSettingsProperties(typeSettingsProperties);
		}

		if (type.equals(LayoutConstants.TYPE_PORTLET)) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			layoutTypePortlet.setLayoutTemplateId(
				0, PropsValues.LAYOUT_DEFAULT_TEMPLATE_ID, false);
		}

		layoutPersistence.update(layout, false);

		// Resources

		boolean addGuestPermissions = false;

		if (!privateLayout || type.equals(LayoutConstants.TYPE_CONTROL_PANEL)) {
			addGuestPermissions = true;
		}

		resourceLocalService.addResources(
			user.getCompanyId(), groupId, user.getUserId(),
			Layout.class.getName(), layout.getPlid(), false, true,
			addGuestPermissions);

		// Group

		groupLocalService.updateSite(groupId, true);

		// Layout set

		layoutSetLocalService.updatePageCount(groupId, privateLayout);

		// Expando

		ExpandoBridge expandoBridge = layout.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Message boards

		if (PropsValues.LAYOUT_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				userId, user.getFullName(), groupId, Layout.class.getName(),
				plid, WorkflowConstants.ACTION_PUBLISH);
		}

		return layout;
	}

	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, String name, String title, String description,
			String type, boolean hidden, String friendlyURL, boolean locked,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Map<Locale, String> localeNamesMap = new HashMap<Locale, String>();

		Locale defaultLocale = LocaleUtil.getDefault();

		localeNamesMap.put(defaultLocale, name);

		return addLayout(
			userId, groupId, privateLayout, parentLayoutId, localeNamesMap,
			new HashMap<Locale, String>(), new HashMap<Locale, String>(),
			new HashMap<Locale, String>(), new HashMap<Locale, String>(),
			type, hidden, friendlyURL, locked, serviceContext);
	}

	public void deleteLayout(
			Layout layout, boolean updateLayoutSet,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Child layouts

		List<Layout> childLayouts = layoutPersistence.findByG_P_P(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getLayoutId());

		for (Layout childLayout : childLayouts) {
			deleteLayout(childLayout, updateLayoutSet, serviceContext);
		}

		// Portlet preferences

		portletPreferencesLocalService.deletePortletPreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			Layout.class.getName(), layout.getPlid());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			Layout.class.getName(), layout.getPlid());

		// Journal articles

		journalArticleLocalService.deleteLayoutArticleReferences(
			layout.getGroupId(), layout.getUuid());

		// Journal content searches

		journalContentSearchLocalService.deleteLayoutContentSearches(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getLayoutId());

		// Expando

		expandoValueLocalService.deleteValues(
			Layout.class.getName(), layout.getPlid());

		// Icon

		imageLocalService.deleteImage(layout.getIconImageId());

		// Scope group

		Group scopeGroup = layout.getScopeGroup();

		if (scopeGroup != null) {
			groupLocalService.deleteGroup(scopeGroup.getGroupId());
		}

		// Resources

		String primKey =
			layout.getPlid() + PortletConstants.LAYOUT_SEPARATOR + "%";

		List<Resource> resources = resourceFinder.findByC_P(
			layout.getCompanyId(), primKey);

		for (Resource resource : resources) {
			resourceLocalService.deleteResource(resource);
		}

		resourceLocalService.deleteResource(
			layout.getCompanyId(), Layout.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, layout.getPlid());

		// Layout

		layoutPersistence.remove(layout);

		// Layout set

		if (updateLayoutSet) {
			layoutSetLocalService.updatePageCount(
				layout.getGroupId(), layout.isPrivateLayout());
		}
	}

	public void deleteLayout(long plid, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		deleteLayout(layout, true, serviceContext);
	}

	public void deleteLayout(
			long groupId, boolean privateLayout, long layoutId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		deleteLayout(layout, true, serviceContext);
	}

	public void deleteLayouts(
			long groupId, boolean privateLayout, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Layouts

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		for (Layout layout : layouts) {
			try {
				deleteLayout(layout, false, serviceContext);
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		// Layout set

		layoutSetLocalService.updatePageCount(groupId, privateLayout);

		// Counter

		counterLocalService.reset(getCounterName(groupId, privateLayout));
	}

	public byte[] exportLayouts(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		File file = exportLayoutsAsFile(
			groupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);

		try {
			return FileUtil.getBytes(file);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		finally {
			file.delete();
		}
	}

	public byte[] exportLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		return exportLayouts(
			groupId, privateLayout, null, parameterMap, startDate, endDate);
	}

	public File exportLayoutsAsFile(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		try {
			LayoutExporter layoutExporter = new LayoutExporter();

			return layoutExporter.exportLayoutsAsFile(
				groupId, privateLayout, layoutIds, parameterMap, startDate,
				endDate);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public byte[] exportPortletInfo(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		File file = exportPortletInfoAsFile(
			plid, groupId, portletId, parameterMap, startDate, endDate);

		try {
			return FileUtil.getBytes(file);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		finally {
			file.delete();
		}
	}

	public File exportPortletInfoAsFile(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		try {
			PortletExporter portletExporter = new PortletExporter();

			return portletExporter.exportPortletInfoAsFile(
				plid, groupId, portletId, parameterMap, startDate, endDate);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public Layout fetchLayoutByUuidAndGroupId(String uuid, long groupId)
		throws SystemException {

		return layoutPersistence.fetchByUUID_G(uuid, groupId);
	}

	public long getDefaultPlid(long groupId) throws SystemException {
		if (groupId > 0) {
			List<Layout> layouts = layoutPersistence.findByGroupId(
				groupId, 0, 1);

			if (layouts.size() > 0) {
				Layout layout = layouts.get(0);

				return layout.getPlid();
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	public long getDefaultPlid(long groupId, boolean privateLayout)
		throws SystemException {

		if (groupId > 0) {
			List<Layout> layouts = layoutPersistence.findByG_P(
				groupId, privateLayout, 0, 1);

			if (layouts.size() > 0) {
				Layout layout = layouts.get(0);

				return layout.getPlid();
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	public long getDefaultPlid(
			long groupId, boolean privateLayout, String portletId)
		throws PortalException, SystemException {

		if (groupId > 0) {
			List<Layout> layouts = layoutPersistence.findByG_P(
				groupId, privateLayout);

			for (Layout layout : layouts) {
				if (layout.isTypePortlet()) {
					LayoutTypePortlet layoutTypePortlet =
						(LayoutTypePortlet)layout.getLayoutType();

					if (layoutTypePortlet.hasPortletId(portletId)) {
						return layout.getPlid();
					}
				}
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	public Layout getFriendlyURLLayout(
			long groupId, boolean privateLayout, String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(friendlyURL)) {
			throw new NoSuchLayoutException();
		}

		friendlyURL = getFriendlyURL(friendlyURL);

		Layout layout = layoutPersistence.fetchByG_P_F(
			groupId, privateLayout, friendlyURL);

		if ((layout == null) &&
			(friendlyURL.startsWith(StringPool.SLASH)) &&
			(Validator.isNumber(friendlyURL.substring(1)))) {

			long layoutId = GetterUtil.getLong(friendlyURL.substring(1));

			layout = layoutPersistence.fetchByG_P_L(
				groupId, privateLayout, layoutId);
		}

		if (layout == null) {
			throw new NoSuchLayoutException();
		}

		return layout;
	}

	@Override
	public Layout getLayout(long plid)
		throws PortalException, SystemException {

		return layoutPersistence.findByPrimaryKey(plid);
	}

	public Layout getLayout(long groupId, boolean privateLayout, long layoutId)
		throws PortalException, SystemException {

		return layoutPersistence.findByG_P_L(groupId, privateLayout, layoutId);
	}

	public Layout getLayoutByIconImageId(long iconImageId)
		throws PortalException, SystemException {

		return layoutPersistence.findByIconImageId(iconImageId);
	}

	@Override
	public Layout getLayoutByUuidAndGroupId(String uuid, long groupId)
		throws PortalException, SystemException {

		return layoutPersistence.findByUUID_G(uuid, groupId);
	}

	public List<Layout> getLayouts(long groupId, boolean privateLayout)
		throws SystemException {

		return layoutPersistence.findByG_P(groupId, privateLayout);
	}

	public List<Layout> getLayouts(
			long groupId, boolean privateLayout, long parentLayoutId)
		throws SystemException {

		return layoutPersistence.findByG_P_P(
			groupId, privateLayout, parentLayoutId);
	}

	public List<Layout> getLayouts(
			long groupId, boolean privateLayout, long parentLayoutId,
			boolean incomplete, int start, int end)
		throws SystemException {

		return layoutPersistence.findByG_P_P(
			groupId, privateLayout, parentLayoutId, start, end);
	}

	public List<Layout> getLayouts(
			long groupId, boolean privateLayout, long[] layoutIds)
		throws PortalException, SystemException {

		List<Layout> layouts = new ArrayList<Layout>();

		for (long layoutId : layoutIds) {
			Layout layout = getLayout(groupId, privateLayout, layoutId);

			layouts.add(layout);
		}

		return layouts;
	}

	public List<Layout> getLayouts(
			long groupId, boolean privateLayout, String type)
		throws SystemException {

		return layoutPersistence.findByG_P_T(groupId, privateLayout, type);
	}

	public LayoutReference[] getLayouts(
			long companyId, String portletId, String preferencesKey,
			String preferencesValue)
		throws SystemException {

		List<LayoutReference> layoutReferences = layoutFinder.findByC_P_P(
			companyId, portletId, preferencesKey, preferencesValue);

		return layoutReferences.toArray(
			new LayoutReference[layoutReferences.size()]);
	}

	public long getNextLayoutId(long groupId, boolean privateLayout)
		throws SystemException {

		long nextLayoutId = counterLocalService.increment(
			getCounterName(groupId, privateLayout));

		if (nextLayoutId == 1) {
			List<Layout> layouts = layoutPersistence.findByG_P(
				groupId, privateLayout, 0, 1, new LayoutComparator());

			if (!layouts.isEmpty()) {
				Layout layout = layouts.get(0);

				nextLayoutId = layout.getLayoutId() + 1;

				counterLocalService.reset(
					getCounterName(groupId, privateLayout), nextLayoutId);
			}
		}

		return nextLayoutId;
	}

	public List<Layout> getNullFriendlyURLLayouts() throws SystemException {
		return layoutFinder.findByNullFriendlyURL();
	}

	public List<Layout> getScopeGroupLayouts(
			long groupId, boolean privateLayout)
		throws SystemException {

		return layoutFinder.findByScopeGroup(groupId, privateLayout);
	}

	public boolean hasLayouts(
			long groupId, boolean privateLayout, long parentLayoutId)
		throws SystemException {

		return layoutPersistence.countByG_P_P(
			groupId, privateLayout, parentLayoutId) > 0;
	}

	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, byte[] bytes)
		throws PortalException, SystemException {

		importLayouts(
			userId, groupId, privateLayout, parameterMap,
			new UnsyncByteArrayInputStream(bytes));
	}

	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws PortalException, SystemException {

		try {
			LayoutImporter layoutImporter = new LayoutImporter();

			layoutImporter.importLayouts(
				userId, groupId, privateLayout, parameterMap, file);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException, SystemException {

		try {
			File file = FileUtil.createTempFile("lar");

			FileUtil.write(file, is);

			importLayouts(userId, groupId, privateLayout, parameterMap, file);
		}
		catch (IOException e) {
			throw new SystemException(e);
		}
	}

	public void importPortletInfo(
			long userId, long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, File file)
		throws PortalException, SystemException {

		try {
			PortletImporter portletImporter = new PortletImporter();

			portletImporter.importPortletInfo(
				userId, plid, groupId, portletId, parameterMap, file);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public void importPortletInfo(
			long userId, long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException, SystemException {

		try {
			File file = FileUtil.createTempFile("lar");

			FileUtil.write(file, is);

			importPortletInfo(
				userId, plid, groupId, portletId, parameterMap, file);
		}
		catch (IOException e) {
			throw new SystemException(e);
		}
	}

	public void setLayouts(
			long groupId, boolean privateLayout, long parentLayoutId,
			long[] layoutIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (layoutIds == null) {
			return;
		}

		if (parentLayoutId == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			if (layoutIds.length < 1) {
				throw new RequiredLayoutException(
					RequiredLayoutException.AT_LEAST_ONE);
			}

			Layout layout = layoutPersistence.findByG_P_L(
				groupId, privateLayout, layoutIds[0]);

			if (!PortalUtil.isLayoutFirstPageable(layout.getType())) {
				throw new RequiredLayoutException(
					RequiredLayoutException.FIRST_LAYOUT_TYPE);
			}

			if (layout.isHidden()) {
				throw new RequiredLayoutException(
					RequiredLayoutException.FIRST_LAYOUT_HIDDEN);
			}
		}

		Set<Long> layoutIdsSet = new LinkedHashSet<Long>();

		for (long layoutId : layoutIds) {
			layoutIdsSet.add(layoutId);
		}

		Set<Long> newLayoutIdsSet = new HashSet<Long>();

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			groupId, privateLayout, parentLayoutId);

		for (Layout layout : layouts) {
			if (!layoutIdsSet.contains(layout.getLayoutId())) {
				deleteLayout(layout, true, serviceContext);
			}
			else {
				newLayoutIdsSet.add(layout.getLayoutId());
			}
		}

		int priority = 0;

		for (long layoutId : layoutIdsSet) {
			Layout layout = layoutPersistence.findByG_P_L(
				groupId, privateLayout, layoutId);

			layout.setPriority(priority++);

			layoutPersistence.update(layout, false);
		}

		layoutSetLocalService.updatePageCount(groupId, privateLayout);
	}

	public Layout updateFriendlyURL(long plid, String friendlyURL)
		throws PortalException, SystemException {

		Date now = new Date();

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		friendlyURL = getFriendlyURL(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getLayoutId(), StringPool.BLANK, friendlyURL);

		validateFriendlyURL(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			friendlyURL);

		layout.setModifiedDate(now);
		layout.setFriendlyURL(friendlyURL);

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, Map<Locale, String> nameMap,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			Map<Locale, String> keywordsMap, Map<Locale, String> robotsMap,
			String type, boolean hidden, String friendlyURL, Boolean iconImage,
			byte[] iconBytes, boolean locked, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Layout

		parentLayoutId = getParentLayoutId(
			groupId, privateLayout, parentLayoutId);
		String name = nameMap.get(LocaleUtil.getDefault());
		friendlyURL = getFriendlyURL(
			groupId, privateLayout, layoutId, StringPool.BLANK, friendlyURL);

		validate(
			groupId, privateLayout, layoutId, parentLayoutId, name, type,
			hidden, friendlyURL);

		validateParentLayoutId(
			groupId, privateLayout, layoutId, parentLayoutId);

		Date now = new Date();

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (parentLayoutId != layout.getParentLayoutId()) {
			layout.setPriority(
				getNextPriority(groupId, privateLayout, parentLayoutId));
		}

		layout.setModifiedDate(serviceContext.getModifiedDate(now));
		layout.setParentLayoutId(parentLayoutId);
		layout.setNameMap(nameMap);
		layout.setTitleMap(titleMap);
		layout.setDescriptionMap(descriptionMap);
		layout.setKeywordsMap(keywordsMap);
		layout.setRobotsMap(robotsMap);
		layout.setType(type);
		layout.setHidden(hidden);
		layout.setFriendlyURL(friendlyURL);

		if (iconImage != null) {
			layout.setIconImage(iconImage.booleanValue());

			if (iconImage.booleanValue()) {
				long iconImageId = layout.getIconImageId();

				if (iconImageId <= 0) {
					iconImageId = counterLocalService.increment();

					layout.setIconImageId(iconImageId);
				}
			}
		}

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		typeSettingsProperties.put("locked", String.valueOf(locked));

		layout.setTypeSettingsProperties(typeSettingsProperties);

		layoutPersistence.update(layout, false);

		// Icon

		if (iconImage != null) {
			if (!iconImage.booleanValue()) {
				imageLocalService.deleteImage(layout.getIconImageId());
			}
			else if ((iconBytes != null) && (iconBytes.length > 0)) {
				imageLocalService.updateImage(
					layout.getIconImageId(), iconBytes);
			}
		}

		// Portlet preferences

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			updateScopedPortletNames(
				groupId, privateLayout, layoutId, nameMap.get(locale),
				LanguageUtil.getLanguageId(locale));
		}

		// Expando

		ExpandoBridge expandoBridge = layout.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		return layout;
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			String typeSettings)
		throws PortalException, SystemException {

		Date now = new Date();

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			PropsValues.INDEX_DATE_FORMAT_PATTERN);

		typeSettingsProperties.fastLoad(typeSettings);

		typeSettingsProperties.setProperty(
			"modifiedDate", dateFormat.format(now));

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		layout.setModifiedDate(now);
		layout.setTypeSettings(typeSettingsProperties.toString());

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updateLookAndFeel(
			long groupId, boolean privateLayout, long layoutId, String themeId,
			String colorSchemeId, String css, boolean wapTheme)
		throws PortalException, SystemException {

		Date now = new Date();

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		layout.setModifiedDate(now);

		if (wapTheme) {
			layout.setWapThemeId(themeId);
			layout.setWapColorSchemeId(colorSchemeId);
		}
		else {
			layout.setThemeId(themeId);
			layout.setColorSchemeId(colorSchemeId);
			layout.setCss(css);
		}

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updateName(Layout layout, String name, String languageId)
		throws PortalException, SystemException {

		Date now = new Date();

		validateName(name, languageId);

		layout.setModifiedDate(now);
		layout.setName(name, LocaleUtil.fromLanguageId(languageId));

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updateName(
			long groupId, boolean privateLayout, long layoutId, String name,
			String languageId)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		return layoutLocalService.updateName(layout, name, languageId);
	}

	public Layout updateName(long plid, String name, String languageId)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		return layoutLocalService.updateName(layout, name, languageId);
	}

	public Layout updateParentLayoutId(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId)
		throws PortalException, SystemException {

		parentLayoutId = getParentLayoutId(
			groupId, privateLayout, parentLayoutId);

		validateParentLayoutId(
			groupId, privateLayout, layoutId, parentLayoutId);

		Date now = new Date();

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (parentLayoutId != layout.getParentLayoutId()) {
			layout.setPriority(
				getNextPriority(groupId, privateLayout, parentLayoutId));
		}

		layout.setModifiedDate(now);
		layout.setParentLayoutId(parentLayoutId);

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updateParentLayoutId(long plid, long parentPlid)
		throws PortalException, SystemException {

		Date now = new Date();

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		long parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

		if (parentPlid > 0) {
			Layout parentLayout = layoutPersistence.fetchByPrimaryKey(
				parentPlid);

			if (parentLayout != null) {
				parentLayoutId = parentLayout.getLayoutId();
			}
		}

		parentLayoutId = getParentLayoutId(
			layout.getGroupId(), layout.isPrivateLayout(), parentLayoutId);

		validateParentLayoutId(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			parentLayoutId);

		if (parentLayoutId != layout.getParentLayoutId()) {
			int priority = getNextPriority(
				layout.getGroupId(), layout.isPrivateLayout(), parentLayoutId);

			layout.setPriority(priority);
		}

		layout.setModifiedDate(now);
		layout.setParentLayoutId(parentLayoutId);

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updatePriority(Layout layout, int priority)
		throws SystemException {

		if (layout.getPriority() == priority) {
			return layout;
		}

		Date now = new Date();

		boolean lessThan = false;

		if (layout.getPriority() < priority) {
			lessThan = true;
		}

		layout.setModifiedDate(now);
		layout.setPriority(priority);

		layoutPersistence.update(layout, false);

		priority = 0;

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getParentLayoutId());

		layouts = ListUtil.sort(
			layouts, new LayoutPriorityComparator(layout, lessThan));

		for (Layout curLayout : layouts) {
			curLayout.setModifiedDate(now);
			curLayout.setPriority(priority++);

			layoutPersistence.update(curLayout, false);

			if (curLayout.equals(layout)) {
				layout = curLayout;
			}
		}

		return layout;
	}

	public Layout updatePriority(
			long groupId, boolean privateLayout, long layoutId, int priority)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		return updatePriority(layout, priority);
	}

	public Layout updatePriority(long plid, int priority)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		return updatePriority(layout, priority);
	}

	/**
	 * @see com.liferay.portlet.portletconfiguration.action.EditScopeAction#updateScope
	 */
	public void updateScopedPortletNames(
			long groupId, boolean privateLayout, long layoutId, String name,
			String languageId)
		throws PortalException, SystemException {

		Layout layout = getLayout(groupId, privateLayout, layoutId);

		List<Layout> layouts = getLayouts(groupId, privateLayout);

		for (Layout curLayout : layouts) {
			List<PortletPreferences> portletPreferencesList =
				portletPreferencesLocalService.getPortletPreferencesByPlid(
					curLayout.getPlid());

			for (PortletPreferences portletPreferences :
					portletPreferencesList) {

				String portletId = portletPreferences.getPortletId();

				Portlet portlet = portletLocalService.getPortletById(portletId);

				if ((portlet == null) || !portlet.isScopeable()) {
					continue;
				}

				javax.portlet.PortletPreferences preferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						curLayout, portletId);

				String scopeLayoutUuid = GetterUtil.getString(
					preferences.getValue("lfrScopeLayoutUuid", null));

				if (!scopeLayoutUuid.equals(layout.getUuid())) {
					continue;
				}

				String portletTitle = PortalUtil.getPortletTitle(
					portletId, languageId);

				String newPortletTitle = PortalUtil.getNewPortletTitle(
					portletTitle, curLayout.getName(languageId), name);

				if (newPortletTitle.equals(portletTitle)) {
					continue;
				}

				try {
					preferences.setValue(
						"portlet-setup-title-" + languageId, newPortletTitle);
					preferences.setValue(
						"portlet-setup-use-custom-title",
						Boolean.TRUE.toString());

					preferences.store();
				}
				catch (IOException ioe) {
					throw new SystemException(ioe);
				}
				catch (PortletException pe) {
					throw new SystemException(pe);
				}
			}
		}
	}

	protected String getFriendlyURL(
			long groupId, boolean privateLayout, long layoutId,
			String name, String friendlyURL)
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

	protected String getFriendlyURL(String friendlyURL) {
		return FriendlyURLNormalizer.normalize(friendlyURL);
	}

	protected int getNextPriority(
			long groupId, boolean privateLayout, long parentLayoutId)
		throws SystemException {

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			groupId, privateLayout, parentLayoutId);

		if (layouts.size() == 0) {
			return 0;
		}

		Layout layout = layouts.get(layouts.size() - 1);

		return layout.getPriority() + 1;
	}

	protected long getParentLayoutId(
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

	protected void validate(
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

	protected void validateFirstLayout(String type)
		throws PortalException {

		if (!PortalUtil.isLayoutFirstPageable(type)) {
			throw new LayoutTypeException(LayoutTypeException.FIRST_LAYOUT);
		}
	}

	protected void validateFriendlyURL(
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

	protected void validateName(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new LayoutNameException();
		}
	}

	protected void validateName(String name, String languageId)
		throws PortalException {

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		if (defaultLanguageId.equals(languageId)) {
			validateName(name);
		}
	}

	protected void validateParentLayoutId(
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
					catch (LayoutHiddenException lhe) {
						throw new LayoutParentLayoutIdException(
							LayoutParentLayoutIdException.FIRST_LAYOUT_HIDDEN);
					}
					catch (LayoutTypeException lte) {
						throw new LayoutParentLayoutIdException(
							LayoutParentLayoutIdException.FIRST_LAYOUT_TYPE);
					}
				}
			}
		}
	}

}