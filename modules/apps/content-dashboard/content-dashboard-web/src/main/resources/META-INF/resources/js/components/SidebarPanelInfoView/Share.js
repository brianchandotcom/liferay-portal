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
