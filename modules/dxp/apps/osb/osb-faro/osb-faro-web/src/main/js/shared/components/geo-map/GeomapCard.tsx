import * as d3 from 'd3';
import ClayChart from '@clayui/charts';
import GeoMapLangKey from './geo-map-lang-key';
import getCN from 'classnames';
import React, {useEffect, useRef, useState} from 'react';
import ReactDOMServer from 'react-dom/server';
import {Colors} from 'shared/util/charts';
import {toThousands} from 'shared/util/numbers';

const OTHERS = 'others';
const TOTAL_COUNTRIES_LIST = 5;

const List = ({countries, features, onSelectCountry}) => {
	const [hoverList, setHoverList] = useState(null);

	const getLocationName = location =>
		location.toLowerCase() === OTHERS
			? Liferay.Language.get('others')
			: GeoMapLangKey[location];

	const getPathSelected = locationFilter => {
		for (let i = 0; i < features.length; i++) {
			if (features[i].properties.name.includes(locationFilter)) {
				return features[i];
			}
		}

		return locationFilter.includes('Other') ? null : true;
	};

	const handleMouseOverList = (locationList, index) => {
		setHoverList(index < TOTAL_COUNTRIES_LIST ? index : null);

		onSelectCountry(getPathSelected(locationList));
	};

	return (
		<table className='analytics-geomap-table'>
			<tbody>
				{countries
					.filter(
						(value, index) =>
							index < TOTAL_COUNTRIES_LIST || value.id === OTHERS
					)
					.map((value, index) => {
						const classNames = classes =>
							getCN(classes, {
								['lighten-item']:
									hoverList !== null && hoverList !== index,
								['text-l-secondary']: value.id === OTHERS
							});

						return (
							<tr
								key={index}
								onFocus={() =>
									handleMouseOverList(value.name, index)
								}
								onMouseLeave={() => {
									setHoverList(null);

									onSelectCountry(null);
								}}
								onMouseOver={() =>
									handleMouseOverList(value.name, index)
								}
							>
								<td
									className={classNames(
										'text-left font-weight-semibold'
									)}
								>
									{getLocationName(value.name) || value.name}
								</td>

								<td className={classNames('text-right')}>
									{toThousands(value.total)}

									<span className='percentage font-weight-semibold'>{`${value.value}%`}</span>
								</td>
							</tr>
						);
					})}
			</tbody>
		</table>
	);
};

const mergeData = countries => {
	const countriesJson = require('../../../../resources/META-INF/resources/countries.geo.json');
	const data = {...countriesJson.data};

	return {
		...countriesJson,
		features: data.features.map(feature => {
			const country = countries.find(country =>
				feature.properties.name.includes(country.id)
			);

			return {
				...feature,
				properties: {
					...feature.properties,
					total: country?.total ?? 0,
					value: country?.value ?? 0
				}
			};
		})
	};
};

const Tooltip = (payload, metricLabel) => (
	<>
		<div className='arrow' />

		<div className='popover-header'>
			{GeoMapLangKey[payload.properties.name]}
		</div>

		<div className='d-flex justify-content-between popover-body'>
			<div className='mr-4'>
				{payload.properties.total} <span>{metricLabel}</span>
			</div>

			<div>{`${payload.properties.value}%`}</div>
		</div>
	</>
);

export const GeomapCard = ({data, metricLabel}) => {
	const mergedData = mergeData(data.countries);
	const [selectedCountry, setSelectedCountry] = useState(null);

	const chartRef = useRef<any>(null);

	const fillFn = d => {
		if (d && d.properties) {
			const value = d.properties[chartRef.current._color.value];

			if (!value) {
				return Colors.mapEmpty;
			}

			if (selectedCountry) {
				if (d.id === selectedCountry.id) {
					return chartRef.current._color.selected;
				}

				return Colors.mapEmpty;
			}

			return chartRef.current.colorScale!(value);
		}

		return chartRef.current.colorScale!(0);
	};

	useEffect(() => {
		const tooltip = d3
			.select(chartRef.current._element)
			.append('div')
			.attr('class', 'clay-popover-top popover text-2')
			.style('position', 'absolute')
			.style('display', 'none');

		const handleMouseOver = (_feature, index, selection) => {
			const node = selection[index];

			d3.select(node).style('fill', chartRef.current._color.selected);

			const html = feature =>
				ReactDOMServer.renderToString(Tooltip(feature, metricLabel));

			tooltip.html(html(_feature)).style('display', null);
		};

		const handleMouseOut = (_feature, index, selection) => {
			const node = selection[index];

			d3.select(node).style('fill', value =>
				chartRef.current._fillFn(value)
			);

			tooltip.style('display', 'none');
		};

		const handleMoveTooltip = () => {
			const {height, width} = tooltip.node().getBoundingClientRect();

			const tooltipProps = {
				// @ts-ignore
				left: d3.event.layerX - (width / 2 + 5),
				// @ts-ignore
				top: d3.event.layerY - (height + 20)
			};

			tooltip
				.style('left', `${tooltipProps.left}px`)
				.style('top', `${tooltipProps.top}px`);
		};

		const bounds = chartRef.current.svg.node()!.getBoundingClientRect();

		chartRef.current.projection = d3
			.geoMercator()
			.scale(100)
			.translate([bounds.width / 2, bounds.height / 2])
			.fitHeight(bounds.height, chartRef.current._data.data);

		chartRef.current.path = d3
			.geoPath()
			.projection(chartRef.current.projection);

		chartRef.current
			.mapLayer!.selectAll('path')
			.attr('d', chartRef.current.path)
			.on('mousemove', handleMoveTooltip)
			.on('mouseout', handleMouseOut)
			.on('mouseover', handleMouseOver);

		chartRef.current._fillFn = fillFn;
	}, []);

	useEffect(() => {
		chartRef.current.mapLayer!.selectAll('path').style('fill', fillFn);
	}, [selectedCountry]);

	return (
		<div className='analytics-geomap'>
			<div style={{height: 232, width: 350}}>
				<ClayChart
					color={{
						empty: Colors.mapEmpty,
						range: {
							max: Colors.mapMax,
							min: Colors.mapMin
						},
						selected: Colors.mapSelected,
						value: 'total'
					}}
					data={{...mergedData, type: 'geo-map'}}
					ref={chartRef}
				/>
			</div>

			<List
				countries={data.countries}
				features={mergedData.features}
				onSelectCountry={setSelectedCountry}
			/>
		</div>
	);
};
