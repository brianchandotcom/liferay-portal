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

package com.liferay.journal.internal.util;

import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.constants.JournalStructureConstants;
import com.liferay.journal.internal.transformer.JournalTransformer;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.portlet.ThemeDisplayModel;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Wesley Gong
 * @author Angelo Jefferson
 * @author Hugo Huijser
 */
public class JournalUtil {

	public static final String[] SELECTED_FIELD_NAMES = {
		Field.ARTICLE_ID, Field.COMPANY_ID, Field.GROUP_ID, Field.UID
	};

	public static void addAllReservedEls(
		Element rootElement, Map<String, String> tokens, JournalArticle article,
		String languageId, ThemeDisplay themeDisplay) {

		_addReservedEl(
			rootElement, tokens, JournalStructureConstants.RESERVED_ARTICLE_ID,
			article.getArticleId());

		_addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_VERSION,
			String.valueOf(article.getVersion()));

		_addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_TITLE,
			article.getTitle(languageId));

		_addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_URL_TITLE,
			article.getUrlTitle());

		_addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_DESCRIPTION,
			article.getDescription(languageId));

		_addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_CREATE_DATE,
			article.getCreateDate());

		_addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_MODIFIED_DATE,
			article.getModifiedDate());

		if (article.getDisplayDate() != null) {
			_addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_DISPLAY_DATE,
				article.getDisplayDate());
		}

		String smallImageURL = StringPool.BLANK;

		if (Validator.isNotNull(article.getSmallImageURL())) {
			smallImageURL = article.getSmallImageURL();
		}
		else if ((themeDisplay != null) && article.isSmallImage()) {
			smallImageURL = StringBundler.concat(
				themeDisplay.getPathImage(), "/journal/article?img_id=",
				article.getSmallImageId(), "&t=",
				WebServerServletTokenUtil.getToken(article.getSmallImageId()));
		}

		_addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_SMALL_IMAGE_URL,
			smallImageURL);

		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		_addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_ASSET_TAG_NAMES,
			StringUtil.merge(assetTagNames));

		_addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_AUTHOR_ID,
			String.valueOf(article.getUserId()));

		String userName = StringPool.BLANK;
		String userEmailAddress = StringPool.BLANK;
		String userComments = StringPool.BLANK;
		String userJobTitle = StringPool.BLANK;

		User user = UserLocalServiceUtil.fetchUserById(article.getUserId());

		if (user != null) {
			userName = user.getFullName();
			userEmailAddress = user.getEmailAddress();
			userComments = user.getComments();
			userJobTitle = user.getJobTitle();
		}

		_addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_AUTHOR_NAME, userName);

		_addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_AUTHOR_EMAIL_ADDRESS,
			userEmailAddress);

		_addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_AUTHOR_COMMENTS,
			userComments);

		_addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_AUTHOR_JOB_TITLE,
			userJobTitle);
	}

	public static String getJournalControlPanelLink(
		long folderId, long groupId,
		LiferayPortletResponse liferayPortletResponse) {

		if (liferayPortletResponse != null) {
			return PortletURLBuilder.createRenderURL(
				liferayPortletResponse
			).setParameter(
				"folderId", folderId
			).setParameter(
				"groupId", groupId
			).buildString();
		}

		try {
			String portletId = PortletProviderUtil.getPortletId(
				JournalArticle.class.getName(), PortletProvider.Action.EDIT);

			String articleURL = PortalUtil.getControlPanelFullURL(
				groupId, portletId, null);

			String namespace = PortalUtil.getPortletNamespace(
				JournalPortletKeys.JOURNAL);

			articleURL = HttpUtil.addParameter(
				articleURL, namespace + "groupId", groupId);

			return HttpUtil.addParameter(
				articleURL, namespace + "folderId", folderId);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return StringPool.BLANK;
	}

	public static String getJournalControlPanelLink(
			PortletRequest portletRequest, long folderId)
		throws PortalException {

		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				portletRequest, JournalArticle.class.getName(),
				PortletProvider.Action.EDIT)
		).setParameter(
			"folderId", folderId
		).buildString();
	}

	public static Map<String, String> getTokens(
			JournalArticle article, DDMTemplate ddmTemplate,
			PortletRequestModel portletRequestModel, ThemeDisplay themeDisplay)
		throws PortalException {

		DDMStructure ddmStructure = article.getDDMStructure();

		Map<String, String> tokens = HashMapBuilder.put(
			TemplateConstants.CLASS_NAME_ID,
			String.valueOf(
				ClassNameLocalServiceUtil.getClassNameId(DDMStructure.class))
		).put(
			"article_resource_pk", String.valueOf(article.getResourcePrimKey())
		).put(
			"ddm_structure_id", String.valueOf(ddmStructure.getStructureId())
		).put(
			"ddm_structure_key", ddmStructure.getStructureKey()
		).build();

		if (ddmTemplate != null) {
			tokens.put(
				"ddm_template_id", String.valueOf(ddmTemplate.getTemplateId()));
			tokens.put(
				"ddm_template_key",
				String.valueOf(ddmTemplate.getTemplateKey()));

			Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
				article.getCompanyId());

			if (companyGroup.getGroupId() == ddmTemplate.getGroupId()) {
				tokens.put(
					"company_group_id",
					String.valueOf(companyGroup.getGroupId()));
			}
		}

		if (themeDisplay != null) {
			_populateTokens(tokens, article.getGroupId(), themeDisplay);
		}
		else if (portletRequestModel != null) {
			ThemeDisplayModel themeDisplayModel =
				portletRequestModel.getThemeDisplayModel();

			if (themeDisplayModel != null) {
				try {
					_populateTokens(
						tokens, article.getGroupId(), themeDisplayModel);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception, exception);
					}
				}
			}
		}
		else {
			tokens.put("company_id", String.valueOf(article.getCompanyId()));

			Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
				article.getCompanyId());

			tokens.put(
				"article_group_id", String.valueOf(article.getGroupId()));
			tokens.put(
				"company_group_id", String.valueOf(companyGroup.getGroupId()));
		}

		return tokens;
	}

	public static String getUrlTitle(long id, String title) {
		if (title == null) {
			return String.valueOf(id);
		}

		title = StringUtil.toLowerCase(title.trim());

		if (Validator.isNull(title) || Validator.isNumber(title) ||
			title.equals("rss")) {

			title = String.valueOf(id);
		}
		else {
			title = FriendlyURLNormalizerUtil.normalizeWithPeriodsAndSlashes(
				title);
		}

		return ModelHintsUtil.trimString(
			JournalArticle.class.getName(), "urlTitle", title);
	}

	public static boolean isHead(JournalArticle article) {
		JournalArticle latestArticle =
			JournalArticleLocalServiceUtil.fetchLatestArticle(
				article.getResourcePrimKey(),
				new int[] {
					WorkflowConstants.STATUS_APPROVED,
					WorkflowConstants.STATUS_IN_TRASH
				});

		if ((latestArticle != null) && !latestArticle.isIndexable()) {
			return false;
		}
		else if ((latestArticle != null) &&
				 (article.getId() == latestArticle.getId())) {

			return true;
		}

		return false;
	}

	public static boolean isHeadListable(JournalArticle article) {
		JournalArticle latestArticle =
			JournalArticleLocalServiceUtil.fetchLatestArticle(
				article.getResourcePrimKey(),
				new int[] {
					WorkflowConstants.STATUS_APPROVED,
					WorkflowConstants.STATUS_IN_TRASH,
					WorkflowConstants.STATUS_SCHEDULED
				});

		if ((latestArticle != null) &&
			(article.getId() == latestArticle.getId())) {

			return true;
		}

		return false;
	}

	public static boolean isLatestArticle(JournalArticle article) {
		JournalArticle latestArticle =
			JournalArticleLocalServiceUtil.fetchLatestArticle(
				article.getResourcePrimKey(), WorkflowConstants.STATUS_ANY,
				false);

		if ((latestArticle != null) &&
			(article.getId() == latestArticle.getId())) {

			return true;
		}

		return false;
	}

	public static String removeArticleLocale(
		JournalArticle article, String languageId) {

		Document document = article.getDocument();

		if (document == null) {
			return null;
		}

		Element rootElement = document.getRootElement();

		String availableLocales = rootElement.attributeValue(
			"available-locales");

		if (availableLocales == null) {
			return article.getContent();
		}

		availableLocales = StringUtil.removeFromList(
			availableLocales, languageId);

		if (availableLocales.endsWith(",")) {
			availableLocales = availableLocales.substring(
				0, availableLocales.length() - 1);
		}

		rootElement.addAttribute("available-locales", availableLocales);

		_removeArticleLocale(rootElement, languageId);

		return XMLUtil.formatXML(document);
	}

	public static String transform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId, Document document,
			PortletRequestModel portletRequestModel, String script,
			boolean propagateException, Map<String, Object> contextObjects)
		throws Exception {

		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(
				JournalArticle.class.getName());

		contextObjects.putAll(templateHandler.getCustomContextObjects());

		return _journalTransformer.transform(
			themeDisplay, contextObjects, tokens, viewMode, languageId,
			document, portletRequestModel, script, propagateException);
	}

	private static void _addReservedEl(
		Element rootElement, Map<String, String> tokens, String name,
		Date value) {

		_addReservedEl(rootElement, tokens, name, Time.getRFC822(value));
	}

	private static void _addReservedEl(
		Element rootElement, Map<String, String> tokens, String name,
		String value) {

		// XML

		if (rootElement != null) {
			Element dynamicElementElement = rootElement.addElement(
				"dynamic-element");

			dynamicElementElement.addAttribute("name", name);

			dynamicElementElement.addAttribute("type", "text");

			Element dynamicContentElement = dynamicElementElement.addElement(
				"dynamic-content");

			//dynamicContentElement.setText("<![CDATA[" + value + "]]>");
			dynamicContentElement.setText(value);
		}

		// Tokens

		tokens.put(
			StringUtil.replace(name, CharPool.DASH, CharPool.UNDERLINE), value);
	}

	private static String _getCustomTokenValue(
		String tokenName,
		JournalServiceConfiguration journalServiceConfiguration) {

		for (String tokenValue :
				journalServiceConfiguration.customTokenValues()) {

			String[] tokenValueParts = tokenValue.split("\\=");

			if (tokenValueParts.length != 2) {
				continue;
			}

			if (tokenValueParts[0].equals(tokenName)) {
				return tokenValueParts[1];
			}
		}

		return null;
	}

	private static void _populateCustomTokens(
		Map<String, String> tokens, long companyId) {

		JournalServiceConfiguration journalServiceConfiguration = null;

		try {
			journalServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					JournalServiceConfiguration.class, companyId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		if (journalServiceConfiguration == null) {
			return;
		}

		if (MapUtil.isEmpty(_customTokens)) {
			synchronized (JournalUtil.class) {
				_customTokens = new HashMap<>();

				for (String tokenName :
						journalServiceConfiguration.customTokenNames()) {

					String tokenValue = _getCustomTokenValue(
						tokenName, journalServiceConfiguration);

					if (Validator.isNull(tokenValue)) {
						continue;
					}

					_customTokens.put(tokenName, tokenValue);
				}
			}
		}

		if (!_customTokens.isEmpty()) {
			tokens.putAll(_customTokens);
		}
	}

	private static void _populateTokens(
			Map<String, String> tokens, long articleGroupId,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Layout layout = themeDisplay.getLayout();

		Group group = layout.getGroup();

		LayoutSet layoutSet = layout.getLayoutSet();

		String friendlyUrlCurrent = null;

		if (layout.isPublicLayout()) {
			friendlyUrlCurrent = themeDisplay.getPathFriendlyURLPublic();
		}
		else if (group.isUserGroup()) {
			friendlyUrlCurrent = themeDisplay.getPathFriendlyURLPrivateUser();
		}
		else {
			friendlyUrlCurrent = themeDisplay.getPathFriendlyURLPrivateGroup();
		}

		String layoutSetFriendlyUrl = themeDisplay.getI18nPath();

		TreeMap<String, String> virtualHostnames =
			layoutSet.getVirtualHostnames();

		if (virtualHostnames.isEmpty() ||
			!virtualHostnames.containsKey(themeDisplay.getServerName())) {

			layoutSetFriendlyUrl = friendlyUrlCurrent + group.getFriendlyURL();
		}

		tokens.put("article_group_id", String.valueOf(articleGroupId));
		tokens.put("cdn_host", themeDisplay.getCDNHost());
		tokens.put("company_id", String.valueOf(themeDisplay.getCompanyId()));
		tokens.put("friendly_url_current", friendlyUrlCurrent);
		tokens.put(
			"friendly_url_private_group",
			themeDisplay.getPathFriendlyURLPrivateGroup());
		tokens.put(
			"friendly_url_private_user",
			themeDisplay.getPathFriendlyURLPrivateUser());
		tokens.put(
			"friendly_url_public", themeDisplay.getPathFriendlyURLPublic());
		tokens.put("group_friendly_url", group.getFriendlyURL());
		tokens.put("image_path", themeDisplay.getPathImage());
		tokens.put("layout_set_friendly_url", layoutSetFriendlyUrl);
		tokens.put("main_path", themeDisplay.getPathMain());
		tokens.put("portal_ctx", themeDisplay.getPathContext());
		tokens.put(
			"portal_url", HttpUtil.removeProtocol(themeDisplay.getURLPortal()));
		tokens.put(
			"protocol", HttpUtil.getProtocol(themeDisplay.getURLPortal()));
		tokens.put("root_path", themeDisplay.getPathContext());
		tokens.put(
			"scope_group_id", String.valueOf(themeDisplay.getScopeGroupId()));
		tokens.put(
			"site_group_id", String.valueOf(themeDisplay.getSiteGroupId()));
		tokens.put("theme_image_path", themeDisplay.getPathThemeImages());

		_populateCustomTokens(tokens, themeDisplay.getCompanyId());
	}

	private static void _populateTokens(
			Map<String, String> tokens, long articleGroupId,
			ThemeDisplayModel themeDisplayModel)
		throws Exception {

		Layout layout = LayoutLocalServiceUtil.getLayout(
			themeDisplayModel.getPlid());

		Group group = layout.getGroup();

		LayoutSet layoutSet = layout.getLayoutSet();

		String friendlyUrlCurrent = null;

		if (layout.isPublicLayout()) {
			friendlyUrlCurrent = themeDisplayModel.getPathFriendlyURLPublic();
		}
		else if (group.isUserGroup()) {
			friendlyUrlCurrent =
				themeDisplayModel.getPathFriendlyURLPrivateUser();
		}
		else {
			friendlyUrlCurrent =
				themeDisplayModel.getPathFriendlyURLPrivateGroup();
		}

		String layoutSetFriendlyUrl = themeDisplayModel.getI18nPath();

		TreeMap<String, String> virtualHostnames =
			layoutSet.getVirtualHostnames();

		if (virtualHostnames.isEmpty() ||
			!virtualHostnames.containsKey(themeDisplayModel.getServerName())) {

			layoutSetFriendlyUrl = friendlyUrlCurrent + group.getFriendlyURL();
		}

		tokens.put("article_group_id", String.valueOf(articleGroupId));
		tokens.put("cdn_host", themeDisplayModel.getCdnHost());
		tokens.put(
			"company_id", String.valueOf(themeDisplayModel.getCompanyId()));
		tokens.put("friendly_url_current", friendlyUrlCurrent);
		tokens.put(
			"friendly_url_private_group",
			themeDisplayModel.getPathFriendlyURLPrivateGroup());
		tokens.put(
			"friendly_url_private_user",
			themeDisplayModel.getPathFriendlyURLPrivateUser());
		tokens.put(
			"friendly_url_public",
			themeDisplayModel.getPathFriendlyURLPublic());
		tokens.put("group_friendly_url", group.getFriendlyURL());
		tokens.put("image_path", themeDisplayModel.getPathImage());
		tokens.put("layout_set_friendly_url", layoutSetFriendlyUrl);
		tokens.put("main_path", themeDisplayModel.getPathMain());
		tokens.put("portal_ctx", themeDisplayModel.getPathContext());
		tokens.put(
			"portal_url",
			HttpUtil.removeProtocol(themeDisplayModel.getURLPortal()));
		tokens.put(
			"protocol", HttpUtil.getProtocol(themeDisplayModel.getURLPortal()));
		tokens.put("root_path", themeDisplayModel.getPathContext());
		tokens.put(
			"scope_group_id",
			String.valueOf(themeDisplayModel.getScopeGroupId()));
		tokens.put("theme_image_path", themeDisplayModel.getPathThemeImages());

		_populateCustomTokens(tokens, themeDisplayModel.getCompanyId());
	}

	private static void _removeArticleLocale(
		Element element, String languageId) {

		for (Element dynamicElementElement :
				element.elements("dynamic-element")) {

			for (Element dynamicContentElement :
					dynamicElementElement.elements("dynamic-content")) {

				String curLanguageId = GetterUtil.getString(
					dynamicContentElement.attributeValue("language-id"));

				if (curLanguageId.equals(languageId)) {
					dynamicContentElement.detach();
				}
			}

			_removeArticleLocale(dynamicElementElement, languageId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(JournalUtil.class);

	private static Map<String, String> _customTokens;
	private static final JournalTransformer _journalTransformer =
		new JournalTransformer();

}