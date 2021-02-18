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

import {useIsMounted} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useMemo, useState} from 'react';
import {createPortal} from 'react-dom';

import RawDOM from '../../common/components/RawDOM';
import {config} from '../config/index';

const GlobalContext = React.createContext([{document, window}, () => {}]);

export function GlobalContextFrame({children, useIframe}) {
	const [baseElement, setBaseElement] = useState(null);
	const localContext = useMemo(() => ({document, window}), []);
	const [iframeContext, setIFrameContext] = useState(null);
	const [iframeElement, setIframeElement] = useState(null);
	const isMounted = useIsMounted();
	const [, setGlobalContext] = useContext(GlobalContext);

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

					setIFrameContext({
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
			iframeElement.classList.add('page-editor__global-context-iframe');
		}

		return () => {
			clearTimeout(timeoutId);

			if (iframeElement) {
				iframeElement.removeEventListener('load', handleIframeLoaded);
			}

			if (isMounted()) {
				setBaseElement(null);
				setIFrameContext(null);
			}
		};
	}, [iframeElement, isMounted]);

	let context;
	let content;

	if (useIframe && baseElement && iframeContext) {
		content = createPortal(<>{children}</>, baseElement);
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
		iframeElement.classList.add(
			'page-editor__global-context-iframe--loading'
		);
	}

	useEffect(() => {
		setGlobalContext(context);
	}, [context, setGlobalContext]);

	return (
		<>
			{content}
			<RawDOM TagName="iframe" elementRef={setIframeElement} />
		</>
	);
}

GlobalContextFrame.propTypes = {
	useIframe: PropTypes.bool,
};

export const GlobalContextProvider = ({children}) => (
	<GlobalContext.Provider value={useState({document, window})}>
		{children}
	</GlobalContext.Provider>
);

export function useGlobalContext() {
	const [globalContext] = useContext(GlobalContext);

	return globalContext;
}
