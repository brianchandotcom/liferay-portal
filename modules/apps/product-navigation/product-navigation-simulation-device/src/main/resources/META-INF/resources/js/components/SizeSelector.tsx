/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {SIZES, Size} from '../constants/sizes';
import {useSetCustomSize} from '../contexts/CustomSizeContext';

interface ISizeSelectorProps {
	activeSize: Size;
	namespace: string;
	open: boolean;
	previewRef: React.RefObject<HTMLDivElement>;
	setActiveSize: Function;
}

const INITIAL_LIST: Size[] = [
	SIZES.desktop,
	SIZES.tablet,
	SIZES.smartphone,
	SIZES.autosize,
	SIZES.custom,
];

const MAX_CUSTOM_SIZE: number = 9999;
const MIN_CUSTOM_SIZE: number = 1;

export default function SizeSelector({
	activeSize,
	namespace,
	open,
	previewRef,
	setActiveSize,
}: ISizeSelectorProps) {
	const [sizesList, setSizesList] = useState<Size[]>(INITIAL_LIST);

	const onRotate = (size: Size) => {
		const nextList = sizesList.map((_size) => {
			if (_size.id === size.id) {
				return SIZES[size.rotatedId!];
			}

			return _size;
		});

		setSizesList(nextList);
	};

	const customSizeSelectorId = `${namespace}customSizeSelector`;

	return (
		<>
			<ClayLayout.Container>
				<ClayLayout.Row className="size-selector">
					{sizesList.map((size, index) => (
						<SizeButton
							activeSize={activeSize}
							customSizeSelectorId={customSizeSelectorId}
							key={index}
							onRotate={onRotate}
							setActiveSize={setActiveSize}
							size={size}
						/>
					))}
				</ClayLayout.Row>
			</ClayLayout.Container>

			{activeSize.id === SIZES.custom.id && (
				<CustomSizeSelector
					id={customSizeSelectorId}
					namespace={namespace}
					open={open}
					previewRef={previewRef}
				/>
			)}

			<span aria-live="polite" className="sr-only">
				{sub(
					Liferay.Language.get('x-screen-size-is-selected'),
					activeSize.label
				)}
			</span>
		</>
	);
}

SizeSelector.propTypes = {
	activeSize: PropTypes.object.isRequired,
	namespace: PropTypes.string.isRequired,
	previewRef: PropTypes.object.isRequired,
	setActiveSize: PropTypes.func.isRequired,
};

interface ISizeButtonProps {
	activeSize: Size;
	customSizeSelectorId: string;
	onRotate: Function;
	setActiveSize: Function;
	size: Size;
}

function SizeButton({
	activeSize,
	customSizeSelectorId,
	onRotate,
	setActiveSize,
	size,
}: ISizeButtonProps) {
	const {icon, id, label, responsive, rotatedId} = size;

	const onClick = () => {
		if (id === activeSize.id && rotatedId) {
			onRotate(size);

			setActiveSize(SIZES[rotatedId]);
		}
		else {
			setActiveSize(size);
		}
	};

	const labelId = useId();

	return (
		<ClayButton
			aria-controls={
				id === SIZES.custom.id ? customSizeSelectorId : undefined
			}
			aria-current={activeSize.id === id}
			aria-expanded={
				id === SIZES.custom.id
					? activeSize.id === id
						? true
						: false
					: undefined
			}
			aria-labelledby={labelId}
			className={classNames('col-4 size-button text-center', {
				'd-lg-block d-none': !responsive,
				'selected': activeSize.id === id,
			})}
			displayType="unstyled"
			onClick={onClick}
		>
			<span className="icon icon-monospaced">
				<ClayIcon symbol={icon} />
			</span>

			<span className="mt-1" id={labelId}>
				{label}
			</span>
		</ClayButton>
	);
}

SizeButton.propTypes = {
	activeSize: PropTypes.object.isRequired,
	customSizeSelectorId: PropTypes.string.isRequired,
	onRotate: PropTypes.func.isRequired,
	setActiveSize: PropTypes.func.isRequired,
	size: PropTypes.object.isRequired,
};

interface ICustomSizeSelectorProps {
	id: string;
	namespace: string;
	open: boolean;
	previewRef: React.RefObject<HTMLDivElement>;
}

function CustomSizeSelector({
	id,
	namespace,
	open,
	previewRef,
}: ICustomSizeSelectorProps) {
	const setCustomSize = useSetCustomSize();

	const [alertMessage, setAlertMessage] = useState<string | null>(null);
	const [height, setHeight] = useState<number>(
		SIZES.custom.screenSize.height
	);
	const [width, setWidth] = useState<number>(SIZES.custom.screenSize.width);

	const updatePreview = () => {
		if (previewRef.current) {
			previewRef.current.style.height = `${height}px`;
			previewRef.current.style.width = `${width}px`;

			setCustomSize({height, width});

			setAlertMessage(
				sub(
					Liferay.Language.get('custom-size-x-applied'),
					`${height}x${width}`
				)
			);
		}
	};

	useEffect(() => {
		const resizeObserver = new ResizeObserver(([firstEntry]) => {
			const preview = firstEntry.target as HTMLElement;

			setCustomSize({
				height: preview.clientHeight,
				width: preview.clientWidth,
			});
			setHeight(preview.clientHeight);
			setWidth(preview.clientWidth);
		});

		if (previewRef.current && open) {
			resizeObserver.observe(previewRef.current);
		}

		return () => {
			resizeObserver.disconnect();
		};
	}, [open, previewRef, setCustomSize]);

	return (
		<div id={id}>
			<div className="d-flex flex-nowrap mt-4 w-100">
				<ClayForm.Group className="flex-grow-1 mr-3">
					<>
						<label htmlFor={`${namespace}width`}>
							{Liferay.Language.get('width')}

							<span className="sr-only">(px)</span>
						</label>
						<ClayInput.Group>
							<ClayInput.GroupItem prepend>
								<ClayInput
									id={`${namespace}width`}
									max={MAX_CUSTOM_SIZE}
									min={MIN_CUSTOM_SIZE}
									onChange={(event) => {
										const value = Number(
											event.target.value
										);

										if (
											value >= MIN_CUSTOM_SIZE &&
											value <= MAX_CUSTOM_SIZE
										) {
											setWidth(value);
										}
									}}
									sizing="sm"
									type="number"
									value={width}
								/>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem append shrink>
								<span className="input-group-text">PX</span>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</>
				</ClayForm.Group>

				<ClayForm.Group className="flex-grow-1">
					<>
						<label htmlFor={`${namespace}height`}>
							{Liferay.Language.get('height')}

							<span className="sr-only">(px)</span>
						</label>
						<ClayInput.Group>
							<ClayInput.GroupItem prepend>
								<ClayInput
									id={`${namespace}height`}
									max={MAX_CUSTOM_SIZE}
									min={MIN_CUSTOM_SIZE}
									onChange={(event) => {
										const value = Number(
											event.target.value
										);

										if (
											value >= MIN_CUSTOM_SIZE &&
											value <= MAX_CUSTOM_SIZE
										) {
											setHeight(value);
										}
									}}
									sizing="sm"
									type="number"
									value={height}
								/>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem append shrink>
								<span className="input-group-text">PX</span>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</>
				</ClayForm.Group>
			</div>

			<ClayButton
				aria-label={Liferay.Language.get('apply-custom-size')}
				className="w-100"
				displayType="secondary"
				onClick={() => {
					updatePreview();
				}}
			>
				{Liferay.Language.get('apply-custom-size')}
			</ClayButton>

			{alertMessage && (
				<ClayAlert className="mt-3" displayType="info" role="status">
					{alertMessage}
				</ClayAlert>
			)}
		</div>
	);
}

CustomSizeSelector.propTypes = {
	id: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
	previewRef: PropTypes.object.isRequired,
};
