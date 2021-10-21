import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useEffect, useRef, useState} from 'react';

import {
	chooseIcon,
	validateExtensions,
} from '../Steps/UploadDocuments/upload.util';

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

		if (countFiles <= limitFiles) {
			for (let i = 0; i < currentFiles.length; i++) {
				const fileType = currentFiles[i].type;

				if (validateExtensions(fileType, type)) {
					const fileReader = new FileReader();
					fileReader.onload = () => {
						let fileURL = '';

						if (type === 'image') {
							fileURL = fileReader.result;
						} else {
							const json = JSON.stringify({
								dataURL: fileReader.result,
							});

							fileURL = JSON.parse(json).dataURL;
						}

						currentFiles[i].icon = chooseIcon(fileType);
						currentFiles[i].id = `${
							currentFiles[i].name
						}-${Math.random()}`;
						currentFiles[i].fileURL = fileURL;

						_setFiles([...filesRef.current, currentFiles[i]]);
					};

					fileReader.readAsDataURL(currentFiles[i]);
				} else {
					alert('Invalid file!');
				}
			}
		} else {
			setShowBadgeInfo(true);
		}
	};

	useEffect(() => {
		const dropArea = dropAreaRef.current,
			button = buttonRef.current,
			input = inputRef.current;

		button.onclick = () => {
			input.click();
		};

		input.addEventListener('change', function () {
			showFile(this.files);
		});

		dropArea.addEventListener('dragover', (event) => {
			event.preventDefault();
		});

		dropArea.addEventListener('drop', (event) => {
			event.preventDefault();

			showFile(event.dataTransfer.files);
		});

		if (type === 'image') {
			_setWidth(BASE_WIDTH);
			_setHeight(BASE_HEIGHT);
		}
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
				hide: !showUpload,
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
