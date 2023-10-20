/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {TreeView} from '@clayui/core';
import {ClayCheckbox} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import {FDSViewType} from '../../../FDSViews';
import {getFields} from '../../../api';
import {IField} from '../../../types';
import {IFDSField} from '../Fields';

const AddFieldsModalContent = ({
	closeModal,
	fdsFields,
	fdsView,
}: {
	closeModal: Function;
	fdsFields: Array<IFDSField>;
	fdsView: FDSViewType;
}) => {
	const [fields, setFields] = useState<Array<IField> | null>(null);

	useEffect(() => {
		getFields(fdsView).then((newFields) => {
			if (!newFields) {
				return;
			}

			setFields(
				newFields.map((field) => {
					const fdsField = fdsFields.find(
						(fdsField) => fdsField.name === field.name
					);

					return {
						children: field.children,
						id: fdsField?.id,
						name: field.name,
						selected: Boolean(fdsField),
						type: field.type,
						visible: true,
					};
				})
			);
		});
	}, [fdsFields, fdsView]);

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('add-fields')}
			</ClayModal.Header>

			<ClayModal.Body className="bg-light m-4 p-0">
				{fields === null ? (
					<ClayLoadingIndicator />
				) : (
					<div className="pb-2 pt-2">
						<TreeView
							defaultItems={fields}
							selectionMode="multiple"
						>
							{(field: IField) => (
								<TreeView.Item>
									<TreeView.ItemStack>
										<ClayCheckbox checked={false} />

										{field.name}
									</TreeView.ItemStack>

									<TreeView.Group items={field.children}>
										{(field: IField) => (
											<TreeView.Item>
												<ClayCheckbox checked={false} />

												{field.name}
											</TreeView.Item>
										)}
									</TreeView.Group>
								</TreeView.Item>
							)}
						</TreeView>
					</div>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton>{Liferay.Language.get('save')}</ClayButton>

						<ClayButton
							displayType="secondary"
							onClick={() => closeModal()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
};

export default AddFieldsModalContent;
