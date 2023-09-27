/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.soy.builder.maven;

import com.liferay.portal.tools.soy.builder.commands.ReplaceSoyTranslationCommand;

/**
 * Replace 'goog.getMsg' definitions.
 *
 * @author Andrea Di Giorgi
 * @goal replace-translation
 */
public class ReplaceSoyTranslationMojo
	extends BaseSoyJsMojo<ReplaceSoyTranslationCommand> {

	@Override
	protected ReplaceSoyTranslationCommand createCommand() {
		return new ReplaceSoyTranslationCommand();
	}

}