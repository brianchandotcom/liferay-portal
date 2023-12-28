/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.UserAccountFullNameDefinition;
import com.liferay.headless.admin.user.dto.v1_0.UserAccountFullNameDefinitionField;
import com.liferay.headless.admin.user.resource.v1_0.UserAccountFullNameDefinitionResource;
import com.liferay.portal.kernel.security.auth.FullNameDefinition;
import com.liferay.portal.kernel.security.auth.FullNameDefinitionFactory;
import com.liferay.portal.kernel.security.auth.FullNameField;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Stefano Motta
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/user-account-full-name-definition.properties",
	scope = ServiceScope.PROTOTYPE,
	service = UserAccountFullNameDefinitionResource.class
)
public class UserAccountFullNameDefinitionResourceImpl
	extends BaseUserAccountFullNameDefinitionResourceImpl {

	@Override
	public UserAccountFullNameDefinition getUserAccountFullNameDefinition(
			String languageId)
		throws Exception {

		Locale locale = LocaleUtil.fromLanguageId(languageId, true);

		FullNameDefinition fullNameDefinition =
			FullNameDefinitionFactory.getInstance(locale);

		return new UserAccountFullNameDefinition() {
			{
				setUserAccountFullNameDefinitionFields(
					() -> transformToArray(
						fullNameDefinition.getFullNameFields(),
						fullNameField -> _toUserAccountFullNameDefinitionField(
							fullNameField),
						UserAccountFullNameDefinitionField.class));
			}
		};
	}

	private UserAccountFullNameDefinitionField
		_toUserAccountFullNameDefinitionField(FullNameField fullNameField) {

		return new UserAccountFullNameDefinitionField() {
			{
				setKey(fullNameField::getName);
				setRequired(fullNameField::isRequired);
				setValues(fullNameField::getValues);
			}
		};
	}

}