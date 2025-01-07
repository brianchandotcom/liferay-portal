/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {useId} from 'frontend-js-components-web';
import React, {useMemo} from 'react';

import {useSelector} from '../contexts/StoreContext';
import useOnToggleSidebars from './useOnToggleSidebars';

export default function HideSidebarButton() {
	const id = useId();
	const sidebarHidden = useSelector((state) => state.sidebar.hidden);
	const onToggleSidebars = useOnToggleSidebars();

	const buttonTitle = useMemo(() => {
		const keyLabel = Liferay.Browser?.isMac() ? '⌘' : 'Ctrl';

		return getOpenMenuTooltipMarkup(keyLabel);
	}, []);

	return (
		<>
			<ClayButtonWithIcon
				aria-labelledby={id}
				className="btn btn-secondary"
				data-title={buttonTitle}
				data-title-set-as-html
				displayType="secondary"
				onClick={onToggleSidebars}
				size="sm"
				symbol={sidebarHidden ? 'hidden' : 'view'}
				type="button"
			/>

			<div
				className="sr-only"
				dangerouslySetInnerHTML={{__html: buttonTitle}}
				id={id}
			/>
		</>
	);
}

const getOpenMenuTooltipMarkup = (keyLabel) =>
	`
		<span class="d-block">
			${Liferay.Language.get('toggle-sidebars')}
		</span>

		<kbd class="c-kbd c-kbd-dark mt-1">
			<kbd class="c-kbd">${keyLabel}</kbd>

			<span class="c-kbd-separator">+</span>

			<kbd class="c-kbd">⇧</kbd>

			<span class="c-kbd-separator">+</span>

			<kbd class="c-kbd">.</kbd>
		</kbd>`
		.replaceAll('\n', '')
		.replaceAll('\t', '');
