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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {ItemSelectorDialog, cancelDebounce, debounce} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

const useDebounceCallback = (callback, milliseconds) => {
	const callbackRef = useRef(debounce(callback, milliseconds));

	return [callbackRef.current, () => cancelDebounce(callbackRef.current)];
};

const ImagePicker = ({
	id,
	inputValue,
	itemSelectorURL,
	name,
	onClearClick,
	onDescriptionChange,
	onFieldChanged,
	portletNamespace,
	readOnly,
}) => {
	const [imageValues, setImageValues] = useState(inputValue);
	const [modalVisible, setModalVisible] = useState(false);

	useEffect(() => {
		setImageValues({
			...{description: '', title: '', url: ''},
			...inputValue,
		});
	}, [inputValue]);

	const {observer, onClose} = useModal({
		onClose: () => setModalVisible(false),
	});

	const dispatchValue = ({clear, value}, callback = () => {}) => {
		setImageValues((oldValues) => {
			let mergedValues = {...oldValues, ...value};

			mergedValues = clear ? {} : mergedValues;

			callback(mergedValues);

			return mergedValues;
		});
	};

	const handleClearClick = (event) => {
		dispatchValue(
			{clear: true, value: {description: '', event, title: '', url: ''}},
			(mergedValues) => {
				onClearClick(mergedValues);
			}
		);
	};

	const [debounce] = useDebounceCallback(({event, value}) => {
		dispatchValue({value: {description: value, event}}, (mergedValues) => {
			onDescriptionChange(mergedValues);
		});
	}, 500);

	const handleDescriptionChange = ({event, target: {value}}) =>
		debounce({event, value});

	const handleFieldChanged = (event) => {
		const selectedItem = event.selectedItem;

		if (selectedItem && selectedItem.value) {
			const img = new Image();
			const item = JSON.parse(selectedItem.value);
			img.addEventListener('load', (event) => {
				const {
					target: {height, width},
				} = event;

				const imageData = {
					...{
						description: '',
						event,
						height,
						title: '',
						url: '',
						width,
					},
					...item,
				};

				dispatchValue({value: imageData}, (mergedValues) => {
					onFieldChanged(mergedValues);
				});
			});
			img.src = item.url;
		}
	};

	const handleItemSelectorTriggerClick = (event) => {
		event.preventDefault();

		const itemSelectorDialog = new ItemSelectorDialog({
			eventName: `${portletNamespace}selectDocumentLibrary`,
			singleSelect: true,
			url: itemSelectorURL,
		});

		itemSelectorDialog.on('selectedItemChange', handleFieldChanged);

		itemSelectorDialog.open();
	};

	const placeholder = readOnly
		? ''
		: Liferay.Language.get('add-image-description');

	return (
		<>
			<ClayForm.Group style={{marginBottom: '0.5rem'}}>
				<input
					id={id}
					name={name}
					type="hidden"
					value={JSON.stringify(imageValues)}
				/>
				<ClayInput.Group>
					<ClayInput.GroupItem className="d-none d-sm-block" prepend>
						<ClayInput
							className="bg-light"
							disabled={readOnly}
							onClick={handleItemSelectorTriggerClick}
							readOnly
							type="text"
							value={imageValues.title}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem append shrink>
						<ClayButton
							disabled={readOnly}
							displayType="secondary"
							onClick={handleItemSelectorTriggerClick}
							type="button"
						>
							{Liferay.Language.get('select')}
						</ClayButton>
					</ClayInput.GroupItem>

					{imageValues.url && (
						<ClayInput.GroupItem shrink>
							<ClayButton
								disabled={readOnly}
								displayType="secondary"
								onClick={handleClearClick}
								type="button"
							>
								{Liferay.Language.get('clear')}
							</ClayButton>
						</ClayInput.GroupItem>
					)}
				</ClayInput.Group>
			</ClayForm.Group>

			{imageValues.url && modalVisible ? (
				<ClayModal
					className="image-picker-preview-modal"
					observer={observer}
					size="full-screen"
				>
					<ClayModal.Header />
					<ClayModal.Body>
						<img
							alt={imageValues.description}
							className="d-block img-fluid mb-2 mx-auto rounded"
							onClick={onClose}
							src={imageValues.url}
							style={{cursor: 'zoom-out', maxHeight: '95%'}}
						/>
						<p
							className="font-weight-light text-center"
							style={{color: '#FFFFFF'}}
						>
							{imageValues.description}
						</p>
					</ClayModal.Body>
				</ClayModal>
			) : (
				imageValues.url && (
					<>
						<div className="image-picker-preview">
							<img
								alt={imageValues.description}
								className="d-block img-fluid mb-2 rounded"
								onClick={() => setModalVisible(true)}
								src={imageValues.url}
								style={{
									cursor: 'pointer',
								}}
							/>
						</div>

						<ClayForm.Group>
							<ClayInput
								defaultValue={imageValues.description}
								disabled={readOnly}
								name={`${name}-description`}
								onChange={handleDescriptionChange}
								placeholder={placeholder}
								type="text"
							/>
						</ClayForm.Group>
					</>
				)
			)}
		</>
	);
};

const Main = ({
	id,
	inputValue,
	itemSelectorURL,
	name,
	onChange,
	portletNamespace,
	readOnly,
	value,
	...otherProps
}) => {
	const formatValue = (sourceValue) => {
		if (sourceValue) {
			if (typeof sourceValue === 'string') {
				return JSON.parse(sourceValue);
			}
			else if (typeof sourceValue === 'object') {
				return sourceValue;
			}
		}

		return null;
	};

	return (
		<FieldBase {...otherProps} id={id} name={name} readOnly={readOnly}>
			<ImagePicker
				id={id}
				inputValue={{
					...(formatValue(inputValue) || formatValue(value) || {}),
				}}
				itemSelectorURL={itemSelectorURL}
				name={name}
				onClearClick={(data) => {
					const {event} = data;

					onChange(event, data);
				}}
				onDescriptionChange={(data) => {
					const {event} = data;

					onChange(event, data);
				}}
				onFieldChanged={(data) => {
					const {event} = data;

					onChange(event, data);
				}}
				portletNamespace={portletNamespace}
				readOnly={readOnly}
			/>
		</FieldBase>
	);
};

Main.displayName = 'ImagePicker';

export default Main;
