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

import com.liferay.portal.PortletIdException;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.SpriteProcessor;
import com.liferay.portal.kernel.image.SpriteProcessorUtil;
import com.liferay.portal.kernel.lar.DefaultConfigurationPortletDataHandler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.spring.aop.Skip;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.EventDefinition;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletFilter;
import com.liferay.portal.model.PortletInfo;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.impl.EventDefinitionImpl;
import com.liferay.portal.model.impl.PortletAppImpl;
import com.liferay.portal.model.impl.PortletFilterImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.model.impl.PortletURLListenerImpl;
import com.liferay.portal.model.impl.PublicRenderParameterImpl;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.base.PortletLocalServiceBaseImpl;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletCategoryKeys;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletConfigFactoryUtil;
import com.liferay.portlet.PortletContextFactory;
import com.liferay.portlet.PortletInstanceFactoryUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletQNameUtil;
import com.liferay.portlet.expando.model.CustomAttributesDisplay;
import com.liferay.util.ContentUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletMode;
import javax.portlet.PreferencesValidator;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 * @author Wesley Gong
 * @author Shuyang Zhou
 */
public class PortletLocalServiceImpl extends PortletLocalServiceBaseImpl {

	@Override
	@Skip
	public void addPortletCategory(long companyId, String categoryName) {
		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
			companyId, WebKeys.PORTLET_CATEGORY);

		if (portletCategory == null) {
			_log.error(
				"Unable to add portlet category for company " + companyId +
					" because it does not exist");

			return;
		}

		PortletCategory newPortletCategory = new PortletCategory(categoryName);

		if (newPortletCategory.getParentCategory() == null) {
			PortletCategory rootPortletCategory = new PortletCategory();

			rootPortletCategory.addCategory(newPortletCategory);
		}

		portletCategory.merge(newPortletCategory.getRootCategory());
	}

	@Override
	public void checkPortlet(Portlet portlet)
		throws PortalException, SystemException {

		if (portlet.isSystem()) {
			return;
		}

		String[] roleNames = portlet.getRolesArray();

		if (roleNames.length == 0) {
			return;
		}

		long companyId = portlet.getCompanyId();
		String name = portlet.getPortletId();
		int scope = ResourceConstants.SCOPE_COMPANY;
		String primKey = String.valueOf(companyId);
		String actionId = ActionKeys.ADD_TO_PAGE;

		List<String> actionIds = ResourceActionsUtil.getPortletResourceActions(
			name);

		if (actionIds.contains(actionId)) {
			for (String roleName : roleNames) {
				Role role = roleLocalService.getRole(companyId, roleName);

				if (resourceBlockLocalService.isSupported(name)) {
					resourceBlockLocalService.addCompanyScopePermission(
						companyId, name, role.getRoleId(), actionId);
				}
				else {
					resourcePermissionLocalService.addResourcePermission(
						companyId, name, scope, primKey, role.getRoleId(),
						actionId);
				}
			}
		}

		updatePortlet(
			companyId, portlet.getPortletId(), StringPool.BLANK,
			portlet.isActive());
	}

	@Override
	public void checkPortlets(long companyId)
		throws PortalException, SystemException {

		List<Portlet> portlets = getPortlets(companyId);

		for (Portlet portlet : portlets) {
			checkPortlet(portlet);
		}
	}

	@Override
	@Skip
	public void clearCache() {

		// Refresh security path to portlet id mapping for all portlets

		_portletIdsByStrutsPath.clear();

		// Refresh company portlets

		portletLocalService.clearCompanyPortletsPool();
	}

	@Clusterable
	@Override
	@Transactional(enabled = false)
	public void clearCompanyPortletsPool() {
		_companyPortletsPool.clear();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #clonePortlet(String)}
	 */
	@Deprecated
	@Override
	@Skip
	public Portlet clonePortlet(long companyId, String portletId) {
		return clonePortlet(portletId);
	}

	@Override
	@Skip
	public Portlet clonePortlet(String portletId) {
		Portlet portlet = getPortletById(portletId);

		return (Portlet)portlet.clone();
	}

	@Override
	public void deletePortlet(long companyId, String portletId, long plid)
		throws PortalException, SystemException {

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		resourceLocalService.deleteResource(
			companyId, rootPortletId, ResourceConstants.SCOPE_INDIVIDUAL,
			PortletPermissionUtil.getPrimaryKey(plid, portletId));

		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		if (PortletConstants.hasUserId(portletId)) {
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}

		List<PortletPreferences> portletPreferencesList =
			portletPreferencesLocalService.getPortletPreferences(
				ownerType, plid, portletId);

		Portlet portlet = getPortletById(companyId, portletId);

		PortletLayoutListener portletLayoutListener = null;

		if (portlet != null) {
			portletLayoutListener = portlet.getPortletLayoutListenerInstance();

			PortletInstanceFactoryUtil.delete(portlet);
		}

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			if (portletLayoutListener != null) {
				portletLayoutListener.onRemoveFromLayout(
					portletPreferences.getPortletId(), plid);
			}

			portletPreferencesLocalService.deletePortletPreferences(
				portletPreferences.getPortletPreferencesId());
		}
	}

	@Override
	public void deletePortlets(long companyId, String[] portletIds, long plid)
		throws PortalException, SystemException {

		for (String portletId : portletIds) {
			deletePortlet(companyId, portletId, plid);
		}
	}

	@Override
	public Portlet deployRemotePortlet(Portlet portlet, String categoryName)
		throws PortalException, SystemException {

		return deployRemotePortlet(portlet, new String[] {categoryName});
	}

	@Override
	public Portlet deployRemotePortlet(Portlet portlet, String[] categoryNames)
		throws PortalException, SystemException {

		Map<String, Portlet> portletsPool = _getPortletsPool();

		portletsPool.put(portlet.getPortletId(), portlet);

		PortletInstanceFactoryUtil.clear(portlet, false);

		PortletConfigFactoryUtil.destroy(portlet);

		clearCache();

		List<String> portletActions =
			ResourceActionsUtil.getPortletResourceActions(
				portlet.getPortletId());

		resourceActionLocalService.checkResourceActions(
			portlet.getPortletId(), portletActions);

		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
			portlet.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		if (portletCategory == null) {
			_log.error(
				"Unable to register remote portlet for company " +
					portlet.getCompanyId() + " because it does not exist");

			return portlet;
		}

		portletCategory.separate(portlet.getPortletId());

		for (String categoryName : categoryNames) {
			PortletCategory newPortletCategory = new PortletCategory(
				categoryName);

			if (newPortletCategory.getParentCategory() == null) {
				PortletCategory rootPortletCategory = new PortletCategory();

				rootPortletCategory.addCategory(newPortletCategory);
			}

			Set<String> portletIds = newPortletCategory.getPortletIds();

			portletIds.add(portlet.getPortletId());

			portletCategory.merge(newPortletCategory.getRootCategory());
		}

		checkPortlet(portlet);

		return portlet;
	}

	@Override
	@Skip
	public void destroyPortlet(Portlet portlet) {
		String portletId = portlet.getRootPortletId();

		_friendlyURLMapperPortlets.remove(portletId);

		Map<String, Portlet> portletsPool = _getPortletsPool();

		portletsPool.remove(portletId);

		PortletApp portletApp = portlet.getPortletApp();

		if (portletApp != null) {
			_portletAppsPool.remove(portletApp.getServletContextName());
		}

		clearCache();
	}

	@Override
	@Skip
	public void destroyRemotePortlet(Portlet portlet) {
		destroyPortlet(portlet);
	}

	@Override
	@Skip
	public List<CustomAttributesDisplay> getCustomAttributesDisplays() {
		List<CustomAttributesDisplay> customAttributesDisplays =
			new ArrayList<CustomAttributesDisplay>(
				_customAttributesDisplayPortlets.size());

		for (Map.Entry<String, Portlet> entry :
				_customAttributesDisplayPortlets.entrySet()) {

			Portlet portlet = entry.getValue();

			List<CustomAttributesDisplay> portletCustomAttributesDisplays =
				portlet.getCustomAttributesDisplayInstances();

			if ((portletCustomAttributesDisplays != null) &&
				!portletCustomAttributesDisplays.isEmpty()) {

				customAttributesDisplays.addAll(
					portletCustomAttributesDisplays);
			}
		}

		return customAttributesDisplays;
	}

	@Override
	@Skip
	public PortletCategory getEARDisplay(String xml) throws SystemException {
		try {
			return _readLiferayDisplayXML(xml);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	@Skip
	public List<Portlet> getFriendlyURLMapperPortlets() {
		List<Portlet> portlets = new ArrayList<Portlet>(
			_friendlyURLMapperPortlets.size());

		for (Map.Entry<String, Portlet> entry :
				_friendlyURLMapperPortlets.entrySet()) {

			Portlet portlet = entry.getValue();

			FriendlyURLMapper friendlyURLMapper =
				portlet.getFriendlyURLMapperInstance();

			if (friendlyURLMapper != null) {
				portlets.add(portlet);
			}
		}

		return portlets;
	}

	@Override
	@Skip
	public List<FriendlyURLMapper> getFriendlyURLMappers() {
		List<FriendlyURLMapper> friendlyURLMappers =
			new ArrayList<FriendlyURLMapper>(_friendlyURLMapperPortlets.size());

		for (Map.Entry<String, Portlet> entry :
				_friendlyURLMapperPortlets.entrySet()) {

			Portlet portlet = entry.getValue();

			FriendlyURLMapper friendlyURLMapper =
				portlet.getFriendlyURLMapperInstance();

			if (friendlyURLMapper != null) {
				friendlyURLMappers.add(friendlyURLMapper);
			}
		}

		return friendlyURLMappers;
	}

	@Override
	@Skip
	public PortletApp getPortletApp(String servletContextName) {
		return _getPortletApp(servletContextName);
	}

	@Override
	@Skip
	public Portlet getPortletById(long companyId, String portletId)
		throws SystemException {

		portletId = PortalUtil.getJsSafePortletId(portletId);

		Portlet portlet = null;

		Map<String, Portlet> companyPortletsPool = _getPortletsPool(companyId);

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		if (portletId.equals(rootPortletId)) {
			portlet = companyPortletsPool.get(portletId);
		}
		else {
			portlet = companyPortletsPool.get(rootPortletId);

			if (portlet != null) {
				portlet = portlet.getClonedInstance(portletId);
			}
		}

		if (portlet != null) {
			return portlet;
		}

		if (portletId.equals(PortletKeys.LIFERAY_PORTAL)) {
			return portlet;
		}

		if (_portletsPool.isEmpty()) {
			if (_log.isDebugEnabled()) {
				_log.debug("No portlets are installed");
			}
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Portlet not found for " + companyId + " " + portletId);
			}

			portlet = new PortletImpl(CompanyConstants.SYSTEM, portletId);

			portlet.setTimestamp(System.currentTimeMillis());

			PortletApp portletApp = _getPortletApp(StringPool.BLANK);

			portlet.setPortletApp(portletApp);

			portlet.setPortletName(portletId);
			portlet.setDisplayName(portletId);
			portlet.setPortletClass(MVCPortlet.class.getName());

			Map<String, String> initParams = portlet.getInitParams();

			initParams.put("view-jsp", "/html/portal/undeployed_portlet.jsp");

			Set<String> mimeTypePortletModes = new HashSet<String>();

			mimeTypePortletModes.add(
				StringUtil.toLowerCase(PortletMode.VIEW.toString()));

			Map<String, Set<String>> portletModes = portlet.getPortletModes();

			portletModes.put(ContentTypes.TEXT_HTML, mimeTypePortletModes);

			Set<String> mimeTypeWindowStates = new HashSet<String>();

			mimeTypeWindowStates.add(
				StringUtil.toLowerCase(WindowState.NORMAL.toString()));

			Map<String, Set<String>> windowStates = portlet.getWindowStates();

			windowStates.put(ContentTypes.TEXT_HTML, mimeTypeWindowStates);

			portlet.setPortletInfo(
				new PortletInfo(portletId, portletId, portletId, portletId));

			if (PortletConstants.hasInstanceId(portletId)) {
				portlet.setInstanceable(true);
			}

			portlet.setActive(true);
			portlet.setUndeployedPortlet(true);
		}

		return portlet;
	}

	@Override
	@Skip
	public Portlet getPortletById(String portletId) {
		Map<String, Portlet> portletsPool = _getPortletsPool();

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		return portletsPool.get(rootPortletId);
	}

	@Override
	@Skip
	public Portlet getPortletByStrutsPath(long companyId, String strutsPath)
		throws SystemException {

		return getPortletById(companyId, _getPortletId(strutsPath));
	}

	@Override
	@Skip
	public List<Portlet> getPortlets() {
		Map<String, Portlet> portletsPool = _getPortletsPool();

		return ListUtil.fromMapValues(portletsPool);
	}

	@Override
	@Skip
	public List<Portlet> getPortlets(long companyId) throws SystemException {
		return getPortlets(companyId, true, true);
	}

	@Override
	@Skip
	public List<Portlet> getPortlets(
			long companyId, boolean showSystem, boolean showPortal)
		throws SystemException {

		Map<String, Portlet> portletsPool = _getPortletsPool(companyId);

		List<Portlet> portlets = ListUtil.fromMapValues(portletsPool);

		if (showSystem && showPortal) {
			return portlets;
		}

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			if (showPortal &&
				portlet.getPortletId().equals(PortletKeys.PORTAL)) {
			}
			else if (!showPortal &&
					 portlet.getPortletId().equals(PortletKeys.PORTAL)) {

				itr.remove();
			}
			else if (!showSystem && portlet.isSystem()) {
				itr.remove();
			}
		}

		return portlets;
	}

	@Override
	@Skip
	public List<Portlet> getScopablePortlets() {
		Map<String, Portlet> portletsPool = _getPortletsPool();

		List<Portlet> portlets = ListUtil.fromMapValues(portletsPool);

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			if (!portlet.isScopeable()) {
				itr.remove();
			}
		}

		return portlets;
	}

	@Override
	@Skip
	public PortletCategory getWARDisplay(String servletContextName, String xml)
		throws SystemException {

		try {
			return _readLiferayDisplayXML(servletContextName, xml);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	@Skip
	public boolean hasPortlet(long companyId, String portletId)
		throws SystemException {

		portletId = PortalUtil.getJsSafePortletId(portletId);

		Portlet portlet = null;

		Map<String, Portlet> companyPortletsPool = _getPortletsPool(companyId);

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		if (portletId.equals(rootPortletId)) {
			portlet = companyPortletsPool.get(portletId);
		}
		else {
			portlet = companyPortletsPool.get(rootPortletId);
		}

		if (portlet == null) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	@Skip
	public void initEAR(
		ServletContext servletContext, String[] xmls,
		PluginPackage pluginPackage) {

		// Clear pools every time initEAR is called. See LEP-5452.

		portletLocalService.clearCompanyPortletsPool();

		_portletAppsPool.clear();
		_portletsPool.clear();
		_portletIdsByStrutsPath.clear();
		_friendlyURLMapperPortlets.clear();

		Map<String, Portlet> portletsPool = _getPortletsPool();

		try {
			Set<String> servletURLPatterns = _readWebXML(xmls[4]);

			Set<String> portletIds = _readPortletXML(
				servletContext, xmls[0], portletsPool, servletURLPatterns,
				pluginPackage);

			portletIds.addAll(
				_readPortletXML(
					servletContext, xmls[1], portletsPool, servletURLPatterns,
					pluginPackage));

			Set<String> liferayPortletIds = _readLiferayPortletXML(
				xmls[2], portletsPool);

			liferayPortletIds.addAll(
				_readLiferayPortletXML(xmls[3], portletsPool));

			// Check for missing entries in liferay-portlet.xml

			for (String portletId : portletIds) {
				if (_log.isWarnEnabled() &&
					!liferayPortletIds.contains(portletId)) {

					_log.warn(
						"Portlet with the name " + portletId +
							" is described in portlet.xml but does not " +
								"have a matching entry in liferay-portlet.xml");
				}
			}

			// Check for missing entries in portlet.xml

			for (String portletId : liferayPortletIds) {
				if (_log.isWarnEnabled() && !portletIds.contains(portletId)) {
					_log.warn(
						"Portlet with the name " + portletId +
							" is described in liferay-portlet.xml but does " +
								"not have a matching entry in portlet.xml");
				}
			}

			// Remove portlets that should not be included

			Iterator<Map.Entry<String, Portlet>> portletPoolsItr =
				portletsPool.entrySet().iterator();

			while (portletPoolsItr.hasNext()) {
				Map.Entry<String, Portlet> entry = portletPoolsItr.next();

				Portlet portletModel = entry.getValue();

				if (!portletModel.getPortletId().equals(PortletKeys.ADMIN) &&
					!portletModel.getPortletId().equals(
						PortletKeys.MY_ACCOUNT) &&
					!portletModel.isInclude()) {

					portletPoolsItr.remove();

					_friendlyURLMapperPortlets.remove(
						portletModel.getPortletId());
				}
			}

			// Sprite images

			PortletApp portletApp = _getPortletApp(StringPool.BLANK);

			_setSpriteImages(servletContext, portletApp, "/html/icons/");
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	@Skip
	public List<Portlet> initWAR(
		String servletContextName, ServletContext servletContext, String[] xmls,
		PluginPackage pluginPackage) {

		List<Portlet> portlets = new ArrayList<Portlet>();

		Map<String, Portlet> portletsPool = _getPortletsPool();

		try {
			Set<String> servletURLPatterns = _readWebXML(xmls[3]);

			Set<String> portletIds = _readPortletXML(
				servletContextName, servletContext, xmls[0], portletsPool,
				servletURLPatterns, pluginPackage);

			portletIds.addAll(
				_readPortletXML(
					servletContextName, servletContext, xmls[1], portletsPool,
					servletURLPatterns, pluginPackage));

			Set<String> liferayPortletIds = _readLiferayPortletXML(
				servletContextName, xmls[2], portletsPool);

			// Check for missing entries in liferay-portlet.xml

			for (String portletId : portletIds) {
				if (_log.isWarnEnabled() &&
					!liferayPortletIds.contains(portletId)) {

					_log.warn(
						"Portlet with the name " + portletId +
							" is described in portlet.xml but does not " +
								"have a matching entry in liferay-portlet.xml");
				}
			}

			// Check for missing entries in portlet.xml

			for (String portletId : liferayPortletIds) {
				if (_log.isWarnEnabled() && !portletIds.contains(portletId)) {
					_log.warn(
						"Portlet with the name " + portletId +
							" is described in liferay-portlet.xml but does " +
								"not have a matching entry in portlet.xml");
				}
			}

			// Return the new portlets

			for (String portletId : portletIds) {
				Portlet portlet = _getPortletsPool().get(portletId);

				portlets.add(portlet);

				PortletInstanceFactoryUtil.clear(portlet);

				PortletConfigFactoryUtil.destroy(portlet);
				PortletContextFactory.destroy(portlet);
			}

			// Sprite images

			PortletApp portletApp = _getPortletApp(servletContextName);

			_setSpriteImages(servletContext, portletApp, "/icons/");
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		clearCache();

		return portlets;
	}

	@Override
	public Map<String, Portlet> loadGetPortletsPool(long companyId)
		throws SystemException {

		Map<String, Portlet> portletsPool =
			new ConcurrentHashMap<String, Portlet>();

		Map<String, Portlet> parentPortletsPool = _getPortletsPool();

		if (parentPortletsPool == null) {

			// The Upgrade scripts sometimes try to access portlet preferences
			// before the portal's been initialized. Return an empty pool.

			return portletsPool;
		}

		for (Portlet portlet : parentPortletsPool.values()) {
			portlet = (Portlet)portlet.clone();

			portlet.setCompanyId(companyId);

			portletsPool.put(portlet.getPortletId(), portlet);
		}

		List<Portlet> portlets = portletPersistence.findByCompanyId(companyId);

		for (Portlet portlet : portlets) {
			Portlet portletModel = portletsPool.get(portlet.getPortletId());

			// Portlet may be null if it exists in the database but its portlet
			// WAR is not yet loaded

			if (portletModel != null) {
				portletModel.setPluginPackage(portlet.getPluginPackage());
				portletModel.setDefaultPluginSetting(
					portlet.getDefaultPluginSetting());
				portletModel.setRoles(portlet.getRoles());
				portletModel.setActive(portlet.getActive());
			}
		}

		return portletsPool;
	}

	@Clusterable
	@Override
	@Transactional(enabled = false)
	public void removeCompanyPortletsPool(long companyId) {
		_companyPortletsPool.remove(companyId);
	}

	@Override
	public Portlet updatePortlet(
			long companyId, String portletId, String roles, boolean active)
		throws SystemException {

		portletId = PortalUtil.getJsSafePortletId(portletId);

		Portlet portlet = portletPersistence.fetchByC_P(companyId, portletId);

		if (portlet == null) {
			long id = counterLocalService.increment();

			portlet = portletPersistence.create(id);

			portlet.setCompanyId(companyId);
			portlet.setPortletId(portletId);
		}

		portlet.setRoles(roles);
		portlet.setActive(active);

		portletPersistence.update(portlet);

		portlet = getPortletById(companyId, portletId);

		portlet.setRoles(roles);
		portlet.setActive(active);

		portletLocalService.removeCompanyPortletsPool(companyId);

		return portlet;
	}

	private PortletApp _getPortletApp(String servletContextName) {
		PortletApp portletApp = _portletAppsPool.get(servletContextName);

		if (portletApp == null) {
			portletApp = new PortletAppImpl(servletContextName);

			_portletAppsPool.put(servletContextName, portletApp);
		}

		return portletApp;
	}

	private String _getPortletId(String securityPath) {
		if (_portletIdsByStrutsPath.isEmpty()) {
			for (Portlet portlet : _getPortletsPool().values()) {
				String strutsPath = portlet.getStrutsPath();

				if (_portletIdsByStrutsPath.containsKey(strutsPath)) {
					if (_log.isWarnEnabled()) {
						_log.warn("Duplicate struts path " + strutsPath);
					}
				}

				_portletIdsByStrutsPath.put(strutsPath, portlet.getPortletId());
			}
		}

		String portletId = _portletIdsByStrutsPath.get(securityPath);

		if (Validator.isNull(portletId)) {
			for (String strutsPath : _portletIdsByStrutsPath.keySet()) {
				if (securityPath.startsWith(
						strutsPath.concat(StringPool.SLASH))) {

					portletId = _portletIdsByStrutsPath.get(strutsPath);

					break;
				}
			}
		}

		if (Validator.isNull(portletId)) {
			_log.error(
				"Struts path " + securityPath + " is not mapped to a portlet " +
					"in liferay-portlet.xml");
		}

		return portletId;
	}

	private List<Portlet> _getPortletsByPortletName(
		String portletName, String servletContextName,
		Map<String, Portlet> portletsPool) {

		List<Portlet> portlets = null;

		int pos = portletName.indexOf(CharPool.STAR);

		if (pos == -1) {
			portlets = new ArrayList<Portlet>();

			String portletId = portletName;

			if (Validator.isNotNull(servletContextName)) {
				portletId =
					portletId + PortletConstants.WAR_SEPARATOR +
						servletContextName;
			}

			portletId = PortalUtil.getJsSafePortletId(portletId);

			Portlet portlet = portletsPool.get(portletId);

			if (portlet != null) {
				portlets.add(portlet);
			}

			return portlets;
		}

		String portletNamePrefix = portletName.substring(0, pos);

		portlets = _getPortletsByServletContextName(
			servletContextName, portletsPool);

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			String portletId = portlet.getPortletId();

			if (!portletId.startsWith(portletNamePrefix)) {
				itr.remove();
			}
		}

		return portlets;
	}

	private List<Portlet> _getPortletsByServletContextName(
		String servletContextName, Map<String, Portlet> portletsPool) {

		List<Portlet> portlets = new ArrayList<Portlet>();

		String servletContextNameSuffix = servletContextName;

		if (Validator.isNotNull(servletContextName)) {
			servletContextNameSuffix = PortalUtil.getJsSafePortletId(
				PortletConstants.WAR_SEPARATOR.concat(servletContextName));
		}

		for (Map.Entry<String, Portlet> entry : portletsPool.entrySet()) {
			String portletId = entry.getKey();
			Portlet portlet = entry.getValue();

			if (Validator.isNotNull(servletContextNameSuffix)) {
				if (portletId.endsWith(servletContextNameSuffix)) {
					portlets.add(portlet);
				}
			}
			else {
				if (!portletId.contains(PortletConstants.WAR_SEPARATOR)) {
					portlets.add(portlet);
				}
			}
		}

		return portlets;
	}

	private Map<String, Portlet> _getPortletsPool() {
		return _portletsPool;
	}

	private Map<String, Portlet> _getPortletsPool(long companyId)
		throws SystemException {

		Map<String, Portlet> portletsPool = _companyPortletsPool.get(companyId);

		if (portletsPool == null) {
			portletsPool = portletLocalService.loadGetPortletsPool(companyId);

			_companyPortletsPool.put(companyId, portletsPool);
		}

		return portletsPool;
	}

	private void _readLiferayDisplay(
		String servletContextName, Element element,
		PortletCategory portletCategory, Set<String> portletIds) {

		for (Element categoryElement : element.elements("category")) {
			String name = categoryElement.attributeValue("name");

			PortletCategory curPortletCategory = new PortletCategory(name);

			portletCategory.addCategory(curPortletCategory);

			Set<String> curPortletIds = curPortletCategory.getPortletIds();

			for (Element portletElement : categoryElement.elements("portlet")) {
				String portletId = portletElement.attributeValue("id");

				if (Validator.isNotNull(servletContextName)) {
					portletId =
						portletId + PortletConstants.WAR_SEPARATOR +
							servletContextName;
				}

				portletId = PortalUtil.getJsSafePortletId(portletId);

				portletIds.add(portletId);
				curPortletIds.add(portletId);
			}

			_readLiferayDisplay(
				servletContextName, categoryElement, curPortletCategory,
				portletIds);
		}
	}

	private PortletCategory _readLiferayDisplayXML(String xml)
		throws Exception {

		return _readLiferayDisplayXML(null, xml);
	}

	private PortletCategory _readLiferayDisplayXML(
			String servletContextName, String xml)
		throws Exception {

		PortletCategory portletCategory = new PortletCategory();

		if (xml == null) {
			xml = ContentUtil.get(
				"com/liferay/portal/deploy/dependencies/liferay-display.xml");
		}

		Document document = SAXReaderUtil.read(xml, true);

		Element rootElement = document.getRootElement();

		Set<String> portletIds = new HashSet<String>();

		_readLiferayDisplay(
			servletContextName, rootElement, portletCategory, portletIds);

		// Portlets that do not belong to any categories should default to the
		// Undefined category

		Set<String> undefinedPortletIds = new HashSet<String>();

		for (Portlet portlet : _getPortletsPool().values()) {
			String portletId = portlet.getPortletId();

			PortletApp portletApp = portlet.getPortletApp();

			if ((servletContextName != null) && portletApp.isWARFile() &&
				portletId.endsWith(
					PortletConstants.WAR_SEPARATOR +
						PortalUtil.getJsSafePortletId(servletContextName)) &&
				!portletIds.contains(portletId)) {

				undefinedPortletIds.add(portletId);
			}
			else if ((servletContextName == null) &&
					 !portletApp.isWARFile() &&
					 !portletId.contains(PortletConstants.WAR_SEPARATOR) &&
					 !portletIds.contains(portletId)) {

				undefinedPortletIds.add(portletId);
			}
		}

		if (!undefinedPortletIds.isEmpty()) {
			PortletCategory undefinedCategory = new PortletCategory(
				"category.undefined");

			portletCategory.addCategory(undefinedCategory);

			undefinedCategory.getPortletIds().addAll(undefinedPortletIds);
		}

		return portletCategory;
	}

	private Set<String> _readLiferayPortletXML(
			String xml, Map<String, Portlet> portletsPool)
		throws Exception {

		return _readLiferayPortletXML(StringPool.BLANK, xml, portletsPool);
	}

	private void _readLiferayPortletXML(
		String servletContextName, Map<String, Portlet> portletsPool,
		Set<String> liferayPortletIds, Map<String, String> roleMappers,
		Element portletElement) {

		String portletId = portletElement.elementText("portlet-name");

		if (Validator.isNotNull(servletContextName)) {
			portletId = portletId.concat(PortletConstants.WAR_SEPARATOR).concat(
				servletContextName);
		}

		portletId = PortalUtil.getJsSafePortletId(portletId);

		if (_log.isDebugEnabled()) {
			_log.debug("Reading portlet extension " + portletId);
		}

		liferayPortletIds.add(portletId);

		Portlet portletModel = portletsPool.get(portletId);

		if (portletModel == null) {
			return;
		}

		portletModel.setIcon(
			GetterUtil.getString(
				portletElement.elementText("icon"), portletModel.getIcon()));
		portletModel.setVirtualPath(
			GetterUtil.getString(
				portletElement.elementText("virtual-path"),
				portletModel.getVirtualPath()));
		portletModel.setStrutsPath(
			GetterUtil.getString(
				portletElement.elementText("struts-path"),
				portletModel.getStrutsPath()));

		String strutsPath = portletModel.getStrutsPath();

		if (Validator.isNotNull(strutsPath)) {
			if (_portletIdsByStrutsPath.containsKey(strutsPath)) {
				String strutsPathPortletId = _portletIdsByStrutsPath.get(
					strutsPath);

				if (!strutsPathPortletId.equals(portletId)) {
					if (_log.isWarnEnabled()) {
						_log.warn("Duplicate struts path " + strutsPath);
					}
				}
			}

			_portletIdsByStrutsPath.put(strutsPath, portletId);
		}

		portletModel.setParentStrutsPath(
			GetterUtil.getString(
				portletElement.elementText("parent-struts-path"),
				portletModel.getParentStrutsPath()));

		if (Validator.isNotNull(
				portletElement.elementText("configuration-path"))) {

			_log.error(
				"The configuration-path element is no longer supported. Use " +
					"configuration-action-class instead.");
		}

		portletModel.setConfigurationActionClass(
			GetterUtil.getString(
				portletElement.elementText("configuration-action-class"),
				portletModel.getConfigurationActionClass()));

		List<String> indexerClasses = new ArrayList<String>();

		for (Element indexerClassElement :
				portletElement.elements("indexer-class")) {

			indexerClasses.add(indexerClassElement.getText());
		}

		portletModel.setIndexerClasses(indexerClasses);

		portletModel.setOpenSearchClass(
			GetterUtil.getString(
				portletElement.elementText("open-search-class"),
				portletModel.getOpenSearchClass()));

		for (Element schedulerEntryElement :
				portletElement.elements("scheduler-entry")) {

			SchedulerEntry schedulerEntry = new SchedulerEntryImpl();

			schedulerEntry.setDescription(
				GetterUtil.getString(
					schedulerEntryElement.elementText(
						"scheduler-description")));
			schedulerEntry.setEventListenerClass(
				GetterUtil.getString(
					schedulerEntryElement.elementText(
						"scheduler-event-listener-class"),
					schedulerEntry.getEventListenerClass()));

			Element triggerElement = schedulerEntryElement.element("trigger");

			Element cronElement = triggerElement.element("cron");
			Element simpleElement = triggerElement.element("simple");

			if (cronElement != null) {
				schedulerEntry.setTriggerType(TriggerType.CRON);

				Element propertyKeyElement = cronElement.element(
					"property-key");

				if (propertyKeyElement != null) {
					schedulerEntry.setPropertyKey(
						propertyKeyElement.getTextTrim());
				}
				else {
					schedulerEntry.setTriggerValue(
						cronElement.elementText("cron-trigger-value"));
				}
			}
			else if (simpleElement != null) {
				schedulerEntry.setTriggerType(TriggerType.SIMPLE);

				Element propertyKeyElement = simpleElement.element(
					"property-key");

				if (propertyKeyElement != null) {
					schedulerEntry.setPropertyKey(
						propertyKeyElement.getTextTrim());
				}
				else {
					Element simpleTriggerValueElement = simpleElement.element(
						"simple-trigger-value");

					schedulerEntry.setTriggerValue(
						simpleTriggerValueElement.getTextTrim());
				}

				String timeUnit = GetterUtil.getString(
					simpleElement.elementText("time-unit"),
					TimeUnit.SECOND.getValue());

				schedulerEntry.setTimeUnit(
					TimeUnit.parse(StringUtil.toLowerCase(timeUnit)));
			}

			portletModel.addSchedulerEntry(schedulerEntry);
		}

		portletModel.setPortletURLClass(
			GetterUtil.getString(
				portletElement.elementText("portlet-url-class"),
				portletModel.getPortletURLClass()));

		portletModel.setFriendlyURLMapperClass(
			GetterUtil.getString(
				portletElement.elementText("friendly-url-mapper-class"),
				portletModel.getFriendlyURLMapperClass()));

		if (Validator.isNull(portletModel.getFriendlyURLMapperClass())) {
			_friendlyURLMapperPortlets.remove(portletId);
		}
		else {
			_friendlyURLMapperPortlets.put(portletId, portletModel);
		}

		portletModel.setFriendlyURLMapping(
			GetterUtil.getString(
				portletElement.elementText("friendly-url-mapping"),
				portletModel.getFriendlyURLMapping()));
		portletModel.setFriendlyURLRoutes(
			GetterUtil.getString(
				portletElement.elementText("friendly-url-routes"),
				portletModel.getFriendlyURLRoutes()));
		portletModel.setURLEncoderClass(
			GetterUtil.getString(
				portletElement.elementText("url-encoder-class"),
				portletModel.getURLEncoderClass()));

		String portletDataHandlerClass = GetterUtil.getString(
			portletElement.elementText("portlet-data-handler-class"),
			portletModel.getPortletDataHandlerClass());

		if (Validator.isNull(portletDataHandlerClass)) {
			portletDataHandlerClass =
				DefaultConfigurationPortletDataHandler.class.getName();
		}

		portletModel.setPortletDataHandlerClass(portletDataHandlerClass);

		List<String> stagedModelDataHandlerClasses = new ArrayList<String>();

		for (Element stagedModelDataHandlerClassElement :
				portletElement.elements("staged-model-data-handler-class")) {

			stagedModelDataHandlerClasses.add(
				stagedModelDataHandlerClassElement.getText());
		}

		portletModel.setStagedModelDataHandlerClasses(
			stagedModelDataHandlerClasses);

		portletModel.setTemplateHandlerClass(
			GetterUtil.getString(
				portletElement.elementText("template-handler"),
				portletModel.getTemplateHandlerClass()));
		portletModel.setPortletLayoutListenerClass(
			GetterUtil.getString(
				portletElement.elementText("portlet-layout-listener-class"),
				portletModel.getPortletLayoutListenerClass()));
		portletModel.setPollerProcessorClass(
			GetterUtil.getString(
				portletElement.elementText("poller-processor-class"),
				portletModel.getPollerProcessorClass()));
		portletModel.setPopMessageListenerClass(
			GetterUtil.getString(
				portletElement.elementText("pop-message-listener-class"),
				portletModel.getPopMessageListenerClass()));

		List<String> socialActivityInterpreterClasses = new ArrayList<String>();

		for (Element socialActivityInterpreterClassElement :
				portletElement.elements("social-activity-interpreter-class")) {

			socialActivityInterpreterClasses.add(
				socialActivityInterpreterClassElement.getText());
		}

		portletModel.setSocialActivityInterpreterClasses(
			socialActivityInterpreterClasses);

		portletModel.setSocialRequestInterpreterClass(
			GetterUtil.getString(
				portletElement.elementText("social-request-interpreter-class"),
				portletModel.getSocialRequestInterpreterClass()));
		portletModel.setUserNotificationDefinitions(
			GetterUtil.getString(
				portletElement.elementText("user-notification-definitions"),
				portletModel.getUserNotificationDefinitions()));

		List<String> userNotificationHandlerClasses = new ArrayList<String>();

		for (Element userNotificationHandlerClassElement :
				portletElement.elements(
					"user-notification-handler-class")) {

			userNotificationHandlerClasses.add(
				userNotificationHandlerClassElement.getText());
		}

		portletModel.setUserNotificationHandlerClasses(
			userNotificationHandlerClasses);

		portletModel.setWebDAVStorageToken(
			GetterUtil.getString(
				portletElement.elementText("webdav-storage-token"),
				portletModel.getWebDAVStorageToken()));
		portletModel.setWebDAVStorageClass(
			GetterUtil.getString(
				portletElement.elementText("webdav-storage-class"),
				portletModel.getWebDAVStorageClass()));
		portletModel.setXmlRpcMethodClass(
			GetterUtil.getString(
				portletElement.elementText("xml-rpc-method-class"),
				portletModel.getXmlRpcMethodClass()));

		String controlPanelEntryCategory = GetterUtil.getString(
			portletElement.elementText("control-panel-entry-category"),
			portletModel.getControlPanelEntryCategory());

		if (Validator.equals(controlPanelEntryCategory, "content")) {
			controlPanelEntryCategory =
				PortletCategoryKeys.SITE_ADMINISTRATION_CONTENT;
		}
		else if (Validator.equals(controlPanelEntryCategory, "marketplace")) {
			controlPanelEntryCategory = PortletCategoryKeys.APPS;
		}
		else if (Validator.equals(controlPanelEntryCategory, "portal")) {
			controlPanelEntryCategory = PortletCategoryKeys.USERS;
		}
		else if (Validator.equals(controlPanelEntryCategory, "server")) {
			controlPanelEntryCategory = PortletCategoryKeys.APPS;
		}

		portletModel.setControlPanelEntryCategory(controlPanelEntryCategory);

		portletModel.setControlPanelEntryWeight(
			GetterUtil.getDouble(
				portletElement.elementText("control-panel-entry-weight"),
				portletModel.getControlPanelEntryWeight()));
		portletModel.setControlPanelEntryClass(
			GetterUtil.getString(
				portletElement.elementText("control-panel-entry-class"),
				portletModel.getControlPanelEntryClass()));

		List<String> assetRendererFactoryClasses = new ArrayList<String>();

		for (Element assetRendererFactoryClassElement :
				portletElement.elements("asset-renderer-factory")) {

			assetRendererFactoryClasses.add(
				assetRendererFactoryClassElement.getText());
		}

		portletModel.setAssetRendererFactoryClasses(
			assetRendererFactoryClasses);

		List<String> atomCollectionAdapterClasses = new ArrayList<String>();

		for (Element atomCollectionAdapterClassElement :
				portletElement.elements("atom-collection-adapter")) {

			atomCollectionAdapterClasses.add(
				atomCollectionAdapterClassElement.getText());
		}

		portletModel.setAtomCollectionAdapterClasses(
			atomCollectionAdapterClasses);

		List<String> customAttributesDisplayClasses = new ArrayList<String>();

		for (Element customAttributesDisplayClassElement :
				portletElement.elements("custom-attributes-display")) {

			customAttributesDisplayClasses.add(
				customAttributesDisplayClassElement.getText());
		}

		portletModel.setCustomAttributesDisplayClasses(
			customAttributesDisplayClasses);

		if (customAttributesDisplayClasses.isEmpty()) {
			_customAttributesDisplayPortlets.remove(portletId);
		}
		else {
			_customAttributesDisplayPortlets.put(portletId, portletModel);
		}

		portletModel.setDDMDisplayClass(
			GetterUtil.getString(
				portletElement.elementText("ddm-display"),
				portletModel.getDDMDisplayClass()));
		portletModel.setPermissionPropagatorClass(
			GetterUtil.getString(
				portletElement.elementText("permission-propagator"),
				portletModel.getPermissionPropagatorClass()));

		List<String> trashHandlerClasses = new ArrayList<String>();

		for (Element trashHandlerClassElement :
				portletElement.elements("trash-handler")) {

			trashHandlerClasses.add(trashHandlerClassElement.getText());
		}

		portletModel.setTrashHandlerClasses(trashHandlerClasses);

		List<String> workflowHandlerClasses = new ArrayList<String>();

		for (Element workflowHandlerClassElement :
				portletElement.elements("workflow-handler")) {

			workflowHandlerClasses.add(workflowHandlerClassElement.getText());
		}

		portletModel.setWorkflowHandlerClasses(workflowHandlerClasses);

		portletModel.setPreferencesCompanyWide(
			GetterUtil.getBoolean(
				portletElement.elementText("preferences-company-wide"),
				portletModel.isPreferencesCompanyWide()));
		portletModel.setPreferencesUniquePerLayout(
			GetterUtil.getBoolean(
				portletElement.elementText("preferences-unique-per-layout"),
				portletModel.isPreferencesUniquePerLayout()));
		portletModel.setPreferencesOwnedByGroup(
			GetterUtil.getBoolean(
				portletElement.elementText("preferences-owned-by-group"),
				portletModel.isPreferencesOwnedByGroup()));
		portletModel.setUseDefaultTemplate(
			GetterUtil.getBoolean(
				portletElement.elementText("use-default-template"),
				portletModel.isUseDefaultTemplate()));
		portletModel.setShowPortletAccessDenied(
			GetterUtil.getBoolean(
				portletElement.elementText("show-portlet-access-denied"),
				portletModel.isShowPortletAccessDenied()));
		portletModel.setShowPortletInactive(
			GetterUtil.getBoolean(
				portletElement.elementText("show-portlet-inactive"),
				portletModel.isShowPortletInactive()));
		portletModel.setActionURLRedirect(
			GetterUtil.getBoolean(
				portletElement.elementText("action-url-redirect"),
				portletModel.isActionURLRedirect()));
		portletModel.setRestoreCurrentView(
			GetterUtil.getBoolean(
				portletElement.elementText("restore-current-view"),
				portletModel.isRestoreCurrentView()));
		portletModel.setMaximizeEdit(
			GetterUtil.getBoolean(
				portletElement.elementText("maximize-edit"),
				portletModel.isMaximizeEdit()));
		portletModel.setMaximizeHelp(
			GetterUtil.getBoolean(
				portletElement.elementText("maximize-help"),
				portletModel.isMaximizeHelp()));
		portletModel.setPopUpPrint(
			GetterUtil.getBoolean(
				portletElement.elementText("pop-up-print"),
				portletModel.isPopUpPrint()));
		portletModel.setLayoutCacheable(
			GetterUtil.getBoolean(
				portletElement.elementText("layout-cacheable"),
				portletModel.isLayoutCacheable()));
		portletModel.setInstanceable(
			GetterUtil.getBoolean(
				portletElement.elementText("instanceable"),
				portletModel.isInstanceable()));
		portletModel.setRemoteable(
			GetterUtil.getBoolean(
				portletElement.elementText("remoteable"),
				portletModel.isRemoteable()));
		portletModel.setScopeable(
			GetterUtil.getBoolean(
				portletElement.elementText("scopeable"),
				portletModel.isScopeable()));
		portletModel.setUserPrincipalStrategy(
			GetterUtil.getString(
				portletElement.elementText("user-principal-strategy"),
				portletModel.getUserPrincipalStrategy()));
		portletModel.setPrivateRequestAttributes(
			GetterUtil.getBoolean(
				portletElement.elementText("private-request-attributes"),
				portletModel.isPrivateRequestAttributes()));
		portletModel.setPrivateSessionAttributes(
			GetterUtil.getBoolean(
				portletElement.elementText("private-session-attributes"),
				portletModel.isPrivateSessionAttributes()));

		Element autopropagatedParametersElement = portletElement.element(
			"autopropagated-parameters");

		Set<String> autopropagatedParameters = new HashSet<String>();

		if (autopropagatedParametersElement != null) {
			String[] autopropagatedParametersArray = StringUtil.split(
				autopropagatedParametersElement.getText());

			for (String autopropagatedParameter :
					autopropagatedParametersArray) {

				autopropagatedParameters.add(autopropagatedParameter);
			}
		}

		portletModel.setAutopropagatedParameters(autopropagatedParameters);

		portletModel.setRequiresNamespacedParameters(
			GetterUtil.getBoolean(
				portletElement.elementText("requires-namespaced-parameters"),
				portletModel.isRequiresNamespacedParameters()));
		portletModel.setActionTimeout(
			GetterUtil.getInteger(
				portletElement.elementText("action-timeout"),
				portletModel.getActionTimeout()));
		portletModel.setRenderTimeout(
			GetterUtil.getInteger(
				portletElement.elementText("render-timeout"),
				portletModel.getRenderTimeout()));
		portletModel.setRenderWeight(
			GetterUtil.getInteger(
				portletElement.elementText("render-weight"),
				portletModel.getRenderWeight()));
		portletModel.setAjaxable(
			GetterUtil.getBoolean(
				portletElement.elementText("ajaxable"),
				portletModel.isAjaxable()));

		List<String> headerPortalCssList = new ArrayList<String>();

		for (Element headerPortalCssElement :
				portletElement.elements("header-portal-css")) {

			headerPortalCssList.add(headerPortalCssElement.getText());
		}

		portletModel.setHeaderPortalCss(headerPortalCssList);

		List<String> headerPortletCssList = new ArrayList<String>();

		for (Element headerPortletCssElement :
				portletElement.elements("header-portlet-css")) {

			headerPortletCssList.add(headerPortletCssElement.getText());
		}

		portletModel.setHeaderPortletCss(headerPortletCssList);

		List<String> headerPortalJavaScriptList = new ArrayList<String>();

		for (Element headerPortalJavaScriptElement :
				portletElement.elements("header-portal-javascript")) {

			headerPortalJavaScriptList.add(
				headerPortalJavaScriptElement.getText());
		}

		portletModel.setHeaderPortalJavaScript(headerPortalJavaScriptList);

		List<String> headerPortletJavaScriptList = new ArrayList<String>();

		for (Element headerPortletJavaScriptElement :
				portletElement.elements("header-portlet-javascript")) {

			headerPortletJavaScriptList.add(
				headerPortletJavaScriptElement.getText());
		}

		portletModel.setHeaderPortletJavaScript(headerPortletJavaScriptList);

		List<String> footerPortalCssList = new ArrayList<String>();

		for (Element footerPortalCssElement :
				portletElement.elements("footer-portal-css")) {

			footerPortalCssList.add(footerPortalCssElement.getText());
		}

		portletModel.setFooterPortalCss(footerPortalCssList);

		List<String> footerPortletCssList = new ArrayList<String>();

		for (Element footerPortletCssElement :
				portletElement.elements("footer-portlet-css")) {

			footerPortletCssList.add(footerPortletCssElement.getText());
		}

		portletModel.setFooterPortletCss(footerPortletCssList);

		List<String> footerPortalJavaScriptList = new ArrayList<String>();

		for (Element footerPortalJavaScriptElement :
				portletElement.elements("footer-portal-javascript")) {

			footerPortalJavaScriptList.add(
				footerPortalJavaScriptElement.getText());
		}

		portletModel.setFooterPortalJavaScript(footerPortalJavaScriptList);

		List<String> footerPortletJavaScriptList = new ArrayList<String>();

		for (Element footerPortletJavaScriptElement :
				portletElement.elements("footer-portlet-javascript")) {

			footerPortletJavaScriptList.add(
				footerPortletJavaScriptElement.getText());
		}

		portletModel.setFooterPortletJavaScript(footerPortletJavaScriptList);

		portletModel.setCssClassWrapper(
			GetterUtil.getString(
				portletElement.elementText("css-class-wrapper"),
				portletModel.getCssClassWrapper()));
		portletModel.setFacebookIntegration(
			GetterUtil.getString(
				portletElement.elementText("facebook-integration"),
				portletModel.getFacebookIntegration()));
		portletModel.setAddDefaultResource(
			GetterUtil.getBoolean(
				portletElement.elementText("add-default-resource"),
				portletModel.isAddDefaultResource()));
		portletModel.setSystem(
			GetterUtil.getBoolean(
				portletElement.elementText("system"), portletModel.isSystem()));
		portletModel.setActive(
			GetterUtil.getBoolean(
				portletElement.elementText("active"), portletModel.isActive()));
		portletModel.setInclude(
			GetterUtil.getBoolean(portletElement.elementText("include"),
			portletModel.isInclude()));

		if (Validator.isNull(servletContextName)) {
			portletModel.setReady(true);
		}

		if (!portletModel.isAjaxable() &&
			(portletModel.getRenderWeight() < 1)) {

			portletModel.setRenderWeight(1);
		}

		portletModel.getRoleMappers().putAll(roleMappers);
		portletModel.linkRoles();
	}

	private Set<String> _readLiferayPortletXML(
			String servletContextName, String xml,
			Map<String, Portlet> portletsPool)
		throws Exception {

		Set<String> liferayPortletIds = new HashSet<String>();

		if (xml == null) {
			return liferayPortletIds;
		}

		Document document = SAXReaderUtil.read(xml, true);

		Element rootElement = document.getRootElement();

		PortletApp portletApp = _getPortletApp(servletContextName);

		Map<String, String> roleMappers = new HashMap<String, String>();

		for (Element roleMapperElement : rootElement.elements("role-mapper")) {
			String roleName = roleMapperElement.elementText("role-name");
			String roleLink = roleMapperElement.elementText("role-link");

			roleMappers.put(roleName, roleLink);
		}

		Map<String, String> customUserAttributes =
			portletApp.getCustomUserAttributes();

		for (Element customUserAttributeElement :
				rootElement.elements("custom-user-attribute")) {

			String customClass = customUserAttributeElement.elementText(
				"custom-class");

			for (Element nameElement :
					customUserAttributeElement.elements("name")) {

				String name = nameElement.getText();

				customUserAttributes.put(name, customClass);
			}
		}

		for (Element portletElement : rootElement.elements("portlet")) {
			_readLiferayPortletXML(
				servletContextName, portletsPool, liferayPortletIds,
				roleMappers, portletElement);
		}

		return liferayPortletIds;
	}

	private Set<String> _readPortletXML(
			ServletContext servletContext, String xml,
			Map<String, Portlet> portletsPool, Set<String> servletURLPatterns,
			PluginPackage pluginPackage)
		throws Exception {

		return _readPortletXML(
			StringPool.BLANK, servletContext, xml, portletsPool,
			servletURLPatterns, pluginPackage);
	}

	private void _readPortletXML(
			String servletContextName, Map<String, Portlet> portletsPool,
			PluginPackage pluginPackage, PortletApp portletApp,
			Set<String> portletIds, long timestamp, Element portletElement)
		throws PortletIdException {

		String portletName = portletElement.elementText("portlet-name");

		String portletId = portletName;

		if (Validator.isNotNull(servletContextName)) {
			portletId = portletId.concat(PortletConstants.WAR_SEPARATOR).concat(
				servletContextName);
		}

		portletId = PortalUtil.getJsSafePortletId(portletId);

		if (portletId.length() > _PORTLET_ID_MAX_LENGTH) {

			// LPS-32878

			throw new PortletIdException(
				"Portlet id " + portletId + " has more than " +
					_PORTLET_ID_MAX_LENGTH + " characters");
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Reading portlet " + portletId);
		}

		portletIds.add(portletId);

		Portlet portletModel = portletsPool.get(portletId);

		if (portletModel == null) {
			portletModel = new PortletImpl(CompanyConstants.SYSTEM, portletId);

			portletsPool.put(portletId, portletModel);
		}

		portletModel.setTimestamp(timestamp);

		portletModel.setPluginPackage(pluginPackage);
		portletModel.setPortletApp(portletApp);

		portletModel.setPortletName(portletName);
		portletModel.setDisplayName(
			GetterUtil.getString(
				portletElement.elementText("display-name"),
				portletModel.getDisplayName()));
		portletModel.setPortletClass(
			GetterUtil.getString(portletElement.elementText("portlet-class")));

		Map<String, String> initParams = new HashMap<String, String>();

		for (Element initParamElement : portletElement.elements("init-param")) {
			initParams.put(
				initParamElement.elementText("name"),
				initParamElement.elementText("value"));
		}

		portletModel.setInitParams(initParams);

		Element expirationCacheElement = portletElement.element(
			"expiration-cache");

		if (expirationCacheElement != null) {
			portletModel.setExpCache(
				GetterUtil.getInteger(expirationCacheElement.getText()));
		}

		Map<String, Set<String>> portletModes =
			new HashMap<String, Set<String>>();
		Map<String, Set<String>> windowStates =
			new HashMap<String, Set<String>>();

		for (Element supportsElement : portletElement.elements("supports")) {
			String mimeType = supportsElement.elementText("mime-type");

			Set<String> mimeTypePortletModes = new HashSet<String>();

			mimeTypePortletModes.add(
				StringUtil.toLowerCase(PortletMode.VIEW.toString()));

			for (Element portletModeElement :
					supportsElement.elements("portlet-mode")) {

				mimeTypePortletModes.add(
					StringUtil.toLowerCase(portletModeElement.getTextTrim()));
			}

			portletModes.put(mimeType, mimeTypePortletModes);

			Set<String> mimeTypeWindowStates = new HashSet<String>();

			mimeTypeWindowStates.add(
				StringUtil.toLowerCase(WindowState.NORMAL.toString()));

			List<Element> windowStateElements = supportsElement.elements(
				"window-state");

			if (windowStateElements.isEmpty()) {
				mimeTypeWindowStates.add(
					StringUtil.toLowerCase(WindowState.MAXIMIZED.toString()));
				mimeTypeWindowStates.add(
					StringUtil.toLowerCase(WindowState.MINIMIZED.toString()));
				mimeTypeWindowStates.add(
					StringUtil.toLowerCase(
						LiferayWindowState.EXCLUSIVE.toString()));
				mimeTypeWindowStates.add(
					StringUtil.toLowerCase(
						LiferayWindowState.POP_UP.toString()));
			}

			for (Element windowStateElement : windowStateElements) {
				mimeTypeWindowStates.add(
					StringUtil.toLowerCase(windowStateElement.getTextTrim()));
			}

			windowStates.put(mimeType, mimeTypeWindowStates);
		}

		portletModel.setPortletModes(portletModes);
		portletModel.setWindowStates(windowStates);

		Set<String> supportedLocales = new HashSet<String>();

		//supportedLocales.add(
		//	LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

		for (Element supportedLocaleElement : portletElement.elements(
				"supported-locale")) {

			String supportedLocale = supportedLocaleElement.getText();

			supportedLocales.add(supportedLocale);
		}

		portletModel.setSupportedLocales(supportedLocales);

		portletModel.setResourceBundle(
			portletElement.elementText("resource-bundle"));

		Element portletInfoElement = portletElement.element("portlet-info");

		String portletInfoTitle = null;
		String portletInfoShortTitle = null;
		String portletInfoKeyWords = null;
		String portletInfoDescription = null;

		if (portletInfoElement != null) {
			portletInfoTitle = portletInfoElement.elementText("title");
			portletInfoShortTitle = portletInfoElement.elementText(
				"short-title");
			portletInfoKeyWords = portletInfoElement.elementText("keywords");
		}

		PortletInfo portletInfo = new PortletInfo(
			portletInfoTitle, portletInfoShortTitle, portletInfoKeyWords,
			portletInfoDescription);

		portletModel.setPortletInfo(portletInfo);

		Element portletPreferencesElement = portletElement.element(
			"portlet-preferences");

		String defaultPreferences = null;
		String preferencesValidator = null;

		if (portletPreferencesElement != null) {
			Element preferencesValidatorElement =
				portletPreferencesElement.element("preferences-validator");

			if (preferencesValidatorElement != null) {
				preferencesValidator = preferencesValidatorElement.getText();

				portletPreferencesElement.remove(preferencesValidatorElement);
			}

			defaultPreferences = portletPreferencesElement.asXML();
		}

		portletModel.setDefaultPreferences(defaultPreferences);
		portletModel.setPreferencesValidator(preferencesValidator);

		if (!portletApp.isWARFile() &&
			Validator.isNotNull(preferencesValidator) &&
			PropsValues.PREFERENCE_VALIDATE_ON_STARTUP) {

			try {
				PreferencesValidator preferencesValidatorObj =
					PortalUtil.getPreferencesValidator(portletModel);

				preferencesValidatorObj.validate(
					PortletPreferencesFactoryUtil.fromDefaultXML(
						defaultPreferences));
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Portlet with the name " + portletId +
							" does not have valid default preferences");
				}
			}
		}

		Set<String> unlinkedRoles = new HashSet<String>();

		for (Element roleElement :
				portletElement.elements("security-role-ref")) {

			unlinkedRoles.add(roleElement.elementText("role-name"));
		}

		portletModel.setUnlinkedRoles(unlinkedRoles);

		Set<QName> processingEvents = new HashSet<QName>();

		for (Element supportedProcessingEventElement :
				portletElement.elements("supported-processing-event")) {

			Element qNameElement = supportedProcessingEventElement.element(
				"qname");
			Element nameElement = supportedProcessingEventElement.element(
				"name");

			QName qName = PortletQNameUtil.getQName(
				qNameElement, nameElement, portletApp.getDefaultNamespace());

			processingEvents.add(qName);

			Set<EventDefinition> eventDefinitions =
				portletApp.getEventDefinitions();

			for (EventDefinition eventDefinition : eventDefinitions) {
				Set<QName> qNames = eventDefinition.getQNames();

				if (qNames.contains(qName)) {
					processingEvents.addAll(qNames);
				}
			}
		}

		portletModel.setProcessingEvents(processingEvents);

		Set<QName> publishingEvents = new HashSet<QName>();

		for (Element supportedPublishingEventElement :
				portletElement.elements("supported-publishing-event")) {

			Element qNameElement = supportedPublishingEventElement.element(
				"qname");
			Element nameElement = supportedPublishingEventElement.element(
				"name");

			QName qName = PortletQNameUtil.getQName(
				qNameElement, nameElement, portletApp.getDefaultNamespace());

			publishingEvents.add(qName);
		}

		portletModel.setPublishingEvents(publishingEvents);

		Set<PublicRenderParameter> publicRenderParameters =
			new HashSet<PublicRenderParameter>();

		for (Element supportedPublicRenderParameter :
				portletElement.elements("supported-public-render-parameter")) {

			String identifier = supportedPublicRenderParameter.getTextTrim();

			PublicRenderParameter publicRenderParameter =
				portletApp.getPublicRenderParameter(identifier);

			if (publicRenderParameter == null) {
				_log.error(
					"Supported public render parameter references " +
						"unnknown identifier " + identifier);

				continue;
			}

			publicRenderParameters.add(publicRenderParameter);
		}

		portletModel.setPublicRenderParameters(publicRenderParameters);
	}

	private Set<String> _readPortletXML(
			String servletContextName, ServletContext servletContext,
			String xml, Map<String, Portlet> portletsPool,
			Set<String> servletURLPatterns, PluginPackage pluginPackage)
		throws Exception {

		Set<String> portletIds = new HashSet<String>();

		if (xml == null) {
			return portletIds;
		}

		Document document = SAXReaderUtil.read(
			xml, PropsValues.PORTLET_XML_VALIDATE);

		Element rootElement = document.getRootElement();

		PortletApp portletApp = _getPortletApp(servletContextName);

		portletApp.addServletURLPatterns(servletURLPatterns);

		Set<String> userAttributes = portletApp.getUserAttributes();

		for (Element userAttributeElement :
				rootElement.elements("user-attribute")) {

			String name = userAttributeElement.elementText("name");

			userAttributes.add(name);
		}

		String defaultNamespace = rootElement.elementText("default-namespace");

		if (Validator.isNotNull(defaultNamespace)) {
			portletApp.setDefaultNamespace(defaultNamespace);
		}

		for (Element eventDefinitionElement :
				rootElement.elements("event-definition")) {

			Element qNameElement = eventDefinitionElement.element("qname");
			Element nameElement = eventDefinitionElement.element("name");
			String valueType = eventDefinitionElement.elementText("value-type");

			QName qName = PortletQNameUtil.getQName(
				qNameElement, nameElement, portletApp.getDefaultNamespace());

			EventDefinition eventDefinition = new EventDefinitionImpl(
				qName, valueType, portletApp);

			List<Element> aliases = eventDefinitionElement.elements("alias");

			for (Element alias : aliases) {
				qName = PortletQNameUtil.getQName(
					alias, null, portletApp.getDefaultNamespace());

				eventDefinition.addAliasQName(qName);
			}

			portletApp.addEventDefinition(eventDefinition);
		}

		for (Element publicRenderParameterElement :
				rootElement.elements("public-render-parameter")) {

			String identifier = publicRenderParameterElement.elementText(
				"identifier");
			Element qNameElement = publicRenderParameterElement.element(
				"qname");
			Element nameElement = publicRenderParameterElement.element("name");

			QName qName = PortletQNameUtil.getQName(
				qNameElement, nameElement, portletApp.getDefaultNamespace());

			PublicRenderParameter publicRenderParameter =
				new PublicRenderParameterImpl(identifier, qName, portletApp);

			portletApp.addPublicRenderParameter(publicRenderParameter);
		}

		for (Element containerRuntimeOptionElement :
				rootElement.elements("container-runtime-option")) {

			String name = GetterUtil.getString(
				containerRuntimeOptionElement.elementText("name"));

			List<String> values = new ArrayList<String>();

			for (Element valueElement :
					containerRuntimeOptionElement.elements("value")) {

				values.add(valueElement.getTextTrim());
			}

			Map<String, String[]> containerRuntimeOptions =
				portletApp.getContainerRuntimeOptions();

			containerRuntimeOptions.put(
				name, values.toArray(new String[values.size()]));

			if (name.equals(
					LiferayPortletConfig.RUNTIME_OPTION_PORTAL_CONTEXT) &&
				!values.isEmpty() && GetterUtil.getBoolean(values.get(0))) {

				portletApp.setWARFile(false);
			}
		}

		long timestamp = ServletContextUtil.getLastModified(servletContext);

		for (Element portletElement : rootElement.elements("portlet")) {
			_readPortletXML(
				servletContextName, portletsPool, pluginPackage, portletApp,
				portletIds, timestamp, portletElement);
		}

		for (Element filterElement : rootElement.elements("filter")) {
			String filterName = filterElement.elementText("filter-name");
			String filterClass = filterElement.elementText("filter-class");

			Set<String> lifecycles = new LinkedHashSet<String>();

			for (Element lifecycleElement :
					filterElement.elements("lifecycle")) {

				lifecycles.add(lifecycleElement.getText());
			}

			Map<String, String> initParams = new HashMap<String, String>();

			for (Element initParamElement :
					filterElement.elements("init-param")) {

				initParams.put(
					initParamElement.elementText("name"),
					initParamElement.elementText("value"));
			}

			PortletFilter portletFilter = new PortletFilterImpl(
				filterName, filterClass, lifecycles, initParams, portletApp);

			portletApp.addPortletFilter(portletFilter);
		}

		for (Element filterMappingElement :
				rootElement.elements("filter-mapping")) {

			String filterName = filterMappingElement.elementText("filter-name");

			for (Element portletNameElement :
					filterMappingElement.elements("portlet-name")) {

				String portletName = portletNameElement.getTextTrim();

				PortletFilter portletFilter = portletApp.getPortletFilter(
					filterName);

				if (portletFilter == null) {
					_log.error(
						"Filter mapping references unnknown filter name " +
							filterName);

					continue;
				}

				List<Portlet> portletModels = _getPortletsByPortletName(
					portletName, servletContextName, portletsPool);

				if (portletModels.size() == 0) {
					_log.error(
						"Filter mapping with filter name " + filterName +
							" references unnknown portlet name " + portletName);
				}

				for (Portlet portletModel : portletModels) {
					portletModel.getPortletFilters().put(
						filterName, portletFilter);
				}
			}
		}

		for (Element listenerElement : rootElement.elements("listener")) {
			String listenerClass = listenerElement.elementText(
				"listener-class");

			PortletURLListener portletURLListener = new PortletURLListenerImpl(
				listenerClass, portletApp);

			portletApp.addPortletURLListener(portletURLListener);
		}

		return portletIds;
	}

	private Set<String> _readWebXML(String xml) throws Exception {
		Set<String> servletURLPatterns = new LinkedHashSet<String>();

		if (xml == null) {
			return servletURLPatterns;
		}

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		for (Element servletMappingElement :
				rootElement.elements("servlet-mapping")) {

			String urlPattern = servletMappingElement.elementText(
				"url-pattern");

			servletURLPatterns.add(urlPattern);
		}

		return servletURLPatterns;
	}

	private void _setSpriteImages(
			ServletContext servletContext, PortletApp portletApp,
			String resourcePath)
		throws Exception {

		Set<String> resourcePaths = servletContext.getResourcePaths(
			resourcePath);

		if ((resourcePaths == null) || resourcePaths.isEmpty()) {
			return;
		}

		List<URL> imageURLs = new ArrayList<URL>(resourcePaths.size());

		for (String curResourcePath : resourcePaths) {
			if (curResourcePath.endsWith(StringPool.SLASH)) {
				_setSpriteImages(servletContext, portletApp, curResourcePath);
			}
			else if (curResourcePath.endsWith(".png")) {
				URL imageURL = servletContext.getResource(curResourcePath);

				if (imageURL != null) {
					imageURLs.add(imageURL);
				}
				else {
					_log.error(
						"Resource URL for " + curResourcePath + " is null");
				}
			}
		}

		String spriteRootDirName = PropsValues.SPRITE_ROOT_DIR;
		String spriteFileName = resourcePath.concat(
			PropsValues.SPRITE_FILE_NAME);
		String spritePropertiesFileName = resourcePath.concat(
			PropsValues.SPRITE_PROPERTIES_FILE_NAME);
		String rootPath = ServletContextUtil.getRootPath(servletContext);

		Properties spriteProperties = SpriteProcessorUtil.generate(
			servletContext, imageURLs, spriteRootDirName, spriteFileName,
			spritePropertiesFileName, rootPath, 16, 16, 10240);

		if (spriteProperties == null) {
			return;
		}

		String contextPath = ContextPathUtil.getContextPath(servletContext);

		spriteFileName = contextPath.concat(SpriteProcessor.PATH).concat(
			spriteFileName);

		portletApp.setSpriteImages(spriteFileName, spriteProperties);
	}

	private static final int _PORTLET_ID_MAX_LENGTH =
		ModelHintsUtil.getMaxLength(Portlet.class.getName(), "portletId") -
			PortletConstants.INSTANCE_SEPARATOR.length() +
				PortletConstants.USER_SEPARATOR.length() + 39;

	private static Log _log = LogFactoryUtil.getLog(
		PortletLocalServiceImpl.class);

	private static Map<Long, Map<String, Portlet>> _companyPortletsPool =
		new ConcurrentHashMap<Long, Map<String, Portlet>>();
	private static Map<String, Portlet> _customAttributesDisplayPortlets =
		new ConcurrentHashMap<String, Portlet>();
	private static Map<String, Portlet> _friendlyURLMapperPortlets =
		new ConcurrentHashMap<String, Portlet>();
	private static Map<String, PortletApp> _portletAppsPool =
		new ConcurrentHashMap<String, PortletApp>();
	private static Map<String, String> _portletIdsByStrutsPath =
		new ConcurrentHashMap<String, String>();
	private static Map<String, Portlet> _portletsPool =
		new ConcurrentHashMap<String, Portlet>();

}