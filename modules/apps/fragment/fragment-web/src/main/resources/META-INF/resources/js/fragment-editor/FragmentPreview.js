/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {useEventListener, useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {debounce, fetch} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import createFile from './createFile';

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

const PREVIEW_SIZES_LABELS = {
	'desktop': Liferay.Language.get('desktop'),
	'full-size': Liferay.Language.get('full-size'),
	'mobile-portrait': Liferay.Language.get('portrait-phone'),
	'tablet-portrait': Liferay.Language.get('tablet'),
};

const FragmentPreview = ({configuration, css, html, js, urls = {}}) => {
	const iframeRef = useRef();
	const ref = useRef();

	const [currentPreviewSize, setCurrentPreviewSize] = useState('full-size');
	const [loading, setLoading] = useState(false);
	const [previewStyles, setPreviewStyles] = useState({});

	const isMounted = useIsMounted();

	const updatePreviewStyles = useCallback(() => {
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
	}, [currentPreviewSize]);

	useEffect(() => {
		updatePreviewStyles();
	}, [updatePreviewStyles]);

	useEffect(() => {
		if (!isMounted()) {
			return;
		}

		updatePreviewDebounced({
			configuration,
			css,
			html,
			iframe: iframeRef.current,
			isMounted,
			js,
			renderUrl: urls.render,
			setLoading,
		});
	}, [configuration, css, isMounted, html, js, urls]);

	useEventListener('resize', updatePreviewStyles, true, window);

	return (
		<div className="fragment-preview" ref={ref}>
			<div className="btn-group fragment-preview__toolbar">
				{PREVIEW_SIZES.map((previewSize) => (
					<ClayButtonWithIcon
						aria-label={PREVIEW_SIZES_LABELS[previewSize]}
						borderless={true}
						className={classNames({
							active: currentPreviewSize === previewSize,
						})}
						displayType="secondary"
						key={previewSize}
						onClick={() => setCurrentPreviewSize(previewSize)}
						size="sm"
						symbol={previewSize}
						title={PREVIEW_SIZES_LABELS[previewSize]}
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
					onClickCapture={(event) => {
						event.preventDefault();
						event.stopPropagation();
					}}
					onLoad={(event) => {
						event.target.contentWindow.addEventListener(
							'click',
							(event) => {
								event.preventDefault();
								event.stopPropagation();
							}
						);

						updatePreviewDebounced({
							configuration,
							css,
							html,
							iframe: iframeRef.current,
							isMounted,
							js,
							loading,
							renderUrl: urls.render,
							setLoading,
						});
					}}
					ref={iframeRef}
					src={urls.preview}
				></iframe>
			</div>
		</div>
	);
};

const updatePreviewDebounced = debounce(
	({
		configuration,
		css,
		html,
		iframe,
		isMounted,
		js,
		renderUrl,
		setLoading,
	}) => {
		setLoading(true);

		const formData = new FormData();

		formData.append(`configuration`, configuration);
		formData.append(`css`, createFile('css', css));
		formData.append(`html`, createFile('html', html));
		formData.append(`js`, createFile('js', js));

		fetch(renderUrl, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.text())
			.then((response) => {
				if (isMounted()) {
					setLoading(false);
				}

				iframe.contentWindow.Liferay.fire(
					'fragmentEditor:updatePreview',
					{data: response}
				);
			});
	},
	500
);

export default FragmentPreview;
