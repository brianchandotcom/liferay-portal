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

package com.liferay.wiki.internal.asset.validator;

import com.liferay.asset.kernel.validator.AssetEntryValidatorExclusionRule;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageConstants;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	configurationPid = "com.liferay.wiki.configuration.WikiGroupServiceConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = "model.class.name=com.liferay.wiki.model.WikiPage",
	service = AssetEntryValidatorExclusionRule.class
)
public class FrontPageAssetEntryValidatorExclusionRule
	implements AssetEntryValidatorExclusionRule {

	@Activate
	@Modified
	public void activate(Map<String, Object> properties) {
		_wikiGroupServiceConfiguration = ConfigurableUtil.createConfigurable(
			WikiGroupServiceConfiguration.class, properties);
	}

	@Override
	public boolean isValidationExcluded(
		long groupId, String className, long classPK, long classTypePK,
		long[] categoryIds, String[] tagNames) {

		WikiPage wikiPage = _wikiPageLocalService.fetchWikiPage(classPK);

		if (wikiPage == null) {
			wikiPage = _wikiPageLocalService.fetchPage(classPK);
		}

		if (wikiPage == null) {
			try {
				wikiPage = _wikiPageLocalService.getPage(classPK, false);
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(pe, pe);
				}

				return false;
			}
		}

		if (StringUtil.equals(
				wikiPage.getTitle(),
				_wikiGroupServiceConfiguration.frontPageName()) &&
			(wikiPage.getVersion() == WikiPageConstants.VERSION_DEFAULT)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrontPageAssetEntryValidatorExclusionRule.class);

	private WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;

	@Reference(unbind = "-")
	private WikiPageLocalService _wikiPageLocalService;

}