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

package com.liferay.content.dashboard.web.internal.item.type;

import com.liferay.content.dashboard.web.internal.info.item.provider.util.ClassNameClassPKInfoItemIdentifier;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Alejandro Tardín
 */
public class DLFileEntryTypeContentDashboardItemSubtype
	implements ContentDashboardItemSubtype<DLFileEntryType> {

	public DLFileEntryTypeContentDashboardItemSubtype(
		DLFileEntryType dlFileEntryType, Group group) {

		_dlFileEntryType = dlFileEntryType;
		_group = group;

		_infoItemReference = new InfoItemReference(
			FileEntry.class.getName(),
			new ClassNameClassPKInfoItemIdentifier(
				DLFileEntryType.class.getName(),
				_dlFileEntryType.getFileEntryTypeId()));
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ContentDashboardItemSubtype)) {
			return false;
		}

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			(ContentDashboardItemSubtype)object;

		InfoItemReference infoItemReference =
			contentDashboardItemSubtype.getInfoItemReference();

		if (!Objects.equals(
				_infoItemReference.getClassName(),
				infoItemReference.getClassName()) ||
			!(infoItemReference.getInfoItemIdentifier() instanceof
				ClassNameClassPKInfoItemIdentifier)) {

			return false;
		}

		ClassNameClassPKInfoItemIdentifier
			curClassNameClassPKInfoItemIdentifier =
				(ClassNameClassPKInfoItemIdentifier)
					_infoItemReference.getInfoItemIdentifier();

		ClassNameClassPKInfoItemIdentifier classNameClassPKInfoItemIdentifier =
			(ClassNameClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		if (!Objects.equals(
				curClassNameClassPKInfoItemIdentifier.getClassName(),
				classNameClassPKInfoItemIdentifier.getClassName()) ||
			!Objects.equals(
				curClassNameClassPKInfoItemIdentifier.getClassPK(),
				classNameClassPKInfoItemIdentifier.getClassPK())) {

			return false;
		}

		return true;
	}

	@Override
	public String getFullLabel(Locale locale) {
		String groupName = StringPool.BLANK;

		if (_group != null) {
			try {
				groupName =
					StringPool.OPEN_PARENTHESIS +
						_group.getDescriptiveName(locale) +
							StringPool.CLOSE_PARENTHESIS;
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}

		return getLabel(locale) + StringPool.SPACE + groupName;
	}

	@Override
	public InfoItemReference getInfoItemReference() {
		return _infoItemReference;
	}

	@Override
	public String getLabel(Locale locale) {
		return _dlFileEntryType.getName(locale);
	}

	@Override
	public Date getModifiedDate() {
		return _dlFileEntryType.getModifiedDate();
	}

	@Override
	public long getUserId() {
		return _dlFileEntryType.getUserId();
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _infoItemReference.getClassPK());

		return HashUtil.hash(hash, _infoItemReference.getClassName());
	}

	@Override
	public String toJSONString(Locale locale) {
		ClassNameClassPKInfoItemIdentifier classNameClassPKInfoItemIdentifier =
			(ClassNameClassPKInfoItemIdentifier)
				_infoItemReference.getInfoItemIdentifier();

		return JSONUtil.put(
			"className", classNameClassPKInfoItemIdentifier.getClassName()
		).put(
			"classPK", classNameClassPKInfoItemIdentifier.getClassPK()
		).put(
			"entryClassName", _infoItemReference.getClassName()
		).put(
			"title", getFullLabel(locale)
		).toJSONString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryTypeContentDashboardItemSubtype.class);

	private final DLFileEntryType _dlFileEntryType;
	private final Group _group;
	private final InfoItemReference _infoItemReference;

}