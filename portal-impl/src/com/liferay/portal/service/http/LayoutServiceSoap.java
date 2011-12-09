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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.LayoutServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portal.service.LayoutServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.model.LayoutSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.model.Layout}, that is translated to a
 * {@link com.liferay.portal.model.LayoutSoap}. Methods that SOAP cannot
 * safely wire are skipped.
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
 * You can see a list of services at
 * http://localhost:8080/api/secure/axis. Set the property
 * <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutServiceHttp
 * @see       com.liferay.portal.model.LayoutSoap
 * @see       com.liferay.portal.service.LayoutServiceUtil
 * @generated
 */
public class LayoutServiceSoap {
	/**
	* Adds a layout with empty maps for descriptions, keywords, and titles ,
	* and a names map containing a mapping for the default locale as its only
	* entry.
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
	* @param parentLayoutId the primary key of the parent layout (optionally
	{@link
	com.liferay.portal.model.LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	* @param name Map the layout's locales and localized names
	* @param title Map the layout's locales and localized titles
	* @param description Map the layout's locales and localized descriptions
	* @param type the layout's type (optionally {@link
	com.liferay.portal.model.LayoutConstants#TYPE_PORTLET}). The
	possible types can be found in {@link
	com.liferay.portal.model.LayoutConstants}.
	* @param hidden whether the layout is hidden
	* @param friendlyURL the layout's friendly URL (optionally {@link
	com.liferay.portal.util.PropsValues#DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL}
	or {@link
	com.liferay.portal.util.PropsValues#DEFAULT_USER_PUBLIC_LAYOUT_FRIENDLY_URL}).
	The default values can be overridden in
	<code>portal-ext.properties</code> by specifying new values for
	the corresponding properties defined in {@link
	com.liferay.portal.util.PropsValues}. To see how the URL is
	normalized when accessed see {@link
	com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	String)}.
	* @param serviceContext the service context. Must specify the replacement
	universally unique identifier and can specify the replacement
	create date, replacement modified date and the new expando bridge
	attributes. For layouts that belong to a layout set prototype, an
	attribute named 'layoutUpdateable' can be used to specify whether
	site administrators can modify this page within their site.
	* @return the layout
	* @throws PortalException if a group with the primary key could not be
	found, if the group did not have permission to manage the layouts
	involved, or if layout values were invalid
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSoap addLayout(long groupId,
		boolean privateLayout, long parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.addLayout(groupId,
					privateLayout, parentLayoutId, name, title, description,
					type, hidden, friendlyURL, serviceContext);

			return com.liferay.portal.model.LayoutSoap.toSoapModel(returnValue);
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
	* @param serviceContext the service context
	* @throws PortalException if the user did not have permission to delete the
	layout, if a layout with the primary key could not be found , or
	if some other portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteLayout(long plid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			LayoutServiceUtil.deleteLayout(plid, serviceContext);
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
	* @param layoutId the primary key of the layout
	* @param serviceContext the service context
	* @throws PortalException if the user did not have permission to delete the
	layout, if a matching layout could not be found , or if some
	other portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteLayout(long groupId, boolean privateLayout,
		long layoutId, com.liferay.portal.service.ServiceContext serviceContext)
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
	* Returns the primary key of the default layout for the group.
	*
	* @param groupId the primary key of the group
	* @param scopeGroupId the primary key of the scope group. See {@link
	ServiceContext#getScopeGroupId()}.
	* @param privateLayout whether the layout is private to the group
	* @param portletId the primary key of the portlet
	* @return Returns the primary key of the default layout group; {@link
	com.liferay.portal.model.LayoutConstants#DEFAULT_PLID} otherwise
	* @throws PortalException if a group, layout, or portlet with the primary
	key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static long getDefaultPlid(long groupId, long scopeGroupId,
		boolean privateLayout, java.lang.String portletId)
		throws RemoteException {
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

	/**
	* Returns the name of the layout.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param languageId the primary key of the language. For more information
	See {@link java.util.Locale}.
	* @return the layout's name
	* @throws PortalException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static java.lang.String getLayoutName(long groupId,
		boolean privateLayout, long layoutId, java.lang.String languageId)
		throws RemoteException {
		try {
			java.lang.String returnValue = LayoutServiceUtil.getLayoutName(groupId,
					privateLayout, layoutId, languageId);

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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutReference[] getLayoutReferences(
		long companyId, java.lang.String portletId,
		java.lang.String preferencesKey, java.lang.String preferencesValue)
		throws RemoteException {
		try {
			com.liferay.portal.model.LayoutReference[] returnValue = LayoutServiceUtil.getLayoutReferences(companyId,
					portletId, preferencesKey, preferencesValue);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.LayoutSoap[] getLayouts(
		long groupId, boolean privateLayout) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Layout> returnValue = LayoutServiceUtil.getLayouts(groupId,
					privateLayout);

			return com.liferay.portal.model.LayoutSoap.toSoapModels(returnValue);
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
	* @param parentLayoutId the primary key of the parent layout
	* @param layoutIds the primary keys of the layouts
	* @param serviceContext the service context
	* @throws PortalException if a group or layout with the primary key could
	not be found, if the group did not have permission to manage the
	layouts, if no layouts were specified, if the first layout was
	not page-able, if the first layout was hidden, or if some other
	portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void setLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, long[] layoutIds,
		com.liferay.portal.service.ServiceContext serviceContext)
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
	com.liferay.portal.kernel.messaging.DestinationNames#LAYOUTS_LOCAL_PUBLISHER}).
	See {@link com.liferay.portal.kernel.messaging.DestinationNames}.
	* @throws PortalException if the group did not permission to manage staging
	and publish
	* @throws SystemException if a system exception occurred
	*/
	public static void unschedulePublishToLive(long groupId,
		java.lang.String jobName, java.lang.String groupName)
		throws RemoteException {
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
	com.liferay.portal.kernel.messaging.DestinationNames#LAYOUTS_LOCAL_PUBLISHER}).
	See {@link com.liferay.portal.kernel.messaging.DestinationNames}.
	* @throws PortalException if a group with the primary key could not be
	found or if the group did not have permission to publish
	* @throws SystemException if a system exception occurred
	*/
	public static void unschedulePublishToRemote(long groupId,
		java.lang.String jobName, java.lang.String groupName)
		throws RemoteException {
		try {
			LayoutServiceUtil.unschedulePublishToRemote(groupId, jobName,
				groupName);
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
	* @param layoutId the primary key of the layout
	* @param typeSettings the settings to load the unicode properties object.
	See {@link com.liferay.portal.kernel.util.UnicodeProperties
	#fastLoad(String)}.
	* @return the updated layout
	* @throws PortalException if a matching layout could not be found or if the
	user did not have permission to update the layout
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSoap updateLayout(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String typeSettings) throws RemoteException {
		try {
			com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updateLayout(groupId,
					privateLayout, layoutId, typeSettings);

			return com.liferay.portal.model.LayoutSoap.toSoapModel(returnValue);
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
	* @param layoutId the primary key of the layout
	* @param themeId the primary key of the layout's new theme
	* @param colorSchemeId the primary key of the layout's new color scheme
	* @param css the layout's new CSS
	* @param wapTheme whether the theme is for WAP browsers
	* @return the updated layout
	* @throws PortalException if a matching layout could not be found, or if
	the user did not have permission to update the layout and
	permission to apply the theme
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSoap updateLookAndFeel(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String themeId, java.lang.String colorSchemeId,
		java.lang.String css, boolean wapTheme) throws RemoteException {
		try {
			com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updateLookAndFeel(groupId,
					privateLayout, layoutId, themeId, colorSchemeId, css,
					wapTheme);

			return com.liferay.portal.model.LayoutSoap.toSoapModel(returnValue);
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
	* @param layoutId the primary key of the layout
	* @param name the layout's new name
	* @param languageId the primary key of the language. For more information
	see {@link java.util.Locale}.
	* @return the updated layout
	* @throws PortalException if a matching layout could not be found, if the
	user did not have permission to update the layout, or if the new
	name was <code>null</code>
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSoap updateName(long groupId,
		boolean privateLayout, long layoutId, java.lang.String name,
		java.lang.String languageId) throws RemoteException {
		try {
			com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updateName(groupId,
					privateLayout, layoutId, name, languageId);

			return com.liferay.portal.model.LayoutSoap.toSoapModel(returnValue);
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
	see {@link java.util.Locale}.
	* @return the updated layout
	* @throws PortalException if a layout with the primary key could not be
	found, or if the user did not have permission to update the
	layout, or if the name was <code>null</code>
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSoap updateName(long plid,
		java.lang.String name, java.lang.String languageId)
		throws RemoteException {
		try {
			com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updateName(plid,
					name, languageId);

			return com.liferay.portal.model.LayoutSoap.toSoapModel(returnValue);
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
	* @param layoutId the primary key of the layout
	* @param parentLayoutId the primary key to be assigned to the parent
	layout
	* @return the matching layout
	* @throws PortalException if a valid parent layout ID to use could not be
	found, if a matching layout could not be found, or if the user
	did not have permission to update the layout
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSoap updateParentLayoutId(
		long groupId, boolean privateLayout, long layoutId, long parentLayoutId)
		throws RemoteException {
		try {
			com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updateParentLayoutId(groupId,
					privateLayout, layoutId, parentLayoutId);

			return com.liferay.portal.model.LayoutSoap.toSoapModel(returnValue);
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
	* com.liferay.portal.model.LayoutConstants#DEFAULT_PARENT_LAYOUT_ID} is
	* assigned.
	*
	* @param plid the primary key of the layout
	* @param parentPlid the primary key of the parent layout
	* @return the layout matching the primary key
	* @throws PortalException if a layout with the primary key could not be
	found, if the user did not have permission to update the layout,
	or if a valid parent layout ID to use could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSoap updateParentLayoutId(
		long plid, long parentPlid) throws RemoteException {
		try {
			com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updateParentLayoutId(plid,
					parentPlid);

			return com.liferay.portal.model.LayoutSoap.toSoapModel(returnValue);
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
	* @param layoutId the primary key of the layout
	* @param priority the layout's new priority
	* @return the updated layout
	* @throws PortalException if a matching layout could not be found or if the
	user did not have permission to update the layout
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSoap updatePriority(
		long groupId, boolean privateLayout, long layoutId, int priority)
		throws RemoteException {
		try {
			com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updatePriority(groupId,
					privateLayout, layoutId, priority);

			return com.liferay.portal.model.LayoutSoap.toSoapModel(returnValue);
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
	* @throws PortalException if a layout with the primary key could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSoap updatePriority(
		long plid, int priority) throws RemoteException {
		try {
			com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updatePriority(plid,
					priority);

			return com.liferay.portal.model.LayoutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutServiceSoap.class);
}