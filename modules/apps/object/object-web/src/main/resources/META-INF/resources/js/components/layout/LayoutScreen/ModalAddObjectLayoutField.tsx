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
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import ClayModal from '@clayui/modal';
import React, {useContext, useMemo, useState} from 'react';

import {normalizeLanguageId} from '../../../utils/string';
import CustomSelect from '../../CustomSelect/CustomSelect';
import LayoutContext, {TYPES} from '../context';
import {TObjectField} from '../types';
import RequiredLabel from './RequiredLabel';

const defaultLanguageId = normalizeLanguageId(
	Liferay.ThemeDisplay.getDefaultLanguageId()
);

interface IModalAddObjectLayoutFieldProps
	extends React.HTMLAttributes<HTMLElement> {
	boxIndex: number;
	observer: any;
	onClose: () => void;
	tabIndex: number;
}

const ModalAddObjectLayoutField: React.FC<IModalAddObjectLayoutFieldProps> = ({
	boxIndex,
	observer,
	onClose,
	tabIndex,
}) => {
	const [{objectFields}, dispatch] = useContext(LayoutContext);
	const [active, setActive] = useState<boolean>(false);
	const [query, setQuery] = useState<string>('');
	const [selectedObjectField, setSelectedObjectField] = useState<
		TObjectField
	>();

	const filteredObjectFields = useMemo(() => {
		return objectFields.filter(({inLayout, label}) => {
			return label[defaultLanguageId].match(query) && !inLayout;
		});
	}, [objectFields, query]);

	return (
		<ClayModal observer={observer}>
			<ClayModal.Header>
				{Liferay.Language.get('add-block')}
			</ClayModal.Header>
			<ClayModal.Body>
				<ClayForm.Group>
					<label htmlFor="inputField">
						{Liferay.Language.get('field')}
					</label>

					<ClayDropDown
						active={active}
						onActiveChange={(value) => setActive(value)}
						trigger={
							<CustomSelect
								contentRight={
									<>
										{selectedObjectField?.label[
											defaultLanguageId
										] && (
											<RequiredLabel
												className="label-inside-custom-select"
												required={
													selectedObjectField.required
												}
											/>
										)}
									</>
								}
								placeholder={Liferay.Language.get(
									'choose-an-option'
								)}
								value={
									selectedObjectField?.label[
										defaultLanguageId
									]
								}
							/>
						}
					>
						<ClayDropDown.Search
							onChange={(event) => setQuery(event.target.value)}
							placeholder={Liferay.Language.get('search')}
							value={query}
						/>

						{filteredObjectFields.length ? (
							<ClayDropDown.ItemList>
								{filteredObjectFields.map(
									(objectField, objectFieldIndex) => {
										const {label, required} = objectField;

										return (
											<ClayDropDown.Item
												key={objectFieldIndex}
												onClick={() => {
													setSelectedObjectField(
														objectField
													);
													setActive(false);
												}}
											>
												<div className="d-flex justify-content-between">
													<div>
														{
															label[
																defaultLanguageId
															]
														}
													</div>
													<div>
														<RequiredLabel
															required={required}
														/>
													</div>
												</div>
											</ClayDropDown.Item>
										);
									}
								)}
							</ClayDropDown.ItemList>
						) : (
							<ClayDropDown.ItemList>
								<ClayDropDown.Item>
									{Liferay.Language.get(
										'there-are-no-fields-for-this-object'
									)}
								</ClayDropDown.Item>
							</ClayDropDown.ItemList>
						)}
					</ClayDropDown>
				</ClayForm.Group>
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton
							onClick={() => {
								dispatch({
									payload: {
										boxIndex,
										objectFieldId: selectedObjectField?.id,
										tabIndex,
									},
									type: TYPES.ADD_OBJECT_LAYOUT_FIELD,
								});

								onClose();
							}}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

export default ModalAddObjectLayoutField;
