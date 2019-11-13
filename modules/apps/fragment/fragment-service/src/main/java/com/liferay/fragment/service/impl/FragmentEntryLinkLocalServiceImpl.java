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

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.base.FragmentEntryLinkLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "model.class.name=com.liferay.fragment.model.FragmentEntryLink",
	service = AopService.class
)
public class FragmentEntryLinkLocalServiceImpl
	extends FragmentEntryLinkLocalServiceBaseImpl {

	@Override
	public FragmentEntryLink addFragmentEntryLink(
			long userId, long groupId, long originalFragmentEntryLinkId,
			long fragmentEntryId, long classNameId, long classPK, String css,
			String html, String js, String configuration, String editableValues,
			String namespace, int position, String rendererKey,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		_classNameLocalService.getClassName(classNameId);

		long fragmentEntryLinkId = counterLocalService.increment();

		FragmentEntryLink fragmentEntryLink =
			fragmentEntryLinkPersistence.create(fragmentEntryLinkId);

		fragmentEntryLink.setUuid(serviceContext.getUuid());
		fragmentEntryLink.setGroupId(groupId);
		fragmentEntryLink.setCompanyId(user.getCompanyId());
		fragmentEntryLink.setUserId(user.getUserId());
		fragmentEntryLink.setUserName(user.getFullName());
		fragmentEntryLink.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		fragmentEntryLink.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		fragmentEntryLink.setOriginalFragmentEntryLinkId(
			originalFragmentEntryLinkId);
		fragmentEntryLink.setFragmentEntryId(fragmentEntryId);
		fragmentEntryLink.setClassNameId(classNameId);
		fragmentEntryLink.setClassPK(classPK);
		fragmentEntryLink.setCss(css);

		html = _replaceResources(fragmentEntryId, html);

		fragmentEntryLink.setHtml(html);

		fragmentEntryLink.setJs(js);
		fragmentEntryLink.setConfiguration(configuration);

		if (Validator.isNull(editableValues)) {
			JSONObject jsonObject =
				_fragmentEntryProcessorRegistry.
					getDefaultEditableValuesJSONObject(html, configuration);

			editableValues = jsonObject.toString();
		}

		fragmentEntryLink.setEditableValues(editableValues);

		if (Validator.isNull(namespace)) {
			namespace = StringUtil.randomId();
		}

		fragmentEntryLink.setNamespace(namespace);

		fragmentEntryLink.setPosition(position);
		fragmentEntryLink.setRendererKey(rendererKey);
		fragmentEntryLink.setLastPropagationDate(
			serviceContext.getCreateDate(new Date()));

		fragmentEntryLinkPersistence.update(fragmentEntryLink);

		return fragmentEntryLink;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public FragmentEntryLink deleteFragmentEntryLink(
		FragmentEntryLink fragmentEntryLink) {

		// Fragment entry link

		fragmentEntryLinkPersistence.remove(fragmentEntryLink);

		// Fragment entry processor registry

		_fragmentEntryProcessorRegistry.deleteFragmentEntryLinkData(
			fragmentEntryLink);

		return fragmentEntryLink;
	}

	@Override
	public FragmentEntryLink deleteFragmentEntryLink(long fragmentEntryLinkId)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink =
			fragmentEntryLinkPersistence.findByPrimaryKey(fragmentEntryLinkId);

		return fragmentEntryLinkLocalService.deleteFragmentEntryLink(
			fragmentEntryLink);
	}

	@Override
	public void deleteFragmentEntryLinks(long groupId) {
		List<FragmentEntryLink> fragmentEntryLinks =
			fragmentEntryLinkPersistence.findByGroupId(groupId);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			fragmentEntryLinkLocalService.deleteFragmentEntryLink(
				fragmentEntryLink);
		}
	}

	@Override
	public void deleteFragmentEntryLinks(long[] fragmentEntryLinkIds)
		throws PortalException {

		for (long fragmentEntryLinkId : fragmentEntryLinkIds) {
			fragmentEntryLinkLocalService.deleteFragmentEntryLink(
				fragmentEntryLinkId);
		}
	}

	@Override
	public List<FragmentEntryLink>
		deleteLayoutPageTemplateEntryFragmentEntryLinks(
			long groupId, long classNameId, long classPK) {

		List<FragmentEntryLink> fragmentEntryLinks = getFragmentEntryLinks(
			groupId, classNameId, classPK);

		if (ListUtil.isEmpty(fragmentEntryLinks)) {
			return Collections.emptyList();
		}

		List<FragmentEntryLink> deletedFragmentEntryLinks = new ArrayList<>();

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			fragmentEntryLinkLocalService.deleteFragmentEntryLink(
				fragmentEntryLink);

			deletedFragmentEntryLinks.add(fragmentEntryLink);
		}

		return deletedFragmentEntryLinks;
	}

	@Override
	public int getClassedModelFragmentEntryLinksCount(
		long groupId, long classNameId, long classPK) {

		return fragmentEntryLinkPersistence.countByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public List<FragmentEntryLink> getFragmentEntryLinks(
		long groupId, long fragmentEntryId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return fragmentEntryLinkFinder.findByG_F(
			groupId, fragmentEntryId, start, end, orderByComparator);
	}

	@Override
	public List<FragmentEntryLink> getFragmentEntryLinks(
		long groupId, long classNameId, long classPK) {

		return fragmentEntryLinkPersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public List<FragmentEntryLink> getFragmentEntryLinks(
		long groupId, long fragmentEntryId, long classNameId,
		int layoutPageTemplateType, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return fragmentEntryLinkFinder.findByG_F_C_L(
			groupId, fragmentEntryId, classNameId, layoutPageTemplateType,
			start, end, orderByComparator);
	}

	@Override
	public List<FragmentEntryLink> getFragmentEntryLinks(
		long groupId, long fragmentEntryId, long classNameId, int start,
		int end, OrderByComparator<FragmentEntryLink> orderByComparator) {

		return fragmentEntryLinkFinder.findByG_F_C(
			groupId, fragmentEntryId, classNameId, start, end,
			orderByComparator);
	}

	@Override
	public List<FragmentEntryLink> getFragmentEntryLinks(String rendererKey) {
		return fragmentEntryLinkPersistence.findByRendererKey(rendererKey);
	}

	@Override
	public List<FragmentEntryLink> getFragmentEntryLinksByFragmentEntryId(
		long fragmentEntryId) {

		return fragmentEntryLinkPersistence.findByFragmentEntryId(
			fragmentEntryId);
	}

	@Override
	public int getFragmentEntryLinksCount(long groupId, long fragmentEntryId) {
		return fragmentEntryLinkFinder.countByG_F(groupId, fragmentEntryId);
	}

	@Override
	public int getFragmentEntryLinksCount(
		long groupId, long fragmentEntryId, long classNameId) {

		return fragmentEntryLinkFinder.countByG_F_C(
			groupId, fragmentEntryId, classNameId);
	}

	@Override
	public int getFragmentEntryLinksCount(
		long groupId, long fragmentEntryId, long classNameId,
		int layoutPageTemplateType) {

		return fragmentEntryLinkFinder.countByG_F_C_L(
			groupId, fragmentEntryId, classNameId, layoutPageTemplateType);
	}

	@Override
	public int getFragmentEntryLinksCountByFragmentEntryId(
		long fragmentEntryId) {

		return fragmentEntryLinkPersistence.countByFragmentEntryId(
			fragmentEntryId);
	}

	@Override
	public void updateClassedModel(long classNameId, long classPK)
		throws PortalException {

		if (classNameId != _portal.getClassNameId(Layout.class)) {
			return;
		}

		Layout layout = _layoutLocalService.fetchLayout(classPK);

		if (layout == null) {
			return;
		}

		_layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	@Override
	public FragmentEntryLink updateFragmentEntryLink(
			long fragmentEntryLinkId, int position)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink = fetchFragmentEntryLink(
			fragmentEntryLinkId);

		fragmentEntryLink.setPosition(position);

		fragmentEntryLinkPersistence.update(fragmentEntryLink);

		return fragmentEntryLink;
	}

	@Override
	public FragmentEntryLink updateFragmentEntryLink(
			long userId, long fragmentEntryLinkId,
			long originalFragmentEntryLinkId, long fragmentEntryId,
			long classNameId, long classPK, String css, String html, String js,
			String configuration, String editableValues, String namespace,
			int position, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		_classNameLocalService.getClassName(classNameId);

		FragmentEntryLink fragmentEntryLink = fetchFragmentEntryLink(
			fragmentEntryLinkId);

		fragmentEntryLink.setUserId(user.getUserId());
		fragmentEntryLink.setUserName(user.getFullName());
		fragmentEntryLink.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		fragmentEntryLink.setOriginalFragmentEntryLinkId(
			originalFragmentEntryLinkId);
		fragmentEntryLink.setFragmentEntryId(fragmentEntryId);
		fragmentEntryLink.setClassNameId(classNameId);
		fragmentEntryLink.setClassPK(classPK);
		fragmentEntryLink.setCss(css);
		fragmentEntryLink.setHtml(html);
		fragmentEntryLink.setJs(js);
		fragmentEntryLink.setConfiguration(configuration);
		fragmentEntryLink.setEditableValues(editableValues);

		if (Validator.isNotNull(namespace)) {
			fragmentEntryLink.setNamespace(namespace);
		}

		fragmentEntryLink.setPosition(position);

		fragmentEntryLinkPersistence.update(fragmentEntryLink);

		return fragmentEntryLink;
	}

	@Override
	public FragmentEntryLink updateFragmentEntryLink(
			long fragmentEntryLinkId, String editableValues)
		throws PortalException {

		return updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues, true);
	}

	@Override
	public FragmentEntryLink updateFragmentEntryLink(
			long fragmentEntryLinkId, String editableValues,
			boolean updateClassedModel)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink = fetchFragmentEntryLink(
			fragmentEntryLinkId);

		fragmentEntryLink.setEditableValues(editableValues);

		if (updateClassedModel) {
			updateClassedModel(
				fragmentEntryLink.getClassNameId(),
				fragmentEntryLink.getClassPK());
		}

		fragmentEntryLinkPersistence.update(fragmentEntryLink);

		return fragmentEntryLink;
	}

	@Override
	public void updateFragmentEntryLinks(
			long userId, long groupId, long classNameId, long classPK,
			long[] fragmentEntryIds, String editableValues,
			ServiceContext serviceContext)
		throws PortalException {

		deleteLayoutPageTemplateEntryFragmentEntryLinks(
			groupId, classNameId, classPK);

		if (ArrayUtil.isEmpty(fragmentEntryIds)) {
			return;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(editableValues);

		int position = 0;

		for (long fragmentId : fragmentEntryIds) {
			FragmentEntry fragmentEntry =
				fragmentEntryPersistence.fetchByPrimaryKey(fragmentId);

			addFragmentEntryLink(
				userId, groupId, 0, fragmentEntry.getFragmentEntryId(),
				classNameId, classPK, fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				fragmentEntry.getConfiguration(),
				jsonObject.getString(String.valueOf(position)),
				StringPool.BLANK, position++, null, serviceContext);
		}
	}

	@Override
	public void updateFragmentEntryLinks(
			Map<Long, String> fragmentEntryLinksEditableValuesMap)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink = null;

		for (Map.Entry<Long, String> entry :
				fragmentEntryLinksEditableValuesMap.entrySet()) {

			fragmentEntryLink = fetchFragmentEntryLink(entry.getKey());

			fragmentEntryLink.setEditableValues(entry.getValue());

			fragmentEntryLinkPersistence.update(fragmentEntryLink);
		}

		if (fragmentEntryLink != null) {
			updateClassedModel(
				fragmentEntryLink.getClassNameId(),
				fragmentEntryLink.getClassPK());
		}
	}

	@Override
	public void updateLatestChanges(long fragmentEntryLinkId)
		throws PortalException {

		FragmentEntryLink oldFragmentEntryLink =
			fragmentEntryLinkPersistence.findByPrimaryKey(fragmentEntryLinkId);

		FragmentEntry fragmentEntry = fragmentEntryPersistence.findByPrimaryKey(
			oldFragmentEntryLink.getFragmentEntryId());

		List<FragmentEntryLink> fragmentEntryLinks =
			fragmentEntryLinkPersistence.findByG_F_C_C(
				oldFragmentEntryLink.getGroupId(),
				oldFragmentEntryLink.getFragmentEntryId(),
				oldFragmentEntryLink.getClassNameId(),
				oldFragmentEntryLink.getClassPK());

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			fragmentEntryLink.setCss(fragmentEntry.getCss());
			fragmentEntryLink.setHtml(fragmentEntry.getHtml());
			fragmentEntryLink.setJs(fragmentEntry.getJs());
			fragmentEntryLink.setConfiguration(
				fragmentEntry.getConfiguration());
			fragmentEntryLink.setLastPropagationDate(new Date());

			fragmentEntryLinkPersistence.update(fragmentEntryLink);
		}
	}

	private String _replaceResources(long fragmentEntryId, String html)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryPersistence.fetchByPrimaryKey(fragmentEntryId);

		if (fragmentEntry == null) {
			return html;
		}

		FragmentCollection fragmentCollection =
			fragmentCollectionPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentCollectionId());

		Matcher matcher = _pattern.matcher(html);

		while (matcher.find()) {
			FileEntry fileEntry =
				PortletFileRepositoryUtil.fetchPortletFileEntry(
					fragmentEntry.getGroupId(),
					fragmentCollection.getResourcesFolderId(),
					matcher.group(1));

			String fileEntryURL = StringPool.BLANK;

			if (fileEntry != null) {
				fileEntryURL = _dlURLHelper.getDownloadURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false);
			}

			html = StringUtil.replace(html, matcher.group(), fileEntryURL);
		}

		return html;
	}

	private static final Pattern _pattern = Pattern.compile(
		"\\[resources:(.+?)\\]");

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}