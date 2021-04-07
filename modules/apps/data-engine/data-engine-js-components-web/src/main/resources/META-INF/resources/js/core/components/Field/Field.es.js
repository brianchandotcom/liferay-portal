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

import './Field.scss';

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {sub} from 'dynamic-data-mapping-form-field-type/util/strings.es';
import MetalComponent from 'metal-component';
import React, {
	Suspense,
	lazy,
	useCallback,
	useContext,
	useRef,
	useState,
} from 'react';

import {useFormState} from '../../hooks/useForm.es';
import {usePage} from '../../hooks/usePage.es';
import {useStorage} from '../../hooks/useStorage.es';
import {AutoFocus} from '../AutoFocus.es';
import {ErrorBoundary} from '../ErrorBoundary.es';
import {MetalComponentAdapter} from './MetalComponentAdapter.es';
import {ParentFieldContext} from './ParentFieldContext.es';

const getModule = (fieldTypes, fieldType) => {
	const field = fieldTypes.find((field) => field.name === fieldType);

	return field;
};

const load = (fieldModule) => {
	return new Promise((resolve, reject) => {
		Liferay.Loader.require(
			[fieldModule],
			(Field) => resolve(Field),
			(error) => reject({error, network: true})
		);
	});
};

const useLazy = () => {
	const {components} = useStorage();

	return useCallback(
		(fieldModule) => {
			if (!components.has(fieldModule)) {
				const Component = lazy(() => {
					return load(fieldModule)
						.then((instance) => {
							if (!(instance && instance.default)) {
								return null;
							}

							// To maintain compatibility with fields in Metal+Soy,
							// we call the bridge component to handle this component.

							if (
								MetalComponent.isComponentCtor(instance.default)
							) {
								return {
									default: MetalComponentAdapter,
								};
							}

							return instance;
						})
						.catch((error) => {
							components.delete(fieldModule);

							throw error;
						});
				});

				components.set(fieldModule, Component);
			}

			return components.get(fieldModule);
		},
		[components]
	);
};

class FieldEventStruct {
	constructor(event, field, value = null, key) {
		this.fieldInstance = {
			...field,

			// This is a fake function that maintains compatibility with the use
			// of Metal+Soy fields.

			isDisposed: () => false,
		};

		this.key = key;
		this.originalEvent = event;
		this.value = value !== null ? value : event?.target?.value;
	}
}

/**
 * This only assembles the expected structure of the Forms field
 * event, creates a makeup to maintain compatibility with the
 * mechanism, the fields in React do not need to assemble this
 * structure, they must only provide a native event or value in
 * the case of an onChange
 */
const mountStruct = (event, field, value, key) => {

	// A field event struct may have been declared before, for cases of nested
	// fields with the FieldSet field.

	if (event instanceof FieldEventStruct) {
		return event;
	}

	return new FieldEventStruct(event, field, value, key);
};

const FieldLazy = ({
	field,
	fieldTypes,
	onBlur,
	onChange,
	onFocus,
	...otherProps
}) => {
	const {editable} = usePage();

	const focusDurationRef = useRef({end: null, start: null});

	const {configuration = {}, javaScriptModule} = getModule(
		fieldTypes,
		field.type
	);

	const ComponentLazy = useLazy()(javaScriptModule);

	return (
		<ComponentLazy
			onBlur={(event) => {
				focusDurationRef.current.end = new Date();
				onBlur(mountStruct(event, field), focusDurationRef.current);
			}}
			onChange={(event, value, key) =>
				onChange(mountStruct(event, field, value, key))
			}
			onFocus={(event) => {
				focusDurationRef.current.start = new Date();
				onFocus(mountStruct(event, field));
			}}
			{...field}
			readOnly={editable ? true : field.readOnly}
			visible={editable ? true : field.visible ?? true}
			{...otherProps}
			{...configuration}
		/>
	);
};

const getRootParentField = (field, currentLoc, {loc, root}) => {
	if (root) {
		return {
			...field,
			loc: [currentLoc, ...loc],
			root,
		};
	}

	return {
		...field,
		loc: [currentLoc],
		root: field,
	};
};

export const Field = ({field, loc, ...otherProps}) => {
	const parentField = useContext(ParentFieldContext);
	const {defaultLanguageId, editingLanguageId} = useFormState();
	const {fieldTypes} = usePage();
	const [hasError, setHasError] = useState();

	const getReadOnly = ({
		editOnlyInDefaultLanguage = false,
		localizable,
		readOnly,
	}) => {
		if (
			editOnlyInDefaultLanguage &&
			!localizable &&
			editingLanguageId !== defaultLanguageId
		) {
			return true;
		}

		return readOnly;
	};

	if (!fieldTypes) {
		return <ClayLoadingIndicator />;
	}

	if (hasError) {
		return (
			<div className="ddm-field-renderer--error">
				<p className="ddm-field-renderer--title">
					{sub(
						Liferay.Language.get(
							'there-was-an-error-when-loading-the-x-field'
						),
						[field.type]
					)}
				</p>
				{hasError.network && (
					<ClayButton
						className="ddm-field-renderer--button"
						displayType="secondary"
						onClick={() => setHasError(false)}
						small
					>
						{Liferay.Language.get('refresh')}
					</ClayButton>
				)}
			</div>
		);
	}

	return (
		<ErrorBoundary onError={setHasError}>
			<AutoFocus>
				<div className="ddm-field" data-field-name={field.fieldName}>
					<Suspense fallback={<ClayLoadingIndicator />}>
						<ParentFieldContext.Provider
							value={getRootParentField(field, loc, parentField)}
						>
							<FieldLazy
								field={{
									...field,
									readOnly: getReadOnly(field),
								}}
								fieldTypes={fieldTypes}
								{...otherProps}
							/>
						</ParentFieldContext.Provider>
					</Suspense>
				</div>
			</AutoFocus>
		</ErrorBoundary>
	);
};
