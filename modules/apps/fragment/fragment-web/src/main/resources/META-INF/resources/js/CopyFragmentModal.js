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
import {fetch, navigate} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

export default function CopyFragmentModal({
	addFragmentCollectionURL,
	contributedEntryKeys,
	copyFragmentEntriesURL,
	fragmentCollections,
	fragmentEntryIds,
	portletNamespace,
}) {
	const noFragmentCollections = !fragmentCollections.length;

	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});

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
		}).then((response) => {
			onClose();

			if (response.redirected) {
				navigate(response.url);
			}
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
					{showFragmentSetForm ? (
						<FragmentSetForm
							addFragmentCollectionURL={addFragmentCollectionURL}
							copyFragments={copyFragments}
							formId={formId}
							portletNamespace={portletNamespace}
							showNoFragmentCollectionMessage={
								noFragmentCollections
							}
						/>
					) : (
						<FragmentSetSelector
							copyFragments={copyFragments}
							formId={formId}
							fragmentCollections={fragmentCollections}
							portletNamespace={portletNamespace}
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
	formId,
	fragmentCollections,
	portletNamespace,
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

		copyFragments(selectedFragmentCollection);
	};

	return (
		<ClayForm id={formId} onSubmit={handleSubmit}>
			<ClayForm.Group>
				<label htmlFor={`${portletNamespace}fragment-sets`}>
					{Liferay.Language.get('fragment-sets')}

					<ClayIcon className="reference-mark" symbol="asterisk" />
				</label>

				<ClaySelectWithOption
					id={`${portletNamespace}fragment-sets`}
					onChange={(event) =>
						setSelectedFragmentCollection(event.target.value)
					}
					options={items}
					value={selectedFragmentCollection}
				/>
			</ClayForm.Group>
		</ClayForm>
	);
}

function FragmentSetForm({
	addFragmentCollectionURL,
	copyFragments,
	formId,
	portletNamespace,
	showNoFragmentCollectionMessage,
}) {
	const [name, setName] = useState(
		showNoFragmentCollectionMessage
			? Liferay.Language.get('untitled-set')
			: ''
	);
	const [description, setDescription] = useState('');

	const handleSubmit = (event) => {
		event.preventDefault();

		const formData = new FormData();

		formData.append(`${portletNamespace}name`, name);

		formData.append(`${portletNamespace}description`, description);

		fetch(addFragmentCollectionURL, {body: formData, method: 'POST'})
			.then((response) => response.json())
			.then((response) => {
				if (response.fragmentCollectionId) {
					copyFragments(response.fragmentCollectionId);
				}
			});
	};

	return (
		<ClayForm id={formId} onSubmit={handleSubmit}>
			{showNoFragmentCollectionMessage ? (
				<p className="text-secondary">
					{Liferay.Language.get(
						'a-fragment-set-must-first-be-created-before-you-can-copy-your-fragment'
					)}
				</p>
			) : null}

			<ClayForm.Group>
				<label htmlFor={`${portletNamespace}name`}>
					{Liferay.Language.get('name')}

					<ClayIcon className="reference-mark" symbol="asterisk" />
				</label>

				<ClayInput
					id={`${portletNamespace}name`}
					name={`${portletNamespace}name`}
					onChange={(event) => setName(event.target.value)}
					required
					type="text"
					value={name}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor={`${portletNamespace}description`}>
					{Liferay.Language.get('description')}
				</label>

				<textarea
					className="form-control"
					id={`${portletNamespace}description`}
					name={`${portletNamespace}description`}
					onChange={(event) => setDescription(event.target.value)}
					value={description}
				/>
			</ClayForm.Group>
		</ClayForm>
	);
}
