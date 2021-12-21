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

import {useResource} from '@clayui/data-provider';
import React, {useEffect, useState} from 'react';

// @ts-ignore

import FormReport from '../../form-report/index';

import './PartialResults.scss';

const PartialResults: React.FC<IProps> = ({reportDataURL}) => {
	const [resourceState, setResourceState] = useState(() => 'loading');
	const {resource} = useResource({
		fetch,
		link: reportDataURL,
		onNetworkStatusChange: (status) => {
			let resourceState = 'idle';
			if (status < 4) {
				resourceState = 'loading';
			}
			else if (status === 5) {
				resourceState = 'error';
			}
			setResourceState(resourceState);
		},
	});

	const {
		data,
		fields = [],
		formReportRecordsFieldValuesURL,
		lastModifiedDate,
		portletNamespace,
		totalItems = 0,
	} = (resource as IReportDataResponse) ?? {};

	useEffect(() => {
		const formsPortlet = document.querySelector('.portlet-forms');

		const localeActions = document.querySelector('.locale-actions');

		formsPortlet?.classList.add('lfr-de__partial-results--background');
		localeActions?.classList.add('hide');

		return () => {
			formsPortlet?.classList.remove(
				'lfr-de__partial-results--background'
			);
			localeActions?.classList.remove('hide');
		};
	}, []);

	return (
		<>
			{resourceState !== 'loading' && (
				<>
					<div className="lfr-de__partial-results-entries">
						<div className="align-items-center">
							<span className="lfr-de__partial-results-title text-truncate">
								{totalItems === 1
									? Liferay.Util.sub(
											Liferay.Language.get('x-entry'),
											[totalItems]
									  )
									: Liferay.Util.sub(
											Liferay.Language.get('x-entries'),
											[totalItems]
									  )}
							</span>
						</div>

						<div className="align-items-center">
							<span className="lfr-de__partial-results-subtitle text-truncate">
								{totalItems > 0
									? lastModifiedDate
									: Liferay.Language.get(
											'there-are-no-entries'
									  )}
							</span>
						</div>
					</div>

					<FormReport
						data={data}
						fields={fields}
						formReportRecordsFieldValuesURL={
							formReportRecordsFieldValuesURL
						}
						portletNamespace={portletNamespace}
					/>
				</>
			)}
		</>
	);
};

export default PartialResults;

interface IProps {
	reportDataURL: string;
}

interface IReportDataResponse {
	data?: string;
	fields: unknown[];
	formReportRecordsFieldValuesURL: string;
	lastModifiedDate: string;
	portletNamespace: string;
	totalItems: number;
}
