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
	draftElementVariation: ElementVariation | null;
	previewURL: string;
}

const ElementVariationsPreview = forwardRef<ElementVariationsPreviewRef, Props>(
	function ElementVariationsPreview(
		{draftElementVariation, previewURL},
		ref
	) {
		const iframeRef = useRef<HTMLIFrameElement>(null);
		const appliedElementVariationRef = useRef<{
			element: Element;
			originalHTML: string | null;
			styleElement: HTMLStyleElement | null;
		} | null>(null);

		const [previewReady, setPreviewReady] = useState(false);

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

			let originalHTML: string | null = null;
			let styleElement: HTMLStyleElement | null = null;

			if (html) {
				originalHTML = element.innerHTML;

				element.innerHTML = html;
			}

			if (js) {
				const scriptElement = iframeDocument.createElement('script');

				scriptElement.textContent = getElementVariationScript(
					draftElementVariation
				);

				iframeDocument.body.appendChild(scriptElement);

				iframeDocument.body.removeChild(scriptElement);
			}

			if (hide) {
				styleElement = iframeDocument.createElement('style');

				styleElement.textContent = `${targetElement} { display: none !important; }`;

				iframeDocument.head.appendChild(styleElement);
			}

			appliedElementVariationRef.current = {
				element,
				originalHTML,
				styleElement,
			};
		}, [draftElementVariation]);

		useEffect(() => {
			applyDraftElementVariation();
		}, [applyDraftElementVariation]);

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
