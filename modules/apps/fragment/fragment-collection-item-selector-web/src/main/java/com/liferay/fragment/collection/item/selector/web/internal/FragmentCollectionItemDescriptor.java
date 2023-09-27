/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.collection.item.selector.web.internal;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Date;
import java.util.Locale;

/**
 * @author Rubén Pulido
 */
public class FragmentCollectionItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public FragmentCollectionItemDescriptor(
		FragmentCollection fragmentCollection) {

		_fragmentCollection = fragmentCollection;
	}

	@Override
	public String getIcon() {
		return "documents-and-media";
	}

	@Override
	public String getImageURL() {
		return null;
	}

	@Override
	public Date getModifiedDate() {
		return _fragmentCollection.getModifiedDate();
	}

	@Override
	public String getPayload() {
		return JSONUtil.put(
			"fragmentCollectionId",
			_fragmentCollection.getFragmentCollectionId()
		).put(
			"fragmentCollectionKey",
			_fragmentCollection.getFragmentCollectionKey()
		).put(
			"groupId", _fragmentCollection.getGroupId()
		).put(
			"name", _fragmentCollection.getName()
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return _fragmentCollection.getDescription();
	}

	@Override
	public String getTitle(Locale locale) {
		return _fragmentCollection.getName();
	}

	@Override
	public long getUserId() {
		return _fragmentCollection.getUserId();
	}

	@Override
	public String getUserName() {
		return _fragmentCollection.getUserName();
	}

	@Override
	public boolean isCompact() {
		return true;
	}

	private final FragmentCollection _fragmentCollection;

}