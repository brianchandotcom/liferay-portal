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

package com.liferay.oauth2.provider.service.impl;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.constants.OAuth2ProviderConstants;
import com.liferay.oauth2.provider.exception.DuplicateOAuth2ApplicationClientIdException;
import com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationClientGrantTypeException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationHomePageURLException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationHomePageURLSchemeException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationNameException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationPrivacyPolicyURLException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationPrivacyPolicyURLSchemeException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationRedirectURIException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationRedirectURIFragmentException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationRedirectURIMissingException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationRedirectURIPathException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationRedirectURISchemeException;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.base.OAuth2ApplicationLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ImageTypeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.awt.image.RenderedImage;

import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class OAuth2ApplicationLocalServiceImpl
	extends OAuth2ApplicationLocalServiceBaseImpl {

	@Override
	public OAuth2Application addOAuth2Application(
			long companyId, long userId, String userName,
			List<GrantType> allowedGrantTypesList, String clientId,
			int clientProfile, String clientSecret, String description,
			List<String> featuresList, String homePageURL, long iconFileEntryId,
			String name, String privacyPolicyURL, List<String> redirectURIsList,
			List<String> scopeAliasesList, ServiceContext serviceContext)
		throws PortalException {

		if (allowedGrantTypesList == null) {
			allowedGrantTypesList = new ArrayList<>();
		}

		clientId = StringUtil.trim(clientId);
		homePageURL = StringUtil.trim(homePageURL);
		name = StringUtil.trim(name);
		privacyPolicyURL = StringUtil.trim(privacyPolicyURL);

		if (redirectURIsList == null) {
			redirectURIsList = new ArrayList<>();
		}

		if (scopeAliasesList == null) {
			scopeAliasesList = new ArrayList<>();
		}

		validate(
			companyId, allowedGrantTypesList, clientId, clientProfile,
			clientSecret, homePageURL, name, privacyPolicyURL,
			redirectURIsList);

		long oAuth2ApplicationId = counterLocalService.increment(
			OAuth2Application.class.getName());

		OAuth2Application oAuth2Application =
			oAuth2ApplicationPersistence.create(oAuth2ApplicationId);

		oAuth2Application.setCompanyId(companyId);
		oAuth2Application.setUserId(userId);
		oAuth2Application.setUserName(userName);
		oAuth2Application.setCreateDate(new Date());
		oAuth2Application.setModifiedDate(new Date());
		oAuth2Application.setAllowedGrantTypesList(allowedGrantTypesList);
		oAuth2Application.setClientId(clientId);
		oAuth2Application.setClientProfile(clientProfile);
		oAuth2Application.setClientSecret(clientSecret);
		oAuth2Application.setDescription(description);
		oAuth2Application.setFeaturesList(featuresList);
		oAuth2Application.setHomePageURL(homePageURL);
		oAuth2Application.setIconFileEntryId(iconFileEntryId);
		oAuth2Application.setName(name);
		oAuth2Application.setPrivacyPolicyURL(privacyPolicyURL);
		oAuth2Application.setRedirectURIsList(redirectURIsList);

		if (ListUtil.isNotEmpty(scopeAliasesList)) {
			OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
				oAuth2ApplicationScopeAliasesLocalService.
					addOAuth2ApplicationScopeAliases(
						companyId, userId, userName, oAuth2ApplicationId,
						scopeAliasesList);

			oAuth2Application.setOAuth2ApplicationScopeAliasesId(
				oAuth2ApplicationScopeAliases.
					getOAuth2ApplicationScopeAliasesId());
		}

		resourceLocalService.addResources(
			oAuth2Application.getCompanyId(), 0, oAuth2Application.getUserId(),
			OAuth2Application.class.getName(),
			oAuth2Application.getOAuth2ApplicationId(), false, false, false);

		return oAuth2ApplicationPersistence.update(oAuth2Application);
	}

	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		String ianaRegisteredSchemes = PropsUtil.get(
			"iana.registered.uri.schemes");

		if (!Validator.isBlank(ianaRegisteredSchemes)) {
			_ianaRegisteredUriSchemes = new HashSet<>(
				Arrays.asList(StringUtil.split(ianaRegisteredSchemes)));
		}
	}

	@Override
	public OAuth2Application deleteOAuth2Application(long oAuth2ApplicationId)
		throws PortalException {

		List<OAuth2Authorization> oAuth2Authorizations =
			oAuth2AuthorizationLocalService.getOAuth2Authorizations(
				oAuth2ApplicationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		for (OAuth2Authorization oAuth2Authorization : oAuth2Authorizations) {
			oAuth2AuthorizationLocalService.deleteOAuth2Authorization(
				oAuth2Authorization.getOAuth2AuthorizationId());
		}

		List<OAuth2ApplicationScopeAliases> oAuth2ApplicationScopeAliaseses =
			oAuth2ApplicationScopeAliasesLocalService.
				getOAuth2ApplicationScopeAliaseses(
					oAuth2ApplicationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

		for (OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases :
				oAuth2ApplicationScopeAliaseses) {

			oAuth2ApplicationScopeAliasesLocalService.
				deleteOAuth2ApplicationScopeAliases(
					oAuth2ApplicationScopeAliases.
						getOAuth2ApplicationScopeAliasesId());
		}

		return oAuth2ApplicationPersistence.remove(oAuth2ApplicationId);
	}

	@Override
	public OAuth2Application fetchOAuth2Application(
		long companyId, String clientId) {

		return oAuth2ApplicationPersistence.fetchByC_C(companyId, clientId);
	}

	@Override
	public OAuth2Application getOAuth2Application(
			long companyId, String clientId)
		throws NoSuchOAuth2ApplicationException {

		return oAuth2ApplicationPersistence.findByC_C(companyId, clientId);
	}

	@Override
	public OAuth2Application updateIcon(
			long oAuth2ApplicationId, InputStream inputStream)
		throws PortalException {

		OAuth2Application oAuth2Application = getOAuth2Application(
			oAuth2ApplicationId);

		long oldIconFileEntryId = oAuth2Application.getIconFileEntryId();

		if (inputStream == null) {
			if (oldIconFileEntryId > 0) {
				PortletFileRepositoryUtil.deletePortletFileEntry(
					oldIconFileEntryId);

				oAuth2Application.setIconFileEntryId(-1);

				oAuth2Application = updateOAuth2Application(oAuth2Application);
			}

			return oAuth2Application;
		}

		Group group = groupLocalService.getCompanyGroup(
			oAuth2Application.getCompanyId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			group.getGroupId(), OAuth2ProviderConstants.SERVICE_NAME,
			serviceContext);

		Folder folder = PortletFileRepositoryUtil.addPortletFolder(
			userLocalService.getDefaultUserId(oAuth2Application.getCompanyId()),
			repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "icons",
			serviceContext);

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try {
			ImageBag imageBag = ImageToolUtil.read(inputStream);

			RenderedImage renderedImage = imageBag.getRenderedImage();

			if (renderedImage == null) {
				throw new ImageTypeException("Unable to read icon");
			}

			renderedImage = ImageToolUtil.scale(renderedImage, 160, 160);

			ImageToolUtil.write(
				renderedImage, imageBag.getType(), unsyncByteArrayOutputStream);
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}

		String fileName = PortletFileRepositoryUtil.getUniqueFileName(
			group.getGroupId(), folder.getFolderId(),
			oAuth2Application.getClientId());

		FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			group.getGroupId(), oAuth2Application.getUserId(),
			OAuth2Application.class.getName(),
			oAuth2Application.getOAuth2ApplicationId(),
			OAuth2ProviderConstants.SERVICE_NAME, folder.getFolderId(),
			new UnsyncByteArrayInputStream(
				unsyncByteArrayOutputStream.toByteArray()),
			fileName, null, false);

		oAuth2Application.setIconFileEntryId(fileEntry.getFileEntryId());

		oAuth2Application = updateOAuth2Application(oAuth2Application);

		if (oldIconFileEntryId > 0) {
			PortletFileRepositoryUtil.deletePortletFileEntry(
				oldIconFileEntryId);
		}

		return oAuth2Application;
	}

	@Override
	public OAuth2Application updateOAuth2Application(
			long oAuth2ApplicationId, List<GrantType> allowedGrantTypesList,
			String clientId, int clientProfile, String clientSecret,
			String description, List<String> featuresList, String homePageURL,
			long iconFileEntryId, String name, String privacyPolicyURL,
			List<String> redirectURIsList, long auth2ApplicationScopeAliasesId,
			ServiceContext serviceContext)
		throws PortalException {

		OAuth2Application oAuth2Application =
			oAuth2ApplicationPersistence.findByPrimaryKey(oAuth2ApplicationId);

		clientId = StringUtil.trim(clientId);
		homePageURL = StringUtil.trim(homePageURL);
		name = StringUtil.trim(name);
		privacyPolicyURL = StringUtil.trim(privacyPolicyURL);

		if (redirectURIsList == null) {
			redirectURIsList = new ArrayList<>();
		}

		validate(
			oAuth2Application.getCompanyId(), oAuth2ApplicationId,
			allowedGrantTypesList, clientId, clientProfile, clientSecret,
			homePageURL, name, privacyPolicyURL, redirectURIsList);

		oAuth2Application.setModifiedDate(new Date());
		oAuth2Application.setOAuth2ApplicationScopeAliasesId(
			auth2ApplicationScopeAliasesId);
		oAuth2Application.setAllowedGrantTypesList(allowedGrantTypesList);
		oAuth2Application.setClientId(clientId);
		oAuth2Application.setClientProfile(clientProfile);
		oAuth2Application.setClientSecret(clientSecret);
		oAuth2Application.setDescription(description);
		oAuth2Application.setFeaturesList(featuresList);
		oAuth2Application.setHomePageURL(homePageURL);
		oAuth2Application.setIconFileEntryId(iconFileEntryId);
		oAuth2Application.setName(name);
		oAuth2Application.setPrivacyPolicyURL(privacyPolicyURL);
		oAuth2Application.setRedirectURIsList(redirectURIsList);

		return oAuth2ApplicationPersistence.update(oAuth2Application);
	}

	@Override
	public OAuth2Application updateScopeAliases(
			long userId, String userName, long oAuth2ApplicationId,
			List<String> scopeAliasesList)
		throws PortalException {

		OAuth2Application oAuth2Application =
			oAuth2ApplicationPersistence.findByPrimaryKey(oAuth2ApplicationId);

		if (ListUtil.isEmpty(scopeAliasesList)) {
			if (oAuth2Application.getOAuth2ApplicationScopeAliasesId() == 0) {
				return oAuth2Application;
			}

			oAuth2Application.setModifiedDate(new Date());
			oAuth2Application.setOAuth2ApplicationScopeAliasesId(0);

			return oAuth2ApplicationPersistence.update(oAuth2Application);
		}

		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
			oAuth2ApplicationScopeAliasesLocalService.
				fetchOAuth2ApplicationScopeAliases(
					oAuth2ApplicationId, scopeAliasesList);

		if (oAuth2ApplicationScopeAliases != null) {
			oAuth2ApplicationScopeAliases.setUserId(userId);
			oAuth2ApplicationScopeAliases.setUserName(userName);

			oAuth2ApplicationScopeAliases =
				oAuth2ApplicationScopeAliasesLocalService.
					updateOAuth2ApplicationScopeAliases(
						oAuth2ApplicationScopeAliases);
		}
		else {
			oAuth2ApplicationScopeAliases =
				oAuth2ApplicationScopeAliasesLocalService.
					addOAuth2ApplicationScopeAliases(
						oAuth2Application.getCompanyId(), userId, userName,
						oAuth2ApplicationId, scopeAliasesList);
		}

		if (oAuth2Application.getOAuth2ApplicationScopeAliasesId() !=
				oAuth2ApplicationScopeAliases.
					getOAuth2ApplicationScopeAliasesId()) {

			oAuth2Application.setModifiedDate(new Date());
			oAuth2Application.setOAuth2ApplicationScopeAliasesId(
				oAuth2ApplicationScopeAliases.
					getOAuth2ApplicationScopeAliasesId());

			return oAuth2ApplicationPersistence.update(oAuth2Application);
		}

		return oAuth2Application;
	}

	protected void validate(
			long companyId, List<GrantType> allowedGrantTypesList,
			String clientId, int clientProfile, String clientSecret,
			String homePageURL, String name, String privacyPolicyURL,
			List<String> redirectURIsList)
		throws PortalException {

		validate(
			companyId, 0, allowedGrantTypesList, clientId, clientProfile,
			clientSecret, homePageURL, name, privacyPolicyURL,
			redirectURIsList);
	}

	protected void validate(
			long companyId, long oAuth2ApplicationId,
			List<GrantType> allowedGrantTypesList, String clientId,
			int clientProfile, String clientSecret, String homePageURL,
			String name, String privacyPolicyURL, List<String> redirectURIsList)
		throws PortalException {

		if (!Validator.isBlank(clientSecret)) {
			for (GrantType grantType : allowedGrantTypesList) {
				if (!grantType.isSupportsConfidentialClients()) {
					throw new
						OAuth2ApplicationClientGrantTypeException(
							grantType.name());
				}
			}
		}
		else {
			for (GrantType grantType : allowedGrantTypesList) {
				if (!grantType.isSupportsPublicClients()) {
					throw new
						OAuth2ApplicationClientGrantTypeException(
							grantType.name());
				}
			}
		}

		OAuth2Application existingOAuth2Application =
			oAuth2ApplicationPersistence.fetchByC_C(companyId, clientId);

		if ((existingOAuth2Application != null) &&
			(existingOAuth2Application.getOAuth2ApplicationId() !=
				oAuth2ApplicationId)) {

			throw new DuplicateOAuth2ApplicationClientIdException();
		}

		if (!Validator.isBlank(homePageURL)) {
			if (!StringUtil.startsWith(homePageURL, Http.HTTP_WITH_SLASH) &&
				!StringUtil.startsWith(homePageURL, Http.HTTPS_WITH_SLASH)) {

				throw new OAuth2ApplicationHomePageURLSchemeException();
			}

			if (!Validator.isUri(homePageURL)) {
				throw new OAuth2ApplicationHomePageURLException();
			}
		}

		if (Validator.isBlank(name)) {
			throw new OAuth2ApplicationNameException();
		}

		if (!Validator.isBlank(privacyPolicyURL)) {
			if (!StringUtil.startsWith(
					privacyPolicyURL, Http.HTTP_WITH_SLASH) &&
				!StringUtil.startsWith(
					privacyPolicyURL, Http.HTTPS_WITH_SLASH)) {

				throw new OAuth2ApplicationPrivacyPolicyURLSchemeException();
			}

			if (!Validator.isUri(privacyPolicyURL)) {
				throw new OAuth2ApplicationPrivacyPolicyURLException();
			}
		}

		if (redirectURIsList.isEmpty()) {
			for (GrantType grantType : allowedGrantTypesList) {
				if (grantType.isRequiresRedirectURI()) {
					throw new OAuth2ApplicationRedirectURIMissingException(
						grantType.name());
				}
			}
		}

		for (String redirectURI : redirectURIsList) {
			try {
				URI uri = new URI(redirectURI);

				if (uri.getFragment() != null) {
					throw new OAuth2ApplicationRedirectURIFragmentException(
						redirectURI);
				}

				String scheme = uri.getScheme();

				if (scheme == null) {
					throw new OAuth2ApplicationRedirectURISchemeException(
						redirectURI);
				}

				scheme = StringUtil.toLowerCase(scheme);

				if (!Objects.equals(scheme, Http.HTTP) &&
					!Objects.equals(scheme, Http.HTTPS) &&
					_ianaRegisteredUriSchemes.contains(scheme)) {

					throw new OAuth2ApplicationHomePageURLSchemeException(
						redirectURI);
				}

				String path = uri.getPath();

				String normalizedPath = HttpUtil.normalizePath(path);

				if (!Objects.equals(path, normalizedPath)) {
					throw new OAuth2ApplicationRedirectURIPathException(
						redirectURI);
				}
			}
			catch (URISyntaxException urise) {
				throw new OAuth2ApplicationRedirectURIException(
					redirectURI, urise);
			}
		}
	}

	@ServiceReference(type = PortalCapabilityLocator.class)
	protected PortalCapabilityLocator portalCapabilityLocator;

	private static Set<String> _ianaRegisteredUriSchemes = SetUtil.fromArray(
		new String[] {
			"aaa", "aaas", "about", "acap", "acct", "acr", "adiumxtra", "afp",
			"afs", "aim", "appdata", "apt", "attachment", "aw", "barion",
			"beshare", "bitcoin", "blob", "bolo", "browserext", "callto", "cap",
			"chrome", "chrome-extension", "cid", "coap", "coap+tcp", "coap+ws",
			"coaps", "coaps+tcp", "coaps+ws", "com-eventbrite-attendee",
			"content", "conti", "crid", "cvs", "data", "dav", "diaspora",
			"dict", "dis", "dlna-playcontainer", "dlna-playsingle", "dns",
			"dntp", "dtn", "dvb", "ed2k", "example", "facetime", "fax", "feed",
			"feedready", "file", "filesystem", "finger", "fish", "ftp", "geo",
			"gg", "git", "gizmoproject", "go", "gopher", "graph", "gtalk",
			"h323", "ham", "hcp", "http", "https", "hxxp", "hxxps", "hydrazone",
			"iax", "icap", "icon", "im", "imap", "info", "iotdisco", "ipn",
			"ipp", "ipps", "irc", "irc6", "ircs", "iris", "iris.beep",
			"iris.lwz", "iris.xpc", "iris.xpcs", "isostore", "itms", "jabber",
			"jar", "jms", "keyparc", "lastfm", "ldap", "ldaps", "lvlt",
			"magnet", "mailserver", "mailto", "maps", "market", "message",
			"mid", "mms", "modem", "mongodb", "moz", "ms-access",
			"ms-browser-extension", "ms-drive-to", "ms-enrollment", "ms-excel",
			"ms-gamebarservices", "ms-gamingoverlay", "ms-getoffice", "ms-help",
			"ms-infopath", "ms-inputapp", "ms-lockscreencomponent-config",
			"ms-media-stream-id", "ms-mixedrealitycapture", "ms-officeapp",
			"ms-people", "ms-project", "ms-powerpoint", "ms-publisher",
			"ms-restoretabcompanion", "ms-search-repair",
			"ms-secondary-screen-controller", "ms-secondary-screen-setup",
			"ms-settings", "ms-settings-airplanemode", "ms-settings-bluetooth",
			"ms-settings-camera", "ms-settings-cellular",
			"ms-settings-cloudstorage", "ms-settings-connectabledevices",
			"ms-settings-displays-topology", "ms-settings-emailandaccounts",
			"ms-settings-language", "ms-settings-location", "ms-settings-lock",
			"ms-settings-nfctransactions", "ms-settings-notifications",
			"ms-settings-power", "ms-settings-privacy", "ms-settings-proximity",
			"ms-settings-screenrotation", "ms-settings-wifi",
			"ms-settings-workplace", "ms-spd", "ms-sttoverlay", "ms-transit-to",
			"ms-useractivityset", "ms-virtualtouchpad", "ms-visio",
			"ms-walk-to", "ms-whiteboard", "ms-whiteboard-cmd", "ms-word",
			"msnim", "msrp", "msrps", "mtqp", "mumble", "mupdate", "mvn",
			"news", "nfs", "ni", "nih", "nntp", "notes", "ocf", "oid",
			"onenote", "onenote-cmd", "opaquelocktoken", "pack", "palm",
			"paparazzi", "pkcs11", "platform", "pop", "pres", "prospero",
			"proxy", "pwid", "psyc", "qb", "query", "redis", "rediss", "reload",
			"res", "resource", "rmi", "rsync", "rtmfp", "rtmp", "rtsp", "rtsps",
			"rtspu", "secondlife", "service", "session", "sftp", "sgn", "shttp",
			"sieve", "sip", "sips", "skype", "smb", "sms", "smtp", "snews",
			"snmp", "soap.beep", "soap.beeps", "soldat", "spiffe", "spotify",
			"ssh", "steam", "stun", "stuns", "submit", "svn", "tag",
			"teamspeak", "tel", "teliaeid", "telnet", "tftp", "things",
			"thismessage", "tip", "tn3270", "tool", "turn", "turns", "tv",
			"udp", "unreal", "urn", "ut2004", "v-event", "vemmi", "ventrilo",
			"videotex", "vnc", "view-source", "wais", "webcal", "wpid", "ws",
			"wss", "wtai", "wyciwyg", "xcon", "xcon-userid", "xfire",
			"xmlrpc.beep", "xmlrpc.beeps", "xmpp", "xri", "ymsgr", "z39.50",
			"z39.50r", "z39.50s"
		});

}