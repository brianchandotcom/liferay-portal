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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useEffect, useRef, useState} from 'react';

import {
	chooseIcon,
	validateExtensions,
} from '../Steps/UploadDocuments/utils/upload';

const BASE_WIDTH = '176px';
const BASE_HEIGHT = '176px';

const DropArea = ({
	dropAreaProps: {heightContainer, limitFiles, type, widthContainer},
	files = [],
	setFiles,
	setShowBadgeInfo,
}) => {
	const [width, setWidth] = useState(widthContainer);
	const [height, setHeight] = useState(heightContainer);
	const [showUpload, setShowUpload] = useState(false);

	const buttonRef = useRef();
	const dropAreaRef = useRef();
	const filesRef = useRef(files);
	const heightRef = useRef(height);
	const inputRef = useRef();
	const showUploadRef = useRef(showUpload);
	const widthRef = useRef(width);

	const _setFiles = (data) => {
		filesRef.current = data;

		setFiles(data);
	};

	const _setHeight = (data) => {
		heightRef.current = data;

		setHeight(data);
	};

	const _setShowUpload = (data) => {
		showUploadRef.current = data;

		setShowUpload(data);
	};

	const _setWidth = (data) => {
		widthRef.current = data;

		setWidth(data);
	};

	function getFileReader(file) {
		return new Promise((resolve, reject) => {
			const fileReader = new FileReader();

			fileReader.readAsDataURL(file);

			fileReader.onload = () => {
				resolve(fileReader);
			};

			fileReader.onerror = reject;
		});
	}

	const showFile = async (currentFiles) => {
		const countFiles = filesRef.current.length + currentFiles.length;

		if (countFiles > limitFiles) {
			return setShowBadgeInfo(true);
		}

		for (const currentFile of currentFiles) {
			const fileName = currentFile.name;
			const fileType = currentFile.type;

			const currentFileExist = filesRef.current.some(
				(file) => file.name === fileName
			);

			if (currentFileExist) {
				alert(`File ${fileName} already exists!`);

				continue;
			}

			if (!validateExtensions(fileType, type)) {
				alert(`Invalid file ${fileName}!`);

				continue;
			}

			const fileReader = await getFileReader(currentFile);

			currentFile.icon = chooseIcon(fileType);
			currentFile.id = `${fileName}-${Math.random()}`;
			currentFile.fileURL = fileReader.result;

			_setFiles([...filesRef.current, currentFile]);
		}
	};

	useEffect(() => {
		filesRef.current = files;
	}, [files]);

	useEffect(() => {
		const button = buttonRef.current;
		const dropArea = dropAreaRef.current;
		const input = inputRef.current;

		if (type === 'image') {
			_setWidth(BASE_WIDTH);
			_setHeight(BASE_HEIGHT);
		}

		button.onclick = () => {
			input.value = null;
			input.click();
		};

		const onChangeFile = function () {
			showFile(this.files);
		};

		const onDragOverFile = (event) => {
			event.preventDefault();
		};

		const onDropFile = (event) => {
			event.preventDefault();

			showFile(event.dataTransfer.files);
		};

		dropArea.addEventListener('dragover', onDragOverFile);
		dropArea.addEventListener('drop', onDropFile);
		input.addEventListener('change', onChangeFile);

		return () => {
			dropArea.removeEventListener('dragover', onDragOverFile);
			dropArea.removeEventListener('drop', onDropFile);
			input.removeEventListener('change', onChangeFile);
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		const countFiles = files.length;

		if (countFiles >= 1 && countFiles < limitFiles) {
			_setWidth(BASE_WIDTH);
			_setHeight(BASE_HEIGHT);
			_setShowUpload(true);

			return setShowBadgeInfo(false);
		}

		if (countFiles === 0 && type !== 'image') {
			_setWidth(widthContainer);
			_setHeight(heightContainer);
		}

		_setShowUpload(true);

		if (countFiles !== 0) {
			setShowBadgeInfo(true);
			_setShowUpload(false);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [files]);

	return (
		<div
			className={classNames(
				'align-items-center bg-brand-primary-lighten-6 font-weight-normal text-neutral-8 d-flex drop-area flex-wrap justify-content-center position-static rounded-xl text-paragraph',
				{
					// eslint-disable-next-line quote-props
					'hide': !showUpload,
					'margin-left': files.length > 0,
				}
			)}
			ref={dropAreaRef}
			style={{
				height: `${height}`,
				width: `${width}`,
			}}
		>
			<div className="align-items-center d-flex flex-wrap justify-content-center upload-button">
				<p className="c-px-2">
					Drag &amp; drop files or
					{type !== 'image' && <span>&nbsp;</span>}
				</p>

				<a
					className="align-items-center c-px-3 c-py-2 d-flex font-weight-bolder justify-content-center link-button rounded-xs text-brand-primary text-paragraph-sm"
					ref={buttonRef}
				>
					<ClayIcon className="c-mr-2" symbol="upload" />
					BROWSE FILES
				</a>

				{` `}

				<input
					className="d-none input-file position-relative rounded-xl"
					multiple
					name="input-file"
					ref={inputRef}
					style={{
						height: `${height}`,
						top: `-${height}`,
						width: `${width}`,
					}}
					type="file"
				/>
			</div>
		</div>
	);
};

export default DropArea;
