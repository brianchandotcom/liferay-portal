/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import classnames from 'classnames';
import {sub} from 'frontend-js-web';
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
	const [currentView, setCurrentView] = useState(VIEWS[0]);
	const [expanded, setExpanded] = useState(false);
	const [menuData, setMenuData] = useState(null);
	const [menuParentData, setMenuParentData] = useState(null);
	const [modalActive, setModalActive] = useState(false);
	const [modalData, setModalData] = useState(null);
	const [rootData, setRootData] = useState(null);
	const [searchData, setSearchData] = useState(null);
	const [searchDataCount, setSearchDataCount] = useState(0);

	const clickedMenuButtonRef = useRef(null);
	const chartSVGRef = useRef(null);
	const chartInstanceRef = useRef(null);
	const zoomOutRef = useRef(null);
	const zoomInRef = useRef(null);

	useEffect(() => {
		if (Number(rootOrganizationId)) {
			getOrganization(rootOrganizationId).then(setRootData);
		}
		else {
			getOrganizations(pageSize).then((jsonResponse) =>
				setRootData(jsonResponse.items)
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
						setModalData({
							parentData,
							type,
						});
						setModalActive(true);
					},
				},
				{
					close: () => {
						clickedMenuButtonRef.current = null;
						setMenuData(null);
						setMenuParentData(null);
					},
					open: (target, data, parentData) => {
						clickedMenuButtonRef.current = target;
						setMenuData(data);
						setMenuParentData(parentData);
					},
				},
				setSearchDataCount
			);
		}

		return () => chartInstanceRef.current?.cleanUp();
	}, [pageSize, rootData, spritemap]);

	return (
		<ChartContext.Provider
			value={{
				chartInstanceRef,
				currentView,
				setCurrentView,
			}}
		>
			<ManagementBar
				onSearchSelected={(id, name, type) => {
					setSearchData(name);
					if (chartInstanceRef && chartInstanceRef.current) {
						chartInstanceRef.current.search(id, type);
					}
				}}
			/>

			<div className={classnames('org-chart-container', {expanded})}>
				{searchData && !!searchData.length ? (
					<div className="org-chart-result-helper">
						{sub(Liferay.Language.get('x-result-for-x'), [
							searchDataCount,
							searchData,
						])}
					</div>
				) : (
					<></>
				)}

				<svg className="svg-chart" ref={chartSVGRef} />

				<div className="zoom-controls">
					<ClayButtonWithIcon
						displayType="secondary"
						onClick={() => setExpanded(!expanded)}
						size="sm"
						symbol="expand"
					/>

					<ClayButton.Group className="ml-3">
						<ClayButtonWithIcon
							displayType="secondary"
							ref={zoomOutRef}
							size="sm"
							symbol="hr"
						/>

						<ClayButtonWithIcon
							displayType="secondary"
							ref={zoomInRef}
							size="sm"
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
				closeModal={() => setModalActive(false)}
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
