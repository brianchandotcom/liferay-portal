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

import React, {useCallback, useContext, useEffect, useRef} from 'react';

import PreviewInfoBar from './PreviewInfoBar';
import {StyleBookContext} from './StyleBookContext';
import {config} from './config';

export default function LayoutPreview() {
	const iframeRef = useRef();

	const {frontendTokensValues = {}, previewLayout} = useContext(
		StyleBookContext
	);

	const loadFrontendTokenValues = useCallback(() => {
		if (iframeRef.current) {
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
			}
		}
	}, [frontendTokensValues]);

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
				{previewLayout?.layoutURL ? (
					<>
						{!config.templatesPreviewEnabled && <PreviewInfoBar />}
						<iframe
							className="style-book-editor__page-preview-frame"
							onLoad={() => {
								loadOverlay(iframeRef);
								loadFrontendTokenValues();
							}}
							ref={iframeRef}
							src={urlWithPreviewParameter(
								previewLayout?.layoutURL
							)}
						/>
					</>
				) : (
					<div className="style-book-editor__page-preview-no-page-message">
						{Liferay.Language.get(
							'you-cannot-preview-the-style-book-because-there-are-no-pages-in-this-site'
						)}
					</div>
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
