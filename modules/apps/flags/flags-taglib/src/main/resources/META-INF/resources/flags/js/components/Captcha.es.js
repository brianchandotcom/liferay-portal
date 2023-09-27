/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
