/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React from 'react';

import './CodeMirrorKeyboardMessage.scss';

interface IProps {
	className?: string;
	keyIsEnabled: boolean;
}

export default function CodeMirrorKeyboardMessage({
	className,
	keyIsEnabled = false,
}: IProps) {
	return (
		<div
			className={classNames(
				className,
				'keyboard-message popover px-2 py-1'
			)}
		>
			<span className="c-kbd-sm">
				{`${sub(
					Liferay.Language.get('x-tab-key-using'),
					keyIsEnabled
						? Liferay.Language.get('enable')
						: Liferay.Language.get('disable')
				)} `}
			</span>

			<kbd className="c-kbd c-kbd-light c-kbd-sm">
				<kbd className="c-kbd">Ctrl</kbd>

				<span className="c-kbd-separator">+</span>

				<kbd className="c-kbd">M</kbd>
			</kbd>
		</div>
	);
}
