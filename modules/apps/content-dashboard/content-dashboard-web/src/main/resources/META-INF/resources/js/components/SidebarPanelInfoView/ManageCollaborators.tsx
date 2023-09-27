/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, runScriptsInElement} from 'frontend-js-web';
import React, {useEffect, useRef} from 'react';

const ManageCollaborators = ({
	fetchSharingCollaboratorsURL,
	onError,
}: IProps) => {
	const elementRef = useRef<HTMLDivElement>(document.createElement('div'));

	useEffect(() => {
		const fetchButton = async (): Promise<void> => {
			try {
				const response: Response = await fetch(
					fetchSharingCollaboratorsURL
				);

				if (!response.ok) {
					throw new Error(
						`Failed to fetch ${fetchSharingCollaboratorsURL}`
					);
				}

				elementRef.current.innerHTML = await response.text();
				runScriptsInElement(elementRef.current);
			}
			catch (error: unknown) {
				onError();

				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			}
		};

		fetchButton();
	}, [fetchSharingCollaboratorsURL, onError]);

	return <div className="c-mt-4 manage-collaborators" ref={elementRef} />;
};

interface IProps {
	children?: React.ReactNode;
	fetchSharingCollaboratorsURL: RequestInfo;
	onError: Function;
}

export default ManageCollaborators;
