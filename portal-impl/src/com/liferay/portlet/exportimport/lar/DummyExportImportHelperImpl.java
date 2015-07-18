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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.File;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Daniel Kocsis
 */
public class DummyExportImportHelperImpl implements ExportImportHelper {

	@Override
	public long[] getAllLayoutIds(long groupId, boolean privateLayout) {
		return new long[0];
	}

	@Override
	public Map<Long, Boolean> getAllLayoutIdsMap(
		long groupId, boolean privateLayout) {

		return Collections.<Long, Boolean>emptyMap();
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

		return new GregorianCalendar();
	}

	@Override
	public List<Portlet> getDataSiteLevelPortlets(long companyId)
		throws Exception {

		return Collections.<Portlet>emptyList();
	}

	@Override
	public List<Portlet> getDataSiteLevelPortlets(
			long companyId, boolean excludeDataAlwaysStaged)
		throws Exception {

		return Collections.<Portlet>emptyList();
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

		return new DateRange(new Date(), new Date());
	}

	@Override
	public Layout getExportableLayout(ThemeDisplay themeDisplay) {
		return new LayoutImpl();
	}

	@Override
	public String getExportableRootPortletId(long companyId, String portletId)
		throws Exception {

		return StringPool.BLANK;
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

		return new boolean[0];
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

		return new boolean[0];
	}

	@Override
	public Map<String, Boolean> getExportPortletControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap)
		throws Exception {

		return Collections.<String, Boolean>emptyMap();
	}

	@Override
	public Map<String, Boolean> getExportPortletControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, String type)
		throws Exception {

		return Collections.<String, Boolean>emptyMap();
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

		return new boolean[0];
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

		return new boolean[0];
	}

	@Override
	public Map<String, Boolean> getImportPortletControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, Element portletDataElement,
			ManifestSummary manifestSummary)
		throws Exception {

		return Collections.<String, Boolean>emptyMap();
	}

	@Override
	public Map<Long, Boolean> getLayoutIdMap(PortletRequest portletRequest) {
		return Collections.<Long, Boolean>emptyMap();
	}

	@Override
	public long[] getLayoutIds(List<Layout> layouts) {
		return new long[0];
	}

	@Override
	public long[] getLayoutIds(Map<Long, Boolean> layoutIdMap) {
		return new long[0];
	}

	@Override
	public long[] getLayoutIds(
		Map<Long, Boolean> layoutIdMap, long targetGroupId) {

		return new long[0];
	}

	@Override
	public long[] getLayoutIds(PortletRequest portletRequest) {
		return new long[0];
	}

	@Override
	public long[] getLayoutIds(
		PortletRequest portletRequest, long targetGroupId) {

		return new long[0];
	}

	@Override
	public ZipWriter getLayoutSetZipWriter(long groupId) {
		return ZipWriterFactoryUtil.getZipWriter();
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

		return new ManifestSummary();
	}

	@Override
	public ManifestSummary getManifestSummary(
			long userId, long groupId, Map<String, String[]> parameterMap,
			FileEntry fileEntry)
		throws Exception {

		return new ManifestSummary();
	}

	@Override
	public ManifestSummary getManifestSummary(
			PortletDataContext portletDataContext)
		throws Exception {

		return new ManifestSummary();
	}

	@Override
	public List<Layout> getMissingParentLayouts(
		Layout layout, long liveGroupId) {

		return Collections.<Layout>emptyList();
	}

	@Override
	public long getModelDeletionCount(
		PortletDataContext portletDataContext,
		StagedModelType stagedModelType) {

		return 0L;
	}

	@Override
	public ZipWriter getPortletZipWriter(String portletId) {
		return ZipWriterFactoryUtil.getZipWriter();
	}

	@Override
	public String getSelectedLayoutsJSON(
		long groupId, boolean privateLayout, String selectedNodes) {

		return StringPool.BLANK;
	}

	@Override
	public FileEntry getTempFileEntry(
		long groupId, long userId, String folderName) {

		return null;
	}

	@Override
	public UserIdStrategy getUserIdStrategy(
		long userId, String userIdStrategy) {

		return null;
	}

	@Override
	public boolean isReferenceWithinExportScope(
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		return false;
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

		return StringPool.BLANK;
	}

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content,
			boolean exportReferencedContent)
		throws Exception {

		return StringPool.BLANK;
	}

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content,
			boolean exportReferencedContent, boolean escapeContent)
		throws Exception {

		return StringPool.BLANK;
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

		return StringPool.BLANK;
	}

	@Override
	public String replaceExportDLReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content,
			boolean exportReferencedContent)
		throws Exception {

		return StringPool.BLANK;
	}

	@Override
	public String replaceExportLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return StringPool.BLANK;
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

		return StringPool.BLANK;
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

		return StringPool.BLANK;
	}

	@Override
	public String replaceExportLinksToLayouts(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content)
		throws Exception {

		return StringPool.BLANK;
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

		return StringPool.BLANK;
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content)
		throws Exception {

		return StringPool.BLANK;
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

		return StringPool.BLANK;
	}

	@Override
	public String replaceImportDLReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content)
		throws Exception {

		return StringPool.BLANK;
	}

	@Override
	public String replaceImportLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return StringPool.BLANK;
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

		return StringPool.BLANK;
	}

	@Override
	public String replaceImportLinksToLayouts(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return StringPool.BLANK;
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

		return StringPool.BLANK;
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

		return new MissingReferences();
	}

	@Override
	public MissingReferences validateMissingReferences(
			PortletDataContext portletDataContext)
		throws Exception {

		return new MissingReferences();
	}

	@Override
	public void writeManifestSummary(
		Document document, ManifestSummary manifestSummary) {
	}

}