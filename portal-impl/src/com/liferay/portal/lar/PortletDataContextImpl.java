/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextListener;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PrimitiveLongList;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.model.AttachedModel;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcedModel;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.impl.LockImpl;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockPermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetLinkLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.blogs.model.impl.BlogsEntryImpl;
import com.liferay.portlet.bookmarks.model.impl.BookmarksEntryImpl;
import com.liferay.portlet.bookmarks.model.impl.BookmarksFolderImpl;
import com.liferay.portlet.calendar.model.impl.CalEventImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.model.impl.JournalFeedImpl;
import com.liferay.portlet.journal.model.impl.JournalStructureImpl;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBBanImpl;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.portlet.messageboards.model.impl.MBThreadFlagImpl;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBDiscussionUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageUtil;
import com.liferay.portlet.polls.model.impl.PollsChoiceImpl;
import com.liferay.portlet.polls.model.impl.PollsQuestionImpl;
import com.liferay.portlet.polls.model.impl.PollsVoteImpl;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.impl.RatingsEntryImpl;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;
import com.liferay.portlet.wiki.model.impl.WikiNodeImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;

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
 */
public class PortletDataContextImpl implements PortletDataContext {

	public PortletDataContextImpl(
			long companyId, long groupId, Map<String, String[]> parameterMap,
			Set<String> primaryKeys, Date startDate, Date endDate,
			ZipWriter zipWriter)
		throws PortletDataException {

		validateDateRange(startDate, endDate);

		_companyId = companyId;

		try {
			Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
				_companyId);

			_companyGroupId = companyGroup.getGroupId();
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}

		_groupId = groupId;
		_scopeGroupId = groupId;
		_parameterMap = parameterMap;
		_primaryKeys = primaryKeys;
		_dataStrategy = null;
		_userIdStrategy = null;
		_startDate = startDate;
		_endDate = endDate;
		_zipReader = null;
		_zipWriter = zipWriter;

		initXStream();
	}

	public PortletDataContextImpl(
		long companyId, long groupId, Map<String, String[]> parameterMap,
		Set<String> primaryKeys, UserIdStrategy userIdStrategy,
		ZipReader zipReader) {

		_companyId = companyId;

		try {
			Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
				_companyId);

			_companyGroupId = companyGroup.getGroupId();
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}

		_groupId = groupId;
		_scopeGroupId = groupId;
		_parameterMap = parameterMap;
		_primaryKeys = primaryKeys;
		_dataStrategy = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.DATA_STRATEGY,
			PortletDataHandlerKeys.DATA_STRATEGY_MIRROR);
		_userIdStrategy = userIdStrategy;
		_zipReader = zipReader;
		_zipWriter = null;

		initXStream();
	}

	public void addAssetCategories(Class<?> clazz, long classPK)
		throws SystemException {

		List<AssetCategory> assetCategories =
			AssetCategoryLocalServiceUtil.getCategories(
				clazz.getName(), classPK);

		_assetCategoryUuidsMap.put(
			getPrimaryKeyString(clazz, classPK),
			StringUtil.split(
				ListUtil.toString(
					assetCategories, AssetCategory.UUID_ACCESSOR)));
		_assetCategoryIdsMap.put(
			getPrimaryKeyString(clazz, classPK),
			StringUtil.split(
				ListUtil.toString(
					assetCategories, AssetCategory.CATEGORY_ID_ACCESSOR), 0L));
	}

	public void addAssetCategories(
		String className, long classPK, long[] assetCategoryIds) {

		_assetCategoryIdsMap.put(
			getPrimaryKeyString(className, classPK), assetCategoryIds);
	}

	public void addAssetLinks(Class<?> clazz, long classPK)
		throws PortalException, SystemException {

		AssetEntry assetEntry = null;

		try {
			assetEntry = AssetEntryLocalServiceUtil.getEntry(
				clazz.getName(), classPK);
		}
		catch (NoSuchEntryException nsee) {
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

			int assetLinkType = entry.getKey();
			List<AssetLink> assetLinks = entry.getValue();

			List<String> assetLinkUuids = new ArrayList<String>(
				directAssetLinks.size());

			for (AssetLink assetLink : assetLinks) {
				try {
					AssetEntry assetLinkEntry =
						AssetEntryLocalServiceUtil.getEntry(
							assetLink.getEntryId2());

					assetLinkUuids.add(assetLinkEntry.getClassUuid());
				}
				catch (NoSuchEntryException nsee) {
				}
			}

			_assetLinkUuidsMap.put(
				getPrimaryKeyString(
					assetEntry.getClassUuid(), String.valueOf(assetLinkType)),
				assetLinkUuids.toArray(new String[assetLinkUuids.size()]));
		}
	}

	public void addAssetTags(Class<?> clazz, long classPK)
		throws SystemException {

		String[] tagNames = AssetTagLocalServiceUtil.getTagNames(
			clazz.getName(), classPK);

		_assetTagNamesMap.put(getPrimaryKeyString(clazz, classPK), tagNames);
	}

	public void addAssetTags(
		String className, long classPK, String[] assetTagNames) {

		_assetTagNamesMap.put(
			getPrimaryKeyString(className, classPK), assetTagNames);
	}

	public void addClassedModel(
			Element element, String path, ClassedModel classedModel,
			String namespace)
		throws PortalException, SystemException {

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

		if (classedModel instanceof AuditedModel) {
			AuditedModel auditedModel = (AuditedModel)classedModel;

			auditedModel.setUserUuid(auditedModel.getUserUuid());
		}

		if (isResourceMain(classedModel)) {
			Class<?> clazz = classedModel.getModelClass();
			long classPK = getClassPK(classedModel);

			addAssetLinks(clazz, classPK);
			addExpando(element, path, classedModel);
			addLocks(clazz, String.valueOf(classPK));
			addPermissions(clazz, classPK);

			boolean portletMetadataAll = getBooleanParameter(
				namespace, PortletDataHandlerKeys.PORTLET_METADATA_ALL);

			if (portletMetadataAll ||
				getBooleanParameter(namespace, "categories")) {

				addAssetCategories(clazz, classPK);
			}

			if (portletMetadataAll ||
				getBooleanParameter(namespace, "comments")) {

				addComments(clazz, classPK);
			}

			if (portletMetadataAll ||
				getBooleanParameter(namespace, "ratings")) {

				addRatingsEntries(clazz, classPK);
			}

			if (portletMetadataAll || getBooleanParameter(namespace, "tags")) {
				addAssetTags(clazz, classPK);
			}
		}

		addZipEntry(path, classedModel);
	}

	public void addComments(Class<?> clazz, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(clazz);

		MBDiscussion discussion = MBDiscussionUtil.fetchByC_C(
			classNameId, classPK);

		if (discussion == null) {
			return;
		}

		List<MBMessage> messages = MBMessageLocalServiceUtil.getThreadMessages(
			discussion.getThreadId(), WorkflowConstants.STATUS_APPROVED);

		if (messages.isEmpty()) {
			return;
		}

		MBMessage firstMessage = messages.get(0);

		if ((messages.size() == 1) && firstMessage.isRoot()) {
			return;
		}

		for (MBMessage message : messages) {
			message.setUserUuid(message.getUserUuid());

			addRatingsEntries(MBDiscussion.class, message.getPrimaryKey());
		}

		_commentsMap.put(getPrimaryKeyString(clazz, classPK), messages);
	}

	public void addComments(
		String className, long classPK, List<MBMessage> messages) {

		_commentsMap.put(getPrimaryKeyString(className, classPK), messages);
	}

	/**
	 * @see #isWithinDateRange(Date)
	 */
	public void addDateRangeCriteria(
		DynamicQuery dynamicQuery, String modifiedDatePropertyName) {

		if (!hasDateRange()) {
			return;
		}

		Property modifiedDateProperty = PropertyFactoryUtil.forName(
			modifiedDatePropertyName);

		dynamicQuery.add(modifiedDateProperty.ge(_startDate));
		dynamicQuery.add(modifiedDateProperty.lt(_endDate));
	}

	public void addExpando(
			Element element, String path, ClassedModel classedModel)
		throws PortalException, SystemException {

		Class<?> clazz = classedModel.getModelClass();

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

		Map<String, Serializable> expandoBridgeAttributes =
			expandoBridge.getAttributes();

		if (!expandoBridgeAttributes.isEmpty()) {
			String expandoPath = ExportImportPathUtil.getExpandoPath(path);

			element.addAttribute("expando-path", expandoPath);

			addZipEntry(expandoPath, expandoBridgeAttributes);
		}
	}

	public void addLocks(Class<?> clazz, String key)
		throws PortalException, SystemException {

		if (!_locksMap.containsKey(getPrimaryKeyString(clazz, key)) &&
			LockLocalServiceUtil.isLocked(clazz.getName(), key)) {

			Lock lock = LockLocalServiceUtil.getLock(clazz.getName(), key);

			addLocks(clazz.getName(), key, lock);
		}
	}

	public void addLocks(String className, String key, Lock lock) {
		_locksMap.put(getPrimaryKeyString(className, key), lock);
	}

	public void addPermissions(Class<?> clazz, long classPK)
		throws PortalException, SystemException {

		addPermissions(clazz.getName(), classPK);
	}

	public void addPermissions(String resourceName, long resourcePK)
		throws PortalException, SystemException {

		if (!MapUtil.getBoolean(
				_parameterMap, PortletDataHandlerKeys.PERMISSIONS)) {

			return;
		}

		List<KeyValuePair> permissions = new ArrayList<KeyValuePair>();

		Group group = GroupLocalServiceUtil.getGroup(_groupId);

		List<Role> roles = RoleLocalServiceUtil.getRoles(_companyId);

		PrimitiveLongList roleIds = new PrimitiveLongList(roles.size());
		Map<Long, String> roleIdsToNames = new HashMap<Long, String>();

		for (Role role : roles) {
			int type = role.getType();

			if ((type == RoleConstants.TYPE_REGULAR) ||
				((type == RoleConstants.TYPE_ORGANIZATION) &&
				 group.isOrganization()) ||
				((type == RoleConstants.TYPE_SITE) &&
				 (group.isLayoutSetPrototype() || group.isSite()))) {

				String name = role.getName();

				roleIds.add(role.getRoleId());
				roleIdsToNames.put(role.getRoleId(), name);
			}
			else if ((type == RoleConstants.TYPE_PROVIDER) && role.isTeam()) {
				Team team = TeamLocalServiceUtil.getTeam(role.getClassPK());

				if (team.getGroupId() == _groupId) {
					String name =
						PermissionExporter.ROLE_TEAM_PREFIX + team.getName();

					roleIds.add(role.getRoleId());
					roleIdsToNames.put(role.getRoleId(), name);
				}
			}
		}

		List<String> actionIds = ResourceActionsUtil.getModelResourceActions(
			resourceName);

		Map<Long, Set<String>> roleIdsToActionIds = getActionIds(
			_companyId, roleIds.getArray(), resourceName, resourcePK,
			actionIds);

		for (Map.Entry<Long, String> entry : roleIdsToNames.entrySet()) {
			long roleId = entry.getKey();
			String name = entry.getValue();

			Set<String> availableActionIds = roleIdsToActionIds.get(roleId);

			if (availableActionIds == null) {
				availableActionIds = Collections.emptySet();
			}

			KeyValuePair permission = new KeyValuePair(
				name, StringUtil.merge(availableActionIds));

			permissions.add(permission);
		}

		_permissionsMap.put(
			getPrimaryKeyString(resourceName, resourcePK), permissions);
	}

	public void addPermissions(
		String resourceName, long resourcePK, List<KeyValuePair> permissions) {

		_permissionsMap.put(
			getPrimaryKeyString(resourceName, resourcePK), permissions);
	}

	public boolean addPrimaryKey(Class<?> clazz, String primaryKey) {
		boolean value = hasPrimaryKey(clazz, primaryKey);

		if (!value) {
			_primaryKeys.add(getPrimaryKeyString(clazz, primaryKey));
		}

		return value;
	}

	public void addRatingsEntries(Class<?> clazz, long classPK)
		throws SystemException {

		List<RatingsEntry> ratingsEntries =
			RatingsEntryLocalServiceUtil.getEntries(clazz.getName(), classPK);

		if (ratingsEntries.size() == 0) {
			return;
		}

		for (RatingsEntry entry : ratingsEntries) {
			entry.setUserUuid(entry.getUserUuid());
		}

		_ratingsEntriesMap.put(
			getPrimaryKeyString(clazz, classPK), ratingsEntries);
	}

	public void addRatingsEntries(
		String className, long classPK, List<RatingsEntry> ratingsEntries) {

		_ratingsEntriesMap.put(
			getPrimaryKeyString(className, classPK), ratingsEntries);
	}

	public Element addReferenceElement(
		Element element, ClassedModel referencedClassedModel) {

		Element referencesElement = element.element("references");

		if (referencesElement == null) {
			referencesElement = element.addElement("references");
		}

		Element referenceElement = referencesElement.addElement("reference");

		referenceElement.addAttribute(
			"class-name", referencedClassedModel.getModelClassName());

		Serializable primaryKeyObj = referencedClassedModel.getPrimaryKeyObj();

		referenceElement.addAttribute(
			"class-pk", String.valueOf(primaryKeyObj));

		return referenceElement;
	}

	public void addZipEntry(String path, byte[] bytes) throws SystemException {
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

	public void addZipEntry(String path, InputStream is)
		throws SystemException {

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

	public void addZipEntry(String path, Object object) throws SystemException {
		addZipEntry(path, toXML(object));
	}

	public void addZipEntry(String path, String s) throws SystemException {
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

	public void addZipEntry(String path, StringBuilder sb)
		throws SystemException {

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

	public ServiceContext createServiceContext(
		Element element, ClassedModel classedModel, String namespace) {

		return createServiceContext(element, null, classedModel, namespace);
	}

	public ServiceContext createServiceContext(
		StagedModel stagedModel, String namespace) {

		return createServiceContext(
			ExportImportPathUtil.getModelPath(stagedModel), stagedModel,
			namespace);
	}

	public ServiceContext createServiceContext(
		String path, ClassedModel classedModel, String namespace) {

		return createServiceContext(null, path, classedModel, namespace);
	}

	public Object fromXML(byte[] bytes) {
		if ((bytes == null) || (bytes.length == 0)) {
			return null;
		}

		return _xStream.fromXML(new String(bytes));
	}

	public Object fromXML(String xml) {
		if (Validator.isNull(xml)) {
			return null;
		}

		return _xStream.fromXML(xml);
	}

	public long[] getAssetCategoryIds(Class<?> clazz, long classPK) {
		return _assetCategoryIdsMap.get(getPrimaryKeyString(clazz, classPK));
	}

	public Map<String, long[]> getAssetCategoryIdsMap() {
		return _assetCategoryIdsMap;
	}

	public Map<String, String[]> getAssetCategoryUuidsMap() {
		return _assetCategoryUuidsMap;
	}

	public Map<String, String[]> getAssetLinkUuidsMap() {
		return _assetLinkUuidsMap;
	}

	public String[] getAssetTagNames(Class<?> clazz, long classPK) {
		return _assetTagNamesMap.get(getPrimaryKeyString(clazz, classPK));
	}

	public String[] getAssetTagNames(String className, long classPK) {
		return _assetTagNamesMap.get(getPrimaryKeyString(className, classPK));
	}

	public Map<String, String[]> getAssetTagNamesMap() {
		return _assetTagNamesMap;
	}

	public boolean getBooleanParameter(String namespace, String name) {
		boolean defaultValue = MapUtil.getBoolean(
			getParameterMap(),
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT, true);

		return MapUtil.getBoolean(
			getParameterMap(),
			PortletDataHandlerControl.getNamespacedControlName(namespace, name),
			defaultValue);
	}

	public ClassLoader getClassLoader() {
		return _xStream.getClassLoader();
	}

	public Map<String, List<MBMessage>> getComments() {
		return _commentsMap;
	}

	public long getCompanyGroupId() {
		return _companyGroupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getDataStrategy() {
		return _dataStrategy;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public Map<String, List<ExpandoColumn>> getExpandoColumns() {
		return _expandoColumnsMap;
	}

	public Element getExportDataGroupElement(
		Class<? extends StagedModel> clazz) {

		return getExportDataGroupElement(clazz.getSimpleName());
	}

	public Element getExportDataRootElement() {
		return _exportDataRootElement;
	}

	public Element getExportDataStagedModelElement(StagedModel stagedModel) {
		Class<?> clazz = stagedModel.getModelClass();

		Element groupElement = getExportDataGroupElement(clazz.getSimpleName());

		return groupElement.addElement("staged-model");
	}

	public long getGroupId() {
		return _groupId;
	}

	public Element getImportDataGroupElement(
		Class<? extends StagedModel> clazz) {

		return getImportDataGroupElement(clazz.getSimpleName());
	}

	public Element getImportDataRootElement() {
		return _importDataRootElement;
	}

	public Element getImportDataStagedModelElement(StagedModel stagedModel) {
		String path = ExportImportPathUtil.getModelPath(stagedModel);

		Class<?> clazz = stagedModel.getModelClass();

		return getImportDataStagedModelElement(
			clazz.getSimpleName(), "path", path);
	}

	public Element getImportDataStagedModelElement(
		String name, String attribute, String value) {

		Element groupElement = getImportDataGroupElement(name);

		if (groupElement == null) {
			return null;
		}

		StringBundler sb = new StringBundler(4);

		sb.append("staged-model");
		sb.append("[@" + attribute + "='");
		sb.append(value);
		sb.append("']");

		XPath xPath = SAXReaderUtil.createXPath(sb.toString());

		return (Element)xPath.selectSingleNode(groupElement);
	}

	public String getLayoutPath(long layoutId) {
		return ExportImportPathUtil.getLayoutPath(this, layoutId);
	}

	public Map<String, Lock> getLocks() {
		return _locksMap;
	}

	public Map<?, ?> getNewPrimaryKeysMap(Class<?> clazz) {
		return getNewPrimaryKeysMap(clazz.getName());
	}

	public Map<?, ?> getNewPrimaryKeysMap(String className) {
		Map<?, ?> map = _newPrimaryKeysMaps.get(className);

		if (map == null) {
			map = new HashMap<Object, Object>();

			_newPrimaryKeysMaps.put(className, map);
		}

		return map;
	}

	public long getOldPlid() {
		return _oldPlid;
	}

	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	public Map<String, List<KeyValuePair>> getPermissions() {
		return _permissionsMap;
	}

	public long getPlid() {
		return _plid;
	}

	public String getPortletPath(String portletId) {
		return ExportImportPathUtil.getPortletPath(this, portletId);
	}

	public Set<String> getPrimaryKeys() {
		return _primaryKeys;
	}

	public Map<String, List<RatingsEntry>> getRatingsEntries() {
		return _ratingsEntriesMap;
	}

	public List<Element> getReferencedDataElements(
		StagedModel parentStagedModel, Class<? extends StagedModel> clazz) {

		List<Element> referencedElements = new ArrayList<Element>();

		List<Element> referenceElements = getReferenceElements(
			parentStagedModel, clazz);

		for (Element referenceElement : referenceElements) {
			long classPK = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));

			StringBuilder sb = new StringBuilder(6);

			sb.append("staged-model[contains(@path, '/");
			sb.append(clazz.getName());
			sb.append(StringPool.FORWARD_SLASH);
			sb.append(classPK);
			sb.append(".xml");
			sb.append("')]");

			XPath xPath = SAXReaderUtil.createXPath(sb.toString());

			Element groupElement = getImportDataGroupElement(clazz);

			Element referencedElement = (Element)xPath.selectSingleNode(
				groupElement);

			referencedElements.add(referencedElement);
		}

		return referencedElements;
	}

	public String getRootPath() {
		return ExportImportPathUtil.getRootPath(this);
	}

	public long getScopeGroupId() {
		return _scopeGroupId;
	}

	public String getScopeLayoutUuid() {
		return _scopeLayoutUuid;
	}

	public String getScopeType() {
		return _scopeType;
	}

	public long getSourceCompanyGroupId() {
		return _sourceCompanyGroupId;
	}

	public long getSourceGroupId() {
		return _sourceGroupId;
	}

	public String getSourceLayoutPath(long layoutId) {
		return ExportImportPathUtil.getSourceLayoutPath(this, layoutId);
	}

	public String getSourcePortletPath(String portletId) {
		return ExportImportPathUtil.getSourcePortletPath(this, portletId);
	}

	public String getSourceRootPath() {
		return ExportImportPathUtil.getSourceRootPath(this);
	}

	public Date getStartDate() {
		return _startDate;
	}

	public long getUserId(String userUuid) {
		return _userIdStrategy.getUserId(userUuid);
	}

	public UserIdStrategy getUserIdStrategy() {
		return _userIdStrategy;
	}

	public List<String> getZipEntries() {
		return getZipReader().getEntries();
	}

	public byte[] getZipEntryAsByteArray(String path) {
		if (!Validator.isFilePath(path, false)) {
			return null;
		}

		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsByteArray(path);
	}

	public InputStream getZipEntryAsInputStream(String path) {
		if (!Validator.isFilePath(path, false)) {
			return null;
		}

		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsInputStream(path);
	}

	public Object getZipEntryAsObject(Element element, String path) {
		Object object = fromXML(getZipEntryAsString(path));

		Attribute classNameAttribute = element.attribute("class-name");

		if (classNameAttribute != null) {
			BeanPropertiesUtil.setProperty(
				object, "className", classNameAttribute.getText());
		}

		return object;
	}

	public Object getZipEntryAsObject(String path) {
		return fromXML(getZipEntryAsString(path));
	}

	public String getZipEntryAsString(String path) {
		if (!Validator.isFilePath(path, false)) {
			return null;
		}

		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsString(path);
	}

	public List<String> getZipFolderEntries() {
		return getZipFolderEntries(StringPool.SLASH);
	}

	public List<String> getZipFolderEntries(String path) {
		if (!Validator.isFilePath(path, false)) {
			return null;
		}

		return getZipReader().getFolderEntries(path);
	}

	public ZipReader getZipReader() {
		return _zipReader;
	}

	public ZipWriter getZipWriter() {
		return _zipWriter;
	}

	public boolean hasDateRange() {
		if (_startDate != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasNotUniquePerLayout(String dataKey) {
		return _notUniquePerLayout.contains(dataKey);
	}

	public boolean hasPrimaryKey(Class<?> clazz, String primaryKey) {
		return _primaryKeys.contains(getPrimaryKeyString(clazz, primaryKey));
	}

	public void importClassedModel(
			ClassedModel classedModel, ClassedModel newClassedModel,
			String namespace)
		throws PortalException, SystemException {

		if (!isResourceMain(classedModel)) {
			return;
		}

		Class<?> clazz = classedModel.getModelClass();
		long classPK = getClassPK(classedModel);

		long newClassPK = getClassPK(newClassedModel);

		Map<Long, Long> newPrimaryKeysMap =
			(Map<Long, Long>)getNewPrimaryKeysMap(clazz);

		newPrimaryKeysMap.put(classPK, newClassPK);

		importLocks(clazz, String.valueOf(classPK), String.valueOf(newClassPK));
		importPermissions(clazz, classPK, newClassPK);

		boolean portletMetadataAll = getBooleanParameter(
			namespace, PortletDataHandlerKeys.PORTLET_METADATA_ALL);

		if (portletMetadataAll || getBooleanParameter(namespace, "comments")) {
			importComments(clazz, classPK, newClassPK, getScopeGroupId());
		}

		if (portletMetadataAll || getBooleanParameter(namespace, "ratings")) {
			importRatingsEntries(clazz, classPK, newClassPK);
		}
	}

	public void importComments(
			Class<?> clazz, long classPK, long newClassPK, long groupId)
		throws PortalException, SystemException {

		Map<Long, Long> messagePKs = new HashMap<Long, Long>();
		Map<Long, Long> threadPKs = new HashMap<Long, Long>();

		List<MBMessage> messages = _commentsMap.get(
			getPrimaryKeyString(clazz, classPK));

		if (messages == null) {
			return;
		}

		MBMessage firstMessage = messages.get(0);

		if ((messages.size() == 1) && firstMessage.isRoot()) {
			return;
		}

		long classNameId = PortalUtil.getClassNameId(clazz);

		MBDiscussion discussion = MBDiscussionUtil.fetchByC_C(
			classNameId, newClassPK);

		for (MBMessage message : messages) {
			long userId = getUserId(message.getUserUuid());
			long parentMessageId = MapUtil.getLong(
				messagePKs, message.getParentMessageId(),
				message.getParentMessageId());
			long threadId = MapUtil.getLong(
				threadPKs, message.getThreadId(), message.getThreadId());

			if (message.isRoot()) {
				if (discussion != null) {
					MBThread thread = MBThreadLocalServiceUtil.getThread(
						discussion.getThreadId());

					long rootMessageId = thread.getRootMessageId();

					messagePKs.put(message.getMessageId(), rootMessageId);
					threadPKs.put(message.getThreadId(), thread.getThreadId());
				}
				else if (clazz == Layout.class) {
					MBMessage importedMessage =
						MBMessageLocalServiceUtil.addDiscussionMessage(
							userId, message.getUserName(), groupId,
							clazz.getName(), newClassPK,
							WorkflowConstants.ACTION_PUBLISH);

					messagePKs.put(
						message.getMessageId(), importedMessage.getMessageId());
					threadPKs.put(
						message.getThreadId(), importedMessage.getThreadId());
				}
			}
			else {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setCreateDate(message.getCreateDate());
				serviceContext.setModifiedDate(message.getModifiedDate());
				serviceContext.setScopeGroupId(groupId);

				MBMessage importedMessage = null;

				if (_dataStrategy.equals(
						PortletDataHandlerKeys.DATA_STRATEGY_MIRROR) ||
					_dataStrategy.equals(
						PortletDataHandlerKeys.
							DATA_STRATEGY_MIRROR_OVERWRITE)) {

					MBMessage existingMessage = MBMessageUtil.fetchByUUID_G(
						message.getUuid(), groupId);

					if (existingMessage == null) {
						serviceContext.setUuid(message.getUuid());

						importedMessage =
							MBMessageLocalServiceUtil.addDiscussionMessage(
								userId, message.getUserName(), groupId,
								clazz.getName(), newClassPK, threadId,
								parentMessageId, message.getSubject(),
								message.getBody(), serviceContext);
					}
					else {
						serviceContext.setWorkflowAction(
							WorkflowConstants.ACTION_PUBLISH);

						importedMessage =
							MBMessageLocalServiceUtil.updateDiscussionMessage(
								userId, existingMessage.getMessageId(),
								clazz.getName(), newClassPK,
								message.getSubject(), message.getBody(),
								serviceContext);
					}
				}
				else {
					importedMessage =
						MBMessageLocalServiceUtil.addDiscussionMessage(
							userId, message.getUserName(), groupId,
							clazz.getName(), newClassPK, threadId,
							parentMessageId, message.getSubject(),
							message.getBody(), serviceContext);
				}

				messagePKs.put(
					message.getMessageId(), importedMessage.getMessageId());
				threadPKs.put(
					message.getThreadId(), importedMessage.getThreadId());
			}

			importRatingsEntries(
				MBDiscussion.class, message.getPrimaryKey(),
				messagePKs.get(message.getPrimaryKey()));
		}
	}

	public void importLocks(Class<?> clazz, String key, String newKey)
		throws PortalException, SystemException {

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

	public void importPermissions(Class<?> clazz, long classPK, long newClassPK)
		throws PortalException, SystemException {

		importPermissions(clazz.getName(), classPK, newClassPK);
	}

	public void importPermissions(
			String resourceName, long resourcePK, long newResourcePK)
		throws PortalException, SystemException {

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

	public void importRatingsEntries(
			Class<?> clazz, long classPK, long newClassPK)
		throws PortalException, SystemException {

		List<RatingsEntry> ratingsEntries = _ratingsEntriesMap.get(
			getPrimaryKeyString(clazz, classPK));

		if (ratingsEntries == null) {
			return;
		}

		ServiceContext serviceContext = new ServiceContext();

		for (RatingsEntry ratingsEntry : ratingsEntries) {
			long userId = getUserId(ratingsEntry.getUserUuid());

			serviceContext.setCreateDate(ratingsEntry.getCreateDate());
			serviceContext.setModifiedDate(ratingsEntry.getModifiedDate());

			RatingsEntryLocalServiceUtil.updateEntry(
				userId, clazz.getName(), newClassPK, ratingsEntry.getScore(),
				serviceContext);
		}
	}

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

	public boolean isDataStrategyMirrorWithOverwriting() {
		if (_dataStrategy.equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isPathNotProcessed(String path) {
		return !isPathProcessed(path);
	}

	public boolean isPathProcessed(String path) {
		return addPrimaryKey(String.class, path);
	}

	public boolean isPerformDirectBinaryImport() {
		return MapUtil.getBoolean(
			_parameterMap, PortletDataHandlerKeys.PERFORM_DIRECT_BINARY_IMPORT);
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	/**
	 * @see #addDateRangeCriteria(DynamicQuery, String)
	 */
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

	public void putNotUniquePerLayout(String dataKey) {
		_notUniquePerLayout.add(dataKey);
	}

	public void setClassLoader(ClassLoader classLoader) {
		_xStream.setClassLoader(classLoader);
	}

	public void setExportDataRootElement(Element exportDataRootElement) {
		_exportDataRootElement = exportDataRootElement;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setImportDataRootElement(Element importDataRootElement) {
		_importDataRootElement = importDataRootElement;
	}

	public void setOldPlid(long oldPlid) {
		_oldPlid = oldPlid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public void setPortetDataContextListener(
		PortletDataContextListener portletDataContextListener) {

		_portletDataContextListener = portletDataContextListener;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	public void setScopeGroupId(long scopeGroupId) {
		_scopeGroupId = scopeGroupId;
	}

	public void setScopeLayoutUuid(String scopeLayoutUuid) {
		_scopeLayoutUuid = scopeLayoutUuid;
	}

	public void setScopeType(String scopeType) {
		_scopeType = scopeType;
	}

	public void setSourceCompanyGroupId(long sourceCompanyGroupId) {
		_sourceCompanyGroupId = sourceCompanyGroupId;
	}

	public void setSourceGroupId(long sourceGroupId) {
		_sourceGroupId = sourceGroupId;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public String toXML(Object object) {
		return _xStream.toXML(object);
	}

	protected ServiceContext createServiceContext(
		Element element, String path, ClassedModel classedModel,
		String namespace) {

		Class<?> clazz = classedModel.getModelClass();
		long classPK = getClassPK(classedModel);

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

		boolean portletMetadataAll = getBooleanParameter(
			namespace, PortletDataHandlerKeys.PORTLET_METADATA_ALL);

		if (isResourceMain(classedModel)) {
			if (portletMetadataAll ||
				getBooleanParameter(namespace, "categories")) {

				long[] assetCategoryIds = getAssetCategoryIds(clazz, classPK);

				serviceContext.setAssetCategoryIds(assetCategoryIds);
			}

			if (portletMetadataAll || getBooleanParameter(namespace, "tags")) {
				String[] assetTagNames = getAssetTagNames(clazz, classPK);

				serviceContext.setAssetTagNames(assetTagNames);
			}
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

				serviceContext.setExpandoBridgeAttributes(
					expandoBridgeAttributes);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}

		return serviceContext;
	}

	protected Map<Long, Set<String>> getActionIds(
			long companyId, long[] roleIds, String className, long primKey,
			List<String> actionIds)
		throws PortalException, SystemException {

		if (ResourceBlockLocalServiceUtil.isSupported(className)) {
			return ResourceBlockPermissionLocalServiceUtil.
				getAvailableResourceBlockPermissionActionIds(
					roleIds, className, primKey, actionIds);
		}
		else {
			return ResourcePermissionLocalServiceUtil.
				getAvailableResourcePermissionActionIds(
					companyId, className, ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(primKey), roleIds, actionIds);
		}
	}

	protected long getClassPK(ClassedModel classedModel) {
		if (classedModel instanceof ResourcedModel) {
			ResourcedModel resourcedModel = (ResourcedModel)classedModel;

			return resourcedModel.getResourcePrimKey();
		}
		else {
			return (Long)classedModel.getPrimaryKeyObj();
		}
	}

	protected String getExpandoPath(String path) {
		return ExportImportPathUtil.getExpandoPath(path);
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

	protected List<Element> getReferenceElements(
		StagedModel parentStagedModel, Class<? extends StagedModel> clazz) {

		Element stagedModelElement = getImportDataStagedModelElement(
			parentStagedModel);

		if (stagedModelElement == null) {
			return Collections.emptyList();
		}

		Element referencesElement = stagedModelElement.element("references");

		if (referencesElement == null) {
			return Collections.emptyList();
		}

		XPath xPath = SAXReaderUtil.createXPath(
			"reference[@class-name='"+ clazz.getName() + "']");

		List<Node> nodes = xPath.selectNodes(referencesElement);

		return ListUtil.fromArray(nodes.toArray(new Element[nodes.size()]));
	}

	protected long getUserId(AuditedModel auditedModel) {
		try {
			String userUuid = auditedModel.getUserUuid();

			return getUserId(userUuid);
		}
		catch (Exception e) {
		}

		return 0;
	}

	protected void initXStream() {
		_xStream = new XStream();

		_xStream.alias("BlogsEntry", BlogsEntryImpl.class);
		_xStream.alias("BookmarksFolder", BookmarksFolderImpl.class);
		_xStream.alias("BookmarksEntry", BookmarksEntryImpl.class);
		_xStream.alias("CalEvent", CalEventImpl.class);
		_xStream.alias("DLFolder", DLFolderImpl.class);
		_xStream.alias("DLFileEntry", DLFileEntryImpl.class);
		_xStream.alias("DLFileShortcut", DLFileShortcutImpl.class);
		_xStream.alias("DLFileRank", DLFileRankImpl.class);
		_xStream.alias("JournalArticle", JournalArticleImpl.class);
		_xStream.alias("JournalFeed", JournalFeedImpl.class);
		_xStream.alias("JournalStructure", JournalStructureImpl.class);
		_xStream.alias("JournalTemplate", JournalTemplateImpl.class);
		_xStream.alias("Lock", LockImpl.class);
		_xStream.alias("MBBan", MBBanImpl.class);
		_xStream.alias("MBCategory", MBCategoryImpl.class);
		_xStream.alias("MBMessage", MBMessageImpl.class);
		_xStream.alias("MBThreadFlag", MBThreadFlagImpl.class);
		_xStream.alias("PollsQuestion", PollsQuestionImpl.class);
		_xStream.alias("PollsChoice", PollsChoiceImpl.class);
		_xStream.alias("PollsVote", PollsVoteImpl.class);
		_xStream.alias("RatingsEntry", RatingsEntryImpl.class);
		_xStream.alias("WikiNode", WikiNodeImpl.class);
		_xStream.alias("WikiPage", WikiPageImpl.class);

		_xStream.omitField(HashMap.class, "cache_bitmask");
	}

	protected boolean isResourceMain(ClassedModel classedModel) {
		if (classedModel instanceof ResourcedModel) {
			ResourcedModel resourcedModel = (ResourcedModel)classedModel;

			return resourcedModel.isResourceMain();
		}

		return true;
	}

	protected void validateDateRange(Date startDate, Date endDate)
		throws PortletDataException {

		if ((startDate == null) && (endDate != null)) {
			throw new PortletDataException(
				PortletDataException.END_DATE_IS_MISSING_START_DATE);
		}
		else if ((startDate != null) && (endDate == null)) {
			throw new PortletDataException(
				PortletDataException.START_DATE_IS_MISSING_END_DATE);
		}

		if (startDate != null) {
			if (startDate.after(endDate) || startDate.equals(endDate)) {
				throw new PortletDataException(
					PortletDataException.START_DATE_AFTER_END_DATE);
			}

			Date now = new Date();

			if (startDate.after(now)) {
				throw new PortletDataException(
					PortletDataException.FUTURE_START_DATE);
			}

			if (endDate.after(now)) {
				throw new PortletDataException(
					PortletDataException.FUTURE_END_DATE);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletDataContextImpl.class);

	private Map<String, long[]> _assetCategoryIdsMap =
		new HashMap<String, long[]>();
	private Map<String, String[]> _assetCategoryUuidsMap =
		new HashMap<String, String[]>();
	private Map<String, String[]> _assetLinkUuidsMap =
		new HashMap<String, String[]>();
	private Map<String, String[]> _assetTagNamesMap =
		new HashMap<String, String[]>();
	private Map<String, List<MBMessage>> _commentsMap =
		new HashMap<String, List<MBMessage>>();
	private long _companyGroupId;
	private long _companyId;
	private String _dataStrategy;
	private Date _endDate;
	private Map<String, List<ExpandoColumn>> _expandoColumnsMap =
		new HashMap<String, List<ExpandoColumn>>();
	private Element _exportDataRootElement;
	private long _groupId;
	private Element _importDataRootElement;
	private Map<String, Lock> _locksMap = new HashMap<String, Lock>();
	private Map<String, Map<?, ?>> _newPrimaryKeysMaps =
		new HashMap<String, Map<?, ?>>();
	private Set<String> _notUniquePerLayout = new HashSet<String>();
	private long _oldPlid;
	private Map<String, String[]> _parameterMap;
	private Map<String, List<KeyValuePair>> _permissionsMap =
		new HashMap<String, List<KeyValuePair>>();
	private long _plid;
	private PortletDataContextListener _portletDataContextListener;
	private Set<String> _primaryKeys;
	private boolean _privateLayout;
	private Map<String, List<RatingsEntry>> _ratingsEntriesMap =
		new HashMap<String, List<RatingsEntry>>();
	private long _scopeGroupId;
	private String _scopeLayoutUuid;
	private String _scopeType;
	private long _sourceCompanyGroupId;
	private long _sourceGroupId;
	private Date _startDate;
	private UserIdStrategy _userIdStrategy;
	private XStream _xStream;
	private ZipReader _zipReader;
	private ZipWriter _zipWriter;

}