import autobind from 'autobind-decorator';
import BasePage from 'shared/components/base-page';
import GeoMapLangKey from './geo-map-lang-key';
import GeomapReact from './index';
import getCN from 'classnames';
import Loading from 'shared/components/Loading';
import memoize from 'memoize-one';
import React from 'react';
import {getFilters} from 'shared/util/filter';
import {PropTypes} from 'prop-types';
import {toThousands} from 'shared/util/numbers';

const CLASSNAME = 'analytics-geomap';
const OTHERS = 'others';
const TOTAL_COUNTRIES_LIST = 5;

/**
 * Geo Location
 * @class
 */
class GeoLocation extends React.Component {
	static contextType = BasePage.Context;

	static defaultProps = {
		data: {
			countries: [],
			total: 0
		},
		loading: false,
		metricLabel: Liferay.Language.get('views')
	};

	/**
	 * GeoLocation props definition.
	 * @type {!Object}
	 * @static
	 */
	static propTypes = {
		data: PropTypes.shape({
			countries: PropTypes.arrayOf(
				PropTypes.shape({
					group: PropTypes.string.isRequired,
					id: PropTypes.string.isRequired,
					name: PropTypes.string.isRequired,
					total: PropTypes.number.isRequired,
					value: PropTypes.string.isRequired
				})
			),
			total: PropTypes.number
		}),

		/**
		 * @type {boolean}
		 * @default true
		 */
		loading: PropTypes.bool,

		/**
		 * @type {string}
		 * @default views
		 */
		metricLabel: PropTypes.string
	};

	/**
	 * GeoLocation state definition.
	 * @type {!Object}
	 * @static
	 */
	state = {
		/**
		 * @type {int|null}
		 * @default null
		 */
		hoverList: null,

		/**
		 * @type {object}
		 * @default object
		 */
		paths: {features: []},

		/**
		 * @type {Object|null}
		 * @default null
		 */
		selected: null
	};

	memoizeMerge = memoize((countriesJson, jsonPath) =>
		// eslint-disable-next-line no-invalid-this
		this.mergeData(countriesJson, jsonPath)
	);

	/**
	 * Lifecycle Component Did Mount - ReactJS
	 */
	componentDidMount() {
		this.loadPaths().then(paths => {
			const pathSelected = this.handleSelected();
			this.setState({
				paths,
				selected: pathSelected
			});
		});
	}

	/**
	 * Lifecycle Component Did Update - ReactJS
	 */
	componentDidUpdate(prevProps) {
		const {
			filters: {location}
		} = this.props;

		if (location !== prevProps.filters.location) {
			const selected = this.handleSelected();

			this.setState({
				selected
			});
		}
	}

	/**
	 * Handle Selected
	 */
	handleSelected() {
		const {location} = getFilters(this.context.filters);
		const pathSelected = this.getPathSelected(location);

		return location != 'Any' ? pathSelected : null;
	}

	/**
	 * Handle Mouse Over List
	 * @param {Object|Array} locationList
	 * @param {number} index
	 */
	handleMouseOverList(locationList, index) {
		const {location} = getFilters(this.context.filters);
		if (location == 'Any') {
			this.setState({
				hoverList: index < TOTAL_COUNTRIES_LIST ? index : null,
				selected: this.getPathSelected(locationList)
			});
		}
	}

	/**
	 * Handle Mouse Out List
	 */
	@autobind
	handleMouseOutList() {
		const {location} = getFilters(this.context.filters);
		if (location == 'Any') {
			this.setState({
				hoverList: null,
				selected: null
			});
		}
	}

	/**
	 * Load Paths
	 */
	loadPaths() {
		const data = import(
			'../../../../resources/META-INF/resources/countries.geo.json'
		);

		return data
			.then(paths => {
				paths.data.features = this.normalizeGeoData(paths.data);
				return paths.data;
			})
			.catch(() => ({features: []}));
	}

	/**
	 * Normalize Geo Data
	 * @param {object} param0
	 */
	normalizeGeoData({features}) {
		return features.map(({properties: {name}, ...otherProps}) => ({
			...otherProps,
			properties: {
				countryName: GeoMapLangKey[name],
				name,
				total: 0,
				value: 0
			}
		}));
	}

	/**
	 * Merge Data
	 * @param {array} data
	 */
	mergeData(countries, jsonPath) {
		const {features} = jsonPath;

		loop1: for (let i = 0; i < features.length; i++) {
			features[i].properties = Object.assign(features[i].properties, {
				total: 0,
				value: 0
			});
			for (let j = 0; j < countries.length; j++) {
				if (features[i].properties.name.includes(countries[j].id)) {
					features[i].properties = Object.assign(
						features[i].properties,
						{
							total: countries[j].total,
							value: countries[j].value
						}
					);
					continue loop1;
				}
			}
		}

		return {features, ...jsonPath};
	}

	/**
	 * Get Path Selected
	 * @param {object} locationFilter
	 */
	getPathSelected(locationFilter) {
		const {features} = this.state.paths;

		for (let i = 0; i < features.length; i++) {
			if (features[i].properties.name.includes(locationFilter)) {
				return features[i];
			}
		}

		return locationFilter.includes('Other') ? null : true;
	}

	/**
	 * Render List
	 */
	renderList() {
		const {data} = this.props;

		const getLocationName = location =>
			location.toLowerCase() === OTHERS
				? Liferay.Language.get('others')
				: GeoMapLangKey[location];

		return (
			<table className={`${CLASSNAME}-table`}>
				<tbody>
					{data.countries
						.filter(
							(value, index) =>
								index < TOTAL_COUNTRIES_LIST ||
								value.id === OTHERS
						)
						.map((value, index) => {
							const {hoverList} = this.state;
							const otherClass = getCN({
								['lighten-item']:
									hoverList !== null && hoverList !== index,
								['text-l-secondary']: value.id === OTHERS
							});
							return (
								<tr
									key={index}
									// eslint-disable-next-line
									onFocus={this.handleMouseOverList.bind(
										this,
										value.name,
										index
									)}
									onMouseLeave={this.handleMouseOutList}
									// eslint-disable-next-line
									onMouseOver={this.handleMouseOverList.bind(
										this,
										value.name,
										index
									)}
								>
									<td
										className={`text-left font-weight-semibold ${otherClass}`}
									>
										{getLocationName(value.name) ||
											value.name}
									</td>

									<td className={`text-right ${otherClass}`}>
										{toThousands(value.total)}{' '}
										<span className='percentage font-weight-semibold'>{`${value.value}%`}</span>
									</td>
								</tr>
							);
						})}
				</tbody>
			</table>
		);
	}

	/**
	 * Lifecycle Render - ReactJS
	 */
	render() {
		const {
			className,
			data: {countries},
			loading,
			metricLabel
		} = this.props;

		if (loading) {
			return <Loading />;
		}

		const {paths, selected} = this.state;
		const mergedCountries = this.memoizeMerge(countries, paths);

		return (
			<div className={getCN(CLASSNAME, className)}>
				<GeomapReact
					color={{value: 'total'}}
					data={mergedCountries}
					height='232px'
					metriclabel={metricLabel}
					selected={selected}
					width='350px'
				/>

				{this.renderList()}
			</div>
		);
	}
}

export default GeoLocation;
