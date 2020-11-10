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

import {fetch, runScriptsInElement} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useLayoutEffect, useRef, useState} from 'react';

function Captcha({uri}) {
	const ref = useRef(null);
	const [html, setHtml] = useState(null);

	useEffect(() => {
		fetch(uri)
			.then((res) => res.text())
			.then(setHtml);
	}, [uri]);

	useLayoutEffect(() => {
		if (html) {
			ref.current.innerHTML = html;
			runScriptsInElement(ref.current);
		}
	}, [html]);

	return html ? <div className="captcha w-50" ref={ref} /> : null;
}

Captcha.propTypes = {
	uri: PropTypes.string.isRequired,
};

export default Captcha;
