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
