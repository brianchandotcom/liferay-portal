/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.web.internal.security.auth;

import com.liferay.portal.kernel.security.auth.AlwaysAllowDoAsUser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(service = AlwaysAllowDoAsUser.class)
public class EditPageAttachmentAlwaysAllowDoAsUser
	implements AlwaysAllowDoAsUser {

	@Override
	public Collection<String> getActionNames() {
		return Collections.emptyList();
	}

	@Override
	public Collection<String> getMVCRenderCommandNames() {
		return _mvcRenderCommandNames;
	}

	@Override
	public Collection<String> getPaths() {
		return Collections.emptyList();
	}

	@Override
	public Collection<String> getStrutsActions() {
		return Collections.emptyList();
	}

	private final Collection<String> _mvcRenderCommandNames = Arrays.asList(
		"/wiki/edit_page_attachment");

}