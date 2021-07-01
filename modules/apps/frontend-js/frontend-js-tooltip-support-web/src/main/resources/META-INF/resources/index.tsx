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

export default () => {
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
};
