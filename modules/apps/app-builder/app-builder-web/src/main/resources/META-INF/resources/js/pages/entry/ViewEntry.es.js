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

import React, {useEffect, useState, useContext} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import ControlMenu from '../../components/control-menu/ControlMenu.es';
import {Loading} from '../../components/loading/Loading.es';
import useQuery, {toQueryString} from '../../hooks/useQuery.es';
import {getItem} from '../../utils/client.es';
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

export default withRouter(({history, match: {params: {entryIndex}}}) => {
	const {appId, basePortletURL} = useContext(AppContext);
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
		getItem(`/o/app-builder/v1.0/apps/${appId}`).then(
			({dataDefinitionId, dataLayoutId}) => {
				Promise.all([
					getItem(
						`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}/data-records`,
						{...query, page: entryIndex, pageSize: 1}
					).then(({items, page, totalCount}) => {
						setResults({
							dataRecord: items.pop(),
							page,
							total: totalCount
						});
					}),
					getItem(
						`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}`
					).then(dataDefinition => setDataDefinition(dataDefinition)),
					getItem(
						`/o/data-engine/v1.0/data-layouts/${dataLayoutId}`
					).then(dataLayout => setDataLayout(dataLayout))
				]).then(() => setLoading(false));
			}
		);
	}, [appId, entryIndex, query]);

	const {dataRecordValues} = dataRecord;
	const {dataLayoutPages} = dataLayout;

	const onCancel = () => {
		history.push('/');
	};

	const onEdit = () => {
		Liferay.Util.navigate(
			Liferay.Util.PortletURL.createRenderURL(basePortletURL, {
				dataDefinitionId: dataDefinition.id,
				dataLayoutId: dataLayout.id,
				dataRecordId: dataRecord.id,
				mvcPath: '/edit_entry.jsp'
			})
		);
	};

	const onNext = () => {
		const nextIndex = Math.min(parseInt(entryIndex, 10) + 1, total);

		history.push(`/entries/${nextIndex}?${toQueryString(query)}`);
	};

	const onPrev = () => {
		const prevIndex = Math.max(parseInt(entryIndex, 10) - 1, 1);

		history.push(`/entries/${prevIndex}?${toQueryString(query)}`);
	};

	return (
		<>
			<ControlMenu
				backURL="../../"
				title={Liferay.Language.get('details-view')}
			/>

			<Loading isLoading={isLoading}>
				<ViewEntryUpperToolbar
					onCancel={onCancel}
					onEdit={onEdit}
					onNext={onNext}
					onPrev={onPrev}
					page={page}
					total={total}
				/>

				<div className="container view-entry">
					<div className="justify-content-center row">
						<div className="col-lg-8">
							{dataLayoutPages &&
								dataLayoutPages.map((dataLayoutPage, index) => (
									<div className="sheet" key={index}>
										<ViewDataLayoutPageValues
											dataDefinition={dataDefinition}
											dataLayoutPage={dataLayoutPage}
											dataRecordValues={dataRecordValues}
											key={index}
										/>
									</div>
								))}
						</div>
					</div>
				</div>
			</Loading>
		</>
	);
});
