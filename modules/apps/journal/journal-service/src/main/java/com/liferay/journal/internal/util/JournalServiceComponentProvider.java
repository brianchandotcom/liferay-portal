/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.util;

import com.liferay.journal.configuration.JournalGroupServiceConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Juergen Kappler
 */
@Component(
	configurationPid = "com.liferay.journal.configuration.JournalGroupServiceConfiguration",
	service = {}
)
public class JournalServiceComponentProvider {

	public static JournalServiceComponentProvider
		getJournalServiceComponentProvider() {

		return _journalServiceComponentProvider;
	}

	public JournalGroupServiceConfiguration
		getJournalGroupServiceConfiguration() {

		return _journalGroupServiceConfiguration;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_journalGroupServiceConfiguration = ConfigurableUtil.createConfigurable(
			JournalGroupServiceConfiguration.class, properties);

		_journalServiceComponentProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_journalServiceComponentProvider = null;
	}

	protected void unsetJournalGroupServiceConfiguration(
		JournalGroupServiceConfiguration journalGroupServiceConfiguration) {

		_journalGroupServiceConfiguration = null;
	}

	private static JournalServiceComponentProvider
		_journalServiceComponentProvider;

	private JournalGroupServiceConfiguration _journalGroupServiceConfiguration;

}