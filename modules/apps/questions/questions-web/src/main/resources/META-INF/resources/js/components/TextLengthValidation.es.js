/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

import lang from '../utils/lang.es';
import {stripHTML} from '../utils/utils.es';

export default function TextLengthValidation({text}) {
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
}
