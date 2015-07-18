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

package com.liferay.portlet.exportimport.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.io.File;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Daniel Kocsis
 */
public class BridgeExportImportHelperImpl implements ExportImportHelper {

	public BridgeExportImportHelperImpl() {
		this(new DummyExportImportHelperImpl());
	}

	public BridgeExportImportHelperImpl(
		ExportImportHelper defaultExportImportHelper) {

		_defaultExportImportHelper = defaultExportImportHelper;

		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(ExportImportHelper.class);

		_serviceTracker.open();
	}

	@Override
	public long[] getAllLayoutIds(long groupId, boolean privateLayout) {
		return getExportImportHelper().getAllLayoutIds(groupId, privateLayout);
	}

	@Override
	public Map<Long, Boolean> getAllLayoutIdsMap(
		long groupId, boolean privateLayout) {

		return getExportImportHelper().getAllLayoutIdsMap(
			groupId, privateLayout);
	}

	/**
	 * @deprecated As of 7.0.0, moved to {@link
	 *             ExportImportDateUtil#getCalendar(PortletRequest, String,
	 *             boolean)}
	 */
	@Deprecated
	@Override
	public Calendar getCalendar(
		PortletRequest portletRequest, String paramPrefix,
		boolean timeZoneSensitive) {

		return getExportImportHelper().getCalendar(
			portletRequest, paramPrefix, timeZoneSensitive);
	}

	@Override
	public List<Portlet> getDataSiteLevelPortlets(long companyId)
		throws Exception {

		return getExportImportHelper().getDataSiteLevelPortlets(companyId);
	}

	@Override
	public List<Portlet> getDataSiteLevelPortlets(
			long companyId, boolean excludeDataAlwaysStaged)
		throws Exception {

		return getExportImportHelper().getDataSiteLevelPortlets(
			companyId, excludeDataAlwaysStaged);
	}

	/**
	 * @deprecated As of 7.0.0, moved to {@link
	 *             ExportImportDateUtil#getDateRange(PortletRequest, long,
	 *             boolean, long, String, String)}
	 */
	@Deprecated
	@Override
	public DateRange getDateRange(
			PortletRequest portletRequest, long groupId, boolean privateLayout,
			long plid, String portletId, String defaultRange)
		throws Exception {

		return getExportImportHelper().getDateRange(
			portletRequest, groupId, privateLayout, plid, portletId,
			defaultRange);
	}

	@Override
	public Layout getExportableLayout(ThemeDisplay themeDisplay)
		throws PortalException {

		return getExportImportHelper().getExportableLayout(themeDisplay);
	}

	@Override
	public String getExportableRootPortletId(long companyId, String portletId)
		throws Exception {

		return getExportImportHelper().getExportableRootPortletId(
			companyId, portletId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getExportPortletControlsMap(long, String, Map)}
	 */
	@Deprecated
	@Override
	public boolean[] getExportPortletControls(
			long companyId, String portletId,
			Map<String, String[]> parameterMap)
		throws Exception {

		return getExportImportHelper().getExportPortletControls(
			companyId, portletId, parameterMap);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getExportPortletControlsMap(long, String, Map, String)}
	 */
	@Deprecated
	@Override
	public boolean[] getExportPortletControls(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, String type)
		throws Exception {

		return getExportImportHelper().getExportPortletControls(
			companyId, portletId, parameterMap, type);
	}

	@Override
	public Map<String, Boolean> getExportPortletControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap)
		throws Exception {

		return getExportImportHelper().getExportPortletControlsMap(
			companyId, portletId, parameterMap);
	}

	@Override
	public Map<String, Boolean> getExportPortletControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, String type)
		throws Exception {

		return getExportImportHelper().getExportPortletControlsMap(
			companyId, portletId, parameterMap, type);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getImportPortletControlsMap(long, String, Map, Element,
	 *             ManifestSummary)}
	 */
	@Deprecated
	@Override
	public boolean[] getImportPortletControls(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, Element portletDataElement)
		throws Exception {

		return getExportImportHelper().getImportPortletControls(
			companyId, portletId, parameterMap, portletDataElement);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getImportPortletControlsMap(long, String, Map, Element,
	 *             ManifestSummary)}
	 */
	@Deprecated
	@Override
	public boolean[] getImportPortletControls(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, Element portletDataElement,
			ManifestSummary manifestSummary)
		throws Exception {

		return getExportImportHelper().getImportPortletControls(
			companyId, portletId, parameterMap, portletDataElement,
			manifestSummary);
	}

	@Override
	public Map<String, Boolean> getImportPortletControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, Element portletDataElement,
			ManifestSummary manifestSummary)
		throws Exception {

		return getExportImportHelper().getImportPortletControlsMap(
			companyId, portletId, parameterMap, portletDataElement,
			manifestSummary);
	}

	@Override
	public Map<Long, Boolean> getLayoutIdMap(PortletRequest portletRequest)
		throws PortalException {

		return getExportImportHelper().getLayoutIdMap(portletRequest);
	}

	@Override
	public long[] getLayoutIds(List<Layout> layouts) {
		return getExportImportHelper().getLayoutIds(layouts);
	}

	@Override
	public long[] getLayoutIds(Map<Long, Boolean> layoutIdMap)
		throws PortalException {

		return getExportImportHelper().getLayoutIds(layoutIdMap);
	}

	@Override
	public long[] getLayoutIds(
			Map<Long, Boolean> layoutIdMap, long targetGroupId)
		throws PortalException {

		return getExportImportHelper().getLayoutIds(layoutIdMap, targetGroupId);
	}

	@Override
	public long[] getLayoutIds(PortletRequest portletRequest)
		throws PortalException {

		return getExportImportHelper().getLayoutIds(portletRequest);
	}

	@Override
	public long[] getLayoutIds(
			PortletRequest portletRequest, long targetGroupId)
		throws PortalException {

		return getExportImportHelper().getLayoutIds(
			portletRequest, targetGroupId);
	}

	@Override
	public ZipWriter getLayoutSetZipWriter(long groupId) {
		return getExportImportHelper().getLayoutSetZipWriter(groupId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getManifestSummary(PortletDataContext)}
	 */
	@Deprecated
	@Override
	public ManifestSummary getManifestSummary(
			long userId, long groupId, Map<String, String[]> parameterMap,
			File file)
		throws Exception {

		return getExportImportHelper().getManifestSummary(
			userId, groupId, parameterMap, file);
	}

	@Override
	public ManifestSummary getManifestSummary(
			long userId, long groupId, Map<String, String[]> parameterMap,
			FileEntry fileEntry)
		throws Exception {

		return getExportImportHelper().getManifestSummary(
			userId, groupId, parameterMap, fileEntry);
	}

	@Override
	public ManifestSummary getManifestSummary(
			PortletDataContext portletDataContext)
		throws Exception {

		return getExportImportHelper().getManifestSummary(portletDataContext);
	}

	/**
	 * @see com.liferay.portlet.exportimport.backgroundtask.LayoutRemoteStagingBackgroundTaskExecutor#getMissingRemoteParentLayouts(
	 *      com.liferay.portal.security.auth.HttpPrincipal, Layout, long)
	 */
	@Override
	public List<Layout> getMissingParentLayouts(Layout layout, long liveGroupId)
		throws PortalException {

		return getExportImportHelper().getMissingParentLayouts(
			layout, liveGroupId);
	}

	@Override
	public long getModelDeletionCount(
			final PortletDataContext portletDataContext,
			final StagedModelType stagedModelType)
		throws PortalException {

		return getExportImportHelper().getModelDeletionCount(
			portletDataContext, stagedModelType);
	}

	@Override
	public ZipWriter getPortletZipWriter(String portletId) {
		return getExportImportHelper().getPortletZipWriter(portletId);
	}

	@Override
	public String getSelectedLayoutsJSON(
		long groupId, boolean privateLayout, String selectedNodes) {

		return getExportImportHelper().getSelectedLayoutsJSON(
			groupId, privateLayout, selectedNodes);
	}

	@Override
	public FileEntry getTempFileEntry(
			long groupId, long userId, String folderName)
		throws PortalException {

		return getExportImportHelper().getTempFileEntry(
			groupId, userId, folderName);
	}

	@Override
	public UserIdStrategy getUserIdStrategy(long userId, String userIdStrategy)
		throws PortalException {

		return getExportImportHelper().getUserIdStrategy(
			userId, userIdStrategy);
	}

	@Override
	public boolean isReferenceWithinExportScope(
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		return getExportImportHelper().isReferenceWithinExportScope(
			portletDataContext, stagedModel);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #replaceExportContentReferences(PortletDataContext,
	 *             StagedModel, String, boolean)}
	 */
	@Deprecated
	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		return getExportImportHelper().replaceExportContentReferences(
			portletDataContext, entityStagedModel, entityElement, content,
			exportReferencedContent);
	}

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content,
			boolean exportReferencedContent)
		throws Exception {

		return getExportImportHelper().replaceExportContentReferences(
			portletDataContext, entityStagedModel, content,
			exportReferencedContent);
	}

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content,
			boolean exportReferencedContent, boolean escapeContent)
		throws Exception {

		return getExportImportHelper().replaceExportContentReferences(
			portletDataContext, entityStagedModel, content,
			exportReferencedContent, escapeContent);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #replaceExportDLReferences(PortletDataContext, StagedModel,
	 *             String, boolean)}
	 */
	@Deprecated
	@Override
	public String replaceExportDLReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		return getExportImportHelper().replaceExportDLReferences(
			portletDataContext, entityStagedModel, entityElement, content,
			exportReferencedContent);
	}

	@Override
	public String replaceExportDLReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content,
			boolean exportReferencedContent)
		throws Exception {

		return getExportImportHelper().replaceExportDLReferences(
			portletDataContext, entityStagedModel, content,
			exportReferencedContent);
	}

	@Override
	public String replaceExportLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return getExportImportHelper().replaceExportLayoutReferences(
			portletDataContext, content);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #replaceExportLayoutReferences(PortletDataContext, String)}
	 */
	@Deprecated
	@Override
	public String replaceExportLayoutReferences(
			PortletDataContext portletDataContext, String content,
			boolean exportReferencedContent)
		throws Exception {

		return getExportImportHelper().replaceExportLayoutReferences(
			portletDataContext, content, exportReferencedContent);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #replaceExportLinksToLayouts(PortletDataContext, StagedModel,
	 *             String)}
	 */
	@Deprecated
	@Override
	public String replaceExportLinksToLayouts(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		return getExportImportHelper().replaceExportLinksToLayouts(
			portletDataContext, entityStagedModel, entityElement, content,
			exportReferencedContent);
	}

	@Override
	public String replaceExportLinksToLayouts(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content)
		throws Exception {

		return getExportImportHelper().replaceExportLinksToLayouts(
			portletDataContext, entityStagedModel, content);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #replaceImportContentReferences(PortletDataContext,
	 *             StagedModel, String)}
	 */
	@Deprecated
	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content, boolean importReferencedContent)
		throws Exception {

		return getExportImportHelper().replaceImportContentReferences(
			portletDataContext, entityElement, content,
			importReferencedContent);
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content)
		throws Exception {

		return getExportImportHelper().replaceImportContentReferences(
			portletDataContext, entityStagedModel, content);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #replaceImportDLReferences(PortletDataContext, StagedModel,
	 *             String)}
	 */
	@Deprecated
	@Override
	public String replaceImportDLReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content, boolean importReferencedContent)
		throws Exception {

		return getExportImportHelper().replaceImportDLReferences(
			portletDataContext, entityElement, content,
			importReferencedContent);
	}

	@Override
	public String replaceImportDLReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content)
		throws Exception {

		return getExportImportHelper().replaceImportDLReferences(
			portletDataContext, entityStagedModel, content);
	}

	@Override
	public String replaceImportLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return getExportImportHelper().replaceImportLayoutReferences(
			portletDataContext, content);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #replaceImportLayoutReferences(PortletDataContext, String)}
	 */
	@Deprecated
	@Override
	public String replaceImportLayoutReferences(
			PortletDataContext portletDataContext, String content,
			boolean importReferencedContent)
		throws Exception {

		return getExportImportHelper().replaceImportLayoutReferences(
			portletDataContext, content, importReferencedContent);
	}

	@Override
	public String replaceImportLinksToLayouts(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return getExportImportHelper().replaceImportLinksToLayouts(
			portletDataContext, content);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #replaceImportLinksToLayouts(PortletDataContext, String)}
	 */
	@Deprecated
	@Override
	public String replaceImportLinksToLayouts(
			PortletDataContext portletDataContext, String content,
			boolean importReferencedContent)
		throws Exception {

		return getExportImportHelper().replaceImportLinksToLayouts(
			portletDataContext, content, importReferencedContent);
	}

	/**
	 * @deprecated As of 7.0.0, see {@link
	 *             DefaultConfigurationPortletDataHandler#updateExportPortletPreferencesClassPKs(
	 *             PortletDataContext, Portlet, PortletPreferences, String,
	 *             String)}
	 */
	@Deprecated
	@Override
	public void updateExportPortletPreferencesClassPKs(
			PortletDataContext portletDataContext, Portlet portlet,
			PortletPreferences portletPreferences, String key, String className)
		throws Exception {

		getExportImportHelper().updateExportPortletPreferencesClassPKs(
			portletDataContext, portlet, portletPreferences, key, className);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #updateExportPortletPreferencesClassPKs(PortletDataContext,
	 *             Portlet, PortletPreferences, String, String)}
	 */
	@Deprecated
	@Override
	public void updateExportPortletPreferencesClassPKs(
			PortletDataContext portletDataContext, Portlet portlet,
			PortletPreferences portletPreferences, String key, String className,
			Element rootElement)
		throws Exception {

		getExportImportHelper().updateExportPortletPreferencesClassPKs(
			portletDataContext, portlet, portletPreferences, key, className,
			rootElement);
	}

	/**
	 * @deprecated As of 7.0.0, see {@link
	 *             DefaultConfigurationPortletDataHandler#updateImportPortletPreferencesClassPKs(
	 *             PortletDataContext, PortletPreferences, String, Class, long)}
	 */
	@Deprecated
	@Override
	public void updateImportPortletPreferencesClassPKs(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences, String key, Class<?> clazz,
			long companyGroupId)
		throws Exception {

		getExportImportHelper().updateImportPortletPreferencesClassPKs(
			portletDataContext, portletPreferences, key, clazz, companyGroupId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #validateMissingReferences(PortletDataContext)}
	 */
	@Deprecated
	@Override
	public MissingReferences validateMissingReferences(
			long userId, long groupId, Map<String, String[]> parameterMap,
			File file)
		throws Exception {

		return getExportImportHelper().validateMissingReferences(
			userId, groupId, parameterMap, file);
	}

	@Override
	public MissingReferences validateMissingReferences(
			final PortletDataContext portletDataContext)
		throws Exception {

		return getExportImportHelper().validateMissingReferences(
			portletDataContext);
	}

	@Override
	public void writeManifestSummary(
		Document document, ManifestSummary manifestSummary) {

		getExportImportHelper().writeManifestSummary(document, manifestSummary);
	}

	protected ExportImportHelper getExportImportHelper() {
		if (_serviceTracker.isEmpty()) {
			return _defaultExportImportHelper;
		}

		return _serviceTracker.getService();
	}

	private final ExportImportHelper _defaultExportImportHelper;
	private final ServiceTracker<ExportImportHelper, ExportImportHelper>
		_serviceTracker;

}