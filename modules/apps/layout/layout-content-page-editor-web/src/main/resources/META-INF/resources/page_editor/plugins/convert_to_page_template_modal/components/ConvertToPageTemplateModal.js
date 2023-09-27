/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {openToast, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';
import {flushSync} from 'react-dom';

import {config} from '../../../app/config/index';
import {useSelector} from '../../../app/contexts/StoreContext';
import selectSegmentsExperienceId from '../../../app/selectors/selectSegmentsExperienceId';
import LayoutService from '../../../app/services/LayoutService';
import getUniqueName from '../../../app/utils/getUniqueName';
import FormField from './FormField';

export default function ConvertToPageTemplateModal() {
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

	return <PageTemplateModal observer={observer} onClose={onClose} />;
}

export function PageTemplateModal({observer, onClose}) {
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const hasMultipleSegmentsExperienceIds = useSelector(
		(state) => Object.keys(state.availableSegmentsExperiences).length > 1
	);

	const [availableSets, setAvailableSets] = useState([]);
	const [formErrors, setFormErrors] = useState({});
	const [loading, setLoading] = useState(false);
	const [openAddTemplateSetModal, setOpenAddTemplateSetModal] = useState(
		false
	);
	const [templateSetDescription, setTemplateSetDescription] = useState('');
	const [templateSetId, setTemplateSetId] = useState('');
	const [templateSetName, setTemplateSetName] = useState(
		Liferay.Language.get('untitled-set')
	);

	const nameInputRef = useRef(null);

	const templateSetSelectOptions = useMemo(
		() => [
			{label: `-- ${Liferay.Language.get('not-selected')} --`, value: ''},
			...availableSets.map((set) => ({label: set.name, value: set.id})),
		],
		[availableSets]
	);

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
					setOpenAddTemplateSetModal(!sets.length);
					setTemplateSetName(
						getUniqueName(
							sets,
							Liferay.Language.get('untitled-set')
						)
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
		const errors = {};

		if (openAddTemplateSetModal) {
			if (!templateSetName) {
				errors.templateSetName = sub(
					Liferay.Language.get('x-field-is-required'),
					Liferay.Language.get('name')
				);
			}
		}
		else {
			if (!templateSetId) {
				errors.templateSetId = sub(
					Liferay.Language.get('x-field-is-required'),
					Liferay.Language.get('page-template-set')
				);
			}
		}

		return errors;
	}, [templateSetId, templateSetName, openAddTemplateSetModal]);

	// We are using flush here because this way we can clear errors inmediately
	// in handleSubmit. Otherwise, React will batch setStates and will do only
	// one update.

	const resetErrors = useCallback(
		() =>
			flushSync(() => {
				setFormErrors({});
			}),
		[]
	);

	const handleSubmit = useCallback(
		(event) => {
			event.preventDefault();

			const errors = validateForm();

			resetErrors();

			if (Object.keys(errors).length) {
				setFormErrors(errors);

				return;
			}

			setLoading(true);

			LayoutService.createLayoutPageTemplateEntry({
				segmentsExperienceId,
				templateSetDescription,
				templateSetId: templateSetId || null,
				templateSetName,
			})
				.then((response) => {
					openToast({
						message: sub(
							Liferay.Language.get(
								'the-page-template-was-created-successfully.-you-can-view-it-here-x'
							),
							`<a href="${response.url}">${Liferay.Language.get(
								'see-in-page-templates'
							)}</a>`
						),
						type: 'success',
					});

					onClose();
				})
				.catch(() => {
					setLoading(false);

					openToast({
						message: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
						type: 'danger',
					});
				});
		},
		[
			onClose,
			resetErrors,
			segmentsExperienceId,
			templateSetDescription,
			templateSetId,
			templateSetName,
			validateForm,
		]
	);

	return (
		<ClayModal
			containerProps={{className: 'cadmin'}}
			observer={observer}
			size="md"
		>
			<ClayModal.Header>
				{openAddTemplateSetModal
					? Liferay.Language.get('add-page-template-set')
					: Liferay.Language.get('select-page-template-set')}
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
					{openAddTemplateSetModal ? (
						<>
							{!availableSets.length ? (
								<div className="mb-3 text-secondary">
									{Liferay.Language.get(
										'a-page-template-set-must-first-be-created-before-you-can-create-your-page-template'
									)}
								</div>
							) : null}
							<FormField
								error={formErrors.templateSetName}
								id={`${config.portletNamespace}templateSetName`}
								name={Liferay.Language.get('name')}
							>
								<ClayInput
									id={`${config.portletNamespace}templateSetName`}
									name={`${config.portletNamespace}name`}
									onChange={(event) => {
										setTemplateSetName(event.target.value);

										setFormErrors({
											...formErrors,
											setTemplateSetName: null,
										});
									}}
									ref={nameInputRef}
									required
									value={templateSetName}
								/>
							</FormField>
							<FormField
								id={`${config.portletNamespace}templateSetDescription`}
								name={Liferay.Language.get('description')}
								required={false}
							>
								<ClayInput
									component="textarea"
									id={`${config.portletNamespace}templateSetDescription`}
									name={`${config.portletNamespace}description`}
									onChange={(event) => {
										setTemplateSetDescription(
											event.target.value
										);
									}}
									ref={nameInputRef}
									value={templateSetDescription}
								/>
							</FormField>
						</>
					) : (
						<>
							<div className="mb-3 text-secondary">
								{Liferay.Language.get(
									'select-an-existing-set-or-create-a-new-one-to-save-your-page-template'
								)}
							</div>

							<FormField
								error={formErrors.templateSetId}
								id={`${config.portletNamespace}templateSet`}
								name={Liferay.Language.get('page-template-set')}
							>
								<ClaySelectWithOption
									id={`${config.portletNamespace}templateSet`}
									onChange={(event) => {
										setTemplateSetId(event.target.value);
										setFormErrors({
											...formErrors,
											templateSetId: null,
										});
									}}
									options={templateSetSelectOptions}
									required
									value={templateSetId}
								/>
							</FormField>
						</>
					)}
				</ClayForm>
			</ClayModal.Body>

			<ClayModal.Footer
				first={
					!openAddTemplateSetModal ? (
						<ClayButton
							displayType="secondary"
							onClick={() => setOpenAddTemplateSetModal(true)}
						>
							{Liferay.Language.get('save-in-new-set')}
						</ClayButton>
					) : null
				}
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

							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

PageTemplateModal.propTypes = {
	observer: PropTypes.object.isRequired,
	onClose: PropTypes.func.isRequired,
};
