/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PanelResizer as Resizer, useObservedMaxWidth} from '@clayui/shared';
import {useEventListener} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import {EVENT_HANDLE_PREVIEW} from './ContentEditorToolbar';

import '../../../css/content_editor/ContentEditorPreview.scss';

const PREVIEW_WIDTH_MIN = 500;

export default function ContentEditorPreview({title}: {title: string}) {
	const [isVisible, setIsVisible] = useState<boolean>(false);
	const [resizeWidth, setResizeWidth] = useState<number>(
		window.innerWidth / 2
	);
	const [resizing, setResizing] = useState<boolean>(false);

	const previewRef = useRef<HTMLDivElement>(null);

	const previewWidthMax = useObservedMaxWidth(previewRef);
	const previewWidth = Math.min(previewWidthMax, resizeWidth);

	useEffect(() => {
		const handlePreview = ({showPreview}: {showPreview: boolean}) => {
			setIsVisible(showPreview);
		};

		Liferay.on(EVENT_HANDLE_PREVIEW, handlePreview);

		return () => Liferay.detach(EVENT_HANDLE_PREVIEW, handlePreview);
	}, []);

	useEventListener('mouseup', () => setResizing(false), true, document);

	if (!Liferay.FeatureFlags['LPD-44507']) {
		return null;
	}

	return (
		<div
			className={classNames('content-editor__preview c-slideout-end', {
				resizing,
				visible: isVisible,
			})}
			ref={previewRef}
			style={{width: previewWidth}}
		>
			{isVisible ? (
				<div className="border-bottom d-flex justify-content-between p-3">
					<span className="font-weight-bold text-6">
						{sub(Liferay.Language.get('x-preview'), title)}
					</span>
				</div>
			) : null}

			<Resizer
				onPanelWidthChange={(width) => {
					setResizeWidth(width);
					setResizing(true);
				}}
				panelWidth={previewWidth}
				panelWidthMax={previewWidthMax}
				panelWidthMin={PREVIEW_WIDTH_MIN}
				position="right"
			/>
		</div>
	);
}
