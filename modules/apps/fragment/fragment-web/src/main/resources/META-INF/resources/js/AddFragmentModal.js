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

import ClayCard from '@clayui/card';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {MultiStepFormModal, MultiStepFormModalStep} from './MultiStepFormModal';

function getFieldName(namespace, fieldName) {
	return namespace.concat(fieldName);
}

export default function AddFragmentModal({
	addFragmentEntryURL,
	fieldTypes,
	fragmentTypes,
	namespace,
}) {
	const [name, setName] = useState('');
	const [type, setType] = useState(fragmentTypes[0]);

	const [visible, setVisible] = useState(true);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});

	return (
		visible && (
			<MultiStepFormModal
				className="add-fragment-modal"
				observer={observer}
				onClose={onClose}
				submitLabel={Liferay.Language.get('add')}
				submitURL={addFragmentEntryURL}
				title={Liferay.Language.get('add-fragment')}
			>
				<MultiStepFormModalStep>
					<p className="font-weight-semi-bold mb-4 text-secondary">
						{Liferay.Language.get('select-fragment-type')}
					</p>

					<div className="d-flex">
						{fragmentTypes.map((fragmentType) => (
							<FragmentTypeCard
								active={type.key === fragmentType.key}
								fragmentType={fragmentType}
								key={fragmentType.key}
								onSelect={setType}
							/>
						))}
					</div>

					<input
						name={getFieldName(namespace, 'type')}
						readOnly
						required
						type="hidden"
						value={type.key}
					/>
				</MultiStepFormModalStep>

				<MultiStepFormModalStep>
					<ClayForm.Group>
						<label htmlFor={getFieldName(namespace, 'name')}>
							{Liferay.Language.get('fragment-name')}
						</label>

						<ClayInput
							id={getFieldName(namespace, 'name')}
							name={getFieldName(namespace, 'name')}
							onChange={(event) => setName(event.target.value)}
							required
							type="text"
							value={name}
						/>
					</ClayForm.Group>

					{type.name === 'form' && (
						<ClayForm.Group className="form-group-sm">
							<p className="sheet-subtitle">
								{Liferay.Language.get(
									'select-supported-field-types'
								)}
							</p>

							<p>
								{Liferay.Language.get(
									'specify-which-field-types-this-fragment-will-support.-you-can-change-this-configuration-later'
								)}
							</p>

							{fieldTypes.map(({key, label}) => (
								<ClayCheckbox
									aria-label={label}
									key={key}
									label={label}
									name={getFieldName(namespace, 'fieldTypes')}
									value={key}
								/>
							))}
						</ClayForm.Group>
					)}
				</MultiStepFormModalStep>
			</MultiStepFormModal>
		)
	);
}

AddFragmentModal.propTypes = {
	addFragmentEntryURL: PropTypes.string.isRequired,
	fieldTypes: PropTypes.array.isRequired,
	fragmentTypes: PropTypes.array.isRequired,
	namespace: PropTypes.string.isRequired,
};

function FragmentTypeCard({active, fragmentType, onSelect}) {
	const {description, name, symbol, title} = fragmentType;

	return (
		<ClayCard
			active={active}
			className={`fragment-type-card mb-0 fragment-type-card-${name}`}
			onClick={() => onSelect(fragmentType)}
			selectable
		>
			<ClayCard.AspectRatio className="card-item-first">
				<div className="aspect-ratio-item aspect-ratio-item-center-middle card-type-asset-icon">
					<ClayIcon className="text-white" symbol={symbol} />
				</div>
			</ClayCard.AspectRatio>

			<ClayCard.Body>
				<ClayCard.Row>
					<div className="autofit-col autofit-col-expand">
						<section className="autofit-section">
							<ClayCard.Description displayType="title">
								{title}
							</ClayCard.Description>

							<ClayCard.Description
								displayType="text"
								truncate={false}
							>
								{description}
							</ClayCard.Description>
						</section>
					</div>
				</ClayCard.Row>
			</ClayCard.Body>
		</ClayCard>
	);
}

FragmentTypeCard.propTypes = {
	active: PropTypes.bool.isRequired,
	fragmentType: PropTypes.object.isRequired,
	onSelect: PropTypes.func.isRequired,
};
