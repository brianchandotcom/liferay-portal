/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.layout.display.page;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

/**
 * @author Eudaldo Alonso
 * @author Alec Sloan
 */
public class CPDefinitionLayoutDisplayPageObjectProvider
	implements LayoutDisplayPageObjectProvider<CPDefinition> {

	public CPDefinitionLayoutDisplayPageObjectProvider(
		CPDefinition cpDefinition, long groupId) {

		_cpDefinition = cpDefinition;
		_groupId = groupId;
	}

	@Override
	public String getClassName() {
		return CPDefinition.class.getName();
	}

	@Override
	public long getClassNameId() {
		return PortalUtil.getClassNameId(CPDefinition.class);
	}

	@Override
	public long getClassPK() {
		return _cpDefinition.getCPDefinitionId();
	}

	@Override
	public long getClassTypeId() {
		return 0;
	}

	@Override
	public String getDescription(Locale locale) {
		return _cpDefinition.getDescription(LanguageUtil.getLanguageId(locale));
	}

	@Override
	public CPDefinition getDisplayObject() {
		return _cpDefinition;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public String getKeywords(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return _cpDefinition.getName(LanguageUtil.getLanguageId(locale));
	}

	@Override
	public String getURLTitle(Locale locale) {
		return _cpDefinition.getURL(LanguageUtil.getLanguageId(locale));
	}

	private final CPDefinition _cpDefinition;
	private final long _groupId;

}