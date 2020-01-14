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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

/**
 * @class Languages
 */

const Languages = ({defaultLanguage, languages}) => {
	const Language = ({id, label}) => {
		return (
			<ClayTable.Row>
				<ClayTable.Cell>
					{label}
					<span className="hide"> {id} </span>
				</ClayTable.Cell>
				<ClayTable.Cell>
					<ClayIcon symbol="ellipsis-v" />
				</ClayTable.Cell>
			</ClayTable.Row>
		);
	};

	return (
	<>
		<ClayTable borderless>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell expanded headingCell headingTitle>
						{Liferay.Language.get('language')}
					</ClayTable.Cell>
					<ClayTable.Cell>
						<ClayButton displayType="secondary">{Liferay.Language.get('add')}</ClayButton>
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{languages.map(language => {
					return <Language id={language.key} label={language.value} key={language.id} />;
				})}
			</ClayTable.Body>
		</ClayTable>
	</>
	);
}

Languages.propTypes = {
	defaultLanguage: PropTypes.string,
	languages: PropTypes.array
};

export default function(props) {
	console.log(props);
	return <Languages {...props} />;
}