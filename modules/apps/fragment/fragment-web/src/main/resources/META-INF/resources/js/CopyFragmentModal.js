/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {fetch, navigate, openToast, sub} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

import FormField from './FormField';

export default function CopyFragmentModal({
	addFragmentCollectionURL,
	contributedEntryKeys,
	copyFragmentEntriesURL,
	fragmentCollections = [],
	fragmentEntryIds,
	portletNamespace,
}) {
	const noFragmentCollections = !fragmentCollections.length;

	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});

	const [errors, setErrors] = useState({});
	const [showFragmentSetForm, setShowFragmentSetForm] = useState(
		noFragmentCollections
	);
	const [visible, setVisible] = useState(true);

	const formId = `${portletNamespace}form`;

	const copyFragments = (fragmentCollectionId) => {
		const formData = new FormData();

		if (fragmentEntryIds) {
			formData.append(
				`${portletNamespace}fragmentEntryIds`,
				fragmentEntryIds.join(',')
			);
		}

		if (contributedEntryKeys) {
			formData.append(
				`${portletNamespace}contributedEntryKeys`,
				contributedEntryKeys.join(',')
			);
		}

		formData.append(
			`${portletNamespace}fragmentCollectionId`,
			fragmentCollectionId
		);

		fetch(copyFragmentEntriesURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => {
				onClose();

				if (response.redirected) {
					navigate(response.url);
				}

				openToast({
					message: Liferay.Language.get(
						'the-fragment-was-copied-successfully'
					),
					type: 'success',
				});
			})
			.catch(() => {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});
			});
	};

	return (
		visible && (
			<ClayModal observer={observer} size="md">
				<ClayModal.Header>
					{showFragmentSetForm
						? Liferay.Language.get('add-fragment-set')
						: Liferay.Language.get('select-fragment-set')}
				</ClayModal.Header>

				<ClayModal.Body>
					{errors.error && (
						<ClayAlert
							displayType="danger"
							title={Liferay.Language.get('error')}
						>
							{errors.error}
						</ClayAlert>
					)}

					{showFragmentSetForm ? (
						<FragmentSetForm
							addFragmentCollectionURL={addFragmentCollectionURL}
							copyFragments={copyFragments}
							errors={errors}
							formId={formId}
							fragmentCollections={fragmentCollections}
							portletNamespace={portletNamespace}
							setErrors={setErrors}
							showNoFragmentCollectionMessage={
								noFragmentCollections
							}
						/>
					) : (
						<FragmentSetSelector
							copyFragments={copyFragments}
							errors={errors}
							formId={formId}
							fragmentCollections={fragmentCollections}
							portletNamespace={portletNamespace}
							setErrors={setErrors}
						/>
					)}
				</ClayModal.Body>

				<ClayModal.Footer
					first={
						!showFragmentSetForm ? (
							<ClayButton
								displayType="secondary"
								onClick={() => setShowFragmentSetForm(true)}
							>
								{Liferay.Language.get('save-in-new-set')}
							</ClayButton>
						) : null
					}
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								displayType="primary"
								form={formId}
								type="submit"
							>
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayModal>
		)
	);
}

function FragmentSetSelector({
	copyFragments,
	errors,
	formId,
	fragmentCollections,
	portletNamespace,
	setErrors,
}) {
	const [
		selectedFragmentCollection,
		setSelectedFragmentCollection,
	] = useState('');

	const items = useMemo(
		() => [
			{label: `-- ${Liferay.Language.get('not-selected')} --`, value: ''},
			...fragmentCollections.map((fragmentSet) => ({
				label: fragmentSet.name,
				value: fragmentSet.fragmentCollectionId,
			})),
		],
		[fragmentCollections]
	);

	const handleSubmit = (event) => {
		event.preventDefault();

		if (!selectedFragmentCollection) {
			setErrors({
				fragmentSets: sub(
					Liferay.Language.get('x-field-is-required'),
					Liferay.Language.get('fragment-set')
				),
			});

			return;
		}

		copyFragments(selectedFragmentCollection);
	};

	return (
		<ClayForm id={formId} onSubmit={handleSubmit}>
			<FormField
				error={errors.fragmentSets}
				id={`${portletNamespace}fragment-sets`}
				name={Liferay.Language.get('fragment-sets')}
				required
			>
				<ClaySelectWithOption
					id={`${portletNamespace}fragment-sets`}
					onChange={(event) => {
						setErrors({...errors, fragmentSets: null});
						setSelectedFragmentCollection(event.target.value);
					}}
					options={items}
					value={selectedFragmentCollection}
				/>
			</FormField>
		</ClayForm>
	);
}

function FragmentSetForm({
	addFragmentCollectionURL,
	copyFragments,
	errors,
	formId,
	fragmentCollections,
	portletNamespace,
	setErrors,
	showNoFragmentCollectionMessage,
}) {
	const [name, setName] = useState(() =>
		getDefaultFragmentSetName(fragmentCollections)
	);
	const [description, setDescription] = useState('');

	const handleSubmit = (event) => {
		event.preventDefault();

		if (!name) {
			setErrors({
				name: sub(
					Liferay.Language.get('x-field-is-required'),
					Liferay.Language.get('name')
				),
			});

			return;
		}

		const formData = new FormData();

		formData.append(`${portletNamespace}name`, name);

		formData.append(`${portletNamespace}description`, description);

		fetch(addFragmentCollectionURL, {body: formData, method: 'POST'})
			.then((response) => response.json())
			.then((response) => {
				if (response.error) {
					setErrors({error: response.error});
				}
				else if (response.fragmentCollectionId) {
					copyFragments(response.fragmentCollectionId);
				}
			});
	};

	return (
		<ClayForm id={formId} noValidate onSubmit={handleSubmit}>
			{showNoFragmentCollectionMessage ? (
				<p className="text-secondary">
					{Liferay.Language.get(
						'a-fragment-set-must-first-be-created-before-you-can-copy-it'
					)}
				</p>
			) : null}

			<FormField
				error={errors.name}
				id={`${portletNamespace}name`}
				name={Liferay.Language.get('name')}
				required
			>
				<ClayInput
					id={`${portletNamespace}name`}
					name={`${portletNamespace}name`}
					onChange={(event) => {
						setErrors({...errors, name: null});
						setName(event.target.value);
					}}
					required
					type="text"
					value={name}
				/>
			</FormField>

			<FormField
				id={`${portletNamespace}description`}
				name={Liferay.Language.get('description')}
			>
				<textarea
					className="form-control"
					id={`${portletNamespace}description`}
					name={`${portletNamespace}description`}
					onChange={(event) => setDescription(event.target.value)}
					value={description}
				/>
			</FormField>
		</ClayForm>
	);
}

function getDefaultFragmentSetName(fragmentCollections) {
	const untitledSets = fragmentCollections.filter((fragmentCollection) =>
		fragmentCollection.name?.startsWith(
			Liferay.Language.get('untitled-set')
		)
	);

	if (untitledSets.length) {
		return `${Liferay.Language.get('untitled-set')} ${
			untitledSets.length + 1
		}`;
	}

	return Liferay.Language.get('untitled-set');
}
