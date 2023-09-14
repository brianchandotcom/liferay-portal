/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.questions.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Javier Gamarra
 */
@ExtendedObjectClassDefinition(
	category = "message-boards", generateUI = true,
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.questions.web.internal.configuration.QuestionsConfiguration",
	localization = "content/Language", name = "questions-configuration-name"
)
public interface QuestionsConfiguration {

	@Meta.AD(
		deflt = "false", name = "enable-redirect-to-login", required = false
	)
	public boolean enableRedirectToLogin();

	@Meta.AD(
		deflt = "false", name = "enable-custom-asset-renderer", required = false
	)
	public boolean enableCustomAssetRenderer();

	@Meta.AD(deflt = "false", name = "enable-anonymous-read", required = false)
	public boolean enableAnonymousRead();

	@Meta.AD(
		deflt = "true", name = "show-cards-for-topic-navigation",
		required = false
	)
	public boolean showCardsForTopicNavigation();

	@Meta.AD(
		deflt = "", name = "set-base-path-for-history-router", required = false
	)
	public String historyRouterBasePath();

	@Meta.AD(deflt = "true", name = "use-topic-names-in-url", required = false)
	public boolean useTopicNamesInURL();

	@Meta.AD(
		deflt = "0",
		description = "specify-the-message-boards-category-id-that-acts-as-the-root-topic",
		name = "root-topic-id", required = false
	)
	public long rootTopicId();

}