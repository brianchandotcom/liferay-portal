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
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {SIZES, Size} from '../constants/sizes';

interface ISizeSelectorProps {
	activeSize: Size;
	namespace: string;
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

export default function SizeSelector({
	activeSize,
	namespace,
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

	return (
		<ClayLayout.Container>
			<ClayLayout.Row className="default-devices">
				{sizesList.map((size) => (
					<SizeButton
						activeSize={activeSize}
						key={size.id}
						onRotate={onRotate}
						setActiveSize={setActiveSize}
						size={size}
					/>
				))}
			</ClayLayout.Row>

			{activeSize.id === SIZES.custom.id && (
				<CustomSizeSelector
					namespace={namespace}
					previewRef={previewRef}
				/>
			)}
		</ClayLayout.Container>
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
	onRotate: Function;
	setActiveSize: Function;
	size: Size;
}

function SizeButton({
	activeSize,
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

	return (
		<ClayButton
			className={classNames('col-4 lfr-device-item text-center', {
				'd-lg-block d-none': !responsive,
				'selected': activeSize.id === id,
			})}
			displayType="unstyled"
			onClick={onClick}
		>
			<span className="icon icon-monospaced">
				<ClayIcon symbol={icon} />
			</span>

			<span className="mt-1">{label}</span>
		</ClayButton>
	);
}

SizeButton.propTypes = {
	activeSize: PropTypes.object.isRequired,
	onRotate: PropTypes.func.isRequired,
	setActiveSize: PropTypes.func.isRequired,
	size: PropTypes.object.isRequired,
};

interface ICustomSizeSelectorProps {
	namespace: string;
	previewRef: React.RefObject<HTMLDivElement>;
}

function CustomSizeSelector({namespace, previewRef}: ICustomSizeSelectorProps) {
	const [height, setHeight] = useState<number>(
		SIZES.custom.screenSize.height
	);
	const [width, setWidth] = useState<number>(SIZES.custom.screenSize.width);

	useEffect(() => {
		const resizeObserver = new ResizeObserver(([firstEntry]) => {
			const preview = firstEntry.target as HTMLElement;

			setHeight(preview.offsetHeight);
			setWidth(preview.offsetWidth);
		});

		if (previewRef.current) {
			resizeObserver.observe(previewRef.current);
		}

		return () => {
			resizeObserver.disconnect();
		};
	}, [previewRef]);

	return (
		<>
			<div className="d-flex flex-nowrap mt-4">
				<ClayForm.Group className="mr-3">
					<label htmlFor={`${namespace}height`}>
						{Liferay.Language.get('height') + ' (px):'}
					</label>

					<ClayInput
						id={`${namespace}height`}
						onChange={(event) =>
							setHeight(Number(event.target.value))
						}
						type="number"
						value={height}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor={`${namespace}width`}>
						{Liferay.Language.get('width') + ' (px):'}
					</label>

					<ClayInput
						id={`${namespace}width`}
						onChange={(event) =>
							setWidth(Number(event.target.value))
						}
						type="number"
						value={width}
					/>
				</ClayForm.Group>
			</div>

			<ClayButton
				aria-label={Liferay.Language.get('apply-custom-size')}
				className="w-100"
				displayType="secondary"
				onClick={() => {
					if (previewRef.current) {
						previewRef.current.style.height = `${height}px`;
						previewRef.current.style.width = `${width}px`;
					}
				}}
			>
				{Liferay.Language.get('apply-custom-size')}
			</ClayButton>
		</>
	);
}

CustomSizeSelector.propTypes = {
	namespace: PropTypes.string.isRequired,
	previewRef: PropTypes.object.isRequired,
};
