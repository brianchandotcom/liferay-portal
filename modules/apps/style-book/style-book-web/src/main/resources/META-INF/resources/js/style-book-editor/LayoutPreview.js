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

import ClayEmptyState from '@clayui/empty-state';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import React, {
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState,
} from 'react';

import PreviewInfoBar from './PreviewInfoBar';
import {StyleBookContext} from './StyleBookContext';
import {config} from './config';

export default function LayoutPreview() {
	const iframeRef = useRef();
	const [iframeLoaded, setIframeLoaded] = useState(false);

	const {
		frontendTokensValues = {},
		previewLayout,
		loading,
		setLoading,
	} = useContext(StyleBookContext);

	const loadFrontendTokenValues = useCallback(() => {
		if (iframeLoaded) {
			const root = iframeRef.current.contentDocument.documentElement;

			if (root) {
				Object.values(frontendTokensValues).forEach(
					({cssVariableMapping, value}) => {
						root.style.setProperty(
							`--${cssVariableMapping}`,
							value
						);
					}
				);

				setLoading(false);
			}
		}
	}, [frontendTokensValues, setLoading, iframeLoaded]);

	useEffect(() => {
		loadFrontendTokenValues();
	}, [loadFrontendTokenValues, frontendTokensValues]);

	useEffect(() => {
		if (iframeRef.current) {
			iframeRef.current.style['pointer-events'] = 'none';
		}
	}, [previewLayout]);

	return (
		<>
			<div className="style-book-editor__page-preview">
				{loading && previewLayout?.url && (
					<div className="align-items-center d-flex h-100 justify-content-center">
						<ClayLoadingIndicator />
					</div>
				)}

				{previewLayout?.url ? (
					<>
						{!config.templatesPreviewEnabled && <PreviewInfoBar />}
						<iframe
							className={classNames(
								'style-book-editor__page-preview-frame',
								{'d-none': loading}
							)}
							onLoad={() => {
								loadOverlay(iframeRef);
								setIframeLoaded(true);
								loadFrontendTokenValues();
							}}
							ref={iframeRef}
							src={
								config.templatesPreviewEnabled
									? previewLayout?.url
									: urlWithPreviewParameter(
											previewLayout?.url
									  )
							}
						/>
					</>
				) : (
					<ClayEmptyState
						className="h-100 justify-content-center mt-0 style-book-editor__page-preview-empty-site-message"
						description={Liferay.Language.get(
							'you-cannot-preview-the-style-book-because-your-site-is-empty'
						)}
						imgSrc={`${themeDisplay.getPathThemeImages()}/states/empty_state.gif`}
					/>
				)}
			</div>
		</>
	);
}

function urlWithPreviewParameter(url) {
	const nextURL = new URL(url);

	nextURL.searchParams.set('p_l_mode', 'preview');
	nextURL.searchParams.set('styleBookEntryPreview', true);

	return nextURL.href;
}

function loadOverlay(iframeRef) {
	const style = {
		cursor: 'not-allowed',
		height: '100%',
		left: 0,
		position: 'fixed',
		top: 0,
		width: '100%',
		zIndex: 100000,
	};

	if (iframeRef.current) {
		const overlay = document.createElement('div');

		Object.keys(style).forEach((key) => {
			overlay.style[key] = style[key];
		});

		iframeRef.current.removeAttribute('style');
		iframeRef.current.contentDocument.body.append(overlay);
	}
}
