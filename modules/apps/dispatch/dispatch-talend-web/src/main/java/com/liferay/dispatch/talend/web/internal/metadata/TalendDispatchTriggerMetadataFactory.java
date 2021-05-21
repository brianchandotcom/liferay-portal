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

package com.liferay.dispatch.talend.web.internal.metadata;

import com.liferay.dispatch.metadata.DispatchTriggerMetadata;
import com.liferay.dispatch.metadata.DispatchTriggerMetadataFactory;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.repository.DispatchFileRepository;
import com.liferay.dispatch.talend.web.internal.executor.TalendDispatchTaskExecutor;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	immediate = true,
	property = "dispatch.task.executor.type=" + TalendDispatchTaskExecutor.TALEND,
	service = DispatchTriggerMetadataFactory.class
)
public class TalendDispatchTriggerMetadataFactory
	implements DispatchTriggerMetadataFactory {

	@Override
	public DispatchTriggerMetadata instance(DispatchTrigger dispatchTrigger) {
		FileEntry fileEntry = _dispatchFileRepository.fetchFileEntry(
			dispatchTrigger.getDispatchTriggerId());

		if (fileEntry != null) {
			return new TalendDispatchTriggerMetadata(true);
		}

		Map<String, String> errors = new HashMap<>();

		errors.put("talend-archive-file-misses", null);

		return new TalendDispatchTriggerMetadata(false, errors);
	}

	@Reference
	private DispatchFileRepository _dispatchFileRepository;

}