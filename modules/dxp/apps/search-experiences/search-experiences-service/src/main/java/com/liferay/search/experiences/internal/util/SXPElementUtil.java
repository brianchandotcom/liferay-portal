/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.util;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.search.experiences.internal.model.listener.CompanyModelListener;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Renan Vasconcelos
 */
public class SXPElementUtil {

	public static void addSXPElements(
			Company company, SXPElementLocalService sxpElementLocalService)
		throws PortalException {

		Set<String> externalReferenceCodes = new HashSet<>();

		for (com.liferay.search.experiences.model.SXPElement sxpPElement :
				sxpElementLocalService.getSXPElements(
					company.getCompanyId(), true)) {

			externalReferenceCodes.add(sxpPElement.getExternalReferenceCode());
		}

		for (SXPElement sxpElement : _getOrCreateSXPElements()) {
			if ((!FeatureFlagManagerUtil.isEnabled("LPS-122920") &&
				 Objects.equals(
					 sxpElement.getExternalReferenceCode(),
					 "RESCORE_BY_TEXT_EMBEDDING")) ||
				externalReferenceCodes.contains(
					sxpElement.getExternalReferenceCode())) {

				continue;
			}

			User user = company.getGuestUser();

			sxpElementLocalService.addSXPElement(
				sxpElement.getExternalReferenceCode(), user.getUserId(),
				LocalizedMapUtil.getLocalizedMap(
					sxpElement.getDescription_i18n()),
				String.valueOf(sxpElement.getElementDefinition()), true,
				_SCHEMA_VERSION,
				LocalizedMapUtil.getLocalizedMap(sxpElement.getTitle_i18n()), 0,
				new ServiceContext() {
					{
						setAddGuestPermissions(true);
						setCompanyId(company.getCompanyId());
						setScopeGroupId(company.getGroupId());
						setUserId(user.getUserId());
					}
				});
		}
	}

	private static List<SXPElement> _createSXPElements() {
		Bundle bundle = FrameworkUtil.getBundle(CompanyModelListener.class);

		Package pkg = CompanyModelListener.class.getPackage();

		String path = StringUtil.replace(
			pkg.getName(), CharPool.PERIOD, CharPool.SLASH);

		List<SXPElement> sxpElements = new ArrayList<>();

		Enumeration<URL> enumeration = bundle.findEntries(
			path.concat("/dependencies"), "*.json", false);

		try {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				sxpElements.add(
					com.liferay.search.experiences.rest.dto.v1_0.util.
						SXPElementUtil.toSXPElement(
							StreamUtil.toString(url.openStream())));
			}
		}
		catch (IOException ioException) {
			throw new ExceptionInInitializerError(ioException);
		}

		return sxpElements;
	}

	private static List<SXPElement> _getOrCreateSXPElements() {
		if (_sxpElements == null) {
			_sxpElements = _createSXPElements();
		}

		return _sxpElements;
	}

	private static final String _SCHEMA_VERSION = StringUtil.replace(
		StringUtil.extractFirst(
			StringUtil.extractLast(SXPElement.class.getName(), ".v"),
			CharPool.PERIOD),
		CharPool.UNDERLINE, CharPool.PERIOD);

	private static List<SXPElement> _sxpElements;

}