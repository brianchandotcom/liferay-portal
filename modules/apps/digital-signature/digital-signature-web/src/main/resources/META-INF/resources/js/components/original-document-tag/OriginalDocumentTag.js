/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {ReactPortal} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

export function OriginalDocumentTag({id}) {
	const [container, setContainer] = useState(null);

	useEffect(() => {
		const imageContainer = document.querySelector(
			'.preview-file-container .image-container'
		);
		if (imageContainer) {
			setContainer(imageContainer);
		}
		else {
			setContainer(
				document.querySelector('.preview-file .preview-file-container')
			);
		}
	}, [id]);

	if (!container) {
		return <></>;
	}

	return (
		<ReactPortal container={container}>
			<div className={classNames('preview-image-mark')}>
				<ClayLabel displayType="secondary">
					{Liferay.Language.get('original-document')}
				</ClayLabel>
			</div>
		</ReactPortal>
	);
}
