/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {EVENT_HANDLE_PREVIEW} from './ContentEditorToolbar';

export default function ContentEditorPreview({title}: {title: string}) {
	const [isVisible, setIsVisible] = useState<boolean>(false);

	useEffect(() => {
		const handlePreview = ({showPreview}: {showPreview: boolean}) => {
			setIsVisible(showPreview);
		};

		Liferay.on(EVENT_HANDLE_PREVIEW, handlePreview);

		return () => Liferay.detach(EVENT_HANDLE_PREVIEW, handlePreview);
	}, []);

	return (
		<div className={classNames({'d-none': !isVisible})}>
			{sub(Liferay.Language.get('x-preview'), title)}
		</div>
	);
}
