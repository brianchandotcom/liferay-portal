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

import ClayLabel from '@clayui/label';
import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const ManageLanguages = ({
	availableLocales,
	customDefaultLocaleId,
	customLocales
}) => {
	const Language = ({displayName, isDefault, localeId}) => {
		return (
			<ClayTable.Row>
				<ClayTable.Cell align="center">
					checkbox
				</ClayTable.Cell>

				<ClayTable.Cell expanded>
					{displayName}

					{isDefault && (
						<ClayLabel className="ml-3" displayType="info">
							{Liferay.Language.get('default')}
						</ClayLabel>
					)}
				</ClayTable.Cell>
			</ClayTable.Row>
		);
	};

	return(
		<>
			<h1> Manage languages </h1>

			<ClayTable borderless headVerticalAlignment="middle">
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell expanded headingCell headingTitle>
							{Liferay.Language.get('language')}
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{availableLocales.map(locale => {
						return (
							<Language
								{...locale}
								isDefault={customDefaultLocaleId === locale.localeId}
								key={locale.localeId}
							/>
						);
					})}
				</ClayTable.Body>
			</ClayTable>
		</>
	);
}

ManageLanguages.propTypes = {
	availableLocales: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			localeId: PropTypes.string
		})
	).isRequired,
	customDefaultLocaleId: PropTypes.string.isRequired,
	customLocales: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			localeId: PropTypes.string
		})
	).isRequired,
}

export default function(props) {
	return <ManageLanguages {...props} />;
}