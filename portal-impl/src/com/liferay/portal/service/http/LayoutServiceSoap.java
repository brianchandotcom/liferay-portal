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

package com.liferay.portal.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>LayoutServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.kernel.model.LayoutSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.kernel.model.Layout</code>, that is translated to a
 * <code>com.liferay.portal.kernel.model.LayoutSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutServiceHttp
 * @generated
 */
@ProviderType
public class LayoutServiceSoap {
	/**
	* Adds a layout with additional parameters.
	*
	* <p>
	* This method handles the creation of the layout including its resources,
	* metadata, and internal data structures. It is not necessary to make
	* subsequent calls to any methods to setup default groups, resources, ...
	* etc.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parentLayoutId the layout ID of the parent layout (optionally
	{@link LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	* @param localeNamesMap the layout's locales and localized names
	* @param localeTitlesMap the layout's locales and localized titles
	* @param descriptionMap the layout's locales and localized descriptions
	* @param keywordsMap the layout's locales and localized keywords
	* @param robotsMap the layout's locales and localized robots
	* @param type the layout's type (optionally {@link
	LayoutConstants#TYPE_PORTLET}). The possible types can be found
	in {@link LayoutConstants}.
	* @param typeSettings the settings to load the unicode properties object.
	See {@link com.liferay.portal.kernel.util.UnicodeProperties
	#fastLoad(String)}.
	* @param hidden whether the layout is hidden
	* @param friendlyURLMap the layout's locales and localized friendly URLs.
	To see how the URL is normalized when accessed, see {@link
	com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	String)}.
	* @param serviceContext the service context to be applied. Must set the
	UUID for the layout. Can set the creation date, modification
	date, and expando bridge attributes for the layout. For layouts
	that belong to a layout set prototype, an attribute named
	<code>layoutUpdateable</code> can be used to specify whether site
	administrators can modify this page within their site.
	* @return the layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.LayoutSoap addLayout(
		long groupId, boolean privateLayout, long parentLayoutId,
		String[] localeNamesMapLanguageIds, String[] localeNamesMapValues,
		String[] localeTitlesMapLanguageIds, String[] localeTitlesMapValues,
		String[] descriptionMapLanguageIds, String[] descriptionMapValues,
		String[] keywordsMapLanguageIds, String[] keywordsMapValues,
		String[] robotsMapLanguageIds, String[] robotsMapValues, String type,
		String typeSettings, boolean hidden,
		String[] friendlyURLMapLanguageIds, String[] friendlyURLMapValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> localeNamesMap = LocalizationUtil.getLocalizationMap(localeNamesMapLanguageIds,
					localeNamesMapValues);
			Map<Locale, String> localeTitlesMap = LocalizationUtil.getLocalizationMap(localeTitlesMapLanguageIds,
					localeTitlesMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);
			Map<Locale, String> keywordsMap = LocalizationUtil.getLocalizationMap(keywordsMapLanguageIds,
					keywordsMapValues);
			Map<Locale, String> robotsMap = LocalizationUtil.getLocalizationMap(robotsMapLanguageIds,
					robotsMapValues);
			Map<Locale, String> friendlyURLMap = LocalizationUtil.getLocalizationMap(friendlyURLMapLanguageIds,
					friendlyURLMapValues);

			com.liferay.portal.kernel.model.Layout returnValue = LayoutServiceUtil.addLayout(groupId,
					privateLayout, parentLayoutId, localeNamesMap,
					localeTitlesMap, descriptionMap, keywordsMap, robotsMap,
					type, typeSettings, hidden, friendlyURLMap, serviceContext);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Adds a layout with single entry maps for name, title, and description to
	* the default locale.
	*
	* <p>
	* This method handles the creation of the layout including its resources,
	* metadata, and internal data structures. It is not necessary to make
	* subsequent calls to any methods to setup default groups, resources, ...
	* etc.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parentLayoutId the layout ID of the parent layout (optionally
	{@link LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	* @param name the layout's locales and localized names
	* @param title the layout's locales and localized titles
	* @param description the layout's locales and localized descriptions
	* @param type the layout's type (optionally {@link
	LayoutConstants#TYPE_PORTLET}). The possible types can be found
	in {@link LayoutConstants}.
	* @param hidden whether the layout is hidden
	* @param friendlyURL the layout's locales and localized friendly URLs. To
	see how the URL is normalized when accessed, see {@link
	com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	String)}.
	* @param serviceContext the service context to be applied. Must set the
	UUID for the layout. Can specify the creation date, modification
	date, and expando bridge attributes for the layout. For layouts
	that belong to a layout set prototype, an attribute named
	<code>layoutUpdateable</code> can be used to specify whether site
	administrators can modify this page within their site.
	* @return the layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.LayoutSoap addLayout(
		long groupId, boolean privateLayout, long parentLayoutId, String name,
		String title, String description, String type, boolean hidden,
		String friendlyURL,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Layout returnValue = LayoutServiceUtil.addLayout(groupId,
					privateLayout, parentLayoutId, name, title, description,
					type, hidden, friendlyURL, serviceContext);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes the layout with the primary key, also deleting the layout's child
	* layouts, and associated resources.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the layout ID of the layout
	* @param serviceContext the service context to be applied
	* @throws PortalException if a portal exception occurred
	*/
	public static void deleteLayout(long groupId, boolean privateLayout,
		long layoutId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			LayoutServiceUtil.deleteLayout(groupId, privateLayout, layoutId,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes the layout with the plid, also deleting the layout's child
	* layouts, and associated resources.
	*
	* @param plid the primary key of the layout
	* @param serviceContext the service context to be applied
	* @throws PortalException if a portal exception occurred
	*/
	public static void deleteLayout(long plid,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			LayoutServiceUtil.deleteLayout(plid, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteTempFileEntry(long groupId, String folderName,
		String fileName) throws RemoteException {
		try {
			LayoutServiceUtil.deleteTempFileEntry(groupId, folderName, fileName);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#exportLayoutsAsFileInBackground(
	ExportImportConfiguration)}
	*/
	@Deprecated
	public static long exportLayoutsAsFileInBackground(
		com.liferay.exportimport.kernel.model.ExportImportConfigurationSoap exportImportConfiguration)
		throws RemoteException {
		try {
			long returnValue = LayoutServiceUtil.exportLayoutsAsFileInBackground(com.liferay.portlet.exportimport.model.impl.ExportImportConfigurationModelImpl.toModel(
						exportImportConfiguration));

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#exportLayoutsAsFileInBackground(
	long)}
	*/
	@Deprecated
	public static long exportLayoutsAsFileInBackground(
		long exportImportConfigurationId) throws RemoteException {
		try {
			long returnValue = LayoutServiceUtil.exportLayoutsAsFileInBackground(exportImportConfigurationId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns all the ancestor layouts of the layout.
	*
	* @param plid the primary key of the layout
	* @return the ancestor layouts of the layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.LayoutSoap[] getAncestorLayouts(
		long plid) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.model.Layout> returnValue = LayoutServiceUtil.getAncestorLayouts(plid);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the control panel layout's plid.
	*
	* @return the control panel layout's plid
	* @throws PortalException if a portal exception is occured
	*/
	public static long getControlPanelLayoutPlid() throws RemoteException {
		try {
			long returnValue = LayoutServiceUtil.getControlPanelLayoutPlid();

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns primary key of the matching default layout for the group.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return the primary key of the default layout for the group; {@link
	LayoutConstants#DEFAULT_PLID}) otherwise
	*/
	public static long getDefaultPlid(long groupId, boolean privateLayout)
		throws RemoteException {
		try {
			long returnValue = LayoutServiceUtil.getDefaultPlid(groupId,
					privateLayout);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the primary key of the default layout for the group.
	*
	* @param groupId the primary key of the group
	* @param scopeGroupId the primary key of the scope group. See {@link
	ServiceContext#getScopeGroupId()}.
	* @param privateLayout whether the layout is private to the group
	* @param portletId the primary key of the portlet
	* @return Returns the primary key of the default layout group; {@link
	LayoutConstants#DEFAULT_PLID} otherwise
	* @throws PortalException if a portal exception occurred
	*/
	public static long getDefaultPlid(long groupId, long scopeGroupId,
		boolean privateLayout, String portletId) throws RemoteException {
		try {
			long returnValue = LayoutServiceUtil.getDefaultPlid(groupId,
					scopeGroupId, privateLayout, portletId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long getDefaultPlid(long groupId, long scopeGroupId,
		String portletId) throws RemoteException {
		try {
			long returnValue = LayoutServiceUtil.getDefaultPlid(groupId,
					scopeGroupId, portletId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the layout matching the UUID, group, and privacy.
	*
	* @param uuid the layout's UUID
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return the matching layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.LayoutSoap getLayoutByUuidAndGroupId(
		String uuid, long groupId, boolean privateLayout)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Layout returnValue = LayoutServiceUtil.getLayoutByUuidAndGroupId(uuid,
					groupId, privateLayout);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the name of the layout.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the layout ID of the layout
	* @param languageId the primary key of the language. For more information
	See {@link Locale}.
	* @return the layout's name
	* @throws PortalException if a portal exception occurred
	*/
	public static String getLayoutName(long groupId, boolean privateLayout,
		long layoutId, String languageId) throws RemoteException {
		try {
			String returnValue = LayoutServiceUtil.getLayoutName(groupId,
					privateLayout, layoutId, languageId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the layout's plid that matches the parameters.
	*
	* @param uuid the layout's UUID
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return the matching layout's plid
	* @throws PortalException if a portal exception occurred
	*/
	public static long getLayoutPlid(String uuid, long groupId,
		boolean privateLayout) throws RemoteException {
		try {
			long returnValue = LayoutServiceUtil.getLayoutPlid(uuid, groupId,
					privateLayout);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the layout references for all the layouts that belong to the
	* company and belong to the portlet that matches the preferences.
	*
	* @param companyId the primary key of the company
	* @param portletId the primary key of the portlet
	* @param preferencesKey the portlet's preference key
	* @param preferencesValue the portlet's preference value
	* @return the layout references of the matching layouts
	*/
	public static com.liferay.portal.kernel.model.LayoutReference[] getLayoutReferences(
		long companyId, String portletId, String preferencesKey,
		String preferencesValue) throws RemoteException {
		try {
			com.liferay.portal.kernel.model.LayoutReference[] returnValue = LayoutServiceUtil.getLayoutReferences(companyId,
					portletId, preferencesKey, preferencesValue);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.LayoutSoap[] getLayouts(
		long groupId, boolean privateLayout) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.model.Layout> returnValue = LayoutServiceUtil.getLayouts(groupId,
					privateLayout);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.LayoutSoap[] getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.model.Layout> returnValue = LayoutServiceUtil.getLayouts(groupId,
					privateLayout, parentLayoutId);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.LayoutSoap[] getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean incomplete, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.model.Layout> returnValue = LayoutServiceUtil.getLayouts(groupId,
					privateLayout, parentLayoutId, incomplete, start, end);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.LayoutSoap[] getLayouts(
		long groupId, String type) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.model.Layout> returnValue = LayoutServiceUtil.getLayouts(groupId,
					type);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.LayoutSoap[] getLayouts(
		long groupId, String type, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.model.Layout> returnValue = LayoutServiceUtil.getLayouts(groupId,
					type, start, end);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutsCount(long groupId, boolean privateLayout)
		throws RemoteException {
		try {
			int returnValue = LayoutServiceUtil.getLayoutsCount(groupId,
					privateLayout);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutsCount(long groupId, boolean privateLayout,
		long parentLayoutId) throws RemoteException {
		try {
			int returnValue = LayoutServiceUtil.getLayoutsCount(groupId,
					privateLayout, parentLayoutId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutsCount(long groupId, boolean privateLayout,
		long parentLayoutId, int priority) throws RemoteException {
		try {
			int returnValue = LayoutServiceUtil.getLayoutsCount(groupId,
					privateLayout, parentLayoutId, priority);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutsCount(long groupId, String type)
		throws RemoteException {
		try {
			int returnValue = LayoutServiceUtil.getLayoutsCount(groupId, type);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String[] getTempFileNames(long groupId, String folderName)
		throws RemoteException {
		try {
			String[] returnValue = LayoutServiceUtil.getTempFileNames(groupId,
					folderName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns <code>true</code> if there is a matching layout with the UUID,
	* group, and privacy.
	*
	* @param uuid the layout's UUID
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return <code>true</code> if the layout is found; <code>false</code>
	otherwise
	* @throws PortalException if a portal exception occurred
	*/
	public static boolean hasLayout(String uuid, long groupId,
		boolean privateLayout) throws RemoteException {
		try {
			boolean returnValue = LayoutServiceUtil.hasLayout(uuid, groupId,
					privateLayout);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasPortletId(long plid, String portletId)
		throws RemoteException {
		try {
			boolean returnValue = LayoutServiceUtil.hasPortletId(plid, portletId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Sets the layouts for the group, replacing and prioritizing all layouts of
	* the parent layout.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parentLayoutId the layout ID of the parent layout
	* @param layoutIds the primary keys of the layouts
	* @param serviceContext the service context to be applied
	* @throws PortalException if a portal exception occurred
	*/
	public static void setLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, long[] layoutIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			LayoutServiceUtil.setLayouts(groupId, privateLayout,
				parentLayoutId, layoutIds, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes the job from the scheduler's queue.
	*
	* @param groupId the primary key of the group
	* @param jobName the job name
	* @param groupName the group name (optionally {@link
	DestinationNames#LAYOUTS_LOCAL_PUBLISHER}). See {@link
	DestinationNames}.
	* @throws PortalException if a portal exception occurred
	*/
	public static void unschedulePublishToLive(long groupId, String jobName,
		String groupName) throws RemoteException {
		try {
			LayoutServiceUtil.unschedulePublishToLive(groupId, jobName,
				groupName);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes the job from the scheduler's persistent queue.
	*
	* @param groupId the primary key of the group
	* @param jobName the job name
	* @param groupName the group name (optionally {@link
	DestinationNames#LAYOUTS_LOCAL_PUBLISHER}). See {@link
	DestinationNames}.
	* @throws PortalException if a portal exception occurred
	*/
	public static void unschedulePublishToRemote(long groupId, String jobName,
		String groupName) throws RemoteException {
		try {
			LayoutServiceUtil.unschedulePublishToRemote(groupId, jobName,
				groupName);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.LayoutSoap updateIconImage(
		long plid, byte[] bytes) throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Layout returnValue = LayoutServiceUtil.updateIconImage(plid,
					bytes);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the layout with additional parameters.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the layout ID of the layout
	* @param parentLayoutId the layout ID of the layout's new parent layout
	* @param localeNamesMap the layout's locales and localized names
	* @param localeTitlesMap the layout's locales and localized titles
	* @param descriptionMap the locales and localized descriptions to merge
	(optionally <code>null</code>)
	* @param keywordsMap the locales and localized keywords to merge
	(optionally <code>null</code>)
	* @param robotsMap the locales and localized robots to merge (optionally
	<code>null</code>)
	* @param type the layout's new type (optionally {@link
	LayoutConstants#TYPE_PORTLET})
	* @param hidden whether the layout is hidden
	* @param friendlyURLMap the layout's locales and localized friendly URLs.
	To see how the URL is normalized when accessed see {@link
	com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	String)}.
	* @param iconImage whether the icon image will be updated
	* @param iconBytes the byte array of the layout's new icon image
	* @param serviceContext the service context to be applied. Can set the
	modification date and expando bridge attributes for the layout.
	* @return the updated layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.LayoutSoap updateLayout(
		long groupId, boolean privateLayout, long layoutId,
		long parentLayoutId, String[] localeNamesMapLanguageIds,
		String[] localeNamesMapValues, String[] localeTitlesMapLanguageIds,
		String[] localeTitlesMapValues, String[] descriptionMapLanguageIds,
		String[] descriptionMapValues, String[] keywordsMapLanguageIds,
		String[] keywordsMapValues, String[] robotsMapLanguageIds,
		String[] robotsMapValues, String type, boolean hidden,
		String[] friendlyURLMapLanguageIds, String[] friendlyURLMapValues,
		boolean iconImage, byte[] iconBytes,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> localeNamesMap = LocalizationUtil.getLocalizationMap(localeNamesMapLanguageIds,
					localeNamesMapValues);
			Map<Locale, String> localeTitlesMap = LocalizationUtil.getLocalizationMap(localeTitlesMapLanguageIds,
					localeTitlesMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);
			Map<Locale, String> keywordsMap = LocalizationUtil.getLocalizationMap(keywordsMapLanguageIds,
					keywordsMapValues);
			Map<Locale, String> robotsMap = LocalizationUtil.getLocalizationMap(robotsMapLanguageIds,
					robotsMapValues);
			Map<Locale, String> friendlyURLMap = LocalizationUtil.getLocalizationMap(friendlyURLMapLanguageIds,
					friendlyURLMapValues);

			com.liferay.portal.kernel.model.Layout returnValue = LayoutServiceUtil.updateLayout(groupId,
					privateLayout, layoutId, parentLayoutId, localeNamesMap,
					localeTitlesMap, descriptionMap, keywordsMap, robotsMap,
					type, hidden, friendlyURLMap, iconImage, iconBytes,
					serviceContext);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the layout replacing its type settings.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the layout ID of the layout
	* @param typeSettings the settings to load the unicode properties object.
	See {@link com.liferay.portal.kernel.util.UnicodeProperties
	#fastLoad(String)}.
	* @return the updated layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.LayoutSoap updateLayout(
		long groupId, boolean privateLayout, long layoutId, String typeSettings)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Layout returnValue = LayoutServiceUtil.updateLayout(groupId,
					privateLayout, layoutId, typeSettings);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the look and feel of the layout.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the layout ID of the layout
	* @param themeId the primary key of the layout's new theme
	* @param colorSchemeId the primary key of the layout's new color scheme
	* @param css the layout's new CSS
	* @return the updated layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.LayoutSoap updateLookAndFeel(
		long groupId, boolean privateLayout, long layoutId, String themeId,
		String colorSchemeId, String css) throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Layout returnValue = LayoutServiceUtil.updateLookAndFeel(groupId,
					privateLayout, layoutId, themeId, colorSchemeId, css);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the name of the layout matching the group, layout ID, and
	* privacy.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the layout ID of the layout
	* @param name the layout's new name
	* @param languageId the primary key of the language. For more information
	see {@link Locale}.
	* @return the updated layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.LayoutSoap updateName(
		long groupId, boolean privateLayout, long layoutId, String name,
		String languageId) throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Layout returnValue = LayoutServiceUtil.updateName(groupId,
					privateLayout, layoutId, name, languageId);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the name of the layout matching the primary key.
	*
	* @param plid the primary key of the layout
	* @param name the name to be assigned
	* @param languageId the primary key of the language. For more information
	see {@link Locale}.
	* @return the updated layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.LayoutSoap updateName(
		long plid, String name, String languageId) throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Layout returnValue = LayoutServiceUtil.updateName(plid,
					name, languageId);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the parent layout ID of the layout matching the group, layout ID,
	* and privacy.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the layout ID of the layout
	* @param parentLayoutId the layout ID to be assigned to the parent
	layout
	* @return the matching layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.LayoutSoap updateParentLayoutId(
		long groupId, boolean privateLayout, long layoutId, long parentLayoutId)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Layout returnValue = LayoutServiceUtil.updateParentLayoutId(groupId,
					privateLayout, layoutId, parentLayoutId);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the parent layout ID of the layout matching the primary key. If a
	* layout matching the parent primary key is found, the layout ID of that
	* layout is assigned, otherwise {@link
	* LayoutConstants#DEFAULT_PARENT_LAYOUT_ID} is assigned.
	*
	* @param plid the primary key of the layout
	* @param parentPlid the primary key of the parent layout
	* @return the layout matching the primary key
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.LayoutSoap updateParentLayoutId(
		long plid, long parentPlid) throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Layout returnValue = LayoutServiceUtil.updateParentLayoutId(plid,
					parentPlid);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the parent layout ID and priority of the layout.
	*
	* @param plid the primary key of the layout
	* @param parentPlid the primary key of the parent layout
	* @param priority the layout's new priority
	* @return the layout matching the primary key
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.LayoutSoap updateParentLayoutIdAndPriority(
		long plid, long parentPlid, int priority) throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Layout returnValue = LayoutServiceUtil.updateParentLayoutIdAndPriority(plid,
					parentPlid, priority);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the priority of the layout matching the group, layout ID, and
	* privacy.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the layout ID of the layout
	* @param priority the layout's new priority
	* @return the updated layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.LayoutSoap updatePriority(
		long groupId, boolean privateLayout, long layoutId, int priority)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Layout returnValue = LayoutServiceUtil.updatePriority(groupId,
					privateLayout, layoutId, priority);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the priority of the layout matching the group, layout ID, and
	* privacy, setting the layout's priority based on the priorities of the
	* next and previous layouts.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the layout ID of the layout
	* @param nextLayoutId the layout ID of the next layout
	* @param previousLayoutId the layout ID of the previous layout
	* @return the updated layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.LayoutSoap updatePriority(
		long groupId, boolean privateLayout, long layoutId, long nextLayoutId,
		long previousLayoutId) throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Layout returnValue = LayoutServiceUtil.updatePriority(groupId,
					privateLayout, layoutId, nextLayoutId, previousLayoutId);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the priority of the layout matching the primary key.
	*
	* @param plid the primary key of the layout
	* @param priority the layout's new priority
	* @return the updated layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.LayoutSoap updatePriority(
		long plid, int priority) throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Layout returnValue = LayoutServiceUtil.updatePriority(plid,
					priority);

			return com.liferay.portal.kernel.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutServiceSoap.class);
}