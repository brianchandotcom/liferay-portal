/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.util;

import com.liferay.exportimport.constants.ExportImportConstants;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerChoice;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.rest.dto.v1_0.Choice;
import com.liferay.exportimport.rest.dto.v1_0.PortletDataHandler;
import com.liferay.exportimport.rest.dto.v1_0.PortletDataHandlerControl;
import com.liferay.exportimport.rest.dto.v1_0.PortletDataHandlerSection;
import com.liferay.exportimport.rest.dto.v1_0.PortletDataHandlerSetting;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Daniel Raposo
 */
public class PortletDataHandlerSectionUtil {

	public static void addPortletDataHandlerSection(
			long companyId, Locale locale, ManifestSummary manifestSummary,
			Portlet portlet,
			com.liferay.exportimport.kernel.lar.PortletDataHandler
				portletDataHandler,
			UnsafeFunction
				<com.liferay.exportimport.kernel.lar.PortletDataHandler,
				 com.liferay.exportimport.kernel.lar.
					 PortletDataHandlerControl[],
				 Exception> portletDataHandlerControlsUnsafeFunction,
			Map<String, List<PortletDataHandler>> portletDataHandlersMap)
		throws Exception {

		if ((portletDataHandler == null) || portletDataHandler.isHidden() ||
			!portletDataHandler.isEnabled(companyId)) {

			return;
		}

		long modelAdditionCount = Math.max(
			0L, portletDataHandler.getExportModelCount(manifestSummary));
		long modelDeletionCount = Math.max(
			0L,
			manifestSummary.getModelDeletionCount(
				portletDataHandler.getDeletionSystemEventStagedModelTypes()));

		if ((modelAdditionCount == 0) && (modelDeletionCount == 0)) {
			return;
		}

		String sectionKey = portletDataHandler.getSectionKey();

		if (sectionKey == null) {
			sectionKey = ExportImportConstants.SECTION_KEY_OTHER;
		}

		List<PortletDataHandler> portletDataHandlers =
			portletDataHandlersMap.computeIfAbsent(
				sectionKey, key -> new ArrayList<>());

		portletDataHandlers.add(
			_toPortletDataHandler(
				modelAdditionCount, locale, manifestSummary, modelDeletionCount,
				portlet, portletDataHandler,
				portletDataHandlerControlsUnsafeFunction.apply(
					portletDataHandler)));
	}

	public static long getAdditionCount(
		Map<String, List<PortletDataHandler>> portletDataHandlersMap) {

		long additionCount = 0;

		for (List<PortletDataHandler> portletDataHandlers :
				portletDataHandlersMap.values()) {

			for (PortletDataHandler portletDataHandler : portletDataHandlers) {
				additionCount += portletDataHandler.getAdditionCount();
			}
		}

		return additionCount;
	}

	public static long getDeletionCount(
		Map<String, List<PortletDataHandler>> portletDataHandlersMap) {

		long deletionCount = 0;

		for (List<PortletDataHandler> portletDataHandlers :
				portletDataHandlersMap.values()) {

			for (PortletDataHandler portletDataHandler : portletDataHandlers) {
				deletionCount += portletDataHandler.getDeletionCount();
			}
		}

		return deletionCount;
	}

	public static PortletDataHandlerSection[] toPortletDataHandlerSections(
		Locale locale,
		Map<String, List<PortletDataHandler>> portletDataHandlersMap) {

		return TransformUtil.transformToArray(
			portletDataHandlersMap.entrySet(),
			entry -> new PortletDataHandlerSection() {
				{
					long additionCount = 0;
					long deletionCount = 0;

					for (PortletDataHandler portletDataHandler :
							entry.getValue()) {

						additionCount += portletDataHandler.getAdditionCount();
						deletionCount += portletDataHandler.getDeletionCount();
					}

					long finalAdditionCount = additionCount;
					long finalDeletionCount = deletionCount;

					setAdditionCount(() -> finalAdditionCount);
					setDeletionCount(() -> finalDeletionCount);
					setLabel(() -> LanguageUtil.get(locale, entry.getKey()));
					setName(entry::getKey);
					setPortletDataHandlers(
						() -> entry.getValue(
						).toArray(
							new PortletDataHandler[0]
						));
				}
			},
			PortletDataHandlerSection.class);
	}

	private static PortletDataHandler _toPortletDataHandler(
		long modelAdditionCount, Locale locale, ManifestSummary manifestSummary,
		long modelDeletionCount, Portlet portlet,
		com.liferay.exportimport.kernel.lar.PortletDataHandler
			portletDataHandler,
		com.liferay.exportimport.kernel.lar.PortletDataHandlerControl[]
			sourcePortletDataHandlerControls) {

		return new PortletDataHandler() {
			{
				setAdditionCount(() -> modelAdditionCount);
				setDeletionCount(() -> modelDeletionCount);
				setLabel(
					() -> {
						String portletTitle = portletDataHandler.getTitle(
							locale);

						if (portletTitle == null) {
							portletTitle = PortalUtil.getPortletTitle(
								portlet, locale);
						}

						return portletTitle;
					});
				setName(portlet::getPortletId);
				setPortletDataHandlerControls(
					() -> {
						if ((sourcePortletDataHandlerControls == null) ||
							ArrayUtil.isEmpty(
								sourcePortletDataHandlerControls)) {

							return null;
						}

						return TransformUtil.transform(
							sourcePortletDataHandlerControls,
							sourcePortletDataHandlerControl ->
								_toPortletDataHandlerControl(
									locale, manifestSummary,
									sourcePortletDataHandlerControl),
							PortletDataHandlerControl.class);
					});
			}
		};
	}

	private static PortletDataHandlerControl _toPortletDataHandlerControl(
		Locale locale, ManifestSummary manifestSummary,
		com.liferay.exportimport.kernel.lar.PortletDataHandlerControl
			portletDataHandlerControl) {

		if (portletDataHandlerControl instanceof
				PortletDataHandlerBoolean portletDataHandlerBoolean) {

			if (portletDataHandlerBoolean.getClassName() == null) {
				return new PortletDataHandlerSetting() {
					{
						setDefaultState(
							portletDataHandlerBoolean::getDefaultState);
						setDisabled(portletDataHandlerControl::isDisabled);
						setLabel(
							() -> LanguageUtil.get(
								locale, portletDataHandlerControl.getLabel()));
						setName(portletDataHandlerControl::getName);
						setPortletDataHandlerControls(
							() -> {
								com.liferay.exportimport.kernel.lar.
									PortletDataHandlerControl[]
										childrenPortletDataHandlerControls =
											portletDataHandlerBoolean.
												getChildrenPortletDataHandlerControls();

								if ((childrenPortletDataHandlerControls ==
										null) ||
									ArrayUtil.isEmpty(
										childrenPortletDataHandlerControls)) {

									return null;
								}

								return TransformUtil.transform(
									childrenPortletDataHandlerControls,
									childPortletDataHandlerControl ->
										_toPortletDataHandlerControl(
											locale, manifestSummary,
											childPortletDataHandlerControl),
									PortletDataHandlerControl.class);
							});
						setType(() -> Type.SETTING);
					}
				};
			}

			long modelAdditionCount = Math.max(
				0L,
				manifestSummary.getModelAdditionCount(
					new StagedModelType(
						portletDataHandlerBoolean.getClassName(),
						portletDataHandlerBoolean.getReferrerClassName())));
			long modelDeletionCount = Math.max(
				0L,
				manifestSummary.getModelDeletionCount(
					new StagedModelType(
						portletDataHandlerBoolean.getClassName(),
						portletDataHandlerBoolean.getReferrerClassName())));

			if ((modelAdditionCount == 0) && (modelDeletionCount == 0)) {
				return null;
			}

			return new com.liferay.exportimport.rest.dto.v1_0.
				PortletDataHandlerBoolean() {

				{
					setAdditionCount(() -> modelAdditionCount);
					setDefaultState(portletDataHandlerBoolean::getDefaultState);
					setDeletionCount(() -> modelDeletionCount);
					setDisabled(portletDataHandlerControl::isDisabled);
					setLabel(
						() -> LanguageUtil.get(
							locale, portletDataHandlerControl.getLabel()));
					setName(portletDataHandlerControl::getName);
					setPortletDataHandlerControls(
						() -> {
							com.liferay.exportimport.kernel.lar.
								PortletDataHandlerControl[]
									childrenPortletDataHandlerControls =
										portletDataHandlerBoolean.
											getChildrenPortletDataHandlerControls();

							if ((childrenPortletDataHandlerControls == null) ||
								ArrayUtil.isEmpty(
									childrenPortletDataHandlerControls)) {

								return null;
							}

							return TransformUtil.transform(
								childrenPortletDataHandlerControls,
								childPortletDataHandlerControl ->
									_toPortletDataHandlerControl(
										locale, manifestSummary,
										childPortletDataHandlerControl),
								PortletDataHandlerControl.class);
						});
					setType(() -> Type.BOOLEAN);
				}
			};
		}

		if (portletDataHandlerControl instanceof
				PortletDataHandlerChoice portletDataHandlerChoice) {

			return new com.liferay.exportimport.rest.dto.v1_0.
				PortletDataHandlerChoice() {

				{
					setChoices(
						() -> TransformUtil.transform(
							portletDataHandlerChoice.getChoices(),
							choice -> new Choice() {
								{
									setLabel(
										() -> LanguageUtil.get(locale, choice));
									setName(() -> choice);
								}
							},
							Choice.class));
					setDefaultChoice(
						portletDataHandlerChoice::getDefaultChoice);
					setDisabled(portletDataHandlerControl::isDisabled);
					setLabel(
						() -> LanguageUtil.get(
							locale, portletDataHandlerControl.getLabel()));
					setName(portletDataHandlerControl::getName);
					setType(() -> Type.CHOICE);
				}
			};
		}

		return null;
	}

}