/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.client.extension.type.ThemeCSSCET;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.frontend.token.definition.internal.validator.FrontendTokenDefinitionJSONValidator;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.validator.JSONValidatorException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.URL;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera
 */
@Component(service = FrontendTokenDefinitionRegistry.class)
public class FrontendTokenDefinitionRegistryImpl
	implements FrontendTokenDefinitionRegistry {

	@Override
	public FrontendTokenDefinition getFrontendTokenDefinition(
		LayoutSet layoutSet) {

		return _getFrontendTokenDefinition(
			layoutSet.getCompanyId(),
			_getLayoutSetThemeCSSCETExternalReferenceCode(
				layoutSet.getLayoutSetId()),
			layoutSet.getThemeId());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE, _bundleTrackerCustomizer);

		_companyFrontendTokenDefinitionsMap = new ConcurrentHashMap<>();

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, ThemeCSSCET.class,
			new ServiceTrackerCustomizer<ThemeCSSCET, ThemeCSSCET>() {

				@Override
				public ThemeCSSCET addingService(
					ServiceReference<ThemeCSSCET> serviceReference) {

					ThemeCSSCET themeCSSCET = bundleContext.getService(
						serviceReference);

					if (!Validator.isBlank(
							themeCSSCET.getFrontendTokenDefinitionJSON())) {

						_registerThemeCSSClientExtensionFrontendTokenDefinition(
							themeCSSCET);
					}

					return themeCSSCET;
				}

				@Override
				public void modifiedService(
					ServiceReference<ThemeCSSCET> serviceReference,
					ThemeCSSCET themeCSSCET) {
				}

				@Override
				public void removedService(
					ServiceReference<ThemeCSSCET> serviceReference,
					ThemeCSSCET themeCSSCET) {

					_unregisterThemeCSSClientExtensionFrontendTokenDefinition(
						themeCSSCET);

					bundleContext.ungetService(serviceReference);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();

		_serviceTracker.close();
	}

	protected FrontendTokenDefinitionImpl getFrontendTokenDefinitionImpl(
		Bundle bundle) {

		String json = _getFrontendTokenDefinitionJSON(bundle);

		if (json == null) {
			return null;
		}

		String themeId = getThemeId(bundle);

		try {
			ResourceBundleLoader resourceBundleLoader =
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByBundleSymbolicName(
						bundle.getSymbolicName());

			if (resourceBundleLoader == null) {
				resourceBundleLoader =
					ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
			}

			return new FrontendTokenDefinitionImpl(
				jsonFactory.createJSONObject(json), jsonFactory,
				resourceBundleLoader, themeId);
		}
		catch (JSONException | RuntimeException exception) {
			_log.error(
				"Unable to parse frontend token definitions for theme " +
					themeId,
				exception);
		}

		return null;
	}

	protected String getServletContextName(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String webContextPath = headers.get("Web-ContextPath");

		if (webContextPath == null) {
			return null;
		}

		if (webContextPath.startsWith(StringPool.SLASH)) {
			webContextPath = webContextPath.substring(1);
		}

		return webContextPath;
	}

	protected String getThemeId(Bundle bundle) {
		URL url = bundle.getEntry("WEB-INF/liferay-look-and-feel.xml");

		if (url == null) {
			return null;
		}

		try {
			String xml = URLUtil.toString(url);

			xml = xml.replaceAll(StringPool.NEW_LINE, StringPool.SPACE);

			Matcher matcher = _themeIdPattern.matcher(xml);

			if (!matcher.matches()) {
				return null;
			}

			String themeId = matcher.group(1);

			String servletContextName = getServletContextName(bundle);

			if (servletContextName != null) {
				themeId =
					themeId + PortletConstants.WAR_SEPARATOR +
						servletContextName;
			}

			return portal.getJsSafePortletId(themeId);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to read WEB-INF/liferay-look-and-feel.xml",
				ioException);
		}
	}

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected Portal portal;

	private FrontendTokenDefinition _getFrontendTokenDefinition(
		long companyId, String externalReferenceCode, String themeId) {

		if ((externalReferenceCode != null) &&
			FeatureFlagManagerUtil.isEnabled("LPD-10773")) {

			Map<String, FrontendTokenDefinition> frontendTokenDefinitionsMap =
				_getFrontendTokenDefinitionsMap(companyId);

			FrontendTokenDefinition frontendTokenDefinition =
				frontendTokenDefinitionsMap.get(externalReferenceCode);

			if (frontendTokenDefinition != null) {
				return frontendTokenDefinition;
			}
		}

		Map<String, FrontendTokenDefinitionImpl>
			themeIdFrontendTokenDefinitionImpls =
				_themeIdFrontendTokenDefinitionImplsDCLSingleton.getSingleton(
					() -> {
						_bundleTracker.open();

						return _themeIdFrontendTokenDefinitionImpls;
					});

		return themeIdFrontendTokenDefinitionImpls.get(themeId);
	}

	private String _getFrontendTokenDefinitionJSON(Bundle bundle) {
		URL url = bundle.getEntry("WEB-INF/frontend-token-definition.json");

		if (url == null) {
			return null;
		}

		try {
			return URLUtil.toString(url);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to read WEB-INF/frontend-token-definition.json",
				ioException);
		}
	}

	private Map<String, FrontendTokenDefinition>
		_getFrontendTokenDefinitionsMap(long companyId) {

		return _companyFrontendTokenDefinitionsMap.getOrDefault(
			companyId, new ConcurrentHashMap<>());
	}

	private String _getLayoutSetThemeCSSCETExternalReferenceCode(
		long layoutSetId) {

		ClientExtensionEntryRel clientExtensionEntryRel =
			_clientExtensionEntryRelLocalService.fetchClientExtensionEntryRel(
				_portal.getClassNameId(LayoutSet.class), layoutSetId,
				ClientExtensionEntryConstants.TYPE_THEME_CSS);

		if (clientExtensionEntryRel != null) {
			return clientExtensionEntryRel.getCETExternalReferenceCode();
		}

		return null;
	}

	private void _registerThemeCSSClientExtensionFrontendTokenDefinition(
		ThemeCSSCET themeCSSCET) {

		String frontendTokenDefinitionJSON =
			themeCSSCET.getFrontendTokenDefinitionJSON();

		try {
			_frontendTokenDefinitionJSONValidator.validate(
				frontendTokenDefinitionJSON);

			Map<String, FrontendTokenDefinition> frontendTokenDefinitionsMap =
				_companyFrontendTokenDefinitionsMap.computeIfAbsent(
					themeCSSCET.getCompanyId(),
					entry -> new ConcurrentHashMap<>());

			frontendTokenDefinitionsMap.put(
				themeCSSCET.getExternalReferenceCode(),
				new FrontendTokenDefinitionImpl(
					jsonFactory.createJSONObject(frontendTokenDefinitionJSON),
					jsonFactory,
					ResourceBundleLoaderUtil.getPortalResourceBundleLoader(),
					themeCSSCET.getExternalReferenceCode()));
		}
		catch (JSONException | JSONValidatorException exception) {
			_log.error(
				"Unable to parse theme CSS client extension frontend token " +
					"definition",
				exception);
		}
	}

	private void _unregisterThemeCSSClientExtensionFrontendTokenDefinition(
		ThemeCSSCET themeCSSCET) {

		Map<String, FrontendTokenDefinition> frontendTokenDefinitionsMap =
			_getFrontendTokenDefinitionsMap(themeCSSCET.getCompanyId());

		frontendTokenDefinitionsMap.remove(
			themeCSSCET.getExternalReferenceCode());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendTokenDefinitionRegistryImpl.class);

	private static final Pattern _themeIdPattern = Pattern.compile(
		".*<theme id=\"([^\"]*)\"[^>]*>.*");

	private BundleTracker<FrontendTokenDefinitionImpl> _bundleTracker;

	private final BundleTrackerCustomizer<FrontendTokenDefinitionImpl>
		_bundleTrackerCustomizer =
			new BundleTrackerCustomizer<FrontendTokenDefinitionImpl>() {

				@Override
				public FrontendTokenDefinitionImpl addingBundle(
					Bundle bundle, BundleEvent bundleEvent) {

					FrontendTokenDefinitionImpl frontendTokenDefinitionImpl =
						getFrontendTokenDefinitionImpl(bundle);

					if ((frontendTokenDefinitionImpl != null) &&
						(frontendTokenDefinitionImpl.getThemeId() != null)) {

						_themeIdFrontendTokenDefinitionImpls.put(
							frontendTokenDefinitionImpl.getThemeId(),
							frontendTokenDefinitionImpl);

						return frontendTokenDefinitionImpl;
					}

					return null;
				}

				@Override
				public void modifiedBundle(
					Bundle bundle, BundleEvent bundleEvent,
					FrontendTokenDefinitionImpl frontendTokenDefinitionImpl) {
				}

				@Override
				public void removedBundle(
					Bundle bundle, BundleEvent bundleEvent,
					FrontendTokenDefinitionImpl frontendTokenDefinitionImpl) {

					_themeIdFrontendTokenDefinitionImpls.remove(
						frontendTokenDefinitionImpl.getThemeId());
				}

			};

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	private Map<Long, Map<String, FrontendTokenDefinition>>
		_companyFrontendTokenDefinitionsMap;
	private final FrontendTokenDefinitionJSONValidator
		_frontendTokenDefinitionJSONValidator =
			new FrontendTokenDefinitionJSONValidator();

	@Reference
	private Portal _portal;

	private ServiceTracker<ThemeCSSCET, ThemeCSSCET> _serviceTracker;
	private final Map<String, FrontendTokenDefinitionImpl>
		_themeIdFrontendTokenDefinitionImpls = new ConcurrentHashMap<>();
	private final DCLSingleton<Map<String, FrontendTokenDefinitionImpl>>
		_themeIdFrontendTokenDefinitionImplsDCLSingleton = new DCLSingleton<>();

}