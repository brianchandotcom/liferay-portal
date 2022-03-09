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
import classNames from 'classnames';
import React, {useContext, useMemo, useState} from 'react';

import useForm from '../../../hooks/useForm';
import AutoComplete from '../../Form/AutoComplete';
import LayoutContext, {TYPES} from '../context';
import {TObjectField} from '../types';
import RequiredLabel from './RequiredLabel';

const objectFieldSizes = [1, 2, 3];

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

type TInitialValues = {
	objectFieldId: number;
	objectFieldSize: number;
};

interface IBoxBtnColumnsProps extends React.HTMLAttributes<HTMLElement> {
	handleChange: any;
}

const BoxBtnColumns: React.FC<IBoxBtnColumnsProps> = ({handleChange}) => {
	const [activeIndex, setActiveIndex] = useState<number>(0);

	return (
		<div className="box-btn-columns">
			{objectFieldSizes.map((objectFieldSize, objectFieldSizeIndex) => {
				const columns = [];

				for (let index = 0; index < objectFieldSize; index++) {
					columns.push(
						<div className="box-btn-columns__item" key={index} />
					);
				}

				const syntheticEvent = {
					target: {
						name: 'objectFieldSize',
						value: String(objectFieldSize),
					},
				} as any;

				return (
					<button
						className={classNames('box-btn-columns__btn', {
							active: activeIndex === objectFieldSizeIndex,
						})}
						key={objectFieldSizeIndex}
						name="objectFieldSize"
						onClick={() => {
							setActiveIndex(objectFieldSizeIndex);
							handleChange(syntheticEvent);
						}}
						type="button"
						value={objectFieldSize}
					>
						{columns}
					</button>
				);
			})}
		</div>
	);
};

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
			return (
				label[defaultLanguageId]
					.toLowerCase()
					.match(query.toLowerCase()) && !inLayout
			);
		});
	}, [objectFields, query]);

	const onSubmit = (values: any) => {
		dispatch({
			payload: {
				boxIndex,
				objectFieldId: values.objectFieldId,
				objectFieldSize: 12 / Number(values.objectFieldSize),
				tabIndex,
			},
			type: TYPES.ADD_OBJECT_LAYOUT_FIELD,
		});

		onClose();
	};

	const onValidate = (values: any) => {
		const errors: any = {};

		if (!values.objectFieldId) {
			errors.objectFieldId = Liferay.Language.get('required');
		}

		return errors;
	};

	const initialValues: TInitialValues = {
		objectFieldId: 0,
		objectFieldSize: 1,
	};

	const {errors, handleChange, handleSubmit} = useForm({
		initialValues,
		onSubmit,
		validate: onValidate,
	});

	return (
		<ClayModal observer={observer}>
			<ClayForm onSubmit={handleSubmit}>
				<ClayModal.Header>
					{Liferay.Language.get('add-field')}
				</ClayModal.Header>

				<ClayModal.Body>
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
						error={errors.objectFieldId}
						items={filteredObjectFields}
						label={Liferay.Language.get('field')}
						onChangeQuery={setQuery}
						onSelectItem={(item) => {
							const syntheticEvent: any = {
								target: {
									name: 'objectFieldId',
									value: item.id,
								},
							};

							setSelectedObjectField(item);
							handleChange(syntheticEvent);
						}}
						query={query}
						required
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

					<BoxBtnColumns handleChange={handleChange} />
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton type="submit">
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
};

export default ModalAddObjectLayoutField;
