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
import React, {useEffect, useState} from 'react';
import {createPortal} from 'react-dom';

export default function Frame({children}) {
	const [iframe, setIframe] = useState();
	const [iframeBody, setIframeBody] = useState();
	const isMounted = useIsMounted();

	useEffect(() => {
		let intervalId = null;

		if (iframe) {
			let body;

			intervalId = setInterval(() => {
				if (isMounted() && body !== iframe.contentDocument.body) {
					body = iframe.contentDocument.body;
					setIframeBody(body);
				}
			}, 500);
		}

		return () => {
			clearInterval(intervalId);
		};
	}, [iframe, isMounted]);

	return (
		<>
			<iframe className="page-editor__frame" ref={setIframe}></iframe>
			{iframeBody && createPortal(children, iframeBody)}
		</>
	);
}
