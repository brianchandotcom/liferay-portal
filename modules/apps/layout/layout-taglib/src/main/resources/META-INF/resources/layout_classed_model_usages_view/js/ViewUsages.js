/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import ClayTable from '@clayui/table';
import {fetch, openModal, openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

export default function ViewUsages({getUsagesURL}) {
	const [activePage, setActivePage] = useState(1);
	const [totalPages, setTotalPages] = useState(0);

	const [items, setItems] = useState([]);
	const [loading, setLoading] = useState(false);

	const onUsagesLoad = (pageIndex) => {
		setLoading(true);

		const url = new URL(getUsagesURL);

		url.searchParams.set('pageIndex', pageIndex);

		fetch(url)
			.then((response) => response.json())
			.then(({totalNumberOfPages, usages}) => {
				setItems(usages);
				setLoading(false);
				setTotalPages(totalNumberOfPages);
			})
			.catch(() => openErrorToast());
	};

	useEffect(() => {
		onUsagesLoad(1);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	if (!items.length) {
		return (
			<p className="text-secondary">
				{Liferay.Language.get('there-are-no-usages')}
			</p>
		);
	}

	return (
		<div className="cadmin">
			{loading ? (
				<ClayLoadingIndicator className="my-7" size="sm" />
			) : (
				<ClayTable borderless hover={false}>
					<ClayTable.Body>
						{items.map(({id, name, type, url}) => (
							<ClayTable.Row flex key={id}>
								<ClayTable.Cell expanded>
									<p
										className="font-weight-bold m-0 text-truncate"
										title={name}
									>
										{name}
									</p>

									<p className="m-0 text-secondary">{type}</p>
								</ClayTable.Cell>

								{url && (
									<ClayTable.Cell>
										<ClayButtonWithIcon
											aria-label={Liferay.Language.get(
												'view-usage'
											)}
											displayType="secondary"
											onClick={() =>
												openModal({
													title: Liferay.Language.get(
														'preview'
													),
													url,
												})
											}
											size="sm"
											symbol="view"
											title={Liferay.Language.get(
												'view-usage'
											)}
										/>
									</ClayTable.Cell>
								)}
							</ClayTable.Row>
						))}
					</ClayTable.Body>
				</ClayTable>
			)}

			{totalPages > 1 && (
				<ClayPaginationWithBasicItems
					active={activePage}
					ellipsisBuffer={2}
					ellipsisProps={{
						'aria-label': Liferay.Language.get('more'),
						'title': Liferay.Language.get('more'),
					}}
					onActiveChange={(nextPageIndex) => {
						if (nextPageIndex > 0 && nextPageIndex <= totalPages) {
							setActivePage(nextPageIndex);
							onUsagesLoad(nextPageIndex);
						}
					}}
					totalPages={totalPages}
				/>
			)}
		</div>
	);
}

function openErrorToast() {
	openToast({
		message: Liferay.Language.get('an-unexpected-error-occurred'),
		title: Liferay.Language.get('error'),
		type: 'danger',
	});
}
