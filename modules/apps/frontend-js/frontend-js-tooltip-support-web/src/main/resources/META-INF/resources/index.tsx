/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayTooltipProvider} from '@clayui/tooltip';
import {render} from '@liferay/frontend-js-react-web';

const SELECTOR_TRIGGER = `
	.lfr-portal-tooltip,
	.manage-collaborators-dialog .lexicon-icon[data-title]:not(.lfr-portal-tooltip),
	.manage-collaborators-dialog .lexicon-icon[title]:not(.lfr-portal-tooltip),
	.manage-collaborators-dialog [data-restore-title],
	.management-bar [data-title]:not(.lfr-portal-tooltip),
	.management-bar [title]:not(.lfr-portal-tooltip),
	.management-bar [data-restore-title],
	.preview-toolbar-container [data-title]:not(.lfr-portal-tooltip),
	.preview-toolbar-container [title]:not(.lfr-portal-tooltip),
	.preview-tooltbar-containter [data-restore-title],
	.progress-container[data-title],
	.redirect-entries [data-title]:not(.lfr-portal-tooltip),
	.source-editor__fixed-text__help[data-title],
	.upper-tbar [data-title]:not(.lfr-portal-tooltip),
	.upper-tbar [title]:not(.lfr-portal-tooltip),
	.upper-tbar [data-restore-title],
	.lfr-tooltip-scope [title]:not(iframe):not([title=""]),
	.lfr-tooltip-scope [data-title]:not([data-title=""]),
	.lfr-tooltip-scope [data-restore-title]:not([data-restore-title=""])
`;

const DEFAULT_TOOLTIP_CONTAINER_ID = 'tooltipContainer';

const getDefaultTooltipContainer = () => {
	let container = document.getElementById(DEFAULT_TOOLTIP_CONTAINER_ID);

	if (!container) {
		container = document.createElement('div');
		container.id = DEFAULT_TOOLTIP_CONTAINER_ID;
		document.body.appendChild(container);
	}

	return container;
};

export default function main() {
	render(
		ClayTooltipProvider,
		{
			containerProps: {
				className: 'cadmin',
			},
			scope: SELECTOR_TRIGGER,
		},
		getDefaultTooltipContainer()
	);
}
