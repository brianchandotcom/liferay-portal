/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal;

import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.store.service.CTSContentLocalService;
import com.liferay.data.cleanup.DataCleanup;
import com.liferay.data.cleanup.DataCleanupAdapter;
import com.liferay.data.cleanup.internal.upgrade.AmazonRankingsUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.ChatUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.CurrencyConverterUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.DLPreviewCTSContentDataUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.DictionaryUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.DirectoryUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.DocumentLibraryFileRankServiceUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.ExpiredJournalArticleUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.FrontendImageEditorUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.GoogleMapsUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.HTMLPreviewUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.HelloVelocityUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.InvitationUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.LayoutClassedModelUsageOrphanDataUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.LoanCalculatorUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.MailReaderUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.NetworkUtilitiesUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.OAuthUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.OpenSocialUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.OutdatedPublishedCTCollectionUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.PasswordGeneratorUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.PortalSecurityWedeployAuthUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.PublishedCTSContentDataUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.QuickNoteUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.RecentDocumentsUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.ShoppingUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.SocialActivityUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.SocialGroupStatisticsUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.SocialPrivateMessagingUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.SocialRequestsUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.SocialUserStatisticsUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.SoftwareCatalogUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.SyncUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.TranslatorUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.TwitterUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.UnitConverterUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.UpgradeHelloWorld;
import com.liferay.data.cleanup.internal.upgrade.WeatherUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.WebFormUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.WebProxyUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.WidgetLayoutTypeSettingsUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.WysiwygUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.XSLContentUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.YoutubeUpgradeProcess;
import com.liferay.data.cleanup.internal.verify.ClassNamePostUpgradeDataCleanupProcess;
import com.liferay.data.cleanup.internal.verify.PostUpgradeDataCleanupProcess;
import com.liferay.data.cleanup.internal.verify.ServiceComponentPostUpgradeDataCleanupProcess;
import com.liferay.data.cleanup.util.DataCleanupUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.manager.ContentManager;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.service.ServiceComponentLocalService;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.upgrade.data.cleanup.AnalyticsMessageDataCleanupPreupgradeProcess;
import com.liferay.portal.upgrade.data.cleanup.CompanyDataCleanupPreupgradeProcess;
import com.liferay.portal.upgrade.data.cleanup.ConfigurationDataCleanupPreupgradeProcess;
import com.liferay.portal.upgrade.data.cleanup.CounterDataCleanupPreupgradeProcess;
import com.liferay.portal.upgrade.data.cleanup.DDMDataCleanupPreupgradeProcess;
import com.liferay.portal.upgrade.data.cleanup.DDMStorageLinkDataCleanupPreupgradeProcess;
import com.liferay.portal.upgrade.data.cleanup.DLFileEntryDataCleanupPreupgradeProcess;
import com.liferay.portal.upgrade.data.cleanup.DataCleanupPreupgradeProcessSuite;
import com.liferay.portal.upgrade.data.cleanup.GroupDataCleanupPreupgradeProcess;
import com.liferay.portal.upgrade.data.cleanup.JournalDataCleanupPreupgradeProcess;
import com.liferay.portal.upgrade.data.cleanup.NullUnicodeContentDataCleanupPreupgradeProcess;
import com.liferay.portal.upgrade.data.cleanup.QuartzJobDetailsDataCleanupPreupgradeProcess;
import com.liferay.portal.upgrade.data.cleanup.UserDataCleanupPreupgradeProcess;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(service = {})
public class DataCleanupRegistrator {

	@Activate
	protected void activate() throws Exception {
		_registerModuleDataCleanups();
		_registerSystemDataCleanups();
	}

	@Deactivate
	protected void deactivate() throws Exception {
		for (DataCleanup dataCleanup : _dataCleanups) {
			DataCleanupUtil.unregisterDataCleanup(dataCleanup);
		}
	}

	private String _getDataCleanupLabel(
		DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess) {

		return _dataCleanupLabels.get(dataCleanupPreupgradeProcess.getClass());
	}

	private void _registerDataCleanup(DataCleanup dataCleanup) {
		_dataCleanups.add(dataCleanup);

		DataCleanupUtil.registerDataCleanup(dataCleanup);
	}

	private void _registerDataCleanup(
		DataCleanup dataCleanup, String servletContextName) {

		Release release = _releaseLocalService.fetchRelease(servletContextName);

		if (release != null) {
			_registerDataCleanup(dataCleanup);
		}
	}

	private void _registerModuleDataCleanups() {
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-amazon-rankings-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new AmazonRankingsUpgradeProcess()),
			"com.liferay.amazon.rankings.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-chat-module-data", DataCleanup.MODULE_DATA_CLEANUP,
				new ChatUpgradeProcess()),
			"com.liferay.chat.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-currency-converter-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new CurrencyConverterUpgradeProcess()),
			"com.liferay.currency.converter.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-dictionary-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new DictionaryUpgradeProcess()),
			"com.liferay.dictionary.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-directory-module-data",
				DataCleanup.MODULE_DATA_CLEANUP, new DirectoryUpgradeProcess()),
			"com.liferay.directory.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-document-library-file-rank-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new DocumentLibraryFileRankServiceUpgradeProcess()),
			"com.liferay.document.library.file.rank.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-frontend-image-editor-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new FrontendImageEditorUpgradeProcess()),
			"com.liferay.frontend.image.editor.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-google-maps-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new GoogleMapsUpgradeProcess()),
			"com.liferay.google.maps.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-hello-velocity-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new HelloVelocityUpgradeProcess()),
			"com.liferay.hello.velocity.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-hello-world-module-data",
				DataCleanup.MODULE_DATA_CLEANUP, new UpgradeHelloWorld()),
			"com.liferay.hello.world.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-html-preview-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new HTMLPreviewUpgradeProcess()),
			"com.liferay.html.preview.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-invitation-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new InvitationUpgradeProcess()),
			"com.liferay.invitation.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-loan-calculator-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new LoanCalculatorUpgradeProcess()),
			"com.liferay.loan.calculator.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-mail-reader-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new MailReaderUpgradeProcess()),
			"com.liferay.mail.reader.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-network-utilities-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new NetworkUtilitiesUpgradeProcess()),
			"com.liferay.network.utilities.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-oauth-module-data", DataCleanup.MODULE_DATA_CLEANUP,
				new OAuthUpgradeProcess()),
			"com.liferay.oauth.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-password-generator-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new PasswordGeneratorUpgradeProcess()),
			"com.liferay.password.generator.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-portal-security-wedeploy-auth-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new PortalSecurityWedeployAuthUpgradeProcess()),
			"com.liferay.portal.security.wedeploy.auth.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-quick-note-module-data",
				DataCleanup.MODULE_DATA_CLEANUP, new QuickNoteUpgradeProcess()),
			"com.liferay.quick.note.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-recent-documents-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new RecentDocumentsUpgradeProcess()),
			"com.liferay.recent.documents.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-shopping-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new ShoppingUpgradeProcess(_imageLocalService)),
			"com.liferay.shopping.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-social-activity-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new SocialActivityUpgradeProcess()),
			"com.liferay.social.activity.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-social-group-statistics-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new SocialGroupStatisticsUpgradeProcess()),
			"com.liferay.social.group.statistics.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-social-private-messaging-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new SocialPrivateMessagingUpgradeProcess(
					_mbThreadLocalService)),
			"com.liferay.social.privatemessaging.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-social-requests-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new SocialRequestsUpgradeProcess()),
			"com.liferay.social.requests.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-social-user-statistics-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new SocialUserStatisticsUpgradeProcess()),
			"com.liferay.social.user.statistics.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-software-catalog-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new SoftwareCatalogUpgradeProcess(
					_imageLocalService, _mbMessageLocalService,
					_ratingsStatsLocalService, _subscriptionLocalService)),
			"com.liferay.softwarecatalog.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-sync-module-data", DataCleanup.MODULE_DATA_CLEANUP,
				new SyncUpgradeProcess()),
			"com.liferay.sync.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-translator-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new TranslatorUpgradeProcess()),
			"com.liferay.translator.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-twitter-module-data", DataCleanup.MODULE_DATA_CLEANUP,
				new TwitterUpgradeProcess()),
			"com.liferay.twitter.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-unit-converter-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new UnitConverterUpgradeProcess()),
			"com.liferay.unit.converter.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-weather-module-data", DataCleanup.MODULE_DATA_CLEANUP,
				new WeatherUpgradeProcess()),
			"com.liferay.weather.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-web-form-module-data",
				DataCleanup.MODULE_DATA_CLEANUP, new WebFormUpgradeProcess()),
			"com.liferay.web.form.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-web-proxy-module-data",
				DataCleanup.MODULE_DATA_CLEANUP, new WebProxyUpgradeProcess()),
			"com.liferay.web.proxy.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-wysiwyg-module-data", DataCleanup.MODULE_DATA_CLEANUP,
				new WysiwygUpgradeProcess()),
			"com.liferay.wysiwyg.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-xsl-content-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new XSLContentUpgradeProcess()),
			"com.liferay.xsl.content.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-youtube-module-data", DataCleanup.MODULE_DATA_CLEANUP,
				new YoutubeUpgradeProcess()),
			"com.liferay.youtube.web");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"clean-up-open-social-module-data",
				DataCleanup.MODULE_DATA_CLEANUP,
				new OpenSocialUpgradeProcess(_expandoTableLocalService)),
			"opensocial-portlet");
	}

	private void _registerSystemDataCleanups() {
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"remove-class-name-orphan-data",
				DataCleanup.SYSTEM_DATA_CLEANUP,
				new VerifyProcess() {

					@Override
					protected void doVerify() throws Exception {
						PostUpgradeDataCleanupProcess
							postUpgradeDataCleanupProcess =
								new ClassNamePostUpgradeDataCleanupProcess(
									_classNameLocalService, connection);

						postUpgradeDataCleanupProcess.cleanUp();
					}

				}));
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"remove-dl-preview-cts-content-data",
				DataCleanup.SYSTEM_DATA_CLEANUP,
				new DLPreviewCTSContentDataUpgradeProcess(
					_ctCollectionLocalService, _ctEntryLocalService, _portal)),
			"com.liferay.change.tracking.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"remove-expired-journal-articles",
				DataCleanup.SYSTEM_DATA_CLEANUP,
				new ExpiredJournalArticleUpgradeProcess(
					_journalArticleLocalService)),
			"com.liferay.journal.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"remove-layout-classed-model-usage-orphan-data",
				DataCleanup.SYSTEM_DATA_CLEANUP,
				new LayoutClassedModelUsageOrphanDataUpgradeProcess(
					_classNameLocalService, _contentManager,
					_ctCollectionLocalService, _fragmentEntryLinkLocalService,
					_layoutClassedModelUsageLocalService,
					_layoutPageTemplateStructureLocalService,
					_layoutPageTemplateStructureRelLocalService)),
			"com.liferay.layout.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"remove-publications-older-than-6-months",
				DataCleanup.SYSTEM_DATA_CLEANUP,
				new OutdatedPublishedCTCollectionUpgradeProcess(
					_ctCollectionLocalService)),
			"com.liferay.change.tracking.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"remove-published-cts-content-data",
				DataCleanup.SYSTEM_DATA_CLEANUP,
				new PublishedCTSContentDataUpgradeProcess(
					_ctsContentLocalService, _portal)),
			"com.liferay.change.tracking.store.service");
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"remove-service-component-orphan-data",
				DataCleanup.SYSTEM_DATA_CLEANUP,
				new VerifyProcess() {

					@Override
					protected void doVerify() throws Exception {
						PostUpgradeDataCleanupProcess
							postUpgradeDataCleanupProcess =
								new ServiceComponentPostUpgradeDataCleanupProcess(
									connection, _serviceComponentLocalService);

						postUpgradeDataCleanupProcess.cleanUp();
					}

				}));
		_registerDataCleanup(
			DataCleanupAdapter.create(
				"remove-widget-layout-type-settings",
				DataCleanup.SYSTEM_DATA_CLEANUP,
				new WidgetLayoutTypeSettingsUpgradeProcess(
					_layoutLocalService)),
			"com.liferay.layout.service");

		DataCleanupPreupgradeProcessSuite dataCleanupPreupgradeProcessSuite =
			new DataCleanupPreupgradeProcessSuite();

		for (DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess :
				dataCleanupPreupgradeProcessSuite.
					getSortedDataCleanupPreupgradeProcesses()) {

			String dataCleanupLabel = _getDataCleanupLabel(
				dataCleanupPreupgradeProcess);

			if (dataCleanupLabel == null) {
				continue;
			}

			_registerDataCleanup(
				DataCleanupAdapter.create(
					dataCleanupLabel, DataCleanup.SYSTEM_DATA_CLEANUP,
					dataCleanupPreupgradeProcess));
		}
	}

	private static final Map<Class<?>, String> _dataCleanupLabels =
		HashMapBuilder.<Class<?>, String>put(
			AnalyticsMessageDataCleanupPreupgradeProcess.class,
			"remove-analytics-message-data"
		).put(
			CompanyDataCleanupPreupgradeProcess.class,
			"remove-company-orphan-data"
		).put(
			ConfigurationDataCleanupPreupgradeProcess.class,
			"remove-configuration-orphan-data"
		).put(
			CounterDataCleanupPreupgradeProcess.class, "fix-counter-values"
		).put(
			DDMDataCleanupPreupgradeProcess.class, "remove-ddm-orphan-data"
		).put(
			DDMStorageLinkDataCleanupPreupgradeProcess.class,
			"remove-ddm-storage-link-orphan-data"
		).put(
			DLFileEntryDataCleanupPreupgradeProcess.class,
			"remove-dl-file-entry-orphan-data"
		).put(
			GroupDataCleanupPreupgradeProcess.class, "remove-group-orphan-data"
		).put(
			JournalDataCleanupPreupgradeProcess.class,
			"remove-journal-orphan-data"
		).put(
			NullUnicodeContentDataCleanupPreupgradeProcess.class,
			"remove-null-unicode-content-data"
		).put(
			QuartzJobDetailsDataCleanupPreupgradeProcess.class,
			"remove-quartz-job-details-data"
		).put(
			UserDataCleanupPreupgradeProcess.class, "remove-user-orphan-data"
		).build();

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ContentManager _contentManager;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTSContentLocalService _ctsContentLocalService;

	private final List<DataCleanup> _dataCleanups = new ArrayList<>();

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private ReleaseLocalService _releaseLocalService;

	@Reference
	private ServiceComponentLocalService _serviceComponentLocalService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}