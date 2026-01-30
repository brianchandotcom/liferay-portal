/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.configuration;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.redirect.configuration.RedirectPatternConfigurationProvider;
import com.liferay.redirect.model.RedirectPatternEntry;
import com.liferay.redirect.provider.RedirectProvider;

import java.util.Dictionary;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = RedirectPatternConfigurationProvider.class)
public class RedirectPatternConfigurationProviderImpl
	implements RedirectPatternConfigurationProvider {

	@Override
	public List<RedirectPatternEntry> getRedirectPatternEntries(long groupId) {
		return _redirectProvider.getRedirectPatternEntries(groupId);
	}

	@Override
	public void updatePatternStrings(
			long groupId, List<RedirectPatternEntry> redirectPatternEntries)
		throws Exception {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		if (ListUtil.isEmpty(redirectPatternEntries)) {
			properties.put("patternStrings", new String[0]);
		}
		else {
			int i = 0;

			String[] patternStringsArray =
				new String[redirectPatternEntries.size()];

			for (RedirectPatternEntry redirectPatternEntry :
					redirectPatternEntries) {

				patternStringsArray[i] = StringBundler.concat(
					redirectPatternEntry.getPattern(), StringPool.SPACE,
					redirectPatternEntry.getDestinationURL(), StringPool.SPACE,
					redirectPatternEntry.getUserAgent());

				i++;
			}

			properties.put("patternStrings", patternStringsArray);
		}

		Group group = _groupLocalService.fetchGroup(groupId);

		_configurationProvider.saveGroupConfiguration(
			group.getCompanyId(), groupId,
			RedirectPatternConfiguration.class.getName(), properties);
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private RedirectProvider _redirectProvider;

}