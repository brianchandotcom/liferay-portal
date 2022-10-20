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

import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';

import './CodeMirrorKeyboardMessage.scss';

export default function CodeMirrorKeyboardMessage({className, keyIsEnabled}) {
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

CodeMirrorKeyboardMessage.propTypes = {
	className: PropTypes.string,
	keyIsEnabled: PropTypes.bool,
};
