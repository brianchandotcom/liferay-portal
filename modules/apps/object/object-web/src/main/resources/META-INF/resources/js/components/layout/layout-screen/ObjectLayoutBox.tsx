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
import {ClayToggle} from '@clayui/form';
import {useModal} from '@clayui/modal';
import React, {useContext, useState} from 'react';

import Panel from '../../Panel/Panel';
import LayoutContext, {TYPES} from '../context';
import {TObjectLayoutRow} from '../types';
import DropdownWithDeleteButton from './DropdownWithDeleteButton';
import ModalAddObjectLayoutField from './ModalAddObjectLayoutField';
import ObjectLayoutRows from './ObjectLayoutRows';

interface IObjectLayoutBoxProps extends React.HTMLAttributes<HTMLElement> {
	boxIndex: number;
	tabIndex: number;
	collapsable: boolean;
	label: string;
	objectLayoutRows?: TObjectLayoutRow[];
}

const ObjectLayoutBox: React.FC<IObjectLayoutBoxProps> = ({
	boxIndex,
	collapsable,
	label,
	objectLayoutRows,
	tabIndex,
}) => {
	const [, dispatch] = useContext(LayoutContext);
	const [visibleModal, setVisibleModal] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	return (
		<>
			<Panel>
				<Panel.Header
					contentRight={
						<>
							<ClayToggle
								aria-label={Liferay.Language.get('collapsable')}
								label={Liferay.Language.get('collapsable')}
								onToggle={(value) => {
									dispatch({
										payload: {
											boxIndex,
											collapsable: value,
											tabIndex,
										},
										type:
											TYPES.CHANGE_OBJECT_LAYOUT_BOX_ATTRIBUTE,
									});
								}}
								toggled={collapsable}
							/>

							<ClayButton
								className="ml-4"
								displayType="secondary"
								onClick={() => setVisibleModal(true)}
								small
							>
								{Liferay.Language.get('add-field')}
							</ClayButton>

							<DropdownWithDeleteButton
								onClick={() => {
									dispatch({
										payload: {
											boxIndex,
											tabIndex,
										},
										type: TYPES.DELETE_OBJECT_LAYOUT_BOX,
									});
								}}
							/>
						</>
					}
					title={label}
				/>

				{!!objectLayoutRows?.length && (
					<Panel.Body>
						<ObjectLayoutRows
							boxIndex={boxIndex}
							objectLayoutRows={objectLayoutRows}
							tabIndex={tabIndex}
						/>
					</Panel.Body>
				)}
			</Panel>

			{visibleModal && (
				<ModalAddObjectLayoutField
					boxIndex={boxIndex}
					observer={observer}
					onClose={onClose}
					tabIndex={tabIndex}
				/>
			)}
		</>
	);
};

export default ObjectLayoutBox;
