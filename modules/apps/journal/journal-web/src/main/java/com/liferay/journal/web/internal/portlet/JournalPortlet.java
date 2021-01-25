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

package com.liferay.journal.web.internal.portlet;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.dynamic.data.mapping.configuration.DDMWebConfiguration;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException;
import com.liferay.dynamic.data.mapping.exception.StorageFieldRequiredException;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToMapConverter;
import com.liferay.dynamic.data.mapping.util.DDMTemplateHelper;
import com.liferay.exportimport.kernel.exception.ExportImportContentValidationException;
import com.liferay.item.selector.ItemSelector;
import com.liferay.journal.configuration.JournalFileUploadsConfiguration;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.exception.ArticleContentException;
import com.liferay.journal.exception.ArticleContentSizeException;
import com.liferay.journal.exception.ArticleDisplayDateException;
import com.liferay.journal.exception.ArticleExpirationDateException;
import com.liferay.journal.exception.ArticleIdException;
import com.liferay.journal.exception.ArticleSmallImageNameException;
import com.liferay.journal.exception.ArticleSmallImageSizeException;
import com.liferay.journal.exception.ArticleTitleException;
import com.liferay.journal.exception.ArticleVersionException;
import com.liferay.journal.exception.DuplicateArticleIdException;
import com.liferay.journal.exception.DuplicateFeedIdException;
import com.liferay.journal.exception.DuplicateFolderNameException;
import com.liferay.journal.exception.FeedContentFieldException;
import com.liferay.journal.exception.FeedIdException;
import com.liferay.journal.exception.FeedNameException;
import com.liferay.journal.exception.FeedTargetLayoutFriendlyUrlException;
import com.liferay.journal.exception.FeedTargetPortletIdException;
import com.liferay.journal.exception.FolderNameException;
import com.liferay.journal.exception.InvalidDDMStructureException;
import com.liferay.journal.exception.InvalidFolderException;
import com.liferay.journal.exception.MaxAddMenuFavItemsException;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.exception.NoSuchFeedException;
import com.liferay.journal.exception.NoSuchFolderException;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.web.internal.configuration.JournalWebConfiguration;
import com.liferay.journal.web.internal.helper.JournalDDMTemplateHelper;
import com.liferay.journal.web.internal.portlet.action.ActionUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.translation.url.provider.TranslationURLProvider;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.util.TrashWebKeys;

import java.io.IOException;

import java.util.Map;
import java.util.Objects;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	configurationPid = {
		"com.liferay.dynamic.data.mapping.configuration.DDMWebConfiguration",
		"com.liferay.journal.web.internal.configuration.JournalWebConfiguration"
	},
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-journal",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/journal.png",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Web Content",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.mvc-action-command-package-prefix=com.liferay.journal.web.portlet.action",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = {JournalPortlet.class, Portlet.class}
)
public class JournalPortlet extends MVCPortlet {

	public static final String VERSION_SEPARATOR = "_version_";

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			AssetDisplayPageFriendlyURLProvider.class.getName(),
			_assetDisplayPageFriendlyURLProvider);

		renderRequest.setAttribute(TrashWebKeys.TRASH_HELPER, _trashHelper);

		String path = getPath(renderRequest, renderResponse);

		if (Objects.equals(path, "/edit_article.jsp")) {
			renderRequest.setAttribute(
				JournalWebKeys.ITEM_SELECTOR, _itemSelector);
		}

		if (Objects.equals(path, "/edit_ddm_template.jsp")) {
			renderRequest.setAttribute(
				DDMTemplateHelper.class.getName(), _ddmTemplateHelper);
			renderRequest.setAttribute(
				JournalDDMTemplateHelper.class.getName(),
				_journalDDMTemplateHelper);
		}

		renderRequest.setAttribute(
			DDMFormValuesFactory.class.getName(), _ddmFormValuesFactory);
		renderRequest.setAttribute(
			DDMFormValuesToMapConverter.class.getName(),
			_ddmFormValuesToMapConverter);
		renderRequest.setAttribute(
			DDMWebConfiguration.class.getName(), _ddmWebConfiguration);
		renderRequest.setAttribute(
			JournalFileUploadsConfiguration.class.getName(),
			_journalFileUploadsConfiguration);
		renderRequest.setAttribute(
			JournalWebConfiguration.class.getName(), _journalWebConfiguration);
		renderRequest.setAttribute(
			JournalWebKeys.JOURNAL_CONTENT, _journalContent);
		renderRequest.setAttribute(
			JournalWebKeys.JOURNAL_CONVERTER, _journalConverter);
		renderRequest.setAttribute(
			TranslationURLProvider.class.getName(), _translationURLProvider);

		super.render(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		resourceRequest.setAttribute(
			AssetDisplayPageFriendlyURLProvider.class.getName(),
			_assetDisplayPageFriendlyURLProvider);
		resourceRequest.setAttribute(
			DDMTemplateHelper.class.getName(), _ddmTemplateHelper);
		resourceRequest.setAttribute(
			JournalWebConfiguration.class.getName(), _journalWebConfiguration);
		resourceRequest.setAttribute(
			TranslationURLProvider.class.getName(), _translationURLProvider);
		resourceRequest.setAttribute(TrashWebKeys.TRASH_HELPER, _trashHelper);

		super.serveResource(resourceRequest, resourceResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ddmWebConfiguration = ConfigurableUtil.createConfigurable(
			DDMWebConfiguration.class, properties);
		_journalFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			JournalFileUploadsConfiguration.class, properties);
		_journalWebConfiguration = ConfigurableUtil.createConfigurable(
			JournalWebConfiguration.class, properties);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			String path = getPath(renderRequest, renderResponse);

			if (Objects.equals(path, "/edit_article.jsp") ||
				Objects.equals(path, "/view_article_history.jsp")) {

				ActionUtil.getArticle(renderRequest);
			}
			else {
				ActionUtil.getFolder(renderRequest);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
			else {
				_log.error(exception.getMessage());
			}

			SessionErrors.add(renderRequest, exception.getClass());
		}

		if (SessionErrors.contains(
				renderRequest, NoSuchArticleException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchFeedException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchFolderException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchStructureException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchTemplateException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isAlwaysSendRedirect() {
		return true;
	}

	@Override
	protected boolean isSessionErrorException(Throwable throwable) {
		if (throwable instanceof ArticleContentException ||
			throwable instanceof ArticleContentSizeException ||
			throwable instanceof ArticleDisplayDateException ||
			throwable instanceof ArticleExpirationDateException ||
			throwable instanceof ArticleIdException ||
			throwable instanceof ArticleSmallImageNameException ||
			throwable instanceof ArticleSmallImageSizeException ||
			throwable instanceof ArticleTitleException ||
			throwable instanceof ArticleVersionException ||
			throwable instanceof AssetCategoryException ||
			throwable instanceof AssetTagException ||
			throwable instanceof DuplicateArticleIdException ||
			throwable instanceof DuplicateFeedIdException ||
			throwable instanceof DuplicateFileEntryException ||
			throwable instanceof DuplicateFolderNameException ||
			throwable instanceof ExportImportContentValidationException ||
			throwable instanceof FeedContentFieldException ||
			throwable instanceof FeedIdException ||
			throwable instanceof FeedNameException ||
			throwable instanceof FeedTargetLayoutFriendlyUrlException ||
			throwable instanceof FeedTargetPortletIdException ||
			throwable instanceof FileSizeException ||
			throwable instanceof FolderNameException ||
			throwable instanceof InvalidDDMStructureException ||
			throwable instanceof InvalidFolderException ||
			throwable instanceof LiferayFileItemException ||
			throwable instanceof LocaleException ||
			throwable instanceof MaxAddMenuFavItemsException ||
			throwable instanceof StorageFieldRequiredException ||
			throwable instanceof SystemException ||
			super.isSessionErrorException(throwable)) {

			return true;
		}

		return false;
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.journal.web)(&(release.schema.version>=1.0.0)(!(release.schema.version>=2.0.0))))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	private static final Log _log = LogFactoryUtil.getLog(JournalPortlet.class);

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private DDMFormValuesToMapConverter _ddmFormValuesToMapConverter;

	@Reference
	private DDMTemplateHelper _ddmTemplateHelper;

	private volatile DDMWebConfiguration _ddmWebConfiguration;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private JournalDDMTemplateHelper _journalDDMTemplateHelper;

	private volatile JournalFileUploadsConfiguration
		_journalFileUploadsConfiguration;
	private volatile JournalWebConfiguration _journalWebConfiguration;

	@Reference
	private TranslationURLProvider _translationURLProvider;

	@Reference
	private TrashHelper _trashHelper;

}