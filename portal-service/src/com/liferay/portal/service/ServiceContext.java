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

package com.liferay.portal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Contains context information about a given API call.
 *
 * <p>
 * The <code>ServiceContext</code> object simplifies method signatures and
 * provides a way to consolidate many different methods with different sets of
 * optional parameters into a single, easier to use method. It also aggregates
 * information necessary for transversal features such as permissioning,
 * tagging, categorization, etc.
 * </p>
 *
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class ServiceContext implements Cloneable, Serializable {

	/**
	 * Creates a new service context object with an attributes map and an
	 * expando bridge attributes map. The attributes map contains standard
	 * service context parameters and the expando bridge attributes map contains
	 * optional service context parameters.
	 */
	public ServiceContext() {
		_attributes = new LinkedHashMap<String, Serializable>();
		_expandoBridgeAttributes = new LinkedHashMap<String, Serializable>();
	}

	/**
	 * Returns a new service context object identical to this service context
	 * object.
	 *
	 * @return a new service context object
	 */
	@Override
	public Object clone() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(isAddGroupPermissions());
		serviceContext.setAddGuestPermissions(isAddGuestPermissions());
		serviceContext.setAssetCategoryIds(getAssetCategoryIds());
		serviceContext.setAssetEntryVisible(isAssetEntryVisible());
		serviceContext.setAssetLinkEntryIds(getAssetLinkEntryIds());
		serviceContext.setAssetTagNames(getAssetTagNames());
		serviceContext.setAttributes(getAttributes());
		serviceContext.setCommand(getCommand());
		serviceContext.setCompanyId(getCompanyId());
		serviceContext.setCreateDate(getCreateDate());
		serviceContext.setCurrentURL(getCurrentURL());
		serviceContext.setExpandoBridgeAttributes(getExpandoBridgeAttributes());
		serviceContext.setFailOnPortalException(isFailOnPortalException());
		serviceContext.setGroupPermissions(getGroupPermissions());
		serviceContext.setGuestPermissions(getGuestPermissions());
		serviceContext.setHeaders(getHeaders());
		serviceContext.setIndexingEnabled(isIndexingEnabled());
		serviceContext.setLanguageId(getLanguageId());
		serviceContext.setLayoutFullURL(getLayoutFullURL());
		serviceContext.setLayoutURL(getLayoutURL());
		serviceContext.setModifiedDate(getModifiedDate());
		serviceContext.setPathFriendlyURLPrivateGroup(
			getPathFriendlyURLPrivateGroup());
		serviceContext.setPathFriendlyURLPrivateUser(
			getPathFriendlyURLPrivateUser());
		serviceContext.setPathFriendlyURLPublic(getPathFriendlyURLPublic());
		serviceContext.setPathMain(getPathMain());
		serviceContext.setPlid(getPlid());
		serviceContext.setPortalURL(getPortalURL());
		serviceContext.setPortletPreferencesIds(getPortletPreferencesIds());
		serviceContext.setRemoteAddr(getRemoteAddr());
		serviceContext.setRemoteHost(getRemoteHost());
		serviceContext.setRequest(getRequest());
		serviceContext.setScopeGroupId(getScopeGroupId());
		serviceContext.setSignedIn(isSignedIn());
		serviceContext.setUserDisplayURL(getUserDisplayURL());
		serviceContext.setUserId(getUserId());
		serviceContext.setUuid(getUuid());
		serviceContext.setWorkflowAction(getWorkflowAction());

		return serviceContext;
	}

	/**
	 * Derive default permissions based on the logic found in
	 * portal-web/docroot/html/taglib/ui/input_permissions/page.jsp. Do not
	 * update this logic updating the logic in the JSP.
	 */
	public void deriveDefaultPermissions(long repositoryId, String modelName)
		throws PortalException, SystemException {

		long siteGroupId = PortalUtil.getSiteGroupId(repositoryId);

		Group siteGroup = GroupLocalServiceUtil.getGroup(siteGroupId);

		Role defaultGroupRole = RoleLocalServiceUtil.getDefaultGroupRole(
			siteGroupId);

		List<String> groupPermissions = new ArrayList<String>();
		List<String> guestPermissions = new ArrayList<String>();

		String[] roleNames = {RoleConstants.GUEST, defaultGroupRole.getName()};

		List<String> supportedActions =
			ResourceActionsUtil.getModelResourceActions(modelName);
		List<String> groupDefaultActions =
			ResourceActionsUtil.getModelResourceGroupDefaultActions(modelName);
		List<String> guestDefaultActions =
			ResourceActionsUtil.getModelResourceGuestDefaultActions(modelName);
		List<String> guestUnsupportedActions =
			ResourceActionsUtil.getModelResourceGuestUnsupportedActions(
				modelName);

		for (String roleName : roleNames) {
			for (String action : supportedActions) {
				if (roleName.equals(RoleConstants.GUEST) &&
					!guestUnsupportedActions.contains(action) &&
					guestDefaultActions.contains(action) &&
					siteGroup.hasPublicLayouts()) {

					guestPermissions.add(action);
				}
				else if (roleName.equals(defaultGroupRole.getName()) &&
						 groupDefaultActions.contains(action)) {

					groupPermissions.add(action);
				}
			}
		}

		setGroupPermissions(
			groupPermissions.toArray(new String[groupPermissions.size()]));
		setGuestPermissions(
			guestPermissions.toArray(new String[guestPermissions.size()]));
	}

	/**
	 * Returns <code>true</code> if this service context is being passed as a
	 * parameter to a method which manipulates a resource to which default group
	 * permissions apply.
	 *
	 * @return     <code>true</code> if this service context is being passed as
	 *             a parameter to a method which manipulates a resource to which
	 *             default community permissions apply; <code>false</code>
	 *             otherwise
	 * @deprecated As of 6.1, renamed to {@link #isAddGroupPermissions()}
	 */
	public boolean getAddCommunityPermissions() {
		return isAddGroupPermissions();
	}

	/**
	 * Returns the asset category IDs to be applied to an asset entry if the
	 * service context is being passed as a parameter to a method which
	 * manipulates the asset entry.
	 *
	 * @return the asset category IDs
	 */
	public long[] getAssetCategoryIds() {
		return _assetCategoryIds;
	}

	/**
	 * Returns the primary keys of the asset entries linked to an asset entry if
	 * the service context is being passed as a parameter to a method which
	 * manipulates the asset entry.
	 *
	 * @return the primary keys of the asset entries
	 */
	public long[] getAssetLinkEntryIds() {
		return _assetLinkEntryIds;
	}

	/**
	 * Returns the asset tag names to be applied to an asset entry if the
	 * service context is being passed as a parameter to a method which
	 * manipulates the asset entry.
	 *
	 * @return the asset tag names
	 */
	public String[] getAssetTagNames() {
		return _assetTagNames;
	}

	/**
	 * Returns the serializable object associated with the name of the standard
	 * parameter of this service context.
	 *
	 * @param  name the name of the standard parameter
	 * @return the serializable object associated with the name
	 */
	public Serializable getAttribute(String name) {
		return _attributes.get(name);
	}

	/**
	 * Returns the map of name/value pairs that are the standard parameters of
	 * this service context. Each value is serializable.
	 *
	 * @return the map of name/value pairs
	 */
	public Map<String, Serializable> getAttributes() {
		return _attributes;
	}

	/**
	 * Returns the value of the {@link
	 * com.liferay.portal.kernel.util.Constants#CMD} parameter used in most
	 * Liferay forms for internal portlets.
	 *
	 * @return the value of the command parameter
	 */
	public String getCommand() {
		return _command;
	}

	/**
	 * Returns the specific community permissions for a resource if the service
	 * context is being passed as a parameter to a method which manipulates the
	 * resource.
	 *
	 * @return     the community permissions
	 * @deprecated As of 6.1, renamed to {@link #getGroupPermissions()}
	 */
	public String[] getCommunityPermissions() {
		return getGroupPermissions();
	}

	/**
	 * Returns the company ID of this service context's current portal instance.
	 *
	 * @return the company ID
	 */
	public long getCompanyId() {
		return _companyId;
	}

	/**
	 * Returns the date when an entity was created if this service context is
	 * being passed as a parameter to a method which creates an entity.
	 *
	 * @return the creation date
	 */
	public Date getCreateDate() {
		return _createDate;
	}

	/**
	 * Returns the date when an entity was created (or a default date) if this
	 * service context is being passed as a parameter to a method which creates
	 * an entity.
	 *
	 * @param  defaultCreateDate an optional default create date to use if the
	 *         service context does not have a create date
	 * @return the creation date if available; the default date otherwise
	 */
	public Date getCreateDate(Date defaultCreateDate) {
		if (_createDate != null) {
			return _createDate;
		}
		else if (defaultCreateDate != null) {
			return defaultCreateDate;
		}
		else {
			return new Date();
		}
	}

	/**
	 * Returns the current URL of this service context
	 *
	 * @return the current URL
	 */
	public String getCurrentURL() {
		return _currentURL;
	}

	/**
	 * Returns an arbitrary number of attributes of an entity to be persisted.
	 *
	 * <p>
	 * These attributes only include fields that this service context does not
	 * possess by default.
	 * </p>
	 *
	 * @return the expando bridge attributes
	 */
	public Map<String, Serializable> getExpandoBridgeAttributes() {
		return _expandoBridgeAttributes;
	}

	/**
	 * Returns the date when an <code>aui:form</code> was generated in this
	 * service context. The form date can be used in detecting situations in
	 * which an entity has been modified while another client was editing that
	 * entity. </p>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <p>
	 * Person1 and person2 start editing the same version of a Web Content
	 * article. Person1 publishes changes to the article first. When person2
	 * attempts to publish changes to that article, the service implementation
	 * finds that a modification to that article has already been published some
	 * time after person2 started editing the article. Since the the article
	 * modification date was found to be later than the form date for person2,
	 * person2 could be alerted to the modification and make a backup copy of
	 * his edits before synchronizing with the published changes by person1.
	 * </p>
	 */
	public Date getFormDate() {
		return _formDate;
	}

	/**
	 * Returns the specific group permissions for a resource if this service
	 * context is being passed as a parameter to a method which manipulates the
	 * resource.
	 *
	 * @return the specific group permissions
	 */
	public String[] getGroupPermissions() {
		return _groupPermissions;
	}

	/**
	 * Returns this service context's user ID or guest ID if no user ID is
	 * available.
	 *
	 * @return the user ID, or guest ID if there is no user in this service
	 *         context, or <code>0</code> if there is no company in this service
	 *         context
	 * @throws PortalException if a default user for the company could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public long getGuestOrUserId() throws PortalException, SystemException {
		long userId = getUserId();

		if (userId > 0) {
			return userId;
		}

		long companyId = getCompanyId();

		if (companyId > 0) {
			return UserLocalServiceUtil.getDefaultUserId(getCompanyId());
		}

		return 0;
	}

	/**
	 * Returns the specific guest permissions for a resource if this service
	 * context is being passed as a parameter to a method which manipulates the
	 * resource.
	 *
	 * @return the specific guest permissions
	 */
	public String[] getGuestPermissions() {
		return _guestPermissions;
	}

	/**
	 * Returns the the map of request header name/value pairs of this service
	 * context.
	 *
	 * @return the the map of request header name/value pairs
	 * @see    com.liferay.portal.kernel.servlet.HttpHeaders
	 */
	public Map<String, String> getHeaders() {
		return _headers;
	}

	/**
	 * Returns the language ID of the locale of this service context's current
	 * user.
	 *
	 * @return the language ID
	 */
	public String getLanguageId() {
		if (_languageId != null) {
			return _languageId;
		}

		return LocaleUtil.toLanguageId(LocaleUtil.getMostRelevantLocale());
	}

	/**
	 * Returns the complete URL of the current page if a page context can be
	 * determined for this service context.
	 *
	 * @return the complete URL of the current page
	 */
	public String getLayoutFullURL() {
		return _layoutFullURL;
	}

	/**
	 * Returns the relative URL of the current page if a page context can be
	 * determined for this service context.
	 *
	 * @return the relative URL of the current page
	 */
	public String getLayoutURL() {
		return _layoutURL;
	}

	public LiferayPortletRequest getLiferayPortletRequest() {
		if (_request == null) {
			return null;
		}

		LiferayPortletRequest liferayPortletRequest =
			(LiferayPortletRequest)_request.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		return liferayPortletRequest;
	}

	public LiferayPortletResponse getLiferayPortletResponse() {
		if (_request == null) {
			return null;
		}

		LiferayPortletResponse liferayPortletResponse =
			(LiferayPortletResponse)_request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		return liferayPortletResponse;
	}

	public Locale getLocale() {
		return LocaleUtil.fromLanguageId(_languageId);
	}

	/**
	 * Returns the date when an entity was modified if this service context is
	 * being passed as a parameter to a method which updates an entity.
	 *
	 * @return the date when an entity was modified if this service context is
	 *         being passed as a parameter to a method which updates an entity
	 */
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	/**
	 * Returns the date when an entity was modified if this service context is
	 * being passed as a parameter to a method which modifies an entity.
	 *
	 * @param  defaultModifiedDate an optional default modified date to use if
	 *         this service context does not have a modified date
	 * @return the modified date if available; the default date otherwise
	 */
	public Date getModifiedDate(Date defaultModifiedDate) {
		if (_modifiedDate != null) {
			return _modifiedDate;
		}
		else if (defaultModifiedDate != null) {
			return defaultModifiedDate;
		}
		else {
			return new Date();
		}
	}

	public String getPathFriendlyURLPrivateGroup() {
		return _pathFriendlyURLPrivateGroup;
	}

	public String getPathFriendlyURLPrivateUser() {
		return _pathFriendlyURLPrivateUser;
	}

	public String getPathFriendlyURLPublic() {
		return _pathFriendlyURLPublic;
	}

	/**
	 * Returns the main context path of the portal, concatenated with
	 * <code>/c</code>.
	 *
	 * @return the main context path of the portal
	 */
	public String getPathMain() {
		return _pathMain;
	}

	/**
	 * Returns the portal layout ID of the current page of this service context.
	 *
	 * @return the portal layout ID of the current page
	 */
	public long getPlid() {
		return _plid;
	}

	/**
	 * Returns the URL of this service context's portal, including the protocol,
	 * domain, and non-default port relative to the company instance and any
	 * virtual host.
	 *
	 * <p>
	 * The URL returned does not include the port if a default port is used.
	 * </p>
	 *
	 * @return the URL of this service context's portal, including the protocol,
	 *         domain, and non-default port relative to company instance and any
	 *         virtual host
	 */
	public String getPortalURL() {
		return _portalURL;
	}

	/**
	 * Returns the ID of the current portlet if this service context is being
	 * passed as a parameter to a portlet.
	 *
	 * @return the ID of the current portlet
	 * @see    com.liferay.portal.model.PortletPreferencesIds
	 */
	public String getPortletId() {
		if (_portletPreferencesIds == null) {
			return null;
		}

		return _portletPreferencesIds.getPortletId();
	}

	/**
	 * Returns the portlet preferences IDs of the current portlet if the service
	 * context is being passed as a parameter to a portlet.
	 *
	 * <p>
	 * The {@link com.liferay.portal.model.PortletPreferencesIds} can be used to
	 * look up portlet preferences of the current portlet.
	 * </p>
	 *
	 * @return the portlet preferences IDs of the current portlet
	 * @see    com.liferay.portal.model.PortletPreferencesIds
	 */
	public PortletPreferencesIds getPortletPreferencesIds() {
		return _portletPreferencesIds;
	}

	/**
	 * Returns the remote address of the user making the request in this service
	 * context.
	 *
	 * @return the remote address of the user making the request
	 */
	public String getRemoteAddr() {
		return _remoteAddr;
	}

	/**
	 * Returns the remote host name of the user making the request in this
	 * service context.
	 *
	 * @return the remote host name of the user making the request
	 */
	public String getRemoteHost() {
		return _remoteHost;
	}

	public HttpServletRequest getRequest() {
		return _request;
	}

	public HttpServletResponse getResponse() {
		LiferayPortletResponse liferayPortletResponse =
			getLiferayPortletResponse();

		if (liferayPortletResponse == null) {
			return null;
		}

		return PortalUtil.getHttpServletResponse(liferayPortletResponse);
	}

	public String getRootPortletId() {
		String portletId = getPortletId();

		if (portletId == null) {
			return null;
		}

		return PortletConstants.getRootPortletId(portletId);
	}

	public Group getScopeGroup() throws PortalException, SystemException {
		return GroupLocalServiceUtil.getGroup(_scopeGroupId);
	}

	/**
	 * Returns the ID of the group corresponding to the current data scope of
	 * this service context.
	 *
	 * @return the ID of the group corresponding to the current data scope
	 * @see    com.liferay.portal.model.Group
	 */
	public long getScopeGroupId() {
		return _scopeGroupId;
	}

	public ThemeDisplay getThemeDisplay() {
		if (_request == null) {
			return null;
		}

		return (ThemeDisplay)_request.getAttribute(WebKeys.THEME_DISPLAY);
	}

	public TimeZone getTimeZone() {
		return _timeZone;
	}

	/**
	 * Returns the user-agent request header of this service context.
	 *
	 * @return the user-agent request header
	 * @see    com.liferay.portal.kernel.servlet.HttpHeaders
	 */
	public String getUserAgent() {
		if (_request == null) {
			return null;
		}

		return _request.getHeader(HttpHeaders.USER_AGENT);
	}

	/**
	 * Returns the complete URL of this service context's current user's profile
	 * page.
	 *
	 * @return the complete URL of this service context's current user's profile
	 *         page
	 */
	public String getUserDisplayURL() {
		return _userDisplayURL;
	}

	/**
	 * Returns the ID of this service context's current user.
	 *
	 * @return the ID of this service context's current user
	 */
	public long getUserId() {
		return _userId;
	}

	/**
	 * Returns the UUID of this service context's current entity.
	 *
	 * @return the UUID of this service context's current entity
	 */
	public String getUuid() {
		String uuid = _uuid;

		_uuid = null;

		return uuid;
	}

	/**
	 * Returns the workflow action to take if this service context is being
	 * passed as a parameter to a method that processes a workflow action.
	 *
	 * @return the workflow action to take
	 */
	public int getWorkflowAction() {
		return _workflowAction;
	}

	/**
	 * Returns <code>true</code> if this service context is being passed as a
	 * parameter to a method which manipulates a resource to which default group
	 * permissions apply.
	 *
	 * @return <code>true</code> if this service context is being passed as a
	 *         parameter to a method which manipulates a resource to which
	 *         default group permissions apply; <code>false</code> otherwise
	 */
	public boolean isAddGroupPermissions() {
		return _addGroupPermissions;
	}

	/**
	 * Returns <code>true</code> if this service context is being passed as a
	 * parameter to a method which manipulates a resource to which default guest
	 * permissions apply.
	 *
	 * @return <code>true</code> if this service context is being passed as a
	 *         parameter to a method which manipulates a resource to which
	 *         default guest permissions apply; <code>false</code> otherwise
	 */
	public boolean isAddGuestPermissions() {
		return _addGuestPermissions;
	}

	public boolean isAssetEntryVisible() {
		return _assetEntryVisible;
	}

	/**
	 * Returns <code>true</code> if this service context contains an add command
	 * (i.e. has command value {@link
	 * com.liferay.portal.kernel.util.Constants#ADD})
	 *
	 * @return <code>true</code> if this service context contains an add
	 *         command; <code>false</code> otherwise
	 */
	public boolean isCommandAdd() {
		if (Validator.equals(_command, Constants.ADD) ||
			Validator.equals(_command, Constants.ADD_MULTIPLE)) {

			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if this service context contains an update
	 * command (i.e. has command value {@link
	 * com.liferay.portal.kernel.util.Constants#UPDATE})
	 *
	 * @return <code>true</code> if this service context contains an update
	 *         command; <code>false</code> otherwise
	 */
	public boolean isCommandUpdate() {
		if (Validator.equals(_command, Constants.UPDATE)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isDeriveDefaultPermissions() {
		return _deriveDefaultPermissions;
	}

	/**
	 * Returns <code>true</code> if portal exceptions should be handled as
	 * failures, possibly halting processing, or <code>false</code> if
	 * exceptions should be handled differently, possibly allowing processing to
	 * continue in some manner. Services that check this flag dictate their
	 * desired behavior.
	 *
	 * <p>
	 * Those service methods - exposed as a JSON web service - that perform an
	 * operation on multiple items can be instructed through this flag not to
	 * fail when processing encounters portal exceptions.
	 * </p>
	 *
	 * <p>
	 * If this flag is set to <code>false</code>, services can implement logic
	 * that allows processing to continue, while collecting information
	 * regarding the exceptions to return to the caller. For example, the {@link
	 * com.liferay.portlet.asset.service.impl.AssetVocabularyServiceImpl#deleteVocabularies(
	 * long[], ServiceContext)} method uses the list it returns to give
	 * information on vocabularies it failed to delete; it returns an empty list
	 * if all deletions were successful.
	 * </p>
	 *
	 * @return <code>true</code> if portal exceptions are to be handled as
	 *         failures; <code>false</code> if portal exceptions can be handled
	 *         differently, possibly allowing processing to continue in some
	 *         manner
	 */
	public boolean isFailOnPortalException() {
		return _failOnPortalException;
	}

	/**
	 * Returns whether the primary entity of this service context is to be
	 * indexed/re-indexed.
	 *
	 * @return <code>true</code> the primary entity of this service context is
	 *         to be indexed/re-indexed; <code>false</code> otherwise
	 */
	public boolean isIndexingEnabled() {
		return _indexingEnabled;
	}

	/**
	 * Returns <code>true</code> if the sender of this service context's request
	 * is signed in.
	 *
	 * @return <code>true</code> if the sender of this service context's request
	 *         is signed in; <code>false</code> otherwise
	 */
	public boolean isSignedIn() {
		return _signedIn;
	}

	public void merge(ServiceContext serviceContext) {
		setAddGroupPermissions(serviceContext.isAddGroupPermissions());
		setAddGuestPermissions(serviceContext.isAddGuestPermissions());

		if (serviceContext.getAssetCategoryIds() != null) {
			setAssetCategoryIds(serviceContext.getAssetCategoryIds());
		}

		if (serviceContext.getAssetLinkEntryIds() != null) {
			setAssetLinkEntryIds(serviceContext.getAssetLinkEntryIds());
		}

		if (serviceContext.getAssetTagNames() != null) {
			setAssetTagNames(serviceContext.getAssetTagNames());
		}

		if (serviceContext.getAttributes() != null) {
			setAttributes(serviceContext.getAttributes());
		}

		if (Validator.isNotNull(serviceContext.getCommand())) {
			setCommand(serviceContext.getCommand());
		}

		if (serviceContext.getCompanyId() > 0) {
			setCompanyId(serviceContext.getCompanyId());
		}

		if (serviceContext.getCreateDate() != null) {
			setCreateDate(serviceContext.getCreateDate());
		}

		if (Validator.isNotNull(serviceContext.getCurrentURL())) {
			setCurrentURL(serviceContext.getCurrentURL());
		}

		if (serviceContext.getExpandoBridgeAttributes() != null) {
			setExpandoBridgeAttributes(
				serviceContext.getExpandoBridgeAttributes());
		}

		if (serviceContext.getGroupPermissions() != null) {
			setGroupPermissions(serviceContext.getGroupPermissions());
		}

		if (serviceContext.getGuestPermissions() != null) {
			setGuestPermissions(serviceContext.getGuestPermissions());
		}

		if (serviceContext.getHeaders() != null) {
			setHeaders(serviceContext.getHeaders());
		}

		setFailOnPortalException(serviceContext.isFailOnPortalException());
		setLanguageId(serviceContext.getLanguageId());

		if (Validator.isNotNull(serviceContext.getLayoutFullURL())) {
			setLayoutFullURL(serviceContext.getLayoutFullURL());
		}

		if (Validator.isNotNull(serviceContext.getLayoutURL())) {
			setLayoutURL(serviceContext.getLayoutURL());
		}

		if (serviceContext.getModifiedDate() != null) {
			setModifiedDate(serviceContext.getModifiedDate());
		}

		if (Validator.isNotNull(serviceContext.getPathMain())) {
			setPathMain(serviceContext.getPathMain());
		}

		if (serviceContext.getPlid() > 0) {
			setPlid(serviceContext.getPlid());
		}

		if (Validator.isNotNull(serviceContext.getPortalURL())) {
			setPortalURL(serviceContext.getPortalURL());
		}

		if (serviceContext.getPortletPreferencesIds() != null) {
			setPortletPreferencesIds(serviceContext.getPortletPreferencesIds());
		}

		if (Validator.isNotNull(serviceContext.getRemoteAddr())) {
			setRemoteAddr(serviceContext.getRemoteAddr());
		}

		if (Validator.isNotNull(serviceContext.getRemoteHost())) {
			setRemoteHost(serviceContext.getRemoteHost());
		}

		if (serviceContext.getScopeGroupId() > 0) {
			setScopeGroupId(serviceContext.getScopeGroupId());
		}

		setSignedIn(serviceContext.isSignedIn());

		if (Validator.isNotNull(serviceContext.getUserDisplayURL())) {

			setUserDisplayURL(serviceContext.getUserDisplayURL());
		}

		if (serviceContext.getUserId() > 0) {
			setUserId(serviceContext.getUserId());
		}

		if (Validator.isNotNull(serviceContext.getUuid())) {
			setUuid(serviceContext.getUuid());
		}

		if (serviceContext.getWorkflowAction() > 0) {
			setWorkflowAction(serviceContext.getWorkflowAction());
		}
	}

	/**
	 * Removes the mapping of the serializable object to the name of the
	 * standard parameter of this service context.
	 *
	 * @param  name the name of the standard parameter
	 * @return the serializable object associated to the name
	 */
	public Serializable removeAttribute(String name) {
		return _attributes.remove(name);
	}

	/**
	 * Sets whether or not default community permissions should apply to a
	 * resource being manipulated by a method to which this service context is
	 * passed as a parameter.
	 *
	 * @param      addCommunityPermissions indicates whether or not to apply
	 *             default community permissions
	 * @deprecated As of 6.1, renamed to {@link
	 *             #setAddGroupPermissions(boolean)}
	 */
	public void setAddCommunityPermissions(boolean addCommunityPermissions) {
		setAddGroupPermissions(addCommunityPermissions);
	}

	/**
	 * Sets whether or not default group permissions should apply to a resource
	 * being manipulated by a method to which this service context is passed as
	 * a parameter.
	 *
	 * @param addGroupPermissions indicates whether or not to apply default
	 *        group permissions
	 */
	public void setAddGroupPermissions(boolean addGroupPermissions) {
		_addGroupPermissions = addGroupPermissions;
	}

	/**
	 * Sets whether or not default guest permissions should apply to a resource
	 * being manipulated by a method to which this service context is passed as
	 * a parameter.
	 *
	 * @param addGuestPermissions indicates whether or not to apply default
	 *        guest permissions
	 */
	public void setAddGuestPermissions(boolean addGuestPermissions) {
		_addGuestPermissions = addGuestPermissions;
	}

	/**
	 * Sets an array of asset category IDs to be applied to an asset entry if
	 * this service context is being passed as a parameter to a method which
	 * manipulates the asset entry.
	 *
	 * @param assetCategoryIds the primary keys of the asset categories
	 */
	public void setAssetCategoryIds(long[] assetCategoryIds) {
		_assetCategoryIds = assetCategoryIds;
	}

	public void setAssetEntryVisible(boolean assetEntryVisible) {
		_assetEntryVisible = assetEntryVisible;
	}

	/**
	 * Sets an array of the primary keys of asset entries to be linked to an
	 * asset entry if this service context is being passed as a parameter to a
	 * method which manipulates the asset entry.
	 *
	 * @param assetLinkEntryIds the primary keys of the asset entries to be
	 *        linked to an asset entry
	 */
	public void setAssetLinkEntryIds(long[] assetLinkEntryIds) {
		_assetLinkEntryIds = assetLinkEntryIds;
	}

	/**
	 * Sets an array of asset tag names to be applied to an asset entry if this
	 * service context is being passed as a parameter to a method which
	 * manipulates the asset entry.
	 *
	 * @param assetTagNames the tag names to be applied to an asset entry
	 */
	public void setAssetTagNames(String[] assetTagNames) {
		_assetTagNames = assetTagNames;
	}

	/**
	 * Sets a mapping of a standard parameter's name to its serializable object.
	 *
	 * @param name the standard parameter name to associate with the value
	 * @param value the serializable object to be associated with the name
	 */
	public void setAttribute(String name, Serializable value) {
		_attributes.put(name, value);
	}

	/**
	 * Sets the map of the name/value pairs that are the standard parameters of
	 * this service context. Each value must be serializable.
	 *
	 * @param attributes the map of the name/value pairs that are the standard
	 *        parameters of this service context
	 */
	public void setAttributes(Map<String, Serializable> attributes) {
		_attributes = attributes;
	}

	/**
	 * Sets the value of the {@link
	 * com.liferay.portal.kernel.util.Constants#CMD} parameter used in most
	 * Liferay forms for internal portlets.
	 *
	 * @param command the value of the {@link
	 *        com.liferay.portal.kernel.util.Constants#CMD} parameter
	 */
	public void setCommand(String command) {
		_command = command;
	}

	/**
	 * Sets an array containing specific community permissions for a resource if
	 * this service context is being passed as a parameter to a method which
	 * manipulates the resource.
	 *
	 * @param      communityPermissions the community permissions (optionally
	 *             <code>null</code>)
	 * @deprecated As of 6.1, renamed to {@link #setGroupPermissions(String[])}
	 */
	public void setCommunityPermissions(String[] communityPermissions) {
		setGroupPermissions(communityPermissions);
	}

	/**
	 * Sets the company ID of this service context's current portal instance.
	 *
	 * @param companyId the primary key of this service context's current portal
	 *        instance
	 */
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	/**
	 * Sets the date when an entity was created if this service context is being
	 * passed as a parameter to a method which creates an entity.
	 *
	 * @param createDate the date the entity was created
	 */
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	/**
	 * Sets the current URL of this service context
	 *
	 * @param currentURL the current URL of this service context
	 */
	public void setCurrentURL(String currentURL) {
		_currentURL = currentURL;
	}

	public void setDeriveDefaultPermissions(boolean deriveDefaultPermissions) {
		_deriveDefaultPermissions = deriveDefaultPermissions;
	}

	/**
	 * Sets an arbitrary number of attributes of an entity to be persisted.
	 *
	 * <p>
	 * These attributes should only include fields that {@link
	 * com.liferay.portal.service.ServiceContext} does not possess by default.
	 * </p>
	 *
	 * @param expandoBridgeAttributes the expando bridge attributes (optionally
	 *        <code>null</code>)
	 */
	public void setExpandoBridgeAttributes(
		Map<String, Serializable> expandoBridgeAttributes) {

		_expandoBridgeAttributes = expandoBridgeAttributes;
	}

	/**
	 * Sets whether portal exceptions should be handled as failures, possibly
	 * halting processing, or if exceptions should be handled differently,
	 * possibly allowing processing to continue in some manner.
	 *
	 * @param failOnPortalException whether portal exceptions should be handled
	 *        as failures, or if portal exceptions should be handled
	 *        differently, possibly allowing processing to continue in some
	 *        manner
	 * @see   #isFailOnPortalException()
	 */
	public void setFailOnPortalException(boolean failOnPortalException) {
		_failOnPortalException = failOnPortalException;
	}

	/**
	 * Sets the date when an <code>aui:form</code> was generated in this service
	 * context. The form date can be used in detecting situations in which an
	 * entity has been modified while another client was editing that entity.
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <p>
	 * Person1 and person2 start editing the same version of a Web Content
	 * article. Person1 publishes changes to the article first. When person2
	 * attempts to publish changes to that article, the service implementation
	 * finds that a modification to that article has already been published some
	 * time after person2 started editing the article. Since the article
	 * modification date was found to be later than the form date for person2,
	 * person2 could be alerted to the modification and make a backup copy of
	 * his edits before synchronizing with the published changes by person1.
	 * </p>
	 *
	 * @param formDate the date that an <code>aui:form</code> was generated for
	 *        this service context (optionally <code>null</code>)
	 */
	public void setFormDate(Date formDate) {
		_formDate = formDate;
	}

	/**
	 * Sets an array containing specific group permissions for a resource if
	 * this service context is being passed as a parameter to a method which
	 * manipulates the resource.
	 *
	 * @param groupPermissions the permissions (optionally <code>null</code>)
	 */
	public void setGroupPermissions(String[] groupPermissions) {
		_groupPermissions = groupPermissions;
	}

	/**
	 * Sets an array containing specific guest permissions for a resource if
	 * this service context is being passed as a parameter to a method which
	 * manipulates the resource.
	 *
	 * @param guestPermissions the guest permissions (optionally
	 *        <code>null</code>)
	 */
	public void setGuestPermissions(String[] guestPermissions) {
		_guestPermissions = guestPermissions;
	}

	/**
	 * Sets the map of request header name/value pairs of this service context.
	 *
	 * @param headers map of request header name/value pairs of this service
	 *        context
	 * @see   com.liferay.portal.kernel.servlet.HttpHeaders
	 */
	public void setHeaders(Map<String, String> headers) {
		_headers = headers;
	}

	/**
	 * Sets whether the primary entity of this service context is to be
	 * indexed/re-indexed.
	 *
	 * <p>
	 * The entity is only indexed/re-indexed if the method receiving this
	 * service context as a parameter does indexing.
	 * </p>
	 *
	 * @param indexingEnabled whether the primary entity of this service context
	 *        is to be indexed/re-indexed (default is <code>true</code>)
	 */
	public void setIndexingEnabled(boolean indexingEnabled) {
		_indexingEnabled = indexingEnabled;
	}

	/**
	 * Sets the language ID of the locale of this service context.
	 *
	 * @param languageId the language ID of the locale of this service context's
	 *        current user
	 */
	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	/**
	 * Sets the complete URL of the current page for this service context.
	 *
	 * @param layoutFullURL the complete URL of the current page if a page
	 *        context can be determined for this service context
	 */
	public void setLayoutFullURL(String layoutFullURL) {
		_layoutFullURL = layoutFullURL;
	}

	/**
	 * Sets the relative URL of the current page for this service context.
	 *
	 * @param layoutURL the relative URL of the current page if a page context
	 *        can be determined for this service context
	 */
	public void setLayoutURL(String layoutURL) {
		_layoutURL = layoutURL;
	}

	/**
	 * Sets the date when an entity was modified in this service context.
	 *
	 * @param modifiedDate the date when an entity was modified in this service
	 *        context
	 */
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setPathFriendlyURLPrivateGroup(
		String pathFriendlyURLPrivateGroup) {

		_pathFriendlyURLPrivateGroup = pathFriendlyURLPrivateGroup;
	}

	public void setPathFriendlyURLPrivateUser(
		String pathFriendlyURLPrivateUser) {

		_pathFriendlyURLPrivateUser = pathFriendlyURLPrivateUser;
	}

	public void setPathFriendlyURLPublic(String pathFriendlyURLPublic) {
		_pathFriendlyURLPublic = pathFriendlyURLPublic;
	}

	/**
	 * Sets the main context path of the portal, concatenated with
	 * <code>/c</code>.
	 *
	 * @param pathMain the main context path of the portal
	 */
	public void setPathMain(String pathMain) {
		_pathMain = pathMain;
	}

	/**
	 * Sets the portal layout ID of the current page in this service context.
	 *
	 * @param plid the portal layout ID of the current page
	 */
	public void setPlid(long plid) {
		_plid = plid;
	}

	/**
	 * Sets the URL of this service context's portal, including the protocol,
	 * domain, and non-default port relative to the company instance and any
	 * virtual host.
	 *
	 * <p>
	 * The URL should not include the port if a default port is used.
	 * </p>
	 *
	 * @param portalURL the portal URL
	 */
	public void setPortalURL(String portalURL) {
		_portalURL = portalURL;
	}

	/**
	 * Sets the portlet preferences IDs of the current portlet if this service
	 * context is being passed as a parameter to a portlet.
	 *
	 * <p>
	 * The {@link com.liferay.portal.model.PortletPreferencesIds} can be used to
	 * look up portlet preferences of the current portlet.
	 * </p>
	 *
	 * @param portletPreferencesIds the portlet preferences
	 * @see   com.liferay.portal.model.PortletPreferencesIds
	 */
	public void setPortletPreferencesIds(
		PortletPreferencesIds portletPreferencesIds) {

		_portletPreferencesIds = portletPreferencesIds;
	}

	/**
	 * Sets the remote address of the user making the request in this service
	 * context.
	 *
	 * @param remoteAddr the remote address of the user making the request in
	 *        this service context
	 */
	public void setRemoteAddr(String remoteAddr) {
		_remoteAddr = remoteAddr;
	}

	/**
	 * Sets the remote host name of the user making the request in this service
	 * context.
	 *
	 * @param remoteHost the remote host name of the user making the request in
	 *        this service context
	 */
	public void setRemoteHost(String remoteHost) {
		_remoteHost = remoteHost;
	}

	/**
	 * Sets the optional request used when instantiating this service context.
	 * The field is volatile and so will be discarded on serialization.
	 *
	 * @param request the request
	 */
	public void setRequest(HttpServletRequest request) {
		_request = request;
	}

	/**
	 * Sets the ID of the group corresponding to the current data scope of this
	 * service context.
	 *
	 * @param scopeGroupId the ID of the group corresponding to the current data
	 *        scope of this service context
	 * @see   com.liferay.portal.model.Group
	 */
	public void setScopeGroupId(long scopeGroupId) {
		_scopeGroupId = scopeGroupId;
	}

	/**
	 * Sets whether the sender of this service context's request is signed in.
	 *
	 * @param signedIn whether the sender of this service context's request is
	 *        signed in
	 */
	public void setSignedIn(boolean signedIn) {
		_signedIn = signedIn;
	}

	public void setTimeZone(TimeZone timeZone) {
		_timeZone = timeZone;
	}

	/**
	 * Sets the complete URL of this service context's current user's profile
	 * page.
	 *
	 * @param userDisplayURL the complete URL of the current user's profile page
	 */
	public void setUserDisplayURL(String userDisplayURL) {
		_userDisplayURL = userDisplayURL;
	}

	/**
	 * Sets the ID of this service context's current user.
	 *
	 * @param userId the ID of the current user
	 */
	public void setUserId(long userId) {
		_userId = userId;
	}

	/**
	 * Sets the UUID of this service context's current entity.
	 *
	 * @param uuid the UUID of the current entity
	 */
	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	/**
	 * Sets the workflow action to take if this service context is being passed
	 * as parameter to a method that processes a workflow action.
	 *
	 * @param workflowAction workflow action to take (default is {@link
	 *        com.liferay.portal.kernel.workflow.WorkflowConstants#ACTION_PUBLISH})
	 */
	public void setWorkflowAction(int workflowAction) {
		_workflowAction = workflowAction;
	}

	public String translate(String pattern, Object... arguments) {
		Locale locale = getLocale();

		return LanguageUtil.format(locale, pattern, arguments);
	}

	public void validateModifiedDate(
			AuditedModel auditedModel, Class<? extends PortalException> clazz)
		throws PortalException {

		int value = DateUtil.compareTo(
			auditedModel.getModifiedDate(), _formDate);

		if (value > 0) {
			try {
				throw clazz.newInstance();
			}
			catch (IllegalAccessException iae) {
				throw new RuntimeException(iae);
			}
			catch (InstantiationException ie) {
				throw new RuntimeException(ie);
			}
		}
	}

	private boolean _addGroupPermissions;
	private boolean _addGuestPermissions;
	private long[] _assetCategoryIds;
	private boolean _assetEntryVisible = true;
	private long[] _assetLinkEntryIds;
	private String[] _assetTagNames;
	private Map<String, Serializable> _attributes;
	private String _command;
	private long _companyId;
	private Date _createDate;
	private String _currentURL;
	private boolean _deriveDefaultPermissions;
	private Map<String, Serializable> _expandoBridgeAttributes;
	private boolean _failOnPortalException = true;
	private Date _formDate;
	private String[] _groupPermissions;
	private String[] _guestPermissions;
	private Map<String, String> _headers;
	private boolean _indexingEnabled = true;
	private String _languageId;
	private String _layoutFullURL;
	private String _layoutURL;
	private Date _modifiedDate;
	private String _pathFriendlyURLPrivateGroup;
	private String _pathFriendlyURLPrivateUser;
	private String _pathFriendlyURLPublic;
	private String _pathMain;
	private long _plid;
	private String _portalURL;
	private PortletPreferencesIds _portletPreferencesIds;
	private String _remoteAddr;
	private String _remoteHost;
	private transient HttpServletRequest _request;
	private long _scopeGroupId;
	private boolean _signedIn;
	private TimeZone _timeZone;
	private String _userDisplayURL;
	private long _userId;
	private String _uuid;
	private int _workflowAction = WorkflowConstants.ACTION_PUBLISH;

}