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
import ClayLabel from '@clayui/label';
import ClayModal from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import {
	AutoComplete,
	FormError,
	REQUIRED_MSG,
	stringIncludesQuery,
	useForm,
} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import React, {useMemo, useState} from 'react';

import {defaultLanguageId} from '../../../utils/constants';
import {TYPES, useLayoutContext} from '../objectLayoutContext';
import {TObjectField} from '../types';

import './ModalAddObjectLayoutField.scss';

const objectFieldSizes = [1, 2, 3];

type TInitialValues = {
	objectFieldName: string;
	objectFieldSize: number;
};

interface IBoxBtnColumnsProps {
	setValues: (values: Partial<TInitialValues>) => void;
}

function BoxBtnColumns({setValues}: IBoxBtnColumnsProps) {
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

				return (
					<button
						className={classNames('box-btn-columns__btn', {
							active: activeIndex === objectFieldSizeIndex,
						})}
						key={objectFieldSizeIndex}
						name="objectFieldSize"
						onClick={() => {
							setActiveIndex(objectFieldSizeIndex);
							setValues({objectFieldSize});
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
}

interface IProps extends React.HTMLAttributes<HTMLElement> {
	boxIndex: number;
	observer: Observer;
	onClose: () => void;
	tabIndex: number;
}

export default function ModalAddObjectLayoutField({
	boxIndex,
	observer,
	onClose,
	tabIndex,
}: IProps) {
	const [{objectFields}, dispatch] = useLayoutContext();
	const [query, setQuery] = useState<string>('');
	const [selectedObjectField, setSelectedObjectField] = useState<
		TObjectField
	>();

	const [readOnlyField, setReadOnlyField] = useState<ObjectFieldSetting>({
		name: 'readOnly',
		value: 'false',
	});

	const filteredObjectFields = useMemo(() => {
		return objectFields.filter(
			({inLayout, label}) =>
				stringIncludesQuery(
					label[defaultLanguageId] as string,
					query
				) && !inLayout
		);
	}, [objectFields, query]);

	const onSubmit = (values: TInitialValues) => {
		dispatch({
			payload: {
				boxIndex,
				objectFieldName: values.objectFieldName,
				objectFieldSize: 12 / Number(values.objectFieldSize),
				tabIndex,
			},
			type: TYPES.ADD_OBJECT_LAYOUT_FIELD,
		});

		onClose();
	};

	const onValidate = (values: TInitialValues) => {
		const errors: FormError<TInitialValues> = {};

		if (!values.objectFieldName) {
			errors.objectFieldName = REQUIRED_MSG;
		}

		return errors;
	};

	const initialValues: TInitialValues = {
		objectFieldName: '',
		objectFieldSize: 1,
	};

	const {errors, handleSubmit, setValues} = useForm({
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
					<AutoComplete<TObjectField>
						contentRight={
							<>
								<ClayLabel
									className="label-inside-custom-select"
									displayType={
										selectedObjectField?.required
											? 'warning'
											: 'success'
									}
								>
									{selectedObjectField?.required
										? Liferay.Language.get('mandatory')
										: Liferay.Language.get('optional')}
								</ClayLabel>

								{(readOnlyField.value === 'true' ||
									readOnlyField.value === 'conditional') && (
									<ClayLabel
										className="label-inside-custom-select"
										displayType="secondary"
									>
										{Liferay.Language.get('read-only')}
									</ClayLabel>
								)}
							</>
						}
						creationLanguageId={
							selectedObjectField?.indexedLanguageId as Liferay.Language.Locale
						}
						emptyStateMessage={Liferay.Language.get(
							'there-are-no-fields-for-this-object'
						)}
						error={errors.objectFieldName}
						items={filteredObjectFields}
						label={Liferay.Language.get('field')}
						onChangeQuery={setQuery}
						onSelectItem={(item: ObjectField) => {
							const readOnlySetting = item.objectFieldSettings?.find(
								(fieldSetting) =>
									fieldSetting.name === 'readOnly'
							);
							if (readOnlySetting) {
								setReadOnlyField(readOnlySetting);
							}
							setSelectedObjectField(item);
							setValues({objectFieldName: item.name});
						}}
						query={query}
						required
						value={selectedObjectField?.label[defaultLanguageId]}
					>
						{({label, objectFieldSettings, required}) => (
							<div className="d-flex justify-content-between">
								<div className="lfr__object-web-layout-modal-add-field-label">
									{label[defaultLanguageId]}
								</div>

								<div>
									<ClayLabel
										className="label-inside-custom-select"
										displayType={
											required ? 'warning' : 'success'
										}
									>
										{required
											? Liferay.Language.get('mandatory')
											: Liferay.Language.get('optional')}
									</ClayLabel>

									{objectFieldSettings?.find(
										(fieldSetting: ObjectFieldSetting) =>
											fieldSetting.value === 'true' ||
											fieldSetting.value === 'conditional'
									) && (
										<ClayLabel
											className="label-inside-custom-select"
											displayType="secondary"
										>
											{Liferay.Language.get('read-only')}
										</ClayLabel>
									)}
								</div>
							</div>
						)}
					</AutoComplete>

					<BoxBtnColumns setValues={setValues} />
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
}
