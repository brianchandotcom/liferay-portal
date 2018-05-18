/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.type.simple.internal;

import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"commerce.product.type.display.order:Integer=5",
		"commerce.product.type.name=" + SimpleCPTypeConstants.NAME
	},
	service = CPType.class
)
public class SimpleCPType implements CPType {

	@Override
	public void deleteCPDefinition(long cpDefinitionId) throws PortalException {
	}

	@Override
	public String getCPDefinitionEditUrl(
			long cpDefinitionId, HttpServletRequest httpServletRequest)
		throws PortalException {

		return null;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, SimpleCPTypeConstants.NAME);
	}

	@Override
	public String getName() {
		return SimpleCPTypeConstants.NAME;
	}

}