/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayCard from '@clayui/card';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import ClaySticker from '@clayui/sticker';
import {openModal} from 'frontend-js-components-web';
import React, {FormEvent, useState} from 'react';

import Button from '../../common/components/Button';
import InvisibleFieldset from '../../common/components/InvisibleFieldset';
import {SelectedImage, openImageSelector} from '../../common/openImageSelector';
import {State} from '../../types/State';
import {LayoutDataItem} from '../../types/layout_data/LayoutData';
import {FragmentSet} from '../actions/updateFragments';
import {config} from '../config/index';
import {Dispatch} from '../contexts/StoreContext';
import FragmentService from '../services/FragmentService';
import addFragmentComposition from '../thunks/addFragmentComposition';

export default async function openFragmentCompositionModal({
	dispatch,
	fragments,
	itemId,
	segmentsExperienceId,
}: {
	dispatch: Dispatch;
	fragments: FragmentSet[];
	itemId: LayoutDataItem['itemId'];
	segmentsExperienceId: State['segmentsExperienceId'];
}) {
	const {valid} = await FragmentService.validateFragmentComposition({
		itemId,
		segmentsExperienceId,
	});

	if (!valid) {
		openModal({
			bodyHTML: Liferay.Language.get(
				'the-composition-cannot-be-created-because-some-fragment-references-are-missing-reimport-the-fragments-and-try-again'
			),
			buttons: [
				{
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					type: 'cancel',
				},
				{
					displayType: 'danger',
					label: Liferay.Language.get('done'),
					type: 'cancel',
				},
			],
			center: true,
			status: 'danger',
			title: Liferay.Language.get('save-composition-not-allowed'),
		});

		return;
	}

	openModal({
		contentComponent: ({closeModal}: {closeModal: () => void}) =>
			SaveFragmentCompositionModal({
				closeModal,
				dispatch,
				fragments,
				itemId,
			}),
		size: 'lg',
	});
}

function SaveFragmentCompositionModal({
	closeModal,
	dispatch,
	fragments,
	itemId,
}: {
	closeModal: () => void;
	dispatch: Dispatch;
	fragments: FragmentSet[];
	itemId: LayoutDataItem['itemId'];
}) {
	const [name, setName] = useState<string | undefined>(undefined);
	const [loading, setLoading] = useState(false);
	const [description, setDescription] = useState('');
	const [saveInlineContent, setSaveInlineContent] = useState(false);

	const [fragmentCollectionId, setFragmentCollectionId] = useState(
		fragments.length ? fragments[0].fragmentCollectionId : ''
	);

	const [saveMappingConfiguration, setSaveMappingConfiguration] =
		useState(false);

	const [thumbnail, setThumbnail] = useState<SelectedImage | null>(null);

	const handleSubmit = (event: FormEvent) => {
		event.preventDefault();

		if (!name) {
			setName('');
		}
		else {
			setLoading(true);

			dispatch(
				addFragmentComposition({
					description,
					fileEntryId: thumbnail?.fileEntryId,
					fragmentCollectionId,
					itemId,
					name,
					saveInlineContent,
					saveMappingConfiguration,
				})
			)
				.then(() => {
					closeModal();
				})
				.catch(() => {
					setLoading(false);
				});
		}
	};

	const handleThumbnailSelected = (image: SelectedImage) => {
		setThumbnail(image);
	};

	const nameInputId = `${config.portletNamespace}fragmentCompositionName`;
	const descriptionInputId = `${config.portletNamespace}fragmentCompositionDescription`;

	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{Liferay.Language.get('save-as-fragment')}
			</ClayModal.Header>

			<ClayModal.Body scrollable>
				<ClayForm className="mb-3" onSubmit={handleSubmit}>
					<InvisibleFieldset disabled={loading}>
						<ClayForm.Group
							className={name === '' ? 'has-error mb-3' : 'mb-3'}
						>
							<label htmlFor={nameInputId}>
								{Liferay.Language.get('name')}

								<ClayIcon
									className="ml-1 reference-mark"
									focusable="false"
									role="presentation"
									symbol="asterisk"
								/>
							</label>

							<ClayInput
								autoFocus
								id={nameInputId}
								maxLength={
									config.fragmentCompositionNameMaxLength
								}
								onChange={(event) =>
									setName(event.target.value)
								}
								onClick={(event) => {
									if (Liferay.Browser.isFirefox()) {
										(
											event.target as HTMLButtonElement
										).focus();
									}
								}}
								placeholder={Liferay.Language.get('name')}
								required
								type="text"
								value={name || ''}
							/>

							{name === '' && (
								<ClayForm.FeedbackGroup>
									<ClayForm.FeedbackItem>
										<ClayForm.FeedbackIndicator symbol="exclamation-full" />

										{Liferay.Language.get(
											'this-field-is-required'
										)}
									</ClayForm.FeedbackItem>
								</ClayForm.FeedbackGroup>
							)}
						</ClayForm.Group>

						<ClayForm.Group>
							<ClayInput.Group>
								<ClayInput.GroupItem shrink>
									<ClayButton
										displayType="secondary"
										onClick={() =>
											openImageSelector(
												handleThumbnailSelected
											)
										}
										size="sm"
										value={Liferay.Language.get(
											'upload-thumbnail'
										)}
									>
										<ClayIcon
											className="mr-2"
											focusable="false"
											role="presentation"
											symbol="upload"
										/>

										{Liferay.Language.get(
											'upload-thumbnail'
										)}
									</ClayButton>
								</ClayInput.GroupItem>

								<ClayInput.GroupItem className="align-items-center">
									<span className="ml-2 text-truncate">
										{thumbnail?.title}
									</span>
								</ClayInput.GroupItem>
							</ClayInput.Group>
						</ClayForm.Group>

						<ClayForm.Group>
							<label htmlFor={descriptionInputId}>
								{Liferay.Language.get('description')}
							</label>

							<ClayInput
								component="textarea"
								maxLength={
									config.fragmentCompositionDescriptionMaxLength
								}
								onChange={(event) =>
									setDescription(event.target.value)
								}
								placeholder={Liferay.Language.get(
									'description'
								)}
								type="text"
								value={description}
							/>
						</ClayForm.Group>

						<ClayForm.Group>
							<ClayInput.Group className="input-group-stacked-sm-down">
								<ClayInput.GroupItem className="mr-4" shrink>
									<ClayCheckbox
										checked={saveInlineContent}
										id={`${config.portletNamespace}saveInlineContent`}
										label={Liferay.Language.get(
											'save-inline-content'
										)}
										onChange={(event) =>
											setSaveInlineContent(
												event.target.checked
											)
										}
									/>
								</ClayInput.GroupItem>

								<ClayInput.GroupItem>
									<ClayCheckbox
										checked={saveMappingConfiguration}
										id={`${config.portletNamespace}saveMappingConfiguration`}
										label={Liferay.Language.get(
											'save-mapping-configuration-and-link'
										)}
										onChange={(event) =>
											setSaveMappingConfiguration(
												event.target.checked
											)
										}
									/>
								</ClayInput.GroupItem>
							</ClayInput.Group>
						</ClayForm.Group>

						<ClayForm.Group>
							{fragments.length ? (
								<>
									<p className="sheet-tertiary-title">
										{Liferay.Language.get(
											'select-fragment-set'
										)}
									</p>

									<ClayLayout.Row>
										{fragments.map((collection) => (
											<ClayLayout.Col
												key={
													collection.fragmentCollectionId
												}
												size={4}
											>
												<ClayCard
													className={
														fragmentCollectionId ===
														collection.fragmentCollectionId
															? 'active'
															: ''
													}
													horizontal
													interactive
													onClick={() =>
														setFragmentCollectionId(
															collection.fragmentCollectionId
														)
													}
												>
													<ClayCard.Body>
														<ClayCard.Row>
															<ClayLayout.ContentCol containerElement="span">
																<ClaySticker
																	inline
																>
																	<ClayIcon symbol="folder" />
																</ClaySticker>
															</ClayLayout.ContentCol>

															<ClayLayout.ContentCol
																containerElement="span"
																expand
															>
																<ClayLayout.ContentSection containerElement="span">
																	<ClayCard.Description
																		displayType="title"
																		truncate
																	>
																		{
																			collection.name
																		}
																	</ClayCard.Description>
																</ClayLayout.ContentSection>
															</ClayLayout.ContentCol>
														</ClayCard.Row>
													</ClayCard.Body>
												</ClayCard>
											</ClayLayout.Col>
										))}
									</ClayLayout.Row>
								</>
							) : (
								<div className="alert alert-info">
									<ClayIcon
										className="inline-item inline-item-after mr-2 reference-mark"
										focusable="false"
										role="presentation"
										symbol="exclamation-full"
									/>

									{Liferay.Language.get(
										'this-fragment-will-be-saved-in-a-new-fragment-set-called-saved-fragments'
									)}
								</div>
							)}
						</ClayForm.Group>
					</InvisibleFieldset>
				</ClayForm>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={loading}
							displayType="secondary"
							onClick={closeModal}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<Button
							disabled={loading}
							displayType="primary"
							loading={loading}
							onClick={handleSubmit}
						>
							{Liferay.Language.get('save')}
						</Button>
					</ClayButton.Group>
				}
			></ClayModal.Footer>
		</>
	);
}
