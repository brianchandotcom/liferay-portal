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

package com.liferay.workflow.apio.internal.architect.identifier;

import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.uri.mapper.PathIdentifierMapper;
import com.liferay.workflow.apio.architect.identifier.ReusableWorkflowTaskIdentifier;
import com.liferay.workflow.apio.architect.identifier.ReusableWorkflowTaskIdentifier.WorkflowTaskType;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;

/**
 * Converts a {@code Path} to a {@code ReusableWorkflowTaskIdentifier}, and vice
 * versa. The reusable workflow task identifier can be used as a resource's
 * identifier.
 *
 * @author Víctor Galán
 */
@Component(immediate = true, service = PathIdentifierMapper.class)
public class ReusableWorkflowTaskIdentifierMapper
	implements PathIdentifierMapper<ReusableWorkflowTaskIdentifier> {

	@Override
	public ReusableWorkflowTaskIdentifier map(Path path) {
		String id = path.getId();

		WorkflowTaskType workflowTaskType = WorkflowTaskType.get(id);

		if (workflowTaskType == null) {
			throw new BadRequestException(
				id + " should be a string with the form \"by-role\" or " +
					"\"by-user\"");
		}

		return ReusableWorkflowTaskIdentifier.create(workflowTaskType);
	}

	@Override
	public Path map(
		String name,
		ReusableWorkflowTaskIdentifier reusableWorkflowTaskIdentifier) {

		WorkflowTaskType workflowTaskType =
			reusableWorkflowTaskIdentifier.getWorkflowTaskType();

		return new Path(name, workflowTaskType.getName());
	}

}