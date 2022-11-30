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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import CodeEditor, {SidebarCategory} from './CodeEditor/index';
import {FieldBase} from './FieldBase';

export function ExpressionBuilder({
	buttonDisabled,
	className,
	component,
	disabled,
	error,
	feedbackMessage,
	hideFeedback,
	id,
	label,
	name,
	onChange,
	onInput,
	onOpenModal,
	required,
	type,
	value,
	...otherProps
}: IProps) {
	return (
		<FieldBase
			className={className}
			disabled={disabled}
			errorMessage={error}
			helpMessage={feedbackMessage}
			hideFeedback={hideFeedback}
			id={id}
			label={label}
			required={required}
		>
			<ClayInput.Group>
				<ClayInput.GroupItem prepend>
					<ClayInput
						{...otherProps}
						component={component}
						disabled={disabled}
						id={id}
						name={name}
						onChange={onChange}
						onInput={onInput}
						type={type}
						value={value}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append shrink>
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('expand-input-area')}
						disabled={buttonDisabled}
						displayType="secondary"
						onClick={onOpenModal}
						symbol="code"
						title={Liferay.Language.get('expand-input-area')}
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</FieldBase>
	);
}

export function ExpressionBuilderModal({sidebarElements}: IModalProps) {
	const editorRef = useRef<CodeMirror.Editor>(null);
	const [
		{
			error,
			header,
			onSave,
			placeholder,
			required,
			source,
			validateExpressionURL,
		},
		setState,
	] = useState<{
		error?: string;
		header?: string;
		onSave?: Callback;
		placeholder?: string;
		required?: boolean;
		source?: string;
		validateExpressionURL?: string;
	}>({});

	const {observer, onOpenChange} = useModal({
		onClose: () => setState({}),
	});

	useEffect(() => {
		const openModal = (params: {
			header: string;
			onSave: Callback;
			placeholder: string;
			required: boolean;
			source: string;
			validateExpressionURL: string;
		}) => {
			setState(params);
		};

		Liferay.on('openExpressionBuilderModal', openModal);

		return () =>
			Liferay.detach(
				'openExpressionBuilderModal',
				openModal as () => void
			);
	}, []);

	if (source === undefined) {
		return null;
	}

	const closeModal = () => {
		onOpenChange(false);
	};

	const handleSave = async () => {
		const source = editorRef.current?.getValue();

		let error: string | undefined;

		if (required && !source?.trim()) {
			error = Liferay.Language.get('required');
		}
		else if (source?.trim() && validateExpressionURL) {
			const response = await fetch(
				createResourceURL(validateExpressionURL, {
					expression: source,
				}).href
			);

			const {valid}: {valid: boolean} = await response.json();

			if (!valid) {
				error = Liferay.Language.get('syntax-error');
			}
		}

		if (error) {
			setState((state) => ({
				...state,
				error,
			}));
		}
		else {
			onSave?.(source);
			closeModal();
		}
	};

	return (
		<ClayModal
			className="lfr-objects__expression-builder-modal"
			observer={observer}
			size="lg"
		>
			<ClayModal.Header>
				{header ?? Liferay.Language.get('expression-builder')}
			</ClayModal.Header>

			<ClayModal.Body>
				<CodeEditor
					error={error}
					onChange={() => {}}
					placeholder={
						placeholder ??
						`<#-- ${Liferay.Util.sub(
							Liferay.Language.get(
								'create-the-condition-of-the-action-using-the-expression-builder-type-x-to-use-the-autocomplete-feature'
							),
							['"${"']
						)} -->`
					}
					ref={editorRef}
					sidebarElements={sidebarElements}
					value={source}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton onClick={handleSave}>
							{Liferay.Language.get('done')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

type Callback = (source?: string) => void;

interface IModalProps {
	sidebarElements: SidebarCategory[];
}
interface IProps extends React.InputHTMLAttributes<HTMLInputElement> {
	buttonDisabled?: boolean;
	component?: 'input' | 'textarea' | React.ForwardRefExoticComponent<any>;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	hideFeedback?: boolean;
	id?: string;
	label?: string;
	name?: string;
	onOpenModal: () => void;
	required?: boolean;
	type?: 'number' | 'text';
	value?: string | number | string[];
}
