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
import React, {useEffect, useRef, useState} from 'react';

import CodeEditor, {SidebarCategory} from './CodeEditor/index';
import {FieldBase} from './FieldBase';

import './ExpressionBuilder.scss';

export function ExpressionBuilder({
	className,
	component,
	disabled,
	error,
	feedbackMessage,
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
	const {observer, onOpenChange} = useModal();
	const editorRef = useRef<CodeMirror.Editor>(null);
	const [{error, onSave, source}, setState] = useState<{
		error?: string;
		onSave?: Callback;
		source?: string;
	}>({});

	useEffect(() => {
		const openModal = (params: {onSave: Callback; source: string}) => {
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
		setState({});
		onOpenChange(false);
	};

	const handleSave = () => {
		const source = editorRef.current?.getValue();

		if (source?.trim()) {
			onSave?.(source);
			closeModal();
		}
		else {
			setState((state) => ({
				...state,
				error: Liferay.Language.get('required'),
			}));
		}
	};

	return (
		<ClayModal observer={observer} size="lg">
			<ClayModal.Header>
				{Liferay.Language.get('expression-builder')}
			</ClayModal.Header>

			<ClayModal.Body className="lfr-objects__expression-builder-modal-body">
				<CodeEditor
					error={error}
					onChange={() => {}}
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
							{Liferay.Language.get('save')}
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
	component?: 'input' | 'textarea' | React.ForwardRefExoticComponent<any>;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label?: string;
	name?: string;
	onOpenModal: () => void;
	required?: boolean;
	type?: 'number' | 'text';
	value?: string | number | string[];
}
