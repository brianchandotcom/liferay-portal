/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.model.listener;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPElementUtil;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false, immediate = true,
	service = {CompanyModelListener.class, ModelListener.class}
)
public class CompanyModelListener extends BaseModelListener<Company> {

	public void addSXPElements(
			Company company, SXPElementLocalService sxpElementLocalService)
		throws PortalException {

		Set<String> titles = new HashSet<>();

		for (com.liferay.search.experiences.model.SXPElement sxpPElement :
				sxpElementLocalService.getSXPElements(
					company.getCompanyId(), true)) {

			titles.add(sxpPElement.getTitle(LocaleUtil.US));
		}

		for (SXPElement sxpElement : _sxpElements) {

			// TODO Should this be en_US or en-US?

			if (titles.contains(
					MapUtil.getString(sxpElement.getTitle_i18n(), "en_US"))) {

				continue;
			}

			User user = company.getDefaultUser();

			sxpElementLocalService.addSXPElement(
				user.getUserId(),
				LocalizedMapUtil.getLocalizedMap(
					sxpElement.getDescription_i18n()),
				String.valueOf(sxpElement.getElementDefinition()), true,
				_SCHEMA_VERSION,
				LocalizedMapUtil.getLocalizedMap(sxpElement.getTitle_i18n()), 0,
				new ServiceContext() {
					{
						setAddGroupPermissions(true);
						setAddGuestPermissions(true);
						setCompanyId(company.getCompanyId());
						setScopeGroupId(company.getGroupId());
						setUserId(user.getUserId());
					}
				});
		}
	}

	@Override
	public void onAfterCreate(Company company) {
		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				try {
					addSXPElements(company, _sxpElementLocalService);
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}

				return null;
			});
	}

	@Override
	public void onAfterRemove(Company company) {
		try {
			_sxpBlueprintLocalService.deleteCompanySXPBlueprints(
				company.getCompanyId());

			_sxpElementLocalService.deleteCompanySXPElements(
				company.getCompanyId());
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private List<SXPElement> _createSXPElements() {
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
					SXPElementUtil.toSXPElement(
						StreamUtil.toString(url.openStream())));
			}
		}
		catch (IOException ioException) {
			throw new ExceptionInInitializerError(ioException);
		}

		return sxpElements;
	}

	private static final String _SCHEMA_VERSION = StringUtil.replace(
		StringUtil.extractFirst(
			StringUtil.extractLast(SXPElement.class.getName(), ".v"),
			CharPool.PERIOD),
		CharPool.UNDERLINE, CharPool.PERIOD);

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyModelListener.class);

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

	private final List<SXPElement> _sxpElements = _createSXPElements();

}