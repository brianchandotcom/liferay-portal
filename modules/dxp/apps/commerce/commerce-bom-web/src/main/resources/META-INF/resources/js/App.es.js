import React, {
	useMemo
} from 'react';

import Datalist from './components/datalist/Datalist.es';
import { createBrowserHistory } from 'history';

import PartFinder from './components/PartFinder.es';

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
		() => createBrowserHistory(
			{ basename: props.basename || '/' }
		),
		[
			props.basename
		]
	)

	if(props.showFilters) {	
		return (
			<div className="bom-wrapper container pt-3">
				<div className="mb-3">
					<Datalist
						label={Liferay.Language.get('car-maker')}
						additionalClasses="mr-3"
						multiselect={false}
						placeholder={Liferay.Language.get('search-input')}
						spritemap={props.spritemap}
						datasourceSettings={{
							remote: {
								read: props.modelSelectorMakerEndpoint
							},
							labelField: 'name',
							valueField: 'id',
							on: {
								parseResponse: response => response.data,
								mapParameters: data => {
									return `/${
										data.filters && data.filters.length
											? convertFiltersToQueryString(
													data.filters
											  )
											: ''
									}`;
								}
							}
						}}
						connectorSettings={{
							id: 'carMakerDatalist'
						}}
					/>
	
					<Datalist
						label={Liferay.Language.get('model')}
						additionalClasses="mr-3"
						multiselect={false}
						placeholder={Liferay.Language.get('search-input')}
						spritemap={props.spritemap}
						disabled={true}
						datasourceSettings={{
							remote: {
								read: props.modelSelectorModelEndpoint
							},
							labelField: 'name',
							valueField: 'id',
							on: {
								parseResponse: response => response.data,
								mapParameters: data => {
									return `/${
										data.filters && data.filters.length
											? convertFiltersToQueryString(
													data.filters
											  )
											: ''
									}`;
								}
							}
						}}
						connectorSettings={{
							id: 'modelDatalist',
							emitters: ['carMakerDatalist'],
							on: {
								notified: (values, setState, datasource) => {
									const emittersHaveValuesSelected = Object.values(
										values
									).reduce((acc, el) => acc && !!el, true);
	
									if (emittersHaveValuesSelected) {
										setState({
											disabled: false
										});
										datasource.setFilter(
											'car-maker',
											values['carMakerDatalist']
										);
										datasource.read();
									} else {
										datasource.setFilter('car-maker', null);
										datasource.setFilter('keyword', null);
										setState({
											disabled: true,
											data: null,
											selected: null
										});
									}
								}
							}
						}}
					/>
	
					<Datalist
						label={Liferay.Language.get('year')}
						multiselect={false}
						placeholder={Liferay.Language.get('search-input')}
						spritemap={props.spritemap}
						disabled={true}
						datasourceSettings={{
							remote: {
								read: props.modelSelectorYearEndpoint
							},
							labelField: 'year',
							valueField: 'year',
							on: {
								parseResponse: response => response.data,
								mapParameters: data => {
									return `/${
										data.filters && data.filters.length
											? convertFiltersToQueryString(
													data.filters
											  )
											: ''
									}`;
								}
							}
						}}
						connectorSettings={{
							id: 'yearDatalist',
							emitters: ['carMakerDatalist', 'modelDatalist'],
							on: {
								notified: (values, setState, datasource) => {
									const emittersHaveValuesSelected = Object.values(
										values
									).reduce((acc, el) => acc && !!el, true);
	
									if (emittersHaveValuesSelected) {
										setState({
											disabled: false
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
									} else {
										datasource.setFilter('model', null);
										datasource.setFilter('car-maker', null);
										datasource.setFilter('keyword', null);
										setState({
											disabled: true,
											data: null,
											selected: null
										});
									}
								}
							}
						}}
					/>
				</div>
	
				<PartFinder
					history={history}
					spritemap={props.spritemap}
					areaApiEndpoint={props.areasEndpoint}
					foldersApiEndpoint={props.foldersEndpoint}
					basename={props.basename}
					basePathUrl={props.basePathUrl}
					connectorSettings={{
						emitters: [
							'carMakerDatalist',
							'modelDatalist',
							'yearDatalist'
						],
						on: {
							notified: values => {
								const emittersHaveValuesSelected = Object.values(
									values
								).reduce((acc, el) => acc && !!el, true);
								if (emittersHaveValuesSelected) {
									const query = Object.entries(values)
										.map(el => `${el[0]}=${el[1]}`)
										.join('&');
									history.push('/folders/' + query);
								} else {
									history.push('/');
								}
							}
						}
					}}
				/>
			</div>
		);
	}

	return (
		<div className="bom-wrapper container pt-3">
			<PartFinder
				history={history}
				spritemap={props.spritemap}
				areasEndpoint={props.areasEndpoint}
				foldersEndpoint={props.foldersEndpoint}
				basename={props.basename}
				basePathUrl={props.basePathUrl}
			/>
		</div>
	);
}

export default App;
