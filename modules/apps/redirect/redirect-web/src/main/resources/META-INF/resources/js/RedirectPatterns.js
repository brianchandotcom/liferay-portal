/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';
import {v4 as uuidv4} from 'uuid';

import '../css/redirect_pattern.scss';

const REGEX_URL_ALLOW_RELATIVE = /((([A-Za-z]{3,9}:(?:\/\/)?)|\/(?:[-;:&=+$,\w]+@)?[A-Za-z0-9.-]+|(https?:\/\/|www.|[-;:&=+$,\w]+@)[A-Za-z0-9.-]+)((?:\/[+~%/.\w-_]*)?\??(?:[-+=&;%@.\w_]*)#?(?:[\w]*))((.*):(\d*)\/?(.*))?)/;

const urlAllowRelative = (url) => REGEX_URL_ALLOW_RELATIVE.test(url);

const PatternField = ({
	destinationURL: initialDestinationUrl,
	error,
	handleAddClick,
	handlePatternError,
	handleRemoveClick,
	index,
	pattern = '',
	portletNamespace,
	strings,
	userAgent: initialUserAgent,
	userAgents,
}) => {
	const [destinationUrl, setDestinationUrl] = useState(initialDestinationUrl);
	const [userAgent, setUserAgent] = useState(initialUserAgent);

	return (
		<div className="redirect-pattern-group">
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
				aria-label={Liferay.Language.get('add')}
				className="redirect-field-repeatable-add-button"
				onClick={() => handleAddClick(index)}
				size="sm"
				title={Liferay.Language.get('add')}
				type="button"
			>
				<ClayIcon symbol="plus" />
			</ClayButton>

			<ClayLayout.Row>
				<ClayLayout.Col md="12">
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
			</ClayLayout.Row>

			<ClayLayout.Row className="mt-4">
				<ClayLayout.Col className={error ? 'has-error' : ''} md="8">
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
						className="destination-url-input"
						id="destinationURL"
						name={`${portletNamespace}destinationURL_${index}`}
						onBlur={({currentTarget}) => {
							const error = Boolean(
								destinationUrl &&
									!urlAllowRelative(currentTarget.value)
							);

							handlePatternError(error, index);
						}}
						onChange={({currentTarget}) =>
							setDestinationUrl(currentTarget.value)
						}
						type="text"
						value={destinationUrl}
					/>

					{error && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{Liferay.Language.get(
									'please-enter-a-valid-url'
								)}
							</ClayForm.FeedbackItem>

							<small>
								{sub(
									Liferay.Language.get(
										'destination-url-error-help-message'
									),
									strings.absoluteURL,
									strings.relativeURL
								)}
							</small>
						</ClayForm.FeedbackGroup>
					)}
				</ClayLayout.Col>

				<ClayLayout.Col md="4">
					<label htmlFor="userAgent">
						{Liferay.Language.get('user-agent')}
					</label>

					<ClaySelect
						aria-label={Liferay.Language.get('select-user-agent')}
						id="userAgent"
						name={`${portletNamespace}userAgent_${index}`}
						onChange={({currentTarget}) =>
							setUserAgent(currentTarget.value)
						}
						value={userAgent}
					>
						{userAgents.map((item) => (
							<ClaySelect.Option
								key={item.value}
								label={item.label}
								value={item.value}
							/>
						))}
					</ClaySelect>
				</ClayLayout.Col>
			</ClayLayout.Row>
		</div>
	);
};

const RedirectPattern = ({
	actionUrl,
	description = Liferay.Language.get('redirect-patterns-description'),
	patterns: initialPatternsList,
	portletNamespace,
	strings,
	userAgents,
}) => {
	const emptyRow = () => ({
		destinationURL: '',
		error: false,
		id: uuidv4(),
		pattern: '',
	});

	const [patterns, setPatterns] = useState(
		initialPatternsList && !!initialPatternsList.length
			? initialPatternsList.map((item) => ({
					...item,
					error: false,
					id: uuidv4(),
			  }))
			: [emptyRow()]
	);

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

	const handleUrlError = (error, index) => {
		const tempList = [...patterns];
		tempList[index] = {...tempList[index], error};
		setPatterns(tempList);
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
							error={item.error}
							handleAddClick={addRow}
							handlePatternError={handleUrlError}
							handleRemoveClick={removeRow}
							index={index}
							key={item.id}
							pattern={item.pattern}
							portletNamespace={portletNamespace}
							strings={strings}
							userAgent={item.userAgent}
							userAgents={userAgents}
						/>
					))}
				</div>

				<div className="sheet-footer">
					<ClayButton
						disabled={patterns.some((pattern) => pattern.error)}
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
			userAgent: PropTypes.string,
		})
	),
	portletNamespace: PropTypes.string.isRequired,
	strings: PropTypes.shape({
		absoluteURL: PropTypes.string,
		relativeURL: PropTypes.string,
	}),
	userAgents: PropTypes.array.isRequired,
};

export default RedirectPattern;
