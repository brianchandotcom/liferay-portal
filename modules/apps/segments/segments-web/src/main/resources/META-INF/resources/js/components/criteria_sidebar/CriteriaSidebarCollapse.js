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

import ClayBadge from '@clayui/badge';
import ClayPanel from '@clayui/panel';
import {parse} from 'date-fns';
import PropTypes from 'prop-types';
import React from 'react';

import {PROPERTY_TYPES} from '../../utils/constants';
import {propertyGroupShape} from '../../utils/types.es';
import {jsDatetoYYYYMMDD} from '../../utils/utils';
import CriteriaSidebarItem from './CriteriaSidebarItem';

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
	}
	else if (type === PROPERTY_TYPES.DATE) {
		defaultValue = jsDatetoYYYYMMDD(new Date());
	}
	else if (type === PROPERTY_TYPES.DATE_TIME) {
		const simpleDate = jsDatetoYYYYMMDD(new Date());

		defaultValue = parse(
			simpleDate,
			'yyyy-MM-dd',
			new Date()
		).toISOString();
	}
	else if (type === PROPERTY_TYPES.BOOLEAN) {
		defaultValue = 'true';
	}
	else if (type === PROPERTY_TYPES.INTEGER && options && options.length) {
		defaultValue = options[0].value;
	}
	else if (type === PROPERTY_TYPES.INTEGER) {
		defaultValue = 0;
	}
	else if (type === PROPERTY_TYPES.DOUBLE && options && options.length) {
		defaultValue = options[0].value;
	}
	else if (type === PROPERTY_TYPES.DOUBLE) {
		defaultValue = '0.00';
	}

	return defaultValue;
}

/**
 * Filters properties by label
 */
function filterProperties(properties, searchValue) {
	return properties.filter((property) => {
		const propertyLabel = property.label.toLowerCase();

		return propertyLabel.indexOf(searchValue.toLowerCase()) !== -1;
	});
}

const CriteriaSidebarCollapse = ({
	onCollapseClick,
	propertyGroups,
	propertyKey,
	searchValue,
}) => {
	const _handleClick = (key, editing) => () => onCollapseClick(key, editing);

	return (
		<ClayPanel.Group className="c-mt-1">
			{propertyGroups.map((propertyGroup) => {
				const key = propertyGroup.propertyKey;

				const active = key === propertyKey;
				const properties = propertyGroup
					? propertyGroup.properties
					: [];

				const filteredProperties = searchValue
					? filterProperties(properties, searchValue)
					: properties;

				return (
					<li
						className={classNames(
							`d-flex flex-column sidebar-collapse-item sidebar-collapse-${propertyGroup.propertyKey}`,
							{
								active,
							}
						)}
						key={key}
					>
						<a
							className="d-flex justify-content-between position-relative sidebar-collapse-header text-decoration-none text-uppercase"
							onClick={_handleClick(key, active)}
							tabIndex="0"
						>
							{propertyGroup.name}

							{searchValue && (
								<ClayBadge
									className="c-ml-auto c-mr-2"
									displayType="secondary"
									label={filteredProperties.length}
								/>
							)}

							<span>
								<ClayIcon
									className={classNames({
										active,
									})}
									symbol="angle-right"
								/>
							</span>
						</a>

						{active && (
							<div className="flex-grow-1 overflow-y-auto sidebar-collapse-body">
								<p className="c-pt-3 c-px-4 text-secondary">
									{Liferay.Language.get(
										'inherited-attributes-are-not-taken-into-account-to-include-members-in-segments'
									)}
								</p>

								<ul className="c-pl-0">
									{!filteredProperties.length && (
										<li className="align-items-center d-flex empty-message h-100 justify-content-center position-relative">
											{Liferay.Language.get(
												'no-results-were-found'
											)}
										</li>
									)}

									{!!filteredProperties.length &&
										filteredProperties.map(
											({label, name, options, type}) => {
												const defaultValue = getDefaultValue(
													{
														label,
														name,
														options,
														type,
													}
												);

												return (
													<CriteriaSidebarItem
														className={`color--${key}`}
														defaultValue={
															defaultValue
														}
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
							</div>
						)}
					</li>
				);
			})}
		</ClayPanel.Group>
	);
};

CriteriaSidebarCollapse.propTypes = {
	onCollapseClick: PropTypes.func,
	propertyGroups: PropTypes.arrayOf(propertyGroupShape),
	propertyKey: PropTypes.string,
	searchValue: PropTypes.string,
};

export default CriteriaSidebarCollapse;
