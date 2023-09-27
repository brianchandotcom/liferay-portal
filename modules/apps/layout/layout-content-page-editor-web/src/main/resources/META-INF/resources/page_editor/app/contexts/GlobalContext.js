/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ReactPortal, useIsMounted} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import RawDOM from '../../common/components/RawDOM';
import {config} from '../config/index';

const GlobalContext = React.createContext([{document, window}, () => {}]);

export function GlobalContextFrame({children, useIframe}) {
	const [baseElement, setBaseElement] = useState(null);
	const [iframeContext, setIframeContext] = useState(null);
	const [iframeElement, setIframeElement] = useState(null);
	const isMounted = useIsMounted();
	const [loadIframe, setLoadIframe] = useState(false);
	const localContext = useMemo(() => ({document, window}), []);
	const [, setGlobalContext] = useContext(GlobalContext);

	useEffect(() => {
		if (useIframe && !loadIframe) {
			setLoadIframe(true);
		}
	}, [useIframe, loadIframe]);

	useEffect(() => {
		let timeoutId = null;

		const handleIframeLoaded = () => {
			if (!isMounted() || !iframeElement) {
				return;
			}

			const pageEditorStylesLinkId = `${config.portletNamespace}pageEditorStylesLink`;

			if (
				!iframeElement.contentDocument.getElementById(
					pageEditorStylesLinkId
				)
			) {
				const pageEditorStylesLink = iframeElement.contentDocument.createElement(
					'link'
				);

				pageEditorStylesLink.id = pageEditorStylesLinkId;
				pageEditorStylesLink.rel = 'stylesheet';
				pageEditorStylesLink.href = config.getIframeContentCssURL;

				iframeElement.contentDocument.head.appendChild(
					pageEditorStylesLink
				);
			}

			const element =
				iframeElement.contentDocument &&
				iframeElement.contentDocument.getElementById('content');

			if (element) {
				element.innerHTML = '';

				iframeElement.contentWindow.requestAnimationFrame(() => {
					setBaseElement(element);

					setIframeContext({
						document: iframeElement.contentDocument,
						iframe: iframeElement,
						window: iframeElement.contentWindow,
					});
				});
			}
			else {
				timeoutId = setTimeout(handleIframeLoaded, 100);
			}
		};

		if (iframeElement) {
			iframeElement.addEventListener('load', handleIframeLoaded);
			iframeElement.src = config.getIframeContentURL;
		}

		return () => {
			clearTimeout(timeoutId);

			if (iframeElement) {
				iframeElement.removeEventListener('load', handleIframeLoaded);
			}

			if (isMounted()) {
				setBaseElement(null);
				setIframeContext(null);
			}
		};
	}, [iframeElement, isMounted]);

	let context;
	let content;

	if (useIframe && baseElement && iframeContext) {
		content = <ReactPortal container={baseElement}>{children}</ReactPortal>;
		context = iframeContext;

		iframeElement.classList.remove(
			'page-editor__global-context-iframe--hidden',
			'page-editor__global-context-iframe--loading'
		);
	}
	else if (!useIframe) {
		content = <>{children}</>;
		context = localContext;

		if (iframeElement) {
			iframeElement.classList.add(
				'page-editor__global-context-iframe--hidden'
			);
		}
	}
	else {
		content = <ClayLoadingIndicator />;
		context = localContext;
	}

	useEffect(() => {
		setGlobalContext(context);
	}, [context, setGlobalContext]);

	return (
		<>
			{content}

			{loadIframe ? (
				<RawDOM
					TagName="iframe"
					elementRef={(element) => {
						if (element) {
							element.classList.add(
								'page-editor__global-context-iframe',
								'page-editor__global-context-iframe--loading'
							);
						}

						setIframeElement(element);
					}}
				/>
			) : null}
		</>
	);
}

GlobalContextFrame.propTypes = {
	useIframe: PropTypes.bool,
};

export function GlobalContextProvider({children}) {
	return (
		<GlobalContext.Provider value={useState({document, window})}>
			{children}
		</GlobalContext.Provider>
	);
}

export function useGlobalContext() {
	const [globalContext] = useContext(GlobalContext);

	return globalContext;
}
