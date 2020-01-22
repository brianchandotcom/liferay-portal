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

import openToast from 'frontend-js-web/liferay/toast/commands/OpenToast.es';
import React, {useEffect, useState, useContext} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import ControlMenu from '../../components/control-menu/ControlMenu.es';
import {Loading} from '../../components/loading/Loading.es';
import useQuery, {toQueryString} from '../../hooks/useQuery.es';
import {confirmDelete, getItem} from '../../utils/client.es';
import FieldPreview from './FieldPreview.es';
import ViewEntryUpperToolbar from './ViewEntryUpperToolbar.es';

const ViewDataLayoutPageValues = ({
	dataDefinition,
	dataLayoutPage,
	dataRecordValues
}) => {
	const {dataLayoutRows} = dataLayoutPage;

	return dataLayoutRows
		.reduce(
			(fields, {dataLayoutColumns = []}) => [
				...fields,
				...dataLayoutColumns.reduce(
					(fields, {fieldNames = []}) => [...fields, ...fieldNames],
					[]
				)
			],
			[]
		)
		.map(fieldName => (
			<FieldPreview
				dataDefinition={dataDefinition}
				dataRecordValues={dataRecordValues}
				fieldName={fieldName}
				key={fieldName}
			/>
		));
};

export default withRouter(
	({
		history,
		match: {
			params: {entryIndex}
		}
	}) => {
		const {basePortletURL, dataDefinitionId, dataLayoutId} = useContext(
			AppContext
		);
		const [isLoading, setLoading] = useState(true);
		const [dataDefinition, setDataDefinition] = useState();
		const [dataLayout, setDataLayout] = useState({});

		const [{dataRecord, page, total}, setResults] = useState({
			dataRecord: {},
			page: 1,
			total: 0
		});

		const [query] = useQuery(history, {
			keywords: '',
			page: 1,
			sort: ''
		});

		useEffect(() => {
			Promise.all([
				getItem(
					`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-records`,
					{...query, page: entryIndex, pageSize: 1}
				).then(({items = [], page, totalCount}) => {
					if (items.length > 0) {
						setResults({
							dataRecord: items.pop(),
							page,
							total: totalCount
						});
					}
				}),
				getItem(
					`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`
				).then(dataDefinition => setDataDefinition(dataDefinition)),
				getItem(
					`/o/data-engine/v2.0/data-layouts/${dataLayoutId}`
				).then(dataLayout => setDataLayout(dataLayout))
			]).then(() => setLoading(false));
		}, [dataDefinitionId, dataLayoutId, entryIndex, query]);

		const {dataRecordValues = {}} = dataRecord;
		const {dataLayoutPages} = dataLayout;

		const onDelete = () => {
			confirmDelete('/o/data-engine/v2.0/data-records/')({
				id: dataRecord.id
			}).then(confirmed => {
				if (confirmed) {
					openToast({
						message: Liferay.Language.get('an-entry-was-deleted'),
						title: Liferay.Language.get('success'),
						type: 'success'
					});

					history.push('/');
				}
			});
		};

		const onEdit = () => {
			Liferay.Util.navigate(
				Liferay.Util.PortletURL.createRenderURL(basePortletURL, {
					dataDefinitionId: dataDefinition.id,
					dataLayoutId: dataLayout.id,
					dataRecordId: dataRecord.id,
					mvcPath: '/edit_entry.jsp',
					redirect: location.href
				})
			);
		};

		const onNext = () => {
			const nextIndex = Math.min(parseInt(entryIndex, 10) + 1, total);

			setLoading(true);

			history.push(`/entries/${nextIndex}?${toQueryString(query)}`);
		};

		const onPrev = () => {
			const prevIndex = Math.max(parseInt(entryIndex, 10) - 1, 1);

			setLoading(true);

			history.push(`/entries/${prevIndex}?${toQueryString(query)}`);
		};

		return (
			<div className="view-entry">
				<ControlMenu
					backURL="../../"
					title={Liferay.Language.get('details-view')}
				/>

				<ViewEntryUpperToolbar
					onDelete={onDelete}
					onEdit={onEdit}
					onNext={onNext}
					onPrev={onPrev}
					page={page}
					total={total}
				/>

				<Loading isLoading={isLoading}>
					<div className="container">
						<div className="justify-content-center row">
							<div className="col-lg-8">
								{dataLayoutPages &&
									dataRecordValues &&
									dataLayoutPages.map(
										(dataLayoutPage, index) => (
											<div className="sheet" key={index}>
												<ViewDataLayoutPageValues
													dataDefinition={
														dataDefinition
													}
													dataLayoutPage={
														dataLayoutPage
													}
													dataRecordValues={
														dataRecordValues
													}
													key={index}
												/>
											</div>
										)
									)}
							</div>
						</div>
					</div>
				</Loading>
			</div>
		);
	}
);
