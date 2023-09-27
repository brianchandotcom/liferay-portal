/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.journal.internal.item.type;

import com.liferay.content.dashboard.info.item.ClassNameClassPKInfoItemIdentifier;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtype;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.info.item.InfoItemReference;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.RandomUtil;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Cristina González
 */
public class DDMStructureContentDashboardItemSubtype
	implements ContentDashboardItemSubtype<DDMStructure> {

	public DDMStructureContentDashboardItemSubtype(
		DDMStructure ddmStructure, Group group) {

		_ddmStructure = ddmStructure;
		_group = group;

		_infoItemReference = new InfoItemReference(
			JournalArticle.class.getName(),
			new ClassNameClassPKInfoItemIdentifier(
				DDMStructure.class.getName(), ddmStructure.getStructureId()));
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

		try {
			groupName =
				StringPool.OPEN_PARENTHESIS +
					_group.getDescriptiveName(locale) +
						StringPool.CLOSE_PARENTHESIS;
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return getLabel(locale) + StringPool.SPACE + groupName;
	}

	@Override
	public InfoItemReference getInfoItemReference() {
		return _infoItemReference;
	}

	@Override
	public String getLabel(Locale locale) {
		return _ddmStructure.getName(locale);
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
		DDMStructureContentDashboardItemSubtype.class);

	private final DDMStructure _ddmStructure;
	private final Group _group;
	private final InfoItemReference _infoItemReference;

}