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
import {Loading} from '../../components/loading/Loading.es';
import {getItem} from '../../utils/client.es';
import FieldPreview from './FieldPreview.es';

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

export default withRouter(({match: {params: {dataRecordId}}}) => {
	const {appId} = useContext(AppContext);
	const [isLoading, setLoading] = useState(true);
	const [dataDefinition, setDataDefinition] = useState(null);
	const [dataLayout, setDataLayout] = useState({});
	const [dataRecord, setDataRecord] = useState({});

	useEffect(() => {
		getItem(`/o/app-builder/v1.0/apps/${appId}`).then(
			({dataDefinitionId, dataLayoutId}) => {
				Promise.all([
					getItem(
						`/o/data-engine/v1.0/data-records/${dataRecordId}`
					).then(dataRecord => setDataRecord(dataRecord)),
					getItem(
						`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}`
					).then(dataDefinition => setDataDefinition(dataDefinition)),
					getItem(
						`/o/data-engine/v1.0/data-layouts/${dataLayoutId}`
					).then(dataLayout => setDataLayout(dataLayout))
				]).then(() => setLoading(false));
			}
		);
	}, [appId, dataRecordId]);

	const {dataRecordValues} = dataRecord;
	const {dataLayoutPages} = dataLayout;

	return (
		<div className="container view-entry">
			<div className="justify-content-center row">
				<div className="col-lg-8">
					<Loading isLoading={isLoading}>
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
					</Loading>
				</div>
			</div>
		</div>
	);
});
