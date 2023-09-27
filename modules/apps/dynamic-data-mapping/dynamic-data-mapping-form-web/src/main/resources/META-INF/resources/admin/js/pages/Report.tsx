/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useResource} from '@clayui/data-provider';
import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import {FormReport, useConfig} from 'data-engine-js-components-web';
import React from 'react';

import './Report.scss';

export default function Report() {
	const {
		dataEngineModule,
		displayChartAsTable,
		formReportDataURL,
	} = useConfig();
	const {resource} = useResource({link: formReportDataURL});
	const {
		data,
		fields = [],
		formReportRecordsFieldValuesURL = '',
		lastModifiedDate,
		portletNamespace = '',
		totalItems = 0,
	} = (resource as IReportDataResponse) ?? {};

	return (
		<>
			<div className="lfr-ddm__form-report__header" tabIndex={0}>
				<div className="container-fluid container-fluid-max-xl">
					<h2 className="lfr-ddm__form-report__title text-truncate">
						{Liferay.Util.sub(
							totalItems === 1
								? Liferay.Language.get('x-entry')
								: Liferay.Language.get('x-entries'),
							totalItems.toString()
						)}
					</h2>

					<span className="lfr-ddm__form-report__subtitle text-truncate">
						{totalItems > 0
							? lastModifiedDate
							: Liferay.Language.get('there-are-no-entries')}
					</span>
				</div>
			</div>

			<ClayNavigationBar
				className="lfr-ddm__form-report__tabs"
				triggerLabel={Liferay.Language.get('summary')}
			>
				<ClayNavigationBar.Item active>
					<ClayLink>{Liferay.Language.get('summary')}</ClayLink>
				</ClayNavigationBar.Item>
			</ClayNavigationBar>

			<hr className="m-0" />
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
	);
}

interface IReportDataResponse {
	data?: string;
	fields: unknown[];
	formReportRecordsFieldValuesURL: string;
	lastModifiedDate: string;
	portletNamespace: string;
	totalItems: number;
}
