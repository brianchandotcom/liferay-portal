/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.document.library.internal.item.type;

import com.liferay.content.dashboard.document.library.internal.item.provider.FileExtensionGroupsProvider;
import com.liferay.content.dashboard.info.item.ClassNameClassPKInfoItemIdentifier;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtype;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.RandomUtil;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Alejandro Tardín
 */
public class DLFileEntryTypeContentDashboardItemSubtype
	implements ContentDashboardItemSubtype<DLFileEntryType> {

	public DLFileEntryTypeContentDashboardItemSubtype(
		DLFileEntryType basicDocumentDLFileEntryType, DLFileEntry dlFileEntry,
		DLFileEntryType dlFileEntryType,
		FileExtensionGroupsProvider fileExtensionGroupsProvider, Group group,
		Language language) {

		_basicDocumentDLFileEntryType = basicDocumentDLFileEntryType;
		_dlFileEntry = dlFileEntry;
		_dlFileEntryType = dlFileEntryType;
		_fileExtensionGroupsProvider = fileExtensionGroupsProvider;
		_group = group;
		_language = language;

		_infoItemReference = new InfoItemReference(
			FileEntry.class.getName(),
			new ClassNameClassPKInfoItemIdentifier(
				DLFileEntryType.class.getName(),
				dlFileEntryType.getFileEntryTypeId()));
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
		if (_group != null) {
			try {
				return StringBundler.concat(
					getLabel(locale), StringPool.SPACE,
					StringPool.OPEN_PARENTHESIS,
					_group.getDescriptiveName(locale),
					StringPool.CLOSE_PARENTHESIS);
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}

		return getLabel(locale);
	}

	@Override
	public InfoItemReference getInfoItemReference() {
		return _infoItemReference;
	}

	@Override
	public String getLabel(Locale locale) {
		if ((_dlFileEntry != null) &&
			Objects.equals(_basicDocumentDLFileEntryType, _dlFileEntryType)) {

			return StringBundler.concat(
				_dlFileEntryType.getName(locale), StringPool.SPACE,
				StringPool.OPEN_PARENTHESIS,
				_language.get(
					locale,
					_fileExtensionGroupsProvider.getFileGroupKey(
						_dlFileEntry.getExtension())),
				StringPool.CLOSE_PARENTHESIS);
		}

		return _dlFileEntryType.getName(locale);
	}

	@Override
	public int hashCode() {
		if (!(_infoItemReference.getInfoItemIdentifier() instanceof
				ClassNameClassPKInfoItemIdentifier)) {

			return RandomUtil.nextInt(4);
		}

		ClassNameClassPKInfoItemIdentifier classNameClassPKInfoItemIdentifier =
			(ClassNameClassPKInfoItemIdentifier)
				_infoItemReference.getInfoItemIdentifier();

		int hash = HashUtil.hash(
			0, classNameClassPKInfoItemIdentifier.getClassPK());

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
		).toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryTypeContentDashboardItemSubtype.class);

	private final DLFileEntryType _basicDocumentDLFileEntryType;
	private final DLFileEntry _dlFileEntry;
	private final DLFileEntryType _dlFileEntryType;
	private final FileExtensionGroupsProvider _fileExtensionGroupsProvider;
	private final Group _group;
	private final InfoItemReference _infoItemReference;
	private final Language _language;

}