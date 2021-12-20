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

import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import {FormReport} from 'data-engine-js-components-web';
import React from 'react';

import './index.scss';

export default function Index({
	lastModifiedDate,
	totalItems,
	...otherProps
}: IProps) {
	return (
		<>
			<div className="portlet-ddm-form-report__header">
				<div className="container-fluid container-fluid-max-xl">
					<h2 className="portlet-ddm-form-report__title text-truncate">
						{Liferay.Util.sub(
							totalItems === 1
								? Liferay.Language.get('x-entry')
								: Liferay.Language.get('x-entries'),
							totalItems.toString()
						)}
					</h2>

					<span className="portlet-ddm-form-report__subtitle text-truncate">
						{totalItems > 0
							? lastModifiedDate
							: Liferay.Language.get('there-are-no-entries')}
					</span>
				</div>
			</div>

			<ClayNavigationBar
				className="portlet-ddm-form-report__tabs"
				triggerLabel={Liferay.Language.get('summary')}
			>
				{

					// TODO: remove this workaround when the following issue is fixed
					// ClayNavigationBar throws an exception if only a single item
					// is provided: https://github.com/liferay/clay/issues/4517

					[1].map((key) => (
						<ClayNavigationBar.Item active key={key}>
							<ClayLink
								className="nav-link"
								displayType="unstyled"
							>
								{Liferay.Language.get('summary')}
							</ClayLink>
						</ClayNavigationBar.Item>
					))
				}
			</ClayNavigationBar>

			<hr className="m-0" />

			<div className="container-fluid container-fluid-max-xl">
				<FormReport {...otherProps} />
			</div>
		</>
	);
}

interface IProps {
	data?: string;
	fields: unknown;
	formReportRecordsFieldValuesURL: string;
	lastModifiedDate: string;
	portletNamespace: string;
	totalItems: number;
}
