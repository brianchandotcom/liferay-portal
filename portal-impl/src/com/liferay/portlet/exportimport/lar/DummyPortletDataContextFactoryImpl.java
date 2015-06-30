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

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.StagedGroupedModel;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.ratings.model.RatingsEntry;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Daniel Kocsis
 */
public class DummyPortletDataContextFactoryImpl
	implements PortletDataContextFactory {

	@Override
	public PortletDataContext clonePortletDataContext(
		PortletDataContext portletDataContext) {

		return portletDataContext;
	}

	@Override
	public PortletDataContext createExportPortletDataContext(
		long companyId, long groupId, Map<String, String[]> parameterMap,
		Date startDate, Date endDate, ZipWriter zipWriter) {

		return _dummyPortletDataContext;
	}

	@Override
	public PortletDataContext createImportPortletDataContext(
		long companyId, long groupId, Map<String, String[]> parameterMap,
		UserIdStrategy userIdStrategy, ZipReader zipReader) {

		return _dummyPortletDataContext;
	}

	@Override
	public PortletDataContext createPreparePortletDataContext(
		long companyId, long groupId, Date startDate, Date endDate) {

		return _dummyPortletDataContext;
	}

	@Override
	public PortletDataContext createPreparePortletDataContext(
		ThemeDisplay themeDisplay, Date startDate, Date endDate) {

		return _dummyPortletDataContext;
	}

	private static final PortletDataContext _dummyPortletDataContext =
		new PortletDataContext() {

			@Deprecated
			@Override
			public void addAssetCategories(Class<?> clazz, long classPK) {
			}

			@Override
			public void addAssetCategories(
				String className, long classPK, long[] assetCategoryIds) {
			}

			@Override
			public void addAssetTags(Class<?> clazz, long classPK) {
			}

			@Override
			public void addAssetTags(
				String className, long classPK, String[] assetTagNames) {
			}

			@Override
			public void addClassedModel(
				Element element, String path, ClassedModel classedModel) {
			}

			@Override
			public void addClassedModel(
				Element element, String path, ClassedModel classedModel,
				Class<?> clazz) {
			}

			@Deprecated
			@Override
			public void addClassedModel(
				Element element, String path, ClassedModel classedModel,
				Class<?> clazz, String namespace) {
			}

			@Deprecated
			@Override
			public void addClassedModel(
				Element element, String path, ClassedModel classedModel,
				String namespace) {
			}

			@Deprecated
			@Override
			public void addComments(Class<?> clazz, long classPK) {
			}

			@Deprecated
			@Override
			public void addComments(
				String className, long classPK, List<MBMessage> messages) {
			}

			@Override
			public void addDateRangeCriteria(
				DynamicQuery dynamicQuery, String propertyName) {
			}

			@Override
			public void addDeletionSystemEventStagedModelTypes(
				StagedModelType... stagedModelTypes) {
			}

			@Override
			public void addExpando(
				Element element, String path, ClassedModel classedModel) {
			}

			@Override
			public void addLocks(Class<?> clazz, String key) {
			}

			@Override
			public void addLocks(String className, String key, Lock lock) {
			}

			@Override
			public void addPermissions(Class<?> clazz, long classPK) {
			}

			@Override
			public void addPermissions(String resourceName, long resourcePK) {
			}

			@Override
			public void addPermissions(
				String resourceName, long resourcePK,
				List<KeyValuePair> permissions) {
			}

			@Override
			public void addPortalPermissions() {
			}

			@Override
			public void addPortletPermissions(String resourceName) {
			}

			@Override
			public boolean addPrimaryKey(Class<?> clazz, String primaryKey) {
				return false;
			}

			@Deprecated
			@Override
			public void addRatingsEntries(Class<?> clazz, long classPK) {
			}

			@Deprecated
			@Override
			public void addRatingsEntries(
				String className, long classPK,
				List<RatingsEntry> ratingsEntries) {
			}

			@Deprecated
			@Override
			public Element addReferenceElement(
				ClassedModel referrerClassedModel, Element element,
				ClassedModel classedModel, Class<?> clazz, String referenceType,
				boolean missing) {

				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public Element addReferenceElement(
				ClassedModel referrerClassedModel, Element element,
				ClassedModel classedModel, String referenceType,
				boolean missing) {

				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public Element addReferenceElement(
				ClassedModel referrerClassedModel, Element element,
				ClassedModel classedModel, String binPath, String referenceType,
				boolean missing) {

				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public Element addReferenceElement(
				ClassedModel referrerClassedModel, Element element,
				ClassedModel classedModel, String className, String binPath,
				String referenceType, boolean missing) {

				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public boolean addScopedPrimaryKey(
				Class<?> clazz, String primaryKey) {

				return false;
			}

			@Override
			public void addZipEntry(String path, byte[] bytes) {
			}

			@Override
			public void addZipEntry(String path, InputStream is) {
			}

			@Override
			public void addZipEntry(String path, Object object) {
			}

			@Override
			public void addZipEntry(String path, String s) {
			}

			@Override
			public void addZipEntry(String name, StringBuilder sb) {
			}

			@Override
			public void cleanUpMissingReferences(ClassedModel classedModel) {
			}

			@Override
			public void clearScopedPrimaryKeys() {
			}

			@Override
			public ServiceContext createServiceContext(
				Element element, ClassedModel classedModel) {

				return new ServiceContext();
			}

			@Deprecated
			@Override
			public ServiceContext createServiceContext(
				Element element, ClassedModel classedModel, String namespace) {

				return new ServiceContext();
			}

			@Override
			public ServiceContext createServiceContext(
				StagedModel stagedModel) {

				return new ServiceContext();
			}

			@Override
			public ServiceContext createServiceContext(
				StagedModel stagedModel, Class<?> clazz) {

				return new ServiceContext();
			}

			@Override
			public ServiceContext createServiceContext(
				String path, ClassedModel classedModel) {

				return new ServiceContext();
			}

			@Deprecated
			@Override
			public ServiceContext createServiceContext(
				String path, ClassedModel classedModel, String namespace) {

				return new ServiceContext();
			}

			@Override
			public Object fromXML(byte[] bytes) {
				return new Object();
			}

			@Override
			public Object fromXML(String xml) {
				return new Object();
			}

			@Override
			public long[] getAssetCategoryIds(Class<?> clazz, long classPK) {
				return new long[0];
			}

			@Deprecated
			@Override
			public Map<String, long[]> getAssetCategoryIdsMap() {
				return Collections.<String, long[]>emptyMap();
			}

			@Deprecated
			@Override
			public Map<String, String[]> getAssetCategoryUuidsMap() {
				return Collections.<String, String[]>emptyMap();
			}

			@Override
			public Map<String, List<AssetLink>> getAssetLinksMap() {
				return Collections.<String, List<AssetLink>>emptyMap();
			}

			@Override
			public String[] getAssetTagNames(Class<?> clazz, long classPK) {
				return new String[0];
			}

			@Override
			public String[] getAssetTagNames(String className, long classPK) {
				return new String[0];
			}

			@Override
			public Map<String, String[]> getAssetTagNamesMap() {
				return Collections.<String, String[]>emptyMap();
			}

			@Override
			public boolean getBooleanParameter(String namespace, String name) {
				return false;
			}

			@Override
			public boolean getBooleanParameter(
				String namespace, String name, boolean useDefaultValue) {

				return false;
			}

			@Override
			public ClassLoader getClassLoader() {
				return getClass().getClassLoader();
			}

			@Deprecated
			@Override
			public Map<String, List<MBMessage>> getComments() {
				return Collections.<String, List<MBMessage>>emptyMap();
			}

			@Override
			public long getCompanyGroupId() {
				return 0;
			}

			@Override
			public long getCompanyId() {
				return 0;
			}

			@Override
			public String getDataStrategy() {
				return StringPool.BLANK;
			}

			@Override
			public DateRange getDateRange() {
				return new DateRange(new Date(), new Date());
			}

			@Override
			public Criterion getDateRangeCriteria(String propertyName) {
				return null;
			}

			@Override
			public Set<StagedModelType>
				getDeletionSystemEventStagedModelTypes() {

				return Collections.<StagedModelType>emptySet();
			}

			@Override
			public Date getEndDate() {
				return new Date();
			}

			@Override
			public Map<String, List<ExpandoColumn>> getExpandoColumns() {
				return Collections.<String, List<ExpandoColumn>>emptyMap();
			}

			@Override
			public Element getExportDataElement(ClassedModel classedModel) {
				return SAXReaderUtil.createElement("foo");
			}

			@Deprecated
			@Override
			public Element getExportDataElement(
				ClassedModel classedModel, Class<?> modelClass) {

				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public Element getExportDataElement(
				ClassedModel classedModel, String modelClassSimpleName) {

				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public Element getExportDataGroupElement(
				Class<? extends StagedModel> clazz) {

				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public Element getExportDataRootElement() {
				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public long getGroupId() {
				return 0;
			}

			@Override
			public Element getImportDataElement(StagedModel stagedModel) {
				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public Element getImportDataElement(
				String name, String attribute, String value) {

				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public Element getImportDataGroupElement(
				Class<? extends StagedModel> clazz) {

				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public Element getImportDataRootElement() {
				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public Element getImportDataStagedModelElement(
				StagedModel stagedModel) {

				return SAXReaderUtil.createElement("foo");
			}

			@Deprecated
			@Override
			public String getLayoutPath(long plid) {
				return StringPool.BLANK;
			}

			@Override
			public Map<String, Lock> getLocks() {
				return Collections.<String, Lock>emptyMap();
			}

			@Override
			public ManifestSummary getManifestSummary() {
				return new ManifestSummary();
			}

			@Override
			public Element getMissingReferencesElement() {
				return SAXReaderUtil.createElement("foo");
			}

			@Deprecated
			@Override
			public List<Layout> getNewLayouts() {
				return Collections.<Layout>emptyList();
			}

			@Override
			public Map<?, ?> getNewPrimaryKeysMap(Class<?> clazz) {
				return Collections.emptyMap();
			}

			@Override
			public Map<?, ?> getNewPrimaryKeysMap(String className) {
				return Collections.emptyMap();
			}

			@Override
			public Map<String, Map<?, ?>> getNewPrimaryKeysMaps() {
				return Collections.<String, Map<?, ?>>emptyMap();
			}

			@Deprecated
			@Override
			public long getOldPlid() {
				return 0;
			}

			@Override
			public Map<String, String[]> getParameterMap() {
				return Collections.<String, String[]>emptyMap();
			}

			@Override
			public Map<String, List<KeyValuePair>> getPermissions() {
				return Collections.<String, List<KeyValuePair>>emptyMap();
			}

			@Override
			public long getPlid() {
				return 0;
			}

			@Override
			public String getPortletId() {
				return StringPool.BLANK;
			}

			@Deprecated
			@Override
			public String getPortletPath(String portletId) {
				return StringPool.BLANK;
			}

			@Override
			public Set<String> getPrimaryKeys() {
				return Collections.<String>emptySet();
			}

			@Deprecated
			@Override
			public Map<String, List<RatingsEntry>> getRatingsEntries() {
				return Collections.<String, List<RatingsEntry>>emptyMap();
			}

			@Override
			public Element getReferenceDataElement(
				Element parentElement, Class<?> clazz, long classPK) {

				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public Element getReferenceDataElement(
				Element parentElement, Class<?> clazz, long groupId,
				String uuid) {

				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public Element getReferenceDataElement(
				StagedModel parentStagedModel, Class<?> clazz, long classPK) {

				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public Element getReferenceDataElement(
				StagedModel parentStagedModel, Class<?> clazz, long groupId,
				String uuid) {

				return SAXReaderUtil.createElement("foo");
			}

			@Deprecated
			@Override
			public List<Element> getReferenceDataElements(
				Element parentElement, Class<?> clazz) {

				return Collections.<Element>emptyList();
			}

			@Override
			public List<Element> getReferenceDataElements(
				Element parentElement, Class<?> clazz, String referenceType) {

				return Collections.<Element>emptyList();
			}

			@Override
			public List<Element> getReferenceDataElements(
				StagedModel parentStagedModel, Class<?> clazz) {

				return Collections.<Element>emptyList();
			}

			@Override
			public List<Element> getReferenceDataElements(
				StagedModel parentStagedModel, Class<?> clazz,
				String referenceType) {

				return Collections.<Element>emptyList();
			}

			@Override
			public Element getReferenceElement(
				Element parentElement, Class<?> clazz, long groupId,
				String uuid, String referenceType) {

				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public Element getReferenceElement(
				StagedModel parentStagedModel, Class<?> clazz, long classPK) {

				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public Element getReferenceElement(
				StagedModel parentStagedModel, String className, long classPK) {

				return SAXReaderUtil.createElement("foo");
			}

			@Override
			public List<Element> getReferenceElements(
				StagedModel parentStagedModel, Class<?> clazz) {

				return Collections.<Element>emptyList();
			}

			@Deprecated
			@Override
			public String getRootPath() {
				return StringPool.BLANK;
			}

			@Override
			public String getRootPortletId() {
				return StringPool.BLANK;
			}

			@Deprecated
			@Override
			public Set<String> getScopedPrimaryKeys() {
				return Collections.<String>emptySet();
			}

			@Override
			public long getScopeGroupId() {
				return 0;
			}

			@Override
			public String getScopeLayoutUuid() {
				return StringPool.BLANK;
			}

			@Override
			public String getScopeType() {
				return StringPool.BLANK;
			}

			@Override
			public long getSourceCompanyGroupId() {
				return 0;
			}

			@Override
			public long getSourceCompanyId() {
				return 0;
			}

			@Override
			public long getSourceGroupId() {
				return 0;
			}

			@Deprecated
			@Override
			public String getSourceLayoutPath(long layoutId) {
				return StringPool.BLANK;
			}

			@Deprecated
			@Override
			public String getSourcePortletPath(String portletId) {
				return StringPool.BLANK;
			}

			@Deprecated
			@Override
			public String getSourceRootPath() {
				return StringPool.BLANK;
			}

			@Override
			public long getSourceUserPersonalSiteGroupId() {
				return 0;
			}

			@Override
			public Date getStartDate() {
				return new Date();
			}

			@Override
			public long getUserId(String userUuid) {
				return 0;
			}

			@Override
			public UserIdStrategy getUserIdStrategy() {
				return null;
			}

			@Override
			public long getUserPersonalSiteGroupId() {
				return 0;
			}

			@Deprecated
			@Override
			public List<String> getZipEntries() {
				return Collections.<String>emptyList();
			}

			@Override
			public byte[] getZipEntryAsByteArray(String path) {
				return new byte[0];
			}

			@Override
			public InputStream getZipEntryAsInputStream(String path) {
				return new ByteArrayInputStream(new byte[0]);
			}

			@Override
			public Object getZipEntryAsObject(Element element, String path) {
				return new Object();
			}

			@Override
			public Object getZipEntryAsObject(String path) {
				return new Object();
			}

			@Override
			public String getZipEntryAsString(String path) {
				return StringPool.BLANK;
			}

			@Deprecated
			@Override
			public List<String> getZipFolderEntries() {
				return Collections.<String>emptyList();
			}

			@Override
			public List<String> getZipFolderEntries(String path) {
				return Collections.<String>emptyList();
			}

			@Override
			public ZipReader getZipReader() {
				return ZipReaderFactoryUtil.getZipReader(
					FileUtil.createTempFile());
			}

			@Override
			public ZipWriter getZipWriter() {
				return ZipWriterFactoryUtil.getZipWriter();
			}

			@Override
			public boolean hasDateRange() {
				return false;
			}

			@Override
			public boolean hasNotUniquePerLayout(String dataKey) {
				return false;
			}

			@Override
			public boolean hasPrimaryKey(Class<?> clazz, String primaryKey) {
				return false;
			}

			@Override
			public boolean hasScopedPrimaryKey(
				Class<?> clazz, String primaryKey) {

				return false;
			}

			@Override
			public void importClassedModel(
				ClassedModel classedModel, ClassedModel newClassedModel) {
			}

			@Override
			public void importClassedModel(
				ClassedModel classedModel, ClassedModel newClassedModel,
				Class<?> clazz) {
			}

			@Deprecated
			@Override
			public void importClassedModel(
				ClassedModel classedModel, ClassedModel newClassedModel,
				Class<?> clazz, String namespace) {
			}

			@Deprecated
			@Override
			public void importClassedModel(
				ClassedModel classedModel, ClassedModel newClassedModel,
				String namespace) {
			}

			@Deprecated
			@Override
			public void importComments(
				Class<?> clazz, long classPK, long newClassPK, long groupId) {
			}

			@Override
			public void importLocks(Class<?> clazz, String key, String newKey) {
			}

			@Override
			public void importPermissions(
				Class<?> clazz, long classPK, long newClassPK) {
			}

			@Override
			public void importPermissions(
				String resourceName, long resourcePK, long newResourcePK) {
			}

			@Override
			public void importPortalPermissions() {
			}

			@Override
			public void importPortletPermissions(String resourceName) {
			}

			@Deprecated
			@Override
			public void importRatingsEntries(
				Class<?> clazz, long classPK, long newClassPK) {
			}

			@Override
			public boolean isCompanyStagedGroupedModel(
				StagedGroupedModel stagedGroupedModel) {

				return false;
			}

			@Override
			public boolean isDataStrategyMirror() {
				return false;
			}

			@Override
			public boolean isDataStrategyMirrorWithOverwriting() {
				return false;
			}

			@Override
			public boolean isInitialPublication() {
				return false;
			}

			@Override
			public boolean isModelCounted(String className, long classPK) {
				return false;
			}

			@Override
			public boolean isPathExportedInScope(String path) {
				return false;
			}

			@Deprecated
			@Override
			public boolean isPathNotExportedInScope(String path) {
				return false;
			}

			@Override
			public boolean isPathNotProcessed(String path) {
				return false;
			}

			@Override
			public boolean isPathProcessed(String path) {
				return false;
			}

			@Override
			public boolean isPerformDirectBinaryImport() {
				return false;
			}

			@Override
			public boolean isPrivateLayout() {
				return false;
			}

			@Override
			public boolean isStagedModelCounted(StagedModel stagedModel) {
				return false;
			}

			@Override
			public boolean isWithinDateRange(Date modifiedDate) {
				return false;
			}

			@Override
			public void putNotUniquePerLayout(String dataKey) {
			}

			@Override
			public void setClassLoader(ClassLoader classLoader) {
			}

			@Override
			public void setCompanyGroupId(long companyGroupId) {
			}

			@Override
			public void setCompanyId(long companyId) {
			}

			@Override
			public void setDataStrategy(String dataStrategy) {
			}

			@Override
			public void setEndDate(Date endDate) {
			}

			@Override
			public void setExportDataRootElement(
				Element exportDataRootElement) {
			}

			@Override
			public void setGroupId(long groupId) {
			}

			@Override
			public void setImportDataRootElement(
				Element importDataRootElement) {
			}

			@Override
			public void setManifestSummary(ManifestSummary manifestSummary) {
			}

			@Override
			public void setMissingReferencesElement(
				Element missingReferencesElement) {
			}

			@Override
			public void setNewLayouts(List<Layout> newLayouts) {
			}

			@Override
			public void setOldPlid(long oldPlid) {
			}

			@Override
			public void setParameterMap(Map<String, String[]> parameterMap) {
			}

			@Override
			public void setPlid(long plid) {
			}

			@Deprecated
			@Override
			public void setPortetDataContextListener(
				PortletDataContextListener portletDataContextListener) {
			}

			@Override
			public void setPortletId(String portletId) {
			}

			@Override
			public void setPrivateLayout(boolean privateLayout) {
			}

			@Override
			public void setScopeGroupId(long scopeGroupId) {
			}

			@Override
			public void setScopeLayoutUuid(String scopeLayoutUuid) {
			}

			@Override
			public void setScopeType(String scopeType) {
			}

			@Override
			public void setSourceCompanyGroupId(long sourceCompanyGroupId) {
			}

			@Override
			public void setSourceCompanyId(long sourceCompanyId) {
			}

			@Override
			public void setSourceGroupId(long sourceGroupId) {
			}

			@Override
			public void setSourceUserPersonalSiteGroupId(
				long sourceUserPersonalSiteGroupId) {
			}

			@Override
			public void setStartDate(Date startDate) {
			}

			@Override
			public void setUserIdStrategy(UserIdStrategy userIdStrategy) {
			}

			@Override
			public void setUserPersonalSiteGroupId(
				long userPersonalSiteGroupId) {
			}

			@Override
			public void setZipReader(ZipReader zipReader) {
			}

			@Override
			public void setZipWriter(ZipWriter zipWriter) {
			}

			@Override
			public String toXML(Object object) {
				return null;
			}

		};

}