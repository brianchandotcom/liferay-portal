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

import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import dateFns from 'date-fns';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

import {PROPERTY_TYPES} from '../../utils/constants.es';
import {propertyGroupShape} from '../../utils/types.es';
import {jsDatetoYYYYMMDD} from '../../utils/utils.es';
import CriteriaSidebarItem from './CriteriaSidebarItem.es';

const INPUT_DATE_FORMAT = 'YYYY-MM-DD';

/**
 * Returns a default value for a property provided.
 * @param {Object} property
 * @returns {string}
 */
function getDefaultValue(property) {
	const {options, type} = property;

	let defaultValue = '';

	if (type === PROPERTY_TYPES.STRING && options && options.length) {
		defaultValue = options[0].value;
	} else if (type === PROPERTY_TYPES.DATE) {
		defaultValue = jsDatetoYYYYMMDD(new Date());
	} else if (type === PROPERTY_TYPES.DATE_TIME) {
		const simpleDate = jsDatetoYYYYMMDD(new Date());

		defaultValue = dateFns
			.parse(simpleDate, INPUT_DATE_FORMAT)
			.toISOString();
	} else if (type === PROPERTY_TYPES.BOOLEAN) {
		defaultValue = 'true';
	} else if (type === PROPERTY_TYPES.INTEGER) {
		defaultValue = 0;
	} else if (type === PROPERTY_TYPES.DOUBLE) {
		defaultValue = '0.00';
	}

	return defaultValue;
}

class CriteriaSidebarCollapse extends Component {
	static propTypes = {
		onCollapseClick: PropTypes.func,
		propertyGroups: PropTypes.arrayOf(propertyGroupShape),
		propertyKey: PropTypes.string,
		searchValue: PropTypes.string
	};

	_filterProperties = searchValue => {
		const propertyGroup = this.props.propertyGroups.find(
			propertyGroup =>
				this.props.propertyKey === propertyGroup.propertyKey
		);

		const properties = propertyGroup ? propertyGroup.properties : [];

		return properties.filter(property => {
			const propertyLabel = property.label.toLowerCase();

			return propertyLabel.indexOf(searchValue.toLowerCase()) !== -1;
		});
	};

	_handleClick = (key, editing) => () => {
		this.props.onCollapseClick(key, editing);
	};

	render() {
		const {propertyGroups, propertyKey, searchValue} = this.props;

		const propertyGroup = propertyGroups.find(
			propertyGroup => propertyKey === propertyGroup.propertyKey
		);

		const properties = propertyGroup ? propertyGroup.properties : [];

		const filteredProperties = searchValue
			? this._filterProperties(searchValue)
			: properties;

		return (
			<ul className="list-unstyled sidebar-collapse-groups">
				{propertyGroups.map(propertyGroup => {
					const active = propertyGroup.propertyKey === propertyKey;

					const activeClasses = getCN({
						active
					});

					const propertyListClasses = getCN(
						'properties-list',
						activeClasses
					);

					const key = propertyGroup.propertyKey;

					return (
						<li
							className={`sidebar-collapse-${propertyGroup.propertyKey}`}
							key={key}
						>
							<div
								className="sidebar-collapse-header-root"
								onClick={this._handleClick(key, active)}
							>
								<a className="d-flex justify-content-between sidebar-collapse-header">
									{propertyGroup.name}
									<span className="collapse-icon">
										<ClayIcon
											className={activeClasses}
											symbol="angle-right"
										/>
									</span>
								</a>
							</div>
							<ul className={propertyListClasses}>
								{active && filteredProperties.length === 0 && (
									<li className="empty-message">
										{Liferay.Language.get(
											'no-results-were-found'
										)}
									</li>
								)}

								{active &&
									filteredProperties.length > 0 &&
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
									)}
							</ul>
						</li>
					);
				})}
			</ul>
		);
	}
}

export default CriteriaSidebarCollapse;
