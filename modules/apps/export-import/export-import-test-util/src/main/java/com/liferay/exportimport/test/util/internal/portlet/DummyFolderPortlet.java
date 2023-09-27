/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test.util.internal.portlet;

import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.test.util.constants.DummyFolderPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = {
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"javax.portlet.name=" + DummyFolderPortletKeys.DUMMY_FOLDER,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class DummyFolderPortlet extends MVCPortlet {

	@Reference(
		target = "(javax.portlet.name=" + DummyFolderPortletKeys.DUMMY_FOLDER + ")"
	)
	private PortletDataHandler _dummyFolderPortletDataHandler;

}