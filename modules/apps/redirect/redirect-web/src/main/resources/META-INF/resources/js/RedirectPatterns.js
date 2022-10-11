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
	pattern = '',
	portletNamespace,
}) => {
	return (
		<ClayLayout.Row className="redirect-pattern-row">
			<ClayLayout.Col md="6">
				<label htmlFor="pattern">
					{Liferay.Language.get('pattern-field-label')}

					<span
						className="inline-item-after"
						title={Liferay.Language.get('pattern-help-message')}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</label>

				<ClayInput
					defaultValue={pattern}
					id="pattern"
					name={`${portletNamespace}pattern_${index}`}
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
	patterns: initialPatternsList,
	portletNamespace,
}) => {
	const emptyRow = () => ({destinationURL: '', id: uuidv4(), pattern: ''});

	const addRow = (index) => {
		const tempList = [...patterns];
		tempList.splice(index + 1, 0, emptyRow());
		setPatterns(tempList);
	};

	const removeRow = (index) => {
		const tempList = [...patterns];
		tempList.splice(index, 1);
		setPatterns(tempList);
	};

	const [patterns, setPatterns] = useState(
		initialPatternsList && !!initialPatternsList.length
			? initialPatternsList.map((item) => ({...item, id: uuidv4()}))
			: [emptyRow()]
	);

	return (
		<>
			<p className="text-muted">{description}</p>

			{patterns.map((item, index) => (
				<PatternField
					destinationURL={item.destinationURL}
					handleAddClick={addRow}
					handleRemoveClick={removeRow}
					index={index}
					key={item.id}
					pattern={item.pattern}
					portletNamespace={portletNamespace}
				/>
			))}
		</>
	);
};

RedirectPattern.propTypes = {
	description: PropTypes.string,
	patterns: PropTypes.arrayOf(
		PropTypes.shape({
			destinationURL: PropTypes.string,
			pattern: PropTypes.string,
		})
	),
	portletNamespace: PropTypes.string.isRequired,
};

export default RedirectPattern;
