/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {fetch, sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import VersionActions, {IAction} from './VersionActions';
import formatDate from './utils/formatDate';

const VersionsContent = ({
	active,
	getItemVersionsURL,
	languageTag = 'en',
	onError,
}: IProps) => {
	const [loading, setLoading] = useState(false);
	const dataIsFetchedRef = useRef(false);

	const [versionsData, setVersionsData] = useState({
		versions: [],
		viewVersionsURL: '',
	} as IData);

	const getVersionsData = useCallback(async (): Promise<void> => {
		try {
			setLoading(true);
			const response: Response = await fetch(getItemVersionsURL);

			if (!response.ok) {
				throw new Error(`Failed to fetch ${getItemVersionsURL}`);
			}

			const data: IData = await response.json();

			setVersionsData(data);
			dataIsFetchedRef.current = true;
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
		if (active && !dataIsFetchedRef.current) {
			getVersionsData();
		}
	}, [active, getVersionsData]);

	const {versions, viewVersionsURL} = versionsData;

	return (
		<>
			{loading ? (
				<div className="align-items-center d-flex loading-indicator-wrapper">
					<ClayLoadingIndicator small />
				</div>
			) : (
				<>
					<ul className="list-group sidebar-list-group">
						{versions.map(
							({
								actions,
								changeLog,
								createDate,
								userName,
								version,
							}) => (
								<li
									className="list-group-item list-group-item-flex p-0"
									key={version}
								>
									<ClayLayout.ContentCol expand>
										<div className="list-group-title">
											{Liferay.Language.get('version') +
												' '}

											{version}
										</div>

										<div className="list-group-subtitle">
											{sub(
												Liferay.Language.get('x-by-x'),
												[
													formatDate(
														createDate,
														languageTag
													),
													userName,
												]
											)}
										</div>

										<div className="list-group-subtext">
											{changeLog
												? changeLog
												: Liferay.Language.get(
														'no-change-log'
												  )}
										</div>
									</ClayLayout.ContentCol>

									{actions && !!actions.length && (
										<VersionActions actions={actions} />
									)}
								</li>
							)
						)}
					</ul>
					{viewVersionsURL && (
						<div className="d-flex justify-content-center">
							<ClayLink
								button
								className="mt-3"
								displayType="secondary"
								href={viewVersionsURL}
							>
								{Liferay.Language.get('view-more')}
							</ClayLink>
						</div>
					)}
				</>
			)}
		</>
	);
};
interface IData {
	versions: IVersion[];
	viewVersionsURL: string;
}

interface IProps {
	active: boolean;
	getItemVersionsURL: string;
	languageTag?: string;
	onError: () => void;
}

interface IVersion {
	actions: IAction[];
	changeLog?: string;
	createDate: string;
	statusLabel: string;
	statusStyle: string;
	userName: string;
	version: string;
}

export default VersionsContent;
