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

package com.liferay.fragment.service.impl;

import com.liferay.fragment.configuration.FragmentServiceConfiguration;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.exception.DuplicateFragmentEntryKeyException;
import com.liferay.fragment.exception.FragmentEntryNameException;
import com.liferay.fragment.exception.RequiredFragmentEntryException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.base.FragmentEntryLocalServiceBaseImpl;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "model.class.name=com.liferay.fragment.model.FragmentEntry",
	service = AopService.class
)
public class FragmentEntryLocalServiceImpl
	extends FragmentEntryLocalServiceBaseImpl {

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addFragmentEntry(long, long, long, String, String, String,
	 *             String, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			int type, int status, ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, StringPool.BLANK, name, 0,
			type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addFragmentEntry(long, long, long, String, String, String,
	 *             String, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			int status, ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, StringPool.BLANK, name, 0,
			FragmentConstants.TYPE_SECTION, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addFragmentEntry(long, long, long, String, String, String,
	 *             String, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			long previewFileEntryId, int type, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, StringPool.BLANK, name,
			previewFileEntryId, type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addFragmentEntry(long, long, long, String, String, String,
	 *             String, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			long previewFileEntryId, int status, ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, StringPool.BLANK, name,
			previewFileEntryId, FragmentConstants.TYPE_SECTION, status,
			serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addFragmentEntry(long, long, long, String, String, String,
	 *             String, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, int type, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name, 0,
			type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addFragmentEntry(long, long, long, String, String, String,
	 *             String, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name, 0,
			FragmentConstants.TYPE_SECTION, status, serviceContext);
	}

	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, long previewFileEntryId,
			int type, int status, ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, previewFileEntryId, type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addFragmentEntry(long, long, long, String, String, String,
	 *             String, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, long previewFileEntryId,
			int status, ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name,
			previewFileEntryId, FragmentConstants.TYPE_SECTION, status,
			serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addFragmentEntry(long, long, long, String, String, String,
	 *             String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			String css, String html, String js, int type, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, StringPool.BLANK, name, css,
			html, js, type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addFragmentEntry(long, long, long, String, String, String,
	 *             String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			String css, String html, String js, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, StringPool.BLANK, name, css,
			html, js, FragmentConstants.TYPE_SECTION, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addFragmentEntry(long, long, long, String, String, String,
	 *             String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			String css, String html, String js, long previewFileEntryId,
			int type, int status, ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, StringPool.BLANK, name, css,
			html, js, previewFileEntryId, type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addFragmentEntry(long, long, long, String, String, String,
	 *             String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			String css, String html, String js, long previewFileEntryId,
			int status, ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, StringPool.BLANK, name, css,
			html, js, previewFileEntryId, FragmentConstants.TYPE_SECTION,
			status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addFragmentEntry(long, long, long, String, String, String,
	 *             String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, String css, String html,
			String js, int type, int status, ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name, css,
			html, js, 0, type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addFragmentEntry(long, long, long, String, String, String,
	 *             String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, String css, String html,
			String js, int status, ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name, css,
			html, js, 0, FragmentConstants.TYPE_SECTION, status,
			serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addFragmentEntry(long, long, long, String, String, String,
	 *             String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, String css, String html,
			String js, long previewFileEntryId, int type, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name, css,
			html, js, null, previewFileEntryId, type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addFragmentEntry(long, long, long, String, String, String,
	 *             String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, String css, String html,
			String js, long previewFileEntryId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name, css,
			html, js, previewFileEntryId, FragmentConstants.TYPE_SECTION,
			status, serviceContext);
	}

	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, String css, String html,
			String js, String configuration, long previewFileEntryId, int type,
			int status, ServiceContext serviceContext)
		throws PortalException {

		// Fragment entry

		User user = userLocalService.getUser(userId);

		if (Validator.isNull(fragmentEntryKey)) {
			fragmentEntryKey = generateFragmentEntryKey(groupId, name);
		}

		fragmentEntryKey = _getFragmentEntryKey(fragmentEntryKey);

		validate(name);
		validateFragmentEntryKey(groupId, fragmentEntryKey);

		_fragmentEntryValidator.validateConfiguration(configuration);

		if (WorkflowConstants.STATUS_APPROVED == status) {
			validateContent(html, configuration);
		}

		long fragmentEntryId = counterLocalService.increment();

		FragmentEntry fragmentEntry = fragmentEntryPersistence.create(
			fragmentEntryId);

		fragmentEntry.setUuid(serviceContext.getUuid());
		fragmentEntry.setGroupId(groupId);
		fragmentEntry.setCompanyId(user.getCompanyId());
		fragmentEntry.setUserId(user.getUserId());
		fragmentEntry.setUserName(user.getFullName());
		fragmentEntry.setCreateDate(serviceContext.getCreateDate(new Date()));
		fragmentEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		fragmentEntry.setFragmentCollectionId(fragmentCollectionId);
		fragmentEntry.setFragmentEntryKey(fragmentEntryKey);
		fragmentEntry.setName(name);
		fragmentEntry.setCss(css);
		fragmentEntry.setHtml(html);
		fragmentEntry.setJs(js);
		fragmentEntry.setConfiguration(configuration);
		fragmentEntry.setPreviewFileEntryId(previewFileEntryId);
		fragmentEntry.setType(type);
		fragmentEntry.setStatus(status);
		fragmentEntry.setStatusByUserId(userId);
		fragmentEntry.setStatusByUserName(user.getFullName());
		fragmentEntry.setStatusDate(new Date());

		fragmentEntryPersistence.update(fragmentEntry);

		return fragmentEntry;
	}

	@Override
	public FragmentEntry copyFragmentEntry(
			long userId, long groupId, long fragmentEntryId,
			long fragmentCollectionId, ServiceContext serviceContext)
		throws PortalException {

		FragmentEntry fragmentEntry = getFragmentEntry(fragmentEntryId);

		StringBundler sb = new StringBundler(5);

		sb.append(fragmentEntry.getName());
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(LanguageUtil.get(LocaleUtil.getMostRelevantLocale(), "copy"));
		sb.append(StringPool.CLOSE_PARENTHESIS);

		long originalFragmentCollectionId =
			fragmentEntry.getFragmentCollectionId();

		FragmentEntry copyFragmentEntry = addFragmentEntry(
			userId, groupId, fragmentCollectionId, null, sb.toString(),
			fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
			fragmentEntry.getPreviewFileEntryId(), fragmentEntry.getType(),
			fragmentEntry.getStatus(), serviceContext);

		_copyFragmentEntryResources(
			fragmentEntry, originalFragmentCollectionId, fragmentCollectionId);

		return copyFragmentEntry;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public FragmentEntry deleteFragmentEntry(FragmentEntry fragmentEntry)
		throws PortalException {

		// Fragment entry

		long fragmentEntryLinkCount =
			fragmentEntryLinkPersistence.countByFragmentEntryId(
				fragmentEntry.getFragmentEntryId());

		if (fragmentEntryLinkCount > 0) {
			throw new RequiredFragmentEntryException();
		}

		// Resources

		resourceLocalService.deleteResource(
			fragmentEntry.getCompanyId(), FragmentEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			fragmentEntry.getFragmentEntryId());

		// Preview image

		if (fragmentEntry.getPreviewFileEntryId() > 0) {
			PortletFileRepositoryUtil.deletePortletFileEntry(
				fragmentEntry.getPreviewFileEntryId());
		}

		fragmentEntryPersistence.remove(fragmentEntry);

		return fragmentEntry;
	}

	@Override
	public FragmentEntry deleteFragmentEntry(long fragmentEntryId)
		throws PortalException {

		return fragmentEntryLocalService.deleteFragmentEntry(
			getFragmentEntry(fragmentEntryId));
	}

	@Override
	public FragmentEntry fetchFragmentEntry(long fragmentEntryId) {
		return fragmentEntryPersistence.fetchByPrimaryKey(fragmentEntryId);
	}

	@Override
	public FragmentEntry fetchFragmentEntry(
		long groupId, String fragmentEntryKey) {

		return fragmentEntryPersistence.fetchByG_FEK(
			groupId, _getFragmentEntryKey(fragmentEntryKey));
	}

	@Override
	public String generateFragmentEntryKey(long groupId, String name) {
		String fragmentEntryKey = _getFragmentEntryKey(name);

		fragmentEntryKey = StringUtil.replace(
			fragmentEntryKey, CharPool.SPACE, CharPool.DASH);

		String curFragmentEntryKey = fragmentEntryKey;

		int count = 0;

		while (true) {
			FragmentEntry fragmentEntry = fragmentEntryPersistence.fetchByG_FEK(
				groupId, curFragmentEntryKey);

			if (fragmentEntry == null) {
				return curFragmentEntryKey;
			}

			curFragmentEntryKey = fragmentEntryKey + CharPool.DASH + count++;
		}
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(long fragmentCollectionId) {
		return fragmentEntryPersistence.findByFragmentCollectionId(
			fragmentCollectionId);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long fragmentCollectionId, int start, int end) {

		return fragmentEntryPersistence.findByFragmentCollectionId(
			fragmentCollectionId, start, end);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int status) {

		return fragmentEntryPersistence.findByG_FCI_S(
			groupId, fragmentCollectionId, status);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return fragmentEntryPersistence.findByG_FCI(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator) {

		if (Validator.isNull(name)) {
			return fragmentEntryPersistence.findByG_FCI(
				groupId, fragmentCollectionId, start, end, orderByComparator);
		}

		return fragmentEntryPersistence.findByG_FCI_LikeN(
			groupId, fragmentCollectionId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0], start,
			end, orderByComparator);
	}

	@Override
	public int getFragmentEntriesCount(long fragmentCollectionId) {
		return fragmentEntryPersistence.countByFragmentCollectionId(
			fragmentCollectionId);
	}

	@Override
	public String[] getTempFileNames(
			long userId, long groupId, String folderName)
		throws PortalException {

		return TempFileEntryUtil.getTempFileNames(groupId, userId, folderName);
	}

	@Override
	public FragmentEntry moveFragmentEntry(
			long fragmentEntryId, long fragmentCollectionId)
		throws PortalException {

		FragmentEntry fragmentEntry = fragmentEntryPersistence.findByPrimaryKey(
			fragmentEntryId);

		if (fragmentEntry.getFragmentCollectionId() == fragmentCollectionId) {
			return fragmentEntry;
		}

		long originalFragmentCollectionId =
			fragmentEntry.getFragmentCollectionId();

		fragmentEntry.setFragmentCollectionId(fragmentCollectionId);

		_copyFragmentEntryResources(
			fragmentEntry, originalFragmentCollectionId, fragmentCollectionId);

		return fragmentEntryPersistence.update(fragmentEntry);
	}

	@Override
	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, long previewFileEntryId)
		throws PortalException {

		FragmentEntry fragmentEntry = fragmentEntryPersistence.findByPrimaryKey(
			fragmentEntryId);

		fragmentEntry.setModifiedDate(new Date());
		fragmentEntry.setPreviewFileEntryId(previewFileEntryId);

		fragmentEntryPersistence.update(fragmentEntry);

		return fragmentEntry;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #updateFragmentEntry(long, long, String, String, String,
	 *             String, String, int)}
	 */
	@Deprecated
	@Override
	public FragmentEntry updateFragmentEntry(
			long userId, long fragmentEntryId, String name, String css,
			String html, String js, int status)
		throws PortalException {

		FragmentEntry fragmentEntry = fragmentEntryPersistence.findByPrimaryKey(
			fragmentEntryId);

		return updateFragmentEntry(
			userId, fragmentEntryId, name, css, html, js,
			fragmentEntry.getConfiguration(),
			fragmentEntry.getPreviewFileEntryId(), status);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #updateFragmentEntry(long, long, String, String, String,
	 *             String, String, long, int)}
	 */
	@Deprecated
	@Override
	public FragmentEntry updateFragmentEntry(
			long userId, long fragmentEntryId, String name, String css,
			String html, String js, long previewFileEntryId, int status)
		throws PortalException {

		FragmentEntry fragmentEntry = fragmentEntryPersistence.findByPrimaryKey(
			fragmentEntryId);

		return updateFragmentEntry(
			userId, fragmentEntryId, name, css, html, js,
			fragmentEntry.getConfiguration(), previewFileEntryId, status);
	}

	@Override
	public FragmentEntry updateFragmentEntry(
			long userId, long fragmentEntryId, String name, String css,
			String html, String js, String configuration, int status)
		throws PortalException {

		FragmentEntry fragmentEntry = fragmentEntryPersistence.findByPrimaryKey(
			fragmentEntryId);

		return updateFragmentEntry(
			userId, fragmentEntryId, name, css, html, js, configuration,
			fragmentEntry.getPreviewFileEntryId(), status);
	}

	@Override
	public FragmentEntry updateFragmentEntry(
			long userId, long fragmentEntryId, String name, String css,
			String html, String js, String configuration,
			long previewFileEntryId, int status)
		throws PortalException {

		FragmentEntry fragmentEntry = fragmentEntryPersistence.findByPrimaryKey(
			fragmentEntryId);

		validate(name);

		_fragmentEntryValidator.validateConfiguration(configuration);

		if (WorkflowConstants.STATUS_APPROVED == status) {
			validateContent(html, configuration);
		}

		User user = userLocalService.getUser(userId);

		fragmentEntry.setModifiedDate(new Date());
		fragmentEntry.setName(name);
		fragmentEntry.setCss(css);
		fragmentEntry.setHtml(html);
		fragmentEntry.setJs(js);
		fragmentEntry.setConfiguration(configuration);
		fragmentEntry.setPreviewFileEntryId(previewFileEntryId);
		fragmentEntry.setStatus(status);
		fragmentEntry.setStatusByUserId(userId);
		fragmentEntry.setStatusByUserName(user.getFullName());
		fragmentEntry.setStatusDate(new Date());

		fragmentEntryPersistence.update(fragmentEntry);

		FragmentServiceConfiguration fragmentServiceConfiguration =
			_configurationProvider.getCompanyConfiguration(
				FragmentServiceConfiguration.class,
				fragmentEntry.getCompanyId());

		if (fragmentServiceConfiguration.propagateChanges()) {
			_propagateChanges(fragmentEntryId);
		}

		return fragmentEntry;
	}

	@Override
	public FragmentEntry updateFragmentEntry(long fragmentEntryId, String name)
		throws PortalException {

		FragmentEntry fragmentEntry = fragmentEntryPersistence.findByPrimaryKey(
			fragmentEntryId);

		if (Objects.equals(fragmentEntry.getName(), name)) {
			return fragmentEntry;
		}

		validate(name);

		fragmentEntry.setName(name);

		return fragmentEntryPersistence.update(fragmentEntry);
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new FragmentEntryNameException("Name must not be null");
		}

		int nameMaxLength = ModelHintsUtil.getMaxLength(
			FragmentEntry.class.getName(), "name");

		if (name.length() > nameMaxLength) {
			throw new FragmentEntryNameException(
				"Maximum length of name exceeded");
		}
	}

	protected void validateContent(String html, String configuration)
		throws PortalException {

		_fragmentEntryProcessorRegistry.validateFragmentEntryHTML(
			html, configuration);
	}

	protected void validateFragmentEntryKey(
			long groupId, String fragmentEntryKey)
		throws PortalException {

		fragmentEntryKey = _getFragmentEntryKey(fragmentEntryKey);

		FragmentEntry fragmentEntry = fragmentEntryPersistence.fetchByG_FEK(
			groupId, fragmentEntryKey);

		if (fragmentEntry != null) {
			throw new DuplicateFragmentEntryKeyException();
		}
	}

	private void _copyFragmentEntryResources(
			FragmentEntry fragmentEntry, long sourceFragmentCollectionId,
			long targetFragmentCollectionId)
		throws PortalException {

		if (sourceFragmentCollectionId == targetFragmentCollectionId) {
			return;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Set<FileEntry> fileEntries = _getFileEntries(
			sourceFragmentCollectionId, fragmentEntry);

		if ((serviceContext == null) || fileEntries.isEmpty()) {
			return;
		}

		FragmentCollection targetFragmentCollection =
			fragmentCollectionPersistence.fetchByPrimaryKey(
				targetFragmentCollectionId);

		try {
			for (FileEntry fileEntry : fileEntries) {
				FileEntry existingFileEntry =
					PortletFileRepositoryUtil.fetchPortletFileEntry(
						fragmentEntry.getGroupId(),
						targetFragmentCollection.getResourcesFolderId(),
						fileEntry.getFileName());

				if (existingFileEntry == null) {
					PortletFileRepositoryUtil.addPortletFileEntry(
						serviceContext.getScopeGroupId(),
						serviceContext.getUserId(),
						FragmentCollection.class.getName(),
						targetFragmentCollection.getFragmentCollectionId(),
						FragmentPortletKeys.FRAGMENT,
						targetFragmentCollection.getResourcesFolderId(),
						fileEntry.getContentStream(), fileEntry.getFileName(),
						fileEntry.getMimeType(), false);
				}
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	private Set<FileEntry> _getFileEntries(
			long fragmentCollectionId, FragmentEntry fragmentEntry)
		throws PortalException {

		Set<FileEntry> fileEntries = new HashSet<>();

		FragmentCollection fragmentCollection =
			fragmentCollectionPersistence.fetchByPrimaryKey(
				fragmentCollectionId);

		Matcher matcher = _pattern.matcher(fragmentEntry.getHtml());

		while (matcher.find()) {
			FileEntry fileEntry =
				PortletFileRepositoryUtil.fetchPortletFileEntry(
					fragmentEntry.getGroupId(),
					fragmentCollection.getResourcesFolderId(),
					matcher.group(1));

			if (fileEntry != null) {
				fileEntries.add(fileEntry);
			}
		}

		return fileEntries;
	}

	private String _getFragmentEntryKey(String fragmentEntryKey) {
		if (fragmentEntryKey != null) {
			fragmentEntryKey = fragmentEntryKey.trim();

			return StringUtil.toLowerCase(fragmentEntryKey);
		}

		return StringPool.BLANK;
	}

	private void _propagateChanges(long fragmentEntryId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_fragmentEntryLinkLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property fragmentEntryIdProperty = PropertyFactoryUtil.forName(
					"fragmentEntryId");

				dynamicQuery.add(fragmentEntryIdProperty.eq(fragmentEntryId));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(FragmentEntryLink fragmentEntryLink) ->
				_fragmentEntryLinkLocalService.updateLatestChanges(
					fragmentEntryLink.getFragmentEntryLinkId()));

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLocalServiceImpl.class);

	private static final Pattern _pattern = Pattern.compile(
		"\\[resources:(.+?)\\]");

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private FragmentEntryValidator _fragmentEntryValidator;

}