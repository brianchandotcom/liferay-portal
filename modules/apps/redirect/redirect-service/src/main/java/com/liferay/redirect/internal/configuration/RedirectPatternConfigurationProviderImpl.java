/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.configuration;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.redirect.configuration.RedirectPatternConfigurationProvider;
import com.liferay.redirect.model.RedirectPatternEntry;
import com.liferay.redirect.provider.RedirectProvider;

import java.util.Dictionary;
import java.util.List;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = RedirectPatternConfigurationProvider.class)
public class RedirectPatternConfigurationProviderImpl
	implements RedirectPatternConfigurationProvider {

	public List<RedirectPatternEntry> getRedirectPatternEntries(long groupId) {
		return _redirectProvider.getRedirectPatternEntries(groupId);
	}

	@Override
	public void updatePatternStrings(
			long groupId, List<RedirectPatternEntry> redirectPatternEntries)
		throws Exception {

		Dictionary<String, Object> properties = null;

		Configuration configuration = null;

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(&(service.factoryPid=%s)(%s=%d))",
				RedirectPatternConfiguration.class.getName() + ".scoped",
				ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
				groupId));

		if (configurations != null) {
			configuration = configurations[0];
		}

		if (configuration == null) {
			configuration = _configurationAdmin.createFactoryConfiguration(
				RedirectPatternConfiguration.class.getName() + ".scoped",
				StringPool.QUESTION);

			properties = HashMapDictionaryBuilder.<String, Object>put(
				ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
				groupId
			).build();
		}
		else {
			properties = configuration.getProperties();
		}

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

		configuration.update(properties);
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private RedirectProvider _redirectProvider;

}