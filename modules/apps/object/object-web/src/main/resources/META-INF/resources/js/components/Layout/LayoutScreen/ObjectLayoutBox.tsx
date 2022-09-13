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
import {Panel, PanelBody, PanelHeader} from '@liferay/object-js-components-web';
import React, {useState} from 'react';

import {TYPES, useLayoutContext} from '../objectLayoutContext';
import {BoxType, TObjectLayoutRow} from '../types';
import {HeaderDropdown} from './HeaderDropdown';
import ModalAddObjectLayoutField from './ModalAddObjectLayoutField';
import {ObjectLayoutRows} from './ObjectLayoutRows';

interface ObjectLayoutBoxProps extends React.HTMLAttributes<HTMLElement> {
	boxIndex: number;
	collapsable: boolean;
	label: string;
	objectLayoutRows?: TObjectLayoutRow[];
	tabIndex: number;
	type: BoxType;
}

export function ObjectLayoutBox({
	boxIndex,
	collapsable,
	label,
	objectLayoutRows,
	tabIndex,
	type,
}: ObjectLayoutBoxProps) {
	const [{enabledCategorization, isViewOnly}, dispatch] = useLayoutContext();
	const [visibleModal, setVisibleModal] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const disabled =
		(Liferay.FeatureFlags['LPS-158672'] &&
			type === 'categorization' &&
			!enabledCategorization) ||
		isViewOnly;

	return (
		<>
			<Panel>
				<PanelHeader
					contentRight={
						<>
							<ClayToggle
								aria-label={Liferay.Language.get('collapsible')}
								disabled={disabled}
								label={Liferay.Language.get('collapsible')}
								onToggle={(value) => {
									dispatch({
										payload: {
											attribute: {
												key: 'collapsable',
												value,
											},
											boxIndex,
											tabIndex,
										},
										type:
											TYPES.CHANGE_OBJECT_LAYOUT_BOX_ATTRIBUTE,
									});
								}}
								toggled={collapsable}
							/>

							{type === 'regular' && (
								<ClayButton
									className="ml-4"
									disabled={isViewOnly}
									displayType="secondary"
									onClick={() => setVisibleModal(true)}
									small
								>
									{Liferay.Language.get('add-field')}
								</ClayButton>
							)}

							<HeaderDropdown
								deleteElement={() => {
									dispatch({
										payload: {
											boxIndex,
											tabIndex,
										},
										type: TYPES.DELETE_OBJECT_LAYOUT_BOX,
									});
								}}
								disabled={disabled}
							/>
						</>
					}
					disabled={disabled}
					title={label}
					type={type}
				/>

				{!!objectLayoutRows?.length && (
					<PanelBody>
						<ObjectLayoutRows
							boxIndex={boxIndex}
							objectLayoutRows={objectLayoutRows}
							tabIndex={tabIndex}
						/>
					</PanelBody>
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
}
