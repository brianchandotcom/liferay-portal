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

package com.liferay.blogs.internal.atom;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.atom.AtomPager;
import com.liferay.portal.atom.AtomUtil;
import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.atom.AtomEntryContent;
import com.liferay.portal.kernel.atom.AtomRequestContext;
import com.liferay.portal.kernel.atom.BaseAtomCollectionAdapter;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Spasic
 */
@Component(
	property = "model.class.name=com.liferay.blogs.model.BlogsEntry",
	service = AtomCollectionAdapter.class
)
public class BlogsEntryAtomCollectionAdapter
	extends BaseAtomCollectionAdapter<BlogsEntry> {

	@Override
	public String getCollectionName() {
		return _COLLECTION_NAME;
	}

	@Override
	public List<String> getEntryAuthors(BlogsEntry blogsEntry) {
		return ListUtil.fromArray(blogsEntry.getUserName());
	}

	@Override
	public AtomEntryContent getEntryContent(
		BlogsEntry blogsEntry, AtomRequestContext atomRequestContext) {

		return new AtomEntryContent(blogsEntry.getContent());
	}

	@Override
	public String getEntryId(BlogsEntry blogsEntry) {
		return String.valueOf(blogsEntry.getEntryId());
	}

	@Override
	public String getEntrySummary(BlogsEntry blogsEntry) {
		return blogsEntry.getDescription();
	}

	@Override
	public String getEntryTitle(BlogsEntry blogsEntry) {
		return blogsEntry.getTitle();
	}

	@Override
	public Date getEntryUpdated(BlogsEntry blogsEntry) {
		return blogsEntry.getModifiedDate();
	}

	@Override
	public String getFeedTitle(AtomRequestContext atomRequestContext) {
		String portletId = PortletProviderUtil.getPortletId(
			BlogsEntry.class.getName(), PortletProvider.Action.VIEW);

		return AtomUtil.createFeedTitleFromPortletName(
			atomRequestContext, portletId);
	}

	@Override
	protected void doDeleteEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws Exception {

		long blogsEntryId = GetterUtil.getLong(resourceName);

		_blogsEntryService.deleteEntry(blogsEntryId);
	}

	@Override
	protected BlogsEntry doGetEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws Exception {

		long blogsEntryId = GetterUtil.getLong(resourceName);

		return _blogsEntryService.getEntry(blogsEntryId);
	}

	@Override
	protected Iterable<BlogsEntry> doGetFeedEntries(
			AtomRequestContext atomRequestContext)
		throws Exception {

		long groupId = atomRequestContext.getLongParameter("groupId");
		int status = WorkflowConstants.STATUS_APPROVED;

		int max = atomRequestContext.getIntParameter(
			"max", SearchContainer.DEFAULT_DELTA);

		if (groupId > 0) {
			int page = atomRequestContext.getIntParameter("page");

			if (page == 0) {
				return _blogsEntryService.getGroupEntries(groupId, status, max);
			}

			int count = _blogsEntryService.getGroupEntriesCount(
				groupId, new Date(), status);

			AtomPager atomPager = new AtomPager(page, max, count);

			AtomUtil.saveAtomPagerInRequest(atomRequestContext, atomPager);

			return _blogsEntryService.getGroupEntries(
				groupId, new Date(), status, atomPager.getStart(),
				atomPager.getEnd() + 1);
		}

		long organizationId = atomRequestContext.getLongParameter(
			"organizationId");

		if (organizationId > 0) {
			return _blogsEntryService.getOrganizationEntries(
				organizationId, new Date(), status, max);
		}

		long companyId = CompanyThreadLocal.getCompanyId();

		if (companyId > 0) {
			return _blogsEntryService.getCompanyEntries(
				companyId, new Date(), status, max);
		}

		return Collections.emptyList();
	}

	@Override
	protected BlogsEntry doPostEntry(
			String title, String summary, String content, Date date,
			AtomRequestContext atomRequestContext)
		throws Exception {

		long groupId = atomRequestContext.getLongParameter("groupId");

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		int displayDateMonth = cal.get(Calendar.MONTH);
		int displayDateDay = cal.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = cal.get(Calendar.YEAR);
		int displayDateHour = cal.get(Calendar.HOUR_OF_DAY);
		int displayDateMinute = cal.get(Calendar.MINUTE);

		boolean allowPingbacks = true;
		boolean allowTrackbacks = true;
		String[] trackbacks = new String[0];

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		return _blogsEntryService.addEntry(
			title, StringPool.BLANK, summary, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, StringPool.BLANK, null,
			null, serviceContext);
	}

	@Override
	protected void doPutEntry(
			BlogsEntry blogsEntry, String title, String summary, String content,
			Date date, AtomRequestContext atomRequestContext)
		throws Exception {

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		int displayDateMonth = cal.get(Calendar.MONTH);
		int displayDateDay = cal.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = cal.get(Calendar.YEAR);
		int displayDateHour = cal.get(Calendar.HOUR_OF_DAY);
		int displayDateMinute = cal.get(Calendar.MINUTE);

		String[] trackbacks = StringUtil.split(blogsEntry.getTrackbacks());

		ServiceContext serviceContext = new ServiceContext();

		_blogsEntryService.updateEntry(
			blogsEntry.getEntryId(), title, blogsEntry.getSubtitle(), summary,
			content, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, blogsEntry.isAllowPingbacks(),
			blogsEntry.isAllowTrackbacks(), trackbacks, StringPool.BLANK, null,
			null, serviceContext);
	}

	private static final String _COLLECTION_NAME = "blogs";

	@Reference
	private BlogsEntryService _blogsEntryService;

}