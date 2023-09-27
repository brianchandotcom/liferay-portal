/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useResource} from '@clayui/data-provider';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

// @ts-ignore

import FormReport from '../../form-report/index';

import './PartialResults.scss';

const PartialResults: React.FC<IProps> = ({
	dataEngineModule,
	displayChartAsTable,
	reportDataURL,
}) => {
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
									? sub(Liferay.Language.get('x-entry'), [
											totalItems,
									  ])
									: sub(Liferay.Language.get('x-entries'), [
											totalItems,
									  ])}
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
						dataEngineModule={dataEngineModule}
						displayChartAsTable={displayChartAsTable}
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
	dataEngineModule: string;
	displayChartAsTable: boolean;
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
