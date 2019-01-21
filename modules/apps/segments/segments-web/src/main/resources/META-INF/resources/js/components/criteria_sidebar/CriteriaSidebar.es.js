import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CriteriaSidebarSearchBar from './CriteriaSidebarSearchBar.es';
import CriteriaSidebarItem from './CriteriaSidebarItem.es';
import {PROPERTY_TYPES} from '../../utils/constants.es';

/**
 * Returns a default value for a property provided
 *
 * @param {*} property
 * @returns
 */
function getDefaultValue(property) {
	const {options, type} = property;

	let defaultValue = '';

	if (type === PROPERTY_TYPES.STRING && options && options.length) {
		defaultValue = options[0].value;
	}
	else if (type === PROPERTY_TYPES.DATE) {
		defaultValue = (new Date()).toISOString();
	}
	else if (type === PROPERTY_TYPES.BOOLEAN) {
		defaultValue = 'true';
	}
	else if (type === PROPERTY_TYPES.INTEGER) {
		defaultValue = 0;
	}
	else if (type === PROPERTY_TYPES.DOUBLE) {
		defaultValue = '0.00';
	}

	return defaultValue;
}

class CriteriaSidebar extends Component {
	static propTypes = {
		propertyKey: PropTypes.string,
		supportedProperties: PropTypes.array,
		title: PropTypes.string
	};

	static defaultProps = {
		supportedProperties: [],
		title: Liferay.Language.get('properties')
	}

	state = {
		searchValue: ''
	};

	_handleOnSearchChange = value => {
		this.setState({searchValue: value});
	}

	_filterProperties = searchValue => {
		return this.props.supportedProperties.filter(
			property => {
				const propertyLabel = property.label.toLowerCase();

				return propertyLabel.includes(searchValue.toLowerCase());
			}
		);
	}

	render() {
		const {propertyKey, supportedProperties, title} = this.props;

		const {searchValue} = this.state;

		const filteredProperties = searchValue ?
			this._filterProperties(searchValue) :
			supportedProperties;

		return (
			<div className="criteria-sidebar-root">
				<div className="sidebar-header">
					{title}
				</div>

				<div className="sidebar-search">
					<CriteriaSidebarSearchBar
						onChange={this._handleOnSearchChange}
						searchValue={searchValue}
					/>
				</div>

				<ul className="properties-list">
					{filteredProperties.length ?
						filteredProperties.map(
							({label, name, options, type}, index) => {
								const defaultValue = getDefaultValue(
									{
										label,
										name,
										options,
										type
									}
								);

								return (
									<CriteriaSidebarItem
										className={`color--${propertyKey}`}
										defaultValue={defaultValue}
										key={index}
										label={label}
										name={name}
										type={type}
									/>
								);
							}
						) :
						<li className="empty-message">
							{Liferay.Language.get('no-results-were-found')}
						</li>
					}
				</ul>
			</div>
		);
	}
}

export default CriteriaSidebar;