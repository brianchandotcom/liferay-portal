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
import ClayDatePicker from '@clayui/date-picker';
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import {openModal} from 'frontend-js-web';
import React from 'react';

import OrderableTable from '../components/OrderableTable';

function AddFDSFilterModalContent({closeModal, namespace, onSave}: any) {
	const [type, setType] = React.useState('createDate');

	return (
		<div className="fds-view-fields-modal">
			<ClayModal.Header>
				{Liferay.Language.get('new-filter')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm>
					<ClayForm.Group>
						<label htmlFor={namespace + 'type'}>
							{Liferay.Language.get('filter-by')}
						</label>

						<ClaySelectWithOption
							aria-label={Liferay.Language.get('filter-by')}
							name={namespace + 'type'}
							onChange={(event) => setType(event.target.value)}
							options={[
								{label: 'Create Date', value: 'createDate'},
							]}
							title={Liferay.Language.get('filter-by')}
							value={type}
						/>
					</ClayForm.Group>

					<ClayForm.Group className="form-group-autofit">
						<div className="form-group-item">
							<label htmlFor={namespace + 'from'}>
								{Liferay.Language.get('from')}:
							</label>

							<ClayDatePicker
								id={namespace + 'from'}
								onChange={() => {}}
								placeholder="YYYY-MM-DD"
								value=""
							/>
						</div>

						<div className="form-group-item">
							<label htmlFor={namespace + 'to'}>
								{Liferay.Language.get('to')}:
							</label>

							<ClayDatePicker
								id={namespace + 'to'}
								onChange={() => {}}
								placeholder="YYYY-MM-DD"
								value=""
							/>
						</div>
					</ClayForm.Group>
				</ClayForm>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							onClick={() => {
								closeModal();

								onSave();
							}}
						>
							{Liferay.Language.get('save')}
						</ClayButton>

						<ClayButton
							displayType="secondary"
							onClick={() => closeModal()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</div>
	);
}

function Filters() {
	const onCreationButtonClick = () =>
		openModal({
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<AddFDSFilterModalContent
					closeModal={closeModal}
					onSave={() => {}}
				/>
			),
		});

	return (
		<ClayLayout.ContainerFluid>
			<OrderableTable
				fields={[
					{
						label: Liferay.Language.get('filter-by'),
						name: 'filterBy',
					},
					{
						label: Liferay.Language.get('type'),
						name: 'type',
					},
					{
						label: Liferay.Language.get('value'),
						name: 'value',
					},
				]}
				items={[]}
				noItemsButtonLabel={Liferay.Language.get('create-filter')}
				noItemsDescription={Liferay.Language.get(
					'start-creating-a-filter-to-display-specific-data'
				)}
				noItemsTitle={Liferay.Language.get(
					'no-default-filters-were-created'
				)}
				onCancelButtonClick={() => {}}
				onCreationButtonClick={onCreationButtonClick}
				onOrderChange={() => {}}
				onSaveButtonClick={() => {}}
				title={Liferay.Language.get('filters')}
			/>
		</ClayLayout.ContainerFluid>
	);
}

export default Filters;
