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

import ClayAlert from '@clayui/alert';
import ClayLink from '@clayui/link';
import {render} from '@liferay/frontend-js-react-web';
import React from 'react';

const DEFAULT_CONTAINER_ID = 'redirectOptionsContainer';

const IGNORED_REDIRECT_OPTIONS =
	'com.liferay.friendly.url.web_ignoreRedirectOptions';

const getDefaultContainer = () => {
	let container = document.getElementById(DEFAULT_CONTAINER_ID);

	if (!container) {
		container = document.createElement('div');
		container.id = DEFAULT_CONTAINER_ID;
		document.body.appendChild(container);
	}

	return container;
};

const RedirectOptions = ({url}) => {
	const handleToastClose = (event) => {
		if (event) {
			Liferay.Util.Session.set(IGNORED_REDIRECT_OPTIONS, true, {
				useHttpSession: true,
			});
		}
	};

	return (
		<ClayAlert.ToastContainer>
			<ClayAlert
				autoClose={5000}
				displayType="info"
				onClose={handleToastClose}
				title={`${Liferay.Language.get('info')}:`}
			>
				{Liferay.Language.get(
					'this-page-has-been-loaded-avoiding-any-redirections'
				)}

				<ClayLink
					className="d-block"
					href={url}
					target="_blank"
				>
					{Liferay.Language.get('continue-to-the-redirection')}
				</ClayLink>
			</ClayAlert>
		</ClayAlert.ToastContainer>
	);
};

export default function main({redirectURL}) {
	render(
		<RedirectOptions url={redirectURL} />,
		{},
		getDefaultContainer()
	);
}
