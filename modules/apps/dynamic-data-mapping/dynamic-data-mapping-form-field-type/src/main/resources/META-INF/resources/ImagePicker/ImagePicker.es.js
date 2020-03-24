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

import './ImagePickerRegister.soy';

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {ItemSelectorDialog, cancelDebounce, debounce} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import templates from './ImagePickerAdapter.soy';

const useDebounceCallback = (callback, milliseconds) => {
	const callbackRef = useRef(debounce(callback, milliseconds));

	return [callbackRef.current, () => cancelDebounce(callbackRef.current)];
};

const ImagePicker = ({
	id,
	inputValue = '',
	itemSelectorURL,
	name,
	onClearClick,
	onDescriptionChange,
	onFieldChanged,
	portletNamespace,
	readOnly,
}) => {
	const [imageValues, setImageValues] = useState({});
	const [modalVisible, setModalVisible] = useState(false);

	useEffect(() => {
		setImageValues({
			...{description: '', title: '', url: ''},
			...JSON.parse(inputValue || '{}'),
		});
	}, [inputValue]);

	const {observer} = useModal({
		onClose: () => setModalVisible(false),
	});

	const dispatchValue = ({clear, value}, callback) => {
		setImageValues(oldValues => {
			let mergedValues = {...oldValues, ...value};

			mergedValues = clear ? {} : mergedValues;

			if (callback) {
				callback(mergedValues);
			}

			return mergedValues;
		});
	};

	const handleClearClick = event => {
		dispatchValue(
			{clear: true, value: {description: '', event, title: '', url: ''}},
			mergedValues => {
				onClearClick(mergedValues);
			}
		);
	};

	const [debounce] = useDebounceCallback(({event, value}) => {
		dispatchValue({value: {description: value, event}}, mergedValues => {
			onDescriptionChange(mergedValues);
		});
	}, 500);

	const handleDescriptionChange = ({event, target: {value}}) =>
		debounce({event, value});

	const handleFieldChanged = event => {
		const selectedItem = event.selectedItem;

		if (selectedItem && selectedItem.value) {
			const img = new Image();
			const item = JSON.parse(selectedItem.value);
			img.addEventListener('load', event => {
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

				dispatchValue({value: imageData}, mergedValues => {
					onFieldChanged(mergedValues);
				});
			});
			img.src = item.url;
		}
	};

	const handleItemSelectorTriggerClick = event => {
		event.preventDefault();

		const itemSelectorDialog = new ItemSelectorDialog({
			eventName: `${portletNamespace}selectDocumentLibrary`,
			singleSelect: true,
			url: itemSelectorURL,
		});

		itemSelectorDialog.on('selectedItemChange', handleFieldChanged);

		itemSelectorDialog.open();
	};

	const spritemap =
		Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg';

	const placeholder = readOnly
		? ''
		: Liferay.Language.get('add-image-description');

	let {height = '0', width = '0'} = {
		...JSON.parse(inputValue || '{}'),
	};

	height = `${height}px`;
	width = `${width}px`;

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

			{imageValues.url && modalVisible && (
				<ClayModal
					className="image-picker-preview-modal"
					observer={observer}
					size="full-screen"
					spritemap={spritemap}
				>
					<ClayModal.Header />
					<ClayModal.Body>
						<img
							alt={imageValues.description}
							className="d-block img-fluid mb-2 mx-auto rounded"
							onClick={() => setModalVisible(false)}
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
			)}

			{imageValues.url && (
				<>
					<div className="image-picker-preview">
						<img
							alt={imageValues.description}
							className="d-block img-fluid mb-2 rounded"
							src={imageValues.url}
						/>
						<div
							className="image-picker-priview-backdor"
							onClick={() => setModalVisible(true)}
							style={{
								height,
								width,
							}}
						>
							<ClayIcon symbol="search" />
						</div>
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
			)}
		</>
	);
};

const ImagePickerProxy = connectStore(({emit, ...otherProps}) => (
	<ImagePicker
		{...otherProps}
		onClearClick={data => {
			const {event} = data;

			emit('fieldEdited', event, data);
		}}
		onDescriptionChange={data => {
			const {event} = data;

			emit('fieldEdited', event, data);
		}}
		onFieldChanged={data => {
			const {event} = data;

			emit('fieldEdited', event, data);
		}}
	/>
));

const ReactImagePickerAdapter = getConnectedReactComponentAdapter(
	ImagePickerProxy,
	templates
);

export {ReactImagePickerAdapter};
export default ReactImagePickerAdapter;
