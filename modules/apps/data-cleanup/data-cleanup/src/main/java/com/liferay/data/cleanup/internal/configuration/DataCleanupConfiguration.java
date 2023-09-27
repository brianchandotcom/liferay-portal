/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Preston Crary
 */
@ExtendedObjectClassDefinition(
	category = "upgrades", liferayLearnMessageKey = "general",
	liferayLearnMessageResource = "data-cleanup"
)
@Meta.OCD(
	id = "com.liferay.data.cleanup.internal.configuration.DataCleanupConfiguration",
	name = "data-cleanup-configuration-name"
)
public interface DataCleanupConfiguration {

	@Meta.AD(
		deflt = "false", name = "clean-up-amazon-rankings-module-data",
		required = false
	)
	public boolean cleanUpAmazonRankingsModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-chat-module-data", required = false
	)
	public boolean cleanUpChatModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-currency-converter-module-data",
		required = false
	)
	public boolean cleanUpCurrencyConverterModuleData();

	@Meta.AD(
		deflt = "false",
		name = "clean-up-document-library-file-rank-module-data",
		required = false
	)
	public boolean cleanUpDocumentLibraryFileRankModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-dictionary-module-data",
		required = false
	)
	public boolean cleanUpDictionaryModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-directory-module-data",
		required = false
	)
	public boolean cleanUpDirectoryModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-google-maps-module-data",
		required = false
	)
	public boolean cleanUpGoogleMapsModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-hello-world-module-data",
		required = false
	)
	public boolean cleanUpHelloWorldModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-html-preview-module-data",
		required = false
	)
	public boolean cleanUpHTMLPreviewModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-image-editor-module-data",
		required = false
	)
	public boolean cleanUpImageEditorModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-invitation-module-data",
		required = false
	)
	public boolean cleanUpInvitationModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-loan-calculator-module-data",
		required = false
	)
	public boolean cleanUpLoanCalculatorModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-mail-reader-module-data",
		required = false
	)
	public boolean cleanUpMailReaderModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-network-utilities-module-data",
		required = false
	)
	public boolean cleanUpNetworkUtilitiesModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-oauth-module-data", required = false
	)
	public boolean cleanUpOAuthModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-open-social-module-data",
		required = false
	)
	public boolean cleanUpOpenSocialModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-password-generator-module-data",
		required = false
	)
	public boolean cleanUpPasswordGeneratorModuleData();

	@Meta.AD(
		deflt = "false",
		name = "clean-up-portal-security-wedeploy-auth-module-data",
		required = false
	)
	public boolean cleanUpPortalSecurityWedeployAuthModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-private-messaging-module-data",
		required = false
	)
	public boolean cleanUpPrivateMessagingModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-quick-note-module-data",
		required = false
	)
	public boolean cleanUpQuickNoteModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-rencet-documents-module-data",
		required = false
	)
	public boolean cleanUpRecentDocumentsModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-social-activity-module-data",
		required = false
	)
	public boolean cleanUpSocialActivityModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-social-group-statistics-module-data",
		required = false
	)
	public boolean cleanUpSocialGroupStatisticsModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-social-request-module-data",
		required = false
	)
	public boolean cleanUpSocialRequestModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-social-user-statistics-module-data",
		required = false
	)
	public boolean cleanUpSocialUserStatisticsModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-software-catalog-module-data",
		required = false
	)
	public boolean cleanUpSoftwareCatalogModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-shopping-module-data",
		required = false
	)
	public boolean cleanUpShoppingModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-sync-module-data", required = false
	)
	public boolean cleanUpSyncModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-translator-module-data",
		required = false
	)
	public boolean cleanUpTranslatorModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-twitter-module-data", required = false
	)
	public boolean cleanUpTwitterModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-unit-converter-module-data",
		required = false
	)
	public boolean cleanUpUnitConverterModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-weather-module-data", required = false
	)
	public boolean cleanUpWeatherModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-web-form-module-data",
		required = false
	)
	public boolean cleanUpWebFormModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-web-proxy-module-data",
		required = false
	)
	public boolean cleanUpWebProxyModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-wysiwyg-module-data", required = false
	)
	public boolean cleanUpWysiwygModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-xsl-content-module-data",
		required = false
	)
	public boolean cleanUpXSLContentModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-youtube-module-data", required = false
	)
	public boolean cleanUpYoutubeModuleData();

}