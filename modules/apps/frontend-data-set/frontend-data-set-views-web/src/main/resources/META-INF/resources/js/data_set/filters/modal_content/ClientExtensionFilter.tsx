/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import {IClientExtensionRenderer} from '@liferay/frontend-data-set-web';
import classNames from 'classnames';
import React, {useState} from 'react';

import {API_URL} from '../../../utils/constants';
import {
	IClientExtensionFilter,
	IFilter,
	TSaveState,
} from '../../../utils/types';

function Header() {
	return <>{Liferay.Language.get('new-client-extension-filter')}</>;
}

interface IBodyProps {
	fdsFilterClientExtensions: IClientExtensionRenderer[];
	filter?: IFilter;
	namespace: string;
	onChange: (newState: TSaveState) => void;
}

function Body({
	fdsFilterClientExtensions,
	filter,
	namespace,
	onChange,
}: IBodyProps) {
	const [selectedClientExtension, setSelectedClientExtension] = useState<
		IClientExtensionRenderer | undefined
	>(
		filter
			? fdsFilterClientExtensions.find(
					(clientExtensionRenderer: IClientExtensionRenderer) =>
						clientExtensionRenderer.externalReferenceCode ===
						(filter as IClientExtensionFilter)
							.fdsFilterClientExtensionERC
			  )
			: undefined
	);
	const fdsFilterClientExtensionFormElementId = `${namespace}fdsFilterClientExtensionERC`;

	const handleFilterSave = () => {
		const body = {
			fdsFilterClientExtensionERC:
				selectedClientExtension?.externalReferenceCode,
			fieldName: selectedField?.name,
			label_i18n: i18nFilterLabels,
		};

		handleSave(body);
	};

	const isFormInvalid = ({
		i18nFilterLabels,
		selectedClientExtension,
		selectedField,
	}: {
		i18nFilterLabels: Partial<Liferay.Language.FullyLocalizedValue<string>>;
		selectedClientExtension?: IClientExtensionRenderer;
		selectedField: IField | undefined;
	}) => {
		if (!selectedClientExtension) {
			return true;
		}

		if (!selectedField) {
			return true;
		}

		if (selectedField && !filter) {
			if (inUseFields.includes(selectedField.name)) {
				return true;
			}
		}

		if (!i18nFilterLabels || !Object.values(i18nFilterLabels).length) {
			return true;
		}
		else {
			let isI18nFilterLabelInvalid = false;

			Object.values(i18nFilterLabels).forEach((value) => {
				if (!value) {
					isI18nFilterLabelInvalid = true;
				}
			});

			if (isI18nFilterLabelInvalid) {
				return true;
			}
		}

		return false;
	};

	return (
		<ClayForm.Group className="form-group-autofit">
			<div className={classNames('form-group-item')}>
				<label htmlFor={fdsFilterClientExtensionFormElementId}>
					{Liferay.Language.get('client-extension')}
				</label>

				<ClayDropDown
					closeOnClick
					menuElementAttrs={{
						className: 'fds-cell-renderers-dropdown-menu',
					}}
				/>

				{!fdsFilterClientExtensions.length ? (
					<ClayAlert displayType="info" title="Info">
						{Liferay.Language.get(
							'no-frontend-data-set-filter-client-extensions-are-available.-add-a-client-extension-first-in-order-to-create-a-filter'
						)}
					</ClayAlert>
				) : (
					<ClayForm.Group className="form-group-autofit">
						<div className={classNames('form-group-item')}>
							<label
								htmlFor={fdsFilterClientExtensionFormElementId}
							>
								{Liferay.Language.get('client-extension')}
							</label>

							<ClayDropDown
								closeOnClick
								menuElementAttrs={{
									className:
										'fds-cell-renderers-dropdown-menu',
								}}
								trigger={
									<ClayButton
										aria-labelledby={`${namespace}cellRenderersLabel`}
										className="form-control form-control-select form-control-select-secondary"
										displayType="secondary"
										name={
											fdsFilterClientExtensionFormElementId
										}
									>
										{selectedClientExtension
											? selectedClientExtension.name
											: Liferay.Language.get('select')}
									</ClayButton>
								}
							>
								<ClayDropDown.ItemList
									items={fdsFilterClientExtensions}
									role="listbox"
								>
									{fdsFilterClientExtensions.map(
										(
											filterClientExtension: IClientExtensionRenderer
										) => (
											<ClayDropDown.Item
												className="align-items-center d-flex justify-content-between"
												key={filterClientExtension.name}
												onClick={() => {
													setSelectedClientExtension(
														filterClientExtension
													);

													setSaveButtonDisabled(
														isFormInvalid({
															i18nFilterLabels,
															selectedClientExtension: filterClientExtension,
															selectedField,
														})
													);
												}}
												roleItem="option"
											>
												{filterClientExtension.name}
											</ClayDropDown.Item>
										)
									)}
								</ClayDropDown.ItemList>
							</ClayDropDown>
						</div>
					</ClayForm.Group>
				)}
			</ClayModal.Body>

			<FilterModalFooter
				closeModal={closeModal}
				handleSave={handleFilterSave}
				saveButtonDisabled={saveButtonDisabled}
			/>
		</>
	);
}

export default {
	Body,
	Header,
};
