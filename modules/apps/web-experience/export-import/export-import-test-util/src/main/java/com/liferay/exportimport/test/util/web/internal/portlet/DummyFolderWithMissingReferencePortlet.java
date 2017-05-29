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

package com.liferay.exportimport.test.util.web.internal.portlet;

import com.liferay.exportimport.test.util.constants.DummyFolderPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + DummyFolderPortletKeys.DUMMY_FOLDER_WITH_MISSING_REFERENCE},
	service = {DummyFolderWithMissingReferencePortlet.class, Portlet.class}
)
public class DummyFolderWithMissingReferencePortlet extends MVCPortlet {
}