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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayCard from '@clayui/card';
import ClayForm, {ClayInput, ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import ClaySticker from '@clayui/sticker';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import {ConfigContext} from '../../app/config/index';
import Button from '../../common/components/Button';
import {openImageSelector} from '../../core/openImageSelector';

const CompositionModal = ({
	errorMessage,
	observer,
	onClose,
	onErrorDismiss
}) => {
	const {collections, imageSelectorURL, portletNamespace} = useContext(
		ConfigContext
	);

	const [fragmentCollectionId, setFragmentCollectionId] = useState(-1);

	const [thumbnail, setThumbnail] = useState({});

	const handleThumbnailSelected = image => {
		setThumbnail(image);
	};

	const thumbnailSelectorConfig = {
		imageSelectorURL,
		portletNamespace
	};

	const [loading] = useState(false);

	const nameInputId = portletNamespace + 'fragmentCompositionName';
	const descriptionInputId =
		portletNamespace + 'fragmentCompositionDescription';

	return (
		<ClayModal observer={observer} size="lg">
			<ClayModal.Header>
				{Liferay.Language.get('new-fragment')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm autoComplete="off" className="mb-3" noValidate>
					{errorMessage && (
						<ClayAlert
							displayType="danger"
							onClose={onErrorDismiss}
							title={errorMessage}
						/>
					)}
					<ClayForm.Group>
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
							placeholder={Liferay.Language.get('name')}
							required
							type="text"
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<ClayInput.Group>
							<ClayInput.GroupItem shrink>
								<ClayButton
									disabled={loading}
									displayType="secondary"
									onClick={() =>
										openImageSelector(
											thumbnailSelectorConfig,
											handleThumbnailSelected
										)
									}
									value={Liferay.Language.get(
										'upload-thumbnail'
									)}
								>
									<ClayIcon
										className="inline-item inline-item-after mr-2 reference-mark"
										focusable="false"
										role="presentation"
										symbol="upload"
									/>

									{Liferay.Language.get('upload-thumbnail')}
								</ClayButton>
							</ClayInput.GroupItem>
							<ClayInput.GroupItem className="align-items-center">
								<span className="ml-2">{thumbnail.title}</span>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayForm.Group>

					<ClayForm.Group>
						<label htmlFor={descriptionInputId}>
							{Liferay.Language.get('description')}
						</label>

						<ClayInput
							autoFocus
							component="textarea"
							id={descriptionInputId}
							placeholder={Liferay.Language.get('description')}
							type="text"
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<ClayInput.Group>
							<ClayInput.GroupItem shrink>
								<ClayCheckbox
									id={portletNamespace + 'saveInlineContent'}
									inline={true}
									label={Liferay.Language.get(
										'save-inline-content'
									)}
								/>
							</ClayInput.GroupItem>
							<ClayInput.GroupItem className="ml-4">
								<ClayCheckbox
									id={
										portletNamespace +
										'saveMappingConfiguration'
									}
									inline={true}
									label={Liferay.Language.get(
										'save-mapping-configuration'
									)}
								/>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayForm.Group>
					<ClayForm.Group>
						{collections.length > 0 ? (
							<>
								<h5 className="text-muted text-uppercase">
									{Liferay.Language.get('select-collection')}
								</h5>

								<div className="row">
									{collections.length > 0 &&
										collections.map(collection => (
											<div
												className="col-md-4"
												key={
													collection.fragmentCollectionId
												}
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
															<span className="autofit-col">
																<ClaySticker
																	inline
																>
																	<ClayIcon symbol="folder" />
																</ClaySticker>
															</span>
															<span className="autofit-col autofit-col-expand">
																<span className="autofit-section">
																	<ClayCard.Description
																		displayType="title"
																		truncate
																	>
																		{
																			collection.name
																		}
																	</ClayCard.Description>
																</span>
															</span>
														</ClayCard.Row>
													</ClayCard.Body>
												</ClayCard>
											</div>
										))}
								</div>
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
									'this-fragment-will-be-saved-in-a-new-collection-called-saved-fragments'
								)}
							</div>
						)}
					</ClayForm.Group>
				</ClayForm>
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={loading}
							displayType="secondary"
							onClick={onClose}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<Button
							disabled={loading}
							displayType="primary"
							loading={loading}
						>
							{Liferay.Language.get('save')}
						</Button>
					</ClayButton.Group>
				}
			></ClayModal.Footer>
		</ClayModal>
	);
};

CompositionModal.propTypes = {
	errorMessage: PropTypes.string,
	observer: PropTypes.object.isRequired,
	onClose: PropTypes.func.isRequired,
	onErrorDismiss: PropTypes.func.isRequired
};

export default CompositionModal;
