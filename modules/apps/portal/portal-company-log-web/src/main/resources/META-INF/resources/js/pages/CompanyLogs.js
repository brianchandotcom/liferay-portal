/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayList from '@clayui/list';
import {fetch} from 'frontend-js-web';
import React, {Fragment, useCallback, useEffect, useState} from 'react';
import {Link} from 'react-router-dom';

const Logs = ({companiesLog, history}) => (
	<ClayList className="mt-3">
		{companiesLog.map(({companyId, companyLogs, webId}, index) => (
			<Fragment key={index}>
				<ClayList.Header>{`${webId} - ${companyId}`}</ClayList.Header>

				{companyLogs.map(({fileName, fileSize}, companyIndex) => (
					<ClayList.Item flex key={companyIndex}>
						<ClayList.ItemField>
							<ClayIcon
								className="mt-1"
								fontSize={18}
								symbol="document"
							/>
						</ClayList.ItemField>

						<ClayList.ItemField expand>
							<ClayList.ItemTitle>
								<Link to={`/${companyId}/${fileName}`}>
									{fileName}

									{companyIndex === 0 && (
										<ClayLabel
											className="ml-2"
											displayType="info"
										>
											{Liferay.Language.get('newest')}
										</ClayLabel>
									)}
								</Link>
							</ClayList.ItemTitle>

							<ClayList.ItemText>{fileSize}</ClayList.ItemText>
						</ClayList.ItemField>

						<ClayList.ItemField>
							<ClayList.QuickActionMenu>
								<ClayList.QuickActionMenu.Item
									onClick={() =>
										history.push(
											`/${companyId}/${fileName}`
										)
									}
									symbol="view"
									title={Liferay.Language.get('view')}
								/>

								<ClayList.QuickActionMenu.Item
									onClick={() =>
										window.open(
											`/o/company-log/${companyId}/${fileName}`,
											'_blank'
										)
									}
									symbol="download"
									title={Liferay.Language.get('download')}
								/>
							</ClayList.QuickActionMenu>
						</ClayList.ItemField>
					</ClayList.Item>
				))}
			</Fragment>
		))}
	</ClayList>
);

const CompanyLogs = ({history}) => {
	const [{companiesLog, loading}, setState] = useState({
		companiesLog: [],
		loading: true,
	});

	const getCompaniesLog = useCallback(async () => {
		const response = await fetch('/o/company-log');

		const data = await response.json();

		setState({companiesLog: data, loading: false});
	}, []);

	useEffect(() => {
		getCompaniesLog();
	}, [getCompaniesLog]);

	return (
		<div>
			<h1>{Liferay.Language.get('logs')}</h1>

			{loading ? (
				<span
					aria-hidden="true"
					className="loading-animation loading-animation-secondary loading-animation-sm"
				/>
			) : (
				<Logs companiesLog={companiesLog} history={history} />
			)}
		</div>
	);
};

export default CompanyLogs;
