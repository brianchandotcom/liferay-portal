/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.internal.helper;

import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.layout.set.prototype.exception.LayoutSetPrototypeSyncException;
import com.liferay.layout.set.prototype.helper.LayoutSetPrototypeHelper;
import com.liferay.layout.set.prototype.internal.sync.LayoutSetPrototypeSyncSessionManagerUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.LayoutSetPrototypeTable;
import com.liferay.portal.kernel.model.LayoutSetTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sites.kernel.util.Sites;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = LayoutSetPrototypeHelper.class)
public class LayoutSetPrototypeHelperImpl implements LayoutSetPrototypeHelper {

	@Override
	public void executeLayoutSetPrototypeSync(
			long layoutSetPrototypeId, long userId)
		throws PortalException {

		if (ExportImportThreadLocal.isExportInProcess() ||
			ExportImportThreadLocal.isImportInProcess() ||
			ExportImportThreadLocal.isStagingInProcess()) {

			throw new LayoutSetPrototypeSyncException.
				MustNotHaveExportImportInProgress();
		}

		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.fetchLayoutSetPrototype(
				layoutSetPrototypeId);

		if (layoutSetPrototype == null) {
			return;
		}

		List<LayoutSet> mergeableLayoutSets = new ArrayList<>();

		for (LayoutSet layoutSet :
				_layoutSetLocalService.getLayoutSetsByLayoutSetPrototypeUuid(
					layoutSetPrototype.getUuid())) {

			if (_isLayoutSetMergeable(layoutSet.getGroup(), layoutSet)) {
				mergeableLayoutSets.add(layoutSet);
			}
		}

		Map<Long, ExportImportConfiguration> exportImportConfigurations =
			new HashMap<>();

		boolean hasPreValidationErrors = false;

		for (Iterator<LayoutSet> iterator = mergeableLayoutSets.iterator();
			 iterator.hasNext();) {

			LayoutSet layoutSet = iterator.next();

			try {
				exportImportConfigurations.put(
					layoutSet.getLayoutSetId(),
					_buildExportImportConfiguration(false, layoutSet));
			}
			catch (PortalException portalException) {
				_log.error(
					"Unable to add draft export-import configuration for " +
						"layout set " + layoutSet.getLayoutSetId(),
					portalException);

				iterator.remove();

				hasPreValidationErrors = true;
			}
		}

		if (mergeableLayoutSets.isEmpty() && hasPreValidationErrors) {
			LayoutSetPrototypeSyncSessionManagerUtil.postFailureNotification(
				layoutSetPrototype.getNameMap(), userId);

			return;
		}

		try (SafeCloseable safeCloseable =
				LayoutSetPrototypeSyncSessionManagerUtil.openSession(
					hasPreValidationErrors, mergeableLayoutSets,
					layoutSetPrototype, userId)) {

			for (LayoutSet layoutSet : mergeableLayoutSets) {
				try {
					_runSyncInBackground(
						exportImportConfigurations.get(
							layoutSet.getLayoutSetId()),
						layoutSet);
				}
				catch (Exception exception) {
					_log.error(
						"Unable to start site template sync for layout set " +
							layoutSet.getLayoutSetId(),
						exception);
				}
			}
		}
	}

	@Override
	public void executeLayoutSetSync(boolean initialSync, LayoutSet layoutSet)
		throws PortalException {

		if (ExportImportThreadLocal.isExportInProcess() ||
			ExportImportThreadLocal.isImportInProcess() ||
			ExportImportThreadLocal.isStagingInProcess()) {

			throw new LayoutSetPrototypeSyncException.
				MustNotHaveExportImportInProgress();
		}

		Group group = layoutSet.getGroup();

		layoutSet = _layoutSetLocalService.fetchLayoutSet(
			layoutSet.getLayoutSetId());

		if (!_isLayoutSetMergeable(group, layoutSet)) {
			return;
		}

		_runSyncInBackground(
			_buildExportImportConfiguration(initialSync, layoutSet), layoutSet);
	}

	@Override
	public List<Layout> getDuplicatedFriendlyURLLayouts(Layout layout)
		throws PortalException {

		Group group = layout.getGroup();

		if (group.isLayoutSetPrototype()) {
			return _getDuplicatedFriendlyURLSiteLayouts(layout);
		}

		LayoutSet layoutSet = layout.getLayoutSet();

		if (!layoutSet.isLayoutSetPrototypeLinkActive()) {
			return Collections.emptyList();
		}

		Layout conflictLayout = _getDuplicatedFriendlyURLPrototypeLayout(
			layout);

		if (conflictLayout != null) {
			return Collections.singletonList(conflictLayout);
		}

		return Collections.emptyList();
	}

	@Override
	public List<Long> getDuplicatedFriendlyURLPlids(LayoutSet layoutSet) {
		LayoutTable tempLayoutTable = LayoutTable.INSTANCE.as(
			"tempLayoutTable");

		return _layoutLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				LayoutTable.INSTANCE.plid
			).from(
				LayoutTable.INSTANCE
			).innerJoinON(
				LayoutSetTable.INSTANCE,
				LayoutSetTable.INSTANCE.companyId.eq(
					LayoutTable.INSTANCE.companyId
				).and(
					LayoutSetTable.INSTANCE.groupId.eq(
						LayoutTable.INSTANCE.groupId)
				).and(
					LayoutSetTable.INSTANCE.privateLayout.eq(
						LayoutTable.INSTANCE.privateLayout)
				)
			).innerJoinON(
				LayoutSetPrototypeTable.INSTANCE,
				LayoutSetPrototypeTable.INSTANCE.companyId.eq(
					LayoutSetTable.INSTANCE.companyId
				).and(
					LayoutSetPrototypeTable.INSTANCE.uuid.eq(
						LayoutSetTable.INSTANCE.layoutSetPrototypeUuid)
				)
			).innerJoinON(
				GroupTable.INSTANCE,
				GroupTable.INSTANCE.companyId.eq(
					LayoutSetPrototypeTable.INSTANCE.companyId
				).and(
					GroupTable.INSTANCE.classPK.eq(
						LayoutSetPrototypeTable.INSTANCE.layoutSetPrototypeId)
				)
			).innerJoinON(
				tempLayoutTable,
				tempLayoutTable.companyId.eq(
					GroupTable.INSTANCE.companyId
				).and(
					tempLayoutTable.groupId.eq(GroupTable.INSTANCE.groupId)
				).and(
					tempLayoutTable.friendlyURL.eq(
						LayoutTable.INSTANCE.friendlyURL)
				)
			).where(
				LayoutTable.INSTANCE.groupId.eq(
					layoutSet.getGroupId()
				).and(
					LayoutTable.INSTANCE.system.eq(false)
				).and(
					LayoutTable.INSTANCE.layoutSetPrototypeLayoutERC.isNull()
				)
			));
	}

	@Override
	public List<Long> getDuplicatedFriendlyURLPlids(
			LayoutSetPrototype layoutSetPrototype)
		throws PortalException {

		LayoutTable tempLayoutTable = LayoutTable.INSTANCE.as(
			"tempLayoutTable");

		return _layoutLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				LayoutTable.INSTANCE.plid
			).from(
				LayoutTable.INSTANCE
			).innerJoinON(
				GroupTable.INSTANCE,
				GroupTable.INSTANCE.companyId.eq(
					LayoutTable.INSTANCE.companyId
				).and(
					GroupTable.INSTANCE.groupId.eq(LayoutTable.INSTANCE.groupId)
				)
			).innerJoinON(
				LayoutSetPrototypeTable.INSTANCE,
				LayoutSetPrototypeTable.INSTANCE.companyId.eq(
					GroupTable.INSTANCE.companyId
				).and(
					LayoutSetPrototypeTable.INSTANCE.layoutSetPrototypeId.eq(
						GroupTable.INSTANCE.classPK)
				)
			).innerJoinON(
				LayoutSetTable.INSTANCE,
				LayoutSetTable.INSTANCE.companyId.eq(
					LayoutSetPrototypeTable.INSTANCE.companyId
				).and(
					LayoutSetTable.INSTANCE.layoutSetPrototypeUuid.eq(
						LayoutSetPrototypeTable.INSTANCE.uuid)
				)
			).innerJoinON(
				tempLayoutTable,
				tempLayoutTable.companyId.eq(
					LayoutSetTable.INSTANCE.companyId
				).and(
					tempLayoutTable.groupId.eq(LayoutSetTable.INSTANCE.groupId)
				).and(
					tempLayoutTable.privateLayout.eq(
						LayoutSetTable.INSTANCE.privateLayout)
				).and(
					tempLayoutTable.friendlyURL.eq(
						LayoutTable.INSTANCE.friendlyURL)
				).and(
					tempLayoutTable.layoutSetPrototypeLayoutERC.isNull()
				)
			).where(
				LayoutTable.INSTANCE.groupId.eq(
					layoutSetPrototype.getGroupId()
				).and(
					LayoutTable.INSTANCE.system.eq(false)
				)
			));
	}

	@Override
	public boolean hasDuplicatedFriendlyURLs(
			String layoutExternalReferenceCode, long groupId,
			boolean privateLayout, String friendlyURL)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		if (group.isLayoutSetPrototype()) {
			long count = _getDuplicatedFriendlyURLSiteLayoutsCount(
				layoutExternalReferenceCode, group.getCompanyId(),
				group.getGroupId(), friendlyURL);

			if (count > 0) {
				return true;
			}

			return false;
		}

		return _hasDuplicatedFriendlyURLPrototypeLayout(
			layoutExternalReferenceCode, groupId, privateLayout, friendlyURL);
	}

	/**
	 * Checks the permissions necessary for resetting the layout. If sufficient,
	 * the layout is reset by calling {@link #_resetPrototype(Layout)}.
	 *
	 * @param layout the page being checked for sufficient permissions
	 */
	@Override
	public void resetPrototype(Layout layout) throws PortalException {
		_checkResetPrototypePermissions(layout.getGroup(), layout);

		_resetPrototype(layout);
	}

	/**
	 * Sets the number of failed merge attempts for the layout prototype to a
	 * new value.
	 *
	 * @param layoutPrototype the page template of the counter being updated
	 * @param newMergeFailCount the new value of the counter
	 */
	@Override
	public void setMergeFailCount(
			LayoutPrototype layoutPrototype, int newMergeFailCount)
		throws PortalException {

		Layout layoutPrototypeLayout = layoutPrototype.getLayout();

		boolean updateLayoutPrototypeLayout = false;

		UnicodeProperties prototypeTypeSettingsUnicodeProperties =
			layoutPrototypeLayout.getTypeSettingsProperties();

		if (newMergeFailCount == 0) {
			if (prototypeTypeSettingsUnicodeProperties.containsKey(
					Sites.MERGE_FAIL_COUNT)) {

				prototypeTypeSettingsUnicodeProperties.remove(
					Sites.MERGE_FAIL_COUNT);

				updateLayoutPrototypeLayout = true;
			}
		}
		else {
			prototypeTypeSettingsUnicodeProperties.setProperty(
				Sites.MERGE_FAIL_COUNT, String.valueOf(newMergeFailCount));

			updateLayoutPrototypeLayout = true;
		}

		if (updateLayoutPrototypeLayout) {
			_layoutService.updateTypeSettings(
				layoutPrototypeLayout.getGroupId(),
				layoutPrototypeLayout.isPrivateLayout(),
				layoutPrototypeLayout.getLayoutId(),
				layoutPrototypeLayout.getTypeSettings());
		}
	}

	private ExportImportConfiguration _buildExportImportConfiguration(
			boolean initialSync, LayoutSet layoutSet)
		throws PortalException {

		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.
				getLayoutSetPrototypeByUuidAndCompanyId(
					layoutSet.getLayoutSetPrototypeUuid(),
					layoutSet.getCompanyId());

		Map<String, String[]> parameterMap = _getLayoutSetPrototypesParameters(
			initialSync);

		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_PRIVATE_LAYOUT,
			new String[] {String.valueOf(layoutSet.isPrivateLayout())});
		parameterMap.put(
			"layoutSetId",
			new String[] {String.valueOf(layoutSet.getLayoutSetId())});
		parameterMap.put(
			"layoutSetPrototypeId",
			new String[] {
				String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId())
			});

		User user = _userLocalService.getDefaultUser(layoutSet.getCompanyId());

		List<Layout> layoutSetPrototypeLayouts = _layoutLocalService.getLayouts(
			layoutSetPrototype.getGroupId(), true);

		Map<String, Serializable> exportLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					user, layoutSetPrototype.getGroupId(), true,
					_exportImportHelper.getLayoutIds(layoutSetPrototypeLayouts),
					parameterMap);

		return _exportImportConfigurationLocalService.
			addDraftExportImportConfiguration(
				user.getUserId(),
				ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
				exportLayoutSettingsMap);
	}

	/**
	 * Checks the permissions necessary for resetting the layout or site. If the
	 * permissions are not sufficient, a {@link PortalException} is thrown.
	 *
	 * @param group the site being checked for sufficient permissions
	 * @param layout the page being checked for sufficient permissions
	 *        (optionally <code>null</code>). If <code>null</code>, the
	 *        permissions are only checked for resetting the site.
	 */
	private void _checkResetPrototypePermissions(Group group, Layout layout)
		throws PortalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((layout != null) &&
			!_layoutPermission.contains(
				permissionChecker, layout, ActionKeys.UPDATE)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, layout.getName(), layout.getLayoutId(),
				ActionKeys.UPDATE);
		}
		else if (!GroupPermissionUtil.contains(
					permissionChecker, group, ActionKeys.UPDATE) &&
				 (!group.isUser() ||
				  (permissionChecker.getUserId() != group.getClassPK()))) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, group.getName(), group.getGroupId(),
				ActionKeys.UPDATE);
		}
	}

	private Layout _getDuplicatedFriendlyURLPrototypeLayout(Layout layout)
		throws PortalException {

		LayoutSet layoutSet = layout.getLayoutSet();

		if (!layoutSet.isLayoutSetPrototypeLinkActive()) {
			return null;
		}

		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.
				getLayoutSetPrototypeByUuidAndCompanyId(
					layoutSet.getLayoutSetPrototypeUuid(),
					layoutSet.getCompanyId());

		LayoutSet prototypeLayoutSet = layoutSetPrototype.getLayoutSet();

		LayoutFriendlyURL layoutFriendlyURL =
			_layoutFriendlyURLLocalService.fetchFirstLayoutFriendlyURL(
				prototypeLayoutSet.getGroupId(),
				prototypeLayoutSet.isPrivateLayout(), layout.getFriendlyURL());

		if (layoutFriendlyURL == null) {
			return null;
		}

		Layout foundLayout = _layoutLocalService.getLayout(
			layoutFriendlyURL.getPlid());

		String layoutSetPrototypeLayoutERC =
			layout.getLayoutSetPrototypeLayoutERC();

		if (Validator.isNotNull(layout.getLayoutSetPrototypeLayoutERC()) &&
			layoutSetPrototypeLayoutERC.equals(
				foundLayout.getExternalReferenceCode())) {

			return null;
		}

		return foundLayout;
	}

	private List<Layout> _getDuplicatedFriendlyURLSiteLayouts(Layout layout)
		throws PortalException {

		return _layoutLocalService.getLayouts(
			_layoutLocalService.dslQuery(
				DSLQueryFactoryUtil.selectDistinct(
					LayoutTable.INSTANCE.plid
				).from(
					LayoutTable.INSTANCE
				).innerJoinON(
					LayoutSetTable.INSTANCE,
					LayoutSetTable.INSTANCE.companyId.eq(
						LayoutTable.INSTANCE.companyId
					).and(
						LayoutSetTable.INSTANCE.groupId.eq(
							LayoutTable.INSTANCE.groupId)
					).and(
						LayoutSetTable.INSTANCE.privateLayout.eq(
							LayoutTable.INSTANCE.privateLayout)
					)
				).innerJoinON(
					LayoutSetPrototypeTable.INSTANCE,
					LayoutSetPrototypeTable.INSTANCE.companyId.eq(
						LayoutSetTable.INSTANCE.companyId
					).and(
						LayoutSetPrototypeTable.INSTANCE.uuid.eq(
							LayoutSetTable.INSTANCE.layoutSetPrototypeUuid)
					)
				).innerJoinON(
					GroupTable.INSTANCE,
					GroupTable.INSTANCE.companyId.eq(
						LayoutSetPrototypeTable.INSTANCE.companyId
					).and(
						GroupTable.INSTANCE.classPK.eq(
							LayoutSetPrototypeTable.INSTANCE.
								layoutSetPrototypeId)
					)
				).where(
					LayoutSetTable.INSTANCE.companyId.eq(
						layout.getCompanyId()
					).and(
						LayoutTable.INSTANCE.friendlyURL.eq(
							layout.getFriendlyURL())
					).and(
						LayoutTable.INSTANCE.layoutSetPrototypeLayoutERC.
							isNull()
					).and(
						GroupTable.INSTANCE.groupId.eq(layout.getGroupId())
					)
				)));
	}

	private long _getDuplicatedFriendlyURLSiteLayoutsCount(
			String layoutExternalReferenceCode, long companyId, long groupId,
			String friendlyURL)
		throws PortalException {

		Predicate layoutSetPrototypeLayoutERCPredicate =
			LayoutTable.INSTANCE.layoutSetPrototypeLayoutERC.isNull();

		if (Validator.isNotNull(layoutExternalReferenceCode)) {
			layoutSetPrototypeLayoutERCPredicate = Predicate.withParentheses(
				layoutSetPrototypeLayoutERCPredicate.or(
					LayoutTable.INSTANCE.layoutSetPrototypeLayoutERC.neq(
						layoutExternalReferenceCode)));
		}

		return _layoutLocalService.dslQuery(
			DSLQueryFactoryUtil.count(
			).from(
				LayoutTable.INSTANCE
			).innerJoinON(
				LayoutSetTable.INSTANCE,
				LayoutSetTable.INSTANCE.companyId.eq(
					LayoutTable.INSTANCE.companyId
				).and(
					LayoutSetTable.INSTANCE.groupId.eq(
						LayoutTable.INSTANCE.groupId)
				).and(
					LayoutSetTable.INSTANCE.privateLayout.eq(
						LayoutTable.INSTANCE.privateLayout)
				)
			).innerJoinON(
				LayoutSetPrototypeTable.INSTANCE,
				LayoutSetPrototypeTable.INSTANCE.companyId.eq(
					LayoutSetTable.INSTANCE.companyId
				).and(
					LayoutSetPrototypeTable.INSTANCE.uuid.eq(
						LayoutSetTable.INSTANCE.layoutSetPrototypeUuid)
				)
			).innerJoinON(
				GroupTable.INSTANCE,
				GroupTable.INSTANCE.companyId.eq(
					LayoutSetPrototypeTable.INSTANCE.companyId
				).and(
					GroupTable.INSTANCE.classPK.eq(
						LayoutSetPrototypeTable.INSTANCE.layoutSetPrototypeId)
				)
			).where(
				LayoutTable.INSTANCE.companyId.eq(
					companyId
				).and(
					GroupTable.INSTANCE.groupId.eq(groupId)
				).and(
					LayoutTable.INSTANCE.friendlyURL.eq(friendlyURL)
				).and(
					layoutSetPrototypeLayoutERCPredicate
				)
			));
	}

	private Map<String, String[]> _getLayoutSetPrototypesParameters(
		boolean initialSync) {

		return LinkedHashMapBuilder.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR}
		).put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.FAVICON,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[] {
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE
			}
		).put(
			PortletDataHandlerKeys.LOGO, new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {String.valueOf(initialSync)}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID}
		).build();
	}

	private boolean _hasDuplicatedFriendlyURLPrototypeLayout(
			String layoutSetPrototypeLayoutERC, long groupId,
			boolean privateLayout, String friendlyURL)
		throws PortalException {

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			groupId, privateLayout);

		if (!layoutSet.isLayoutSetPrototypeLinkActive()) {
			return false;
		}

		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.
				getLayoutSetPrototypeByUuidAndCompanyId(
					layoutSet.getLayoutSetPrototypeUuid(),
					layoutSet.getCompanyId());

		LayoutSet prototypeLayoutSet = layoutSetPrototype.getLayoutSet();

		LayoutFriendlyURL layoutFriendlyURL =
			_layoutFriendlyURLLocalService.fetchFirstLayoutFriendlyURL(
				prototypeLayoutSet.getGroupId(),
				prototypeLayoutSet.isPrivateLayout(), friendlyURL);

		if (layoutFriendlyURL == null) {
			return false;
		}

		Layout foundLayout = _layoutLocalService.getLayout(
			layoutFriendlyURL.getPlid());

		if (Validator.isNotNull(layoutSetPrototypeLayoutERC) &&
			layoutSetPrototypeLayoutERC.equals(
				foundLayout.getExternalReferenceCode())) {

			return false;
		}

		return true;
	}

	private boolean _isLayoutSetMergeable(Group group, LayoutSet layoutSet) {
		if (!layoutSet.isLayoutSetPrototypeLinkActive() ||
			group.isLayoutPrototype() || group.isLayoutSetPrototype()) {

			return false;
		}

		return true;
	}

	/**
	 * Resets the modified timestamp on the layout so the linked page template is
	 * merged into the layout when it is first accessed.
	 *
	 * @param layout the page having its timestamp reset
	 */
	private void _resetPrototype(Layout layout) throws PortalException {
		layout.setModifiedDate(null);

		_layoutLocalService.updateLayout(layout);
	}

	private void _runSyncInBackground(
			ExportImportConfiguration exportImportConfiguration,
			LayoutSet layoutSet)
		throws PortalException {

		User user = _userLocalService.getDefaultUser(layoutSet.getCompanyId());

		_exportImportLocalService.syncLayoutSetPrototypeInBackground(
			user.getUserId(), layoutSet.getGroupId(),
			exportImportConfiguration);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeHelperImpl.class);

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private ExportImportLocalService _exportImportLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}