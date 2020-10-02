/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {createBrowserHistory} from 'history';
import React, {useMemo} from 'react';

import PartFinder from './components/PartFinder.es';
import Datalist from './components/datalist/Datalist.es';

function convertFiltersToQueryString(filters) {
	return filters.reduce((queryParams, current, i) => {
		const value = Array.isArray(current.value)
			? current.value.join(',')
			: current.value;

		return (
			queryParams +
			(current.field +
				'=' +
				value +
				(i !== filters.length - 1 ? '&' : ''))
		);
	}, '');
}

function App(props) {
	const history = useMemo(
		() => createBrowserHistory({basename: props.basename || '/'}),
		[props.basename]
	);

	if (props.showFilters) {
		return (
			<div className="bom-wrapper container pt-3">
				<div className="mb-3">
					<Datalist
						additionalClasses="mr-3"
						connectorSettings={{
							id: 'carMakerDatalist',
						}}
						datasourceSettings={{
							labelField: 'name',
							on: {
								mapParameters: (data) => {
									return `/${
										data.filters && data.filters.length
											? convertFiltersToQueryString(
													data.filters
											  )
											: ''
									}`;
								},
								parseResponse: (response) => response.data,
							},
							remote: {
								read: props.modelSelectorMakerEndpoint,
							},
							valueField: 'id',
						}}
						label={Liferay.Language.get('car-maker')}
						multiselect={false}
						placeholder={Liferay.Language.get('search-input')}
						spritemap={props.spritemap}
					/>

					<Datalist
						additionalClasses="mr-3"
						connectorSettings={{
							emitters: ['carMakerDatalist'],
							id: 'modelDatalist',
							on: {
								notified: (values, setState, datasource) => {
									const emittersHaveValuesSelected = Object.values(
										values
									).reduce((acc, el) => acc && !!el, true);

									if (emittersHaveValuesSelected) {
										setState({
											disabled: false,
										});
										datasource.setFilter(
											'car-maker',
											values['carMakerDatalist']
										);
										datasource.read();
									}
									else {
										datasource.setFilter('car-maker', null);
										datasource.setFilter('keyword', null);
										setState({
											data: null,
											disabled: true,
											selected: null,
										});
									}
								},
							},
						}}
						datasourceSettings={{
							labelField: 'name',
							on: {
								mapParameters: (data) => {
									return `/${
										data.filters && data.filters.length
											? convertFiltersToQueryString(
													data.filters
											  )
											: ''
									}`;
								},
								parseResponse: (response) => response.data,
							},
							remote: {
								read: props.modelSelectorModelEndpoint,
							},
							valueField: 'id',
						}}
						disabled={true}
						label={Liferay.Language.get('model')}
						multiselect={false}
						placeholder={Liferay.Language.get('search-input')}
						spritemap={props.spritemap}
					/>

					<Datalist
						connectorSettings={{
							emitters: ['carMakerDatalist', 'modelDatalist'],
							id: 'yearDatalist',
							on: {
								notified: (values, setState, datasource) => {
									const emittersHaveValuesSelected = Object.values(
										values
									).reduce((acc, el) => acc && !!el, true);

									if (emittersHaveValuesSelected) {
										setState({
											disabled: false,
										});
										datasource.setFilter(
											'car-maker',
											values['carMakerDatalist']
										);
										datasource.setFilter(
											'model',
											values['modelDatalist']
										);
										datasource.read();
									}
									else {
										datasource.setFilter('model', null);
										datasource.setFilter('car-maker', null);
										datasource.setFilter('keyword', null);
										setState({
											data: null,
											disabled: true,
											selected: null,
										});
									}
								},
							},
						}}
						datasourceSettings={{
							labelField: 'year',
							on: {
								mapParameters: (data) => {
									return `/${
										data.filters && data.filters.length
											? convertFiltersToQueryString(
													data.filters
											  )
											: ''
									}`;
								},
								parseResponse: (response) => response.data,
							},
							remote: {
								read: props.modelSelectorYearEndpoint,
							},
							valueField: 'year',
						}}
						disabled={true}
						label={Liferay.Language.get('year')}
						multiselect={false}
						placeholder={Liferay.Language.get('search-input')}
						spritemap={props.spritemap}
					/>
				</div>

				<PartFinder
					areaApiEndpoint={props.areasEndpoint}
					basePathUrl={props.basePathUrl}
					basename={props.basename}
					connectorSettings={{
						emitters: [
							'carMakerDatalist',
							'modelDatalist',
							'yearDatalist',
						],
						on: {
							notified: (values) => {
								const emittersHaveValuesSelected = Object.values(
									values
								).reduce((acc, el) => acc && !!el, true);
								if (emittersHaveValuesSelected) {
									const query = Object.entries(values)
										.map((el) => `${el[0]}=${el[1]}`)
										.join('&');
									history.push('/folders/' + query);
								}
								else {
									history.push('/');
								}
							},
						},
					}}
					foldersApiEndpoint={props.foldersEndpoint}
					history={history}
					spritemap={props.spritemap}
				/>
			</div>
		);
	}

	return (
		<div className="bom-wrapper container pt-3">
			<PartFinder
				areasEndpoint={props.areasEndpoint}
				basePathUrl={props.basePathUrl}
				basename={props.basename}
				foldersEndpoint={props.foldersEndpoint}
				history={history}
				spritemap={props.spritemap}
			/>
		</div>
	);
}

export default App;
