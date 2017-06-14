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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutStagingHandler;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ExceptionRetryAcceptor;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.spring.aop.Property;
import com.liferay.portal.kernel.spring.aop.Retry;
import com.liferay.portal.kernel.spring.aop.Skip;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.base.PortletPreferencesLocalServiceBaseImpl;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.exportimport.staging.ProxiedLayoutsThreadLocal;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			if (portlet == null) {
				defaultPreferences = PortletConstants.DEFAULT_PREFERENCES;
			}
			else {
				defaultPreferences = portlet.getDefaultPreferences();
			}
		}

		portletPreferences.setPreferences(defaultPreferences);

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(13);

			sb.append("Add {companyId=");
			sb.append(companyId);
			sb.append(", ownerId=");
			sb.append(ownerId);
			sb.append(", ownerType=");
			sb.append(ownerType);
			sb.append(", plid=");
			sb.append(plid);
			sb.append(", portletId=");
			sb.append(portletId);
			sb.append(", defaultPreferences=");
			sb.append(defaultPreferences);
			sb.append("}");

			_log.debug(sb.toString());
		}

		try {
			portletPreferencesPersistence.update(portletPreferences);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Add failed, fetch {ownerId=" + ownerId + ", ownerType=" +
						ownerType + ", plid=" + plid + ", portletId=" +
							portletId + "}");
			}

			portletPreferences = portletPreferencesPersistence.fetchByO_O_P_P(
				ownerId, ownerType, plid, portletId, false);

			if (portletPreferences == null) {
				throw se;
			}
		}

		return portletPreferences;
	}

	@Override
	public void deletePortletPreferences(
		long ownerId, int ownerType, long plid) {

		portletPreferencesPersistence.removeByO_O_P(ownerId, ownerType, plid);
	}

	@Override
	public void deletePortletPreferences(
			long ownerId, int ownerType, long plid, String portletId)
		throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Delete {ownerId=" + ownerId + ", ownerType=" + ownerType +
					", plid=" + plid + ", portletId=" + portletId + "}");
		}

		portletPreferencesPersistence.removeByO_O_P_P(
			ownerId, ownerType, plid, portletId);
	}

	@Override
	public void deletePortletPreferencesByPlid(long plid) {
		if (_log.isDebugEnabled()) {
			_log.debug("Delete {plid=" + plid + "}");
		}

		portletPreferencesPersistence.removeByPlid(plid);
	}

	@Override
	public PortletPreferences fetchPortletPreferences(
		long ownerId, int ownerType, long plid, String portletId) {

		return portletPreferencesPersistence.fetchByO_O_P_P(
			ownerId, ownerType, _swapPlidForPortletPreferences(plid),
			portletId);
	}

	@Override
	public javax.portlet.PortletPreferences fetchPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId) {

		PortletPreferences portletPreferences =
			portletPreferencesPersistence.fetchByO_O_P_P(
				ownerId, ownerType, plid, portletId);

		if (portletPreferences == null) {
			return null;
		}

		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId,
				portletPreferences.getPreferences());

		return portletPreferencesImpl;
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
	@Skip
	public javax.portlet.PortletPreferences getDefaultPreferences(
		long companyId, String portletId) {

		Portlet portlet = portletLocalService.getPortletById(
			companyId, portletId);

		return PortletPreferencesFactoryUtil.fromDefaultXML(
			portlet.getDefaultPreferences());
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
	public List<PortletPreferences> getPortletPreferencesByPlid(long plid) {
		return portletPreferencesPersistence.findByPlid(plid);
	}

	@Override
	public long getPortletPreferencesCount(
		int ownerType, long plid, String portletId) {

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
		acceptor = ExceptionRetryAcceptor.class,
		properties = {
			@Property(
				name = ExceptionRetryAcceptor.EXCEPTION_NAME,
				value = "org.springframework.dao.DataIntegrityViolationException"
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
		acceptor = ExceptionRetryAcceptor.class,
		properties = {
			@Property(
				name = ExceptionRetryAcceptor.EXCEPTION_NAME,
				value = "org.springframework.dao.DataIntegrityViolationException"
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
			Portlet portlet = portletLocalService.fetchPortletById(
				companyId, portletId);

			portletPreferences =
				portletPreferencesLocalService.addPortletPreferences(
					companyId, ownerId, ownerType, plid, portletId, portlet,
					defaultPreferences);
		}

		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId,
				portletPreferences.getPreferences());

		return portletPreferencesImpl;
	}

	@Override
	@Retry(
		acceptor = ExceptionRetryAcceptor.class,
		properties = {
			@Property(
				name = ExceptionRetryAcceptor.EXCEPTION_NAME,
				value = "org.springframework.dao.DataIntegrityViolationException"
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

		List<PortletPreferences> portletPreferencesList =
			portletPreferencesPersistence.findByO_O_P(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid);

		for (Portlet portlet : portlets) {
			long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;
			String portletId = portlet.getPortletId();

			String preferences = portlet.getDefaultPreferences();

			if (PortletIdCodec.hasUserId(portletId)) {
				ownerId = PortletIdCodec.decodeUserId(portletId);
				ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;

				PortletPreferences portletsPreferences =
					portletPreferencesPersistence.fetchByO_O_P_P(
						ownerId, ownerType, plid, portletId);

				if (portletsPreferences != null) {
					preferences = portletsPreferences.getPreferences();
				}
			}
			else {
				for (PortletPreferences portletPreferences :
						portletPreferencesList) {

					if (portletId.equals(portletPreferences.getPortletId())) {
						preferences = portletPreferences.getPreferences();

						break;
					}
				}
			}

			portletPreferencesMap.put(
				portletId,
				PortletPreferencesFactoryUtil.strictFromXML(
					layout.getCompanyId(), ownerId, ownerType, plid, portletId,
					preferences));
		}

		return portletPreferencesMap;
	}

	@Override
	public javax.portlet.PortletPreferences getStrictPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId) {

		plid = _swapPlidForPreferences(plid);

		PortletPreferences portletPreferences =
			portletPreferencesPersistence.fetchByO_O_P_P(
				ownerId, ownerType, plid, portletId);

		if (portletPreferences == null) {
			String defaultPreferences = PortletConstants.DEFAULT_PREFERENCES;

			Portlet portlet = portletLocalService.fetchPortletById(
				companyId, portletId);

			if (portlet != null) {
				defaultPreferences = portlet.getDefaultPreferences();
			}

			return PortletPreferencesFactoryUtil.strictFromXML(
				companyId, ownerId, ownerType, plid, portletId,
				defaultPreferences);
		}

		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId,
				portletPreferences.getPreferences());

		return portletPreferencesImpl;
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

		String xml = PortletPreferencesFactoryUtil.toXML(portletPreferences);

		return updatePreferences(ownerId, ownerType, plid, portletId, xml);
	}

	@Override
	public PortletPreferences updatePreferences(
		long ownerId, int ownerType, long plid, String portletId, String xml) {

		plid = _swapPlidForUpdatePreferences(plid);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Update {ownerId=" + ownerId + ", ownerType=" + ownerType +
					", plid=" + plid + ", portletId=" + portletId + ", xml=" +
						xml + "}");
		}

		PortletPreferences portletPreferences =
			portletPreferencesPersistence.fetchByO_O_P_P(
				ownerId, ownerType, plid, portletId);

		if (portletPreferences == null) {
			long portletPreferencesId = counterLocalService.increment();

			portletPreferences = portletPreferencesPersistence.create(
				portletPreferencesId);

			portletPreferences.setOwnerId(ownerId);
			portletPreferences.setOwnerType(ownerType);
			portletPreferences.setPlid(plid);
			portletPreferences.setPortletId(portletId);
		}

		portletPreferences.setPreferences(xml);

		portletPreferencesPersistence.update(portletPreferences);

		return portletPreferences;
	}

	private LayoutRevision _getLayoutRevision(long plid) {
		if (plid <= 0) {
			return null;
		}

		LayoutRevision layoutRevision =
			layoutRevisionPersistence.fetchByPrimaryKey(plid);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		Layout layout = layoutPersistence.fetchByPrimaryKey(plid);

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

		User user = userPersistence.fetchByPrimaryKey(
			PrincipalThreadLocal.getUserId());

		if ((user == null) || user.isDefaultUser()) {
			return layoutRevision.getLayoutRevisionId();
		}

		try {
			return StagingUtil.getRecentLayoutRevisionId(
				user, layoutRevision.getLayoutSetBranchId(),
				layoutRevision.getPlid());
		}
		catch (PortalException pe) {
			return ReflectionUtil.throwException(pe);
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
			layoutRevision = layoutRevisionLocalService.updateLayoutRevision(
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
		catch (PortalException pe) {
			ReflectionUtil.throwException(pe);
		}

		plid = layoutRevision.getLayoutRevisionId();

		ProxiedLayoutsThreadLocal.clearProxiedLayouts();

		return plid;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletPreferencesLocalServiceImpl.class);

}