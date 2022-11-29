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
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {openToast, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';

import {config} from '../../../app/config/index';
import {useSelector} from '../../../app/contexts/StoreContext';
import selectSegmentsExperienceId from '../../../app/selectors/selectSegmentsExperienceId';
import LayoutService from '../../../app/services/LayoutService';
import FormField from './FormField';

export default function ModalWrapper() {
	const isMounted = useIsMounted();

	const [openModal, setOpenModal] = useState(false);
	const onClose = useCallback(() => {
		if (isMounted()) {
			setOpenModal(false);
		}
	}, [isMounted]);

	const {observer} = useModal({
		onClose,
	});

	useEffect(() => {
		const handler = Liferay.on('convertToPageTemplate', () => {
			setOpenModal(true);
		});

		return () => {
			handler.detach();
		};
	}, []);

	if (!openModal) {
		return null;
	}

	return <ConvertToPageTemplateModal observer={observer} onClose={onClose} />;
}

const ConvertToPageTemplateModal = ({observer, onClose}) => {
	const [formErrors, setFormErrors] = useState({});
	const hasMultipleSegmentsExperienceIds = useSelector(
		(state) => Object.keys(state.availableSegmentsExperiences).length > 1
	);
	const [availableSets, setAvailableSets] = useState([]);
	const [loading, setLoading] = useState(false);
	const nameInputRef = useRef(null);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const templateSetSelectOptions = useMemo(
		() => [
			{label: `-- ${Liferay.Language.get('not-selected')} --`, value: ''},
			...availableSets.map((set) => ({label: set.name, value: set.id})),
		],
		[availableSets]
	);

	const [templateName, setTemplateName] = useState('');
	const [templateSet, setTemplateSet] = useState('');

	useEffect(() => {
		if (nameInputRef.current) {
			nameInputRef.current.focus();
		}
	}, []);

	useEffect(() => {
		LayoutService.getLayoutPageTemplateCollections()
			.then((sets) => {
				if (Array.isArray(sets)) {
					setAvailableSets(sets);
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
		const errors = {};

		if (!templateName) {
			errors.templateName = sub(
				Liferay.Language.get('x-field-is-required'),
				Liferay.Language.get('name')
			);
		}

		if (!templateSet) {
			errors.templateSet = sub(
				Liferay.Language.get('x-field-is-required'),
				Liferay.Language.get('page-template-set')
			);
		}

		return errors;
	}, [templateName, templateSet]);

	const handleSubmit = useCallback(
		(event) => {
			event.preventDefault();

			const errors = validateForm();

			if (Object.keys(errors).length) {
				setFormErrors(errors);

				return;
			}

			setLoading(true);

			LayoutService.createLayoutPageTemplateEntry(
				templateSet,
				templateName,
				segmentsExperienceId
			)
				.then((response) => {
					openToast({
						message: sub(
							Liferay.Language.get(
								'the-page-template-was-created-successfully.-you-can-view-it-here-x'
							),
							`<a href="${response.url}"><b>${templateName}</b></a>`
						),
						type: 'success',
					});

					onClose();
				})
				.catch((error) => {
					setLoading(false);

					if (typeof error === 'string') {
						setFormErrors((previousErrors) => ({
							...previousErrors,
							templateName: error,
						}));
					}
					else {
						openToast({
							message: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
							type: 'danger',
						});
					}
				});
		},
		[onClose, segmentsExperienceId, validateForm, templateName, templateSet]
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

				<ClayForm onSubmit={handleSubmit}>
					<FormField
						error={formErrors.templateName}
						id={`${config.portletNamespace}templateName`}
						name={Liferay.Language.get('name')}
					>
						<ClayInput
							id={`${config.portletNamespace}templateName`}
							name={`${config.portletNamespace}name`}
							onChange={(event) =>
								setTemplateName(event.target.value)
							}
							ref={nameInputRef}
							required
							value={templateName}
						/>
					</FormField>

					<FormField
						error={formErrors.templateSet}
						id={`${config.portletNamespace}templateSet`}
						name={Liferay.Language.get('page-template-set')}
					>
						<ClaySelectWithOption
							id={`${config.portletNamespace}templateSet`}
							onChange={(event) =>
								setTemplateSet(event.target.value)
							}
							options={templateSetSelectOptions}
							required
							value={templateSet}
						/>
					</FormField>
				</ClayForm>
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

ConvertToPageTemplateModal.propTypes = {
	observer: PropTypes.object.isRequired,
	onClose: PropTypes.func.isRequired,
};
