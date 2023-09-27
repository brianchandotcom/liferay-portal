/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayPanel from '@clayui/panel';
import React, {useContext, useEffect, useState} from 'react';

import {ModalContext} from '../../../ModalProvider.es';

function Card({cardIndex, nextTransitions = [], tasks}) {
	const {
		bulkTransition: {transition, transitionTasks},
		setBulkTransition,
	} = useContext(ModalContext);
	const [comment, setComment] = useState('');
	const [hasError, setHasError] = useState(true);
	const [selectedTransition, setSelectedTransition] = useState('');
	const [showAll, setShowAll] = useState(false);

	const {errors, onGoing} = transition;

	const handleChange = ({target}) => {
		const showError = target.value === '';
		const cardError = errors;

		cardError[cardIndex] = showError;

		setBulkTransition({
			transition: {
				errors,
				onGoing,
			},
			transitionTasks,
		});
		setHasError(showError);
		setSelectedTransition(target.value);
	};

	useEffect(() => {
		const cardError = errors;

		cardError[cardIndex] = hasError;

		setBulkTransition({
			transition: {
				...transition,
				errors: cardError,
				onGoing,
			},
			transitionTasks: [
				...transitionTasks.filter(
					({workflowTaskId}) =>
						!tasks.some(({id}) => id === workflowTaskId)
				),
				...tasks.map(({id}) => ({
					comment,
					transitionName: selectedTransition,
					workflowTaskId: id,
				})),
			],
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [comment, selectedTransition, tasks]);

	return (
		<>
			<ClayPanel.Body className="transition-panel-body">
				<div className="table-container">
					<div
						className={`table-visualization ${
							showAll ? 'table-visualization-show-all' : ''
						}`}
					>
						<ClayList className={showAll ? 'table-show-all' : ''}>
							<ClayList.Item
								flex
								style={{backgroundColor: '#F7F8F9'}}
							>
								<ClayList.ItemField style={{width: '20%'}}>
									<ClayList.ItemTitle>
										{Liferay.Language.get('id')}
									</ClayList.ItemTitle>
								</ClayList.ItemField>

								<ClayList.ItemField expand>
									<ClayList.ItemTitle>
										{Liferay.Language.get('item-subject')}
									</ClayList.ItemTitle>
								</ClayList.ItemField>
							</ClayList.Item>

							{tasks.map(
								({assetTitle, assetType, id, instanceId}) => (
									<ClayList.Item
										className="list-item-no-border"
										flex
										key={id}
									>
										<ClayList.ItemField
											style={{width: '20%'}}
										>
											<ClayList.ItemTitle>
												{instanceId}
											</ClayList.ItemTitle>
										</ClayList.ItemField>

										<ClayList.ItemField expand>
											<ClayList.ItemText>
												{`${assetType}: ${assetTitle}`}
											</ClayList.ItemText>
										</ClayList.ItemField>
									</ClayList.Item>
								)
							)}
						</ClayList>
					</div>

					{tasks.length > 10 && (
						<span
							className="mt-4 show-all-button"
							onClick={() => {
								setShowAll(!showAll);
							}}
						>
							{!showAll
								? Liferay.Language.get('show-all')
								: Liferay.Language.get('show-less')}
						</span>
					)}
				</div>

				<div
					className={`${
						errors[cardIndex] && onGoing ? 'has-error' : ''
					} ml-3`}
				>
					<label
						className="control-label"
						htmlFor={`transitionSelect${cardIndex}`}
					>
						{Liferay.Language.get('transition-to') + ' '}

						<ClayIcon
							className="reference-mark text-warning"
							symbol="asterisk"
						/>
					</label>

					<ClaySelect
						aria-label="Select Label"
						className="error-field"
						id={`transitionSelect${cardIndex}`}
						onChange={handleChange}
					>
						<ClaySelect.Option label="" value="" />

						{nextTransitions.map((nextTransition) => (
							<ClaySelect.Option
								key={nextTransition.name}
								label={nextTransition.label}
								value={nextTransition.name}
							/>
						))}
					</ClaySelect>

					{errors[cardIndex] && onGoing && (
						<label className="help-block">
							<ClayIcon
								className="mr-1"
								symbol="exclamation-full"
							/>

							{Liferay.Language.get('select-a-transition')}
						</label>
					)}
				</div>
			</ClayPanel.Body>

			<Card.Footer comment={comment} setComment={setComment} />
		</>
	);
}

function Footer({comment, setComment}) {
	const [addComment, setAddComment] = useState(false);

	return (
		<ClayPanel.Footer className="pb-3">
			{!addComment ? (
				<ClayButton
					displayType="secondary"
					onClick={() => setAddComment(true)}
				>
					<ClayIcon className="mr-3" symbol="message" />

					{Liferay.Language.get('add-comment')}
				</ClayButton>
			) : (
				<>
					<label htmlFor="commentTextArea">
						{`${Liferay.Language.get(
							'comment'
						)} (${Liferay.Language.get('optional')})`}
					</label>

					<ClayInput
						component="textarea"
						id="commentTextArea"
						onChange={({target}) => setComment(target.value)}
						placeholder={Liferay.Language.get('comment')}
						type="text"
						value={comment}
					/>
				</>
			)}
		</ClayPanel.Footer>
	);
}

Card.Footer = Footer;

export default Card;
