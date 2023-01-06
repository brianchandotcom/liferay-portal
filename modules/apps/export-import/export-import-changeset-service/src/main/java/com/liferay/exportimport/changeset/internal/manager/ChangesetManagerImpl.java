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