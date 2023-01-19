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

import com.liferay.fragment.model.FragmentEntryContributed;
import com.liferay.fragment.service.base.FragmentEntryContributedLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.fragment.model.FragmentEntryContributed",
	service = AopService.class
)
public class FragmentEntryContributedLocalServiceImpl
	extends FragmentEntryContributedLocalServiceBaseImpl {

	@Override
	public FragmentEntryContributed addOrUpdateFragmentEntryContributed(
		String fragmentEntryKey, String css, String html, String js,
		String configuration, int type) {

		FragmentEntryContributed fragmentEntryContributed =
			fragmentEntryContributedPersistence.fetchByFragmentEntryKey(
				fragmentEntryKey);

		if (fragmentEntryContributed == null) {
			fragmentEntryContributed =
				fragmentEntryContributedPersistence.create(
					counterLocalService.increment());

			fragmentEntryContributed.setCreateDate(new Date());
			fragmentEntryContributed.setFragmentEntryKey(fragmentEntryKey);
		}

		fragmentEntryContributed.setModifiedDate(new Date());
		fragmentEntryContributed.setFragmentEntryKey(fragmentEntryKey);
		fragmentEntryContributed.setCss(css);
		fragmentEntryContributed.setHtml(html);
		fragmentEntryContributed.setJs(js);
		fragmentEntryContributed.setConfiguration(configuration);
		fragmentEntryContributed.setType(type);

		return fragmentEntryContributedPersistence.update(
			fragmentEntryContributed);
	}

	@Override
	public FragmentEntryContributed fetchByFragmentEntryKey(
		String fragmentEntryKey) {

		return fragmentEntryContributedPersistence.fetchByFragmentEntryKey(
			fragmentEntryKey);
	}

}