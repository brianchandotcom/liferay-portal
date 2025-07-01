/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.web.internal.portlet.action;

import com.liferay.osb.patcher.constants.PatcherPortletKeys;
import com.liferay.osb.patcher.constants.WorkflowConstants;
import com.liferay.osb.patcher.model.PatcherFix;
import com.liferay.osb.patcher.model.PatcherFixComponent;
import com.liferay.osb.patcher.model.PatcherFixPack;
import com.liferay.osb.patcher.service.PatcherFixComponentLocalService;
import com.liferay.osb.patcher.service.PatcherFixLocalService;
import com.liferay.osb.patcher.service.PatcherFixPackLocalService;
import com.liferay.osb.patcher.util.JenkinsUtil;
import com.liferay.osb.patcher.util.PatcherFixPackUtil;
import com.liferay.osb.patcher.util.PatcherFixUtil;
import com.liferay.osb.patcher.util.PatcherUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"jakarta.portlet.name=" + PatcherPortletKeys.PATCHER,
		"mvc.command.name=/patcher/set_fix_pack_fields_fixes"
	},
	service = MVCActionCommand.class
)
public class SetFixPackFieldsFixesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long patcherFixId = ParamUtil.getLong(actionRequest, "patcherFixId");
		String dependencies = ParamUtil.getString(
			actionRequest, "dependencies");
		int fixPackStatus = ParamUtil.getInteger(
			actionRequest, "fixPackStatus");
		String requirements = ParamUtil.getString(
			actionRequest, "requirements");
		Set<Long> patcherFixPackIds = SetUtil.fromArray(
			ParamUtil.getLongValues(actionRequest, "patcherFixPackIds"));

		PatcherFix patcherFix = _patcherFixLocalService.getPatcherFix(
			patcherFixId);

		_validateSetFixPackFields(
			dependencies, patcherFix, patcherFixPackIds, requirements,
			themeDisplay);

		_patcherFixPackLocalService.clearPatcherFixPatcherFixPacks(
			patcherFix.getPatcherFixId());

		for (long patcherFixPackId : patcherFixPackIds) {
			_patcherFixPackLocalService.addPatcherFixPatcherFixPack(
				patcherFix.getPatcherFixId(), patcherFixPackId);
		}

		_patcherFixLocalService.updatePatcherFix(
			patcherFixId, dependencies, fixPackStatus, requirements);
	}

	private void _validatePatcherFixPackMainFix(
			PatcherFix patcherFix, ThemeDisplay themeDisplay)
		throws Exception {

		PatcherFixPack patcherFixPack = PatcherFixPackUtil.fetchPatcherFixPack(
			patcherFix.getName(), patcherFix.getPatcherProjectVersionId());

		if (patcherFixPack != null) {
			throw new Exception(
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-main-fix-of-a-fix-pack-cannot-change"));
		}
	}

	private void _validateSetFixPackFields(
			String dependencies, PatcherFix patcherFix,
			Set<Long> patcherFixPackIds, String requirements,
			ThemeDisplay themeDisplay)
		throws Exception {

		_validatePatcherFixPackMainFix(patcherFix, themeDisplay);

		Set<String> dependenciesComponentNames = new HashSet<>();

		List<String> dependenciesTokens = PatcherUtil.getTokens(dependencies);

		for (String dependenciesToken : dependenciesTokens) {
			String[] names = dependenciesToken.split("->");

			if ((names.length % 2) != 0) {
				throw new Exception(
					LanguageUtil.get(
						themeDisplay.getLocale(),
						"the-fix's-dependencies-are-invalid"));
			}

			for (String name : names) {
				PatcherFixComponent patcherFixComponent =
					_patcherFixComponentLocalService.fetchPatcherFixComponent(
						name);

				if (patcherFixComponent == null) {
					throw new Exception(
						LanguageUtil.get(
							themeDisplay.getLocale(),
							"the-fix's-dependencies-has-an-invalid-fix-" +
								"component"));
				}

				dependenciesComponentNames.add(name);
			}
		}

		Set<Long> patcherFixComponentIds = new HashSet<>();

		Map<String, Set<String>> patcherFixComponentDependencies =
			PatcherFixUtil.getComponentDependencies(
				patcherFix.getDependencies());
		Map<String, Set<String>> requestComponentDependencies =
			PatcherFixUtil.getComponentDependencies(dependencies);

		for (long patcherFixPackId : patcherFixPackIds) {
			PatcherFixPack patcherFixPack =
				_patcherFixPackLocalService.getPatcherFixPack(patcherFixPackId);

			if (patcherFixComponentIds.contains(
					patcherFixPack.getPatcherFixComponentId())) {

				throw new Exception(
					LanguageUtil.get(
						themeDisplay.getLocale(),
						"the-fix-cannot-be-in-multiple-fix-packs-with-the-" +
							"same-component"));
			}

			patcherFixComponentIds.add(
				patcherFixPack.getPatcherFixComponentId());

			PatcherFixComponent patcherFixComponent =
				_patcherFixComponentLocalService.getPatcherFixComponent(
					patcherFixPack.getPatcherFixComponentId());

			dependenciesComponentNames.remove(patcherFixComponent.getName());

			if (patcherFixPack.getStatus() ==
					WorkflowConstants.STATUS_FIX_PACK_UNDER_DEVELOPMENT) {

				continue;
			}

			Set<String> patcherFixComponentNameDependencies = new HashSet<>();

			if (patcherFixComponentDependencies.containsKey(
					patcherFixComponent.getName())) {

				patcherFixComponentNameDependencies =
					patcherFixComponentDependencies.get(
						patcherFixComponent.getName());
			}

			Set<String> requestComponentNameDependencies = new HashSet<>();

			if (requestComponentDependencies.containsKey(
					patcherFixComponent.getName())) {

				requestComponentNameDependencies =
					requestComponentDependencies.get(
						patcherFixComponent.getName());
			}

			if (!requestComponentNameDependencies.equals(
					patcherFixComponentNameDependencies)) {

				throw new Exception(
					LanguageUtil.format(
						themeDisplay.getLocale(),
						"the-fix-pack-x-is-not-under-development-so-its-" +
							"dependencies-cannot-change",
						patcherFixPack.getName()));
			}
		}

		if (!dependenciesComponentNames.isEmpty()) {
			throw new Exception(
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-fix's-current-fix-packs-must-include-the-fix's-" +
						"dependency-components"));
		}

		Set<Long> oldPatcherFixPackIds = new HashSet<>();

		List<PatcherFixPack> patcherFixPatcherFixPacks =
			_patcherFixPackLocalService.getPatcherFixPatcherFixPacks(
				patcherFix.getPatcherFixId());

		for (PatcherFixPack patcherFixPatcherFixPack :
				patcherFixPatcherFixPacks) {

			oldPatcherFixPackIds.add(
				patcherFixPatcherFixPack.getPatcherFixPackId());
		}

		Set<Long> newPatcherFixPackIds = new HashSet<>(patcherFixPackIds);

		newPatcherFixPackIds.removeAll(oldPatcherFixPackIds);

		for (long newPatcherFixPackId : newPatcherFixPackIds) {
			List<String> patcherFixTokens = PatcherUtil.getTokens(
				patcherFix.getName());

			List<PatcherFix> patcherFixPackPatcherFixes =
				_patcherFixLocalService.getPatcherFixPackPatcherFixes(
					newPatcherFixPackId);

			String patcherFixesNames = ListUtil.toString(
				patcherFixPackPatcherFixes, "name");

			patcherFixTokens.retainAll(
				PatcherUtil.getTokens(patcherFixesNames));

			if (patcherFixTokens.isEmpty()) {
				continue;
			}

			List<Long> patcherFixIds = new ArrayList<>();

			for (PatcherFix patcherFixPackPatcherFix :
					patcherFixPackPatcherFixes) {

				List<String> patcherFixPackPatcherFixTokens =
					PatcherUtil.getTokens(patcherFixPackPatcherFix.getName());

				if (!Collections.disjoint(
						patcherFixTokens, patcherFixPackPatcherFixTokens)) {

					patcherFixIds.add(
						patcherFixPackPatcherFix.getPatcherFixId());
				}
			}

			PatcherFixPack patcherFixPack =
				_patcherFixPackLocalService.getPatcherFixPack(
					newPatcherFixPackId);

			throw new Exception(
				LanguageUtil.format(
					themeDisplay.getLocale(),
					"the-fix-pack-x-already-has-tickets-x-in-fixes-x",
					new Object[] {
						patcherFixPack.getName(),
						StringUtil.merge(
							patcherFixTokens, StringPool.COMMA_AND_SPACE),
						StringUtil.merge(
							patcherFixIds, StringPool.COMMA_AND_SPACE)
					}));
		}

		Set<Long> changedPatcherFixPackIds = new HashSet<>(
			newPatcherFixPackIds);

		oldPatcherFixPackIds.removeAll(patcherFixPackIds);

		changedPatcherFixPackIds.addAll(oldPatcherFixPackIds);

		for (long changedPatcherFixPackId : changedPatcherFixPackIds) {
			PatcherFixPack patcherFixPack =
				_patcherFixPackLocalService.getPatcherFixPack(
					changedPatcherFixPackId);

			if (patcherFixPack.getStatus() !=
					WorkflowConstants.STATUS_FIX_PACK_UNDER_DEVELOPMENT) {

				throw new Exception(
					LanguageUtil.format(
						themeDisplay.getLocale(),
						"the-fix-pack-x-is-not-under-development-so-its-" +
							"dependencies-cannot-change",
						patcherFixPack.getName()));
			}
		}

		List<String> requirementsTokens = PatcherUtil.getTokens(requirements);

		for (String requirementsToken : requirementsTokens) {
			if (!JenkinsUtil.isValidJenkinsRequirement(requirementsToken)) {
				throw new Exception(
					LanguageUtil.format(
						themeDisplay.getLocale(),
						"the-fix's-requirement-x-is-invalid",
						requirementsToken));
			}
		}
	}

	@Reference
	private PatcherFixComponentLocalService _patcherFixComponentLocalService;

	@Reference
	private PatcherFixLocalService _patcherFixLocalService;

	@Reference
	private PatcherFixPackLocalService _patcherFixPackLocalService;

}