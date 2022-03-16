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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {useMemo} from 'react';

import {ZOOM_STEP, ZOOM_VALUES} from '../utilities/constants';

function DiagramFooter({
	chartInstance,
	currentZoom,
	expanded,
	updateCurrentZoom,
	updateExpanded,
}) {
	function _handleZoomUpdate(value) {
		chartInstance.current?.updateZoom(value);

		updateCurrentZoom(value);
	}

	const zoomValues = useMemo(() => {
		return ZOOM_VALUES.includes(currentZoom)
			? ZOOM_VALUES
			: ZOOM_VALUES.concat(currentZoom).sort();
	}, [currentZoom]);

	return (
		<ManagementToolbar.Container className="py-2">
			<div className="d-flex flex-grow-1 justify-content-end">
				<ManagementToolbar.ItemList>
					<ManagementToolbar.Item>
						<ClayButton
							className="ml-1"
							displayType="secondary"
							onClick={() => updateExpanded(!expanded)}
						>
							<span className="inline-item inline-item-before">
								<ClayIcon
									symbol={expanded ? 'compress' : 'expand'}
								/>
							</span>

							{expanded
								? Liferay.Language.get('compress')
								: Liferay.Language.get('expand')}
						</ClayButton>
					</ManagementToolbar.Item>

					<ManagementToolbar.Item>
						<ClayButtonWithIcon
							className="ml-1"
							disabled={currentZoom <= ZOOM_VALUES[0]}
							displayType="secondary"
							monospaced
							onClick={() => {
								let newZoom = currentZoom - ZOOM_STEP;

								if (!ZOOM_VALUES.includes(currentZoom)) {
									newZoom =
										zoomValues[
											zoomValues.indexOf(currentZoom) - 1
										];
								}

								_handleZoomUpdate(newZoom);
							}}
							symbol="hr"
						/>
					</ManagementToolbar.Item>

					<ManagementToolbar.Item>
						<ClaySelect
							className="ml-1"
							onChange={(event) => {
								_handleZoomUpdate(Number(event.target.value));
							}}
							value={currentZoom}
						>
							{zoomValues.map((value) => (
								<ClaySelect.Option
									key={value}
									label={`${(value * 100).toFixed(0)}%`}
									value={value}
								/>
							))}
						</ClaySelect>
					</ManagementToolbar.Item>

					<ManagementToolbar.Item>
						<ClayButtonWithIcon
							className="ml-1"
							disabled={
								currentZoom >=
								ZOOM_VALUES[ZOOM_VALUES.length - 1]
							}
							displayType="secondary"
							monospaced
							onClick={() => {
								let newZoom = currentZoom + ZOOM_STEP;

								if (!ZOOM_VALUES.includes(currentZoom)) {
									newZoom =
										zoomValues[
											zoomValues.indexOf(currentZoom) + 1
										];
								}

								_handleZoomUpdate(newZoom);
							}}
							symbol="plus"
						/>
					</ManagementToolbar.Item>
				</ManagementToolbar.ItemList>
			</div>
		</ManagementToolbar.Container>
	);
}

export default DiagramFooter;
