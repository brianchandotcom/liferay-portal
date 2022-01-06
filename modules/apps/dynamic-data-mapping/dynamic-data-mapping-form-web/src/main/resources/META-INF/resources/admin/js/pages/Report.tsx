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
import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import {FormReport, useConfig} from 'data-engine-js-components-web';
import React from 'react';

import './Report.scss';

export function Report() {
	const {formReportDataURL} = useConfig();
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
			<div className="lfr-ddm__form-report__header">
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
					<ClayLink className="nav-link" displayType="unstyled">
						{Liferay.Language.get('summary')}
					</ClayLink>
				</ClayNavigationBar.Item>
			</ClayNavigationBar>

			<hr className="m-0" />

			<div className="container-fluid container-fluid-max-xl">
				<FormReport
					data={data}
					fields={fields}
					formReportRecordsFieldValuesURL={
						formReportRecordsFieldValuesURL
					}
					portletNamespace={portletNamespace}
				/>
			</div>
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
