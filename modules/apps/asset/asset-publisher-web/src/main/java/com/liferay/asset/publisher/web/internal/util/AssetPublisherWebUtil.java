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

package com.liferay.asset.publisher.web.internal.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.configuration.AssetPublisherPortletInstanceConfiguration;
import com.liferay.asset.publisher.web.util.AssetPublisherUtil;
import com.liferay.asset.util.AssetEntryQueryProcessor;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.StrictPortletPreferencesImpl;
import com.liferay.sites.kernel.util.SitesUtil;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = AssetPublisherWebUtil.class)
public class AssetPublisherWebUtil {

	public void addAndStoreSelection(
			PortletRequest portletRequest, String className, long classPK,
			int assetEntryOrder)
		throws Exception {

		String portletId = _portal.getPortletId(portletRequest);

		String rootPortletId = PortletIdCodec.decodePortletName(portletId);

		if (!rootPortletId.equals(AssetPublisherPortletKeys.ASSET_PUBLISHER)) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = _layoutLocalService.fetchLayout(themeDisplay.getPlid());

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, portletId);

		if (portletPreferences instanceof StrictPortletPreferencesImpl) {
			return;
		}

		String selectionStyle = portletPreferences.getValue(
			"selectionStyle", "dynamic");

		if (selectionStyle.equals("dynamic")) {
			return;
		}

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			className, classPK);

		addSelection(
			portletPreferences, assetEntry.getEntryId(), assetEntryOrder,
			className);

		portletPreferences.store();
	}

	public void addSelection(
			PortletPreferences portletPreferences, long assetEntryId,
			int assetEntryOrder, String assetEntryType)
		throws Exception {

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			assetEntryId);

		String[] assetEntryXmls = portletPreferences.getValues(
			"assetEntryXml", new String[0]);

		String assetEntryXml = _getAssetEntryXml(
			assetEntryType, assetEntry.getClassUuid());

		if (!ArrayUtil.contains(assetEntryXmls, assetEntryXml)) {
			if (assetEntryOrder > -1) {
				assetEntryXmls[assetEntryOrder] = assetEntryXml;
			}
			else {
				assetEntryXmls = ArrayUtil.append(
					assetEntryXmls, assetEntryXml);
			}

			portletPreferences.setValues("assetEntryXml", assetEntryXmls);
		}

		try {
			portletPreferences.store();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addSelection(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws Exception {

		long assetEntryId = ParamUtil.getLong(portletRequest, "assetEntryId");
		int assetEntryOrder = ParamUtil.getInteger(
			portletRequest, "assetEntryOrder");
		String assetEntryType = ParamUtil.getString(
			portletRequest, "assetEntryType");

		addSelection(
			portletPreferences, assetEntryId, assetEntryOrder, assetEntryType);
	}

	public String encodeName(
		long ddmStructureId, String fieldName, Locale locale) {

		return _ddmIndexer.encodeName(ddmStructureId, fieldName, locale);
	}

	public String filterAssetTagNames(long groupId, String assetTagNames) {
		List<String> filteredAssetTagNames = new ArrayList<>();

		String[] assetTagNamesArray = StringUtil.split(assetTagNames);

		long[] assetTagIds = _assetTagLocalService.getTagIds(
			groupId, assetTagNamesArray);

		for (long assetTagId : assetTagIds) {
			AssetTag assetTag = _assetTagLocalService.fetchAssetTag(assetTagId);

			if (assetTag != null) {
				filteredAssetTagNames.add(assetTag.getName());
			}
		}

		return StringUtil.merge(filteredAssetTagNames);
	}

	public String getClassName(AssetRendererFactory<?> assetRendererFactory) {
		Class<?> clazz = assetRendererFactory.getClass();

		String className = clazz.getName();

		int pos = className.lastIndexOf(StringPool.PERIOD);

		return className.substring(pos + 1);
	}

	public Long[] getClassTypeIds(
		PortletPreferences portletPreferences, String className,
		List<ClassType> availableClassTypes) {

		Long[] availableClassTypeIds = new Long[availableClassTypes.size()];

		for (int i = 0; i < availableClassTypeIds.length; i++) {
			ClassType classType = availableClassTypes.get(i);

			availableClassTypeIds[i] = classType.getClassTypeId();
		}

		return _getClassTypeIds(
			portletPreferences, className, availableClassTypeIds);
	}

	public String getDefaultAssetPublisherId(Layout layout) {
		return layout.getTypeSettingsProperty(
			LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID,
			StringPool.BLANK);
	}

	public Map<Locale, String> getEmailAssetEntryAddedBodyMap(
		PortletPreferences portletPreferences) {

		LocalizedValuesMap emailAssetEntryAddedLocalizedBodyMap =
			_assetPublisherPortletInstanceConfiguration.
				emailAssetEntryAddedBody();

		Map<Locale, String> emailAssetEntryAddedBodyMap =
			LocalizationUtil.getLocalizationMap(
				portletPreferences, "emailAssetEntryAddedBody",
				StringPool.BLANK, StringPool.BLANK,
				AssetPublisherWebUtil.class.getClassLoader());

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		if (Validator.isNull(emailAssetEntryAddedBodyMap.get(defaultLocale))) {
			emailAssetEntryAddedBodyMap.put(
				defaultLocale,
				emailAssetEntryAddedLocalizedBodyMap.getDefaultValue());
		}

		return emailAssetEntryAddedBodyMap;
	}

	public boolean getEmailAssetEntryAddedEnabled(
		PortletPreferences portletPreferences) {

		String emailAssetEntryAddedEnabled = portletPreferences.getValue(
			"emailAssetEntryAddedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailAssetEntryAddedEnabled)) {
			return GetterUtil.getBoolean(emailAssetEntryAddedEnabled);
		}

		return _assetPublisherPortletInstanceConfiguration.
			emailAssetEntryAddedEnabled();
	}

	public Map<Locale, String> getEmailAssetEntryAddedSubjectMap(
		PortletPreferences portletPreferences) {

		LocalizedValuesMap emailAssetEntryAddedLocalizedSubjectMap =
			_assetPublisherPortletInstanceConfiguration.
				emailAssetEntryAddedSubject();

		Map<Locale, String> emailAssetEntryAddedSubjectMap =
			LocalizationUtil.getLocalizationMap(
				portletPreferences, "emailAssetEntryAddedSubject",
				StringPool.BLANK, StringPool.BLANK,
				AssetPublisherWebUtil.class.getClassLoader());

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		if (Validator.isNull(
				emailAssetEntryAddedSubjectMap.get(defaultLocale))) {

			emailAssetEntryAddedSubjectMap.put(
				defaultLocale,
				emailAssetEntryAddedLocalizedSubjectMap.getDefaultValue());
		}

		return emailAssetEntryAddedSubjectMap;
	}

	public Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms = new LinkedHashMap<>();

		definitionTerms.put(
			"[$ASSET_ENTRIES$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-list-of-assets"));
		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-id-associated-with-the-assets"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-mx-associated-with-the-assets"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-name-associated-with-the-assets"));
		definitionTerms.put(
			"[$FROM_ADDRESS$]", HtmlUtil.escape(emailFromAddress));
		definitionTerms.put("[$FROM_NAME$]", HtmlUtil.escape(emailFromName));

		Company company = themeDisplay.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		definitionTerms.put(
			"[$PORTLET_NAME$]",
			HtmlUtil.escape(
				_portal.getPortletTitle(
					AssetPublisherPortletKeys.ASSET_PUBLISHER,
					themeDisplay.getLocale())));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		definitionTerms.put(
			"[$PORTLET_TITLE$]", HtmlUtil.escape(portletDisplay.getTitle()));

		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-site-name-associated-with-the-assets"));
		definitionTerms.put(
			"[$TO_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-address-of-the-email-recipient"));
		definitionTerms.put(
			"[$TO_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-name-of-the-email-recipient"));

		return definitionTerms;
	}

	public String getEmailFromAddress(
		PortletPreferences portletPreferences, long companyId) {

		return _portal.getEmailFromAddress(
			portletPreferences, companyId,
			_assetPublisherPortletInstanceConfiguration.emailFromAddress());
	}

	public String getEmailFromName(
		PortletPreferences portletPreferences, long companyId) {

		return _portal.getEmailFromName(
			portletPreferences, companyId,
			_assetPublisherPortletInstanceConfiguration.emailFromName());
	}

	public long getSubscriptionClassPK(
			long ownerId, int ownerType, long plid, String portletId)
		throws PortalException {

		if (PortletIdCodec.hasUserId(portletId)) {
			ownerId = PortletIdCodec.decodeUserId(portletId);
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}

		com.liferay.portal.kernel.model.PortletPreferences
			portletPreferencesModel =
				_portletPreferencesLocalService.getPortletPreferences(
					ownerId, ownerType, plid, portletId);

		return portletPreferencesModel.getPortletPreferencesId();
	}

	public long getSubscriptionClassPK(long plid, String portletId)
		throws PortalException {

		return getSubscriptionClassPK(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId);
	}

	public boolean isDefaultAssetPublisher(
		Layout layout, String portletId, String portletResource) {

		String defaultAssetPublisherPortletId = getDefaultAssetPublisherId(
			layout);

		if (Validator.isNull(defaultAssetPublisherPortletId)) {
			return false;
		}

		if (defaultAssetPublisherPortletId.equals(portletId) ||
			defaultAssetPublisherPortletId.equals(portletResource)) {

			return true;
		}

		return false;
	}

	public boolean isScopeIdSelectable(
			PermissionChecker permissionChecker, String scopeId,
			long companyGroupId, Layout layout, boolean checkPermission)
		throws PortalException {

		long groupId = _assetPublisherUtil.getGroupIdFromScopeId(
			scopeId, layout.getGroupId(), layout.isPrivateLayout());

		if (scopeId.startsWith(
				AssetPublisherUtil.SCOPE_ID_CHILD_GROUP_PREFIX)) {

			Group group = _groupLocalService.getGroup(groupId);

			if (!group.hasAncestor(layout.getGroupId())) {
				return false;
			}
		}
		else if (scopeId.startsWith(
					 AssetPublisherUtil.SCOPE_ID_PARENT_GROUP_PREFIX)) {

			Group siteGroup = layout.getGroup();

			if (!siteGroup.hasAncestor(groupId)) {
				return false;
			}

			Group group = _groupLocalService.getGroup(groupId);

			if (SitesUtil.isContentSharingWithChildrenEnabled(group)) {
				return true;
			}

			if (!PrefsPropsUtil.getBoolean(
					layout.getCompanyId(),
					PropsKeys.
						SITES_CONTENT_SHARING_THROUGH_ADMINISTRATORS_ENABLED)) {

				return false;
			}

			if (checkPermission) {
				return GroupPermissionUtil.contains(
					permissionChecker, group, ActionKeys.UPDATE);
			}
		}
		else if ((groupId != companyGroupId) && checkPermission) {
			return GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.UPDATE);
		}

		return true;
	}

	public boolean isSubscribed(
			long companyId, long userId, long plid, String portletId)
		throws PortalException {

		return _subscriptionLocalService.isSubscribed(
			companyId, userId,
			com.liferay.portal.kernel.model.PortletPreferences.class.getName(),
			getSubscriptionClassPK(plid, portletId));
	}

	public void processAssetEntryQuery(
			User user, PortletPreferences portletPreferences,
			AssetEntryQuery assetEntryQuery)
		throws Exception {

		for (AssetEntryQueryProcessor assetEntryQueryProcessor :
				_assetEntryQueryProcessors) {

			assetEntryQueryProcessor.processAssetEntryQuery(
				user, portletPreferences, assetEntryQuery);
		}
	}

	public void subscribe(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId)
		throws PortalException {

		Layout layout = _layoutLocalService.fetchLayout(plid);

		PortletPermissionUtil.check(
			permissionChecker, 0, layout, portletId, ActionKeys.SUBSCRIBE,
			false, false);

		_subscriptionLocalService.addSubscription(
			permissionChecker.getUserId(), groupId,
			com.liferay.portal.kernel.model.PortletPreferences.class.getName(),
			getSubscriptionClassPK(plid, portletId));
	}

	public void unsubscribe(
			PermissionChecker permissionChecker, long plid, String portletId)
		throws PortalException {

		Layout layout = _layoutLocalService.fetchLayout(plid);

		PortletPermissionUtil.check(
			permissionChecker, 0, layout, portletId, ActionKeys.SUBSCRIBE,
			false, false);

		_subscriptionLocalService.deleteSubscription(
			permissionChecker.getUserId(),
			com.liferay.portal.kernel.model.PortletPreferences.class.getName(),
			getSubscriptionClassPK(plid, portletId));
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties)
		throws ConfigurationException {

		_assetPublisherPortletInstanceConfiguration =
			ConfigurationProviderUtil.getSystemConfiguration(
				AssetPublisherPortletInstanceConfiguration.class);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setAssetEntryQueryProcessor(
		AssetEntryQueryProcessor assetEntryQueryProcessor) {

		_assetEntryQueryProcessors.add(assetEntryQueryProcessor);
	}

	protected void unsetAssetEntryQueryProcessor(
		AssetEntryQueryProcessor assetEntryQueryProcessor) {

		_assetEntryQueryProcessors.remove(assetEntryQueryProcessor);
	}

	private String _getAssetEntryXml(
		String assetEntryType, String assetEntryUuid) {

		String xml = null;

		try {
			Document document = SAXReaderUtil.createDocument(StringPool.UTF8);

			Element assetEntryElement = document.addElement("asset-entry");

			Element assetEntryTypeElement = assetEntryElement.addElement(
				"asset-entry-type");

			assetEntryTypeElement.addText(assetEntryType);

			Element assetEntryUuidElement = assetEntryElement.addElement(
				"asset-entry-uuid");

			assetEntryUuidElement.addText(assetEntryUuid);

			xml = document.formattedString(StringPool.BLANK);
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe, ioe);
			}
		}

		return xml;
	}

	private Long[] _getClassTypeIds(
		PortletPreferences portletPreferences, String className,
		Long[] availableClassTypeIds) {

		boolean anyAssetType = GetterUtil.getBoolean(
			portletPreferences.getValue(
				"anyClassType" + className, Boolean.TRUE.toString()));

		if (anyAssetType) {
			return availableClassTypeIds;
		}

		long defaultClassTypeId = GetterUtil.getLong(
			portletPreferences.getValue("anyClassType" + className, null), -1);

		if (defaultClassTypeId > -1) {
			return new Long[] {defaultClassTypeId};
		}

		Long[] classTypeIds = ArrayUtil.toArray(
			StringUtil.split(
				portletPreferences.getValue("classTypeIds" + className, null),
				0L));

		if (classTypeIds != null) {
			return classTypeIds;
		}

		return availableClassTypeIds;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetPublisherWebUtil.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	private final List<AssetEntryQueryProcessor> _assetEntryQueryProcessors =
		new CopyOnWriteArrayList<>();
	private AssetPublisherPortletInstanceConfiguration
		_assetPublisherPortletInstanceConfiguration;

	@Reference
	private AssetPublisherUtil _assetPublisherUtil;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private DDMIndexer _ddmIndexer;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}