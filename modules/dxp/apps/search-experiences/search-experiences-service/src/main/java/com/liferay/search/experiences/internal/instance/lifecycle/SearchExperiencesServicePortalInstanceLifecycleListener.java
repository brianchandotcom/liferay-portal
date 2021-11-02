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

package com.liferay.search.experiences.internal.instance.lifecycle;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPElementUtil;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.net.URL;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	enabled = true, immediate = true,
	service = PortalInstanceLifecycleListener.class
)
public class SearchExperiencesServicePortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		long groupId = company.getGroupId();
		User user = company.getDefaultUser();

		Stream<String> stream = _getSXPElementJSONStringsStream();

		RuntimeException runtimeException = new RuntimeException();

		stream.forEach(
			json -> {
				try {
					_importSXPElement(
						company.getCompanyId(), groupId, json,
						user.getUserId());
				}
				catch (Exception exception) {
					runtimeException.addSuppressed(exception);
				}
			});

		if (!ArrayUtil.isEmpty(runtimeException.getSuppressed())) {
			throw runtimeException;
		}
	}

	public interface SXPElementJSONStringsLookup {

		public Stream<String> getSXPElementJSONStrings();

	}

	public interface SXPElementLookup {

		public boolean exists(SXPElement sxpElement);

	}

	protected static final String ELEMENTS_PATH = "/META-INF/elements";

	private ServiceContext _createServiceContext(
		long companyId, long groupId, long userId) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(companyId);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);
		serviceContext.setUuid(_createUUID());

		return serviceContext;
	}

	private String _createUUID() {
		UUID uuid = new UUID(
			SecureRandomUtil.nextLong(), SecureRandomUtil.nextLong());

		return uuid.toString();
	}

	private boolean _exists(SXPElement sxpElement) {
		if (_sxpElementLookup != null) {
			return _sxpElementLookup.exists(sxpElement);
		}

		// TODO reliably check whether system element has been added before

		return ListUtil.exists(
			_sxpElementLocalService.getSXPElements(0, 100),
			modelSXPElement -> Objects.equals(
				MapUtil.getString(sxpElement.getTitle_i18n(), "en_US"),
				modelSXPElement.getTitle(LocaleUtil.US)));
	}

	private Stream<String> _getSXPElementJSONStringsStream() {
		if (_sxpElementJSONStringsLookup != null) {
			return _sxpElementJSONStringsLookup.getSXPElementJSONStrings();
		}

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		Enumeration<URL> enumeration = bundle.findEntries(
			ELEMENTS_PATH, "*.json", false);

		if (enumeration == null) {
			return Stream.empty();
		}

		List<URL> urls = Collections.list(enumeration);

		return urls.stream(
		).map(
			url -> StringUtil.read(getClass(), StringPool.SLASH + url.getPath())
		);
	}

	private void _importSXPElement(
		long companyId, long groupId, String json, long userId) {

		SXPElement sxpElement = SXPElementUtil.toSXPElement(json);

		if (_exists(sxpElement)) {
			return;
		}

		try {
			_sxpElementLocalService.addSXPElement(
				userId, _toLocalizationMap(sxpElement.getDescription_i18n()),
				String.valueOf(sxpElement.getElementDefinition()), true,
				_toLocalizationMap(sxpElement.getTitle_i18n()), 0,
				_createServiceContext(companyId, groupId, userId));
		}
		catch (PortalException portalException) {
			ReflectionUtil.throwException(portalException);
		}
	}

	private Map<Locale, String> _toLocalizationMap(Map<String, String> map1) {
		if (map1 == null) {
			return null;
		}

		Map<Locale, String> map2 = new HashMap<>();

		map1.forEach(
			(languageId, translation) -> map2.put(
				LocaleUtil.fromLanguageId(languageId), translation));

		return map2;
	}

	@Reference
	private JSONFactory _jsonFactory;

	private SXPElementJSONStringsLookup _sxpElementJSONStringsLookup;

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

	private SXPElementLookup _sxpElementLookup;

}