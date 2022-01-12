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
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {config} from '../../../app/config/index';
import {useSelector} from '../../../app/contexts/StoreContext';
import selectSegmentsExperienceId from '../../../app/selectors/selectSegmentsExperienceId';
import LayoutService from '../../../app/services/LayoutService';
import FormField from './FormField';

const CreateLayoutPageTemplateEntryModal = ({observer, onClose}) => {
	const [error, setError] = useState(null);
	const hasMultipleSegmentsExperienceIds = useSelector(
		(state) => Object.keys(state.availableSegmentsExperiences).length > 1
	);
	const [
		layoutPageTemplateCollections,
		setLayoutPageTemplateCollections,
	] = useState([]);
	const [loading, setLoading] = useState(false);
	const layoutPageTemplateCollectionInputRef = useRef(null);
	const nameInputRef = useRef(null);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	useEffect(() => {
		if (nameInputRef.current) {
			nameInputRef.current.focus();
		}
	}, []);

	useEffect(() => {
		LayoutService.getLayoutPageTemplateCollections()
			.then((layoutPageTemplateCollections) => {
				if (Array.isArray(layoutPageTemplateCollections)) {
					setLayoutPageTemplateCollections(
						layoutPageTemplateCollections
					);
				}
				else {
					throw new Error();
				}
			})
			.catch((error) => {
				console.error(error);
			});
	}, []);

	const validateForm = useCallback(() => {
		const error = {};

		const errorMessage = Liferay.Language.get('this-field-is-required');

		if (!nameInputRef.current.value) {
			error.name = errorMessage;
		}

		if (layoutPageTemplateCollectionInputRef.current.selectedIndex === 0) {
			error.layoutPageTemplateCollectionId = errorMessage;
		}

		return error;
	}, []);

	const handleSubmit = useCallback(
		(event) => {
			event.preventDefault();

			const error = validateForm();

			if (Object.keys(error).length !== 0) {
				setError(error);

				return;
			}

			setLoading(true);

			LayoutService.createLayoutPageTemplateEntry(
				layoutPageTemplateCollectionInputRef.current.value,
				nameInputRef.current.value,
				segmentsExperienceId
			)
				.then((response) => {
					openToast({
						message: Liferay.Util.sub(
							Liferay.Language.get(
								'the-page-template-was-created-successfully.-you-can-view-it-here-x'
							),
							`<a href="${response.url}"><b>${nameInputRef.current.value}</b></a>`
						),
						type: 'success',
					});

					onClose();
				})
				.catch((error) => {
					setLoading(false);

					setError({
						other:
							typeof error === 'string'
								? error
								: Liferay.Language.get(
										'an-unexpected-error-occurred'
								  ),
					});
				});
		},
		[onClose, segmentsExperienceId, validateForm]
	);

	return (
		<ClayModal
			containerProps={{className: 'cadmin'}}
			observer={observer}
			size="md"
		>
			<ClayModal.Header>
				{Liferay.Language.get('create-page-template')}
			</ClayModal.Header>

			<ClayModal.Body>
				{error && error.other && (
					<ClayAlert
						displayType="danger"
						onClose={() => setError({...error, other: null})}
						title={Liferay.Language.get('error')}
					>
						{error.other}
					</ClayAlert>
				)}

				{hasMultipleSegmentsExperienceIds && (
					<div className="form-feedback-group mb-3">
						<div className="form-feedback-item text-info">
							<ClayIcon className="mr-2" symbol="info-circle" />

							<span>
								{Liferay.Language.get(
									'the-page-template-is-based-on-the-current-experience'
								)}
							</span>
						</div>
					</div>
				)}

				<form onSubmit={handleSubmit}>
					<FormField
						error={error && error.name}
						id={`${config.portletNamespace}name`}
						name={Liferay.Language.get('name')}
					>
						<input
							className="form-control"
							id={`${config.portletNamespace}name`}
							onChange={() => setError({...error, name: null})}
							ref={nameInputRef}
						/>
					</FormField>

					<fieldset>
						<FormField
							error={
								error && error.layoutPageTemplateCollectionId
							}
							id={`${config.portletNamespace}layoutPageTemplateCollectionId`}
							name={Liferay.Language.get(
								'page-template-collection'
							)}
						>
							<select
								className="form-control"
								id={`${config.portletNamespace}layoutPageTemplateCollectionId`}
								ref={layoutPageTemplateCollectionInputRef}
							>
								<option value="">
									{`-- ${Liferay.Language.get(
										'not-selected'
									)} --`}
								</option>

								{layoutPageTemplateCollections.map(
									(layoutPageTemplateCollection) => (
										<option
											key={
												layoutPageTemplateCollection.id
											}
											value={
												layoutPageTemplateCollection.id
											}
										>
											{layoutPageTemplateCollection.name}
										</option>
									)
								)}
							</select>
						</FormField>
					</fieldset>
				</form>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={handleSubmit}
						>
							{loading && (
								<span className="inline-item inline-item-before">
									<span
										aria-hidden="true"
										className="loading-animation"
									></span>
								</span>
							)}

							{Liferay.Language.get('create')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

CreateLayoutPageTemplateEntryModal.propTypes = {
	observer: PropTypes.object.isRequired,
	onClose: PropTypes.func.isRequired,
};

export {CreateLayoutPageTemplateEntryModal};
export default CreateLayoutPageTemplateEntryModal;
