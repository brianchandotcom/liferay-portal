/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service.impl;

import com.liferay.frontend.token.definition.FrontendTokenCategory;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.frontend.token.definition.FrontendTokenSet;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.style.book.exception.DuplicateStyleBookTokenSetNameException;
import com.liferay.style.book.exception.StyleBookTokenSetFrontendTokenCategoryNameException;
import com.liferay.style.book.exception.StyleBookTokenSetNameException;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.model.StyleBookTokenSet;
import com.liferay.style.book.service.StyleBookEntryLocalService;
import com.liferay.style.book.service.base.StyleBookTokenSetLocalServiceBaseImpl;

import java.util.Date;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Lima
 */
@Component(
	property = "model.class.name=com.liferay.style.book.model.StyleBookTokenSet",
	service = AopService.class
)
public class StyleBookTokenSetLocalServiceImpl
	extends StyleBookTokenSetLocalServiceBaseImpl {

	@Override
	public StyleBookTokenSet addStyleBookTokenSet(
			String externalReferenceCode, String description,
			String frontendTokenCategoryName, String name,
			long styleBookEntryId, String themeId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(serviceContext.getUserId());

		_validate(
			user.getCompanyId(), frontendTokenCategoryName, name,
			styleBookEntryId, themeId);

		long styleBookTokenSetId = counterLocalService.increment(
			StyleBookTokenSet.class.getName());

		StyleBookTokenSet styleBookTokenSet =
			styleBookTokenSetPersistence.create(styleBookTokenSetId);

		String uuid = serviceContext.getUuid();

		if (Validator.isNotNull(uuid)) {
			styleBookTokenSet.setUuid(uuid);
		}

		if (Validator.isNull(externalReferenceCode)) {
			externalReferenceCode = styleBookTokenSet.getUuid();
		}

		styleBookTokenSet.setExternalReferenceCode(externalReferenceCode);

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.getStyleBookEntry(styleBookEntryId);

		styleBookTokenSet.setGroupId(styleBookEntry.getGroupId());

		styleBookTokenSet.setCompanyId(user.getCompanyId());
		styleBookTokenSet.setUserId(user.getUserId());
		styleBookTokenSet.setUserName(user.getFullName());

		Date date = new Date();

		styleBookTokenSet.setCreateDate(serviceContext.getCreateDate(date));
		styleBookTokenSet.setModifiedDate(serviceContext.getModifiedDate(date));

		styleBookTokenSet.setDescription(description);
		styleBookTokenSet.setFrontendTokenCategoryName(
			frontendTokenCategoryName);
		styleBookTokenSet.setName(name);
		styleBookTokenSet.setStyleBookEntryId(styleBookEntryId);
		styleBookTokenSet.setThemeId(themeId);

		return styleBookTokenSetPersistence.update(styleBookTokenSet);
	}

	private FrontendTokenCategory _getFrontendTokenCategory(
		long companyId, String frontendTokenCategoryName, String themeId) {

		FrontendTokenDefinition frontendTokenDefinition =
			_frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				companyId, themeId);

		if (frontendTokenDefinition == null) {
			return null;
		}

		for (FrontendTokenCategory frontendTokenCategory :
				frontendTokenDefinition.getFrontendTokenCategories()) {

			JSONObject frontendTokenCategoryJSONObject =
				frontendTokenCategory.getJSONObject(
					LocaleUtil.getSiteDefault());

			if (Objects.equals(
					frontendTokenCategoryName,
					frontendTokenCategoryJSONObject.getString("name"))) {

				return frontendTokenCategory;
			}
		}

		return null;
	}

	private FrontendTokenSet _getFrontendTokenSet(
		FrontendTokenCategory frontendTokenCategory, String name) {

		for (FrontendTokenSet frontendTokenSet :
				frontendTokenCategory.getFrontendTokenSets()) {

			JSONObject frontendTokenSetJSONObject =
				frontendTokenSet.getJSONObject(LocaleUtil.getSiteDefault());

			if (Objects.equals(
					name, frontendTokenSetJSONObject.getString("name"))) {

				return frontendTokenSet;
			}
		}

		return null;
	}

	private void _validate(
			long companyId, String frontendTokenCategoryName, String name,
			long styleBookEntryId, String themeId)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new StyleBookTokenSetNameException("Name is null");
		}

		if (name.length() > ModelHintsUtil.getMaxLength(
				StyleBookTokenSet.class.getName(), "name")) {

			throw new StyleBookTokenSetNameException(
				"Name exceeds the maximum length");
		}

		StyleBookTokenSet styleBookTokenSet =
			styleBookTokenSetPersistence.fetchByFTCN_N_SBEI_T(
				frontendTokenCategoryName, name, styleBookEntryId, themeId);

		if (styleBookTokenSet != null) {
			throw new DuplicateStyleBookTokenSetNameException(
				"Style book token set name \"" + name + "\" already exists");
		}

		FrontendTokenCategory frontendTokenCategory = _getFrontendTokenCategory(
			companyId, frontendTokenCategoryName, themeId);

		if (frontendTokenCategory == null) {
			throw new StyleBookTokenSetFrontendTokenCategoryNameException(
				"Frontend token category \"" + frontendTokenCategoryName +
					"\" does not exist");
		}

		FrontendTokenSet frontendTokenSet = _getFrontendTokenSet(
			frontendTokenCategory, name);

		if (frontendTokenSet != null) {
			throw new DuplicateStyleBookTokenSetNameException(
				"Style book token set name \"" + name + "\" already exists");
		}
	}

	@Reference
	private FrontendTokenDefinitionRegistry _frontendTokenDefinitionRegistry;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}