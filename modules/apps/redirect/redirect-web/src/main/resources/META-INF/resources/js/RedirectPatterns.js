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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';
import uuidv4 from 'uuid/v4';

import '../css/redirect_pattern.scss';

const REGEX_URL_ALLOW_RELATIVE = /((([A-Za-z]{3,9}:(?:\/\/)?)|\/(?:[-;:&=+$,\w]+@)?[A-Za-z0-9.-]+|(https?:\/\/|www.|[-;:&=+$,\w]+@)[A-Za-z0-9.-]+)((?:\/[+~%/.\w-_]*)?\??(?:[-+=&;%@.\w_]*)#?(?:[\w]*))((.*):(\d*)\/?(.*))?)/;

const PatternField = ({
	destinationURL: initialDestinationUrl,
	handleAddClick,
	handleRemoveClick,
	handleUrlErrorChange,
	index,
	pattern = '',
	portletNamespace,
}) => {
	const [destinationUrl, setDestinationUrl] = useState(initialDestinationUrl);
	const [urlError, setUrlError] = useState(false);

	const urlAllowRelative = (url) => {
		return REGEX_URL_ALLOW_RELATIVE && REGEX_URL_ALLOW_RELATIVE.test(url);
	};

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

			<ClayLayout.Col
				className={destinationUrl && urlError ? 'has-error' : ''}
				md="6"
			>
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
					id="destinationURL"
					name={`${portletNamespace}destinationURL_${index}`}
					onBlur={({currentTarget}) => {
						const error = !urlAllowRelative(currentTarget.value);
						setUrlError(error);
						handleUrlErrorChange(destinationUrl && error, index);
					}}
					onChange={({currentTarget}) =>
						setDestinationUrl(currentTarget.value)
					}
					type="text"
					value={destinationUrl}
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

				{destinationUrl && urlError && (
					<ClayForm.FeedbackGroup>
						<ClayForm.FeedbackItem>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{Liferay.Language.get('please-enter-a-valid-url')}
						</ClayForm.FeedbackItem>

						<small>{Liferay.Language.get('destination-url-error-help-message')}</small>
					</ClayForm.FeedbackGroup>
				)}
			</ClayLayout.Col>
		</ClayLayout.Row>
	);
};

const RedirectPattern = ({
	actionUrl,
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

		if (invalidPatternsSet.has(index)) {
			const newInvalidPatternSet = new Set(invalidPatternsSet);
			newInvalidPatternSet.delete(index);
			setInvalidPatternsSet(newInvalidPatternSet);
		}
	};

	const [patterns, setPatterns] = useState(
		initialPatternsList && !!initialPatternsList.length
			? initialPatternsList.map((item) => ({...item, id: uuidv4()}))
			: [emptyRow()]
	);

	const [invalidPatternsSet, setInvalidPatternsSet] = useState(new Set());

	const handleUrlError = (error, index) => {
		const newInvalidPatternSet = new Set(invalidPatternsSet);

		if (error) {
			newInvalidPatternSet.add(index);
		}
		else {
			newInvalidPatternSet.delete(index);
		}

		setInvalidPatternsSet(newInvalidPatternSet);
	};

	return (
		<form
			action={actionUrl}
			className="container-fluid container-fluid-max-xl mt-4"
			method="post"
			name={`${portletNamespace}fm`}
		>
			<div className="sheet sheet-lg">
				<div className="sheet-header">
					<h2>
						{Liferay.Language.get(
							'redirect-pattern-configuration-name'
						)}
					</h2>
				</div>

				<div className="sheet-section">
					<p className="text-muted">{description}</p>

					{patterns.map((item, index) => (
						<PatternField
							destinationURL={item.destinationURL}
							handleAddClick={addRow}
							handleRemoveClick={removeRow}
							handleUrlErrorChange={handleUrlError}
							index={index}
							key={item.id}
							pattern={item.pattern}
							portletNamespace={portletNamespace}
						/>
					))}
				</div>

				<div className="sheet-footer">
					<ClayButton
						disabled={invalidPatternsSet.size > 0}
						type="submit"
					>
						{Liferay.Language.get('save')}
					</ClayButton>
				</div>
			</div>
		</form>
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
