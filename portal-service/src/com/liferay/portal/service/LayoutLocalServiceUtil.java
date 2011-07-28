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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the layout local service. This utility wraps {@link com.liferay.portal.service.impl.LayoutLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutLocalService
 * @see com.liferay.portal.service.base.LayoutLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutLocalServiceImpl
 * @generated
 */
public class LayoutLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the layout to the database. Also notifies the appropriate model listeners.
	*
	* @param layout the layout
	* @return the layout that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout addLayout(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addLayout(layout);
	}

	/**
	* Creates a new layout with the primary key. Does not add the layout to the database.
	*
	* @param plid the primary key for the new layout
	* @return the new layout
	*/
	public static com.liferay.portal.model.Layout createLayout(long plid) {
		return getService().createLayout(plid);
	}

	/**
	* Deletes the layout with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param plid the primary key of the layout
	* @throws PortalException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteLayout(long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayout(plid);
	}

	/**
	* Deletes the layout from the database. Also notifies the appropriate model listeners.
	*
	* @param layout the layout
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteLayout(com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayout(layout);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the layout with the primary key.
	*
	* @param plid the primary key of the layout
	* @return the layout
	* @throws PortalException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout getLayout(long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayout(plid);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the layout with the UUID in the group.
	*
	* @param uuid the UUID of layout
	* @param groupId the group id of the layout
	* @return the layout
	* @throws PortalException if a layout with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout getLayoutByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of layouts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Layout> getLayouts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayouts(start, end);
	}

	/**
	* Returns the number of layouts.
	*
	* @return the number of layouts
	* @throws SystemException if a system exception occurred
	*/
	public static int getLayoutsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutsCount();
	}

	/**
	* Updates the layout in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layout the layout
	* @return the layout that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout updateLayout(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateLayout(layout);
	}

	/**
	* Updates the layout in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layout the layout
	* @param merge whether to merge the layout with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the layout that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Layout updateLayout(
		com.liferay.portal.model.Layout layout, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateLayout(layout, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portal.model.Layout addLayout(long userId,
		long groupId, boolean privateLayout, long parentLayoutId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap,
		java.util.Map<java.util.Locale, java.lang.String> robotsMap,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL,
		boolean locked, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addLayout(userId, groupId, privateLayout, parentLayoutId,
			nameMap, titleMap, descriptionMap, keywordsMap, robotsMap, type,
			hidden, friendlyURL, locked, serviceContext);
	}

	public static com.liferay.portal.model.Layout addLayout(long userId,
		long groupId, boolean privateLayout, long parentLayoutId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL, boolean locked,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addLayout(userId, groupId, privateLayout, parentLayoutId,
			name, title, description, type, hidden, friendlyURL, locked,
			serviceContext);
	}

	public static void deleteLayout(com.liferay.portal.model.Layout layout,
		boolean updateLayoutSet,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayout(layout, updateLayoutSet, serviceContext);
	}

	public static void deleteLayout(long plid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayout(plid, serviceContext);
	}

	public static void deleteLayout(long groupId, boolean privateLayout,
		long layoutId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteLayout(groupId, privateLayout, layoutId, serviceContext);
	}

	public static void deleteLayouts(long groupId, boolean privateLayout,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayouts(groupId, privateLayout, serviceContext);
	}

	public static byte[] exportLayouts(long groupId, boolean privateLayout,
		long[] layoutIds,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .exportLayouts(groupId, privateLayout, layoutIds,
			parameterMap, startDate, endDate);
	}

	public static byte[] exportLayouts(long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .exportLayouts(groupId, privateLayout, parameterMap,
			startDate, endDate);
	}

	public static java.io.File exportLayoutsAsFile(long groupId,
		boolean privateLayout, long[] layoutIds,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .exportLayoutsAsFile(groupId, privateLayout, layoutIds,
			parameterMap, startDate, endDate);
	}

	public static byte[] exportPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .exportPortletInfo(plid, groupId, portletId, parameterMap,
			startDate, endDate);
	}

	public static java.io.File exportPortletInfoAsFile(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .exportPortletInfoAsFile(plid, groupId, portletId,
			parameterMap, startDate, endDate);
	}

	public static com.liferay.portal.model.Layout fetchLayoutByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchLayoutByUuidAndGroupId(uuid, groupId);
	}

	public static long getDefaultPlid(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultPlid(groupId);
	}

	public static long getDefaultPlid(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultPlid(groupId, privateLayout);
	}

	public static long getDefaultPlid(long groupId, boolean privateLayout,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultPlid(groupId, privateLayout, portletId);
	}

	public static com.liferay.portal.model.Layout getFriendlyURLLayout(
		long groupId, boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFriendlyURLLayout(groupId, privateLayout, friendlyURL);
	}

	public static com.liferay.portal.model.Layout getLayout(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayout(groupId, privateLayout, layoutId);
	}

	public static com.liferay.portal.model.Layout getLayoutByIconImageId(
		long iconImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutByIconImageId(iconImageId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayouts(groupId, privateLayout);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayouts(groupId, privateLayout, parentLayoutId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean incomplete, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getLayouts(groupId, privateLayout, parentLayoutId,
			incomplete, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, long[] layoutIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayouts(groupId, privateLayout, layoutIds);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayouts(groupId, privateLayout, type);
	}

	public static com.liferay.portal.model.LayoutReference[] getLayouts(
		long companyId, java.lang.String portletId,
		java.lang.String preferencesKey, java.lang.String preferencesValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getLayouts(companyId, portletId, preferencesKey,
			preferencesValue);
	}

	public static long getNextLayoutId(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNextLayoutId(groupId, privateLayout);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getNullFriendlyURLLayouts()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNullFriendlyURLLayouts();
	}

	public static java.util.List<com.liferay.portal.model.Layout> getScopeGroupLayouts(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getScopeGroupLayouts(groupId, privateLayout);
	}

	public static boolean hasLayouts(long groupId, boolean privateLayout,
		long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasLayouts(groupId, privateLayout, parentLayoutId);
	}

	public static void importLayouts(long userId, long groupId,
		boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.importLayouts(userId, groupId, privateLayout, parameterMap, bytes);
	}

	public static void importLayouts(long userId, long groupId,
		boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.importLayouts(userId, groupId, privateLayout, parameterMap, file);
	}

	public static void importLayouts(long userId, long groupId,
		boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.importLayouts(userId, groupId, privateLayout, parameterMap, is);
	}

	public static void importPortletInfo(long userId, long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.importPortletInfo(userId, plid, groupId, portletId, parameterMap,
			file);
	}

	public static void importPortletInfo(long userId, long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.importPortletInfo(userId, plid, groupId, portletId, parameterMap,
			is);
	}

	public static void setLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, long[] layoutIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setLayouts(groupId, privateLayout, parentLayoutId, layoutIds,
			serviceContext);
	}

	public static com.liferay.portal.model.Layout updateFriendlyURL(long plid,
		java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateFriendlyURL(plid, friendlyURL);
	}

	public static com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap,
		java.util.Map<java.util.Locale, java.lang.String> robotsMap,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL,
		java.lang.Boolean iconImage, byte[] iconBytes, boolean locked,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLayout(groupId, privateLayout, layoutId,
			parentLayoutId, nameMap, titleMap, descriptionMap, keywordsMap,
			robotsMap, type, hidden, friendlyURL, iconImage, iconBytes, locked,
			serviceContext);
	}

	public static com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, java.lang.String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLayout(groupId, privateLayout, layoutId, typeSettings);
	}

	public static com.liferay.portal.model.Layout updateLookAndFeel(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String themeId, java.lang.String colorSchemeId,
		java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLookAndFeel(groupId, privateLayout, layoutId,
			themeId, colorSchemeId, css, wapTheme);
	}

	public static com.liferay.portal.model.Layout updateName(
		com.liferay.portal.model.Layout layout, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateName(layout, name, languageId);
	}

	public static com.liferay.portal.model.Layout updateName(long groupId,
		boolean privateLayout, long layoutId, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateName(groupId, privateLayout, layoutId, name,
			languageId);
	}

	public static com.liferay.portal.model.Layout updateName(long plid,
		java.lang.String name, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateName(plid, name, languageId);
	}

	public static com.liferay.portal.model.Layout updateParentLayoutId(
		long groupId, boolean privateLayout, long layoutId, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateParentLayoutId(groupId, privateLayout, layoutId,
			parentLayoutId);
	}

	public static com.liferay.portal.model.Layout updateParentLayoutId(
		long plid, long parentPlid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateParentLayoutId(plid, parentPlid);
	}

	public static com.liferay.portal.model.Layout updatePriority(
		com.liferay.portal.model.Layout layout, int priority)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePriority(layout, priority);
	}

	public static com.liferay.portal.model.Layout updatePriority(long groupId,
		boolean privateLayout, long layoutId, int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePriority(groupId, privateLayout, layoutId, priority);
	}

	public static com.liferay.portal.model.Layout updatePriority(long plid,
		int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePriority(plid, priority);
	}

	/**
	* @see com.liferay.portlet.portletconfiguration.action.EditScopeAction#updateScope
	*/
	public static void updateScopedPortletNames(long groupId,
		boolean privateLayout, long layoutId, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateScopedPortletNames(groupId, privateLayout, layoutId, name,
			languageId);
	}

	public static LayoutLocalService getService() {
		if (_service == null) {
			_service = (LayoutLocalService)PortalBeanLocatorUtil.locate(LayoutLocalService.class.getName());

			ReferenceRegistry.registerReference(LayoutLocalServiceUtil.class,
				"_service");
			MethodCache.remove(LayoutLocalService.class);
		}

		return _service;
	}

	public void setService(LayoutLocalService service) {
		MethodCache.remove(LayoutLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(LayoutLocalServiceUtil.class,
			"_service");
		MethodCache.remove(LayoutLocalService.class);
	}

	private static LayoutLocalService _service;
}