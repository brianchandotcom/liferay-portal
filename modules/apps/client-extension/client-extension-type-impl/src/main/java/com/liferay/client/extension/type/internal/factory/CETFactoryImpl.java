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
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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

		Date date = new Date(cetConfiguration.buildTimestamp());

		try {
			return cetImplFactory.create(
				baseURL, companyId, date, cetConfiguration.description(),
				externalReferenceCode, date, cetConfiguration.name(),
				_loadProperties(cetConfiguration), true,
				cetConfiguration.sourceCodeURL(),
				WorkflowConstants.STATUS_APPROVED,
				_toTypeSettingsUnicodeProperties(cetConfiguration));
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	@Override
	public CET create(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		long companyId = 0;
		Date createDate = null;
		String description = StringPool.BLANK;
		String externalReferenceCode = StringPool.BLANK;
		Date modifiedDate = null;
		String name = StringPool.BLANK;
		Properties properties = null;
		String sourceCodeURL = StringPool.BLANK;
		int status = WorkflowConstants.STATUS_APPROVED;
		UnicodeProperties unicodeProperties;

		if (clientExtensionEntry != null) {
			companyId = clientExtensionEntry.getCompanyId();
			createDate = clientExtensionEntry.getCreateDate();
			description = clientExtensionEntry.getDescription();
			externalReferenceCode =
				clientExtensionEntry.getExternalReferenceCode();
			modifiedDate = clientExtensionEntry.getModifiedDate();
			name = clientExtensionEntry.getName();

			try {
				properties = PropertiesUtil.load(
					clientExtensionEntry.getProperties());
			}
			catch (IOException ioException) {
				ReflectionUtil.throwException(ioException);
			}

			sourceCodeURL = clientExtensionEntry.getSourceCodeURL();
			status = clientExtensionEntry.getStatus();
			unicodeProperties = UnicodePropertiesBuilder.create(
				true
			).load(
				clientExtensionEntry.getTypeSettings()
			).build();
		}
		else {
			unicodeProperties = UnicodePropertiesBuilder.create(
				true
			).build();
		}

		CETImplFactory cetImplFactory = _getCETImplFactory(
			clientExtensionEntry.getType());

		return cetImplFactory.create(
			StringPool.BLANK, companyId, createDate, description,
			externalReferenceCode, modifiedDate, name, properties, false,
			sourceCodeURL, status, unicodeProperties);
	}

	@Override
	public CET create(PortletRequest portletRequest, String type)
		throws PortalException {

		CETImplFactory cetImplFactory = _getCETImplFactory(type);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			return cetImplFactory.create(
				StringPool.BLANK, themeDisplay.getCompanyId(), null,
				ParamUtil.getString(portletRequest, "description"),
				ParamUtil.getString(portletRequest, "externalReferenceCode"),
				null, ParamUtil.getString(portletRequest, "name"),
				PropertiesUtil.load(
					ParamUtil.getString(portletRequest, "properties")),
				false, ParamUtil.getString(portletRequest, "sourceCodeURL"),
				WorkflowConstants.STATUS_APPROVED,
				cetImplFactory.getUnicodeProperties(portletRequest));
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
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

		CET oldCET = null;

		if (oldTypeSettingsUnicodeProperties != null) {
			oldCET = cetImplFactory.create(
				StringPool.BLANK, 0, null, StringPool.BLANK, StringPool.BLANK,
				null, StringPool.BLANK, null, false, StringPool.BLANK,
				WorkflowConstants.STATUS_APPROVED,
				oldTypeSettingsUnicodeProperties);
		}

		cetImplFactory.validate(
			cetImplFactory.create(
				StringPool.BLANK, 0, null, StringPool.BLANK, StringPool.BLANK,
				null, StringPool.BLANK, null, false, StringPool.BLANK,
				WorkflowConstants.STATUS_APPROVED,
				newTypeSettingsUnicodeProperties),
			oldCET);
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