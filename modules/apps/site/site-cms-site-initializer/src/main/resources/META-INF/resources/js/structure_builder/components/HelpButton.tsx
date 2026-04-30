/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React, {useMemo} from 'react';

import openHelpModal from '../utils/openHelpModal';

export default function HelpButton() {
	const title = useMemo(() => getTooltipMarkup(), []);

	return (
		<ClayButtonWithIcon
			aria-label={Liferay.Language.get('help-and-shortcuts')}
			className="lfr-portal-tooltip position-fixed rounded-circle structure-builder__help-button"
			data-title={title}
			data-title-set-as-html
			data-tooltip-align="top"
			displayType="primary"
			onClick={() => openHelpModal()}
			symbol="question-mark"
		/>
	);
}

function getTooltipMarkup() {
	const shift = Liferay.Browser.isMac() ? '⇧' : 'Shift';

	return `
	<div>${Liferay.Language.get('help-and-shortcuts')}</div>
	<kbd class="c-kbd c-kbd-dark mt-1">
		<kbd class="c-kbd">${shift}</kbd>

		<span class="c-kbd-separator">+</span>

		<kbd class="c-kbd">?</kbd>
	</kbd>
`
		.replaceAll('\n', '')
		.replaceAll('\t', '');
}
