/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import React, {useCallback, useRef, useState} from 'react';

export default function ChangeTrackingRelationshipsView({
	ctMappingInfos,
	namespace,
	orderByTypeFromURL,
	spritemap,
}) {
	const PARAM_ORDER_BY_TYPE = namespace + 'orderByType';
	const ORDER_BY_TYPE_ASC = 'asc';
	const ORDER_BY_TYPE_DESC = 'desc';

	const pathname = window.location.pathname;

	const search = window.location.search;

	const params = new URLSearchParams(search);

	params.delete(PARAM_ORDER_BY_TYPE);

	const basePathRef = useRef(pathname + '?' + params.toString());

	const [ascendingState, setAscendingState] = useState(
		orderByTypeFromURL !== ORDER_BY_TYPE_DESC
	);

	const pushState = (path) => {
		if (Liferay.SPA && Liferay.SPA.app) {
			Liferay.SPA.app.updateHistory_(
				document.title,
				path,
				{
					form: false,
					path,
					senna: true,
				},
				false
			);

			return;
		}

		window.history.pushState({path}, document.title, path);
	};

	const getPath = useCallback(
		(ascending) => {
			let orderByType = ORDER_BY_TYPE_DESC;

			if (ascending) {
				orderByType = ORDER_BY_TYPE_ASC;
			}

			const path =
				basePathRef.current +
				'&' +
				PARAM_ORDER_BY_TYPE +
				'=' +
				orderByType;

			return path;
		},
		[PARAM_ORDER_BY_TYPE]
	);

	const renderTableBody = () => {
		const items = ctMappingInfos.slice(0);

		items.sort((a, b) => {
			if (a.name < b.name) {
				if (ascendingState) {
					return -1;
				}

				return 1;
			}

			if (a.name > b.name) {
				if (ascendingState) {
					return 1;
				}

				return -1;
			}

			return 0;
		});

		return (
			<ClayTable.Body>
				{items.map((ctMappingInfo) => (
					<ClayTable.Row key={ctMappingInfo.tableName}>
						<ClayTable.Cell>
							<strong>{ctMappingInfo.name}</strong>

							{' (' + ctMappingInfo.description + ')'}
						</ClayTable.Cell>
					</ClayTable.Row>
				))}
			</ClayTable.Body>
		);
	};

	const renderTableHead = () => {
		return (
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell>
						<ClayButton
							displayType="unstyled"
							onClick={() => {
								pushState(getPath(!ascendingState));

								setAscendingState(!ascendingState);
							}}
						>
							{Liferay.Language.get('name')}

							<span className="inline-item inline-item-after">
								<ClayIcon
									spritemap={spritemap}
									symbol={
										ascendingState
											? 'order-list-down'
											: 'order-list-up'
									}
								/>
							</span>
						</ClayButton>
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>
		);
	};

	return (
		<div className="publications-changes-content row">
			<div className="col-md-12">
				<ClayTable
					className="publications-table"
					headingNoWrap
					hover
					noWrap
				>
					{renderTableHead()}

					{renderTableBody()}
				</ClayTable>
			</div>
		</div>
	);
}
