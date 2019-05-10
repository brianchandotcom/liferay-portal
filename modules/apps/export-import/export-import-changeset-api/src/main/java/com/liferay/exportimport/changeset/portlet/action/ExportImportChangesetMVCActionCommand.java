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

package com.liferay.exportimport.changeset.portlet.action;

import com.liferay.exportimport.changeset.Changeset;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Máté Thurzó
 */
@ProviderType
public interface ExportImportChangesetMVCActionCommand
	extends MVCActionCommand {

	public void processExportAction(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Changeset changeset)
		throws Exception;

	public void processPublishAction(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Changeset changeset)
		throws Exception;

}