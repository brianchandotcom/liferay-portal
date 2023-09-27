/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, runScriptsInElement} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef} from 'react';

const Share = ({fetchSharingButtonURL, onError}) => {
	const elRef = useRef(null);

	useEffect(() => {
		if (elRef.current) {
			fetch(fetchSharingButtonURL)
				.then((response) => {
					if (!response.ok) {
						throw new Error(
							`Failed to fetch ${fetchSharingButtonURL}`
						);
					}

					return response.text();
				})
				.then((html) => {
					elRef.current.innerHTML = html;
					runScriptsInElement(elRef.current);
				})
				.catch((error) => {
					if (onError) {
						onError();
					}
					if (process.env.NODE_ENV === 'development') {
						console.error('Failed to fetch share button: ', error);
					}
				});
		}
	}, [fetchSharingButtonURL, onError]);

	return <span ref={elRef} />;
};

Share.propTypes = {
	fetchSharingButtonURL: PropTypes.string.isRequired,
	onError: PropTypes.func,
};

export default Share;
