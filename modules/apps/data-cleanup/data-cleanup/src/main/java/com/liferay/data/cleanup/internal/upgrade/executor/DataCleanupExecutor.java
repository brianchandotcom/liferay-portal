/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade.executor;

import com.liferay.data.cleanup.internal.configuration.DataCleanupConfiguration;
import com.liferay.data.cleanup.internal.upgrade.AmazonRankingsUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.ChatUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.CurrenctConverterUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.DictionaryUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.DirectoryUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.DocumentLibraryFileRankServiceUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.GoogleMapsUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.HTMLPreviewServiceUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.ImageEditorUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.InvitationUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.LoanCalculatorUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.MailReaderUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.NetworkUtilitiesUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.OAuthUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.OpenSocialUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.PasswordGeneratorUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.PortalSecurityWedeployAuthUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.PrivateMessagingUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.QuickNoteUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.RecentDocumentsUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.ShoppingUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.SocialActivityUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.SocialGroupStatisticsUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.SocialRequestUpgradeProcess;
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
import com.liferay.data.cleanup.internal.upgrade.WysiwygUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.XSLContentUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.YoutubeUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.util.ConfigurationUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.util.Map;
import java.util.function.Supplier;

import org.apache.felix.cm.PersistenceManager;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	configurationPid = "com.liferay.data.cleanup.internal.configuration.DataCleanupConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class DataCleanupExecutor {

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		DataCleanupConfiguration dataCleanupConfiguration =
			ConfigurableUtil.createConfigurable(
				DataCleanupConfiguration.class, properties);

		ConfigurationUtil.deleteConfiguration(
			_configurationAdmin, _persistenceManager,
			DataCleanupConfiguration.class.getName());

		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpAmazonRankingsModuleData,
			"com.liferay.amazon.rankings.web",
			AmazonRankingsUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpChatModuleData,
			"com.liferay.chat.service", ChatUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpDocumentLibraryFileRankModuleData,
			"com.liferay.document.library.file.rank.service",
			DocumentLibraryFileRankServiceUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpDictionaryModuleData,
			"com.liferay.dictionary.web", DictionaryUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpCurrencyConverterModuleData,
			"com.liferay.currency.converter.web",
			CurrenctConverterUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpDirectoryModuleData,
			"com.liferay.directory.web", DirectoryUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpImageEditorModuleData,
			"com.liferay.frontend.image.editor.web",
			ImageEditorUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpGoogleMapsModuleData,
			"com.liferay.google.maps.web", GoogleMapsUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpHelloWorldModuleData,
			"com.liferay.hello.world.web", UpgradeHelloWorld::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpHTMLPreviewModuleData,
			"com.liferay.html.preview.service",
			HTMLPreviewServiceUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpInvitationModuleData,
			"com.liferay.invitation.web", InvitationUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpLoanCalculatorModuleData,
			"com.liferay.loan.calculator.web",
			LoanCalculatorUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpMailReaderModuleData,
			"com.liferay.mail.reader.service", MailReaderUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpNetworkUtilitiesModuleData,
			"com.liferay.network.utilities.web",
			NetworkUtilitiesUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpOAuthModuleData,
			"com.liferay.oauth.service", OAuthUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpPasswordGeneratorModuleData,
			"com.liferay.password.generator.web",
			PasswordGeneratorUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::
				cleanUpPortalSecurityWedeployAuthModuleData,
			"com.liferay.portal.security.wedeploy.auth.service",
			PortalSecurityWedeployAuthUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpQuickNoteModuleData,
			"com.liferay.quick.note.web", QuickNoteUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpRecentDocumentsModuleData,
			"com.liferay.recent.documents.web",
			RecentDocumentsUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpShoppingModuleData,
			"com.liferay.shopping.service",
			() -> new ShoppingUpgradeProcess(_imageLocalService));
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpSocialActivityModuleData,
			"com.liferay.social.activity.web",
			SocialActivityUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpSocialGroupStatisticsModuleData,
			"com.liferay.social.group.statistics.web",
			SocialGroupStatisticsUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpPrivateMessagingModuleData,
			"com.liferay.social.privatemessaging.service",
			() -> new PrivateMessagingUpgradeProcess(_mbThreadLocalService));
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpSocialRequestModuleData,
			"com.liferay.social.requests.web",
			SocialRequestUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpSocialUserStatisticsModuleData,
			"com.liferay.social.user.statistics.web",
			SocialUserStatisticsUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpSoftwareCatalogModuleData,
			"com.liferay.softwarecatalog.service",
			() -> new SoftwareCatalogUpgradeProcess(
				_imageLocalService, _mbMessageLocalService,
				_ratingsStatsLocalService, _subscriptionLocalService));
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpSyncModuleData,
			"com.liferay.sync.service", SyncUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpTranslatorModuleData,
			"com.liferay.translator.web", TranslatorUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpTwitterModuleData,
			"com.liferay.twitter.service", TwitterUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpUnitConverterModuleData,
			"com.liferay.unit.converter.web", UnitConverterUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpWeatherModuleData,
			"com.liferay.weather.web", WeatherUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpWebFormModuleData,
			"com.liferay.web.form.web", WebFormUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpWebProxyModuleData,
			"com.liferay.web.proxy.web", WebProxyUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpWysiwygModuleData,
			"com.liferay.wysiwyg.web", WysiwygUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpXSLContentModuleData,
			"com.liferay.xsl.content.web", XSLContentUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpYoutubeModuleData,
			"com.liferay.youtube.web", YoutubeUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpOpenSocialModuleData,
			"opensocial-portlet",
			() -> new OpenSocialUpgradeProcess(_expandoTableLocalService));
	}

	private void _cleanUpModuleData(
			Supplier<Boolean> booleanSupplier, String servletContextName,
			Supplier<UpgradeProcess> upgradeProcessSupplier)
		throws Exception {

		if (booleanSupplier.get()) {
			Release release = _releaseLocalService.fetchRelease(
				servletContextName);

			if (release != null) {
				UpgradeProcess upgradeProcess = upgradeProcessSupplier.get();

				upgradeProcess.upgrade();

				CacheRegistryUtil.clear();
			}
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private PersistenceManager _persistenceManager;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private ReleaseLocalService _releaseLocalService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}