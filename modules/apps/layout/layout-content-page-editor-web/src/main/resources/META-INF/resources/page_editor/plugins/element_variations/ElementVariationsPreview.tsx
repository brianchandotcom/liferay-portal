/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {
	forwardRef,
	useCallback,
	useEffect,
	useImperativeHandle,
	useRef,
	useState,
} from 'react';

import {ElementVariation} from './elementVariationsReducer';
import getElementVariationScript from './getElementVariationScript';

export interface ElementVariationsPreviewRef {
	reload: () => void;
}

interface Props {
	defaultLanguageId: string;
	draftElementVariation: ElementVariation | null;
	languageId: string;
	previewURL: string;
}

const ElementVariationsPreview = forwardRef<ElementVariationsPreviewRef, Props>(
	function ElementVariationsPreview(
		{
			defaultLanguageId,
			draftElementVariation,
			languageId,
			previewURL: initialPreviewURL,
		},
		ref
	) {
		const iframeRef = useRef<HTMLIFrameElement>(null);
		const appliedElementVariationRef = useRef<{
			element: Element;
			originalHTML: string | null;
			styleElement: HTMLStyleElement | null;
		} | null>(null);

		const [previewReady, setPreviewReady] = useState(false);
		const [previewURL, setPreviewURL] = useState(
			() => `${initialPreviewURL}&languageId=${languageId}`
		);

		useImperativeHandle(
			ref,
			() => ({
				reload: () => {
					setPreviewReady(false);

					iframeRef.current?.contentWindow?.location.reload();
				},
			}),
			[]
		);

		const applyDraftElementVariation = useCallback(() => {
			const iframeDocument = iframeRef.current?.contentDocument;

			if (!iframeDocument?.body) {
				return;
			}

			const appliedElementVariation = appliedElementVariationRef.current;

			if (appliedElementVariation) {
				if (
					appliedElementVariation.originalHTML !== null &&
					appliedElementVariation.element.isConnected
				) {
					appliedElementVariation.element.innerHTML =
						appliedElementVariation.originalHTML;
				}

				appliedElementVariation.styleElement?.remove();

				appliedElementVariationRef.current = null;
			}

			if (!draftElementVariation?.targetElement) {
				return;
			}

			const {hide, html, js, targetElement} = draftElementVariation;

			const element = iframeDocument.querySelector(targetElement);

			if (!element) {
				return;
			}

			const hideValue = Boolean(
				hide[languageId] ?? hide[defaultLanguageId]
			);
			const htmlValue = html[languageId] ?? html[defaultLanguageId] ?? '';
			const jsValue = js[languageId] ?? js[defaultLanguageId] ?? '';

			let originalHTML: string | null = null;
			let styleElement: HTMLStyleElement | null = null;

			if (htmlValue) {
				originalHTML = element.innerHTML;

				element.innerHTML = htmlValue;
			}

			if (jsValue) {
				const scriptElement = iframeDocument.createElement('script');

				scriptElement.textContent = getElementVariationScript({
					js: jsValue,
					targetElement,
				});

				iframeDocument.body.appendChild(scriptElement);

				iframeDocument.body.removeChild(scriptElement);
			}

			if (hideValue) {
				styleElement = iframeDocument.createElement('style');

				styleElement.textContent = `${targetElement} { display: none !important; }`;

				iframeDocument.head.appendChild(styleElement);
			}

			appliedElementVariationRef.current = {
				element,
				originalHTML,
				styleElement,
			};
		}, [defaultLanguageId, draftElementVariation, languageId]);

		useEffect(() => {
			applyDraftElementVariation();
		}, [applyDraftElementVariation]);

		useEffect(() => {
			setPreviewReady(false);

			setPreviewURL(`${initialPreviewURL}&languageId=${languageId}`);
		}, [initialPreviewURL, languageId]);

		return (
			<div className="d-flex flex-column flex-grow-1 position-relative">
				{previewReady ? null : (
					<ClayLoadingIndicator className="mt-3" />
				)}

				<iframe
					className="border-0 flex-grow-1 w-100"
					onLoad={() => {
						applyDraftElementVariation();

						setPreviewReady(true);
					}}
					ref={iframeRef}
					src={previewURL}
					style={{visibility: previewReady ? 'visible' : 'hidden'}}
					title={Liferay.Language.get('element-variations')}
				/>
			</div>
		);
	}
);

export default ElementVariationsPreview;
