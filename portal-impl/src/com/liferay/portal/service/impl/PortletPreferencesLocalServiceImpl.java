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

package com.liferay.portal.service.impl;

import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutStagingHandler;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.PortletPreferenceValue;
import com.liferay.portal.kernel.model.PortletPreferenceValueTable;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.model.PortletPreferencesTable;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.SQLStateAcceptor;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.LayoutPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutRevisionPersistence;
import com.liferay.portal.kernel.service.persistence.PortletPreferenceValuePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.settings.PortletPreferencesSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocatorHelperUtil;
import com.liferay.portal.kernel.spring.aop.Property;
import com.liferay.portal.kernel.spring.aop.Retry;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.CopyLayoutThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.base.PortletPreferencesLocalServiceBaseImpl;
import com.liferay.portlet.PortletPreferencesFactoryImpl;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.Preference;
import com.liferay.portlet.exportimport.staging.ProxiedLayoutsThreadLocal;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class PortletPreferencesLocalServiceImpl
	extends PortletPreferencesLocalServiceBaseImpl {

	@Override
	public PortletPreferences addPortletPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, Portlet portlet, String defaultPreferences) {

		long portletPreferencesId = counterLocalService.increment();

		PortletPreferences portletPreferences =
			portletPreferencesPersistence.create(portletPreferencesId);

		portletPreferences.setOwnerId(ownerId);
		portletPreferences.setOwnerType(ownerType);
		portletPreferences.setPlid(plid);
		portletPreferences.setPortletId(portletId);

		if (Validator.isNull(defaultPreferences)) {
			LayoutRevision layoutRevision =
				_layoutRevisionLocalService.fetchLayoutRevision(plid);

			if (layoutRevision != null) {
				PortletPreferences layoutPortletPreferences =
					portletPreferencesPersistence.fetchByO_O_P_P(
						ownerId, ownerType, layoutRevision.getPlid(),
						portletId);

				if (layoutPortletPreferences != null) {
					javax.portlet.PortletPreferences jxPortletPreferences =
						_portletPreferenceValueLocalService.getPreferences(
							layoutPortletPreferences);

					if (jxPortletPreferences != null) {
						defaultPreferences =
							PortletPreferencesFactoryUtil.toXML(
								jxPortletPreferences);
					}
				}
			}

			if (Validator.isNull(defaultPreferences)) {
				if (portlet == null) {
					defaultPreferences = PortletConstants.DEFAULT_PREFERENCES;
				}
				else {
					defaultPreferences = portlet.getDefaultPreferences();
				}
			}
		}

		if (!Objects.equals(
				PortletConstants.DEFAULT_PREFERENCES, defaultPreferences)) {

			_updatePortletPreferences(
				portletPreferences, Collections.emptyMap(),
				PortletPreferencesFactoryImpl.createPreferencesMap(
					defaultPreferences));
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Add {companyId=", companyId, ", ownerId=", ownerId,
					", ownerType=", ownerType, ", plid=", plid, ", portletId=",
					portletId, ", defaultPreferences=", defaultPreferences,
					"}"));
		}

		try {
			portletPreferences = portletPreferencesPersistence.update(
				portletPreferences);
		}
		catch (SystemException systemException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Add failed, fetch {ownerId=", ownerId, ", ownerType=",
						ownerType, ", plid=", plid, ", portletId=", portletId,
						"}"));
			}

			portletPreferences = portletPreferencesPersistence.fetchByO_O_P_P(
				ownerId, ownerType, plid, portletId, false);

			if (portletPreferences == null) {
				throw systemException;
			}
		}

		return portletPreferences;
	}

	@Override
	public void deletePortletPreferences(
		long ownerId, int ownerType, long plid) {

		for (PortletPreferenceValue portletPreferenceValue :
				_getPortletPreferenceValues(
					PortletPreferencesTable.INSTANCE.ownerId.eq(
						ownerId
					).and(
						PortletPreferencesTable.INSTANCE.ownerType.eq(ownerType)
					).and(
						PortletPreferencesTable.INSTANCE.plid.eq(plid)
					))) {

			_portletPreferenceValuePersistence.remove(portletPreferenceValue);
		}

		portletPreferencesPersistence.removeByO_O_P(ownerId, ownerType, plid);
	}

	@Override
	public void deletePortletPreferences(
			long ownerId, int ownerType, long plid, String portletId)
		throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Delete {ownerId=", ownerId, ", ownerType=", ownerType,
					", plid=", plid, ", portletId=", portletId, "}"));
		}

		PortletPreferences portletPreferences =
			portletPreferencesPersistence.findByO_O_P_P(
				ownerId, ownerType, plid, portletId);

		_portletPreferenceValuePersistence.removeByPortletPreferencesId(
			portletPreferences.getPortletPreferencesId());

		portletPreferencesPersistence.remove(portletPreferences);
	}

	@Override
	public void deletePortletPreferencesByOwnerId(long ownerId) {
		for (PortletPreferenceValue portletPreferenceValue :
				_getPortletPreferenceValues(
					PortletPreferencesTable.INSTANCE.ownerId.eq(ownerId))) {

			_portletPreferenceValuePersistence.remove(portletPreferenceValue);
		}

		portletPreferencesPersistence.removeByOwnerId(ownerId);
	}

	@Override
	public void deletePortletPreferencesByPlid(long plid) {
		if (_log.isDebugEnabled()) {
			_log.debug("Delete {plid=" + plid + "}");
		}

		for (PortletPreferenceValue portletPreferenceValue :
				_getPortletPreferenceValues(
					PortletPreferencesTable.INSTANCE.plid.eq(plid))) {

			_portletPreferenceValuePersistence.remove(portletPreferenceValue);
		}

		portletPreferencesPersistence.removeByPlid(plid);
	}

	@Override
	public PortletPreferences fetchPortletPreferences(
		long ownerId, int ownerType, long plid, String portletId) {

		if (!_exists(plid, portletId)) {
			return null;
		}

		return portletPreferencesPersistence.fetchByO_O_P_P(
			ownerId, ownerType, _swapPlidForPortletPreferences(plid),
			portletId);
	}

	@Override
	public javax.portlet.PortletPreferences fetchPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId) {

		plid = _swapPlidForPortletPreferences(plid);

		PortletPreferences portletPreferences =
			portletPreferencesPersistence.fetchByO_O_P_P(
				ownerId, ownerType, plid, portletId);

		if (portletPreferences == null) {
			return null;
		}

		return _portletPreferenceValueLocalService.getPreferences(
			portletPreferences);
	}

	@Override
	public javax.portlet.PortletPreferences fetchPreferences(
		PortletPreferencesIds portletPreferencesIds) {

		return fetchPreferences(
			portletPreferencesIds.getCompanyId(),
			portletPreferencesIds.getOwnerId(),
			portletPreferencesIds.getOwnerType(),
			portletPreferencesIds.getPlid(),
			portletPreferencesIds.getPortletId());
	}

	@Override
	@Transactional(enabled = false)
	public javax.portlet.PortletPreferences getDefaultPreferences(
		long companyId, String portletId) {

		Portlet portlet = _portletLocalService.getPortletById(
			companyId, portletId);

		return PortletPreferencesFactoryUtil.fromDefaultXML(
			portlet.getDefaultPreferences());
	}

	@Override
	public Settings getPortletInstanceSettings(
		long companyId, long groupId, String portletId,
		PortletInstanceSettingsLocator portletInstanceSettingsLocator,
		Settings portalPreferencesSettings) {

		String defaultPreferences = PortletConstants.DEFAULT_PREFERENCES;

		String portletName = PortletIdCodec.decodePortletName(portletId);

		Portlet portlet = _portletLocalService.fetchPortletById(
			companyId, portletName);

		if (portlet != null) {
			defaultPreferences = portlet.getDefaultPreferences();
		}

		String configurationPid =
			portletInstanceSettingsLocator.getConfigurationPid();

		Settings companyConfigurationBeanSettings =
			SettingsLocatorHelperUtil.getCompanyConfigurationBeanSettings(
				companyId, configurationPid, portalPreferencesSettings);

		Settings companyPortletPreferencesSettings =
			new PortletPreferencesSettings(
				_getStrictPreferences(
					companyId, companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
					PortletKeys.PREFS_PLID_SHARED, portletName,
					defaultPreferences),
				companyConfigurationBeanSettings);

		Settings groupConfigurationBeanSettings =
			SettingsLocatorHelperUtil.getGroupConfigurationBeanSettings(
				groupId, configurationPid, companyPortletPreferencesSettings);

		Settings groupPortletPreferencesSettings =
			new PortletPreferencesSettings(
				_getStrictPreferences(
					companyId, groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP,
					PortletKeys.PREFS_PLID_SHARED, portletName,
					defaultPreferences),
				groupConfigurationBeanSettings);

		Settings portletInstanceConfigurationBeanSettings =
			SettingsLocatorHelperUtil.
				getPortletInstanceConfigurationBeanSettings(
					portletId, configurationPid,
					groupPortletPreferencesSettings);

		long ownerId = portletInstanceSettingsLocator.getOwnerId();
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		long userId = PortletIdCodec.decodeUserId(portletId);

		if (userId > 0) {
			ownerId = userId;
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}

		long plid = _swapPlidForPortletPreferences(
			portletInstanceSettingsLocator.getPlid());

		return new PortletPreferencesSettings(
			_getStrictPreferences(
				companyId, ownerId, ownerType, plid, portletId,
				defaultPreferences),
			portletInstanceConfigurationBeanSettings);
	}

	@Override
	public List<PortletPreferences> getPortletPreferences() {
		return portletPreferencesPersistence.findAll();
	}

	@Override
	public List<PortletPreferences> getPortletPreferences(
		int ownerType, long plid, String portletId) {

		return portletPreferencesPersistence.findByO_P_P(
			ownerType, _swapPlidForPortletPreferences(plid), portletId);
	}

	@Override
	public List<PortletPreferences> getPortletPreferences(
		long ownerId, int ownerType, long plid) {

		return portletPreferencesPersistence.findByO_O_P(
			ownerId, ownerType, _swapPlidForPortletPreferences(plid));
	}

	@Override
	public PortletPreferences getPortletPreferences(
			long ownerId, int ownerType, long plid, String portletId)
		throws PortalException {

		return portletPreferencesPersistence.findByO_O_P_P(
			ownerId, ownerType, _swapPlidForPortletPreferences(plid),
			portletId);
	}

	@Override
	public List<PortletPreferences> getPortletPreferences(
			long companyId, long ownerId, int ownerType, String portletId)
		throws PortalException {

		return portletPreferencesPersistence.findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId);
	}

	@Override
	public List<PortletPreferences> getPortletPreferences(
		long companyId, long groupId, long ownerId, int ownerType,
		String portletId, boolean privateLayout) {

		return portletPreferencesFinder.findByC_G_O_O_P_P(
			companyId, groupId, ownerId, ownerType, portletId, privateLayout);
	}

	@Override
	public List<PortletPreferences> getPortletPreferences(
		long plid, String portletId) {

		return portletPreferencesPersistence.findByP_P(
			_swapPlidForPortletPreferences(plid), portletId);
	}

	@Override
	public List<PortletPreferences> getPortletPreferencesByOwnerId(
		long ownerId) {

		return portletPreferencesPersistence.findByOwnerId(ownerId);
	}

	@Override
	public List<PortletPreferences> getPortletPreferencesByPlid(long plid) {
		return portletPreferencesPersistence.findByPlid(plid);
	}

	@Override
	public long getPortletPreferencesCount(
		int ownerType, long plid, String portletId) {

		if (!_exists(plid, portletId)) {
			return 0;
		}

		return portletPreferencesPersistence.countByO_P_P(
			ownerType, _swapPlidForPortletPreferences(plid), portletId);
	}

	@Override
	public long getPortletPreferencesCount(int ownerType, String portletId) {
		return portletPreferencesPersistence.countByO_P(ownerType, portletId);
	}

	@Override
	public long getPortletPreferencesCount(
		long ownerId, int ownerType, long plid, Portlet portlet,
		boolean excludeDefaultPreferences) {

		plid = _swapPlidForPortletPreferences(plid);

		String portletId = portlet.getPortletId();

		if (plid == -1) {
			portletId = portlet.getRootPortletId();
		}

		return portletPreferencesFinder.countByO_O_P_P_P(
			ownerId, ownerType, plid, portletId, excludeDefaultPreferences);
	}

	@Override
	public long getPortletPreferencesCount(
		long ownerId, int ownerType, String portletId,
		boolean excludeDefaultPreferences) {

		return portletPreferencesFinder.countByO_O_P(
			ownerId, ownerType, portletId, excludeDefaultPreferences);
	}

	@Override
	@Retry(
		acceptor = SQLStateAcceptor.class,
		properties = {
			@Property(
				name = SQLStateAcceptor.SQLSTATE,
				value = SQLStateAcceptor.SQLSTATE_INTEGRITY_CONSTRAINT_VIOLATION
			)
		}
	)
	public javax.portlet.PortletPreferences getPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId) {

		return getPreferences(
			companyId, ownerId, ownerType, plid, portletId, null);
	}

	@Override
	@Retry(
		acceptor = SQLStateAcceptor.class,
		properties = {
			@Property(
				name = SQLStateAcceptor.SQLSTATE,
				value = SQLStateAcceptor.SQLSTATE_INTEGRITY_CONSTRAINT_VIOLATION
			)
		}
	)
	public javax.portlet.PortletPreferences getPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, String defaultPreferences) {

		plid = _swapPlidForPreferences(plid);

		PortletPreferences portletPreferences =
			portletPreferencesPersistence.fetchByO_O_P_P(
				ownerId, ownerType, plid, portletId);

		if (portletPreferences == null) {
			Portlet portlet = _portletLocalService.fetchPortletById(
				companyId, portletId);

			portletPreferences =
				portletPreferencesLocalService.addPortletPreferences(
					companyId, ownerId, ownerType, plid, portletId, portlet,
					defaultPreferences);
		}

		return _portletPreferenceValueLocalService.getPreferences(
			portletPreferences);
	}

	@Override
	@Retry(
		acceptor = SQLStateAcceptor.class,
		properties = {
			@Property(
				name = SQLStateAcceptor.SQLSTATE,
				value = SQLStateAcceptor.SQLSTATE_INTEGRITY_CONSTRAINT_VIOLATION
			)
		}
	)
	public javax.portlet.PortletPreferences getPreferences(
		PortletPreferencesIds portletPreferencesIds) {

		return getPreferences(
			portletPreferencesIds.getCompanyId(),
			portletPreferencesIds.getOwnerId(),
			portletPreferencesIds.getOwnerType(),
			portletPreferencesIds.getPlid(),
			portletPreferencesIds.getPortletId());
	}

	@Override
	public Map<String, javax.portlet.PortletPreferences> getStrictPreferences(
		Layout layout, List<Portlet> portlets) {

		long plid = layout.getPlid();

		plid = _swapPlidForPreferences(plid);

		Map<String, javax.portlet.PortletPreferences> portletPreferencesMap =
			new HashMap<>();

		List<PortletPreferences> portletPreferencesList = new ArrayList<>();

		portletPreferencesList.addAll(
			portletPreferencesPersistence.findByO_O_P(
				layout.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				PortletKeys.PREFS_PLID_SHARED));

		portletPreferencesList.addAll(
			portletPreferencesPersistence.findByO_O_P(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid));

		for (Portlet portlet : portlets) {
			long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;
			long preferencesPlid = plid;
			String portletId = portlet.getPortletId();

			String preferences = portlet.getDefaultPreferences();

			if (PortletIdCodec.hasUserId(portletId)) {
				ownerId = PortletIdCodec.decodeUserId(portletId);
				ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;

				PortletPreferences portletPreferences =
					portletPreferencesPersistence.fetchByO_O_P_P(
						ownerId, ownerType, plid, portletId);

				if (portletPreferences != null) {
					javax.portlet.PortletPreferences jxPortletPreferences =
						_portletPreferenceValueLocalService.getPreferences(
							portletPreferences);

					preferences = PortletPreferencesFactoryUtil.toXML(
						jxPortletPreferences);
				}
			}
			else {
				for (PortletPreferences portletPreferences :
						portletPreferencesList) {

					if (portletId.equals(portletPreferences.getPortletId())) {
						ownerId = portletPreferences.getOwnerId();
						preferencesPlid = portletPreferences.getPlid();

						javax.portlet.PortletPreferences jxPortletPreferences =
							_portletPreferenceValueLocalService.getPreferences(
								portletPreferences);

						preferences = PortletPreferencesFactoryUtil.toXML(
							jxPortletPreferences);

						break;
					}
				}
			}

			portletPreferencesMap.put(
				portletId,
				PortletPreferencesFactoryUtil.strictFromXML(
					layout.getCompanyId(), ownerId, ownerType, preferencesPlid,
					portletId, preferences));
		}

		return portletPreferencesMap;
	}

	@Override
	public javax.portlet.PortletPreferences getStrictPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId) {

		if (!_exists(plid, companyId, portletId)) {
			return PortletPreferencesFactoryUtil.strictFromXML(
				companyId, ownerId, ownerType, plid, portletId,
				PortletConstants.DEFAULT_PREFERENCES);
		}

		plid = _swapPlidForPreferences(plid);

		PortletPreferences portletPreferences =
			portletPreferencesPersistence.fetchByO_O_P_P(
				ownerId, ownerType, plid, portletId);

		if (portletPreferences == null) {
			String defaultPreferences = PortletConstants.DEFAULT_PREFERENCES;

			Portlet portlet = _portletLocalService.fetchPortletById(
				companyId, portletId);

			if (portlet != null) {
				defaultPreferences = portlet.getDefaultPreferences();
			}

			return PortletPreferencesFactoryUtil.strictFromXML(
				companyId, ownerId, ownerType, plid, portletId,
				defaultPreferences);
		}

		return _portletPreferenceValueLocalService.getPreferences(
			portletPreferences);
	}

	@Override
	public javax.portlet.PortletPreferences getStrictPreferences(
		PortletPreferencesIds portletPreferencesIds) {

		return getStrictPreferences(
			portletPreferencesIds.getCompanyId(),
			portletPreferencesIds.getOwnerId(),
			portletPreferencesIds.getOwnerType(),
			portletPreferencesIds.getPlid(),
			portletPreferencesIds.getPortletId());
	}

	@Override
	public PortletPreferences updatePreferences(
		long ownerId, int ownerType, long plid, String portletId,
		javax.portlet.PortletPreferences portletPreferences) {

		if (portletPreferences instanceof PortletPreferencesImpl) {
			PortletPreferencesImpl portletPreferencesImpl =
				(PortletPreferencesImpl)portletPreferences;

			return _updatePreferences(
				ownerId, ownerType, plid, portletId,
				portletPreferencesImpl.getPreferences());
		}

		Map<String, Preference> preferenceMap = new HashMap<>();

		Map<String, String[]> map = portletPreferences.getMap();

		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			String name = entry.getKey();

			preferenceMap.put(
				name,
				new Preference(
					name, entry.getValue(),
					portletPreferences.isReadOnly(name)));
		}

		return _updatePreferences(
			ownerId, ownerType, plid, portletId, preferenceMap);
	}

	@Override
	public PortletPreferences updatePreferences(
		long ownerId, int ownerType, long plid, String portletId, String xml) {

		return _updatePreferences(
			ownerId, ownerType, plid, portletId,
			PortletPreferencesFactoryImpl.createPreferencesMap(xml));
	}

	private boolean _exists(long plid, long companyId, String portletId) {
		if ((plid == PortletKeys.PREFS_PLID_SHARED) ||
			(_portletLocalService.fetchPortletById(companyId, portletId) !=
				null)) {

			return true;
		}

		return false;
	}

	private boolean _exists(long plid, String portletId) {
		if (plid == PortletKeys.PREFS_PLID_SHARED) {
			return true;
		}

		Layout layout = _layoutPersistence.fetchByPrimaryKey(plid);

		if (layout == null) {
			return false;
		}

		return _exists(plid, layout.getCompanyId(), portletId);
	}

	private LayoutRevision _getLayoutRevision(long plid) {
		if (plid <= 0) {
			return null;
		}

		LayoutRevision layoutRevision =
			_layoutRevisionPersistence.fetchByPrimaryKey(plid);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		Layout layout = _layoutPersistence.fetchByPrimaryKey(plid);

		if (layout == null) {
			return null;
		}

		if (LayoutStagingUtil.isBranchingLayout(layout)) {
			LayoutStagingHandler layoutStagingHandler =
				new LayoutStagingHandler(layout);

			return layoutStagingHandler.getLayoutRevision();
		}

		return null;
	}

	private List<PortletPreferenceValue> _getPortletPreferenceValues(
		Predicate predicate) {

		return _portletPreferenceValuePersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				PortletPreferenceValueTable.INSTANCE
			).from(
				PortletPreferenceValueTable.INSTANCE
			).innerJoinON(
				PortletPreferencesTable.INSTANCE,
				PortletPreferencesTable.INSTANCE.portletPreferencesId.eq(
					PortletPreferenceValueTable.INSTANCE.portletPreferencesId)
			).where(
				predicate
			));
	}

	private javax.portlet.PortletPreferences _getStrictPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, String defaultPreferences) {

		PortletPreferences portletPreferences =
			portletPreferencesPersistence.fetchByO_O_P_P(
				ownerId, ownerType, plid, portletId);

		if (portletPreferences == null) {
			return PortletPreferencesFactoryUtil.strictFromXML(
				companyId, ownerId, ownerType, plid, portletId,
				defaultPreferences);
		}

		return _portletPreferenceValueLocalService.getPreferences(
			portletPreferences);
	}

	private long _swapPlidForPortletPreferences(long plid) {
		if (!StagingAdvicesThreadLocal.isEnabled()) {
			return plid;
		}

		LayoutRevision layoutRevision = _getLayoutRevision(plid);

		if (layoutRevision == null) {
			return plid;
		}

		return layoutRevision.getLayoutRevisionId();
	}

	private long _swapPlidForPreferences(long plid) {
		if (!StagingAdvicesThreadLocal.isEnabled()) {
			return plid;
		}

		LayoutRevision layoutRevision = _getLayoutRevision(plid);

		if (layoutRevision == null) {
			return plid;
		}

		User user = _userPersistence.fetchByPrimaryKey(
			PrincipalThreadLocal.getUserId());

		if ((user == null) || user.isDefaultUser()) {
			return layoutRevision.getLayoutRevisionId();
		}

		try {
			return StagingUtil.getRecentLayoutRevisionId(
				user, layoutRevision.getLayoutSetBranchId(),
				layoutRevision.getPlid());
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private long _swapPlidForUpdatePreferences(long plid) {
		if (!StagingAdvicesThreadLocal.isEnabled()) {
			return plid;
		}

		LayoutRevision layoutRevision = _getLayoutRevision(plid);

		if (layoutRevision == null) {
			return plid;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return plid;
		}

		boolean exporting = ParamUtil.getBoolean(serviceContext, "exporting");

		if (exporting) {
			return plid;
		}

		if (!MergeLayoutPrototypesThreadLocal.isInProgress()) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		try {
			boolean hasWorkflowTask = StagingUtil.hasWorkflowTask(
				serviceContext.getUserId(), layoutRevision);

			serviceContext.setAttribute("revisionInProgress", hasWorkflowTask);

			layoutRevision = _layoutRevisionLocalService.updateLayoutRevision(
				serviceContext.getUserId(),
				layoutRevision.getLayoutRevisionId(),
				layoutRevision.getLayoutBranchId(), layoutRevision.getName(),
				layoutRevision.getTitle(), layoutRevision.getDescription(),
				layoutRevision.getKeywords(), layoutRevision.getRobots(),
				layoutRevision.getTypeSettings(), layoutRevision.getIconImage(),
				layoutRevision.getIconImageId(), layoutRevision.getThemeId(),
				layoutRevision.getColorSchemeId(), layoutRevision.getCss(),
				serviceContext);
		}
		catch (PortalException portalException) {
			ReflectionUtil.throwException(portalException);
		}

		plid = layoutRevision.getLayoutRevisionId();

		ProxiedLayoutsThreadLocal.clearProxiedLayouts();

		return plid;
	}

	private void _updatePortletPreferences(
		PortletPreferences portletPreferences,
		Map<String, List<PortletPreferenceValue>> portletPreferenceValuesMap,
		Map<String, Preference> preferencesMap) {

		List<Map.Entry<List<PortletPreferenceValue>, Preference>>
			preferenceEntries = new ArrayList<>(preferencesMap.size());

		int newCount = 0;

		for (Map.Entry<String, Preference> entry : preferencesMap.entrySet()) {
			Preference preference = entry.getValue();

			String[] values = preference.getValues();

			if (values == null) {
				continue;
			}

			int size = 0;

			List<PortletPreferenceValue> portletPreferenceValues =
				portletPreferenceValuesMap.remove(entry.getKey());

			if (portletPreferenceValues != null) {
				size = portletPreferenceValues.size();
			}

			if (values.length > size) {
				newCount += values.length - size;
			}

			preferenceEntries.add(
				new AbstractMap.SimpleImmutableEntry<>(
					portletPreferenceValues, preference));
		}

		for (List<PortletPreferenceValue> portletPreferenceValues :
				portletPreferenceValuesMap.values()) {

			for (PortletPreferenceValue portletPreferenceValue :
					portletPreferenceValues) {

				_portletPreferenceValuePersistence.remove(
					portletPreferenceValue);
			}
		}

		long batchCounter = 0;

		if (newCount > 0) {
			batchCounter = counterLocalService.increment(
				PortletPreferenceValue.class.getName(), newCount);

			batchCounter -= newCount;
		}

		for (Map.Entry<List<PortletPreferenceValue>, Preference> entry :
				preferenceEntries) {

			List<PortletPreferenceValue> portletPreferenceValues =
				entry.getKey();

			Preference preference = entry.getValue();

			String[] newValues = preference.getValues();

			int oldSize = 0;

			if (portletPreferenceValues != null) {
				oldSize = portletPreferenceValues.size();
			}

			for (int i = 0; i < newValues.length; i++) {
				String value = newValues[i];
				boolean readOnly = preference.isReadOnly();

				if (oldSize > i) {
					PortletPreferenceValue portletPreferenceValue =
						portletPreferenceValues.get(i);

					if (!Objects.equals(
							newValues[i], portletPreferenceValue.getValue()) ||
						(preference.isReadOnly() !=
							portletPreferenceValue.isReadOnly())) {

						portletPreferenceValue.setReadOnly(readOnly);
						portletPreferenceValue.setValue(value);

						_portletPreferenceValuePersistence.update(
							portletPreferenceValue);
					}
				}
				else {
					PortletPreferenceValue portletPreferenceValue =
						_portletPreferenceValuePersistence.create(
							++batchCounter);

					portletPreferenceValue.setCompanyId(
						portletPreferences.getCompanyId());
					portletPreferenceValue.setPortletPreferencesId(
						portletPreferences.getPortletPreferencesId());
					portletPreferenceValue.setIndex(i);
					portletPreferenceValue.setName(preference.getName());
					portletPreferenceValue.setReadOnly(readOnly);
					portletPreferenceValue.setValue(value);

					_portletPreferenceValuePersistence.update(
						portletPreferenceValue);
				}
			}

			for (int i = newValues.length; i < oldSize; i++) {
				_portletPreferenceValuePersistence.remove(
					portletPreferenceValues.get(i));
			}
		}
	}

	private PortletPreferences _updatePreferences(
		long ownerId, int ownerType, long plid, String portletId,
		Map<String, Preference> preferenceMap) {

		if (CopyLayoutThreadLocal.isCopyLayout()) {
			Layout layout = _layoutPersistence.fetchByPrimaryKey(plid);

			if ((layout != null) &&
				LayoutStagingUtil.isBranchingLayout(layout)) {

				LayoutStagingHandler layoutStagingHandler =
					new LayoutStagingHandler(layout);

				LayoutRevision layoutRevision =
					layoutStagingHandler.getLayoutRevision();

				if (layoutRevision != null) {
					_updatePreferences(
						ownerId, ownerType,
						layoutRevision.getLayoutRevisionId(), portletId,
						preferenceMap);
				}
			}
		}

		plid = _swapPlidForUpdatePreferences(plid);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Update {ownerId=", ownerId, ", ownerType=", ownerType,
					", plid=", plid, ", portletId=", portletId,
					", preferenceMap=", preferenceMap, "}"));
		}

		PortletPreferences portletPreferences =
			portletPreferencesPersistence.fetchByO_O_P_P(
				ownerId, ownerType, plid, portletId);

		Map<String, List<PortletPreferenceValue>> portletPreferenceValuesMap =
			Collections.emptyMap();

		if (portletPreferences == null) {
			long portletPreferencesId = counterLocalService.increment();

			portletPreferences = portletPreferencesPersistence.create(
				portletPreferencesId);

			portletPreferences.setOwnerId(ownerId);
			portletPreferences.setOwnerType(ownerType);
			portletPreferences.setPlid(plid);
			portletPreferences.setPortletId(portletId);
		}
		else {
			portletPreferenceValuesMap =
				PortletPreferenceValueLocalServiceImpl.
					getPortletPreferenceValuesMap(
						_portletPreferenceValuePersistence,
						portletPreferences.getPortletPreferencesId());
		}

		_updatePortletPreferences(
			portletPreferences, portletPreferenceValuesMap, preferenceMap);

		return portletPreferencesPersistence.update(portletPreferences);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletPreferencesLocalServiceImpl.class);

	@BeanReference(type = LayoutPersistence.class)
	private LayoutPersistence _layoutPersistence;

	@BeanReference(type = LayoutRevisionLocalService.class)
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@BeanReference(type = LayoutRevisionPersistence.class)
	private LayoutRevisionPersistence _layoutRevisionPersistence;

	@BeanReference(type = PortletLocalService.class)
	private PortletLocalService _portletLocalService;

	@BeanReference(type = PortletPreferenceValueLocalService.class)
	private PortletPreferenceValueLocalService
		_portletPreferenceValueLocalService;

	@BeanReference(type = PortletPreferenceValuePersistence.class)
	private PortletPreferenceValuePersistence
		_portletPreferenceValuePersistence;

	@BeanReference(type = UserPersistence.class)
	private UserPersistence _userPersistence;

}