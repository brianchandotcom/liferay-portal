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
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.internal.transformer.JournalTransformer;
import com.liferay.journal.internal.transformer.JournalTransformerListenerRegistryUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalStructureConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
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
import com.liferay.portal.kernel.service.ImageLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

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
			article.getVersion());

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
			StringBundler sb = new StringBundler(5);

			sb.append(themeDisplay.getPathImage());
			sb.append("/journal/article?img_id=");
			sb.append(article.getSmallImageId());
			sb.append("&t=");
			sb.append(
				WebServerServletTokenUtil.getToken(article.getSmallImageId()));

			smallImageURL = sb.toString();
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
			PortletURL portletURL = liferayPortletResponse.createRenderURL();

			portletURL.setParameter("groupId", String.valueOf(groupId));
			portletURL.setParameter("folderId", String.valueOf(folderId));

			return portletURL.toString();
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
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return StringPool.BLANK;
	}

	public static String getJournalControlPanelLink(
			PortletRequest portletRequest, long folderId)
		throws PortalException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			portletRequest, JournalArticle.class.getName(),
			PortletProvider.Action.EDIT);

		portletURL.setParameter("folderId", String.valueOf(folderId));

		return portletURL.toString();
	}

	public static Map<String, String> getTokens(
			long articleGroupId, PortletRequestModel portletRequestModel,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Map<String, String> tokens = new HashMap<>();

		if (themeDisplay != null) {
			_populateTokens(tokens, articleGroupId, themeDisplay);
		}
		else if (portletRequestModel != null) {
			ThemeDisplayModel themeDisplayModel =
				portletRequestModel.getThemeDisplayModel();

			if (themeDisplayModel != null) {
				try {
					_populateTokens(tokens, articleGroupId, themeDisplayModel);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e, e);
					}
				}
			}
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
		Document document, String content, String languageId) {

		try {
			Element rootElement = document.getRootElement();

			String availableLocales = rootElement.attributeValue(
				"available-locales");

			if (availableLocales == null) {
				return content;
			}

			availableLocales = StringUtil.removeFromList(
				availableLocales, languageId);

			if (availableLocales.endsWith(",")) {
				availableLocales = availableLocales.substring(
					0, availableLocales.length() - 1);
			}

			rootElement.addAttribute("available-locales", availableLocales);

			_removeArticleLocale(rootElement, languageId);

			content = XMLUtil.formatXML(document);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return content;
	}

	public static String transform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId, Document document,
			PortletRequestModel portletRequestModel, String script,
			String langType)
		throws Exception {

		return transform(
			themeDisplay, tokens, viewMode, languageId, document,
			portletRequestModel, script, langType, false);
	}

	public static String transform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId, Document document,
			PortletRequestModel portletRequestModel, String script,
			String langType, boolean propagateException)
		throws Exception {

		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(langType);

		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(
				JournalArticle.class.getName());

		Map<String, Object> contextObjects = new HashMap<>();

		templateManager.addContextObjects(
			contextObjects, templateHandler.getCustomContextObjects());

		return _journalTransformer.transform(
			themeDisplay, contextObjects, tokens, viewMode, languageId,
			document, portletRequestModel, script, langType,
			propagateException);
	}

	private static void _addElementOptions(
		Element curContentElement, Element newContentElement) {

		List<Element> newElementOptions = newContentElement.elements("option");

		for (Element newElementOption : newElementOptions) {
			Element curElementOption = SAXReaderUtil.createElement("option");

			curElementOption.addCDATA(newElementOption.getText());

			curContentElement.add(curElementOption);
		}
	}

	private static void _addReservedEl(
		Element rootElement, Map<String, String> tokens, String name,
		Date value) {

		_addReservedEl(rootElement, tokens, name, Time.getRFC822(value));
	}

	private static void _addReservedEl(
		Element rootElement, Map<String, String> tokens, String name,
		double value) {

		_addReservedEl(rootElement, tokens, name, String.valueOf(value));
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

	private static Element _getElementByInstanceId(
		Document document, String instanceId) {

		if (Validator.isNull(instanceId)) {
			return null;
		}

		XPath xPathSelector = SAXReaderUtil.createXPath(
			"//dynamic-element[@instance-id=" +
				HtmlUtil.escapeXPathAttribute(instanceId) + "]");

		List<Node> nodes = xPathSelector.selectNodes(document);

		if (nodes.size() == 1) {
			return (Element)nodes.get(0);
		}

		return null;
	}

	private static String _getTemplateScript(
		DDMTemplate ddmTemplate, Map<String, String> tokens, String languageId,
		boolean transform) {

		String script = ddmTemplate.getScript();

		if (!transform) {
			return script;
		}

		for (TransformerListener transformerListener :
				JournalTransformerListenerRegistryUtil.
					getTransformerListeners()) {

			script = transformerListener.onScript(
				script, (Document)null, languageId, tokens);
		}

		return script;
	}

	private static String _getTemplateScript(
			long groupId, String ddmTemplateKey, Map<String, String> tokens,
			String languageId, boolean transform)
		throws PortalException {

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
			groupId, PortalUtil.getClassNameId(DDMStructure.class),
			ddmTemplateKey, true);

		return _getTemplateScript(ddmTemplate, tokens, languageId, transform);
	}

	private static void _mergeArticleContentUpdate(
			Document curDocument, Element newParentElement, Element newElement,
			int pos, String defaultLocale)
		throws Exception {

		_mergeArticleContentUpdate(curDocument, newElement, defaultLocale);

		String instanceId = newElement.attributeValue("instance-id");

		Element curElement = _getElementByInstanceId(curDocument, instanceId);

		if (curElement != null) {
			_mergeArticleContentUpdate(curElement, newElement, defaultLocale);
		}
		else {
			String parentInstanceId = newParentElement.attributeValue(
				"instance-id");

			if (Validator.isNull(parentInstanceId)) {
				Element curRoot = curDocument.getRootElement();

				List<Element> curRootElements = curRoot.elements();

				curRootElements.add(pos, newElement.createCopy());
			}
			else {
				Element curParentElement = _getElementByInstanceId(
					curDocument, parentInstanceId);

				if (curParentElement != null) {
					List<Element> curParentElements =
						curParentElement.elements();

					curParentElements.add(pos, newElement.createCopy());
				}
			}
		}
	}

	private static void _mergeArticleContentUpdate(
			Document curDocument, Element newParentElement,
			String defaultLocale)
		throws Exception {

		List<Element> newElements = newParentElement.elements(
			"dynamic-element");

		for (int i = 0; i < newElements.size(); i++) {
			Element newElement = newElements.get(i);

			_mergeArticleContentUpdate(
				curDocument, newParentElement, newElement, i, defaultLocale);
		}
	}

	private static void _mergeArticleContentUpdate(
		Element curElement, Element newElement, String defaultLocale) {

		Attribute curTypeAttribute = curElement.attribute("type");
		Attribute newTypeAttribute = newElement.attribute("type");

		curTypeAttribute.setValue(newTypeAttribute.getValue());

		Attribute newIndexTypeAttribute = newElement.attribute("index-type");

		if (newIndexTypeAttribute != null) {
			Attribute curIndexTypeAttribute = curElement.attribute(
				"index-type");

			if (curIndexTypeAttribute == null) {
				curElement.addAttribute(
					"index-type", newIndexTypeAttribute.getValue());
			}
			else {
				curIndexTypeAttribute.setValue(
					newIndexTypeAttribute.getValue());
			}
		}

		List<Element> elements = newElement.elements("dynamic-content");

		if ((elements == null) || elements.isEmpty()) {
			return;
		}

		Element newContentElement = elements.get(0);

		String newLanguageId = newContentElement.attributeValue("language-id");
		String newValue = newContentElement.getText();

		String indexType = newElement.attributeValue("index-type");

		if (Validator.isNotNull(indexType)) {
			curElement.addAttribute("index-type", indexType);
		}

		List<Element> curContentElements = curElement.elements(
			"dynamic-content");

		if (Validator.isNull(newLanguageId)) {
			for (Element curContentElement : curContentElements) {
				curContentElement.detach();
			}

			Element curContentElement = SAXReaderUtil.createElement(
				"dynamic-content");

			if (newContentElement.element("option") != null) {
				_addElementOptions(curContentElement, newContentElement);
			}
			else {
				curContentElement.addCDATA(newValue);
			}

			curElement.add(curContentElement);
		}
		else {
			boolean alreadyExists = false;

			for (Element curContentElement : curContentElements) {
				String curLanguageId = curContentElement.attributeValue(
					"language-id");

				if (newLanguageId.equals(curLanguageId)) {
					alreadyExists = true;

					curContentElement.clearContent();

					if (newContentElement.element("option") != null) {
						_addElementOptions(
							curContentElement, newContentElement);
					}
					else {
						curContentElement.addCDATA(newValue);
					}

					break;
				}
			}

			if (!alreadyExists) {
				Element curContentElement = curContentElements.get(0);

				String curLanguageId = curContentElement.attributeValue(
					"language-id");

				if (Validator.isNull(curLanguageId)) {
					if (newLanguageId.equals(defaultLocale)) {
						curContentElement.clearContent();

						if (newContentElement.element("option") != null) {
							_addElementOptions(
								curContentElement, newContentElement);
						}
						else {
							curContentElement.addCDATA(newValue);
						}
					}
					else {
						curElement.add(newContentElement.createCopy());
					}

					curContentElement.addAttribute(
						"language-id", defaultLocale);
				}
				else {
					curElement.add(newContentElement.createCopy());
				}
			}
		}
	}

	private static void _populateCustomTokens(
		Map<String, String> tokens, long companyId) {

		JournalServiceConfiguration journalServiceConfiguration = null;

		try {
			journalServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					JournalServiceConfiguration.class, companyId);
		}
		catch (Exception e) {
			_log.error(e, e);
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

		// Deprecated tokens

		tokens.put("friendly_url", themeDisplay.getPathFriendlyURLPublic());
		tokens.put(
			"friendly_url_private",
			themeDisplay.getPathFriendlyURLPrivateGroup());
		tokens.put("group_id", String.valueOf(articleGroupId));
		tokens.put("page_url", themeDisplay.getPathFriendlyURLPublic());
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

		// Deprecated tokens

		tokens.put(
			"friendly_url", themeDisplayModel.getPathFriendlyURLPublic());
		tokens.put(
			"friendly_url_private",
			themeDisplayModel.getPathFriendlyURLPrivateGroup());
		tokens.put("group_id", String.valueOf(articleGroupId));
		tokens.put("page_url", themeDisplayModel.getPathFriendlyURLPublic());
	}

	private static void _removeArticleLocale(Element element, String languageId)
		throws PortalException {

		for (Element dynamicElementElement :
				element.elements("dynamic-element")) {

			for (Element dynamicContentElement :
					dynamicElementElement.elements("dynamic-content")) {

				String curLanguageId = GetterUtil.getString(
					dynamicContentElement.attributeValue("language-id"));

				if (curLanguageId.equals(languageId)) {
					long id = GetterUtil.getLong(
						dynamicContentElement.attributeValue("id"));

					if (id > 0) {
						ImageLocalServiceUtil.deleteImage(id);
					}

					dynamicContentElement.detach();
				}
			}

			_removeArticleLocale(dynamicElementElement, languageId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(JournalUtil.class);

	private static Map<String, String> _customTokens;
	private static final JournalTransformer _journalTransformer =
		new JournalTransformer(true);

}