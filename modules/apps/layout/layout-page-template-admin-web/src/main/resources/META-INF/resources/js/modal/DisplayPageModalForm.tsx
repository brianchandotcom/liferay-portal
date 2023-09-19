/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	RefObject,
	useCallback,
	useEffect,
	useRef,
	useState,
} from 'react';

import {MODAL_TYPES, ModalType} from '../constants/modalTypes';
import {MappingSubtype, MappingType} from '../types/MappingTypes';
import {ValidationError} from '../types/ValidationError';
import FormField from './FormField';

interface Props {
	displayPageName: string;
	error: ValidationError;
	formRef: RefObject<HTMLFormElement>;
	mappingTypes: MappingType[];
	namespace: string;
	onSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
	type: ModalType;
}

export default function DisplayPageModalForm({
	displayPageName,
	error: initialError,
	formRef,
	mappingTypes,
	namespace,
	onSubmit,
	type,
}: Props) {
	const [error, setError] = useState<ValidationError>(initialError);

	const nameInputRef = useRef<HTMLInputElement>(null);

	useEffect(() => {
		if (nameInputRef.current) {
			nameInputRef.current.focus();
		}
	}, []);

	useEffect(() => {
		setError(initialError);
	}, [initialError]);

	if (type === MODAL_TYPES.edit) {
		return (
			<form onSubmit={onSubmit} ref={formRef}>
				<MappingTypeSelector
					error={error}
					mappingTypes={mappingTypes}
					namespace={namespace}
					setError={setError}
				/>
			</form>
		);
	}

	return (
		<form onSubmit={onSubmit} ref={formRef}>
			<FormField
				error={error.name}
				id={`${namespace}name`}
				name={Liferay.Language.get('name')}
			>
				<input
					className="form-control"
					defaultValue={displayPageName}
					id={`${namespace}name`}
					name={`${namespace}name`}
					onChange={() => setError({...error, name: ''})}
					ref={nameInputRef}
				/>
			</FormField>

			<MappingTypeSelector
				error={error}
				mappingTypes={mappingTypes}
				namespace={namespace}
				setError={setError}
			/>
		</form>
	);
}

interface MappingTypesSelectorProps {
	error: ValidationError;
	mappingTypes: MappingType[];
	namespace: string;
	setError: (error: ValidationError) => void;
}

function MappingTypeSelector({
	error,
	mappingTypes,
	namespace,
	setError,
}: MappingTypesSelectorProps) {
	const [subtypes, setSubtypes] = useState<MappingSubtype[]>([]);

	const onChange = useCallback(
		(event) => {
			setError({...error, classNameId: '', classTypeId: ''});

			const select = event.target;
			const selectedMappingId =
				select.options[select.selectedIndex].value;

			const mappingType = mappingTypes.find(
				(mappingType) => mappingType.id === selectedMappingId
			);

			if (mappingType) {
				setSubtypes(mappingType.subtypes);
			}
			else {
				setSubtypes([]);
			}
		},
		[error, mappingTypes, setError]
	);

	if (!Array.isArray(mappingTypes) || !mappingTypes.length) {
		return null;
	}

	return (
		<fieldset>
			<FormField
				error={error.classNameId}
				id={`${namespace}classNameId`}
				name={Liferay.Language.get('content-type')}
			>
				<select
					className="form-control"
					name={`${namespace}classNameId`}
					onChange={onChange}
				>
					<option value="">
						{`-- ${Liferay.Language.get('not-selected')} --`}
					</option>

					{mappingTypes.map((mappingType) => (
						<option key={mappingType.id} value={mappingType.id}>
							{mappingType.label}
						</option>
					))}
				</select>
			</FormField>

			{Array.isArray(subtypes) && Boolean(subtypes.length) && (
				<FormField
					error={error.classTypeId}
					id={`${namespace}classTypeId`}
					name={Liferay.Language.get('subtype')}
				>
					<select
						className="form-control"
						name={`${namespace}classTypeId`}
						onChange={() =>
							setError({
								...error,
								classTypeId: '',
							})
						}
					>
						<option value="">
							{`-- ${Liferay.Language.get('not-selected')} --`}
						</option>

						{subtypes.map((subtype) => (
							<option key={subtype.id} value={subtype.id}>
								{subtype.label}
							</option>
						))}
					</select>
				</FormField>
			)}
		</fieldset>
	);
}
