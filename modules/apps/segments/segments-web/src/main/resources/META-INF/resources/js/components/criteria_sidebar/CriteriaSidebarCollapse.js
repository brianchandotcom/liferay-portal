/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		<ClayPanel.Group>
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
					<ClayPanel
						collapsable={true}
						displayTitle={
							<div className="c-inner" tabIndex="-1">
								<ClayPanel.Title className="d-flex justify-content-between text-uppercase">
									{propertyGroup.name}

									{searchValue && (
										<ClayBadge
											displayType="secondary"
											label={filteredProperties.length}
										/>
									)}
								</ClayPanel.Title>
							</div>
						}
						displayType="unstyled"
						expanded={active}
						key={key}
						onExpandedChange={_handleClick(key, active)}
					>
						<ClayPanel.Body className="c-px-0">
							<p className="c-pt-1 c-px-4 text-secondary">
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
										({
											icon,
											label,
											name,
											options,
											type,
										}) => {
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
													defaultValue={defaultValue}
													icon={icon}
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
						</ClayPanel.Body>
					</ClayPanel>
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
