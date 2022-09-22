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
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import PropTypes from 'prop-types';
import React, {useState} from 'react';
import uuidv4 from 'uuid/v4';

import '../css/redirect_pattern.scss';

const PatternField = ({
	destinationURL = '',
	handleAddClick,
	handleRemoveClick,
	index,
	portletNamespace,
	source = '',
}) => {
	return (
		<ClayLayout.Row className="redirect-pattern-row">
			<ClayLayout.Col md="6">
				<label htmlFor="source">
					{Liferay.Language.get('mime-type-field-label')}

					<span
						className="inline-item-after"
						title={Liferay.Language.get('mime-type-help-message')}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</label>

				<ClayInput
					defaultValue={source}
					id="source"
					name={`${portletNamespace}source_${index}`}
					type="text"
				/>
			</ClayLayout.Col>

			<ClayLayout.Col md="6">
				<label htmlFor="destinationURL">
					{Liferay.Language.get('destination-url')}

					<span
						className="inline-item-after"
						title={Liferay.Language.get(
							'destination-url-help-message'
						)}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</label>

				<ClayInput
					defaultValue={destinationURL}
					id="destinationURL"
					name={`${portletNamespace}destinationURL_${index}`}
					type="text"
				/>

				{index > 0 && (
					<ClayButton
						aria-label={Liferay.Language.get('remove')}
						className="redirect-field-repeatable-delete-button"
						onClick={() => handleRemoveClick(index)}
						size="sm"
						title={Liferay.Language.get('remove')}
						type="button"
					>
						<ClayIcon symbol="hr" />
					</ClayButton>
				)}

				<ClayButton
					className="redirect-field-repeatable-add-button"
					onClick={() => handleAddClick(index)}
					size="sm"
					title={Liferay.Language.get('add')}
					type="button"
				>
					<ClayIcon symbol="plus" />
				</ClayButton>
			</ClayLayout.Col>
		</ClayLayout.Row>
	);
};

const RedirectPattern = ({
	description = Liferay.Language.get('redirect-patterns-description'),
	patternList: initialPatternList,
	portletNamespace,
}) => {
	const emptyRow = () => ({destinationURL: '', id: uuidv4(), source: ''});

	const addRow = (index) => {
		const tempList = [...patternsList];
		tempList.splice(index + 1, 0, emptyRow());
		setPatternsList(tempList);
	};

	const removeRow = (index) => {
		const tempList = [...patternsList];
		tempList.splice(index, 1);
		setPatternsList(tempList);
	};

	const [patternsList, setPatternsList] = useState(
		initialPatternList && !!initialPatternList.length
			? initialPatternList
			: [emptyRow()]
	);

	return (
		<>
			<p className="text-muted">{description}</p>

			{patternsList.map((item, index) => (
				<PatternField
					destinationURL={item.destinationURL}
					handleAddClick={addRow}
					handleRemoveClick={removeRow}
					index={index}
					key={item.id}
					portletNamespace={portletNamespace}
					source={item.source}
				/>
			))}
		</>
	);
};

RedirectPattern.propTypes = {
	description: PropTypes.string,
	patternList: PropTypes.arrayOf(
		PropTypes.shape({
			destinationURL: PropTypes.string,
			source: PropTypes.string,
		})
	),
	portletNamespace: PropTypes.string.isRequired,
};

export default RedirectPattern;
