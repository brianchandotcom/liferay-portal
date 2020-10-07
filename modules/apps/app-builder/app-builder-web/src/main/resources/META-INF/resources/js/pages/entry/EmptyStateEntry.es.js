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

import ClayButton from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import {fetch, openModal} from 'frontend-js-web';
import React, {useContext, useEffect, useState} from 'react';

import {AppContext} from '../../AppContext.es';
import {errorToast} from '../../utils/toast.es';

export default () => {
	const {appDeploymentType} = useContext(AppContext);
	const [bodyHTML, setBodyHTML] = useState();
	const isSignedIn = themeDisplay.isSignedIn();
	const showLoginButton = !isSignedIn && appDeploymentType === 'standalone';

	useEffect(() => {
		if (showLoginButton) {
			const {href, origin} = window.location;
			let signInURL = `${origin}/c/portal/login`;

			signInURL = Liferay.Util.addParams(
				`p_p_id=com_liferay_login_web_portlet_LoginPortlet`,
				signInURL
			);
			signInURL = Liferay.Util.addParams(
				`windowState=exclusive`,
				signInURL
			);

			fetch(`${signInURL}&redirect=${href}`)
				.then((response) => response.text())
				.then((html) => setBodyHTML(html))
				.catch((error) => errorToast(error.message));
		}
	}, [isSignedIn, showLoginButton]);

	const openSignInModal = () => {
		if (bodyHTML) {
			openModal({
				bodyHTML,
				height: '400px',
				onOpen: () =>
					Liferay.Util.focusFormField(
						'.modal #_com_liferay_login_web_portlet_LoginPortlet_login'
					),
				size: 'md',
				title: Liferay.Language.get('sign-in'),
			});
		}
	};

	return (
		<ClayEmptyState
			description={
				isSignedIn
					? Liferay.Language.get(
							'you-do-not-have-permissions-to-access-this-app-contact-the-app-administrator-to-request-the-access'
					  )
					: Liferay.Language.get(
							'you-do-not-have-access-to-this-app-sign-in-to-access-it'
					  )
			}
			imgSrc={`${themeDisplay.getPathThemeImages()}/app_builder/illustration-locker.svg`}
			title={Liferay.Language.get('no-permissions')}
		>
			{showLoginButton && (
				<ClayButton onClick={openSignInModal}>
					{Liferay.Language.get('sign-in')}
				</ClayButton>
			)}
		</ClayEmptyState>
	);
};
