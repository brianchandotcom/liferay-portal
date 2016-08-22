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

package com.liferay.exportimport.kernel.lar;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.ratings.kernel.model.RatingsEntry;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Holds context information that is used during exporting and importing portlet
 * data.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Mate Thurzo
 */
@ProviderType
public interface PortletDataContext extends Serializable {

	public static final String REFERENCE_TYPE_CHILD = "child";

	public static final String REFERENCE_TYPE_DEPENDENCY = "dependency";

	public static final String REFERENCE_TYPE_DEPENDENCY_DISPOSABLE =
		"disposable_dependency";

	public static final String REFERENCE_TYPE_EMBEDDED = "embedded";

	public static final String REFERENCE_TYPE_PARENT = "parent";

	public static final String REFERENCE_TYPE_STRONG = "strong";

	public static final String REFERENCE_TYPE_WEAK = "weak";

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             BaseStagedModelDataHandler#exportAssetCategories(
	 *             PortletDataContext, StagedModel)}
	 */
	@Deprecated
	public void addAssetCategories(Class<?> clazz, long classPK);

	public void addAssetCategories(
		String className, long classPK, long[] assetCategoryIds);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             BaseStagedModelDataHandler#exportAssetTags(
	 *             PortletDataContext, StagedModel)}
	 */
	@Deprecated
	public void addAssetTags(Class<?> clazz, long classPK);

	public void addAssetTags(
		String className, long classPK, String[] assetTagNames);

	public void addClassedModel(
			Element element, String path, ClassedModel classedModel)
		throws PortalException;

	public void addClassedModel(
			Element element, String path, ClassedModel classedModel,
			Class<?> clazz)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             BaseStagedModelDataHandler#exportComments(PortletDataContext,
	 *             StagedModel)}
	 */
	@Deprecated
	public void addComments(Class<?> clazz, long classPK);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             BaseStagedModelDataHandler#exportComments(PortletDataContext,
	 *             StagedModel)}
	 */
	@Deprecated
	public void addComments(
		String className, long classPK, List<MBMessage> messages);

	public void addDateRangeCriteria(
		DynamicQuery dynamicQuery, String propertyName);

	public void addDeletionSystemEventStagedModelTypes(
		StagedModelType... stagedModelTypes);

	public void addExpando(
			Element element, String path, ClassedModel classedModel)
		throws PortalException;

	public void addLocks(Class<?> clazz, String key) throws PortalException;

	public void addLocks(String className, String key, Lock lock);

	public void addPermissions(Class<?> clazz, long classPK)
		throws PortalException;

	public void addPermissions(String resourceName, long resourcePK)
		throws PortalException;

	public void addPermissions(
		String resourceName, long resourcePK, List<KeyValuePair> permissions);

	public void addPortalPermissions() throws PortalException;

	public void addPortletPermissions(String resourceName)
		throws PortalException;

	public boolean addPrimaryKey(Class<?> clazz, String primaryKey);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             BaseStagedModelDataHandler#exportRatings(PortletDataContext,
	 *             StagedModel)}
	 */
	@Deprecated
	public void addRatingsEntries(Class<?> clazz, long classPK);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             BaseStagedModelDataHandler#exportRatings(PortletDataContext,
	 *             StagedModel)}
	 */
	@Deprecated
	public void addRatingsEntries(
		String className, long classPK, List<RatingsEntry> ratingsEntries);

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public Element addReferenceElement(
		ClassedModel referrerClassedModel, Element element,
		ClassedModel classedModel, Class<?> clazz, String referenceType,
		boolean missing);

	public Element addReferenceElement(
		ClassedModel referrerClassedModel, Element element,
		ClassedModel classedModel, String referenceType, boolean missing);

	public Element addReferenceElement(
		ClassedModel referrerClassedModel, Element element,
		ClassedModel classedModel, String binPath, String referenceType,
		boolean missing);

	public Element addReferenceElement(
		ClassedModel referrerClassedModel, Element element,
		ClassedModel classedModel, String className, String binPath,
		String referenceType, boolean missing);

	public boolean addScopedPrimaryKey(Class<?> clazz, String primaryKey);

	public void addZipEntry(String path, byte[] bytes);

	public void addZipEntry(String path, InputStream is);

	public void addZipEntry(String path, Object object);

	public void addZipEntry(String path, String s);

	public void addZipEntry(String name, StringBuilder sb);

	public void cleanUpMissingReferences(ClassedModel classedModel);

	public void clearScopedPrimaryKeys();

	public ServiceContext createServiceContext(
		Element element, ClassedModel classedModel);

	public ServiceContext createServiceContext(StagedModel stagedModel);

	public ServiceContext createServiceContext(
		StagedModel stagedModel, Class<?> clazz);

	public ServiceContext createServiceContext(
		String path, ClassedModel classedModel);

	public Object fromXML(byte[] bytes);

	public Object fromXML(String xml);

	public long[] getAssetCategoryIds(Class<?> clazz, long classPK);

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public Map<String, long[]> getAssetCategoryIdsMap();

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public Map<String, String[]> getAssetCategoryUuidsMap();

	public Set<Long> getAssetLinkIds();

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getAssetLinkIds()}
	 */
	@Deprecated
	public Map<String, List<AssetLink>> getAssetLinksMap();

	public String[] getAssetTagNames(Class<?> clazz, long classPK);

	public String[] getAssetTagNames(String className, long classPK);

	public Map<String, String[]> getAssetTagNamesMap();

	public boolean getBooleanParameter(String namespace, String name);

	public boolean getBooleanParameter(
		String namespace, String name, boolean useDefaultValue);

	public ClassLoader getClassLoader();

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public Map<String, List<MBMessage>> getComments();

	public long getCompanyGroupId();

	public long getCompanyId();

	public String getDataStrategy();

	public DateRange getDateRange();

	public Criterion getDateRangeCriteria(String propertyName);

	public Set<StagedModelType> getDeletionSystemEventStagedModelTypes();

	public Date getEndDate();

	public Map<String, List<ExpandoColumn>> getExpandoColumns();

	public Element getExportDataElement(ClassedModel classedModel);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getExportDataElement(ClassedModel, String)}
	 */
	@Deprecated
	public Element getExportDataElement(
		ClassedModel classedModel, Class<?> modelClass);

	public Element getExportDataElement(
		ClassedModel classedModel, String modelClassSimpleName);

	public Element getExportDataGroupElement(
		Class<? extends StagedModel> clazz);

	public Element getExportDataRootElement();

	public long getGroupId();

	public Element getImportDataElement(StagedModel stagedModel);

	public Element getImportDataElement(
		String name, String attribute, String value);

	public Element getImportDataGroupElement(
		Class<? extends StagedModel> clazz);

	public Element getImportDataRootElement();

	public Element getImportDataStagedModelElement(StagedModel stagedModel);

	public long[] getLayoutIds();

	public Map<String, Lock> getLocks();

	public ManifestSummary getManifestSummary();

	public Element getMissingReferencesElement();

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getNewPrimaryKeysMap(String)}
	 */
	@Deprecated
	public List<Layout> getNewLayouts();

	public Map<?, ?> getNewPrimaryKeysMap(Class<?> clazz);

	public Map<?, ?> getNewPrimaryKeysMap(String className);

	public Map<String, Map<?, ?>> getNewPrimaryKeysMaps();

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public long getOldPlid();

	public Map<String, String[]> getParameterMap();

	public Map<String, List<KeyValuePair>> getPermissions();

	public long getPlid();

	public String getPortletId();

	public Set<String> getPrimaryKeys();

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public Map<String, List<RatingsEntry>> getRatingsEntries();

	public Element getReferenceDataElement(
		Element parentElement, Class<?> clazz, long classPK);

	public Element getReferenceDataElement(
		Element parentElement, Class<?> clazz, long groupId, String uuid);

	public Element getReferenceDataElement(
		StagedModel parentStagedModel, Class<?> clazz, long classPK);

	public Element getReferenceDataElement(
		StagedModel parentStagedModel, Class<?> clazz, long groupId,
		String uuid);

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public List<Element> getReferenceDataElements(
		Element parentElement, Class<?> clazz);

	public List<Element> getReferenceDataElements(
		Element parentElement, Class<?> clazz, String referenceType);

	public List<Element> getReferenceDataElements(
		StagedModel parentStagedModel, Class<?> clazz);

	public List<Element> getReferenceDataElements(
		StagedModel parentStagedModel, Class<?> clazz, String referenceType);

	public Element getReferenceElement(Class<?> clazz, long classPK);

	public Element getReferenceElement(
		Element parentElement, Class<?> clazz, long groupId, String uuid,
		String referenceType);

	public Element getReferenceElement(
		StagedModel parentStagedModel, Class<?> clazz, long classPK);

	public Element getReferenceElement(
		StagedModel parentStagedModel, String className, long classPK);

	public Element getReferenceElement(String className, long classPK);

	public List<Element> getReferenceElements(
		StagedModel parentStagedModel, Class<?> clazz);

	public String getRootPortletId();

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public Set<String> getScopedPrimaryKeys();

	public long getScopeGroupId();

	public String getScopeLayoutUuid();

	public String getScopeType();

	public long getSourceCompanyGroupId();

	public long getSourceCompanyId();

	public long getSourceGroupId();

	public long getSourceUserPersonalSiteGroupId();

	public Date getStartDate();

	public String getType();

	public long getUserId(String userUuid);

	public UserIdStrategy getUserIdStrategy();

	public long getUserPersonalSiteGroupId();

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public List<String> getZipEntries();

	public byte[] getZipEntryAsByteArray(String path);

	public InputStream getZipEntryAsInputStream(String path);

	public Object getZipEntryAsObject(Element element, String path);

	public Object getZipEntryAsObject(String path);

	public String getZipEntryAsString(String path);

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public List<String> getZipFolderEntries();

	public List<String> getZipFolderEntries(String path);

	public ZipReader getZipReader();

	public ZipWriter getZipWriter();

	public boolean hasDateRange();

	public boolean hasNotUniquePerLayout(String dataKey);

	public boolean hasPrimaryKey(Class<?> clazz, String primaryKey);

	public boolean hasScopedPrimaryKey(Class<?> clazz, String primaryKey);

	public void importClassedModel(
			ClassedModel classedModel, ClassedModel newClassedModel)
		throws PortalException;

	public void importClassedModel(
			ClassedModel classedModel, ClassedModel newClassedModel,
			Class<?> clazz)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             BaseStagedModelDataHandler#importComments(PortletDataContext,
	 *             StagedModel)}
	 */
	@Deprecated
	public void importComments(
			Class<?> clazz, long classPK, long newClassPK, long groupId)
		throws PortalException;

	public void importLocks(Class<?> clazz, String key, String newKey)
		throws PortalException;

	public void importPermissions(Class<?> clazz, long classPK, long newClassPK)
		throws PortalException;

	public void importPermissions(
			String resourceName, long resourcePK, long newResourcePK)
		throws PortalException;

	public void importPortalPermissions() throws PortalException;

	public void importPortletPermissions(String resourceName)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             BaseStagedModelDataHandler#importRatings(PortletDataContext,
	 *             StagedModel)}
	 */
	@Deprecated
	public void importRatingsEntries(
			Class<?> clazz, long classPK, long newClassPK)
		throws PortalException;

	public boolean isCompanyStagedGroupedModel(
		StagedGroupedModel stagedGroupedModel);

	public boolean isDataStrategyMirror();

	public boolean isDataStrategyMirrorWithOverwriting();

	public boolean isInitialPublication();

	public boolean isMissingReference(Element referenceElement);

	public boolean isModelCounted(String className, long classPK);

	public boolean isPathExportedInScope(String path);

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public boolean isPathNotExportedInScope(String path);

	public boolean isPathNotProcessed(String path);

	public boolean isPathProcessed(String path);

	public boolean isPerformDirectBinaryImport();

	public boolean isPrivateLayout();

	public boolean isStagedModelCounted(StagedModel stagedModel);

	public boolean isWithinDateRange(Date modifiedDate);

	public void putNotUniquePerLayout(String dataKey);

	public void setClassLoader(ClassLoader classLoader);

	public void setCompanyGroupId(long companyGroupId);

	public void setCompanyId(long companyId);

	public void setDataStrategy(String dataStrategy);

	public void setEndDate(Date endDate);

	public void setExportDataRootElement(Element exportDataRootElement);

	public void setGroupId(long groupId);

	public void setImportDataRootElement(Element importDataRootElement);

	public void setLayoutIds(long[] layoutIds);

	public void setManifestSummary(ManifestSummary manifestSummary);

	public void setMissingReferencesElement(Element missingReferencesElement);

	public void setNewLayouts(List<Layout> newLayouts);

	public void setOldPlid(long oldPlid);

	public void setParameterMap(Map<String, String[]> parameterMap);

	public void setPlid(long plid);

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setPortetDataContextListener(
		PortletDataContextListener portletDataContextListener);

	public void setPortletId(String portletId);

	public void setPrivateLayout(boolean privateLayout);

	public void setScopeGroupId(long scopeGroupId);

	public void setScopeLayoutUuid(String scopeLayoutUuid);

	public void setScopeType(String scopeType);

	public void setSourceCompanyGroupId(long sourceCompanyGroupId);

	public void setSourceCompanyId(long sourceCompanyId);

	public void setSourceGroupId(long sourceGroupId);

	public void setSourceUserPersonalSiteGroupId(
		long sourceUserPersonalSiteGroupId);

	public void setStartDate(Date startDate);

	public void setType(String type);

	public void setUserIdStrategy(UserIdStrategy userIdStrategy);

	public void setUserPersonalSiteGroupId(long userPersonalSiteGroupId);

	public void setZipReader(ZipReader zipReader);

	public void setZipWriter(ZipWriter zipWriter);

	public String toXML(Object object);

}