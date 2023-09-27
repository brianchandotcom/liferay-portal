/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.exception.ClientExtensionEntryTypeException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.configuration.CETConfiguration;
import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.io.IOException;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = CETFactory.class)
public class CETFactoryImpl implements CETFactory {

	public CETFactoryImpl() {
		_cetImplFactories = HashMapBuilder.<String, CETImplFactory>put(
			ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT,
			new CustomElementCETImplFactoryImpl()
		).put(
			ClientExtensionEntryConstants.TYPE_FDS_CELL_RENDERER,
			new FDSCellRendererCETImplFactoryImpl()
		).put(
			ClientExtensionEntryConstants.TYPE_FDS_FILTER,
			new FDSFilterCETImplFactoryImpl()
		).put(
			ClientExtensionEntryConstants.TYPE_GLOBAL_CSS,
			new GlobalCSSCETImplFactoryImpl()
		).put(
			ClientExtensionEntryConstants.TYPE_GLOBAL_JS,
			new GlobalJSCETImplFactoryImpl()
		).put(
			ClientExtensionEntryConstants.TYPE_IFRAME,
			new IFrameCETImplFactoryImpl()
		).put(
			ClientExtensionEntryConstants.TYPE_JS_IMPORT_MAPS_ENTRY,
			new JSImportMapsEntryCETImplFactoryImpl()
		).put(
			ClientExtensionEntryConstants.TYPE_STATIC_CONTENT,
			new StaticContentCETImplFactoryImpl()
		).put(
			ClientExtensionEntryConstants.TYPE_THEME_CSS,
			 new ThemeCSSCETImplFactoryImpl()
		).put(
			ClientExtensionEntryConstants.TYPE_THEME_SPRITEMAP,
			 new ThemeSpritemapCETImplFactoryImpl()
		).put(
			ClientExtensionEntryConstants.TYPE_THEME_FAVICON,
			new ThemeFaviconCETImplFactoryImpl()

		// TODO

		/*).put(
			ClientExtensionEntryConstants.TYPE_THEME_JS,
			new ThemeJSCETImplFactoryImpl()*/

		).build();

		_types = Collections.unmodifiableSortedSet(
			new TreeSet<>(_cetImplFactories.keySet()));
	}

	@Override
	public CET create(
			CETConfiguration cetConfiguration, long companyId,
			String externalReferenceCode)
		throws PortalException {

		CETImplFactory cetImplFactory = _getCETImplFactory(
			cetConfiguration.type());

		String baseURL = cetConfiguration.baseURL();

		// TODO Use AbsolutePortalURLBuilder

		baseURL = baseURL.replaceAll(
			Pattern.quote("${portalURL}"), _portal.getPathContext());

		if (baseURL.endsWith(StringPool.SLASH)) {
			baseURL = baseURL.substring(0, baseURL.length() - 1);
		}

		try {
			return cetImplFactory.create(
				baseURL, companyId, cetConfiguration.description(),
				externalReferenceCode, cetConfiguration.name(),
				_loadProperties(cetConfiguration),
				cetConfiguration.sourceCodeURL(),
				_toTypeSettingsUnicodeProperties(cetConfiguration));
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	@Override
	public CET create(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		CETImplFactory cetImplFactory = _getCETImplFactory(
			clientExtensionEntry.getType());

		return cetImplFactory.create(clientExtensionEntry);
	}

	@Override
	public CET create(PortletRequest portletRequest, String type)
		throws PortalException {

		CETImplFactory cetImplFactory = _getCETImplFactory(type);

		return cetImplFactory.create(portletRequest);
	}

	@Override
	public Collection<String> getTypes() {
		return _types;
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties, String type)
		throws PortalException {

		CETImplFactory cetImplFactory = _getCETImplFactory(type);

		cetImplFactory.validate(
			newTypeSettingsUnicodeProperties, oldTypeSettingsUnicodeProperties);
	}

	private CETImplFactory _getCETImplFactory(String type)
		throws ClientExtensionEntryTypeException {

		CETImplFactory cetImplFactory = _cetImplFactories.get(type);

		if (cetImplFactory != null) {
			String key = FEATURE_FLAG_KEYS.get(type);

			if ((key == null) || FeatureFlagManagerUtil.isEnabled(key)) {
				return cetImplFactory;
			}
		}

		throw new ClientExtensionEntryTypeException("Unknown type " + type);
	}

	private Properties _loadProperties(CETConfiguration cetConfiguration)
		throws IOException {

		String[] properties = cetConfiguration.properties();

		if (properties == null) {
			return new Properties();
		}

		return PropertiesUtil.load(
			StringUtil.merge(properties, StringPool.NEW_LINE));
	}

	private UnicodeProperties _toTypeSettingsUnicodeProperties(
		CETConfiguration cetConfiguration) {

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.create(
				true
			).build();

		String[] typeSettings = cetConfiguration.typeSettings();

		if (typeSettings == null) {
			return typeSettingsUnicodeProperties;
		}

		for (String typeSetting : typeSettings) {
			typeSettingsUnicodeProperties.put(typeSetting);
		}

		return typeSettingsUnicodeProperties;
	}

	private final Map<String, CETImplFactory> _cetImplFactories;

	@Reference
	private Portal _portal;

	private final Set<String> _types;

}