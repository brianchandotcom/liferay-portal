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

package com.liferay.portal.lar;

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.NoSuchTeamException;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextListener;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.lar.xstream.XStreamAliasRegistryUtil;
import com.liferay.portal.kernel.lar.xstream.XStreamConverter;
import com.liferay.portal.kernel.lar.xstream.XStreamConverterRegistryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.lar.xstream.ConverterAdapter;
import com.liferay.portal.model.AttachedModel;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletModel;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcedModel;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.StagedGroupedModel;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.Team;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockPermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetLinkLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.ratings.model.RatingsEntry;

import com.thoughtworks.xstream.XStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jodd.bean.BeanUtil;

/**
 * <p>
 * Holds context information that is used during exporting and importing portlet
 * data.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Bruno Farache
 * @author Alexander Chow
 * @author Mate Thurzo
 */
public class PortletDataContextImpl implements PortletDataContext {

	public PortletDataContextImpl() {
		initXStream();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portal.kernel.lar.BaseStagedModelDataHandler#exportAssetCategories(
	 *             PortletDataContext, StagedModel)}
	 */
	@Deprecated
	@Override
	public void addAssetCategories(Class<?> clazz, long classPK) {
	}

	@Override
	public void addAssetCategories(
		String className, long classPK, long[] assetCategoryIds) {

		_assetCategoryIdsMap.put(
			getPrimaryKeyString(className, classPK), assetCategoryIds);
	}

	public void addAssetLinks(Class<?> clazz, long classPK) {
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			clazz.getName(), classPK);

		if (assetEntry == null) {
			return;
		}

		List<AssetLink> directAssetLinks =
			AssetLinkLocalServiceUtil.getDirectLinks(assetEntry.getEntryId());

		if (directAssetLinks.isEmpty()) {
			return;
		}

		Map<Integer, List<AssetLink>> assetLinksMap =
			new HashMap<Integer, List<AssetLink>>();

		for (AssetLink assetLink : directAssetLinks) {
			List<AssetLink> assetLinks = assetLinksMap.get(assetLink.getType());

			if (assetLinks == null) {
				assetLinks = new ArrayList<AssetLink>();

				assetLinksMap.put(assetLink.getType(), assetLinks);
			}

			assetLinks.add(assetLink);
		}

		for (Map.Entry<Integer, List<AssetLink>> entry :
				assetLinksMap.entrySet()) {

			_assetLinksMap.put(
				getPrimaryKeyString(assetEntry.getClassUuid(), entry.getKey()),
				entry.getValue());
		}
	}

	@Override
	public void addAssetTags(Class<?> clazz, long classPK) {
		String[] tagNames = AssetTagLocalServiceUtil.getTagNames(
			clazz.getName(), classPK);

		_assetTagNamesMap.put(getPrimaryKeyString(clazz, classPK), tagNames);
	}

	@Override
	public void addAssetTags(
		String className, long classPK, String[] assetTagNames) {

		_assetTagNamesMap.put(
			getPrimaryKeyString(className, classPK), assetTagNames);
	}

	@Override
	public void addClassedModel(
			Element element, String path, ClassedModel classedModel)
		throws PortalException {

		addClassedModel(
			element, path, classedModel, classedModel.getModelClass());
	}

	@Override
	public void addClassedModel(
			Element element, String path, ClassedModel classedModel,
			Class<?> clazz)
		throws PortalException {

		element.addAttribute("path", path);

		if (classedModel instanceof AttachedModel) {
			AttachedModel attachedModel = (AttachedModel)classedModel;

			element.addAttribute("class-name", attachedModel.getClassName());
		}
		else if (BeanUtil.hasProperty(classedModel, "className")) {
			String className = BeanPropertiesUtil.getStringSilent(
				classedModel, "className");

			if (className != null) {
				element.addAttribute("class-name", className);
			}
		}

		if (isPathProcessed(path)) {
			return;
		}

		if (classedModel instanceof AuditedModel) {
			AuditedModel auditedModel = (AuditedModel)classedModel;

			auditedModel.setUserUuid(auditedModel.getUserUuid());
		}

		if (isResourceMain(classedModel)) {
			long classPK = ExportImportClassedModelUtil.getClassPK(
				classedModel);

			addAssetLinks(clazz, classPK);
			addAssetTags(clazz, classPK);
			addExpando(element, path, classedModel, clazz);
			addLocks(clazz, String.valueOf(classPK));
			addPermissions(clazz, classPK);
		}

		_references.add(getReferenceKey(classedModel));

		addZipEntry(path, classedModel);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addClassedModel(Element,
	 *             String, ClassedModel, Class)}
	 */
	@Deprecated
	@Override
	public void addClassedModel(
			Element element, String path, ClassedModel classedModel,
			Class<?> clazz, String namespace)
		throws PortalException {

		addClassedModel(element, path, classedModel, clazz);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addClassedModel(Element,
	 *             String, ClassedModel)}
	 */
	@Deprecated
	@Override
	public void addClassedModel(
			Element element, String path, ClassedModel classedModel,
			String namespace)
		throws PortalException {

		addClassedModel(element, path, classedModel);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portal.kernel.lar.BaseStagedModelDataHandler#exportComments(
	 *             PortletDataContext, StagedModel)}
	 */
	@Deprecated
	@Override
	public void addComments(Class<?> clazz, long classPK) {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portal.kernel.lar.BaseStagedModelDataHandler#exportComments(
	 *             PortletDataContext, StagedModel)}
	 */
	@Deprecated
	@Override
	public void addComments(
		String className, long classPK, List<MBMessage> messages) {
	}

	/**
	 * @see #isWithinDateRange(Date)
	 */
	@Override
	public void addDateRangeCriteria(
		DynamicQuery dynamicQuery, String modifiedDatePropertyName) {

		if (!hasDateRange()) {
			return;
		}

		Property modifiedDateProperty = PropertyFactoryUtil.forName(
			modifiedDatePropertyName);

		dynamicQuery.add(modifiedDateProperty.ge(_startDate));
		dynamicQuery.add(modifiedDateProperty.le(_endDate));
	}

	@Override
	public void addDeletionSystemEventStagedModelTypes(
		StagedModelType... stagedModelTypes) {

		for (StagedModelType stagedModelType : stagedModelTypes) {
			_deletionSystemEventModelTypes.add(stagedModelType);
		}
	}

	@Override
	public void addExpando(
		Element element, String path, ClassedModel classedModel) {

		addExpando(element, path, classedModel, classedModel.getModelClass());
	}

	@Override
	public void addLocks(Class<?> clazz, String key) throws PortalException {
		if (!_locksMap.containsKey(getPrimaryKeyString(clazz, key)) &&
			LockLocalServiceUtil.isLocked(clazz.getName(), key)) {

			Lock lock = LockLocalServiceUtil.getLock(clazz.getName(), key);

			addLocks(clazz.getName(), key, lock);
		}
	}

	@Override
	public void addLocks(String className, String key, Lock lock) {
		_locksMap.put(getPrimaryKeyString(className, key), lock);
	}

	@Override
	public void addPermissions(Class<?> clazz, long classPK) {
		addPermissions(clazz.getName(), classPK);
	}

	@Override
	public void addPermissions(String resourceName, long resourcePK) {
		if (!MapUtil.getBoolean(
				_parameterMap, PortletDataHandlerKeys.PERMISSIONS)) {

			return;
		}

		List<String> actionIds = ResourceActionsUtil.getModelResourceActions(
			resourceName);

		Map<Long, Set<String>> roleIdsToActionIds = null;

		try {
			roleIdsToActionIds = getActionIds(
				resourceName, resourcePK, actionIds);
		}
		catch (PortalException pe) {
			return;
		}

		List<KeyValuePair> permissions = new ArrayList<KeyValuePair>();

		for (Map.Entry<Long, Set<String>> entry :
				roleIdsToActionIds.entrySet()) {

			long roleId = entry.getKey();
			Set<String> availableActionIds = entry.getValue();

			Role role = RoleLocalServiceUtil.fetchRole(roleId);

			if (role == null) {
				continue;
			}

			String roleName = role.getName();

			if (role.isTeam()) {
				roleName = PermissionExporter.ROLE_TEAM_PREFIX + roleName;
			}

			KeyValuePair permission = new KeyValuePair(
				roleName, StringUtil.merge(availableActionIds));

			permissions.add(permission);
		}

		_permissionsMap.put(
			getPrimaryKeyString(resourceName, resourcePK), permissions);
	}

	@Override
	public void addPermissions(
		String resourceName, long resourcePK, List<KeyValuePair> permissions) {

		_permissionsMap.put(
			getPrimaryKeyString(resourceName, resourcePK), permissions);
	}

	@Override
	public void addPortalPermissions() {
		addPermissions(PortletKeys.PORTAL, getCompanyId());
	}

	@Override
	public void addPortletPermissions(String resourceName)
		throws PortalException {

		long groupId = getGroupId();

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isStagingGroup()) {
			if (group.isStagedRemotely()) {
				groupId = group.getLiveGroupId();
			}
			else {
				return;
			}
		}

		addPermissions(resourceName, groupId);
	}

	@Override
	public boolean addPrimaryKey(Class<?> clazz, String primaryKey) {
		boolean value = hasPrimaryKey(clazz, primaryKey);

		if (!value) {
			_primaryKeys.add(getPrimaryKeyString(clazz, primaryKey));
		}

		return value;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portal.kernel.lar.BaseStagedModelDataHandler#exportRatings(
	 *             PortletDataContext, StagedModel)}
	 */
	@Deprecated
	@Override
	public void addRatingsEntries(Class<?> clazz, long classPK) {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portal.kernel.lar.BaseStagedModelDataHandler#exportRatings(
	 *             PortletDataContext, StagedModel)}
	 */
	@Deprecated
	@Override
	public void addRatingsEntries(
		String className, long classPK, List<RatingsEntry> ratingsEntries) {
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public Element addReferenceElement(
		ClassedModel referrerClassedModel, Element element,
		ClassedModel classedModel, Class<?> clazz, String referenceType,
		boolean missing) {

		return addReferenceElement(
			referrerClassedModel, element, classedModel, clazz.getName(),
			StringPool.BLANK, referenceType, missing);
	}

	@Override
	public Element addReferenceElement(
		ClassedModel referrerClassedModel, Element element,
		ClassedModel classedModel, String referenceType, boolean missing) {

		return addReferenceElement(
			referrerClassedModel, element, classedModel,
			ExportImportClassedModelUtil.getClassName(classedModel),
			StringPool.BLANK, referenceType, missing);
	}

	@Override
	public Element addReferenceElement(
		ClassedModel referrerClassedModel, Element element,
		ClassedModel classedModel, String binPath, String referenceType,
		boolean missing) {

		return addReferenceElement(
			referrerClassedModel, element, classedModel,
			ExportImportClassedModelUtil.getClassName(classedModel), binPath,
			referenceType, missing);
	}

	@Override
	public Element addReferenceElement(
		ClassedModel referrerClassedModel, Element element,
		ClassedModel classedModel, String className, String binPath,
		String referenceType, boolean missing) {

		Element referenceElement = doAddReferenceElement(
			referrerClassedModel, element, classedModel, className, binPath,
			referenceType, false);

		String referenceKey = getReferenceKey(classedModel);

		if (missing) {
			if (_references.contains(referenceKey)) {
				return referenceElement;
			}

			referenceElement.addAttribute("missing", Boolean.TRUE.toString());

			if (!_missingReferences.contains(referenceKey)) {
				_missingReferences.add(referenceKey);

				doAddReferenceElement(
					referrerClassedModel, null, classedModel, className,
					binPath, referenceType, true);
			}
		}
		else {
			_references.add(referenceKey);

			referenceElement.addAttribute("missing", Boolean.FALSE.toString());

			cleanUpMissingReferences(classedModel);
		}

		return referenceElement;
	}

	@Override
	public boolean addScopedPrimaryKey(Class<?> clazz, String primaryKey) {
		boolean value = hasScopedPrimaryKey(clazz, primaryKey);

		if (!value) {
			_scopedPrimaryKeys.add(getPrimaryKeyString(clazz, primaryKey));
		}

		return value;
	}

	@Override
	public void addZipEntry(String path, byte[] bytes) {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			ZipWriter zipWriter = getZipWriter();

			zipWriter.addEntry(path, bytes);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void addZipEntry(String path, InputStream is) {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			ZipWriter zipWriter = getZipWriter();

			zipWriter.addEntry(path, is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void addZipEntry(String path, Object object) {
		addZipEntry(path, toXML(object));
	}

	@Override
	public void addZipEntry(String path, String s) {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			ZipWriter zipWriter = getZipWriter();

			zipWriter.addEntry(path, s);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void addZipEntry(String path, StringBuilder sb) {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			ZipWriter zipWriter = getZipWriter();

			zipWriter.addEntry(path, sb);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void cleanUpMissingReferences(ClassedModel classedModel) {
		String referenceKey = getReferenceKey(classedModel);

		if (_missingReferences.contains(referenceKey)) {
			_missingReferences.remove(referenceKey);

			Element missingReferenceElement = getMissingReferenceElement(
				classedModel);

			_missingReferencesElement.remove(missingReferenceElement);
		}
	}

	@Override
	public void clearScopedPrimaryKeys() {
		_scopedPrimaryKeys.clear();
	}

	@Override
	public ServiceContext createServiceContext(
		Element element, ClassedModel classedModel) {

		return createServiceContext(
			element, null, classedModel, classedModel.getModelClass());
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #createServiceContext(Element, ClassedModel)}
	 */
	@Deprecated
	@Override
	public ServiceContext createServiceContext(
		Element element, ClassedModel classedModel, String namespace) {

		return createServiceContext(element, classedModel);
	}

	@Override
	public ServiceContext createServiceContext(StagedModel stagedModel) {
		return createServiceContext(stagedModel, stagedModel.getModelClass());
	}

	@Override
	public ServiceContext createServiceContext(
		StagedModel stagedModel, Class<?> clazz) {

		return createServiceContext(
			null, ExportImportPathUtil.getModelPath(stagedModel), stagedModel,
			clazz);
	}

	@Override
	public ServiceContext createServiceContext(
		String path, ClassedModel classedModel) {

		return createServiceContext(
			null, path, classedModel, classedModel.getModelClass());
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #createServiceContext(String,
	 *             ClassedModel)}
	 */
	@Deprecated
	@Override
	public ServiceContext createServiceContext(
		String path, ClassedModel classedModel, String namespace) {

		return createServiceContext(path, classedModel);
	}

	@Override
	public Object fromXML(byte[] bytes) {
		if (ArrayUtil.isEmpty(bytes)) {
			return null;
		}

		return _xStream.fromXML(new String(bytes));
	}

	@Override
	public Object fromXML(String xml) {
		if (Validator.isNull(xml)) {
			return null;
		}

		return _xStream.fromXML(xml);
	}

	@Override
	public long[] getAssetCategoryIds(Class<?> clazz, long classPK) {
		return _assetCategoryIdsMap.get(getPrimaryKeyString(clazz, classPK));
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public Map<String, long[]> getAssetCategoryIdsMap() {
		return Collections.emptyMap();
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public Map<String, String[]> getAssetCategoryUuidsMap() {
		return Collections.emptyMap();
	}

	@Override
	public Map<String, List<AssetLink>> getAssetLinksMap() {
		return _assetLinksMap;
	}

	@Override
	public String[] getAssetTagNames(Class<?> clazz, long classPK) {
		return _assetTagNamesMap.get(getPrimaryKeyString(clazz, classPK));
	}

	@Override
	public String[] getAssetTagNames(String className, long classPK) {
		return _assetTagNamesMap.get(getPrimaryKeyString(className, classPK));
	}

	@Override
	public Map<String, String[]> getAssetTagNamesMap() {
		return _assetTagNamesMap;
	}

	@Override
	public boolean getBooleanParameter(String namespace, String name) {
		return getBooleanParameter(namespace, name, true);
	}

	@Override
	public boolean getBooleanParameter(
		String namespace, String name, boolean useDefaultValue) {

		if (!useDefaultValue) {
			return MapUtil.getBoolean(
				getParameterMap(),
				PortletDataHandlerControl.getNamespacedControlName(
					namespace, name));
		}

		boolean defaultValue = MapUtil.getBoolean(
			getParameterMap(),
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT, true);

		return MapUtil.getBoolean(
			getParameterMap(),
			PortletDataHandlerControl.getNamespacedControlName(namespace, name),
			defaultValue);
	}

	@Override
	public ClassLoader getClassLoader() {
		return _xStream.getClassLoader();
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public Map<String, List<MBMessage>> getComments() {
		return Collections.emptyMap();
	}

	@Override
	public long getCompanyGroupId() {
		return _companyGroupId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public String getDataStrategy() {
		return _dataStrategy;
	}

	@Override
	public DateRange getDateRange() {
		DateRange dateRange = null;

		if (hasDateRange()) {
			dateRange = new DateRange(_startDate, _endDate);
		}

		return dateRange;
	}

	@Override
	public Set<StagedModelType> getDeletionSystemEventStagedModelTypes() {
		return _deletionSystemEventModelTypes;
	}

	@Override
	public Date getEndDate() {
		return _endDate;
	}

	@Override
	public Map<String, List<ExpandoColumn>> getExpandoColumns() {
		return _expandoColumnsMap;
	}

	@Override
	public Element getExportDataElement(ClassedModel classedModel) {
		return getExportDataElement(
			classedModel,
			ExportImportClassedModelUtil.getClassSimpleName(classedModel));
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getExportDataElement(ClassedModel, String)}
	 */
	@Deprecated
	@Override
	public Element getExportDataElement(
		ClassedModel classedModel, Class<?> modelClass) {

		return getExportDataElement(classedModel, modelClass.getSimpleName());
	}

	@Override
	public Element getExportDataElement(
		ClassedModel classedModel, String modelClassSimpleName) {

		Element groupElement = getExportDataGroupElement(modelClassSimpleName);

		Element element = null;

		if (classedModel instanceof StagedModel) {
			StagedModel stagedModel = (StagedModel)classedModel;

			String path = ExportImportPathUtil.getModelPath(stagedModel);

			element = getDataElement(groupElement, "path", path);

			if (element != null) {
				return element;
			}

			element = getDataElement(
				groupElement, "uuid", stagedModel.getUuid());

			if (element != null) {
				return element;
			}
		}

		element = groupElement.addElement("staged-model");

		if (classedModel instanceof StagedGroupedModel) {
			StagedGroupedModel stagedGroupedModel =
				(StagedGroupedModel)classedModel;

			element.addAttribute(
				"group-id",String.valueOf(stagedGroupedModel.getGroupId()));
			element.addAttribute("uuid", stagedGroupedModel.getUuid());
		}
		else if (classedModel instanceof StagedModel) {
			StagedModel stagedModel = (StagedModel)classedModel;

			element.addAttribute("uuid", stagedModel.getUuid());
		}

		return element;
	}

	@Override
	public Element getExportDataGroupElement(
		Class<? extends StagedModel> clazz) {

		return getExportDataGroupElement(clazz.getSimpleName());
	}

	@Override
	public Element getExportDataRootElement() {
		return _exportDataRootElement;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public Element getImportDataElement(StagedModel stagedModel) {
		return getImportDataElement(
			ExportImportClassedModelUtil.getClassSimpleName(stagedModel),
			"uuid", stagedModel.getUuid());
	}

	@Override
	public Element getImportDataElement(
		String name, String attribute, String value) {

		Element groupElement = getImportDataGroupElement(name);

		return getDataElement(groupElement, attribute, value);
	}

	@Override
	public Element getImportDataGroupElement(
		Class<? extends StagedModel> clazz) {

		return getImportDataGroupElement(clazz.getSimpleName());
	}

	@Override
	public Element getImportDataRootElement() {
		return _importDataRootElement;
	}

	@Override
	public Element getImportDataStagedModelElement(StagedModel stagedModel) {
		String path = ExportImportPathUtil.getModelPath(stagedModel);

		return getImportDataElement(
			ExportImportClassedModelUtil.getClassSimpleName(stagedModel),
			"path", path);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             ExportImportPathUtil#getLayoutPath(PortletDataContext, long)}
	 */
	@Deprecated
	@Override
	public String getLayoutPath(long plid) {
		return StringPool.BLANK;
	}

	@Override
	public Map<String, Lock> getLocks() {
		return _locksMap;
	}

	@Override
	public ManifestSummary getManifestSummary() {
		return _manifestSummary;
	}

	@Override
	public Element getMissingReferencesElement() {
		return _missingReferencesElement;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getNewPrimaryKeysMap(String)}
	 */
	@Deprecated
	@Override
	public List<Layout> getNewLayouts() {
		return _newLayouts;
	}

	@Override
	public Map<?, ?> getNewPrimaryKeysMap(Class<?> clazz) {
		return getNewPrimaryKeysMap(clazz.getName());
	}

	@Override
	public Map<?, ?> getNewPrimaryKeysMap(String className) {
		Map<?, ?> map = _newPrimaryKeysMaps.get(className);

		if (map == null) {
			map = new HashMap<Object, Object>();

			_newPrimaryKeysMaps.put(className, map);
		}

		return map;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public long getOldPlid() {
		return _oldPlid;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	@Override
	public Map<String, List<KeyValuePair>> getPermissions() {
		return _permissionsMap;
	}

	@Override
	public long getPlid() {
		return _plid;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             ExportImportPathUtil#getPortletPath(PortletDataContext,
	 *             String)}
	 */
	@Deprecated
	@Override
	public String getPortletPath(String portletId) {
		return ExportImportPathUtil.getPortletPath(this, portletId);
	}

	@Override
	public Set<String> getPrimaryKeys() {
		return _primaryKeys;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public Map<String, List<RatingsEntry>> getRatingsEntries() {
		return Collections.emptyMap();
	}

	@Override
	public Element getReferenceDataElement(
		Element parentElement, Class<?> clazz, long classPK) {

		List<Element> referenceElements = getReferenceElements(
			parentElement, clazz.getName(), 0, null, classPK, null);

		List<Element> referenceDataElements = getReferenceDataElements(
			referenceElements, clazz);

		if (referenceDataElements.isEmpty()) {
			return null;
		}

		return referenceDataElements.get(0);
	}

	@Override
	public Element getReferenceDataElement(
		Element parentElement, Class<?> clazz, long groupId, String uuid) {

		List<Element> referenceElements = getReferenceElements(
			parentElement, clazz.getName(), groupId, uuid, 0, null);

		List<Element> referenceDataElements = getReferenceDataElements(
			referenceElements, clazz);

		if (referenceDataElements.isEmpty()) {
			return null;
		}

		return referenceDataElements.get(0);
	}

	@Override
	public Element getReferenceDataElement(
		StagedModel parentStagedModel, Class<?> clazz, long classPK) {

		Element parentElement = getImportDataStagedModelElement(
			parentStagedModel);

		return getReferenceDataElement(parentElement, clazz, classPK);
	}

	@Override
	public Element getReferenceDataElement(
		StagedModel parentStagedModel, Class<?> clazz, long groupId,
		String uuid) {

		Element parentElement = getImportDataStagedModelElement(
			parentStagedModel);

		return getReferenceDataElement(parentElement, clazz, groupId, uuid);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public List<Element> getReferenceDataElements(
		Element parentElement, Class<?> clazz) {

		return getReferenceDataElements(parentElement, clazz, null);
	}

	@Override
	public List<Element> getReferenceDataElements(
		Element parentElement, Class<?> clazz, String referenceType) {

		List<Element> referenceElements = getReferenceElements(
			parentElement, clazz.getName(), 0, null, 0, referenceType);

		return getReferenceDataElements(referenceElements, clazz);
	}

	@Override
	public List<Element> getReferenceDataElements(
		StagedModel parentStagedModel, Class<?> clazz) {

		return getReferenceDataElements(parentStagedModel, clazz, null);
	}

	@Override
	public List<Element> getReferenceDataElements(
		StagedModel parentStagedModel, Class<?> clazz, String referenceType) {

		List<Element> referenceElements = getReferenceElements(
			parentStagedModel, clazz.getName(), 0, referenceType);

		return getReferenceDataElements(referenceElements, clazz);
	}

	@Override
	public Element getReferenceElement(
		Element parentElement, Class<?> clazz, long groupId, String uuid,
		String referenceType) {

		List<Element> referenceElements = getReferenceElements(
			parentElement, clazz.getName(), groupId, uuid, 0, referenceType);

		if (!referenceElements.isEmpty()) {
			return referenceElements.get(0);
		}

		return null;
	}

	@Override
	public Element getReferenceElement(
		StagedModel parentStagedModel, Class<?> clazz, long classPK) {

		return getReferenceElement(parentStagedModel, clazz.getName(), classPK);
	}

	@Override
	public Element getReferenceElement(
		StagedModel parentStagedModel, String className, long classPK) {

		List<Element> referenceElements = getReferenceElements(
			parentStagedModel, className, classPK, null);

		if (!referenceElements.isEmpty()) {
			return referenceElements.get(0);
		}

		return null;
	}

	@Override
	public List<Element> getReferenceElements(
		StagedModel parentStagedModel, Class<?> clazz) {

		return getReferenceElements(
			parentStagedModel, clazz.getName(), 0, null);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             ExportImportPathUtil#getRootPath(PortletDataContext)}
	 */
	@Deprecated
	@Override
	public String getRootPath() {
		return ExportImportPathUtil.getRootPath(this);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public Set<String> getScopedPrimaryKeys() {
		return _scopedPrimaryKeys;
	}

	@Override
	public long getScopeGroupId() {
		return _scopeGroupId;
	}

	@Override
	public String getScopeLayoutUuid() {
		return _scopeLayoutUuid;
	}

	@Override
	public String getScopeType() {
		return _scopeType;
	}

	@Override
	public long getSourceCompanyGroupId() {
		return _sourceCompanyGroupId;
	}

	@Override
	public long getSourceCompanyId() {
		return _sourceCompanyId;
	}

	@Override
	public long getSourceGroupId() {
		return _sourceGroupId;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             ExportImportPathUtil#getSourceLayoutPath(PortletDataContext,
	 *             long)}
	 */
	@Deprecated
	@Override
	public String getSourceLayoutPath(long layoutId) {
		return StringPool.BLANK;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             ExportImportPathUtil#getSourcePortletPath(PortletDataContext,
	 *             String)}
	 */
	@Deprecated
	@Override
	public String getSourcePortletPath(String portletId) {
		return StringPool.BLANK;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             ExportImportPathUtil#getSourceRootPath(PortletDataContext)}
	 */
	@Deprecated
	@Override
	public String getSourceRootPath() {
		return ExportImportPathUtil.getSourceRootPath(this);
	}

	@Override
	public long getSourceUserPersonalSiteGroupId() {
		return _sourceUserPersonalSiteGroupId;
	}

	@Override
	public Date getStartDate() {
		return _startDate;
	}

	@Override
	public long getUserId(String userUuid) {
		return _userIdStrategy.getUserId(userUuid);
	}

	@Override
	public UserIdStrategy getUserIdStrategy() {
		return _userIdStrategy;
	}

	@Override
	public long getUserPersonalSiteGroupId() {
		return _userPersonalSiteGroupId;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public List<String> getZipEntries() {
		return getZipReader().getEntries();
	}

	@Override
	public byte[] getZipEntryAsByteArray(String path) {
		if (!Validator.isFilePath(path, false)) {
			return null;
		}

		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsByteArray(path);
	}

	@Override
	public InputStream getZipEntryAsInputStream(String path) {
		if (!Validator.isFilePath(path, false)) {
			return null;
		}

		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsInputStream(path);
	}

	@Override
	public Object getZipEntryAsObject(Element element, String path) {
		Object object = fromXML(getZipEntryAsString(path));

		Attribute classNameAttribute = element.attribute("class-name");

		if (classNameAttribute != null) {
			BeanPropertiesUtil.setProperty(
				object, "className", classNameAttribute.getText());
		}

		return object;
	}

	@Override
	public Object getZipEntryAsObject(String path) {
		return fromXML(getZipEntryAsString(path));
	}

	@Override
	public String getZipEntryAsString(String path) {
		if (!Validator.isFilePath(path, false)) {
			return null;
		}

		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsString(path);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public List<String> getZipFolderEntries() {
		return getZipFolderEntries(StringPool.SLASH);
	}

	@Override
	public List<String> getZipFolderEntries(String path) {
		if (!Validator.isFilePath(path, false)) {
			return null;
		}

		return getZipReader().getFolderEntries(path);
	}

	@Override
	public ZipReader getZipReader() {
		return _zipReader;
	}

	@Override
	public ZipWriter getZipWriter() {
		return _zipWriter;
	}

	@Override
	public boolean hasDateRange() {
		if (_startDate != null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasNotUniquePerLayout(String dataKey) {
		return _notUniquePerLayout.contains(dataKey);
	}

	@Override
	public boolean hasPrimaryKey(Class<?> clazz, String primaryKey) {
		return _primaryKeys.contains(getPrimaryKeyString(clazz, primaryKey));
	}

	@Override
	public boolean hasScopedPrimaryKey(Class<?> clazz, String primaryKey) {
		return _scopedPrimaryKeys.contains(
			getPrimaryKeyString(clazz, primaryKey));
	}

	@Override
	public void importClassedModel(
			ClassedModel classedModel, ClassedModel newClassedModel)
		throws PortalException {

		importClassedModel(
			classedModel, newClassedModel, classedModel.getModelClass());
	}

	@Override
	public void importClassedModel(
			ClassedModel classedModel, ClassedModel newClassedModel,
			Class<?> clazz)
		throws PortalException {

		if (!isResourceMain(classedModel)) {
			return;
		}

		long classPK = ExportImportClassedModelUtil.getClassPK(classedModel);

		long newClassPK = ExportImportClassedModelUtil.getClassPK(
			newClassedModel);

		Map<Long, Long> newPrimaryKeysMap =
			(Map<Long, Long>)getNewPrimaryKeysMap(clazz);

		newPrimaryKeysMap.put(classPK, newClassPK);

		if (classedModel instanceof StagedGroupedModel &&
			newClassedModel instanceof StagedGroupedModel) {

			Map<Long, Long> groupIds = (Map<Long, Long>)getNewPrimaryKeysMap(
				Group.class);

			StagedGroupedModel stagedGroupedModel =
				(StagedGroupedModel)classedModel;

			if (!groupIds.containsKey(stagedGroupedModel.getGroupId())) {
				StagedGroupedModel newStagedGroupedModel =
					(StagedGroupedModel)newClassedModel;

				groupIds.put(
					stagedGroupedModel.getGroupId(),
					newStagedGroupedModel.getGroupId());
			}
		}

		importLocks(clazz, String.valueOf(classPK), String.valueOf(newClassPK));
		importPermissions(clazz, classPK, newClassPK);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #importClassedModel(ClassedModel, ClassedModel, Class)}
	 */
	@Deprecated
	@Override
	public void importClassedModel(
			ClassedModel classedModel, ClassedModel newClassedModel,
			Class<?> clazz, String namespace)
		throws PortalException {

		importClassedModel(classedModel, newClassedModel, clazz);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #importClassedModel(ClassedModel, ClassedModel)}
	 */
	@Deprecated
	@Override
	public void importClassedModel(
			ClassedModel classedModel, ClassedModel newClassedModel,
			String namespace)
		throws PortalException {

		importClassedModel(
			classedModel, newClassedModel, classedModel.getModelClass());
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portal.kernel.lar.BaseStagedModelDataHandler#importComments(
	 *             PortletDataContext, StagedModel)}
	 */
	@Deprecated
	@Override
	public void importComments(
		Class<?> clazz, long classPK, long newClassPK, long groupId) {
	}

	@Override
	public void importLocks(Class<?> clazz, String key, String newKey)
		throws PortalException {

		Lock lock = _locksMap.get(getPrimaryKeyString(clazz, key));

		if (lock == null) {
			return;
		}

		long userId = getUserId(lock.getUserUuid());

		long expirationTime = 0;

		if (lock.getExpirationDate() != null) {
			Date expirationDate = lock.getExpirationDate();

			expirationTime = expirationDate.getTime();
		}

		LockLocalServiceUtil.lock(
			userId, clazz.getName(), newKey, lock.getOwner(),
			lock.isInheritable(), expirationTime);
	}

	@Override
	public void importPermissions(Class<?> clazz, long classPK, long newClassPK)
		throws PortalException {

		importPermissions(clazz.getName(), classPK, newClassPK);
	}

	@Override
	public void importPermissions(
			String resourceName, long resourcePK, long newResourcePK)
		throws PortalException {

		if (!MapUtil.getBoolean(
				_parameterMap, PortletDataHandlerKeys.PERMISSIONS)) {

			return;
		}

		List<KeyValuePair> permissions = _permissionsMap.get(
			getPrimaryKeyString(resourceName, resourcePK));

		if (permissions == null) {
			return;
		}

		Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();

		for (KeyValuePair permission : permissions) {
			String roleName = permission.getKey();

			Role role = null;

			Team team = null;

			if (roleName.startsWith(PermissionExporter.ROLE_TEAM_PREFIX)) {
				roleName = roleName.substring(
					PermissionExporter.ROLE_TEAM_PREFIX.length());

				try {
					team = TeamLocalServiceUtil.getTeam(_groupId, roleName);
				}
				catch (NoSuchTeamException nste) {
					if (_log.isWarnEnabled()) {
						_log.warn("Team " + roleName + " does not exist");
					}

					continue;
				}
			}

			try {
				if (team != null) {
					role = RoleLocalServiceUtil.getTeamRole(
						_companyId, team.getTeamId());
				}
				else {
					role = RoleLocalServiceUtil.getRole(_companyId, roleName);
				}
			}
			catch (NoSuchRoleException nsre) {
				if (_log.isWarnEnabled()) {
					_log.warn("Role " + roleName + " does not exist");
				}

				continue;
			}

			String[] actionIds = StringUtil.split(permission.getValue());

			roleIdsToActionIds.put(role.getRoleId(), actionIds);
		}

		if (roleIdsToActionIds.isEmpty()) {
			return;
		}

		if (ResourceBlockLocalServiceUtil.isSupported(resourceName)) {
			ResourceBlockLocalServiceUtil.setIndividualScopePermissions(
				_companyId, _groupId, resourceName, newResourcePK,
				roleIdsToActionIds);
		}
		else {
			ResourcePermissionLocalServiceUtil.setResourcePermissions(
				_companyId, resourceName, ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(newResourcePK), roleIdsToActionIds);
		}
	}

	@Override
	public void importPortalPermissions() throws PortalException {
		importPermissions(
			PortletKeys.PORTAL, getSourceCompanyId(), getCompanyId());
	}

	@Override
	public void importPortletPermissions(String resourceName)
		throws PortalException {

		importPermissions(resourceName, getSourceGroupId(), getScopeGroupId());
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portal.kernel.lar.BaseStagedModelDataHandler#importRatings(
	 *             PortletDataContext, StagedModel)}
	 */
	@Deprecated
	@Override
	public void importRatingsEntries(
		Class<?> clazz, long classPK, long newClassPK) {
	}

	@Override
	public boolean isCompanyStagedGroupedModel(
		StagedGroupedModel stagedGroupedModel) {

		if ((stagedGroupedModel.getGroupId() == getCompanyGroupId()) &&
			(getGroupId() != getCompanyGroupId())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isDataStrategyMirror() {
		if (_dataStrategy.equals(PortletDataHandlerKeys.DATA_STRATEGY_MIRROR) ||
			_dataStrategy.equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE)) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isDataStrategyMirrorWithOverwriting() {
		if (_dataStrategy.equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE)) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isModelCounted(String className, long classPK) {
		String modelCountedPrimaryKey = className.concat(
			StringPool.POUND).concat(String.valueOf(classPK));

		return addPrimaryKey(String.class, modelCountedPrimaryKey);
	}

	@Override
	public boolean isPathExportedInScope(String path) {
		return addScopedPrimaryKey(String.class, path);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isPathNotExportedInScope(String path) {
		return !isPathExportedInScope(path);
	}

	@Override
	public boolean isPathNotProcessed(String path) {
		return !isPathProcessed(path);
	}

	@Override
	public boolean isPathProcessed(String path) {
		addScopedPrimaryKey(String.class, path);

		return addPrimaryKey(String.class, path);
	}

	@Override
	public boolean isPerformDirectBinaryImport() {
		return MapUtil.getBoolean(
			_parameterMap, PortletDataHandlerKeys.PERFORM_DIRECT_BINARY_IMPORT);
	}

	@Override
	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	@Override
	public boolean isStagedModelCounted(StagedModel stagedModel) {
		StagedModelType stagedModelType = stagedModel.getStagedModelType();

		return isModelCounted(
			stagedModelType.getClassName(),
			(Long)stagedModel.getPrimaryKeyObj());
	}

	/**
	 * @see #addDateRangeCriteria(DynamicQuery, String)
	 */
	@Override
	public boolean isWithinDateRange(Date modifiedDate) {
		if (!hasDateRange()) {
			return true;
		}
		else if ((_startDate.compareTo(modifiedDate) <= 0) &&
				 _endDate.after(modifiedDate)) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void putNotUniquePerLayout(String dataKey) {
		_notUniquePerLayout.add(dataKey);
	}

	@Override
	public void setClassLoader(ClassLoader classLoader) {
		_xStream.setClassLoader(classLoader);
	}

	@Override
	public void setCompanyGroupId(long companyGroupId) {
		_companyGroupId = companyGroupId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public void setDataStrategy(String dataStrategy) {
		_dataStrategy = dataStrategy;
	}

	@Override
	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	@Override
	public void setExportDataRootElement(Element exportDataRootElement) {
		_exportDataRootElement = exportDataRootElement;
	}

	@Override
	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	@Override
	public void setImportDataRootElement(Element importDataRootElement) {
		_importDataRootElement = importDataRootElement;
	}

	@Override
	public void setManifestSummary(ManifestSummary manifestSummary) {
		_manifestSummary = manifestSummary;
	}

	@Override
	public void setMissingReferencesElement(Element missingReferencesElement) {
		_missingReferencesElement = missingReferencesElement;
	}

	@Override
	public void setNewLayouts(List<Layout> newLayouts) {
		_newLayouts = newLayouts;
	}

	@Override
	public void setOldPlid(long oldPlid) {
		_oldPlid = oldPlid;
	}

	@Override
	public void setParameterMap(Map<String, String[]> parameterMap) {
		_parameterMap = parameterMap;
	}

	@Override
	public void setPlid(long plid) {
		_plid = plid;
	}

	@Override
	public void setPortetDataContextListener(
		PortletDataContextListener portletDataContextListener) {

		_portletDataContextListener = portletDataContextListener;
	}

	@Override
	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	@Override
	public void setScopeGroupId(long scopeGroupId) {
		_scopeGroupId = scopeGroupId;
	}

	@Override
	public void setScopeLayoutUuid(String scopeLayoutUuid) {
		_scopeLayoutUuid = scopeLayoutUuid;
	}

	@Override
	public void setScopeType(String scopeType) {
		_scopeType = scopeType;
	}

	@Override
	public void setSourceCompanyGroupId(long sourceCompanyGroupId) {
		_sourceCompanyGroupId = sourceCompanyGroupId;
	}

	@Override
	public void setSourceCompanyId(long sourceCompanyId) {
		_sourceCompanyId = sourceCompanyId;
	}

	@Override
	public void setSourceGroupId(long sourceGroupId) {
		_sourceGroupId = sourceGroupId;
	}

	@Override
	public void setSourceUserPersonalSiteGroupId(
		long sourceUserPersonalSiteGroupId) {

		_sourceUserPersonalSiteGroupId = sourceUserPersonalSiteGroupId;
	}

	@Override
	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	@Override
	public void setUserIdStrategy(UserIdStrategy userIdStrategy) {
		_userIdStrategy = userIdStrategy;
	}

	@Override
	public void setUserPersonalSiteGroupId(long userPersonalSiteGroupId) {
		_userPersonalSiteGroupId = userPersonalSiteGroupId;
	}

	@Override
	public void setZipReader(ZipReader zipReader) {
		_zipReader = zipReader;
	}

	@Override
	public void setZipWriter(ZipWriter zipWriter) {
		_zipWriter = zipWriter;
	}

	@Override
	public String toXML(Object object) {
		return _xStream.toXML(object);
	}

	protected void addExpando(
		Element element, String path, ClassedModel classedModel,
		Class<?> clazz) {

		String className = clazz.getName();

		if (!_expandoColumnsMap.containsKey(className)) {
			List<ExpandoColumn> expandoColumns =
				ExpandoColumnLocalServiceUtil.getDefaultTableColumns(
					_companyId, className);

			for (ExpandoColumn expandoColumn : expandoColumns) {
				addPermissions(
					ExpandoColumn.class, expandoColumn.getColumnId());
			}

			_expandoColumnsMap.put(className, expandoColumns);
		}

		ExpandoBridge expandoBridge = classedModel.getExpandoBridge();

		if (expandoBridge == null) {
			return;
		}

		Map<String, Serializable> expandoBridgeAttributes =
			expandoBridge.getAttributes();

		if (!expandoBridgeAttributes.isEmpty()) {
			String expandoPath = ExportImportPathUtil.getExpandoPath(path);

			element.addAttribute("expando-path", expandoPath);

			addZipEntry(expandoPath, expandoBridgeAttributes);
		}
	}

	protected ServiceContext createServiceContext(
		Element element, String path, ClassedModel classedModel,
		Class<?> clazz) {

		long classPK = ExportImportClassedModelUtil.getClassPK(classedModel);

		ServiceContext serviceContext = new ServiceContext();

		// Theme display

		serviceContext.setCompanyId(getCompanyId());
		serviceContext.setScopeGroupId(getScopeGroupId());

		// Dates

		if (classedModel instanceof AuditedModel) {
			AuditedModel auditedModel = (AuditedModel)classedModel;

			serviceContext.setUserId(getUserId(auditedModel));
			serviceContext.setCreateDate(auditedModel.getCreateDate());
			serviceContext.setModifiedDate(auditedModel.getModifiedDate());
		}

		// Permissions

		if (!MapUtil.getBoolean(
				_parameterMap, PortletDataHandlerKeys.PERMISSIONS)) {

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
		}

		// Asset

		if (isResourceMain(classedModel)) {
			long[] assetCategoryIds = getAssetCategoryIds(clazz, classPK);

			serviceContext.setAssetCategoryIds(assetCategoryIds);

			String[] assetTagNames = getAssetTagNames(clazz, classPK);

			serviceContext.setAssetTagNames(assetTagNames);
		}

		// Expando

		String expandoPath = null;

		if (element != null) {
			expandoPath = element.attributeValue("expando-path");
		}
		else {
			expandoPath = ExportImportPathUtil.getExpandoPath(path);
		}

		if (Validator.isNotNull(expandoPath)) {
			try {
				Map<String, Serializable> expandoBridgeAttributes =
					(Map<String, Serializable>)getZipEntryAsObject(expandoPath);

				if (expandoBridgeAttributes != null) {
					serviceContext.setExpandoBridgeAttributes(
						expandoBridgeAttributes);
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}

		return serviceContext;
	}

	protected Element doAddReferenceElement(
		ClassedModel referrerClassedModel, Element element,
		ClassedModel classedModel, String className, String binPath,
		String referenceType, boolean missing) {

		Element referenceElement = null;

		if (missing) {
			Element referencesElement = _missingReferencesElement;

			referenceElement = referencesElement.addElement(
				"missing-reference");
		}
		else {
			Element referencesElement = element.element("references");

			if (referencesElement == null) {
				referencesElement = element.addElement("references");
			}

			referenceElement = referencesElement.addElement("reference");
		}

		referenceElement.addAttribute("class-name", className);

		referenceElement.addAttribute(
			"class-pk", String.valueOf(classedModel.getPrimaryKeyObj()));

		if (missing) {
			if (classedModel instanceof StagedModel) {
				referenceElement.addAttribute(
					"display-name",
					StagedModelDataHandlerUtil.getDisplayName(
						(StagedModel)classedModel));
			}
			else {
				referenceElement.addAttribute(
					"display-name",
					String.valueOf(classedModel.getPrimaryKeyObj()));
			}
		}

		if (classedModel instanceof StagedGroupedModel) {
			StagedGroupedModel stagedGroupedModel =
				(StagedGroupedModel)classedModel;

			referenceElement.addAttribute(
				"group-id", String.valueOf(stagedGroupedModel.getGroupId()));

			try {
				Group group = GroupLocalServiceUtil.getGroup(
					stagedGroupedModel.getGroupId());

				long liveGroupId = group.getLiveGroupId();

				if (group.isStagedRemotely()) {
					liveGroupId = group.getRemoteLiveGroupId();
				}

				if (liveGroupId == GroupConstants.DEFAULT_LIVE_GROUP_ID) {
					liveGroupId = group.getGroupId();
				}

				referenceElement.addAttribute(
					"live-group-id", String.valueOf(liveGroupId));
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to find group " +
							stagedGroupedModel.getGroupId());
				}
			}
		}

		if (Validator.isNotNull(binPath)) {
			referenceElement.addAttribute("path", binPath);
		}

		referenceElement.addAttribute("type", referenceType);

		if (missing) {
			referenceElement.addAttribute(
				"referrer-class-name",
				ExportImportClassedModelUtil.getClassName(
					referrerClassedModel));

			if (referrerClassedModel instanceof PortletModel) {
				Portlet portlet = (Portlet)referrerClassedModel;

				referenceElement.addAttribute(
					"referrer-display-name", portlet.getRootPortletId());
			}
			else if (referrerClassedModel instanceof StagedModel) {
				StagedModel referrerStagedModel =
					(StagedModel)referrerClassedModel;

				referenceElement.addAttribute(
					"referrer-display-name",
					StagedModelDataHandlerUtil.getDisplayName(
						referrerStagedModel));
			}
		}

		if (classedModel instanceof StagedModel) {
			StagedModel stagedModel = (StagedModel)classedModel;

			referenceElement.addAttribute("uuid", stagedModel.getUuid());
			referenceElement.addAttribute(
				"company-id", String.valueOf(stagedModel.getCompanyId()));

			Map<String, String> referenceAttributes =
				StagedModelDataHandlerUtil.getReferenceAttributes(
					this, stagedModel);

			for (Map.Entry<String, String> referenceAttribute :
					referenceAttributes.entrySet()) {

				referenceElement.addAttribute(
					referenceAttribute.getKey(), referenceAttribute.getValue());
			}
		}

		return referenceElement;
	}

	protected Map<Long, Set<String>> getActionIds(
			String className, long primKey, List<String> actionIds)
		throws PortalException {

		if (ResourceBlockLocalServiceUtil.isSupported(className)) {
			return ResourceBlockPermissionLocalServiceUtil.
				getAvailableResourceBlockPermissionActionIds(
					className, primKey, actionIds);
		}
		else {
			return ResourcePermissionLocalServiceUtil.
				getAvailableResourcePermissionActionIds(
					_companyId, className, ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(primKey), actionIds);
		}
	}

	protected Element getDataElement(
		Element parentElement, String attribute, String value) {

		if (parentElement == null) {
			return null;
		}

		StringBundler sb = new StringBundler(6);

		sb.append("staged-model");
		sb.append("[@");
		sb.append(attribute);
		sb.append(StringPool.EQUAL);
		sb.append(HtmlUtil.escapeXPathAttribute(value));
		sb.append(StringPool.CLOSE_BRACKET);

		XPath xPath = SAXReaderUtil.createXPath(sb.toString());

		return (Element)xPath.selectSingleNode(parentElement);
	}

	protected Element getExportDataGroupElement(String name) {
		if (_exportDataRootElement == null) {
			throw new IllegalStateException(
				"Root data element not initialized");
		}

		Element groupElement = _exportDataRootElement.element(name);

		if (groupElement == null) {
			groupElement = _exportDataRootElement.addElement(name);
		}

		return groupElement;
	}

	protected Element getImportDataGroupElement(String name) {
		if (_importDataRootElement == null) {
			throw new IllegalStateException(
				"Root data element not initialized");
		}

		if (Validator.isNull(name)) {
			return SAXReaderUtil.createElement("EMPTY-ELEMENT");
		}

		Element groupElement = _importDataRootElement.element(name);

		if (groupElement == null) {
			return SAXReaderUtil.createElement("EMPTY-ELEMENT");
		}

		return groupElement;
	}

	protected Element getMissingReferenceElement(ClassedModel classedModel) {
		StringBundler sb = new StringBundler(5);

		sb.append("missing-reference[@class-name='");
		sb.append(ExportImportClassedModelUtil.getClassName(classedModel));
		sb.append("' and @class-pk='");
		sb.append(String.valueOf(classedModel.getPrimaryKeyObj()));
		sb.append("']");

		XPath xPath = SAXReaderUtil.createXPath(sb.toString());

		Node node = xPath.selectSingleNode(_missingReferencesElement);

		return (Element)node;
	}

	protected String getPrimaryKeyString(Class<?> clazz, long classPK) {
		return getPrimaryKeyString(clazz.getName(), String.valueOf(classPK));
	}

	protected String getPrimaryKeyString(Class<?> clazz, String primaryKey) {
		return getPrimaryKeyString(clazz.getName(), primaryKey);
	}

	protected String getPrimaryKeyString(String className, long classPK) {
		return getPrimaryKeyString(className, String.valueOf(classPK));
	}

	protected String getPrimaryKeyString(String className, String primaryKey) {
		return className.concat(StringPool.POUND).concat(primaryKey);
	}

	protected List<Element> getReferenceDataElements(
		List<Element> referenceElements, Class<?> clazz) {

		List<Element> referenceDataElements = new ArrayList<Element>();

		for (Element referenceElement : referenceElements) {
			Element referenceDataElement = null;

			String path = referenceElement.attributeValue("path");

			if (Validator.isNotNull(path)) {
				referenceDataElement = getImportDataElement(
					clazz.getSimpleName(), "path", path);
			}
			else {
				String groupId = referenceElement.attributeValue("group-id");
				String uuid = referenceElement.attributeValue("uuid");

				StringBuilder sb = new StringBuilder(5);

				sb.append("staged-model[@uuid=");
				sb.append(HtmlUtil.escapeXPathAttribute(uuid));

				if (groupId != null) {
					sb.append(" and @group-id=");
					sb.append(HtmlUtil.escapeXPathAttribute(groupId));
				}

				sb.append(StringPool.CLOSE_BRACKET);

				XPath xPath = SAXReaderUtil.createXPath(sb.toString());

				Element groupElement = getImportDataGroupElement(
					clazz.getSimpleName());

				referenceDataElement = (Element)xPath.selectSingleNode(
					groupElement);
			}

			if (referenceDataElement == null) {
				continue;
			}

			referenceDataElements.add(referenceDataElement);
		}

		return referenceDataElements;
	}

	protected List<Element> getReferenceElements(
		Element parentElement, String className, long groupId, String uuid,
		long classPK, String referenceType) {

		if (parentElement == null) {
			return Collections.emptyList();
		}

		Element referencesElement = parentElement.element("references");

		if (referencesElement == null) {
			return Collections.emptyList();
		}

		StringBundler sb = new StringBundler(13);

		sb.append("reference[@class-name=");
		sb.append(HtmlUtil.escapeXPathAttribute(className));

		if (groupId > 0) {
			sb.append(" and @group-id='");
			sb.append(groupId);
			sb.append(StringPool.APOSTROPHE);
		}

		if (Validator.isNotNull(uuid)) {
			sb.append(" and @uuid=");
			sb.append(HtmlUtil.escapeXPathAttribute(uuid));
		}

		if (classPK > 0) {
			sb.append(" and @class-pk='");
			sb.append(classPK);
			sb.append(StringPool.APOSTROPHE);
		}

		if (referenceType != null) {
			sb.append(" and @type=");
			sb.append(HtmlUtil.escapeXPathAttribute(referenceType));
		}

		sb.append(StringPool.CLOSE_BRACKET);

		XPath xPath = SAXReaderUtil.createXPath(sb.toString());

		List<Node> nodes = xPath.selectNodes(referencesElement);

		return ListUtil.fromArray(nodes.toArray(new Element[nodes.size()]));
	}

	protected List<Element> getReferenceElements(
		StagedModel parentStagedModel, String className, long classPK,
		String referenceType) {

		Element stagedModelElement = getImportDataStagedModelElement(
			parentStagedModel);

		return getReferenceElements(
			stagedModelElement, className, 0, null, classPK, referenceType);
	}

	protected String getReferenceKey(ClassedModel classedModel) {
		String referenceKey = ExportImportClassedModelUtil.getClassName(
			classedModel);

		return referenceKey.concat(StringPool.POUND).concat(
			String.valueOf(classedModel.getPrimaryKeyObj()));
	}

	protected long getUserId(AuditedModel auditedModel) {
		try {
			String userUuid = auditedModel.getUserUuid();

			return getUserId(userUuid);
		}
		catch (SystemException se) {
			if (_log.isErrorEnabled()) {
				_log.error(se, se);
			}
		}

		return 0;
	}

	protected void initXStream() {
		_xStream = new XStream();

		Map<Class<?>, String> aliases = XStreamAliasRegistryUtil.getAliases();

		for (Map.Entry<Class<?>, String> alias : aliases.entrySet()) {
			_xStream.alias(alias.getValue(), alias.getKey());
		}

		Set<XStreamConverter> xStreamConverters =
			XStreamConverterRegistryUtil.getXStreamConverters();

		for (XStreamConverter xStreamConverter : xStreamConverters) {
			_xStream.registerConverter(
				new ConverterAdapter(xStreamConverter),
				XStream.PRIORITY_VERY_HIGH);
		}

		_xStream.omitField(HashMap.class, "cache_bitmask");
	}

	protected boolean isResourceMain(ClassedModel classedModel) {
		if (classedModel instanceof ResourcedModel) {
			ResourcedModel resourcedModel = (ResourcedModel)classedModel;

			return resourcedModel.isResourceMain();
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletDataContextImpl.class);

	private final Map<String, long[]> _assetCategoryIdsMap =
		new HashMap<String, long[]>();
	private final Map<String, List<AssetLink>> _assetLinksMap =
		new HashMap<String, List<AssetLink>>();
	private final Map<String, String[]> _assetTagNamesMap =
		new HashMap<String, String[]>();
	private long _companyGroupId;
	private long _companyId;
	private String _dataStrategy;
	private final Set<StagedModelType> _deletionSystemEventModelTypes =
		new HashSet<StagedModelType>();
	private Date _endDate;
	private final Map<String, List<ExpandoColumn>> _expandoColumnsMap =
		new HashMap<String, List<ExpandoColumn>>();
	private Element _exportDataRootElement;
	private long _groupId;
	private Element _importDataRootElement;
	private final Map<String, Lock> _locksMap = new HashMap<String, Lock>();
	private ManifestSummary _manifestSummary = new ManifestSummary();
	private final Set<String> _missingReferences = new HashSet<String>();
	private Element _missingReferencesElement;
	private List<Layout> _newLayouts;
	private final Map<String, Map<?, ?>> _newPrimaryKeysMaps =
		new HashMap<String, Map<?, ?>>();
	private final Set<String> _notUniquePerLayout = new HashSet<String>();
	private long _oldPlid;
	private Map<String, String[]> _parameterMap;
	private final Map<String, List<KeyValuePair>> _permissionsMap =
		new HashMap<String, List<KeyValuePair>>();
	private long _plid;
	private PortletDataContextListener _portletDataContextListener;
	private final Set<String> _primaryKeys = new HashSet<String>();
	private boolean _privateLayout;
	private final Set<String> _references = new HashSet<String>();
	private final Set<String> _scopedPrimaryKeys = new HashSet<String>();
	private long _scopeGroupId;
	private String _scopeLayoutUuid;
	private String _scopeType;
	private long _sourceCompanyGroupId;
	private long _sourceCompanyId;
	private long _sourceGroupId;
	private long _sourceUserPersonalSiteGroupId;
	private Date _startDate;
	private UserIdStrategy _userIdStrategy;
	private long _userPersonalSiteGroupId;
	private XStream _xStream;
	private ZipReader _zipReader;
	private ZipWriter _zipWriter;

}