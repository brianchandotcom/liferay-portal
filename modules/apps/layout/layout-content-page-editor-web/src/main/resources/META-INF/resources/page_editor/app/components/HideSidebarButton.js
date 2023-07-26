/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ReactDOMServer} from '@liferay/frontend-js-react-web';
import {useId} from 'frontend-js-components-web';
import React, {useMemo} from 'react';

import {useDispatch, useSelector} from '../contexts/StoreContext';
import switchSidebarPanel from '../thunks/switchSidebarPanel';

export default function HideSidebarButton() {
	const dispatch = useDispatch();
	const id = useId();
	const sidebarHidden = useSelector((state) => state.sidebar.hidden);

	const buttonTitle = useMemo(() => {
		const keyLabel = Liferay.Browser?.isMac() ? '⌘' : 'Ctrl';

		return getOpenMenuTooltip(keyLabel);
	}, []);

	return (
		<>
			<ClayButtonWithIcon
				aria-labelledby={id}
				className="btn btn-secondary"
				data-title={ReactDOMServer.renderToString(buttonTitle)}
				data-title-set-as-html
				displayType="secondary"
				onClick={() =>
					dispatch(
						switchSidebarPanel({
							hidden: !sidebarHidden,
						})
					)
				}
				size="sm"
				symbol={sidebarHidden ? 'hidden' : 'view'}
				type="button"
			/>

			<div className="sr-only" id={id}>
				{buttonTitle}
			</div>
		</>
	);
}

const getOpenMenuTooltip = (keyLabel) => (
	<>
		<span className="d-block">
			{Liferay.Language.get('toggle-sidebars')}
		</span>

		<kbd className="c-kbd c-kbd-dark mt-1">
			<kbd className="c-kbd">{keyLabel}</kbd>

			<span className="c-kbd-separator">+</span>

			<kbd className="c-kbd">⇧</kbd>

			<span className="c-kbd-separator">+</span>

			<kbd className="c-kbd">.</kbd>
		</kbd>
	</>
);
