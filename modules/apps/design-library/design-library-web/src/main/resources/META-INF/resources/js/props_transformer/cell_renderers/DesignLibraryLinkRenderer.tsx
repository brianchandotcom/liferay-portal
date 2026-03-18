/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClaySticker from '@clayui/sticker';
import {findAction, replaceTokens} from '@liferay/frontend-data-set-web';
import classNames from 'classnames';
import React from 'react';

import {ActionItem, DesignLibraryItem} from '../../types';

export function DesignLibraryLinkRenderer({
	actions,
	itemData,
	options,
	value,
}: {
	actions: ActionItem[];
	itemData: DesignLibraryItem;
	options: {actionId: string};
	value: string;
}) {
	const {actionId} = options;
	const title =
		value && value !== '' ? value : Liferay.Language.get('untitled-asset');

	if (!actions.length || !actionId) {
		return <>{title}</>;
	}

	const selectedAction = findAction(actions, actionId);

	if (!selectedAction?.href) {
		return <>{title}</>;
	}

	const formattedHref = replaceTokens(selectedAction.href, itemData);

	return (
		<div className="align-items-center d-flex table-list-title">
			<ClaySticker
				className={classNames(
					'c-mr-2',
					'flex-shrink-0',
					'inline-item',
					'inline-item-before',
					'design-library-fds-sticker-books'
				)}
			>
				<ClayIcon symbol="books" />
			</ClaySticker>

			<ClayLink aria-label={title} data-senna-off href={formattedHref}>
				{title}
			</ClayLink>
		</div>
	);
}
