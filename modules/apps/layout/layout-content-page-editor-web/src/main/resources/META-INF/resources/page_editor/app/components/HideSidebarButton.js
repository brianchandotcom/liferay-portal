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

import {ClayButtonWithIcon} from '@clayui/button';
import {ReactDOMServer} from '@liferay/frontend-js-react-web';
import React, {useMemo} from 'react';

import {useId} from '../../core/hooks/useId';
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

			<p className="sr-only" id={id}>
				{buttonTitle}
			</p>
		</>
	);
}

const getOpenMenuTooltip = (keyLabel) => (
	<>
		<span className="d-block">
			{Liferay.Language.get('toggle-sidebars')}
		</span>

		<kbd className="c-kbd c-kbd-dark">
			<kbd className="c-kbd">{keyLabel}</kbd>

			<span className="c-kbd-separator">+</span>

			<kbd className="c-kbd">⇧</kbd>

			<span className="c-kbd-separator">+</span>

			<kbd className="c-kbd">.</kbd>
		</kbd>
	</>
);
