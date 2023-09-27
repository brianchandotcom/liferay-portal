/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.changeset.internal.manager;

import com.liferay.exportimport.changeset.Changeset;
import com.liferay.exportimport.changeset.ChangesetManager;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * @author Máté Thurzó
 */
@Component(service = AopService.class)
public class ChangesetManagerImpl
	implements AopService, ChangesetManager, IdentifiableOSGiService {

	@Clusterable(onMaster = true)
	@Override
	public void addChangeset(Changeset changeset) {
		_changesets.putIfAbsent(changeset.getUuid(), changeset);
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return ChangesetManager.class.getName();
	}

	@Clusterable(onMaster = true)
	@Override
	public Changeset removeChangeset(String changesetUuid) {
		return _changesets.remove(changesetUuid);
	}

	private final Map<String, Changeset> _changesets =
		new ConcurrentHashMap<>();

}