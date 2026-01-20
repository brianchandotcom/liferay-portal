/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.frontend.data.set;

import com.liferay.change.tracking.web.internal.constants.PublicationsFDSNames;
import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "frontend.data.set.name=" + PublicationsFDSNames.PUBLICATIONS_CHANGES,
	service = SystemFDSEntry.class
)
public class PublicationsChangesSystemFDSEntry implements SystemFDSEntry {

	@Override
	public String getAdditionalAPIURLParameters(
		HttpServletRequest httpServletRequest) {

		return "showHideable=" +
			ParamUtil.getBoolean(httpServletRequest, "showHideable");
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getName() {
		return PublicationsFDSNames.PUBLICATIONS_CHANGES;
	}

	@Override
	public String getRESTApplication() {
		return "/change-tracking-rest/v1.0";
	}

	@Override
	public String getRESTEndpoint() {
		return "/v1.0/ct-collections/{ctCollectionId}/ct-entries";
	}

	@Override
	public String getRESTSchema() {
		return "CTEntry";
	}

	@Override
	public boolean getSnapshotsEnabled() {
		return true;
	}

	@Override
	public String getTitle() {
		return _language.get(
			LocaleThreadLocal.getThemeDisplayLocale(), "publications-changes");
	}

	@Reference
	private Language _language;

}