/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.helper;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.metrics.model.Assignment;
import com.liferay.portal.workflow.metrics.model.RoleAssignment;
import com.liferay.portal.workflow.metrics.model.UserAssignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = IndexerHelper.class)
public class IndexerHelper {

	public Map<Locale, String> createAssetTitleLocalizationMap(
		String className, long classPK, long groupId) {

		AssetRenderer<?> assetRenderer = _getAssetRenderer(className, classPK);

		if (assetRenderer != null) {
			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
				assetRenderer.getClassName(), assetRenderer.getClassPK());

			if (assetEntry != null) {
				return LocalizationUtil.populateLocalizationMap(
					assetEntry.getTitleMap(), assetEntry.getDefaultLanguageId(),
					assetEntry.getGroupId());
			}
		}

		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

		if (workflowHandler != null) {
			Map<Locale, String> localizationMap = new HashMap<>();

			for (Locale availableLocale :
					LanguageUtil.getAvailableLocales(groupId)) {

				localizationMap.put(
					availableLocale,
					workflowHandler.getTitle(classPK, availableLocale));
			}

			return localizationMap;
		}

		return Collections.emptyMap();
	}

	public Map<Locale, String> createAssetTypeLocalizationMap(
		String className, long groupId) {

		Map<Locale, String> localizationMap = new HashMap<>();

		for (Locale availableLocale :
				LanguageUtil.getAvailableLocales(groupId)) {

			localizationMap.put(
				availableLocale,
				ResourceActionsUtil.getModelResource(
					availableLocale, className));
		}

		return localizationMap;
	}

	public List<Assignment> toAssignments(
		List<KaleoTaskAssignmentInstance> kaleoTaskAssignmentInstances) {

		List<Assignment> assignments = new ArrayList<>();

		if (ListUtil.isEmpty(kaleoTaskAssignmentInstances)) {
			return assignments;
		}

		KaleoTaskAssignmentInstance firstKaleoTaskAssignmentInstance =
			kaleoTaskAssignmentInstances.get(0);

		if (Objects.equals(
				firstKaleoTaskAssignmentInstance.getAssigneeClassName(),
				User.class.getName())) {

			User user = _userLocalService.fetchUser(
				firstKaleoTaskAssignmentInstance.getAssigneeClassPK());

			assignments.add(
				new UserAssignment(
					firstKaleoTaskAssignmentInstance.getAssigneeClassPK(),
					user.getFullName()));
		}
		else {
			Stream.of(
				kaleoTaskAssignmentInstances
			).flatMap(
				List::stream
			).collect(
				Collectors.groupingBy(
					KaleoTaskAssignmentInstance::getAssigneeClassPK,
					Collectors.mapping(
						KaleoTaskAssignmentInstance::getGroupId,
						Collectors.toList()))
			).forEach(
				(assignmentId, assignmentGroupIds) -> assignments.add(
					new RoleAssignment(assignmentId, assignmentGroupIds))
			);
		}

		return assignments;
	}

	private AssetRenderer<?> _getAssetRenderer(String className, long classPK) {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (assetRendererFactory != null) {
			try {
				return assetRendererFactory.getAssetRenderer(classPK);
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException, portalException);
				}
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(IndexerHelper.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}