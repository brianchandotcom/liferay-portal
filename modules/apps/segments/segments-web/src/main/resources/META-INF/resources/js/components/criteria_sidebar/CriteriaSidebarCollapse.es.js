import ClayIcon from '../shared/ClayIcon.es';
import CriteriaSidebarItem from './CriteriaSidebarItem.es';
import getCN from 'classnames';
import PropTypes from 'prop-types';
import React, {Component} from 'react';
import {jsDatetoYYYYMMDD} from '../../utils/utils.es';
import {PROPERTY_TYPES} from '../../utils/constants.es';

/**
 * Returns a default value for a property provided.
 * @param {Object} property
 * @returns
 */
function getDefaultValue(property) {
	const {options, type} = property;

	let defaultValue = '';

	if (type === PROPERTY_TYPES.STRING && options && options.length) {
		defaultValue = options[0].value;
	}
	else if (type === PROPERTY_TYPES.DATE) {
		defaultValue = jsDatetoYYYYMMDD(new Date());
	}
	else if (type === PROPERTY_TYPES.DATE_TIME) {
		defaultValue = new Date().toISOString();
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

class CriteriaSidebarCollapse extends Component {
	static propTypes = {
		onCollapseClick: PropTypes.func,
		propertyGroups: PropTypes.array,
		propertyKey: PropTypes.string,
		searchValue: PropTypes.string
	};

	_filterProperties = searchValue => {
		const propertyGroup = this.props.propertyGroups.find(
			propertyGroup => this.props.propertyKey === propertyGroup.propertyKey
		);

		const properties = propertyGroup ? propertyGroup.properties : [];

		return properties.filter(
			property => {
				const propertyLabel = property.label.toLowerCase();

				return propertyLabel.indexOf(searchValue.toLowerCase()) !== -1;
			}
		);
	}

	_handleClick = (key, editing) => () => {
		this.props.onCollapseClick(key, editing);
	}

	render() {
		const {propertyGroups, propertyKey, searchValue} = this.props;

		const propertyGroup = propertyGroups.find(
			propertyGroup => propertyKey === propertyGroup.propertyKey
		);

		const properties = propertyGroup ? propertyGroup.properties : [];

		const filteredProperties = searchValue ? this._filterProperties(searchValue) : properties;

		return (
			<ul className="sidebar-collapse-groups list-unstyled">
				{
					propertyGroups.map(
						propertyGroup => {
							const active = propertyGroup.propertyKey === propertyKey;

							const activeClasses = getCN(
								{
									active
								}
							);

							const propertyListClasses = getCN(
								'properties-list',
								activeClasses
							);

							const key = propertyGroup.propertyKey;

							return (<li className={`sidebar-collapse-${propertyGroup.propertyKey}`} key={key}>
								<div className="sidebar-collapse-header-root" onClick={this._handleClick(key, active)}>
									<a className="sidebar-collapse-header d-flex justify-content-between">
										{propertyGroup.name}
										<span className="collapse-icon">
											<ClayIcon className={activeClasses} iconName="angle-right" />
										</span>
									</a>
								</div>
								<ul className={propertyListClasses}>
									{active && filteredProperties.length === 0 &&
										<li className="empty-message">
											{Liferay.Language.get('no-results-were-found')}
										</li>
									}

									{active && filteredProperties.length &&
										filteredProperties.map(
											({label, name, options, type}) => {
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
														className={`color--${key}`}
														defaultValue={defaultValue}
														key={name}
														label={label}
														name={name}
														propertyKey={key}
														type={type}
													/>
												);
											}
										)
									}
								</ul>
							</li>);
						}
					)
				}
			</ul>
		);
	}
}

export default CriteriaSidebarCollapse;