/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.portlet;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.change.tracking.spi.constants.CTTimelineKeys;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.dynamic.data.mapping.configuration.DDMWebConfiguration;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException;
import com.liferay.dynamic.data.mapping.exception.StorageFieldRequiredException;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToMapConverter;
import com.liferay.dynamic.data.mapping.util.DDMTemplateHelper;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.exportimport.kernel.exception.ExportImportContentValidationException;
import com.liferay.item.selector.ItemSelector;
import com.liferay.journal.configuration.JournalFileUploadsConfiguration;
import com.liferay.journal.constants.JournalConstants;
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
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderService;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.JournalHelper;
import com.liferay.journal.web.internal.configuration.FFJournalAutoSaveDraftConfiguration;
import com.liferay.journal.web.internal.configuration.JournalWebConfiguration;
import com.liferay.journal.web.internal.helper.JournalDDMTemplateHelper;
import com.liferay.journal.web.internal.portlet.action.ActionUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.translation.security.permission.TranslationPermission;
import com.liferay.translation.url.provider.TranslationURLProvider;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.util.TrashWebKeys;

import java.io.IOException;

import java.util.Objects;

import javax.persistence.PersistenceException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
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
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
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

		if (Objects.equals(
				getPath(renderRequest, renderResponse),
				"/edit_ddm_template.jsp")) {

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
			FieldsToDDMFormValuesConverter.class.getName(),
			_fieldsToDDMFormValuesConverter);
		renderRequest.setAttribute(ItemSelector.class.getName(), _itemSelector);
		renderRequest.setAttribute(
			JournalHelper.class.getName(), _journalHelper);
		renderRequest.setAttribute(
			JournalWebKeys.JOURNAL_CONTENT, _journalContent);
		renderRequest.setAttribute(
			JournalWebKeys.JOURNAL_CONVERTER, _journalConverter);
		renderRequest.setAttribute(
			TranslationPermission.class.getName(), _translationPermission);
		renderRequest.setAttribute(
			TranslationURLProvider.class.getName(), _translationURLProvider);

		try {
			renderRequest.setAttribute(
				DDMWebConfiguration.class.getName(),
				_configurationProvider.getSystemConfiguration(
					DDMWebConfiguration.class));
			renderRequest.setAttribute(
				FFJournalAutoSaveDraftConfiguration.class.getName(),
				_configurationProvider.getSystemConfiguration(
					FFJournalAutoSaveDraftConfiguration.class));
			renderRequest.setAttribute(
				JournalFileUploadsConfiguration.class.getName(),
				_configurationProvider.getSystemConfiguration(
					JournalFileUploadsConfiguration.class));
			renderRequest.setAttribute(
				JournalWebConfiguration.class.getName(),
				_configurationProvider.getSystemConfiguration(
					JournalWebConfiguration.class));
		}
		catch (ConfigurationException configurationException) {
			throw new PortletException(configurationException);
		}

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
			ItemSelector.class.getName(), _itemSelector);
		resourceRequest.setAttribute(
			JournalHelper.class.getName(), _journalHelper);
		resourceRequest.setAttribute(
			TranslationPermission.class.getName(), _translationPermission);
		resourceRequest.setAttribute(
			TranslationURLProvider.class.getName(), _translationURLProvider);
		resourceRequest.setAttribute(TrashWebKeys.TRASH_HELPER, _trashHelper);

		try {
			resourceRequest.setAttribute(
				FFJournalAutoSaveDraftConfiguration.class.getName(),
				_configurationProvider.getSystemConfiguration(
					FFJournalAutoSaveDraftConfiguration.class));
			resourceRequest.setAttribute(
				JournalWebConfiguration.class.getName(),
				_configurationProvider.getSystemConfiguration(
					JournalWebConfiguration.class));
		}
		catch (ConfigurationException configurationException) {
			throw new PortletException(configurationException);
		}

		super.serveResource(resourceRequest, resourceResponse);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			String path = getPath(renderRequest, renderResponse);

			if (Objects.equals(path, "/edit_article.jsp") ||
				Objects.equals(path, "/view_article_history.jsp")) {

				ActionUtil.getArticle(
					_portal.getHttpServletRequest(renderRequest));
			}
			else {
				_getFolder(_portal.getHttpServletRequest(renderRequest));
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
			else {
				_log.error(exception);
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
			throwable instanceof PersistenceException ||
			throwable instanceof StorageFieldRequiredException ||
			throwable instanceof SystemException ||
			super.isSessionErrorException(throwable)) {

			return true;
		}

		return false;
	}

	private void _getFolder(HttpServletRequest httpServletRequest)
		throws PortalException {

		long folderId = ParamUtil.getLong(httpServletRequest, "folderId");

		if (folderId > 0) {
			_journalFolderService.fetchFolder(folderId);

			httpServletRequest.setAttribute(
				CTTimelineKeys.CLASS_NAME, JournalFolder.class.getName());
			httpServletRequest.setAttribute(CTTimelineKeys.CLASS_PK, folderId);
		}
		else {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_portletResourcePermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup(), ActionKeys.VIEW);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(JournalPortlet.class);

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private DDMFormValuesToMapConverter _ddmFormValuesToMapConverter;

	@Reference
	private DDMTemplateHelper _ddmTemplateHelper;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private JournalDDMTemplateHelper _journalDDMTemplateHelper;

	@Reference
	private JournalFolderService _journalFolderService;

	@Reference
	private JournalHelper _journalHelper;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + JournalConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.journal.web)(&(release.schema.version>=1.0.0)(!(release.schema.version>=2.0.0))))"
	)
	private Release _release;

	@Reference
	private TranslationPermission _translationPermission;

	@Reference
	private TranslationURLProvider _translationURLProvider;

	@Reference
	private TrashHelper _trashHelper;

}