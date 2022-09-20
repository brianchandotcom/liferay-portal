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

import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {fetch, sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import formatDate from './utils/formatDate';

const useIsFirstRender = (): boolean => {
	const isFirstRef = useRef(true);

	if (isFirstRef.current) {
		isFirstRef.current = false;

		return true;
	}

	return isFirstRef.current;
};

const VersionsContent = ({
	getItemVersionsURL,
	languageTag = 'en',
	onError,
}: IProps) => {
	const [loading, setLoading] = useState(false);
	const [versions, setVersions] = useState([] as IVersion[]);
	const isFirst: boolean = useIsFirstRender();
	const getVersionsData = useCallback(async (): Promise<void> => {
		try {
			setLoading(true);
			const response: Response = await fetch(getItemVersionsURL);

			if (!response.ok) {
				throw new Error(`Failed to fetch ${getItemVersionsURL}`);
			}

			const {versions}: IData = await response.json();
			setVersions(versions);
		}
		catch (error: unknown) {
			onError();

			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}
		}
		finally {
			setLoading(false);
		}
	}, [getItemVersionsURL, onError]);

	useEffect((): void => {

		// prevent the initial fetch when the tab is inactive

		if (isFirst) {
			return;
		}
		getVersionsData();
	}, [getVersionsData, isFirst]);

	return (
		<>
			{loading ? (
				<div className="align-items-center d-flex loading-indicator-wrapper">
					<ClayLoadingIndicator small />
				</div>
			) : (
				<ul className="list-group sidebar-list-group">
					{versions.map((version) => (
						<li
							className="list-group-item list-group-item-flex"
							key={version.version}
						>
							<ClayLayout.ContentCol expand>
								<div className="list-group-title">
									{Liferay.Language.get('version') + ' '}

									{version.version}
								</div>

								<div className="list-group-subtitle">
									{sub(Liferay.Language.get('x-by-x'), [
										formatDate(
											version.createDate,
											languageTag
										),
										version.userName,
									])}
								</div>

								<div className="list-group-subtext">
									{version.changeLog
										? version.changeLog
										: Liferay.Language.get('no-change-log')}
								</div>
							</ClayLayout.ContentCol>
						</li>
					))}
				</ul>
			)}
		</>
	);
};

interface IData {
	versions: IVersion[];
}

interface IProps {
	getItemVersionsURL: string;
	languageTag?: string;
	onError: () => void;
}

interface IVersion {
	changeLog?: string;
	createDate: string;
	statusLabel: string;
	statusStyle: string;
	userName: string;
	version: string;
}

export default VersionsContent;
