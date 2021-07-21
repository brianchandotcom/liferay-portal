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
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useLayoutEffect, useRef, useState} from 'react';

import ChartContext from './ChartContext';
import D3OrganizationChart from './D3OrganizationChart';
import ManagementBar from './ManagementBar/ManagementBar';
import {getOrganization, getOrganizations} from './data/organizations';
import MenuProvider from './menu/MenuProvider';
import ModalProvider from './modals/ModalProvider';
import {VIEWS} from './utils/constants';

import '../style/main.scss';
function OrganizationChart({pageSize, rootOrganizationId, spritemap}) {
	const [modalActive, updateModalActive] = useState(false);
	const [modalData, updateModalData] = useState(null);
	const [currentView, updateCurrentView] = useState(VIEWS[0]);
	const [expanded, updateExpanded] = useState(false);
	const [menuData, updateMenuData] = useState(null);
	const [menuParentData, updateMenuParentData] = useState(null);
	const [rootData, updateRootData] = useState(null);
	const clickedMenuButtonRef = useRef(null);
	const chartSVGRef = useRef(null);
	const chartInstanceRef = useRef(null);
	const zoomOutRef = useRef(null);
	const zoomInRef = useRef(null);

	useEffect(() => {
		if (Number(rootOrganizationId)) {
			getOrganization(rootOrganizationId).then(updateRootData);
		}
		else {
			getOrganizations(pageSize).then((jsonResponse) =>
				updateRootData(jsonResponse.items)
			);
		}
	}, [rootOrganizationId, pageSize]);

	useLayoutEffect(() => {
		if (rootData && chartSVGRef.current) {
			chartInstanceRef.current = new D3OrganizationChart(
				rootData,
				{
					svg: chartSVGRef.current,
					zoomIn: zoomInRef.current,
					zoomOut: zoomOutRef.current,
				},
				spritemap,
				{
					open: (parentData, type) => {
						updateModalData({
							parentData,
							type,
						});
						updateModalActive(true);
					},
				},
				{
					close: () => {
						clickedMenuButtonRef.current = null;
						updateMenuData(null);
						updateMenuParentData(null);
					},
					open: (target, data, parentData) => {
						clickedMenuButtonRef.current = target;
						updateMenuData(data);
						updateMenuParentData(parentData);
					},
				}
			);
		}

		return () => chartInstanceRef.current?.cleanUp();
	}, [pageSize, rootData, spritemap]);

	return (
		<ChartContext.Provider
			value={{
				chartInstanceRef,
				currentView,
				updateCurrentView,
			}}
		>
			<ManagementBar />

			<div className={classnames('org-chart-container', {expanded})}>
				<svg className="svg-chart" ref={chartSVGRef} />

				<div className="zoom-controls">
					<ClayButtonWithIcon
						displayType="secondary"
						onClick={() => updateExpanded(!expanded)}
						small
						symbol="expand"
					/>

					<ClayButton.Group className="ml-3">
						<ClayButtonWithIcon
							displayType="secondary"
							ref={zoomOutRef}
							small
							symbol="hr"
						/>

						<ClayButtonWithIcon
							displayType="secondary"
							ref={zoomInRef}
							small
							symbol="plus"
						/>
					</ClayButton.Group>
				</div>
			</div>

			<MenuProvider
				alignElementRef={clickedMenuButtonRef}
				data={menuData}
				parentData={menuParentData}
			/>

			<ModalProvider
				active={modalActive}
				closeModal={() => updateModalActive(false)}
				parentData={modalData?.parentData}
				type={modalData?.type}
			/>
		</ChartContext.Provider>
	);
}

OrganizationChart.defaultProps = {
	pageSize: 10,
	rootOrganizationId: 0,
};

OrganizationChart.propTypes = {
	pageSize: PropTypes.number,
	rootOrganizationId: PropTypes.number,
	spritemap: PropTypes.string.isRequired,
};

export default OrganizationChart;
