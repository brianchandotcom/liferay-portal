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

import {useIsMounted} from 'frontend-js-react-web';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {createPortal} from 'react-dom';

export default function Frame({children}) {
	const [iframe, setIframe] = useState();
	const [iframeBody, setIframeBody] = useState();
	const setFrameContext = useSetFrameContext();
	const isMounted = useIsMounted();

	useEffect(() => {
		let intervalId = null;

		if (iframe) {
			let _body;

			intervalId = setInterval(() => {
				if (isMounted()) {
					if (_body !== iframe.contentDocument.body) {
						setFrameContext(iframe.contentWindow);
						setIframeBody(iframe.contentDocument.body);

						_body = iframe.contentDocument.body;
					}
				}
			}, 500);
		}

		return () => {
			clearInterval(intervalId);
		};
	}, [iframe, isMounted, setFrameContext]);

	return (
		<>
			<iframe className="page-editor__frame" ref={setIframe}></iframe>
			{iframeBody && createPortal(children, iframeBody)}
		</>
	);
}

const FrameContext = React.createContext({
	getValue: () => {},
	setValue: () => {},
});

export function FrameContextProvider({children}) {
	const [value, setValue] = useState(null);
	const getValue = useCallback(() => value, [value]);

	return (
		<FrameContext.Provider value={{getValue, setValue}}>
			{children}
		</FrameContext.Provider>
	);
}

export function useFrameContext() {
	return useContext(FrameContext).getValue();
}

export function useSetFrameContext() {
	return useContext(FrameContext).setValue;
}
