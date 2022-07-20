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

import {fetch, openToast, runScriptsInElement} from 'frontend-js-web';
import React, {useEffect, useRef} from 'react';

const ManageCollaborators = ({fetchSharingContactsButtonURL}: IProps) => {
	const elementRef = useRef<HTMLSpanElement>(document.createElement('span'));

	useEffect(() => {
		const fetchButton = async () => {
			try {
				const response: Response = await fetch(
					fetchSharingContactsButtonURL
				);

				if (!response.ok) {
					throw new Error(
						`Failed to fetch ${fetchSharingContactsButtonURL}`
					);
				}

				elementRef.current.innerHTML = await response.text();
				runScriptsInElement(elementRef.current);
			}
			catch (error: unknown) {
				openToast({
					message: `${Liferay.Language.get(
						'unexpected-error'
					)}: ${error}`,
					type: 'danger',
				});
			}
		};

		fetchButton();
	}, [fetchSharingContactsButtonURL]);

	return <span ref={elementRef} />;
};

interface IProps {
	children?: React.ReactNode;
	fetchSharingContactsButtonURL: RequestInfo;
}

export default ManageCollaborators;
