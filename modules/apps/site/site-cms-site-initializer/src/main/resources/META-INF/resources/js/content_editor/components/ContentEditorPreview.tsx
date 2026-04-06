/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {EVENT_HANDLE_PREVIEW} from './ContentEditorToolbar';

import '../../../css/content_editor/ContentEditorPreview.scss';

export default function ContentEditorPreview({title}: {title: string}) {
	const [isVisible, setIsVisible] = useState<boolean>(false);

	useEffect(() => {
		const handlePreview = ({showPreview}: {showPreview: boolean}) => {
			setIsVisible(showPreview);
		};

		Liferay.on(EVENT_HANDLE_PREVIEW, handlePreview);

		return () => Liferay.detach(EVENT_HANDLE_PREVIEW, handlePreview);
	}, []);

	if (!Liferay.FeatureFlags['LPD-44507']) {
		return null;
	}

	return (
		<div
			className={classNames('content-editor__preview', {
				visible: isVisible,
			})}
		>
			{isVisible ? (
				<div className="border-bottom d-flex justify-content-between p-3">
					<span className="font-weight-bold text-6">
						{sub(Liferay.Language.get('x-preview'), title)}
					</span>
				</div>
			) : null}
		</div>
	);
}
