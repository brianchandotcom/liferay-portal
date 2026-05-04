/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.resource.v1_0;

import com.liferay.exportimport.constants.ExportImportConstants;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactory;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerChoice;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.portlet.data.handler.provider.PortletDataHandlerProvider;
import com.liferay.exportimport.rest.dto.v1_0.Choice;
import com.liferay.exportimport.rest.dto.v1_0.ExportPreview;
import com.liferay.exportimport.rest.dto.v1_0.PortletDataHandler;
import com.liferay.exportimport.rest.dto.v1_0.PortletDataHandlerControl;
import com.liferay.exportimport.rest.dto.v1_0.PortletDataHandlerSection;
import com.liferay.exportimport.rest.dto.v1_0.PortletDataHandlerSetting;
import com.liferay.exportimport.rest.internal.util.PermissionUtil;
import com.liferay.exportimport.rest.resource.v1_0.ExportPreviewResource;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.staging.StagingGroupHelper;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Daniel Raposo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/export-preview.properties",
	scope = ServiceScope.PROTOTYPE, service = ExportPreviewResource.class
)
public class ExportPreviewResourceImpl extends BaseExportPreviewResourceImpl {

	@Override
	public ExportPreview getAssetLibraryExportPreview(
			String assetLibraryExternalReferenceCode, Date endDate,
			Integer last, String range, Date startDate)
		throws Exception {

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			assetLibraryExternalReferenceCode, contextCompany.getCompanyId());

		if (!group.isDepot()) {
			throw new NotFoundException();
		}

		return _getExportPreview(
			endDate, group.getGroupId(), last, range, startDate);
	}

	@Override
	public ExportPreview getExportPreview(
			Date endDate, Integer last, String range, Date startDate)
		throws Exception {

		Group group = _stagingGroupHelper.fetchCompanyGroup(
			contextCompany.getCompanyId());

		return _getExportPreview(
			endDate, group.getGroupId(), last, range, startDate);
	}

	@Override
	public ExportPreview getSiteExportPreview(
			String siteExternalReferenceCode, Date endDate, Integer last,
			String range, Date startDate)
		throws Exception {

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		if (!group.isSite()) {
			throw new NotFoundException();
		}

		return _getExportPreview(
			endDate, group.getGroupId(), last, range, startDate);
	}

	private DateRange _getDateRange(
		Date endDate, Integer last, String range, Date startDate) {

		if (range.equals(ExportImportDateUtil.RANGE_DATE_RANGE)) {
			return new DateRange(startDate, endDate);
		}

		if (range.equals(ExportImportDateUtil.RANGE_LAST)) {
			if (last == null) {
				throw new BadRequestException();
			}

			Date now = new Date();

			return new DateRange(
				new Date(now.getTime() - (last * Time.HOUR)), now);
		}

		return new DateRange(null, null);
	}

	private ExportPreview _getExportPreview(
			Date endDate, long groupId, Integer last, String range,
			Date startDate)
		throws Exception {

		PermissionUtil.checkExportPermission(
			contextCompany.getCompanyId(), groupId);

		DateRange dateRange = _getDateRange(endDate, last, range, startDate);

		Locale locale = contextAcceptLanguage.getPreferredLocale();

		Map<String, List<PortletDataHandler>> portletDataHandlersMap =
			new LinkedHashMap<>();

		for (Portlet portlet :
				_exportImportHelper.getExportablePortlets(
					contextCompany.getCompanyId(), false, groupId)) {

			com.liferay.exportimport.kernel.lar.PortletDataHandler
				portletDataHandler = _portletDataHandlerProvider.provide(
					portlet);

			if ((portletDataHandler == null) || portletDataHandler.isHidden() ||
				!portletDataHandler.isEnabled(contextCompany.getCompanyId())) {

				continue;
			}

			PortletDataContext portletDataContext =
				_portletDataContextFactory.createPreparePortletDataContext(
					contextCompany.getCompanyId(), groupId, range,
					dateRange.getStartDate(), dateRange.getEndDate());

			portletDataHandler.prepareManifestSummary(portletDataContext);

			ManifestSummary manifestSummary =
				portletDataContext.getManifestSummary();

			long exportModelCount = Math.max(
				0L, portletDataHandler.getExportModelCount(manifestSummary));
			long modelDeletionCount = Math.max(
				0L,
				manifestSummary.getModelDeletionCount(
					portletDataHandler.
						getDeletionSystemEventStagedModelTypes()));

			if ((exportModelCount == 0) && (modelDeletionCount == 0)) {
				continue;
			}

			String sectionKey = portletDataHandler.getSectionKey();

			if (sectionKey == null) {
				sectionKey = ExportImportConstants.SECTION_KEY_OTHERS;
			}

			List<PortletDataHandler> portletDataHandlers =
				portletDataHandlersMap.computeIfAbsent(
					sectionKey, key -> new ArrayList<>());

			portletDataHandlers.add(
				_toPortletDataHandler(
					exportModelCount, locale, manifestSummary,
					modelDeletionCount, portlet, portletDataHandler));
		}

		List<PortletDataHandlerSection> portletDataHandlerSections =
			new ArrayList<>(portletDataHandlersMap.size());

		long totalAdditionCount = 0;
		long totalDeletionCount = 0;

		for (Map.Entry<String, List<PortletDataHandler>> entry :
				portletDataHandlersMap.entrySet()) {

			long additionCount = 0;
			long deletionCount = 0;

			for (PortletDataHandler portletDataHandler : entry.getValue()) {
				additionCount += portletDataHandler.getAdditionCount();
				deletionCount += portletDataHandler.getDeletionCount();
			}

			totalAdditionCount += additionCount;
			totalDeletionCount += deletionCount;

			long finalAdditionCount = additionCount;
			long finalDeletionCount = deletionCount;

			portletDataHandlerSections.add(
				new PortletDataHandlerSection() {
					{
						setAdditionCount(() -> finalAdditionCount);
						setDeletionCount(() -> finalDeletionCount);
						setLabel(() -> _language.get(locale, entry.getKey()));
						setName(entry::getKey);
						setPortletDataHandlers(
							() -> entry.getValue(
							).toArray(
								new PortletDataHandler[0]
							));
					}
				});
		}

		List<PortletDataHandlerSection> finalPortletDataHandlerSections =
			portletDataHandlerSections;
		long finalTotalAdditionCount = totalAdditionCount;
		long finalTotalDeletionCount = totalDeletionCount;

		return new ExportPreview() {
			{
				setAdditionCount(() -> finalTotalAdditionCount);
				setDeletionCount(() -> finalTotalDeletionCount);
				setPortletDataHandlerSections(
					() -> finalPortletDataHandlerSections.toArray(
						new PortletDataHandlerSection[0]));
			}
		};
	}

	private PortletDataHandler _toPortletDataHandler(
		long exportModelCount, Locale locale, ManifestSummary manifestSummary,
		long modelDeletionCount, Portlet portlet,
		com.liferay.exportimport.kernel.lar.PortletDataHandler
			portletDataHandler) {

		return new PortletDataHandler() {
			{
				setAdditionCount(() -> exportModelCount);
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
					() -> transform(
						portletDataHandler.
							getExportPortletDataHandlerControls(),
						exportPortletDataHandlerControl ->
							_toPortletDataHandlerControl(
								locale, manifestSummary,
								exportPortletDataHandlerControl),
						PortletDataHandlerControl.class));
			}
		};
	}

	private PortletDataHandlerControl _toPortletDataHandlerControl(
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
							() -> _language.get(
								locale, portletDataHandlerControl.getLabel()));
						setName(portletDataHandlerControl::getName);
						setPortletDataHandlerControls(
							() -> transform(
								portletDataHandlerBoolean.
									getChildrenPortletDataHandlerControls(),
								childPortletDataHandlerControl ->
									_toPortletDataHandlerControl(
										locale, manifestSummary,
										childPortletDataHandlerControl),
								PortletDataHandlerControl.class));
						setType(() -> Type.SETTING);
					}
				};
			}

			StagedModelType stagedModelType = new StagedModelType(
				portletDataHandlerBoolean.getClassName(),
				portletDataHandlerBoolean.getReferrerClassName());

			long modelAdditionCount = Math.max(
				0L, manifestSummary.getModelAdditionCount(stagedModelType));
			long modelDeletionCount = Math.max(
				0L, manifestSummary.getModelDeletionCount(stagedModelType));

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
						() -> _language.get(
							locale, portletDataHandlerControl.getLabel()));
					setName(portletDataHandlerControl::getName);
					setPortletDataHandlerControls(
						() -> transform(
							portletDataHandlerBoolean.
								getChildrenPortletDataHandlerControls(),
							childPortletDataHandlerControl ->
								_toPortletDataHandlerControl(
									locale, manifestSummary,
									childPortletDataHandlerControl),
							PortletDataHandlerControl.class));
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
						() -> transform(
							portletDataHandlerChoice.getChoices(),
							choice -> new Choice() {
								{
									setLabel(
										() -> _language.get(locale, choice));
									setName(() -> choice);
								}
							},
							Choice.class));
					setDefaultChoice(
						portletDataHandlerChoice::getDefaultChoice);
					setDisabled(portletDataHandlerControl::isDisabled);
					setLabel(
						() -> _language.get(
							locale, portletDataHandlerControl.getLabel()));
					setName(portletDataHandlerControl::getName);
					setType(() -> Type.CHOICE);
				}
			};
		}

		return null;
	}

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private Language _language;

	@Reference
	private PortletDataContextFactory _portletDataContextFactory;

	@Reference
	private PortletDataHandlerProvider _portletDataHandlerProvider;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}