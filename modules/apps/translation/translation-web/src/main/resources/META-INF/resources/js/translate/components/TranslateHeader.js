/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import React from 'react';

const TranslateHeader = ({
	autoTranslateEnabled,
	sourceLanguageIdTitle,
	targetLanguageIdTitle,
}) => (
	<ClayLayout.Row
		className={classNames({
			'row-autotranslate-title': autoTranslateEnabled,
		})}
	>
		<ClayLayout.Col md={6}>
			<ClayIcon symbol={sourceLanguageIdTitle.toLowerCase()} />

			<span className="ml-2">{sourceLanguageIdTitle}</span>

			<hr className="separator" />
		</ClayLayout.Col>

		<ClayLayout.Col md={6}>
			<ClayIcon symbol={targetLanguageIdTitle.toLowerCase()} />

			<span className="ml-2">{targetLanguageIdTitle}</span>

			<hr className="separator" />
		</ClayLayout.Col>
	</ClayLayout.Row>
);

export default TranslateHeader;
