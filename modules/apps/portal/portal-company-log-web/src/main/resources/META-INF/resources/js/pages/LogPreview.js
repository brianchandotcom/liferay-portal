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

import {ClayButtonWithIcon} from '@clayui/button';
import {fetch} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

const LogPreview = ({
	history,
	match: {
		params: {companyId, fileName},
	},
}) => {
	const [{loading, logs}, setState] = useState({
		loading: true,
		logs: '',
	});

	const getCompanyLog = useCallback(async () => {
		const response = await fetch(
			`/o/company-log/${companyId}/${fileName}?action=read`
		);

		const data = await response.text();

		setState({loading: false, logs: data});
	}, [companyId, fileName]);

	useEffect(() => {
		getCompanyLog();
	}, [getCompanyLog]);

	return (
		<section>
			<div className="d-flex justify-content-between mb-2">
				<h1>
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('back')}
						displayType="unstyled"
						onClick={() => history.push('/')}
						size="sm"
						symbol="angle-left"
						title={Liferay.Language.get('back')}
					/>

					{fileName}
				</h1>

				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('download')}
					displayType="unstyled"
					onClick={() =>
						window.open(
							`/o/company-log/${companyId}/${fileName}`,
							'_blank'
						)
					}
					symbol="download"
					title={Liferay.Language.get('download')}
				/>
			</div>

			{loading ? (
				<span
					aria-hidden="true"
					className="loading-animation loading-animation-secondary loading-animation-sm"
				/>
			) : (
				<div className="bg-dark">
					<pre className="logs-container px-4 text-white">{logs}</pre>
				</div>
			)}
		</section>
	);
};

export default LogPreview;
