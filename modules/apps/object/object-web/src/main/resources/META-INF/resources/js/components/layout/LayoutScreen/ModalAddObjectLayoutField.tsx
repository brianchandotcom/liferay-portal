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
import ClayForm from '@clayui/form';
import ClayModal from '@clayui/modal';
import React, {useContext, useMemo, useState} from 'react';

import {normalizeLanguageId} from '../../../utils/string';
import LayoutContext, {TYPES} from '../context';
import {TObjectField} from '../types';
import AutoComplete from './AutoComplete';
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
					<AutoComplete
						contentRight={
							<RequiredLabel
								className="label-inside-custom-select"
								required={selectedObjectField?.required}
							/>
						}
						emptyStateMessage={Liferay.Language.get(
							'there-are-no-fields-for-this-object'
						)}
						items={filteredObjectFields}
						onChangeQuery={setQuery}
						onSelectItem={setSelectedObjectField}
						query={query}
						title={Liferay.Language.get('fields')}
						value={selectedObjectField?.label[defaultLanguageId]}
					>
						{({label, required}) => (
							<div className="d-flex justify-content-between">
								<div>{label[defaultLanguageId]}</div>
								<div>
									<RequiredLabel required={required} />
								</div>
							</div>
						)}
					</AutoComplete>
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
