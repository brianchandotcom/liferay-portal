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

import React, {useEffect, useState} from 'react';

import lang from '../utils/lang.es';
import {stripHTML} from '../utils/utils.es';

export default ({text}) => {
	const [htmlText, setHtmlText] = useState('');

	useEffect(() => setHtmlText(stripHTML(text)), [text]);

	return (
		<>
			{text && htmlText.length < 15 && (
				<p className="float-right small text-secondary">
					{lang.sub(
						Liferay.Language.get('enter-at-least-x-characters'),
						[15 - htmlText.length]
					)}
				</p>
			)}
		</>
	);
};
