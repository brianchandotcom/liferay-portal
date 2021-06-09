/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React from 'react';

const DiagramFooter = ({
	enableResetZoom,
	selectedOption,
	setChangedScale,
	setResetZoom,
	setSelectedOption,
	setZoomInHandler,
	setZoomOutHandler,
	spritemap,
}) => {
	const handleZoomChange = (event) => {
		setSelectedOption(event.target.value);
		setChangedScale(true);
	};

	const options = [2, 1.75, 1.5, 1.25, 1, 0.75, 0.5];

	return (
		<div className="d-flex diagram-footer justify-content-end mt-3">
			<ClayButton className="mr-3">
				<span className="inline-item inline-item-before">
					<ClayIcon spritemap={spritemap} symbol="expand" />
				</span>
				Expand
			</ClayButton>

			<div className="d-flex">
				<ClayButton
					className=""
					displayType="secondary"
					onClick={() => {
						setZoomOutHandler(true);
					}}
				>
					-
				</ClayButton>

				<ClaySelect
					aria-label="Select Label"
					className="ml-3 mr-3"
					id="mySelectId"
					onChange={handleZoomChange}
					value={selectedOption}
				>
					{options.map((value) => (
						<ClaySelect.Option
							key={value}
							label={`${value * 100}%`}
							value={value}
						/>
					))}
				</ClaySelect>

				<ClayButton
					className=""
					displayType="secondary"
					onClick={() => {
						setZoomInHandler(true);
					}}
				>
					+
				</ClayButton>
			</div>

			{enableResetZoom && (
				<ClayButton
					className="ml-3 reset-zoom"
					displayType="secondary"
					id="reset"
					onClick={() => setResetZoom(true)}
				>
					Reset Zoom
				</ClayButton>
			)}
		</div>
	);
};

export default DiagramFooter;
