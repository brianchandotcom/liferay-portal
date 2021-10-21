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
import {
	useEventListener,
	useIsMounted,
	usePrevious,
} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {cancelDebounce, debounce, fetch} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

/**
 * Defined ratios for preview sizing.
 */
const SIZE_RATIOS = {
	'desktop': {
		height: 9,
		width: 16,
	},
	'full-size': {
		height: '',
		width: '',
	},
	'mobile-portrait': {
		height: 16,
		width: 10,
	},
	'tablet-portrait': {
		height: 3,
		width: 4,
	},
};

/**
 * Available preview sizes in order.
 */
const PREVIEW_SIZES = [
	'desktop',
	'tablet-portrait',
	'mobile-portrait',
	'full-size',
];

const stopEventPropagation = (event) => {
	event.preventDefault();
	event.stopPropagation();
};

const FragmentPreview = ({
	configuration,
	css,
	html,
	js,
	namespace,
	urls = {},
}) => {
	const iframeRef = useRef();
	const ref = useRef();

	const [currentPreviewSize, setCurrentPreviewSize] = useState('full-size');
	const [loading, setLoading] = useState(false);
	const [previewStyles, setPreviewStyles] = useState({});

	const isMounted = useIsMounted();

	/* eslint-disable-next-line react-hooks/exhaustive-deps */
	const updatePreview = useCallback(
		debounce(() => {
			if (!loading && isMounted()) {
				setLoading(true);

				const formData = new FormData();

				formData.append(`${namespace}configuration`, configuration);
				formData.append(`${namespace}css`, css);
				formData.append(`${namespace}html`, html);
				formData.append(`${namespace}js`, js);

				fetch(urls.render, {
					body: formData,
					method: 'POST',
				})
					.then((response) => response.text())
					.then((response) => {
						if (isMounted()) {
							setLoading(false);
						}

						iframeRef.current.contentWindow.postMessage(
							JSON.stringify({data: response}),
							'*'
						);
					});
			}
		}, 500),
		[configuration, css, html, js, iframeRef]
	);

	/* eslint-disable-next-line react-hooks/exhaustive-deps */
	const updatePreviewStyles = useCallback(
		debounce(() => {
			const ratio = SIZE_RATIOS[currentPreviewSize];

			if (ratio && ref.current) {
				const wrapperRect = ref.current.getBoundingClientRect();

				const scale = Math.min(
					(wrapperRect.width * 0.9) / ratio.width,
					(wrapperRect.height * 0.8) / ratio.height
				);

				setPreviewStyles({
					height: ratio.height ? `${ratio.height * scale}px` : '',
					width: ratio.width ? `${ratio.width * scale}px` : '',
				});
			}
		}, 100),
		[currentPreviewSize]
	);

	const previousUpdatePreview = usePrevious(updatePreview);
	const previousUpdatePreviewStyles = usePrevious(updatePreviewStyles);

	useEffect(() => {
		if (previousUpdatePreview && previousUpdatePreview !== updatePreview) {
			cancelDebounce(previousUpdatePreview);
			updatePreview();
		}
	}, [previousUpdatePreview, updatePreview]);

	useEffect(() => {
		if (
			previousUpdatePreviewStyles &&
			previousUpdatePreviewStyles !== updatePreviewStyles
		) {
			cancelDebounce(previousUpdatePreviewStyles);
			updatePreviewStyles();
		}
	}, [previousUpdatePreviewStyles, updatePreviewStyles]);

	useEventListener(
		'click',
		stopEventPropagation,
		true,
		iframeRef.current && iframeRef.current.contentWindow
	);

	useEventListener('resize', updatePreviewStyles, true, window);

	return (
		<div className="fragment-preview" ref={ref}>
			<div className="btn-group fragment-preview__toolbar">
				{PREVIEW_SIZES.map((previewSize) => (
					<ClayButtonWithIcon
						borderless={true}
						className={classNames({
							active: currentPreviewSize === previewSize,
						})}
						displayType="secondary"
						key={previewSize}
						onClick={() => setCurrentPreviewSize(previewSize)}
						small={true}
						symbol={previewSize}
					/>
				))}
			</div>

			<div
				className={classNames('fragment-preview__wrapper', {
					'fragment-preview__wrapper--loading': loading,
					'fragment-preview__wrapper--resized': currentPreviewSize,
				})}
				style={previewStyles}
			>
				{loading && (
					<div className="fragment-preview__loading-indicator">
						<span
							aria-hidden="true"
							className="loading-animation"
						></span>
					</div>
				)}

				<iframe
					className="fragment-preview__content"
					onLoad={updatePreview}
					ref={iframeRef}
					src={urls.preview}
				></iframe>
			</div>
		</div>
	);
};

export default FragmentPreview;
