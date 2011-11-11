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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.PortletPreferencesIds;

import java.io.Serializable;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

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
	 * Creates a new <code>ServiceContext</code> object with an
	 * <code>_attributes</code> map and an <code>_expandoBridgeAttributes</code>
	 * map. The <code>_attributes</code> map contains standard
	 * <code>ServiceContext</code> parameters and the
	 * <code>expandoBridgeAttributes</code> contains optional
	 * <code>ServiceContext</code> parameters.
	 */
	public ServiceContext() {
		_attributes = new LinkedHashMap<String, Serializable>();
		_expandoBridgeAttributes = new LinkedHashMap<String, Serializable>();
	}

	/**
	 * Returns an identical <code>ServiceContext</code> object.
	 * 
	 * @return an identical <code>ServiceContext</code> object
	 */
	@Override
	public Object clone() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(getAddGroupPermissions());
		serviceContext.setAddGuestPermissions(getAddGuestPermissions());
		serviceContext.setAssetCategoryIds(getAssetCategoryIds());
		serviceContext.setAssetLinkEntryIds(getAssetLinkEntryIds());
		serviceContext.setAssetTagNames(getAssetTagNames());
		serviceContext.setAttributes(getAttributes());
		serviceContext.setCommand(getCommand());
		serviceContext.setCompanyId(getCompanyId());
		serviceContext.setCreateDate(getCreateDate());
		serviceContext.setCurrentURL(getCurrentURL());
		serviceContext.setExpandoBridgeAttributes(getExpandoBridgeAttributes());
		serviceContext.setGroupPermissions(getGroupPermissions());
		serviceContext.setGuestPermissions(getGuestPermissions());
		serviceContext.setHeaders(getHeaders());
		serviceContext.setIndexingEnabled(isIndexingEnabled());
		serviceContext.setLanguageId(getLanguageId());
		serviceContext.setLayoutFullURL(getLayoutFullURL());
		serviceContext.setLayoutURL(getLayoutURL());
		serviceContext.setModifiedDate(getModifiedDate());
		serviceContext.setPathMain(getPathMain());
		serviceContext.setPlid(getPlid());
		serviceContext.setPortalURL(getPortalURL());
		serviceContext.setPortletPreferencesIds(getPortletPreferencesIds());
		serviceContext.setRemoteAddr(getRemoteAddr());
		serviceContext.setRemoteHost(getRemoteHost());
		serviceContext.setScopeGroupId(getScopeGroupId());
		serviceContext.setSignedIn(isSignedIn());
		serviceContext.setUserDisplayURL(getUserDisplayURL());
		serviceContext.setUserId(getUserId());
		serviceContext.setUuid(getUuid());
		serviceContext.setWorkflowAction(getWorkflowAction());

		return serviceContext;
	}

	/**
	 * Returns <code>true</code> if the <code>ServiceContext</code> is being
	 * passed as a parameter to a method which manipulates a resource to which
	 * default group permissions apply.
	 * 
	 * @return <code>true</code> if the <code>ServiceContext</code> is being
	 *         passed as a parameter to a method which manipulates a resource to
	 *         which default community permissions apply; <code>false</code>
	 *         otherwise
	 * @deprecated As of 6.1, renamed to {@link #getAddGroupPermissions()}
	 */
	public boolean getAddCommunityPermissions() {
		return getAddGroupPermissions();
	}

	/**
	 * Returns <code>true</code> if the <code>ServiceContext</code> is being
	 * passed as a parameter to a method which manipulates a resource to which
	 * default guest permissions apply.
	 * 
	 * @return <code>true</code> if the <code>ServiceContext</code> is being
	 *         passed as a parameter to a method which manipulates a resource to
	 *         which default guest permissions apply; <code>false</code>
	 *         otherwise
	 */
	public boolean getAddGuestPermissions() {
		return _addGuestPermissions;
	}

	/**
	 * Returns <code>true</code> if the <code>ServiceContext</code> is being
	 * passed as a parameter to a method which manipulates a resource to which
	 * default group permissions apply.
	 * 
	 * @return <code>true</code> if the <code>ServiceContext</code> is being
	 *         passed as a parameter to a method which manipulates a resource to
	 *         which default group permissions apply; <code>false</code>
	 *         otherwise
	 */
	public boolean getAddGroupPermissions() {
		return _addGroupPermissions;
	}

	/**
	 * Returns an <code>Array</code> of asset category ids to be applied to an
	 * asset entry if the <code>ServiceContext</code> is being passed as a
	 * parameter to a method which manipulates the asset entry.
	 * 
	 * @return an <code>Array</code> of asset category ids to be applied to an
	 *         asset entry if the <code>ServiceContext</code> is being passed as
	 *         a parameter to a method which manipulates the asset entry
	 */
	public long[] getAssetCategoryIds() {
		return _assetCategoryIds;
	}

	/**
	 * Returns the TODO something matching condition
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @return Returns the TODO something matching condition
	 */
	public long[] getAssetLinkEntryIds() {
		return _assetLinkEntryIds;
	}

	/**
	 * Returns an <code>Array</code> of asset tag names to be applied to an
	 * asset entry if the <code>ServiceContext</code> is being passed as a
	 * parameter to a method which manipulates the asset entry.
	 * 
	 * @return an <code>Array</code> of asset tag names to be applied to an
	 *         asset entry if the <code>ServiceContext</code> is being passed as
	 *         a parameter to a method which manipulates the asset entry
	 */
	public String[] getAssetTagNames() {
		return _assetTagNames;
	}

	/**
	 * Returns the TODO something matching condition
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @param name
	 *            the TODO name, default and acceptable values? (optionally
	 *            <code>null</code>)
	 * @return Returns the TODO something matching condition
	 */
	public Serializable getAttribute(String name) {
		return _attributes.get(name);
	}

	/**
	 * Returns the TODO something matching condition
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @return Returns the TODO something matching condition
	 */
	public Map<String, Serializable> getAttributes() {
		return _attributes;
	}

	/**
	 * Returns the value of the <code>Constants.CMD</code> parameter used in
	 * most Liferay forms for internal portlets.
	 * 
	 * @return the value of the <code>Constants.CMD</code> parameter used in
	 *         most Liferay forms for internal portlets
	 */
	public String getCommand() {
		return _command;
	}

	/**
	 * Returns an <code>Array</code> containing specific community permissions
	 * for a resource if the <code>ServiceContext</code> is being passed as a
	 * parameter to a method which manipulates the resource.
	 * 
	 * @return an <code>Array</code> containing specific community permissions
	 *         for a resource if the <code>ServiceContext</code> is being passed
	 *         as a parameter to a method which manipulates the resource
	 * @deprecated As of 6.1, renamed to {@link #getGroupPermissions()}
	 */
	public String[] getCommunityPermissions() {
		return getGroupPermissions();
	}

	/**
	 * Returns the <code>_companyId</code> of the current portal instance.
	 * 
	 * @return the <code>_companyId</code> of the current portal instance
	 */

	public long getCompanyId() {
		return _companyId;
	}

	/**
	 * Returns the <code>Date</code> when an entity was created if the
	 * <code>ServiceContext</code> is being passed as a parameter to a method
	 * which creates an entity.
	 * 
	 * @return the <code>Date</code> when an entity was created if the
	 *         <code>ServiceContext</code> is being passed as a parameter to a
	 *         method which creates an entity
	 */

	public Date getCreateDate() {
		return _createDate;
	}

	/**
	 * Returns the <code>Date</code> when an entity was created if the
	 * <code>ServiceContext</code> is being passed as a parameter to a method
	 * which creates an entity.
	 * 
	 * <p>
	 * Can provide a default <code>Date</code> to use if the
	 * <code>ServiceContext</code> does not already have a create date.
	 * </p>
	 * 
	 * @param defaultCreateDate
	 *            an optional default create date to use if the
	 *            <code>ServiceContext</code> does not have a create date
	 * @return the <code>Date</code> when an entity was created if the
	 *         <code>ServiceContext</code> is being passed as a parameter to a
	 *         method which creates an entity
	 */
	public Date getCreateDate(Date defaultCreateDate) {
		if (_createDate != null) {
			return _createDate;
		} else if (defaultCreateDate != null) {
			return defaultCreateDate;
		} else {
			return new Date();
		}
	}

	/**
	 * Returns the TODO something matching condition
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @return Returns the TODO something matching condition
	 */
	public String getCurrentURL() {
		return _currentURL;
	}

	/**
	 * Returns an arbitrary number of attributes of an entity to be persisted.
	 * 
	 * <p>
	 * These attributes only include fields that the <code>ServiceContext</code>
	 * does not possess by default.
	 * </p>
	 * 
	 * @return an arbitrary number of attributes of an entity to be persisted
	 */
	public Map<String, Serializable> getExpandoBridgeAttributes() {
		return _expandoBridgeAttributes;
	}

	/**
	 * Returns an <code>Array</code> containing specific group permissions for a
	 * resource if the <code>ServiceContext</code> is being passed as a
	 * parameter to a method which manipulates the resource.
	 * 
	 * @return an <code>Array</code> containing specific group permissions for a
	 *         resource if the <code>ServiceContext</code> is being passed as a
	 *         parameter to a method which manipulates the resource
	 */

	public String[] getGroupPermissions() {
		return _groupPermissions;
	}

	/**
	 * Returns the TODO something matching condition
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @return Returns the TODO something matching condition
	 * @throws PortalException
	 *             TODO list exceptional conditions that could have occurred
	 * @throws SystemException
	 *             if a system exception occurred
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
	 * Returns an <code>Array</code> containing specific guest permissions for a
	 * resource if the <code>ServiceContext</code> is being passed as a
	 * parameter to a method which manipulates the resource.
	 * 
	 * @return an <code>Array</code> containing specific guest permissions for a
	 *         resource if the <code>ServiceContext</code> is being passed as a
	 *         parameter to a method which manipulates the resource
	 */
	public String[] getGuestPermissions() {
		return _guestPermissions;
	}

	/**
	 * Returns the TODO something matching condition
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @return Returns the TODO something matching condition
	 */
	public Map<String, String> getHeaders() {
		return _headers;
	}

	/**
	 * Returns the current user <code>Locale</code> as a <code>String</code>.
	 * 
	 * @return Returns the TODO something matching condition
	 */
	public String getLanguageId() {
		return _languageId;
	}

	/**
	 * Returns the complete URL of the current page if a page context can be
	 * determined.
	 * 
	 * @return the complete URL of the current page if a page context can be
	 *         determined
	 */
	public String getLayoutFullURL() {
		return _layoutFullURL;
	}

	/**
	 * Returns the relative URL of the current page if a page context can be
	 * determined.
	 * 
	 * @return the relative URL of the current page if a page context can be
	 *         determined
	 */
	public String getLayoutURL() {
		return _layoutURL;
	}

	/**
	 * Returns the <code>Date</code> when an entity was modified if the
	 * <code>ServiceContext</code> is being passed as a parameter to a method
	 * which updates an entity.
	 * 
	 * @return the <code>Date</code> when an entity was modified if the
	 *         <code>ServiceContext</code> is being passed as a parameter to a
	 *         method which updates an entity
	 */
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	/**
	 * Returns the <code>Date</code> when an entity was modified if the
	 * <code>ServiceContext</code> is being passed as a parameter to a method
	 * which modifies an entity.
	 * 
	 * <p>
	 * Can provide a default <code>Date</code> to use if the
	 * <code>ServiceContext</code> does not already have a modified date.
	 * </p>
	 * 
	 * @param defaultModifiedDate
	 *            an optional default modified date to use if the
	 *            <code>ServiceContext</code> does not have a modified date
	 * @return the <code>Date</code> when an entity was modified if the
	 *         <code>ServiceContext</code> is being passed as a parameter to a
	 *         method which modifies an entity
	 */
	public Date getModifiedDate(Date defaultModifiedDate) {
		if (_modifiedDate != null) {
			return _modifiedDate;
		} else if (defaultModifiedDate != null) {
			return defaultModifiedDate;
		} else {
			return new Date();
		}
	}

	/**
	 * Returns the main context path of the portal, concatenated with
	 * <code>/c</code>.
	 * 
	 * @return the main context path of the portal, concatenated with
	 *         <code>/c</code>
	 */
	public String getPathMain() {
		return _pathMain;
	}

	/**
	 * Returns the id of the current page.
	 * 
	 * @return the id of the current page
	 */
	public long getPlid() {
		return _plid;
	}

	/**
	 * Returns the URL of the portal, including the protocol, domain, and
	 * nondefault port relative to the company instance and any virtual host.
	 * 
	 * <p>
	 * The URL returned does not include the port if a default port is used.
	 * </p>
	 * 
	 * @return the URL of the portal, including the protocol, domain, and
	 *         nondefault port relative to company instance and any virtual host
	 */
	public String getPortalURL() {
		return _portalURL;
	}

	/**
	 * Returns the TODO something matching condition
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @return Returns the TODO something matching condition
	 */
	public String getPortletId() {
		if (_portletPreferencesIds != null) {
			return _portletPreferencesIds.getPortletId();
		} else {
			return null;
		}
	}

	/**
	 * Returns the <code>PortletPreferencesIds</code> of the current portlet if
	 * the <code>ServiceContext</code> is being passed as a parameter to a
	 * portlet.
	 * 
	 * The <code>PortletPreferencesIds</code> can be used to look up portlet
	 * preferences of the current portlet.
	 * 
	 * @return the <code>PortletPreferencesIds</code> of the current portlet if
	 *         the <code>ServiceContext</code> is being passed as a parameter to
	 *         a portlet
	 */
	public PortletPreferencesIds getPortletPreferencesIds() {
		return _portletPreferencesIds;
	}

	/**
	 * Returns the TODO something matching condition
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @return Returns the TODO something matching condition
	 */
	public String getRemoteAddr() {
		return _remoteAddr;
	}

	/**
	 * Returns the TODO something matching condition
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @return Returns the TODO something matching condition
	 */
	public String getRemoteHost() {
		return _remoteHost;
	}

	/**
	 * Returns the id of the <code>Group</code> corresponding to the current
	 * data scope.
	 * 
	 * @return the id of the <code>Group</code> corresponding to the current
	 *         data scope
	 */
	public long getScopeGroupId() {
		return _scopeGroupId;
	}

	/**
	 * Returns the TODO something matching condition
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @return Returns the TODO something matching condition
	 */
	public String getUserAgent() {
		if (_headers == null) {
			return null;
		}

		return _headers.get(HttpHeaders.USER_AGENT);
	}

	/**
	 * Returns the complete URL of the current user's profile page.
	 * 
	 * @return the complete URL of the current user's profile page
	 */
	public String getUserDisplayURL() {
		return _userDisplayURL;
	}

	/**
	 * Returns the id of the current user.
	 * 
	 * @return the id of the current user
	 */
	public long getUserId() {
		return _userId;
	}

	/**
	 * Returns the uuid (universally unique identifier) of the current entity.
	 * 
	 * @return the uuid (universally unique identifier) of the current entity
	 */
	public String getUuid() {
		String uuid = _uuid;

		_uuid = null;

		return uuid;
	}

	/**
	 * Returns the TODO something matching condition
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @return Returns the TODO something matching condition
	 */
	public int getWorkflowAction() {
		return _workflowAction;
	}

	/**
	 * Returns <code>true</code> if TODO some condition
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @return <code>true</code> if TODO some condition is met;
	 *         <code>false</code> otherwise
	 */
	public boolean isCommandAdd() {
		if (Validator.equals(_command, Constants.ADD)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if TODO some condition
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @return <code>true</code> if TODO some condition is met;
	 *         <code>false</code> otherwise
	 */
	public boolean isCommandUpdate() {
		if (Validator.equals(_command, Constants.UPDATE)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if TODO some condition
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @return <code>true</code> if TODO some condition is met;
	 *         <code>false</code> otherwise
	 */
	public boolean isIndexingEnabled() {
		return _indexingEnabled;
	}

	/**
	 * Returns <code>true</code> if TODO some condition
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @return <code>true</code> if TODO some condition is met;
	 *         <code>false</code> otherwise
	 */
	public boolean isSignedIn() {
		return _signedIn;
	}

	/**
	 * TODO Initial description of method's purpose.
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @param name
	 *            the TODO name, default and acceptable values? (optionally
	 *            <code>null</code>)
	 * @return the TODO something, any special return values?
	 */
	public Serializable removeAttribute(String name) {
		return _attributes.remove(name);
	}

	/**
	 * Sets whether or not default community permissions should apply to a
	 * resource being manipulated by a method to which the
	 * <code>ServiceContext</code> is passed as a parameter.
	 * 
	 * @param addCommunityPermissions
	 *            indicates whether or not to apply default community
	 *            permissions
	 * @deprecated As of 6.1, renamed to
	 *             {@link #setAddGroupPermissions(boolean)}
	 */
	public void setAddCommunityPermissions(boolean addCommunityPermissions) {
		setAddGroupPermissions(addCommunityPermissions);
	}

	/**
	 * Sets whether or not default group permissions should apply to a resource
	 * being manipulated by a method to which the <code>ServiceContext</code> is
	 * passed as a parameter.
	 * 
	 * @param addGroupPermissions
	 *            indicates whether or not to apply default group permissions
	 */
	public void setAddGroupPermissions(boolean addGroupPermissions) {
		_addGroupPermissions = addGroupPermissions;
	}

	/**
	 * Sets whether or not default guest permissions should apply to a resource
	 * being manipulated by a method to which the <code>ServiceContext</code> is
	 * passed as a parameter.
	 * 
	 * @param addGuestPermissions
	 *            indicates whether or not to apply default guest permissions
	 */
	public void setAddGuestPermissions(boolean addGuestPermissions) {
		_addGuestPermissions = addGuestPermissions;
	}

	/**
	 * Sets an <code>Array</code> of asset category ids to be applied to an
	 * asset entry if the <code>ServiceContext</code> is being passed as a
	 * parameter to a method which manipulates the asset entry.
	 * 
	 * @param assetCategoryIds
	 *            the primary keys of the asset categories
	 */
	public void setAssetCategoryIds(long[] assetCategoryIds) {
		_assetCategoryIds = assetCategoryIds;
	}

	/**
	 * TODO Initial description of method's purpose.
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @param _assetLinkEntryIds
	 *            the primary keys of the TODO _asset link entrys
	 */
	public void setAssetLinkEntryIds(long[] _assetLinkEntryIds) {
		this._assetLinkEntryIds = _assetLinkEntryIds;
	}

	/**
	 * Sets an <code>Array</code> of asset tag names to be applied to an asset
	 * entry if the <code>ServiceContext</code> is being passed as a parameter
	 * to a method which manipulates the asset entry.
	 * 
	 * @param assetTagNames
	 *            the tag names (optionally <code>null</code>)
	 */
	public void setAssetTagNames(String[] assetTagNames) {
		_assetTagNames = assetTagNames;
	}

	/**
	 * TODO Initial description of method's purpose.
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @param name
	 *            the TODO name, default and acceptable values? (optionally
	 *            <code>null</code>)
	 * @param value
	 *            the TODO value, default and acceptable values? (optionally
	 *            <code>null</code>)
	 */
	public void setAttribute(String name, Serializable value) {
		_attributes.put(name, value);
	}

	/**
	 * TODO Initial description of method's purpose.
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @param attributes
	 *            the TODO attributes, default and acceptable values?
	 *            (optionally <code>null</code>)
	 */
	public void setAttributes(Map<String, Serializable> attributes) {
		_attributes = attributes;
	}

	/**
	 * TODO Initial description of method's purpose.
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @param command
	 *            the TODO command, default and acceptable values? (optionally
	 *            <code>null</code>)
	 */
	public void setCommand(String command) {
		_command = command;
	}

	/**
	 * Sets an <code>Array</code> containing specific community permissions for
	 * a resource if the <code>ServiceContext</code> is being passed as a
	 * parameter to a method which manipulates the resource.
	 * 
	 * @param communityPermissions
	 *            the community permissions (optionally <code>null</code>)
	 * @deprecated As of 6.1, renamed to {@link #setGroupPermissions(String[])}
	 */
	public void setCommunityPermissions(String[] communityPermissions) {
		setGroupPermissions(communityPermissions);
	}

	/**
	 * Sets the <code>_companyId</code> of the current portal instance.
	 * 
	 * @param companyId
	 *            the primary key of the current portal instance
	 */
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	/**
	 * Sets the <code>Date</code> when an entity was created if the
	 * <code>ServiceContext</code> is being passed as a parameter to a method
	 * which creates an entity.
	 * 
	 * @param createDate
	 *            the create date (optionally <code>null</code>)
	 */
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	/**
	 * TODO Initial description of method's purpose.
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @param currentURL
	 *            the TODO current URL, default and acceptable values?
	 *            (optionally <code>null</code>)
	 */
	public void setCurrentURL(String currentURL) {
		_currentURL = currentURL;
	}

	/**
	 * Sets an arbitrary number of attributes of an entity to be persisted.
	 * 
	 * <p>
	 * These attributes only include fields that the <code>ServiceContext</code>
	 * does not possess by default.
	 * </p>
	 * 
	 * @param expandoBridgeAttributes
	 *            the expando bridge attributes (optionally <code>null</code>)
	 */
	public void setExpandoBridgeAttributes(
			Map<String, Serializable> expandoBridgeAttributes) {

		_expandoBridgeAttributes = expandoBridgeAttributes;
	}

	/**
	 * Sets an <code>Array</code> containing specific group permissions for a
	 * resource if the <code>ServiceContext</code> is being passed as a
	 * parameter to a method which manipulates the resource.
	 * 
	 * @param groupPermissions
	 *            the permissions (optionally <code>null</code>)
	 */
	public void setGroupPermissions(String[] groupPermissions) {
		_groupPermissions = groupPermissions;
	}

	/**
	 * Sets an <code>Array</code> containing specific guest permissions for a
	 * resource if the <code>ServiceContext</code> is being passed as a
	 * parameter to a method which manipulates the resource.
	 * 
	 * @param guestPermissions
	 *            the guest permissions (optionally <code>null</code>)
	 */
	public void setGuestPermissions(String[] guestPermissions) {
		_guestPermissions = guestPermissions;
	}

	/**
	 * TODO Initial description of method's purpose.
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @param headers
	 *            the TODO headers, default and acceptable values? (optionally
	 *            <code>null</code>)
	 */
	public void setHeaders(Map<String, String> headers) {
		_headers = headers;
	}

	/**
	 * TODO Initial description of method's purpose.
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @param indexingEnabled
	 *            whether to TODO do something
	 */
	public void setIndexingEnabled(boolean indexingEnabled) {
		_indexingEnabled = indexingEnabled;
	}

	/**
	 * Sets the current user <code>Locale</code> as a <code>String</code>.
	 * 
	 * @param languageId
	 *            the <code>String</code> to set the user <code>Locale</code>
	 */
	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	/**
	 * Sets the complete URL of the current page if a page context can be
	 * determined.
	 * 
	 * @param layoutFullURL
	 *            the full URL (optionally <code>null</code>)
	 */
	public void setLayoutFullURL(String layoutFullURL) {
		_layoutFullURL = layoutFullURL;
	}

	/**
	 * Returns the relative URL of the current page if a page context can be
	 * determined.
	 * 
	 * @param layoutURL
	 *            the relative URL (optionally <code>null</code>)
	 */
	public void setLayoutURL(String layoutURL) {
		_layoutURL = layoutURL;
	}

	/**
	 * Sets the <code>Date</code> when an entity was modified if the
	 * <code>ServiceContext</code> is being passed as a parameter to a method
	 * which updates an entity.
	 * 
	 * @param modifiedDate
	 *            the modified date (optionally <code>null</code>)
	 */
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	/**
	 * Sets the main context path of the portal, concatenated with
	 * <code>/c</code>.
	 * 
	 * @param pathMain
	 *            the main context path of the portal (optionally
	 *            <code>null</code>)
	 */
	public void setPathMain(String pathMain) {
		_pathMain = pathMain;
	}

	/**
	 * Sets the id of the current page.
	 * 
	 * @param plid
	 *            the portal layout id (optionally <code>0</code>)
	 */
	public void setPlid(long plid) {
		_plid = plid;
	}

	/**
	 * Sets the URL of the portal, including the protocol, domain, and
	 * nondefault port relative to the company instance and any virtual host.
	 * 
	 * <p>
	 * The URL should not include the port if a default port is used.
	 * </p>
	 * 
	 * @param portalURL
	 *            the portal URL (optionally <code>null</code>)
	 */
	public void setPortalURL(String portalURL) {
		_portalURL = portalURL;
	}

	/**
	 * Sets the <code>PortletPreferencesIds</code> of the current portlet if the
	 * <code>ServiceContext</code> is being passed as a parameter to a portlet.
	 * 
	 * The <code>PortletPreferencesIds</code> can be used to look up portlet
	 * preferences of the current portlet.
	 * 
	 * @param portletPreferencesIds
	 *            the portlet preferences
	 */
	public void setPortletPreferencesIds(
			PortletPreferencesIds portletPreferencesIds) {

		_portletPreferencesIds = portletPreferencesIds;
	}

	/**
	 * TODO Initial description of method's purpose.
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @param remoteAddr
	 *            the TODO remote addr, default and acceptable values?
	 *            (optionally <code>null</code>)
	 */
	public void setRemoteAddr(String remoteAddr) {
		_remoteAddr = remoteAddr;
	}

	/**
	 * TODO Initial description of method's purpose.
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @param remoteHost
	 *            the TODO remote host, default and acceptable values?
	 *            (optionally <code>null</code>)
	 */
	public void setRemoteHost(String remoteHost) {
		_remoteHost = remoteHost;
	}

	/**
	 * Sets the id of the <code>Group</code> corresponding to the current data
	 * scope.
	 * 
	 * @param scopeGroupId
	 *            the id of the <code>Group</code> corresponding to the current
	 *            data scope
	 */
	public void setScopeGroupId(long scopeGroupId) {
		_scopeGroupId = scopeGroupId;
	}

	/**
	 * TODO Initial description of method's purpose.
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @param signedIn
	 *            whether to TODO do something
	 */
	public void setSignedIn(boolean signedIn) {
		_signedIn = signedIn;
	}

	/**
	 * Sets the complete URL of the current user's profile page.
	 * 
	 * @param userDisplayURL
	 *            the complete URL of the current user's profile page
	 */
	public void setUserDisplayURL(String userDisplayURL) {
		_userDisplayURL = userDisplayURL;
	}

	/**
	 * Returns the id of the current user.
	 * 
	 * @param userId
	 *            the id of the current user
	 */
	public void setUserId(long userId) {
		_userId = userId;
	}

	/**
	 * Returns the uuid (universally unique identifier) of the current entity.
	 * 
	 * @param uuid
	 *            the uuid (universally unique identifier) of the current entity
	 *            (optionally <code>null</code>)
	 */
	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	/**
	 * TODO Initial description of method's purpose.
	 * 
	 * TODO Detailed description, reference links and/or examples (optional).
	 * 
	 * @param workflowAction
	 *            the TODO workflow action, default and acceptable values?
	 *            (optionally <code>0</code>)
	 */
	public void setWorkflowAction(int workflowAction) {
		_workflowAction = workflowAction;
	}

	private boolean _addGroupPermissions;
	private boolean _addGuestPermissions;
	private long[] _assetCategoryIds;
	private long[] _assetLinkEntryIds;
	private String[] _assetTagNames;
	private Map<String, Serializable> _attributes;
	private String _command;
	private long _companyId;
	private Date _createDate;
	private String _currentURL;
	private Map<String, Serializable> _expandoBridgeAttributes;
	private String[] _groupPermissions;
	private String[] _guestPermissions;
	private Map<String, String> _headers;
	private boolean _indexingEnabled = true;
	private String _languageId;
	private String _layoutFullURL;
	private String _layoutURL;
	private Date _modifiedDate;
	private String _pathMain;
	private String _portalURL;
	private PortletPreferencesIds _portletPreferencesIds;
	private String _remoteAddr;
	private String _remoteHost;
	private long _scopeGroupId;
	private boolean _signedIn;
	private String _userDisplayURL;
	private long _plid;
	private int _workflowAction = WorkflowConstants.ACTION_PUBLISH;
	private long _userId;
	private String _uuid;

}