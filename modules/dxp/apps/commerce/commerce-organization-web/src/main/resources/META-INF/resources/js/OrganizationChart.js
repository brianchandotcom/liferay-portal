/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {
	useCallback,
	useEffect,
	useLayoutEffect,
	useRef,
	useState,
} from 'react';

import ChartContext from './ChartContext';
import D3OrganizationChart from './D3OrganizationChart';
import ManagementBar from './ManagementBar/ManagementBar';
import ResultsBar from './ManagementBar/ResultsBar';
import {getOrganization, getOrganizations} from './data/organizations';
import MenuProvider from './menu/MenuProvider';
import ModalProvider from './modals/ModalProvider';
import InfoPanelProvider from './panels/InfoPanelProvider';
import {
	DEFAULT_PAGE_SIZE,
	MAX_DISPLAYED_ORGANIZATIONS,
	VIEWS,
} from './utils/constants';
import {findRoot} from './utils/findRoot';

import '../style/main.scss';

function OrganizationChart({
	maxDisplayedOrganizations,
	namespace,
	pageSize,
	pathImage,
	rootOrganizationId,
	selectLogoURL,
	spritemap,
}) {
	const [alertMessage, setAlertMessage] = useState('');
	const [configRootOrganizationId] = useState(Number(rootOrganizationId));
	const [currentView, setCurrentView] = useState(VIEWS[0]);
	const [expanded, setExpanded] = useState(false);
	const [menuData, setMenuData] = useState(null);
	const [menuParentData, setMenuParentData] = useState(null);
	const [modalActive, setModalActive] = useState(false);
	const [modalData, setModalData] = useState(null);
	const [organizations, setOrganizations] = useState([]);
	const [organizationsIds, setOrganizationsIds] = useState([]);
	const [rootData, setRootData] = useState(null);
	const [searchResult, setSearchResult] = useState({
		id: null,
		name: '',
		type: '',
	});
	const [searchResultCount, setSearchResultCount] = useState(0);

	const chartInstanceRef = useRef(null);
	const chartSVGRef = useRef(null);
	const clickedMenuButtonRef = useRef(null);
	const zoomInRef = useRef(null);
	const zoomOutRef = useRef(null);

	const fetchOrganizations = useCallback(
		(ids = []) => {
			if (chartInstanceRef.current) {
				chartInstanceRef.current.destroyChart();
			}

			setRootData(null);

			const get = () =>
				ids[0] === configRootOrganizationId
					? getOrganization(configRootOrganizationId).then((item) =>
							Promise.resolve({items: [item]})
						)
					: getOrganizations(pageSize, ids);

			return get().then(({items}) => {
				if (items.length) {
					setOrganizations(items);
				}
				else {

					/**
					 * Root organizations not found.
					 * This means either that:
					 * 	- no permission to view the selected root org
					 * 	- there are in fact no root organizations at all
					 */

					setAlertMessage(
						Liferay.Language.get('no-root-organizations-were-found')
					);
				}
			});
		},
		[configRootOrganizationId, pageSize]
	);

	useEffect(() => {
		const isTooManyOrganizations =
			organizations.length &&
			organizations.length > maxDisplayedOrganizations;

		setRootData(isTooManyOrganizations ? null : organizations);

		setOrganizationsIds(
			isTooManyOrganizations
				? []
				: organizations.map(({id}) => Number(id))
		);

		setAlertMessage(
			isTooManyOrganizations
				? Liferay.Language.get(
						'you-have-too-many-organizations-to-display'
					)
				: ''
		);
	}, [maxDisplayedOrganizations, organizations]);

	useEffect(() => {

		// Root organization from widget configuration or all roots

		fetchOrganizations(
			configRootOrganizationId ? [configRootOrganizationId] : []
		);
	}, [fetchOrganizations, configRootOrganizationId]);

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
				namespace,
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
				setSearchResultCount
			);
		}

		return () => chartInstanceRef.current?.cleanUp();
	}, [namespace, rootData, spritemap]);

	useEffect(() => {
		document.body.classList[expanded ? 'add' : 'remove']('overflow-hidden');
	}, [expanded]);

	return (
		<ChartContext.Provider
			value={{
				chartInstanceRef,
				configRootOrganizationId,
				currentView,
				fetchOrganizations,
				namespace,
				organizations,
				organizationsIds,
				pageSize,
				pathImage,
				searchResult,
				selectLogoURL,
				setCurrentView,
				spritemap,
			}}
		>
			<div
				className={classnames('org-management-portlet-wrapper', {
					expanded,
				})}
			>
				<ManagementBar
					onSearchSelected={(id, name, type) => {
						if (id && name && type) {

							/**
							 * Preventing useless network requests
							 * as much as possible
							 */
							const willFindRoot = () =>
								configRootOrganizationId
									? Promise.resolve([
											configRootOrganizationId,
										])
									: findRoot(id, type);

							willFindRoot()
								.then((foundRootIds) => {
									const isOrgInView = foundRootIds.find(
										(foundRootId) =>
											organizationsIds.includes(
												foundRootId
											)
									);

									return isOrgInView
										? Promise.resolve()
										: fetchOrganizations(foundRootIds);
								})
								.then(() => {
									setSearchResult({id, name, type});

									if (
										chartInstanceRef &&
										chartInstanceRef.current
									) {
										chartInstanceRef.current.search(
											id,
											type
										);
									}
								});
						}
					}}
				/>

				{alertMessage ? (
					<div className="container-fluid container-fluid-max-xl py-4">
						<ClayAlert
							displayType="info"
							spritemap={spritemap}
							title={Liferay.Language.get('info')}
						>
							{alertMessage}
						</ClayAlert>
					</div>
				) : (
					<div
						className={classnames('org-chart-container', {
							expanded,
						})}
					>
						{searchResult.name && (
							<ResultsBar
								searchResult={searchResult}
								searchResultCount={searchResultCount}
								setSearchResult={setSearchResult}
							/>
						)}

						<svg className="svg-chart" ref={chartSVGRef} />

						<div className="zoom-controls">
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get('full-screen')}
								displayType="secondary"
								onClick={() => setExpanded(!expanded)}
								size="sm"
								symbol={expanded ? 'compress' : 'expand'}
							/>

							<ClayButton.Group className="ml-3">
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get(
										'zoom-out'
									)}
									displayType="secondary"
									ref={zoomOutRef}
									size="sm"
									symbol="hr"
								/>

								<ClayButtonWithIcon
									aria-label={Liferay.Language.get('zoom-in')}
									displayType="secondary"
									ref={zoomInRef}
									size="sm"
									symbol="plus"
								/>
							</ClayButton.Group>
						</div>
					</div>
				)}

				<InfoPanelProvider
					namespace={namespace}
					pathImage={pathImage}
					selectLogoURL={selectLogoURL}
					spritemap={spritemap}
				/>

				<MenuProvider
					alignElementRef={clickedMenuButtonRef}
					data={menuData}
					namespace={namespace}
					parentData={menuParentData}
				/>

				<ModalProvider
					active={modalActive}
					closeModal={() => setModalActive(false)}
					parentData={modalData?.parentData}
					type={modalData?.type}
				/>
			</div>
		</ChartContext.Provider>
	);
}

OrganizationChart.defaultProps = {
	maxDisplayedOrganizations: MAX_DISPLAYED_ORGANIZATIONS,
	pageSize: DEFAULT_PAGE_SIZE,
	pathImage: '/image',
	rootOrganizationId: 0,
};

OrganizationChart.propTypes = {
	maxDisplayedOrganizations: PropTypes.number,
	namespace: PropTypes.string,
	pageSize: PropTypes.number,
	pathImage: PropTypes.string.isRequired,
	rootOrganizationId: PropTypes.string,
	selectLogoURL: PropTypes.string.isRequired,
	spritemap: PropTypes.string.isRequired,
};

export default OrganizationChart;
