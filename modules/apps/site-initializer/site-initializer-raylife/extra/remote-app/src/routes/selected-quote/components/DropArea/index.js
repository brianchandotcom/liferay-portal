/* eslint-disable @liferay/empty-line-between-elements */
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

	const showFile = (currentFiles) => {
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

			const fileReader = new FileReader();

			fileReader.onload = () => {
				const fileURL = fileReader.result;

				currentFile.icon = chooseIcon(fileType);
				currentFile.id = `${fileName}-${Math.random()}`;
				currentFile.fileURL = fileURL;

				_setFiles([...filesRef.current, currentFile]);
			};

			fileReader.readAsDataURL(currentFile);
		}
	};

	useEffect(() => {
		const button = buttonRef.current;
		const dropArea = dropAreaRef.current;
		const input = inputRef.current;

		if (type === 'image') {
			_setWidth(BASE_WIDTH);
			_setHeight(BASE_HEIGHT);
		}

		button.onclick = () => {
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

	useEffect(() => {
		filesRef.current = files;
	}, [files]);

	return (
		<div
			className={classNames('drop-area', {
				// eslint-disable-next-line quote-props
				'hide': !showUpload,
				'margin-left': files.length > 0,
			})}
			ref={dropAreaRef}
			style={{
				height: `${height}`,
				width: `${width}`,
			}}
		>
			<div className="upload-button">
				Drag &amp; drop files or
				{type !== 'image' && <span>&nbsp;</span>}
				<a className="link-button" ref={buttonRef}>
					<ClayIcon symbol="upload" />
					BROWSE FILES
				</a>
				{` `}
				<input
					className="input-file"
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
